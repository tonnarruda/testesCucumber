package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.dwr.AmbienteDWR;

public class AmbienteDWRTest 
{
	private AmbienteDWR ambienteDWR;
	private AmbienteManager ambienteManager;
	private HistoricoAmbienteManager historicoAmbienteManager;
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;

	@Before
	public void setUp() throws Exception
	{
		ambienteDWR = new AmbienteDWR();

		ambienteManager = mock(AmbienteManager.class);
		historicoAmbienteManager = mock(HistoricoAmbienteManager.class);
		tabelaReajusteColaboradorManager = mock(TabelaReajusteColaboradorManager.class);
		
		ambienteDWR.setAmbienteManager(ambienteManager);
		ambienteDWR.setHistoricoAmbienteManager(historicoAmbienteManager);
		ambienteDWR.setTabelaReajusteColaboradorManager(tabelaReajusteColaboradorManager);
	}
	
	@Test
	public void testGetAmbienteByEstabelecimentoAndAmbientesDeTerceiros()
	{
		Long empresaId = 2L;
		Long estabelecimentoId = 1L;
		String estabelecimentoNome = "";
		String data = "10/11/2017";
		
		Collection<Ambiente> collectionAmbiente = Arrays.asList(AmbienteFactory.getEntity("Ambiente 1"));
		
		LinkedHashMap<String, Collection<Ambiente>> ambientes = new LinkedHashMap<String, Collection<Ambiente>>();
		ambientes.put(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getDescricao(), collectionAmbiente);
		
		when(ambienteManager.montaMapAmbientes(empresaId, estabelecimentoId, estabelecimentoNome, DateUtil.criarDataDiaMesAno(data))).thenReturn(ambientes);
		
		assertEquals(1, ambienteDWR.getAmbienteByEstabelecimentoAndAmbientesDeTerceiros(empresaId, estabelecimentoId, estabelecimentoNome, data).size());
	}
	
	@Test
	public void testGetAmbientes()
	{
		Long empresaId = 1L;
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L, "Estabelecimento");
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(2L);
		tabelaReajusteColaborador.setData(new Date());
		
		when(tabelaReajusteColaboradorManager.findEntidadeComAtributosSimplesById(tabelaReajusteColaborador.getId())).thenReturn(tabelaReajusteColaborador);
		Collection<Ambiente> collectionAmbiente = Arrays.asList(AmbienteFactory.getEntity("Ambiente 1"));
		
		LinkedHashMap<String, Collection<Ambiente>> ambientes = new LinkedHashMap<String, Collection<Ambiente>>();
		ambientes.put(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getDescricao(), collectionAmbiente);
		
		when(ambienteManager.montaMapAmbientes(empresaId, estabelecimento.getId(), estabelecimento.getNome(), tabelaReajusteColaborador.getData())).thenReturn(ambientes);
		
		assertEquals(1, ambienteDWR.getAmbientes(empresaId, tabelaReajusteColaborador.getId(), estabelecimento.getId(), estabelecimento.getNome()).size());
	}
	
	@Test
	public void testGetAmbientesByEstabelecimentoOrAmbientesDeTerceiroComIgualALocalDeTerceiros()
	{
		Long empresaId = 1L;
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao(); 
		String data = "10/11/2017";
				
		when(ambienteManager.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimento.getId(), localAmbiente, DateUtil.criarDataDiaMesAno(data))).thenReturn(new ArrayList<Ambiente>()); 
		Map<Object, Object> ambientes = ambienteDWR.getAmbientesByEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimento.getId(), localAmbiente, data);
		assertEquals(1, ambientes.size());
	}
	
	@Test
	public void testGetAmbientesByEstabelecimentoOrAmbientesDeTerceiroComIgualALocalDoProprioEmpregador()
	{
		Long empresaId = 1L;
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao(); 
		String data = "10/11/2017";
				
		when(ambienteManager.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimento.getId(), localAmbiente, DateUtil.criarDataDiaMesAno(data))).thenReturn(new ArrayList<Ambiente>()); 
		Map<Object, Object> ambientes = ambienteDWR.getAmbientesByEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimento.getId(), localAmbiente, data);
		verify(ambienteManager, times(1)).findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimento.getId(), localAmbiente, DateUtil.criarDataDiaMesAno(data));
		assertEquals(1, ambientes.size());
	}
	
	@Test
	public void testGetAmbientesByEstabelecimentoOrAmbientesDeTerceiroComIgualALocalDoProprioEmpregadorMasIdDoEstabelecimentoIgualAZero()
	{
		Long empresaId = 1L;
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(0L);
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao(); 
		String data = "10/11/2017";
		 
		Map<Object, Object> ambientes = ambienteDWR.getAmbientesByEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimento.getId(), localAmbiente, data);
		verify(ambienteManager, never()).findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimento.getId(), localAmbiente, DateUtil.criarDataDiaMesAno(data));
		assertEquals(1, ambientes.size());
	}
	
	@Test
	public void testGetAmbienteChecks()
	{
		Long empresaId = 1L;
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(0L);
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao(); 
		String data = "10/11/2017";
		
		when(ambienteManager.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimento.getId(), localAmbiente, DateUtil.criarDataDiaMesAno(data))).thenReturn(new ArrayList<Ambiente>());
		
		Map<Object, Object> ambientes = ambienteDWR.getAmbienteChecks(empresaId, estabelecimento.getId(), localAmbiente, data);
		verify(ambienteManager, times(1)).findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimento.getId(), localAmbiente, DateUtil.criarDataDiaMesAno(data));
		assertEquals(0, ambientes.size());
	}
	
	@Test
	public void testGetAmbientesByEstabelecimentos()
	{
		String[] estabelecimentosIds = new String[]{"1"};
		String data = "17/11/2017";
		
		when(ambienteManager.findAmbientesPorEstabelecimento(LongUtil.arrayStringToArrayLong(estabelecimentosIds), DateUtil.criarDataDiaMesAno(data))).thenReturn(new ArrayList<Ambiente>());
		
		Map<Object, Object> ambientes = ambienteDWR.getAmbientesByEstabelecimentos(estabelecimentosIds, data);
		verify(ambienteManager, times(1)).findAmbientesPorEstabelecimento(LongUtil.arrayStringToArrayLong(estabelecimentosIds), DateUtil.criarDataDiaMesAno(data));
		assertEquals(0, ambientes.size());
	}
	
	@Test
	public void existeHistoricoAmbienteByDataTrue() throws Exception{
		Long estabelecimentoId = 2L;
		Long ambienteId = 1L;
		String data = "17/11/2017";
		
		when(historicoAmbienteManager.existeHistoricoAmbienteByData(estabelecimentoId, ambienteId, DateUtil.criarDataDiaMesAno(data))).thenReturn(true);
		assertTrue(ambienteDWR.existeHistoricoAmbienteByData(estabelecimentoId, ambienteId, data));
	}
	
	@Test
	public void existeHistoricoAmbienteByDataFalse() throws Exception{
		Long estabelecimentoId = 2L;
		Long ambienteId = 1L;
		String data = "17/11/2017";
		
		when(historicoAmbienteManager.existeHistoricoAmbienteByData(estabelecimentoId, ambienteId, DateUtil.criarDataDiaMesAno(data))).thenReturn(false);
		assertFalse(ambienteDWR.existeHistoricoAmbienteByData(estabelecimentoId, ambienteId, data));
	}
}
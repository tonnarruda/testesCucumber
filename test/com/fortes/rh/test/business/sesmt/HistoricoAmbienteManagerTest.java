package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManagerImpl;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoAmbienteFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class HistoricoAmbienteManagerTest
{
	private HistoricoAmbienteManagerImpl historicoAmbienteManager = new HistoricoAmbienteManagerImpl();
	private HistoricoAmbienteDao historicoAmbienteDao = null;
	private RiscoAmbienteManager riscoAmbienteManager;
	private AmbienteManager ambienteManager;

	@Before
	public void setUp() throws Exception
    {
		ambienteManager = mock(AmbienteManager.class);
        historicoAmbienteDao = mock(HistoricoAmbienteDao.class);
        riscoAmbienteManager = mock(RiscoAmbienteManager.class);
        
        historicoAmbienteManager.setDao(historicoAmbienteDao);
        historicoAmbienteManager.setRiscoAmbienteManager(riscoAmbienteManager);
        
        PowerMockito.mockStatic(SpringUtil.class);
    }
	
	@Test
	public void testSaveInserindo() throws Exception
	{
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setDescricao("Histórico do ambiente");
		
		String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		
		Collection<RiscoAmbiente> riscosAmbientes = new ArrayList<RiscoAmbiente>();
		riscosAmbientes.add(null);
		
		when(SpringUtil.getBean("ambienteManager")).thenReturn(ambienteManager);
		when(historicoAmbienteDao.findByData(eq(historicoAmbiente.getData()), eq(historicoAmbiente.getId()), eq(ambiente.getId()))).thenReturn(null);
		
		Exception exception = null;
		
		try 
		{
			historicoAmbienteManager.saveOrUpdate(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
		} 
		catch (Exception e)  { 
			exception = e; 
		}	
		
		assertNull(exception);
	}
	
	@Test
	public void testSaveOrUpdateAtualizandoHistoricoComLocalDoAmbienteSendoEstabelecimentoDeTerceiro()
	{
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setId(1L);
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setDescricao("Histórico do ambiente");
		historicoAmbiente.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbiente.setEstabelecimento(null);
		
		String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		
		Collection<RiscoAmbiente> riscosAmbientes = RiscoAmbienteFactory.getCollection();
		
		when(SpringUtil.getBean("ambienteManager")).thenReturn(ambienteManager);
		when(historicoAmbienteDao.findByData(eq(historicoAmbiente.getData()), eq(historicoAmbiente.getId()), eq(ambiente.getId()))).thenReturn(null);
		when(riscoAmbienteManager.removeByHistoricoAmbiente(eq(historicoAmbiente.getId()))).thenReturn(true);
		
		Exception exception = null;
		
		try 
		{
			historicoAmbienteManager.saveOrUpdate(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
		} 
		catch (Exception e) { 
			exception = e; 
		}
		
		assertNull(exception);
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testSaveOrUpdateInserindoUmNovoHistoricoNaMesmaDataDeOutroJaCadastrado() throws FortesException, Exception
	{
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setId(1L);
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setDescricao("Histórico do ambiente");
		
		String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		
		Collection<RiscoAmbiente> riscosAmbientes = RiscoAmbienteFactory.getCollection();
		
		when(historicoAmbienteDao.findByData(eq(historicoAmbiente.getData()), eq(historicoAmbiente.getId()), eq(ambiente.getId()))).thenReturn(historicoAmbiente);
		
		thrown.expect(FortesException.class);
	    thrown.expectMessage("Já existe um histórico para a data informada");

	    historicoAmbienteManager.saveOrUpdate(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
	}
	
	@Test
	public void testSaveOrUpdateInserindoUmHistoricoComLocalDoAmbienteSendoEstabelecimentoDoProprioEmpregador()
	{
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		
		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setDescricao("Histórico do ambiente");
		historicoAmbiente.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbiente.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
		
		String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		
		RiscoAmbiente riscoAmbiente1 = RiscoAmbienteFactory.getEntity(RiscoFactory.getEntity(823L), historicoAmbiente, null);
		RiscoAmbiente riscoAmbiente2 = RiscoAmbienteFactory.getEntity(RiscoFactory.getEntity(822L), historicoAmbiente, null);

		Collection<RiscoAmbiente> riscosAmbientes = Arrays.asList(riscoAmbiente1, riscoAmbiente2);
		
		when(SpringUtil.getBean("ambienteManager")).thenReturn(ambienteManager);
		when(historicoAmbienteDao.findByData(eq(historicoAmbiente.getData()), eq(historicoAmbiente.getId()), eq(ambiente.getId()))).thenReturn(null);
		when(riscoAmbienteManager.removeByHistoricoAmbiente(eq(historicoAmbiente.getId()))).thenReturn(true);
		
		Exception exception = null;
		
		try 
		{
			historicoAmbienteManager.saveOrUpdate(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
		} 
		catch (Exception e) { 
			exception = e; 
		}
		
		assertNull(exception);
	}
	
	@Test
	public void testFindById()
	{
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setId(1L);
		Collection<RiscoAmbiente> riscoAmbientes = new ArrayList<RiscoAmbiente>();
		RiscoAmbiente riscoAmbiente = new RiscoAmbiente();
		riscoAmbiente.setId(1L);
		riscoAmbiente.setId(2L);
		
		riscoAmbientes.add(riscoAmbiente);
		
		historicoAmbiente.setRiscoAmbientes(riscoAmbientes);
		
		when(historicoAmbienteDao.findById(historicoAmbiente.getId())).thenReturn(historicoAmbiente);
		
		historicoAmbiente = historicoAmbienteManager.findById(1L);
		
		assertEquals(1, historicoAmbiente.getRiscoAmbientes().size());
	}
	
	@Test
	public void testRemoveByAmbiente()
	{
		when(historicoAmbienteDao.removeByAmbiente(anyLong())).thenReturn(true);
		assertTrue(historicoAmbienteManager.removeByAmbiente(1L));
	}
	
	@Test
	public void testRemoveCascade()
	{
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(1L);
		historicoAmbiente.setAmbiente(ambiente);
		
		when(SpringUtil.getBean("ambienteManager")).thenReturn(ambienteManager);
		when(historicoAmbienteDao.findById(eq(historicoAmbiente.getId()))).thenReturn(historicoAmbiente);
		historicoAmbienteManager.removeCascade(historicoAmbiente.getId());
	}
	
	@Test
	public void testFindByAmbiente()
	{
		Long ambienteId = 3L;
		when(historicoAmbienteDao.findByAmbiente(eq(ambienteId))).thenReturn(new ArrayList<HistoricoAmbiente>());
		
		assertNotNull(historicoAmbienteManager.findByAmbiente(ambienteId));
	}
	
	@Test
	public void testFindUltimoHistorico()
	{
		Date hoje = new Date();
		
		Long ambienteId = 3L;
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(hoje);
		historicoAmbiente.setId(5L);
		
		when(historicoAmbienteDao.findUltimoHistorico(eq(ambienteId))).thenReturn(historicoAmbiente);
		
		assertEquals(historicoAmbiente, historicoAmbienteManager.findUltimoHistorico(ambienteId));
	}
	
	@Test
	public void testFindDadosNoPeriodo() 
	{
		Long ambienteId = 2L;
		Date dataIni = new Date();
		Date dataFim = DateUtil.incrementaDias(dataIni, 10);
		
		when(historicoAmbienteDao.findDadosNoPeriodo(eq(ambienteId), eq(dataIni), eq(dataFim))).thenReturn(new ArrayList<DadosAmbienteOuFuncaoRisco>());
		assertEquals(0, historicoAmbienteManager.findDadosNoPeriodo(ambienteId, dataIni, dataFim).size());
		verify(historicoAmbienteDao, times(1)).findDadosNoPeriodo(eq(ambienteId), eq(dataIni), eq(dataFim));
	}

	@Test
	public void testFindUltimoHistoricoAteData()
	{
		Long ambienteId = 2L;
		Date dataMaxima = new Date();
		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(1L);
		
		when(historicoAmbienteDao.findUltimoHistoricoAteData(ambienteId, dataMaxima)).thenReturn(historicoAmbiente);
		assertEquals(historicoAmbiente, historicoAmbienteManager.findUltimoHistoricoAteData(ambienteId, dataMaxima));
		verify(historicoAmbienteDao).findUltimoHistoricoAteData(ambienteId, dataMaxima);
	}

	@Test
	public void testFindRiscosAmbientes() 
	{
		Collection<Long> ambienteIds = new ArrayList<Long>(); 
		Date data = new Date();
		
		when(historicoAmbienteDao.findRiscosAmbientes(ambienteIds, data)).thenReturn(new ArrayList<HistoricoAmbiente>());
		assertEquals(0, historicoAmbienteManager.findRiscosAmbientes(ambienteIds, data).size());
		verify(historicoAmbienteDao, times(1)).findRiscosAmbientes(ambienteIds, data);
	}

	@Test
	public void testExisteHistoricoAmbienteByDataRetornoTrue() {
		Long estabelecimentoId = 2L; 
		Long ambienteId = 1L;
		Date data = new Date();
		when(historicoAmbienteDao.existeHistoricoAmbienteByData(eq(estabelecimentoId), eq(ambienteId), eq(data))).thenReturn(true);
		assertTrue(historicoAmbienteManager.existeHistoricoAmbienteByData(estabelecimentoId, ambienteId, data));
	}

	@Test
	public void testExisteHistoricoAmbienteByDataRetornoFalse() {
		Long estabelecimentoId = 2L; 
		Long ambienteId = 1L;
		Date data = new Date();
		when(historicoAmbienteDao.existeHistoricoAmbienteByData(eq(estabelecimentoId), eq(ambienteId), eq(data))).thenReturn(false);
		assertFalse(historicoAmbienteManager.existeHistoricoAmbienteByData(estabelecimentoId, ambienteId, data));
	}
	
	@Test
	public void testDeleteByEstabelecimentos() throws Exception {
		Long[] estabelecimentoIds = new Long[]{1L};
		
		doNothing().when(historicoAmbienteDao).deleteByEstabelecimentos(estabelecimentoIds);
		historicoAmbienteManager.deleteByEstabelecimentos(estabelecimentoIds);
		verify(historicoAmbienteDao, times(1)).deleteByEstabelecimentos(estabelecimentoIds);
	}
}
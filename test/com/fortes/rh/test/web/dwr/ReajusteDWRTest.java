package com.fortes.rh.test.web.dwr;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.dwr.ReajusteDWR;
import com.fortes.web.tags.Option;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class ReajusteDWRTest{
	private ReajusteDWR reajusteDWR;
	private ReajusteColaboradorManager reajusteColaboradorManager;
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	private ColaboradorManager colaboradorManager;
	private IndiceManager indiceManager;
	private FaixaSalarialManager faixaSalarialManager;
	
	@Before
	public void setUp() throws Exception
	{
		reajusteDWR = new ReajusteDWR();
		
		reajusteColaboradorManager=mock(ReajusteColaboradorManager.class);
		tabelaReajusteColaboradorManager=mock(TabelaReajusteColaboradorManager.class);
		colaboradorManager=mock(ColaboradorManager.class);
		indiceManager=mock(IndiceManager.class);
		faixaSalarialManager=mock(FaixaSalarialManager.class);
		
		reajusteDWR.setReajusteColaboradorManager(reajusteColaboradorManager);
		reajusteDWR.setTabelaReajusteColaboradorManager(tabelaReajusteColaboradorManager);
		reajusteDWR.setColaboradorManager(colaboradorManager);
		reajusteDWR.setIndiceManager(indiceManager);
		reajusteDWR.setFaixaSalarialManager(faixaSalarialManager);
		
		PowerMockito.mockStatic(SpringUtil.class);
		
	}

	@Test(expected=Exception.class)
	public void testVerificaColaboradorValidaTabela() throws Exception
	{
		reajusteDWR.verificaColaborador(-1L, null, true);
	}
	@Test
	public void testCalculaSalarioHistoricoPorValor() throws Exception
	{
		assertEquals("200,00", reajusteDWR.calculaSalarioHistorico("3", "1", "", "", "200,00", "18/03/2010"));
	}
	@Test(expected=Exception.class)
	public void testVerificaColaboradorValidaColaborador() throws Exception
	{
		reajusteDWR.verificaColaborador(2L, -1L, true);
	}
	@Test(expected=Exception.class)
	public void testVerificaColaboradorValidaColaboradorCodigoACNull() throws Exception
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		when(tabelaReajusteColaboradorManager.findByIdProjection(tabelaReajusteColaborador.getId())).thenReturn(tabelaReajusteColaborador);
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		
		reajusteDWR.verificaColaborador(tabelaReajusteColaborador.getId(), colaborador.getId(), true);
	}
	@Test(expected=Exception.class)
	public void testVerificaColaboradorValidaColaboradorExistenteNaTabela() throws Exception
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		when(tabelaReajusteColaboradorManager.findByIdProjection(tabelaReajusteColaborador.getId())).thenReturn(tabelaReajusteColaborador);
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		
		reajusteDWR.verificaColaborador(tabelaReajusteColaborador.getId(), colaborador.getId(), false);
	}
	@Test
	public void testFindRealinhamentosByPeriodo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		String dataIni = "1/1/2016";
		String dataFim = "31/1/2016";
		
		TabelaReajusteColaborador tabelaReajusteColaborador1 = TabelaReajusteColaboradorFactory.getEntity(1L);
		TabelaReajusteColaborador tabelaReajusteColaborador2 = TabelaReajusteColaboradorFactory.getEntity(2L);
		
		Collection<TabelaReajusteColaborador> tabelaReajusteColaboradores = new ArrayList<TabelaReajusteColaborador>();
		tabelaReajusteColaboradores.add(tabelaReajusteColaborador1);
		tabelaReajusteColaboradores.add(tabelaReajusteColaborador2);
				
		when(tabelaReajusteColaboradorManager.findAllSelect(eq(empresa.getId()), any(Date.class), any(Date.class))).thenReturn(tabelaReajusteColaboradores);
		
		Collection<Option> retorno = reajusteDWR.findRealinhamentosByPeriodo(empresa.getId(), dataIni, dataFim);
		
		assertEquals(2, retorno.size());
	}
	@Test
	public void testCalculaSalarioHistoricoPorIndiceSemDataDeveConsiderarDataAtual() throws Exception
	{
		String tipoSalarioIndice = "2";
		
		Indice indice = IndiceFactory.getEntity(1l);
		
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1l);
		indiceHistorico.setValor(400.00);
		
		indice.setIndiceHistoricoAtual(indiceHistorico);
		
		when(indiceManager.findHistorico(eq(indice.getId()), any(Date.class))).thenReturn(indice);
		
		assertEquals("1.600,00", reajusteDWR.calculaSalarioHistorico(tipoSalarioIndice, "1", indice.getId().toString(), "4","", ""));
	}
	@Test
	public void testCalculaSalarioHistoricoPorCargoValor() throws Exception
	{
		String tipoSalarioCargo = "1";
		String faixaSalarialId = "1";
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1l);
		
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1l);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setValor(100.00);
		
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);
		
		when(faixaSalarialManager.findHistorico(eq(Long.parseLong(faixaSalarialId)), any(Date.class))).thenReturn(faixaSalarial);
		assertEquals("100,00", reajusteDWR.calculaSalarioHistorico(tipoSalarioCargo, faixaSalarialId, "", "", "", ""));
	}
	@Test
	public void testCalculaSalarioHistoricoPorCargoIndice() throws Exception
	{
		String tipoSalarioCargo = "1";
		String faixaSalarialId = "1";
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1l);
		Indice indice = IndiceFactory.getEntity(1l);
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1l);
		
		indice.setIndiceHistoricoAtual(indiceHistorico);
		
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1l);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico.setValor(100.00);
		faixaSalarialHistorico.setIndice(indice);
		
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);
		
		when(faixaSalarialManager.findHistorico(eq(Long.parseLong(faixaSalarialId)), any(Date.class))).thenReturn(faixaSalarial);
		assertEquals("0,00", reajusteDWR.calculaSalarioHistorico(tipoSalarioCargo, faixaSalarialId, "", "", "", ""));
	}
}

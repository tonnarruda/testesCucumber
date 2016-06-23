package com.fortes.rh.test.web.dwr;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.sesmt.OrdemDeServicoManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.OrdemDeServicoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.dwr.OrdemDeServicoDWR;

public class OrdemDeServicoDWRTest
{
	private OrdemDeServicoDWR ordemDeServicoDWR;
	private OrdemDeServicoManager ordemDeServicoManager;
	private EmpresaManager empresaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	

	@Before
	public void setUp() throws Exception {
		ordemDeServicoDWR = new OrdemDeServicoDWR();
		
		ordemDeServicoManager = mock(OrdemDeServicoManager.class);
		ordemDeServicoDWR.setOrdemDeServicoManager(ordemDeServicoManager);
		
		empresaManager = mock(EmpresaManager.class);
		ordemDeServicoDWR.setEmpresaManager(empresaManager);
		
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
		ordemDeServicoDWR.setHistoricoColaboradorManager(historicoColaboradorManager);
	}
	
	@Test
	public void recarregaDadosOrdemDeServicoInseriPrimeiraOrdemDeServiço() throws Exception{
		Colaborador colaborador = ColaboradorFactory.getEntity(false, DateUtil.criarDataMesAno(1, 1, 2016), null);
		Funcao funcao = FuncaoFactory.getEntity(5L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L, colaborador, funcao, DateUtil.criarDataMesAno(1, 1, 2016));
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L, DateUtil.criarDataMesAno(1, 02, 2016), false);
		Empresa empresa = new Empresa();

		Long colaboradorId = 1L;
		Long empresaId = 1L;
		
		when(historicoColaboradorManager.findHistoricoColaboradorByData(eq(colaboradorId), any(Date.class))).thenReturn(historicoColaborador);
		when(ordemDeServicoManager.findUltimaOrdemDeServico(colaboradorId)).thenReturn(null);
		when(empresaManager.findByIdProjection(empresaId)).thenReturn(empresa); 
		
		when(ordemDeServicoManager.montaOrdemDeServico(eq(colaboradorId), eq(empresa), any(Date.class))).thenReturn(ordemDeServico);
		Exception exception = null;
		try {
			ordemDeServicoDWR.recarregaDadosOrdemDeServico(colaboradorId, empresaId, "01/02/2016");
		} catch (Exception e) {
				exception = e;
		}
		assertNull("Existem dados para carrega a Ordem de Serviço",exception);
	}
	
	@Test
	public void recarregaDadosOrdemDeServicoComDataAnteriorAAdmissao() throws Exception{
		Colaborador colaborador = ColaboradorFactory.getEntity(false, DateUtil.criarDataMesAno(1, 1, 2016), null);
		Funcao funcao = FuncaoFactory.getEntity(5L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L, colaborador, funcao, DateUtil.criarDataMesAno(1, 1, 2016));

		Long colaboradorId = 1L;
		Long empresaId = 1L;
		
		when(historicoColaboradorManager.findHistoricoColaboradorByData(eq(colaboradorId), any(Date.class))).thenReturn(historicoColaborador);
		Exception exception = null;
		try {
			ordemDeServicoDWR.recarregaDadosOrdemDeServico(colaboradorId, empresaId, "01/02/2015");
		} catch (Exception e) {
				exception = e;
		}
		assertEquals("Não é possível inserir uma ordem de serviço com data anterior a data de Admissão do colaborador.", exception.getMessage());
	}
	
	@Test
	public void recarregaDadosOrdemDeServicoComDataPosteriorAoDesligamento() throws Exception{
		Colaborador colaborador = ColaboradorFactory.getEntity(true, DateUtil.criarDataMesAno(1, 1, 2016), DateUtil.criarDataMesAno(1, 5, 2016));
		Funcao funcao = FuncaoFactory.getEntity(5L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L, colaborador, funcao, DateUtil.criarDataMesAno(1, 1, 2016));

		Long colaboradorId = 1L;
		Long empresaId = 1L;
		
		when(historicoColaboradorManager.findHistoricoColaboradorByData(eq(colaboradorId), any(Date.class))).thenReturn(historicoColaborador);
		Exception exception = null;
		try {
			ordemDeServicoDWR.recarregaDadosOrdemDeServico(colaboradorId, empresaId, "01/06/2016");
		} catch (Exception e) {
				exception = e;
		}
		assertEquals("Não é possível inserir uma ordem de serviço com data posterior a data de desligamento do colaborador.", exception.getMessage());
	}
	
	@Test
	public void recarregaDadosOrdemDeServicoSemFuncaoNoHistoricoDoColaborador() throws Exception{
		Colaborador colaborador = ColaboradorFactory.getEntity(true, DateUtil.criarDataMesAno(1, 1, 2016), DateUtil.criarDataMesAno(1, 5, 2016));
		Funcao funcao = null;
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L, colaborador, funcao, DateUtil.criarDataMesAno(1, 1, 2016));

		Long colaboradorId = 1L;
		Long empresaId = 1L;
		
		when(historicoColaboradorManager.findHistoricoColaboradorByData(eq(colaboradorId), any(Date.class))).thenReturn(historicoColaborador);
		Exception exception = null;
		try {
			ordemDeServicoDWR.recarregaDadosOrdemDeServico(colaboradorId, empresaId, "01/06/2016");
		} catch (Exception e) {
				exception = e;
		}
		String mensagemErro = "Não é possível inserir uma ordem de serviço para a data: "
				+ "<b>" + DateUtil.formataDate(DateUtil.criarDataDiaMesAno("01/06/2016"), "dd/MM/yyyy") +""
				+ "</b>, pois o histórico do colaborador não possui função.";
		assertEquals(mensagemErro, exception.getMessage());
	}
	
	@Test
	public void recarregaDadosOrdemDeServicoComDataAnteriorADataDaUltimaOrdemDeServicoImpressa() throws Exception{
		Colaborador colaborador = ColaboradorFactory.getEntity(false, DateUtil.criarDataMesAno(1, 1, 2015), null);
		Funcao funcao = FuncaoFactory.getEntity(5L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L, colaborador, funcao, DateUtil.criarDataMesAno(1, 1, 2015));
		OrdemDeServico ordemDeServicoImpressa = OrdemDeServicoFactory.getEntity(1L, DateUtil.criarDataMesAno(1, 01, 2016), true);
		
		Long colaboradorId = 1L;
		Long empresaId = 1L;
		
		when(historicoColaboradorManager.findHistoricoColaboradorByData(eq(colaboradorId), any(Date.class))).thenReturn(historicoColaborador);
		when(ordemDeServicoManager.findUltimaOrdemDeServico(colaboradorId)).thenReturn(ordemDeServicoImpressa);
		Exception exception = null;
		try {
			ordemDeServicoDWR.recarregaDadosOrdemDeServico(colaboradorId, empresaId, "01/01/2015");
		} catch (Exception e) {
				exception = e;
		}
		String mensagemErro = "Não é possível inserir uma ordem de serviço."
				+ "</br></br>A data informada está inferior a data da última Ordem de Serviço impressa."
				+ "</br></br>Data da última ordem de serviço: " +  ordemDeServicoImpressa.getDataFormatada();
		
		assertEquals(mensagemErro, exception.getMessage());
	}
}
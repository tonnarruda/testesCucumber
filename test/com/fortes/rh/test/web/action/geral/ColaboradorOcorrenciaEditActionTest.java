package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.geral.ProvidenciaManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.geral.ColaboradorOcorrenciaEditAction;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorOcorrenciaEditActionTest 
{
	private ColaboradorOcorrenciaEditAction action = new ColaboradorOcorrenciaEditAction();
	private HistoricoColaboradorManager historicoColaboradorManager;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private OcorrenciaManager ocorrenciaManager;
	private ProvidenciaManager providenciaManager;
	private ColaboradorManager colaboradorManager;

	@Before
	public void setUp () throws Exception
	{
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
		colaboradorOcorrenciaManager = mock (ColaboradorOcorrenciaManager.class);
		ocorrenciaManager = mock(OcorrenciaManager.class);
		providenciaManager = mock(ProvidenciaManager.class);
		colaboradorManager = mock(ColaboradorManager.class);
		
		action.setHistoricoColaboradorManager(historicoColaboradorManager);
        action.setColaboradorOcorrenciaManager(colaboradorOcorrenciaManager);
        action.setOcorrenciaManager(ocorrenciaManager);
        action.setProvidenciaManager(providenciaManager);
        action.setColaboradorManager(colaboradorManager);
        
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}
	
	@Test
	public void testinsertOrUpdate() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		action.setEmpresaSistema(empresa);
		
		Colaborador colab = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colab);
		
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity(1L);
		
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity(colab, ocorrencia, DateUtil.criarDataMesAno(1, 1, 2016), DateUtil.criarDataMesAno(1, 1, 2016));
		colaboradorOcorrencia.setProvidencia(new Providencia());
		action.setColaboradorOcorrencia(colaboradorOcorrencia);
		
		HistoricoColaborador primeiroHistorico = HistoricoColaboradorFactory.getEntity(colab, DateUtil.criarDataMesAno(1, 2, 2015), StatusRetornoAC.CONFIRMADO);
		
		Collection<ColaboradorOcorrencia> ocorrenciasNaMesmaData = new ArrayList<ColaboradorOcorrencia>();

		when(historicoColaboradorManager.getPrimeiroHistorico(colab.getId())).thenReturn(primeiroHistorico);
		when(colaboradorManager.findColaboradorByIdProjection(colab.getId())).thenReturn(colab);
		when(colaboradorOcorrenciaManager.getCount(new String[]{"colaborador.id"}, new Object[]{colab.getId()})).thenReturn(2);
		when(colaboradorOcorrenciaManager.verifyOcorrenciasMesmaData(colaboradorOcorrencia.getId(), colaboradorOcorrencia.getColaborador().getId(), colaboradorOcorrencia.getOcorrencia().getId(), action.getEmpresaSistema().getId(), colaboradorOcorrencia.getDataIni(), colaboradorOcorrencia.getDataFim())).thenReturn(ocorrenciasNaMesmaData);
		when(ocorrenciaManager.findById(colaboradorOcorrencia.getOcorrencia().getId())).thenReturn(ocorrencia);
		
		assertEquals(Action.SUCCESS, action.insertOrUpdate());
	}
	
	@Test
	public void testinsertOrUpdateComOcorrenciaNaMesmadata() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		action.setEmpresaSistema(empresa);
		
		Colaborador colab = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colab);
		
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity(colab, OcorrenciaFactory.getEntity(1L), DateUtil.criarDataMesAno(1, 1, 2016), DateUtil.criarDataMesAno(1, 1, 2016));
		colaboradorOcorrencia.setProvidencia(new Providencia());
		action.setColaboradorOcorrencia(colaboradorOcorrencia);
		
		HistoricoColaborador primeiroHistorico = HistoricoColaboradorFactory.getEntity(colab, DateUtil.criarDataMesAno(1, 2, 2015), StatusRetornoAC.CONFIRMADO);
		
		Collection<ColaboradorOcorrencia> ocorrenciasNaMesmaData = new ArrayList<ColaboradorOcorrencia>();
		ocorrenciasNaMesmaData.add(colaboradorOcorrencia);

		when(historicoColaboradorManager.getPrimeiroHistorico(colab.getId())).thenReturn(primeiroHistorico);
		when(colaboradorOcorrenciaManager.verifyOcorrenciasMesmaData(colaboradorOcorrencia.getId(), colaboradorOcorrencia.getColaborador().getId(), colaboradorOcorrencia.getOcorrencia().getId(), action.getEmpresaSistema().getId(), colaboradorOcorrencia.getDataIni(), colaboradorOcorrencia.getDataFim())).thenReturn(ocorrenciasNaMesmaData);
		
		assertEquals(Action.INPUT, action.insertOrUpdate());
		assertEquals("A ocorrência não pôde ser gravada com as datas informadas. <br/>A mesma ocorrência já foi cadastrada para esse colaborador nas seguintes datas: <br/> - 01/01/2016 a 01/01/2016<br/>", (String) action.getActionWarnings().toArray()[0]);
	}
	
	@Test
	public void testinsertOrUpdateInferirorADataDeAdmissao() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		action.setEmpresaSistema(empresa);
		
		Colaborador colab = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colab);
		
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity(colab, OcorrenciaFactory.getEntity(1L), DateUtil.criarDataMesAno(1, 1, 2016), DateUtil.criarDataMesAno(1, 1, 2016));
		action.setColaboradorOcorrencia(colaboradorOcorrencia);
		
		HistoricoColaborador primeiroHistorico = HistoricoColaboradorFactory.getEntity(colab, DateUtil.criarDataMesAno(1, 2, 2016), StatusRetornoAC.CONFIRMADO);
		when(historicoColaboradorManager.getPrimeiroHistorico(colab.getId())).thenReturn(primeiroHistorico);
		
		assertEquals(Action.INPUT, action.insertOrUpdate());
		assertEquals("Não é permitido inserir ocorrência antes da data da primeira situação do colaborador: (01/02/2016)", (String) action.getActionWarnings().toArray()[0]);
	}
}
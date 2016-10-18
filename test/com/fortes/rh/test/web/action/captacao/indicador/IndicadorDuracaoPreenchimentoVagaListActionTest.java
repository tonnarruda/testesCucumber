package com.fortes.rh.test.web.action.captacao.indicador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.DuracaoPreenchimentoVagaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.action.captacao.indicador.IndicadorDuracaoPreenchimentoVagaListAction;

public class IndicadorDuracaoPreenchimentoVagaListActionTest {
	
	private IndicadorDuracaoPreenchimentoVagaListAction action = new IndicadorDuracaoPreenchimentoVagaListAction();
	private DuracaoPreenchimentoVagaManager duracaoPreenchimentoVagaManager;
	private HistoricoCandidatoManager historicoCandidatoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private SolicitacaoManager solicitacaoManager;
	private ColaboradorManager colaboradorManager;
	private CandidatoManager candidatoManager;
	
	@Before
	public void setUp() throws Exception
    {
		duracaoPreenchimentoVagaManager = mock(DuracaoPreenchimentoVagaManager.class);
		historicoCandidatoManager = mock(HistoricoCandidatoManager.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		solicitacaoManager = mock(SolicitacaoManager.class);
		colaboradorManager = mock(ColaboradorManager.class);
		candidatoManager = mock(CandidatoManager.class);
		
		action.setDuracaoPreenchimentoVagaManager(duracaoPreenchimentoVagaManager);
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		action.setHistoricoCandidatoManager(historicoCandidatoManager);
		action.setEstabelecimentoManager(estabelecimentoManager);
		action.setSolicitacaoManager(solicitacaoManager);
		action.setColaboradorManager(colaboradorManager);
		action.setCandidatoManager(candidatoManager);
    }
	
	@Before
	public void setEmpresaSistema(){
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		action.setEmpresaSistema(empresa);
	}
	
	@Test
	public void testPainel()
	{
		Exception exception = null;
		try {
			assertEquals("success",action.painel());
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
}

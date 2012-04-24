package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.CandidatoEleicaoManager;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.business.sesmt.EtapaProcessoEleitoralManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.EleicaoEditAction;

public class EleicaoEditActionTest extends MockObjectTestCase
{
	private EleicaoEditAction action;
	private Mock manager;
	private Mock estabelecimentoManager;
	private Mock etapaProcessoEleitoralManager;
	private Mock colaboradorManager;
	private Mock candidatoEleicaoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(EleicaoManager.class);
        action = new EleicaoEditAction();
        action.setEleicaoManager((EleicaoManager) manager.proxy());
        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        etapaProcessoEleitoralManager = new Mock(EtapaProcessoEleitoralManager.class);
        action.setEtapaProcessoEleitoralManager((EtapaProcessoEleitoralManager) etapaProcessoEleitoralManager.proxy());
        colaboradorManager = new Mock(ColaboradorManager.class);
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        candidatoEleicaoManager = new Mock(CandidatoEleicaoManager.class);
        action.setCandidatoEleicaoManager((CandidatoEleicaoManager) candidatoEleicaoManager.proxy());

        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        action.setEleicao(new Eleicao());

    }


    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testPrepareInsert() throws Exception
    {
    	estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
    	assertEquals("success",action.prepareInsert());
    }

    public void testPrepareUpdate() throws Exception
    {

    	action.setEleicao(EleicaoFactory.getEntity(1L));
    	manager.expects(once()).method("findByIdProjection");
    	estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
    	assertEquals("success",action.prepareUpdate());
    }

    public void testInsert() throws Exception
    {
    	manager.expects(once()).method("save");
		etapaProcessoEleitoralManager.expects(once()).method("clonarEtapas");
		assertEquals("success", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	action.setEleicao(EleicaoFactory.getEntity(1L));
    	manager.expects(once()).method("update");
    	manager.expects(once()).method("findByIdProjection");
    	estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
    	assertEquals("success",action.update());
    }

    public void testImprimirResultado() throws Exception
    {
    	Eleicao eleicao = EleicaoFactory.getEntity(1L);
    	manager.expects(once()).method("findImprimirResultado").will(returnValue(eleicao));
    	manager.expects(once()).method("setCandidatosGrafico").will(returnValue(new ArrayList<CandidatoEleicao>()));
    	assertEquals("success",action.imprimirResultado());
    }

    public void testImprimirResultadoException() throws Exception
    {
    	manager.expects(once()).method("findImprimirResultado").will(throwException(new ColecaoVaziaException("Nenhum dado para o filtro")));
    	assertEquals("input",action.imprimirResultado());
    }
    
    public void testImprimirAtaEleicaoApuracao() throws Exception
    {
    	action.setImprimaRelatorio("ataEleicao");
    	Eleicao eleicao = EleicaoFactory.getEntity(1L);
    	manager.expects(once()).method("update");
    	manager.expects(once()).method("montaAtaDaEleicao").will(returnValue(eleicao));
    	assertEquals("imprimirAtaEleicao",action.updateImprimir());
    }

    public void testPrepareComunicados() throws Exception
	{
    	estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
    	assertEquals("success",action.prepareComunicados());
	}

	public void testUpdateImprimirLocalIncricao() throws Exception
	{
		action.setImprimaRelatorio("localInscricao");
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		eleicao.setInscricaoCandidatoIni(new Date());
		eleicao.setInscricaoCandidatoFim(new Date());
		manager.expects(once()).method("update");

		assertEquals("imprimirLocalIncricao",action.updateImprimir());
	}

	public void testUpdateImprimirSindicato() throws Exception
	{
		action.setImprimaRelatorio("chamadoEleicao");
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		eleicao.setInscricaoCandidatoIni(new Date());
		eleicao.setInscricaoCandidatoFim(new Date());
		eleicao.setTextoSindicato("");
		action.setEleicao(eleicao);
		manager.expects(once()).method("update");

		assertEquals("imprimirSindicado",action.updateImprimir());
	}

	public void testUpdateImprimirLocalVotacao() throws Exception
	{
		Eleicao eleicao = EleicaoFactory.getEntity(1L);
		action.setEleicao(eleicao);
		action.setImprimaRelatorio("localVotacao");
		Collection<CandidatoEleicao> colecao = new ArrayList<CandidatoEleicao>();
		colecao.add(CandidatoEleicaoFactory.getEntity());

		manager.expects(once()).method("update");
		candidatoEleicaoManager.expects(once()).method("findByEleicao").will(returnValue(colecao));

		assertEquals("imprimirLocalVotacao",action.updateImprimir());
	}

	public void testUpdateImprimirLocalVotacaoInput() throws Exception
	{
		action.setImprimaRelatorio("localVotacao");
		manager.expects(once()).method("update");
		candidatoEleicaoManager.expects(once()).method("findByEleicao").will(returnValue(new ArrayList<CandidatoEleicao>()));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));

		assertEquals("imprimirLocalVotacao",action.updateImprimir());
	}

    public void testGetSet() throws Exception
    {
    	assertNotNull(action.getEleicao());

    	action.setEleicao(null);
    	assertTrue(action.getEleicao() instanceof Eleicao);

    	action.getParametros();
    	action.getDataSource();
    	action.getEstabelecimentos();
    	action.getImprimaRelatorio();
    }
}
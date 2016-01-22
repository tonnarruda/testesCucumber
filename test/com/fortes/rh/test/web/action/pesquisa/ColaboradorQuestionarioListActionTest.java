package com.fortes.rh.test.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.ColaboradorQuestionarioListAction;
import com.fortes.web.tags.CheckBox;

public class ColaboradorQuestionarioListActionTest extends MockObjectTestCase
{
	private ColaboradorQuestionarioListAction colaboradorQuestionarioAction;
	private Mock colaboradorQuestionarioManager;
	private Mock colaboradorManager;
	private Mock colaboradorRespostaManager;
	private Mock questionarioManager;
	private Mock empresaManager;
	private Mock parametrosDoSistemaManager;
	private Mock estabelecimentoManager;

    protected void setUp() throws Exception
    {
        super.setUp();

        colaboradorQuestionarioAction = new ColaboradorQuestionarioListAction();

        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        colaboradorQuestionarioAction.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

        colaboradorManager = new Mock(ColaboradorManager.class);
        colaboradorQuestionarioAction.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

        colaboradorRespostaManager = new Mock(ColaboradorRespostaManager.class);
        colaboradorQuestionarioAction.setColaboradorRespostaManager((ColaboradorRespostaManager) colaboradorRespostaManager.proxy());

        questionarioManager = new Mock(QuestionarioManager.class);
        colaboradorQuestionarioAction.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());

        empresaManager = new Mock(EmpresaManager.class);
        colaboradorQuestionarioAction.setEmpresaManager((EmpresaManager) empresaManager.proxy());

        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        colaboradorQuestionarioAction.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        
        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        colaboradorQuestionarioAction.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
    	colaboradorQuestionarioAction = null;
        colaboradorQuestionarioManager = null;

        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals("success", colaboradorQuestionarioAction.execute());
    }

    public void testList() throws Exception
    {
    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = ColaboradorQuestionarioFactory.getCollection();

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	colaboradorQuestionarioAction.setQuestionario(questionario);

    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	colaboradorQuestionarioManager.expects(once()).method("findByQuestionarioEmpresaRespondida").with(eq(questionario.getId()), ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorQuestionarios));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	estabelecimentoManager.expects(once()).method("populaCheckBox").withAnyArguments().will(returnValue(new ArrayList<CheckBox>()));

    	assertEquals("success", colaboradorQuestionarioAction.list());
    	assertEquals(colaboradorQuestionarios, colaboradorQuestionarioAction.getColaboradorQuestionarios());
    	assertEquals(questionario, colaboradorQuestionarioAction.getQuestionario());

    }

    public void testDelete() throws Exception
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionarioAction.setColaboradorQuestionario(colaboradorQuestionario);
    	colaboradorQuestionario.setAvaliacao(new Avaliacao());

    	colaboradorQuestionarioManager.expects(once()).method("findById").with(eq(colaboradorQuestionario.getId())).will(returnValue(colaboradorQuestionario));
    	colaboradorRespostaManager.expects(once()).method("removeFicha").with(eq(colaboradorQuestionario.getId()));

    	assertEquals("success", colaboradorQuestionarioAction.delete());
    }
    
    public void testDeleteSemAvaliacao() throws Exception
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionarioAction.setColaboradorQuestionario(colaboradorQuestionario);

    	colaboradorQuestionarioManager.expects(once()).method("findById").with(eq(colaboradorQuestionario.getId())).will(returnValue(colaboradorQuestionario));
    	colaboradorRespostaManager.expects(once()).method("removeFicha").with(eq(colaboradorQuestionario.getId()));

    	assertEquals("success", colaboradorQuestionarioAction.delete());
    }

    public void testGets() throws Exception
    {
    	assertTrue(colaboradorQuestionarioAction.getColaboradorQuestionarios().isEmpty());

    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionarioAction.setColaboradorQuestionario(colaboradorQuestionario);
    	assertEquals(colaboradorQuestionario, colaboradorQuestionarioAction.getColaboradorQuestionario());

    	ColaboradorQuestionario colaboradorQuestionario1 = null;
    	colaboradorQuestionarioAction.setColaboradorQuestionario(colaboradorQuestionario1);
    	assertNotNull(colaboradorQuestionarioAction.getColaboradorQuestionario());
    }

    public void testVisualizarRespostasPorColaborador()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	colaboradorQuestionarioAction.setQuestionario(questionario);
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	Collection<ColaboradorResposta> colaboradorRespostas = ColaboradorRespostaFactory.getCollection();

    	colaboradorManager.expects(once()).method("findByIdProjectionUsuario").with(ANYTHING).will(returnValue(colaborador));
    	questionarioManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(questionario));
    	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorQuestionario));

    	colaboradorRespostaManager.expects(once()).method("findRespostasColaborador").with(ANYTHING, ANYTHING).will(returnValue(colaboradorRespostas));
    	
    	assertEquals("success", colaboradorQuestionarioAction.visualizarRespostasPorColaborador());
    }
}

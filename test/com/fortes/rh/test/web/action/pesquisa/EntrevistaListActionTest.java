package com.fortes.rh.test.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.EntrevistaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.EntrevistaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.EntrevistaListAction;

public class EntrevistaListActionTest extends MockObjectTestCase
{
	private EntrevistaListAction entrevistaListAction;
	private Mock entrevistaManager;
	private Mock colaboradorManager;
	private Mock colaboradorQuestionarioManager;
	private Mock empresaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        entrevistaListAction = new EntrevistaListAction();

        entrevistaManager = new Mock(EntrevistaManager.class);
        entrevistaListAction.setEntrevistaManager((EntrevistaManager) entrevistaManager.proxy());

        colaboradorManager = new Mock(ColaboradorManager.class);
        entrevistaListAction.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        entrevistaListAction.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

        empresaManager = new Mock(EmpresaManager.class);
        entrevistaListAction.setEmpresaManager((EmpresaManager) empresaManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        entrevistaManager = null;
        entrevistaListAction = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals("success", entrevistaListAction.execute());
    }

    public void testDelete() throws Exception
    {
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	entrevistaListAction.setEntrevista(entrevista);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	entrevistaListAction.setEmpresaSistema(empresa);

    	entrevistaManager.expects(once()).method("delete").with(ANYTHING, ANYTHING);

    	assertEquals("success", entrevistaListAction.delete());
    	assertNotNull(entrevistaListAction.getActionMsg());
    }

    public void testList() throws Exception
    {
    	Collection<Entrevista> entrevistas = EntrevistaFactory.getCollection();

    	entrevistaManager.expects(once()).method("getCount").with(ANYTHING).will(returnValue(1));
    	entrevistaManager.expects(once()).method("findToListByEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(entrevistas));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(new ArrayList<Empresa>()));
    	
    	assertEquals("success", entrevistaListAction.list());
    	assertEquals(1, entrevistaListAction.getTotalSize());
    }

    public void testClonarEntrevista() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	entrevistaListAction.setEmpresaSistema(empresa);
    	
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	entrevistaListAction.setEntrevista(entrevista);

    	entrevistaManager.expects(once()).method("clonarEntrevista").with(eq(entrevista.getId()), eq(new Long[]{empresa.getId()})).isVoid();

    	assertEquals("success", entrevistaListAction.clonarEntrevista());
    	assertEquals(entrevista.getId(), entrevistaListAction.getEmpresaSistema().getId());
    }

    public void testClonarEntrevistaException() throws Exception
    {
    	Collection<Entrevista> entrevistas = EntrevistaFactory.getCollection();

    	entrevistaManager.expects(once()).method("getCount").with(ANYTHING).will(returnValue(1));
    	entrevistaManager.expects(once()).method("findToListByEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(entrevistas));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(new ArrayList<Empresa>()));

    	assertEquals("input", entrevistaListAction.clonarEntrevista());
    }

    public void testPrepareResponderEntrevista() throws Exception
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	entrevistaListAction.setColaborador(colaborador);

    	ColaboradorQuestionario colaboradorQuestionario = null;

    	Collection<Entrevista> entrevistas = EntrevistaFactory.getCollection();

    	colaboradorQuestionarioManager.expects(once()).method("findColaboradorComEntrevistaDeDesligamento").with(ANYTHING).will(returnValue(colaboradorQuestionario));

    	entrevistaManager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(entrevistas));

    	colaboradorManager.expects(once()).method("findByIdHistoricoAtual").with(eq(colaborador.getId()), eq(Boolean.FALSE)).will(returnValue(colaborador));

    	assertEquals("success", entrevistaListAction.prepareResponderEntrevista());
    	assertEquals(colaborador, entrevistaListAction.getColaborador());
    }

    public void testPrepareResponderEntrevistaJaRespondida() throws Exception
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	entrevistaListAction.setColaborador(colaborador);

    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);

    	colaboradorQuestionarioManager.expects(once()).method("findColaboradorComEntrevistaDeDesligamento").with(ANYTHING).will(returnValue(colaboradorQuestionario));

    	assertEquals("entrevistaDeDesligamentoJaRespondida", entrevistaListAction.prepareResponderEntrevista());
    	assertEquals(colaborador, entrevistaListAction.getColaborador());
    }

    public void testGets() throws Exception
    {
    	entrevistaListAction.setEntrevista(null);
    	assertNotNull(entrevistaListAction.getEntrevista());

    	Entrevista entrevista = EntrevistaFactory.getEntity();
    	entrevista.setId(1L);

    	entrevistaListAction.setEntrevista(entrevista);

    	assertEquals(entrevista, entrevistaListAction.getEntrevista());

    	entrevistaListAction.setEntrevista(null);

    	assertTrue(entrevistaListAction.getEntrevistas().isEmpty());

    	ColaboradorQuestionario colaboraradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	entrevistaListAction.setColaboradorQuestionario(colaboraradorQuestionario);
    	assertNotNull(entrevistaListAction.getColaboradorQuestionario());
    }
}

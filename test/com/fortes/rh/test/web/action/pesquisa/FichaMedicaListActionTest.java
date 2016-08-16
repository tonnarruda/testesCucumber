package com.fortes.rh.test.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.FichaMedicaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.FichaMedicaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.FichaMedicaListAction;

public class FichaMedicaListActionTest extends MockObjectTestCase
{	
	private FichaMedicaListAction fichaMedicaListAction;
	private Mock fichaMedicaManager;
	private Mock colaboradorQuestionarioManager;
	private Mock colaboradorRespostaManager;
	private Mock candidatoManager;
	private Mock empresaManager;

    protected void setUp() throws Exception
    {
        fichaMedicaListAction = new FichaMedicaListAction();

        fichaMedicaManager = new Mock(FichaMedicaManager.class);
        fichaMedicaListAction.setFichaMedicaManager((FichaMedicaManager) fichaMedicaManager.proxy());
        
        colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
        fichaMedicaListAction.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());
        
        colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
        fichaMedicaListAction.setColaboradorRespostaManager((ColaboradorRespostaManager) colaboradorRespostaManager.proxy());
        
        candidatoManager = mock(CandidatoManager.class);
        fichaMedicaListAction.setCandidatoManager((CandidatoManager) candidatoManager.proxy());

        empresaManager = mock(EmpresaManager.class);
        fichaMedicaListAction.setEmpresaManager((EmpresaManager) empresaManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        
        fichaMedicaListAction.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

    protected void tearDown() throws Exception
    {
        fichaMedicaManager = null;
        fichaMedicaListAction = null;
        MockSecurityUtil.verifyRole = false;
    }

    public void testDelete() throws Exception
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	fichaMedicaListAction.setFichaMedica(fichaMedica);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	fichaMedicaListAction.setEmpresaSistema(empresa);

    	Collection<FichaMedica> fichaMedicas = FichaMedicaFactory.getCollection();

    	fichaMedicaManager.expects(once()).method("delete").with(ANYTHING, ANYTHING);
    	fichaMedicaManager.expects(once()).method("findToListByEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(fichaMedicas));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").withAnyArguments().will(returnValue(new ArrayList<Empresa>()));

    	assertEquals("success", fichaMedicaListAction.delete());
    	assertNotNull(fichaMedicaListAction.getActionSuccess());
    }

    public void testDeleteException() throws Exception
    {
    	Collection<FichaMedica> fichaMedicas = FichaMedicaFactory.getCollection();

    	fichaMedicaManager.expects(once()).method("findToListByEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(fichaMedicas));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").withAnyArguments().will(returnValue(new ArrayList<Empresa>()));
    	
    	assertEquals("success", fichaMedicaListAction.delete());
    	assertNull(fichaMedicaListAction.getActionMsg());
    	assertNotNull(fichaMedicaListAction.getActionErrors());
    }

    public void testList() throws Exception
    {
    	Collection<FichaMedica> fichaMedicas = FichaMedicaFactory.getCollection();

    	fichaMedicaManager.expects(once()).method("findToListByEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(fichaMedicas));
    	
    	empresaManager.expects(once()).method("findEmpresasPermitidas").with(eq(true), eq(null), ANYTHING, eq(new String[]{"ROLE_CAD_FICHAMEDICA"})).will(returnValue(new ArrayList<Empresa>()));

    	assertEquals("success", fichaMedicaListAction.list());
    }

    public void testClonarFichaMedica() throws Exception
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	fichaMedicaListAction.setFichaMedica(fichaMedica);

    	fichaMedicaManager.expects(once()).method("clonarFichaMedica").with(eq(fichaMedica.getId()), ANYTHING).isVoid();
    	fichaMedicaManager.expects(once()).method("findToListByEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(FichaMedicaFactory.getCollection()));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").withAnyArguments().will(returnValue(new ArrayList<Empresa>()));

    	assertEquals("success", fichaMedicaListAction.clonarFichaMedica()); 
    	assertEquals(fichaMedica.getId(), fichaMedicaListAction.getEmpresaSistema().getId());
    }

    public void testClonarFichaMedicaException() throws Exception
    {
    	Collection<FichaMedica> fichaMedicas = FichaMedicaFactory.getCollection();

    	fichaMedicaManager.expects(once()).method("findToListByEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(fichaMedicas));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").withAnyArguments().will(returnValue(new ArrayList<Empresa>()));

    	assertEquals("input", fichaMedicaListAction.clonarFichaMedica());
    }
    
    public void testListPreenchida() throws Exception
    {
    	fichaMedicaListAction.setActionMsg("Teste");
    	fichaMedicaListAction.setVinculo('C');
    	fichaMedicaListAction.setNomeBusca("Colaborador");
    	Date hoje = new Date();
		fichaMedicaListAction.setDataIni(hoje);
		fichaMedicaListAction.setDataFim(hoje);
		fichaMedicaListAction.setCpfBusca("042123432299");
		fichaMedicaListAction.setMatriculaBusca("112333");
    	
    	colaboradorQuestionarioManager.expects(once()).method("findFichasMedicas").will(returnValue(new ArrayList<ColaboradorQuestionario>()));
    	
    	assertEquals("success", fichaMedicaListAction.listPreenchida());
    }
    
    public void testDeletePreenchida() throws Exception
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(3L);
    	fichaMedicaListAction.setColaboradorQuestionario(colaboradorQuestionario);
    	
    	colaboradorRespostaManager.expects(once()).method("removeFicha").with(eq(3L)).isVoid();
    	
    	assertEquals("success",fichaMedicaListAction.deletePreenchida());
    	assertEquals("Ficha médica excluída com sucesso.", fichaMedicaListAction.getActionSuccess().toArray()[0]);
    }
    
    public void testPrepareInsertFicha() throws Exception
    {
    	fichaMedicaListAction.setActionMsg("Teste");
    	Candidato candidato = CandidatoFactory.getCandidato();
		fichaMedicaListAction.setCandidato(candidato);
    	
    	fichaMedicaManager.expects(once()).method("findAllSelect").with(eq(fichaMedicaListAction.getEmpresaSistema().getId()), eq(true)).will(returnValue(new ArrayList<FichaMedica>()));
    	
    	assertEquals("success",fichaMedicaListAction.prepareInsertFicha());
    	
    }

    public void testGets() throws Exception
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity();
    	fichaMedica.setId(1L);

    	fichaMedicaListAction.setFichaMedica(fichaMedica);

    	assertEquals(fichaMedica, fichaMedicaListAction.getFichaMedica());

    	fichaMedicaListAction.setFichaMedica(null);
    	
    	fichaMedicaListAction.setFichaMedicas(new ArrayList<FichaMedica>());
    	assertTrue(fichaMedicaListAction.getFichaMedicas().isEmpty());
    	
    	fichaMedicaListAction.setColaborador(ColaboradorFactory.getEntity());
    	fichaMedicaListAction.getColaborador();
    	fichaMedicaListAction.getColaboradorQuestionario();
    	fichaMedicaListAction.setVoltarPara("");
    	fichaMedicaListAction.getVoltarPara();
    	fichaMedicaListAction.getCandidatos();
    	fichaMedicaListAction.getCandidato();
    	fichaMedicaListAction.getNomeBusca();
    	fichaMedicaListAction.getCpfBusca();
    	fichaMedicaListAction.getMatriculaBusca();
    	fichaMedicaListAction.getVinculo();
    	fichaMedicaListAction.getDataFim();
    	fichaMedicaListAction.getDataIni();
    	
    	fichaMedicaListAction.getColaboradorQuestionarios();
    	
    	
    }
}

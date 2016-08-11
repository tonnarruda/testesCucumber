package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.desenvolvimento.CertificacaoListAction;

public class CertificacaoListActionTest extends MockObjectTestCase
{
	private CertificacaoListAction action;
	private Mock certificacaoManager;
	private Mock cursoManager;
	private Mock areaOrganizacionalManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new CertificacaoListAction();
        cursoManager = new Mock(CursoManager.class);
        action.setCursoManager((CursoManager) cursoManager.proxy());
        
        certificacaoManager = mock(CertificacaoManager.class);
        action.setCertificacaoManager((CertificacaoManager) certificacaoManager.proxy());
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
    }

    protected void tearDown() throws Exception
    {
        cursoManager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testList() throws Exception
    {
    	Collection<Certificacao> certificacaos = new ArrayList<Certificacao>();
    	
    	certificacaoManager.expects(once()).method("getCount").will(returnValue(2));
    	certificacaoManager.expects(once()).method("findAllSelect").will(returnValue(certificacaos));
    	
    	assertEquals("success", action.list());
    	assertEquals(certificacaos, action.getCertificacaos());
    }

    public void testDelete() throws Exception
    {
    	Certificacao certificacao = CertificacaoFactory.getEntity(1L);
    	action.setCertificacao(certificacao);
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	
    	certificacaoManager.expects(once()).method("verificaEmpresa").with(ANYTHING, ANYTHING).will(returnValue(true));
    	certificacaoManager.expects(once()).method("remove").with(eq(certificacao.getId()));
    	
    	assertEquals("success", action.delete());
    	assertEquals("Certificação excluída com sucesso.", action.getActionSuccess().toArray()[0]);
    }

    public void testDeleteEmpresaErrada() throws Exception
    {

    	Certificacao certificacao = CertificacaoFactory.getEntity(1L);
    	certificacao.setEmpresa(EmpresaFactory.getEmpresa(5L));
    	
    	action.setCertificacao(certificacao);
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	
    	certificacaoManager.expects(once()).method("verificaEmpresa").with(ANYTHING, ANYTHING).will(returnValue(false));
    	
    	assertEquals("success", action.delete());
    }

    public void testImprimir() throws Exception 
    {
    	Collection<Curso> cursos = new ArrayList<Curso>();
    	Curso curso = CursoFactory.getEntity(10L);
    	cursos.add(curso);
    	Certificacao certificacao = CertificacaoFactory.getEntity(1L);
    	action.setCertificacao(certificacao);
    	
    	cursoManager.expects(once()).method("findByCertificacao").will(returnValue(cursos));
    	certificacaoManager.expects(once()).method("findById").with(eq(certificacao.getId())).will(returnValue(certificacao));
    	
    	assertEquals("success", action.imprimir());
    }

    public void testGets() throws Exception
    {
    	action.setCertificacao(null);
    	assertNotNull(action.getCertificacao());
    	
    	action.getAreasCheckList();
    	action.getCargosCheckList();
    	action.getFaixaSalarialsCheckList();
    	action.setCargosCheck(null);
    	action.setFaixaSalarialsCheck(null);
    	action.getParametros();
    	action.getMatrizTreinamentos();
    	action.getCursos();
    }
}
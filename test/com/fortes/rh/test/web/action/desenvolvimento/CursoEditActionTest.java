package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.desenvolvimento.CursoEditAction;

public class CursoEditActionTest extends MockObjectTestCase
{
	private CursoEditAction action;
	private Mock cursoManager;
	private Mock avaliacaoCursoManager;
	private Mock empresaManager;
	private Mock parametrosDoSistemaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new CursoEditAction();
        cursoManager = new Mock(CursoManager.class);
        action.setCursoManager((CursoManager) cursoManager.proxy());

        avaliacaoCursoManager = new Mock(AvaliacaoCursoManager.class);
        action.setAvaliacaoCursoManager((AvaliacaoCursoManager) avaliacaoCursoManager.proxy());

        empresaManager = new Mock(EmpresaManager.class);
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());

        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        cursoManager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }
    
    public void testPrepareUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Usuario usuario = UsuarioFactory.getEntity(1L);

    	action.setUsuarioLogado(usuario);
    	action.setEmpresaSistema(empresa);
    	
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setEmpresa(empresa);
    	
    	action.setCurso(curso);
    	
    	avaliacaoCursoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<AvaliacaoCurso>()));
    	cursoManager.expects(once()).method("findById").with(eq(curso.getId())).will(returnValue(curso));
    	cursoManager.expects(once()).method("existeEmpresasNoCurso").with(eq(empresa.getId()), eq(curso.getId())).will(returnValue(true));
    	cursoManager.expects(once()).method("existeAvaliacaoAlunoDeTipoNotaOuPorcentagemRespondida").with(eq(curso.getId())).will(returnValue(false));
    	cursoManager.expects(once()).method("existeAvaliacaoAlunoDeTipoAvaliacaoRespondida").with(eq(curso.getId())).will(returnValue(false));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").with(eq(true),eq(empresa.getId()),eq(usuario.getId()),ANYTHING).will(returnValue(new ArrayList<Empresa>()));
    	
    	ParametrosDoSistema params = new ParametrosDoSistema();
    	params.setId(1L);
    	params.setCompartilharCursos(true);
    	parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(params));

    	assertEquals("success", action.prepareUpdate());
    }
    
    public void testPrepareUpdateEmpresaErrada() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(2L);
    	Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);
    	Usuario usuario = UsuarioFactory.getEntity(1L);

    	action.setUsuarioLogado(usuario);
    	action.setEmpresaSistema(empresaSistema);
    	
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setEmpresa(empresa);
    	action.setCurso(curso);
    	
    	avaliacaoCursoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<AvaliacaoCurso>()));
    	cursoManager.expects(once()).method("findById").with(eq(curso.getId())).will(returnValue(curso));
    	cursoManager.expects(once()).method("existeEmpresasNoCurso").with(eq(empresaSistema.getId()), eq(curso.getId())).will(returnValue(false));
    	cursoManager.expects(once()).method("existeAvaliacaoAlunoDeTipoNotaOuPorcentagemRespondida").with(eq(curso.getId())).will(returnValue(false));
    	cursoManager.expects(once()).method("existeAvaliacaoAlunoDeTipoAvaliacaoRespondida").with(eq(curso.getId())).will(returnValue(false));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").with(eq(true),eq(empresaSistema.getId()),eq(usuario.getId()),ANYTHING).will(returnValue(new ArrayList<Empresa>()));
    	
    	ParametrosDoSistema params = new ParametrosDoSistema();
    	params.setId(1L);
    	params.setCompartilharCursos(true);
    	parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(params));
    	
    	assertEquals("error", action.prepareUpdate());
    }
    
    public void testPrepareInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Usuario usuario = UsuarioFactory.getEntity(1L);

    	action.setUsuarioLogado(usuario);
    	action.setEmpresaSistema(empresa);
    	
    	Collection<AvaliacaoCurso> avaliacoes = new ArrayList<AvaliacaoCurso>();
    	avaliacoes.add(AvaliacaoCursoFactory.getEntity(1L));
    	avaliacaoCursoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(avaliacoes));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").with(eq(true),eq(empresa.getId()),eq(usuario.getId()),ANYTHING).will(returnValue(new ArrayList<Empresa>()));
    	
    	ParametrosDoSistema params = new ParametrosDoSistema();
    	params.setId(1L);
    	params.setCompartilharCursos(true);
    	parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(params));
    	
    	assertEquals("success", action.prepareInsert());
    	assertEquals(1, action.getAvaliacaoCursoCheckList().size());
    }
    
    public void testInsert() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);

    	cursoManager.expects(once()).method("save").with(eq(curso));
    	assertEquals("success", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setEmpresa(empresa);
    	action.setCurso(curso);

    	cursoManager.expects(once()).method("update").with(eq(curso), ANYTHING, ANYTHING);
    	assertEquals("success", action.update());
    }
    
    public void testUpdateExeption() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Usuario usuario = UsuarioFactory.getEntity(1L);

    	action.setUsuarioLogado(usuario);
    	action.setEmpresaSistema(empresa);
    	
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setEmpresa(empresa);
    	action.setCurso(curso);
    	
    	cursoManager.expects(once()).method("update").with(eq(curso), ANYTHING, ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(curso.getId(),""))));;
    	avaliacaoCursoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<AvaliacaoCurso>()));
    	cursoManager.expects(once()).method("findById").with(eq(curso.getId())).will(returnValue(curso));
    	cursoManager.expects(once()).method("existeEmpresasNoCurso").with(eq(empresa.getId()), eq(curso.getId())).will(returnValue(true));
    	cursoManager.expects(once()).method("existeAvaliacaoAlunoDeTipoNotaOuPorcentagemRespondida").with(eq(curso.getId())).will(returnValue(false));
    	cursoManager.expects(once()).method("existeAvaliacaoAlunoDeTipoAvaliacaoRespondida").with(eq(curso.getId())).will(returnValue(false));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").with(eq(true),eq(empresa.getId()),eq(usuario.getId()),ANYTHING).will(returnValue(new ArrayList<Empresa>()));
    	
    	ParametrosDoSistema params = new ParametrosDoSistema();
    	params.setId(1L);
    	params.setCompartilharCursos(true);
    	parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(params));
    	
    	assertEquals("input", action.update());
    }
    
//    public void testUpdateEmpresaErrada() throws Exception
//    {
//    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
//    	Curso curso = CursoFactory.getEntity(1L);
//    	curso.setEmpresa(empresa);
//    	action.setCurso(curso);
//    	
//    	cursoManager.expects(once()).method("update").with(eq(curso), ANYTHING, ANYTHING).will(throwException(new Exception()));;
//    	
//    	assertEquals("input", action.update());
//    }

    public void testGets() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);
    	assertEquals(curso, action.getModel());
    	
    	action.setCurso(null);
    	assertNotNull(action.getCurso());    	
    }

}
package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.action.desenvolvimento.RelatorioPresencaAction;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class RelatorioPresencaActionTest extends MockObjectTestCase
{
	private RelatorioPresencaAction action;
	private Mock cursoManager;
	private Mock colaboradorTurmaManager;
	private Mock turmaManager;
	private Mock colaboradorPresencaManager;
	private Mock parametrosDoSistemaManager;
	
    protected void setUp() throws Exception
    {
        super.setUp();
        action = new RelatorioPresencaAction();

        cursoManager = new Mock(CursoManager.class);
        action.setCursoManager((CursoManager) cursoManager.proxy());

        colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
        action.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());

        turmaManager = new Mock(TurmaManager.class);
        action.setTurmaManager((TurmaManager) turmaManager.proxy());
        
        colaboradorPresencaManager = new Mock(ColaboradorPresencaManager.class);
        action.setColaboradorPresencaManager((ColaboradorPresencaManager) colaboradorPresencaManager.proxy());
        
		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		MockSpringUtil.mocks.put("parametrosDoSistemaManager", parametrosDoSistemaManager);

		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
    }

    protected void tearDown() throws Exception
    {
        action = null;
        super.tearDown();
    }

    public void testExecuteEmpresaErrada() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(2L);
    	
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setEmpresa(empresa);
    	
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setCurso(curso);
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	colaboradorTurma.setTurma(turma);
    	
    	action.setColaboradorTurma(colaboradorTurma);
    	Collection<Curso> cursos = new ArrayList<Curso>();
    	
    	turmaManager.expects(once()).method("findById").with(eq(colaboradorTurma.getTurma().getId())).will(returnValue(turma));    	
    	cursoManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(cursos));
    	
    	assertEquals("acessonegado", action.execute());
    	assertEquals(cursos, action.getCursos());
    }
    
    public void testExecute() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setEmpresa(empresa);
    	
    	Date data = new Date();
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setCurso(curso);
    	turma.setDataPrevFim(data);
    	turma.setDataPrevIni(data);
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	colaboradorTurma.setTurma(turma);
    	
    	action.setColaboradorTurma(colaboradorTurma);
    	
    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
    	colaboradorTurmas.add(colaboradorTurma);
    	action.setDiasCheck(new String[]{"1"});
    	
    	turmaManager.expects(once()).method("findById").with(eq(colaboradorTurma.getTurma().getId())).will(returnValue(turma));    	
    	colaboradorTurmaManager.expects(once()).method("findByTurma").with(eq(colaboradorTurma.getTurma().getId()), eq(null), ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));    	
    	colaboradorTurmaManager.expects(once()).method("montaColunas").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(colaboradorTurmas));    	
    	colaboradorPresencaManager.expects(once()).method("preparaLinhaEmBranco").with(ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
    	
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setAppVersao("1.01.1");

		parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));
		
    	assertEquals("success", action.execute());
    	assertEquals(colaboradorTurma, action.getColaboradorTurma());
    	assertEquals(colaboradorTurmas, action.getColaboradorTurmas());
    	assertNotNull(action.getListaDePresencas());
    }
    
    public void testExecuteException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setEmpresa(empresa);
    	
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setCurso(curso);
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	colaboradorTurma.setTurma(turma);
    	
    	action.setColaboradorTurma(colaboradorTurma);
    	
    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
    	colaboradorTurmas.add(colaboradorTurma);
    	action.setDiasCheck(new String[]{"1"});
    	
    	turmaManager.expects(once()).method("findById").with(eq(colaboradorTurma.getTurma().getId())).will(returnValue(turma));    	
    	colaboradorTurmaManager.expects(once()).method("findByTurma").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(turma.getId(),""))));

    	assertEquals("error", action.execute());
    }
          
    public void testGets() throws Exception
    {
    	Map parametros = new HashMap();
    	action.setParametros(parametros);
    	assertEquals(parametros, action.getParametros());
    	action.getTurmas();
    	action.setDiasCheckList(null);
    	String[] diasCheck = new String[]{};
    	action.setDiasCheck(diasCheck);
    	assertEquals(diasCheck, action.getDiasCheck());
    }
}
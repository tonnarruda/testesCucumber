package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.FiltroPlanoTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.desenvolvimento.TurmaListAction;

public class TurmaListActionTest extends MockObjectTestCase
{
	private TurmaListAction action;
	private Mock turmaManager;
	private Mock colaboradorTurmaManager;
	private Mock cursoManager;
	private Mock avaliacaoCursoManager;
	private Mock parametrosDoSistemaManager;
	private Mock empresaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new TurmaListAction();
        turmaManager = new Mock(TurmaManager.class);
        colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
        cursoManager = new Mock(CursoManager.class);
        avaliacaoCursoManager = new Mock(AvaliacaoCursoManager.class);
        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        empresaManager = new Mock(EmpresaManager.class);

        action.setAvaliacaoCursoManager((AvaliacaoCursoManager) avaliacaoCursoManager.proxy());
        action.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());
        action.setTurmaManager((TurmaManager) turmaManager.proxy());
        action.setCursoManager((CursoManager) cursoManager.proxy());
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        turmaManager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testFiltroPlanoTreinamento() throws Exception
    {
    	Date data = new Date();
    	Collection<Curso> cursos = new ArrayList<Curso>();
    	FiltroPlanoTreinamento filtroPT = new FiltroPlanoTreinamento();
    	filtroPT.setCursoId(1L);
    	filtroPT.setPage(2);
    	filtroPT.setDataIni(data);
    	filtroPT.setDataFim(data);
    	filtroPT.setRealizada('S');
    	
    	action.setFiltroPlanoTreinamento(filtroPT);
    	action.setShowFilter(true);
    	Collection<Turma> turmas = new ArrayList<Turma>();
		
    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	parametrosDoSistema.setCompartilharCandidatos(true);

    	cursoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(cursos));
    	cursoManager.expects(once()).method("somaCargaHoraria").with(ANYTHING).will(returnValue("14:30"));
    	turmaManager.expects(once()).method("findPlanosDeTreinamento").with(new Constraint[]{ANYTHING, ANYTHING, eq(filtroPT.getCursoId()), eq(filtroPT.getDataIni()), eq(filtroPT.getDataFim()), eq('S'), ANYTHING}).will(returnValue(turmas));
    	parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
    	
    	assertEquals("successFiltroPlanoTreinamento", action.filtroPlanoTreinamento());
    	assertEquals(cursos, action.getCursos());
    	assertNotNull(action.getFiltroPlanoTreinamento());
    }

    public void testFiltroPlanoTreinamentoRealizadaFalse() throws Exception
    {
    	Date data = new Date();
    	Collection<Curso> cursos = new ArrayList<Curso>();
    	FiltroPlanoTreinamento filtroPT = new FiltroPlanoTreinamento();
    	filtroPT.setCursoId(1L);
    	filtroPT.setPage(2);
    	filtroPT.setDataIni(data);
    	filtroPT.setDataFim(data);
    	filtroPT.setRealizada('N');
    	action.setFiltroPlanoTreinamento(filtroPT);

    	action.setShowFilter(true);
    	Collection<Turma> turmas = new ArrayList<Turma>();
    	
    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	parametrosDoSistema.setCompartilharCandidatos(true);

    	cursoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(cursos));
    	cursoManager.expects(once()).method("somaCargaHoraria").with(ANYTHING).will(returnValue("14:30"));
    	turmaManager.expects(once()).method("findPlanosDeTreinamento").with(new Constraint[]{ANYTHING, ANYTHING, eq(filtroPT.getCursoId()), eq(filtroPT.getDataIni()), eq(filtroPT.getDataFim()), eq('N'), ANYTHING}).will(returnValue(turmas));
    	parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		
    	assertEquals("successFiltroPlanoTreinamento", action.filtroPlanoTreinamento());
    }

    public void testImprimirConfirmacaoCertificado() throws Exception
    {
    	Turma turma = TurmaFactory.getEntity(1L);
    	action.setTurma(turma);

    	colaboradorTurmaManager.expects(once()).method("findAprovadosByTurma").with(eq(turma.getId())).will(returnValue(new ArrayList<ColaboradorTurma>()));
    	colaboradorTurmaManager.expects(once()).method("getDadosTurma").with(ANYTHING, ANYTHING).will(returnValue(new HashMap<String, Object>()));
    	turmaManager.expects(once()).method("getParametrosRelatorio").with(ANYTHING, ANYTHING).will(returnValue(new HashMap<String, Object>()));

    	assertEquals("success", action.imprimirConfirmacaoCertificado());
    }

    public void testImprimirConfirmacaoCertificadoException() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);

    	turmaManager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(0));
    	turmaManager.expects(once()).method("findTurmas").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Turma>()));
    	cursoManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(new Curso()));
    	avaliacaoCursoManager.expects(once()).method("findByCurso");

    	assertEquals("input", action.imprimirConfirmacaoCertificado());
    }

    public void testList() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);
    	action.setPage(1);

    	//turmaManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Turma>()));
    	turmaManager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(0));
    	turmaManager.expects(once()).method("findTurmas").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Turma>()));
    	cursoManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(new Curso()));
    	avaliacaoCursoManager.expects(once()).method("findByCurso");
    	action.setMsgAlert("teste");

    	assertEquals("success", action.list());
    }

    public void testDelete() throws Exception
    {
    	Turma turma = TurmaFactory.getEntity(1L);
    	action.setTurma(turma);

    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);

    	action.setFiltroPlanoTreinamento(null);

    	turmaManager.expects(once()).method("removeCascade").with(eq(turma.getId()));

    	assertEquals("success", action.delete());
    }

    public void testDeleteFiltroPlano() throws Exception
    {
    	FiltroPlanoTreinamento filtroPT = new FiltroPlanoTreinamento();
    	filtroPT.setDataIni(new Date());
    	action.setFiltroPlanoTreinamento(filtroPT);

    	Turma turma = TurmaFactory.getEntity(1L);
    	action.setTurma(turma);

    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);

    	turmaManager.expects(once()).method("removeCascade").with(eq(turma.getId()));
    	
    	//essas 2 flags estão ativas quando o delete vem da listagem "plano de treinamento"
    	action.setShowFilter(true);
    	action.setPlanoTreinamento(true);
    	
    	assertEquals("successFiltroPlanoTreinamento", action.delete());
    }
    
    public void testGets() throws Exception
    {
    	action.setDataSource(new ArrayList<ColaboradorTurma>());
    	assertNotNull(action.getDataSource());

    	action.setMsgAlert("teste");
    	assertEquals("teste", action.getMsgAlert());

    	action.setCurso(new Curso());
    	assertNotNull(action.getCurso());

    	assertNull(action.getTurmas());

    	action.setParametros(new HashMap<String, Object>());
    	assertNotNull(action.getParametros());

    	action.setTurma(null);
    	assertNotNull(action.getTurma());
    }

}
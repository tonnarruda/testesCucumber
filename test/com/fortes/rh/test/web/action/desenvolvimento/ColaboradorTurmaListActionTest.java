package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.desenvolvimento.ColaboradorTurmaListAction;

public class ColaboradorTurmaListActionTest extends MockObjectTestCase
{
	private ColaboradorTurmaListAction action;
	private Mock colaboradorTurmaManager;
	private Mock turmaManager;
	private Mock colaboradorQuestionarioManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new ColaboradorTurmaListAction();

        colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
        action.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());

        turmaManager = new Mock(TurmaManager.class);
        action.setTurmaManager((TurmaManager) turmaManager.proxy());

        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        action.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testList() throws Exception
    {
    	action.setMsgAlert("msgAlert");

    	Turma turma = TurmaFactory.getEntity(1L);
    	action.setTurma(turma);
    	turmaManager.expects(once()).method("findByIdProjection").with(eq(turma.getId())).will(returnValue(turma));

    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();

    	colaboradorTurmaManager.expects(once()).method("findByTurma").with(eq(turma.getId())).will(returnValue(colaboradorTurmas));
    	colaboradorTurmaManager.expects(once()).method("setFamiliaAreas").with(ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
    	colaboradorQuestionarioManager.expects(once()).method("findRespondidasByColaboradorETurma").with(eq(null), eq(turma.getId())).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
    	assertEquals("success", action.list());
    	assertEquals(colaboradorTurmas, action.getColaboradorTurmas());
    }

//    public void testDelete() throws Exception
//    {
//    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
//    	action.setColaboradorTurma(colaboradorTurma);
//
//    	colaboradorTurmaManager.expects(once()).method("comparaEmpresa").with(eq(colaboradorTurma), ANYTHING).will(returnValue(true));
//    	colaboradorTurmaManager.expects(once()).method("remove").with(eq(colaboradorTurma));
//
//    	assertEquals("success", action.delete());
//    }

    public void testDeleteComDnt() throws Exception
    {
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	action.setColaboradorTurma(colaboradorTurma);

    	colaboradorTurmaManager.expects(once()).method("comparaEmpresa").with(eq(colaboradorTurma), ANYTHING).will(returnValue(true));
    	colaboradorTurmaManager.expects(once()).method("remove").with(eq(colaboradorTurma));

    	Long idFiltro = new Long(1L);
    	action.setDntId(idFiltro);
    	action.setAreaFiltroId(idFiltro);

    	assertEquals("successDnt", action.delete());
    	assertNotNull(action.getMsgAlert());
    	assertEquals(idFiltro, action.getDntId());
    	assertEquals(idFiltro, action.getAreaFiltroId());
    }

    public void testDeleteEmpresaErrada() throws Exception
    {
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	action.setColaboradorTurma(colaboradorTurma);

    	colaboradorTurmaManager.expects(once()).method("comparaEmpresa").with(eq(colaboradorTurma), ANYTHING).will(returnValue(false));

    	//list
    	Turma turma = TurmaFactory.getEntity(1L);
    	action.setTurma(turma);
    	turmaManager.expects(once()).method("findByIdProjection").with(eq(turma.getId())).will(returnValue(turma));

    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
    	colaboradorTurmaManager.expects(once()).method("findByTurma").with(eq(turma.getId())).will(returnValue(colaboradorTurmas));
    	colaboradorTurmaManager.expects(once()).method("setFamiliaAreas").with(ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
    	colaboradorQuestionarioManager.expects(once()).method("findRespondidasByColaboradorETurma").with(eq(null), eq(turma.getId())).will(returnValue(new ArrayList<ColaboradorQuestionario>()));

    	assertEquals("input", action.delete());
    }

    public void testDeleteException() throws Exception
    {
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	action.setColaboradorTurma(colaboradorTurma);

    	colaboradorTurmaManager.expects(once()).method("comparaEmpresa").with(eq(colaboradorTurma), ANYTHING).will(returnValue(true));
    	colaboradorTurmaManager.expects(once()).method("remove").with(eq(colaboradorTurma)).will(throwException(new org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException(new ObjectNotFoundException(colaboradorTurma.getId(),""))));;

    	//list
    	Turma turma = TurmaFactory.getEntity(1L);
    	action.setTurma(turma);
    	turmaManager.expects(once()).method("findByIdProjection").with(eq(turma.getId())).will(returnValue(turma));

    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
    	colaboradorTurmaManager.expects(once()).method("findByTurma").with(eq(turma.getId())).will(returnValue(colaboradorTurmas));
    	colaboradorTurmaManager.expects(once()).method("setFamiliaAreas").with(ANYTHING, ANYTHING).will(returnValue(colaboradorTurmas));
    	colaboradorQuestionarioManager.expects(once()).method("findRespondidasByColaboradorETurma").with(eq(null), eq(turma.getId())).will(returnValue(new ArrayList<ColaboradorQuestionario>()));

    	assertEquals("input", action.delete());
    }

    public void testGets() throws Exception
    {
    	action.setColaboradorTurma(null);
    	assertNotNull(action.getColaboradorTurma());

    	Turma turma = TurmaFactory.getEntity(1L);
    	action.setTurma(turma);
    	assertEquals(turma, action.getTurma());

    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);
    	assertEquals(curso, action.getCurso());

    	action.setGestor(true);
    	assertEquals(true, action.isGestor());

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	action.setEstabelecimento(estabelecimento);
    	assertEquals(estabelecimento, action.getEstabelecimento());

    	action.setPrioridadeTreinamentos(null);
    }

}
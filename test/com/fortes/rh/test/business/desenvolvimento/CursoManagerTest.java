package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.CursoManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

public class CursoManagerTest extends MockObjectTestCase
{
	private CursoManagerImpl cursoManager = new CursoManagerImpl();
	private Mock colaboradorManager;
	private Mock cursoDao = null;

	protected void setUp() throws Exception
	{
		cursoDao = new Mock(CursoDao.class);
		cursoManager.setDao((CursoDao) cursoDao.proxy());
		colaboradorManager = new Mock(ColaboradorManager.class);
		cursoManager.setColaboradorManager((ColaboradorManager)colaboradorManager.proxy());
	}

	public void testFindAllSelect()
	{
		Collection<Curso> cursos = new ArrayList<Curso>();

		cursoDao.expects(once()).method("findAllSelect").with(eq(1L)).will(returnValue(cursos));
		Collection<Curso> cursosRetorno = cursoManager.findAllSelect(1L);

		assertEquals(cursos, cursosRetorno);
	}

	public void testFindByIdProjection()
	{
		Curso curso = CursoFactory.getEntity(1L);

		cursoDao.expects(once()).method("findByIdProjection").with(eq(curso.getId())).will(returnValue(curso));

		assertEquals(curso , cursoManager.findByIdProjection(curso.getId()));
	}

	public void testGetConteudoProgramatico()
	{
		Curso curso = CursoFactory.getEntity(1L);
		cursoDao.expects(once()).method("getConteudoProgramatico").with(eq(curso.getId())).will(returnValue("teste"));

		assertEquals("teste" , cursoManager.getConteudoProgramatico(curso.getId()));
	}

	public void testFindByFiltro()
	{
		Integer page=1, pagingSize=15;
		Long empresaId=1L;
		Curso curso = CursoFactory.getEntity();
		Collection<Curso> resultado = new ArrayList<Curso>();
		resultado.add(curso);

		cursoDao.expects(once()).method("findByFiltro").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(resultado));
		assertEquals(resultado, cursoManager.findByFiltro(page, pagingSize, curso, empresaId));
	}

	public void testGetCount()
	{
		Long empresaId=1L;
		Curso curso = CursoFactory.getEntity();
		cursoDao.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(0));
		assertEquals(Integer.valueOf(0), cursoManager.getCount(curso, empresaId));
	}

	public void testSomaCargaHoraria() {

		Curso curso1 = CursoFactory.getEntity();
		curso1.setCargaHoraria(null);
		
		Curso curso2 = CursoFactory.getEntity();
		curso2.setCargaHoraria(135);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setCurso(curso1);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setCurso(curso2);
		
		Turma turma3 = TurmaFactory.getEntity();
		turma3.setCurso(curso2);
		
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma1);
		turmas.add(turma2);
		turmas.add(turma3);
		
		assertEquals("4:30", cursoManager.somaCargaHoraria(turmas));
	}
	
	public void testFindSomaCustoEHorasTreinamentos()
	{
		IndicadorTreinamento indicador1 = new IndicadorTreinamento(1L, 60.25);
		IndicadorTreinamento indicador2 = new IndicadorTreinamento(2L, 45.0);
		IndicadorTreinamento indicador3 = new IndicadorTreinamento(3L, 1.56);
		
		Collection<IndicadorTreinamento> indicadoresPorCurso = Arrays.asList(indicador1, indicador2, indicador3);
		
		cursoDao.expects(once()).method("findIndicadorHorasTreinamentos").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(indicadoresPorCurso));
		
		IndicadorTreinamento indicadorTreinamento = cursoManager.findIndicadorHorasTreinamentos(new Date(), new Date(), new Long[] { 1L }, null, null); 
		
		assertEquals(106.81, indicadorTreinamento.getSomaHoras());
	}
}

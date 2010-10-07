package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.CursoManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;

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

	public void testFindHorasPerCapita()
	{

		IndicadorTreinamento indicadorTreinamento = new IndicadorTreinamento();
		indicadorTreinamento.setSomaHoras(80);
		indicadorTreinamento.setQtdColaboradoresInscritos(20);

		colaboradorManager.expects(once()).method("getCountAtivos").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(3000));

		cursoManager.findHorasPerCapita(indicadorTreinamento, new Date(), new Date(), 4L);

		Double calculoHorasPerCapita = ((double)80*20)/3000;
		assertEquals(calculoHorasPerCapita, indicadorTreinamento.getHorasPerCapita());

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

}

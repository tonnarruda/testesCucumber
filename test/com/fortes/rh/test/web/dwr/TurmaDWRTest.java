package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.desenvolvimento.TurmaAvaliacaoTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.test.dao.hibernate.pesquisa.AvaliacaoTurmaFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.TurmaTipoDespesaFactory;
import com.fortes.rh.web.dwr.TurmaDWR;

public class TurmaDWRTest extends MockObjectTestCase
{
	private TurmaDWR turmaDWR;
	private Mock turmaManager;
	private Mock avaliacaoTurmaManager;
	private Mock turmaTipoDespesaManager;
	private Mock turmaAvaliacaoTurmaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		turmaDWR =  new TurmaDWR();

		turmaManager = new Mock(TurmaManager.class);
		turmaDWR.setTurmaManager((TurmaManager) turmaManager.proxy());

		avaliacaoTurmaManager = new Mock(AvaliacaoTurmaManager.class);
		turmaDWR.setAvaliacaoTurmaManager((AvaliacaoTurmaManager) avaliacaoTurmaManager.proxy());

		turmaTipoDespesaManager = new Mock(TurmaTipoDespesaManager.class);
		turmaDWR.setTurmaTipoDespesaManager((TurmaTipoDespesaManager) turmaTipoDespesaManager.proxy());

		turmaAvaliacaoTurmaManager = new Mock(TurmaAvaliacaoTurmaManager.class);
		turmaDWR.setTurmaAvaliacaoTurmaManager((TurmaAvaliacaoTurmaManager) turmaAvaliacaoTurmaManager.proxy());
	}

	public void testGetTurmas()
	{
		Curso curso = CursoFactory.getEntity();
		curso.setId(1L);

		Turma turma1 = TurmaFactory.getEntity();
		turma1.setId(1L);
		turma1.setCurso(curso);

		Turma turma2 = TurmaFactory.getEntity();
		turma2.setId(2L);
		turma2.setCurso(curso);

		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma1);
		turmas.add(turma2);

		turmaManager.expects(once()).method("findToList").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(turmas));

		Map retorno = turmaDWR.getTurmas(curso.getId().toString());

		assertEquals(turmas.size(), retorno.size());
	}

	public void testGetTurmasSemCurso()
	{
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setId(1L);

		Turma turma2 = TurmaFactory.getEntity();
		turma2.setId(2L);

		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma1);
		turmas.add(turma2);

		Map retorno = turmaDWR.getTurmas(null);

		assertNull(retorno);
	}

	public void testGetTurmasFinalizadas()
	{
		Curso curso = CursoFactory.getEntity();
		curso.setId(1L);

		Turma turma1 = TurmaFactory.getEntity();
		turma1.setId(1L);
		turma1.setCurso(curso);

		Turma turma2 = TurmaFactory.getEntity();
		turma2.setId(2L);
		turma2.setCurso(curso);

		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma1);
		turmas.add(turma2);

		turmaManager.expects(once()).method("getTurmaFinalizadas").with(ANYTHING).will(returnValue(turmas));

		Map retorno = turmaDWR.getTurmasFinalizadas(curso.getId().toString());

		assertEquals(turmas.size(), retorno.size());
	}
	
	public void testFindAvaliacaoTurmas()
	{
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setId(1L);

		AvaliacaoTurma av1 = AvaliacaoTurmaFactory.getEntity(111L);
		AvaliacaoTurma av2 = AvaliacaoTurmaFactory.getEntity(222L);
		
		Collection<AvaliacaoTurma> avaliacaoTurmas = Arrays.asList(av1, av2);
		
		avaliacaoTurmaManager.expects(once()).method("findByTurma").with(eq(1L)).will(returnValue(avaliacaoTurmas));

		Collection<AvaliacaoTurma> retorno = turmaDWR.getAvaliacaoTurmas(1L);

		assertEquals(avaliacaoTurmas.size(), retorno.size());
	}
	
	public void testGetTurmasByFiltro() throws Exception
	{
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setId(1L);
		
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma1);
		
		turmaManager.expects(once()).method("findByFiltro").withAnyArguments().will(returnValue(turmas));
		
		Map retorno = turmaDWR.getTurmasByFiltro("01/01/2000", "02/02/2002", 'T', 1L);
		
		assertEquals(turmas.size(), retorno.size());
	}
	
	public void testGetTurmasByFiltroDataInvalida() throws Exception
	{
		Exception exc = null;
		try
		{
			turmaDWR.getTurmasByFiltro("  /01/2000", "02/02/2002", 'T', 1L);			
		}
		catch (Exception e)
		{
			exc = e;
		}

		assertNotNull(exc);
	}
	
	public void testGetTurmasByFiltroVazio() throws Exception
	{
		Collection<Turma> turmas = new ArrayList<Turma>();
		turmaManager.expects(once()).method("findByFiltro").withAnyArguments().will(returnValue(turmas));

		Exception exc = null;
		try
		{
			turmaDWR.getTurmasByFiltro("01/01/2000", "02/02/2002", 'T', 1L);			
		}
		catch (Exception e)
		{
			exc = e;
		}
		
		assertNotNull(exc);
	}

	public void testGetTurmasFinalizadasSemCurso()
	{
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setId(1L);

		Turma turma2 = TurmaFactory.getEntity();
		turma2.setId(2L);

		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma1);
		turmas.add(turma2);

		Map retorno = turmaDWR.getTurmasFinalizadas(null);

		assertNull(retorno);
	}

	public void testGetTurmasFinalizadasSemTurmaFinalizada()
	{
		Curso curso = CursoFactory.getEntity();
		curso.setId(1L);

		Collection<Turma> turmas = new ArrayList<Turma>();

		turmaManager.expects(once()).method("getTurmaFinalizadas").with(ANYTHING).will(returnValue(turmas));

		Map retorno = turmaDWR.getTurmasFinalizadas(curso.getId().toString());

		assertEquals(1, retorno.size());
	}
	
	public void testUpdateRealizada() throws Exception
	{
		Turma turma = TurmaFactory.getEntity(1L);
		boolean realizada = false;
	
		turmaManager.expects(once()).method("updateRealizada").with(eq(turma.getId()), eq(realizada)).isVoid();
		
		assertEquals(false, turmaDWR.updateRealizada(turma.getId(), realizada));
	}
	
	public void testUpdateRealizadaException() throws Exception
	{
		Turma turma = TurmaFactory.getEntity(1L);
		boolean realizada = false;

		turmaManager.expects(once()).method("updateRealizada").with(eq(turma.getId()), eq(realizada)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(turma.getId(),""))));;
		
		Exception ex = null;
		try
		{			
			turmaDWR.updateRealizada(turma.getId(), realizada);
		}
		catch (Exception e)
		{
			ex = e;
		}
		
		assertNotNull(ex);
	}

	public void testGetDespesas()
	{
		Turma turma = TurmaFactory.getEntity(1L);
		
		TurmaTipoDespesa turmaTipoDespesa = TurmaTipoDespesaFactory.getEntity(1L);
		
		Collection<TurmaTipoDespesa> turmaTipoDespesas = Arrays.asList(turmaTipoDespesa);

		turmaTipoDespesaManager.expects(once()).method("findTipoDespesaTurma").with(eq(turma.getId())).will(returnValue(turmaTipoDespesas));

		Collection<TurmaTipoDespesa> retorno = turmaDWR.getDespesas(turma.getId());

		assertEquals(1, retorno.size());
	}
	
    public void testLiberar() 
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setEmpresa(empresa);
    	
    	AvaliacaoTurma avaliacaoTurma = AvaliacaoTurmaFactory.getEntity(1L);
    	
    	turmaAvaliacaoTurmaManager.expects(once()).method("updateLiberada").with(eq(turma.getId()), ANYTHING, ANYTHING, ANYTHING);
    	
    	assertEquals(new Long(1), turmaDWR.liberar(turma.getId(), avaliacaoTurma.getId(), empresa.getId()));
	}
    
    public void testBloquear() 
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setEmpresa(empresa);
    	
    	AvaliacaoTurma avaliacaoTurma = AvaliacaoTurmaFactory.getEntity(1L);
    	
    	turmaAvaliacaoTurmaManager.expects(once()).method("updateLiberada").with(eq(turma.getId()), ANYTHING, ANYTHING, ANYTHING);
    	
    	assertEquals(new Long(1), turmaDWR.bloquear(turma.getId(), avaliacaoTurma.getId(), empresa.getId()));
    }
}

package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

public class AvaliacaoCursoDaoHibernateTest extends GenericDaoHibernateTest<AvaliacaoCurso>
{
	private AvaliacaoCursoDao avaliacaoCursoDao;
	private CursoDao cursoDao;
	private EmpresaDao empresaDao;
	private TurmaDao turmaDao;

	public AvaliacaoCurso getEntity()
	{
		AvaliacaoCurso avaliacaoCurso = new AvaliacaoCurso();

		avaliacaoCurso.setId(null);

		return avaliacaoCurso;
	}
	public GenericDao<AvaliacaoCurso> getGenericDao()
	{
		return avaliacaoCursoDao;
	}

	public void setAvaliacaoCursoDao(AvaliacaoCursoDao AvaliacaoCursoDao)
	{
		this.avaliacaoCursoDao = AvaliacaoCursoDao;
	}
	
	public void setCursoDao(CursoDao cursoDao)
	{
		this.cursoDao = cursoDao;
	}
	public void testFindByCurso()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);
		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso2);
		
		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso);
		avaliacaoCursos.add(avaliacaoCurso2);
		
		Curso curso = CursoFactory.getEntity();
		curso.setEmpresa(empresa);
		curso.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso);
		
		assertEquals(2, avaliacaoCursoDao.findByCurso(curso.getId()).size());
	}
	
	public void testCountAvaliacaoCursos()
	{				
		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso1);
		
		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso2);
		
		Collection<AvaliacaoCurso> avaliacaoCursos1 = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos1.add(avaliacaoCurso1);
		avaliacaoCursos1.add(avaliacaoCurso2);
		
		Curso curso1 = CursoFactory.getEntity();
		curso1.setAvaliacaoCursos(avaliacaoCursos1);
		cursoDao.save(curso1);
		
		Collection<AvaliacaoCurso> avaliacaoCursos2 = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos2.add(avaliacaoCurso2);
		Curso curso2 = CursoFactory.getEntity();
		curso2.setAvaliacaoCursos(avaliacaoCursos2);
		cursoDao.save(curso2);
		
		assertEquals(new Integer(3), avaliacaoCursoDao.countAvaliacoes(new Long[]{curso1.getId(), curso2.getId()}));
	}
	
	public void testCountAvaliacaoWherePorCurso()
	{				
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);
		
		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso);
		
		Curso curso = CursoFactory.getEntity();
		curso.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso);
		
		assertEquals(new Integer(1), avaliacaoCursoDao.countAvaliacoes(curso.getId(), "C"));
	}
	
	public void testCountAvaliacao()
	{				
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);
	
		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso);
		
		Curso curso = CursoFactory.getEntity();
		curso.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		assertEquals(new Integer(1), avaliacaoCursoDao.countAvaliacoes(turma.getId(), "T"));
	}
	
	public void testCountAvaliacaoSemAvaliacao()
	{				
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		assertEquals(new Integer(0), avaliacaoCursoDao.countAvaliacoes(turma.getId(), "T"));
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}
	
	public void testBuscaFiltro()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso.setTitulo("teste1");
		avaliacaoCursoDao.save(avaliacaoCurso);
		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso2.setTitulo("teste2");
		avaliacaoCursoDao.save(avaliacaoCurso2);
		
		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso);
		avaliacaoCursos.add(avaliacaoCurso2);
		
		assertEquals(2, avaliacaoCursoDao.buscaFiltro("test").size());
	}
}
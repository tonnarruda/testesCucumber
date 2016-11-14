package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.json.TurmaJson;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

public class TurmaDaoHibernateTest_JUnit4 extends GenericDaoHibernateTest_JUnit4<Turma>
{
	@Autowired
	private TurmaDao turmaDao;
	@Autowired
	private CursoDao cursoDao;
	@Autowired
	private EmpresaDao empresaDao;
	
	public Turma getEntity()
	{
		return TurmaFactory.getEntity();
	}
	public GenericDao<Turma> getGenericDao()
	{
		return turmaDao;
	}
	
	@Test
	public void testGetTurmasJson(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Curso curso = CursoFactory.getEntity();
		curso.setEmpresa(empresa);
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity(new Date(), new Date(), curso);
		turmaDao.save(turma);
		
		char realizada = 'T'; 
		
		Collection<TurmaJson> turmasJson = turmaDao.getTurmasJson(empresa.getCnpj(), turma.getId(), realizada );
		assertEquals(1, turmasJson.size());
	}
	
	@Test
	public void testGetTurmasJsonCursoCompartilhado(){
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2.setCnpj("01245678");
		empresaDao.save(empresa2);
		
		Curso curso = CursoFactory.getEntity();
		curso.setEmpresasParticipantes(Arrays.asList(empresa2));
		curso.setEmpresa(empresa1);
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity(new Date(), new Date(), curso);
		turma.setRealizada(true);
		turmaDao.save(turma);
		
		Collection<TurmaJson> turmasJson = turmaDao.getTurmasJson(empresa2.getCnpj(), null, 'S');
		assertEquals(1, turmasJson.size());
	}
}
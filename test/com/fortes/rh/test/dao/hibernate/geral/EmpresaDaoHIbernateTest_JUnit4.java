package com.fortes.rh.test.dao.hibernate.geral;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;

public class EmpresaDaoHIbernateTest_JUnit4  extends GenericDaoHibernateTest_JUnit4<Empresa>
{
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	@Autowired
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;

	@Override
	public Empresa getEntity() {
		return new Empresa();
	}

	public GenericDao<Empresa> getGenericDao() {
		return empresaDao;
	}
	
	@Test
	public void testFindDistinctEmpresasByAvaliacaoDesempenho()
	{
	Empresa empresa = EmpresaFactory.getEmpresa();
	Empresa empresa2 = EmpresaFactory.getEmpresa();
	
	empresaDao.save(empresa);
	empresaDao.save(empresa2);

	Colaborador colaborador = ColaboradorFactory.getEntity(1l, empresa);
	Colaborador colaborador2 = ColaboradorFactory.getEntity(2l, empresa2);
	
	colaboradorDao.save(colaborador);
	colaboradorDao.save(colaborador2);
	
	AvaliacaoDesempenho avaliacaoDesempenho= AvaliacaoDesempenhoFactory.getEntity();
	avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
	
	ColaboradorQuestionario colaboradorQuestionario =ColaboradorQuestionarioFactory.getEntity(colaborador,colaborador2,null,avaliacaoDesempenho,Boolean.TRUE);
	colaboradorQuestionarioDao.save(colaboradorQuestionario);
	
	ColaboradorQuestionario colaboradorQuestionario2 =ColaboradorQuestionarioFactory.getEntity(colaborador2,colaborador,null,avaliacaoDesempenho,Boolean.TRUE);
	colaboradorQuestionarioDao.save(colaboradorQuestionario2);
	
	Collection<Empresa> resultado = empresaDao.findDistinctEmpresasByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), new Long[]{empresa.getId(), empresa2.getId()});
	assertEquals(2, resultado.size());
	
	resultado = empresaDao.findDistinctEmpresasByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), new Long[]{empresa.getId()});
	assertEquals(1, resultado.size());
	}
}
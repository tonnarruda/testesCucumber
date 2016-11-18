package com.fortes.rh.test.dao.hibernate.avaliacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorPeriodoExperienciaAvaliacaoFactory;

public class PeriodoExperienciaDaoHibernateTest_Junit4 extends GenericDaoHibernateTest_JUnit4<PeriodoExperiencia>
{
	@Autowired
	private PeriodoExperienciaDao periodoExperienciaDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private AvaliacaoDao avaliacaoDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao;

	public PeriodoExperiencia getEntity()
	{
		return PeriodoExperienciaFactory.getEntity();
	}

	public GenericDao<PeriodoExperiencia> getGenericDao()
	{
		return periodoExperienciaDao;
	}
	
	@Test
	public void testFindPeriodosAtivosAndPeriodoDaAvaliacaoId(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		PeriodoExperiencia experiencia1 = PeriodoExperienciaFactory.getEntity(empresa, 30, false);
		periodoExperienciaDao.save(experiencia1);
		PeriodoExperiencia experiencia2 = PeriodoExperienciaFactory.getEntity(empresa, 50, true);
		periodoExperienciaDao.save(experiencia2);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L, empresa);
		avaliacao.setPeriodoExperiencia(experiencia1);
		avaliacaoDao.save(avaliacao);
		
		Collection<PeriodoExperiencia> periodos = periodoExperienciaDao.findPeriodosAtivosAndPeriodoDaAvaliacaoId(empresa.getId(), avaliacao.getId());
		assertEquals(2, periodos.size());
	}
	
	@Test
	public void testFindPeriodosAtivosAndPeriodoDaAvaliacaoIdComAvaliacaoIdNulo(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		PeriodoExperiencia experiencia1 = PeriodoExperienciaFactory.getEntity(empresa, 30, false);
		periodoExperienciaDao.save(experiencia1);
		PeriodoExperiencia experiencia2 = PeriodoExperienciaFactory.getEntity(empresa, 50, true);
		periodoExperienciaDao.save(experiencia2);
		
		Collection<PeriodoExperiencia> periodos = periodoExperienciaDao.findPeriodosAtivosAndPeriodoDaAvaliacaoId(empresa.getId(), null);
		assertEquals(1, periodos.size());
		assertTrue(periodos.iterator().next().isAtivo());
	}

	@Test
	public void testFindPeriodosAtivosAndPeriodosConfiguradosParaColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		PeriodoExperiencia experiencia1 = PeriodoExperienciaFactory.getEntity(empresa, 30, false);
		periodoExperienciaDao.save(experiencia1);
		PeriodoExperiencia experiencia2 = PeriodoExperienciaFactory.getEntity(empresa, 50, true);
		periodoExperienciaDao.save(experiencia2);

		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L, empresa);
		avaliacao.setPeriodoExperiencia(experiencia1);
		avaliacaoDao.save(avaliacao);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colaborador, experiencia1, avaliacao, 'C');
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colaboradorPeriodoExperienciaAvaliacao);
			
		Collection<PeriodoExperiencia> periodos = periodoExperienciaDao.findPeriodosAtivosAndPeriodosConfiguradosParaColaborador(empresa.getId(), colaborador.getId());
		assertEquals(2, periodos.size());
	}
}

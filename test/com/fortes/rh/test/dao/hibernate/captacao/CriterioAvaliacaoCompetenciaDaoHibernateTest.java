package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.CriterioAvaliacaoCompetenciaDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;

public class CriterioAvaliacaoCompetenciaDaoHibernateTest extends GenericDaoHibernateTest<CriterioAvaliacaoCompetencia>
{
	private CriterioAvaliacaoCompetenciaDao criterioAvaliacaoCompetenciaDao;
	private EmpresaDao empresaDao;
	private ConhecimentoDao conhecimentoDao;
	private HabilidadeDao habilidadeDao;

	public CriterioAvaliacaoCompetencia getEntity()
	{
		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia = new CriterioAvaliacaoCompetencia();
		criterioAvaliacaoCompetencia.setId(null);
		return criterioAvaliacaoCompetencia;
	}
	
	public GenericDao<CriterioAvaliacaoCompetencia> getGenericDao()
	{
		return criterioAvaliacaoCompetenciaDao;
	}

	public void setCriterioAvaliacaoCompetenciaDao(CriterioAvaliacaoCompetenciaDao CriterioAvaliacaoCompetenciaDao)
	{
		this.criterioAvaliacaoCompetenciaDao = CriterioAvaliacaoCompetenciaDao;
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao) {
		this.conhecimentoDao = conhecimentoDao;
	}
	
	public void testFindByCompetencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia1 = new CriterioAvaliacaoCompetencia(null, "Criterio 1");

		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia1);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		criterioAvaliacaoCompetencia1.setConhecimento(conhecimento);
		conhecimento.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetencias);
		conhecimentoDao.save(conhecimento);

		assertEquals(1, criterioAvaliacaoCompetenciaDao.findByCompetencia(conhecimento.getId(), TipoCompetencia.CONHECIMENTO).size());
	}
	
	public void testRemoveByCompetencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia1 = new CriterioAvaliacaoCompetencia(null, "Criterio 1");
		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia2 = new CriterioAvaliacaoCompetencia(null, "Criterio 1");

		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia1);
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia2);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		criterioAvaliacaoCompetencia1.setConhecimento(conhecimento);
		criterioAvaliacaoCompetencia2.setConhecimento(conhecimento);
		conhecimento.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetencias);
		conhecimentoDao.save(conhecimento);
		
		criterioAvaliacaoCompetenciaDao.removeByCompetencia(conhecimento.getId(), TipoCompetencia.CONHECIMENTO, new Long[]{criterioAvaliacaoCompetencia2.getId()});
		
		assertEquals(1, criterioAvaliacaoCompetenciaDao.findByCompetencia(conhecimento.getId(), TipoCompetencia.CONHECIMENTO).size());
	}
	
	public void testExisteCriterioAvaliacaoCompetencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidade.setEmpresa(empresa);
		habilidadeDao.save(habilidade);

		assertFalse(criterioAvaliacaoCompetenciaDao.existeCriterioAvaliacaoCompetencia(empresa.getId()));
		
		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia1 = new CriterioAvaliacaoCompetencia(null, "Criterio 1");
		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia2 = new CriterioAvaliacaoCompetencia(null, "Criterio 2");

		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia1);
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia2);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setEmpresa(empresa);
		criterioAvaliacaoCompetencia1.setConhecimento(conhecimento);
		criterioAvaliacaoCompetencia2.setConhecimento(conhecimento);
		conhecimento.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetencias);
		conhecimentoDao.save(conhecimento);
		
		assertTrue(criterioAvaliacaoCompetenciaDao.existeCriterioAvaliacaoCompetencia(empresa.getId()));
	}

	public void setHabilidadeDao(HabilidadeDao habilidadeDao) {
		this.habilidadeDao = habilidadeDao;
	}
	
}
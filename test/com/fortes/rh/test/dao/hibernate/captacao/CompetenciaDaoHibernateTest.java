package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.dao.captacao.CompetenciaDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.BaseDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;

public class CompetenciaDaoHibernateTest extends BaseDaoHibernateTest
{
	private CompetenciaDao competenciaDao;
	private ConhecimentoDao conhecimentoDao;
	private HabilidadeDao habilidadeDao;
	private AtitudeDao atitudeDao;
	private EmpresaDao empresaDao;

	public Competencia getEntity()
	{
		Competencia competencia = new Competencia();
		competencia.setNome("liderança");
		competencia.setObservacao("teste");
		competencia.setTipo('C');
		return competencia;
	}

	public GenericDao<Competencia> getGenericDao()
	{
		return competenciaDao;
	}

	public void testExisteNome()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresa1 = empresaDao.save(empresa1);
		empresa2 = empresaDao.save(empresa2);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setNome("conhecimento");
		conhecimento.setEmpresa(empresa1);
		conhecimentoDao.save(conhecimento);

		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidade.setNome("habilidade");
		habilidade.setEmpresa(empresa1);
		habilidadeDao.save(habilidade);

		Atitude atitude = AtitudeFactory.getEntity();
		atitude.setNome("atitude");
		atitude.setEmpresa(empresa1);
		atitudeDao.save(atitude);

		Conhecimento conhecimentoNomeHabilidade = ConhecimentoFactory.getConhecimento();
		conhecimentoNomeHabilidade.setNome("habilidade");
		conhecimentoNomeHabilidade.setEmpresa(empresa1);
		conhecimentoDao.save(conhecimentoNomeHabilidade);

		assertTrue(competenciaDao.existeNome("conhecimento", null, null, empresa1.getId()));
		assertFalse(competenciaDao.existeNome("conhecimento", conhecimento.getId(), Competencia.CONHECIMENTO, empresa1.getId()));
		
		assertTrue(competenciaDao.existeNome("habilidade", null, null, empresa1.getId()));
		assertTrue(competenciaDao.existeNome("habilidade", habilidade.getId(), Competencia.HABILIDADE, empresa1.getId()));
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setCompetenciaDao(CompetenciaDao competenciaDao) 
	{
		this.competenciaDao = competenciaDao;
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao) {
		this.conhecimentoDao = conhecimentoDao;
	}

	public void setHabilidadeDao(HabilidadeDao habilidadeDao) {
		this.habilidadeDao = habilidadeDao;
	}

	public void setAtitudeDao(AtitudeDao atitudeDao) {
		this.atitudeDao = atitudeDao;
	}
}

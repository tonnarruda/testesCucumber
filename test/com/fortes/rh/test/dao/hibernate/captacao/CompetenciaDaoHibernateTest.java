package com.fortes.rh.test.dao.hibernate.captacao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;

public class CompetenciaDaoHibernateTest extends DaoHibernateAnnotationTest
{
	@Autowired
	private CompetenciaDao competenciaDao;
	@Autowired
	private ConhecimentoDao conhecimentoDao;
	@Autowired
	private HabilidadeDao habilidadeDao;
	@Autowired
	private AtitudeDao atitudeDao;
	@Autowired
	private EmpresaDao empresaDao;

	@Test
	public void testExisteNome()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresaDao.save(empresa1);
		empresaDao.save(empresa2);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(null, "conhecimento", empresa1);
		conhecimentoDao.save(conhecimento);

		Habilidade habilidade = HabilidadeFactory.getEntity(null, "habilidade", empresa1);
		habilidadeDao.save(habilidade);

		Atitude atitude = AtitudeFactory.getEntity(null, "atitude", empresa1);
		atitudeDao.save(atitude);

		Conhecimento conhecimentoNomeHabilidade = ConhecimentoFactory.getConhecimento(null, "habilidade", empresa1);
		conhecimentoDao.save(conhecimentoNomeHabilidade);

		Assert.assertTrue(competenciaDao.existeNome("conhecimento", null, null, empresa1.getId()));
		Assert.assertFalse(competenciaDao.existeNome("conhecimento", conhecimento.getId(), Competencia.CONHECIMENTO, empresa1.getId()));
		
		Assert.assertTrue(competenciaDao.existeNome("habilidade", null, null, empresa1.getId()));
		Assert.assertTrue(competenciaDao.existeNome("habilidade", habilidade.getId(), Competencia.HABILIDADE, empresa1.getId()));
	}
}

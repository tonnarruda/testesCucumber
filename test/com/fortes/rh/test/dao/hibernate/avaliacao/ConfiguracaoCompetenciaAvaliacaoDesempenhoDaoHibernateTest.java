package com.fortes.rh.test.dao.hibernate.avaliacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoCompetenciaAvaliacaoDesempenho>
{
	private ConfiguracaoCompetenciaAvaliacaoDesempenhoDao configuracaoCompetenciaAvaliacaoDesempenhoDao;
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private ColaboradorDao colaboradorDao;
	private ConhecimentoDao conhecimentoDao;

	@Override
	public ConfiguracaoCompetenciaAvaliacaoDesempenho getEntity()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);		
		
		Colaborador avaliador = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador);
				
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);

		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho.setAvaliacaoDesempenho(avaliacaoDesempenho);
		configuracaoCompetenciaAvaliacaoDesempenho.setAvaliador(avaliador);
		configuracaoCompetenciaAvaliacaoDesempenho.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenho.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoCompetenciaAvaliacaoDesempenho.setCompetenciaId(conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho);
		
		return configuracaoCompetenciaAvaliacaoDesempenho;
	}

	@Override
	public GenericDao<ConfiguracaoCompetenciaAvaliacaoDesempenho> getGenericDao()
	{
		return configuracaoCompetenciaAvaliacaoDesempenhoDao;
	}

	public void testExiste()
	{
		ConfiguracaoCompetenciaAvaliacaoDesempenho configCompetenciaAd = getEntity();
		assertTrue(configuracaoCompetenciaAvaliacaoDesempenhoDao.existe(configCompetenciaAd.getConfiguracaoNivelCompetenciaFaixaSalarial().getId(), configCompetenciaAd.getAvaliacaoDesempenho().getId()));
	}
	
	public void setConfiguracaoCompetenciaAvaliacaoDesempenhoDao(ConfiguracaoCompetenciaAvaliacaoDesempenhoDao configuracaoCompetenciaAvaliacaoDesempenhoDao) {
		this.configuracaoCompetenciaAvaliacaoDesempenhoDao = configuracaoCompetenciaAvaliacaoDesempenhoDao;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialDao(ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao) {
		this.configuracaoNivelCompetenciaFaixaSalarialDao = configuracaoNivelCompetenciaFaixaSalarialDao;
	}

	public void setAvaliacaoDesempenhoDao(AvaliacaoDesempenhoDao avaliacaoDesempenhoDao) {
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao) {
		this.conhecimentoDao = conhecimentoDao;
	}
}

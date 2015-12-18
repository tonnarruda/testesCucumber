package com.fortes.rh.test.dao.hibernate.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoCompetenciaAvaliacaoDesempenho>
{
	private ConfiguracaoCompetenciaAvaliacaoDesempenhoDao configuracaoCompetenciaAvaliacaoDesempenhoDao;
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private ColaboradorDao colaboradorDao;
	private ConhecimentoDao conhecimentoDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;

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
	
	public void testFindColabSemCompetenciaConfiguradaByAvalDesempenhoId()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		Colaborador avaliadorComCompetencia = ColaboradorFactory.getEntity();
		avaliadorComCompetencia.setNome("Avaliador com competencia");
		colaboradorDao.save(avaliadorComCompetencia);

		Colaborador avaliadorSemCompetencia = ColaboradorFactory.getEntity();
		avaliadorSemCompetencia.setNome("Avaliador SEM competencia");
		colaboradorDao.save(avaliadorSemCompetencia);
		
		ColaboradorQuestionario colaboradorQuestionarioAvaliadorComCompetencia = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioAvaliadorComCompetencia.setAvaliador(avaliadorComCompetencia);
		colaboradorQuestionarioAvaliadorComCompetencia.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioAvaliadorComCompetencia);
		
		ColaboradorQuestionario colaboradorQuestionarioAvaliadorSemCompetencia = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioAvaliadorSemCompetencia.setAvaliador(avaliadorSemCompetencia);
		colaboradorQuestionarioAvaliadorSemCompetencia.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioAvaliadorSemCompetencia);

		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);	
		
		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho.setAvaliacaoDesempenho(avaliacaoDesempenho);
		configuracaoCompetenciaAvaliacaoDesempenho.setAvaliador(avaliadorComCompetencia);
		configuracaoCompetenciaAvaliacaoDesempenho.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenho.setCompetenciaId(conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenho.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho);
		
		configuracaoCompetenciaAvaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<Colaborador> colaboradores = configuracaoCompetenciaAvaliacaoDesempenhoDao.findColabSemCompetenciaConfiguradaByAvalDesempenhoId(avaliacaoDesempenho.getId());
		
		assertEquals(1, colaboradores.size());
		assertEquals("Avaliador SEM competencia", ((Colaborador)colaboradores.toArray()[0]).getNome());
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

	public void setColaboradorQuestionarioDao(
			ColaboradorQuestionarioDao colaboradorQuestionarioDao) {
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}
}

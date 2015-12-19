package com.fortes.rh.test.dao.hibernate.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoCompetenciaAvaliacaoDesempenho>
{
	private ConfiguracaoCompetenciaAvaliacaoDesempenhoDao configuracaoCompetenciaAvaliacaoDesempenhoDao;
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private ConhecimentoDao conhecimentoDao;
	private ColaboradorDao colaboradorDao;
	private HabilidadeDao habilidadeDao;

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
	
	public void testFindByAvaliacaoDesempenho()
	{
		AvaliacaoDesempenho avaliacaoDesempenho1 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho1);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);		
		
		Colaborador avaliador = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador);
				
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);

		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho1 = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliacaoDesempenho(avaliacaoDesempenho1);
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliador(avaliador);
		configuracaoCompetenciaAvaliacaoDesempenho1.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenho1.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoCompetenciaAvaliacaoDesempenho1.setCompetenciaId(conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho1);
		
		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);

		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho2 = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho2.setAvaliacaoDesempenho(avaliacaoDesempenho2);
		configuracaoCompetenciaAvaliacaoDesempenho2.setAvaliador(avaliador);
		configuracaoCompetenciaAvaliacaoDesempenho2.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenho2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoCompetenciaAvaliacaoDesempenho2.setCompetenciaId(conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho2);
		
		assertEquals(1, configuracaoCompetenciaAvaliacaoDesempenhoDao.findByAvaliacaoDesempenho(avaliacaoDesempenho1.getId()).size());
	}
	
	public void testFindByAvaliador()
	{
		AvaliacaoDesempenho avaliacaoDesempenho1 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho1);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);		
		
		Colaborador avaliador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador1);

		Colaborador avaliador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador2);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarial.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);

		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho1 = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliacaoDesempenho(avaliacaoDesempenho1);
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliador(avaliador1);
		configuracaoCompetenciaAvaliacaoDesempenho1.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenho1.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoCompetenciaAvaliacaoDesempenho1.setCompetenciaId(conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho1);

		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho2 = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho2.setAvaliacaoDesempenho(avaliacaoDesempenho1);
		configuracaoCompetenciaAvaliacaoDesempenho2.setAvaliador(avaliador2);
		configuracaoCompetenciaAvaliacaoDesempenho2.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenho2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoCompetenciaAvaliacaoDesempenho2.setCompetenciaId(conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho2);

		assertEquals(1, configuracaoCompetenciaAvaliacaoDesempenhoDao.findByAvaliador(avaliador1.getId(), faixaSalarial.getId(), avaliacaoDesempenho1.getId()).size());
	}
	
	public void testReplaceConfiguracaoNivelCompetenciaFaixaSalarial() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);		
		
		Colaborador avaliador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador1);

		Colaborador avaliador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador2);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2015));
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);

		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho1 = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliacaoDesempenho(avaliacaoDesempenho);
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliador(avaliador1);
		configuracaoCompetenciaAvaliacaoDesempenho1.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenho1.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoCompetenciaAvaliacaoDesempenho1.setCompetenciaId(conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho1);

		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial2 = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, DateUtil.criarDataMesAno(2, 1, 2015));
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial2);

		configuracaoCompetenciaAvaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		
		configuracaoCompetenciaAvaliacaoDesempenhoDao.replaceConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial2);
		Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracoesCompetenciaAvaliacaoDesempenho = configuracaoCompetenciaAvaliacaoDesempenhoDao.findByAvaliacaoDesempenho(avaliacaoDesempenho.getId());
		
		assertEquals(configuracaoNivelCompetenciaFaixaSalarial2.getId(), ((ConfiguracaoCompetenciaAvaliacaoDesempenho)configuracoesCompetenciaAvaliacaoDesempenho.toArray()[0]).getConfiguracaoNivelCompetenciaFaixaSalarial().getId());
	}
	
	public void testRemoveByCompetenciasQueNaoPermaneceram() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);		
		
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		habilidadeDao.save(habilidade);
		
		Colaborador avaliador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador1);

		Colaborador avaliador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador2);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2015));
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);

		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho1 = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliacaoDesempenho(avaliacaoDesempenho);
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliador(avaliador1);
		configuracaoCompetenciaAvaliacaoDesempenho1.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenho1.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoCompetenciaAvaliacaoDesempenho1.setCompetenciaId(conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho1);
		
		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho2 = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		configuracaoCompetenciaAvaliacaoDesempenho2.setAvaliador(avaliador1);
		configuracaoCompetenciaAvaliacaoDesempenho2.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenho2.setTipoCompetencia(TipoCompetencia.HABILIDADE);
		configuracaoCompetenciaAvaliacaoDesempenho2.setCompetenciaId(habilidade.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho2);
		
		configuracaoCompetenciaAvaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		configuracaoCompetenciaAvaliacaoDesempenhoDao.removeByCompetenciasQueNaoPermaneceram(new Long[]{conhecimento.getId()}, faixaSalarial.getId(), TipoCompetencia.CONHECIMENTO);
		assertEquals(1, configuracaoCompetenciaAvaliacaoDesempenhoDao.findByAvaliacaoDesempenho(avaliacaoDesempenho.getId()).size());
	}
	
	public void testFindFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);		
		
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		habilidadeDao.save(habilidade);
		
		Colaborador avaliador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador1);

		Colaborador avaliador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador2);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial1.setNome("Faixa I");
		faixaSalarialDao.save(faixaSalarial1);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial2.setNome("Faixa II");
		faixaSalarialDao.save(faixaSalarial2);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial1 = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial1, DateUtil.criarDataMesAno(1, 1, 2015));
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial1);

		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial2 = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial2, DateUtil.criarDataMesAno(2, 1, 2015));
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial2);

		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho1 = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliacaoDesempenho(avaliacaoDesempenho);
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliador(avaliador1);
		configuracaoCompetenciaAvaliacaoDesempenho1.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial1);
		configuracaoCompetenciaAvaliacaoDesempenho1.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoCompetenciaAvaliacaoDesempenho1.setCompetenciaId(conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho1);
		
		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho2 = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		configuracaoCompetenciaAvaliacaoDesempenho2.setAvaliador(avaliador1);
		configuracaoCompetenciaAvaliacaoDesempenho2.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial2);
		configuracaoCompetenciaAvaliacaoDesempenho2.setTipoCompetencia(TipoCompetencia.HABILIDADE);
		configuracaoCompetenciaAvaliacaoDesempenho2.setCompetenciaId(habilidade.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho2);
		
		configuracaoCompetenciaAvaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<FaixaSalarial> faixasSalariais = configuracaoCompetenciaAvaliacaoDesempenhoDao.findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(avaliacaoDesempenho.getId());
		
		assertEquals(2, faixasSalariais.size());
		assertEquals(faixaSalarial1.getId(), ((FaixaSalarial) faixasSalariais.toArray()[0]).getId());
		assertEquals(faixaSalarial2.getId(), ((FaixaSalarial) faixasSalariais.toArray()[1]).getId());
	}
	
	public void testExisteNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(){
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);		
		
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		habilidadeDao.save(habilidade);
		
		Colaborador avaliado = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliado);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial1.setNome("Faixa I");
		faixaSalarialDao.save(faixaSalarial1);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial1 = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial1, DateUtil.criarDataMesAno(1, 1, 2015));
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial1);

		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial2 = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial1, DateUtil.criarDataMesAno(2, 1, 2015));
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial2);

		ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho1 = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliacaoDesempenho(avaliacaoDesempenho);
		configuracaoCompetenciaAvaliacaoDesempenho1.setAvaliador(avaliado);
		configuracaoCompetenciaAvaliacaoDesempenho1.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial1);
		configuracaoCompetenciaAvaliacaoDesempenho1.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoCompetenciaAvaliacaoDesempenho1.setCompetenciaId(conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(configuracaoCompetenciaAvaliacaoDesempenho1);
		
		configuracaoCompetenciaAvaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		
		assertTrue(configuracaoCompetenciaAvaliacaoDesempenhoDao.existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(avaliacaoDesempenho.getId()));
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

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setHabilidadeDao(HabilidadeDao habilidadeDao) {
		this.habilidadeDao = habilidadeDao;
	}
}
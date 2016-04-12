package com.fortes.rh.test.business.captacao;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;

public class ConfiguracaoNivelCompetenciaColaboradorManagerTest extends MockObjectTestCase
{
	private ConfiguracaoNivelCompetenciaColaboradorManagerImpl configuracaoNivelCompetenciaColaboradorManager = new ConfiguracaoNivelCompetenciaColaboradorManagerImpl();
	private Mock configuracaoNivelCompetenciaColaboradorDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configuracaoNivelCompetenciaColaboradorDao = new Mock(ConfiguracaoNivelCompetenciaColaboradorDao.class);
        configuracaoNivelCompetenciaColaboradorManager.setDao((ConfiguracaoNivelCompetenciaColaboradorDao) configuracaoNivelCompetenciaColaboradorDao.proxy());
    }

	public void testFindByIdProjection()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho.setAnonima(true);
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L);
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(colaboradorQuestionario);

		configuracaoNivelCompetenciaColaboradorDao.expects(once()).method("findByIdProjection").with(eq(configuracaoNivelCompetenciaColaborador.getId())).will(returnValue(configuracaoNivelCompetenciaColaborador));
		
		assertEquals(configuracaoNivelCompetenciaColaborador, configuracaoNivelCompetenciaColaboradorManager.findByIdProjection(configuracaoNivelCompetenciaColaborador.getId()));
	}
	
	public void testFindByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho.setAnonima(false);
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
				
		ConfiguracaoNivelCompetenciaColaborador config1 = new ConfiguracaoNivelCompetenciaColaborador();
		config1.setColaborador(colaborador);
		config1.setColaboradorQuestionario(colaboradorQuestionario);

		ConfiguracaoNivelCompetenciaColaborador config2 = new ConfiguracaoNivelCompetenciaColaborador();
		config2.setColaborador(colaborador);
		config2.setColaboradorQuestionario(colaboradorQuestionario);
		
		Collection<ConfiguracaoNivelCompetenciaColaborador> configuracoes = Arrays.asList(config1, config2);
		
		configuracaoNivelCompetenciaColaboradorDao.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(configuracoes));
		
		assertEquals(2, configuracaoNivelCompetenciaColaboradorManager.findByColaborador(colaborador.getId()).size());
	}
}

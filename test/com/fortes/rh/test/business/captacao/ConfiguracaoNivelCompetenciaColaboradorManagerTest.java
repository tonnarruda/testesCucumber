package com.fortes.rh.test.business.captacao;

import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManagerImpl;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;

public class ConfiguracaoNivelCompetenciaColaboradorManagerTest extends MockObjectTestCaseManager<ConfiguracaoNivelCompetenciaColaboradorManagerImpl> implements TesteAutomaticoManager
{
	private Mock configuracaoNivelCompetenciaColaboradorDao;
	private Mock configuracaoNivelCompetenciaManager;
	
	protected void setUp() throws Exception
    {
		super.setUp();

		manager = new ConfiguracaoNivelCompetenciaColaboradorManagerImpl();
		
        configuracaoNivelCompetenciaColaboradorDao = new Mock(ConfiguracaoNivelCompetenciaColaboradorDao.class);
        manager.setDao((ConfiguracaoNivelCompetenciaColaboradorDao) configuracaoNivelCompetenciaColaboradorDao.proxy());
        
        configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
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
		
		assertEquals(configuracaoNivelCompetenciaColaborador, manager.findByIdProjection(configuracaoNivelCompetenciaColaborador.getId()));
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
		
		assertEquals(2, manager.findByColaborador(colaborador.getId()).size());
	}

	public void testDeleteByFaixaSalarial()
	{
		MockSpringUtil.mocks.put("configuracaoNivelCompetenciaManager", configuracaoNivelCompetenciaManager);

		Long[] faixasIds = new Long[] {1L};
		
		configuracaoNivelCompetenciaManager.expects(once()).method("removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixaSalarial").with(eq(faixasIds)).isVoid();
		configuracaoNivelCompetenciaColaboradorDao.expects(once()).method("deleteByFaixaSalarial").isVoid();
		try {
			manager.deleteByFaixaSalarial(faixasIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testExecutaTesteAutomaticoDoManager()
	{
		testeAutomatico(configuracaoNivelCompetenciaColaboradorDao);
	}
}

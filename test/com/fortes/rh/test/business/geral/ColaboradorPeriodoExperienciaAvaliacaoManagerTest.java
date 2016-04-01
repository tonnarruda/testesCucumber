package com.fortes.rh.test.business.geral;

import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;

import com.fortes.rh.business.geral.ColaboradorPeriodoExperienciaAvaliacaoManagerImpl;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

public class ColaboradorPeriodoExperienciaAvaliacaoManagerTest extends MockObjectTestCaseManager<ColaboradorPeriodoExperienciaAvaliacaoManagerImpl> implements TesteAutomaticoManager
{
	Mock colaboradorPeriodoExperienciaAvaliacaoDao = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new ColaboradorPeriodoExperienciaAvaliacaoManagerImpl();

		colaboradorPeriodoExperienciaAvaliacaoDao = new Mock(ColaboradorPeriodoExperienciaAvaliacaoDao.class);
		manager.setDao((ColaboradorPeriodoExperienciaAvaliacaoDao) colaboradorPeriodoExperienciaAvaliacaoDao.proxy());
		
	}

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    }

	public void testSaveConfiguracaoAvaliacaoPeriodoExperiencia()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		ColaboradorPeriodoExperienciaAvaliacao config1 = new ColaboradorPeriodoExperienciaAvaliacao();
		config1.setColaborador(colaborador);
		config1.setPeriodoExperiencia(PeriodoExperienciaFactory.getEntity(1L));
		config1.setAvaliacao(AvaliacaoFactory.getEntity(1L));

		ColaboradorPeriodoExperienciaAvaliacao config2 = new ColaboradorPeriodoExperienciaAvaliacao();
		config2.setColaborador(colaborador);
		config2.setPeriodoExperiencia(PeriodoExperienciaFactory.getEntity(2L));
		config2.setAvaliacao(null);

		ColaboradorPeriodoExperienciaAvaliacao config3 = new ColaboradorPeriodoExperienciaAvaliacao();
		config3.setColaborador(colaborador);
		config3.setPeriodoExperiencia(PeriodoExperienciaFactory.getEntity(3L));
		config3.setAvaliacao(AvaliacaoFactory.getEntity(3L));

		Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes = Arrays.asList(config1, config2, config3);
		
		colaboradorPeriodoExperienciaAvaliacaoDao.expects(atLeastOnce()).method("save").withAnyArguments();

		Exception exception = null;
		try {
			manager.saveConfiguracaoAvaliacaoPeriodoExperiencia(colaborador, colaboradorAvaliacoes, null);
		
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testFindByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		ColaboradorPeriodoExperienciaAvaliacao config1 = new ColaboradorPeriodoExperienciaAvaliacao();
		config1.setColaborador(colaborador);
		config1.setPeriodoExperiencia(PeriodoExperienciaFactory.getEntity(1L));
		config1.setAvaliacao(AvaliacaoFactory.getEntity(1L));
		
		Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes = Arrays.asList(config1);
		colaboradorPeriodoExperienciaAvaliacaoDao.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(colaboradorAvaliacoes));
		
		assertEquals(1, manager.findByColaborador(colaborador.getId()).size());
	}
	
	public void testRemoveConfiguracaoAvaliacaoPeriodoExperiencia()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		colaboradorPeriodoExperienciaAvaliacaoDao.expects(once()).method("removeByColaborador").withAnyArguments();
		manager.removeConfiguracaoAvaliacaoPeriodoExperiencia(colaborador.getId());
	}

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(colaboradorPeriodoExperienciaAvaliacaoDao);
	}
}

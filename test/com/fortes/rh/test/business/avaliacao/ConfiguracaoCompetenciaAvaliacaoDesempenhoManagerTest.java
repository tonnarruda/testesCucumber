package com.fortes.rh.test.business.avaliacao;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerTest extends MockObjectTestCase
{
	private ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl configuracaoCompetenciaAvaliacaoDesempenhoManager = new ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl();
	private Mock configuracaoCompetenciaAvaliacaoDesempenhoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configuracaoCompetenciaAvaliacaoDesempenhoDao = new Mock(ConfiguracaoCompetenciaAvaliacaoDesempenhoDao.class);
        configuracaoCompetenciaAvaliacaoDesempenhoManager.setDao((ConfiguracaoCompetenciaAvaliacaoDesempenhoDao) configuracaoCompetenciaAvaliacaoDesempenhoDao.proxy());
    }

	public void testReajusteByConfiguracaoNivelCompetenciaFaixaSalarial() {
	}
}

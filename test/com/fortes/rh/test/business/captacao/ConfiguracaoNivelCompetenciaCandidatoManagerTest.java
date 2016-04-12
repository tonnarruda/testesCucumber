package com.fortes.rh.test.business.captacao;

import org.jmock.Mock;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaCandidatoManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaCandidatoDao;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;

public class ConfiguracaoNivelCompetenciaCandidatoManagerTest extends MockObjectTestCaseManager<ConfiguracaoNivelCompetenciaCandidatoManagerImpl> implements TesteAutomaticoManager 
{
	private Mock configuracaoNivelCompetenciaCandidatoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        manager = new ConfiguracaoNivelCompetenciaCandidatoManagerImpl();
        configuracaoNivelCompetenciaCandidatoDao = new Mock(ConfiguracaoNivelCompetenciaCandidatoDao.class);
        manager.setDao((ConfiguracaoNivelCompetenciaCandidatoDao) configuracaoNivelCompetenciaCandidatoDao.proxy());
    }

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(configuracaoNivelCompetenciaCandidatoDao);
	}
}

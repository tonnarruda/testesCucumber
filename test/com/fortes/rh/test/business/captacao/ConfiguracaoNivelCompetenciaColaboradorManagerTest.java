package com.fortes.rh.test.business.captacao;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;

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

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradors = ConfiguracaoNivelCompetenciaColaboradorFactory.getCollection(1L);

		configuracaoNivelCompetenciaColaboradorDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(configuracaoNivelCompetenciaColaboradors));
		//assertEquals(configuracaoNivelCompetenciaColaboradors, configuracaoNivelCompetenciaColaboradorManager.findAllSelect(empresaId));
	}
}

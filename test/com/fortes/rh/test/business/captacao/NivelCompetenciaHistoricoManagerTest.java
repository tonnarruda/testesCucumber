package com.fortes.rh.test.business.captacao;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.NivelCompetenciaHistoricoManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;

public class NivelCompetenciaHistoricoManagerTest extends MockObjectTestCase
{
	private NivelCompetenciaHistoricoManagerImpl nivelCompetenciaHistoricoManager = new NivelCompetenciaHistoricoManagerImpl();
	private Mock nivelCompetenciaHistoricoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        nivelCompetenciaHistoricoDao = new Mock(NivelCompetenciaHistoricoDao.class);
        nivelCompetenciaHistoricoManager.setDao((NivelCompetenciaHistoricoDao) nivelCompetenciaHistoricoDao.proxy());
    }

}

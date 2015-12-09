package com.fortes.rh.test.business.captacao;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.NivelCompetenciaManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;

public class NivelCompetenciaManagerTest extends MockObjectTestCase
{
	private NivelCompetenciaManagerImpl nivelCompetenciaManager = new NivelCompetenciaManagerImpl();
	private Mock nivelCompetenciaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        nivelCompetenciaDao = new Mock(NivelCompetenciaDao.class);
        nivelCompetenciaManager.setDao((NivelCompetenciaDao) nivelCompetenciaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<NivelCompetencia> nivelCompetencias = NivelCompetenciaFactory.getCollection(1L);

		nivelCompetenciaDao.expects(once()).method("findAllSelect").with(eq(empresaId), ANYTHING, ANYTHING).will(returnValue(nivelCompetencias));
		
		assertEquals(nivelCompetencias, nivelCompetenciaManager.findAllSelect(empresaId, null, null));
	}

	public void testValidaLimite()
	{
		Exception e = null;
		Long empresaId = 1L;
		
		Collection<NivelCompetencia> nivelCompetencias = NivelCompetenciaFactory.getCollection(1L);
		nivelCompetenciaDao.expects(once()).method("findAllSelect").with(eq(empresaId), ANYTHING, ANYTHING).will(returnValue(nivelCompetencias));
		
		try {
			nivelCompetenciaManager.validaLimite(empresaId);
		} catch (Exception ex) {
			e = ex;
		}
		
		assertNull(e);
	}

	public void testValidaLimiteExcedido()
	{
		Exception e = null;
		Long empresaId = 1L;
		
		Collection<NivelCompetencia> nivelCompetencias = Arrays.asList(NivelCompetenciaFactory.getEntity(),NivelCompetenciaFactory.getEntity(),NivelCompetenciaFactory.getEntity(),NivelCompetenciaFactory.getEntity(),NivelCompetenciaFactory.getEntity(),NivelCompetenciaFactory.getEntity(),NivelCompetenciaFactory.getEntity(),NivelCompetenciaFactory.getEntity(),NivelCompetenciaFactory.getEntity(),NivelCompetenciaFactory.getEntity());
		nivelCompetenciaDao.expects(once()).method("findAllSelect").with(eq(empresaId), ANYTHING, ANYTHING).will(returnValue(nivelCompetencias));
		
		try {
			nivelCompetenciaManager.validaLimite(empresaId);
		} catch (Exception ex) {
			e = ex;
		}
		
		assertNotNull(e);
	}

	public void testGetPontuacaoObtidaByConfiguracoesNiveisCompetencia()
	{
		NivelCompetencia nivelCompetencia1 = NivelCompetenciaFactory.getEntity(1L);
		nivelCompetencia1.setOrdem(4);
		
		NivelCompetencia nivelCompetencia2 = NivelCompetenciaFactory.getEntity(2L);
		nivelCompetencia2.setOrdem(3);
		
		ConfiguracaoNivelCompetencia configNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configNivelCompetencia1.setNivelCompetencia(nivelCompetencia1);

		ConfiguracaoNivelCompetencia configNivelCompetencia2 = new ConfiguracaoNivelCompetencia();
		configNivelCompetencia2.setNivelCompetencia(nivelCompetencia2);
		
		Collection<ConfiguracaoNivelCompetencia> confgniveisCompetencia = Arrays.asList(configNivelCompetencia1, configNivelCompetencia2);
		
		assertEquals((Integer) 7, nivelCompetenciaManager.getPontuacaoObtidaByConfiguracoesNiveisCompetencia(confgniveisCompetencia));
	}

}

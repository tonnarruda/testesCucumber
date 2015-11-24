package com.fortes.rh.test.business.captacao;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ConfigHistoricoNivelManagerImpl;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;

public class ConfigHistoricoNivelManagerTest extends MockObjectTestCase
{
	private ConfigHistoricoNivelManagerImpl configHistoricoNivelManager = new ConfigHistoricoNivelManagerImpl();
	private Mock nivelCompetenciaManager;
	private Mock configHistoricoNivelDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configHistoricoNivelDao = new Mock(ConfigHistoricoNivelDao.class);
        nivelCompetenciaManager = new Mock(NivelCompetenciaManager.class);
        configHistoricoNivelManager.setDao((ConfigHistoricoNivelDao) configHistoricoNivelDao.proxy());
        configHistoricoNivelManager.setNivelCompetenciaManager((NivelCompetenciaManager) nivelCompetenciaManager.proxy());
    }
	
	public void testFindNiveisCompetenciaByEmpresa(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		NivelCompetencia nivelCompetencia1  = NivelCompetenciaFactory.getEntity(1L);
		NivelCompetencia nivelCompetencia2  = NivelCompetenciaFactory.getEntity(2L);
		
		Collection<NivelCompetencia> niveisCompetencia = Arrays.asList(nivelCompetencia1, nivelCompetencia2);
		nivelCompetenciaManager.expects(once()).method("find").will(returnValue(niveisCompetencia));
		
		assertNotNull(configHistoricoNivelManager.findNiveisCompetenciaByEmpresa(empresa.getId()));
	}

}

package com.fortes.rh.test.business.captacao;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.NivelCompetenciaFaixaSalarialManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class NivelCompetenciaFaixaSalarialManagerTest extends MockObjectTestCase
{
	private NivelCompetenciaFaixaSalarialManagerImpl nivelCompetenciaFaixaSalarialManager = new NivelCompetenciaFaixaSalarialManagerImpl();
	private Mock nivelCompetenciaFaixaSalarialDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        nivelCompetenciaFaixaSalarialDao = new Mock(NivelCompetenciaFaixaSalarialDao.class);
        nivelCompetenciaFaixaSalarialManager.setDao((NivelCompetenciaFaixaSalarialDao) nivelCompetenciaFaixaSalarialDao.proxy());
    }

	public void testSaveByFaixa()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		
		NivelCompetenciaFaixaSalarial nivelCompetenciaFaixaSalarial1 = new NivelCompetenciaFaixaSalarial();
		nivelCompetenciaFaixaSalarial1.setFaixaSalarial(faixaSalarial);
		nivelCompetenciaFaixaSalarial1.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaFaixaSalarial1.setCompetenciaId(atitude.getId());
		nivelCompetenciaFaixaSalarial1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		NivelCompetenciaFaixaSalarial nivelCompetenciaSemCompetenciaId = new NivelCompetenciaFaixaSalarial();
		nivelCompetenciaSemCompetenciaId.setFaixaSalarial(faixaSalarial);
		nivelCompetenciaSemCompetenciaId.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaSemCompetenciaId.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);

		Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaFaixaSalariais = Arrays.asList(nivelCompetenciaFaixaSalarial1, nivelCompetenciaSemCompetenciaId);
		
		nivelCompetenciaFaixaSalarialDao.expects(once()).method("deleteConfiguracaoByFaixa").with(eq(faixaSalarial.getId())).isVoid();
		//so passa uma vez no save
		nivelCompetenciaFaixaSalarialDao.expects(once()).method("save").with(ANYTHING).isVoid();
	
		nivelCompetenciaFaixaSalarialManager.saveByFaixa(niveisCompetenciaFaixaSalariais, faixaSalarial.getId());
	}
}

package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.NivelCompetenciaFaixaSalarialManagerImpl;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.dao.captacao.NivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class NivelCompetenciaFaixaSalarialManagerTest extends MockObjectTestCase
{
	private NivelCompetenciaFaixaSalarialManagerImpl nivelCompetenciaFaixaSalarialManager = new NivelCompetenciaFaixaSalarialManagerImpl();
	private Mock nivelCompetenciaFaixaSalarialDao;
	private Mock nivelCompetenciaManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        nivelCompetenciaFaixaSalarialDao = new Mock(NivelCompetenciaFaixaSalarialDao.class);
        nivelCompetenciaFaixaSalarialManager.setDao((NivelCompetenciaFaixaSalarialDao) nivelCompetenciaFaixaSalarialDao.proxy());

        nivelCompetenciaManager = new Mock(NivelCompetenciaManager.class);
        nivelCompetenciaFaixaSalarialManager.setNivelCompetenciaManager((NivelCompetenciaManager) nivelCompetenciaManager.proxy());
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
	
		nivelCompetenciaFaixaSalarialManager.saveCompetencias(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), null);
	}
	
	public void testGetCompetenciasCandidato()
	{
		NivelCompetenciaFaixaSalarial nivelConhecimento = new NivelCompetenciaFaixaSalarial(TipoCompetencia.CONHECIMENTO, 1L, null);
		NivelCompetenciaFaixaSalarial nivelAtitude = new NivelCompetenciaFaixaSalarial(TipoCompetencia.ATITUDE, 3L, null);

		Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaCandidato = Arrays.asList(nivelConhecimento, nivelAtitude);

		nivelConhecimento.setCompetenciaDescricao("java");
		nivelAtitude.setCompetenciaDescricao("proatividade");
		NivelCompetenciaFaixaSalarial nivelHabilidade = new NivelCompetenciaFaixaSalarial(TipoCompetencia.HABILIDADE, 2L, "comunicacao");
		
		Collection<NivelCompetenciaFaixaSalarial> niveisCompetencia = Arrays.asList(nivelConhecimento, nivelHabilidade, nivelAtitude);

		nivelCompetenciaFaixaSalarialDao.expects(once()).method("findByCandidato").with(ANYTHING).will(returnValue(niveisCompetenciaCandidato));
		nivelCompetenciaManager.expects(once()).method("findByCargoOrEmpresa").with(ANYTHING,ANYTHING).will(returnValue(niveisCompetencia));
		
		Collection<NivelCompetenciaFaixaSalarial> niveisComDescricao = nivelCompetenciaFaixaSalarialManager.getCompetenciasCandidato(1L, 1L); 
		
		assertEquals(2, niveisComDescricao.size());
		assertEquals("java", ((NivelCompetenciaFaixaSalarial)niveisComDescricao.toArray()[0]).getCompetenciaDescricao());
		assertEquals("proatividade", ((NivelCompetenciaFaixaSalarial)niveisComDescricao.toArray()[1]).getCompetenciaDescricao());
	}
}

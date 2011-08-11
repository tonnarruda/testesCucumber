package com.fortes.rh.test.business.captacao;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManagerImpl;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class ConfiguracaoNivelCompetenciaManagerTest extends MockObjectTestCase
{
	private ConfiguracaoNivelCompetenciaManagerImpl configuracaoNivelCompetenciaManager = new ConfiguracaoNivelCompetenciaManagerImpl();
	private Mock configuracaoNivelCompetenciaDao;
	private Mock nivelCompetenciaManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configuracaoNivelCompetenciaDao = new Mock(ConfiguracaoNivelCompetenciaDao.class);
        configuracaoNivelCompetenciaManager.setDao((ConfiguracaoNivelCompetenciaDao) configuracaoNivelCompetenciaDao.proxy());

        nivelCompetenciaManager = new Mock(NivelCompetenciaManager.class);
        configuracaoNivelCompetenciaManager.setNivelCompetenciaManager((NivelCompetenciaManager) nivelCompetenciaManager.proxy());
    }

	public void testSaveByFaixa()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaSemCompetenciaId = new ConfiguracaoNivelCompetencia();
		nivelCompetenciaSemCompetenciaId.setFaixaSalarial(faixaSalarial);
		nivelCompetenciaSemCompetenciaId.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaSemCompetenciaId.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);

		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = Arrays.asList(configuracaoNivelCompetencia1, nivelCompetenciaSemCompetenciaId);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("deleteConfiguracaoByFaixa").with(eq(faixaSalarial.getId())).isVoid();
		//so passa uma vez no save
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
	
		configuracaoNivelCompetenciaManager.saveCompetencias(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), null);
	}
	
	public void testSaveCompetenciasColaborador()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaSemCompetenciaId = new ConfiguracaoNivelCompetencia();
		nivelCompetenciaSemCompetenciaId.setFaixaSalarial(faixaSalarial);
		nivelCompetenciaSemCompetenciaId.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaSemCompetenciaId.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = Arrays.asList(configuracaoNivelCompetencia1, nivelCompetenciaSemCompetenciaId);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("deleteConfiguracaoByFaixa").with(eq(faixaSalarial.getId())).isVoid();
		//so passa uma vez no save
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		
		configuracaoNivelCompetenciaManager.saveCompetencias(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), null);
	}
	
	public void testGetCompetenciasCandidato()
	{
		ConfiguracaoNivelCompetencia nivelConhecimento = new ConfiguracaoNivelCompetencia(TipoCompetencia.CONHECIMENTO, 1L, null);
		ConfiguracaoNivelCompetencia nivelAtitude = new ConfiguracaoNivelCompetencia(TipoCompetencia.ATITUDE, 3L, null);

		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaCandidato = Arrays.asList(nivelConhecimento, nivelAtitude);

		nivelConhecimento.setCompetenciaDescricao("java");
		nivelAtitude.setCompetenciaDescricao("proatividade");
		ConfiguracaoNivelCompetencia nivelHabilidade = new ConfiguracaoNivelCompetencia(TipoCompetencia.HABILIDADE, 2L, "comunicacao");
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetencia = Arrays.asList(nivelConhecimento, nivelHabilidade, nivelAtitude);

		configuracaoNivelCompetenciaDao.expects(once()).method("findByCandidato").with(ANYTHING).will(returnValue(niveisCompetenciaCandidato));
		nivelCompetenciaManager.expects(once()).method("findByCargoOrEmpresa").with(ANYTHING,ANYTHING).will(returnValue(niveisCompetencia));
		
		Collection<ConfiguracaoNivelCompetencia> niveisComDescricao = configuracaoNivelCompetenciaManager.getCompetenciasCandidato(1L, 1L); 
		
		assertEquals(2, niveisComDescricao.size());
		assertEquals("java", ((ConfiguracaoNivelCompetencia)niveisComDescricao.toArray()[0]).getCompetenciaDescricao());
		assertEquals("proatividade", ((ConfiguracaoNivelCompetencia)niveisComDescricao.toArray()[1]).getCompetenciaDescricao());
	}
	
	public void testFindColaboradorAbaixoNivel()
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia("faixa1", "bom", 5, "Joao", "Ruim", 2);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = new ConfiguracaoNivelCompetencia("faixa2", "medio", 2, "Pedro", "otimo", 5);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias =  Arrays.asList(configuracaoNivelCompetencia1, configuracaoNivelCompetencia2);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaColaborador").with(ANYTHING).will(returnValue(configuracaoNivelCompetencias));
		
		assertEquals(1, configuracaoNivelCompetenciaManager.findColaboradorAbaixoNivel(new Long[]{1L, 2L}).size());
	}
}

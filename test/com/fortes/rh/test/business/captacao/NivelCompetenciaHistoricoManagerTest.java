package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.ConfigHistoricoNivelManager;
import com.fortes.rh.business.captacao.NivelCompetenciaHistoricoManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class NivelCompetenciaHistoricoManagerTest
{
	private NivelCompetenciaHistoricoManagerImpl nivelCompetenciaHistoricoManagerImpl = new NivelCompetenciaHistoricoManagerImpl();
	private NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao;
	private ConfigHistoricoNivelManager configHistoricoNivelManager;

	@Before
	public void setUp() throws Exception
	{
		nivelCompetenciaHistoricoDao = mock(NivelCompetenciaHistoricoDao.class);
		nivelCompetenciaHistoricoManagerImpl.setDao(nivelCompetenciaHistoricoDao);
		
		configHistoricoNivelManager = mock(ConfigHistoricoNivelManager.class);
		nivelCompetenciaHistoricoManagerImpl.setConfigHistoricoNivelManager(configHistoricoNivelManager);
	}

	@Test
	public void testRemoveNivelConfiguracaoHistorico() throws FortesException{
		when(nivelCompetenciaHistoricoDao.dependenciaComCompetenciasDaFaixaSalarial(1L)).thenReturn(new ArrayList<ConfiguracaoNivelCompetenciaFaixaSalarial>());

		nivelCompetenciaHistoricoManagerImpl.removeNivelConfiguracaoHistorico(1L);
		
		verify(configHistoricoNivelManager).removeByNivelConfiguracaoHistorico(1L);
		verify(nivelCompetenciaHistoricoDao).remove(1L);
	}
	
	@Test
	public void testRemoveNivelConfiguracaoHistoricoException(){
		Cargo cargo = CargoFactory.getEntity(1L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
		configuracaoNivelCompetenciaFaixaSalarial.setFaixaSalarial(faixaSalarial);
		
		Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> configNivelCompetenciaFaixaSalariais = new ArrayList<ConfiguracaoNivelCompetenciaFaixaSalarial>();
		configNivelCompetenciaFaixaSalariais.add(configuracaoNivelCompetenciaFaixaSalarial);
		when(nivelCompetenciaHistoricoDao.dependenciaComCompetenciasDaFaixaSalarial(1L)).thenReturn(configNivelCompetenciaFaixaSalariais);
		
		try {
			nivelCompetenciaHistoricoManagerImpl.removeNivelConfiguracaoHistorico(1L);
			fail("Era para ter entrado na exceção");
		} catch (FortesException e) {
			assertEquals("Este histórico dos níveis de competência não pode ser excluído, pois existem dependências</br> com as configuraçõe de competências das faixas salariais abaixo.</br>nome faixa", e.getMessage()); 
		}
	}
}
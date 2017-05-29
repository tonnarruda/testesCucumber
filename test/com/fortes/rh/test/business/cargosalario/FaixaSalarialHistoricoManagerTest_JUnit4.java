package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManagerImpl;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.SpringUtil;

public class FaixaSalarialHistoricoManagerTest_JUnit4
{
	FaixaSalarialHistoricoManagerImpl faixaSalarialHistoricoManager = null;
	FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;
	FaixaSalarialManager faixaSalarialManager;
	IndiceHistoricoManager indiceHistoricoManager;
	IndiceManager indiceManager;

	@Before
	public void setUp() throws Exception
	{
		faixaSalarialHistoricoManager = new FaixaSalarialHistoricoManagerImpl();

		faixaSalarialHistoricoDao = mock(FaixaSalarialHistoricoDao.class);
		faixaSalarialHistoricoManager.setDao(faixaSalarialHistoricoDao);

		faixaSalarialManager = mock(FaixaSalarialManager.class);
		MockSpringUtilJUnit4.mocks.put("faixaSalarialManager", faixaSalarialManager);

		indiceHistoricoManager = mock(IndiceHistoricoManager.class);
		faixaSalarialHistoricoManager.setIndiceHistoricoManager(indiceHistoricoManager);

		indiceManager = mock(IndiceManager.class);
		faixaSalarialHistoricoManager.setIndiceManager(indiceManager);

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
	}

	@Test
	public void testSincronizar()
	{
		Map<Long, Long> faixaSalarialIds = new HashMap<Long, Long>();
		faixaSalarialIds.put(99L, 130L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(99L);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1000L);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		FaixaSalarial faixaSalarialDestino = FaixaSalarialFactory.getEntity(130L);
		
		FaixaSalarialHistorico faixaSalarialHistoricoDepoisDoSave = FaixaSalarialHistoricoFactory.getEntity(2000L);
		faixaSalarialHistoricoDepoisDoSave.setFaixaSalarial(faixaSalarialDestino);
		
		when(faixaSalarialHistoricoDao.findHistoricoAtual(99L)).thenReturn(faixaSalarialHistorico);
		FaixaSalarialHistorico faiSalarialHistorico = faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), faixaSalarialDestino.getId(), EmpresaFactory.getEmpresa());
		
		assertNotNull(faiSalarialHistorico);
		
		verify(faixaSalarialHistoricoDao).save(faixaSalarialHistorico);
	}
	
	@Test
	public void testSincronizarException()
	{
		Map<Long, Long> faixaSalarialIds = new HashMap<Long, Long>();
		faixaSalarialIds.put(99L, 130L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(99L);
		
		FaixaSalarial faixaSalarialDestino = FaixaSalarialFactory.getEntity(130L);
		
		when(faixaSalarialHistoricoDao.findHistoricoAtual(99L)).thenReturn(null);
		
		FaixaSalarialHistorico faiSalarialHistorico = faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), faixaSalarialDestino.getId(), EmpresaFactory.getEmpresa());
		
		assertNull(faiSalarialHistorico);
		
		verify(faixaSalarialHistoricoDao,  never()).save(null);
	}
}
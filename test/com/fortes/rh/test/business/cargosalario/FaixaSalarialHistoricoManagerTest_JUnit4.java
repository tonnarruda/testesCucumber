package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import sun.misc.FpUtils;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManagerImpl;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.mchange.util.AssertException;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(SpringUtil.class)
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

		indiceHistoricoManager = mock(IndiceHistoricoManager.class);
		faixaSalarialHistoricoManager.setIndiceHistoricoManager(indiceHistoricoManager);

		indiceManager = mock(IndiceManager.class);
		faixaSalarialHistoricoManager.setIndiceManager(indiceManager);

//    	PowerMockito.mockStatic(SpringUtil.class);
//    	BDDMockito.given(SpringUtil.getBean("faixaSalarialManager")).willReturn(faixaSalarialManager);
	}

	@Test
	public void testSincronizarFortesException() 
	{
	    Empresa empresaDestino = EmpresaFactory.getEmpresa(1L, "Empresa Destino", "0002", "0002");
        empresaDestino.setAcIntegra(true);
        
        Indice indice = IndiceFactory.getEntity(2L);
        
	    Map<Long, Long> faixaSalarialIds = new HashMap<Long, Long>();
		faixaSalarialIds.put(99L, 130L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(99L);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1000L);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico.setIndice(indice);
		FaixaSalarial faixaSalarialDestino = FaixaSalarialFactory.getEntity(130L);
		
		FaixaSalarialHistorico faixaSalarialHistoricoDepoisDoSave = FaixaSalarialHistoricoFactory.getEntity(2000L);
		faixaSalarialHistoricoDepoisDoSave.setFaixaSalarial(faixaSalarialDestino);
		
		when(faixaSalarialHistoricoDao.findHistoricoAtual(99L)).thenReturn(faixaSalarialHistorico);
		FortesException fortesException = null;
        try {
            faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), faixaSalarialDestino.getId(), empresaDestino, "0001");
        } catch (FortesException e) {
            fortesException = e;
        }
		
		assertNotNull(fortesException);
		assertEquals("Entre empresas com Grupo AC diferentes não é possível importar cargos que possuem situação por indíce.", fortesException.getMessage());	
	}
	
	
	@Test
    public void testSincronizar() 
    {
        Empresa empresaDestino = EmpresaFactory.getEmpresa(1L, "Empresa Destino", "0002", "0001");
        empresaDestino.setAcIntegra(true);
        
        Indice indice = IndiceFactory.getEntity(2L);
        
        Map<Long, Long> faixaSalarialIds = new HashMap<Long, Long>();
        faixaSalarialIds.put(99L, 130L);
        
        FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(99L);

        FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1000L);
        faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
        faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
        faixaSalarialHistorico.setIndice(indice);
        FaixaSalarial faixaSalarialDestino = FaixaSalarialFactory.getEntity(130L);
        
        FaixaSalarialHistorico faixaSalarialHistoricoDepoisDoSave = FaixaSalarialHistoricoFactory.getEntity(2000L);
        faixaSalarialHistoricoDepoisDoSave.setFaixaSalarial(faixaSalarialDestino);
        
        when(faixaSalarialHistoricoDao.findHistoricoAtual(99L)).thenReturn(faixaSalarialHistorico);
        FaixaSalarialHistorico faiSalarialHistorico = null;
        FortesException fortesException = null;
        try {
            faiSalarialHistorico = faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), faixaSalarialDestino.getId(), empresaDestino, "0001");
        } catch (FortesException e) {
            fortesException = e;
        }
        
        assertNotNull(faiSalarialHistorico);
        assertNull(fortesException);
        verify(faixaSalarialHistoricoDao).save(faixaSalarialHistorico);
    }
	
	
	@Test
	public void testSincronizarException() throws FortesException
	{
		Map<Long, Long> faixaSalarialIds = new HashMap<Long, Long>();
		faixaSalarialIds.put(99L, 130L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(99L);
		FaixaSalarial faixaSalarialDestino = FaixaSalarialFactory.getEntity(130L);
		
		when(faixaSalarialHistoricoDao.findHistoricoAtual(99L)).thenReturn(null);
		
		FaixaSalarialHistorico faiSalarialHistorico = faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), faixaSalarialDestino.getId(), EmpresaFactory.getEmpresa(), "0001");
		
		assertNull(faiSalarialHistorico);
		
		verify(faixaSalarialHistoricoDao,  never()).save(null);
	}
}
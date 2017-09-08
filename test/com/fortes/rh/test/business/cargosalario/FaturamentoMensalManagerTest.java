package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.cargosalario.FaturamentoMensalManagerImpl;
import com.fortes.rh.dao.cargosalario.FaturamentoMensalDao;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.test.factory.cargosalario.FaturamentoMensalFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class FaturamentoMensalManagerTest 
{
	private FaturamentoMensalManagerImpl faturamentoMensalManager;
	private FaturamentoMensalDao faturamentoMensalDao;
	
	@Before
	public void setUp() throws Exception
    {
        faturamentoMensalDao = mock(FaturamentoMensalDao.class);
        
        faturamentoMensalManager= new FaturamentoMensalManagerImpl();
        faturamentoMensalManager.setDao(faturamentoMensalDao);
        PowerMockito.mockStatic(SpringUtil.class);
    }

	@Test
	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<FaturamentoMensal> faturamentoMensals = FaturamentoMensalFactory.getCollection(empresaId);

		when(faturamentoMensalDao.findAllSelect(empresaId)).thenReturn(faturamentoMensals);
		assertEquals(faturamentoMensals, faturamentoMensalManager.findAllSelect(empresaId));
	}
	@Test
	public void testFindByPeriodo()
	{
		Date inicio = DateUtil.criarDataMesAno(1, 2, 2000);
		Date fim = DateUtil.criarDataMesAno(1, 11, 2000);
		Long empresaId = 1L;
		
		FaturamentoMensal janeiro = FaturamentoMensalFactory.getEntity(1L);
		janeiro.setMesAno(inicio);
		janeiro.setValor(1000000.0);
		
		Collection<FaturamentoMensal> faturamentoMensals = Arrays.asList(janeiro);
		
		when(faturamentoMensalDao.findByPeriodo(inicio,fim,empresaId,null)).thenReturn(faturamentoMensals);
		when(faturamentoMensalDao.findAtual(inicio,empresaId,null)).thenReturn(null);
		
		assertEquals(10, faturamentoMensalManager.findByPeriodo(inicio, fim, empresaId, null).size());
	}
}

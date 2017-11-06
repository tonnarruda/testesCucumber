package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.RiscoManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;

public class RiscoManagerTest 
{
	private RiscoManagerImpl riscoManager = new RiscoManagerImpl();
	private RiscoDao riscoDao = null;

	@Before
    public void setUp() throws Exception
    {
        riscoDao = mock(RiscoDao.class);
        riscoManager.setDao(riscoDao);
    }

	@Test
	public void testFindEpisByRisco()throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		Epi f1 = EpiFactory.getEntity(1L, "nome1", empresa);
		Epi f2 = EpiFactory.getEntity(2L, "nome2", empresa);

		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(f1);
		epis.add(f2);

		Risco risco = RiscoFactory.getEntity(1L);

		List<Object> lista = new ArrayList<Object>();
		Object[] ob1 = new Object[]{1L,"nome1"};
		Object[] ob2 = new Object[]{2L,"nome2"};

		lista.add(ob1);
		lista.add(ob2);

		when(riscoDao.findEpisByRisco(eq(risco.getId()))).thenReturn(lista);
		Collection<Epi> episTmps = riscoManager.findEpisByRisco(risco.getId());
		assertEquals(episTmps, epis);
	}
	
	@Test
	public void testSincronizar()throws Exception
	{
		Empresa empresaOrigem = EmpresaFactory.getEmpresa(1L);
		Empresa empresaDestino = EmpresaFactory.getEmpresa(2L);
		
		Epi epi1 = EpiFactory.getEntity(1L, "Epi1");
		Epi epi2 = EpiFactory.getEntity(2L, "Epi2");

		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		epis.add(epi2);
		
		Risco risco = new Risco();
		risco.setDescricao("risco 1");
		risco.setEmpresa(empresaOrigem);
		risco.setEpis(epis);
		risco.setId(1L);
		
		Map<Long, Long> epiIds = new  HashMap<Long, Long>();
		epiIds.put(epi1.getId(), 7777777777L);
		epiIds.put(epi2.getId(), 8888888888L);
		
		Collection<Risco> riscos = new ArrayList<Risco>();
		riscos.add(risco);
		
		when(riscoDao.findAllSelect(eq(empresaOrigem.getId()))).thenReturn(riscos);

		Exception exception = null;
		try {
			riscoManager.sincronizar(empresaOrigem.getId(), empresaDestino.getId(), epiIds);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	@Test
	public void testFindEpisByRiscoException()
	{
		Long riscoId = 1L;
		doThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))).when(riscoDao).findEpisByRisco(riscoId);
		assertEquals(0,riscoManager.findEpisByRisco(1L).size());
	}
	
	@Test
	public void testListRiscos() throws ColecaoVaziaException{
		int page = 0;
		int pagingSize = 0;
		Risco risco = RiscoFactory.getEntity(1L);
		
		when(riscoDao.listRiscos(page, pagingSize, risco)).thenReturn(Arrays.asList(RiscoFactory.getEntity()));
		
		Collection<Risco> riscos = riscoManager.listRiscos(page, pagingSize, risco);
		verify(riscoDao, times(1)).listRiscos(page, pagingSize, risco);
		assertEquals(1, riscos.size());
	}
	
	@Test(expected=ColecaoVaziaException.class)
	public void testListRiscosException() throws ColecaoVaziaException{
		int page = 0;
		int pagingSize = 0;
		Risco risco = RiscoFactory.getEntity(1L);
		
		when(riscoDao.listRiscos(page, pagingSize, risco)).thenReturn(new ArrayList<Risco>());
		riscoManager.listRiscos(page, pagingSize, risco);
		verify(riscoDao, times(1)).listRiscos(page, pagingSize, risco);
	}
	
	
	@Test
	public void testGetCount()
	{
		Risco risco = RiscoFactory.getEntity();
		when(riscoDao.getCount(risco)).thenReturn(10);
		
		Integer total = riscoManager.getCount(risco);
		verify(riscoDao).getCount(risco);
		assertEquals(new Integer(10), total);
	}
}
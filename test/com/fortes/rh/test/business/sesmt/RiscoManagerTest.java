package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.RiscoManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class RiscoManagerTest extends MockObjectTestCase
{
	private RiscoManagerImpl riscoManager = new RiscoManagerImpl();
	private Mock riscoDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        riscoDao = new Mock(RiscoDao.class);
        riscoManager.setDao((RiscoDao) riscoDao.proxy());
    }

	public void testFindEpisByRisco()throws Exception
	{
		Collection<Epi> epis = new ArrayList<Epi>();

		Epi f1 = new Epi();
		f1.setId(1L);
		f1.setNome("nome1");

		Epi f2 = new Epi();
		f2.setId(2L);
		f1.setNome("nome2");

		epis.add(f1);
		epis.add(f2);

		Risco risco = new Risco();
		risco.setId(1L);

		List<Object> lista = new ArrayList<Object>();
		Object[] ob1 = new Object[]{1L,"nome1"};
		Object[] ob2 = new Object[]{2L,"nome2"};

		lista.add(ob1);
		lista.add(ob2);

		riscoDao.expects(once()).method("findEpisByRisco").with(eq(risco.getId())).will(returnValue(lista));
		Collection<Epi> episTmps = riscoManager.findEpisByRisco(risco.getId());

		assertEquals(episTmps, epis);
	}
	
	public void testSincronizar()throws Exception
	{
		Empresa empresaOrigem = EmpresaFactory.getEmpresa();
		empresaOrigem.setId(1L);
		Empresa empresaDestino = EmpresaFactory.getEmpresa();
		empresaDestino.setId(2L);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		
		Epi epi1 = new Epi();
		epi1.setId(1L);
		epi1.setNome("Epi1");
		epis.add(epi1);
		
		Epi epi2 = new Epi();
		epi2.setId(2L);
		epi1.setNome("Epi2");
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
		
		riscoDao.expects(once()).method("findAllSelect").with(eq(empresaOrigem.getId())).will(returnValue(riscos));
		riscoDao.expects(once()).method("save").isVoid();

		Exception exception = null;
		try {
			riscoManager.sincronizar(empresaOrigem.getId(), empresaDestino.getId(), epiIds);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testFindEpisByRiscoException()throws Exception
	{
		riscoDao.expects(once()).method("findEpisByRisco").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		riscoManager.findEpisByRisco(1L);
	}
}
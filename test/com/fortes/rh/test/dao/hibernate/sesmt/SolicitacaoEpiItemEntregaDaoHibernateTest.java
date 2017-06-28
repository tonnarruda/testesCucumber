package com.fortes.rh.test.dao.hibernate.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.EpiHistoricoDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemEntregaFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemFactory;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoEpiItemEntregaDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<SolicitacaoEpiItemEntrega>
{
	@Autowired private SolicitacaoEpiItemEntregaDao solicitacaoEpiItemEntregaDao;
	@Autowired private SolicitacaoEpiItemDao solicitacaoEpiItemDao;
	@Autowired private SolicitacaoEpiDao solicitacaoEpiDao;
	@Autowired private EpiDao epiDao;
	@Autowired private EpiHistoricoDao epiHistoricoDao;

	@Override
	public SolicitacaoEpiItemEntrega getEntity()
	{
		return SolicitacaoEpiItemEntregaFactory.getEntity();
	}

	@Override
	public GenericDao<SolicitacaoEpiItemEntrega> getGenericDao()
	{
		return solicitacaoEpiItemEntregaDao;
	}

	@Test
	public void testFindBySolicitacaoEpiItem()
	{
		Date data = DateUtil.criarDataMesAno(9, 3, 2012);
		
		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);

		SolicitacaoEpiItem solicitacaoEpiItem2 = new SolicitacaoEpiItem();
		solicitacaoEpiItemDao.save(solicitacaoEpiItem2);

		SolicitacaoEpiItemEntrega entrega1 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega1.setSolicitacaoEpiItem(solicitacaoEpiItem);
		entrega1.setDataEntrega(data);
		entrega1.setQtdEntregue(2);
		solicitacaoEpiItemEntregaDao.save(entrega1);

		SolicitacaoEpiItemEntrega entrega2 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega2.setSolicitacaoEpiItem(solicitacaoEpiItem);
		entrega2.setDataEntrega(data);
		entrega2.setQtdEntregue(2);
		solicitacaoEpiItemEntregaDao.save(entrega2);

		SolicitacaoEpiItemEntrega entrega3 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega3.setSolicitacaoEpiItem(solicitacaoEpiItem2);
		entrega3.setDataEntrega(data);
		entrega3.setQtdEntregue(2);
		solicitacaoEpiItemEntregaDao.save(entrega3);
		
		assertEquals(2, solicitacaoEpiItemEntregaDao.findBySolicitacaoEpiItem(solicitacaoEpiItem.getId()).size());
		assertEquals(1, solicitacaoEpiItemEntregaDao.findBySolicitacaoEpiItem(solicitacaoEpiItem2.getId()).size());
	}

	@Test
	public void testGetTotalEntregue()
	{
		Date data = DateUtil.criarDataMesAno(9, 3, 2012);
		
		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItem solicitacaoEpiItem2 = new SolicitacaoEpiItem();
		solicitacaoEpiItemDao.save(solicitacaoEpiItem2);
		
		SolicitacaoEpiItemEntrega entrega1 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega1.setSolicitacaoEpiItem(solicitacaoEpiItem);
		entrega1.setDataEntrega(data);
		entrega1.setQtdEntregue(2);
		solicitacaoEpiItemEntregaDao.save(entrega1);
		
		SolicitacaoEpiItemEntrega entrega2 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega2.setSolicitacaoEpiItem(solicitacaoEpiItem);
		entrega2.setDataEntrega(data);
		entrega2.setQtdEntregue(3);
		solicitacaoEpiItemEntregaDao.save(entrega2);
		
		SolicitacaoEpiItemEntrega entrega3 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega3.setSolicitacaoEpiItem(solicitacaoEpiItem2);
		entrega3.setDataEntrega(data);
		entrega3.setQtdEntregue(4);
		solicitacaoEpiItemEntregaDao.save(entrega3);
		
		assertEquals(5, solicitacaoEpiItemEntregaDao.getTotalEntregue(solicitacaoEpiItem.getId(), null));
		assertEquals(4, solicitacaoEpiItemEntregaDao.getTotalEntregue(solicitacaoEpiItem2.getId(), null));
	}
	
	@Test
	public void testFindByIdProjection() 
	{
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity();
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		Epi epi = EpiFactory.getEntity();
		epiDao.save(epi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setEpi(epi);
		epiHistoricoDao.save(epiHistorico);
		
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		solicitacaoEpiItemEntrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
		solicitacaoEpiItemEntrega.setEpiHistorico(epiHistorico);
		solicitacaoEpiItemEntregaDao.save(solicitacaoEpiItemEntrega);
		
		SolicitacaoEpiItemEntrega retorno = solicitacaoEpiItemEntregaDao.findByIdProjection(solicitacaoEpiItemEntrega.getId());
		assertEquals(solicitacaoEpiItemEntrega.getId(), retorno.getId());
		assertEquals(solicitacaoEpiItem.getId(), retorno.getSolicitacaoEpiItem().getId());
		assertEquals(epiHistorico.getId(), retorno.getEpiHistorico().getId());
	}
	
	@Test
	public void testFindBySolicitacaoEpi() 
	{
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity();
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		solicitacaoEpiItemEntrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
		solicitacaoEpiItemEntregaDao.save(solicitacaoEpiItemEntrega);

		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega2 = SolicitacaoEpiItemEntregaFactory.getEntity();
		solicitacaoEpiItemEntrega2.setSolicitacaoEpiItem(solicitacaoEpiItem);
		solicitacaoEpiItemEntregaDao.save(solicitacaoEpiItemEntrega2);
		
		Collection<SolicitacaoEpiItemEntrega> retorno = solicitacaoEpiItemEntregaDao.findBySolicitacaoEpi(solicitacaoEpi.getId());
		assertEquals(2, retorno.size());
	}

	@Test
	public void testFindBySolicitacaoEpiSemEntregas() 
	{
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity();
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		Collection<SolicitacaoEpiItemEntrega> retorno = solicitacaoEpiItemEntregaDao.findBySolicitacaoEpi(solicitacaoEpi.getId());
		assertEquals(0, retorno.size());
	}
	
	@Test
	public void testRemove() throws Exception
	{
		SolicitacaoEpiItem item = SolicitacaoEpiItemFactory.getEntity();
		solicitacaoEpiItemDao.save(item);
		
		SolicitacaoEpiItemEntrega entrega = getEntity();
		entrega.setSolicitacaoEpiItem(item);
		solicitacaoEpiItemEntregaDao.save(entrega);

		Long id = entrega.getId();
		
		solicitacaoEpiItemEntregaDao.remove(id);

		Exception ex = null;

		try {
			entrega = getGenericDao().findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			ex = e;
		}

		assertNotNull(ex);
		assertTrue(ex instanceof HibernateObjectRetrievalFailureException);
	}
	
	@Test
	public void testFindQtdEntregueByDataAndSolicitacaoItemId(){
		Date data1 = DateUtil.criarDataMesAno(1, 1, 2017);
		Date data2 = DateUtil.criarDataMesAno(1, 2, 2017);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity();
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		solicitacaoEpiItemEntrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
		solicitacaoEpiItemEntrega.setDataEntrega(data1);
		solicitacaoEpiItemEntrega.setQtdEntregue(1);
		solicitacaoEpiItemEntregaDao.save(solicitacaoEpiItemEntrega);

		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega2 = SolicitacaoEpiItemEntregaFactory.getEntity();
		solicitacaoEpiItemEntrega2.setSolicitacaoEpiItem(solicitacaoEpiItem);
		solicitacaoEpiItemEntrega2.setDataEntrega(data1);
		solicitacaoEpiItemEntrega2.setQtdEntregue(2);
		solicitacaoEpiItemEntregaDao.save(solicitacaoEpiItemEntrega2);
		
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega3 = SolicitacaoEpiItemEntregaFactory.getEntity();
		solicitacaoEpiItemEntrega3.setSolicitacaoEpiItem(solicitacaoEpiItem);
		solicitacaoEpiItemEntrega3.setDataEntrega(data2);
		solicitacaoEpiItemEntrega3.setQtdEntregue(3);
		solicitacaoEpiItemEntregaDao.save(solicitacaoEpiItemEntrega3);
		
		assertEquals(new Integer(3), solicitacaoEpiItemEntregaDao.findQtdEntregueByDataAndSolicitacaoItemId(data1, solicitacaoEpiItem.getId()));
		assertEquals(new Integer(6), solicitacaoEpiItemEntregaDao.findQtdEntregueByDataAndSolicitacaoItemId(data2, solicitacaoEpiItem.getId()));
	}
	
	@Test
	public void testGetMinDataBySolicitacaoEpiItem() {
		Date data1 = DateUtil.criarDataMesAno(1, 1, 2017);
		Date data2 = DateUtil.criarDataMesAno(1, 2, 2017);

		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpi, null, 3);
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega = SolicitacaoEpiItemEntregaFactory.getEntity(data1, solicitacaoEpiItem, 1);
		solicitacaoEpiItemEntregaDao.save(solicitacaoEpiItemEntrega);

		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega2 = SolicitacaoEpiItemEntregaFactory.getEntity(data2, solicitacaoEpiItem, 2);
		solicitacaoEpiItemEntregaDao.save(solicitacaoEpiItemEntrega2);
		
		assertEquals(data1, solicitacaoEpiItemEntregaDao.getMinDataBySolicitacaoEpiItem(solicitacaoEpiItem.getId()));
	}
}
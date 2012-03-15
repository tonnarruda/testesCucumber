package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemEntregaFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemFactory;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoEpiItemEntregaDaoHibernateTest extends GenericDaoHibernateTest<SolicitacaoEpiItemEntrega>
{
	private SolicitacaoEpiItemEntregaDao solicitacaoEpiItemEntregaDao;
	private SolicitacaoEpiItemDao solicitacaoEpiItemDao;

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
	
	public void testFindByIdProjection() 
	{
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity();
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		solicitacaoEpiItemEntrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
		solicitacaoEpiItemEntregaDao.save(solicitacaoEpiItemEntrega);
		
		SolicitacaoEpiItemEntrega retorno = solicitacaoEpiItemEntregaDao.findByIdProjection(solicitacaoEpiItemEntrega.getId());
		assertEquals(solicitacaoEpiItemEntrega.getId(), retorno.getId());
		assertEquals(solicitacaoEpiItem.getId(), retorno.getSolicitacaoEpiItem().getId());
	}
	
	public void setSolicitacaoEpiItemEntregaDao(SolicitacaoEpiItemEntregaDao solicitacaoEpiItemEntregaDao)
	{
		this.solicitacaoEpiItemEntregaDao = solicitacaoEpiItemEntregaDao;
	}

	public void setSolicitacaoEpiItemDao(SolicitacaoEpiItemDao solicitacaoEpiItemDao) {
		this.solicitacaoEpiItemDao = solicitacaoEpiItemDao;
	}
}

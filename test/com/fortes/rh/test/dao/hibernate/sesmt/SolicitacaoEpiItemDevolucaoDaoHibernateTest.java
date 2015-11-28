package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDevolucaoDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemDevolucaoFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemFactory;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoEpiItemDevolucaoDaoHibernateTest extends GenericDaoHibernateTest<SolicitacaoEpiItemDevolucao> {

	private SolicitacaoEpiItemDevolucaoDao solicitacaoEpiItemDevolucaoDao; 
	private SolicitacaoEpiItemDao solicitacaoEpiItemDao;

	public SolicitacaoEpiItemDevolucao getEntity()
	{
		SolicitacaoEpiItem	solicitacaoEpiItem1 = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem1);
		
		return SolicitacaoEpiItemDevolucaoFactory.getEntity(DateUtil.criarDataMesAno(9, 3, 2012), 1, solicitacaoEpiItem1);
	}

	public GenericDao<SolicitacaoEpiItemDevolucao> getGenericDao()
	{
		return solicitacaoEpiItemDevolucaoDao;
	}
	
	public void testGetTotalDevolvido(){
		Date data = DateUtil.criarDataMesAno(9, 3, 2012);
		
		SolicitacaoEpiItem	solicitacaoEpiItem1 = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem1);

		SolicitacaoEpiItem	solicitacaoEpiItem2 = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem2);
		
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao1 = SolicitacaoEpiItemDevolucaoFactory.getEntity(data, 1, solicitacaoEpiItem1);
		solicitacaoEpiItemDevolucaoDao.save(solicitacaoEpiItemDevolucao1);
		
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao2 = SolicitacaoEpiItemDevolucaoFactory.getEntity(data, 2, solicitacaoEpiItem2);
		solicitacaoEpiItemDevolucaoDao.save(solicitacaoEpiItemDevolucao2);

		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao3 = SolicitacaoEpiItemDevolucaoFactory.getEntity(data, 3, solicitacaoEpiItem2);
		solicitacaoEpiItemDevolucaoDao.save(solicitacaoEpiItemDevolucao3);

		assertEquals(0, solicitacaoEpiItemDevolucaoDao.getTotalDevolvido(solicitacaoEpiItem1.getId(), solicitacaoEpiItemDevolucao1.getId()));
		assertEquals(1, solicitacaoEpiItemDevolucaoDao.getTotalDevolvido(solicitacaoEpiItem1.getId(), null));
		
		assertEquals(3, solicitacaoEpiItemDevolucaoDao.getTotalDevolvido(solicitacaoEpiItem2.getId(), solicitacaoEpiItemDevolucao2.getId()));
		assertEquals(2, solicitacaoEpiItemDevolucaoDao.getTotalDevolvido(solicitacaoEpiItem2.getId(), solicitacaoEpiItemDevolucao3.getId()));
		assertEquals(5, solicitacaoEpiItemDevolucaoDao.getTotalDevolvido(solicitacaoEpiItem2.getId(), null));
	}
	
	public void testFfindBySolicitacaoEpiItem(){
		Date data = DateUtil.criarDataMesAno(9, 3, 2012);
		
		SolicitacaoEpiItem	solicitacaoEpiItem1 = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem1);

		SolicitacaoEpiItem	solicitacaoEpiItem2 = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem2);
		
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao1 = SolicitacaoEpiItemDevolucaoFactory.getEntity(data, 1, solicitacaoEpiItem1);
		solicitacaoEpiItemDevolucaoDao.save(solicitacaoEpiItemDevolucao1);
		
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao2 = SolicitacaoEpiItemDevolucaoFactory.getEntity(data, 2, solicitacaoEpiItem2);
		solicitacaoEpiItemDevolucaoDao.save(solicitacaoEpiItemDevolucao2);

		assertEquals(1, solicitacaoEpiItemDevolucaoDao.findBySolicitacaoEpiItem(solicitacaoEpiItem1.getId()).size());
		assertEquals(solicitacaoEpiItemDevolucao1.getId(), ((SolicitacaoEpiItemDevolucao)solicitacaoEpiItemDevolucaoDao.findBySolicitacaoEpiItem(solicitacaoEpiItem1.getId()).toArray()[0]).getId());
		
		assertEquals(1, solicitacaoEpiItemDevolucaoDao.findBySolicitacaoEpiItem(solicitacaoEpiItem2.getId()).size());
		assertEquals(solicitacaoEpiItemDevolucao2.getId(), ((SolicitacaoEpiItemDevolucao)solicitacaoEpiItemDevolucaoDao.findBySolicitacaoEpiItem(solicitacaoEpiItem2.getId()).toArray()[0]).getId());
		
	}

	public void setSolicitacaoEpiItemDevolucaoDao(SolicitacaoEpiItemDevolucaoDao solicitacaoEpiItemDevolucaoDao)
	{
		this.solicitacaoEpiItemDevolucaoDao = solicitacaoEpiItemDevolucaoDao;
	}
	
	public void setSolicitacaoEpiItemDao(SolicitacaoEpiItemDao solicitacaoEpiItemDao) {
		this.solicitacaoEpiItemDao = solicitacaoEpiItemDao;
	}

}

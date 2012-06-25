package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;

public class SolicitacaoEpiItemManagerTest extends MockObjectTestCase
{
	private SolicitacaoEpiItemManagerImpl solicitacaoEpiItemManager = new SolicitacaoEpiItemManagerImpl();
	//private Mock transactionManager;
	private Mock solicitacaoEpiItemDao;

	protected void setUp() throws Exception
    {
        super.setUp();
        solicitacaoEpiItemDao = new Mock(com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao.class);
        solicitacaoEpiItemManager.setDao((SolicitacaoEpiItemDao) solicitacaoEpiItemDao.proxy());

//        transactionManager = new Mock(PlatformTransactionManager.class);
//        solicitacaoEpiManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
    }

	public void testFindBySolicitacaoEpi()
	{
		Collection<SolicitacaoEpiItem> colecao = new ArrayList<SolicitacaoEpiItem>();
		Long solicitacaoEpiId = 1L;
		solicitacaoEpiItemDao.expects(once()).method("findBySolicitacaoEpi").with(eq(solicitacaoEpiId)).will(returnValue(colecao));

		assertEquals(colecao, solicitacaoEpiItemManager.findBySolicitacaoEpi(solicitacaoEpiId));
	}

	public void testFindBySolicitacaoEpiArray()
	{
		Collection<SolicitacaoEpiItem> colecao = new ArrayList<SolicitacaoEpiItem>();
		Long solicitacaoEpiId = 1L;
		solicitacaoEpiItemDao.expects(once()).method("findAllEntregasBySolicitacaoEpi").with(eq(solicitacaoEpiId)).will(returnValue(colecao));

		assertEquals(colecao, solicitacaoEpiItemManager.findAllEntregasBySolicitacaoEpi(solicitacaoEpiId));
	}
	
	public void testRemoveAllBySolicitacaoEpi()
	{
		Long solicitacaoEpiId = 1L;
		solicitacaoEpiItemDao.expects(once()).method("removeAllBySolicitacaoEpi").with(eq(solicitacaoEpiId)).isVoid();
		solicitacaoEpiItemManager.removeAllBySolicitacaoEpi(solicitacaoEpiId);
	}


}
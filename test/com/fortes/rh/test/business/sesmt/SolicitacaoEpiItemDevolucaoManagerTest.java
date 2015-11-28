package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemDevolucaoManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDevolucaoDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;

public class SolicitacaoEpiItemDevolucaoManagerTest extends MockObjectTestCase
{
	private SolicitacaoEpiItemDevolucaoManagerImpl solicitacaoEpiItemDevolucaoManager = new SolicitacaoEpiItemDevolucaoManagerImpl();
	private Mock solicitacaoEpiItemDevolucaoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        solicitacaoEpiItemDevolucaoDao = new Mock(SolicitacaoEpiItemDevolucaoDao.class);
        solicitacaoEpiItemDevolucaoManager.setDao((SolicitacaoEpiItemDevolucaoDao) solicitacaoEpiItemDevolucaoDao.proxy());
    }

	public void testGetTotalDevolvido()
	{
		solicitacaoEpiItemDevolucaoDao.expects(once()).method("getTotalDevolvido").with(eq(1L), eq(1L)).will(returnValue(0));
		assertEquals(0, solicitacaoEpiItemDevolucaoManager.getDao().getTotalDevolvido(1L, 1L));
	}
	
	public void testFindSolicEpiItemDevolucaoBySolicitacaoEpiItem()
	{
		Collection<SolicitacaoEpiItemDevolucao> solicitacaoEpiItemDevolucoes = new ArrayList<SolicitacaoEpiItemDevolucao>();
		
		solicitacaoEpiItemDevolucaoDao.expects(once()).method("findBySolicitacaoEpiItem").with(eq(1L)).will(returnValue(solicitacaoEpiItemDevolucoes));
		assertEquals(solicitacaoEpiItemDevolucoes, solicitacaoEpiItemDevolucaoManager.getDao().findBySolicitacaoEpiItem(1L));
	}
}

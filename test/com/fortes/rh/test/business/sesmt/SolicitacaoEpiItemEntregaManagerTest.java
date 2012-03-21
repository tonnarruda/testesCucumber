package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemEntregaManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;

public class SolicitacaoEpiItemEntregaManagerTest extends MockObjectTestCase
{
	private SolicitacaoEpiItemEntregaManagerImpl solicitacaoEpiItemEntregaManager = new SolicitacaoEpiItemEntregaManagerImpl();
	private Mock solicitacaoEpiItemEntregaDao;

	protected void setUp() throws Exception
    {
        super.setUp();
        solicitacaoEpiItemEntregaDao = new Mock(com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao.class);
        solicitacaoEpiItemEntregaManager.setDao((SolicitacaoEpiItemEntregaDao) solicitacaoEpiItemEntregaDao.proxy());
    }

	public void testNaoExisteEntrega()
	{
		Collection<SolicitacaoEpiItemEntrega> colecao = new ArrayList<SolicitacaoEpiItemEntrega>();
		Long solicitacaoEpiId = 1L;
		solicitacaoEpiItemEntregaDao.expects(once()).method("findBySolicitacaoEpi").with(eq(solicitacaoEpiId)).will(returnValue(colecao));

		assertEquals(false, solicitacaoEpiItemEntregaManager.existeEntrega(solicitacaoEpiId));
	}
	
	public void testExisteEntrega()
	{
		Collection<SolicitacaoEpiItemEntrega> colecao = Arrays.asList(new SolicitacaoEpiItemEntrega());
		Long solicitacaoEpiId = 1L;
		solicitacaoEpiItemEntregaDao.expects(once()).method("findBySolicitacaoEpi").with(eq(solicitacaoEpiId)).will(returnValue(colecao));
		
		assertEquals(true, solicitacaoEpiItemEntregaManager.existeEntrega(solicitacaoEpiId));
	}
}
package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemEntregaManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;

public class SolicitacaoEpiItemEntregaManagerTest extends MockObjectTestCase
{
	private SolicitacaoEpiItemEntregaManagerImpl solicitacaoEpiItemEntregaManager = new SolicitacaoEpiItemEntregaManagerImpl();
	private Mock solicitacaoEpiItemEntregaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        solicitacaoEpiItemEntregaDao = new Mock(SolicitacaoEpiItemEntregaDao.class);
        solicitacaoEpiItemEntregaManager.setDao((SolicitacaoEpiItemEntregaDao) solicitacaoEpiItemEntregaDao.proxy());
    }
}

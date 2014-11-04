package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.MotivoSolicitacaoEpiManagerImpl;
import com.fortes.rh.dao.sesmt.MotivoSolicitacaoEpiDao;

public class MotivoSolicitacaoEpiManagerTest extends MockObjectTestCase
{
	private MotivoSolicitacaoEpiManagerImpl motivoSolicitacaoEpiManager = new MotivoSolicitacaoEpiManagerImpl();
	private Mock motivoSolicitacaoEpiDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        motivoSolicitacaoEpiDao = new Mock(MotivoSolicitacaoEpiDao.class);
        motivoSolicitacaoEpiManager.setDao((MotivoSolicitacaoEpiDao) motivoSolicitacaoEpiDao.proxy());
    }
}

package com.fortes.rh.test.business.desenvolvimento;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;

public class ColaboradorCertificacaoManagerTest extends MockObjectTestCase
{
	private ColaboradorCertificacaoManagerImpl colaboradorCertificacaoManager = new ColaboradorCertificacaoManagerImpl();
	private Mock colaboradorCertificacaoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        colaboradorCertificacaoDao = new Mock(ColaboradorCertificacaoDao.class);
        colaboradorCertificacaoManager.setDao((ColaboradorCertificacaoDao) colaboradorCertificacaoDao.proxy());
    }

	public void testFindAllSelect()
	{
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = ColaboradorCertificacaoFactory.getCollection(1L);

		colaboradorCertificacaoDao.expects(once()).method("findAll").will(returnValue(colaboradorCertificacaos));
		assertEquals(colaboradorCertificacaos, colaboradorCertificacaoManager.findAll());
	}
}

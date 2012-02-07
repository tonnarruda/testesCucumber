package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.GerenciadorComunicacaoManagerImpl;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;
import com.fortes.rh.test.factory.geral.TipoDespesaFactory;

public class GerenciadorComunicacaoManagerTest extends MockObjectTestCase
{
	private GerenciadorComunicacaoManagerImpl gerenciadorComunicacaoManager = new GerenciadorComunicacaoManagerImpl();
	private Mock gerenciadorComunicacaoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        gerenciadorComunicacaoDao = new Mock(GerenciadorComunicacaoDao.class);
        gerenciadorComunicacaoManager.setDao((GerenciadorComunicacaoDao) gerenciadorComunicacaoDao.proxy());
    }

	public void testFindAllSelect()
	{
		Collection<GerenciadorComunicacao> gerenciadorComunicacao = GerenciadorComunicacaoFactory.getCollection(1L);

		gerenciadorComunicacaoDao.expects(once()).method("findAll").will(returnValue(gerenciadorComunicacao));
		assertEquals(gerenciadorComunicacao, gerenciadorComunicacaoManager.findAll());
	}
}

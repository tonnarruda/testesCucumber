package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ComissaoPlanoTrabalhoManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoPlanoTrabalhoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPlanoTrabalhoFactory;

public class ComissaoPlanoTrabalhoManagerTest extends MockObjectTestCase
{
	private ComissaoPlanoTrabalhoManagerImpl comissaoPlanoTrabalhoManager = new ComissaoPlanoTrabalhoManagerImpl();
	private Mock comissaoPlanoTrabalhoDao;

	protected void setUp() throws Exception
    {
        super.setUp();
        comissaoPlanoTrabalhoDao = new Mock(ComissaoPlanoTrabalhoDao.class);
        comissaoPlanoTrabalhoManager.setDao((ComissaoPlanoTrabalhoDao) comissaoPlanoTrabalhoDao.proxy());
    }

	public void testFindByIdProjection()
	{
		ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity(1L);
		comissaoPlanoTrabalhoDao.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(comissaoPlanoTrabalho));
		assertEquals(comissaoPlanoTrabalho,comissaoPlanoTrabalhoManager.findByIdProjection(comissaoPlanoTrabalho.getId()));
	}

	public void testFindByComissao()
	{
		Long comissaoId = 1L;
		Comissao comissao = ComissaoFactory.getEntity(comissaoId);
		ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity(1L);
		comissaoPlanoTrabalho.setComissao(comissao);
		Collection<ComissaoPlanoTrabalho> colecao = new ArrayList<ComissaoPlanoTrabalho>();
		colecao.add(comissaoPlanoTrabalho);

		comissaoPlanoTrabalhoDao.expects(once()).method("findByComissao").with(eq(comissaoId),ANYTHING, ANYTHING, ANYTHING).will(returnValue(colecao));

		colecao = comissaoPlanoTrabalhoManager.findByComissao(comissaoId, null, null, null);
		ComissaoPlanoTrabalho resultado = ((ComissaoPlanoTrabalho)colecao.toArray()[0]);
		assertEquals(Long.valueOf(1), resultado.getId());
	}

	public void testRemoveByComissao()
	{
		Long comissaoId = 1L;
		Comissao comissao = ComissaoFactory.getEntity(comissaoId);
		ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity(1L);
		comissaoPlanoTrabalho.setComissao(comissao);
		Collection<ComissaoPlanoTrabalho> colecao = new ArrayList<ComissaoPlanoTrabalho>();
		colecao.add(comissaoPlanoTrabalho);

		comissaoPlanoTrabalhoDao.expects(once()).method("removeByComissao").with(ANYTHING).isVoid();

		comissaoPlanoTrabalhoManager.removeByComissao(comissaoId);
	}

	public void testFindImprimirPlanoTrabalho() throws Exception
	{
		Collection<ComissaoPlanoTrabalho> colecao = new ArrayList<ComissaoPlanoTrabalho>();
		colecao.add(ComissaoPlanoTrabalhoFactory.getEntity());
		comissaoPlanoTrabalhoDao.expects(once()).method("findByComissao").with(eq(1L),ANYTHING,ANYTHING,ANYTHING).will(returnValue(colecao));
		assertEquals(1, comissaoPlanoTrabalhoManager.findImprimirPlanoTrabalho(1L, null, null, null).size());
	}

	public void testFindImprimirPlanoTrabalhoException()
	{
		comissaoPlanoTrabalhoDao.expects(once()).method("findByComissao").with(eq(1L),ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<ComissaoPlanoTrabalho>()));

		Exception exception = null;
		try
		{
			comissaoPlanoTrabalhoManager.findImprimirPlanoTrabalho(1L, null, null, null);
		}
		catch (ColecaoVaziaException e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
}
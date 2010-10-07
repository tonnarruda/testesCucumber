package com.fortes.rh.test.business.sesmt;

import java.util.Collection;
import java.util.HashSet;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AnexoManagerImpl;
import com.fortes.rh.dao.sesmt.AnexoDao;
import com.fortes.rh.model.sesmt.Anexo;

public class AnexoManagerTest extends MockObjectTestCase
{
	private AnexoManagerImpl anexoManager = new AnexoManagerImpl();
	private Mock AnexoDao = null;

	protected void setUp() throws Exception
    {
        super.setUp();
        AnexoDao = new Mock(AnexoDao.class);
        anexoManager.setDao((AnexoDao) AnexoDao.proxy());
    }

	public void testFindByOrigem()throws Exception
	{
		Collection<Anexo> anexos = new HashSet<Anexo>();

		Anexo anexo = new Anexo();
		anexo.setId(1L);

		anexos.add(anexo);

		AnexoDao.expects(once()).method("findByOrigem").with(ANYTHING, ANYTHING).will(returnValue(anexos));
		assertEquals(1, anexoManager.findByOrigem(null, 's').size());
	}



}
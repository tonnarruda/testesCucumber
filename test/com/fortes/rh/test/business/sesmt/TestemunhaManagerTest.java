package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.TestemunhaManagerImpl;
import com.fortes.rh.dao.sesmt.TestemunhaDao;
import com.fortes.rh.model.sesmt.Testemunha;

public class TestemunhaManagerTest extends MockObjectTestCase
{
	private TestemunhaManagerImpl testemunhaManager = new TestemunhaManagerImpl();
	private Mock testemunhaDao = null;

	protected void setUp() throws Exception
    {
        super.setUp();
        testemunhaDao = new Mock(TestemunhaDao.class);
        testemunhaManager.setDao((TestemunhaDao) testemunhaDao.proxy());
    }

	public void testRemoveTestemunha()
	{
		Testemunha testemunha1 = new Testemunha();
		testemunha1.setId(1L);

		testemunhaDao.expects(once()).method("removerDependenciaComCat").with(eq(testemunha1.getId()), eq("testemunha1")).isVoid();
		testemunhaDao.expects(once()).method("remove").with(eq(testemunha1.getId())).isVoid();
		Exception exception = null;
		try {
			testemunhaManager.removeTestemunha(testemunha1.getId(), "testemunha1");
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
}
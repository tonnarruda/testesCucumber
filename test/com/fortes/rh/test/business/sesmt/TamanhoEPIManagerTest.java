package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.TamanhoEPIManagerImpl;
import com.fortes.rh.dao.sesmt.TamanhoEPIDao;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.web.tags.CheckBox;

public class TamanhoEPIManagerTest extends MockObjectTestCase
{
	private TamanhoEPIManagerImpl tamanhoEPIManager = new TamanhoEPIManagerImpl();
	private Mock tamanhoEPIDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        tamanhoEPIDao = new Mock(TamanhoEPIDao.class);
        tamanhoEPIManager.setDao((TamanhoEPIDao) tamanhoEPIDao.proxy());
    }
	
	public void testPopulaCheckOrderDescricao()
	{
		Collection<TamanhoEPI> tamanhoEPIs = new ArrayList<TamanhoEPI>();

		tamanhoEPIDao.expects(once()).method("findAll").with(ANYTHING).will(returnValue(tamanhoEPIs));

		assertEquals(tamanhoEPIManager.populaCheckOrderDescricao(1L), new ArrayList<CheckBox>());

		tamanhoEPIDao.expects(once()).method("findAll").with(ANYTHING);

		assertEquals(tamanhoEPIManager.populaCheckOrderDescricao(1L), new ArrayList<CheckBox>());

	}
}

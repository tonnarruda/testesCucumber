package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ExtintorManutencaoManagerImpl;
import com.fortes.rh.dao.sesmt.ExtintorManutencaoDao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorManutencaoFactory;

public class ExtintorManutencaoManagerTest extends MockObjectTestCase
{
	private ExtintorManutencaoManagerImpl extintorManutencaoManager = new ExtintorManutencaoManagerImpl();
	private Mock extintorManutencaoDao = null;

	protected void setUp() throws Exception
    {
        super.setUp();
        extintorManutencaoDao = new Mock(ExtintorManutencaoDao.class);
        extintorManutencaoManager.setDao((ExtintorManutencaoDao) extintorManutencaoDao.proxy());
    }

	Long empresaId = 5L;
	Long estabelecimentoId = 5L;
	Long extintorId = 5L;
	Date inicio = new Date();
	Date fim = new Date();

	public void testGetCount()
	{
		extintorManutencaoDao.expects(once()).method("getCount").will(returnValue(1));
		assertEquals(Integer.valueOf(1),
				extintorManutencaoManager.getCount(empresaId, estabelecimentoId, extintorId, inicio, fim, false));
	}

	public void testFindAllSelect()
	{
		Collection<ExtintorManutencao> colecao = new ArrayList<ExtintorManutencao>();
		colecao.add(ExtintorManutencaoFactory.getEntity(1L));

		extintorManutencaoDao.expects(once()).method("findAllSelect").will(returnValue(colecao ));
		assertEquals(colecao, extintorManutencaoManager.findAllSelect(0, 0, empresaId, estabelecimentoId, extintorId, inicio, fim,false, null));
	}

	public void testSaveOrUpdate() throws Exception
	{
		String[] itemChecks = {"1","3","5"};

		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity();
		extintorManutencao.setExtintor(ExtintorFactory.getEntity(1L));
		extintorManutencao.setSaida(new Date());

		extintorManutencaoDao.expects(once()).method("save").will(returnValue(extintorManutencao));

		extintorManutencao = extintorManutencaoManager.saveOrUpdate(extintorManutencao, itemChecks);

		assertNotNull(extintorManutencao.getServicos());
		assertEquals(3, extintorManutencao.getServicos().size());

		// Update
		extintorManutencao.setId(1L);
		itemChecks = new String[]{"5"};

		extintorManutencaoDao.expects(once()).method("update").isVoid();

		extintorManutencao = extintorManutencaoManager.saveOrUpdate(extintorManutencao, itemChecks);

		assertEquals(1, extintorManutencao.getServicos().size());
	}
	
	public void testFindManutencaoVencida() throws Exception
	{
		extintorManutencaoDao.expects(once()).method("findManutencaoVencida").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ExtintorManutencao>()));
		assertNotNull(extintorManutencaoManager.findManutencaoVencida(null, null, null));
	}
}
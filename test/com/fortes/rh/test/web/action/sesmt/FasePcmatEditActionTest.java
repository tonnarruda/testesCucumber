package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.FasePcmatManager;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.FasePcmatFactory;
import com.fortes.rh.web.action.sesmt.FasePcmatEditAction;

public class FasePcmatEditActionTest extends MockObjectTestCase
{
	private FasePcmatEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(FasePcmatManager.class);
		action = new FasePcmatEditAction();
		action.setFasePcmatManager((FasePcmatManager) manager.proxy());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

		action.setFasePcmat(new FasePcmat());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<FasePcmat>()));
		assertEquals("success", action.list());
		assertNotNull(action.getFasePcmats());
	}

	public void testDelete() throws Exception
	{
		FasePcmat fasepcmat = FasePcmatFactory.getEntity(1L);
		action.setFasePcmat(fasepcmat);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<FasePcmat>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		FasePcmat fasepcmat = FasePcmatFactory.getEntity(1L);
		action.setFasePcmat(fasepcmat);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<FasePcmat>()));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		FasePcmat fasepcmat = FasePcmatFactory.getEntity(1L);
		action.setFasePcmat(fasepcmat);

		manager.expects(once()).method("save").with(eq(fasepcmat)).will(returnValue(fasepcmat));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		FasePcmat fasepcmat = FasePcmatFactory.getEntity(1L);
		action.setFasePcmat(fasepcmat);

		manager.expects(once()).method("update").with(eq(fasepcmat)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		FasePcmat fasepcmat = FasePcmatFactory.getEntity(1L);
		action.setFasePcmat(fasepcmat);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(fasepcmat.getId())).will(returnValue(fasepcmat));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setFasePcmat(null);

		assertNotNull(action.getFasePcmat());
		assertTrue(action.getFasePcmat() instanceof FasePcmat);
	}
}

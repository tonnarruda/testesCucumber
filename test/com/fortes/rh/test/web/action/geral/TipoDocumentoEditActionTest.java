package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.TipoDocumentoManager;
import com.fortes.rh.model.geral.TipoDocumento;
import com.fortes.rh.test.factory.geral.TipoDocumentoFactory;
import com.fortes.rh.web.action.geral.TipoDocumentoEditAction;

public class TipoDocumentoEditActionTest extends MockObjectTestCase
{
	private TipoDocumentoEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(TipoDocumentoManager.class);
		action = new TipoDocumentoEditAction();
		action.setTipoDocumentoManager((TipoDocumentoManager) manager.proxy());

		action.setTipoDocumento(new TipoDocumento());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<TipoDocumento>()));
		assertEquals("success", action.list());
		assertNotNull(action.getTipoDocumentos());
	}

	public void testDelete() throws Exception
	{
		TipoDocumento tipoDocumento = TipoDocumentoFactory.getEntity(1L);
		action.setTipoDocumento(tipoDocumento);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<TipoDocumento>()));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		TipoDocumento tipoDocumento = TipoDocumentoFactory.getEntity(1L);
		action.setTipoDocumento(tipoDocumento);

		manager.expects(once()).method("save").with(eq(tipoDocumento)).will(returnValue(tipoDocumento));

		assertEquals("success", action.insert());
	}

	public void testUpdate() throws Exception
	{
		TipoDocumento tipoDocumento = TipoDocumentoFactory.getEntity(1L);
		action.setTipoDocumento(tipoDocumento);

		manager.expects(once()).method("update").with(eq(tipoDocumento)).isVoid();

		assertEquals("success", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setTipoDocumento(null);

		assertNotNull(action.getTipoDocumento());
		assertTrue(action.getTipoDocumento() instanceof TipoDocumento);
	}
}

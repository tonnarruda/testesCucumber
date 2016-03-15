package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.captacao.AnuncioListAction;

public class AnuncioListActionTest extends MockObjectTestCase
{

	private AnuncioListAction action;
	private Mock manager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		action = new AnuncioListAction();
		manager = new Mock(AnuncioManager.class);
		action.setAnuncioManager((AnuncioManager) manager.proxy());
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
    	MockSecurityUtil.verifyRole = false;
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

	public void testList() throws Exception
	{
		Collection<Anuncio> anuncios = new ArrayList<Anuncio>();
		manager.expects(once()).method("findAll").will(returnValue(anuncios));

		assertEquals("success", action.list());
		assertEquals(anuncios, action.getAnuncios());
	}

	public void testDelete() throws Exception
	{
		Anuncio anuncio = new Anuncio();
		anuncio.setId(1L);
		
		action.setAnuncio(anuncio);		
		manager.expects(once()).method("remove").with(eq(anuncio.getId()));
		
		assertEquals("success", action.delete());
		
		action.setAnuncio(null);
		assertNotNull(action.getAnuncio());
	}

}

package com.fortes.rh.test.web.action.sesmt;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.model.type.File;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.MedicoCoordenadorEditAction;

public class MedicoCoordenadorEditActionTest extends MockObjectTestCase
{
	private MedicoCoordenadorEditAction action;
	private Mock medicoCoordenadorManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		medicoCoordenadorManager = new Mock(MedicoCoordenadorManager.class);

//		response = new Mock(HttpServletResponse.class);

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);

		action = new MedicoCoordenadorEditAction();
		action.setMedicoCoordenadorManager((MedicoCoordenadorManager) medicoCoordenadorManager.proxy());
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
		medicoCoordenadorManager = null;
		action = null;
        MockSecurityUtil.verifyRole = false;
		super.tearDown();
	}

	public void testExecute() throws Exception
	{
		assertEquals(action.execute(), "success");
	}

	public void testPrepareInsert() throws Exception
	{
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenador.setId(1L);

		action.setMedicoCoordenador(medicoCoordenador);

		medicoCoordenadorManager.expects(once()).method("findByIdProjection").with(eq(medicoCoordenador.getId())).will(returnValue(medicoCoordenador));
		medicoCoordenadorManager.expects(once()).method("getAssinaturaDigital").with(eq(medicoCoordenador.getId())).will(returnValue(new File()));
		assertEquals(action.prepareInsert(), "success");
		assertEquals(action.getMedicoCoordenador(), medicoCoordenador);
	}

    public void testPrepareUpdate() throws Exception
    {
    	MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
    	medicoCoordenador.setId(1L);
    	medicoCoordenador.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setMedicoCoordenador(medicoCoordenador);

    	medicoCoordenadorManager.expects(atLeastOnce()).method("findByIdProjection").with(eq(medicoCoordenador.getId())).will(returnValue(medicoCoordenador));
    	medicoCoordenadorManager.expects(once()).method("getAssinaturaDigital").with(eq(medicoCoordenador.getId())).will(returnValue(new File()));
    	assertEquals(action.prepareUpdate(), "success");

    	medicoCoordenadorManager.expects(atLeastOnce()).method("findByIdProjection").with(eq(medicoCoordenador.getId())).will(returnValue(null));
    	assertEquals(action.prepareUpdate(), "error");
    	assertNotNull(action.getActionErrors());

    }

    public void testInsert() throws Exception
    {
    	MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
    	medicoCoordenador.setId(1L);
    	action.setMedicoCoordenador(medicoCoordenador);

		medicoCoordenadorManager.expects(once()).method("save").with(eq(medicoCoordenador)).will(returnValue(medicoCoordenador));

    	assertEquals(action.insert(), "success");
    	assertEquals(action.getMedicoCoordenador(), medicoCoordenador);

    }

    public void testInsertAssinaturaInvalida() throws Exception
    {
    	MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
    	medicoCoordenador.setId(1L);
    	File assinatura = new File();
    	assinatura.setContentType("text/html");//formato invalido
    	medicoCoordenador.setAssinaturaDigital(assinatura);
    	action.setMedicoCoordenador(medicoCoordenador);

    	//prepareInsert
    	medicoCoordenadorManager.expects(once()).method("findByIdProjection").with(eq(medicoCoordenador.getId())).will(returnValue(medicoCoordenador));
		medicoCoordenadorManager.expects(once()).method("getAssinaturaDigital").with(eq(medicoCoordenador.getId())).will(returnValue(new File()));

    	assertEquals(action.insert(), "input");
    }

    public void testUpdate() throws Exception
    {
    	MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
    	medicoCoordenador.setId(1L);
    	medicoCoordenador.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setMedicoCoordenador(medicoCoordenador);

    	medicoCoordenadorManager.expects(once()).method("update").with(eq(medicoCoordenador));

    	assertEquals(action.update(), "success");
    	assertEquals(action.getMedicoCoordenador(), medicoCoordenador);
    }

    public void testUpdateAssinaturaInvalida() throws Exception
    {
    	MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
    	medicoCoordenador.setId(1L);
    	medicoCoordenador.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	File assinatura = new File();
    	assinatura.setContentType("text/html");//formato invalido
    	medicoCoordenador.setAssinaturaDigital(assinatura);
    	action.setMedicoCoordenador(medicoCoordenador);

    	//prepareInsert
    	medicoCoordenadorManager.expects(once()).method("findByIdProjection").with(eq(medicoCoordenador.getId())).will(returnValue(medicoCoordenador));
		medicoCoordenadorManager.expects(once()).method("getAssinaturaDigital").with(eq(medicoCoordenador.getId())).will(returnValue(new File()));

    	assertEquals("input", action.insert());
    }

     // TODO . Como mockar HttpServletResponse?
//    public void testShowAssinatura() throws Exception
//	{
//    	MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
//    	medicoCoordenador.setId(1L);
//    	File assinatura = new File();
//    	assinatura.setContentType("image/jpeg");
//    	assinatura.setBytes(new byte[]{2,3,127,4,43,3,31});
//    	action.setMedicoCoordenador(medicoCoordenador);
//
//    	medicoCoordenadorManager.expects(once()).method("getAssinaturaDigital").with(eq(medicoCoordenador.getId())).will(returnValue(assinatura));
//
//    	response.expects(once()).method("addHeader").with(ANYTHING, ANYTHING).isVoid();
//    	response.expects(once()).method("addHeader").with(ANYTHING, ANYTHING).isVoid();
//    	response.expects(once()).method("addHeader").with(ANYTHING, ANYTHING).isVoid();
//    	response.expects(once()).method("addHeader").with(ANYTHING, ANYTHING).isVoid();
//
//
//    	assertEquals("success", action.showAssinatura());
//	}

    public void testGetSet() throws Exception
    {
    	action.setMedicoCoordenador(null);
        action.getMedicoCoordenador();
        action.getModel();
    }
}
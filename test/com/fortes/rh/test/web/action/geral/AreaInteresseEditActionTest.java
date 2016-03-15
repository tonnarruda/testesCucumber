package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaInteresseManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.AreaInteresseEditAction;
import com.fortes.web.tags.CheckBox;

public class AreaInteresseEditActionTest extends MockObjectTestCase
{
	private AreaInteresseEditAction action;
	private Mock manager;
	private Mock areaOrganizacionalManager;

	protected void setUp()
	{
		action = new AreaInteresseEditAction();
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		manager = new Mock(AreaInteresseManager.class);
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		action.setAreaInteresseManager((AreaInteresseManager) manager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }
	
	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

    public void testPrepareInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	AreaOrganizacional ao1 = AreaOrganizacionalFactory.getEntity();
    	ao1.setId(1L);

    	AreaOrganizacional ao2 = AreaOrganizacionalFactory.getEntity();
    	ao2.setId(2L);

    	Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
    	areaOrganizacionals.add(ao1);
    	areaOrganizacionals.add(ao2);

    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));

    	assertEquals(action.prepareInsert(), "success");

    }

    public void testPrepareUpdate() throws Exception
    {

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	AreaOrganizacional ao1 = AreaOrganizacionalFactory.getEntity();
    	ao1.setId(1L);
    	ao1.setEmpresa(empresa);

    	AreaOrganizacional ao2 = AreaOrganizacionalFactory.getEntity();
    	ao2.setId(2L);
    	ao2.setEmpresa(empresa);

    	Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
    	areaOrganizacionals.add(ao1);
    	areaOrganizacionals.add(ao2);

    	AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse();
    	areaInteresse.setId(1L);
    	areaInteresse.setAreasOrganizacionais(areaOrganizacionals);

    	action.setAreaInteresse(areaInteresse);

    	manager.expects(once()).method("findByIdProjection").with(eq(areaInteresse.getId())).will(returnValue(areaInteresse));
    	areaOrganizacionalManager.expects(once()).method("getAreasByAreaInteresse").with(eq(areaInteresse.getId())).will(returnValue(new ArrayList<AreaOrganizacional>()));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));

    	assertEquals(action.prepareUpdate(), "success");

    }

    public void testInsert() throws Exception
    {
    	AreaOrganizacional ao1r = AreaOrganizacionalFactory.getEntity();
    	ao1r.setId(1L);

    	AreaOrganizacional ao2r = AreaOrganizacionalFactory.getEntity();
    	ao2r.setId(2L);

    	String[] areasCheck = new String[]{ao1r.getId().toString(),ao2r.getId().toString()};
		action.setAreasCheck(areasCheck );

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	AreaOrganizacional ao1 = AreaOrganizacionalFactory.getEntity();
    	ao1.setId(1L);
    	ao1.setEmpresa(empresa);

    	AreaOrganizacional ao2 = AreaOrganizacionalFactory.getEntity();
    	ao2.setId(2L);
    	ao2.setEmpresa(empresa);

    	Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
    	areaOrganizacionals.add(ao1);
    	areaOrganizacionals.add(ao2);

    	AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse();
    	areaInteresse.setId(1L);
    	areaInteresse.setAreasOrganizacionais(areaOrganizacionals);

    	action.setAreaInteresse(areaInteresse);

    	areaOrganizacionalManager.expects(once()).method("populaAreas").with(ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
    	manager.expects(once()).method("save").with(ANYTHING);

    	assertEquals(action.insert(), "success");
    }

    public void testUpdate() throws Exception
    {
    	AreaOrganizacional ao1r = AreaOrganizacionalFactory.getEntity();
    	ao1r.setId(1L);

    	AreaOrganizacional ao2r = AreaOrganizacionalFactory.getEntity();
    	ao2r.setId(2L);

    	String[] areasCheck = new String[]{ao1r.getId().toString(),ao2r.getId().toString()};
		action.setAreasCheck(areasCheck );

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	AreaOrganizacional ao1 = AreaOrganizacionalFactory.getEntity();
    	ao1.setId(1L);
    	ao1.setEmpresa(empresa);

    	AreaOrganizacional ao2 = AreaOrganizacionalFactory.getEntity();
    	ao2.setId(2L);
    	ao2.setEmpresa(empresa);

    	Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
    	areaOrganizacionals.add(ao1);
    	areaOrganizacionals.add(ao2);

    	AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse();
    	areaInteresse.setId(1L);
    	areaInteresse.setAreasOrganizacionais(areaOrganizacionals);

    	action.setAreaInteresse(areaInteresse);

    	areaOrganizacionalManager.expects(once()).method("populaAreas").with(ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
    	manager.expects(once()).method("update").with(ANYTHING);

    	assertEquals(action.update(), "success");
    }

    public void testGetsSets() throws Exception
    {
    	action.getModel();

    	AreaOrganizacional ao = new AreaOrganizacional();
    	ao.setId(1L);
    	action.setAreaOrganizacional(ao);
    	ao = action.getAreaOrganizacional();


    	Collection<String> lista = new ArrayList<String>();
    	lista.add("string");

    	action.getAreasCheck();
    	action.getAreasCheckList();
    	action.setAreasCheckList(new ArrayList<CheckBox>());

    }
}
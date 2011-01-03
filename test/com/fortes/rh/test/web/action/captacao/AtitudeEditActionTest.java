package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.action.captacao.AtitudeEditAction;
import com.fortes.web.tags.CheckBox;

public class AtitudeEditActionTest extends MockObjectTestCase
{
	private AtitudeEditAction action;
	private Mock areaOrganizacionalManager;
	private Mock atitudeManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		action = new AtitudeEditAction();
		atitudeManager = new Mock(AtitudeManager.class);
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);

		action.setAtitude(new Atitude());
		action.setAtitudeManager((AtitudeManager) atitudeManager.proxy());
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
	}

	public void testPrepareInsert() throws Exception
	{
		Atitude Atitude = new Atitude();
		Atitude.setId(1L);
		action.setAtitude(Atitude);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

		atitudeManager.expects(once()).method("findById").will(returnValue(Atitude));
		Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();		
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(1L)).will(returnValue(areasCheckList)); 
		
		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Atitude atitude = new Atitude();
		atitude.setId(1L);
		action.setAtitude(atitude);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		atitudeManager.expects(once()).method("findById").will(returnValue(atitude));
		Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();		
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(1L)).will(returnValue(areasCheckList));
		
		assertEquals("success", action.prepareUpdate());
	}
	
	protected void tearDown() throws Exception
	{
		atitudeManager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		Atitude atitude = new Atitude();
		atitude.setId(1L);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		atitudeManager.expects(once()).method("getCount").will(returnValue(new Integer (1)));
		atitudeManager.expects(once()).method("findToList").will(returnValue(new ArrayList<Atitude>()));
		
		assertEquals("success", action.list());
		assertNotNull(action.getAtitudes());
	}

	public void testDelete() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);

		atitudeManager.expects(once()).method("remove");
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);
		
		atitudeManager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		try {
			action.delete();
		} catch (Exception e) {
			return;
		}
		
		fail("Deveria ter lançado uma exceçao");
	}

	public void testInsert() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);

		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();
		
		areaOrganizacionalManager.expects(once()).method("populaAreas").with(ANYTHING).will(returnValue(areas));
		atitudeManager.expects(once()).method("save").with(eq(atitude)).will(returnValue(atitude));
		
		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		Exception exception = null;
		
    	try
		{
    		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();
    		
    		areaOrganizacionalManager.expects(once()).method("populaAreas").with(ANYTHING).will(returnValue(areas));
    		
    		atitudeManager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
    		action.insert();
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testUpdate() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);

		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();
		
		areaOrganizacionalManager.expects(once()).method("populaAreas").with(ANYTHING).will(returnValue(areas));
		atitudeManager.expects(once()).method("update").with(eq(atitude)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Exception exception = null;
		
    	try
		{	
    		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();
		
			areaOrganizacionalManager.expects(once()).method("populaAreas").with(ANYTHING).will(returnValue(areas));
    		atitudeManager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
    		action.update();
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
		
	}

	public void testGetSet() throws Exception
	{
		action.setAtitude(null);

		assertNotNull(action.getAtitude());
		assertTrue(action.getAtitude() instanceof Atitude);
	}
	
	private Collection<AreaOrganizacional> collectionAreasOrganizacionais() {
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(area);
		return areas;
	}

}

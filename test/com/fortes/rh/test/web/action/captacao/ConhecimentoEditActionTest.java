package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.action.captacao.ConhecimentoEditAction;
import com.fortes.web.tags.CheckBox;

public class ConhecimentoEditActionTest extends MockObjectTestCase
{
	private ConhecimentoEditAction action;
	private Mock manager;
	private Mock competenciaManager;
	Mock areaOrganizacionalManager;

	protected void setUp() throws Exception
	{
		super.setUp();

		manager = new Mock(ConhecimentoManager.class);
		action = new ConhecimentoEditAction();
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		competenciaManager = new Mock(CompetenciaManager.class);
		
		action.setConhecimento(new Conhecimento());
		action.setConhecimentoManager((ConhecimentoManager) manager.proxy());
		action.setCompetenciaManager((CompetenciaManager) competenciaManager.proxy());
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
	}

	public void testPrepareInsert() throws Exception
	{
		Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
		
		Conhecimento conhecimento = new Conhecimento();
		conhecimento.setId(1L);
		action.setConhecimento(conhecimento);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("findByIdProjection").will(returnValue(conhecimento));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").will(returnValue(areasCheckList));
		
		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
		
		Conhecimento conhecimento = new Conhecimento();
		conhecimento.setId(1L);
		action.setConhecimento(conhecimento);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("findByIdProjection").will(returnValue(conhecimento));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").will(returnValue(areasCheckList));
		
		assertEquals("success", action.prepareUpdate());
	}
	
	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testInsert() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(33L);
		action.setEmpresaSistema(empresa);

		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(1L);
		action.setConhecimento(conhecimento);
		competenciaManager.expects(once()).method("existeNome").will(returnValue(false));
		areaOrganizacionalManager.expects(once()).method("populaAreas").will(returnValue(areas));
		manager.expects(once()).method("save").with(eq(conhecimento)).will(returnValue(conhecimento));

		assertEquals("success", action.insert());
		assertEquals(areas, action.getConhecimento().getAreaOrganizacionals());
		assertEquals(empresa.getId(), action.getEmpresaSistema().getId());
	}

	public void testInsertException() throws Exception
	{
		Exception exception = null;
		
		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();
		
    	try
		{
    		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
    		conhecimento.setNome("conhecimento");

    		action.setConhecimento(conhecimento);
    		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    		
    		competenciaManager.expects(once()).method("existeNome").will(returnValue(false));
    		areaOrganizacionalManager.expects(once()).method("populaAreas").will(returnValue(areas));
    		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
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
		Empresa empresa = EmpresaFactory.getEmpresa(33L);
		action.setEmpresaSistema(empresa);

		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(1L);
		action.setConhecimento(conhecimento);
		competenciaManager.expects(once()).method("existeNome").will(returnValue(false));
		areaOrganizacionalManager.expects(once()).method("populaAreas").will(returnValue(areas));
		manager.expects(once()).method("update").with(eq(conhecimento));

		assertEquals("success", action.update());
		assertEquals(areas, action.getConhecimento().getAreaOrganizacionals());
		assertEquals(empresa.getId(), action.getEmpresaSistema().getId());
	}
	
	public void testUpdateException() throws Exception
	{
		Exception exception = null;
		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();
		
    	try
		{
    		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
    		conhecimento.setNome("conhecimento");

    		action.setConhecimento(conhecimento);
    		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    		
    		competenciaManager.expects(once()).method("existeNome").will(returnValue(false));
    		areaOrganizacionalManager.expects(once()).method("populaAreas").will(returnValue(areas));
    		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
    		action.update();
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
		
	}

	private Collection<AreaOrganizacional> collectionAreasOrganizacionais() {
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(area);
		return areas;
	}

	public void testGetSet() throws Exception
	{
		action.setConhecimento(null);

		assertNotNull(action.getConhecimento());
		assertTrue(action.getConhecimento() instanceof Conhecimento);
	}
}

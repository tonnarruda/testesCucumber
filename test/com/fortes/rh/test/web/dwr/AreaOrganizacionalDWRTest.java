package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.dwr.AreaOrganizacionalDWR;

public class AreaOrganizacionalDWRTest extends MockObjectTestCase
{
	private AreaOrganizacionalDWR areaOrganizacionalDWR;
	private Mock areaOrganizacionalManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		areaOrganizacionalDWR = new AreaOrganizacionalDWR();

		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		areaOrganizacionalDWR.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		
	}

	public void testVerificaMaternidade()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setAreaMaeId(1L);

		areaOrganizacionalManager.expects(once()).method("verificaMaternidade").with(ANYTHING).will(returnValue(false));
		Exception exc = null;
		try
		{
			areaOrganizacionalDWR.verificaMaternidade(areaOrganizacional.getId());			
		}
		catch (Exception e)
		{
			exc = e;
		}
		
		assertNull(exc);
	}

	public void testVerificaMaternidadeSemAreaMae()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setAreaMaeId(null);

		areaOrganizacionalManager.expects(once()).method("verificaMaternidade").with(ANYTHING).will(returnValue(true));

		Exception exc = null;
		try
		{
			areaOrganizacionalDWR.verificaMaternidade(areaOrganizacional.getId());			
		}
		catch (Exception e)
		{
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	public void testGetByEmpresa() throws Exception
	{
		Long empresaId = 1L;
		areaOrganizacionalManager.expects(once()).method("findAllList").with(eq(empresaId),eq(AreaOrganizacional.TODAS)).will(returnValue(new ArrayList<AreaOrganizacional>()));
		
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(new ArrayList<AreaOrganizacional>()));
		
		assertEquals(0, areaOrganizacionalDWR.getByEmpresa(empresaId).size());
	}
	
	public void testGetByEmpresaTodas() throws Exception
	{
		Long empresaId = -1L;
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		for (AreaOrganizacional areaOrganizacional : areaOrganizacionals) {
			areaOrganizacional.setEmpresa(EmpresaFactory.getEmpresa(1L));
		}
		
		areaOrganizacionalManager.expects(once()).method("findAll").will(returnValue(areaOrganizacionals));
		
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionals));
		
		assertEquals(1, areaOrganizacionalDWR.getByEmpresa(empresaId).size());
	}
	
	public void testGetByEmpresas() throws Exception
	{
		Long empresaId = 0L;
		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();
		((AreaOrganizacional)areaOrganizacionals.toArray()[0]).setEmpresa(EmpresaFactory.getEmpresa(1L));
		
		areaOrganizacionalManager.expects(once()).method("findByEmpresasIds").will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionals));
		
		assertEquals(1, areaOrganizacionalDWR.getByEmpresas(empresaId, new Long[]{1L}).size());
		
		areaOrganizacionalManager.expects(once()).method("findAllList").will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionals));
		empresaId = 1L;
		assertEquals(1, areaOrganizacionalDWR.getByEmpresas(empresaId, new Long[]{1L}).size());
	}
}

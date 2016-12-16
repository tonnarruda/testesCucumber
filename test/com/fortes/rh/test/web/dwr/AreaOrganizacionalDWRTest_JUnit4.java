package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.dwr.AreaOrganizacionalDWR;
import com.fortes.web.tags.CheckBox;

public class AreaOrganizacionalDWRTest_JUnit4
{
	private AreaOrganizacionalDWR areaOrganizacionalDWR;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	
	@Before
	public void setUp() throws Exception
	{
		areaOrganizacionalDWR = new AreaOrganizacionalDWR();

		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		areaOrganizacionalDWR.setAreaOrganizacionalManager(areaOrganizacionalManager);
	}
	
	@Test
	public void testGetCheckboxByEmpresas() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		when(areaOrganizacionalManager.populaCheckComParameters(empresa.getId())).thenReturn(Arrays.asList(new CheckBox()));
		Collection<CheckBox> checkBoxsRetorno = areaOrganizacionalDWR.getCheckboxByEmpresas(new Long[]{empresa.getId()});
		
		assertEquals(1, checkBoxsRetorno.size());
	}
	
}
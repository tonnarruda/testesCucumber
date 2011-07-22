package com.fortes.rh.test.model.geral;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class AreaOrganizacionalTest extends TestCase {

	AreaOrganizacional areaOrganizacional;
	
	public void setUp() {
	}
		
	public void testGetDescricao() 
	{
		AreaOrganizacional areaAvo = AreaOrganizacionalFactory.getEntity(1L);
		areaAvo.setNome("Avo");
		
		AreaOrganizacional areaPai = AreaOrganizacionalFactory.getEntity(2L);
		areaPai.setNome("Pai");
		
		AreaOrganizacional areaFilho = AreaOrganizacionalFactory.getEntity(3L);
		areaFilho.setNome("Filho");
		
		areaPai.setAreaMae(areaAvo);
		areaFilho.setAreaMae(areaPai);
		
		assertEquals("Avo", areaAvo.getDescricao());
		assertEquals("Avo > Pai", areaPai.getDescricao());
		assertEquals("Avo > Pai > Filho", areaFilho.getDescricao());
	}
	
	public void testGetDescricaoIds() 
	{
		AreaOrganizacional areaAvo = AreaOrganizacionalFactory.getEntity(1L);
		areaAvo.setNome("Avo");
		
		AreaOrganizacional areaPai = AreaOrganizacionalFactory.getEntity(2L);
		areaPai.setNome("Pai");
		
		AreaOrganizacional areaFilho = AreaOrganizacionalFactory.getEntity(3L);
		areaFilho.setNome("Filho");
		
		areaPai.setAreaMae(areaAvo);
		areaFilho.setAreaMae(areaPai);
		
		assertEquals(1, areaAvo.getDescricaoIds().size());
		assertEquals(2, areaPai.getDescricaoIds().size());
		assertEquals(3, areaFilho.getDescricaoIds().size());
	}
	
	public void testGetDescricaoComEmpresa() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("Super");
		
		AreaOrganizacional areaAvo = AreaOrganizacionalFactory.getEntity(1L);
		areaAvo.setEmpresa(empresa);
		areaAvo.setNome("Avo");
		
		AreaOrganizacional areaPai = AreaOrganizacionalFactory.getEntity(2L);
		areaPai.setEmpresa(empresa);
		areaPai.setNome("Pai");
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(2L);
		area.setEmpresa(empresa);
		area.setNome("Sem Pai");
		
		areaPai.setAreaMae(areaAvo);
		
		assertEquals("Super - Avo", areaAvo.getDescricaoComEmpresa());
		assertEquals("Super - Avo > Pai", areaPai.getDescricaoComEmpresa());
		assertEquals("Super - Sem Pai", area.getDescricaoComEmpresa());
	}
	
}

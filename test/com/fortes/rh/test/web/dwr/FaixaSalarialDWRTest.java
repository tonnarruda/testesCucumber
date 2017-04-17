package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.web.dwr.FaixaSalarialDWR;

public class FaixaSalarialDWRTest
{
	private FaixaSalarialDWR faixaSalarialDWR;
	private FaixaSalarialManager faixaSalarialManager;

	@Before
	public void setUp() throws Exception
	{
		faixaSalarialDWR = new FaixaSalarialDWR();
		faixaSalarialManager = mock(FaixaSalarialManager.class);
		faixaSalarialDWR.setFaixaSalarialManager(faixaSalarialManager);
	}

	@Test
	public void testGetFaixas()
	{
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		cargo.setFaixaSalarials(new ArrayList<FaixaSalarial>());
		cargo.getFaixaSalarials().add(faixaSalarial);

		when(faixaSalarialManager.findFaixaSalarialByCargo(cargo.getId())).thenReturn(cargo.getFaixaSalarials());

		assertEquals(2, faixaSalarialDWR.getFaixas(cargo.getId().toString()).size());
	}
	
	@Test
	public void testGetByEmpresa() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setEmpresa(empresa);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		cargo.setFaixaSalarials(new ArrayList<FaixaSalarial>());
		cargo.getFaixaSalarials().add(faixaSalarial);
		
		when(faixaSalarialManager.findAllSelectByCargo(empresa.getId())).thenReturn(cargo.getFaixaSalarials());
		
		assertEquals(1, faixaSalarialDWR.getByEmpresas(empresa.getId(), null).size());
	}
	
	@Test
	public void testGetCargosFaixasByArea(){
		
		Long areaOrganizacionalId = 1L;
		Long empresaId = 2L;
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		
		Collection<FaixaSalarial> faixaSalarials = new ArrayList<FaixaSalarial>();
		faixaSalarials.add(faixaSalarial);
		
		when(faixaSalarialManager.getCargosFaixaByAreaIdAndEmpresaId(areaOrganizacionalId, empresaId, null)).thenReturn(faixaSalarials);
		
		assertTrue(faixaSalarialDWR.getCargosFaixasByArea(areaOrganizacionalId, empresaId).containsKey(faixaSalarial.getId()));
	}
}
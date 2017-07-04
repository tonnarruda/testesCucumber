package com.fortes.rh.test.web.dwr;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.util.EmpresaUtil;
import com.fortes.rh.web.dwr.CargoDWR;

public class CargoDWRTest_Junit4
{
	private CargoDWR cargoDWR;
	private CargoManager cargoManager;

	@Before
	public void setUp()
	{
		cargoDWR = new CargoDWR();
		cargoManager = mock(CargoManager.class);
		cargoDWR.setCargoManager(cargoManager);
	}
	
	@Test
	public void testGetByEmpresasEAreaRetornoVazio()
    {
	    Long[] idsEmpresasPermitidas = new Long[]{1L, 2L};
	    Long[] idsEmpresasSeleciondas = null;
	    Long[] areaOrganizacionalIds = null;
	    boolean exibirSomenteCargoVinculadoComAreasSelecionadas = true;
	    
        when(cargoManager.findCargoByEmpresaEArea(EmpresaUtil.empresasSelecionadas(idsEmpresasSeleciondas, idsEmpresasPermitidas), areaOrganizacionalIds, exibirSomenteCargoVinculadoComAreasSelecionadas)).thenReturn(new ArrayList<Cargo>());
        assertEquals(0, cargoDWR.getByEmpresasEArea(idsEmpresasPermitidas, idsEmpresasSeleciondas, areaOrganizacionalIds, exibirSomenteCargoVinculadoComAreasSelecionadas).size());    
    }
	
   @SuppressWarnings("unchecked")
   @Test
   public void testGetByEmpresasEAreaComCargoInativo()
   {
       Long[] idsEmpresasPermitidas = new Long[]{1L, 2L};
       Long[] idsEmpresasSeleciondas = null;
       Long[] areaOrganizacionalIds = null;
       boolean exibirSomenteCargoVinculadoComAreasSelecionadas = true;
       
       Empresa empresa = EmpresaFactory.getEmpresa();
       empresa.setNome("Fortes");
       
       Cargo cargoInativo = CargoFactory.getEntity("Desenvolvedor 1");
       cargoInativo.setAtivo(false);
        
       when(cargoManager.findCargoByEmpresaEArea(EmpresaUtil.empresasSelecionadas(idsEmpresasSeleciondas, idsEmpresasPermitidas), areaOrganizacionalIds, 
               exibirSomenteCargoVinculadoComAreasSelecionadas)).thenReturn(Arrays.asList(cargoInativo));
       
       Map<String, String> retorno = cargoDWR.getByEmpresasEArea(idsEmpresasPermitidas, idsEmpresasSeleciondas, areaOrganizacionalIds, exibirSomenteCargoVinculadoComAreasSelecionadas);
       
       assertEquals(1, retorno.size());
       assertTrue(retorno.containsValue(cargoInativo.getNomeMercadoComEmpresaEStatus()));
   }
}

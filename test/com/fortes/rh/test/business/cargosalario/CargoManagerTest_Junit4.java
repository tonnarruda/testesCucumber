package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.CargoManagerImpl;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.model.cargosalario.Cargo;

public class CargoManagerTest_Junit4
{
    CargoManagerImpl cargoManager;
    CargoDao cargoDao;

    @Before
    public void setUp() throws Exception
    {
        cargoManager = new CargoManagerImpl();
        cargoDao = mock(CargoDao.class);
        cargoManager.setDao(cargoDao);
    }
    
    @Test
    public void testFindCargoByEmpresaEArea() {
        Long[] empresasIds = new Long[]{1L} ;
        Long[] areaOrganizacionaisIds = new Long[]{1L}; 
        boolean exibirSomenteCargosVinculadosAsAreasSeleciondas = true;
        
        when(cargoDao.findCargoByEmpresaEArea(empresasIds, areaOrganizacionaisIds, exibirSomenteCargosVinculadosAsAreasSeleciondas)).thenReturn(new ArrayList<Cargo>());
        assertEquals(0, cargoManager.findCargoByEmpresaEArea(empresasIds, areaOrganizacionaisIds, exibirSomenteCargosVinculadosAsAreasSeleciondas).size());
        verify(cargoDao, only()).findCargoByEmpresaEArea(empresasIds, areaOrganizacionaisIds, exibirSomenteCargosVinculadosAsAreasSeleciondas);
    }
}
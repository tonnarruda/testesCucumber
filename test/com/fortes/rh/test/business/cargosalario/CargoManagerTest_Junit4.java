package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import com.fortes.rh.business.cargosalario.CargoManagerImpl;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.util.SpringUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class CargoManagerTest_Junit4
{
    private CargoDao cargoDao;
    private CargoManagerImpl cargoManager;
    private EmpresaManager empresaManager;
    private AreaFormacaoManager areaFormacaoManager;
    private FaixaSalarialManager faixaSalarialManager;
    private PlatformTransactionManager transactionManager;
    private GrupoOcupacionalManager grupoOcupacionalManager; 

    @Before
    public void setUp() throws Exception
    {
        cargoManager = new CargoManagerImpl();
        cargoDao = mock(CargoDao.class);
        
        faixaSalarialManager = mock(FaixaSalarialManager.class);
        transactionManager = mock(PlatformTransactionManager.class);
        areaFormacaoManager = mock(AreaFormacaoManager.class);
        grupoOcupacionalManager = mock(GrupoOcupacionalManager.class);
        empresaManager = mock(EmpresaManager.class);
        
        cargoManager.setDao(cargoDao);
        cargoManager.setAreaFormacaoManager(areaFormacaoManager);
        cargoManager.setTransactionManager(transactionManager);
        cargoManager.setEmpresaManager(empresaManager);
        PowerMockito.mockStatic(SpringUtil.class);
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
    
    @Test
    public void testSincronizar()
    {
        BDDMockito.given(SpringUtil.getBean("faixaSalarialManager")).willReturn(faixaSalarialManager);
        BDDMockito.given(SpringUtil.getBean("grupoOcupacionalManager")).willReturn(grupoOcupacionalManager);
        
        Long empresaOrigemId = 1L;
        Map<Long, Long> areaIds = new HashMap<Long, Long>();
        Map<Long, Long> conhecimentoIds = new HashMap<Long, Long>();
        Map<Long, Long> habilidadeIds = new HashMap<Long, Long>();
        Map<Long, Long> atitudeIds = new HashMap<Long, Long>();
        
        Empresa empresaOrigem = EmpresaFactory.getEmpresa(empresaOrigemId);
        
        Collection<Cargo> cargos = new ArrayList<Cargo>();
        Cargo cargo = CargoFactory.getEntity(1L);
        cargos.add(cargo);
        
        when(empresaManager.findByIdProjection(empresaOrigemId)).thenReturn(empresaOrigem);
        when(cargoDao.findSincronizarCargos(empresaOrigemId)).thenReturn(cargos);
        
        Collection<AreaFormacao> areasFormacao = new ArrayList<AreaFormacao>(); 
        when(areaFormacaoManager.findByCargo(cargo.getId())).thenReturn(areasFormacao);
        
        when(transactionManager.getTransaction(any(TransactionDefinition.class))).thenReturn(null);
        
        cargoManager.sincronizar(empresaOrigemId, EmpresaFactory.getEmpresa(), areaIds, conhecimentoIds, habilidadeIds, atitudeIds, null);
    }

	@Test
	public void testFindCollectionByIdProjection() {
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		Cargo cargo = CargoFactory.getEntity(1L);
		Cargo cargo2 = CargoFactory.getEntity(2L);

		cargos.add(cargo);

		Long[] cargosIds = new Long[] { cargo.getId(), cargo2.getId() };
		
		when(cargoDao.findCollectionByIdProjection(cargosIds)).thenReturn(cargos);

		Collection<Cargo> cargoCollections = cargoManager.findCollectionByIdProjection(cargosIds);
		
		assertEquals(cargos.size(), cargoCollections.size());
	}
	
    @Test
    public void testFindCargos()
    {

        Collection<Cargo> cargos = new ArrayList<Cargo>();
        Long empresaId = 1L;

        when(cargoDao.findCargos(eq(1), eq(15), eq(empresaId), anyLong(), anyString(), anyBoolean(), eq(false))).thenReturn(cargos); 

        assertEquals(cargos, cargoManager.findCargos(1, 15, empresaId, null, null, null, false));
    }
}
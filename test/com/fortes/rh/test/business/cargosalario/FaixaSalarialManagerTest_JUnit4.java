package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManagerImpl;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientCargo;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class FaixaSalarialManagerTest_JUnit4 
{
    FaixaSalarialManagerImpl faixaSalarialManager = null;
    FaixaSalarialDao faixaSalarialDao = null;
    FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
    FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;
    ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
    AcPessoalClientCargo acPessoalClientCargo;
    EmpresaManager empresaManager;
    private PlatformTransactionManager transactionManager;

    @Before
    public void setUp() throws Exception {
        faixaSalarialManager = new FaixaSalarialManagerImpl();
        empresaManager = mock(EmpresaManager.class);
        faixaSalarialDao = mock(FaixaSalarialDao.class);
        acPessoalClientCargo = mock(AcPessoalClientCargo.class);
        transactionManager = mock(PlatformTransactionManager.class);
        faixaSalarialHistoricoDao = mock(FaixaSalarialHistoricoDao.class);
        faixaSalarialHistoricoManager = mock(FaixaSalarialHistoricoManager.class);
        configuracaoNivelCompetenciaManager = mock(ConfiguracaoNivelCompetenciaManager.class);

        faixaSalarialManager.setDao(faixaSalarialDao);
        faixaSalarialManager.setTransactionManager(transactionManager);
        faixaSalarialManager.setAcPessoalClientCargo(acPessoalClientCargo);
        faixaSalarialManager.setFaixaSalarialHistoricoManager(faixaSalarialHistoricoManager);
        faixaSalarialManager.setConfiguracaoNivelCompetenciaManager(configuracaoNivelCompetenciaManager);

        PowerMockito.mockStatic(SpringUtil.class);
    }

	@Test
	public void testSincronizar() throws Exception {
		Empresa empresaDestino = EmpresaFactory.getEmpresa();
		empresaDestino.setAcIntegra(true);

		Cargo cargoOrigem = CargoFactory.getEntity(2L);
		Cargo cargoDestino = CargoFactory.getEntity(5L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(99L);		
		faixaSalarial.setCargo(cargoOrigem);
		faixaSalarial.setNome("I");
		Collection<FaixaSalarial> faixas = new ArrayList<FaixaSalarial>();
		faixas.add(faixaSalarial);
		
		FaixaSalarial faixaSalarialDepoisDoSave = FaixaSalarialFactory.getEntity(130L);
		faixaSalarialDepoisDoSave.setCargo(CargoFactory.getEntity(12L));
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(faixaSalarial, new Date(), StatusRetornoAC.CONFIRMADO);

		when(faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), null, empresaDestino, "")).thenReturn(faixaSalarialHistorico);
		when(acPessoalClientCargo.criarCargo(faixaSalarial, faixaSalarialHistorico, empresaDestino)).thenReturn("codigo");
		
		assertEquals("Não é possível importar a faixa salarial I do cargo nome, pois não possue CBO.", faixaSalarialManager.sincronizar(faixas, cargoDestino, empresaDestino, null).toArray()[0]);
	}
	
    @Test
    public void testSincronizarComHistoricoDaFaixaNula() throws Exception {
        Empresa empresaDestino = EmpresaFactory.getEmpresa();
        empresaDestino.setAcIntegra(true);

        Cargo cargoOrigem = CargoFactory.getEntity(2L);
        Cargo cargoDestino = CargoFactory.getEntity(5L);

        FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(99L);
        faixaSalarial.setCargo(cargoOrigem);
        faixaSalarial.setNome("I");
        faixaSalarial.setCodigoCbo("791120");
        Collection<FaixaSalarial> faixas = new ArrayList<FaixaSalarial>();
        faixas.add(faixaSalarial);

        FaixaSalarial faixaSalarialDepoisDoSave = FaixaSalarialFactory.getEntity(130L);
        faixaSalarialDepoisDoSave.setCargo(CargoFactory.getEntity(12L));

        when(faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), null, empresaDestino, "")).thenReturn(null);
        when(acPessoalClientCargo.createOrUpdateCargo(faixaSalarial, empresaDestino)).thenReturn("codigo");

        faixaSalarialManager.sincronizar(faixas, cargoDestino, empresaDestino, null);
        verify(faixaSalarialDao).save(faixaSalarial);
        verify(faixaSalarialDao).updateCodigoAC("codigo", faixaSalarial.getId());
    }
    
    @Test
	public void testUpdateFaixaSalarialEmpresaIntegradaENaoAderiuAoESocial()
    {
        BDDMockito.given(SpringUtil.getBean("empresaManager")).willReturn(empresaManager);
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresa.setAcIntegra(true);

        Cargo cargo = CargoFactory.getEntity();
        cargo.setId(1L);

        FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
        faixaSalarial.setCargo(cargo);
        faixaSalarial.setCodigoAC("001");

        Exception exception = null;
        try
        {
            when(transactionManager.getTransaction(any(TransactionDefinition.class))).thenReturn(null);
            when(empresaManager.findEntidadeComAtributosSimplesById(empresa.getId())).thenReturn(empresa);
            
            doNothing().when(faixaSalarialDao).update(faixaSalarial);
            when(acPessoalClientCargo.createOrUpdateCargo(faixaSalarial, empresa)).thenReturn("sucesso");
            faixaSalarialManager.updateFaixaSalarial(faixaSalarial, empresa.getId(), null);
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNull(exception);
    }
    
    @Test
    public void testUpdateFaixaSalarialEmpresaIntegradaEAderiuAoESocial()
    {
        BDDMockito.given(SpringUtil.getBean("empresaManager")).willReturn(empresaManager);
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresa.setAcIntegra(true);
        empresa.setAderiuAoESocial(true);

        Cargo cargo = CargoFactory.getEntity();
        cargo.setId(1L);

        FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
        faixaSalarial.setCargo(cargo);
        faixaSalarial.setCodigoAC("001");

        Exception exception = null;
        try
        {
            when(transactionManager.getTransaction(any(TransactionDefinition.class))).thenReturn(null);
            when(empresaManager.findEntidadeComAtributosSimplesById(empresa.getId())).thenReturn(empresa);
            
            doNothing().when(faixaSalarialDao).update(faixaSalarial);
            faixaSalarialManager.updateFaixaSalarial(faixaSalarial, empresa.getId(), null);
            verify(acPessoalClientCargo,never()).createOrUpdateCargo(faixaSalarial, empresa);
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNull(exception);
    }
    
    @Test
    public void testUpdateFaixaSalarialCodigoACNull()
    {
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresa.setAcIntegra(true);

        Cargo cargo = CargoFactory.getEntity();
        cargo.setId(1L);

        FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
        faixaSalarial.setCargo(cargo);
        faixaSalarial.setCodigoAC("001");

        Exception exception = null;
        try
        {
            when(transactionManager.getTransaction(any(TransactionDefinition.class))).thenReturn(null);
            when(empresaManager.findEntidadeComAtributosSimplesById(empresa.getId())).thenReturn(empresa);
            doNothing().when(faixaSalarialDao).update(faixaSalarial);
            when(acPessoalClientCargo.createOrUpdateCargo(faixaSalarial, empresa)).thenReturn(null);
            faixaSalarialManager.updateFaixaSalarial(faixaSalarial, empresa.getId(), null);
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNotNull(exception);
    }

    @Test
    public void testUpdateFaixaSalarialCriarCargoException()
    {
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresa.setAcIntegra(true);

        Cargo cargo = CargoFactory.getEntity();
        cargo.setId(1L);

        FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
        faixaSalarial.setCargo(cargo);
        faixaSalarial.setCodigoAC("001");

        Exception exception = null;
        try
        {
            when(transactionManager.getTransaction(any(TransactionDefinition.class))).thenReturn(null);
            when(empresaManager.findEntidadeComAtributosSimplesById(empresa.getId())).thenReturn(empresa);
            doNothing().when(faixaSalarialDao).update(faixaSalarial);
            doThrow(exception).when(acPessoalClientCargo).createOrUpdateCargo(faixaSalarial, empresa);
            faixaSalarialManager.updateFaixaSalarial(faixaSalarial, empresa.getId(), null);
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNotNull(exception);
    }
}
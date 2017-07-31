package com.fortes.rh.test.business.cargosalario;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManagerImpl;
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
import com.fortes.rh.web.ws.AcPessoalClientCargo;

public class FaixaSalarialManagerTest_JUnit4 
{
	FaixaSalarialManagerImpl faixaSalarialManager = null;
	FaixaSalarialDao faixaSalarialDao = null;
	FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;
	ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	AcPessoalClientCargo acPessoalClientCargo;

	@Before
	public void setUp() throws Exception
	{
		faixaSalarialManager = new FaixaSalarialManagerImpl();

		faixaSalarialDao = mock(FaixaSalarialDao.class);
		faixaSalarialManager.setDao(faixaSalarialDao);

		faixaSalarialHistoricoManager = mock(FaixaSalarialHistoricoManager.class);
		faixaSalarialManager.setFaixaSalarialHistoricoManager(faixaSalarialHistoricoManager);

		configuracaoNivelCompetenciaManager = mock(ConfiguracaoNivelCompetenciaManager.class);
		faixaSalarialManager.setConfiguracaoNivelCompetenciaManager(configuracaoNivelCompetenciaManager);
		
		faixaSalarialHistoricoDao = mock(FaixaSalarialHistoricoDao.class);

		acPessoalClientCargo = mock(AcPessoalClientCargo.class);
		faixaSalarialManager.setAcPessoalClientCargo(acPessoalClientCargo);
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

		when(faixaSalarialDao.findByCargo(cargoOrigem.getId())).thenReturn(faixas);
		when(faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), null, empresaDestino, "")).thenReturn(faixaSalarialHistorico);
		when(acPessoalClientCargo.criarCargo(faixaSalarial, faixaSalarialHistorico, empresaDestino)).thenReturn("codigo");
		
		Exception exception = null;
		try {
            faixaSalarialManager.sincronizar(cargoOrigem.getId(), cargoDestino, empresaDestino, null);
        } catch (Exception e) {
            exception = e;
        }
		assertNotNull(exception);
		assertEquals("Não é possível importar cargos que possuem faixa sem o CBO.", exception.getMessage());
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

        when(faixaSalarialDao.findByCargo(cargoOrigem.getId())).thenReturn(faixas);
        when(faixaSalarialHistoricoManager.sincronizar(faixaSalarial.getId(), null, empresaDestino, "")).thenReturn(null);
        when(acPessoalClientCargo.createOrUpdateCargo(faixaSalarial, empresaDestino)).thenReturn("codigo");

        faixaSalarialManager.sincronizar(cargoOrigem.getId(), cargoDestino, empresaDestino, null);
        verify(faixaSalarialDao).save(faixaSalarial);
        verify(faixaSalarialDao).updateCodigoAC("codigo", faixaSalarial.getId());
    }
}
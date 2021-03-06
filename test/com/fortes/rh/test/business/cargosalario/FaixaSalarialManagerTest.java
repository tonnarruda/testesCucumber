package com.fortes.rh.test.business.cargosalario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManagerImpl;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.web.ws.AcPessoalClientCargo;

public class FaixaSalarialManagerTest extends MockObjectTestCase
{
	FaixaSalarialManagerImpl faixaSalarialManager = null;
	Mock faixaSalarialDao = null;
	Mock faixaSalarialHistoricoManager;
	Mock faixaSalarialHistoricoDao;
	Mock configuracaoNivelCompetenciaManager;
	Mock transactionManager;
	Mock acPessoalClientCargo;

	protected void setUp() throws Exception
	{
		super.setUp();
		faixaSalarialManager = new FaixaSalarialManagerImpl();

		faixaSalarialDao = new Mock(FaixaSalarialDao.class);
		faixaSalarialManager.setDao((FaixaSalarialDao) faixaSalarialDao.proxy());

		faixaSalarialHistoricoManager = new Mock(FaixaSalarialHistoricoManager.class);
		faixaSalarialManager.setFaixaSalarialHistoricoManager((FaixaSalarialHistoricoManager) faixaSalarialHistoricoManager.proxy());

		configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
		faixaSalarialManager.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
		
		faixaSalarialHistoricoDao = new Mock(FaixaSalarialHistoricoDao.class);

		transactionManager = new Mock(PlatformTransactionManager.class);
		faixaSalarialManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

		acPessoalClientCargo = mock(AcPessoalClientCargo.class);
		faixaSalarialManager.setAcPessoalClientCargo((AcPessoalClientCargo) acPessoalClientCargo.proxy());
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testFindFaixaSalarialByCargo()
	{
		Collection<FaixaSalarial> faixas = new ArrayList<FaixaSalarial>();
		Long id = 1L;

		faixaSalarialDao.expects(once()).method("findFaixaSalarialByCargo").with(eq(id)).will(returnValue(faixas));

		assertEquals(faixas, faixaSalarialManager.findFaixaSalarialByCargo(id));
	}

	@SuppressWarnings("deprecation")
	public void testFindAll()
	{
		faixaSalarialDao.expects(never()).method("findAll").withNoArguments();

		try
		{
			faixaSalarialManager.findAll();
			fail("Deve considerar a empresa na listagem das Faixas Salariais.");
		}
		catch (NoSuchMethodError noSuchMethodError)
		{
			assertTrue(true);
		}
		catch (Exception e) {
			fail("A Faixa Salarial não pode ser listada sem algum filtro que identifique uma empresa.");
		}

	}

	@SuppressWarnings("deprecation")
	public void testFindAllComOrdenacao()
	{
		faixaSalarialDao.expects(never()).method("findAll").withNoArguments();

		try
		{
			faixaSalarialManager.findAll(new String[]{"Não", "pode", "funcionar"});
			fail("Deve considerar a empresa na listagem das Faixas Salariais.");
		}
		catch (NoSuchMethodError noSuchMethodError)
		{
			assertTrue(true);
		}
		catch (Exception e) {
			fail("A Faixa Salarial não pode ser listada sem algum filtro que identifique uma empresa.");
		}

	}

	public void testFindCodigoACById()
	{
		FaixaSalarial faixaSalarial = new FaixaSalarial();
		faixaSalarial.setId(1L);

		faixaSalarialDao.expects(once()).method("findCodigoACById").with(eq(1L)).will(returnValue(faixaSalarial));

		FaixaSalarial faixa2 = faixaSalarialManager.findCodigoACById(1L);

		Long id = Long.valueOf(1);

		assertEquals(id, faixa2.getId());
	}


	public void testFindAllSelectByCargo()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();

		Collection<FaixaSalarial> faixas = new ArrayList<FaixaSalarial>();
		faixas.add(faixaSalarial);

		faixaSalarialDao.expects(once()).method("findAllSelectByCargo").with(eq(1L)).will(returnValue(faixas));

		Collection<FaixaSalarial> faixasRetorno = faixaSalarialManager.findAllSelectByCargo(1L);

		assertEquals(faixas.size(), faixasRetorno.size());
	}

	public void testVerifyExistsNomeByCargo()
	{
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setNome("TESTE");

		faixaSalarialDao.expects(once()).method("verifyExistsNomeByCargo").with(eq(cargo.getId()), eq(faixaSalarial.getNome())).will(returnValue(true));

		assertEquals(true, faixaSalarialManager.verifyExistsNomeByCargo(cargo.getId(), faixaSalarial.getNome()));
	}
	
	public void testFindByCargoLong()
	{
		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setNome("nome");
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();		
		faixaSalarial.setCargo(cargo);
		faixaSalarial.setNome("I");
		Collection<FaixaSalarial> faixas = new ArrayList<FaixaSalarial>();
		faixas.add(faixaSalarial);
		
		faixaSalarialDao.expects(once()).method("findByCargo").with(eq(cargo.getId())).will(returnValue(faixas));
		
		assertEquals(1, faixaSalarialManager.findByCargo(cargo.getId()).size());
	}
	
	public void testFindByCargo()
	{
		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setNome("nome");
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();		
		faixaSalarial.setCargo(cargo);
		faixaSalarial.setNome("I");
		Collection<FaixaSalarial> faixas = new ArrayList<FaixaSalarial>();
		faixas.add(faixaSalarial);
		
		faixaSalarialDao.expects(once()).method("findByCargo").with(eq(cargo.getId())).will(returnValue(faixas));
		
		assertEquals(1, faixaSalarialManager.findByCargo("1").size());
	}
	
	public void testFindByCargoVazio()
	{
		assertEquals(0, faixaSalarialManager.findByCargo("").size());
	}

	public void testSaveFaixaSalarialSemIntegraAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();

		Exception exception = null;
		try
		{
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
			faixaSalarialDao.expects(once()).method("saveOrUpdate").with(ANYTHING);
			faixaSalarialHistoricoManager.expects(once()).method("save").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING);
			transactionManager.expects(once()).method("commit").with(ANYTHING);
			faixaSalarialManager.saveFaixaSalarial(faixaSalarial, faixaSalarialHistorico, empresa, null);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testSaveFaixaSalarialIntegraAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();

		Exception exception = null;
		try
		{
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
			faixaSalarialDao.expects(once()).method("saveOrUpdate").with(ANYTHING);
			faixaSalarialHistoricoManager.expects(once()).method("save").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING);
			acPessoalClientCargo.expects(once()).method("criarCargo").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue("001"));
			faixaSalarialDao.expects(once()).method("updateCodigoAC").with(ANYTHING, ANYTHING);
			transactionManager.expects(once()).method("commit").with(ANYTHING);

			faixaSalarialManager.saveFaixaSalarial(faixaSalarial, faixaSalarialHistorico, empresa, null);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testSaveFaixaSalarialIntegraACExceptionSave()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial.setCodigoAC("001");

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();

		Exception exception = null;
		try
		{
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
			faixaSalarialDao.expects(once()).method("saveOrUpdate").with(ANYTHING).will(throwException(exception));
			transactionManager.expects(once()).method("rollback").with(ANYTHING);
			faixaSalarialManager.saveFaixaSalarial(faixaSalarial, faixaSalarialHistorico, empresa, null);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testSaveFaixaSalarialIntegraACExceptionCriarCargo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial.setCodigoAC("001");

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();

		Exception exception = null;
		try
		{
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
			faixaSalarialDao.expects(once()).method("saveOrUpdate").with(ANYTHING);
			faixaSalarialHistoricoManager.expects(once()).method("save").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING);
			acPessoalClientCargo.expects(once()).method("criarCargo").with(ANYTHING,ANYTHING,ANYTHING).will(throwException(exception));
			transactionManager.expects(once()).method("rollback").with(ANYTHING);
			faixaSalarialManager.saveFaixaSalarial(faixaSalarial, faixaSalarialHistorico, empresa, null);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testTransfereFaixasCargo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);

		Cargo cargoDestino = CargoFactory.getEntity();
		cargoDestino.setNome("Cargo Destino");
		cargoDestino.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(100L);
		faixaSalarial.setCargo(cargoDestino);
		faixaSalarial.setNome("Nova Faixa");

		Exception exception = null;
		FaixaSalarial faixaTmpCodigoAC = FaixaSalarialFactory.getEntity(300L);
		faixaTmpCodigoAC.setCodigoAC("001");
		

		try {
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
			faixaSalarialDao.expects(once()).method("updateNomeECargo").with(eq(faixaSalarial.getId()), eq(cargoDestino.getId()), eq(faixaSalarial.getNome()));
			faixaSalarialDao.expects(once()).method("findCodigoACById").with(eq(faixaSalarial.getId())).will(returnValue(faixaTmpCodigoAC));
			acPessoalClientCargo.expects(once()).method("createOrUpdateCargo").with(ANYTHING,ANYTHING).will(returnValue("001"));
			transactionManager.expects(once()).method("commit").with(ANYTHING);
			faixaSalarialManager.transfereFaixasCargo(faixaSalarial, cargoDestino, empresa);
		} catch (Exception e) {
			e = exception;
		}
		assertNull(exception);
	}

	public void testTransfereFaixasCargoExceptionUpdate()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);

		Cargo cargoDestino = CargoFactory.getEntity();
		cargoDestino.setNome("Cargo Destino");
		cargoDestino.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargoDestino);
		faixaSalarial.setCodigoAC("001");
		faixaSalarial.setNome("Nova Faixa");

		Exception exception = null;

		try {
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
			faixaSalarialDao.expects(once()).method("updateNomeECargo").with(eq(faixaSalarial.getId()), eq(cargoDestino.getId()), eq(faixaSalarial.getNome())).will(throwException(exception));
			transactionManager.expects(once()).method("rollback").with(ANYTHING);

			faixaSalarialManager.transfereFaixasCargo(faixaSalarial, cargoDestino, empresa);
		} catch (Exception e) {
			exception = e;
		}
		assertNotNull(exception);
	}

	public void testDeleteFaixaSalarial()
	{
		FaixaSalarialHistorico historico = new FaixaSalarialHistorico();
		historico.setId(1l);
		Collection<FaixaSalarialHistorico> historicos = new ArrayList<FaixaSalarialHistorico>(1);
		historicos.add(historico);
		Empresa empresa = EmpresaFactory.getEmpresa();

		Cargo cargo = CargoFactory.getEntity();
		cargo.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial.setCodigoAC("001");


		Exception exception = null;
		try
		{
			faixaSalarialHistoricoManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(historicos));
			faixaSalarialDao.expects(once()).method("remove").with(ANYTHING);
			faixaSalarialHistoricoManager.expects(once()).method("remove").withAnyArguments();
			faixaSalarialManager.deleteFaixaSalarial(faixaSalarial.getId(), empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}
	public void testDeleteFaixaSalarialIntegrandoComAC()
	{
		FaixaSalarialHistorico historico = new FaixaSalarialHistorico();
		historico.setId(1l);
		Collection<FaixaSalarialHistorico> historicos = new ArrayList<FaixaSalarialHistorico>(1);
		historicos.add(historico);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial.setCodigoAC("001");

		faixaSalarialHistoricoManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(historicos));

		Exception exception = null;
		try
		{
			faixaSalarialDao.expects(once()).method("findCodigoACById").withAnyArguments().will(returnValue(faixaSalarial));
			faixaSalarialDao.expects(once()).method("remove").with(ANYTHING);
			acPessoalClientCargo.expects(once()).method("deleteCargo").with(ANYTHING,ANYTHING).will(returnValue(true));
			faixaSalarialHistoricoManager.expects(once()).method("remove").withAnyArguments();
			faixaSalarialManager.deleteFaixaSalarial(faixaSalarial.getId(), empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testDeleteFaixaSalarialDeleteCargoACException()
	{
		FaixaSalarialHistorico historico = new FaixaSalarialHistorico();
		historico.setId(1l);
		Collection<FaixaSalarialHistorico> historicos = new ArrayList<FaixaSalarialHistorico>(1);
		historicos.add(historico);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		faixaSalarial.setCodigoAC("001");

		Exception exception = null;
		try
		{
			faixaSalarialDao.expects(once()).method("findCodigoACById").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
			faixaSalarialHistoricoManager.expects(once()).method("findAllSelect").with(eq(faixaSalarial.getId())).will(returnValue(new ArrayList<FaixaSalarialHistorico>()));
			faixaSalarialHistoricoManager.expects(once()).method("remove").withAnyArguments();
			faixaSalarialDao.expects(once()).method("remove").with(eq(faixaSalarial.getId()));
			acPessoalClientCargo.expects(once()).method("deleteCargo").with(ANYTHING,ANYTHING).will(throwException(exception));
			faixaSalarialManager.deleteFaixaSalarial(faixaSalarial.getId(), empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testDeleteFaixaSalarialDeleteCargoACFalse()
	{
		FaixaSalarialHistorico historico = new FaixaSalarialHistorico();
		historico.setId(1l);
		Collection<FaixaSalarialHistorico> historicos = new ArrayList<FaixaSalarialHistorico>(1);
		historicos.add(historico);

		Empresa empresa = EmpresaFactory.getEmpresa();

		Cargo cargo = CargoFactory.getEntity();
		cargo.setId(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		faixaSalarial.setCodigoAC("001");

		Exception exception = null;
		try
		{
			faixaSalarialHistoricoManager.expects(once()).method("findAllSelect").with(eq(faixaSalarial.getId())).will(returnValue(new ArrayList<FaixaSalarialHistorico>()));
			faixaSalarialHistoricoManager.expects(once()).method("remove").withAnyArguments();
			faixaSalarialDao.expects(once()).method("remove").with(eq(faixaSalarial.getId()));
			faixaSalarialManager.deleteFaixaSalarial(faixaSalarial.getId(), empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testFindByFaixaSalarialId()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setId(1L);

		faixaSalarialDao.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));

		FaixaSalarial faixaRetorno = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());

		assertEquals(faixaSalarial.getId(), faixaRetorno.getId());
	}

	public void testFindFaixas()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Collection<FaixaSalarial> faixaSalarials = new ArrayList<FaixaSalarial>();
		faixaSalarials.add(FaixaSalarialFactory.getEntity(1L));

		faixaSalarialDao.expects(once()).method("findFaixas").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(faixaSalarials));

		Collection<FaixaSalarial> retorno = faixaSalarialManager.findFaixas(empresa, Cargo.TODOS, null);

		assertEquals(faixaSalarials.size(), retorno.size());
	}

	public void testFindHistoricoAtual()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		faixaSalarialDao.expects(once()).method("findHistoricoAtual").with(ANYTHING, ANYTHING).will(returnValue(faixaSalarial));

		FaixaSalarial retorno = faixaSalarialManager.findHistoricoAtual(faixaSalarial.getId());

		assertEquals(faixaSalarial.getId(), retorno.getId());
	}

	public void testFindHistorico()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		Date data = new Date();

		faixaSalarialDao.expects(once()).method("findHistoricoAtual").with(ANYTHING, ANYTHING).will(returnValue(faixaSalarial));

		FaixaSalarial retorno = faixaSalarialManager.findHistorico(faixaSalarial.getId(), data);

		assertEquals(faixaSalarial.getId(), retorno.getId());
	}

	public void testRemoveFaixaAndHistoricos() throws Exception
	{
		Long[] faixaSalarialIds = new Long[] {1L};

		
		configuracaoNivelCompetenciaManager.expects(once()).method("removeByFaixas").with(eq(faixaSalarialIds));
		faixaSalarialHistoricoManager.expects(once()).method("removeByFaixas").with(eq(faixaSalarialIds));
		faixaSalarialDao.expects(once()).method("remove").with(eq(faixaSalarialIds));

		faixaSalarialManager.removeFaixaAndHistoricos(faixaSalarialIds);
	}

	public void testFindFaixaSalarialByCodigoAc() throws Exception
	{
		String faixaCodigoAC = "001";
		String empresaCodigoAC = "002";

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		faixaSalarialDao.expects(once()).method("findFaixaSalarialByCodigoAc").with(eq(faixaCodigoAC), eq(empresaCodigoAC), eq("XXX")).will(returnValue(faixaSalarial));

		FaixaSalarial retorno = faixaSalarialManager.findFaixaSalarialByCodigoAc(faixaCodigoAC, empresaCodigoAC, "XXX");

		assertEquals(faixaSalarial.getId(), retorno.getId());
	}
	
	public void testMontaFaixa()
	{
		TCargo tCargo = new TCargo();
		tCargo.setCodigo("02569");
		tCargo.setDescricao("Desc");
		tCargo.setDescricaoACPessoal("desc AC");
		
		FaixaSalarial faixaSalarial = faixaSalarialManager.montaFaixa(tCargo);
		assertEquals(tCargo.getCodigo(), faixaSalarial.getCodigoAC());
		assertEquals(tCargo.getDescricao(), faixaSalarial.getNome());
		assertEquals(tCargo.getDescricaoACPessoal(), faixaSalarial.getNomeACPessoal());
	}
	
	public void testdeleteFaixaSalarialByIds()
	{
		TCargo tCargo = new TCargo();
		tCargo.setCodigo("02569");
		tCargo.setDescricao("Desc");
		tCargo.setDescricaoACPessoal("desc AC");
		
		FaixaSalarial faixaSalarial = faixaSalarialManager.montaFaixa(tCargo);
		assertEquals(tCargo.getCodigo(), faixaSalarial.getCodigoAC());
		assertEquals(tCargo.getDescricao(), faixaSalarial.getNome());
		assertEquals(tCargo.getDescricaoACPessoal(), faixaSalarial.getNomeACPessoal());
	}
	
	public void testGeraDadosRelatorioResumidoColaboradoresPorCargoXLSComVariosColaboradoresNoUltimoCargo() {
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity(1L);
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity(2L);
		FaixaSalarial faixaSalarial3 = FaixaSalarialFactory.getEntity(3L);
		FaixaSalarial faixaSalarial4 = FaixaSalarialFactory.getEntity(4L);
		FaixaSalarial faixaSalarial5 = FaixaSalarialFactory.getEntity(5L);
		
		HistoricoColaborador historicoColaborador1FaixaSalarial1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1FaixaSalarial1.setFaixaSalarial(faixaSalarial1);
		HistoricoColaborador historicoColaborador2FaixaSalarial1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2FaixaSalarial1.setFaixaSalarial(faixaSalarial1);
		
		HistoricoColaborador historicoColaborador1FaixaSalarial2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1FaixaSalarial2.setFaixaSalarial(faixaSalarial2);
			
		HistoricoColaborador historicoColaborador1FaixaSalarial3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1FaixaSalarial3.setFaixaSalarial(faixaSalarial3);
		HistoricoColaborador historicoColaborador2FaixaSalarial3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2FaixaSalarial3.setFaixaSalarial(faixaSalarial3);
		HistoricoColaborador historicoColaborador3FaixaSalarial3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3FaixaSalarial3.setFaixaSalarial(faixaSalarial3);
		
		HistoricoColaborador historicoColaborador1FaixaSalarial4 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1FaixaSalarial4.setFaixaSalarial(faixaSalarial4);
		
		HistoricoColaborador historicoColaborador1FaixaSalarial5 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1FaixaSalarial5.setFaixaSalarial(faixaSalarial5);
		HistoricoColaborador historicoColaborador2FaixaSalarial5 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2FaixaSalarial5.setFaixaSalarial(faixaSalarial5);
		HistoricoColaborador historicoColaborador3FaixaSalarial5 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3FaixaSalarial5.setFaixaSalarial(faixaSalarial5);
		HistoricoColaborador historicoColaborador4FaixaSalarial5 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador4FaixaSalarial5.setFaixaSalarial(faixaSalarial5);
		HistoricoColaborador historicoColaborador5FaixaSalarial5 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador5FaixaSalarial5.setFaixaSalarial(faixaSalarial5);
		
		List<HistoricoColaborador> historicoColaboradores = Arrays.asList(historicoColaborador1FaixaSalarial1, historicoColaborador2FaixaSalarial1, historicoColaborador1FaixaSalarial2, historicoColaborador1FaixaSalarial3,
				historicoColaborador2FaixaSalarial3, historicoColaborador3FaixaSalarial3, historicoColaborador1FaixaSalarial4, historicoColaborador1FaixaSalarial5, historicoColaborador2FaixaSalarial5,
				historicoColaborador3FaixaSalarial5, historicoColaborador4FaixaSalarial5,historicoColaborador5FaixaSalarial5);
		
		List<FaixaSalarial> faixaSalarials = (List<FaixaSalarial>) faixaSalarialManager.geraDadosRelatorioResumidoColaboradoresPorCargoXLS(historicoColaboradores);
		assertEquals(5, faixaSalarials.size());
		assertEquals(2, faixaSalarials.get(0).getQtdColaboradores());
		assertEquals(1, faixaSalarials.get(1).getQtdColaboradores());
		assertEquals(3, faixaSalarials.get(2).getQtdColaboradores());
		assertEquals(1, faixaSalarials.get(3).getQtdColaboradores());
		assertEquals(5, faixaSalarials.get(4).getQtdColaboradores());
	}
	
	public void testGeraDadosRelatorioResumidoColaboradoresPorCargoXLSComApenasUmColaboradorNoUltimoCargo() {
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity(1L);
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity(2L);
		FaixaSalarial faixaSalarial3 = FaixaSalarialFactory.getEntity(3L);
		FaixaSalarial faixaSalarial4 = FaixaSalarialFactory.getEntity(5L);
		
		HistoricoColaborador historicoColaborador1FaixaSalarial1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1FaixaSalarial1.setFaixaSalarial(faixaSalarial1);
		HistoricoColaborador historicoColaborador2FaixaSalarial1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2FaixaSalarial1.setFaixaSalarial(faixaSalarial1);
		
		HistoricoColaborador historicoColaborador1FaixaSalarial2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1FaixaSalarial2.setFaixaSalarial(faixaSalarial2);
			
		HistoricoColaborador historicoColaborador1FaixaSalarial3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1FaixaSalarial3.setFaixaSalarial(faixaSalarial3);
		HistoricoColaborador historicoColaborador2FaixaSalarial3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2FaixaSalarial3.setFaixaSalarial(faixaSalarial3);
		HistoricoColaborador historicoColaborador3FaixaSalarial3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3FaixaSalarial3.setFaixaSalarial(faixaSalarial3);
		
		HistoricoColaborador historicoColaborador1FaixaSalarial4 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1FaixaSalarial4.setFaixaSalarial(faixaSalarial4);
		HistoricoColaborador historicoColaborador2FaixaSalarial4 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2FaixaSalarial4.setFaixaSalarial(faixaSalarial4);
		
		List<HistoricoColaborador> historicoColaboradores = Arrays.asList(historicoColaborador1FaixaSalarial1, historicoColaborador2FaixaSalarial1, historicoColaborador1FaixaSalarial2, historicoColaborador1FaixaSalarial3,
				historicoColaborador2FaixaSalarial3, historicoColaborador3FaixaSalarial3, historicoColaborador1FaixaSalarial4,historicoColaborador2FaixaSalarial4);
		
		List<FaixaSalarial> faixaSalarials = (List<FaixaSalarial>) faixaSalarialManager.geraDadosRelatorioResumidoColaboradoresPorCargoXLS(historicoColaboradores);
		assertEquals(4, faixaSalarials.size());
		assertEquals(2, faixaSalarials.get(0).getQtdColaboradores());
		assertEquals(1, faixaSalarials.get(1).getQtdColaboradores());
		assertEquals(3, faixaSalarials.get(2).getQtdColaboradores());
		assertEquals(2, faixaSalarials.get(3).getQtdColaboradores());
	}
}


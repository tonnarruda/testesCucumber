package com.fortes.rh.test.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.cargosalario.CargoManagerImpl;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockHibernateTemplate;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientCargo;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class CargoManagerTest extends MockObjectTestCase
{
	CargoManagerImpl cargoManager;

	Mock cargoDao;
	Mock empresaManager;
	Mock acPessoalClientCargo;
	Mock faixaSalarialManager;
	Mock etapaSeletiva;
	Mock funcaoManager;
	Mock parametrosDoSistemaManager;
	Mock conhecimentoManager;
	Mock areaOrganizacionalManager;
	Mock areaFormacaoManager;
	Mock grupoOcupacionalManager;
	Mock etapaSeletivaManager;
	Mock habilidadeManager;
	Mock atitudeManager;
	Mock experienciaManager;
	Mock quantidadeLimiteColaboradoresPorCargoManager;
	Mock candidatoManager;
	Mock transactionManager;
	

	protected void setUp() throws Exception
	{
		super.setUp();
		cargoManager = new CargoManagerImpl();

		cargoDao = new Mock(CargoDao.class);
		cargoManager.setDao((CargoDao) cargoDao.proxy());

		empresaManager = new Mock(EmpresaManager.class);
		cargoManager.setEmpresaManager((EmpresaManager) empresaManager.proxy());

		acPessoalClientCargo = mock(AcPessoalClientCargo.class);
		cargoManager.setAcPessoalClientCargo((AcPessoalClientCargo) acPessoalClientCargo.proxy());

		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		MockSpringUtil.mocks.put("parametrosDoSistemaManager", parametrosDoSistemaManager);

		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		MockSpringUtil.mocks.put("faixaSalarialManager", faixaSalarialManager);
		
		grupoOcupacionalManager = new Mock(GrupoOcupacionalManager.class); 
		MockSpringUtil.mocks.put("grupoOcupacionalManager", grupoOcupacionalManager);

		funcaoManager = new Mock(FuncaoManager.class);
		MockSpringUtil.mocks.put("funcaoManager", funcaoManager);

		quantidadeLimiteColaboradoresPorCargoManager = new Mock(QuantidadeLimiteColaboradoresPorCargoManager.class);
		MockSpringUtil.mocks.put("quantidadeLimiteColaboradoresPorCargoManager", quantidadeLimiteColaboradoresPorCargoManager);
		
		candidatoManager = new Mock(CandidatoManager.class);
		MockSpringUtil.mocks.put("candidatoManager", candidatoManager);
		
		conhecimentoManager = mock(ConhecimentoManager.class);
		cargoManager.setConhecimentoManager((ConhecimentoManager) conhecimentoManager.proxy());
		
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		cargoManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		
		areaFormacaoManager = mock(AreaFormacaoManager.class);
		cargoManager.setAreaFormacaoManager((AreaFormacaoManager) areaFormacaoManager.proxy());

		etapaSeletivaManager = mock(EtapaSeletivaManager.class);
		cargoManager.setEtapaSeletivaManager((EtapaSeletivaManager) etapaSeletivaManager.proxy());

		atitudeManager = mock(AtitudeManager.class);
		cargoManager.setAtitudeManager((AtitudeManager) atitudeManager.proxy());
		
		habilidadeManager = mock(HabilidadeManager.class);
		cargoManager.setHabilidadeManager((HabilidadeManager) habilidadeManager.proxy());
		
		experienciaManager = mock(ExperienciaManager.class);
		cargoManager.setExperienciaManager((ExperienciaManager) experienciaManager.proxy());
		
		transactionManager = new Mock(PlatformTransactionManager.class);
		cargoManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

		Mockit.redefineMethods(HibernateTemplate.class, MockHibernateTemplate.class);

		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
		MockSecurityUtil.verifyRole = false;
		super.tearDown();
	}

	public void testFindByEmpresaACPorCodigoDaEmpresa() {
		
		String codigoDaEmpresa = "123";
		String codigo = "";
		
		// deveria chamar este método
		cargoDao.expects(once()).method("findByEmpresaAC").with(eq(codigoDaEmpresa), eq("XXX")).will(returnValue(Collections.EMPTY_LIST));
		
		cargoManager.findByEmpresaAC(codigoDaEmpresa, codigo, "XXX");
		
		cargoDao.verify();
	}
	
	public void testFindByEmpresaACPorCodigoDaEmpresaECodigo() {
		
		String codigoDaEmpresa = "123";
		String codigo = "321";
		
		// deveria chamar este método
		cargoDao.expects(once()).method("findByEmpresaAC").with(eq(codigoDaEmpresa), eq(codigo), eq("XXX")).will(returnValue(Collections.EMPTY_LIST));
		
		cargoManager.findByEmpresaAC(codigoDaEmpresa, codigo, "XXX");
		
		cargoDao.verify();
	}
	
	public void testFindAllSelectPorIdDasEmpresas() {
		
		Long[] empresaIds = new Long[] {1L, 2L};
		
		cargoDao.expects(once()).method("findAllSelect").with(eq(empresaIds)).will(returnValue(Collections.EMPTY_LIST));
		
		cargoManager.findAllSelect(empresaIds);
		
		cargoDao.verify();
	}
	
	public void testPopulaCargosComArraysDeLong() {
		
		Long[] ids = new Long[]{1L, 2L};
		
		Collection<Cargo> cargos = cargoManager.populaCargos(ids);
		
		assertEquals(2, cargos.size());
	}
	
	public void testFindAllSelectModuloExterno() {
		
		Long empresaId = 69L;
		String ordenarPor = "nome";
		boolean isModuloExterno = true;
		
		cargoDao.expects(once()).method("findAllSelect").with(eq(new Long[]{empresaId}), eq(ordenarPor), eq(true), eq(isModuloExterno)).will(returnValue(Collections.EMPTY_LIST));
		
		cargoManager.findAllSelect(ordenarPor, true, Cargo.ATIVO, empresaId);
		
		cargoDao.verify();
	}
	
	
	public void testGetCount()
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();

		cargoDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(cargos.size()));

		int retorno = cargoManager.getCount(1L, null, null, null);

		assertEquals(retorno, cargos.size());
	}

	public void testPreparaCargoDoAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Cargo cargo = CargoFactory.getEntity(3L);
		TCargo tCargo = new TCargo(); 
		tCargo.setCargoId(2L);
		
		empresaManager.expects(once()).method("findByCodigoAC").with(ANYTHING, ANYTHING).will(returnValue(empresa));
		cargoDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(cargo));
		cargoDao.expects(once()).method("updateCBO").with(ANYTHING, ANYTHING);
		assertEquals(cargo, cargoManager.preparaCargoDoAC(tCargo));
		
		//novo cargo
		tCargo.setCargoId(0L);
		empresaManager.expects(once()).method("findByCodigoAC").with(ANYTHING, ANYTHING).will(returnValue(empresa));
		cargoDao.expects(once()).method("save").with(ANYTHING).will(returnValue(cargo));
		assertEquals(cargo, cargoManager.preparaCargoDoAC(tCargo));		
	}
	
	public void testFindByGrupoOcupacional()
	{
		cargoDao.expects(once()).method("findByGrupoOcupacional").with(ANYTHING).will(returnValue(new ArrayList<Cargo>()));
		assertEquals(0, cargoManager.findByGrupoOcupacional(null).size());
	}
	
	public void testFindAllSelectDistinctNome()
	{
		cargoDao.expects(once()).method("findAllSelectDistinctNomeMercado").will(returnValue(new ArrayList<Cargo>()));
		assertTrue(cargoManager.findAllSelectDistinctNome(null).isEmpty());
	}

	public void testFindCargos()
	{

		Collection<Cargo> cargos = new ArrayList<Cargo>();
		Long empresaId = 1L;

		cargoDao.expects(once()).method("findCargos").with(new Constraint[] { eq(1), eq(15), eq(empresaId), ANYTHING, ANYTHING, ANYTHING}).will(returnValue(cargos));

		assertEquals(cargos, cargoManager.findCargos(1, 15, empresaId, null, null, null));
	}

	public void testUpdateCargoAC() throws Exception
	{
		Cargo cargo = CargoFactory.getEntity();

		Collection<FaixaSalarial> faixaSalarials = new ArrayList<FaixaSalarial>();

		FaixaSalarial faixaSalarial = new FaixaSalarial();
		faixaSalarial.setCodigoAC("1");

		faixaSalarials.add(faixaSalarial);

		cargo.setFaixaSalarials(faixaSalarials);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);

		cargoDao.expects(atLeastOnce()).method("update").with(ANYTHING);

		cargoManager.update(cargo);

		cargoManager.setAcPessoalClientCargo(new AcPessoalClientCargo());
	}

	public void testRemove()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		Cargo cargo = CargoFactory.getEntity(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);

		Collection<FaixaSalarial> faixasSalariais = new ArrayList<FaixaSalarial>();
		faixasSalariais.add(faixaSalarial);

		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCargo").withAnyArguments().will(returnValue(faixasSalariais));
		funcaoManager.expects(once()).method("removeFuncaoAndHistoricosByCargo").withAnyArguments();
		faixaSalarialManager.expects(once()).method("removeFaixaAndHistoricos").withAnyArguments();
		cargoDao.expects(once()).method("findByIdProjection").withAnyArguments().will(returnValue(cargo));;
		experienciaManager.expects(once()).method("desvinculaCargo").withAnyArguments();
		quantidadeLimiteColaboradoresPorCargoManager.expects(once()).method("deleteByCargo").withAnyArguments();
		candidatoManager.expects(once()).method("deleteCargosPretendidos").withAnyArguments();
		cargoDao.expects(once()).method("remove").withAnyArguments();
		cargoDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		acPessoalClientCargo.expects(once()).method("deleteCargo").with(ANYTHING, ANYTHING);

		try
		{
			cargoManager.remove(cargo.getId(), empresa);
		}
		catch (Exception e)
		{
			assertNull(e);
		}
	}

	public void testFindByGrupoOcupacionalIdsProjection()
	{
		Long[] idsLong = new Long[] { 1L };
		Collection<Cargo> cargos = new ArrayList<Cargo>();

		cargoDao.expects(once()).method("findByGrupoOcupacionalIdsProjection").with(eq(idsLong), ANYTHING, ANYTHING).will(returnValue(cargos));

		assertEquals(cargos, cargoManager.findByGrupoOcupacionalIdsProjection(idsLong, 1L, null));
	}
	
	public void testFindBySolicitacao()
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargoDao.expects(once()).method("findBySolicitacao").with(eq(solicitacao.getId())).will(returnValue(cargos));
		
		assertEquals(cargos, cargoManager.findBySolicitacao(solicitacao.getId()));
	}

	public void testFindByGrupoOcupacionalIdsProjectionComIdsVazio()
	{
		Long[] idsLong = new Long[] {};

		cargoDao.expects(once()).method("findByGrupoOcupacionalIdsProjection").with(eq(null), ANYTHING, ANYTHING).will(returnValue(null));

		assertNull(cargoManager.findByGrupoOcupacionalIdsProjection(idsLong, 1L, null));
	}

	public void testGetCargosFromFaixaSalarialHistoricos()
	{
		Cargo cargo1 = CargoFactory.getEntity(1L);
		Cargo cargo2 = CargoFactory.getEntity(2L);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial1.setCargo(cargo1);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity(2L);
		faixaSalarial2.setCargo(cargo2);

		FaixaSalarial faixaSalarial3 = FaixaSalarialFactory.getEntity(3L);
		faixaSalarial3.setCargo(cargo2);

		FaixaSalarialHistorico faixaSalarialHistorico1 = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico1.setFaixaSalarial(faixaSalarial1);

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity(2L);
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial2);

		FaixaSalarialHistorico faixaSalarialHistorico3 = FaixaSalarialHistoricoFactory.getEntity(3L);
		faixaSalarialHistorico3.setFaixaSalarial(faixaSalarial3);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico1);
		faixaSalarialHistoricos.add(faixaSalarialHistorico2);
		faixaSalarialHistoricos.add(faixaSalarialHistorico3);

		Collection<Cargo> cargos = cargoManager.getCargosFromFaixaSalarialHistoricos(faixaSalarialHistoricos);
		assertEquals(2, cargos.size());
	}

	public void testFindByAreasOrganizacionalIdsProjection()
	{

		Long[] idsLong = new Long[] { 1L };
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		Collection<Cargo> cargosNull = new ArrayList<Cargo>();

		cargoDao.expects(once()).method("findByAreaOrganizacionalIdsProjection").with(eq(idsLong), ANYTHING).will(returnValue(cargos));

		assertEquals(cargos, cargoManager.findByAreasOrganizacionalIdsProjection(idsLong, 1L));

		idsLong = null;
		cargoDao.expects(once()).method("findByAreaOrganizacionalIdsProjection").with(NULL, ANYTHING).will(returnValue(cargosNull));

		assertEquals(cargosNull, cargoManager.findByAreasOrganizacionalIdsProjection(idsLong, null));
	}

	public void testGetCargosByIds()
	{

		Long[] idsLong = new Long[] { 1L };
		Collection<Cargo> cargos = new ArrayList<Cargo>();

		AreaOrganizacional ao = new AreaOrganizacional();
		ao.setDescricao("descricao");
		ao.setId(1L);

		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(ao);

		AreaFormacao af = new AreaFormacao();
		af.setId(1L);
		af.setNome("nome");

		Collection<AreaFormacao> areasF = new ArrayList<AreaFormacao>();
		areasF.add(af);

		EtapaSeletiva etapaSeletiva = new EtapaSeletiva();
		etapaSeletiva.setId(1L);
		etapaSeletiva.setNome("etapa");
		
		Collection<EtapaSeletiva> etapaSeletivas = new ArrayList<EtapaSeletiva>();
		etapaSeletivas.add(etapaSeletiva);

		Conhecimento co = new Conhecimento();
		co.setId(1L);
		co.setNome("nome");

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(co);

		FaixaSalarial fs = new FaixaSalarial();
		fs.setId(1L);
		fs.setDescricao("descricao");

		Collection<FaixaSalarial> faixas = new ArrayList<FaixaSalarial>();
		faixas.add(fs);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setId(1L);
		cargo.setAreasOrganizacionais(areas);
		cargo.setAreaFormacaos(areasF);
		cargo.setEtapaSeletivas(etapaSeletivas);
		cargo.setConhecimentos(conhecimentos);
		cargo.setFaixaSalarials(faixas);

		cargos.add(cargo);

		cargoDao.expects(once()).method("findCargosByIds").with(eq(idsLong), ANYTHING).will(returnValue(cargos));
		
		areaOrganizacionalManager.expects(atLeastOnce()).method("findByCargo").will(returnValue(areas));
		areaFormacaoManager.expects(atLeastOnce()).method("findByCargo").will(returnValue(areasF));
		etapaSeletivaManager.expects(atLeastOnce()).method("findByCargo").will(returnValue(etapaSeletivas));
		conhecimentoManager.expects(atLeastOnce()).method("findByCargo").will(returnValue(conhecimentos));
		habilidadeManager.expects(atLeastOnce()).method("findByCargo");
		atitudeManager.expects(atLeastOnce()).method("findByCargo");
		faixaSalarialManager.expects(atLeastOnce()).method("findByCargoComCompetencia").will(returnValue(faixas));
		
		Collection<Cargo> cargosRetorno = new ArrayList<Cargo>();
		try {
			cargosRetorno = cargoManager.getCargosByIds(idsLong, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(1, cargosRetorno.size());
	}

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		String ordenarPor = "a";
		Collection<Cargo> cargos = new ArrayList<Cargo>();

		cargoDao.expects(once()).method("findAllSelect").with(eq(new Long[]{empresaId}), eq(ordenarPor), eq(null), eq(Cargo.TODOS)).will(returnValue(cargos));

		assertEquals(cargos, cargoManager.findAllSelect(ordenarPor, null, Cargo.TODOS, empresaId));
	}

	public void testFindByIdProjection()
	{
		Cargo cargo = new Cargo();
		cargo.setId(1L);

		cargoDao.expects(once()).method("findByIdProjection").with(eq(cargo.getId())).will(returnValue(cargo));

		assertEquals(cargo.getId(), cargoManager.findByIdProjection(cargo.getId()).getId());
	}

	public void testFindByIdAllProjection()
	{
		Cargo cargo = new Cargo();
		cargo.setId(1L);

		cargoDao.expects(once()).method("findByIdAllProjection").with(eq(cargo.getId())).will(returnValue(cargo));

		assertEquals(cargo.getId(), cargoManager.findByIdAllProjection(cargo.getId()).getId());
	}

	public void testPopulaCargos()
	{
		String[] cargosCheck = new String[] { "1", "2" };

		assertEquals(2, cargoManager.populaCargos(cargosCheck).size());
	}

	public void testPopulaCheckBox()
	{
		Cargo cargo = new Cargo();
		cargo.setId(1L);
		cargo.setNome("cargo");

		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);

		cargoDao.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(cargos));

		assertEquals(1, cargoManager.populaCheckBox(false, 1L).size());
	}
	
	public void testFindByAreaDoHistoricoColaborador()
	{
		//vazio ou null
		assertEquals(0, cargoManager.findByAreaDoHistoricoColaborador(null).size());

		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(CargoFactory.getEntity(1L));
		cargos.add(CargoFactory.getEntity(2L));

		cargoDao.expects(atLeastOnce()).method("findByAreaDoHistoricoColaborador").with(ANYTHING).will(returnValue(cargos));
		assertEquals(2, cargoManager.findByAreaDoHistoricoColaborador(new String[]{"1", "2"}).size());
	}

	public void testPopulaCheckBoxException()
	{
		cargoManager.setDao(null);

		assertTrue(cargoManager.populaCheckBox(false, 1L).isEmpty());
	}

	public void testPopulaCheckBoxSemGrupo() throws Exception
	{
		Cargo cargo = new Cargo();
		cargo.setId(1L);
		cargo.setNome("cargo");

		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);

		cargoDao.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(cargos));

		assertEquals(1, cargoManager.populaCheckBox(null, null, 1L).size());
	}

	public void testPopulaCheckBoxGrupo() throws Exception
	{
		Cargo cargo = new Cargo();
		cargo.setId(1L);
		cargo.setNome("cargo");

		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);

		cargoDao.expects(once()).method("findByGrupoOcupacionalIdsProjection").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(cargos));

		String[] gruposCheck = new String[] { "1" };
		assertEquals(1, cargoManager.populaCheckBox(gruposCheck, null, 1L).size());
	}

	public void testVerifyExistCargoNome()
	{
		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setNome("GERENTE");

		cargoDao.expects(once()).method("verifyExistCargoNome").with(ANYTHING,ANYTHING).will(returnValue(true));

		assertTrue(cargoManager.verifyExistCargoNome(cargo.getNome(), 1L));
	}
	
	public void testSincronizar()
	{
		Long empresaOrigemId = 1L;
		Map<Long, Long> areaIds = new HashMap<Long, Long>();
		Map<Long, Long> areaInteresseIds = new HashMap<Long, Long>();
		Map<Long, Long> conhecimentoIds = new HashMap<Long, Long>();
		Map<Long, Long> habilidadeIds = new HashMap<Long, Long>();
		Map<Long, Long> atitudeIds = new HashMap<Long, Long>();
		
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		Cargo cargo = CargoFactory.getEntity(1L);
		cargos.add(cargo);
		
		cargoDao.expects(once()).method("findSincronizarCargos").with(eq(empresaOrigemId)).will(returnValue(cargos));
		cargoDao.expects(once()).method("save").will(returnValue(cargo));
		
		Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
		areaOrganizacionalManager.expects(once()).method("findByCargo").will(returnValue(areasOrganizacionais ));
		
		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>(); 
		conhecimentoManager.expects(once()).method("findByCargo").will(returnValue(conhecimentos ));

		Collection<Habilidade> habilidades = new ArrayList<Habilidade>(); 
		habilidadeManager.expects(once()).method("findByCargo").will(returnValue(habilidades ));
		
		Collection<Atitude> atitudes = new ArrayList<Atitude>(); 
		atitudeManager.expects(once()).method("findByCargo").will(returnValue(atitudes ));
		
		Collection<AreaFormacao> areasFormacao = new ArrayList<AreaFormacao>(); 
		areaFormacaoManager.expects(once()).method("findByCargo").will(returnValue(areasFormacao ));
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		cargoDao.expects(once()).method("update");
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		faixaSalarialManager.expects(once()).method("sincronizar");
		
		cargoManager.sincronizar(empresaOrigemId, EmpresaFactory.getEmpresa(), areaIds, areaInteresseIds, conhecimentoIds, habilidadeIds, atitudeIds, null);
	}
}

package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.captacao.NivelCompetenciaEditAction;

public class NivelCompetenciaEditActionTest extends MockObjectTestCase
{
	private NivelCompetenciaEditAction action;
	private Mock manager;
	private Mock faixaSalarialManager;
	private Mock candidatoManager;
	private Mock colaboradorManager;
	private Mock configuracaoNivelCompetenciaManager;
	private Mock configuracaoNivelCompetenciaColaboradorManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(NivelCompetenciaManager.class);
		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		candidatoManager = new Mock(CandidatoManager.class);
		colaboradorManager = new Mock(ColaboradorManager.class);
		configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
		configuracaoNivelCompetenciaColaboradorManager = new Mock(ConfiguracaoNivelCompetenciaColaboradorManager.class);
		
		action = new NivelCompetenciaEditAction();
		action.setNivelCompetencia(new NivelCompetencia());
		
		action.setNivelCompetenciaManager((NivelCompetenciaManager) manager.proxy());
		action.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());
		action.setCandidatoManager((CandidatoManager) candidatoManager.proxy());
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		action.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
		action.setConfiguracaoNivelCompetenciaColaboradorManager((ConfiguracaoNivelCompetenciaColaboradorManager) configuracaoNivelCompetenciaColaboradorManager.proxy());
		
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(new ArrayList<NivelCompetencia>()));
		
		assertEquals("success", action.list());
		assertNotNull(action.getNivelCompetencias());
	}

	public void testDelete() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(1L);
		action.setNivelCompetencia(nivelCompetencia);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(new ArrayList<NivelCompetencia>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(1L);
		action.setNivelCompetencia(nivelCompetencia);
		
		manager.expects(once()).method("remove").with(eq(nivelCompetencia.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(new ArrayList<NivelCompetencia>()));

		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(1L);
		action.setNivelCompetencia(nivelCompetencia);

		manager.expects(once()).method("validaLimite").with(ANYTHING).isVoid();
		manager.expects(once()).method("save").with(eq(nivelCompetencia)).will(returnValue(nivelCompetencia));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		manager.expects(once()).method("validaLimite").with(ANYTHING).isVoid();
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(1L);
		action.setNivelCompetencia(nivelCompetencia);

		manager.expects(once()).method("update").with(eq(nivelCompetencia)).isVoid();

		assertEquals("success", action.update());
	}
	
	public void testPrepareCompetenciasByFaixa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L); 
		action.setEmpresaSistema(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		action.setFaixaSalarial(faixaSalarial);
		
		ConfiguracaoNivelCompetencia config1 = new ConfiguracaoNivelCompetencia();
		ConfiguracaoNivelCompetencia config2 = new ConfiguracaoNivelCompetencia();
		
		Collection<ConfiguracaoNivelCompetencia> configuracoes = Arrays.asList(config1, config2);
		action.setNiveisCompetenciaFaixaSalariais(configuracoes);
		
		faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
		manager.expects(once()).method("findByCargoOrEmpresa").with(eq(cargo.getId()), eq(null)).will(returnValue(configuracoes));
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(configuracoes));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByFaixa").with(eq(faixaSalarial.getId())).will(returnValue(configuracoes));
		
		assertEquals("success", action.prepareCompetenciasByFaixa());
		assertNotNull(action.getFaixaSalarial());
		assertNotNull(action.getNiveisCompetenciaFaixaSalariais());
		assertNotNull(action.getNivelCompetencias());
	}

	public void testPrepareCompetenciasByCandidato()
	{
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		action.setCandidato(candidato);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L); 
		action.setEmpresaSistema(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		action.setFaixaSalarial(faixaSalarial);
		
		ConfiguracaoNivelCompetencia config1 = new ConfiguracaoNivelCompetencia();
		ConfiguracaoNivelCompetencia config2 = new ConfiguracaoNivelCompetencia();
		
		Collection<ConfiguracaoNivelCompetencia> configuracoes = Arrays.asList(config1, config2);
		action.setNiveisCompetenciaFaixaSalariais(configuracoes);
		
		candidatoManager.expects(once()).method("findByCandidatoId").with(eq(candidato.getId())).will(returnValue(candidato));
		faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
		manager.expects(once()).method("findByCargoOrEmpresa").with(eq(cargo.getId()), eq(empresa.getId())).will(returnValue(configuracoes));
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(configuracoes));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByFaixa").with(eq(faixaSalarial.getId())).will(returnValue(configuracoes));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByCandidato").with(eq(candidato.getId())).will(returnValue(configuracoes));
		
		assertEquals("success", action.prepareCompetenciasByCandidato());
		assertNotNull(action.getCandidato());
		assertNotNull(action.getFaixaSalarial());
		assertNotNull(action.getNiveisCompetenciaFaixaSalariais());
		assertNotNull(action.getNivelCompetencias());
		assertNotNull(action.getNiveisCompetenciaFaixaSalariaisSugeridos());
		assertNotNull(action.getNiveisCompetenciaFaixaSalariaisSalvos());
	}

	public void testSaveCompetenciasByFaixa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L); 
		action.setEmpresaSistema(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		action.setFaixaSalarial(faixaSalarial);
		
		ConfiguracaoNivelCompetencia config1 = new ConfiguracaoNivelCompetencia();
		ConfiguracaoNivelCompetencia config2 = new ConfiguracaoNivelCompetencia();
		
		Collection<ConfiguracaoNivelCompetencia> configuracoes = Arrays.asList(config1, config2);
		action.setNiveisCompetenciaFaixaSalariais(configuracoes);
		
		configuracaoNivelCompetenciaManager.expects(once()).method("saveCompetencias").with(eq(configuracoes), eq(faixaSalarial.getId()), eq(null)).isVoid();
		faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
		manager.expects(once()).method("findByCargoOrEmpresa").with(eq(cargo.getId()), eq(null)).will(returnValue(configuracoes));
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(configuracoes));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByFaixa").with(eq(faixaSalarial.getId())).will(returnValue(configuracoes));
		
		assertEquals("success", action.saveCompetenciasByFaixa());
	}
	
	public void testSaveCompetenciasByCandidato()
	{
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		action.setCandidato(candidato);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L); 
		action.setEmpresaSistema(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		action.setFaixaSalarial(faixaSalarial);
		
		ConfiguracaoNivelCompetencia config1 = new ConfiguracaoNivelCompetencia();
		ConfiguracaoNivelCompetencia config2 = new ConfiguracaoNivelCompetencia();
		
		Collection<ConfiguracaoNivelCompetencia> configuracoes = Arrays.asList(config1, config2);
		action.setNiveisCompetenciaFaixaSalariais(configuracoes);
		
		configuracaoNivelCompetenciaManager.expects(once()).method("saveCompetencias").with(eq(configuracoes), eq(faixaSalarial.getId()), eq(candidato.getId())).isVoid();
		candidatoManager.expects(once()).method("findByCandidatoId").with(eq(candidato.getId())).will(returnValue(candidato));
		faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(faixaSalarial.getId())).will(returnValue(faixaSalarial));
		manager.expects(once()).method("findByCargoOrEmpresa").with(eq(cargo.getId()), eq(empresa.getId())).will(returnValue(configuracoes));
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(configuracoes));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByFaixa").with(eq(faixaSalarial.getId())).will(returnValue(configuracoes));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByCandidato").with(eq(candidato.getId())).will(returnValue(configuracoes));
		
		assertEquals("success", action.saveCompetenciasByCandidato());
	}
	
	public void testListCompetenciasColaborador() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		action.setColaborador(colaborador);
		
		colaboradorManager.expects(once()).method("findColaboradorByIdProjection").with(eq(colaborador.getId())).will(returnValue(colaborador));
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(new ArrayList<ConfiguracaoNivelCompetenciaColaborador>()));
		
		assertEquals("success", action.listCompetenciasColaborador());
		assertNotNull(action.getColaborador());
		assertNotNull(action.getConfiguracaoNivelCompetenciaColaboradores());
	}
	
	public void testImprimirRelatorioCompetenciasColaborador() throws Exception
	{
		Cargo cargo = CargoFactory.getEntity();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		action.setFaixaSalarial(faixaSalarial);
		
		configuracaoNivelCompetenciaManager.expects(once()).method("findColaboradorAbaixoNivel").with(ANYTHING).will(returnValue(Arrays.asList(new ConfiguracaoNivelCompetencia())));
		faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(ANYTHING).will(returnValue(faixaSalarial));
		
		assertEquals("success", action.imprimirRelatorioCompetenciasColaborador());
		assertNotNull(action.getNiveisCompetenciaFaixaSalariais());
	}
	
	public void testImprimirRelatorioCompetenciasColaboradorException() throws Exception
	{
		Cargo cargo = CargoFactory.getEntity();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		action.setFaixaSalarial(faixaSalarial);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		configuracaoNivelCompetenciaManager.expects(once()).method("findColaboradorAbaixoNivel").with(ANYTHING).will(returnValue(new ArrayList<ConfiguracaoNivelCompetencia>()));
		faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(ANYTHING).will(returnValue(faixaSalarial));
		faixaSalarialManager.expects(once()).method("findAllSelectByCargo").with(ANYTHING).will(returnValue(new ArrayList<FaixaSalarial>()));
		
		assertEquals("input", action.imprimirRelatorioCompetenciasColaborador());
	}

	public void testInsertCompetenciasColaborador() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setHistoricoColaborador(historicoColaborador);
		action.setColaborador(colaborador);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		action.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		
		ConfiguracaoNivelCompetencia config1 = new ConfiguracaoNivelCompetencia();
		ConfiguracaoNivelCompetencia config2 = new ConfiguracaoNivelCompetencia();
		
		Collection<ConfiguracaoNivelCompetencia> configuracoes = Arrays.asList(config1, config2);
		action.setNiveisCompetenciaFaixaSalariais(configuracoes);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("checarHistoricoMesmaData").with(eq(configuracaoNivelCompetenciaColaborador)).isVoid();
		configuracaoNivelCompetenciaManager.expects(once()).method("saveCompetenciasColaborador").with(eq(configuracoes), eq(configuracaoNivelCompetenciaColaborador)).isVoid();
		colaboradorManager.expects(once()).method("findById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		manager.expects(once()).method("findByCargoOrEmpresa").with(eq(cargo.getId()), eq(empresa.getId())).will(returnValue(configuracoes));
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(configuracoes));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByFaixa").with(eq(faixaSalarial.getId())).will(returnValue(configuracoes));
		
		assertEquals("success", action.saveCompetenciasColaborador());
		assertNotNull(action.getColaborador());
		assertNotNull(action.getNiveisCompetenciaFaixaSalariais());
		assertNotNull(action.getNiveisCompetenciaFaixaSalariaisSugeridos());
	}

	public void testUpdateCompetenciasColaborador() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setHistoricoColaborador(historicoColaborador);
		action.setColaborador(colaborador);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		configuracaoNivelCompetenciaColaborador.setId(1L);
		configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador.setFaixaSalarial(faixaSalarial);
		action.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		
		ConfiguracaoNivelCompetencia config1 = new ConfiguracaoNivelCompetencia();
		ConfiguracaoNivelCompetencia config2 = new ConfiguracaoNivelCompetencia();
		
		Collection<ConfiguracaoNivelCompetencia> configuracoes = Arrays.asList(config1, config2);
		action.setNiveisCompetenciaFaixaSalariais(configuracoes);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("checarHistoricoMesmaData").with(eq(configuracaoNivelCompetenciaColaborador)).isVoid();
		configuracaoNivelCompetenciaManager.expects(once()).method("saveCompetenciasColaborador").with(eq(configuracoes), eq(configuracaoNivelCompetenciaColaborador)).isVoid();
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("findByIdProjection").with(eq(configuracaoNivelCompetenciaColaborador.getId())).will(returnValue(configuracaoNivelCompetenciaColaborador));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByColaborador").with(eq(configuracaoNivelCompetenciaColaborador.getId())).will(returnValue(configuracoes));
		
		manager.expects(once()).method("findByCargoOrEmpresa").with(eq(cargo.getId()), eq(empresa.getId())).will(returnValue(configuracoes));
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(configuracoes));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByFaixa").with(eq(faixaSalarial.getId())).will(returnValue(configuracoes));
		
		assertEquals("success", action.saveCompetenciasColaborador());
		assertNotNull(action.getColaborador());
		assertNotNull(action.getNiveisCompetenciaFaixaSalariais());
		assertNotNull(action.getNiveisCompetenciaFaixaSalariaisSugeridos());
	}
	
	@SuppressWarnings("unchecked")
	public void testSaveExceptionCompetenciasColaborador() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setHistoricoColaborador(historicoColaborador);
		action.setColaborador(colaborador);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		action.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		
		ConfiguracaoNivelCompetencia config1 = new ConfiguracaoNivelCompetencia();
		ConfiguracaoNivelCompetencia config2 = new ConfiguracaoNivelCompetencia();
		
		Collection<ConfiguracaoNivelCompetencia> configuracoes = Arrays.asList(config1, config2);
		action.setNiveisCompetenciaFaixaSalariais(configuracoes);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("checarHistoricoMesmaData").will(throwException(new Exception("Já existe configuração para essa data")));
		colaboradorManager.expects(once()).method("findById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		manager.expects(once()).method("findByCargoOrEmpresa").with(eq(cargo.getId()), eq(empresa.getId())).will(returnValue(configuracoes));
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(configuracoes));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByFaixa").with(eq(faixaSalarial.getId())).will(returnValue(configuracoes));
		
		assertEquals("success", action.saveCompetenciasColaborador());
		Collection<String> erros = action.getActionErrors(); 
		
		assertEquals(1, erros.size());
		assertEquals("Já existe configuração para essa data", erros.toArray()[0]);
	}
	
	public void testGetSet() throws Exception
	{
		action.setNivelCompetencia(null);

		assertNotNull(action.getNivelCompetencia());
		assertTrue(action.getNivelCompetencia() instanceof NivelCompetencia);
	}
}

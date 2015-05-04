package com.fortes.rh.test.web.action.geral;

import java.util.Collections;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.web.action.cargosalario.HistoricoColaboradorEditAction;
import com.opensymphony.xwork.ActionContext;

public class HistoricoColaboradorEditActionTest extends MockObjectTestCase
{
	private HistoricoColaboradorEditAction action;
	
	private Mock historicoColaboradorManager;
	private Mock indiceManager;
	private Mock funcaoManager;
	private Mock ambienteManager;
	private Mock estabelecimentoManager;
	private Mock faixaSalarialManager;
	private Mock areaOrganizacionalManager;
	private Mock colaboradorManager;
	private Mock solicitacaoManager;
	private Mock quantidadeLimiteColaboradoresPorCargoManager;
	private Mock transactionManager = null;
	
	private Empresa empresaDoSistema;
	private HistoricoColaborador historicoColaborador;

	protected void setUp() throws Exception
	{
		action = new HistoricoColaboradorEditAction();
		quantidadeLimiteColaboradoresPorCargoManager = new Mock(QuantidadeLimiteColaboradoresPorCargoManager.class);
		action.setQuantidadeLimiteColaboradoresPorCargoManager((QuantidadeLimiteColaboradoresPorCargoManager) quantidadeLimiteColaboradoresPorCargoManager.proxy());
		transactionManager = new Mock(PlatformTransactionManager.class);
		
		action.setHistoricoColaboradorManager(mockaHistoricoColaboradorManager());
		action.setIndiceManager(mockaIndiceManager());
		action.setFuncaoManager(mockaFuncaoManager());
		action.setAmbienteManager(mockaAmbienteManager());
		action.setEstabelecimentoManager(mockaEstabelecimentoManager());
		action.setFaixaSalarialManager(mockaFaixaSalarialManager());
		action.setAreaOrganizacionalManager(mockaAreaOrganizacionalManager());
		action.setColaboradorManager(mockaColaboradorManager());
		action.setSolicitacaoManager(mockaSolicitacaoManager());
		action.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
		
		
		empresaDoSistema = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresaDoSistema);
		
		historicoColaborador = HistoricoColaboradorFactory.getEntity();
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
	}
	
	protected void tearDown() throws Exception
    {
        historicoColaboradorManager = null;
        action = null;
        Mockit.restoreAllOriginalDefinitions();
        super.tearDown();
    }

	private ColaboradorManager mockaColaboradorManager() {
		colaboradorManager = new Mock(ColaboradorManager.class);
		return (ColaboradorManager) colaboradorManager.proxy();
	}
	
	private SolicitacaoManager mockaSolicitacaoManager() {
		solicitacaoManager = new Mock(SolicitacaoManager.class);
		return (SolicitacaoManager) solicitacaoManager.proxy();
	}

	private AreaOrganizacionalManager mockaAreaOrganizacionalManager() {
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		return (AreaOrganizacionalManager) areaOrganizacionalManager.proxy();
	}

	private FaixaSalarialManager mockaFaixaSalarialManager() {
		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		return (FaixaSalarialManager) faixaSalarialManager.proxy();
	}

	private EstabelecimentoManager mockaEstabelecimentoManager() {
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		return (EstabelecimentoManager) estabelecimentoManager.proxy();
	}

	private AmbienteManager mockaAmbienteManager() {
		ambienteManager = new Mock(AmbienteManager.class);
		return (AmbienteManager) ambienteManager.proxy();
	}

	private FuncaoManager mockaFuncaoManager() {
		funcaoManager = new Mock(FuncaoManager.class);
		return (FuncaoManager) funcaoManager.proxy();
	}

	private IndiceManager mockaIndiceManager() {
		indiceManager = new Mock(IndiceManager.class);
		return (IndiceManager) indiceManager.proxy();
	}

	private HistoricoColaboradorManager mockaHistoricoColaboradorManager() {
		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		return (HistoricoColaboradorManager) historicoColaboradorManager.proxy();
	}
	
	public void testList() throws Exception {
		assertEquals("success", action.list()); 
	}
	
	public void testPrepare() throws Exception {
		// dado que
		dadoQueExistemEstabelecimentosParaEmpresaDoSistema();
		dadoQueExistemIndicesCadastrados();
		dadoQueExistemFaixasSalariaisAtivasParaEmpresaDoSistema();
		dadoQueExistemAreasOrganizacionaisParaEmpresaDoSistema();
		
		dadoQueHistoricoDoColaboradorPossuiUmCargoArea();
		action.setHistoricoColaborador(historicoColaborador);
		
		dadoQueExistemFuncoesCadastradas();
		dadoQueHistoricoDoColaboradorPossuiUmEstabelecimento();
		dadoQueExistemAmbientesCadastrados();
		// quando
		action.prepare();
		// entao
		estabelecimentoManager.verify();
		indiceManager.verify();
		faixaSalarialManager.verify();
		areaOrganizacionalManager.verify();
		funcaoManager.verify();
		ambienteManager.verify();
	}
	
	public void testPrepareInsert() throws Exception {
		
		// comportamente do prepareInsert()
		dadoQueExisteHistoricoAtualParaColaborador();
		// comportamento do prepare()
		simulaComportamentoDoPrepareSemHistorico();
		
		String outcome = action.prepareInsert();
		
		assertEquals("success", outcome);
		assertNull("id do colaborador", historicoColaborador.getId());
		assertNotNull("data do colaborador", historicoColaborador.getData());
		assertQueMetodoPrepareFoiChamado();
	}
	
	public void testPrepareInsertQuandoHouverSolicitacao() throws Exception {
		// comportamente do prepareInsert()
		dadoQueExisteHistoricoAtualParaColaborador();
		dadoQueExisteUmaSolicitacao();
		// comportamento do prepare()
		simulaComportamentoDoPrepareQuandoHouverSolicitacao();
		
		String outcome = action.prepareInsert();
		
		assertEquals("success", outcome);
		assertNull("id do colaborador", historicoColaborador.getId());
		assertNotNull("data do colaborador", action.getHistoricoColaborador().getData());
		assertQueMetodoPrepareFoiChamadoQuandoHouverSolicitacao();
	}
	
	public void testInsertQuandoNaoExisteHistoricoNaData() throws Exception {
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		dadoQueNaoExisteHistoricoNaData();
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(ColaboradorFactory.getEntity(1L));
		action.setHistoricoColaborador(historicoColaborador);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		quantidadeLimiteColaboradoresPorCargoManager.expects(atLeastOnce()).method("validaLimite").withAnyArguments();
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(historicoColaborador.getColaborador()));
		historicoColaboradorManager.expects(once()).method("verificaPrimeiroHistoricoAdmissao").with(eq(true), eq(historicoColaborador), eq(historicoColaborador.getColaborador())).will(returnValue(false));;
		
		dadoQueNaoOcorreErroAoAjustarFuncaoDoColaborador();
		dadoQueNaoOcorreErroAoInserirHistoricoDeColaborador();
		
		String outcome = action.insert();
		
		assertEquals("success", outcome);
	}
	
	private void dadoQueNaoOcorreErroAoAjustarFuncaoDoColaborador() {
		historicoColaboradorManager.expects(once()).method("ajustaAmbienteFuncao")
			.with(eq(historicoColaborador)).will(returnValue(historicoColaborador));
	}

	private void dadoQueNaoOcorreErroAoInserirHistoricoDeColaborador() {
		historicoColaboradorManager.expects(once()).method("insertHistorico")
			.with(eq(historicoColaborador), eq(empresaDoSistema));
	}

	private void dadoQueNaoExisteHistoricoNaData() {
		action.setHistoricoColaborador(historicoColaborador);
		historicoColaboradorManager.expects(once()).method("existeHistoricoData")
			.with(eq(historicoColaborador)).will(returnValue(false));
	}

	public void testInsertQuandoJaExisteHistoricoNaData() throws Exception {
		// comportamento do insert()
		dadoQueJaExisteHistoricoNaData();
		// comportamento do prepareInsert()
		simulaComportamentoDoPrepareInsert();
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		String outcome = action.insert();
		
		assertEquals("input", outcome);
		assertQueMetodoPrepareInsertFoiChamado();
	}
	
	public void testInsertQuandoOcorrerErroInternoQualquer() throws Exception {
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historicoColaborador.setData(new Date());
		dadoQueNaoExisteHistoricoNaData();
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(ColaboradorFactory.getEntity(1L));
		action.setHistoricoColaborador(historicoColaborador);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		quantidadeLimiteColaboradoresPorCargoManager.expects(atLeastOnce()).method("validaLimite").withAnyArguments();
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(historicoColaborador.getColaborador()));
		historicoColaboradorManager.expects(once()).method("verificaPrimeiroHistoricoAdmissao").with(eq(true), eq(historicoColaborador), eq(historicoColaborador.getColaborador())).will(returnValue(false));;
		
		dadoQueNaoOcorreErroAoAjustarFuncaoDoColaborador();
		dadoQueOcorreErroGenericoAoInserirHistoricoDeColaborador();
		simulaComportamentoDoPrepareInsert();
		
		String outcome = action.insert();
		
		assertEquals("input", outcome);
		assertQueMetodoPrepareInsertFoiChamado();
	}
	
	private void dadoQueOcorreErroGenericoAoInserirHistoricoDeColaborador() {
		historicoColaboradorManager.expects(once()).method("insertHistorico")
			.with(eq(historicoColaborador), eq(empresaDoSistema)).will(throwException(new RuntimeException("Erro interno.")));
	}

	public void testInsertQuandoOcorrerErroDeIntegraACException() throws Exception {
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historicoColaborador.setData(new Date());
		dadoQueNaoExisteHistoricoNaData();
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(ColaboradorFactory.getEntity(1L));
		action.setHistoricoColaborador(historicoColaborador);
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		quantidadeLimiteColaboradoresPorCargoManager.expects(atLeastOnce()).method("validaLimite").withAnyArguments();
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(historicoColaborador.getColaborador()));
		historicoColaboradorManager.expects(once()).method("verificaPrimeiroHistoricoAdmissao").with(eq(true), eq(historicoColaborador), eq(historicoColaborador.getColaborador())).will(returnValue(false));;
		
		
		dadoQueNaoOcorreErroAoAjustarFuncaoDoColaborador();
		dadoQueOcorreErroDeIntegracaoACAoInserirHistoricoDeColaborador();
		simulaComportamentoDoPrepareInsert();
		
		String outcome = action.insert();
		
		assertEquals("input", outcome);
		assertQueMetodoPrepareInsertFoiChamado();
	}
	
	private void dadoQueOcorreErroDeIntegracaoACAoInserirHistoricoDeColaborador() {
		historicoColaboradorManager.expects(once()).method("insertHistorico")
			.with(eq(historicoColaborador), eq(empresaDoSistema)).will(throwException(new IntegraACException("Erro de Integração.")));
	}

	private void assertQueMetodoPrepareInsertFoiChamado() {
		assertNull("id do colaborador", historicoColaborador.getId());
		assertNotNull("data do colaborador", historicoColaborador.getData());
		assertQueMetodoPrepareFoiChamado();		
	}

	private void simulaComportamentoDoPrepareInsert() {
		// comportamente do prepareInsert()
		dadoQueExisteHistoricoAtualParaColaborador();
		// comportamento do prepare()
		simulaComportamentoDoPrepareComHistorico();
	}

	private void dadoQueJaExisteHistoricoNaData() {
		historicoColaborador.setData(new Date());
		action.setHistoricoColaborador(historicoColaborador);
		historicoColaboradorManager.expects(once()).method("existeHistoricoData")
			.with(eq(historicoColaborador)).will(returnValue(true));
	}
	
	private void assertQueMetodoPrepareFoiChamado() {
		estabelecimentoManager.verify();
		indiceManager.verify();
		faixaSalarialManager.verify();
		areaOrganizacionalManager.verify();
		funcaoManager.verify();
		ambienteManager.verify();
	}
	
	private void assertQueMetodoPrepareFoiChamadoQuandoHouverSolicitacao() {
		estabelecimentoManager.verify();
		indiceManager.verify();
		faixaSalarialManager.verify();
		areaOrganizacionalManager.verify();
		funcaoManager.verify();
	}

	private void simulaComportamentoDoPrepare() {
		dadoQueExistemEstabelecimentosParaEmpresaDoSistema();
		dadoQueExistemIndicesCadastrados();
		dadoQueExistemFaixasSalariaisAtivasParaEmpresaDoSistema();
		dadoQueExistemAreasOrganizacionaisParaEmpresaDoSistema();
	}
	
	private void simulaComportamentoDoPrepareSemHistorico() {
		simulaComportamentoDoPrepare();
		
		historicoColaboradorManager.expects(once()).method("getHistoricoAtualOuFuturo").with(eq(1L)).will(returnValue(historicoColaborador));
	}
	
	private void simulaComportamentoDoPrepareComHistorico() {
		simulaComportamentoDoPrepare();
		
		dadoQueHistoricoDoColaboradorPossuiUmCargoArea();
		dadoQueExistemFuncoesCadastradas();
		dadoQueHistoricoDoColaboradorPossuiUmEstabelecimento();
		dadoQueExistemAmbientesCadastrados();		
	}
	
	private void simulaComportamentoDoPrepareQuandoHouverSolicitacao() {
		simulaComportamentoDoPrepare();
		
		dadoQueHistoricoDoColaboradorPossuiUmCargoArea();	
	}

	private void dadoQueExisteHistoricoAtualParaColaborador() {
		action.setColaborador(ColaboradorFactory.getEntity(1L));
		historicoColaboradorManager.expects(once()).method("getPrimeiroHistorico").with(eq(1L)).will(returnValue(historicoColaborador));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtual").with(eq(1L)).will(returnValue(historicoColaborador));
	}
	
	private void dadoQueExisteUmaSolicitacao() {
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		
		action.setSolicitacao(solicitacao);
		action.setHistoricoColaborador(null);
		
		solicitacaoManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(solicitacao));
		faixaSalarialManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(faixaSalarial));
	}

	private void dadoQueExistemAmbientesCadastrados() {
		ambienteManager.expects(once()).method("findByEstabelecimento")
			.with(eq(historicoColaborador.getEstabelecimento().getId()))
				.will(returnValue(Collections.EMPTY_LIST));
	}

	private void dadoQueHistoricoDoColaboradorPossuiUmEstabelecimento() {
		historicoColaborador.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
	}
	
	private void dadoQueExistemFuncoesCadastradas() {
		funcaoManager.expects(once()).method("findByCargo")
			.with(eq(historicoColaborador.getFaixaSalarial().getCargo().getId()))
				.will(returnValue(Collections.EMPTY_LIST));
	}

	private void dadoQueHistoricoDoColaboradorPossuiUmCargoArea() {
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity(1L);
		faixa.setCargo(CargoFactory.getEntity(1L));
		historicoColaborador.setFaixaSalarial(faixa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
	}

	private void dadoQueExistemAreasOrganizacionaisParaEmpresaDoSistema() {
		areaOrganizacionalManager.expects(once()).method("findAllSelectOrderDescricao").with(eq(empresaDoSistema.getId()), eq(AreaOrganizacional.ATIVA), ANYTHING).will(returnValue(Collections.EMPTY_LIST));
	}

	private void dadoQueExistemFaixasSalariaisAtivasParaEmpresaDoSistema() {
		faixaSalarialManager.expects(once()).method("findFaixas").with(eq(empresaDoSistema), eq(Cargo.ATIVO), ANYTHING).will(returnValue(Collections.EMPTY_LIST));
	}
	
	private void dadoQueExistemIndicesCadastrados() {
		indiceManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(Collections.EMPTY_LIST));
	}

	private void dadoQueExistemEstabelecimentosParaEmpresaDoSistema() {
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresaDoSistema.getId())).will(returnValue(Collections.EMPTY_LIST));
	}

}
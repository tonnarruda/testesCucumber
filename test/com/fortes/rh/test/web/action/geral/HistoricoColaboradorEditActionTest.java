package com.fortes.rh.test.web.action.geral;

import java.util.Collections;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.web.action.cargosalario.HistoricoColaboradorEditAction;
import com.opensymphony.xwork.Action;
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

	private Empresa empresaDoSistema;
	private HistoricoColaborador historicoColaborador;

	protected void setUp() throws Exception
	{
		action = new HistoricoColaboradorEditAction();
		
		action.setHistoricoColaboradorManager(mockaHistoricoColaboradorManager());
		action.setIndiceManager(mockaIndiceManager());
		action.setFuncaoManager(mockaFuncaoManager());
		action.setAmbienteManager(mockaAmbienteManager());
		action.setEstabelecimentoManager(mockaEstabelecimentoManager());
		action.setFaixaSalarialManager(mockaFaixaSalarialManager());
		action.setAreaOrganizacionalManager(mockaAreaOrganizacionalManager());
		action.setColaboradorManager(mockaColaboradorManager());
		
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
		dadoQueHistoricoDoColaboradorPossuiUmCargo();
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
		simulaComportamentoDoPrepare();
		
		String outcome = action.prepareInsert();
		
		assertEquals("success", outcome);
		assertNull("id do colaborador", historicoColaborador.getId());
		assertNotNull("data do colaborador", historicoColaborador.getData());
		assertQueMetodoPrepareFoiChamado();
	}
	
	public void testInsertQuandoJaExisteHistoricoNaData() throws Exception {
		// comportamento do insert()
		dadoQueJaExisteHistoricoNaData();
		// comportamento do prepareInsert()
		simulaComportamentoDoPrepareInsert();
		
		String outcome = action.insert();
		
		assertEquals("input", outcome);
		assertQueMetodoPrepareInsertFoiChamado();
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
		simulaComportamentoDoPrepare();
	}

	private void dadoQueJaExisteHistoricoNaData() {
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

	private void simulaComportamentoDoPrepare() {
		dadoQueExistemEstabelecimentosParaEmpresaDoSistema();
		dadoQueExistemIndicesCadastrados();
		dadoQueExistemFaixasSalariaisAtivasParaEmpresaDoSistema();
		dadoQueExistemAreasOrganizacionaisParaEmpresaDoSistema();
		dadoQueHistoricoDoColaboradorPossuiUmCargo();
		dadoQueExistemFuncoesCadastradas();
		dadoQueHistoricoDoColaboradorPossuiUmEstabelecimento();
		dadoQueExistemAmbientesCadastrados();		
	}

	private void dadoQueExisteHistoricoAtualParaColaborador() {
		action.setColaborador(ColaboradorFactory.getEntity(1L));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtual")
			.with(eq(1L)).will(returnValue(historicoColaborador));
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

	private void dadoQueHistoricoDoColaboradorPossuiUmCargo() {
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity(1L);
		faixa.setCargo(CargoFactory.getEntity(1L));
		historicoColaborador.setFaixaSalarial(faixa);
		action.setHistoricoColaborador(historicoColaborador);
	}

	private void dadoQueExistemAreasOrganizacionaisParaEmpresaDoSistema() {
		areaOrganizacionalManager.expects(once()).method("findAllSelectOrderDescricao").with(eq(empresaDoSistema.getId()), eq(AreaOrganizacional.ATIVA)).will(returnValue(Collections.EMPTY_LIST));
	}

	private void dadoQueExistemFaixasSalariaisAtivasParaEmpresaDoSistema() {
		faixaSalarialManager.expects(once()).method("findFaixas").with(eq(empresaDoSistema), eq(Cargo.ATIVO)).will(returnValue(Collections.EMPTY_LIST));
	}
	
	private void dadoQueExistemIndicesCadastrados() {
		indiceManager.expects(once()).method("findAll").with(eq(new String[]{"nome"})).will(returnValue(Collections.EMPTY_LIST));
	}

	private void dadoQueExistemEstabelecimentosParaEmpresaDoSistema() {
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresaDoSistema.getId())).will(returnValue(Collections.EMPTY_LIST));
	}

}

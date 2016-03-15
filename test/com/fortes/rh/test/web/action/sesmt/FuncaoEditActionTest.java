package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFuncaoFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.sesmt.FuncaoEditAction;
import com.fortes.web.tags.CheckBox;

public class FuncaoEditActionTest extends MockObjectTestCase
{
	private FuncaoEditAction action;
	private Mock cargoManager;
	private Mock historicoFuncaoManager;
	private Mock colaboradorManager;
	private Mock historicoColaboradorManager;
	private Mock funcaoManager;
	private Mock ambienteManager;
	private Mock exameManager;
	private Mock epiManager;
	private Mock riscoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        cargoManager = new Mock(CargoManager.class);
        historicoFuncaoManager = new Mock(HistoricoFuncaoManager.class);
        colaboradorManager = new Mock(ColaboradorManager.class);
        historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
        funcaoManager = new Mock(FuncaoManager.class);
        ambienteManager = new Mock(AmbienteManager.class);
        exameManager = new Mock(ExameManager.class);
        epiManager = new Mock(EpiManager.class);
        riscoManager = new Mock(RiscoManager.class);

        action = new FuncaoEditAction();
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        action.setFuncaoManager((FuncaoManager) funcaoManager.proxy());
        action.setCargoManager((CargoManager) cargoManager.proxy());
        action.setHistoricoFuncaoManager((HistoricoFuncaoManager) historicoFuncaoManager.proxy());
        action.setColaboradorManager((ColaboradorManager)colaboradorManager.proxy());
        action.setExameManager((ExameManager) exameManager.proxy());
        action.setEpiManager((EpiManager) epiManager.proxy());
        action.setRiscoManager((RiscoManager) riscoManager.proxy());

        action.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());
        action.setAmbienteManager((AmbienteManager) ambienteManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
    }

    protected void tearDown() throws Exception
    {
        cargoManager = null;
        action = null;
        historicoColaboradorManager = null;
        funcaoManager = null;
        ambienteManager = null;

        Mockit.restoreAllOriginalDefinitions();
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testPrepareInsert() throws Exception
    {
		Funcao funcaoRetorno = FuncaoFactory.getEntity(1L);
		action.setFuncao(funcaoRetorno);

    	Cargo cargo = CargoFactory.getEntity(2L);
    	action.setCargoTmp(cargo);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	
    	Collection<CheckBox> examesCheckList = new ArrayList<CheckBox>();
    	examesCheckList = MockCheckListBoxUtil.populaCheckListBox(null, null, null);

    	cargoManager.expects(once()).method("findByIdProjection").with(eq(cargo.getId())).will(returnValue(cargo));
    	funcaoManager.expects(once()).method("findByIdProjection").with(eq(funcaoRetorno.getId())).will(returnValue(funcaoRetorno));
    	exameManager.expects(once()).method("findByEmpresaComAsoPadrao").with(ANYTHING).will(returnValue(new ArrayList<Exame>()));
    	epiManager.expects(once()).method("populaCheckToEpi").with(eq(empresa.getId()), ANYTHING).will(returnValue(new ArrayList<Epi>()));
    	riscoManager.expects(once()).method("findRiscosFuncoesByEmpresa").with(eq(empresa.getId())).will(returnValue(new ArrayList<RiscoFuncao>()));

    	assertEquals(action.prepareInsert(), "success");
    	assertEquals(action.getCargoTmp(), cargo);
    	assertEquals(action.getFuncao(), funcaoRetorno);
    	assertNotNull(action.getExamesCheckList());
    }

    public void testPrepareUpdate() throws Exception
    {
    	Funcao funcaoRetorno = FuncaoFactory.getEntity(1L);
    	action.setFuncao(funcaoRetorno);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	
    	Collection<HistoricoFuncao> historicoFuncaos = new ArrayList<HistoricoFuncao>();
    	HistoricoFuncao hf1 = new HistoricoFuncao();
    	hf1.setId(2L);
    	HistoricoFuncao hf2 = new HistoricoFuncao();
    	hf2.setId(2L);

    	historicoFuncaos.add(hf1);
    	historicoFuncaos.add(hf2);
    	action.setHistoricoFuncaos(historicoFuncaos);

    	Cargo cargo = new Cargo();
    	cargo.setId(2L);

    	action.setCargoTmp(cargo);

    	Collection<CheckBox> examesCheckList = new ArrayList<CheckBox>();
    	examesCheckList = MockCheckListBoxUtil.populaCheckListBox(null, null, null);

    	exameManager.expects(once()).method("findByEmpresaComAsoPadrao").with(ANYTHING).will(returnValue(new ArrayList<Exame>()));
    	epiManager.expects(once()).method("populaCheckToEpi").with(eq(empresa.getId()), ANYTHING).will(returnValue(new ArrayList<Epi>()));
    	cargoManager.expects(once()).method("findByIdProjection").with(eq(cargo.getId())).will(returnValue(cargo));
    	funcaoManager.expects(once()).method("findByIdProjection").with(eq(funcaoRetorno.getId())).will(returnValue(funcaoRetorno));
    	historicoFuncaoManager.expects(once()).method("findToList").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoFuncaos));

    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(action.getCargoTmp(), cargo);
    	assertEquals(action.getFuncao(), funcaoRetorno);
    	assertEquals(action.getHistoricoFuncaos(), historicoFuncaos);
    	assertNotNull(action.getExamesCheckList());
    }

    public void testInsert() throws Exception
    {
    	
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setControlaRiscoPor('A');
    	action.setEmpresaSistema(empresa);
    	
    	Cargo cargo = new Cargo();
    	cargo.setId(2L);

    	Funcao funcaoRetorno = new Funcao();
    	funcaoRetorno.setId(1L);
    	funcaoRetorno.setCargo(cargo);

    	action.setFuncao(funcaoRetorno);

    	HistoricoFuncao historicoFuncao = new HistoricoFuncao();
    	historicoFuncao.setId(2L);

    	action.setHistoricoFuncao(historicoFuncao);

    	Long[] examesChecked = new Long[]{1L};
    	action.setExamesChecked(examesChecked);

    	Long[] episChecked = new Long[]{1L};
    	action.setEpisChecked(episChecked);
    	
    	String[] riscoChecks = new String[]{"822", "823"};
    	Collection<RiscoFuncao> riscosFuncoes = RiscoFuncaoFactory.getCollection();
    	action.setRiscoChecks(riscoChecks);
		action.setRiscosFuncoes(riscosFuncoes);

    	historicoFuncaoManager.expects(once()).method("saveFuncaoHistorico").with(new Constraint[]{ eq(funcaoRetorno), eq(historicoFuncao), eq(examesChecked), eq(episChecked), eq(riscoChecks), eq(riscosFuncoes)});

    	assertEquals(action.insert(), "success");
    }

    public void testUpdate() throws Exception
    {
    	Cargo cargo = new Cargo();
    	cargo.setId(2L);

    	action.setCargoTmp(cargo);

    	Funcao funcaoRetorno = new Funcao();
    	funcaoRetorno.setId(1L);

    	action.setFuncao(funcaoRetorno);

    	cargoManager.expects(once()).method("findByIdProjection").with(eq(cargo.getId())).will(returnValue(cargo));
    	funcaoManager.expects(once()).method("update").with(eq(funcaoRetorno));

    	assertEquals(action.update(), "success");
    	assertEquals(action.getFuncao(), funcaoRetorno);
    	assertEquals(action.getCargoTmp(), cargo);
    }

    public void testPrepareMudancaFuncaoEmpresaErrada()
    {
    	Empresa empresaDiferente = new Empresa();
    	empresaDiferente.setId(999L);
    	Colaborador colaboradorComEmpresaDiferente = new Colaborador();
    	colaboradorComEmpresaDiferente.setEmpresa(empresaDiferente);

    	Colaborador colaboradorTmp = new Colaborador();
    	colaboradorTmp.setId(123L);
    	action.setColaborador(colaboradorTmp);

    	colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaboradorTmp.getId())).will(returnValue(colaboradorComEmpresaDiferente));

    	assertEquals(action.prepareMudancaFuncao(), "error");
    }
    public void testPrepareMudancaFuncaoEmpresaCorreta()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	Colaborador colaborador = new Colaborador();
    	colaborador.setEmpresa(empresa);

    	Colaborador colaboradorTmp = new Colaborador();
    	colaboradorTmp.setId(123L);
    	action.setColaborador(colaboradorTmp);

    	colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaboradorTmp.getId())).will(returnValue(colaborador));

    	Cargo cargo = new Cargo();
    	cargo.setId(1L);
    	FaixaSalarial faixaSalarial = new FaixaSalarial();
    	faixaSalarial.setCargo(cargo);
    	HistoricoColaborador historicoColaborador = new HistoricoColaborador();
    	historicoColaborador.setFaixaSalarial(faixaSalarial);

    	Collection<Funcao> funcoes = new ArrayList<Funcao>();
    	Funcao funcao = new Funcao();
    	funcao.setId(1L);
    	funcoes.add(funcao);

    	Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
    	Ambiente ambiente = new Ambiente();
    	ambiente.setId(1L);
    	ambientes.add(ambiente);

    	historicoColaboradorManager.expects(once()).method("getHistoricoAtual").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
    	funcaoManager.expects(once()).method("findByCargo").with(new Constraint[]{ANYTHING}).will(returnValue(funcoes));
    	ambienteManager.expects(once()).method("findAmbientes").with(new Constraint[]{ANYTHING}).will(returnValue(ambientes));

    	assertEquals(action.prepareMudancaFuncao(), "success");
    }

    public void testMudaFuncao() throws Exception
    {
    	Empresa empresa = MockSecurityUtil.getEmpresaSession(null);

    	Colaborador colaborador = new Colaborador();
    	colaborador.setId(1L);
    	colaborador.setEmpresa(empresa);
    	
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	
    	Indice indice = IndiceFactory.getEntity(1L);
    	indice.setIndiceHistoricoAtual(indiceHistorico);

    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setIndice(indice);
    		
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);

    	HistoricoColaborador historicoAtual = new HistoricoColaborador();
    	historicoAtual.setId(1L);
    	historicoAtual.setData(DateUtil.criarAnoMesDia(2008, 01, 01));
    	historicoAtual.setColaborador(colaborador);
    	historicoAtual.setTipoSalario(TipoAplicacaoIndice.VALOR);
    	historicoAtual.setFaixaSalarial(faixaSalarial);
    	historicoAtual.setIndice(indice);

    	HistoricoColaborador historicoTmp = new HistoricoColaborador();
    	historicoTmp.setData(DateUtil.criarAnoMesDia(2008, 04, 01));
    	Funcao funcao = new Funcao();
    	funcao.setId(1L);
		historicoTmp.setFuncao(funcao);
    	Ambiente ambiente = new Ambiente();
    	ambiente.setId(1L);
		historicoTmp.setAmbiente(ambiente);

		action.setHistoricoColaborador(historicoTmp);
    	action.setColaborador(colaborador);

    	historicoColaboradorManager.expects(once()).method("getHistoricoAtual").with(eq(colaborador.getId())).will(returnValue(historicoAtual));
    	historicoColaboradorManager.expects(once()).method("existeHistoricoData").with(eq(historicoTmp)).will(returnValue(false));
    	historicoColaboradorManager.expects(once()).method("save").with(new Constraint[]{ANYTHING}).will(returnValue(historicoAtual));
    	assertEquals(action.mudaFuncao(), "success");
    }

    public void testMudaFuncaoMesmaData() throws Exception
    {
    	Empresa empresa = MockSecurityUtil.getEmpresaSession(null);
    	Colaborador colaborador = new Colaborador();
    	colaborador.setId(1L);
    	colaborador.setEmpresa(empresa);

    	HistoricoColaborador historicoAtual = new HistoricoColaborador();
    	historicoAtual.setId(1L);
    	historicoAtual.setData(DateUtil.criarAnoMesDia(2008, 01, 01));
    	historicoAtual.setColaborador(colaborador);
    	Cargo cargo = new Cargo();
    	cargo.setId(1L);
    	FaixaSalarial faixaSalarial = new FaixaSalarial();
    	faixaSalarial.setCargo(cargo);
    	historicoAtual.setFaixaSalarial(faixaSalarial);

    	HistoricoColaborador historicoTmp = new HistoricoColaborador();
    	historicoTmp.setData(DateUtil.criarAnoMesDia(2008, 01, 01));

    	Funcao funcao = new Funcao();
    	funcao.setId(1L);
		historicoTmp.setFuncao(funcao);

		Ambiente ambiente = new Ambiente();
    	ambiente.setId(1L);
		historicoTmp.setAmbiente(ambiente);

    	Collection<Funcao> funcoes = new ArrayList<Funcao>();
    	funcoes.add(funcao);

    	Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
    	ambientes.add(ambiente);

		action.setHistoricoColaborador(historicoTmp);
    	action.setColaborador(colaborador);

    	historicoColaboradorManager.expects(once()).method("getHistoricoAtual").with(eq(colaborador.getId())).will(returnValue(historicoAtual));
    	historicoColaboradorManager.expects(once()).method("existeHistoricoData").with(eq(historicoTmp)).will(returnValue(true));

    	colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
    	historicoColaboradorManager.expects(once()).method("getHistoricoAtual").with(eq(colaborador.getId())).will(returnValue(historicoAtual));
    	funcaoManager.expects(once()).method("findByCargo").with(new Constraint[]{ANYTHING}).will(returnValue(funcoes));
    	ambienteManager.expects(once()).method("findAmbientes").with(new Constraint[]{ANYTHING}).will(returnValue(ambientes));

    	assertEquals(action.mudaFuncao(), "input");

    }

    public void testMudaFuncaoEmpresasDiferentes() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(5648L);
    	Colaborador colaborador = new Colaborador();
    	colaborador.setId(1L);
    	colaborador.setEmpresa(empresa);

    	HistoricoColaborador historicoAtual = new HistoricoColaborador();
    	historicoAtual.setId(1L);
    	historicoAtual.setData(DateUtil.criarAnoMesDia(2008, 01, 01));
    	historicoAtual.setColaborador(colaborador);
    	Cargo cargo = new Cargo();
    	cargo.setId(1L);
    	FaixaSalarial faixaSalarial = new FaixaSalarial();
    	faixaSalarial.setCargo(cargo);

    	HistoricoColaborador historicoTmp = new HistoricoColaborador();
    	historicoTmp.setData(DateUtil.criarAnoMesDia(2008, 01, 01));

    	Funcao funcao = new Funcao();
    	funcao.setId(1L);
		historicoTmp.setFuncao(funcao);

		Ambiente ambiente = new Ambiente();
    	ambiente.setId(1L);
		historicoTmp.setAmbiente(ambiente);

    	Collection<Funcao> funcoes = new ArrayList<Funcao>();
    	funcoes.add(funcao);

    	Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
    	ambientes.add(ambiente);

		action.setHistoricoColaborador(historicoTmp);
    	action.setColaborador(colaborador);

    	historicoColaboradorManager.expects(once()).method("getHistoricoAtual").with(eq(colaborador.getId())).will(returnValue(historicoAtual));
    	historicoColaboradorManager.expects(once()).method("existeHistoricoData").with(eq(historicoTmp)).will(returnValue(false));

    	colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
//    	historicoColaboradorManager.expects(once()).method("getHistoricoAtual").with(eq(colaborador)).will(returnValue(historicoAtual));
//    	funcaoManager.expects(once()).method("findByCargo").with(new Constraint[]{ANYTHING}).will(returnValue(funcoes));
//    	ambienteManager.expects(once()).method("findAmbientes").with(new Constraint[]{ANYTHING}).will(returnValue(ambientes));

    	assertEquals(action.mudaFuncao(), "input");
    }

    public void testGetSet() throws Exception
    {
    	Funcao funcao = new Funcao();
    	funcao.setId(1L);

    	Cargo cargo = new Cargo();
    	cargo.setId(1L);

    	Collection<Cargo> cargos = new ArrayList<Cargo>();

    	action.setFuncao(funcao);
    	action.setCargoTmp(cargo);
    	action.setCargos(cargos);

    	assertEquals(action.getFuncao(), funcao);
    	assertEquals(action.getCargoTmp(), cargo);
    	assertEquals(action.getCargos(), cargos);

    	funcao = null;
    	action.setFuncao(funcao);
    	assertTrue(action.getFuncao() instanceof Funcao);

    	action.setAmbientes(null);
    	action.getAmbientes();
    	action.getColaborador();
    	action.getHistoricoColaborador();
    	action.getFuncaos();
    	action.setFuncaos(null);
    	action.getPage();
    	action.setPage(1);
    	action.getAreaBusca();
    	action.setAreaBusca(null);
    	action.getNomeBusca();
    	action.setNomeBusca("");
    	action.getExamesChecked();
    }
}









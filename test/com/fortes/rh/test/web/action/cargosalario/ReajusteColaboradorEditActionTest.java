package com.fortes.rh.test.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.cargosalario.ReajusteColaboradorEditAction;
import com.opensymphony.xwork.ActionContext;

public class ReajusteColaboradorEditActionTest extends MockObjectTestCase 
{
	private ReajusteColaboradorEditAction action;
	private Mock manager;
	private Mock ambienteManager;
	
	private Mock indiceManager;
	private Mock areaOrganizacionalManager;
	private Mock colaboradorManager;
	private Mock tabelaReajusteColaboradorManager;
	private Mock estabelecimentoManager;
	private Mock faixaSalarialManager;
	private Mock funcaoManager;
	
	@Override
	protected void setUp() throws Exception 
	{
		action = new ReajusteColaboradorEditAction();
		
		manager = mock(ReajusteColaboradorManager.class);
		action.setReajusteColaboradorManager((ReajusteColaboradorManager) manager.proxy());
		
		ambienteManager = mock(AmbienteManager.class);
		action.setAmbienteManager((AmbienteManager) ambienteManager.proxy());
		
		indiceManager = mock(IndiceManager.class);
		action.setIndiceManager((IndiceManager) indiceManager.proxy());
		
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		
		colaboradorManager = mock(ColaboradorManager.class);
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		
		tabelaReajusteColaboradorManager = mock(TabelaReajusteColaboradorManager.class);
		action.setTabelaReajusteColaboradorManager((TabelaReajusteColaboradorManager) tabelaReajusteColaboradorManager.proxy());
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		
		faixaSalarialManager = mock(FaixaSalarialManager.class);
		action.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());
		
		funcaoManager = mock(FuncaoManager.class);
		action.setFuncaoManager((FuncaoManager) funcaoManager.proxy());
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(5L));
	}
	
	@Override
	protected void tearDown() throws Exception 
	{
		action = null;
		manager = null;
		MockSecurityUtil.verifyRole = false;
	}
	
	public void testInsertSolicitacaoReajuste() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Long[] empresasPermitidas = new Long[]{empresa.getId()};
		
		FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity(1L);
		FaixaSalarial faixaSalarialProposta = FaixaSalarialFactory.getEntity(2L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Colaboraor 1");
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 2012));
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador.setData(new Date());
		
		action.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		action.setColaborador(colaborador);
		action.setFaixaSalarialAtual(faixaSalarialAtual);
		action.setFaixaSalarialProposta(faixaSalarialProposta);
		action.setFuncaoAtual(new Funcao());
		action.setAmbienteAtual(new Ambiente());
		action.setFuncaoProposta(new Funcao());
		action.setAmbienteProposto(new Ambiente());
		
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setFaixaSalarialAtual(faixaSalarialAtual);
		reajusteColaborador.setFaixaSalarialProposta(faixaSalarialProposta);
		
		action.setReajusteColaborador(reajusteColaborador);
		action.setColaborador(colaborador);
		action.setEmpresaSistema(empresa);
		
		tabelaReajusteColaboradorManager.expects(once()).method("findByIdProjection").with(eq(tabelaReajusteColaborador.getId())).will(returnValue(tabelaReajusteColaborador));
		colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(eq(colaborador.getId()), ANYTHING).will(returnValue(colaborador));
		manager.expects(once()).method("validaSolicitacaoReajuste").isVoid();
		manager.expects(once()).method("insertSolicitacaoReajuste").with(eq(reajusteColaborador), eq(empresa.getId()), eq(colaborador)).isVoid();
		
		mocksPrepareSolicitacaoReajuste(empresasPermitidas);
		
		assertEquals("success", action.insertSolicitacaoReajuste());
		assertEquals("Solicitação de realinhamento incluída com sucesso", action.getActionSuccess().toArray()[0]);
	}
	
	private void mocksPrepareSolicitacaoReajuste(Long[] empresasPermitidas)
	{
		Collection<AreaOrganizacional> areaOrganizacionalsPropostas = new ArrayList<AreaOrganizacional>();
		areaOrganizacionalsPropostas.add(AreaOrganizacionalFactory.getEntity(1L));
		areaOrganizacionalsPropostas.add(AreaOrganizacionalFactory.getEntity(2L));
		
		indiceManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Indice>()));
		
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(AreaOrganizacional.TODAS), ANYTHING, eq(empresasPermitidas)).will(returnValue(new ArrayList<AreaOrganizacional>()));
		
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionalsPropostas));
		
		tabelaReajusteColaboradorManager.expects(once()).method("findAllSelectByNaoAprovada").with(eq(action.getEmpresaSistema().getId()), eq(TipoReajuste.COLABORADOR)).will(returnValue(new ArrayList<TabelaReajusteColaborador>()));
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<Estabelecimento>()));
		faixaSalarialManager.expects(once()).method("findFaixas").with(eq(action.getEmpresaSistema()),eq(Cargo.ATIVO), ANYTHING).will(returnValue(new ArrayList<Estabelecimento>()));
	}

	
	public void testPrepareSolicitacaoReajuste() throws Exception
	{
		Long[] empresasPermitidas = new Long[]{action.getEmpresaSistema().getId()};
		mocksPrepareSolicitacaoReajuste(empresasPermitidas);
		
		assertEquals("success",action.prepareSolicitacaoReajuste());
	}
	
	public void testPrepareInsert() throws Exception
	{
		assertEquals("success", action.prepareInsert());
	}
	
	public void testPrepareUpdate() throws Exception
	{
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setAreaOrganizacionalAtual(AreaOrganizacionalFactory.getEntity(1L));
		reajusteColaborador.setAreaOrganizacionalProposta(AreaOrganizacionalFactory.getEntity(2L));
		reajusteColaborador.setEstabelecimentoAtual(EstabelecimentoFactory.getEntity(1L));
		reajusteColaborador.setEstabelecimentoProposto(EstabelecimentoFactory.getEntity(2L));
		reajusteColaborador.setFaixaSalarialAtual(FaixaSalarialFactory.getEntity(1L));
		reajusteColaborador.setFaixaSalarialProposta(FaixaSalarialFactory.getEntity(2L));
		
		reajusteColaborador.setAmbienteAtual(new Ambiente());
		reajusteColaborador.setFuncaoAtual(new Funcao());
		
		action.setReajusteColaborador(reajusteColaborador);
		
		manager.expects(once()).method("getSituacaoReajusteColaborador").with(eq(1L)).will(returnValue(reajusteColaborador));
		indiceManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Indice>()));
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<Estabelecimento>()));
		
		Colaborador colaboradorLogado = ColaboradorFactory.getEntity(10L);
		colaboradorManager.expects(once()).method("findByUsuario").will(returnValue(colaboradorLogado));
		
		areaOrganizacionalManager.expects(once()).method("findAllList")
						.with(eq(colaboradorLogado.getId()), eq(action.getEmpresaSistema().getId()),eq(AreaOrganizacional.ATIVA), ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
		
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(new ArrayList<AreaOrganizacional>()));
		
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").will(returnValue(new AreaOrganizacional()));
		
		funcaoManager.expects(once()).method("findFuncaoByFaixa").will(returnValue(new ArrayList<Funcao>()));
		ambienteManager.expects(once()).method("findByEstabelecimento").will(returnValue(new ArrayList<Ambiente>()));
		faixaSalarialManager.expects(once()).method("findFaixas").with(eq(action.getEmpresaSistema()),eq(Cargo.ATIVO), ANYTHING).will(returnValue(new ArrayList<Estabelecimento>()));
		manager.expects(once()).method("calculaSalarioProposto").with(eq(reajusteColaborador));
		
		assertEquals("success", action.prepareUpdate());
	}
	
	public void testUpdate() throws Exception
	{
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setAreaOrganizacionalAtual(AreaOrganizacionalFactory.getEntity(1L));
		reajusteColaborador.setAreaOrganizacionalProposta(AreaOrganizacionalFactory.getEntity(2L));
		action.setReajusteColaborador(reajusteColaborador);
		
		areaOrganizacionalManager.expects(once()).method("verificaMaternidade").will(returnValue(false));
		manager.expects(once()).method("updateReajusteColaborador").with(eq(reajusteColaborador));
		
		assertEquals("success", action.update());
	}
	
	public void testUpdateException() throws Exception
	{
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setAreaOrganizacionalAtual(AreaOrganizacionalFactory.getEntity(1L));
		reajusteColaborador.setAreaOrganizacionalProposta(AreaOrganizacionalFactory.getEntity(2L));
		reajusteColaborador.setEstabelecimentoAtual(EstabelecimentoFactory.getEntity(1L));
		reajusteColaborador.setEstabelecimentoProposto(EstabelecimentoFactory.getEntity(2L));
		reajusteColaborador.setFaixaSalarialAtual(FaixaSalarialFactory.getEntity(1L));
		reajusteColaborador.setFaixaSalarialProposta(FaixaSalarialFactory.getEntity(2L));
		
		reajusteColaborador.setAmbienteAtual(new Ambiente());
		reajusteColaborador.setFuncaoAtual(new Funcao());
		action.setReajusteColaborador(reajusteColaborador);
		
		areaOrganizacionalManager.expects(once()).method("verificaMaternidade").will(returnValue(true));
		
		manager.expects(once()).method("getSituacaoReajusteColaborador").with(eq(1L)).will(returnValue(reajusteColaborador));
		indiceManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Indice>()));
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<Estabelecimento>()));
		
		Colaborador colaboradorLogado = ColaboradorFactory.getEntity(10L);
		colaboradorManager.expects(once()).method("findByUsuario").will(returnValue(colaboradorLogado));
		areaOrganizacionalManager.expects(once()).method("findAllList").with(eq(colaboradorLogado.getId()), eq(action.getEmpresaSistema().getId()),eq(AreaOrganizacional.ATIVA), ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(new ArrayList<AreaOrganizacional>()));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").will(returnValue(new AreaOrganizacional()));
		funcaoManager.expects(once()).method("findFuncaoByFaixa").will(returnValue(new ArrayList<Funcao>()));
		ambienteManager.expects(once()).method("findByEstabelecimento").will(returnValue(new ArrayList<Ambiente>()));
		faixaSalarialManager.expects(once()).method("findFaixas").with(eq(action.getEmpresaSistema()),eq(Cargo.ATIVO), ANYTHING).will(returnValue(new ArrayList<Estabelecimento>()));
		manager.expects(once()).method("calculaSalarioProposto").with(eq(reajusteColaborador));
		
		assertEquals("input", action.update());
		assertEquals("Não é possível fazer solicitações para áreas que possuem sub-áreas.", action.getActionErrors().toArray()[0]);
	}
	
	public void testGetSets()
	{
		action.setReajusteColaborador(null);
		assertNotNull(action.getReajusteColaborador());
		assertNotNull(action.getModel());
		
		action.getAreasCheck();
		action.setAreasCheck(new String[]{"1"});
		action.getEstabelecimentosCheck();
		action.setEstabelecimentosCheck(new String[]{"1"});
		action.getAreasCheckList();
		action.getEstabelecimentosCheckList();
		action.getGruposCheck();
		action.setGruposCheck(new String[]{"1"});
		action.getGruposCheckList();
		
		action.getFaixaSalarialProposta();
		action.getFaixaSalarialAtual();
		action.setFaixaSalarials(new ArrayList<FaixaSalarial>());
		action.getFaixaSalarials();
		action.setDescricaoTipoSalario("Por Cargo");
		action.getDescricaoTipoSalario();
		
		action.setValor(10.0);
		action.getValor();
		action.getSalarioCalculado();
		action.setTipoAplicacaoIndice(new TipoAplicacaoIndice());
		action.getTipoAplicacaoIndice();
		
		action.getFuncaoAtual();
		action.getFuncaoProposta();
		action.getAmbienteAtual();
		action.getAmbienteProposto();
		action.getAmbientes();
		
		action.getDissidioPor();
		action.setDissidioPor('1');
		action.getValorDissidio();
		action.setValorDissidio(10d);
		action.getFiltrarPor();
		action.setFiltrarPor(' ');
		action.isExibeSalario();
		action.getTipoSalario();
		action.setTipoSalario(new TipoAplicacaoIndice());
		action.getTiposSalarios();
		action.setTiposSalarios(new HashMap<Object, Object>());
		action.getIndices();
		action.setIndices(new ArrayList<Indice>());
		action.getEstabelecimentos();
		action.getEstabelecimentoAtual();
		action.getEstabelecimentoProposto();
		action.setEstabelecimentoProposto(new Estabelecimento());
		action.setEstabelecimentoAtual(null);
		action.setFuncaos(null);
		action.getFuncaos();
		action.setAmbientes(null);
		action.getGrupoOcupacionalFiltro();
		action.setGrupoOcupacionalFiltro(null);
		action.getAreaOrganizacionalFiltro();
		action.setAreaOrganizacionalFiltro(null);
	}
}

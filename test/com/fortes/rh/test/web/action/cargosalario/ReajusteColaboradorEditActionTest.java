package com.fortes.rh.test.web.action.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.cargosalario.ReajusteColaboradorEditAction;
import com.opensymphony.xwork.ActionContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtil.class,ActionContext.class})
@SuppressWarnings("unchecked")
public class ReajusteColaboradorEditActionTest
{
	private ReajusteColaboradorEditAction action;
	private ReajusteColaboradorManager manager;
	private AmbienteManager ambienteManager;
	
	private IndiceManager indiceManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ColaboradorManager colaboradorManager;
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;
	private FaixaSalarialManager faixaSalarialManager;
	private FuncaoManager funcaoManager;
	
	@Before
	public void setUp() 
	{
		action = new ReajusteColaboradorEditAction();
		
		manager = mock(ReajusteColaboradorManager.class);
		action.setReajusteColaboradorManager(manager);
		
		ambienteManager = mock(AmbienteManager.class);
		action.setAmbienteManager(ambienteManager);
		
		indiceManager = mock(IndiceManager.class);
		action.setIndiceManager(indiceManager);
		
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		
		colaboradorManager = mock(ColaboradorManager.class);
		action.setColaboradorManager(colaboradorManager);
		
		tabelaReajusteColaboradorManager = mock(TabelaReajusteColaboradorManager.class);
		action.setTabelaReajusteColaboradorManager(tabelaReajusteColaboradorManager);
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager(estabelecimentoManager);
		
		faixaSalarialManager = mock(FaixaSalarialManager.class);
		action.setFaixaSalarialManager(faixaSalarialManager);
		
		funcaoManager = mock(FuncaoManager.class);
		action.setFuncaoManager(funcaoManager);
		
        PowerMockito.mockStatic(SecurityUtil.class);
        PowerMockito.mockStatic(ActionContext.class);
        
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(5L));
	}
	
	@Test
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
		
		Integer statusRetornoAC = null;
		
		when(tabelaReajusteColaboradorManager.findByIdProjection(eq(tabelaReajusteColaborador.getId()))).thenReturn(tabelaReajusteColaborador);
		when(colaboradorManager.findByIdDadosBasicos(eq(colaborador.getId()), eq(statusRetornoAC))).thenReturn(colaborador);
		doNothing().when(manager).validaSolicitacaoReajuste(reajusteColaborador);
		doNothing().when(manager).insertSolicitacaoReajuste(eq(reajusteColaborador), eq(empresa.getId()), eq(colaborador));

		mocksPrepareSolicitacaoReajuste(empresasPermitidas);
		
		assertEquals("success", action.insertSolicitacaoReajuste());
		assertEquals("Solicitação de realinhamento incluída com sucesso", action.getActionSuccess().toArray()[0]);
	}
	
	private void mocksPrepareSolicitacaoReajuste(Long[] empresasPermitidas) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionalsPropostas = new ArrayList<AreaOrganizacional>();
		areaOrganizacionalsPropostas.add(AreaOrganizacionalFactory.getEntity(1L));
		areaOrganizacionalsPropostas.add(AreaOrganizacionalFactory.getEntity(2L));
		
		when(indiceManager.findAll(action.getEmpresaSistema())).thenReturn(new ArrayList<Indice>());
		
		when(areaOrganizacionalManager.findAllListAndInativas(eq(AreaOrganizacional.TODAS), anyCollection(), eq(empresasPermitidas))).thenReturn(new ArrayList<AreaOrganizacional>());
		
		when(areaOrganizacionalManager.montaFamilia(areaOrganizacionalsPropostas)).thenReturn(areaOrganizacionalsPropostas);
		
		when(tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(eq(action.getEmpresaSistema().getId()), eq(TipoReajuste.COLABORADOR))).thenReturn(new ArrayList<TabelaReajusteColaborador>());
		when(estabelecimentoManager.findAllSelect(eq(action.getEmpresaSistema().getId()))).thenReturn(new ArrayList<Estabelecimento>());
		when(faixaSalarialManager.findFaixas(eq(action.getEmpresaSistema()),eq(Cargo.ATIVO), anyLong())).thenReturn(new ArrayList<FaixaSalarial>());
		when(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession())).thenReturn(UsuarioFactory.getEntity(1L));
	}

	@Test
	public void testPrepareSolicitacaoReajuste() throws Exception
	{
		Long[] empresasPermitidas = new Long[]{action.getEmpresaSistema().getId()};
		mocksPrepareSolicitacaoReajuste(empresasPermitidas);
		
		assertEquals("success",action.prepareSolicitacaoReajuste());
	}
	
	@Test
	public void testPrepareInsert() throws Exception
	{
		assertEquals("success", action.prepareInsert());
	}
	
	@Before
	public void prepareSession(){
		Map<Object, Object> map = new HashMap<Object, Object>();
		when(ActionContext.getContext()).thenReturn(mock(ActionContext.class));
		when(ActionContext.getContext().getSession()).thenReturn(map);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
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
		
		Map session = ActionContext.getContext().getSession();
		action.setReajusteColaborador(reajusteColaborador);
		
		when(manager.getSituacaoReajusteColaborador(eq(1L))).thenReturn(reajusteColaborador);
		when(indiceManager.findAll(action.getEmpresaSistema())).thenReturn(new ArrayList<Indice>());
		when(estabelecimentoManager.findAllSelect(eq(action.getEmpresaSistema().getId()))).thenReturn(new ArrayList<Estabelecimento>());
		
		Colaborador colaboradorLogado = ColaboradorFactory.getEntity(10L);
		when(colaboradorManager.findByUsuario(anyLong())).thenReturn(colaboradorLogado.getId());
		
		when(areaOrganizacionalManager.findAllList(eq(colaboradorLogado.getId()), eq(action.getEmpresaSistema().getId()),eq(AreaOrganizacional.ATIVA), anyLong())).thenReturn(new ArrayList<AreaOrganizacional>());
		
		when(areaOrganizacionalManager.montaFamilia(anyCollection())).thenReturn(new ArrayList<AreaOrganizacional>());
		
		when(areaOrganizacionalManager.getAreaOrganizacional(anyCollection(), anyLong())).thenReturn(new AreaOrganizacional());
		
		when(funcaoManager.findByEmpresa(anyLong())).thenReturn(new ArrayList<Funcao>());
		when(ambienteManager.findByEstabelecimento(any(Long[].class))).thenReturn(new ArrayList<Ambiente>());
		when(faixaSalarialManager.findFaixas(eq(action.getEmpresaSistema()),eq(Cargo.ATIVO), anyLong())).thenReturn(new ArrayList<FaixaSalarial>());
		when(manager.calculaSalarioProposto(eq(reajusteColaborador))).thenReturn(1000.0);
		
		when(SecurityUtil.getIdUsuarioLoged(session)).thenReturn(1l);
	}
	
	@Test
	public void testUpdate() throws Exception
	{
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setAreaOrganizacionalAtual(AreaOrganizacionalFactory.getEntity(1L));
		reajusteColaborador.setAreaOrganizacionalProposta(AreaOrganizacionalFactory.getEntity(2L));
		action.setReajusteColaborador(reajusteColaborador);
		
		Boolean ativa = null;
		
		when(areaOrganizacionalManager.verificaMaternidade(eq(reajusteColaborador.getAreaOrganizacionalProposta().getId()), eq(ativa))).thenReturn(false);
		doNothing().when(manager).updateReajusteColaborador(eq(reajusteColaborador));
		
		assertEquals("success", action.update());
	}
	
	@Test
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
		
		when(areaOrganizacionalManager.verificaMaternidade(anyLong(), anyBoolean())).thenReturn(true);
		
		when(manager.getSituacaoReajusteColaborador(eq(1L))).thenReturn(reajusteColaborador);
		when(indiceManager.findAll(action.getEmpresaSistema())).thenReturn(new ArrayList<Indice>());
		when(estabelecimentoManager.findAllSelect(eq(action.getEmpresaSistema().getId()))).thenReturn(new ArrayList<Estabelecimento>());
		
		Colaborador colaboradorLogado = ColaboradorFactory.getEntity(10L);
		when(colaboradorManager.findByUsuario(anyLong())).thenReturn(colaboradorLogado.getId());
		when(areaOrganizacionalManager.findAllList(eq(colaboradorLogado.getId()), eq(action.getEmpresaSistema().getId()),eq(AreaOrganizacional.ATIVA), anyLong())).thenReturn(new ArrayList<AreaOrganizacional>());
		when(areaOrganizacionalManager.montaFamilia(anyCollection())).thenReturn(new ArrayList<AreaOrganizacional>());
		when(areaOrganizacionalManager.getAreaOrganizacional(anyCollection(), anyLong())).thenReturn(new AreaOrganizacional());
		when(funcaoManager.findByEmpresa(anyLong())).thenReturn(new ArrayList<Funcao>());
		when(ambienteManager.findByEstabelecimento(any(Long[].class))).thenReturn(new ArrayList<Ambiente>());
		when(faixaSalarialManager.findFaixas(eq(action.getEmpresaSistema()),eq(Cargo.ATIVO), anyLong())).thenReturn(new ArrayList<FaixaSalarial>());
		when(manager.calculaSalarioProposto(eq(reajusteColaborador))).thenReturn(1500.0);
		
		assertEquals("input", action.update());
		assertEquals("Não é possível fazer solicitações para áreas que possuem sub-áreas.", action.getActionErrors().toArray()[0]);
	}
	
	@Test
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

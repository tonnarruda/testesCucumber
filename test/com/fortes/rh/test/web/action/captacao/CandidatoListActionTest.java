package com.fortes.rh.test.web.action.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.model.type.File;
import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.CandidatoCurriculoManager;
import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoImpressaoCurriculoManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.IdiomaManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.business.geral.AreaInteresseManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CamposExtrasManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoImpressaoCurriculoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.EmpresaUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.captacao.CandidatoListAction;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class CandidatoListActionTest 
{
	private CandidatoListAction action;
	private CandidatoManager candidatoManager;
	private ColaboradorManager colaboradorManager;
	private CargoManager cargoManager;
	private FaixaSalarialManager faixaSalarialManager;
	private AnuncioManager anuncioManager;
	private AreaInteresseManager areaInteresseManager;
	private AreaFormacaoManager areaFormacaoManager;
	private ConhecimentoManager conhecimentoManager;
	private IdiomaManager idiomaManager;
	private EstadoManager estadoManager;
	private CidadeManager cidadeManager;
    private BairroManager bairroManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private FormacaoManager formacaoManager;
	private ExperienciaManager experienciaManager;
	private CandidatoIdiomaManager candidatoIdiomaManager;
	private HistoricoCandidatoManager historicoCandidatoManager;
	private SolicitacaoManager solicitacaoManager;
	private EmpresaManager empresaManager;
	private CandidatoCurriculoManager candidatoCurriculoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private GrupoOcupacionalManager grupoOcupacionalManager;
	private ConfiguracaoImpressaoCurriculoManager configuracaoImpressaoCurriculoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private CamposExtrasManager camposExtrasManager;
	
	@Before
	public void setUp() throws Exception {
		action = new CandidatoListAction();
		
		candidatoManager = mock(CandidatoManager.class);
		action.setCandidatoManager(candidatoManager);
		
		colaboradorManager = mock(ColaboradorManager.class);
		action.setColaboradorManager(colaboradorManager);
		
		cargoManager = mock(CargoManager.class);
		action.setCargoManager(cargoManager);
		
		faixaSalarialManager = mock(FaixaSalarialManager.class);
		action.setFaixaSalarialManager(faixaSalarialManager);
		
		anuncioManager = mock(AnuncioManager.class);
		action.setAnuncioManager(anuncioManager);
		
		areaInteresseManager = mock(AreaInteresseManager.class);
		action.setAreaInteresseManager(areaInteresseManager);
		
		areaFormacaoManager = mock(AreaFormacaoManager.class);
		action.setAreaFormacaoManager(areaFormacaoManager);
		
		conhecimentoManager = mock(ConhecimentoManager.class);
		action.setConhecimentoManager(conhecimentoManager);
		
		idiomaManager = mock(IdiomaManager.class);
		action.setIdiomaManager(idiomaManager);
		
		estadoManager = mock(EstadoManager.class);
		action.setEstadoManager(estadoManager);
		
		cidadeManager = mock(CidadeManager.class);
		action.setCidadeManager(cidadeManager);
		
		bairroManager = mock(BairroManager.class);
		action.setBairroManager(bairroManager);
		
		candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
		action.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);
		
		formacaoManager = mock(FormacaoManager.class);
		action.setFormacaoManager(formacaoManager);
		
		experienciaManager = mock(ExperienciaManager.class);
		action.setExperienciaManager(experienciaManager);
		
		candidatoIdiomaManager = mock(CandidatoIdiomaManager.class);
		action.setCandidatoIdiomaManager(candidatoIdiomaManager);
		
		historicoCandidatoManager = mock(HistoricoCandidatoManager.class);
		action.setHistoricoCandidatoManager(historicoCandidatoManager);
		
		solicitacaoManager = mock(SolicitacaoManager.class);
		action.setSolicitacaoManager(solicitacaoManager);
		
		empresaManager = mock(EmpresaManager.class);
		action.setEmpresaManager(empresaManager);
		
		candidatoCurriculoManager = mock(CandidatoCurriculoManager.class);
		action.setCandidatoCurriculoManager(candidatoCurriculoManager);
		
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager(estabelecimentoManager);
		
		grupoOcupacionalManager = mock(GrupoOcupacionalManager.class);
		action.setGrupoOcupacionalManager(grupoOcupacionalManager);
		
		configuracaoImpressaoCurriculoManager = mock(ConfiguracaoImpressaoCurriculoManager.class);
		action.setConfiguracaoImpressaoCurriculoManager(configuracaoImpressaoCurriculoManager);
		
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
		
		configuracaoCampoExtraManager = mock(ConfiguracaoCampoExtraManager.class);
		action.setConfiguracaoCampoExtraManager(configuracaoCampoExtraManager);
		
		camposExtrasManager = mock(CamposExtrasManager.class);
		action.setCamposExtrasManager(camposExtrasManager);
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
	}

	@Test
	public void testList() throws Exception{
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistema.setCompartilharColaboradores(true);
		action.setParametrosDoSistema(parametrosDoSistema);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		Collection<Empresa> empresas = Arrays.asList(empresa);
		Collection<Candidato> candidatos = Arrays.asList(CandidatoFactory.getCandidato(1L, "Candidato 1"));
		
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
        when(empresaManager.findEmpresasPermitidas(true, action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()))).thenReturn(empresas);
        when(candidatoManager.getCount(anyString(),anyString(), anyString(), anyString(), anyString(), anyString(), anyChar(), any(Date.class), any(Date.class), anyString(), anyBoolean(), anyBoolean(), eq(new Long[]{empresa.getId()}))).thenReturn(1);
        when(candidatoManager.list(eq(1),eq(15), eq(""), eq(""),eq(""), eq(""), eq(""), eq(""), anyChar(), any(Date.class), any(Date.class), eq(""),eq(false), eq(false), eq(new Long[]{empresa.getId()}))).thenReturn(candidatos);
		assertEquals("success",action.list());
	}
	
	@Test
	public void testDelete() throws Exception{
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidato(candidato);
		action.delete();
		assertEquals("Candidato excluído com sucesso!",action.getActionSuccess().iterator().next());
	}
	
	@Test
	public void testDeleteException() throws Exception{
		Exception exception = null;
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidato(candidato);
		doThrow(new Exception()).when(candidatoManager).removeCandidato(candidato);
		try {
			action.delete();
		} catch (Exception e) {
			exception = e;
		}
 		assertNotNull(exception);
	}
	
	@Test
	public void testLlistCbo() throws Exception{
		assertEquals("success", action.listCbo());
	}
	

	@Test
	public void testPrepareBusca() throws Exception {
		montaPreparaBusca();
		assertEquals("success", action.prepareBusca());
	}

	private void montaPreparaBusca() throws Exception {
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistema.setCompartilharColaboradores(true);
		action.setParametrosDoSistema(parametrosDoSistema);
		
		Collection<Estado> estados = new ArrayList<Estado>();
		Collection<Idioma> idiomas = new ArrayList<Idioma>();
		Collection<Cargo> cargos =Arrays.asList(CargoFactory.getEntity(1L));
		Collection<AreaInteresse> areaInteresses = Arrays.asList(AreaInteresseFactory.getAreaInteresse(1L));
		Collection<AreaFormacao> areaFormacaos = new ArrayList<AreaFormacao>();
		Collection<CheckBox> conhecimentosCheckList = new ArrayList<CheckBox>();
		Collection<Cidade> cidades = new ArrayList<Cidade>();
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		Collection<Empresa> empresas = Arrays.asList(empresa);
		
		action.setUf(23L);
		
		when(estadoManager.findAll(eq(new String[]{"sigla"}))).thenReturn(estados);
		when(idiomaManager.findAll(new String[]{"nome"})).thenReturn(idiomas);
		
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
        when(empresaManager.findEmpresasPermitidas(true, action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()))).thenReturn(empresas);

		when(cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, EmpresaUtil.empresasSelecionadas(action.getEmpresaSistema().getId(), empresas))).thenReturn(cargos);
		when(areaInteresseManager.findAllSelect(EmpresaUtil.empresasSelecionadas(action.getEmpresaSistema().getId(), empresas))).thenReturn(areaInteresses);
		when(areaFormacaoManager.findAll()).thenReturn(areaFormacaos);
		when(conhecimentoManager.populaCheckOrderNome(EmpresaUtil.empresasSelecionadas(action.getEmpresaSistema().getId(), empresas))).thenReturn(conhecimentosCheckList);
		when(cidadeManager.find(new String[]{"uf.id"},new Object[]{action.getUf()}, new String[]{"nome"})).thenReturn(cidades);
	}
	
	@Test
	public void testPrepareBuscaComSolicitacaoDefinindoCidade() throws Exception {
		montaPreparaBusca();
		Cargo cargo = CargoFactory.getEntity(2L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(2L, "Faixa", cargo);
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		solicitacao.setFaixaSalarial(faixaSalarial);
		action.setSolicitacao(solicitacao);
		when(solicitacaoManager.findByIdProjection(action.getSolicitacao().getId())).thenReturn(action.getSolicitacao());
		assertEquals("success", action.prepareBusca());
	}
	
	@Test
	public void testPrepareBuscaComSolicitacaoSemDefinirCidade() throws Exception {
		montaPreparaBusca();
		Cargo cargo = CargoFactory.getEntity(2L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(2L, "Faixa", cargo);
		Cidade cidade = CidadeFactory.getEntity(2L);
		cidade.setUf(EstadoFactory.getEntity(23L));
		
		Collection<Cidade> cidades = Arrays.asList(cidade);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setCidade(cidade);
		
		action.setSolicitacao(solicitacao);
		when(solicitacaoManager.findByIdProjection(action.getSolicitacao().getId())).thenReturn(action.getSolicitacao());
		when(cidadeManager.find(new String[]{"uf.id"},new Object[]{action.getUf()}, new String[]{"nome"})).thenReturn(cidades);
		assertEquals("success", action.prepareBusca());
	}
	
	@Test
	public void testPrepareBuscaSimples() throws Exception{
		montaBuscaSimples();
		assertEquals("success", action.prepareBuscaSimples());
	}
	
	@Test
	public void testPrepareBuscaSimplesComSolicitacaoId() throws Exception{
		montaBuscaSimples();
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		action.setSolicitacao(solicitacao);
		when(solicitacaoManager.findByIdProjection(solicitacao.getId())).thenReturn(action.getSolicitacao());
		assertEquals("success", action.prepareBuscaSimples());
	}

	private void montaBuscaSimples() {
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistema.setCompartilharColaboradores(true);
		action.setParametrosDoSistema(parametrosDoSistema);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		Collection<Empresa> empresas = Arrays.asList(empresa);
		
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
        when(empresaManager.findEmpresasPermitidas(true, action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()))).thenReturn(empresas);

		when(cargoManager.populaCheckBox(false, EmpresaUtil.empresasSelecionadas(action.getEmpresaId(), empresas))).thenReturn(new ArrayList<CheckBox>());
		when(conhecimentoManager.populaCheckOrderNome(EmpresaUtil.empresasSelecionadas(action.getEmpresaId(), empresas))).thenReturn(new ArrayList<CheckBox>());
		when(estadoManager.findAll(new String[]{"sigla"})).thenReturn(new ArrayList<Estado>());
	}
	
	@Test
	public void testPrepareTriagemAutomaticaSemSolicitacaoId() throws Exception{
		assertEquals("success", action.prepareTriagemAutomatica());
	}
	
	@Test
	public void testPrepareTriagemAutomaticaCommSolicitacaoId() throws Exception{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		action.setSolicitacao(solicitacao);
		
		when(solicitacaoManager.findByIdProjectionForUpdate(action.getSolicitacao().getId())).thenReturn(solicitacao);
		
		assertEquals("success", action.prepareTriagemAutomatica());
	}
	
	@Test
	public void testPrepareTriagemColaboradores() throws Exception {
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		action.setSolicitacao(solicitacao);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistema.setCompartilharColaboradores(true);
		action.setParametrosDoSistema(parametrosDoSistema);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		Collection<Empresa> empresas = Arrays.asList(empresa);
		
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
        when(empresaManager.findEmpresasPermitidas(true, action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()))).thenReturn(empresas);

		
		when(solicitacaoManager.findByIdProjectionForUpdate(action.getSolicitacao().getId())).thenReturn(solicitacao);
		when(areaOrganizacionalManager.populaCheckOrderDescricao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
		when(faixaSalarialManager.findAllSelectByCargo(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<FaixaSalarial>());
		assertEquals("success", action.prepareTriagemColaboradores());
	}
	
	@Test
	public void testInsertColaboradoresComColaboradoresIdsNulo() throws Exception {
		action.setColaboradoresIds(null);
		assertEquals("success", action.insertColaboradores());
	}
	
	@Test
	public void testInsertColaboradoresComColaboradoresIdsVazio() throws Exception {
		action.setColaboradoresIds(new Long[]{});
		assertEquals("success", action.insertColaboradores());
	}
	
	@Test
	public void testInsertColaboradores() throws Exception {
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		action.setSolicitacao(solicitacao);
		action.setColaboradoresIds(new Long[]{1L,2L});
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		action.setEmpresaSistema(EmpresaFactory.getEmpresa());
		
		assertEquals("success", action.insertColaboradores());
	}
	
	@Test
	public void testBuscaSimplesCollectionVazia() throws Exception {
		montaBuscaSimples();
		action.buscaSimples();
		assertEquals("Não existem candidatos a serem listados", action.getActionMessages().iterator().next());
	}
	
	@Test
	public void testTriagemAutomatica() throws Exception {
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		action.setSolicitacao(solicitacao);
		
		when(solicitacaoManager.findByIdProjectionForUpdate(action.getSolicitacao().getId())).thenReturn(solicitacao);
		action.triagemAutomatica();

		assertEquals("Não existem candidatos a serem listados", action.getActionMessages().iterator().next());
	}
	
	@Test
	public void insertCandidatosComCandidatosIdNulo() throws Exception{
		action.setCandidatosId(null);
		assertEquals("success", action.insertCandidatos());
	}
	
	@Test
	public void testInsertCandidatosComCandidatosIdVazio() throws Exception{
		action.setCandidatosId(new String[]{});
		assertEquals("success", action.insertCandidatos());
	}
	
	@Test
	public void testInsertCandidatosComCandidatos() throws Exception{
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidatosId(new String[]{candidato.getId().toString()});
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		action.setSolicitacao(solicitacao);
		
		assertEquals("success", action.insertCandidatos());
	}
	
	@Test
	public void testSelecionaDestinatariosBDS() throws Exception {
		montaPreparaBusca();
		when(anuncioManager.getEmpresasCheck(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
		assertEquals("success", action.selecionaDestinatariosBDS());
	}
	
	@Test
	public void testExportaBDSComCandidatoIdNulo() throws Exception {
		action.setCandidatosId(null);
		montaPreparaBusca();
		assertEquals("success", action.exportaBDS());

	}
	
	@Test
	public void exportaBDSComCandidatoIdVazio() throws Exception {
		action.setCandidatosId(new String[]{});
		montaPreparaBusca();
		assertEquals("success", action.exportaBDS());
	}
	
	@Test
	public void testExportaBDSComCandidato() throws Throwable {
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidatosId(new String[]{candidato.getId().toString()});
		Collection<Candidato> candidatos = Arrays.asList(CandidatoFactory.getCandidato(1L, "Candidato 1"));
		montaPreparaBusca();
		
		when(candidatoManager.findCandidatosById(new Long[]{1L})).thenReturn(candidatos);
		when(candidatoManager.populaCandidatos(candidatos)).thenReturn(candidatos);
		when(candidatoManager.exportaCandidatosBDS(action.getEmpresaSistema(), candidatos, null, null, null)).thenReturn(true);
		assertEquals("success", action.exportaBDS());
		assertEquals("Candidatos exportados com sucesso.", action.getActionSuccess().iterator().next());
	}
	
	@Test
	public void testExportaBDSComCandidatoComExceptionThrowable() throws Throwable {
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidatosId(new String[]{candidato.getId().toString()});
		Collection<Candidato> candidatos = Arrays.asList(CandidatoFactory.getCandidato(1L, "Candidato 1"));
		montaPreparaBusca();
		
		when(candidatoManager.findCandidatosById(new Long[]{1L})).thenReturn(candidatos);
		when(candidatoManager.populaCandidatos(candidatos)).thenReturn(candidatos);
		doThrow(new Throwable()).when(candidatoManager).exportaCandidatosBDS(action.getEmpresaSistema(), candidatos, null, null, null);
		assertEquals("success", action.exportaBDS());
		assertEquals("Não foi possível exportar Candidatos.", action.getActionErrors().iterator().next());
	}

	@Test
	public void testVerCurriculoEscaneado() throws Exception {
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidato(candidato);
		when(candidatoCurriculoManager.findToList(new String[]{"curriculo"}, new String[]{"curriculo"}, new String[]{"candidato.id"}, new Object[]{action.getCandidato().getId()})).thenReturn(new ArrayList<CandidatoCurriculo>());
		assertEquals("success", action.verCurriculoEscaneado());
	}
	
	@Test
	public void testVerCurriculoTextoOcr() throws Exception{
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidato(candidato);
		action.setForma("3");
		action.setPalavras("Pedro da Silva");
		when(candidatoManager.getOcrTextoById(candidato.getId())).thenReturn("");
		assertEquals("success", action.verCurriculoTextoOcr());
	}
	
	@Test
	public void testVerCurriculoTextoOcrComPalavrasVazio() throws Exception{
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidato(candidato);
		action.setPalavras("");
		when(candidatoManager.getOcrTextoById(candidato.getId())).thenReturn("");
		assertEquals("success", action.verCurriculoTextoOcr());
	}
	
	@Test
	public void testVerCurriculoTextoOcrComFormaDiferenteDe3() throws Exception{
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidato(candidato);
		action.setForma("");
		action.setPalavras("Pedro da Silva");
		when(candidatoManager.getOcrTextoById(candidato.getId())).thenReturn("");
		assertEquals("success", action.verCurriculoTextoOcr());
	}
	
	@Test
	public void testVerExamePalografico() {
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidato(candidato);
		when(candidatoManager.findByIdProjection(action.getCandidato().getId())).thenReturn(candidato);
		assertEquals("success", action.verExamePalografico());
	}
	
	@Test
	public void testVerCurriculo() throws Exception {
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidato(candidato);
		when(candidatoManager.findByIdProjection(action.getCandidato().getId())).thenReturn(candidato);
		when(candidatoManager.getFoto(candidato.getId())).thenReturn(new File());

		when(candidatoManager.findCargosByCandidatoId(candidato.getId())).thenReturn(new ArrayList<Cargo>());
		when(formacaoManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Formacao>());
		when(candidatoIdiomaManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<CandidatoIdioma>());
		when(experienciaManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Experiencia>());
		when(conhecimentoManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Conhecimento>());
		assertEquals("success", action.verCurriculo());
	}
	
	@Test
	public void testVerCurriculoCandidatoSemFoto() throws Exception {
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidato(candidato);
		when(candidatoManager.findByIdProjection(action.getCandidato().getId())).thenReturn(candidato);
		doThrow(new Exception()).when(candidatoManager).getFoto(candidato.getId());

		when(candidatoManager.findCargosByCandidatoId(candidato.getId())).thenReturn(new ArrayList<Cargo>());
		when(formacaoManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Formacao>());
		when(candidatoIdiomaManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<CandidatoIdioma>());
		when(experienciaManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Experiencia>());
		when(conhecimentoManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Conhecimento>());
		assertEquals("success", action.verCurriculo());
	}
	

	@Test
	public void testVerCurriculoComEmpresaConfiguradoCampoExtraMasCandidatoSemInformacaoDeCampoExtra() throws Exception {
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		action.setCandidato(candidato);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraCandidato(true);
		action.setEmpresaSistema(empresa);
		
		when(candidatoManager.findByIdProjection(action.getCandidato().getId())).thenReturn(candidato);
		when(candidatoManager.getFoto(candidato.getId())).thenReturn(new File());

		when(candidatoManager.findCargosByCandidatoId(candidato.getId())).thenReturn(new ArrayList<Cargo>());
		when(formacaoManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Formacao>());
		when(candidatoIdiomaManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<CandidatoIdioma>());
		when(experienciaManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Experiencia>());
		when(conhecimentoManager.findByCandidato(candidato.getId())).thenReturn(new ArrayList<Conhecimento>());
		
		when(configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "empresa.id"}, new Object[]{true, action.getEmpresaSistema().getId()}, new String[]{"ordem"})).thenReturn(new ArrayList<ConfiguracaoCampoExtra>());

		assertEquals("success", action.verCurriculo());
	}
	
	@Test
	public void testVerCurriculoComEmpresaConfiguradoCampoExtraECandidatoSemInformacaoDeCampoExtra() throws Exception {
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		candidato.setCamposExtras(CamposExtrasFactory.getEntity(1L));
		action.setCandidato(candidato);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraCandidato(true);
		action.setEmpresaSistema(empresa);
		
		when(candidatoManager.findByIdProjection(action.getCandidato().getId())).thenReturn(candidato);
		when(candidatoManager.getFoto(action.getCandidato().getId())).thenReturn(new File());

		when(candidatoManager.findCargosByCandidatoId(action.getCandidato().getId())).thenReturn(new ArrayList<Cargo>());
		when(formacaoManager.findByCandidato(action.getCandidato().getId())).thenReturn(new ArrayList<Formacao>());
		when(candidatoIdiomaManager.findByCandidato(action.getCandidato().getId())).thenReturn(new ArrayList<CandidatoIdioma>());
		when(experienciaManager.findByCandidato(action.getCandidato().getId())).thenReturn(new ArrayList<Experiencia>());
		when(conhecimentoManager.findByCandidato(action.getCandidato().getId())).thenReturn(new ArrayList<Conhecimento>());
		
		when(camposExtrasManager.findById(action.getCandidato().getCamposExtras().getId())).thenReturn(new CamposExtras());
		when(configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "empresa.id"}, new Object[]{true, action.getEmpresaSistema().getId()}, new String[]{"ordem"})).thenReturn(new ArrayList<ConfiguracaoCampoExtra>());

		assertEquals("success", action.verCurriculo());
	}
	
	@Test
	public void testImprimirCurriculo() throws Exception {
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		candidato.setCamposExtras(CamposExtrasFactory.getEntity(1L));
		action.setCandidato(candidato);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		action.setSolicitacao(solicitacao);
		
		configuracaoImpressaoCurriculoManager.saveOrUpdate(any(ConfiguracaoImpressaoCurriculo.class));
		
		when(candidatoManager.findByIdProjection(action.getCandidato().getId())).thenReturn(candidato);
		when(candidatoManager.getFoto(action.getCandidato().getId())).thenReturn(new File());

		when(candidatoManager.findCargosByCandidatoId(action.getCandidato().getId())).thenReturn(new ArrayList<Cargo>());
		when(formacaoManager.findByCandidato(action.getCandidato().getId())).thenReturn(new ArrayList<Formacao>());
		when(candidatoIdiomaManager.findByCandidato(action.getCandidato().getId())).thenReturn(new ArrayList<CandidatoIdioma>());
		when(experienciaManager.findByCandidato(action.getCandidato().getId())).thenReturn(new ArrayList<Experiencia>());
		historicoCandidatoManager.findByCandidato(candidato);

		when(solicitacaoManager.findByIdProjectionForUpdate(action.getSolicitacao().getId())).thenReturn(solicitacao);
		assertEquals("success", action.imprimirCurriculo());
	}
	
	@Test
	public void testImprimirCurriculoComConfiguracaoCampoExtra() throws Exception {
		Candidato candidato = CandidatoFactory.getCandidato(1L, "Candidato 1");
		candidato.setCamposExtras(CamposExtrasFactory.getEntity(1L));
		action.setCandidato(candidato);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraCandidato(true);
		action.setEmpresaSistema(empresa);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		action.setSolicitacao(solicitacao);
		
		configuracaoImpressaoCurriculoManager.saveOrUpdate(any(ConfiguracaoImpressaoCurriculo.class));
		
		when(candidatoManager.findByIdProjection(action.getCandidato().getId())).thenReturn(candidato);
		when(candidatoManager.getFoto(action.getCandidato().getId())).thenReturn(new File());

		when(candidatoManager.findCargosByCandidatoId(action.getCandidato().getId())).thenReturn(new ArrayList<Cargo>());
		when(formacaoManager.findByCandidato(action.getCandidato().getId())).thenReturn(new ArrayList<Formacao>());
		when(candidatoIdiomaManager.findByCandidato(action.getCandidato().getId())).thenReturn(new ArrayList<CandidatoIdioma>());
		when(experienciaManager.findByCandidato(action.getCandidato().getId())).thenReturn(new ArrayList<Experiencia>());
		
		when(camposExtrasManager.findById(action.getCandidato().getCamposExtras().getId())).thenReturn(new CamposExtras());
		when(configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "empresa.id"}, new Object[]{true, action.getEmpresaSistema().getId()}, new String[]{"ordem"})).thenReturn(new ArrayList<ConfiguracaoCampoExtra>());

		historicoCandidatoManager.findByCandidato(candidato);

		when(solicitacaoManager.findByIdProjectionForUpdate(action.getSolicitacao().getId())).thenReturn(solicitacao);
		
		assertEquals("success", action.imprimirCurriculo());
	}
	
	@Test
	public void testPrepareRelatorioAvaliacaoCandidatos() throws Exception {
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		when(grupoOcupacionalManager.populaCheckOrderNome(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
		when(cargoManager.populaCheckBox(null, null, action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
		when(areaOrganizacionalManager.populaCheckOrderDescricao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
		assertEquals("success", action.prepareRelatorioAvaliacaoCandidatos());
	}
	
	@Test
	public void testRelatorioAvaliacaoCandidatosFiltrarPorAreas() throws Exception {
		Collection<AvaliacaoCandidatosRelatorio> avaliacaoCandidatos = new ArrayList<AvaliacaoCandidatosRelatorio>();
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraCandidato(true);
		action.setEmpresaSistema(empresa);
		action.setStatusSolicitacao('T');
		action.setFiltrarPor(1);
		when(candidatoManager.findRelatorioAvaliacaoCandidatos(null, null, action.getEmpresaSistema().getId(), new Long[]{}, new Long[]{}, null,'T')).thenReturn(avaliacaoCandidatos); 
		assertEquals("success", action.relatorioAvaliacaoCandidatos());
	}
	
	@Test
	public void testRelatorioAvaliacaoCandidatosFiltrarPorCargos() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraCandidato(true);
		action.setEmpresaSistema(empresa);
		action.setStatusSolicitacao('A');
		action.setFiltrarPor(2);
		when(candidatoManager.findRelatorioAvaliacaoCandidatos(null, null, action.getEmpresaSistema().getId(), new Long[]{}, null, new Long[]{}, 'A')).thenReturn(new ArrayList<AvaliacaoCandidatosRelatorio>()); 
		assertEquals("success", action.relatorioAvaliacaoCandidatos());
	}
	
	@Test
	public void testRelatorioAvaliacaoCandidatosException() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraCandidato(true);
		action.setEmpresaSistema(empresa);
		action.setStatusSolicitacao('A');
		action.setFiltrarPor(2);
		doThrow(new ColecaoVaziaException("Não existem dados para o filtro informado.")).when(candidatoManager).findRelatorioAvaliacaoCandidatos(null, null, action.getEmpresaSistema().getId(), new Long[]{}, null, new Long[]{}, 'A');
		when(areaOrganizacionalManager.populaCheckOrderDescricao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		when(grupoOcupacionalManager.populaCheckOrderNome(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
		when(cargoManager.populaCheckBox(null, null, action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
		
		assertEquals("input", action.relatorioAvaliacaoCandidatos());
		assertEquals("Não existem dados para o filtro informado.", action.getActionMessages().iterator().next());
	}
	
	@Test
	public void testRelatorioCandidatosIndicadosPor() throws Exception {
		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		Collection<Empresa> empresas = new ArrayList<Empresa>();
		empresas.add(empresa);
		
		action.setParametrosDoSistema(parametrosDoSistema);
		action.setEmpresasCheck(new String[]{"1"});
		action.setEmpresaSistema(empresa);
		
		when(empresaManager.findEmpresasPermitidas(true, empresa.getId(), 1L, "ROLE_REL_CANDIDATOS_INDICADOS_POR")).thenReturn(empresas);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		when(candidatoManager.findCandidatosIndicadosPor(null, null, new Long[]{1L})).thenReturn(candidatos); 
		assertEquals("success", action.relatorioCandidatosIndicadosPor());
	}
	
	@Test
	public void testRelatorioCandidatosIndicadosPorEmpresasCheckVazia() throws Exception {
		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		Collection<Empresa> empresas = new ArrayList<Empresa>();
		empresas.add(empresa);
		
		action.setParametrosDoSistema(parametrosDoSistema);
		action.setEmpresaSistema(empresa);
		
		when(empresaManager.findEmpresasPermitidas(true, empresa.getId(), 1L, "ROLE_REL_CANDIDATOS_INDICADOS_POR")).thenReturn(empresas);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		when(candidatoManager.findCandidatosIndicadosPor(null, null, new Long[]{1L})).thenReturn(candidatos); 
		assertEquals("success", action.relatorioCandidatosIndicadosPor());
	}
	
	@Test
	public void testRelatorioCandidatosIndicadosPorColecaoVaziaException() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		Collection<Empresa> empresas = new ArrayList<Empresa>();
		empresas.add(empresa);
		
		action.setParametrosDoSistema(parametrosDoSistema);
		action.setEmpresaSistema(empresa);
		
		when(empresaManager.findEmpresasPermitidas(true, empresa.getId(), 1L, "ROLE_REL_CANDIDATOS_INDICADOS_POR")).thenReturn(empresas);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		doThrow(ColecaoVaziaException.class).when(candidatoManager).findCandidatosIndicadosPor(any(Date.class), any(Date.class),  eq(new Long[]{1L}));
		assertEquals("input", action.relatorioCandidatosIndicadosPor());
	}

	@Test
	public void testRelatorioCandidatosIndicadosPorXLS() throws Exception {
		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		Collection<Empresa> empresas = new ArrayList<Empresa>();
		empresas.add(empresa);
		
		action.setParametrosDoSistema(parametrosDoSistema);
		action.setEmpresasCheck(new String[]{"1"});
		action.setEmpresaSistema(empresa);
		
		when(empresaManager.findEmpresasPermitidas(true, empresa.getId(), 1L, "ROLE_REL_CANDIDATOS_INDICADOS_POR")).thenReturn(empresas);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		when(candidatoManager.findCandidatosIndicadosPor(null, null, new Long[]{1L})).thenReturn(candidatos); 
		assertEquals("success", action.relatorioCandidatosIndicadosPorXLS());
	}
	
	@Test
	public void testRelatorioCandidatosIndicadosPorXLSColecaoVaziaException() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		Collection<Empresa> empresas = new ArrayList<Empresa>();
		empresas.add(empresa);
		
		action.setParametrosDoSistema(parametrosDoSistema);
		action.setEmpresaSistema(empresa);
		
		when(empresaManager.findEmpresasPermitidas(true, empresa.getId(), 1L, "ROLE_REL_CANDIDATOS_INDICADOS_POR")).thenReturn(empresas);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		doThrow(ColecaoVaziaException.class).when(candidatoManager).findCandidatosIndicadosPor(any(Date.class), any(Date.class),  eq(new Long[]{1L}));
		assertEquals("input", action.relatorioCandidatosIndicadosPorXLS());
	}
	@Test
	public void testSucessoInclusao() throws Exception {
		assertEquals("success", action.sucessoInclusao());
		assertEquals("Operação efetuada com sucesso", action.getActionSuccess().iterator().next());
	}

	@Test
	public void testInfoCandidato() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraCandidato(true);
		action.setEmpresaSistema(empresa);
		when(configuracaoImpressaoCurriculoManager.findByUsuario(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId(), action.getEmpresaSistema().getId())).thenReturn(ConfiguracaoImpressaoCurriculoFactory.getEntity(1L));
		assertEquals("success", action.infoCandidato());
	}
	
	@Test
	public void testInfoCandidatoComSolicitacaoId() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraCandidato(true);
		action.setEmpresaSistema(empresa);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		action.setSolicitacao(solicitacao);
		
		when(configuracaoImpressaoCurriculoManager.findByUsuario(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId(), action.getEmpresaSistema().getId())).thenReturn(ConfiguracaoImpressaoCurriculoFactory.getEntity(1L));
		assertEquals("success", action.infoCandidato());
	}
	
	@Test
	public void testFind() throws Exception{
		String descricao = "descrição";
		action.setDescricao(descricao);
		
		AutoCompleteVO autoCompleteVO = new AutoCompleteVO();
		Collection<AutoCompleteVO> autoCompleteVOs = new ArrayList<AutoCompleteVO>();
		autoCompleteVOs.add(autoCompleteVO);
		
		when(candidatoManager.getAutoComplete(descricao, 1L)).thenReturn(autoCompleteVOs);
		
		assertEquals(Action.SUCCESS, action.find());
		assertEquals("[{\"id\":\"\",\"value\":\"\"}]", action.getJson());
	}

}
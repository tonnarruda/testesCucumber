package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CamposExtrasManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.ColaboradorPeriodoExperienciaAvaliacaoManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraVisivelObrigadotorioManager;
import com.fortes.rh.business.geral.ConfiguracaoPerformanceManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.relatorio.ParticipacaoColaboradorCipa;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.ws.TNaturalidadeAndNacionalidade;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoCampoExtraVisivelObrigadotorioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.FormacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.ColaboradorIdiomaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoCampoExtraFactory;
import com.fortes.rh.test.factory.geral.DocumentoAnexoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.action.geral.ColaboradorEditAction;
import com.fortes.rh.web.ws.AcPessoalClientSistema;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringUtil.class, SecurityUtil.class, ActionContext.class})
public class ColaboradorEditActionTest_JUnit4 
{
	private ColaboradorEditAction action = new ColaboradorEditAction();
	private CidadeManager cidadeManager;
	private EstadoManager estadoManager;
	private IndiceManager indiceManager;
	private FuncaoManager funcaoManager;
	private UsuarioManager usuarioManager;
	private FormacaoManager formacaoManager;
	private AmbienteManager ambienteManager;
	private AvaliacaoManager avaliacaoManager;
	private ExperienciaManager experienciaManager;
	private ColaboradorManager colaboradorManager;
	private CamposExtrasManager camposExtrasManager;
	private FaixaSalarialManager faixaSalarialManager;
	private CandidatoIdiomaManager candidatoIdiomaManager;
	private EstabelecimentoManager estabelecimentoManager;
	private AcPessoalClientSistema acPessoalClientSistema;
	private PlatformTransactionManager transactionManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager;
	private ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager;
	private ColaboradorIdiomaManager colaboradorIdiomaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private ColaboradorAfastamentoManager colaboradorAfastamentoManager;
	private CatManager catManager;
	private ComissaoManager comissaoManager;
	private DocumentoAnexoManager documentoAnexoManager;
	private ConfiguracaoPerformanceManager configuracaoPerformanceManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private HistoricoCandidatoManager historicoCandidatoManager;
	
	@Before
	public void setUp () throws Exception
	{
		catManager = mock(CatManager.class) ;
		cidadeManager = mock(CidadeManager.class);
		funcaoManager = mock(FuncaoManager.class);
		estadoManager = mock(EstadoManager.class);
		camposExtrasManager = mock(CamposExtrasManager.class);
		acPessoalClientSistema = mock(AcPessoalClientSistema.class);
		faixaSalarialManager = mock(FaixaSalarialManager.class);
		candidatoIdiomaManager = mock(CandidatoIdiomaManager.class);
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		periodoExperienciaManager = mock(PeriodoExperienciaManager.class);
		avaliacaoManager = mock(AvaliacaoManager.class);
		usuarioManager = mock(UsuarioManager.class);
		indiceManager = mock(IndiceManager.class);
		ambienteManager = mock(AmbienteManager.class);
		comissaoManager = mock(ComissaoManager.class);
		formacaoManager = mock(FormacaoManager.class);
		avaliacaoManager = mock(AvaliacaoManager.class);
		experienciaManager = mock(ExperienciaManager.class);
		transactionManager = mock(PlatformTransactionManager.class);
		candidatoIdiomaManager = mock(CandidatoIdiomaManager.class);
		colaboradorManager = mock(ColaboradorManager.class);
		camposExtrasManager = mock(CamposExtrasManager.class);
		faixaSalarialManager = mock(FaixaSalarialManager.class);
		documentoAnexoManager = mock(DocumentoAnexoManager.class);
		candidatoIdiomaManager = mock(CandidatoIdiomaManager.class);
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		acPessoalClientSistema = mock(AcPessoalClientSistema.class);
		colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
		colaboradorIdiomaManager = mock(ColaboradorIdiomaManager.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		periodoExperienciaManager = mock(PeriodoExperienciaManager.class);
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
		colaboradorOcorrenciaManager = mock (ColaboradorOcorrenciaManager.class);
		colaboradorAfastamentoManager = mock(ColaboradorAfastamentoManager.class);
		configuracaoCampoExtraManager = mock(ConfiguracaoCampoExtraManager.class);
		quantidadeLimiteColaboradoresPorCargoManager = mock(QuantidadeLimiteColaboradoresPorCargoManager.class);
		colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
		configuracaoPerformanceManager = mock(ConfiguracaoPerformanceManager.class);
		usuarioManager = mock(UsuarioManager.class);
		usuarioEmpresaManager = mock(UsuarioEmpresaManager.class);
		colaboradorPeriodoExperienciaAvaliacaoManager = mock(ColaboradorPeriodoExperienciaAvaliacaoManager.class);
		configuracaoCampoExtraVisivelObrigadotorioManager = mock(ConfiguracaoCampoExtraVisivelObrigadotorioManager.class);
		historicoCandidatoManager = mock(HistoricoCandidatoManager.class);
		
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		action.setColaboradorManager(colaboradorManager);
		action.setHistoricoColaboradorManager(historicoColaboradorManager);
		action.setCidadeManager(cidadeManager);
		action.setEstadoManager(estadoManager);
		action.setCamposExtrasManager(camposExtrasManager);
		action.setAcPessoalClientSistema(acPessoalClientSistema);
		action.setFaixaSalarialManager(faixaSalarialManager);
		action.setEstabelecimentoManager(estabelecimentoManager);
		action.setPeriodoExperienciaManager(periodoExperienciaManager);
		action.setFormacaoManager(formacaoManager);
		action.setExperienciaManager(experienciaManager);
		action.setCandidatoIdiomaManager(candidatoIdiomaManager);
		action.setAvaliacaoManager(avaliacaoManager);
		action.setIndiceManager(indiceManager);
		action.setFuncaoManager(funcaoManager);
		action.setUsuarioManager(usuarioManager);
		action.setAmbienteManager(ambienteManager);
		action.setTransactionManager(transactionManager);
		action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        action.setConfiguracaoCampoExtraManager(configuracaoCampoExtraManager);
        action.setQuantidadeLimiteColaboradoresPorCargoManager(quantidadeLimiteColaboradoresPorCargoManager);
        action.setColaboradorQuestionarioManager(colaboradorQuestionarioManager);
        action.setColaboradorIdiomaManager(colaboradorIdiomaManager);
        action.setColaboradorTurmaManager(colaboradorTurmaManager);
        action.setColaboradorOcorrenciaManager(colaboradorOcorrenciaManager);
        action.setColaboradorAfastamentoManager(colaboradorAfastamentoManager);
        action.setCatManager(catManager);
        action.setUsuarioManager(usuarioManager);
        action.setComissaoManager(comissaoManager);
        action.setUsuarioEmpresaManager(usuarioEmpresaManager);
        action.setDocumentoAnexoManager(documentoAnexoManager);
        action.setConfiguracaoPerformanceManager(configuracaoPerformanceManager);
        action.setColaboradorPeriodoExperienciaAvaliacaoManager(colaboradorPeriodoExperienciaAvaliacaoManager);
        action.setConfiguracaoCampoExtraVisivelObrigadotorioManager(configuracaoCampoExtraVisivelObrigadotorioManager);
        action.setHistoricoCandidatoManager(historicoCandidatoManager);
        
		PowerMockito.mockStatic(SpringUtil.class);
		PowerMockito.mockStatic(ActionContext.class);
		PowerMockito.mockStatic(SecurityUtil.class);
		
		BDDMockito.given(SpringUtil.getBean("parametrosDoSistemaManager")).willReturn(parametrosDoSistemaManager);
	}
	
	@Before
	public void prepareSession(){
		Map<Object, Object> map = new HashMap<Object, Object>();
		when(ActionContext.getContext()).thenReturn(mock(ActionContext.class));
		when(ActionContext.getContext().getSession()).thenReturn(map);
	}
	
	@Test
	public void testPrepareInsert() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		empresa.setCampoExtraColaborador(true);
		empresa.setCriarUsuarioAutomaticamente(true);
		action.setEmpresaSistema(empresa);
		
		ConfiguracaoCampoExtraVisivelObrigadotorio configCampoExtraVisivelObrigadotorio = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "texto1", TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo()); 
		configCampoExtraVisivelObrigadotorio.setCamposExtrasObrigatorios("texto1");
		
		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(ParametrosDoSistemaFactory.getEntity(1L));
		when(configuracaoCampoExtraVisivelObrigadotorioManager.findByEmpresaId(empresa.getId(), TipoConfiguracaoCampoExtra.COLABORADOR.getTipo())).thenReturn(configCampoExtraVisivelObrigadotorio);
		assertEquals(Action.SUCCESS, action.prepareInsert());
	}
	
	@Test
	public void testPrepareUpdate() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		empresa.setCampoExtraColaborador(true);
		empresa.setCriarUsuarioAutomaticamente(true);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("123456");
		action.setColaborador(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Cargo cargo = CargoFactory.getEntity(2L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setColaborador(colaborador);
		
		ConfiguracaoCampoExtraVisivelObrigadotorio configCampoExtraVisivelObrigadotorio = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "texto1", TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo()); 
		configCampoExtraVisivelObrigadotorio.setCamposExtrasObrigatorios("texto1");
		
		TNaturalidadeAndNacionalidade tNaturalidadeAndNacionalidade = new TNaturalidadeAndNacionalidade();
		tNaturalidadeAndNacionalidade.setNacionalidade("Brasileiro");
		tNaturalidadeAndNacionalidade.setNaturalidade("Fortaleza - CE");;
		
		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(ParametrosDoSistemaFactory.getEntity(1L));
		when(configuracaoCampoExtraVisivelObrigadotorioManager.findByEmpresaId(empresa.getId(), TipoConfiguracaoCampoExtra.COLABORADOR.getTipo())).thenReturn(configCampoExtraVisivelObrigadotorio);
		when(colaboradorManager.findByIdComHistoricoConfirmados(colaborador.getId())).thenReturn(colaborador);
		when(historicoColaboradorManager.getHistoricoAtual(colaborador.getId())).thenReturn(historicoColaborador);
		when(historicoColaboradorManager.getHistoricoAtual(colaborador.getId())).thenReturn(historicoColaborador);
		when(colaboradorManager.getNaturalidadeAndNacionalidade(empresa, colaborador.getCodigoAC())).thenReturn(tNaturalidadeAndNacionalidade);
		when(areaOrganizacionalManager.getMascaraLotacoesAC(empresa)).thenReturn("99.99.99");
		
		assertEquals(Action.SUCCESS, action.prepareUpdate());
		assertEquals("Fortaleza - CE", action.getColaborador().getNaturalidade());
		assertEquals("Brasileiro", action.getColaborador().getNacionalidade());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testInsert() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		empresa.setCampoExtraColaborador(true);
		empresa.setCriarUsuarioAutomaticamente(true);
		action.setEmpresaSistema(empresa);
		Boolean ativa = null;
		
		Colaborador colaborador = ColaboradorFactory.getEntity("", "Maluco no pedaço", empresa, DateUtil.criarDataMesAno(01, 02, 2000), null);
		colaborador.getEndereco().setUf(EstadoFactory.getEntity(1L));
		colaborador.setNaoIntegraAc(true);
		action.setColaborador(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		Cargo cargo = CargoFactory.getEntity(2L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, faixaSalarial, DateUtil.criarDataMesAno(01, 02, 2000), 1);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		action.setHistoricoColaborador(historicoColaborador);
		
		when(areaOrganizacionalManager.verificaMaternidade(eq(historicoColaborador.getAreaOrganizacional().getId()), eq(ativa))).thenReturn(false);
		when(colaboradorManager.insert(any(Colaborador.class), anyDouble(), anyLong(), anyCollection(), anyCollection(), anyCollection(), any(Solicitacao.class), any(Empresa.class), anyLong())).thenReturn(true);
				
		when(usuarioManager.existeLogin(any(Usuario.class))).thenReturn(false);
		when(parametrosDoSistemaManager.findByIdProjection(eq(1L))).thenReturn(ParametrosDoSistemaFactory.getEntity());
		assertEquals(Action.SUCCESS, action.insert());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		action.setEmpresaSistema(empresa);
		
		Date dataAdmissao = DateUtil.criarDataMesAno(01, 02, 2000);
		Boolean ativa = null;
		
		Colaborador colaborador = ColaboradorFactory.getEntity("0000", "Colaborador", empresa, dataAdmissao, null);
		colaborador.setId(1L);
		action.setColaborador(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		Cargo cargo = CargoFactory.getEntity(2L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L, "Faixa", cargo);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, dataAdmissao, faixaSalarial, null, areaOrganizacional, null, null, StatusRetornoAC.CONFIRMADO);
		action.setHistoricoColaborador(historicoColaborador);
		
		when(areaOrganizacionalManager.verificaMaternidade(eq(historicoColaborador.getAreaOrganizacional().getId()), eq(ativa))).thenReturn(false);
		when(colaboradorManager.insert(any(Colaborador.class), anyDouble(), anyLong(), anyCollection(), anyCollection(), anyCollection(), any(Solicitacao.class), any(Empresa.class), anyLong())).thenReturn(false);
		when(parametrosDoSistemaManager.findByIdProjection(eq(1L))).thenReturn(ParametrosDoSistemaFactory.getEntity());

		assertEquals(Action.ERROR, action.insert());
	}
	
	@Test
	public void testPreparePerformanceFuncional() throws Exception{
		Boolean moduloExterno = null;
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraColaborador(true);
		action.setEmpresaSistema(empresa);
		
		action.setUsuarioLogado(UsuarioFactory.getEntity());

		Colaborador colaborador = ColaboradorFactory.getEntity(2L, empresa);
		colaborador.setCandidato(CandidatoFactory.getCandidato(1L));
		action.setColaborador(colaborador);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L, colaborador);

		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		Collection<ColaboradorIdioma> colaboradorsIdioma = Arrays.asList(ColaboradorIdiomaFactory.getEntity(1L, colaborador));
		Collection<Formacao> formacaos = Arrays.asList(FormacaoFactory.getEntity(1L, colaborador));
		Collection<ColaboradorTurma> cursosColaborador = Arrays.asList(ColaboradorTurmaFactory.getEntity(1L, colaborador));

		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity(empresa, "Abandono do local de trabalho", 10);
		Collection<ColaboradorOcorrencia> ocorrenciasColaborador =  Arrays.asList(ColaboradorOcorrenciaFactory.getEntity(colaborador, ocorrencia, new Date(), null));
		Collection<DocumentoAnexo> documentoAnexosColaborador = Arrays.asList(DocumentoAnexoFactory.getEntity(1L, colaborador.getId(), OrigemAnexo.AnexoCandidato));
		Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = Arrays.asList(ConfiguracaoCampoExtraFactory.getEntity(1L, "", true, false));

		MockSecurityUtil.roles = new String[]{"ROLE_VISUALIZAR_ANEXO_COLAB_LOGADO"};
		
		when(colaboradorManager.getFoto(eq(colaborador.getId()))).thenReturn(null);
		when(configuracaoCampoExtraManager.find(eq(new String[]{"ativoColaborador", "empresa.id"}),eq(new Object[]{true, empresa.getId()}), eq(new String[]{"ordem"}))).thenReturn(configuracaoCampoExtras);
		when(colaboradorManager.findColaboradorById(eq(colaborador.getId()))).thenReturn(colaborador);
		when(colaboradorQuestionarioManager.findAvaliacaoDesempenhoByColaborador(eq(colaborador.getId()))).thenReturn(new ArrayList<ColaboradorQuestionario>());
		when(colaboradorQuestionarioManager.findAvaliacaoByColaborador(eq(colaborador.getId()), any(Colaborador.class), eq(false), eq(areaOrganizacionalManager))).thenReturn(new ArrayList<ColaboradorQuestionario>());
		when(historicoColaboradorManager.progressaoColaborador(eq(colaborador.getId()), eq(empresa.getId()))).thenReturn(historicoColaboradors);
		when(historicoColaboradorManager.getHistoricoAtual(eq(colaborador.getId()))).thenReturn(historicoColaborador1);
		when(colaboradorIdiomaManager.findByColaborador(eq(colaborador.getId()))).thenReturn(colaboradorsIdioma);
		when(formacaoManager.findByColaborador(eq(colaborador.getId()))).thenReturn(formacaos);
		when(colaboradorTurmaManager.findHistoricoTreinamentosByColaborador(eq(empresa.getId()), any(Date.class), any(Date.class),any(Long[].class))).thenReturn(cursosColaborador);
		when(colaboradorOcorrenciaManager.findByColaborador(eq(colaborador.getId()))).thenReturn(ocorrenciasColaborador);
		when(colaboradorAfastamentoManager.findByColaborador(eq(colaborador.getId()))).thenReturn(new ArrayList<ColaboradorAfastamento>());
		when(experienciaManager.findByColaborador(eq(colaborador.getId()))).thenReturn(new ArrayList<Experiencia>());
		when(catManager.findByColaborador(eq(colaborador))).thenReturn(new ArrayList<Cat>());
		when(comissaoManager.getParticipacoesDeColaboradorNaCipa(eq(colaborador.getId()))).thenReturn(new ArrayList<ParticipacaoColaboradorCipa>());
		when(documentoAnexoManager.getDocumentoAnexoByOrigemId(anyChar(), eq(colaborador.getId()))).thenReturn(documentoAnexosColaborador);
		when(colaboradorManager.findByUsuario(any(Usuario.class), eq(action.getEmpresaSistema().getId()))).thenReturn(colaborador);
		when(usuarioManager.isResponsavelOrCoResponsavel(anyLong())).thenReturn(true);
		when(usuarioEmpresaManager.containsRole(anyLong(), eq(action.getEmpresaSistema().getId()), eq("ROLE_MOV_GESTOR_VISUALIZAR_PROPRIA_OCORRENCIA_PROVIDENCIA"))).thenReturn(true); 

		String retorno = "";
		Exception excep = null;

		try {
			retorno = action.preparePerformanceFuncional();
		}catch (Exception e){
			excep = e;
		}

		assertNull(excep);
		assertEquals(retorno, "success");
	}
	
	@Test
	public void testPreparePerformanceFuncionalException() throws Exception
	{
		action.setColaborador(null);
		String retorno = "";
		retorno = action.preparePerformanceFuncional();
		assertEquals("Colaborador não selecionado", action.getActionErrors().iterator().next());
		assertEquals(retorno, "input");
	}
	
	@Test
	public void testPrepareUpdateInfoPessoais() throws Exception
	{
		ConfiguracaoCampoExtra configuracaoCampoExtra = new ConfiguracaoCampoExtra();
		configuracaoCampoExtra.setId(1L);
		configuracaoCampoExtra.setAtivoColaborador(true);
		Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
		configuracaoCampoExtras.add(configuracaoCampoExtra);
	
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		empresa.setCampoExtraColaborador(true);
		empresa.setCampoExtraAtualizarMeusDados(true);
		action.setEmpresaSistema(empresa);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		CamposExtras camposExtras = CamposExtrasFactory.getEntity(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		colaborador.getEndereco().setUf(EstadoFactory.getEntity(1L));
		colaborador.setCamposExtras(camposExtras);
		action.setColaborador(colaborador);
		
		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(parametrosDoSistema);
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(SecurityUtil.getColaboradorSession(anyMap())).thenReturn(colaborador);
		
		when(cidadeManager.find(new String[]{"uf.id"}, new Object[]{colaborador.getEndereco().getUf().getId()}, new String[]{"nome"})).thenReturn(new ArrayList<Cidade>());
		when(estadoManager.findAll(new String[]{"sigla"})).thenReturn(new ArrayList<Estado>());

		when(camposExtrasManager.findById(colaborador.getCamposExtras().getId())).thenReturn(camposExtras);

		assertEquals("success", action.prepareUpdateInfoPessoais());
	}
	
	@Test
	public void testPrepareUpdateInfoPessoaisEmpresaErrada() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		action.setEmpresaSistema(empresa);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		
		Empresa empresaColab = EmpresaFactory.getEmpresa(44L);
		empresaColab.setNome("babau");
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresaColab);
		
		when(SecurityUtil.getColaboradorSession(anyMap())).thenReturn(colaborador);
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(parametrosDoSistema);
		
		assertEquals("success", action.prepareUpdateInfoPessoais());
		assertTrue(((String)action.getActionWarnings().toArray()[0]).equals("Só é possível editar dados pessoais para empresa na qual você foi contratado(a). Acesse a empresa babau para alterar suas informações."));
	}
	
	@Test
	public void testPrepareUpdateInfoPessoaisEmpresaSemColaborador() throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		
		when(SecurityUtil.getColaboradorSession(anyMap())).thenReturn(ColaboradorFactory.getEntity(1L));
		when(colaboradorManager.findColaboradorById(null)).thenReturn(null);
		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(parametrosDoSistema);
		
		assertEquals("success", action.prepareUpdateInfoPessoais());
		assertTrue(((String)action.getActionWarnings().toArray()[0]).equals("Sua conta de usuário não está vinculada à nenhum colaborador"));
	}
	
	@Test 
	public void testUpdateInfoPessoais() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		empresa.setCampoExtraAtualizarMeusDados(true);
		action.setEmpresaSistema(empresa);
		
		CamposExtras camposExtras = CamposExtrasFactory.getEntity(1L);
		action.setCamposExtras(camposExtras);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		colaborador.getEndereco().setUf(EstadoFactory.getEntity(1L));
		colaborador.setCamposExtras(camposExtras);
		action.setColaborador(colaborador);
		
		action.setHabilitaCampoExtra(true);
		when(SecurityUtil.getColaboradorSession(anyMap())).thenReturn(colaborador);
		assertEquals(Action.SUCCESS, action.updateInfoPessoais());
		assertEquals("Dados atualizado com sucesso.", action.getActionSuccess().toArray()[0]);
	}
	
	@Test 
	public void testUpdateInfoPessoaisIntegraACException() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		empresa.setCampoExtraAtualizarMeusDados(true);
		action.setEmpresaSistema(empresa);
		
		CamposExtras camposExtras = CamposExtrasFactory.getEntity(1L);
		action.setCamposExtras(camposExtras);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		colaborador.getEndereco().setUf(EstadoFactory.getEntity(1L));
		colaborador.setCamposExtras(camposExtras);
		action.setColaborador(colaborador);
		
		action.setHabilitaCampoExtra(true);
		when(SecurityUtil.getColaboradorSession(anyMap())).thenReturn(colaborador);
		doThrow(new IntegraACException("IntegraACException")).when(colaboradorManager).updateInfoPessoais(action.getColaborador(), null, null, null, empresa, null, false);
		
		assertEquals(Action.SUCCESS, action.updateInfoPessoais());
		assertEquals("IntegraACException", action.getActionErrors().toArray()[0]);
	}
	
	@Test 
	public void testUpdateInfoPessoaisException() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		empresa.setCampoExtraAtualizarMeusDados(true);
		action.setEmpresaSistema(empresa);
		
		CamposExtras camposExtras = CamposExtrasFactory.getEntity(1L);
		action.setCamposExtras(camposExtras);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		colaborador.getEndereco().setUf(EstadoFactory.getEntity(1L));
		colaborador.setCamposExtras(camposExtras);
		action.setColaborador(colaborador);
		
		action.setHabilitaCampoExtra(true);
		
		when(SecurityUtil.getColaboradorSession(anyMap())).thenReturn(colaborador);
		doThrow(new Exception()).when(colaboradorManager).updateInfoPessoais(action.getColaborador(), null, null, null, empresa, null, false);
		
		assertEquals(Action.SUCCESS, action.updateInfoPessoais());
		assertEquals("Não foi possível atualizar os dados.", action.getActionErrors().toArray()[0]);
	}
}
package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CamposExtrasManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorPeriodoExperienciaAvaliacaoManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraVisivelObrigadotorioManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoCampoExtraVisivelObrigadotorioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.geral.ColaboradorEditAction;
import com.fortes.rh.web.ws.AcPessoalClientSistema;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

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
	private SolicitacaoExameManager solicitacaoExameManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;
	private ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager;
	private ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager;

	@Before
	public void setUp () throws Exception
	{
		colaboradorManager = mock(ColaboradorManager.class);
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		cidadeManager = mock(CidadeManager.class);
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
		funcaoManager = mock(FuncaoManager.class);
		ambienteManager = mock(AmbienteManager.class);
		formacaoManager = mock(FormacaoManager.class);
		experienciaManager = mock(ExperienciaManager.class);
		transactionManager = mock(PlatformTransactionManager.class);
		candidatoIdiomaManager = mock(CandidatoIdiomaManager.class);
		solicitacaoExameManager = mock(SolicitacaoExameManager.class);
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		configuracaoCampoExtraManager = mock(ConfiguracaoCampoExtraManager.class);
		quantidadeLimiteColaboradoresPorCargoManager = mock(QuantidadeLimiteColaboradoresPorCargoManager.class);
		colaboradorPeriodoExperienciaAvaliacaoManager = mock(ColaboradorPeriodoExperienciaAvaliacaoManager.class);
		configuracaoCampoExtraVisivelObrigadotorioManager = mock(ConfiguracaoCampoExtraVisivelObrigadotorioManager.class);
		
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
		action.setSolicitacaoExameManager(solicitacaoExameManager);
		action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        action.setConfiguracaoCampoExtraManager(configuracaoCampoExtraManager);
        action.setQuantidadeLimiteColaboradoresPorCargoManager(quantidadeLimiteColaboradoresPorCargoManager);
        action.setColaboradorPeriodoExperienciaAvaliacaoManager(colaboradorPeriodoExperienciaAvaliacaoManager);
        action.setConfiguracaoCampoExtraVisivelObrigadotorioManager(configuracaoCampoExtraVisivelObrigadotorioManager);
        
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
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
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
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
		
		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(ParametrosDoSistemaFactory.getEntity(1L));
		when(configuracaoCampoExtraVisivelObrigadotorioManager.findByEmpresaId(empresa.getId(), TipoConfiguracaoCampoExtra.COLABORADOR.getTipo())).thenReturn(configCampoExtraVisivelObrigadotorio);
		when(colaboradorManager.findByIdComHistoricoConfirmados(colaborador.getId())).thenReturn(colaborador);
		when(historicoColaboradorManager.getHistoricoAtual(colaborador.getId())).thenReturn(historicoColaborador);
		assertEquals(Action.SUCCESS, action.prepareUpdate());
	}
	
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
		when(colaboradorManager.insert(any(Colaborador.class), anyDouble(), anyLong(), anyCollection(), anyCollection(), anyCollection(), any(Solicitacao.class), any(Empresa.class))).thenReturn(true);
				
		when(usuarioManager.existeLogin(any(Usuario.class))).thenReturn(false);
		when(parametrosDoSistemaManager.findByIdProjection(eq(1L))).thenReturn(ParametrosDoSistemaFactory.getEntity());
		assertEquals(Action.SUCCESS, action.insert());
	}

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
		when(colaboradorManager.insert(any(Colaborador.class), anyDouble(), anyLong(), anyCollection(), anyCollection(), anyCollection(), any(Solicitacao.class), any(Empresa.class))).thenReturn(false);
		when(parametrosDoSistemaManager.findByIdProjection(eq(1L))).thenReturn(null);

		assertEquals(Action.ERROR, action.insert());
	}
}
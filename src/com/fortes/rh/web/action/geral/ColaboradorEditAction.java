package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.NestedRuntimeException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
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
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.exception.LimiteColaboradorExceditoException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.CodigoGFIP;
import com.fortes.rh.model.dicionario.Deficiencia;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.EstadoCivil;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.dicionario.SexoCadastro;
import com.fortes.rh.model.dicionario.StatusAdmisaoColaboradorNoFortesPessoal;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoConfiguracaoCampoExtra;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.relatorio.ParticipacaoColaboradorCipa;
import com.fortes.rh.model.relatorio.RelatorioPerformanceFuncional;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.ws.TNaturalidadeAndNacionalidade;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.rh.web.ws.AcPessoalClientSistema;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("unchecked")
public class ColaboradorEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorManager colaboradorManager;
	private EstadoManager estadoManager;
	private CidadeManager cidadeManager;
	private ColaboradorIdiomaManager colaboradorIdiomaManager;
	private CandidatoIdiomaManager candidatoIdiomaManager;
	private FormacaoManager formacaoManager;
	private ExperienciaManager experienciaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private CandidatoManager candidatoManager;
	private SolicitacaoManager solicitacaoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private EstabelecimentoManager estabelecimentoManager;
	private DocumentoAnexoManager documentoAnexoManager;
	private IndiceManager indiceManager;
	private AcPessoalClientSistema acPessoalClientSistema;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorAfastamentoManager colaboradorAfastamentoManager;
	private HistoricoCandidatoManager historicoCandidatoManager;
	private CatManager catManager;
	private ComissaoManager comissaoManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private CamposExtrasManager camposExtrasManager;
	private ConfiguracaoPerformanceManager configuracaoPerformanceManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private AvaliacaoManager avaliacaoManager;
	private ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager;
	private PlatformTransactionManager transactionManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private UsuarioManager usuarioManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager;
	
	private Colaborador colaborador;
	private AreaOrganizacional areaOrganizacional;
	private Double salarioColaborador = 00.0;

	private Candidato candidato;
	private Solicitacao solicitacao;

	private Map escolaridades;
	private Map estadosCivis;

	private Collection<Cargo> cargos;
	private Collection<Beneficio> beneficios;
	private Collection<AreaOrganizacional> areaOrganizacionals;
	private Collection<Estado> estados;
	private Collection<Cidade> cidades;
	private Collection<Estabelecimento> estabelecimentos;
	private ColaboradorQuestionario avaliacaoExperiencia;
	private ColaboradorQuestionario avaliacaoDesempenho;
	private Collection<ColaboradorQuestionario> cqAvaliacaoExperiencias;
	private Collection<ColaboradorQuestionario> cqAvaliacaoDesempenhos;
	private Collection<ColaboradorQuestionario> colaboradorQuestionarioAvaloacaoExperiencias;
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
	private Collection<PeriodoExperiencia> periodoExperiencias;
	private Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes;
	private Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoesGestor;
	private Collection<Avaliacao> avaliacoes;

	// Utilizados no insert e update para a chamada ao metodo saveDetalhes();
	private Collection<Formacao> formacaos;
	private Collection<CandidatoIdioma> idiomas;
	private Collection<Experiencia> experiencias;

	private HistoricoColaborador historicoColaborador;
	private Collection<Funcao> funcoes;
	private FuncaoManager funcaoManager;
	private Collection<Ambiente> ambientes;
	private AmbienteManager ambienteManager;

	private IndiceHistoricoManager indiceHistoricoManager;
	private RelatorioPerformanceFuncional relatorioPerformanceFuncional;
	
	private String descricao;
	private String json;

	private String nomeBusca;
	private String cpfBusca;
	private String motivo;
	private int page;

	private Collection<HistoricoColaborador> historicoColaboradors;
	private Collection<ColaboradorIdioma> idiomasColaborador;
	private Collection<ColaboradorTurma> cursosColaborador;
	private Collection<ColaboradorOcorrencia> ocorrenciasColaborador;
	private Collection<ColaboradorAfastamento> afastamentosColaborador;
	private Collection<DocumentoAnexo> documentoAnexosColaborador;
	private Collection<DocumentoAnexo> documentoAnexosCandidato;
	private Collection<HistoricoCandidato> historicosCandidatoByColaborador;
	private Collection<Cat> catsColaborador;
	private Collection<ColaboradorQuestionario> pesquisas;
	private Collection<ParticipacaoColaboradorCipa> participacoesNaCipaColaborador;
	private Map vinculos;
	private Map sexos;
	private Map deficiencias;

	private Collection<CheckBox> areaCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> colaboradorCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> estabelecimentoCheckList = new ArrayList<CheckBox>();

	private Long[] areaCheck;
	private Long[] colaboradorCheck;
	private Long[] estabelecimentoCheck;
	
	//Utilizado apenas na hora de contratar o colaborador
	private Long idCandidato;
	
	private HashMap<String, String> codigosGFIP; 

	private boolean integraAc;
	private boolean obrigarAmbienteFuncao;
	private boolean habilitaCampoExtra;
	private CamposExtras camposExtras;
	
	//utilizado para permitir a edição de informações de colaborador com apenas hum histórico
	private boolean editarHistorico = true;

	private Map tiposSalarios = new TipoAplicacaoIndice();
	private TipoAplicacaoIndice tipoSalario = new TipoAplicacaoIndice();
	private Collection<Indice> indices = new ArrayList<Indice>();

	private int salarioPropostoPor;
	private Double quantidadeIndice;
	private Indice indice;

	private FaixaSalarialManager faixaSalarialManager;
	//Collection<FaixaSalarialHistorico> faixas;
	private Collection<FaixaSalarial> faixas;
	private Double valorCalculado;
	private boolean manterFoto;

	private TipoAplicacaoIndice tipoAplicacaoIndice = new TipoAplicacaoIndice();
	private boolean updateDados = true;
	private String configPerformanceBoxes;
	private Long candidatoSolicitacaoId;

	private Map<String,Object> parametros = new HashMap<String, Object>();

	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private char statusCandSol;
	private String obsACPessoal;
	
	private boolean checarCandidatoMesmoCpf = true;
	private boolean vincularCandidatoMesmoCpf;
	
	private int pontuacao = 0;

	private String voltarPara;
	
	Collection<Colaborador> colaboradoresMesmoCpf = new ArrayList<Colaborador>();
	
	private String encerrarSolicitacao;
	
	private ParametrosDoSistema parametrosDoSistema;
	private ConfiguracaoCampoExtraVisivelObrigadotorio campoExtraVisivelObrigadotorio;
	private boolean dadosIntegradosAtualizados = false;
	private Date dataAlteracao = null;
	private boolean desabilitarEdicaoCamposIntegrados = false;
	private String camposColaboradorObrigatorio = "";
	private boolean empresaEstaIntegradaEAderiuAoESocial = false;
	private boolean podeRetificar = true;
	private boolean pisObrigatorio;
	
	private void prepare() throws Exception
	{
		try
		{
			acPessoalClientSistema.verificaWebService(getEmpresaSistema());
		}
		catch (IntegraACException e)
		{
			addActionError(e.getMessage());
		}
		dataAlteracao = new Date();
		parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
		
		obrigarAmbienteFuncao = getEmpresaSistema().isObrigarAmbienteFuncao();
		habilitaCampoExtra = getEmpresaSistema().isCampoExtraColaborador();
		configuraCamposExtras(habilitaCampoExtra);

		camposColaboradorObrigatorio = parametrosDoSistema.getCamposColaboradorObrigatorio();
			
		indices = indiceManager.findAll(getEmpresaSistema());
		
		codigosGFIP = CodigoGFIP.getInstance();
		vinculos = new Vinculo();
		sexos = new SexoCadastro();
		deficiencias = new Deficiencia();
		estados = estadoManager.findAll(new String[]{"sigla"});

		integraAc = getEmpresaSistema().isAcIntegra();
		Long faixaInativaId = null;
		Long areaInativaId = null;
		
		if(colaborador != null && colaborador.getId() != null)
		{
			Long colaboradorId = colaborador.getId();
			colaborador = (Colaborador) colaboradorManager.findByIdComHistoricoConfirmados(colaboradorId);

			historicoColaborador = historicoColaboradorManager.getHistoricoAtual(colaboradorId);

			if (historicoColaborador == null)
				historicoColaborador = historicoColaboradorManager.getHistoricoAtualOuFuturo(colaboradorId);
			
			if (historicoColaborador == null)
				historicoColaborador = historicoColaboradorManager.getHistoricoContratacaoAguardando(colaboradorId);

			colaborador.setFuncao(historicoColaborador.getFuncao());

			if(historicoColaborador.getSalarioCalculado() != null)
				salarioColaborador = historicoColaborador.getSalarioCalculado();

			funcoes = funcaoManager.findByCargo(historicoColaborador.getFaixaSalarial().getCargo().getId());
			
			ambientes = ambienteManager.findByEstabelecimento(historicoColaborador.getEstabelecimento().getId());
			
			colaborador.setFoto(colaboradorManager.getFoto(colaboradorId));
			
			if(habilitaCampoExtra && colaborador.getCamposExtras() != null && colaborador.getCamposExtras().getId() != null)
				camposExtras = camposExtrasManager.findById(colaborador.getCamposExtras().getId());
			
			faixaInativaId = historicoColaborador.getFaixaSalarial().getId();
			areaInativaId = historicoColaborador.getAreaOrganizacional().getId();
		}

		if (colaborador != null && colaborador.getEndereco() != null && colaborador.getEndereco().getUf() != null)
			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{colaborador.getEndereco().getUf().getId()}, new String[]{"nome"});
		else
			cidades = new ArrayList<Cidade>();

		escolaridades = new Escolaridade();
		estadosCivis = new EstadoCivil();

		faixas = faixaSalarialManager.findFaixas(getEmpresaSistema(), Cargo.ATIVO, faixaInativaId);

		areaOrganizacionals = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.ATIVA, areaInativaId, true);
		
		if ( getEmpresaSistema().isAcIntegra() ) {
			String mascara = areaOrganizacionalManager.getMascaraLotacoesAC(getEmpresaSistema()).replaceAll("9", "A");
			if(mascara != "") {
				for (AreaOrganizacional area : areaOrganizacionals) {
					area.setMascara(mascara);
				}
			}
		}
		
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
	}
	
	private void configuraCamposExtras(boolean habilitaCampoExtra){
		if(habilitaCampoExtra){
			configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoColaborador", "empresa.id"}, new Object[]{true, getEmpresaSistema().getId()}, new String[]{"ordem"});
			campoExtraVisivelObrigadotorio = configuracaoCampoExtraVisivelObrigadotorioManager.findByEmpresaId(getEmpresaSistema().getId(), TipoConfiguracaoCampoExtra.COLABORADOR.getTipo());
			if(campoExtraVisivelObrigadotorio != null){
				if (campoExtraVisivelObrigadotorio.getCamposExtrasVisiveis() != null && !campoExtraVisivelObrigadotorio.getCamposExtrasVisiveis().isEmpty() ) {
					parametrosDoSistema.setCamposColaboradorTabs(parametrosDoSistema.getCamposColaboradorTabs()+ ",abaExtra");
					parametrosDoSistema.setCamposColaboradorVisivel(parametrosDoSistema.getCamposColaboradorVisivel()+ "," + campoExtraVisivelObrigadotorio.getCamposExtrasVisiveis());
					if(campoExtraVisivelObrigadotorio.getCamposExtrasObrigatorios() != null && !campoExtraVisivelObrigadotorio.getCamposExtrasObrigatorios().isEmpty())
						parametrosDoSistema.setCamposColaboradorObrigatorio(parametrosDoSistema.getCamposColaboradorObrigatorio()+ "," + campoExtraVisivelObrigadotorio.getCamposExtrasObrigatorios());
				}
			}
		}
	}

	public String prepareInsert() throws Exception
	{
		try
		{
			colaboradorManager.validaQtdCadastros(getEmpresaSistema().getId());
		} catch (Exception e)
		{
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
		
		prepare();
		avaliacoes = avaliacaoManager.findAllSelect(null, null, getEmpresaSistema().getId(), true, TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, null);
		periodoExperiencias = periodoExperienciaManager.findPeriodosAtivosAndPeriodosConfiguradosParaColaborador(getEmpresaSistema().getId(), null);
		
		if(updateDados)
		{
			Map session = ActionContext.getContext().getSession();
			session.put("SESSION_FORMACAO", null);
			session.put("SESSION_IDIOMA", null);
			session.put("SESSION_EXPERIENCIA", null);
		}
		setEmpresaEstaIntegradaEAderiuAoESocial();
		return Action.SUCCESS;
	}

	public String prepareUpdateInfoPessoais() throws Exception
	{
		dadosIntegradosAtualizados = false;
		dataAlteracao = new Date();
		
		Map session = ActionContext.getContext().getSession();
		colaborador = SecurityUtil.getColaboradorSession(session);
		colaborador = colaboradorManager.findColaboradorById(colaborador.getId());
		parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
		
		if(colaborador == null)
		{
			addActionWarning("Sua conta de usuário não está vinculada à nenhum colaborador");
		}
		else if(!colaborador.getEmpresa().getId().equals(getEmpresaSistema().getId()))//isso é muito importante, evita que o cara edite o colaborador com os dados errado no AC (Francisco Barroso)
		{
			addActionWarning("Só é possível editar dados pessoais para empresa na qual você foi contratado(a). Acesse a empresa " + colaborador.getEmpresaNome() + " para alterar suas informações.");
			colaborador = null;
		}
		else
		{
			escolaridades = new Escolaridade();
			estadosCivis = new EstadoCivil();
			
			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{colaborador.getEndereco().getUf().getId()}, new String[]{"nome"});
			estados = estadoManager.findAll(new String[]{"sigla"});
			
			session.put("SESSION_IDIOMA", candidatoIdiomaManager.montaListCandidatoIdioma(colaborador.getId()));
			session.put("SESSION_EXPERIENCIA", experienciaManager.findByColaborador(colaborador.getId()));
			session.put("SESSION_FORMACAO", formacaoManager.findByColaborador(colaborador.getId()));			
			
			habilitaCampoExtra = colaborador.getEmpresa().isCampoExtraAtualizarMeusDados();
			configuraCamposExtras(habilitaCampoExtra);
			if(habilitaCampoExtra && colaborador.getCamposExtras() != null && colaborador.getCamposExtras().getId() != null)
				camposExtras = camposExtrasManager.findById(colaborador.getCamposExtras().getId());
			
			pisObrigatorio = !colaborador.getVinculo().equals(Vinculo.ESTAGIO);
			validaEdicaoDeCamposIntegrados();
			if(!desabilitarEdicaoCamposIntegrados)
				camposColaboradorObrigatorio = parametrosDoSistema.getCamposColaboradorObrigatorio();
		}
		
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		validaEdicaoDeCamposIntegrados();
		
		setNacionalidadeAndNaturalidade();
		
		if(historicoColaboradorManager.findByColaboradorProjection(colaborador.getId(), null).size() > 1 || (getEmpresaSistema().isAcIntegra() && !"".equals(colaborador.getCodigoAC())))
			editarHistorico = false;

		Map session = ActionContext.getContext().getSession();

		//Tipo aplicação do indice, salario
		salarioPropostoPor = historicoColaborador.getTipoSalario();
		quantidadeIndice = historicoColaborador.getQuantidadeIndice();
		indice = historicoColaborador.getIndice();

		session.put("SESSION_IDIOMA", candidatoIdiomaManager.montaListCandidatoIdioma(colaborador.getId()));
		session.put("SESSION_EXPERIENCIA", experienciaManager.findByColaborador(colaborador.getId()));
		session.put("SESSION_FORMACAO", formacaoManager.findByColaborador(colaborador.getId()));

		valorCalculado = historicoColaborador.getSalarioCalculado();

		colaboradorAvaliacoes = colaboradorPeriodoExperienciaAvaliacaoManager.findByColaborador(colaborador.getId()); 
		avaliacoes = avaliacaoManager.findModelosPeriodoExperienciaAtivosAndModelosConfiguradosParaOColaborador(getEmpresaSistema().getId(), colaborador.getId());
		periodoExperiencias = periodoExperienciaManager.findPeriodosAtivosAndPeriodosConfiguradosParaColaborador(getEmpresaSistema().getId(), colaborador.getId());
		
		return Action.SUCCESS;
	}

	private void validaEdicaoDeCamposIntegrados() {
		if(getEmpresaSistema().isAcIntegra() && !colaborador.isNaoIntegraAc())
			setEmpresaEstaIntegradaEAderiuAoESocial();
		
		if(getEmpresaSistema().isAcIntegra() && !colaborador.isNaoIntegraAc() && StringUtils.isBlank(colaborador.getCodigoAC())){
			try {
				desabilitarEdicaoCamposIntegrados = colaboradorManager.statusAdmissaoNoFortesPessoal(getEmpresaSistema(), colaborador.getId()) != StatusAdmisaoColaboradorNoFortesPessoal.NA_TABELA_TEMPORARIA.getOpcao();
				if(desabilitarEdicaoCamposIntegrados)
					addActionMessage("Não é possível editar as informações integradas, este colaborador está em processo de admissão no Fortes Pessoal.");
			} catch (Exception e) {
				e.printStackTrace();
				desabilitarEdicaoCamposIntegrados = true;
				addActionWarning("Não foi possível estabelecer conexão com o Fortes Pessoal, desta forma não é possível editar as informações integradas.");
			}
		}else if(empresaEstaIntegradaEAderiuAoESocial && !colaborador.isNaoIntegraAc()){
			verificaPendenciaNoESocial();
			if(!desabilitarEdicaoCamposIntegrados)
				verificaRetificacao();
		}
		if(desabilitarEdicaoCamposIntegrados)
			camposColaboradorObrigatorio = colaboradorManager.configuraCamposObrigatorios(parametrosDoSistema);
		
	}

	private void verificaRetificacao() {
		try {
			podeRetificar = !colaboradorManager.isHistoricoCadastralDoColaboradorEInicioVinculo(getEmpresaSistema(), colaborador.getCodigoAC());
		} catch (Exception e) {
			e.printStackTrace();
			podeRetificar = false;
		}
	}

	private void verificaPendenciaNoESocial(){
		try {
			desabilitarEdicaoCamposIntegrados = colaboradorManager.isExisteHistoricoCadastralDoColaboradorComPendenciaNoESocial(getEmpresaSistema(), colaborador.getCodigoAC());
			if(desabilitarEdicaoCamposIntegrados)
				addActionMessage("Existem pendências no histórico cadastral do empregado junto ao eSocial. Não é possível editar as informações integradas no Fortes RH, só podem ser alteradas no Fortes Pessoal.");
		} catch (Exception e) {
			e.printStackTrace();
			desabilitarEdicaoCamposIntegrados = true;
			addActionWarning("Não foi possível estabelecer conexão com o Fortes Pessoal, desta forma não é possível editar as informações integradas.");
		}
	}
	
	private void setNacionalidadeAndNaturalidade() {
		if(getEmpresaSistema().isAcIntegra() && colaborador.getCodigoAC() != null && !colaborador.isNaoIntegraAc()){
			try {
				TNaturalidadeAndNacionalidade tNaturalidadeAndNacionalidade = colaboradorManager.getNaturalidadeAndNacionalidade(getEmpresaSistema(), colaborador.getCodigoAC());
				if(tNaturalidadeAndNacionalidade != null){
					colaborador.setNaturalidade(tNaturalidadeAndNacionalidade.getNaturalidade());
					colaborador.setNacionalidade(tNaturalidadeAndNacionalidade.getNacionalidade());
				}
			} catch (IntegraACException e) {}
		}
	}
	
	/**
	 * Promover (inserir novo histórico) para colaborador, a partir do candidato. */
	public String preparePromoverCandidato() throws Exception
	{
		Collection<Colaborador> colaboradors = colaboradorManager.find(new String[]{"candidato.id"}, new Object[]{candidato.getId()});
		
		if (colaboradors == null || colaboradors.isEmpty())
		{
			addActionMessage("Não foi encontrado um colaborador associado a este candidato.");
			return "naoEhColaborador";
		}
		else
			colaborador = (Colaborador) colaboradors.toArray()[0];
		
		return SUCCESS; // vai para prepareInsert da Situação
	}

	public String prepareContrata() throws Exception
	{
		parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
		if(idCandidato != null && (candidato == null || candidato.getId() == null))
		{
			candidato = new Candidato();
			candidato.setId(idCandidato);
		}

		candidato = candidatoManager.findByIdProjection(candidato.getId());
		
		if(candidato.isContratado() && colaboradorManager.candidatoEhColaborador(candidato.getId(), getEmpresaSistema().getId()))
		{
			colaborador = (Colaborador) colaboradorManager.find(new String[]{"candidato.id"}, new Object[]{candidato.getId()}).toArray()[0];
			solicitacao = solicitacaoManager.getValor(solicitacao.getId());
			solicitacao.setValorPromocao(String.valueOf(solicitacao.getFaixaSalarial().getFaixaSalarialHistoricoAtual().getValor()));

			return "ehColaborador";//preparePromoverCandidato.action
		}

		Map session = ActionContext.getContext().getSession();
		candidato.setFoto(candidatoManager.getFoto(candidato.getId()));

		prepareInsert();

		if (candidato.getEndereco().getUf() != null)
			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{candidato.getEndereco().getUf().getId()}, new String[]{"nome"});

		colaborador = getColaborador();
		colaborador.setFoto(candidato.getFoto());

		if(updateDados)
		{
			if(candidato.getCamposExtras() != null && candidato.getCamposExtras().getId() != null)
			{
				camposExtras = camposExtrasManager.findById(candidato.getCamposExtras().getId());
				colaborador.setCamposExtras(camposExtras);
			}
			
			if (solicitacao != null && solicitacao.getId() != null)
			{
				solicitacao = solicitacaoManager.findByIdProjection(solicitacao.getId());
				colaborador.setRegimeRevezamento(solicitacao.getHorarioComercial());
				
				historicoColaborador = populaHistoricoColaborador();
			} else {
				historicoColaborador = new HistoricoColaborador();
			}
			
			colaborador.setHistoricoColaborador(historicoColaborador);
			colaborador.setNome(candidato.getNome());
			// OBS: não sugerimos mais o nome Comercial, porque pode quebrar o insert (limitação do AC)
			colaborador.setContato(candidato.getContato());
			colaborador.setCursos(candidato.getCursos());
			colaborador.setDataAdmissao(new Date());
			colaborador.setHabilitacao(candidato.getHabilitacao());
			colaborador.setEndereco(candidato.getEndereco());
			colaborador.setPessoal(candidato.getPessoal());
			colaborador.setObservacao(candidato.getObservacao());
			
			historicoColaborador.setData(colaborador.getDataAdmissao());

			session.put("SESSION_FORMACAO", formacaoManager.findByCandidato(candidato.getId()));
			session.put("SESSION_IDIOMA", candidatoIdiomaManager.findByCandidato(candidato.getId()));
			session.put("SESSION_EXPERIENCIA", experienciaManager.findByCandidato(candidato.getId()));
		}

		colaborador.setCandidato(candidato);

		return Action.SUCCESS;
	}

	private HistoricoColaborador populaHistoricoColaborador()
	{
		Estabelecimento estabelecimento = solicitacao.getEstabelecimento();
		AreaOrganizacional areaOrganizacional = solicitacao.getAreaOrganizacional();;
		FaixaSalarial faixaSalarial = solicitacao.getFaixaSalarial();
		Ambiente ambiente = solicitacao.getAmbiente();
		Funcao funcao = solicitacao.getFuncao();
		
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);
		
		if(historicoColaborador.getFaixaSalarial() != null && historicoColaborador.getFaixaSalarial().getCargo() != null && historicoColaborador.getFaixaSalarial().getCargo().getId() != null)
			funcoes = funcaoManager.findByCargo(historicoColaborador.getFaixaSalarial().getCargo().getId());
		if(historicoColaborador.getEstabelecimento() != null && historicoColaborador.getEstabelecimento().getId() != null)
			ambientes = ambienteManager.findByEstabelecimento(historicoColaborador.getEstabelecimento().getId());
		
		return historicoColaborador;
	}

	public String insert() throws Exception{
		if(verifyQtdCadastros())
			return Action.INPUT;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try {
			if(colaborador.getDataAdmissao().after(historicoColaborador.getData()))
				throw new FortesException("Data do primeiro histórico não pode ser anterior à data de admissão.");

			if(areaOrganizacionalManager.verificaMaternidade(historicoColaborador.getAreaOrganizacional().getId(), null))
				throw new FortesException("Colaborador não pode ser inserido em áreas que possuem sub-áreas.");
			
			quantidadeLimiteColaboradoresPorCargoManager.validaLimite(historicoColaborador.getAreaOrganizacional().getId(), historicoColaborador.getFaixaSalarial().getId(), getEmpresaSistema().getId(), null);

			if(!fotoValida(colaborador.getFoto())){
				if(idCandidato == null)
					colaborador.setFoto(null);
				
				throw new Exception("Erro ao gravar as informações do colaborador. Foto inválida!");				
			}

			recuperarSessao();

			if(habilitaCampoExtra && camposExtras != null)
				colaborador.setCamposExtras(camposExtrasManager.save(camposExtras));
			
			if(!habilitaCampoExtra || colaborador.getCamposExtras() == null || colaborador.getCamposExtras().getId() == null)
				colaborador.setCamposExtras(null);
			
			if(colaborador.getCandidato() == null || colaborador.getCandidato().getId() == null)
				colaborador.setCandidato(null);
			
			if(colaborador.getMotivoDemissao() == null || colaborador.getMotivoDemissao().getId() == null)
				colaborador.setMotivoDemissao(null);

			if(idCandidato != null && colaborador.getFoto() == null)
				colaborador.setFoto(candidatoManager.getFoto(idCandidato));
			
			if(colaborador.getVinculo().equals(Vinculo.SOCIO))
				colaborador.setNaoIntegraAc(true);
				
			setDadosHistoricoColaborador();

			if (colaboradorManager.insert(colaborador, salarioColaborador, idCandidato, formacaos, idiomas, experiencias, solicitacao, getEmpresaSistema(), candidatoSolicitacaoId))
			{
				if (candidatoSolicitacaoId != null)
					candidatoSolicitacaoManager.setStatusAndDataContratacaoOrPromocao(candidatoSolicitacaoId, StatusCandidatoSolicitacao.CONTRATADO, getDataContratacao());
				
				colaboradorPeriodoExperienciaAvaliacaoManager.saveConfiguracaoAvaliacaoPeriodoExperiencia(colaborador, colaboradorAvaliacoes, colaboradorAvaliacoesGestor);
				
				if (StringUtils.equals(encerrarSolicitacao, "S")) 
					solicitacaoManager.encerrarSolicitacaoAoPreencherTotalVagas(solicitacao, getEmpresaSistema());

				addActionSuccess("Colaborador <strong>" + colaborador.getNome() + "</strong>  cadastrado com sucesso.");
				transactionManager.commit(status);
				
				verifyCadastroUsuario();
					
				if(solicitacao != null && solicitacao.getId() != null)
					return "successSolicitacao";
				else
					return Action.SUCCESS;
			}
			else
			{
				throw new Exception();
			}

		}
		catch (NestedRuntimeException e) // TODO ver necessidade desse catch. 
		{
			transactionManager.rollback(status);
			
			e.printStackTrace();
			addActionError("Erro ao gravar as informações do colaborador!");

			colaborador.setId(null);

			if(idCandidato == null)
				prepare();
			else
			{
				updateDados = false;
				prepareContrata();
			}

			return Action.ERROR;
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			
			e.printStackTrace();
			String message = "Erro ao gravar as informações do colaborador!";
			
			if(e.getMessage() != null)
				message = e.getMessage();
			else if(e.getCause() != null && e.getCause().getLocalizedMessage() != null)
				message = e.getCause().getLocalizedMessage();
			
			if (e instanceof FortesException || e instanceof LimiteColaboradorExceditoException)
				addActionWarning(message);
			else
				addActionError(message);

			colaborador.setId(null);

			if(idCandidato == null)
				prepare();
			else
			{
				updateDados = false;
				prepareContrata();
			}
	
			return Action.ERROR;
		}
	}

	private Date getDataContratacao(){
		if (colaborador.getDataAdmissao().after(DateUtil.criarDataMesAno(new Date()))) {
			return new Date();
		}
		return colaborador.getDataAdmissao();
	}
	
	private boolean verifyQtdCadastros() throws Exception {
		try{
			colaboradorManager.validaQtdCadastros(getEmpresaSistema().getId());
			return false;
		} catch (FortesException e){
			addActionMessage(e.getMessage());
			prepare();
			return true;
		} catch (Exception e){
			addActionError(e.getMessage());
			prepare();
			return true;
		}
	}

	private void verifyCadastroUsuario() {

		if (getEmpresaSistema().isCriarUsuarioAutomaticamente()) {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = transactionManager.getTransaction(def);
			try {
				Usuario usuario = new Usuario(colaborador.getNome(), colaborador.getPessoal().getCpf(), getEmpresaSistema().getSenhaPadrao(), true, colaborador);
				if (!getEmpresaSistema().isAcIntegra() || colaborador.isNaoIntegraAc()){
					if (usuarioManager.existeLogin(usuario))
						addActionWarning("Não foi possível criar o usuário para o colaborador <strong>"+ colaborador.getNome() +"</strong>. O login <strong>"+ colaborador.getPessoal().getCpf() +"</strong> já existe nesta ou em outra empresa.");
					else {
						usuarioManager.save(usuario);
						ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
						UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa(usuario, parametrosDoSistema.getPerfilPadrao(), getEmpresaSistema());
						usuarioEmpresaManager.save(usuarioEmpresa);
						colaboradorManager.atualizarUsuario(colaborador.getId(), usuario.getId());
						gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema(usuario.getLogin(), getEmpresaSistema().getSenhaPadrao(), colaborador.getContato().getEmail(), getEmpresaSistema());
					}
				}
				else if(usuarioManager.existeLogin(usuario)){
					addActionWarning("Não será criado automaticamente o usuário para o colaborador <strong>"+ colaborador.getNome() +"</strong>. O login <strong>"+ colaborador.getPessoal().getCpf() +"</strong> já existe nesta ou em outra empresa.");
				}
				
				transactionManager.commit(status);
			} catch (Exception e) {
				transactionManager.rollback(status);
				e.printStackTrace();
				addActionWarning("Não foi possível criar automaticamente o usuário para o colaborador <strong>"+ colaborador.getNome() +"</strong>. Caso deseje, crie-o manualmente.");
			}
		}
	}
	
	private void setDadosHistoricoColaborador()
	{
		if (historicoColaborador == null)
			historicoColaborador = new HistoricoColaborador();

		historicoColaborador.setTipoSalario(salarioPropostoPor);
		historicoColaborador.setIndice(indice);
		historicoColaborador.setQuantidadeIndice(quantidadeIndice);
		historicoColaborador.setObsACPessoal(obsACPessoal);

		colaborador.setHistoricoColaborador(historicoColaborador);
	}

	private void recuperarSessao()
	{
		Map session = ActionContext.getContext().getSession();

		formacaos = (Collection<Formacao>) session.get("SESSION_FORMACAO");
		idiomas = (Collection<CandidatoIdioma>) session.get("SESSION_IDIOMA");
		experiencias = (Collection<Experiencia>) session.get("SESSION_EXPERIENCIA");

		session.put("SESSION_FORMACAO", null);
		session.put("SESSION_IDIOMA", null);
		session.put("SESSION_EXPERIENCIA", null);
	}

	public String update() throws Exception
	{
		String mensagem = "";
		try
		{
			if(areaOrganizacionalManager.verificaMaternidade(historicoColaborador.getAreaOrganizacional().getId(), null))
			{
				addActionError("Colaborador não pode ser inserido em áreas que possuem sub-áreas.");
				prepareUpdate();
				return Action.INPUT;
			}
			
			if(historicoColaboradorManager.findByColaboradorProjection(colaborador.getId(), null).size() > 1 || (getEmpresaSistema().isAcIntegra() && !StringUtils.isBlank(colaborador.getCodigoAC())))
				editarHistorico = false;

			if(editarHistorico)
				quantidadeLimiteColaboradoresPorCargoManager.validaLimite(historicoColaborador.getAreaOrganizacional().getId(), historicoColaborador.getFaixaSalarial().getId(), getEmpresaSistema().getId(), colaborador.getId());
			
			if(historicoColaboradorManager.verificaPrimeiroHistoricoAdmissao(editarHistorico, historicoColaborador, colaborador))
			{
				addActionError("Data do primeiro histórico não pode ser anterior à data de admissão.");
				prepareUpdate();
				return Action.INPUT;
			}

			if (manterFoto)
			{
				colaborador.setFoto(colaboradorManager.getFoto(colaborador.getId()));
				colaborador.setManterFoto(manterFoto);
			}
			else if(!fotoValida(colaborador.getFoto()))
			{
				prepareUpdate();
				colaborador.setFoto(null);
				return Action.INPUT;
			}

			recuperarSessao();
			
			Colaborador colaboradorAux = (Colaborador) colaboradorManager.findToList(new String[]{"candidato"}, new String[]{"candidato"}, new String[]{"id"}, new Object[]{colaborador.getId()}).toArray()[0];
			if(colaboradorAux.getCandidato() != null && colaboradorAux.getCandidato().getId() != null)
				colaborador.setCandidato(colaboradorAux.getCandidato());
			else
				colaborador.setCandidato(null);
			
			if(colaborador.getMotivoDemissao() == null || colaborador.getMotivoDemissao().getId() == null)
				colaborador.setMotivoDemissao(null);

			colaborador.setEmpresa(getEmpresaSistema());
			
			if(camposExtras != null && habilitaCampoExtra)
				colaborador.setCamposExtras(camposExtrasManager.update(camposExtras, colaborador.getCamposExtras().getId(), getEmpresaSistema().getId()));
			
			if(colaborador.getCamposExtras() == null || colaborador.getCamposExtras().getId() == null)
				colaborador.setCamposExtras(null);
				
			setDadosHistoricoColaborador();
			
			if(getEmpresaSistema().isAcIntegra() && StringUtils.isNotBlank(colaborador.getCodigoAC()) && !colaborador.isNaoIntegraAc()  && 
					colaboradorManager.isExisteHistoricoCadastralDoColaboradorComPendenciaNoESocial(getEmpresaSistema(), colaborador.getCodigoAC())){
				colaboradorManager.setDadosIntegrados(colaborador);
				dadosIntegradosAtualizados = false;
				mensagem = "Os dados integrados não podem ser editados pois no Fortes Pessoal existe pendência como eSocial.";
			}
			
			colaboradorManager.update(colaborador, formacaos, idiomas, experiencias, getEmpresaSistema(),editarHistorico, salarioColaborador, dataAlteracao, dadosIntegradosAtualizados);
			
			boolean removeAvGestor = (getColaboradorSistemaId() == colaboradorAux.getId()) && !SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_AV_GESTOR_RECEBER_NOTIFICACAO_PROPRIA_AVALIACAO_ACOMP_DE_EXPERIENCIA"}); 
			colaboradorPeriodoExperienciaAvaliacaoManager.atualizaConfiguracaoAvaliacaoPeriodoExperiencia(colaborador, colaboradorAvaliacoes, colaboradorAvaliacoesGestor, removeAvGestor);
		}
		catch (IntegraACException e)
		{
			dadosIntegradosAtualizados = true;
			if(e.getMessage().contains("Data do último histórico cadastral"))
				addActionWarning(e.getMessage());
			else
				addActionError(e.getMessage());
			prepareUpdate();
			return Action.INPUT;
		}
		catch (LimiteColaboradorExceditoException e)
		{
			e.printStackTrace();
			addActionWarning(e.getMessage());
			prepareUpdate();
			
			return Action.INPUT;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Erro ao gravar as informações do colaborador!");
			prepareUpdate();

			return Action.INPUT;
		}

		addActionSuccess("Colaborador <strong>" + colaborador.getNome() + "</strong> alterado com sucesso." + "</br>" + mensagem);
		return Action.SUCCESS;
	}

	public String updateInfoPessoais() throws Exception
	{
		try{
			recuperarSessao();

			if(camposExtras != null && habilitaCampoExtra)
				colaborador.setCamposExtras(camposExtrasManager.update(camposExtras, colaborador.getCamposExtras().getId(), getEmpresaSistema().getId()));
			
			if(colaborador.getCamposExtras() == null || colaborador.getCamposExtras().getId() == null)
				colaborador.setCamposExtras(null);

			colaborador.setDataAtualizacao(new Date());
			colaboradorManager.updateInfoPessoais(colaborador, formacaos, idiomas, experiencias, getEmpresaSistema(), dataAlteracao, dadosIntegradosAtualizados);
			prepareUpdateInfoPessoais();
			addActionSuccess("Dados atualizado com sucesso.");
		}catch (IntegraACException e){
			dataAlteracao = new Date();
			dadosIntegradosAtualizados = true;
			prepareUpdateInfoPessoais();
			addActionError(e.getMessage());
		}catch (Exception e){
			prepareUpdateInfoPessoais();
			addActionError("Não foi possível atualizar os dados.");
		}

		return Action.SUCCESS;
	}

	//TODO BACALHAU consulta gigante
	public String prepareColaboradorSolicitacao() throws Exception
	{
		colaborador = (Colaborador) colaboradorManager.findByIdComHistoricoConfirmados(colaborador.getId());

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try {
			if(colaborador.getCandidato() == null || colaborador.getCandidato().getId() == null)
			{
				candidato = candidatoManager.findByCPF(colaborador.getPessoal().getCpf(), colaborador.getEmpresa().getId());
				
				if (candidato != null && checarCandidatoMesmoCpf)
				{
					Colaborador colabcomMesmoCpf = colaboradorManager.findByCandidato(candidato.getId(), colaborador.getEmpresa().getId());
					if(colabcomMesmoCpf == null || colabcomMesmoCpf.isDesligado())
					{
						transactionManager.rollback(status);
						return "mesmo_cpf";
					}
				}
				
				colaborador.setColaboradorIdiomas(colaboradorIdiomaManager.find(new String[]{"colaborador.id"}, new Object[]{colaborador.getId()}));
				colaborador.setExperiencias(experienciaManager.findByColaborador(colaborador.getId()));
				colaborador.setFormacao(formacaoManager.findByColaborador(colaborador.getId()));

				if (!vincularCandidatoMesmoCpf)
					candidato = candidatoManager.saveOrUpdateCandidatoByColaborador(colaborador);
				else if(candidato != null)
					colaboradorManager.desvinculaCandidato(candidato.getId());

				colaborador.setCandidato(candidato);
				colaboradorManager.update(colaborador);
				candidatoManager.updateDisponivelAndContratadoByColaborador(false, !colaborador.isDesligado(), colaborador.getId());
			}
			else
			{
				colaborador.getCandidato().setContratado(!colaborador.isDesligado());
				candidatoManager.update(colaborador.getCandidato());

				candidato = new Candidato();
				candidato.setId(colaborador.getCandidato().getId());
			}

			transactionManager.commit(status);

			return Action.SUCCESS;
		}catch(Exception e){
			
			transactionManager.rollback(status);
			e.printStackTrace();
			addActionError("Erro na atualização dos dados.");
			
			return Action.INPUT;
		}
	}

	public String preparePerformanceFuncional() throws Exception
	{
		if (colaborador != null && colaborador.getId() != null)
		{
			colaborador = colaboradorManager.findColaboradorById(colaborador.getId());
			colaborador.setFoto(colaboradorManager.getFoto(colaborador.getId()));
			Empresa empresaDoColaborador = colaborador.getEmpresa();
			habilitaCampoExtra = empresaDoColaborador.isCampoExtraColaborador();
			
			if(!getEmpresaSistema().getId().equals(empresaDoColaborador.getId()))
				addActionMessage("Colaborador visualizado não pertence a empresa que você está logado atualmente.");
			
			if(habilitaCampoExtra)
				configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoColaborador", "empresa.id"}, new Object[]{true, empresaDoColaborador.getId()}, new String[]{"ordem"});
			
			cqAvaliacaoDesempenhos = colaboradorQuestionarioManager.findAvaliacaoDesempenhoByColaborador(colaborador.getId());
			
			pesquisas = colaboradorQuestionarioManager.findByColaborador(colaborador.getId());
			
			Colaborador colaboradorLogado = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession());
			boolean possuiRole = SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_AV_GESTOR_RECEBER_NOTIFICACAO_PROPRIA_AVALIACAO_ACOMP_DE_EXPERIENCIA"});
			cqAvaliacaoExperiencias = colaboradorQuestionarioManager.findAvaliacaoByColaborador(colaborador.getId(), colaboradorLogado, possuiRole, areaOrganizacionalManager);
			
			historicoColaboradors = historicoColaboradorManager.progressaoColaborador(colaborador.getId(), empresaDoColaborador.getId());
			historicoColaborador = historicoColaboradorManager.getHistoricoAtual(colaborador.getId());

			idiomasColaborador =  colaboradorIdiomaManager.findByColaborador(colaborador.getId());
			formacaos = formacaoManager.findByColaborador(colaborador.getId());

			cursosColaborador = colaboradorTurmaManager.findHistoricoTreinamentosByColaborador(empresaDoColaborador.getId(), null, null, colaborador.getId());
			
			pontuacao = 0;
			ocorrenciasColaborador = new ArrayList<ColaboradorOcorrencia>();
			if(containsPermissaoVisualizarOcorrencias()){
				ocorrenciasColaborador = colaboradorOcorrenciaManager.findByColaborador(colaborador.getId());
				for (ColaboradorOcorrencia colaboradorOcorrencia : ocorrenciasColaborador)
					pontuacao += colaboradorOcorrencia.getOcorrencia().getPontuacao();
			}
			
			afastamentosColaborador = colaboradorAfastamentoManager.findByColaborador(colaborador.getId());
			experiencias = experienciaManager.findByColaborador(colaborador.getId());
			
			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VISUALIZAR_ANEXO_COLAB_LOGADO"}))
				documentoAnexosColaborador = documentoAnexoManager.getDocumentoAnexoByOrigemId(null, OrigemAnexo.AnexoColaborador, colaborador.getId());

			if(colaborador.getCandidato() != null && colaborador.getCandidato().getId() != null)
			{
				if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VISUALIZAR_ANEXO_COLAB_LOGADO"}))
					documentoAnexosCandidato = documentoAnexoManager.getDocumentoAnexoByOrigemId(null, OrigemAnexo.AnexoCandidato, colaborador.getCandidato().getId());
				historicosCandidatoByColaborador = historicoCandidatoManager.findByCandidato(colaborador.getCandidato());
			}
			
			catsColaborador = catManager.findByColaborador(colaborador);
			
			participacoesNaCipaColaborador = comissaoManager.getParticipacoesDeColaboradorNaCipa(colaborador.getId());

			//Popula areas com areas maes
			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaDoColaborador.getId());
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

			for (HistoricoColaborador historico: historicoColaboradors)
			{
				if(historico.getAreaOrganizacional() != null && historico.getAreaOrganizacional().getId() != null)
					historico.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, historico.getAreaOrganizacional().getId()));
			}

			relatorioPerformanceFuncional = new RelatorioPerformanceFuncional(
					colaborador, configuracaoCampoExtras, cqAvaliacaoDesempenhos, cqAvaliacaoExperiencias, historicoColaboradors, 
					historicoColaborador, idiomasColaborador,formacaos,cursosColaborador, ocorrenciasColaborador, 
					afastamentosColaborador, documentoAnexosColaborador, documentoAnexosCandidato, historicosCandidatoByColaborador,
					catsColaborador, participacoesNaCipaColaborador, areaOrganizacionals, experiencias);
			
			configPerformanceBoxes = StringUtil.toJSON(configuracaoPerformanceManager.findByUsuario(SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession())), new String[]{"usuario"}); 
			
			colaboradoresMesmoCpf = colaboradorManager.findByCpf(colaborador.getPessoal().getCpf(), null, null, null);
			
			return Action.SUCCESS;

		} else {
			addActionError("Colaborador não selecionado");
			return Action.INPUT;
		}
	}
	
	private boolean containsPermissaoVisualizarOcorrencias(){
		Colaborador colaboradorLogado = colaboradorManager.findByUsuario(getUsuarioLogado(), getEmpresaSistema().getId());
		if(colaboradorLogado != null && colaborador.getId().equals(colaboradorLogado.getId()) && usuarioManager.isResponsavelOrCoResponsavel(getUsuarioLogado().getId())){
			return usuarioEmpresaManager.containsRole(getUsuarioLogado().getId(), getEmpresaSistema().getId(), "ROLE_MOV_GESTOR_VISUALIZAR_PROPRIA_OCORRENCIA_PROVIDENCIA"); 
		}
		return true;
	}
	
	// TODO: SEM TESTE
	public String imprimirPerformanceFuncional() 
	{
		try
		{
			preparePerformanceFuncional();
			parametros = RelatorioUtil.getParametrosRelatorio("Performance Profissional ", getEmpresaSistema(), null);
			parametros.put("EXIBIR_SALARIO", SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"EXIBIR_SALARIO_PERFORMANCE"}));
		}
		catch (Exception e)
		{
			try {
				preparePerformanceFuncional();
			} catch (Exception e1) {
				addActionError("Erro ao gerar relatório");
				e1.printStackTrace();
			}
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();

			return Action.INPUT;
		}
		return SUCCESS;
	}
	
	public String showFoto() throws Exception
	{
		if (colaborador != null && colaborador.getId() != null)
		{
			colaborador.setFoto(colaboradorManager.getFoto(colaborador.getId()));
		}

		if (colaborador.getFoto() != null && colaborador.getFoto().getBytes() != null)
		{
			HttpServletResponse response = ServletActionContext.getResponse();

	        response.addHeader("Expires", "0");
	        response.addHeader("Pragma", "no-cache");
	        response.addHeader("Content-type", colaborador.getFoto().getContentType());
	        response.addHeader("Content-Transfer-Encoding", "binary");

	        response.getOutputStream().write(colaborador.getFoto().getBytes());
		}

		return Action.SUCCESS;
	}
	
	public String prepareAtualizarModeloAvaliacao() throws Exception
	{
		periodoExperiencias = periodoExperienciaManager.findAllSelect(getEmpresaSistema().getId(), false);
		estabelecimentoCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		avaliacoes = avaliacaoManager.findAllSelect(null, null, getEmpresaSistema().getId(), true, TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, null);
		
		return Action.SUCCESS;
	}

	public String atualizarModeloAvaliacao() throws Exception
	{
		try {
			colaboradorPeriodoExperienciaAvaliacaoManager.atualizaConfiguracaoAvaliacaoPeriodoExperienciaEmVariosColaboradores(colaboradorCheck, colaboradorAvaliacoes, colaboradorAvaliacoesGestor);
			
			addActionSuccess("Colaboradores atualizados com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Não foi possível atualizar os colaboradores.");
		}
		
		
		return Action.SUCCESS;
	}

	private boolean fotoValida(com.fortes.model.type.File foto)
	{
		boolean fotoValida =  true;
		if(foto != null)
		{
			if(foto.getContentType().length() >= 5)
			{
				if(!foto.getContentType().substring(0, 5).equals("image"))
				{
					addActionError("Tipo de arquivo não suportado");
					fotoValida = false;
				}

				if(foto.getSize() > 524288)
				{
					addActionError("Tamanho do arquivo maior que o suportado");
					fotoValida = false;
				}
			}
		}

		return fotoValida;
	}

	public Colaborador getColaborador()
	{
		if(colaborador == null)
			colaborador = new Colaborador();
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador){
		this.colaborador=colaborador;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager){
		this.colaboradorManager=colaboradorManager;
	}

	public Collection<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(Collection<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Collection<Beneficio> getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(Collection<Beneficio> beneficios) {
		this.beneficios = beneficios;
	}

	public Map getEscolaridades() {
		return escolaridades;
	}

	public void setEscolaridades(Map escolaridades) {
		this.escolaridades = escolaridades;
	}

	public Map getEstadosCivis() {
		return estadosCivis;
	}

	public void setEstadosCivis(Map estadosCivis) {
		this.estadosCivis = estadosCivis;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(
			Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
	}

	public Double getSalarioColaborador()
	{
		return salarioColaborador;
	}

	public void setSalarioColaborador(Double salarioColaborador)
	{
		this.salarioColaborador = salarioColaborador;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public Collection<Cidade> getCidades()
	{
		return cidades;
	}

	public void setCidades(Collection<Cidade> cidades)
	{
		this.cidades = cidades;
	}

	public Collection<Estado> getEstados()
	{
		return estados;
	}

	public void setEstados(Collection<Estado> estados)
	{
		this.estados = estados;
	}

	public void setCidadeManager(CidadeManager cidadeManager)
	{
		this.cidadeManager = cidadeManager;
	}

	public void setEstadoManager(EstadoManager estadoManager)
	{
		this.estadoManager = estadoManager;
	}

	public void setColaboradorIdiomaManager(ColaboradorIdiomaManager colaboradorIdiomaManager)
	{
		this.colaboradorIdiomaManager = colaboradorIdiomaManager;
	}

	public void setExperienciaManager(ExperienciaManager experienciaManager)
	{
		this.experienciaManager = experienciaManager;
	}

	public void setFormacaoManager(FormacaoManager formacaoManager)
	{
		this.formacaoManager = formacaoManager;
	}

	public Candidato getCandidato()
	{
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public void setCandidatoIdiomaManager(CandidatoIdiomaManager candidatoIdiomaManager)
	{
		this.candidatoIdiomaManager = candidatoIdiomaManager;
	}

	public Long getIdCandidato()
	{
		return idCandidato;
	}

	public void setIdCandidato(Long idCandidato)
	{
		this.idCandidato = idCandidato;
	}

	public String getCpfBusca() {
		return cpfBusca;
	}

	public void setCpfBusca(String cpfBusca) {
		this.cpfBusca = cpfBusca;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = StringUtil.retiraAcento(nomeBusca);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public HistoricoColaborador getHistoricoColaborador()
	{
		return historicoColaborador;
	}

	public void setHistoricoColaborador(HistoricoColaborador historicoColaborador)
	{
		this.historicoColaborador = historicoColaborador;
	}

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}

	public String getMotivo()
	{
		return motivo;
	}

	public void setMotivo(String motivo)
	{
		this.motivo = motivo;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager)
	{
		this.solicitacaoManager = solicitacaoManager;
	}

	public FuncaoManager getFuncaoManager()
	{
		return funcaoManager;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager)
	{
		this.funcaoManager = funcaoManager;
	}

	public Collection<Funcao> getFuncoes()
	{
		return funcoes;
	}

	public void setFuncoes(Collection<Funcao> funcoes)
	{
		this.funcoes = funcoes;
	}

	public Collection<HistoricoColaborador> getHistoricoColaboradors()
	{
		return historicoColaboradors;
	}

	public void setHistoricoColaboradors(Collection<HistoricoColaborador> historicoColaboradors)
	{
		this.historicoColaboradors = historicoColaboradors;
	}

	public Collection<ColaboradorIdioma> getIdiomasColaborador()
	{
		return idiomasColaborador;
	}

	public Collection<Experiencia> getExperiencias()
	{
		return experiencias;
	}

	public void setExperiencias(Collection<Experiencia> experiencias)
	{
		this.experiencias = experiencias;
	}

	public Collection<Formacao> getFormacaos()
	{
		return formacaos;
	}

	public void setFormacaos(Collection<Formacao> formacaos)
	{
		this.formacaos = formacaos;
	}

	public Collection<CandidatoIdioma> getIdiomas()
	{
		return idiomas;
	}

	public void setIdiomas(Collection<CandidatoIdioma> idiomas)
	{
		this.idiomas = idiomas;
	}

	public Collection<ColaboradorTurma> getCursosColaborador()
	{
		return cursosColaborador;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager)
	{
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public ColaboradorTurmaManager getColaboradorTurmaManager()
	{
		return colaboradorTurmaManager;
	}

	public void setColaboradorOcorrenciaManager(ColaboradorOcorrenciaManager colaboradorOcorrenciaManager)
	{
		this.colaboradorOcorrenciaManager = colaboradorOcorrenciaManager;
	}

	public Collection<ColaboradorOcorrencia> getOcorrenciasColaborador()
	{
		return ocorrenciasColaborador;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Map getVinculos()
	{
		return vinculos;
	}

	public void setVinculos(Map vinculos)
	{
		this.vinculos = vinculos;
	}

	public void setDocumentoAnexoManager(DocumentoAnexoManager documentoAnexoManager)
	{
		this.documentoAnexoManager = documentoAnexoManager;
	}

	public Collection<DocumentoAnexo> getDocumentoAnexosColaborador()
	{
		return documentoAnexosColaborador;
	}

	public Collection<DocumentoAnexo> getDocumentoAnexosCandidato()
	{
		return documentoAnexosCandidato;
	}

	public Map getSexos()
	{
		return sexos;
	}

	public void setSexos(Map sexos)
	{
		this.sexos = sexos;
	}

	public boolean isIntegraAc()
	{
		return integraAc;
	}

	public void setIntegraAc(boolean integraAc)
	{
		this.integraAc = integraAc;
	}

	public boolean isEditarHistorico()
	{
		return editarHistorico;
	}

	public void setEditarHistorico(boolean editarHistorico)
	{
		this.editarHistorico = editarHistorico;
	}

	public Map getDeficiencias()
	{
		return deficiencias;
	}

	public void setDeficiencias(Map deficiencias)
	{
		this.deficiencias = deficiencias;
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public int getSalarioPropostoPor()
	{
		return salarioPropostoPor;
	}

	public void setSalarioPropostoPor(int salarioPropostoPor)
	{
		this.salarioPropostoPor = salarioPropostoPor;
	}

	public Indice getIndice()
	{
		return indice;
	}

	public void setIndice(Indice indice)
	{
		this.indice = indice;
	}

	public Double getQuantidadeIndice()
	{
		return quantidadeIndice;
	}

	public void setQuantidadeIndice(Double quantidadeIndice)
	{
		this.quantidadeIndice = quantidadeIndice;
	}

	public Map getTiposSalarios()
	{
		return tiposSalarios;
	}

	public TipoAplicacaoIndice getTipoSalario()
	{
		return tipoSalario;
	}

	public Collection<Indice> getIndices()
	{
		return indices;
	}

	public IndiceHistoricoManager getIndiceHistoricoManager()
	{
		return indiceHistoricoManager;
	}

	public void setIndiceHistoricoManager(IndiceHistoricoManager indiceHistoricoManager)
	{
		this.indiceHistoricoManager = indiceHistoricoManager;
	}

	public Collection<FaixaSalarial> getFaixas()
	{
		return faixas;
	}

	public void setFaixas(Collection<FaixaSalarial> faixas)
	{
		this.faixas = faixas;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public Collection<Ambiente> getAmbientes()
	{
		return ambientes;
	}

	public void setAmbientes(Collection<Ambiente> ambientes)
	{
		this.ambientes = ambientes;
	}

	public void setAmbienteManager(AmbienteManager ambienteManager)
	{
		this.ambienteManager = ambienteManager;
	}

	public Double getValorCalculado()
	{
		return valorCalculado;
	}

	public TipoAplicacaoIndice getTipoAplicacaoIndice()
	{
		return tipoAplicacaoIndice;
	}

	public void setTipoAplicacaoIndice(TipoAplicacaoIndice tipoAplicacaoIndice)
	{
		this.tipoAplicacaoIndice = tipoAplicacaoIndice;
	}

	public void setAcPessoalClientSistema(AcPessoalClientSistema acPessoalClientSistema)
	{
		this.acPessoalClientSistema = acPessoalClientSistema;
	}

	public boolean isManterFoto()
	{
		return manterFoto;
	}

	public void setManterFoto(boolean manterFoto)
	{
		this.manterFoto = manterFoto;
	}

	public HashMap<String, String> getCodigosGFIP() {
		return codigosGFIP;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios()
	{
		return cqAvaliacaoExperiencias;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public void setColaboradorAfastamentoManager(ColaboradorAfastamentoManager colaboradorAfastamentoManager) {
		this.colaboradorAfastamentoManager = colaboradorAfastamentoManager;
	}

	public Collection<ColaboradorAfastamento> getAfastamentosColaborador() {
		return afastamentosColaborador;
	}

	public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager) {
		this.historicoCandidatoManager = historicoCandidatoManager;
	}

	public Collection<HistoricoCandidato> getHistoricosCandidatoByColaborador() {
		return historicosCandidatoByColaborador;
	}

	public void setCatManager(CatManager catManager) {
		this.catManager = catManager;
	}

	public Collection<Cat> getCatsColaborador() {
		return catsColaborador;
	}

	public void setComissaoManager(ComissaoManager comissaoManager) {
		this.comissaoManager = comissaoManager;
	}

	public Collection<ParticipacaoColaboradorCipa> getParticipacoesNaCipaColaborador() {
		return participacoesNaCipaColaborador;
	}

	public boolean isHabilitaCampoExtra()
	{
		return habilitaCampoExtra;
	}

	public void setHabilitaCampoExtra(boolean habilitaCampoExtra)
	{
		this.habilitaCampoExtra = habilitaCampoExtra;
	}

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager)
	{
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}

	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras()
	{
		return configuracaoCampoExtras;
	}

	public CamposExtras getCamposExtras()
	{
		return camposExtras;
	}

	public void setCamposExtras(CamposExtras camposExtras)
	{
		this.camposExtras = camposExtras;
	}

	public void setCamposExtrasManager(CamposExtrasManager camposExtrasManager)
	{
		this.camposExtrasManager = camposExtrasManager;
	}

	public void setConfiguracaoCampoExtras(Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras)
	{
		this.configuracaoCampoExtras = configuracaoCampoExtras;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarioAvaloacaoExperiencias()
	{
		return colaboradorQuestionarioAvaloacaoExperiencias;
	}

	public ColaboradorQuestionario getAvaliacaoExperiencia()
	{
		return avaliacaoExperiencia;
	}

	public void setAvaliacaoExperiencia(ColaboradorQuestionario avaliacaoExperiencia)
	{
		this.avaliacaoExperiencia = avaliacaoExperiencia;
	}

	public ColaboradorQuestionario getAvaliacaoDesempenho()
	{
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(ColaboradorQuestionario avaliacaoDesempenho)
	{
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}

	public RelatorioPerformanceFuncional getRelatorioPerformanceFuncional()
	{
		return relatorioPerformanceFuncional;
	}

	public void setRelatorioPerformanceFuncional(RelatorioPerformanceFuncional relatorioPerformanceFuncional)
	{
		this.relatorioPerformanceFuncional = relatorioPerformanceFuncional;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public String getConfigPerformanceBoxes()
	{
		return configPerformanceBoxes;
	}

	public void setConfiguracaoPerformanceManager(ConfiguracaoPerformanceManager configuracaoPerformanceManager)
	{
		this.configuracaoPerformanceManager = configuracaoPerformanceManager;
	}

	public Long getCandidatoSolicitacaoId()
	{
		return candidatoSolicitacaoId;
	}

	public void setCandidatoSolicitacaoId(Long candidatoSolicitacaoId)
	{
		this.candidatoSolicitacaoId = candidatoSolicitacaoId;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public char getStatusCandSol()
	{
		return statusCandSol;
	}

	public void setStatusCandSol(char statusCandSol)
	{
		this.statusCandSol = statusCandSol;
	}

	public void setObsACPessoal(String obsACPessoal)
	{
		this.obsACPessoal = obsACPessoal;
	}

	public void setQuantidadeLimiteColaboradoresPorCargoManager(QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager)
	{
		this.quantidadeLimiteColaboradoresPorCargoManager = quantidadeLimiteColaboradoresPorCargoManager;
	}

	public Collection<ColaboradorQuestionario> getPesquisas()
	{
		return pesquisas;
	}

	public boolean isChecarCandidatoMesmoCpf()
	{
		return checarCandidatoMesmoCpf;
	}

	public void setChecarCandidatoMesmoCpf(boolean checarCandidatoMesmoCpf)
	{
		this.checarCandidatoMesmoCpf = checarCandidatoMesmoCpf;
	}

	public boolean isVincularCandidatoMesmoCpf()
	{
		return vincularCandidatoMesmoCpf;
	}

	public void setVincularCandidatoMesmoCpf(boolean vincularCandidatoMesmoCpf)
	{
		this.vincularCandidatoMesmoCpf = vincularCandidatoMesmoCpf;
	}

	public Collection<PeriodoExperiencia> getPeriodoExperiencias()
	{
		return periodoExperiencias;
	}

	public void setPeriodoExperienciaManager(PeriodoExperienciaManager periodoExperienciaManager)
	{
		this.periodoExperienciaManager = periodoExperienciaManager;
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager)
	{
		this.avaliacaoManager = avaliacaoManager;
	}

	public Collection<ColaboradorPeriodoExperienciaAvaliacao> getColaboradorAvaliacoes()
	{
		return colaboradorAvaliacoes;
	}

	public void setColaboradorAvaliacoes(Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes)
	{
		this.colaboradorAvaliacoes = colaboradorAvaliacoes;
	}

	public void setColaboradorPeriodoExperienciaAvaliacaoManager(ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager)
	{
		this.colaboradorPeriodoExperienciaAvaliacaoManager = colaboradorPeriodoExperienciaAvaliacaoManager;
	}

	public Collection<Avaliacao> getAvaliacoes()
	{
		return avaliacoes;
	}

	public boolean isObrigarAmbienteFuncao()
	{
		return obrigarAmbienteFuncao;
	}

	public int getPontuacao()
	{
		return pontuacao;
	}

	public String getVoltarPara()
	{
		return voltarPara;
	}

	public void setVoltarPara(String voltarPara)
	{
		this.voltarPara = voltarPara;
	}

	public Collection<Colaborador> getColaboradoresMesmoCpf()
	{
		return colaboradoresMesmoCpf;
	}

	public Collection<ColaboradorPeriodoExperienciaAvaliacao> getColaboradorAvaliacoesGestor()
	{
		return colaboradorAvaliacoesGestor;
	}

	public void setColaboradorAvaliacoesGestor(Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoesGestor)
	{
		this.colaboradorAvaliacoesGestor = colaboradorAvaliacoesGestor;
	}

	public String getEncerrarSolicitacao()
	{
		return encerrarSolicitacao;
	}

	public void setEncerrarSolicitacao(String encerrarSolicitacao)
	{
		this.encerrarSolicitacao = encerrarSolicitacao;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public Collection<CheckBox> getAreaCheckList()
	{
		return areaCheckList;
	}

	public Collection<CheckBox> getColaboradorCheckList()
	{
		return colaboradorCheckList;
	}

	public Collection<CheckBox> getEstabelecimentoCheckList()
	{
		return estabelecimentoCheckList;
	}

	public void setAreaCheck(Long[] areaCheck)
	{
		this.areaCheck = areaCheck;
	}

	public void setColaboradorCheck(Long[] colaboradorCheck)
	{
		this.colaboradorCheck = colaboradorCheck;
	}

	public void setEstabelecimentoCheck(Long[] estabelecimentoCheck)
	{
		this.estabelecimentoCheck = estabelecimentoCheck;
	}

	public Collection<ColaboradorQuestionario> getCqAvaliacaoDesempenhos() 
	{
		return cqAvaliacaoDesempenhos;
	}

	public void setCqAvaliacaoDesempenhos(Collection<ColaboradorQuestionario> cqAvaliacaoDesempenhos)
	{
		this.cqAvaliacaoDesempenhos = cqAvaliacaoDesempenhos;
	}

	public Collection<ColaboradorQuestionario> getCqAvaliacaoExperiencias()
	{
		return cqAvaliacaoExperiencias;
	}

	public void setCqAvaliacaoExperiencias(Collection<ColaboradorQuestionario> cqAvaliacaoExperiencias)
	{
		this.cqAvaliacaoExperiencias = cqAvaliacaoExperiencias;
	}

	public ParametrosDoSistemaManager getParametrosDoSistemaManager() {
		return parametrosDoSistemaManager;
	}
	
	public ParametrosDoSistema getParametrosDoSistema() {
		return parametrosDoSistema;
	}

	public void setParametrosDoSistema(ParametrosDoSistema parametrosDoSistema) {
		this.parametrosDoSistema = parametrosDoSistema;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setUsuarioManager(UsuarioManager usuarioManager) {
		this.usuarioManager = usuarioManager;
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager)
	{
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setConfiguracaoCampoExtraVisivelObrigadotorioManager(ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager) {
		this.configuracaoCampoExtraVisivelObrigadotorioManager = configuracaoCampoExtraVisivelObrigadotorioManager;
	}

	public ConfiguracaoCampoExtraVisivelObrigadotorio getCampoExtraVisivelObrigadotorio() {
		return campoExtraVisivelObrigadotorio;
	}
	
	public boolean isDadosIntegradosAtualizados() {
		return dadosIntegradosAtualizados;
	}

	public void setDadosIntegradosAtualizados(boolean dadosIntegradosAtualizados) {
		this.dadosIntegradosAtualizados = dadosIntegradosAtualizados;
	}
	
	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public boolean isDesabilitarEdicaoCamposIntegrados() {
		return desabilitarEdicaoCamposIntegrados;
	}

	public void setDesabilitarEdicaoCamposIntegrados(boolean desabilitarEdicaoCamposIntegrados) {
		this.desabilitarEdicaoCamposIntegrados = desabilitarEdicaoCamposIntegrados;
	}

	public String getCamposColaboradorObrigatorio() {
		return camposColaboradorObrigatorio;
	}

	public void setCamposColaboradorObrigatorio(String camposColaboradorObrigatorio) {
		this.camposColaboradorObrigatorio = camposColaboradorObrigatorio;
	}

	public boolean isEmpresaEstaIntegradaEAderiuAoESocial() {
		return empresaEstaIntegradaEAderiuAoESocial;
	}

	public void setEmpresaEstaIntegradaEAderiuAoESocial() {
		this.empresaEstaIntegradaEAderiuAoESocial = isEmpresaIntegradaEAderiuAoESocial();
	}

	public boolean isPodeRetificar() {
		return podeRetificar;
	}

	public void setPodeRetificar(boolean podeRetificar) {
		this.podeRetificar = podeRetificar;
	}

	public boolean isPisObrigatorio() {
		return pisObrigatorio;
	}

	public void setPisObrigatorio(boolean pisObrigatorio) {
		this.pisObrigatorio = pisObrigatorio;
	}
}
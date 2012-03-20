package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NestedRuntimeException;

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
import com.fortes.rh.business.geral.ConfiguracaoPerformanceManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.exception.LimiteColaboradorExceditoException;
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
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
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
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.relatorio.ParticipacaoColaboradorCipa;
import com.fortes.rh.model.relatorio.RelatorioPerformanceFuncional;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.rh.web.ws.AcPessoalClientSistema;
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
	private SolicitacaoExameManager solicitacaoExameManager;
	private IndiceManager indiceManager;
	private AcPessoalClientSistema acPessoalClientSistema;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorAfastamentoManager colaboradorAfastamentoManager;
	private HistoricoCandidatoManager historicoCandidatoManager;
	private CatManager catManager;
	private ComissaoManager comissaoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private CamposExtrasManager camposExtrasManager;
	private ConfiguracaoPerformanceManager configuracaoPerformanceManager;
	private UsuarioMensagemManager usuarioMensagemManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private AvaliacaoManager avaliacaoManager;
	private ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager;
	
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
	private Collection<ColaboradorQuestionario> avaliacaoExperiencias;
	private Collection<ColaboradorQuestionario> avaliacaoDesempenhos;
	private Collection<ColaboradorQuestionario> colaboradorQuestionarioAvaloacaoExperiencias;
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
	private Collection<PeriodoExperiencia> periodoExperiencias;
	private Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes;
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

	//Utilizado apenas na hora de contratar o colaborador
	private Long idCandidato;
	
	private HashMap<String, String> codigosGFIP; 

	private boolean integraAc;
	private boolean obrigarAmbienteFuncaoColaborador;
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

		obrigarAmbienteFuncaoColaborador = getEmpresaSistema().isObrigarAmbienteFuncaoColaborador();
		habilitaCampoExtra = getEmpresaSistema().isCampoExtraColaborador();
		if(habilitaCampoExtra)
			configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoColaborador", "empresa.id"}, new Object[]{true, getEmpresaSistema().getId()}, new String[]{"ordem"});
			
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

		areaOrganizacionals = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.ATIVA, areaInativaId);

		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		
		periodoExperiencias = periodoExperienciaManager.findAllSelect(getEmpresaSistema().getId(), false);
		
		avaliacoes = avaliacaoManager.findAllSelect(getEmpresaSistema().getId(), true, 'A', null);
	}

	public String prepareInsert() throws Exception
	{
		try
		{
			colaboradorManager.validaQtdCadastros();
		} catch (Exception e)
		{
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
		
		prepare();
		if(updateDados)
		{
			Map session = ActionContext.getContext().getSession();
			session.put("SESSION_FORMACAO", null);
			session.put("SESSION_IDIOMA", null);
			session.put("SESSION_EXPERIENCIA", null);
		}

		return Action.SUCCESS;
	}

	public String prepareUpdateInfoPessoais() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		colaborador = SecurityUtil.getColaboradorSession(session);
		colaborador = colaboradorManager.findColaboradorById(colaborador.getId());
		
		if(colaborador == null)
		{
			addActionMessage("Você não tem Colaborador cadastrado");
		}
		else if( ! colaborador.getEmpresa().getId().equals(getEmpresaSistema().getId()))//isso é muito importante, evita que o cara edite o colaborador com os dados errado no AC (Francisco Barroso)
		{
			addActionMessage("Só é possível editar dados pessoais para empresa na qual você foi contratado(a). Acesse a empresa " + colaborador.getEmpresaNome() + " para alterar suas informações.");
			colaborador = null;
		}
		else
		{
			escolaridades = new Escolaridade();
			estadosCivis = new EstadoCivil();
			
			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{colaborador.getEndereco().getUf().getId()}, new String[]{"nome"});
			estados = estadoManager.findAll(new String[]{"sigla"});
			
			habilitaCampoExtra = getEmpresaSistema().isCampoExtraColaborador();
			if(habilitaCampoExtra)
				configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoColaborador", "empresa.id"}, new Object[]{true, getEmpresaSistema().getId()}, new String[]{"ordem"});
			
			if(habilitaCampoExtra && colaborador.getCamposExtras() != null && colaborador.getCamposExtras().getId() != null)
				camposExtras = camposExtrasManager.findById(colaborador.getCamposExtras().getId());
			
			session.put("SESSION_IDIOMA", candidatoIdiomaManager.montaListCandidatoIdioma(colaborador.getId()));
			session.put("SESSION_EXPERIENCIA", experienciaManager.findByColaborador(colaborador.getId()));
			session.put("SESSION_FORMACAO", formacaoManager.findByColaborador(colaborador.getId()));			
		}

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		if(historicoColaboradorManager.findByColaboradorProjection(colaborador.getId()).size() > 1 || (getEmpresaSistema().isAcIntegra() && !colaborador.getCodigoAC().equals("")))
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
		
		return Action.SUCCESS;
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
			}
			
			colaborador.setNome(candidato.getNome());
			// OBS: não sugerimos mais o nome Comercial, porque pode quebrar o insert (limitação do AC)
			colaborador.setContato(candidato.getContato());
			colaborador.setCursos(candidato.getCursos());
			colaborador.setDataAdmissao(new Date());
			colaborador.setHabilitacao(candidato.getHabilitacao());
			colaborador.setEndereco(candidato.getEndereco());
			colaborador.setPessoal(candidato.getPessoal());
			colaborador.setObservacao(candidato.getObservacao());

			session.put("SESSION_FORMACAO", formacaoManager.findByCandidato(candidato.getId()));
			session.put("SESSION_IDIOMA", candidatoIdiomaManager.findByCandidato(candidato.getId()));
			session.put("SESSION_EXPERIENCIA", experienciaManager.findByCandidato(candidato.getId()));
//			session.put("SESSION_HABILITACAO_CANDIDATO", candidato.getHabilitacao());
		}

		colaborador.setCandidato(candidato);

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try
		{
			colaboradorManager.validaQtdCadastros();			
			
		} catch (Exception e)
		{
			addActionMessage(e.getMessage());
			prepare();
			return Action.INPUT;
		}

		try
		{
			
			if(historicoColaborador.getData().before(colaborador.getDataAdmissao()))
				throw new Exception("Data do primeiro histórico não pode ser anterior à data de admissão.");

			if(areaOrganizacionalManager.verificaMaternidade(historicoColaborador.getAreaOrganizacional().getId()))
				throw new Exception("Colaborador não pode ser inserido em áreas que possuem sub-áreas.");

			quantidadeLimiteColaboradoresPorCargoManager.validaLimite(historicoColaborador.getAreaOrganizacional().getId(), historicoColaborador.getFaixaSalarial().getId(), getEmpresaSistema().getId(), null);

			if(!fotoValida(colaborador.getFoto()))
			{
				if(idCandidato == null)
					colaborador.setFoto(null);
				
				throw new Exception("Erro ao gravar as informações do colaborador. Foto inválida!");				
			}

			recuperarSessao();

			if(habilitaCampoExtra && camposExtras != null)
				colaborador.setCamposExtras(camposExtrasManager.save(camposExtras));
			if(colaborador.getCamposExtras() == null || colaborador.getCamposExtras().getId() == null)
				colaborador.setCamposExtras(null);
			
			if(colaborador.getCandidato() == null || colaborador.getCandidato().getId() == null)
				colaborador.setCandidato(null);
			
			if(colaborador.getMotivoDemissao() == null || colaborador.getMotivoDemissao().getId() == null)
				colaborador.setMotivoDemissao(null);

			if(idCandidato != null && colaborador.getFoto() == null)
				colaborador.setFoto(candidatoManager.getFoto(idCandidato));

				
			setDadosHistoricoColaborador();

			if (colaboradorManager.insert(colaborador, salarioColaborador, idCandidato, formacaos, idiomas, experiencias, solicitacao, getEmpresaSistema()))
			{
				// Transferindo solicitações médicas do candidato
				solicitacaoExameManager.transferir(getEmpresaSistema().getId(), idCandidato, colaborador.getId());
				
				if (candidatoSolicitacaoId != null)
					candidatoSolicitacaoManager.setStatus(candidatoSolicitacaoId, StatusCandidatoSolicitacao.CONTRATADO);
				
				colaboradorPeriodoExperienciaAvaliacaoManager.saveConfiguracaoAvaliacaoPeriodoExperiencia(colaborador, colaboradorAvaliacoes);
				
				addActionMessage("Colaborador \"" + colaborador.getNome() + "\"  cadastrado com sucesso.");
				
				return Action.SUCCESS;
			}
			else
			{
				throw new Exception();
			}

		}
		catch (NestedRuntimeException e) // TODO ver necessidade desse catch. 
		{
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
			e.printStackTrace();
			
			String message = "Erro ao gravar as informações do colaborador!";
			
			if(e.getMessage() != null)
				message = e.getMessage();
			else if(e.getCause() != null && e.getCause().getLocalizedMessage() != null)
				message = e.getCause().getLocalizedMessage();
			
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
		try
		{
			if(areaOrganizacionalManager.verificaMaternidade(historicoColaborador.getAreaOrganizacional().getId()))
			{
				addActionError("Colaborador não pode ser inserido em áreas que possuem sub-áreas.");
				prepareUpdate();
				return Action.INPUT;
			}

			if(historicoColaboradorManager.findByColaboradorProjection(colaborador.getId()).size() > 1 || (getEmpresaSistema().isAcIntegra() && !colaborador.getCodigoAC().equals("")))
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
			
			colaboradorManager.update(colaborador, formacaos, idiomas, experiencias, getEmpresaSistema(),editarHistorico, salarioColaborador);
			
			colaboradorPeriodoExperienciaAvaliacaoManager.removeConfiguracaoAvaliacaoPeriodoExperiencia(colaborador);
			colaboradorPeriodoExperienciaAvaliacaoManager.saveConfiguracaoAvaliacaoPeriodoExperiencia(colaborador, colaboradorAvaliacoes);
		}
//		catch (NestedRuntimeException e)
//		{
//			e.printStackTrace();
//			addActionError("Erro ao gravar as informações do colaborador!");
//			prepareUpdate();
//
//			return Action.INPUT;
//		}
		catch (IntegraACException e)
		{
			addActionError("Erro ao gravar as informações do colaborador no AC Pessoal.");
			prepareUpdate();
			
			return Action.INPUT;
		}
		catch (LimiteColaboradorExceditoException e)
		{
			e.printStackTrace();
			addActionError(e.getMessage());
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

		addActionMessage("Colaborador \"" + colaborador.getNome() + "\" editado com sucesso.");
		return Action.SUCCESS;
	}

	public String updateInfoPessoais() throws Exception
	{
		try
		{
			recuperarSessao();
			
			if(camposExtras != null && habilitaCampoExtra)
				colaborador.setCamposExtras(camposExtrasManager.update(camposExtras, colaborador.getCamposExtras().getId(), getEmpresaSistema().getId()));

			colaborador.setDataAtualizacao(new Date());
			colaboradorManager.updateInfoPessoais(colaborador, formacaos, idiomas, experiencias, getEmpresaSistema());
			prepareUpdateInfoPessoais();
			
			addActionMessage("Colaborador editado com sucesso.");
		}
		catch (Exception e)
		{
			prepareUpdateInfoPessoais();
			addActionError("Erro ao Editar Colaborador.");
		}

		return Action.SUCCESS;
	}

	//TODO BACALHAU consulta gigante
	public String prepareColaboradorSolicitacao() throws Exception
	{
		colaborador = (Colaborador) colaboradorManager.findByIdComHistoricoConfirmados(colaborador.getId());
		
		if(colaborador.getCandidato() == null || colaborador.getCandidato().getId() == null)
		{
			candidato = candidatoManager.findByCPF(colaborador.getPessoal().getCpf(), colaborador.getEmpresa().getId());
			if (candidato != null && checarCandidatoMesmoCpf)
				return Action.INPUT;
			
			colaborador.setColaboradorIdiomas(colaboradorIdiomaManager.find(new String[]{"colaborador.id"}, new Object[]{colaborador.getId()}));
			colaborador.setExperiencias(experienciaManager.findByColaborador(colaborador.getId()));
			colaborador.setFormacao(formacaoManager.findByColaborador(colaborador.getId()));

			if (!vincularCandidatoMesmoCpf)
				candidato = candidatoManager.criarCandidatoByColaborador(colaborador);
			
			colaborador.setCandidato(candidato);

			colaboradorManager.update(colaborador);
		}
		else
		{
			colaborador.getCandidato().setContratado(false);
			candidatoManager.update(colaborador.getCandidato());

			candidato = new Candidato();
			candidato.setId(colaborador.getCandidato().getId());
		}

		return Action.SUCCESS;
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
			
			avaliacaoDesempenhos = colaboradorQuestionarioManager.findAvaliacaoByColaborador(colaborador.getId(), true);
			
			pesquisas = colaboradorQuestionarioManager.findByColaborador(colaborador.getId());
			
			avaliacaoExperiencias = colaboradorQuestionarioManager.findAvaliacaoByColaborador(colaborador.getId(), false);
			
			historicoColaboradors = historicoColaboradorManager.progressaoColaborador(colaborador.getId(), empresaDoColaborador.getId());
			historicoColaborador = historicoColaboradorManager.getHistoricoAtual(colaborador.getId());

			idiomasColaborador =  colaboradorIdiomaManager.findByColaborador(colaborador.getId());
			formacaos = formacaoManager.findByColaborador(colaborador.getId());
			
			cursosColaborador = colaboradorTurmaManager.findHistoricoTreinamentosByColaborador(empresaDoColaborador.getId(), colaborador.getId(), null, null);
			
			ocorrenciasColaborador = colaboradorOcorrenciaManager.findByColaborador(colaborador.getId());
			pontuacao = 0;
			for (ColaboradorOcorrencia colaboradorOcorrencia : ocorrenciasColaborador)
				pontuacao += colaboradorOcorrencia.getOcorrencia().getPontuacao();
			
			afastamentosColaborador = colaboradorAfastamentoManager.findByColaborador(colaborador.getId());
			experiencias = experienciaManager.findByColaborador(colaborador.getId());
			
			documentoAnexosColaborador = documentoAnexoManager.getDocumentoAnexoByOrigemId(OrigemAnexo.AnexoColaborador, colaborador.getId());

			if(colaborador.getCandidato() != null && colaborador.getCandidato().getId() != null)
			{
				documentoAnexosCandidato = documentoAnexoManager.getDocumentoAnexoByOrigemId(OrigemAnexo.AnexoCandidato, colaborador.getCandidato().getId());
				historicosCandidatoByColaborador = historicoCandidatoManager.findByCandidato(colaborador.getCandidato());
			}
			
			catsColaborador = catManager.findByColaborador(colaborador);
			
			participacoesNaCipaColaborador = comissaoManager.getParticipacoesDeColaboradorNaCipa(colaborador.getId());

			//Popula areas com areas maes
			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(empresaDoColaborador.getId(), AreaOrganizacional.TODAS, null);
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

			for (HistoricoColaborador historico: historicoColaboradors)
			{
				if(historico.getAreaOrganizacional() != null && historico.getAreaOrganizacional().getId() != null)
					historico.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, historico.getAreaOrganizacional().getId()));
			}

			relatorioPerformanceFuncional = new RelatorioPerformanceFuncional(
					colaborador, configuracaoCampoExtras, avaliacaoDesempenhos, avaliacaoExperiencias, historicoColaboradors, 
					historicoColaborador, idiomasColaborador,formacaos,cursosColaborador, ocorrenciasColaborador, 
					afastamentosColaborador, documentoAnexosColaborador, documentoAnexosCandidato, historicosCandidatoByColaborador,
					catsColaborador, participacoesNaCipaColaborador, areaOrganizacionals);
			
			configPerformanceBoxes = StringUtil.toJSON(configuracaoPerformanceManager.findByUsuario(SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession())), new String[]{"usuario"}); 
			
			colaboradoresMesmoCpf = colaboradorManager.findByCpf(colaborador.getPessoal().getCpf(), null);
			
			return Action.SUCCESS;

		} else {
			addActionError("Colaborador não selecionado");
			return Action.INPUT;
		}
	}
	
	public String imprimirPerformanceFuncional() 
	{
		try
		{
			preparePerformanceFuncional();
			String filtro = "Período : ";
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Admitidos ", getEmpresaSistema(), filtro);
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

	public void setSolicitacaoExameManager(SolicitacaoExameManager solicitacaoExameManager)
	{
		this.solicitacaoExameManager = solicitacaoExameManager;
	}

	public HashMap<String, String> getCodigosGFIP() {
		return codigosGFIP;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios()
	{
		return avaliacaoExperiencias;
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

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
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

	public void setConfiguracaoCampoExtras(Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras) {
		this.configuracaoCampoExtras = configuracaoCampoExtras;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarioAvaloacaoExperiencias() {
		return colaboradorQuestionarioAvaloacaoExperiencias;
	}

	public Collection<ColaboradorQuestionario> getAvaliacaoExperiencias() {
		return avaliacaoExperiencias;
	}

	public void setAvaliacaoExperiencias(Collection<ColaboradorQuestionario> avaliacaoExperiencias) {
		this.avaliacaoExperiencias = avaliacaoExperiencias;
	}

	public Collection<ColaboradorQuestionario> getAvaliacaoDesempenhos() {
		return avaliacaoDesempenhos;
	}

	public void setAvaliacaoDesempenhos(Collection<ColaboradorQuestionario> avaliacaoDesempenhos) {
		this.avaliacaoDesempenhos = avaliacaoDesempenhos;
	}

	public ColaboradorQuestionario getAvaliacaoExperiencia() {
		return avaliacaoExperiencia;
	}

	public void setAvaliacaoExperiencia(ColaboradorQuestionario avaliacaoExperiencia) {
		this.avaliacaoExperiencia = avaliacaoExperiencia;
	}

	public ColaboradorQuestionario getAvaliacaoDesempenho() {
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(ColaboradorQuestionario avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}

	public RelatorioPerformanceFuncional getRelatorioPerformanceFuncional() {
		return relatorioPerformanceFuncional;
	}

	public void setRelatorioPerformanceFuncional(RelatorioPerformanceFuncional relatorioPerformanceFuncional) {
		this.relatorioPerformanceFuncional = relatorioPerformanceFuncional;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public String getConfigPerformanceBoxes() {
		return configPerformanceBoxes;
	}

	public void setConfiguracaoPerformanceManager(ConfiguracaoPerformanceManager configuracaoPerformanceManager) {
		this.configuracaoPerformanceManager = configuracaoPerformanceManager;
	}

	public Long getCandidatoSolicitacaoId() {
		return candidatoSolicitacaoId;
	}

	public void setCandidatoSolicitacaoId(Long candidatoSolicitacaoId) {
		this.candidatoSolicitacaoId = candidatoSolicitacaoId;
	}
	
	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public char getStatusCandSol() {
		return statusCandSol;
	}

	public void setStatusCandSol(char statusCandSol) {
		this.statusCandSol = statusCandSol;
	}

	public void setObsACPessoal(String obsACPessoal) {
		this.obsACPessoal = obsACPessoal;
	}

	public void setUsuarioMensagemManager(UsuarioMensagemManager usuarioMensagemManager) {
		this.usuarioMensagemManager = usuarioMensagemManager;
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public void setQuantidadeLimiteColaboradoresPorCargoManager(QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager) {
		this.quantidadeLimiteColaboradoresPorCargoManager = quantidadeLimiteColaboradoresPorCargoManager;
	}

	public Collection<ColaboradorQuestionario> getPesquisas() {
		return pesquisas;
	}
	
	public boolean isChecarCandidatoMesmoCpf() {
		return checarCandidatoMesmoCpf;
	}
	
	public void setChecarCandidatoMesmoCpf(boolean checarCandidatoMesmoCpf) {
		this.checarCandidatoMesmoCpf = checarCandidatoMesmoCpf;
	}

	public boolean isVincularCandidatoMesmoCpf() {
		return vincularCandidatoMesmoCpf;
	}

	public void setVincularCandidatoMesmoCpf(boolean vincularCandidatoMesmoCpf) {
		this.vincularCandidatoMesmoCpf = vincularCandidatoMesmoCpf;
	}

	public Collection<PeriodoExperiencia> getPeriodoExperiencias() {
		return periodoExperiencias;
	}

	public void setPeriodoExperienciaManager(
			PeriodoExperienciaManager periodoExperienciaManager) {
		this.periodoExperienciaManager = periodoExperienciaManager;
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) {
		this.avaliacaoManager = avaliacaoManager;
	}

	public Collection<ColaboradorPeriodoExperienciaAvaliacao> getColaboradorAvaliacoes() {
		return colaboradorAvaliacoes;
	}

	public void setColaboradorAvaliacoes(
			Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes) {
		this.colaboradorAvaliacoes = colaboradorAvaliacoes;
	}

	public void setColaboradorPeriodoExperienciaAvaliacaoManager(
			ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager) {
		this.colaboradorPeriodoExperienciaAvaliacaoManager = colaboradorPeriodoExperienciaAvaliacaoManager;
	}

	public Collection<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public boolean isObrigarAmbienteFuncaoColaborador() {
		return obrigarAmbienteFuncaoColaborador;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public String getVoltarPara() {
		return voltarPara;
	}

	public void setVoltarPara(String voltarPara) {
		this.voltarPara = voltarPara;
	}

	public Collection<Colaborador> getColaboradoresMesmoCpf() {
		return colaboradoresMesmoCpf;
	}
}

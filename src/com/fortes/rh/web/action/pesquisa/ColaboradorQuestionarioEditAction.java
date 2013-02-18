package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.RespostaQuestionarioVO;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.rh.web.action.captacao.RelatorioCandidatoSolicitacaoList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorQuestionarioEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private GrupoOcupacionalManager grupoOcupacionalManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private CargoManager cargoManager;
	private ColaboradorManager colaboradorManager;
	private QuestionarioManager questionarioManager;
	private EmpresaManager empresaManager;
	private AvaliacaoManager avaliacaoManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private PerguntaManager perguntaManager;
	private RespostaManager respostaManager;
	private CandidatoManager candidatoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private NivelCompetenciaManager nivelCompetenciaManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;

	private Avaliacao avaliacaoExperiencia;
	private Questionario questionario;
	private Colaborador colaborador;
	private Colaborador avaliador;
	private Candidato candidato;
	private ColaboradorQuestionario colaboradorQuestionario;
	private RelatorioCandidatoSolicitacaoList relatorioCandidatoSolicitacaoList;
	private Long etapaSeletivaId;
	private String indicadoPor;
	private String observacaoRH;
	private String nomeBusca;
	private char visualizar;
	
	private Collection<ColaboradorResposta> colaboradorRespostas;
	private Collection<CandidatoSolicitacao> candidatoSolicitacaos;
	private Collection<RespostaQuestionarioVO> respostaQuestionarioVOs;
	private Collection<Pergunta> perguntas;
	private Collection<Avaliacao> avaliacaoExperiencias = new ArrayList<Avaliacao>();
	
	private TipoPergunta tipoPergunta = new TipoPergunta();
	
	private char filtrarPor;	//1 = Área Organizacioal, 2 = Grupo Organizacional
	private char qtdPercentual;	// 1 = Percentual, 2 = Quantidade
	private boolean calcularPercentual;
	private boolean aplicarPorParte;
	private int quantidade;
	private String percentual = "";

	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] gruposCheck;
	private Collection<CheckBox> gruposCheckList = new ArrayList<CheckBox>();

	private Long[] colaboradoresId;
	private boolean exibirBotaoConcluir;

	private Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
	private Collection<Empresa> empresas;
	
	private Long empresaId;
	private Long anuncioId;
	
	private TipoQuestionario tipoQuestionario = new TipoQuestionario();
	private Solicitacao solicitacao;
	private Map<String, Object> parametros;
	private boolean ordenarPorAspecto;
	private boolean exibeResultadoAutoavaliacao;
	private Boolean compartilharColaboradores;
	private boolean respostaColaborador;
	private boolean moduloExterno;
	private boolean preview;
	private boolean exibirCompetencia = true;

	private Collection<NivelCompetencia> nivelCompetencias;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSugeridos;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvos;

	public String prepareInsert() throws Exception
	{
		empresaId = getEmpresaSistema().getId();
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , empresaId, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_MOV_QUESTIONARIO");
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);

		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome");
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

		gruposCheckList = grupoOcupacionalManager.populaCheckOrderNome(getEmpresaSistema().getId());
		gruposCheckList = CheckListBoxUtil.marcaCheckListBox(gruposCheckList, gruposCheck);

		cargosCheckList = cargoManager.populaCheckBox(gruposCheck, cargosCheck, getEmpresaSistema().getId());
		cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);

		questionario = questionarioManager.findByIdProjection(questionario.getId());

		return Action.SUCCESS;
	}
	
	public String imprimeRankingAvaliacao()
	{
		try 
		{
			colaboradores = colaboradorQuestionarioManager.findRespondidasBySolicitacao(solicitacao.getId(), colaboradorQuestionario.getAvaliacao().getId());
			Avaliacao modelo = avaliacaoManager.findById(colaboradorQuestionario.getAvaliacao().getId());
			
			String filtro = "Modelo de Avaliação: " + modelo.getTitulo();
			if (modelo.getPercentualAprovacao() != null)
				filtro += "\nPercentual para Aprovação: " + modelo.getPercentualAprovacaoFormatado();
			
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Ranking de Avaliação da Solicitação", getEmpresaSistema(), filtro);
			parametros.put("PERCENTUAL_APROVACAO", modelo.getPercentualAprovacao());
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório.");
			e.printStackTrace();
			return Action.INPUT;
		}
	}

	public String imprimirListagemCandidatoSolicitacao()
	{
		try 
		{
			candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(null, null, solicitacao.getId(), etapaSeletivaId, indicadoPor, getValueApto(visualizar), false, true, observacaoRH, nomeBusca, visualizar);
			parametros = RelatorioUtil.getParametrosRelatorio("Candidatos da Seleção", getEmpresaSistema(), "");
			
			return Action.SUCCESS;		
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório.");
			e.printStackTrace();
			return Action.INPUT;
		}
	}

	private Boolean getValueApto(char visual)
	{
		if(visual == 'A')
			return true;
		else if(visual == 'N')
			return false;

		return null;
	}
	public String listFiltro() throws Exception
	{
		double valorPercentual = (percentual == null || percentual.equals("")) ? 0 : Double.parseDouble(percentual);

		Collection<Long> areasIds = LongUtil.arrayStringToCollectionLong(areasCheck);
		Collection<Long> cargosIds = LongUtil.arrayStringToCollectionLong(cargosCheck);
		Collection<Long> estabelecimentosIds = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);

		colaboradores = colaboradorManager.getColaboradoresByEstabelecimentoAreaGrupo(filtrarPor, estabelecimentosIds, areasIds, cargosIds, colaborador.getNome(), empresaId);

		if(calcularPercentual)
		{
			if(aplicarPorParte)
				colaboradores = colaboradorQuestionarioManager.selecionaColaboradoresPorParte(colaboradores, filtrarPor, areasIds, cargosIds, qtdPercentual, valorPercentual, quantidade);
			else
				colaboradores = colaboradorQuestionarioManager.selecionaColaboradores(colaboradores, qtdPercentual, valorPercentual, quantidade);
		}

		prepareInsert();
		
		if(colaboradores.isEmpty())
			addActionMessage("Não existem dados para o filtro informado.");
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		colaboradorQuestionarioManager.save(questionario, colaboradoresId, null);
		exibirBotaoConcluir = true;
		
		return Action.SUCCESS;
	}
	
	public String visualizarRespostasAvaliacaoDesempenhoEPeriodoExperiencia()
	{
		prepareResponderAvaliacaoDesempenho();		
		return Action.SUCCESS;
	}
		
	public String prepareResponderAvaliacaoDesempenho()
	{
		colaboradorQuestionario = colaboradorQuestionarioManager.findByIdProjection(colaboradorQuestionario.getId());
		
		if (colaboradorQuestionario.getRespondidaEm() == null)
			colaboradorQuestionario.setRespondidaEm(new Date());
		
		colaborador = colaboradorManager.findByIdDadosBasicos(colaboradorQuestionario.getColaborador().getId(), StatusRetornoAC.CONFIRMADO);
		
		if (colaboradorQuestionario.getAvaliador() != null)
			avaliador = colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getAvaliador().getId());
		
		if (colaboradorQuestionario.getRespondida())
			colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
		else
			colaboradorRespostas = colaboradorQuestionarioManager.populaQuestionario(colaboradorQuestionario.getAvaliacao());
		
		montaPerguntasRespostas();
		
		if (colaboradorQuestionario.getAvaliacao().isAvaliarCompetenciasCargo())
		{
			FaixaSalarial faixaSalarial = colaborador.getFaixaSalarial();
			
			niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(faixaSalarial.getCargo().getId(), getEmpresaSistema().getId());
			nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId());
			niveisCompetenciaFaixaSalariaisSugeridos = configuracaoNivelCompetenciaManager.findByFaixa(faixaSalarial.getId());
			niveisCompetenciaFaixaSalariaisSalvos = configuracaoNivelCompetenciaManager.findByColaborador(colaborador.getId());
		}
		
		Colaborador colaboradorLogado = colaboradorManager.findByUsuario(getUsuarioLogado(), getEmpresaSistema().getId());
		exibirCompetencia = !(colaboradorLogado != null && colaboradorQuestionario.getColaborador().getId().equals(colaboradorLogado.getId())); 

		return Action.SUCCESS;
	}
	
	public String responderAvaliacaoDesempenho()
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = null;
		Date hoje = new Date();
		
		try {
			if(colaboradorQuestionario.getRespondidaEm() == null)
				colaboradorQuestionario.setRespondidaEm(new Date()); 
			
			exibeResultadoAutoavaliacao();//usado em avaliacaodesempenhoQuestionariolist.action

			colaboradorRespostaManager.update(getColaboradorRespostasDasPerguntas(), colaboradorQuestionario, getUsuarioLogado().getId());
			
			if (colaboradorQuestionario.getAvaliacao().isAvaliarCompetenciasCargo())
			{
				colaborador = colaboradorManager.findByIdDadosBasicos(colaboradorQuestionario.getColaborador().getId(), StatusRetornoAC.CONFIRMADO);
				
				configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.findByData(hoje, colaborador.getId());
				if (configuracaoNivelCompetenciaColaborador == null)
					configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
				
				configuracaoNivelCompetenciaColaborador.setData(hoje);
				configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
				configuracaoNivelCompetenciaColaborador.setFaixaSalarial(colaborador.getFaixaSalarial());
				
				configuracaoNivelCompetenciaManager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
			}
			
			addActionMessage("Avaliação respondida com sucesso.");
		
		} catch (Exception e) 
		{
			e.printStackTrace();
			addActionError(e.getMessage());
			
			prepareResponderAvaliacaoDesempenho();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}
	
	public String imprimirAvaliacaoDesempenhoRespondida()
	{
		colaboradorQuestionario = colaboradorQuestionarioManager.findByIdProjection(colaboradorQuestionario.getId());
		colaborador = colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getColaborador().getId());
		avaliador = colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getAvaliador().getId());
		
		respostaQuestionarioVOs = colaboradorRespostaManager.findRespostasAvaliacaoDesempenho(colaboradorQuestionario.getId());
		String filtro = colaboradorQuestionario.getAvaliacaoDesempenho().getTitulo();
		filtro += "\nAvaliador: " + avaliador.getNome();
		filtro += "\nAvaliado: " + colaborador.getNome();

		parametros = RelatorioUtil.getParametrosRelatorio("Avaliação de Desempenho", getEmpresaSistema(), filtro);
		parametros.put("observacoes", colaboradorQuestionario.getObservacao());
		
		if (colaboradorQuestionario.getAvaliacao() != null && colaboradorQuestionario.getAvaliacao().getCabecalho() != null)
			parametros.put("observacoes_modelo", colaboradorQuestionario.getAvaliacao().getCabecalho());
		
		return Action.SUCCESS;
	}

	private void exibeResultadoAutoavaliacao() 
	{
		this.exibeResultadoAutoavaliacao =  colaboradorQuestionario.getAvaliacaoDesempenho().isPermiteAutoAvaliacao() 
		&& colaboradorQuestionario.getColaborador().getId().equals(SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession()).getId()) 
		&& colaboradorQuestionario.getAvaliador().getId().equals(colaboradorQuestionario.getColaborador().getId()) 
		&& colaboradorQuestionario.getAvaliacao().isExibeResultadoAutoavaliacao();
	}
	
	public String prepareInsertAvaliacaoExperiencia()
	{
		ColaboradorQuestionario colaboradorQuestionarioTemp = colaboradorQuestionarioManager.findByColaboradorAvaliacao(colaboradorQuestionario.getColaborador(), colaboradorQuestionario.getAvaliacao());
		if (colaboradorQuestionarioTemp != null)
		{
			colaboradorQuestionario = colaboradorQuestionarioTemp;
			return prepareUpdateAvaliacaoExperiencia();
		}
		
		colaborador = colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getColaborador().getId());
		avaliacaoExperiencias = avaliacaoManager.find(new String[]{"ativo", "tipoModeloAvaliacao", "empresa.id"}, new Object[]{true, TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, getEmpresaSistema().getId()}, new String[]{"titulo"});
		
		colaboradorRespostas = colaboradorQuestionarioManager.populaQuestionario(colaboradorQuestionario.getAvaliacao());
		
		montaPerguntasRespostas();
		
		return Action.SUCCESS;
	}
	
	public String prepareUpdateAvaliacaoExperiencia()
	{
		colaboradorQuestionario = colaboradorQuestionarioManager.findById(colaboradorQuestionario.getId());
		
		colaborador = colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getColaborador().getId());
		avaliacaoExperiencias = avaliacaoManager.find(new String[]{"ativo", "tipoModeloAvaliacao", "empresa.id"}, new Object[]{true, TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, getEmpresaSistema().getId()}, new String[]{"titulo"});
		
		colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
		
		montaPerguntasRespostas();
		
		return Action.SUCCESS;
	}
	
	public String prepareUpdateAvaliacaoSolicitacao()
	{
		colaboradorQuestionario = colaboradorQuestionarioManager.findById(colaboradorQuestionario.getId());
		
		candidato = candidatoManager.findByCandidatoId(candidato.getId());
		colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
		
		montaPerguntasRespostas();
		
		return Action.SUCCESS;
	}
	
	public String prepareInsertAvaliacaoSolicitacao()
	{
		candidato = candidatoManager.findByCandidatoId(candidato.getId());
		colaboradorQuestionario.setAvaliacao(avaliacaoManager.findEntidadeComAtributosSimplesById(colaboradorQuestionario.getAvaliacao().getId()));
		colaboradorQuestionario.setRespondidaEm(new Date());

		colaboradorRespostas = colaboradorQuestionarioManager.populaQuestionario(colaboradorQuestionario.getAvaliacao());
		
		montaPerguntasRespostas();
		
		return Action.SUCCESS;
	}
	
	/**
	 * monta as perguntas e os respectivos ColaboradorResposta na estrutura:
	 * 	Perguntas
	 * 	 |- ColaboradorRespostas
	 *     	 |- Resposta
	 */
	private void montaPerguntasRespostas() {
		
		if (colaboradorQuestionario.getAvaliacao() != null && colaboradorQuestionario.getAvaliacao().getId() != null)
			perguntas = perguntaManager.getPerguntasRespostaByQuestionarioAgrupadosPorAspecto(colaboradorQuestionario.getAvaliacao().getId(), ordenarPorAspecto);
		else
			perguntas = new ArrayList<Pergunta>();
		
		for (Pergunta pergunta : perguntas)
		{
			// setando #AVALIADO# com nome do colaborador avaliado.
			if(colaborador != null && colaborador.getNome() != null)
				perguntaManager.setAvaliadoNaPerguntaDeAvaliacaoDesempenho(pergunta, colaborador.getNome());
			else if(candidato != null && candidato.getNome() != null)
				perguntaManager.setAvaliadoNaPerguntaDeAvaliacaoDesempenho(pergunta, candidato.getNome());
			
			Collection<Resposta> respostas = respostaManager.findByPergunta(pergunta.getId());
			pergunta.setRespostas(respostas);
			
			// agrupando os ColaboradorResposta por pergunta.
			for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
			{
				if (colaboradorResposta.getPergunta().equals(pergunta))
				{
					pergunta.addColaboradorResposta(colaboradorResposta);
					
					if (! colaboradorResposta.getPergunta().getTipo().equals(TipoPergunta.MULTIPLA_ESCOLHA))
						break;
				}
			}
		}
	}
	
	private void ajustaSolicitacao() {
		if(solicitacao == null || solicitacao.getId() == null)
			colaboradorQuestionario.setSolicitacao(null);
		else
			colaboradorQuestionario.setSolicitacao(solicitacao);
	}

	public String insertAvaliacaoExperiencia()
	{
		//TODO: Este metodo tambem insere o "colaboradorQuestionario" relacionado. O ideal seria
		//      que o "colaboradorQuestionarioManager" inserisse as respostas e nao o contrario.
		ajustaSolicitacao();
		
		Long usuarioId = (getUsuarioLogado() != null) ? getUsuarioLogado().getId() : null;
		Long candidatoId = (candidato != null) ? candidatoId = candidato.getId() : null;
		
		colaboradorRespostaManager.save(getColaboradorRespostasDasPerguntas(), colaboradorQuestionario, usuarioId, candidatoId);
		
		if (respostaColaborador)
			return "sucessoIndex";
		else{
			enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional();
			return Action.SUCCESS;
		}
	}

	private void enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional() 
	{
		Long colaboradorAvaliadoId = null;
		Long avaliadorId  = null;
		
		if(colaboradorQuestionario.getColaborador() != null && colaboradorQuestionario.getColaborador().getId()!=null)
			colaboradorAvaliadoId = colaboradorQuestionario.getColaborador().getId();
		
		if(colaboradorQuestionario.getAvaliacao() != null && colaboradorQuestionario.getAvaliacao().getId() != null)
			avaliadorId = colaboradorQuestionario.getAvaliacao().getId();
		
		if(colaboradorAvaliadoId != null && avaliadorId != null)
			gerenciadorComunicacaoManager.enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(colaboradorAvaliadoId, avaliadorId, getUsuarioLogado(), getEmpresaSistema());
	}

	public String updateAvaliacaoExperiencia()
	{
		//TODO: Este metodo tambem atualiza o "colaboradorQuestionario" relacionado. O ideal seria
		//      que o "colaboradorQuestionarioManager" atualizasse as respostas e nao o contrario. 		
		ajustaSolicitacao();
		colaboradorRespostaManager.update(getColaboradorRespostasDasPerguntas(), colaboradorQuestionario, getUsuarioLogado().getId());
		
		if (respostaColaborador)
			return "sucessoIndex";
		else
			return Action.SUCCESS;
	}
	
	private Collection<ColaboradorResposta> getColaboradorRespostasDasPerguntas() 
	{
		Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
		for (Pergunta pergunta : perguntas)
		{
			// desagrupando os colaboradorRespostas que vieram agrupados por pergunta
			if (pergunta.getColaboradorRespostas() != null)
			{
				colaboradorRespostas.addAll(pergunta.getColaboradorRespostas());
			
				setComentariosDasRespostasMultiplaEscolha(pergunta.getColaboradorRespostas());
			}
		}
		return colaboradorRespostas;
	}
	
	// O comentário de resposta multipla só vem na primeira e precisa ser replicado para todas (problema no modelo)
	private void setComentariosDasRespostasMultiplaEscolha(Collection<ColaboradorResposta> colabRespostas) {
		
		String comentario = null;
		
		if (!colabRespostas.isEmpty()) {
			
			ColaboradorResposta primeiroColabResposta = ((ColaboradorResposta)colabRespostas.toArray()[0]);
			
			if (primeiroColabResposta.getPergunta().getTipo() == TipoPergunta.MULTIPLA_ESCOLHA 
					&& primeiroColabResposta.getPergunta().isComentario())
			{
				comentario = primeiroColabResposta.getComentario();
			
				for (ColaboradorResposta colabResposta : colabRespostas)
						colabResposta.setComentario(comentario);
			}
		}
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setGrupoOcupacionalManager(GrupoOcupacionalManager grupoOcupacionalManager)
	{
		this.grupoOcupacionalManager = grupoOcupacionalManager;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public Collection<CheckBox> getCargosCheckList()
	{
		return cargosCheckList;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public Collection<CheckBox> getGruposCheckList()
	{
		return gruposCheckList;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public void setCargosCheck(String[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public char getFiltrarPor()
	{
		return filtrarPor;
	}

	public void setFiltrarPor(char filtrarPor)
	{
		this.filtrarPor = filtrarPor;
	}

	public boolean isCalcularPercentual()
	{
		return calcularPercentual;
	}

	public void setCalcularPercentual(boolean calcularPercentual)
	{
		this.calcularPercentual = calcularPercentual;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public boolean isAplicarPorParte()
	{
		return aplicarPorParte;
	}

	public void setAplicarPorParte(boolean aplicarPorParte)
	{
		this.aplicarPorParte = aplicarPorParte;
	}

	public String getPercentual()
	{
		return percentual;
	}

	public void setPercentual(String percentual)
	{
		this.percentual = percentual;
	}

	public char getQtdPercentual()
	{
		return qtdPercentual;
	}

	public void setQtdPercentual(char qtdPercentual)
	{
		this.qtdPercentual = qtdPercentual;
	}

	public int getQuantidade()
	{
		return quantidade;
	}

	public void setQuantidade(int quantidade)
	{
		this.quantidade = quantidade;
	}

	public Collection<Colaborador> getColaboradores()
	{
		return colaboradores;
	}

	public String[] getGruposCheck()
	{
		return gruposCheck;
	}

	public void setGruposCheck(String[] gruposCheck)
	{
		this.gruposCheck = gruposCheck;
	}

	public void setColaboradoresId(Long[] colaboradoresId)
	{
		this.colaboradoresId = colaboradoresId;
	}

	public TipoQuestionario getTipoQuestionario()
	{
		return tipoQuestionario;
	}

	public void setQuestionarioManager(QuestionarioManager questionarioManager)
	{
		this.questionarioManager = questionarioManager;
	}

	public boolean isExibirBotaoConcluir()
	{
		return exibirBotaoConcluir;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager)
	{
		this.avaliacaoManager = avaliacaoManager;
	}

	public Collection<Pergunta> getPerguntas()
	{
		return perguntas;
	}

	public Avaliacao getAvaliacaoExperiencia()
	{
		return avaliacaoExperiencia;
	}

	public void setAvaliacaoExperiencia(Avaliacao avaliacaoExperiencia)
	{
		this.avaliacaoExperiencia = avaliacaoExperiencia;
	}

	public TipoPergunta getTipoPergunta()
	{
		return tipoPergunta;
	}

	public ColaboradorQuestionario getColaboradorQuestionario()
	{
		return colaboradorQuestionario;
	}

	public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
	{
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public Collection<Avaliacao> getAvaliacaoExperiencias()
	{
		return avaliacaoExperiencias;
	}

	public void setColaboradorRespostaManager(ColaboradorRespostaManager colaboradorRespostaManager)
	{
		this.colaboradorRespostaManager = colaboradorRespostaManager;
	}

	public void setPerguntaManager(PerguntaManager perguntaManager) {
		this.perguntaManager = perguntaManager;
	}

	public void setRespostaManager(RespostaManager respostaManager) {
		this.respostaManager = respostaManager;
	}

	public void setPerguntas(Collection<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}

	public Colaborador getAvaliador()
	{
		return avaliador;
	}

	public void setAvaliador(Colaborador avaliador)
	{
		this.avaliador = avaliador;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager) {
		this.candidatoManager = candidatoManager;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public Boolean getOrdenarPorAspecto() {
		return ordenarPorAspecto;
	}

	public void setOrdenarPorAspecto(Boolean ordenarPorAspecto) {
		this.ordenarPorAspecto = ordenarPorAspecto;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Boolean getCompartilharColaboradores() {
		return compartilharColaboradores;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public boolean isExibeResultadoAutoavaliacao() {
		return exibeResultadoAutoavaliacao;
	}

	public void setExibeResultadoAutoavaliacao(boolean exibeResultadoAutoavaliacao) {
		this.exibeResultadoAutoavaliacao = exibeResultadoAutoavaliacao;
	}

	public boolean isRespostaColaborador() {
		return respostaColaborador;
	}

	public void setRespostaColaborador(boolean respostaColaborador) {
		this.respostaColaborador = respostaColaborador;
	}

	public boolean isModuloExterno() {
		return moduloExterno;
	}

	public void setModuloExterno(boolean moduloExterno) {
		this.moduloExterno = moduloExterno;
	}

	public Long getAnuncioId() {
		return anuncioId;
	}

	public void setAnuncioId(Long anuncioId) {
		this.anuncioId = anuncioId;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public boolean isPreview() {
		return preview;
	}

	public void setPreview(boolean preview) {
		this.preview = preview;
	}

	public Collection<RespostaQuestionarioVO> getRespostaQuestionarioVOs() {
		return respostaQuestionarioVOs;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(
			ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}

	public void setConfiguracaoNivelCompetenciaManager(
			ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public void setNivelCompetenciaManager(
			NivelCompetenciaManager nivelCompetenciaManager) {
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariais() {
		return niveisCompetenciaFaixaSalariais;
	}
	
	public void setNiveisCompetenciaFaixaSalariais(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais) {
		this.niveisCompetenciaFaixaSalariais = niveisCompetenciaFaixaSalariais;
	}

	public Collection<NivelCompetencia> getNivelCompetencias() {
		return nivelCompetencias;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisSugeridos() {
		return niveisCompetenciaFaixaSalariaisSugeridos;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisSalvos() {
		return niveisCompetenciaFaixaSalariaisSalvos;
	}

	public void setCandidatoSolicitacaoManager(
			CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public Collection<CandidatoSolicitacao> getCandidatoSolicitacaos() {
		return candidatoSolicitacaos;
	}

	public void setEtapaSeletivaId(Long etapaSeletivaId) {
		this.etapaSeletivaId = etapaSeletivaId;
	}

	public void setIndicadoPor(String indicadoPor) {
		this.indicadoPor = indicadoPor;
	}

	public void setObservacaoRH(String observacaoRH) {
		this.observacaoRH = observacaoRH;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public void setVisualizar(char visualizar) {
		this.visualizar = visualizar;
	}

	public RelatorioCandidatoSolicitacaoList getRelatorioCandidatoSolicitacaoList() {
		return relatorioCandidatoSolicitacaoList;
	}

	public void setRelatorioCandidatoSolicitacaoList(RelatorioCandidatoSolicitacaoList relatorioCandidatoSolicitacaoList) {
		this.relatorioCandidatoSolicitacaoList = relatorioCandidatoSolicitacaoList;
	}

	public boolean isExibirCompetencia() {
		return exibirCompetencia;
	}

	public void setExibirCompetencia(boolean exibirCompetencia) {
		this.exibirCompetencia = exibirCompetencia;
	}
}
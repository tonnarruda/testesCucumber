package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
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
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

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

	private Avaliacao avaliacaoExperiencia;
	private Questionario questionario;
	private Colaborador colaborador;
	private Colaborador avaliador;
	private ColaboradorQuestionario colaboradorQuestionario;
	
	private Collection<ColaboradorResposta> colaboradorRespostas;
	
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
	
	private TipoQuestionario tipoQuestionario = new TipoQuestionario();


	public String prepareInsert() throws Exception
	{
		empresas = empresaManager.findAll();
		empresaId = getEmpresaSistema().getId();
		
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

	public String listFiltro() throws Exception
	{
		double valorPercentual = (percentual == null || percentual.equals("")) ? 0 : Double.parseDouble(percentual);

		Collection<Long> areasIds = LongUtil.arrayStringToCollectionLong(areasCheck);
		Collection<Long> cargosIds = LongUtil.arrayStringToCollectionLong(cargosCheck);
		Collection<Long> estabelecimentosIds = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);

		colaboradores = colaboradorManager.getColaboradoresByEstabelecimentoAreaGrupo(filtrarPor, estabelecimentosIds, areasIds, cargosIds);

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
	
	public String prepareResponderAvaliacaoDesempenho()
	{
		colaboradorQuestionario = colaboradorQuestionarioManager.findByIdProjection(colaboradorQuestionario.getId());
		
		if (colaboradorQuestionario.getRespondidaEm() == null)
			colaboradorQuestionario.setRespondidaEm(new Date());
		
		colaborador = colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getColaborador().getId());
		avaliador = colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getAvaliador().getId());
		
		if (colaboradorQuestionario.getRespondida())
			colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
		else
			colaboradorRespostas = colaboradorQuestionarioManager.populaQuestionario(colaboradorQuestionario.getAvaliacao());
		
		montaPerguntasRespostas();
		
		return Action.SUCCESS;
	}
	
	public String responderAvaliacaoDesempenho()
	{
		colaboradorRespostaManager.update(getColaboradorRespostasDasPerguntas(), colaboradorQuestionario);
		addActionMessage("Avaliação respondida com sucesso.");
		
		return Action.SUCCESS;
	}
	
	public String prepareInsertAvaliacaoExperiencia()
	{
		colaborador = colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getColaborador().getId());
		avaliacaoExperiencias = avaliacaoManager.find(new String[]{"ativo"}, new Object[]{true}, new String[]{"titulo"});
		
		colaboradorRespostas = colaboradorQuestionarioManager.populaQuestionario(colaboradorQuestionario.getAvaliacao());
		
		montaPerguntasRespostas();
		
		return Action.SUCCESS;
	}
	
	public String prepareUpdateAvaliacaoExperiencia()
	{
		colaboradorQuestionario = colaboradorQuestionarioManager.findById(colaboradorQuestionario.getId());
		
		colaborador = colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getColaborador().getId());
		avaliacaoExperiencias = avaliacaoManager.find(new String[]{"ativo"}, new Object[]{true}, new String[]{"titulo"});
		
		colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
		
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
			perguntas = perguntaManager.getPerguntasRespostaByQuestionario(colaboradorQuestionario.getAvaliacao().getId());
		else
			perguntas = new ArrayList<Pergunta>();
		
		for (Pergunta pergunta : perguntas)
		{
			// setando #AVALIADO# com nome do colaborador avaliado.
			perguntaManager.setAvaliadoNaPerguntaDeAvaliacaoDesempenho(pergunta, colaborador.getNome());
			
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

	public String insertAvaliacaoExperiencia()
	{
		//TODO: Este metodo tambem insere o "colaboradorQuestionario" relacionado. O ideal seria
		//      que o "colaboradorQuestionarioManager" inserisse as respostas e nao o contrario. 
		colaboradorRespostaManager.save(getColaboradorRespostasDasPerguntas(), colaboradorQuestionario);
		
		return Action.SUCCESS;
	}

	public String updateAvaliacaoExperiencia()
	{
		//TODO: Este metodo tambem atualiza o "colaboradorQuestionario" relacionado. O ideal seria
		//      que o "colaboradorQuestionarioManager" atualizasse as respostas e nao o contrario. 		
		colaboradorRespostaManager.update(getColaboradorRespostasDasPerguntas(), colaboradorQuestionario);
		
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
}
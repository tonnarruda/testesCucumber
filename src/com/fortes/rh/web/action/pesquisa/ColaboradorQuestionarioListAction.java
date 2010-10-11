package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.relatorio.PerguntaFichaMedica;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ColaboradorQuestionarioListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private ColaboradorManager colaboradorManager;
	private QuestionarioManager questionarioManager;

	private Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
	private Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
	private Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();

	private ColaboradorQuestionario colaboradorQuestionario;
	private Colaborador colaborador;

	private Questionario questionario;

	private Long pesquisaId;
	private Long colaboradorId;
	private Long questionarioId;

	private String urlVoltar;
	private String nomeComercialEntreParentese = "";
	private boolean exibirBotaoConcluir;

	private TipoPergunta tipoPergunta = new TipoPergunta();
	private TipoQuestionario tipoQuestionario = new TipoQuestionario();
	private String colaboradorNome;
	
	private Map<String, Object> parametros;
	
	private AvaliacaoDesempenho avaliacaoDesempenho;
	private Collection<AvaliacaoDesempenho> avaliacaoDesempenhos;
	private Collection<PerguntaFichaMedica> perguntasRespondidas = new ArrayList<PerguntaFichaMedica>();
	
	public String list() throws Exception
	{
		questionario = questionarioManager.findByIdProjection(questionario.getId());
		colaboradorQuestionarios = colaboradorQuestionarioManager.findByQuestionario(questionario.getId());

		urlVoltar = TipoQuestionario.getUrlVoltarList(questionario.getTipo(), null);

		return Action.SUCCESS;
	}

	public String imprimirColaboradores() throws Exception
	{
		questionario = questionarioManager.findByIdProjection(questionario.getId());
		colaboradorQuestionarios = colaboradorQuestionarioManager.findColaboradorHistoricoByQuestionario(questionario.getId());

		if(colaboradorQuestionarios.isEmpty())
			return Action.INPUT;

		parametros = RelatorioUtil.getParametrosRelatorio("Colaboradores da " + TipoQuestionario.getDescricao(questionario.getTipo()), getEmpresaSistema(), questionario.getTitulo());
		
		return Action.SUCCESS;
	}

	public String periodoExperienciaQuestionarioList() throws Exception
	{
		Long colaboradorLogadoId = colaboradorManager.verificaColaboradorLogadoVerAreas();
		Long empresaId = getEmpresaSistema().getId();
		
		if(colaborador != null)
		{
			colaboradors = colaboradorManager.findByNomeCpfMatriculaAndResponsavelArea(colaborador, empresaId, colaboradorLogadoId);
			
			if(colaborador.getId() != null)
			{
				colaborador = colaboradorManager.findByIdHistoricoAtual(colaborador.getId());
				colaboradorManager.setFamiliaAreas(Arrays.asList(colaborador), empresaId);
				
				if(!colaborador.getNome().equals(colaborador.getNomeComercial()))
					nomeComercialEntreParentese = " (" + colaborador.getNomeComercial() + ")";
				
				colaboradorQuestionarios = colaboradorQuestionarioManager.findAvaliacaoExperienciaByColaborador(colaborador.getId());				
			}
		}
		
		return Action.SUCCESS;
	}
	
	public String delete() throws Exception
	{
		//TODO: Este metodo tambem remove o "colaboradorQuestionario" relacionado. O ideal seria
		//      que o "colaboradorQuestionarioManager" removesse as respostas e nao o contrario. 
		colaboradorRespostaManager.removeFicha(colaboradorQuestionario.getId());

		return Action.SUCCESS;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios()
	{
		return colaboradorQuestionarios;
	}

	public String visualizarRespostasPorColaborador()
	{
		colaborador = colaboradorManager.findByIdProjectionUsuario(colaboradorId);
		questionario = questionarioManager.findByIdProjection(questionario.getId());
		colaboradorQuestionario = colaboradorQuestionarioManager.findByQuestionario(questionario.getId(), colaborador.getId(), null);
		colaboradorRespostas = colaboradorRespostaManager.findRespostasColaborador(colaboradorQuestionario.getId(), questionario.isAplicarPorAspecto());
			
		return Action.SUCCESS;
	}
	
	public String imprimirAvaliacaoRespondida()
	{
		try
		{
			parametros = new HashMap<String, Object>();//tem que ser aqui
			perguntasRespondidas = questionarioManager.montaImpressaoAvaliacaoRespondida(colaboradorQuestionario.getId(), parametros);
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Action.INPUT;
		}
	}

	public ColaboradorQuestionario getColaboradorQuestionario()
	{
		if (colaboradorQuestionario == null)
		{
			colaboradorQuestionario = new ColaboradorQuestionario();
		}
		return colaboradorQuestionario;
	}

	public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
	{
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public Long getPesquisaId()
	{
		return pesquisaId;
	}

	public void setPesquisaId(Long pesquisaId)
	{
		this.pesquisaId = pesquisaId;
	}

	public void setColaboradorRespostaManager(ColaboradorRespostaManager colaboradorRespostaManager)
	{
		this.colaboradorRespostaManager = colaboradorRespostaManager;
	}

	public Collection<ColaboradorResposta> getColaboradorRespostas()
	{
		return colaboradorRespostas;
	}

	public Long getColaboradorId()
	{
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId)
	{
		this.colaboradorId = colaboradorId;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public TipoPergunta getTipoPergunta()
	{
		return tipoPergunta;
	}

	public ColaboradorManager getColaboradorManager()
	{
		return colaboradorManager;
	}
	
	public void setQuestionarioManager(QuestionarioManager questionarioManager)
	{
		this.questionarioManager = questionarioManager;
	}

	public String getUrlVoltar()
	{
		return urlVoltar;
	}

	public void setUrlVoltar(String urlVoltar)
	{
		this.urlVoltar = urlVoltar;
	}

	public Long getQuestionarioId()
	{
		return questionarioId;
	}

	public void setQuestionarioId(Long questionarioId)
	{
		this.questionarioId = questionarioId;
	}

	public TipoQuestionario getTipoQuestionario()
	{
		return tipoQuestionario;
	}

	public boolean isExibirBotaoConcluir()
	{
		return exibirBotaoConcluir;
	}

	public void setExibirBotaoConcluir(boolean exibirBotaoConcluir)
	{
		this.exibirBotaoConcluir = exibirBotaoConcluir;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public String getColaboradorNome()
	{
		return colaboradorNome;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}
	public String getNomeComercialEntreParentese() {
		return nomeComercialEntreParentese;
	}

	public void setNomeComercialEntreParentese(String nomeComercialEntreParentese) {
		this.nomeComercialEntreParentese = nomeComercialEntreParentese;
	}

	public AvaliacaoDesempenho getAvaliacaoDesempenho() {
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}

	public Collection<AvaliacaoDesempenho> getAvaliacaoDesempenhos() {
		return avaliacaoDesempenhos;
	}

	public Collection<PerguntaFichaMedica> getPerguntasRespondidas()
	{
		return perguntasRespondidas;
	}

}

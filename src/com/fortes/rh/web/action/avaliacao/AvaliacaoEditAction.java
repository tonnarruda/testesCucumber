package com.fortes.rh.web.action.avaliacao;


import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class AvaliacaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private AvaliacaoManager avaliacaoManager;
	private PerguntaManager perguntaManager;
	
	private Avaliacao avaliacao;
	
	private Collection<Avaliacao> avaliacaos;
	private Collection<Pergunta> perguntas;
	
	private String urlVoltar;
	private TipoPergunta tipoPergunta = new TipoPergunta();
	private boolean preview = true;
	
	private Map<String, Object> parametros;
	private Collection<QuestionarioRelatorio> dataSource;
	private boolean imprimirFormaEconomica = false;
	
	private void prepare() throws Exception
	{
		if(avaliacao != null && avaliacao.getId() != null)
			avaliacao = (Avaliacao) avaliacaoManager.findById(avaliacao.getId());
	}
	
	public String clonar() throws Exception
	{
		avaliacaoManager.clonar(avaliacao.getId());
		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		avaliacao = new Avaliacao();
		avaliacao.setAtivo(true);
		
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}


	public String insert() throws Exception
	{
		avaliacao.setEmpresa(getEmpresaSistema());
		avaliacaoManager.save(avaliacao);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		avaliacaoManager.update(avaliacao);
		return Action.SUCCESS;
	}

	public String visualizar() throws Exception
	{
		avaliacao = avaliacaoManager.findById(avaliacao.getId());
		perguntas = perguntaManager.getPerguntasRespostaByQuestionario(avaliacao.getId());
    	urlVoltar = "list.action";
    	
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		avaliacaos = avaliacaoManager.findAllSelect(getEmpresaSistema().getId(), null);
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		avaliacaoManager.remove(avaliacao.getId());
		addActionMessage("Modelo de Avaliação excluído com sucesso.");

		return Action.SUCCESS;
	}
	
	public String imprimir()
    {
    	avaliacao = avaliacaoManager.findById(avaliacao.getId());
   	   	dataSource = avaliacaoManager.getQuestionarioRelatorio(avaliacao);

   	   	String titulo = "Avaliação";
   	   	String filtro = avaliacao.getTitulo();
   	   	
    	parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), filtro);
    	parametros.put("FORMA_ECONOMICA", imprimirFormaEconomica );
    	
    	return Action.SUCCESS;
    }
	
	public Avaliacao getAvaliacao()
	{
		if(avaliacao == null)
			avaliacao = new Avaliacao();
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao)
	{
		this.avaliacao = avaliacao;
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager)
	{
		this.avaliacaoManager = avaliacaoManager;
	}
	
	public Collection<Avaliacao> getAvaliacaos()
	{
		return avaliacaos;
	}

	public Collection<Pergunta> getPerguntas()
	{
		return perguntas;
	}

	public String getUrlVoltar()
	{
		return urlVoltar;
	}

	public void setPerguntaManager(PerguntaManager perguntaManager)
	{
		this.perguntaManager = perguntaManager;
	}

	public TipoPergunta getTipoPergunta()
	{
		return tipoPergunta;
	}

	public boolean isPreview()
	{
		return preview;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public Collection<QuestionarioRelatorio> getDataSource() {
		return dataSource;
	}

	public void setImprimirFormaEconomica(boolean imprimirFormaEconomica)
	{
		this.imprimirFormaEconomica = imprimirFormaEconomica;
	}
}
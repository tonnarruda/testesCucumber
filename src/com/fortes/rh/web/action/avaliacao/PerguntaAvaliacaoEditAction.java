package com.fortes.rh.web.action.avaliacao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.util.IntegerUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class PerguntaAvaliacaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired
	private PerguntaManager perguntaManager;
	@Autowired
	private AspectoManager aspectoManager;
	@Autowired
	private RespostaManager respostaManager;
	@Autowired
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	@Autowired
	private ColaboradorRespostaManager colaboradorRespostaManager;
	
	private Avaliacao avaliacao;
	
	private Pergunta pergunta;
	private Collection<Pergunta> perguntas;
	private Collection<Resposta> respostas = new ArrayList<Resposta>();
	private Collection<Resposta> respostasSugeridas = new ArrayList<Resposta>();
	
	private String aspectos;
	private String[] respostaObjetiva;
	private String[] respostaObjetivaSugerida;
	private String[] pesoRespostaObjetiva;
	private String[] pesoRespostaMultipla;
	private String[] multiplaResposta;
	
	private Map tipoPerguntas;
	private Integer[] ordens;
	
	private Integer respostaSugerida = 0;
	
	private String aspectosFormatados;
	
	private boolean temCriterioRespondido;
	private char modeloAvaliacao = 'D';
	private TipoModeloAvaliacao tipoModeloAvaliacao = new TipoModeloAvaliacao();
	
	private void prepare() throws Exception
	{
		respostasSugeridas = respostaManager.findRespostasSugeridas(avaliacao.getId());
		aspectos = aspectoManager.getAspectosByAvaliacao(avaliacao.getId());
		tipoPerguntas = new TipoPergunta();
		aspectosFormatados = aspectoManager.getAspectosFormatadosByAvaliacao(avaliacao.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		
		Integer ateOrdem = perguntaManager.getUltimaOrdenacao(avaliacao.getId()) + 1;
		pergunta = new Pergunta(1, ateOrdem, "Justifique sua resposta:");

		montaOrdem(ateOrdem);
		
		return Action.SUCCESS;
	}
	
	public String prepareUpdate() throws Exception
	{
		prepare();
		
		setTemCriterioRespondido();
		
		pergunta = (Pergunta) perguntaManager.findPergunta(pergunta.getId());
		
		if (StringUtils.isBlank(pergunta.getTextoComentario()))
			pergunta.setTextoComentario("Justifique sua resposta:");
		
		Integer ateOrdem = perguntaManager.getUltimaOrdenacao(avaliacao.getId());
		montaOrdem(ateOrdem);
		
		return Action.SUCCESS;
	}

	private void montaOrdem(Integer ateOrdem) {
		ordens = new Integer[ateOrdem];
		for (int i = 0; i < ateOrdem; i++) {ordens[i]=i+1;}
	}
	
	public String insert() throws Exception
	{
		try
		{
			if(respostaSugerida.equals(1))
				respostaObjetiva = (String[]) ArrayUtils.addAll(respostaObjetivaSugerida, respostaObjetiva);

			setMultiplaEscolha();
			
			pergunta.setAvaliacao(avaliacao);
			perguntaManager.salvarPergunta(pergunta, respostaObjetiva, IntegerUtil.arrayStringToArrayInteger(pesoRespostaObjetiva), pergunta.getOrdem());
			respostaSugerida = 0;
			
			pergunta = null;
			prepareInsert();
			
			addActionSuccess("Pergunta gravada com sucesso.");
			
		} catch (Exception e)
		{
			e.printStackTrace();
			prepareInsert();
			addActionError(e.getMessage());
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	private void setMultiplaEscolha() {
		if(pergunta.getTipo() != null && pergunta.getTipo().equals(TipoPergunta.MULTIPLA_ESCOLHA))
		{
			respostaObjetiva = multiplaResposta;
			pesoRespostaObjetiva = pesoRespostaMultipla;
		}
	}

	public String update() throws Exception
	{
		try
		{
			setMultiplaEscolha();
			
			pergunta.setAvaliacao(avaliacao);
			perguntaManager.atualizarPergunta(pergunta, respostaObjetiva, IntegerUtil.arrayStringToArrayInteger(pesoRespostaObjetiva));
			addActionSuccess("Pergunta atualizada com sucesso!");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			prepareUpdate();
			addActionError(e.getMessage());
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}
	
	private void setTemCriterioRespondido()
	{
		temCriterioRespondido = avaliacao != null && avaliacao.getId() != null && colaboradorRespostaManager.countColaboradorAvaliacaoRespondida(avaliacao.getId()) > 0; 
		
		if (temCriterioRespondido)
			addActionMessage("Esta avaliação já possui perguntas respondidas. Só é possível visualizá-las.");
	}

	public String list() throws Exception
	{
		perguntas = perguntaManager.findByQuestionario(avaliacao.getId());
		setTemCriterioRespondido();
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		perguntaManager.removerPergunta(pergunta);
		addActionSuccess("Pergunta excluída com sucesso.");

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

	public Collection<Pergunta> getPerguntas() {
		return perguntas;
	}

	public Pergunta getPergunta() {
		if (pergunta == null)
			pergunta = new Pergunta();
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public Collection<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(Collection<Resposta> respostas) {
		this.respostas = respostas;
	}

	public Collection<Resposta> getRespostasSugeridas() {
		return respostasSugeridas;
	}

	public Map getTipoPerguntas() {
		return tipoPerguntas;
	}

	public String[] getRespostaObjetiva() {
		return respostaObjetiva;
	}

	public void setRespostaObjetiva(String[] respostaObjetiva) {
		this.respostaObjetiva = respostaObjetiva;
	}

	public String getAspectos()
	{
		return aspectos;
	}

	public void setPesoRespostaObjetiva(String[] pesoRespostaObjetiva)
	{
		this.pesoRespostaObjetiva = pesoRespostaObjetiva;
	}
	
	public Integer[] getOrdens()
	{
		return ordens;
	}

	public void setOrdens(Integer[] ordens)
	{
		this.ordens = ordens;
	}

	public void setRespostaObjetivaSugerida(String[] respostaObjetivaSugerida) {
		this.respostaObjetivaSugerida = respostaObjetivaSugerida;
	}

	public void setRespostaSugerida(Integer respostaSugerida) {
		this.respostaSugerida = respostaSugerida;
	}

	public Integer getRespostaSugerida() {
		return respostaSugerida;
	}

	public void setPesoRespostaMultipla(String[] pesoRespostaMultipla) {
		this.pesoRespostaMultipla = pesoRespostaMultipla;
	}

	public String getAspectosFormatados() {
		return aspectosFormatados;
	}

	public String[] getMultiplaResposta() {
		return multiplaResposta;
	}

	public void setMultiplaResposta(String[] multiplaResposta) {
		this.multiplaResposta = multiplaResposta;
	}

	public boolean getTemCriterioRespondido() {
		return temCriterioRespondido;
	}

	public char getModeloAvaliacao() {
		return modeloAvaliacao;
	}

	public void setModeloAvaliacao(char modeloAvaliacao) {
		this.modeloAvaliacao = modeloAvaliacao;
	}

	public TipoModeloAvaliacao getTipoModeloAvaliacao() {
		return tipoModeloAvaliacao;
	}
}
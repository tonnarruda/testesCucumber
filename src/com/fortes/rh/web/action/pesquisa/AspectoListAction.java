package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class AspectoListAction extends MyActionSupportList
{
	@Autowired private AspectoManager aspectoManager;
	@Autowired private QuestionarioManager questionarioManager;

	private Collection<Aspecto> aspectos = new ArrayList<Aspecto>();

	private Aspecto aspecto;
	private Pesquisa pesquisa;
	private Questionario questionario;
	private Avaliacao avaliacao;
	private Pergunta pergunta;

	private String msg = "";
	private String msgErr = "";
	private String urlVoltar;
	
	private TipoQuestionario tipoQuestionario = new TipoQuestionario();
	private TipoModeloAvaliacao tipoModeloAvaliacao = new TipoModeloAvaliacao();
	private char modeloAvaliacao = 'D'; 

	private void doDelete()
	{
		try
		{
			aspectoManager.remove(aspecto.getId());
			//usei uma outra variavel e não setactionmsg pq estava ficando com a msg duplicada
			msg = "Aspecto excluído com sucesso.";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			msgErr = "Não foi possível excluir o aspecto, pois existem perguntas relacionadas a ele.";
		}
	}
	
	public String delete() throws Exception
	{
		doDelete();
		return list();
	}

	public String deleteAvaliacao() throws Exception
	{
		doDelete();
		return listAvaliacao();
	}

	public String list() throws Exception
	{
		aspectos = aspectoManager.findByQuestionario(questionario.getId());

		if(!msg.equals(""))
			addActionSuccess(msg);
		if(!msgErr.equals(""))
			addActionWarning(msgErr);

		questionario = questionarioManager.findByIdProjection(questionario.getId());
		//monta a url do botão voltar pelo questionario
		urlVoltar = TipoQuestionario.getUrlVoltarList(questionario.getTipo(), null);
		
		return Action.SUCCESS;
	}

	//TODO refatorar msg
	public String listAvaliacao() throws Exception
	{
		aspectos = aspectoManager.findByQuestionario(avaliacao.getId());
		
		if(!msg.equals(""))
			addActionSuccess(msg);
		if(!msgErr.equals(""))
			addActionWarning(msgErr);
		
		return Action.SUCCESS;
	}
	
	public Aspecto getAspecto()
	{
		return aspecto;
	}

	public void setAspecto(Aspecto aspecto)
	{
		this.aspecto = aspecto;
	}

	public Collection<Aspecto> getAspectos()
	{
		return aspectos;
	}

	public Pesquisa getPesquisa()
	{
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa)
	{
		this.pesquisa = pesquisa;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public Pergunta getPergunta()
	{
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta)
	{
		this.pergunta = pergunta;
	}

	public String getUrlVoltar()
	{
		return urlVoltar;
	}

	public TipoQuestionario getTipoQuestionario()
	{
		return tipoQuestionario;
	}

	public Avaliacao getAvaliacao()
	{
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao)
	{
		this.avaliacao = avaliacao;
	}

	public TipoModeloAvaliacao getTipoModeloAvaliacao() {
		return tipoModeloAvaliacao;
	}

	public char getModeloAvaliacao() {
		return modeloAvaliacao;
	}

	public void setModeloAvaliacao(char modeloAvaliacao) {
		this.modeloAvaliacao = modeloAvaliacao;
	}
}
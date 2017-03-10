package com.fortes.rh.web.action.pesquisa;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class AspectoEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private AspectoManager aspectoManager;

	private Aspecto aspecto = new Aspecto();
	private Questionario questionario = new Questionario();
	private Avaliacao avaliacao;
	private Pesquisa pesquisa = new Pesquisa();
	private Pergunta pergunta = new Pergunta();
	private char modeloAvaliacao = 'D'; 
	private TipoModeloAvaliacao tipoModeloAvaliacao = new TipoModeloAvaliacao();
	
	private void prepare() throws Exception
	{
		if(aspecto != null && aspecto.getId() != null)
			aspecto = (Aspecto) aspectoManager.findByIdProjection(aspecto.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		aspecto.setQuestionario(questionario);
		aspecto.setAvaliacao(null);
		aspectoManager.save(aspecto);

		return Action.SUCCESS;
	}
	
	public String insertAvaliacao() throws Exception
	{
		aspecto.setAvaliacao(avaliacao);
		aspecto.setQuestionario(null);
		aspectoManager.save(aspecto);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		aspecto.setAvaliacao(null);
		return doUpdate();
	}
	
	public String updateAvaliacao() throws Exception
	{
		aspecto.setQuestionario(null);
		return doUpdate();
	}

	private String doUpdate()
	{
		try
		{
			aspectoManager.update(aspecto);
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			setActionErr("Não foi possível editar este Tema");
			return Action.INPUT;
		}
	}

	public Object getModel()
	{
		return getAspecto();
	}

	public Aspecto getAspecto()
	{
		if(aspecto == null)
			aspecto = new Aspecto();
		return aspecto;
	}

	public void setAspecto(Aspecto aspecto)
	{
		this.aspecto = aspecto;
	}

	public Pergunta getPergunta()
	{
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta)
	{
		this.pergunta = pergunta;
	}

	public Pesquisa getPesquisa()
	{
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa)
	{
		this.pesquisa = pesquisa;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public Avaliacao getAvaliacao()
	{
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao)
	{
		this.avaliacao = avaliacao;
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
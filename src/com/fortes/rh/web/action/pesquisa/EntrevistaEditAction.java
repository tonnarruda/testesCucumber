package com.fortes.rh.web.action.pesquisa;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.EntrevistaManager;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EntrevistaEditAction extends MyActionSupportEdit
{
	@Autowired private EntrevistaManager entrevistaManager;

	private Entrevista entrevista;
	private Questionario questionario;

	private int quantidadeDeResposta;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(entrevista != null && entrevista.getId() != null)
		{
			entrevista = (Entrevista) entrevistaManager.findByIdProjection(entrevista.getId());
		}
	}

	public String prepareInsert() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		try
		{
			entrevistaManager.verificarEmpresaValida(entrevista.getId(), getEmpresaSistema().getId());
			prepare();
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
			return Action.INPUT;
		}
	}

	public String insert()
	{
		try
		{
			entrevistaManager.save(entrevista, entrevista.getQuestionario(), getEmpresaSistema());
			questionario = entrevista.getQuestionario();
		}
		catch (Exception e)
		{
			setActionErr("Não foi possível inserir esta Entrevista.");
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try
		{
			entrevistaManager.update(entrevista, entrevista.getQuestionario(), getEmpresaSistema().getId());
			questionario = entrevista.getQuestionario();

			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
			return Action.INPUT;
		}
	}

	/**
	 * Diferencia-se do update por voltar para a listagem de entrevistas.
	 */
	public String gravar() throws Exception
	{
		return update();
	}

	public Object getModel()
	{
		return getEntrevista();
	}

	public Entrevista getEntrevista()
	{
		if(entrevista == null)
			entrevista = new Entrevista();

		return entrevista;
	}

	public void setEntrevista(Entrevista entrevista)
	{
		this.entrevista = entrevista;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public int getQuantidadeDeResposta()
	{
		return quantidadeDeResposta;
	}

	public void setQuantidadeDeResposta(int quantidadeDeResposta)
	{
		this.quantidadeDeResposta = quantidadeDeResposta;
	}
}
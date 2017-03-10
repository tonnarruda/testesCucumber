package com.fortes.rh.web.action.pesquisa;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class AvaliacaoTurmaEditAction extends MyActionSupportEdit
{
	@Autowired private AvaliacaoTurmaManager avaliacaoTurmaManager;

	private AvaliacaoTurma avaliacaoTurma;
	private Questionario questionario;

	private int quantidadeDeResposta;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(avaliacaoTurma != null && avaliacaoTurma.getId() != null)
		{
			avaliacaoTurma = (AvaliacaoTurma) avaliacaoTurmaManager.findByIdProjection(avaliacaoTurma.getId());
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
		//	avaliacaoTurmaManager.verificarEmpresaValida(avaliacaoTurma.getId(), getEmpresaSistema().getId());
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
			avaliacaoTurmaManager.save(avaliacaoTurma, avaliacaoTurma.getQuestionario(), getEmpresaSistema());
			questionario = avaliacaoTurma.getQuestionario();
		}
		catch (Exception e)
		{
			setActionErr("Não foi possível inserir esta Avaliação de Curso.");
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try
		{
			avaliacaoTurmaManager.update(avaliacaoTurma, avaliacaoTurma.getQuestionario(), getEmpresaSistema().getId());
			questionario = avaliacaoTurma.getQuestionario();

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
		return getAvaliacaoTurma();
	}

	public AvaliacaoTurma getAvaliacaoTurma()
	{
		if (this.avaliacaoTurma == null)
			this.avaliacaoTurma = new AvaliacaoTurma();
		return avaliacaoTurma;
	}
	public void setAvaliacaoTurma(AvaliacaoTurma avaliacaoTurma)
	{
		this.avaliacaoTurma = avaliacaoTurma;
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
package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;


@SuppressWarnings("serial")
public class AvaliacaoTurmaListAction extends  MyActionSupportList
{
	private AvaliacaoTurmaManager avaliacaoTurmaManager;
	private ColaboradorQuestionario colaboradorQuestionario;

	private Collection<AvaliacaoTurma> avaliacaoTurmas = new ArrayList<AvaliacaoTurma>(0);

	private AvaliacaoTurma avaliacaoTurma;
	private Colaborador colaborador;

	private String voltarPara = "";

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
   		this.setTotalSize(avaliacaoTurmaManager.getCount(getEmpresaSistema().getId()));
   		avaliacaoTurmas = avaliacaoTurmaManager.findToListByEmpresa(getEmpresaSistema().getId(), getPage(), getPagingSize());

        return Action.SUCCESS;
	}

	public String prepareResponderAvaliacaoTurma() throws Exception
	{
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		avaliacaoTurmaManager.delete(avaliacaoTurma.getId(), getEmpresaSistema().getId());
		setActionMsg("Modelo de Avaliação de Curso excluído com sucesso.");

		return Action.SUCCESS;
	}

    public String clonarAvaliacaoTurma() throws Exception
    {
    	try
		{
    		avaliacaoTurma = avaliacaoTurmaManager.clonarAvaliacaoTurma(avaliacaoTurma.getId());
    		return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Não foi possível clonar o modelo de avaliação, devido a um erro interno.<br><br> Entre em contato com o administrador do sistema.");
			list();
			return Action.INPUT;
		}
    }

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
	{
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public ColaboradorQuestionario getColaboradorQuestionario()
	{
		return colaboradorQuestionario;
	}

	public String getVoltarPara()
	{
		return voltarPara;
	}

	public void setVoltarPara(String voltarPara)
	{
		this.voltarPara = voltarPara;
	}

	public Collection<AvaliacaoTurma> getAvaliacaoTurmas()
	{
		return avaliacaoTurmas;
	}

	public void setAvaliacaoTurmas(Collection<AvaliacaoTurma> avaliacaoTurmas)
	{
		this.avaliacaoTurmas = avaliacaoTurmas;
	}

	public AvaliacaoTurma getAvaliacaoTurma()
	{
		return avaliacaoTurma;
	}

	public void setAvaliacaoTurma(AvaliacaoTurma avaliacaoTurma)
	{
		this.avaliacaoTurma = avaliacaoTurma;
	}

	public void setAvaliacaoTurmaManager(AvaliacaoTurmaManager avaliacaoTurmaManager)
	{
		this.avaliacaoTurmaManager = avaliacaoTurmaManager;
	}
}
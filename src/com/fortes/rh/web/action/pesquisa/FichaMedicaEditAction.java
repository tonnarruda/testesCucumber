package com.fortes.rh.web.action.pesquisa;


import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.FichaMedicaManager;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class FichaMedicaEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	@Autowired private FichaMedicaManager fichaMedicaManager;

	private FichaMedica fichaMedica;
	private Questionario questionario;

	private int quantidadeDeResposta;

	private void prepare() throws Exception
	{
		if(fichaMedica != null && fichaMedica.getId() != null)
		{
			fichaMedica = fichaMedicaManager.findByIdProjection(fichaMedica.getId());
		}
	}

	public String prepareInsert() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		//	fichaMedicaManager.verificarEmpresaValida(fichaMedica.getId(), getEmpresaSistema().getId());
		prepare();
		return Action.SUCCESS;
	}

	public String insert()
	{
		try
		{
			fichaMedicaManager.save(fichaMedica, fichaMedica.getQuestionario(), getEmpresaSistema());
			questionario = fichaMedica.getQuestionario();
		}
		catch (Exception e)
		{
			setActionErr("Não foi possível inserir esta Ficha Médica.");
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try
		{
			fichaMedicaManager.update(fichaMedica, fichaMedica.getQuestionario(), getEmpresaSistema().getId());
			questionario = fichaMedica.getQuestionario();

			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
			return Action.INPUT;
		}
	}

	/**
	 * Diferencia-se do update por voltar para a listagem de fichas médicas.
	 */
	public String gravar() throws Exception
	{
		return update();
	}

	public FichaMedica getFichaMedica()
	{
		if (this.fichaMedica == null)
			this.fichaMedica = new FichaMedica();
		return fichaMedica;
	}
	public void setFichaMedica(FichaMedica fichaMedica)
	{
		this.fichaMedica = fichaMedica;
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
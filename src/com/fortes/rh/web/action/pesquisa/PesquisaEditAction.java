package com.fortes.rh.web.action.pesquisa;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

public class PesquisaEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;

	@Autowired private PesquisaManager pesquisaManager;
	@Autowired private ColaboradorQuestionarioManager colaboradorQuestionarioManager;

	private Pesquisa pesquisa;
	private Questionario questionario;
	
	private boolean temResposta;


	private void prepare() throws Exception
	{
		if(pesquisa != null && pesquisa.getId() != null)
		{
			pesquisa = (Pesquisa) pesquisaManager.findByIdProjection(pesquisa.getId());
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
			pesquisaManager.verificarEmpresaValida(pesquisa.getId(), getEmpresaSistema().getId());
			prepare();
			
			Collection<ColaboradorQuestionario> colabQuestionarios = colaboradorQuestionarioManager.findByQuestionario(pesquisa.getQuestionario().getId());
			temResposta = verificaTemResposta(colabQuestionarios);
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
			return Action.INPUT;
		}
	}

	private boolean verificaTemResposta(Collection<ColaboradorQuestionario> colabQuestionarios)
	{
		for (ColaboradorQuestionario colaboradorQuestionario : colabQuestionarios)
		{
			if (colaboradorQuestionario.getRespondida())
				return true;
		}
		
		return false;
	}

	public String insert()
	{
		try
		{
			pesquisa.getQuestionario().setCabecalho(StringUtil.removeAspas(pesquisa.getQuestionario().getCabecalho()));
			pesquisaManager.save(pesquisa, pesquisa.getQuestionario(), getEmpresaSistema());
			questionario = pesquisa.getQuestionario();
		}
		catch (Exception e)
		{
			setActionErr("Não foi possível inserir esta Pesquisa.");
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try
		{
			pesquisa.getQuestionario().setCabecalho(StringUtil.removeAspas(pesquisa.getQuestionario().getCabecalho()));
			pesquisaManager.update(pesquisa, pesquisa.getQuestionario(), getEmpresaSistema().getId());
			questionario = pesquisa.getQuestionario();
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
			return Action.INPUT;
		}
	}

	/**
	 * Diferencia-se do update por voltar para a listagem de pesquisas.
	 */
	public String gravar() throws Exception
	{
		return update();
	}

	public Object getModel()
	{
		return getPesquisa();
	}

	public Pesquisa getPesquisa()
	{
		if(pesquisa == null)
			pesquisa = new Pesquisa();

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

	public boolean isTemResposta() {
		return temResposta;
	}
}
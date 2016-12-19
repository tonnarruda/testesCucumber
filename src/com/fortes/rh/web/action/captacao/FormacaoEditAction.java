package com.fortes.rh.web.action.captacao;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.dicionario.SituacaoFormacao;
import com.fortes.rh.model.dicionario.TipoFormacao;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial", "unchecked"})
public class FormacaoEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private AreaFormacaoManager areaFormacaoManager;
	private Collection<AreaFormacao> areaFormacaos;
	private Formacao formacao;
	private Map tiposFormacao;
	private Map situacoesFormacao;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare()
	{
		HttpServletResponse response = ServletActionContext.getResponse();

        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache, no-store");
        response.addHeader("Content-Transfer-Encoding", "binary");
        response.addHeader("Cache-Control", "no-cache, no-store");

		areaFormacaos = areaFormacaoManager.findAll(new String[]{"nome"});

		tiposFormacao = new TipoFormacao();
		situacoesFormacao = new SituacaoFormacao();
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}


	public String prepareUpdate() throws Exception
	{
		prepare();

		Map session = ActionContext.getContext().getSession();
		Collection<Formacao> lista = (Collection<Formacao>) session.get("SESSION_FORMACAO");

		for (Formacao f : lista)
		{
			if (f.getId().equals(formacao.getId()))
			{
				formacao = f;
				break;
			}
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		Collection<Formacao> lista = (Collection<Formacao>) session.get("SESSION_FORMACAO");

		Integer id =  (lista.size() + 1);
		formacao.setId(Long.valueOf(id.toString()));

		lista.add(formacao);
		session.put("SESSION_FORMACAO", lista);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		Collection<Formacao> lista = (Collection<Formacao>) session.get("SESSION_FORMACAO");

		for (Formacao f : lista)
		{
			if (f.getId().equals(formacao.getId()))
			{
				lista.remove(f);
				break;
			}
		}

		lista.add(formacao);
		session.put("SESSION_FORMACAO", lista);

		return Action.SUCCESS;
	}

	public Formacao getFormacao()
	{
		if(formacao == null)
			formacao = new Formacao();
		return formacao;
	}

	public void setFormacao(Formacao formacao)
	{
		this.formacao=formacao;
	}

	public Object getModel()
	{
		return getFormacao();
	}

	public Collection<AreaFormacao> getAreaFormacaos()
	{
		return areaFormacaos;
	}

	public void setAreaFormacaos(Collection<AreaFormacao> areaFormacaos)
	{
		this.areaFormacaos = areaFormacaos;
	}

	public Map getTipoFormacao()
	{
		return tiposFormacao;
	}

	public void setTipoFormacao(Map tipoFormacao)
	{
		this.tiposFormacao = tipoFormacao;
	}

	public Map getSituacoesFormacao()
	{
		return situacoesFormacao;
	}

	public void setSituacoesFormacao(Map situacoesFormacao)
	{
		this.situacoesFormacao = situacoesFormacao;
	}

	public Map getTiposFormacao()
	{
		return tiposFormacao;
	}

	public void setTiposFormacao(Map tiposFormacao)
	{
		this.tiposFormacao = tiposFormacao;
	}
}
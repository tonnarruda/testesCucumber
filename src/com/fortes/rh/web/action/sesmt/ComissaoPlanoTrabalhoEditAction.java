package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ComissaoPlanoTrabalhoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.MyActionSupport;

@SuppressWarnings("serial")
public class ComissaoPlanoTrabalhoEditAction extends MyActionSupport
{
	private ComissaoPlanoTrabalhoManager comissaoPlanoTrabalhoManager;
	private ComissaoMembroManager comissaoMembroManager;

	private ComissaoPlanoTrabalho comissaoPlanoTrabalho;
	private Collection<ComissaoPlanoTrabalho> comissaoPlanoTrabalhos;
	private Collection<Colaborador> colaboradors;
	private Comissao comissao;

	private Map<String, Object> parametros;

	public String prepare() throws Exception
	{
		if(comissaoPlanoTrabalho != null && comissaoPlanoTrabalho.getId() != null)
			comissaoPlanoTrabalho = (ComissaoPlanoTrabalho) comissaoPlanoTrabalhoManager.findByIdProjection(comissaoPlanoTrabalho.getId());

		return SUCCESS;
	}

	public String insert() throws Exception
	{
		if (comissaoPlanoTrabalho.getResponsavel() != null && comissaoPlanoTrabalho.getResponsavel().getId() == null)
			comissaoPlanoTrabalho.setResponsavel(null);
		
		if (comissaoPlanoTrabalho.getCoresponsavel() != null && comissaoPlanoTrabalho.getCoresponsavel().getId() == null)
			comissaoPlanoTrabalho.setCoresponsavel(null);

		comissaoPlanoTrabalho.setComissao(comissao);
		comissaoPlanoTrabalhoManager.save(comissaoPlanoTrabalho);
		return SUCCESS;
	}

	public String update() throws Exception
	{
		if (comissaoPlanoTrabalho.getResponsavel() != null && comissaoPlanoTrabalho.getResponsavel().getId() == null)
			comissaoPlanoTrabalho.setResponsavel(null);
		
		if (comissaoPlanoTrabalho.getCoresponsavel() != null && comissaoPlanoTrabalho.getCoresponsavel().getId() == null)
			comissaoPlanoTrabalho.setCoresponsavel(null);

		comissaoPlanoTrabalho.setComissao(comissao);
		comissaoPlanoTrabalhoManager.update(comissaoPlanoTrabalho);
		return SUCCESS;
	}

	public String list() throws Exception
	{
		comissaoPlanoTrabalhos = comissaoPlanoTrabalhoManager.findByComissao(comissao.getId());
		colaboradors = comissaoMembroManager.findColaboradorByComissao(comissao.getId());
		return SUCCESS;
	}

	public String delete() throws Exception
	{
		comissaoPlanoTrabalhoManager.remove(comissaoPlanoTrabalho.getId());
		return SUCCESS;
	}

	public String imprimirPlanoTrabalho() throws Exception
	{
		try
		{
			comissaoPlanoTrabalhos = comissaoPlanoTrabalhoManager.findImprimirPlanoTrabalho(comissao.getId());
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			list();
			return INPUT;
		}

		parametros = new HashMap<String, Object>();
		String logoEmpresa = ArquivoUtil.getPathLogoEmpresa() + getEmpresaSistema().getLogoUrl();
		parametros.put("LOGO", logoEmpresa);

		return SUCCESS;
	}

	public ComissaoPlanoTrabalho getComissaoPlanoTrabalho()
	{
		if(comissaoPlanoTrabalho == null)
			comissaoPlanoTrabalho = new ComissaoPlanoTrabalho();
		return comissaoPlanoTrabalho;
	}

	public void setComissaoPlanoTrabalho(ComissaoPlanoTrabalho comissaoPlanoTrabalho)
	{
		this.comissaoPlanoTrabalho = comissaoPlanoTrabalho;
	}

	public void setComissaoPlanoTrabalhoManager(ComissaoPlanoTrabalhoManager comissaoPlanoTrabalhoManager)
	{
		this.comissaoPlanoTrabalhoManager = comissaoPlanoTrabalhoManager;
	}

	public Comissao getComissao()
	{
		return comissao;
	}

	public void setComissao(Comissao comissao)
	{
		this.comissao = comissao;
	}

	public Collection<ComissaoPlanoTrabalho> getComissaoPlanoTrabalhos()
	{
		return comissaoPlanoTrabalhos;
	}

	public void setComissaoMembroManager(ComissaoMembroManager comissaoMembroManager)
	{
		this.comissaoMembroManager = comissaoMembroManager;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public void setColaboradors(Collection<Colaborador> colaboradors)
	{
		this.colaboradors = colaboradors;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}
}
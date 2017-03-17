package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

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
	@Autowired private ComissaoPlanoTrabalhoManager comissaoPlanoTrabalhoManager;
	@Autowired private ComissaoMembroManager comissaoMembroManager;

	private ComissaoPlanoTrabalho comissaoPlanoTrabalho;
	private Collection<ComissaoPlanoTrabalho> comissaoPlanoTrabalhos;
	private Collection<Colaborador> colaboradors;
	private Comissao comissao;
	
	private String situacao = "TODAS";
	private Long responsavelId;
	private Long corresponsavelId;

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
		
		if (comissaoPlanoTrabalho.getCorresponsavel() != null && comissaoPlanoTrabalho.getCorresponsavel().getId() == null)
			comissaoPlanoTrabalho.setCorresponsavel(null);

		comissaoPlanoTrabalho.setComissao(comissao);
		comissaoPlanoTrabalhoManager.save(comissaoPlanoTrabalho);
		return SUCCESS;
	}

	public String update() throws Exception
	{
		if (comissaoPlanoTrabalho.getResponsavel() != null && comissaoPlanoTrabalho.getResponsavel().getId() == null)
			comissaoPlanoTrabalho.setResponsavel(null);
		
		if (comissaoPlanoTrabalho.getCorresponsavel() != null && comissaoPlanoTrabalho.getCorresponsavel().getId() == null)
			comissaoPlanoTrabalho.setCorresponsavel(null);

		comissaoPlanoTrabalho.setComissao(comissao);
		comissaoPlanoTrabalhoManager.update(comissaoPlanoTrabalho);
		return SUCCESS;
	}

	public String list() throws Exception
	{
		comissaoPlanoTrabalhos = comissaoPlanoTrabalhoManager.findByComissao(comissao.getId(), situacao, responsavelId, corresponsavelId);
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
			comissaoPlanoTrabalhos = comissaoPlanoTrabalhoManager.findImprimirPlanoTrabalho(comissao.getId(), situacao, responsavelId, corresponsavelId);
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

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Long getResponsavelId() {
		return responsavelId;
	}

	public void setResponsavelId(Long responsavelId) {
		this.responsavelId = responsavelId;
	}

	public Long getCorresponsavelId() {
		return corresponsavelId;
	}

	public void setCorresponsavelId(Long corresponsavelId) {
		this.corresponsavelId = corresponsavelId;
	}
}
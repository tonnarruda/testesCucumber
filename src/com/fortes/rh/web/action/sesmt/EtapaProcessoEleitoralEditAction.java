package com.fortes.rh.web.action.sesmt;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.business.sesmt.EtapaProcessoEleitoralManager;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EtapaProcessoEleitoralEditAction extends MyActionSupportEdit
{
	@Autowired private EleicaoManager eleicaoManager;
	@Autowired private EtapaProcessoEleitoralManager etapaProcessoEleitoralManager;

	private EtapaProcessoEleitoral etapaProcessoEleitoral;
	private Eleicao eleicao;
	private Collection<EtapaProcessoEleitoral> etapaProcessoEleitorals;
	private String antesOuDepois = "";
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private Collection<EtapaProcessoEleitoral> dataSource;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		Long eleicaoId = (eleicao != null) ? eleicao.getId() : null;
		etapaProcessoEleitorals = etapaProcessoEleitoralManager.findAllSelect(getEmpresaSistema().getId(), eleicaoId);
		
		if(eleicao != null)
		eleicao = (Eleicao) eleicaoManager.findByIdProjection(eleicao.getId());
		
		return SUCCESS;
	}
	

	public String delete() throws Exception
	{
		etapaProcessoEleitoralManager.remove(new Long[]{etapaProcessoEleitoral.getId()});
		
		return SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(etapaProcessoEleitoral != null && etapaProcessoEleitoral.getId() != null)
		{
			etapaProcessoEleitoral = (EtapaProcessoEleitoral) etapaProcessoEleitoralManager.findById(etapaProcessoEleitoral.getId());
			antesOuDepois = etapaProcessoEleitoral.getAntesOuDepois();
		}
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		setPrazoAntesOuDepois();
		return SUCCESS;
	}

	public String insert() throws Exception
	{
		etapaProcessoEleitoral.setEmpresa(getEmpresaSistema());
		setPrazoAntesOuDepois(); // Cadastro de Etapa
		etapaProcessoEleitoralManager.save(etapaProcessoEleitoral);

		return SUCCESS;
	}

	public String update() throws Exception
	{
		setPrazoAntesOuDepois();
		etapaProcessoEleitoralManager.update(etapaProcessoEleitoral);
		return SUCCESS;
	}

	public String gerarEtapasModelo()
	{
		etapaProcessoEleitoralManager.gerarEtapasModelo(getEmpresaSistema());
		return SUCCESS;
	}
	
	private void setPrazoAntesOuDepois()
	{
		if (StringUtils.isNotBlank(antesOuDepois) && antesOuDepois.equalsIgnoreCase("antes"))
		{
			etapaProcessoEleitoral.setPrazo( -etapaProcessoEleitoral.getPrazo() );
		}
	}

	public String imprimirCalendario()
	{
		eleicao = etapaProcessoEleitoralManager.findImprimirCalendario(eleicao.getId());
		dataSource = eleicao.getEtapaProcessoEleitorals();

		String logoEmpresa = ArquivoUtil.getPathLogoEmpresa() + getEmpresaSistema().getLogoUrl();
		parametros = RelatorioUtil.getParametrosRelatorio("CIPA - CALENDÁRIO DO PROCESSO ELEITORAL", getEmpresaSistema(), "");

		parametros.put("POSSE", eleicao.getPosse());
		parametros.put("LOGO", logoEmpresa);

		return SUCCESS;
	}

	public EtapaProcessoEleitoral getEtapaProcessoEleitoral()
	{
		if(etapaProcessoEleitoral == null)
		{
			etapaProcessoEleitoral = new EtapaProcessoEleitoral();
			etapaProcessoEleitoral.setPrazo(null);
		}
		return etapaProcessoEleitoral;
	}

	public void setEtapaProcessoEleitoral(EtapaProcessoEleitoral etapaProcessoEleitoral)
	{
		this.etapaProcessoEleitoral = etapaProcessoEleitoral;
	}

	public String getAntesOuDepois()
	{
		return antesOuDepois;
	}

	public void setAntesOuDepois(String antesOuDepois)
	{
		this.antesOuDepois = antesOuDepois;
	}

	public Eleicao getEleicao()
	{
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao)
	{
		this.eleicao = eleicao;
	}

	public Collection<EtapaProcessoEleitoral> getEtapaProcessoEleitorals()
	{
		return etapaProcessoEleitorals;
	}

	public Collection<EtapaProcessoEleitoral> getDataSource()
	{
		return dataSource;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}
}
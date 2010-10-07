package com.fortes.rh.model.sesmt.relatorio;

import org.apache.commons.lang.StringUtils;



public class Ppra 
{
	private String riscosFisicos = "Inexistente.";
	private String riscosQuimicos = "Inexistente.";
	private String riscosBiologicos = "Inexistente.";
	private String riscosErgonomicos = "Inexistente.";
	private String riscosAcidentes = "Inexistente.";
	private String epcs = "";
	private String epis = "";
	private String tempoExposicao="";
	
	public String getRiscosFisicos() {
		return riscosFisicos;
	}

	public void setRiscosFisicos(String riscosFisicos) 
	{
		if (StringUtils.isNotBlank(riscosFisicos))
			this.riscosFisicos = riscosFisicos;
	}
	public void setRiscosQuimicos(String riscosQuimicos) 
	{
		if (StringUtils.isNotBlank(riscosQuimicos))
			this.riscosQuimicos = riscosQuimicos;
	}
	public void setRiscosBiologicos(String riscosBiologicos) 
	{
		if (StringUtils.isNotBlank(riscosBiologicos))
			this.riscosBiologicos = riscosBiologicos;
	}
	public void setRiscosErgonomicos(String riscosErgonomicos) 
	{
		if (StringUtils.isNotBlank(riscosErgonomicos))
			this.riscosErgonomicos = riscosErgonomicos;
	}
	public void setRiscosAcidentes(String riscosAcidentes) 
	{
		if (StringUtils.isNotBlank(riscosAcidentes))
			this.riscosAcidentes = riscosAcidentes;
	}

	public String getRiscosQuimicos() {
		return riscosQuimicos;
	}

	public String getRiscosBiologicos() {
		return riscosBiologicos;
	}

	public String getRiscosErgonomicos() {
		return riscosErgonomicos;
	}

	public String getRiscosAcidentes() {
		return riscosAcidentes;
	}

	public String getEpcs() {
		return epcs;
	}

	public void setEpcs(String epcs) {
		this.epcs = epcs;
	}

	public String getEpis() {
		return epis;
	}

	public void setEpis(String epis) {
		this.epis = epis;
	}

	public String getTempoExposicao() {
		return tempoExposicao;
	}

	public void setTempoExposicao(String tempoExposicao) {
		this.tempoExposicao = tempoExposicao;
	}
}

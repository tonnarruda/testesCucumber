package com.fortes.rh.model.sesmt.relatorio;

import org.apache.commons.lang.StringUtils;

public class Ltcat 
{
	private String riscosFisicos = "";
	private String riscosQuimicos = "";
	private String riscosBiologicos = "";
	private String riscosErgonomicos = "";
	private String riscosAcidentes = "";
	private String riscosOcupacionais = "";

	public String getRiscosFisicos() {
		return riscosFisicos;
	}

	public void setRiscosOcupacionais(String riscosOcupacionais) {
		if (StringUtils.isNotBlank(riscosOcupacionais))
			this.riscosOcupacionais = riscosOcupacionais;
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

	public String getRiscosOcupacionais() {
		return riscosOcupacionais;
	}

}

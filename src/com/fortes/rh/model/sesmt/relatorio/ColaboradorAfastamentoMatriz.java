package com.fortes.rh.model.sesmt.relatorio;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.ColaboradorAfastamento;

public class ColaboradorAfastamentoMatriz
{
	private String AreaOrganizacionalDescricao;
	private Collection<ColaboradorAfastamento> colaboradorAfastamentos = new ArrayList<ColaboradorAfastamento>();
	
	public Collection<ColaboradorAfastamento> getColaboradorAfastamentos() {
		return colaboradorAfastamentos;
	}
	
	public void setColaboradorAfastamentos(Collection<ColaboradorAfastamento> colaboradorAfastamentos) {
		this.colaboradorAfastamentos = colaboradorAfastamentos;
	}

	public String getAreaOrganizacionalDescricao() {
		return AreaOrganizacionalDescricao;
	}

	public void setAreaOrganizacionalDescricao(String areaOrganizacionalDescricao) {
		AreaOrganizacionalDescricao = areaOrganizacionalDescricao;
	}
}

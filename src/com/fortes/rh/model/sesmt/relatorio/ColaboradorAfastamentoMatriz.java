package com.fortes.rh.model.sesmt.relatorio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.sesmt.ColaboradorAfastamento;

public class ColaboradorAfastamentoMatriz
{
	private Collection<ColaboradorAfastamento> colaboradorAfastamentos = new ArrayList<ColaboradorAfastamento>();
	private Collection<Date> datas;
	
	public Collection<ColaboradorAfastamento> getColaboradorAfastamentos() {
		return colaboradorAfastamentos;
	}
	
	public void setColaboradorAfastamentos(Collection<ColaboradorAfastamento> colaboradorAfastamentos) {
		this.colaboradorAfastamentos = colaboradorAfastamentos;
	}
	
	public Collection<Date> getDatas() {
		return datas;
	}
	
	public void setDatas(Collection<Date> datas) {
		this.datas = datas;
	}
}

package com.fortes.rh.model.sesmt.relatorio;

import java.util.Date;

import com.fortes.rh.util.DateUtil;


public class CatRelatorioAnual
{
	private Date data;
	private String dataMesAno;
	private Integer total=0;
	private Integer totalComAfastamento=0;
	private Integer totalSemAfastamento=0;
	
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null || !(obj instanceof CatRelatorioAnual) || ((CatRelatorioAnual) obj).getData() == null)
			return false;
			
		return (((CatRelatorioAnual) obj).getDataMesAno().equals(this.dataMesAno)); 
	}
	
	@Override
	public int hashCode()
	{
		int hash = (this.dataMesAno != null ? dataMesAno.hashCode() : 0);
		return hash;
	}
	
	public void addTotal() {
		this.total++;
	}
	
	public void addTotalComAfastamento() {
		totalComAfastamento++;
	}
	
	public void addTotalSemAfastamento() {
		totalSemAfastamento++;
	}


	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
		this.dataMesAno = (data == null ? "" : DateUtil.formataMesExtensoAno(data));
	}

	public String getDataMesAno() {
		return dataMesAno;
	}

	public Integer getTotal() {
		return total;
	}

	public Integer getTotalComAfastamento() {
		return totalComAfastamento;
	}

	public Integer getTotalSemAfastamento() {
		return totalSemAfastamento;
	}
}
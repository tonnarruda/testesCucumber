package com.fortes.rh.model.desenvolvimento.relatorio;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fortes.rh.model.sesmt.Risco;

public class DataRiscos {

	private Date data;
	private Collection<Risco> riscos = new HashSet<Risco>();
//	private Ghe ghe;

	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public Collection<Risco> getRiscos()
	{
		return riscos;
	}
//	public Ghe getGhe()
//	{
//		return ghe;
//	}
//	public void setGhe(Ghe ghe)
//	{
//		this.ghe = ghe;
//	}
}

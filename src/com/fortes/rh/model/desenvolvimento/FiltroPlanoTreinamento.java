package com.fortes.rh.model.desenvolvimento;

import java.util.Date;

public class FiltroPlanoTreinamento
{
	private char realizada;
	private Long cursoId;
	private Date dataIni;
	private Date dataFim;
	private int page = 1;
	
	public Long getCursoId()
	{
		return cursoId;
	}
	public void setCursoId(Long cursoId)
	{
		this.cursoId = cursoId;
	}
	public Date getDataFim()
	{
		return dataFim;
	}
	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}
	public Date getDataIni()
	{
		return dataIni;
	}
	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}
	public int getPage()
	{
		return page;
	}
	public void setPage(int page)
	{
		this.page = page;
	}
	public char getRealizada()
	{
		return realizada;
	}
	public void setRealizada(char realizada)
	{
		this.realizada = realizada;
	}
	
}

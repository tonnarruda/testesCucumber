package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.util.DateUtil;

public class Ctps implements Serializable
{
	@Column(length=11)
	private String ctpsNumero;
	@Column(length=6)
	private String ctpsSerie;
	private Character ctpsDv;
	@ManyToOne
	private Estado ctpsUf;
	@Temporal(TemporalType.DATE)
	private Date ctpsDataExpedicao;

	public Date getCtpsDataExpedicao()
	{
		return ctpsDataExpedicao;
	}
	public void setCtpsDataExpedicao(Date ctpsDataExpedicao)
	{
		this.ctpsDataExpedicao = ctpsDataExpedicao;
	}
	public String getCtpsDataExpedicaoFormatada()
	{
		String dataFmt = "";
		if (ctpsDataExpedicao != null)
			dataFmt += DateUtil.formataDiaMesAno(ctpsDataExpedicao);

		return dataFmt;
	}
	public Character getCtpsDv()
	{
		return ctpsDv;
	}
	public void setCtpsDv(Character ctpsDv)
	{
		this.ctpsDv = ctpsDv;
	}
	public String getCtpsNumero()
	{
		return ctpsNumero;
	}
	public void setCtpsNumero(String ctpsNumero)
	{
		this.ctpsNumero = ctpsNumero;
	}
	public String getCtpsSerie()
	{
		return ctpsSerie;
	}
	public void setCtpsSerie(String ctpsSerie)
	{
		this.ctpsSerie = ctpsSerie;
	}
	public Estado getCtpsUf()
	{
		return ctpsUf;
	}
	public void setCtpsUf(Estado ctpsUf)
	{
		this.ctpsUf = ctpsUf;
	}
	
	public String getNumeroCompleto()
	{
		String numeroCompleto="";
		
		if (StringUtils.isNotBlank(ctpsNumero))
			numeroCompleto = ctpsNumero;
		
		numeroCompleto += "/"+getSerieMaisDv();
		
		return numeroCompleto;
	}

	public String getSerieMaisDv()
	{
		String serieMaisDv=" ";
		
		if (StringUtils.isNotBlank(ctpsSerie))
		{
			serieMaisDv = ctpsSerie;
			serieMaisDv += "-";
		}
		
		
		if (ctpsDv != null && !ctpsDv.equals(' '))
			serieMaisDv += ctpsDv;
		
		return serieMaisDv;
	}
}
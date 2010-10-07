package com.fortes.rh.model.desenvolvimento.relatorio;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MatrizTreinamento implements Serializable
{
	private Long certificacaoId;
	private String certificacaoNome;
	private String faixaDescricao;
	private String cursoNome;

	private Long cursoId;
	private boolean realizado;


	public MatrizTreinamento(Long certificacaoId, String certificacaoNome, String faixaNome, String cargoNome, String cursoNome, Long cursoId)
	{
		super();
		this.certificacaoId = certificacaoId;
		this.certificacaoNome = certificacaoNome;
		this.faixaDescricao = cargoNome + " " + faixaNome;;
		this.cursoNome = cursoNome;
		this.cursoId = cursoId;
	}

	public Long getCertificacaoId()
	{
		return certificacaoId;
	}
	public void setCertificacaoId(Long certificacaoId)
	{
		this.certificacaoId = certificacaoId;
	}
	public String getCertificacaoNome()
	{
		return certificacaoNome;
	}
	public void setCertificacaoNome(String certificacaoNome)
	{
		this.certificacaoNome = certificacaoNome;
	}
	public String getCursoNome()
	{
		return cursoNome;
	}
	public void setCursoNome(String cursoNome)
	{
		this.cursoNome = cursoNome;
	}
	public String getFaixaDescricao()
	{
		return faixaDescricao;
	}
	public void setFaixaDescricao(String faixaDescricao)
	{
		this.faixaDescricao = faixaDescricao;
	}
	public Long getCursoId()
	{
		return cursoId;
	}
	public void setCursoId(Long cursoId)
	{
		this.cursoId = cursoId;
	}
	public boolean isRealizado()
	{
		return realizado;
	}
	public void setRealizado(boolean realizado)
	{
		this.realizado = realizado;
	}
}

package com.fortes.rh.model.relatorio;

public class Cabecalho
{
	private String titulo;
	private String nomeEmpresa;
	private String filtro;
	private String usuario;
	private String versaoSistema;
	private String logoUrl;
	private String msgAutenticado;
	private String licenciadoPara;

	public Cabecalho()
	{
		
	}
	
	public Cabecalho(String titulo, String nomeEmpresa, String filtro, String usuario, String versaoSistema, String logoUrl, String msgAutenticado)
	{
		super();
		this.titulo = titulo;
		this.nomeEmpresa = nomeEmpresa;
		this.filtro = filtro;
		this.usuario = usuario;
		this.versaoSistema = versaoSistema;
		this.logoUrl = logoUrl;
		this.msgAutenticado = msgAutenticado;
	}

	public String getFiltro()
	{
		return filtro;
	}
	public void setFiltro(String filtro)
	{
		this.filtro = filtro;
	}
	public String getLogoUrl()
	{
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl)
	{
		this.logoUrl = logoUrl;
	}
	public String getNomeEmpresa()
	{
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa)
	{
		this.nomeEmpresa = nomeEmpresa;
	}
	public String getTitulo()
	{
		return titulo;
	}
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}
	public String getUsuario()
	{
		return usuario;
	}
	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}
	public String getVersaoSistema()
	{
		return versaoSistema;
	}
	public void setVersaoSistema(String versaoSistema)
	{
		this.versaoSistema = versaoSistema;
	}

	public String getMsgAutenticado()
	{
		return msgAutenticado;
	}

	public void setMsgAutenticado(String msgAutenticado)
	{
		this.msgAutenticado = msgAutenticado;
	}

	public String getLicenciadoPara()
	{
		return licenciadoPara;
	}

	public void setLicenciadoPara(String licenciadoPara)
	{
		this.licenciadoPara = licenciadoPara;
	}

}

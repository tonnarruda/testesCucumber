package com.fortes.rh.model.dicionario;

public enum URLTransacaoPC
{
	COLABORADOR_ATUALIZAR(1, "/colaboradores/atualizar.json", "PUT");

	private Integer id;
	private String url;
	private String method;
	
	private URLTransacaoPC(Integer id, String url, String method) 
	{
		this.id = id;
		this.url = url;
		this.method = method;
	}

	public Integer getId() 
	{
		return id;
	}

	public String getUrl() 
	{
		return getBaseUrl() + url;
	}

	private String getBaseUrl()
	{
		return "http://0.0.0.0:3000";
	}
	
	public String getMethod() 
	{
		return method;
	}
	
	public static final URLTransacaoPC getById(int id)
	{
		for (URLTransacaoPC urlPC : values()) 
		{
			if (urlPC.getId() == id)
				return urlPC;
		}
		
		return null;
	}
}

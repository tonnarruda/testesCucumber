package com.fortes.rh.model.dicionario;

public enum URLTransacaoPC
{
	COLABORADOR_ATUALIZAR(1, "/colaboradores", "POST");

	private Integer id;
	private String url;
	private String method;
	
	private URLTransacaoPC(Integer id, String url, String method) 
	{
		this.id = id;
		this.url = url;
		this.method = method;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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

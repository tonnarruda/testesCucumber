package com.fortes.rh.model.dicionario;

public enum URLTransacaoPC
{
	COLABORADOR_ATUALIZAR(1, "/api/v1/colaboradores/atualizar.json", "PUT"),
	TESTAR_CONEXAO_PORTAL(2, "/api/v1/testar_conexao", "POST"),
	COLABORADOR_ATUALIZAR_HISTORICO(3, "/api/v1/colaboradores/atualizar_historico.json", "PUT"),
	EMPRESA_ATUALIZAR(4, "/api/v1/empresas/atualizar.json", "PUT"),
	ENVIAR_EMAIL(5, "/api/v1/enviar_email.json", "POST"),
	EMPRESA_REMOVER(6, "/api/v1/empresas/remover.json", "PUT");

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
		return "http://127.0.0.1:3000";
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

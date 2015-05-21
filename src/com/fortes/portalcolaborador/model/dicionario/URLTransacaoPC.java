package com.fortes.portalcolaborador.model.dicionario;

public enum URLTransacaoPC
{
	ATUALIZAR_COLABORADOR(1, "/api/v1/colaboradores/atualizar.json", "PUT"),
	TESTAR_CONEXAO_PORTAL(2, "/api/v1/testar_conexao", "POST"),
	ATUALIZAR_HISTORICO_COLABORADOR(3, "/api/v1/colaboradores/atualizar_historico.json", "PUT"),
	ATUALIZAR_EMPRESA(4, "/api/v1/empresas/atualizar.json", "PUT"),
	ENVIAR_EMAIL(5, "/api/v1/enviar_email.json", "POST"),
	REMOVER_EMPRESA(6, "/api/v1/empresas/remover.json", "PUT"),
	REMOVER_COLABORADOR(7, "/api/v1/colaboradores/remover.json", "PUT"),
	VERIFICAR_ATUALIZACAO_COLABORADOR(8, "/api/v1/colaboradores/verificar_atualizacao", "GET"),
	ATUALIZAR_DESLIGAMENTO_COLABORADOR(9, "/api/v1/colaboradores/atualizar_desligamento.json", "PUT");

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

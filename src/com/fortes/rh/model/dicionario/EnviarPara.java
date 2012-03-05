package com.fortes.rh.model.dicionario;


public enum EnviarPara
{

	SELECIONAR_ENVIAR_PARA(0, "Selecione..."),
	USUARIO (1, "Usuário"),
	GESTOR_AREA (2, "Gestor da área organizacional"),
	CANDIDATO_NAO_APTO (3, "Candidatos não aptos"),
	SOLICITANTE_SOLICITACAO (4, "Solicitante"),
	AVALIADOR_AVALIACAO_DESEMPENHO (5, "Avaliador"),
	COLABORADOR (6, "Colaborador"),
	RESPONSAVEL_RH (7, "Responsável do RH"),
	RESPONSAVEL_SETOR_PESSOAL (8, "Responsável do setor pessoal"),
	PERFIL_AUTORIZADO_EXAMES_PREVISTOS (9, "Usuários com permissão de receber emails de exames previstos"),
	GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA (10, "Usuários com permissão de gerenciador de mensagem por período de experiência"),
	RECEBE_MENSAGEM_PERIODO_EXPERIENCIA (11, "Usuários com permissão de receber mensagem por período de experiência"),
	RESPONSAVEL_TECNICO (12, "Responsável Tecnico"),
	RECEBE_MENSAGEM_AC_PESSOAL (13, "Usuários com perfil de receber mensagem do AC Pessoal"),
	PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL (14, "Usuários com permissão de visualizar solicitação pessoal"),
	AVULSO (99, "Avulso");
	
	EnviarPara(Integer id , String descricao)
	{
		this.id = id;
		this.descricao = descricao;
	}

	private Integer id;
	private String descricao;
	
	public Integer getId() 
	{
		return id;
	}
	public String getDescricao() 
	{
		return descricao;
	}
	
	public static final String getDescricaoById(int id)
	{
		for (EnviarPara ep : EnviarPara.values()) 
		{
			if(ep.getId() == id)
				return ep.getDescricao();
		}
		
		return "";
	}
	
}
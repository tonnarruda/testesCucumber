package com.fortes.rh.model.dicionario;


public enum EnviarPara
{

	SELECIONAR_ENVIAR_PARA(0, "Selecione..."),
	USUARIOS (1, "Usuários"),
	GESTOR_AREA (2, "Gestor da área organizacional"),
	CANDIDATO_NAO_APTO (3, "Candidatos não aptos"),
	SOLICITANTE_SOLICITACAO (4, "Solicitante"),
	AVALIADOR_AVALIACAO_DESEMPENHO (5, "Avaliador"),
	COLABORADOR (6, "Colaborador"),
	COLABORADOR_AVALIADO (17, "Colaborador (Avaliado)"),
	RESPONSAVEL_RH (7, "Responsável do RH"),
	RESPONSAVEL_SETOR_PESSOAL (8, "Responsável do setor pessoal"),
	PERFIL_AUTORIZADO_EXAMES_PREVISTOS (9, "Usuários com permissão de receber emails de exames previstos"),
	RESPONSAVEL_TECNICO (12, "Responsável Tecnico"),
	RECEBE_MENSAGEM_AC_PESSOAL (13, "Usuários com perfil de receber mensagem do AC Pessoal"),
	PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL (14, "Usuários com permissão de visualizar solicitação pessoal"),
	RESPONSAVEL_LIMITE_CONTRATO (15, "Responsável pelo limite de colaboradores por cargo"),
	PERFIL_VER_AREAS (16, "Usuários com perfil de visualizar todas as áreas organizacionais"),
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
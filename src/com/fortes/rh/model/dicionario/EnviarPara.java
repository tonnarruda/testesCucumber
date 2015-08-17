package com.fortes.rh.model.dicionario;


public enum EnviarPara
{

	SELECIONAR_ENVIAR_PARA(0, "Selecione..."),
	USUARIOS (1, "Usuários"),
	GESTOR_AREA (2, "Gestor da área organizacional"),
	COGESTOR_AREA (18, "Cogestor da área organizacional"),
	CANDIDATO_NAO_APTO (3, "Candidatos não aptos"),
	SOLICITANTE_SOLICITACAO (4, "Solicitante"),
	AVALIADOR_AVALIACAO_DESEMPENHO (5, "Avaliador"),
	COLABORADOR (6, "Colaborador"),
	COLABORADOR_AVALIADO (17, "Colaborador (Avaliado)"),
	RESPONSAVEL_RH (7, "Responsável do RH"),
	RESPONSAVEL_SETOR_PESSOAL (8, "Responsável do setor pessoal"),
	RESPONSAVEL_TECNICO (12, "Responsável Técnico"),
	RECEBE_MENSAGEM_AC_PESSOAL (13, "Usuários com perfil de receber mensagem do Fortes Pessoal"),
	PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL (14, "Usuários com permissão de visualizar solicitação pessoal"),
	RESPONSAVEL_LIMITE_CONTRATO (15, "Responsável pelo limite de colaboradores por cargo"),
	APROVAR_REPROVAR_SOLICITACAO_PESSOAL (19, "Usuários com perfil de aprovar/reprovar solicitação de pessoal"),
	APROVAR_REPROVAR_SOLICITACAO_DESLIGAMENTO (20, "Usuários com perfil de aprovar/reprovar solicitação de desligamento"),
	SOLICITANTE_DESLIGAMENTO (21, "Solicitante do desligamento"),
	APLICAR_REALINHAMENTO(22, "Usuários com perfil de aplicar realinhamento"),
	APROVAR_REPROVAR_SOLICITACAO_PESSOAL_AND_GESTOR(23,"Gestor da área organizacional com o perfil de aprovar/reprovar solicitação de pessoal."), 
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
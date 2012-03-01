package com.fortes.rh.model.dicionario;

import java.util.HashMap;

public enum EnviarPara
{

	USUARIO (1, "Usuário"),
	GESTOR_AREA (2, "Gestor da área organizacional"),
	CANDIDATO_NAO_APTO (3, "Candidatos não aptos"),
	SOLICITANTE_SOLICITACAO (4, "Solicitante"),
	LIBERADOR_SOLICITACAO (5, "Liberador"),
	AVALIADOR_AVALIACAO_DESEMPENHO (6, "Avaliador"),
	COLABORADOR (7, "Colaborador"),
	RESPONSAVEL_RH (8, "Responsável do RH"),
	PERFIL_AUTORIZADO_EXAMES_PREVISTOS (9, "Usuários com perfil de receber emails de exames previstos"),
	GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA (10, "Usuários com perfil de gerenciador de mensagem por período de experiência"),
	RECEBE_MENSAGEM_PERIODO_EXPERIENCIA (11, "Usuários com perfil de receber mensagem por período de experiência"),
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
	
	public static void setCandidatoNaoApto(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.CANDIDATO_NAO_APTO.getId(), EnviarPara.CANDIDATO_NAO_APTO.getDescricao());
	}
	
	public static void setUsuario(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.USUARIO.getId(), EnviarPara.USUARIO.getDescricao());
	}
	
	public static void setGestorArea(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.GESTOR_AREA.getId(), EnviarPara.GESTOR_AREA.getDescricao());
	}
	
	public static void setAvulso(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.AVULSO.getId(), EnviarPara.AVULSO.getDescricao());
	}

	public static void setLiberador(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.LIBERADOR_SOLICITACAO.getId(), EnviarPara.LIBERADOR_SOLICITACAO.getDescricao());
	}
	public static void setSolicitante(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.SOLICITANTE_SOLICITACAO.getId(), EnviarPara.SOLICITANTE_SOLICITACAO.getDescricao());
	}
	
	public static void setAvaliadorAvaliacaoDesempenho(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getId(), EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getDescricao());
	}

	public static void setColaborador(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.COLABORADOR.getId(), EnviarPara.COLABORADOR.getDescricao());
	}
	
	public static void setResponsavelRH(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.RESPONSAVEL_RH.getId(), EnviarPara.RESPONSAVEL_RH.getDescricao());
	}
	
	public static void setPerfilAutorizadoExamesPrevistos(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS.getId(), EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS.getDescricao());
	}
	
	public static void setGerenciadorDeMensagemPeriodoExperiencia(HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA.getId(), EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA.getDescricao());
	}
	
	public static void setRecebeMensagemPeriodoExperiencia (HashMap<Integer, String> enviarPara) {
		enviarPara.put(EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA.getId(), EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA.getDescricao());
	}
}
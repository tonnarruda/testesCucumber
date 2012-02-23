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
}
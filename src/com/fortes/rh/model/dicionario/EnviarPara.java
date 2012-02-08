package com.fortes.rh.model.dicionario;

public enum EnviarPara
{

	USUARIO (1, "Usuário"),
	GESTOR_AREA (2, "Gestor da área organizacional"),
	CANDIDATO_NAO_APTO (3, "Candidatos não aptos"),
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
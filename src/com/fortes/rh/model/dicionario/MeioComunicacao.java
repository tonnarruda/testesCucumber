package com.fortes.rh.model.dicionario;

import java.util.HashMap;


public enum MeioComunicacao
{
	CAIXA_MENSAGEM(1, "Caixa de mensagem"),
	EMAIL(2, "Email");
	
	MeioComunicacao(Integer id, String descricao)
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
		for (MeioComunicacao mc : MeioComunicacao.values()) 
		{
			if(mc.getId() == id)
				return mc.getDescricao();
		}
		
		return "";
	}
}

package com.fortes.rh.model.dicionario;

import java.util.TreeMap;
public enum MeioComunicacao
{
	SELECIONAR_MEIO_COMUNICACAO(0, "Selecione..."),
	CAIXA_MENSAGEM(1, "Caixa de mensagem"),
	EMAIL(2, "Email");

	MeioComunicacao(Integer id, String descricao)
	{
		this.id = id;
		this.descricao = descricao;
	}
	
	private Integer id;
	private String descricao;
	private TreeMap<Integer, String> listEnviarPara = new TreeMap<Integer, String>();
	
	public void add(EnviarPara enviarPara)
	{
		if(listEnviarPara == null)
			listEnviarPara = new TreeMap<Integer, String>();
		
		listEnviarPara.put(enviarPara.getId(), enviarPara.getDescricao());
	}
	
	public void inicializaListaEnviarPara(){
		listEnviarPara = new TreeMap<Integer, String>();
		listEnviarPara.put(EnviarPara.SELECIONAR_ENVIAR_PARA.getId(), EnviarPara.SELECIONAR_ENVIAR_PARA.getDescricao());
	}
	
	public Integer getId() 
	{
		return id;
	}
	
	public String getDescricao() 
	{
		return descricao;
	}
	
	public static final MeioComunicacao getMeioComunicacaoById(int id)
	{
		for (MeioComunicacao mc : MeioComunicacao.values()) 
		{
			if(mc.getId() == id)
				return mc;
		}
		
		return null;
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

	public static void setMeiosDeComunicacoes(TreeMap<Integer, String> meioComunicação) {
		for (MeioComunicacao meioComunicacao : values()) 
			meioComunicação.put(meioComunicacao.getId(), meioComunicacao.getDescricao());
	}
	
	public TreeMap<Integer, String> getListEnviarPara() {
		return listEnviarPara;
	}
}

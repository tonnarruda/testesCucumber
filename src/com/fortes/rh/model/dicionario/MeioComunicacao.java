package com.fortes.rh.model.dicionario;

import java.util.HashMap;
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
	private HashMap<Integer, String> listEnviarPara = new HashMap<Integer, String>();
	
	public void add(EnviarPara enviarPara)
	{
		if(listEnviarPara == null)
			listEnviarPara = new HashMap<Integer, String>();
		
		listEnviarPara.put(enviarPara.getId(), enviarPara.getDescricao());
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

	public HashMap<Integer, String> getEnviarPara(int operacaoId){
		HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
		
		EnviarPara.setUsuario(enviarPara);
		EnviarPara.setAvulso(enviarPara);

		return enviarPara;
	}

	public static HashMap<Integer,String> getEnviarParaById(Integer id){
		for (MeioComunicacao o : MeioComunicacao.values()) 
			if(o.getId() == id)
				return o.getEnviarPara(id);

		return null;
	}
	
	public static void setSelecionarMeioComunicacao(HashMap<Integer, String> meioComunicação) 
	{
		meioComunicação.put(MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO.getId(), MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO.getDescricao());
	}
	
	public static void setCaixaMensagem(HashMap<Integer, String> meioComunicação)
	{
		meioComunicação.put(MeioComunicacao.CAIXA_MENSAGEM.getId(), MeioComunicacao.CAIXA_MENSAGEM.getDescricao());
	}
	
	public static void setEmail(HashMap<Integer, String> meioComunicação) 
	{
		meioComunicação.put(MeioComunicacao.EMAIL.getId(), MeioComunicacao.EMAIL.getDescricao());
	}
	
	public static void setMeiosDeComunicacoes(HashMap<Integer, String> meioComunicação) {
		for (MeioComunicacao meioComunicacao : values()) 
			meioComunicação.put(meioComunicacao.getId(), meioComunicacao.getDescricao());
	}
	
	public HashMap<Integer, String> getListEnviarPara() {
		return listEnviarPara;
	}
}

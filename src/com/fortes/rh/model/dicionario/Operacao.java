package com.fortes.rh.model.dicionario;

import java.util.HashMap;

public enum Operacao
{
	SELECIONAR_OPERACAO(0, "Selecione..."){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			return meioComunicação;
		}

		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			return enviarPara;
		}
	},
	ENCERRAMENTO_SOLICITACAO(1, "Encerramento da solicitação de pessoal"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}

		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setCandidatoNaoApto(enviarPara);
			
			return enviarPara;
		}
	},
	LIBERAR_SOLICITACAO (2, "Liberar Solicitação");

	private int id;
	private String descricao;

	Operacao(int id, String descricao){
		this.id = id;
		this.descricao = descricao;
	}

	public HashMap<Integer, String> meioComunicação(){
		HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
		MeioComunicacao.setMeiosDeComunicacoes(meioComunicação);
		
		return meioComunicação;
	}
	
	public HashMap<Integer, String> enviarPara(){
		HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
		EnviarPara.setUsuario(enviarPara);
		EnviarPara.setAvulso(enviarPara);
		
		return enviarPara;
	}
	
	public String getDescricao(){
		return descricao;
	}

	public int getId(){
		return id;
	}

	public static final HashMap<Integer, String> getHashMap(){
		HashMap<Integer, String> operacoes= new HashMap<Integer, String>();  
	    
		for(Operacao o : Operacao.values()) 
			operacoes.put(o.getId(), o.getDescricao()); 
		
		return operacoes;
	}
	
	public static final String getDescricaoById(int id){
		for (Operacao o : Operacao.values()) 
		{
			if(o.getId() == id)
				return o.getDescricao();
		}
		return "";
	}

	public static HashMap<Integer,String> getMeioComunicacaoById(Integer id){
		for (Operacao o : Operacao.values()) 
			if(o.getId() == id)
				return o.meioComunicação();
	
		return null;
	}
	
	public static HashMap<Integer,String> getEnviarParaById(Integer id){
		for (Operacao o : Operacao.values()) 
			if(o.getId() == id)
				return o.enviarPara();

		return null;
	}
}

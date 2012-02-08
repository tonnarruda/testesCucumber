package com.fortes.rh.model.dicionario;

import java.util.HashMap;


public enum Operacao
{
	NAO_INFORMADO(0, "Não Informado"){
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
			meioComunicação.put(MeioComunicacao.EMAIL.getId(), MeioComunicacao.EMAIL.getDescricao());
			return meioComunicação;
		}

		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			enviarPara.put(EnviarPara.CANDIDATO_NAO_APTO.getId(), EnviarPara.CANDIDATO_NAO_APTO.getDescricao());
			enviarPara.put(EnviarPara.AVULSO.getId(), EnviarPara.AVULSO.getDescricao());
			return enviarPara;
		}
	},
	LIBERAR_SOLICITACAO (2, "Liberar Solicictação");

	private int id;
	private String descricao;

	Operacao(int id, String descricao){
		this.id=id;
		this.descricao = descricao;
	}

	public HashMap<Integer, String> meioComunicação(){
		HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
		
		for (MeioComunicacao mc : MeioComunicacao.values()) 
			meioComunicação.put(mc.getId(), mc.getDescricao());
		
		return meioComunicação;
	}
	
	public HashMap<Integer, String> enviarPara(){
		HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
		
		enviarPara.put(EnviarPara.USUARIO.getId(), EnviarPara.USUARIO.getDescricao());
		enviarPara.put(EnviarPara.AVULSO.getId(), EnviarPara.AVULSO.getDescricao());
		
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

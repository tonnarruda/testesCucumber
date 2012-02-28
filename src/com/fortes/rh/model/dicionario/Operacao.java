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
	LIBERAR_SOLICITACAO (2, "Liberar solicitação"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}

		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setLiberador(enviarPara);
			
			return enviarPara;
		}
	},
	ALTEREAR_STATUS_SOLICITACAO(3, "Ateração no status da solicitação de pessoal"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}

		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setSolicitante(enviarPara);
			
			return enviarPara;
		}
	},
	ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO(4, "Enviar lembrete avaliação desempenho"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}

		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setAvaliadorAvaliacaoDesempenho(enviarPara);
			
			return enviarPara;
		}
	},
	LIBERAR_QUESTIONARIO(5, "Liberar questionário"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
		
		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setColaborador(enviarPara);
			
			return enviarPara;
		}
	},
	LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO(6, "Lembrete de pesquisa não respondida"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);

			return meioComunicação;
		}

		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setColaborador(enviarPara);

			return enviarPara;
		}
	},
	LEMBRETE_QUESTIONARIO_NAO_LIBERADO(7, "Lembrete automático de pesquisa não liberada"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
		
		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setResponsavelRH(enviarPara);
			
			return enviarPara;
		}
	},
	CADASTRO_CANDIDATO_MODULO_EXTERNO(8, "Aviso de cadastro de candidato pelo módulo externo"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
		
		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setResponsavelRH(enviarPara);
			
			return enviarPara;
		}
	},
	QTD_CURRICULOS_CADASTRADOS(9, "Aviso automático da quantidade de currículos cadastros por mês"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
		
		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setResponsavelRH(enviarPara);
			
			return enviarPara;
		}
	},
	AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO(10, "Aviso automático das avaliações do período de experiência a vencer"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
		
		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setResponsavelRH(enviarPara);
			
			return enviarPara;
		}
	},
	EXAMES_PREVISTOS(11, "Aviso automático de exames previstos"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
		
		public HashMap<Integer, String> enviarPara(){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			EnviarPara.setPerfilAutorizadoExamesPrevistos(enviarPara);
			
			return enviarPara;
		}
	};

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

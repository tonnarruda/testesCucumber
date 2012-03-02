package com.fortes.rh.model.dicionario;

import java.util.HashMap;

public enum Operacao
{
	SELECIONAR_OPERACAO(0, "Selecione..."){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			
			return meioComunicação;
		}
	},
	ENCERRAMENTO_SOLICITACAO(1, "Encerramento da solicitação de pessoal"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
	},
	ALTERAR_STATUS_SOLICITACAO(2, "Alteração no status da solicitação de pessoal"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
	},
	ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO(3, "Enviar lembrete avaliação desempenho"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
	},
	LIBERAR_QUESTIONARIO(4, "Liberar questionário"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
	},
	LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO(5, "Lembrete de pesquisa não respondida"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);

			return meioComunicação;
		}
	},
	LEMBRETE_QUESTIONARIO_NAO_LIBERADO(6, "Lembrete automático de pesquisa não liberada"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
	},
	CADASTRO_CANDIDATO_MODULO_EXTERNO(7, "Aviso de cadastro de candidato pelo módulo externo"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
	},
	QTD_CURRICULOS_CADASTRADOS(8, "Aviso automático da quantidade de currículos cadastros por mês"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
	},
	AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO(9, "Aviso automático das avaliações do período de experiência a vencer"),
	EXAMES_PREVISTOS(10, "Aviso automático de exames previstos"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);
			
			return meioComunicação;
		}
	},
	BACKUP_AUTOMATICO(11, "Aviso automático de backup"){
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);
			return meioComunicação;
		}
	},
	CONTRATAR_COLABORADOR(12, "Contratação de Colaborador") {
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setEmail(meioComunicação);

			return meioComunicação;
		}
	}, 
	CANCELAR_SITUACAO_AC(13, "Cancelamento de Situação no AC Pessoal") {
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setCaixaMensagem(meioComunicação);
			
			return meioComunicação;
		}
	},
	SOLICITACAO_CANDIDATOS_MODULO_EXTERNO(14, "Exibir solicitações com canditados do modulo externo") {
		public HashMap<Integer, String> meioComunicação(){
			HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
			MeioComunicacao.setSelecionarMeioComunicacao(meioComunicação);
			MeioComunicacao.setCaixaMensagem(meioComunicação);

			return meioComunicação;
		}
	}, 
	DESLIGAR_COLABORADOR_AC(15, "Desligar colaborador no AC") {
		public HashMap<Integer, String> meioComunicação(){
			
			this.add(MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO);
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL);

			return this.getListMeioComunicacao();
		}
	};

	private int id;
	private String descricao;
	private HashMap<Integer, String> listMeioComunicacao = new HashMap<Integer, String>();
	
	Operacao(int id, String descricao){
		this.id = id;
		this.descricao = descricao;
	}
	
	public void add(MeioComunicacao meioComunicacao)
	{
		if(listMeioComunicacao == null)
			listMeioComunicacao = new HashMap<Integer, String>();
		
		listMeioComunicacao.put(meioComunicacao.getId(), meioComunicacao.getDescricao());
	}

	public HashMap<Integer, String> meioComunicação(){
		HashMap<Integer, String> meioComunicação = new HashMap<Integer, String>();
		MeioComunicacao.setMeiosDeComunicacoes(meioComunicação);
		
		return meioComunicação;
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

	public HashMap<Integer, String> getListMeioComunicacao() {
		return listMeioComunicacao;
	}
}

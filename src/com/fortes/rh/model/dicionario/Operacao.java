package com.fortes.rh.model.dicionario;

import java.util.HashMap;

public enum Operacao
{
	SELECIONAR_OPERACAO(0, "Selecione..."){
		public HashMap<Integer, String> meioComunicação(){
			return this.getListMeioComunicacao();
		}
	},
	ENCERRAMENTO_SOLICITACAO(1, "Encerramento da solicitação de pessoal"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.CANDIDATO_NAO_APTO);
			
			return this.getListMeioComunicacao();
		}
	},
	ALTERAR_STATUS_SOLICITACAO(2, "Alteração no status da solicitação de pessoal"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.SOLICITANTE_SOLICITACAO);
			
			return this.getListMeioComunicacao();
		}
	},
	ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO(3, "Enviar lembrete avaliação desempenho"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO);
			
			return this.getListMeioComunicacao();
		}
	},
	LIBERAR_QUESTIONARIO(4, "Liberar questionário"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.COLABORADOR);
			
			return this.getListMeioComunicacao();
		}
	},
	LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO(5, "Lembrete de pesquisa não respondida"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.COLABORADOR);
			
			return this.getListMeioComunicacao();
		}
	},
	LEMBRETE_QUESTIONARIO_NAO_LIBERADO(6, "Lembrete automático de pesquisa não liberada"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	CADASTRO_CANDIDATO_MODULO_EXTERNO(7, "Aviso de cadastro de candidato pelo módulo externo"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	QTD_CURRICULOS_CADASTRADOS(8, "Aviso automático da quantidade de currículos cadastros por mês"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO(9, "Aviso automático das avaliações do período de experiência a vencer"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	EXAMES_PREVISTOS(10, "Aviso automático de exames previstos"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS);
			
			return this.getListMeioComunicacao();
		}
	},
	BACKUP_AUTOMATICO(11, "Aviso automático de backup"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_TECNICO);
			
			return this.getListMeioComunicacao();
		}
	},
	CONTRATAR_COLABORADOR(12, "Contratação de Colaborador") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_SETOR_PESSOAL);
			
			return this.getListMeioComunicacao();
		}
	}, 
	CANCELAR_SITUACAO_AC(13, "Cancelamento de Situação no AC Pessoal") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL);
			
			return this.getListMeioComunicacao();
		}
	},
	SOLICITACAO_CANDIDATOS_MODULO_EXTERNO(14, "Exibir solicitações com canditados do modulo externo") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL);

			return this.getListMeioComunicacao();
		}
	}, 
	DESLIGAR_COLABORADOR_AC(15, "Desligar colaborador no AC") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL);

			return this.getListMeioComunicacao();
		}
	}, 
	CONFIGURACAO_LIMITE_COLABORADOR(16, "Configuração do limite de colaboradores por cargo") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_LIMITE_CONTRATO);

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
		
		meioComunicacao.inicializaListaEnviarPara();
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

	public static Operacao getById(Integer operacaoId){
		for (Operacao o : Operacao.values()) 
			if(o.getId() == operacaoId)
				return o;
	
		return null;
	}

	public static HashMap<Integer,String> getMeioComunicacaosById(Integer id){
		for (Operacao o : Operacao.values()) 
			if(o.getId() == id)
				return o.meioComunicação();
		
		return null;
	}
	
	public HashMap<Integer, String> getListMeioComunicacao() 
	{
		this.add(MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO);
		
		return listMeioComunicacao;
	}
}

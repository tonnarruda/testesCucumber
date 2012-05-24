package com.fortes.rh.model.dicionario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public enum Operacao
{
	ALTERAR_STATUS_SOLICITACAO(5, "Alterar status da solicitação de pessoal", "R&S"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.SOLICITANTE_SOLICITACAO);
			
			return this.getListMeioComunicacao();
		}
	},
	CADASTRAR_CANDIDATO_MODULO_EXTERNO(1, "Cadastrar candidato pelo módulo externo", "R&S"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			MeioComunicacao.EMAIL.add(EnviarPara.USUARIOS);
			
			return this.getListMeioComunicacao();
		}
	},
	QTD_CURRICULOS_CADASTRADOS(2, "Quantidade mensal de currículos cadastrados (Notificação automática)", "R&S"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	ENCERRAR_SOLICITACAO(4, "Encerrar solicitação de pessoal", "R&S"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.CANDIDATO_NAO_APTO);
			
			return this.getListMeioComunicacao();
		}
	},
	LIBERAR_AVALIACAO_DESEMPENHO(6, "Liberar avaliação de desempenho", "R&S"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO);
			
			return this.getListMeioComunicacao();
		}
	},
	CURRICULO_AGUARDANDO_APROVACAO_MODULO_EXTERNO(3, "Existir currículo aguardando aprovação para participar de seleção (triagem do módulo externo)", "R&S") {
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL);
			
			return this.getListMeioComunicacao();
		}
	},
	CONTRATAR_COLABORADOR_AC(12, "Contratar colaborador (Integração com AC Pessoal)", "R&S") {
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_SETOR_PESSOAL);
			
			return this.getListMeioComunicacao();
		}
	},
	CONTRATAR_COLABORADOR(24, "Contratar colaborador", "R&S"){
		public TreeMap<Integer, String> meioComunicação(){
			
			this.add(MeioComunicacao.EMAIL);
			MeioComunicacao.EMAIL.add(EnviarPara.USUARIOS);
			
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.USUARIOS);
			
			return this.getListMeioComunicacao();
		}
	},
	LIBERAR_PESQUISA(7, "Liberar pesquisa", "Pesquisa"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.COLABORADOR);
			
			return this.getListMeioComunicacao();
		}
	},
	PESQUISA_NAO_LIBERADA(8, "Houver pesquisa a ser liberada (Notificação periódica)", "Pesquisa"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO(9, "Houver avaliações do período de experiência a vencer (Notificação periódica)", "Aval. Desempenho"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.GESTOR_AREA);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.USUARIOS);
			
			MeioComunicacao.EMAIL.add(EnviarPara.COLABORADOR_AVALIADO);
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA(17, "Responder avaliação do período de experiência", "Aval. Desempenho"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.USUARIOS);
			
			return this.getListMeioComunicacao();
		}
	},
	LIBERAR_AVALIACAO_TURMA(11, "Liberar avaliação da turma", "T&D") {
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.COLABORADOR);
			
			return this.getListMeioComunicacao();
		}
	},
	CADASTRAR_OCORRENCIA(22, "Cadastrar ocorrência", "Info. Funcionais"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.USUARIOS);
			
			return this.getListMeioComunicacao();
		}
	},
	CANCELAR_SITUACAO_AC(13, "Cancelar situação no AC Pessoal", "Info. Funcionais") {
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL);
			
			return this.getListMeioComunicacao();
		}
	},
	CANCELAR_CONTRATACAO_AC(18, "Cancelar contratação no AC Pessoal", "Info. Funcionais") {
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
			
			return this.getListMeioComunicacao();
		}
	},
	CANCELAR_SOLICITACAO_DESLIGAMENTO_AC(21, "Cancelar solicitação de desligamento no AC Pessoal", "Info. Funcionais") {
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
			
			return this.getListMeioComunicacao();
		}
	},
	DESLIGAR_COLABORADOR_AC(14, "Desligar colaborador no AC Pessoal", "Info. Funcionais") {
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL);

			return this.getListMeioComunicacao();
		}
	}, 
	
	CADASTRAR_LIMITE_COLABORADOR_CARGO(15, "Cadastrar limite de colaboradores por cargo", "Info. Funcionais") {
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_LIMITE_CONTRATO);

			return this.getListMeioComunicacao();
		}
	},
	EXAMES_PREVISTOS(10, "Houver exames previstos (Notificação automática)", "SESMT"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.USUARIOS);
			
			return this.getListMeioComunicacao();
		}
	},
	NAO_ABERTURA_SOLICITACAO_EPI(19, "Não houver abertura de solicitação de EPI (Notificação periódica)", "SESMT"){
		public TreeMap<Integer, String> meioComunicação(){

			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.USUARIOS);
			return this.getListMeioComunicacao();
		}
	},
	NAO_ENTREGA_SOLICITACAO_EPI(20, "Não houver entrega da solicitação de EPI (Notificação periódica)", "SESMT"){
		public TreeMap<Integer, String> meioComunicação(){

			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.USUARIOS);
			return this.getListMeioComunicacao();
		}
	},
	CADASTRAR_AFASTAMENTO(23, "Cadastrar afastamento", "SESMT"){
		public TreeMap<Integer, String> meioComunicação(){

			this.add(MeioComunicacao.EMAIL);
			MeioComunicacao.EMAIL.add(EnviarPara.USUARIOS);

			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.USUARIOS);
			
			return this.getListMeioComunicacao();
		}
	},
	GERAR_BACKUP(16, "Gerar backup (Notificação automática)", "Utilitários"){
		public TreeMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_TECNICO);
			
			return this.getListMeioComunicacao();
		}
	};

	private int id;
	private String descricao;
	private String grupo;
	private TreeMap<Integer, String> listMeioComunicacao = new TreeMap<Integer, String>();
	
	Operacao(int id, String descricao, String grupo){
		this.id = id;
		this.descricao = descricao;
		this.grupo = grupo;
	}
	
	public void add(MeioComunicacao meioComunicacao)
	{
		if(listMeioComunicacao == null)
			listMeioComunicacao = new TreeMap<Integer, String>();
		
		meioComunicacao.inicializaListaEnviarPara();
		listMeioComunicacao.put(meioComunicacao.getId(), meioComunicacao.getDescricao());
	}

	public TreeMap<Integer, String> meioComunicação(){
		TreeMap<Integer, String> meioComunicação = new TreeMap<Integer, String>();
		MeioComunicacao.setMeiosDeComunicacoes(meioComunicação);
		
		return meioComunicação;
	}
	
	public String getDescricao(){
		return descricao;
	}

	public int getId(){
		return id;
	}
	
	public String getGrupo() {
		return grupo;
	}

	public static final Map<String, Collection<Operacao>> getHashMapGrupos(){
		Map<String, Collection<Operacao>> operacoes = new LinkedHashMap<String, Collection<Operacao>>();  
	    
		for (Operacao o : Operacao.values())
		{
			if (!operacoes.containsKey(o.getGrupo()))
				operacoes.put(o.getGrupo(), new ArrayList<Operacao>());

			operacoes.get(o.getGrupo()).add(o); 
		}
		
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
	
	public static final String getGrupoById(int id){
		for (Operacao o : Operacao.values()) 
		{
			if(o.getId() == id)
				return o.getGrupo();
		}
		return "";
	}

	public static TreeMap<Integer,String> getMeioComunicacaosById(Integer id){
		for (Operacao o : Operacao.values()) 
			if(o.getId() == id)
				return o.meioComunicação();
		
		return null;
	}
	
	public TreeMap<Integer, String> getListMeioComunicacao() 
	{
		this.add(MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO);
		
		return listMeioComunicacao;
	}
}

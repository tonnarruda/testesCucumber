package com.fortes.rh.model.dicionario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public enum Operacao
{
	CADASTRO_CANDIDATO_MODULO_EXTERNO(1, "Aviso de cadastro de candidato pelo módulo externo", "Candidato"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	QTD_CURRICULOS_CADASTRADOS(2, "Aviso automático da quantidade de currículos cadastros por mês", "Candidato"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	SOLICITACAO_CANDIDATOS_MODULO_EXTERNO(3, "Exibir solicitações com candidatos do módulo externo", "Candidato") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL);

			return this.getListMeioComunicacao();
		}
	},
	ENCERRAMENTO_SOLICITACAO(4, "Encerramento da solicitação de pessoal", "Solicitação"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.CANDIDATO_NAO_APTO);
			
			return this.getListMeioComunicacao();
		}
	},
	ALTERAR_STATUS_SOLICITACAO(5, "Alteração no status da solicitação de pessoal", "Solicitação"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.SOLICITANTE_SOLICITACAO);
			
			return this.getListMeioComunicacao();
		}
	},
	ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO(6, "Enviar lembrete avaliação desempenho", "Solicitação"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO);
			
			return this.getListMeioComunicacao();
		}
	},
	LIBERAR_QUESTIONARIO(7, "Liberar pesquisa", "Pesquisa"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.COLABORADOR);
			
			return this.getListMeioComunicacao();
		}
	},
	LEMBRETE_QUESTIONARIO_NAO_LIBERADO(8, "Lembrete automático de pesquisa não liberada", "Pesquisa"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO(9, "Aviso automático das avaliações do período de experiência a vencer", "Período de Experiência"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_RH);
			
			return this.getListMeioComunicacao();
		}
	},
	EXAMES_PREVISTOS(10, "Aviso automático de exames previstos", "Período de Experiência"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS);
			
			return this.getListMeioComunicacao();
		}
	},
	LIBERAR_TURMA(11, "Liberar turma", "Curso") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.COLABORADOR);
			
			return this.getListMeioComunicacao();
		}
	},
	CONTRATAR_COLABORADOR(12, "Contratação de Colaborador", "Colaborador") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_SETOR_PESSOAL);
			
			return this.getListMeioComunicacao();
		}
	}, 
	CANCELAR_SITUACAO_AC(13, "Cancelamento de Situação no AC Pessoal", "Colaborador") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL);
			
			return this.getListMeioComunicacao();
		}
	},
	DESLIGAR_COLABORADOR_AC(14, "Desligar colaborador no AC", "Colaborador") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.CAIXA_MENSAGEM);
			
			MeioComunicacao.CAIXA_MENSAGEM.add(EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL);

			return this.getListMeioComunicacao();
		}
	}, 
	CONFIGURACAO_LIMITE_COLABORADOR(15, "Configuração do limite de colaboradores por cargo", "Colaborador") {
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_LIMITE_CONTRATO);

			return this.getListMeioComunicacao();
		}
	},
	BACKUP_AUTOMATICO(16, "Aviso automático de backup", "Sistema"){
		public HashMap<Integer, String> meioComunicação(){
			this.add(MeioComunicacao.EMAIL);
			
			MeioComunicacao.EMAIL.add(EnviarPara.RESPONSAVEL_TECNICO);
			
			return this.getListMeioComunicacao();
		}
	};

	private int id;
	private String descricao;
	private String grupo;
	private HashMap<Integer, String> listMeioComunicacao = new HashMap<Integer, String>();
	
	Operacao(int id, String descricao, String grupo){
		this.id = id;
		this.descricao = descricao;
		this.grupo = grupo;
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
	
	public String getGrupo() {
		return grupo;
	}

	public static final Map<String, Collection<Operacao>> getHashMapGrupos(){
		Map<String, Collection<Operacao>> operacoes= new LinkedHashMap<String, Collection<Operacao>>();  
	    
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

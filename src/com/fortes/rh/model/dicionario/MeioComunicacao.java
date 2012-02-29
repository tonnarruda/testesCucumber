package com.fortes.rh.model.dicionario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
public enum MeioComunicacao
{
	SELECIONAR_MEIO_COMUNICACAO(0, "Selecione..."),
	CAIXA_MENSAGEM(1, "Caixa de mensagem"),
	EMAIL(2, "Email"){
		public HashMap<Integer, String> getEnviarPara(int operacaoId){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			
			if 	(possuiCandidatoNaoApto(operacaoId))
				EnviarPara.setCandidatoNaoApto(enviarPara);
			
			if 	(possuiLiberador(operacaoId))
				EnviarPara.setLiberador(enviarPara);
			
			if 	(possuiSolicitante(operacaoId))
				EnviarPara.setSolicitante(enviarPara);
			
			if 	(possuiAvaliadorAvaliacaoDesempenho(operacaoId))
				EnviarPara.setAvaliadorAvaliacaoDesempenho(enviarPara);
			
			if 	(possuiColaborador(operacaoId))
				EnviarPara.setColaborador(enviarPara);			
			
			if 	(possuiResponsavelRH(operacaoId))
				EnviarPara.setResponsavelRH(enviarPara);
			
			if 	(possuiPerfilAutorizadoExamesPrevistos(operacaoId))
				EnviarPara.setPerfilAutorizadoExamesPrevistos(enviarPara);

			return enviarPara;
		}
	};

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
	
	public static void setEmail(HashMap<Integer, String> meioComunicação) 
	{
		meioComunicação.put(MeioComunicacao.EMAIL.getId(), MeioComunicacao.EMAIL.getDescricao());
	}

	public static void setMeiosDeComunicacoes(HashMap<Integer, String> meioComunicação) {
		for (MeioComunicacao meioComunicacao : values()) 
			meioComunicação.put(meioComunicacao.getId(), meioComunicacao.getDescricao());
	}
	
	private static boolean possuiResponsavelRH(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId());
		operacoes.add(Operacao.QTD_CURRICULOS_CADASTRADOS.getId());
		operacoes.add(Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO.getId());
		operacoes.add(Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiCandidatoNaoApto(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.ENCERRAMENTO_SOLICITACAO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiLiberador(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.LIBERAR_SOLICITACAO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiSolicitante(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.ALTEREAR_STATUS_SOLICITACAO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiAvaliadorAvaliacaoDesempenho(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiColaborador(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.getId());
		operacoes.add(Operacao.LIBERAR_QUESTIONARIO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiPerfilAutorizadoExamesPrevistos(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.EXAMES_PREVISTOS.getId());
		
		return operacoes.contains(operacaoId);
	}
}

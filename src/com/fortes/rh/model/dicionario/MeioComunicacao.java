package com.fortes.rh.model.dicionario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
public enum MeioComunicacao
{
	SELECIONAR_MEIO_COMUNICACAO(0, "Selecione..."){
		public HashMap<Integer, String> getEnviarPara(int operacaoId){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			return enviarPara;
		}
		
	},
	CAIXA_MENSAGEM(1, "Caixa de mensagem"){
		public HashMap<Integer, String> getEnviarPara(int operacaoId){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			
			if 	(possuiGerenciadorDeMensagemPeriodoExperienciaMensagem(operacaoId))
				EnviarPara.setGerenciadorDeMensagemPeriodoExperiencia(enviarPara);

			if 	(possuiRecebeMensagemPeriodoExperiencia(operacaoId))
				EnviarPara.setRecebeMensagemPeriodoExperiencia(enviarPara);
			
			return enviarPara;
		}
		
	},
	EMAIL(2, "Email"){
		public HashMap<Integer, String> getEnviarPara(int operacaoId){
			HashMap<Integer, String> enviarPara = new HashMap<Integer, String>();
			
			if 	(possuiCandidatoNaoAptoEmail(operacaoId))
				EnviarPara.setCandidatoNaoApto(enviarPara);
			
			if 	(possuiSolicitanteEmail(operacaoId))
				EnviarPara.setSolicitante(enviarPara);
			
			if 	(possuiAvaliadorAvaliacaoDesempenhoEmail(operacaoId))
				EnviarPara.setAvaliadorAvaliacaoDesempenho(enviarPara);
			
			if 	(possuiColaboradorEmail(operacaoId))
				EnviarPara.setColaborador(enviarPara);			
			
			if 	(possuiResponsavelRHEmail(operacaoId))
				EnviarPara.setResponsavelRH(enviarPara);

			if 	(possuiResponsavelSetorPessoalEmail(operacaoId))
				EnviarPara.setResponsavelSetorPessoal(enviarPara);
			
			if 	(possuiPerfilAutorizadoExamesPrevistosEmail(operacaoId))
				EnviarPara.setPerfilAutorizadoExamesPrevistos(enviarPara);
			
			if (possuiResponsavelTecnico(operacaoId))
				EnviarPara.setResponsavelTecnico(enviarPara);

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
	
	private static boolean possuiResponsavelRHEmail(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId());
		operacoes.add(Operacao.QTD_CURRICULOS_CADASTRADOS.getId());
		operacoes.add(Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO.getId());
		operacoes.add(Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiResponsavelTecnico(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.BACKUP_AUTOMATICO.getId());
		
		return operacoes.contains(operacaoId);
	}

	private static boolean possuiResponsavelSetorPessoalEmail(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.CONTRATAR_COLABORADOR.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiCandidatoNaoAptoEmail(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.ENCERRAMENTO_SOLICITACAO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiSolicitanteEmail(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.ALTERAR_STATUS_SOLICITACAO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiAvaliadorAvaliacaoDesempenhoEmail(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiColaboradorEmail(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.getId());
		operacoes.add(Operacao.LIBERAR_QUESTIONARIO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiPerfilAutorizadoExamesPrevistosEmail(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.EXAMES_PREVISTOS.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiGerenciadorDeMensagemPeriodoExperienciaMensagem(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId());
		
		return operacoes.contains(operacaoId);
	}
	
	private static boolean possuiRecebeMensagemPeriodoExperiencia(Integer operacaoId){
		Collection<Integer> operacoes = new ArrayList<Integer>();
		operacoes.add(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId());
		
		return operacoes.contains(operacaoId);
	}
}

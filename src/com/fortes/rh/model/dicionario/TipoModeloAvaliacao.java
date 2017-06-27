package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoModeloAvaliacao extends LinkedHashMap<Character, String> {
	private static final long serialVersionUID = 1L;

	public static final char SOLICITACAO = 'S';
	public static final char DESEMPENHO = 'D';
	public static final char ACOMPANHAMENTO_EXPERIENCIA = 'A';
	public static final char AVALIACAO_DESEMPENHO = 'V';
	public static final char AVALIACAO_ALUNO = 'L';

	public TipoModeloAvaliacao() {
		put(SOLICITACAO, "Solicitação");
		put(DESEMPENHO, "Avaliação de Desempenho");
		put(ACOMPANHAMENTO_EXPERIENCIA, "Acompanhamento do Período de Experiência");
		put(AVALIACAO_DESEMPENHO, "Avaliação de Desempenho");
		put(AVALIACAO_ALUNO, "Avaliação de Aluno");
	}

	public char getSolicitacao() {
		return SOLICITACAO;
	}

	public char getDesempenho() {
		return DESEMPENHO;
	}

	public static char getAcompanhamentoExperiencia() {
		return ACOMPANHAMENTO_EXPERIENCIA;
	}

	public static char getAvaliacaoDesempenho() {
		return AVALIACAO_DESEMPENHO;
	}

	public static char getAvaliacaoAluno() {
		return AVALIACAO_ALUNO;
	}

	public static boolean isAcompanhamentoExperienciaOuNulo(Character tipoModeloAvaliacao) {
		return tipoModeloAvaliacao == null || tipoModeloAvaliacao.equals(ACOMPANHAMENTO_EXPERIENCIA);
	}

	public static boolean isAcompanhamentoExperiencia(Character tipoModeloAvaliacao) {
		return tipoModeloAvaliacao != null && tipoModeloAvaliacao.equals(ACOMPANHAMENTO_EXPERIENCIA);

	}
	public static boolean isDesempenho(Character tipoModeloAvaliacao) {
		return tipoModeloAvaliacao != null && tipoModeloAvaliacao.equals(TipoModeloAvaliacao.DESEMPENHO);
	}

}
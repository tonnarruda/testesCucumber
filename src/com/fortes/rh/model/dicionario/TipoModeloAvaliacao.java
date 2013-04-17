package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoModeloAvaliacao extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 1L;
	
	public static final char SOLICITACAO = 'S';
	public static final char DESEMPENHO = 'D';
	public static final char ACOMPANHAMENTO_EXPERIENCIA = 'A';
	public static final char AVALIACAO_DESEMPENHO = 'V';
	
	public TipoModeloAvaliacao()
	{
		put(SOLICITACAO, "Solicitação");
		put(DESEMPENHO, "Avaliação de Desempenho");
		put(ACOMPANHAMENTO_EXPERIENCIA, "Acompanhamento do Período de Experiência");
		put(AVALIACAO_DESEMPENHO, "Avaliação de Desempenho");
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
}
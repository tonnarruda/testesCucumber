package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoModeloAvaliacao extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 1L;
	
	public static final char SOLICITACAO = 'S';
	public static final char DESEMPENHO = 'D';
	public static final char ACOMPANHAMENTO_EXPERIENCIA = 'A';
	
	public TipoModeloAvaliacao()
	{
		put(SOLICITACAO, "Solicitação");
		put(DESEMPENHO, "Desempenho");
		put(ACOMPANHAMENTO_EXPERIENCIA, "Acompanhamento de Experiência");
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
}
package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;


@SuppressWarnings("serial")
public class TipoMensagem extends LinkedHashMap<Character, String>
{
	public static final char RECRUTAMENTO_SELECAO = 'R';
	public static final char CARGO_SALARIO = 'C';
	public static final char PESQUISAS_AVAL_DISPONIVEIS = 'P';
	public static final char AVALIACAO_DESEMPENHO = 'A';
	public static final char AVALIACOES_TED = 'T';
	public static final char INFO_FUNCIONAIS = 'F';
	public static final char SESMT = 'S';
	public static final char UTILITARIOS = 'U';

	public TipoMensagem() 
	{
		put(RECRUTAMENTO_SELECAO, "Recrutamento & Seleção");
		put(CARGO_SALARIO, "Cargos & Salários");
		put(PESQUISAS_AVAL_DISPONIVEIS, "Pesquisas/Avaliações Disponíveis");
		put(AVALIACAO_DESEMPENHO, "Avaliações de Desempenho");
		put(AVALIACOES_TED, "Avaliações de T&D");
		put(INFO_FUNCIONAIS, "Informações Funcionais");
		put(SESMT, "SESMT");
		put(UTILITARIOS, "Utilitários");
	}
}
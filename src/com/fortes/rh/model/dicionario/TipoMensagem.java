package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;


@SuppressWarnings("serial")
public class TipoMensagem extends LinkedHashMap<Character, String>
{
	public static final char CARGO_SALARIO = 'C';
	public static final char PESQUISAS_AVAL_DISPONIVEIS = 'P';
	public static final char AVALIACAO_DESEMPENHO = 'A';
	public static final char AVALIACOES_TED = 'T';
	public static final char INFO_FUNCIONAIS = 'F';
	public static final char SESMT = 'S';
	public static final char UTILITARIOS = 'U';

	public static final char DESLIGAMENTO = 'D';

	public TipoMensagem() 
	{
		put(CARGO_SALARIO, "Cargo & Salário");
		put(PESQUISAS_AVAL_DISPONIVEIS, "Pesquisas/Avaliações Disponíveis");
		put(AVALIACAO_DESEMPENHO, "Avaliação de Desempenho");
		put(AVALIACOES_TED, "Avaliações de Treinamento & Desenvolvimento");
		put(INFO_FUNCIONAIS, "Informações Funcionais");
		put(SESMT, "SESMT");
		put(UTILITARIOS, "Utilitários");
		put(DESLIGAMENTO, "Desligamento");
	}
}
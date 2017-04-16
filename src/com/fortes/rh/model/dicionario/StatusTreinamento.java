package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class StatusTreinamento extends LinkedHashMap<String, String>
{
	public static final String SEM_TREINAMENTO = "ST";
	public static final String NUNCA_REALIZOU_NENHUM_TREINAMENTO = "NRT";
	public static final String APROVADO_REPROVADO = "AR";
	public static final String APROVADO = "A";
	public static final String REPROVADO = "R";

	public StatusTreinamento()
	{
		put(NUNCA_REALIZOU_NENHUM_TREINAMENTO, "Nunca realizou nenhum curso na empresa");
		put(SEM_TREINAMENTO, "Nunca realizaram os cursos selecionados");
		put(APROVADO_REPROVADO, "Aprovados e Reprovados");
		put(APROVADO, "Aprovados");
		put(REPROVADO, "Reprovados");
	}
	
	public static String getDescricao(String tipo)
	{
		return new StatusAprovacaoSolicitacao().get(tipo);
	}
}
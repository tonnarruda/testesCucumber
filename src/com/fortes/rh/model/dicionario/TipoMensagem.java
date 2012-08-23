package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;


@SuppressWarnings("serial")
public class TipoMensagem extends LinkedHashMap<Object, Object>
{
	public static final char CARGO_SALARIO = 'C';
	public static final char AVALIACAO_DESEMPENHO = 'A';
	public static final char INFO_FUNCIONAIS = 'F';
	public static final char SESMT = 'S';
	public static final char UTILITARIOS = 'U';

	public static final char DESLIGAMENTO = 'D';
}
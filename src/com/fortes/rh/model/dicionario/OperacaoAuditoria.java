package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class OperacaoAuditoria extends LinkedHashMap
{
	private static final String TODAS = "";
	private static final String INSERT = "save";
	private static final String UPDATE = "update";
	private static final String DELETE = "remove";

	@SuppressWarnings("unchecked")
	public OperacaoAuditoria()
	{
		put(TODAS, "Todas");
		put(INSERT, "Cadastro");
		put(UPDATE, "Edição");
		put(DELETE, "Exclusão");
	}
}
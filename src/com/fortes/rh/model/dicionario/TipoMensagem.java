package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

import com.fortes.rh.security.SecurityUtil;
import com.opensymphony.xwork.ActionContext;


@SuppressWarnings("serial")
public class TipoMensagem extends LinkedHashMap<Character, String>
{
	public static final char RES = 'R';
	public static final char CARGO_SALARIO = 'C';
	public static final char PESQUISAS = 'P';
	public static final char AVALIACAO_DESEMPENHO = 'A';
	public static final char TED = 'T';
	public static final char INFO_FUNCIONAIS = 'F';
	public static final char SESMT = 'S';
	public static final char UTILITARIOS = 'U';

	public TipoMensagem() 
	{
		put(RES, "Recrutamento & Seleção");
		put(CARGO_SALARIO, "Cargos & Salários");
		put(PESQUISAS, "Pesquisas");
		put(AVALIACAO_DESEMPENHO, "Aval. Desempenho");
		put(TED, "Treinamento e Desenvolvimento");
		put(INFO_FUNCIONAIS, "Informações Funcionais");
		put(SESMT, "SESMT");
		put(UTILITARIOS, "Utilitários");
	}

	public TipoMensagem(Boolean verificaRoles) 
	{
		if(verificaRoles)
		{
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CX_MENSAGEM_RES"}) )
				put(RES, "Recrutamento & Seleção");
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CX_MENSAGEM_CES"}) )
				put(CARGO_SALARIO, "Cargos & Salários");
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CX_MENSAGEM_PESQUISAS"}) )
				put(PESQUISAS, "Pesquisas");
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CX_MENSAGEM_AV_DESMPENHO"}) )
				put(AVALIACAO_DESEMPENHO, "Aval. Desempenho");
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CX_MENSAGEM_TED"}) )
				put(TED, "Treinamento e Desenvolvimento");
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CX_MENSAGEM_INFO_FUNCIONAIS"}) )
				put(INFO_FUNCIONAIS, "Informações Funcionais");
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CX_MENSAGEM_SESMT"}) )
				put(SESMT, "SESMT");
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_CX_MENSAGEM_UTILITARIOS"}) )
				put(UTILITARIOS, "Utilitários");
		}else
			new TipoMensagem();
	}
}
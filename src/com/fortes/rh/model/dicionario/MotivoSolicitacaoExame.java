package com.fortes.rh.model.dicionario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;

public class MotivoSolicitacaoExame extends LinkedHashMap<String, String>
{
	private static final long serialVersionUID = -6322555023631514807L;
	
	public static final String ADMISSIONAL = "ADMISSIONAL";
	public static final String PERIODICO = "PERIODICO";
	public static final String RETORNO = "RETORNO";
	public static final String MUDANCA = "MUDANCA";
	public static final String DEMISSIONAL = "DEMISSIONAL";
	public static final String CONSULTA = "CONSULTA";
	public static final String ATESTADO = "ATESTADO";
	public static final String SOLICITACAOEXAME = "SOLICITACAOEXAME";
	
	private static MotivoSolicitacaoExame instancia;

	public static MotivoSolicitacaoExame getInstance()
	{
		if (instancia == null)
			instancia = new MotivoSolicitacaoExame();

		return instancia;
	}

	private MotivoSolicitacaoExame()
	{
		put(CONSULTA, "Consulta comum");
		put(ADMISSIONAL, "ASO Admissional");
		put(PERIODICO, "ASO Periódico");
		put(RETORNO, "ASO Retorno ao Trabalho");
		put(MUDANCA, "ASO Mudança de Função");
		put(DEMISSIONAL, "ASO Demissional");
		put(ATESTADO, "Apresentação de Atestado Externo");
		put(SOLICITACAOEXAME, "Solicitação de Exame");
	}

	public static String[] getMarcados(String[] marcados)
	{
		getInstance();
		if(marcados == null || marcados.length == 0)
			return new String[]{};
		
		String[] chaves = new String[marcados.length];
		String[] value = StringUtil.converteCollectionToArrayString(instancia.keySet());
		
		int cont = 0;
		for (String marcado : marcados)
		{
			chaves[cont++] = value[Integer.parseInt(marcado)]; 
		}
		
		return chaves;
	}

	public static Collection<CheckBox> montaCheckListBox()
	{
		getInstance();
		
		Collection<CheckBox> checkBoxs = new ArrayList<CheckBox>();
		
		Collection<String> chaves = instancia.keySet();
		Long cont = 0L;
		for (String chave : chaves)
		{
			CheckBox checkBox = new CheckBox();
			checkBox.setId(cont++);
			checkBox.setNome(instancia.get(chave));
			checkBoxs.add(checkBox);
		}
		
		return checkBoxs;
	}
}
package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class GrupoRiscoESocial extends LinkedHashMap<String, String>
{
	private static final long serialVersionUID = 9009590293178247206L;
	
	public static final String FISICO = "01.01";
	public static final String QUIMICO = "02.01";
	public static final String BIOLOGICO = "03.01";
	public static final String ERGONOMICO_BIOMECANICO = "04.01";
	public static final String ERGONOMICO_MOBILIARIO_E_EQUIPAMENTO = "04.02";
	public static final String ERGONOMICO_ORGANIZACIONAL = "04.03";
	public static final String ERGONOMICO_PSICOSSOCIAL_COGNITIVO = "04.04";
	public static final String ACIDENTE = "05.01";
	public static final String PERICULOSO = "06.01";
	public static final String PENOSO = "07.01";
	public static final String ASSOCIACAO_DE_FATORES_DE_RISCO = "08.01";
	public static final String AUSENCIA_FATOR_DE_RISCO = "09.01";
	public static final String SEM_GRUPO_CONFIGURADO = "0";
	
	private static GrupoRiscoESocial instance;
	
	public static GrupoRiscoESocial getInstance()
	{
		if (instance == null)
			instance = new GrupoRiscoESocial();
		
		return instance;
	}

	private GrupoRiscoESocial()
	{
		put(FISICO, "Físico");
		put(QUIMICO, "Químico");
		put(BIOLOGICO, "Biológico");
		put(ERGONOMICO_BIOMECANICO, "Ergonômico - Biomecânicos");
		put(ERGONOMICO_MOBILIARIO_E_EQUIPAMENTO, "Ergonômico - Mobiliário e Equipamentos");
		put(ERGONOMICO_ORGANIZACIONAL, "Ergonômico - Organizacionais");
		put(ERGONOMICO_PSICOSSOCIAL_COGNITIVO, "Ergonômico - Psicossociais/Cognitivos");
		put(ACIDENTE, "Acidente");
		put(PERICULOSO, "Periculoso");
		put(PENOSO, "Penoso");
		put(ASSOCIACAO_DE_FATORES_DE_RISCO, "Associação de Fatores de Risco");
	}
	
	public static GrupoRiscoESocial getGrupoRiscoESocialListagemDeRiscos()
	{
		if (instance == null)
			instance = new GrupoRiscoESocial();
		
		instance.put(SEM_GRUPO_CONFIGURADO, "Sem grupo configurado");
		return instance;
	}
	
}
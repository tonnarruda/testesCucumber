package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class Sexo extends LinkedHashMap<String, String>
{
	private static final long serialVersionUID = 4215324525333421727L;
	
	public static final String INDIFERENTE = "I";
	public static final String MASCULINO = "M";
	public static final String FEMININO = "F";

	public Sexo()
	{
		put(INDIFERENTE, "Indiferente");
		put(MASCULINO, "Masculino");
		put(FEMININO, "Feminino");
	}

	public static String getDescSexoParaFicha(char sexo)
	{
		String masc = " ";
		String fem = " ";

		if(sexo == 'M')
			masc = "x";
		else if(sexo == 'F')
			fem = "x";

		return "(" + masc + ") Masc.  (" + fem + ") Fem.";
	}
	
	public static String getDescricao(char sexo){
		
		if(sexo == 'M')
			return new Sexo().get(MASCULINO);
		else if (sexo == 'F')
			return new Sexo().get(FEMININO);
		
		return INDIFERENTE;
	} 
}

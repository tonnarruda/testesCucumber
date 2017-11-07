package com.fortes.rh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CnpjUtil
{
	public static String calculaDigitoVerificador(String cnpj)
	{
		String primeiraBase = "543298765432";

		int somaBase1 = 0;

		for (int i = 0; i < 12; i++)
		{
			somaBase1 = somaBase1 + (Integer.valueOf(cnpj.substring(i, (i + 1))) * Integer.valueOf(primeiraBase.substring(i, (i + 1))));
		}
		int restoBase1 = somaBase1 % 11;
		int primeiroDv = 0;

		if (restoBase1 > 1)
			primeiroDv = 11 - restoBase1;

		cnpj = cnpj + primeiroDv;

		String segundaBase = 6 + primeiraBase;

		int somaBase2 = 0;

		for (int i = 0; i < 13; i++)
		{
			somaBase2 = somaBase2 + (Integer.valueOf(cnpj.substring(i, (i + 1))) * Integer.valueOf(segundaBase.substring(i, (i + 1))));
		}

		int restoBase2 = somaBase2 % 11;
		int segundoDv = 0;

		if (restoBase2 > 1)
			segundoDv = 11 - restoBase2;

		String dvRetorno = primeiroDv + "" + segundoDv;

		return dvRetorno;
	}

	public static String formata(String cnpj, boolean formataBaseCnpj)
	{
		if (cnpj == null || cnpj.trim().equals("") || cnpj.length() < 11)
			return null;

		if (cnpj.length() < 14)
			cnpj = cnpj + calculaDigitoVerificador(cnpj);
		
		String padrao = "(\\d{8})(\\d{4})(\\d{2})";
		String mascara = "$1/$2-$3";
		
		if(formataBaseCnpj){
			padrao = "(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})";
			mascara = "$1.$2.$3/$4-$5";
		} 
		
		return formata(cnpj, padrao, mascara);
	}
	
	private static String formata(String cnpj, String padrao, String mascara) {
		Pattern pattern = Pattern.compile(padrao);
		Matcher matcher = pattern.matcher(cnpj);
		if (matcher.matches()) 
			cnpj = matcher.replaceAll(mascara);
		return cnpj;		
	}
}

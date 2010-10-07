package com.fortes.rh.util;

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

	public static String formata(String cnpj)
	{
		if (cnpj == null || cnpj.trim().equals("") || cnpj.length() < 11)
			return null;

		String base = cnpj.substring(0, 8);
		String complemento = cnpj.substring(8, 12);
		String digitoVerificador;
		if (cnpj.length() == 14)
		{
			digitoVerificador = cnpj.substring(12, 14);
		}
		else
		{
			digitoVerificador = calculaDigitoVerificador(cnpj);
		}

		return base.concat("/").concat(complemento).concat("-").concat(digitoVerificador);
	}
}

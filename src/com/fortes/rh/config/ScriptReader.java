package com.fortes.rh.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class ScriptReader
{
	private static StringBuilder getScript(File file, String versao) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String linha = "";
		StringBuilder scripts = new StringBuilder();
		boolean lendo = false;
		boolean versaoAtual = false;

		while(br.ready())
		{
			linha = br.readLine();

			if (versaoAtual)
				if (linha.indexOf("-- versao") > -1)
					lendo = true;

			if (linha.indexOf("-- versao " + versao) > -1)
				versaoAtual = true;

			if (lendo == true && !linha.trim().equals("") && linha.indexOf("-- versao") == -1)
				scripts.append(linha.replaceAll("\t", " "));
		}

		return scripts;
	}

	public static String[] getComandos(File file, String versao) throws IOException
	{
		StringBuilder linhas = getScript(file, versao);
		return Pattern.compile("--.go").split(linhas, 0);
	}
}
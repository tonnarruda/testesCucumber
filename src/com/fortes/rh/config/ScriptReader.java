package com.fortes.rh.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ScriptReader
{
	
	private static final String COMMAND_DELIMITER = "--.go";
	private static final String VERSION_DELIMITER = "-- versao";

	/**
	 * Retorna todos os comandos SQLs do <code>script</code> após
	 * <code>versao</code> especificada.
	 */
	public static String[] getComandos(File script, String versao) 
	{
		StringBuilder linhas = getScript(script, versao);
		return Pattern.compile(COMMAND_DELIMITER).split(linhas, 0);
	}
	
	private static StringBuilder getScript(File script, String versao)
	{
		BufferedReader scriptReader = null;
		try {
			scriptReader = new BufferedReader(new InputStreamReader(new FileInputStream(script), "UTF-8"));
			return extraiTodosOsComandosSqlAposVersao(versao, scriptReader);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao extrair comandos SQL do script " + script.getName() + ".", e);
		} finally {
			try {
				if (scriptReader != null)
					scriptReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Erro ao fechar reader do " + script.getName() + ".", e);
			}
		}
	}

	private static StringBuilder extraiTodosOsComandosSqlAposVersao(String versao, BufferedReader scriptReader) throws IOException {
		StringBuilder scripts = new StringBuilder();
		boolean lendo = false;
		boolean versaoAtualEncontrada = false;
		while(scriptReader.ready())
		{
			String linha = scriptReader.readLine();

			if (versaoAtualEncontrada)
				if (ehLinhaDeVersao(linha))
					lendo = true;

			if (ehLinhaDaVersaoAtual(versao, linha))
				versaoAtualEncontrada = true;

			if (lendo && naoEhLinhaVazia(linha) && !ehLinhaDeVersao(linha))
				scripts.append(linha.replaceAll("\t", " "));
		}
		return scripts;
	}

	private static boolean naoEhLinhaVazia(String linha) {
		return StringUtils.isNotEmpty(linha.trim());
	}

	private static boolean ehLinhaDeVersao(String linha) {
		return linha.indexOf(VERSION_DELIMITER) > -1;
	}

	private static boolean ehLinhaDaVersaoAtual(String versao, String linha) {
		return linha.indexOf(VERSION_DELIMITER + " " + versao) > -1;
	}

}
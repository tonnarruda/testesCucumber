package com.fortes.rh.util.geradorCodigo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gerador
{
	public final String WORKSPACE = "/home/paula/Workspaces/MyEclipseProfessional2014/FortesRH";
	public final char separator = java.io.File.separatorChar;
	public String NOME_CLASSE;
	public String NOME_CLASSE_MINUSCULO;
	public String NOME_PACOTE;
	
	private StringBuilder codigoTemplate;
	
	private String VAR_NOME_CLASSE = "#NOME_CLASSE#";
	private String VAR_NOME_CLASSE_MINUSCULO = "#NOME_CLASSE_MINUSCULO#";
	private String VAR_NOME_PACOTE = "#NOME_PACOTE#";

	public Gerador(String nome_classe, String nome_classe_minusculo, String nome_pacote)
	{
		super();
		NOME_CLASSE = nome_classe;
		NOME_CLASSE_MINUSCULO = nome_classe_minusculo;
		NOME_PACOTE = nome_pacote;
		
	}

	private void criarFile(String path) throws IOException
	{
		if(this.codigoTemplate != null)
		{
			FileWriter fileWriter = new FileWriter(path);
			fileWriter.write(replaceVariaveis());

			fileWriter.flush();
			fileWriter.close();
		}
	}

	private void lerTemplate(String pathTemplate) throws IOException
	{
		StringBuilder codigoTemplate = new StringBuilder();
		BufferedReader in = new BufferedReader(new FileReader(pathTemplate));

		while (in.ready())
			codigoTemplate.append(in.readLine() + "\n");

		in.close();

		this.codigoTemplate = new StringBuilder(codigoTemplate);
	}

	private String replaceVariaveis()
	{
		if(this.codigoTemplate == null || this.codigoTemplate.toString().trim().equals(""))
			return "";

		String result = this.codigoTemplate.toString();

		String[] REPLACES = new String[] { NOME_CLASSE, NOME_CLASSE_MINUSCULO, NOME_PACOTE };
		Pattern[] PATTERNS = new Pattern[] { Pattern.compile(VAR_NOME_CLASSE), Pattern.compile(VAR_NOME_CLASSE_MINUSCULO), Pattern.compile(VAR_NOME_PACOTE) };

		for (int i = 0; i < PATTERNS.length; i++)
		{
			Matcher matcher = PATTERNS[i].matcher(result);
			result = matcher.replaceAll(REPLACES[i]);
		}

		return result;
	}

	private void concatenarFile(String path, String substituir) throws IOException
	{
		if(this.codigoTemplate != null)
		{
			StringBuilder conteudo = lerXML(path);

			Pattern pattern = Pattern.compile(substituir);
			Matcher matcher = pattern.matcher(conteudo);
			
			FileWriter fileWriter = new FileWriter(path);
			fileWriter.write(matcher.replaceAll(replaceVariaveis()));

			fileWriter.flush();
			fileWriter.close();
		}
	}

	private StringBuilder lerXML(String pathXML) throws IOException
	{
		StringBuilder codigoTemplate = new StringBuilder();
		BufferedReader in = new BufferedReader(new FileReader(pathXML));

		while (in.ready())
			codigoTemplate.append(in.readLine() + "\n");

		in.close();

		return codigoTemplate;
	}

	public void criarClass(String diretorio, String fileName, String template, String subDir) throws IOException
	{
		lerTemplate(WORKSPACE + separator + "templates" + separator + template);
		
		criarFile(WORKSPACE + separator + subDir + separator + "com" + separator + "fortes" + separator + "rh"
				+ separator + diretorio + separator + NOME_PACOTE 
				+ separator + fileName);
	}
	
	public void criarFTL(String fileName, String template) throws IOException
	{
		lerTemplate(WORKSPACE + separator + "templates" + separator + template);
		
		criarFile(WORKSPACE + separator + "web" + separator + "WEB-INF" + separator + "forms"
				+ separator + NOME_PACOTE 
				+ separator + fileName);
	}
	
	public void editarXml(String diretorio, String fileAntigo, String template, String substituir) throws IOException
	{
		lerTemplate(WORKSPACE + separator + "templates" + separator + template + ".xml");
		
		if(diretorio == null)
		{
			concatenarFile(WORKSPACE + separator + "src" + separator + fileAntigo + ".xml", substituir);
		}
		else
		{
			concatenarFile(WORKSPACE + separator + "src" + separator + "com" + separator + "fortes" + separator + "rh"
					+ separator + diretorio + separator + NOME_PACOTE 
					+ separator + fileAntigo + ".xml", substituir);
		}
	}
}
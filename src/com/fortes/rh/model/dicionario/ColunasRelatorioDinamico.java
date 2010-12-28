package com.fortes.rh.model.dicionario;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ColunasRelatorioDinamico extends LinkedHashMap<String, String>
{
	private static final long serialVersionUID = 9009590293178247206L;
	
	private static ColunasRelatorioDinamico instance;
	private static HashMap<String, Integer> tamanhoCampo = new LinkedHashMap<String, Integer>();
	
	public static ColunasRelatorioDinamico getInstance()
	{
		if (instance == null)
			instance = new ColunasRelatorioDinamico();
		
		return instance;
	}
	
	public static HashMap<String, Integer> getTamanhos()
	{
		inicializaTamanhos();
		return tamanhoCampo;
	}
	
	private static void inicializaTamanhos() 
	{
		tamanhoCampo.put("Nome", 150);
		tamanhoCampo.put("Nome Comercial", 150);
		tamanhoCampo.put("Matrícula", 40);
		tamanhoCampo.put("Data Admissão", 50);
		tamanhoCampo.put("Cargo Atual", 120);
		tamanhoCampo.put("Estado Civil", 120);
		tamanhoCampo.put("Nome da Mãe", 150);
		tamanhoCampo.put("Nome do Pai", 150);
		tamanhoCampo.put("Data de Desligamento", 50);
		tamanhoCampo.put("Vinculo", 60);
		tamanhoCampo.put("Cpf", 60);
		tamanhoCampo.put("Pis", 60);
		tamanhoCampo.put("Rg", 70);
		tamanhoCampo.put("Orgão Emissor", 40);
		tamanhoCampo.put("Deficiência", 60);
		tamanhoCampo.put("Data de Expedição(RG)", 50);
		tamanhoCampo.put("Sexo", 40);
		tamanhoCampo.put("Data de Nascimento", 50);
		tamanhoCampo.put("Conjugue", 150);
		tamanhoCampo.put("Qtd de Filhos", 30);
		tamanhoCampo.put("Número da Habilitação", 50);
		tamanhoCampo.put("Emissão da Habilitação", 50);
		tamanhoCampo.put("Vencimento da Habilit.", 50);
		tamanhoCampo.put("Categoria da Habilit.", 60);
		tamanhoCampo.put("Logradouro", 120);
		tamanhoCampo.put("Comp. do Logradouro", 120);
		tamanhoCampo.put("Número do Logradouro", 60);
		tamanhoCampo.put("Bairro", 100);
		tamanhoCampo.put("Cep", 60);
		tamanhoCampo.put("Email", 150);
		tamanhoCampo.put("Celular", 40);
		tamanhoCampo.put("Fone Fixo", 40);		
	}

	public static int getTamanho(String key)
	{
		inicializaTamanhos();
		return tamanhoCampo.get(key);
	}

	private ColunasRelatorioDinamico()
	{
		put("Nome", "nome");
		put("Nome Comercial", "nomeComercial");
		put("Matrícula", "matricula");
		put("Data Admissão", "dataAdmissaoFormatada");
		put("Cargo Atual", "faixaSalarial.cargo.nome");
		put("Estado Civil", "pessoal.estadoCivilDic");
		put("Nome da Mãe", "pessoal.mae");
		put("Nome do Pai", "pessoal.pai");
		put("Data de Desligamento", "dataDesligamentoFormatada");
		put("Vinculo", "vinculoDescricao");
		put("Cpf", "pessoal.cpfFormatado");
		put("Pis", "pessoal.pis");
		put("Rg", "pessoal.rg");
		put("Orgão Emissor", "pessoal.rgOrgaoEmissor");
		put("Deficiência", "pessoal.deficienciaDescricao");
		put("Data de Expedição(RG)", "pessoal.rgDataExpedicaoFormatada");
		put("Sexo", "pessoal.sexoDic");
		put("Data de Nascimento", "pessoal.dataNascimentoFormatada");
		put("Conjugue", "pessoal.conjuge");
		put("Qtd de Filhos", "pessoal.qtdFilhosString");
		put("Número da Habilitação", "habilitacao.numeroHab");
		put("Emissão da Habilitação", "habilitacao.emissaoFormatada");
		put("Vencimento da Habilit.", "habilitacao.vencimentoFormatada");
		put("Categoria da Habilit.", "habilitacao.categoria");
		put("Logradouro", "endereco.logradouro");
		put("Comp. do Logradouro", "endereco.complemento");
		put("Número do Logradouro", "endereco.numero");
		put("Bairro", "endereco.bairro");
		put("Cep", "endereco.cepFormatado");
		put("Email", "contato.email");
		put("Celular", "contato.foneCelularFormatado");
		put("Fone Fixo", "contato.foneFixoFormatado");
	}
}
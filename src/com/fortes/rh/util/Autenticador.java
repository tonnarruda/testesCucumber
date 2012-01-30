package com.fortes.rh.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import remprot.RPClient;


public class Autenticador
{
	private static int qtdCadastrosVersaoDemo = 10;
	private static int appId = 33;
	private static String appNome = "RH";
	private static final Logger logger = Logger.getLogger(Autenticador.class);
	
	public static boolean verificaCopia(String url)
	{
		/*
		Passo como parametros de criacao da classe o codigo do sistema para reset (fornecido pelo AG)
		e um nome curto ou sigla para a licenca (ex.: AC, AG, RH, Cargas32). Pode ser o nome mais conhecido do sistema, sem
		espaços, acentos ou caracteres especiais. Esse nome sera o nome do arquivo .inf que conterá os dados
		da licenca.
		*/
		//TODO remprot
//		RPClient c = getRemprot(url);
//
//		System.out.println("applicationId: " + c.getApplicationId());	// codigo de reset do produto (numero fixo para cada produto: AC=1, AG=16, Nettion=22 etc)
//		System.out.println("installationId: " + c.getInstallationId());	// numero de serie recebido atraves de codigo de resposta (unico por cliente, sequencial independente de produto)
//		System.out.println("resetCounter: " + c.getResetCounter());	    // quantas vezes esta licenca já recebeu resets (ajuda a detectar fraudes)
//		System.out.println("customerId: " + c.getCustomerId());		    // CPF ou CNPJ do cliente
//		System.out.println("customerName: " + c.getCustomerName());	    // denominacao social do cliente
//		System.out.println("nextCrashDate: " + c.getNextCrashDate());	// data limite da execucao (o remprot nao implementa os bloqueios, o sistema deve faze-lo)
//		System.out.println("errors: " + c.getErrors());			        // se errors==0 entao nao houve problemas na conversa com o servidor
//		System.out.println("registered: " + c.getRegistered());		    // se nao esta registrado entao é cópia pirata (ou maquina nova, dá no mesmo)
//		System.out.println("modulos: " + c.getEnabledModules());	    // somatorio dos modulos do RH: 1  - Recrut. e Seleção ,2  - Cargos e Salários ,4  - Pesquisa ,8  - Treina. e Desenvolvimento ,16 - Avaliação de Desempenho ,32 - SESMT
//		System.out.println("qtd colab: " + c.getUserCount());		    // qtd colaboradores
//		
//		//codigo de retorno do erro
//		if(c.getErrors() != 0)
//			logger.error("ERRO NA COMUNICAÇÃO COM O REMPROT: " + c.getErrors());			
//		return c.getRegistered();
		return true;
	}
	
	private static int RECRUT_SELECAO = 1;
	private static int CARGO_SALARIO = 2;
	private static int PESQUISA = 4;
	private static int TRE_DESENV = 8;
	private static int AVAL_DESEMP = 16;
	private static int SESMT = 32;
	
	public static Collection<Long> getModulosNaoConfigurados(String url)
	{
		RPClient c = getRemprot(url);
			    // somatorio dos modulos do RH: 1  - Recrut. e Seleção ,2  - Cargos e Salários ,4  - Pesquisa ,
				//8  - Treina. e Desenvolvimento ,16 - Avaliação de Desempenho ,32 - SESMT
		return getModulosNaoConfigurados(c.getEnabledModules());

	}

	public static Collection<Long> getModulosNaoConfigurados(int chave) 
	{
		chave = 63;
		Collection<Long> modulos = new ArrayList<Long>();
		modulos.add(357L);
		modulos.add(361L);
		modulos.add(353L);
		modulos.add(365L);
		modulos.add(382L);
		modulos.add(75L);

		if((chave & RECRUT_SELECAO) == RECRUT_SELECAO)
			modulos.remove(357L);
		if((chave & CARGO_SALARIO) == CARGO_SALARIO)
			modulos.remove(361L);
		if((chave & PESQUISA) == PESQUISA)
			modulos.remove(353L);
		if((chave & TRE_DESENV) == TRE_DESENV)
			modulos.remove(365L);
		if((chave & AVAL_DESEMP) == AVAL_DESEMP)
			modulos.remove(382L);
		if((chave & SESMT) == SESMT)
			modulos.remove(75L);
		
		return modulos;
	}

	public static RPClient getRemprot(String url)
	{
		//TODO remprot
		RPClient c = new RPClient(appId, appNome);
		/* Aqui eu indico que o servidor de licencas está rodando no endereço IP 10.1.254.2 */
		c.setServerAddress(url);
		
		/* Aqui eu tento carregar os dados da licenca */
		c.loadLicense();
		
		return c;
	}

	public static String getMsgPadrao()
	{
			return "Versão Demonstração";
	}
	
	public static String getMsgAutenticado(String url)
	{
		//TODO remprot
//		if(getRemprot(url).getRegistered())
			return "";
//		else
//			return getMsgPadrao();
	}

	public static int getQtdCadastrosVersaoDemo()
	{
		return qtdCadastrosVersaoDemo;
	}
}
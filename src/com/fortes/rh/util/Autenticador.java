package com.fortes.rh.util;

import remprot.RPClient;


public class Autenticador
{
	private static int qtdCadastrosVersaoDemo = 10;
	private static int appId = 33;
	private static String appNome = "RH";
	
	public static boolean verificaCopia(String url)
	{
		/*
		Passo como parametros de criacao da classe o codigo do sistema para reset (fornecido pelo AG)
		e um nome curto ou sigla para a licenca (ex.: AC, AG, Rhesus, Cargas32). Pode ser o nome mais conhecido do sistema, sem
		espaços, acentos ou caracteres especiais. Esse nome sera o nome do arquivo .inf que conterá os dados
		da licenca.
		*/
		//TODO remprot
//		RPClient c = getRemprot(url);

//		System.out.println("applicationId: " + c.getApplicationId());	// codigo de reset do produto (numero fixo para cada produto: AC=1, AG=16, Nettion=22 etc)
//		System.out.println("installationId: " + c.getInstallationId());	// numero de serie recebido atraves de codigo de resposta (unico por cliente, sequencial independente de produto)
//		System.out.println("resetCounter: " + c.getResetCounter());	    // quantas vezes esta licenca já recebeu resets (ajuda a detectar fraudes)
//		System.out.println("customerId: " + c.getCustomerId());		    // CPF ou CNPJ do cliente
//		System.out.println("customerName: " + c.getCustomerName());	    // denominacao social do cliente
//		System.out.println("nextCrashDate: " + c.getNextCrashDate());	// data limite da execucao (o remprot nao implementa os bloqueios, o sistema deve faze-lo)
//		System.out.println("errors: " + c.getErrors());			        // se errors==0 entao nao houve problemas na conversa com o servidor
//		System.out.println("registered: " + c.getRegistered());		    // se nao esta registrado entao é cópia pirata (ou maquina nova, dá no mesmo)

		return true;//c.getRegistered();
	}

	public static RPClient getRemprot(String url)
	{
		//TODO remprot
		RPClient c = new RPClient(appId, appNome);
		/* Aqui eu indico que o servidor de licencas está rodando no endereço IP 10.0.4.16 */
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
//			return "";
//		else
//			return getMsgPadrao();
		return "";
	}

	public static int getQtdCadastrosVersaoDemo()
	{
		return qtdCadastrosVersaoDemo;
	}
}
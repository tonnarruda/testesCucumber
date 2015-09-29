package com.fortes.rh.util;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import remprot.RPClient;

import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.exception.NotRegistredException;
import com.fortes.rh.model.dicionario.ErroFeedBackRemprot;

public class Autenticador
{
	public static final int appId = 47;
	private static int qtdCadastrosVersaoDemo = 10;
	private static String appNome = "RH";
	private static final Logger logger = Logger.getLogger(Autenticador.class);
	private static RPClient clientRemprot = null;
	private static String urlServidorRemprot;
	private static boolean verificaLicensa;
	private static boolean demo;
	private static Integer modulosPermitidos = 0;
	
	public static final Collection<Long> modulos = new ArrayList<Long>(8);
	static {
		modulos.add(357L);
		modulos.add(361L);
		modulos.add(353L);
		modulos.add(365L);
		modulos.add(373L);
		modulos.add(382L);
		modulos.add(75L);
		modulos.add(37L);
	}

	public static boolean isRegistrado() throws NotConectAutenticationException, NotRegistredException
	{
		return (!verificaLicensa || getRemprot().getRegistered());
	}
	
	public static void verificaCopia(String url, boolean verificaLicensaRemprot, Integer modulosPermitidoSemLicensa) throws Exception, NotConectAutenticationException
	{
		/*
		Passo como parametros de criacao da classe o codigo do sistema para reset (fornecido pelo AG)
		e um nome curto ou sigla para a licenca (ex.: AC, AG, RH, Cargas32). Pode ser o nome mais conhecido do sistema, sem
		espaços, acentos ou caracteres especiais. Esse nome sera o nome do arquivo .inf que conterá os dados
		da licenca.
		*/
		//TODO remprot
		urlServidorRemprot = url;
		verificaLicensa = verificaLicensaRemprot;
		modulosPermitidos = modulosPermitidoSemLicensa;
		
		if (verificaLicensa) {
			clientRemprot = getRemprot();

			System.out.println("applicationId: " + clientRemprot.getApplicationId());	// codigo de reset do produto (numero fixo para cada produto: AC=1, AG=16, Nettion=22 etc)
			System.out.println("installationId: " + clientRemprot.getInstallationId());	// numero de serie recebido atraves de codigo de resposta (unico por cliente, sequencial independente de produto)
			System.out.println("resetCounter: " + clientRemprot.getResetCounter());	    // quantas vezes esta licenca já recebeu resets (ajuda a detectar fraudes)
			System.out.println("customerId: " + clientRemprot.getCustomerId());		    // CPF ou CNPJ do cliente
			System.out.println("customerName: " + clientRemprot.getCustomerName());	    // denominacao social do cliente
			System.out.println("nextCrashDate: " + clientRemprot.getNextCrashDate());	// data limite da execucao (o remprot nao implementa os bloqueios, o sistema deve faze-lo)
			System.out.println("errors: " + clientRemprot.getErrors());			        // se errors==0 entao nao houve problemas na conversa com o servidor
			System.out.println("registered: " + clientRemprot.getRegistered());		    // se nao esta registrado entao é cópia pirata (ou maquina nova, dá no mesmo)
			System.out.println("modulos: " + clientRemprot.getEnabledModules());	    // somatorio dos modulos do RH: 1 - R&S, 2 - C&S, 4 - Pesquisas, 8 - T&D, 16 - Aval. Desempenho, 32 - SESMT
			System.out.println("qtd colab: " + clientRemprot.getUserCount());		    // qtd colaboradores

			verificaRegistro();
		}
	}
	
	private static int RECRUT_SELECAO = 1;
	private static int CARGO_SALARIO = 2;
	private static int PESQUISA = 4;
	private static int TRE_DESENV = 8;
	private static int AVAL_DESEMP = 16;
	private static int SESMT = 32;
	
	public static Collection<Long> getModulosNaoConfigurados() throws NotConectAutenticationException, NotRegistredException
	{
		Collection<Long> modulosNaoConfigurados = new ArrayList<Long>();
		
		if(verificaLicensa) {
			RPClient c = getRemprot();
			    // somatorio dos modulos do RH: 1  - Recrut. e Seleção ,2  - Cargos e Salários ,4  - Pesquisa ,
				//8  - Treina. e Desenvolvimento ,16 - Avaliação de Desempenho ,32 - SESMT
			modulosNaoConfigurados = getModulosNaoConfigurados(c.getEnabledModules());
		} else if(!Autenticador.isDemo()){
			modulosNaoConfigurados = getModulosNaoConfigurados(Autenticador.getModulosPermitidos());
		}
		
		return modulosNaoConfigurados;
	}
	
	public static Collection<Long> getModulosNaoConfigurados(int chave) 
	{
		Collection<Long> modulosNaoConfigurados = new ArrayList<Long>();
		
		if(!demo) 
		{
			modulosNaoConfigurados.addAll(modulos);
		
			if((chave & RECRUT_SELECAO) == RECRUT_SELECAO)
				modulosNaoConfigurados.remove(357L);
			if((chave & CARGO_SALARIO) == CARGO_SALARIO)
				modulosNaoConfigurados.remove(361L);
			if((chave & PESQUISA) == PESQUISA)
				modulosNaoConfigurados.remove(353L);
			if((chave & TRE_DESENV) == TRE_DESENV)
				modulosNaoConfigurados.remove(365L);
			if((chave & AVAL_DESEMP) == AVAL_DESEMP)
				modulosNaoConfigurados.remove(382L);
			if((chave & SESMT) == SESMT)
				modulosNaoConfigurados.remove(75L);
		}
		// Estes módulos sempre aparecerão no menu.
		modulosNaoConfigurados.remove(373L);
		modulosNaoConfigurados.remove(37L);
		
		return modulosNaoConfigurados;
	}

	public static RPClient getRemprot() throws NotConectAutenticationException, NotRegistredException
	{
		if(clientRemprot != null)
			return clientRemprot;

		if(!Autenticador.isDemo())
			verificaConexaoComServidorRemprot(urlServidorRemprot);
		
		clientRemprot = new RPClient(appId, appNome);// Aqui eu indico que o servidor de licencas está rodando no endereço IP 10.1.254.2 
		clientRemprot.setServerAddress(urlServidorRemprot);	// Aqui eu carrego os dados da licenca 

		if(!Autenticador.isDemo() && !clientRemprot.testConnection())
			throw new NotConectAutenticationException("Para que seja efetuado a validação do sistema, o servidor remprot (" + urlServidorRemprot + ") deverá ter acesso a internet.<br/>"
					+ "Por gentileza teste a url abaixo no servidor remprot para verificar o acesso ou entre em contato com o suporte caso o problema persista.<br/>"
					+ "Url: <a href=\"http://www.fortesinformatica.com.br/remprot\">http://www.fortesinformatica.com.br/remprot</a>");
		
		loadLicense();
		return clientRemprot;
	}

	public static void loadLicense() throws NotRegistredException 
	{
		try {
			clientRemprot.loadLicense();
		} catch (Exception e) {
			e.printStackTrace();
			msgRetornoRemprotErro();
		}
	}

	public static void verificaRegistro() throws NotRegistredException 
	{
		if (!clientRemprot.getRegistered())
			msgRetornoRemprotErro();
	}
	
	public static void msgRetornoRemprotErro() throws NotRegistredException 
	{
		ErroFeedBackRemprot erroFeedBackRemprot = new ErroFeedBackRemprot();
		logger.info("Erro do Remprot ao liberar licença: " + String.valueOf(clientRemprot.getErrors()));
		throw new NotRegistredException("Problema na validação da licença de uso. (Código do erro: " + String.valueOf(clientRemprot.getErrors())+") <br/>" +
										erroFeedBackRemprot.getMensagem(clientRemprot.getErrors()) + "<br/>" +
										"Favor entrar em contato com o suporte.");
	}
	
	private static void verificaConexaoComServidorRemprot(String url) throws NotConectAutenticationException
	{
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(url, 50001), 3000); //testa se é possível conectar ao servidor remprot com a porta 50001
		} catch (Exception e) {
			throw new NotConectAutenticationException("Não foi possível se conectar com o servidor remprot. <br/>"
					+ "- Verifique se o endereço IP \"" + url + "\" está correto. <br/>"
					+ "- Verifique se existe algum firewall, proxy ou ativirus bloqueando o acesso ao servidor \"" + url + "\" na porta \"50001\". <br/>");
		}
	}

	public static boolean isDemo() throws NotConectAutenticationException
	{
		return Autenticador.demo;
	}

	public static void setDemo(boolean demo) 
	{
		clientRemprot = null;
		Autenticador.demo = demo;
	}

	public static String getMsgPadrao()
	{
		return "Versão Demonstração";
	}
	
	public static String getMsgAutenticado(String url) throws NotConectAutenticationException
	{
		return isDemo() ? getMsgPadrao() : "";
	}

	public static int getQtdCadastrosVersaoDemo()
	{
		return qtdCadastrosVersaoDemo;
	}

	public static Integer getModulosPermitidos() {
		return modulosPermitidos;
	}
}
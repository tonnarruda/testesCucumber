package com.fortes.rh.security.licenca;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONObject;

import remprot.RPClient;

import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.exception.NotRegistredException;
import com.fortes.rh.model.dicionario.ErroFeedBackRemprot;
import com.fortes.rh.util.JSONUtil;


public class AutenticadorJarvis extends Autenticador
{
	private static JClient jClient = null;
	
	public static boolean isRegistrado() throws Exception
	{
		return (!verificaLicensa || getClient().getRegistrado());
	}
	
	public static JClient getClient() throws Exception
	{
		JSONObject jsonObject = JSONUtil.getObject(urlConexao);

		if(jClient == null)
			jClient = new JClient(jsonObject);
		
		return jClient;
	}
	
	public static Collection<Long> getModulosNaoConfigurados() throws NotConectAutenticationException, NotRegistredException
	{
		Collection<Long> modulosNaoConfigurados = new ArrayList<Long>();
		
		if(verificaLicensa) {
			modulosNaoConfigurados = getModulosNaoConfigurados(63);
		} else if(!isDemo()){
			modulosNaoConfigurados = getModulosNaoConfigurados(getModulosPermitidos());
		}
		
		return modulosNaoConfigurados;
	}
	
	public static void verificaCopia(String url, boolean verificaLicensaRemprot, Integer modulosPermitidoSemLicensa) throws Exception, NotConectAutenticationException
	{
		urlConexao = url;
		verificaLicensa = verificaLicensaRemprot;
		modulosPermitidos = modulosPermitidoSemLicensa;
		
		if (verificaLicensa) {
			jClient = getClient();

			System.out.println("applicationId: " + jClient.getCodigoProduto());	// codigo de reset do produto (numero fixo para cada produto: AC=1, AG=16, Nettion=22 etc)
//			System.out.println("installationId: " + clientRemprot.getInstallationId());	// numero de serie recebido atraves de codigo de resposta (unico por cliente, sequencial independente de produto)
//			System.out.println("resetCounter: " + clientRemprot.getResetCounter());	    // quantas vezes esta licenca já recebeu resets (ajuda a detectar fraudes)
//			System.out.println("customerId: " + clientRemprot.getCustomerId());		    // CPF ou CNPJ do cliente
//			System.out.println("customerName: " + clientRemprot.getCustomerName());	    // denominacao social do cliente
//			System.out.println("nextCrashDate: " + clientRemprot.getNextCrashDate());	// data limite da execucao (o remprot nao implementa os bloqueios, o sistema deve faze-lo)
//			System.out.println("errors: " + clientRemprot.getErrors());			        // se errors==0 entao nao houve problemas na conversa com o servidor
//			System.out.println("registered: " + clientRemprot.getRegistered());		    // se nao esta registrado entao é cópia pirata (ou maquina nova, dá no mesmo)
//			System.out.println("modulos: " + clientRemprot.getEnabledModules());	    // somatorio dos modulos do RH: 1 - R&S, 2 - C&S, 4 - Pesquisas, 8 - T&D, 16 - Aval. Desempenho, 32 - SESMT
//			System.out.println("qtd colab: " + clientRemprot.getUserCount());		    // qtd colaboradores

			verificaRegistro();
		}
	}

	public static void verificaRegistro() throws NotRegistredException 
	{
		if (!jClient.getRegistrado())
			msgRetornoErro();
	}
	
	public static void msgRetornoErro() throws NotRegistredException 
	{
		logger.info("Erro ao liberar licença: " + jClient.getMensagemDeErro());
		throw new NotRegistredException("Problema na validação da licença de uso. <br/>" +
				                        jClient.getMensagemDeErro() + "<br/>" +
										"Favor entrar em contato com o suporte.");
	}

}
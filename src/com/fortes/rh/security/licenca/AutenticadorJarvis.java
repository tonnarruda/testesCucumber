package com.fortes.rh.security.licenca;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONObject;

import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.exception.NotRegistredException;
import com.fortes.rh.util.JSONUtil;


public class AutenticadorJarvis extends Autenticador
{
	private static JClient clientJarvis = null;
	
	public static boolean isRegistrado() throws Exception
	{
		return (!verificaLicensa || getClient().getRegistrado());
	}
	
	public static JClient getClient() throws Exception
	{
		if(clientJarvis == null || clientJarvis.getCodigoProduto().equals("null")) {
			JSONObject jsonObject;
			try {
				jsonObject = JSONUtil.getObject(urlConexao);
			} catch (Exception e) {
				e.printStackTrace();
				throw new NotConectAutenticationException();
			}
			clientJarvis = new JClient(jsonObject);
		}
		
		return clientJarvis;
	}
	
	public static Collection<Long> getModulosNaoConfigurados() throws NotConectAutenticationException, NotRegistredException
	{
		Collection<Long> modulosNaoConfigurados = new ArrayList<Long>();
		
		if(verificaLicensa) {
//			modulosNaoConfigurados = getModulosNaoConfigurados(63);
			modulosNaoConfigurados = getModulosNaoConfigurados(clientJarvis.getModulosDisponiveis());
		} else if(!isDemo()){
			modulosNaoConfigurados = getModulosNaoConfigurados(getModulosPermitidos());
		}
		
		return modulosNaoConfigurados;
	}
	
	public static void verificaCopia(String cnpj, boolean verificaLicensaAutenticador, Integer modulosPermitidoSemLicensa) throws Exception
	{
		urlConexao = "http://jarvisws.azurewebsites.net/licenseinfo?cnpj="+cnpj+"&location=TESTE%20RH%20NUVEM&area=BDD5C8EC-942B-4EF0-8AB2-A059820B1B42&productcode=50";
		verificaLicensa = verificaLicensaAutenticador;
		modulosPermitidos = modulosPermitidoSemLicensa;
		
		if (verificaLicensa) {
			clientJarvis = getClient();

			System.out.println("** DADOS DO REGISTRO DA LICENCA **");

			for (String atributo : JSONObject.getNames(clientJarvis.getJsonObject())) 
				System.out.println(atributo+": " + clientJarvis.getJsonObject().get(atributo));	

			verificaRegistro();
		}
	}

	public static void verificaRegistro() throws NotRegistredException 
	{
		if (!clientJarvis.getRegistrado())
			msgRetornoErro();
	}
	
	public static void msgRetornoErro() throws NotRegistredException 
	{
		logger.info("Erro ao liberar licença: " + clientJarvis.getMensagemDeErro());
		throw new NotRegistredException("Problema na validação da licença de uso. <br/>" +
				                        clientJarvis.getMensagemDeErro() + "<br/>" +
										"Favor entrar em contato com o suporte.");
	}

}
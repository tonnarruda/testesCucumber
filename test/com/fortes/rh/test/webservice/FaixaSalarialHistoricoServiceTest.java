package com.fortes.rh.test.webservice;

import java.net.MalformedURLException;

import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import com.fortes.rh.business.ws.RHService;

public class FaixaSalarialHistoricoServiceTest
{
	public static void main(String[] args) throws Exception
	{
		// Determina qual o tipo de objeto que vamos querer criar
		Service serviceModel = new ObjectServiceFactory().create(RHService.class);
		XFire xfire = XFireFactory.newInstance().getXFire();
		XFireProxyFactory factory = new XFireProxyFactory(xfire);

		// URL para acessar o web service
		String serviceUrl = "http://localhost/fortesrh/webservice/RHService";
		RHService client = (RHService) factory.create(serviceModel, serviceUrl);
		
		// Criando Token
		String token = client.getToken("FORTESPESSOAL", "@FortesPessoal");

		System.out.println(client.setStatusFaixaSalarialHistorico(token, 11426L, false, "TESTE WS AC OK?", "0003", "XXX"));
	}
}
package com.fortes.rh.test.webservice;

import java.net.MalformedURLException;

import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.jmock.Mock;

import com.fortes.rh.business.security.TokenManager;
import com.fortes.rh.business.ws.RHService;
import com.fortes.rh.business.ws.RHServiceImpl;

public class ColaboradorDesligamentoServiceTest
{
	public static void main(String[] args) throws Exception
	{
		// Determina qual o tipo de objeto que vamos querer criar
		Service serviceModel = new ObjectServiceFactory().create(RHService.class);
		XFire xfire = XFireFactory.newInstance().getXFire();
		XFireProxyFactory factory = new XFireProxyFactory(xfire);

		// URL para acessar o web service
		String serviceUrl = "http://localhost:8080/fortesrh/webservice/RHService";
		RHService client = (RHService) factory.create(serviceModel, serviceUrl);

		// Criando Token
		String token = client.getToken("FORTESPESSOAL", "@FortesPessoal");
		
		// Invocando o serviço.
		boolean retorno = client.desligarEmpregado(token, "000184", "0003", "01/01/2000", "XXX").isSucesso();

		System.out.println("Retorno do metodo " + retorno);
	}
}
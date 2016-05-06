package com.fortes.rh.test.webservice;

import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import com.fortes.rh.business.ws.RHService;
import com.fortes.rh.model.ws.TIndiceHistorico;

public class IndiceServiceTest
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

		// Invocando o serviço.
//		TIndice tindice = new TIndice();
//		tindice.setNome("teste");
//		tindice.setCodigo("001");
//		
//		System.out.println(client.criarIndice(tindice));

		TIndiceHistorico tindiceHistorico = new TIndiceHistorico();
		tindiceHistorico.setData("01/01/2000");
		tindiceHistorico.setValor(1458.65);
		tindiceHistorico.setIndiceCodigo("001");
		
		// Criando Token
		String token = client.getToken("FORTESPESSOAL", "@FortesPessoal");
		
//		System.out.println(client.criarIndiceHistorico(tindiceHistorico));
		System.out.println(client.removerIndiceHistorico(token, "01/01/2000", "001", "XXX"));
		
		
//		
//		tindice.setNome("nome");
//		System.out.println(client.atualizarIndice(tindice));
//		
//		System.out.println(client.removerIndice("001"));
	}
}
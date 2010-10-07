package com.fortes.rh.web.ws;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ParameterMode;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.Empresa;

public class AcPessoalClientSistemaImpl implements AcPessoalClientSistema
{
	private AcPessoalClient acPessoalClient;

	public String getVersaoWebServiceAC(Empresa empresa) throws Exception
	{
        try
        {
            Call call = acPessoalClient.createCall(empresa.getAcUrlSoap(), "GetWebServiceVersion");

            String token = acPessoalClient.getToken(empresa);

            QName xmlstring = new QName("xs:string");

			call.addParameter("Token",xmlstring,ParameterMode.IN);

        	//Seta o tipo de resultado
        	call.setReturnType(xmlstring);

        	Object[] param = new Object[]{token};

        	//Retorna codigo caso insert ocorra (que será inserido no codigoAC)
            String versaoAC = (String) call.invoke(param);

            return versaoAC;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
	}

	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}

	public void verificaWebService(Empresa empresa) throws IntegraACException
	{
		try
		{
			if(empresa.isAcIntegra())
				acPessoalClient.getToken(empresa);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e.getMessage().equals("Usuário Não Autenticado!"))
				throw new IntegraACException("Não foi possivel autenticar o serviço no AC Pessoal.");
			else
				throw new IntegraACException("Não foi possivel se conectar com o serviço do AC Pessoal.");
		}
	}

	public boolean idACIntegrado(Empresa empresa) throws Exception
	{
        try
        {
        	Call call = acPessoalClient.createCall(empresa.getAcUrlSoap(), "isRHIntegrado");

        	String token = acPessoalClient.getToken(empresa);

            call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_BOOLEAN);
            QName xmlstring = new QName("xs:string");

			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);

        	Object[] param = new Object[]{token, empresa.getCodigoAC()};

            //return true; //(Boolean) call.invoke(param);
        	return (Boolean) call.invoke(param);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
	}

}
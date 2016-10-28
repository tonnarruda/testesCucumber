package com.fortes.rh.web.ws;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ParameterMode;

import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;

@Component
public class AcPessoalClientSistemaImpl implements AcPessoalClientSistema
{
	private AcPessoalClient acPessoalClient;
	private GrupoACManager grupoACManager;

	public String getVersaoWebServiceAC(Empresa empresa) throws Exception
	{
        try
        {
        	StringBuilder token = new StringBuilder();
        	Call call = acPessoalClient.createCall(empresa, token, null, "GetWebServiceVersion");

            QName xmlstring = new QName("xs:string");

			call.addParameter("Token",xmlstring,ParameterMode.IN);

        	//Seta o tipo de resultado
        	call.setReturnType(xmlstring);

        	Object[] param = new Object[]{token.toString()};

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
			{
				GrupoAC grupoAC = grupoACManager.findByCodigo(empresa.getGrupoAC());
				acPessoalClient.getToken(grupoAC);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e.getMessage().equals("Usuário Não Autenticado!"))
				throw new IntegraACException("Não foi possivel autenticar o serviço no Fortes Pessoal.");
			else
				throw new IntegraACException("Não foi possivel se conectar com o serviço do Fortes Pessoal.");
		}
	}

	public boolean idACIntegrado(Empresa empresa) throws Exception
	{
        try
        {
        	StringBuilder token = new StringBuilder();
        	Call call = acPessoalClient.createCall(empresa, token, null, "isRHIntegrado");

            call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_BOOLEAN);
            QName xmlstring = new QName("xs:string");

			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);

        	Object[] param = new Object[]{token.toString(), empresa.getCodigoAC()};

            //return true; //(Boolean) call.invoke(param);
        	return (Boolean) call.invoke(param);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
	}

	public void setGrupoACManager(GrupoACManager grupoACManager) {
		this.grupoACManager = grupoACManager;
	}

}
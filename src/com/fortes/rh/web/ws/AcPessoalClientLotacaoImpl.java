package com.fortes.rh.web.ws;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;

public class AcPessoalClientLotacaoImpl implements AcPessoalClientLotacao
{
	private AcPessoalClient acPessoalClient;
	private GrupoACManager grupoACManager;

	public boolean deleteLotacao(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception
	{
		try
		{
			GrupoAC grupoAC = grupoACManager.findByGrupo(empresa.getGrupoAC());
			String token = acPessoalClient.getToken(empresa);

			Call call = acPessoalClient.createCall(empresa.getAcUrlSoap(), "DelLotacao");

			QName xmlstring = new QName("xs:string");

			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);
			call.addParameter("Codigo",xmlstring,ParameterMode.IN);

			call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_BOOLEAN);

			Object[] param = new Object[]{token, empresa.getCodigoAC(), areaOrganizacional.getCodigoAC()};

			// TODO Por que o retorno boolean não está sendo usado? (Tiago)
			return (Boolean) call.invoke(param);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Erro ao remover Lotação no AC Pessoal.");
		}
	}

	public String criarLotacao(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception
	{
        try
        {
        	
        	String token = acPessoalClient.getToken(empresa);

        	Call call = acPessoalClient.createCall(empresa.getAcUrlSoap(), "SetLotacao");

            QName xmlstring = new QName("xs:string");

            //Seta os parâmetros com os tipos e modos
        	call.addParameter("Token",xmlstring,ParameterMode.IN);
        	call.addParameter("Empresa",xmlstring,ParameterMode.IN);
        	call.addParameter("Codigo",xmlstring,ParameterMode.IN);
        	call.addParameter("Nome",xmlstring,ParameterMode.IN);
        	call.addParameter("Mae",xmlstring,ParameterMode.IN);

        	//Seta o tipo de resultado
        	call.setReturnType(xmlstring);

        	String codigoAc = "";
        	if(areaOrganizacional.getCodigoAC() != null && !areaOrganizacional.getCodigoAC().equals(""))
        		codigoAc = areaOrganizacional.getCodigoAC();

        	String codigoMae = "";
        	if(areaOrganizacional.getAreaMae() != null && areaOrganizacional.getAreaMae().getCodigoAC() != null)
        		codigoMae = areaOrganizacional.getAreaMae().getCodigoAC();

        	//Seta os valores e invoca o serviço não passa codigo para inserir
        	Object[] param = new Object[]{token,empresa.getCodigoAC(), codigoAc, areaOrganizacional.getNome(), codigoMae};

        	//Retorna codigo caso insert ocorra (que será inserido no codigoAC)
            String codigoRetorno = (String) call.invoke(param);

            return codigoRetorno;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception(e);
        }
	}

	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}

	public void setGrupoACManager(GrupoACManager grupoACManager) {
		this.grupoACManager = grupoACManager;
	}
}
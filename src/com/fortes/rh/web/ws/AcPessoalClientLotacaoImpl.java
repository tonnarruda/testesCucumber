package com.fortes.rh.web.ws;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;

public class AcPessoalClientLotacaoImpl implements AcPessoalClientLotacao
{
	private AcPessoalClient acPessoalClient;

	public boolean deleteLotacao(AreaOrganizacional areaOrganizacional, Empresa empresa) throws IntegraACException
	{
		try
		{
			StringBuilder token = new StringBuilder(); 
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "DelLotacao");

			QName xmlstring = new QName("xs:string");

			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);
			call.addParameter("Codigo",xmlstring,ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

			Object[] param = new Object[]{token.toString(), empresa.getCodigoAC(), areaOrganizacional.getCodigoAC()};

			TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
			return result.getSucesso("DelLotacao", param, this.getClass());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IntegraACException("Erro ao remover lotação no Fortes Pessoal.");
		}
	}

	public String criarLotacao(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception
	{
        try
        {
        	StringBuilder token = new StringBuilder();
        	GrupoAC grupoAC = new GrupoAC();
        	Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SetLotacao");

            QName xmlstring = new QName("xs:string");

            //Seta os parâmetros com os tipos e modos
        	call.addParameter("Token",xmlstring,ParameterMode.IN);
        	call.addParameter("Empresa",xmlstring,ParameterMode.IN);
        	call.addParameter("Codigo",xmlstring,ParameterMode.IN);
        	call.addParameter("Nome",xmlstring,ParameterMode.IN);
        	call.addParameter("Mae",xmlstring,ParameterMode.IN);

        	//Seta o tipo de resultado
        	acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

        	String codigoAc = "";
        	if(areaOrganizacional.getCodigoAC() != null && !areaOrganizacional.getCodigoAC().equals(""))
        		codigoAc = areaOrganizacional.getCodigoAC();

        	String codigoMae = "";
        	if(areaOrganizacional.getAreaMae() != null && areaOrganizacional.getAreaMae().getCodigoAC() != null)
        		codigoMae = areaOrganizacional.getAreaMae().getCodigoAC();

        	//Seta os valores e invoca o serviço não passa codigo para inserir
        	Object[] param = new Object[]{token.toString(),empresa.getCodigoAC(), codigoAc, areaOrganizacional.getNome(), codigoMae};

        	//Retorna codigo caso insert ocorra (que será inserido no codigoAC)
        	TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
        	
        	if (result.getSucesso("SetLotacao", param, this.getClass()))
        		return result.getCodigoretorno();
        	else
        		return result.getCodigoerro();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("Erro ao cadastrar/atualizar lotação no Fortes Pessoal.");
        }
	}
	
	public String getMascara(Empresa empresa) throws Exception
	{
		try
		{
			StringBuilder token = new StringBuilder(); 
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "GetMascaraLotacao");

			QName xmlstring = new QName("xs:string");

			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

			Object[] param = new Object[]{token.toString(), empresa.getCodigoAC()};

			TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
			
			return result.getMensagem();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IntegraACException("Erro ao obter a máscara de lotações do Fortes Pessoal.");
		}
	}

	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}
}
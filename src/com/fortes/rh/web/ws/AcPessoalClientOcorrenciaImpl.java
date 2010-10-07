package com.fortes.rh.web.ws;

import static org.apache.axis.Constants.XSD_BOOLEAN;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TOcorrencia;

public class AcPessoalClientOcorrenciaImpl implements AcPessoalClientOcorrencia
{
	private AcPessoalClient acPessoalClient;

	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}

	public String criarTipoOcorrencia(TOcorrencia tocorrencia, Empresa empresa) throws Exception
	{
		try
	    {
	    	String token = acPessoalClient.getToken(empresa);

	    	Call call = acPessoalClient.createCall(empresa.getAcUrlSoap(), "SetTipoOcorrencia");

	    	QName qnameTipoOcorrencia = new QName(empresa.getAcUrlWsdl(), "TOcorrencia");
			call.registerTypeMapping(TOcorrencia.class, qnameTipoOcorrencia, new BeanSerializerFactory(TOcorrencia.class, qnameTipoOcorrencia), new BeanDeserializerFactory(TOcorrencia.class, qnameTipoOcorrencia));

			QName xmltype = new QName("ns1:TOcorrencia");
	        QName xmlstring = new QName("xs:string");

	    	call.addParameter("Token",xmlstring,ParameterMode.IN);
	    	call.addParameter("Ocorrencia", xmltype, ParameterMode.IN);
	    	call.setReturnType(xmlstring);

	    	Object[] param = new Object[]{token,tocorrencia};

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

	public boolean removerTipoOcorrencia(TOcorrencia tocorrencia, Empresa empresa) throws Exception
	{
		String token = acPessoalClient.getToken(empresa);

		Call call = acPessoalClient.createCall(empresa.getAcUrlSoap(), "DelTipoOcorrencia");

		QName qnameTipoOcorrencia = new QName(empresa.getAcUrlWsdl(), "TOcorrencia");
		call.registerTypeMapping(TOcorrencia.class, qnameTipoOcorrencia, new BeanSerializerFactory(TOcorrencia.class, qnameTipoOcorrencia), new BeanDeserializerFactory(TOcorrencia.class, qnameTipoOcorrencia));

		QName xmltype = new QName("ns1:TOcorrencia");
        QName xmlstring = new QName("xs:string");

        //Seta os parâmetros com os tipos e modos
    	call.addParameter("Token",xmlstring,ParameterMode.IN);
    	call.addParameter("Ocorrencia", xmltype, ParameterMode.IN);
    	call.setReturnType(XSD_BOOLEAN);

    	Object[] param = new Object[]{token, tocorrencia};

    	Boolean retorno = (Boolean) call.invoke(param);

    	return retorno;
	}
}

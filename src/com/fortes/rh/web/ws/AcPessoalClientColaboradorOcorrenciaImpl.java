package com.fortes.rh.web.ws;

import static org.apache.axis.Constants.XSD_BOOLEAN;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;

public class AcPessoalClientColaboradorOcorrenciaImpl implements AcPessoalClientColaboradorOcorrencia
{
	private AcPessoalClient acPessoalClient;

	public boolean criarColaboradorOcorrencia(TOcorrenciaEmpregado ocorrenciaEmpregado, Empresa empresa) throws Exception
	{
		try
	    {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
	        Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SetOcorrenciaEmpregado");

	        QName qnameOcorrencia = new QName(grupoAC.getAcUrlWsdl(), "TOcorrenciaEmpregado");
			call.registerTypeMapping(TOcorrenciaEmpregado.class, qnameOcorrencia, new BeanSerializerFactory(TOcorrenciaEmpregado.class, qnameOcorrencia), new BeanDeserializerFactory(TOcorrenciaEmpregado.class, qnameOcorrencia));

			QName xmlstring = new QName("xs:string");
	        QName xmltype = new QName("ns1:TOcorrenciaEmpregado");

	    	call.addParameter("Token",xmlstring,ParameterMode.IN);
	    	call.addParameter("OcorrenciaEmpregado", xmltype, ParameterMode.IN);

	    	call.setReturnType(XSD_BOOLEAN);

	    	//Seta os valores e invoca o serviço não passa codigo para inserir
	    	Object[] param = new Object[]{token.toString(), ocorrenciaEmpregado};

	        return (Boolean) call.invoke(param);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        throw new Exception(e);
	    }
	}

	public boolean removerColaboradorOcorrencia(TOcorrenciaEmpregado ocorrenciaEmpregado, Empresa empresa) throws Exception
	{
		try
	    {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
	        Call call = acPessoalClient.createCall(empresa, token, grupoAC, "DelOcorrenciaEmpregado");

	        QName qnameOcorrencia = new QName(grupoAC.getAcUrlWsdl(), "TOcorrenciaEmpregado");
			call.registerTypeMapping(TOcorrenciaEmpregado.class, qnameOcorrencia, new BeanSerializerFactory(TOcorrenciaEmpregado.class, qnameOcorrencia), new BeanDeserializerFactory(TOcorrenciaEmpregado.class, qnameOcorrencia));

			QName xmlstring = new QName("xs:string");
	        QName xmltype = new QName("ns1:TOcorrenciaEmpregado");

	    	call.addParameter("Token",xmlstring,ParameterMode.IN);
	    	call.addParameter("OcorrenciaEmpregado", xmltype, ParameterMode.IN);

	    	call.setReturnType(XSD_BOOLEAN);

	    	Object[] param = new Object[]{token.toString(), ocorrenciaEmpregado};

	        return (Boolean) call.invoke(param);
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
}

package com.fortes.rh.web.ws;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;

@Component
public class AcPessoalClientColaboradorOcorrenciaImpl implements AcPessoalClientColaboradorOcorrencia
{
	@Autowired private AcPessoalClient acPessoalClient;

	public boolean criarColaboradorOcorrencia(TOcorrenciaEmpregado ocorrenciaEmpregado, Empresa empresa) throws Exception
	{
		return criarOuRemoveColaboradorOcorrencia(ocorrenciaEmpregado, empresa, "SetOcorrenciaEmpregado");
	}
	
	public boolean removerColaboradorOcorrencia(TOcorrenciaEmpregado ocorrenciaEmpregado, Empresa empresa) throws Exception
	{
		return criarOuRemoveColaboradorOcorrencia(ocorrenciaEmpregado, empresa, "DelOcorrenciaEmpregado");
	}

	private boolean criarOuRemoveColaboradorOcorrencia(TOcorrenciaEmpregado ocorrenciaEmpregado, Empresa empresa, String metodoWS) throws Exception {
		try{
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
	        Call call = acPessoalClient.createCall(empresa, token, grupoAC, metodoWS);

	        QName qnameOcorrencia = new QName(grupoAC.getAcUrlWsdl(), "TOcorrenciaEmpregado");
			call.registerTypeMapping(TOcorrenciaEmpregado.class, qnameOcorrencia, new BeanSerializerFactory(TOcorrenciaEmpregado.class, qnameOcorrencia), new BeanDeserializerFactory(TOcorrenciaEmpregado.class, qnameOcorrencia));

			QName xmlstring = new QName("xs:string");
	        QName xmltype = new QName("ns1:TOcorrenciaEmpregado");

	    	call.addParameter("Token",xmlstring,ParameterMode.IN);
	    	call.addParameter("OcorrenciaEmpregado", xmltype, ParameterMode.IN);

	    	acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

	    	Object[] param = new Object[]{token.toString(), ocorrenciaEmpregado};

	    	TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
	    	return result.getSucesso(metodoWS, param, this.getClass());
	    }catch(Exception e){
	        e.printStackTrace();
	        throw new Exception(e);
	    }
	}

	public boolean atualizarColaboradorOcorrencia(TOcorrenciaEmpregado ocorrenciaEmpregadoAntiga, TOcorrenciaEmpregado ocorrenciaEmpregadoNova, Empresa empresa) throws Exception
	{
		try{
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
	        Call call = acPessoalClient.createCall(empresa, token, grupoAC, "UpdateOcorrenciaEmpregado");

	        QName qnameOcorrencia = new QName(grupoAC.getAcUrlWsdl(), "TOcorrenciaEmpregado");
			call.registerTypeMapping(TOcorrenciaEmpregado.class, qnameOcorrencia, new BeanSerializerFactory(TOcorrenciaEmpregado.class, qnameOcorrencia), new BeanDeserializerFactory(TOcorrenciaEmpregado.class, qnameOcorrencia));

			QName xmlstring = new QName("xs:string");
	        QName xmltype = new QName("ns1:TOcorrenciaEmpregado");

	    	call.addParameter("Token",xmlstring,ParameterMode.IN);
	    	call.addParameter("OcorrenciaEmpregadoAntiga", xmltype, ParameterMode.IN);
	    	call.addParameter("OcorrenciaEmpregadoNova", xmltype, ParameterMode.IN);

	    	acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

	    	Object[] param = new Object[]{token.toString(), ocorrenciaEmpregadoAntiga, ocorrenciaEmpregadoNova};

	    	TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
	    	return result.getSucesso("UpdateOcorrenciaEmpregado", param, this.getClass());
	    }catch(Exception e){
	        e.printStackTrace();
	        throw new Exception(e);
	    }
	}
}
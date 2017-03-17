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
import com.fortes.rh.model.ws.TOcorrencia;

@Component
public class AcPessoalClientOcorrenciaImpl implements AcPessoalClientOcorrencia
{
	@Autowired private AcPessoalClient acPessoalClient;

	public String criarTipoOcorrencia(TOcorrencia tocorrencia, Empresa empresa) throws Exception
	{
		try
	    {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
	    	Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SetTipoOcorrencia");

	    	QName qnameTipoOcorrencia = new QName(grupoAC.getAcUrlWsdl(), "TOcorrencia");
			call.registerTypeMapping(TOcorrencia.class, qnameTipoOcorrencia, new BeanSerializerFactory(TOcorrencia.class, qnameTipoOcorrencia), new BeanDeserializerFactory(TOcorrencia.class, qnameTipoOcorrencia));

			QName xmltype = new QName("ns1:TOcorrencia");
	        QName xmlstring = new QName("xs:string");

	    	call.addParameter("Token",xmlstring,ParameterMode.IN);
	    	call.addParameter("Ocorrencia", xmltype, ParameterMode.IN);
	    	acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

	    	Object[] param = new Object[]{token.toString(),tocorrencia};

	    	//Retorna codigo caso insert ocorra (que será inserido no codigoAC)
	    	TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
        	result.getSucesso("SetTipoOcorrencia", param, this.getClass());

            return result.getCodigoretorno();

	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        throw new Exception(e);
	    }
	}

	public boolean removerTipoOcorrencia(TOcorrencia tocorrencia, Empresa empresa) throws Exception
	{
		StringBuilder token = new StringBuilder();
		GrupoAC grupoAC = new GrupoAC();
		Call call = acPessoalClient.createCall(empresa, token, grupoAC, "DelTipoOcorrencia");

		QName qnameTipoOcorrencia = new QName(grupoAC.getAcUrlWsdl(), "TOcorrencia");
		call.registerTypeMapping(TOcorrencia.class, qnameTipoOcorrencia, new BeanSerializerFactory(TOcorrencia.class, qnameTipoOcorrencia), new BeanDeserializerFactory(TOcorrencia.class, qnameTipoOcorrencia));

		QName xmltype = new QName("ns1:TOcorrencia");
        QName xmlstring = new QName("xs:string");

        //Seta os parâmetros com os tipos e modos
    	call.addParameter("Token",xmlstring,ParameterMode.IN);
    	call.addParameter("Ocorrencia", xmltype, ParameterMode.IN);
    	acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

    	Object[] param = new Object[]{token.toString(), tocorrencia};

    	TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
    	return result.getSucesso("DelTipoOcorrencia", param, this.getClass());
	}
}

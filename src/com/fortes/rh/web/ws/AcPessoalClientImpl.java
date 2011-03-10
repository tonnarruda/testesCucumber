package com.fortes.rh.web.ws;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Service;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;

public class AcPessoalClientImpl implements AcPessoalClient
{
	private Service service;
	private GrupoACManager grupoACManager; 

	public String getToken(GrupoAC grupoAC) throws ServiceException, RemoteException
	{
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(grupoAC.getAcUrlSoap());
		call.setOperationName(new QName("getToken"));
		
		call.addParameter("usuario", org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("senha", org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
		
		Object[] param = new Object[]{grupoAC.getAcUsuario(), grupoAC.getAcSenha()};
		
		return (String) call.invoke(param);
	}

	public org.apache.axis.client.Call createCall(Empresa empresa, StringBuilder token, GrupoAC grupoACTmp, String operationName) throws Exception
	{
		GrupoAC grupoAC = grupoACManager.findByCodigo(empresa.getGrupoAC());
		
		if(grupoACTmp != null)
			grupoACTmp.setAcUrlWsdl(grupoAC.getAcUrlWsdl());
		
		org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
		call.setTargetEndpointAddress(grupoAC.getAcUrlSoap());
		call.setOperationName(operationName);
		
		token.append(getToken(grupoAC));
		
		return call;
	}

	public void setService(Service service)
	{
		this.service = service;
	}

	public void setGrupoACManager(GrupoACManager grupoACManager) {
		this.grupoACManager = grupoACManager;
	}
}
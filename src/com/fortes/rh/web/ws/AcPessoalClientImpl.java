package com.fortes.rh.web.ws;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Service;

import com.fortes.rh.model.geral.Empresa;

public class AcPessoalClientImpl implements AcPessoalClient
{
	private Service service;

	public String getToken(Empresa empresa) throws ServiceException, RemoteException
	{
		String usuarioAc = empresa.getAcUsuario();
		String senhaAc   = empresa.getAcSenha();

		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(empresa.getAcUrlSoap());
		call.setOperationName(new QName("getToken"));

		call.addParameter("usuario", org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("senha", org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

		Object[] param = new Object[]{usuarioAc, senhaAc};

		return (String) call.invoke(param);
	}

	public org.apache.axis.client.Call
			createCall(String urlSoap, String operationName) throws Exception
	{
		org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
	    call.setTargetEndpointAddress(urlSoap);
	    call.setOperationName(operationName);
	    return call;
	}

	public void setService(Service service)
	{
		this.service = service;
	}
}
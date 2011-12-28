package com.fortes.rh.web.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;

public interface AcPessoalClient
{
	String getToken(GrupoAC grupoAC) throws ServiceException, RemoteException;
	Call createCall(Empresa empresa, StringBuilder token, GrupoAC grupoAC, String operationName) throws Exception;
	void setReturnType(Call call, String acUrlWsdl);
}
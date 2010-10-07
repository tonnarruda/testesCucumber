package com.fortes.rh.web.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.fortes.rh.model.geral.Empresa;

public interface AcPessoalClient
{
	String getToken(Empresa empresa) throws ServiceException, RemoteException;
	org.apache.axis.client.Call createCall(String urlSoap, String operationName) throws Exception;
}
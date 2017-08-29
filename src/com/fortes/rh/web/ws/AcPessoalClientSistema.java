package com.fortes.rh.web.ws;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.Empresa;

public interface AcPessoalClientSistema
{
	public String getVersaoWebServiceAC(Empresa empresa) throws Exception;

	public void verificaWebService(Empresa empresa)throws IntegraACException;

	public boolean idACIntegrado(Empresa empresa)throws Exception;
	
	public boolean isAderiuAoESocial(Empresa empresa) throws Exception;
}
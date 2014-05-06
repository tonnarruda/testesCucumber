package com.fortes.rh.web.ws;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;

public interface AcPessoalClientLotacao
{
	boolean deleteLotacao(AreaOrganizacional areaOrganizacional, Empresa empresa) throws IntegraACException;
	String criarLotacao(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception;
	String getMascara(Empresa empresa) throws Exception;
}
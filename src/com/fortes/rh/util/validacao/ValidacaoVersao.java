package com.fortes.rh.util.validacao;

import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.ArquivoUtil;

public class ValidacaoVersao extends Validacao{

	public void execute(ParametrosDoSistema parametrosDoSistema) throws FortesException {
		String versao = ArquivoUtil.getVersao();
		if(!parametrosDoSistema.getAppVersao().equals(versao))
			throw new FortesException("A versão do banco de dados está incompatível com a versão da aplicação. Entre em contato com a Fortes Tecnologia.");
	}
}
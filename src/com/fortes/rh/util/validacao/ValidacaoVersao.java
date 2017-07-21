package com.fortes.rh.util.validacao;

import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.ArquivoUtil;

public class ValidacaoVersao extends Validacao{

	public void execute(ParametrosDoSistema parametrosDoSistema) throws FortesException {
		String versaoAplicacao = ArquivoUtil.getVersao();
		if(!parametrosDoSistema.getAppVersao().equals(versaoAplicacao))
			throw new FortesException("A versão do banco de dados ("+parametrosDoSistema.getAppVersao()+") está incompatível com a versão da aplicação ("+versaoAplicacao+"). Entre em contato com a <br />Fortes Tecnologia.");
	}
}
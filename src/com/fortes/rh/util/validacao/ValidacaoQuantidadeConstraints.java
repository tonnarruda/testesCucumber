package com.fortes.rh.util.validacao;

import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.ParametrosDoSistema;

public class ValidacaoQuantidadeConstraints extends Validacao{

	public void execute(ParametrosDoSistema parametrosDoSistema) throws FortesException {
		Integer qtdConstraints = getParametrosDoSistemaManager().getQuantidadeConstraintsDoBanco();
		if(!qtdConstraints.equals(parametrosDoSistema.getQuantidadeConstraints()))
			throw new FortesException("A integridade das tabelas do banco de dados está inconsistente. Entre em contato com o suporte.");
	}
}

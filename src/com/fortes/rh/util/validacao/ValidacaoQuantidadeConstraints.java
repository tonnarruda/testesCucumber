package com.fortes.rh.util.validacao;

import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.ParametrosDoSistema;

public class ValidacaoQuantidadeConstraints extends Validacao{

	public void execute(ParametrosDoSistema parametrosDoSistema) throws FortesException {
		Integer qtdConstraints = getParametrosDoSistemaManager().getQuantidadeConstraintsDoBanco();
		if(!qtdConstraints.equals(parametrosDoSistema.getQuantidadeConstraints()))
			throw new FortesException("A estrutura do banco de dados está diferente da esperada. É de extrema importância que você entre em contato com a Fortes Tecnologia para que o problema seja analisado.");
	}
}

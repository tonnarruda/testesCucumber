package com.fortes.portalcolaborador.business.operacao;

import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;

public class ExcluirEmpresa extends Operacao {

	@Override
	public URLTransacaoPC getUrlTransacaoPC()
	{
		return URLTransacaoPC.REMOVER_EMPRESA;
	}
}

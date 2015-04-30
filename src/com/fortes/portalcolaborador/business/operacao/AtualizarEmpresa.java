package com.fortes.portalcolaborador.business.operacao;

import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;

public class AtualizarEmpresa extends Operacao {

	@Override
	public URLTransacaoPC getUrlTransacaoPC()
	{
		return URLTransacaoPC.ATUALIZAR_EMPRESA;
	}
}

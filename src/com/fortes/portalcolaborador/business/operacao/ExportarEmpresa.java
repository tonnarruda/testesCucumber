package com.fortes.portalcolaborador.business.operacao;

import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;

public class ExportarEmpresa extends Operacao {

	@Override
	public URLTransacaoPC getUrlTransacaoPC()
	{
		return URLTransacaoPC.EMPRESA_ATUALIZAR;
	}
}

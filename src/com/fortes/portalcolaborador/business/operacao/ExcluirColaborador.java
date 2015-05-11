package com.fortes.portalcolaborador.business.operacao;

import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;

public class ExcluirColaborador extends Operacao {

	@Override
	public URLTransacaoPC getUrlTransacaoPC()
	{
		return URLTransacaoPC.REMOVER_COLABORADOR;
	}
}

package com.fortes.portalcolaborador.business;

import com.fortes.business.GenericManager;
import com.fortes.portalcolaborador.model.ColaboradorPC;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;

public interface ColaboradorPCManager extends GenericManager<ColaboradorPC> {

	void enfileirarComHistoricos(URLTransacaoPC uRLTransacaoPC, Long empresaId, Long... colaboradoresIds);

}
package com.fortes.portalcolaborador.business;

import com.fortes.business.GenericManager;
import com.fortes.portalcolaborador.model.ColaboradorPC;

public interface ColaboradorPCManager extends GenericManager<ColaboradorPC> {

	void enfileirarColaboradoresPCComHistoricos(Long colaboradorId, Long empresaId);

}

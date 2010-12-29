package com.fortes.rh.business.geral;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;

public interface ConfiguracaoRelatorioDinamicoManager extends GenericManager<ConfiguracaoRelatorioDinamico>
{

	void update(String campos, String titulo, Long usuarioId);

	public ConfiguracaoRelatorioDinamico findByUsuario(long usuarioId);
}

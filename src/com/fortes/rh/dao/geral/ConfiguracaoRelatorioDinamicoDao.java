package com.fortes.rh.dao.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;

public interface ConfiguracaoRelatorioDinamicoDao extends GenericDao<ConfiguracaoRelatorioDinamico> 
{

	void removeByUsuario(Long usuarioId);

	ConfiguracaoRelatorioDinamico findByUsuario(Long usuarioId);
}

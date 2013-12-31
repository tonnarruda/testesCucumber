package com.fortes.rh.business.geral;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.UsuarioNoticia;

public interface UsuarioNoticiaManager extends GenericManager<UsuarioNoticia>
{
	void marcarLida(Long id, Long id2);
}
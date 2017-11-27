package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.UsuarioAjudaESocial;

public interface UsuarioAjudaESocialManager extends GenericManager<UsuarioAjudaESocial>
{
	void saveUsuarioAjuda(Long usuarioId, String localAjuda) throws Exception;
	Collection<String> findByUsuarioId(Long usuarioId);
}
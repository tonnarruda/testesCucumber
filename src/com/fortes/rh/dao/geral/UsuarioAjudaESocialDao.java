package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.UsuarioAjudaESocial;

public interface UsuarioAjudaESocialDao extends GenericDao<UsuarioAjudaESocial> 
{
	Collection<String> findByUsuarioId(Long usuarioId);
}

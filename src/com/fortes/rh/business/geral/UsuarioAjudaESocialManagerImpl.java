package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.UsuarioAjudaESocialDao;
import com.fortes.rh.model.geral.UsuarioAjudaESocial;

public class UsuarioAjudaESocialManagerImpl extends GenericManagerImpl<UsuarioAjudaESocial, UsuarioAjudaESocialDao> implements UsuarioAjudaESocialManager
{
	public void saveUsuarioAjuda(Long usuarioId, String localAjuda) throws Exception
	{
		UsuarioAjudaESocial usuarioAjudaESocial = new UsuarioAjudaESocial();
		usuarioAjudaESocial.setUsuarioId(usuarioId);
		usuarioAjudaESocial.setTelaAjuda(localAjuda);
		
		this.save(usuarioAjudaESocial);
	}
	
	public Collection<String> findByUsuarioId(Long usuarioId)
	{
		return getDao().findByUsuarioId(usuarioId);
	}
}
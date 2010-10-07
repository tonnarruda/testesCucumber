package com.fortes.rh.business.acesso;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.desenvolvimento.Curso;

public interface PerfilManager extends GenericManager<Perfil>
{
	String[] montaPermissoes(Perfil perfil);
	Collection<String> getEmailsByRoleLiberaSolicitacao(Long empresaId);
	public Integer getCount();
	public Collection<Perfil> findAll(Integer page, Integer pagingSize);
	
}
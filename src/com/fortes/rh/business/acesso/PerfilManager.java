package com.fortes.rh.business.acesso;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.Perfil;

public interface PerfilManager extends GenericManager<Perfil>
{
	String[] montaPermissoes(Perfil perfil);
	Collection<String> getEmailsByRoleLiberaSolicitacao(Long empresaId);
	public Integer getCount();
	public Collection<Perfil> findAll(Integer page, Integer pagingSize);
	Collection<Perfil> findPapeis(Long[] perfisIds);
	public void removePerfilPapelByPapelId(Long papelId);
	
}
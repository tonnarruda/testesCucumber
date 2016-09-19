package com.fortes.rh.dao.acesso;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.acesso.Perfil;

/**
 * @author Igo Coelho
 */
public interface PerfilDao extends GenericDao<Perfil> 
{
	Collection<Perfil> findPerfisByCodigoPapel(String codigo);
	Collection<Perfil> findAll(Integer page, Integer pagingSize);
	Integer getCount();
	Collection<Perfil> findByIds(Long[] perfisIds);
	void removePerfilPapelByPapelId(Long papelId);
}
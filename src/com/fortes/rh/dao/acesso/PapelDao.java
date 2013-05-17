/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32 */
package com.fortes.rh.dao.acesso;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.acesso.Papel;

public interface PapelDao extends GenericDao<Papel> 
{
	Collection<Papel> findByPerfil(Long perfilId);

	Collection<Papel> findNotIn(Collection<Long> ids);
}
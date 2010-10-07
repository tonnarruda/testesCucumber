/* Autor: Igo Coelho
 * Data: 29/06/2006
 * Requisito: RFA020 */
package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.EmpresaBds;

public interface EmpresaBdsDao extends GenericDao<EmpresaBds>
{
	Collection<EmpresaBds> findAllProjection(Long[] empresaBdsIds);

	EmpresaBds findByIdProjection(Long empresaBdsId);
}
/* Autor: Igo Coelho
 * Data: 29/06/2006
 * Requisito: RFA020
 */

package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.EmpresaBds;

public interface EmpresaBdsManager extends GenericManager<EmpresaBds>
{
	public Collection<EmpresaBds> findAllProjection(Long[] empresaBdsIds);

	public EmpresaBds findByIdProjection(Long empresaBdsId);
}
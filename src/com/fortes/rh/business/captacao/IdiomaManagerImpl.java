/* autor: Moesio Medeiros
 * Data: 16/06/2006
 * Requisito: RFA029 - Cadastro de curriculum
 */

package com.fortes.rh.business.captacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.IdiomaDao;
import com.fortes.rh.model.captacao.Idioma;

@Component
public class IdiomaManagerImpl extends GenericManagerImpl<Idioma, IdiomaDao> implements IdiomaManager
{
    /** add more methods here **/
	
	@Autowired
	IdiomaManagerImpl(IdiomaDao idiomaDao) {
		setDao(idiomaDao);
	}
}
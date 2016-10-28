/* Autor: Bruno Bachiega
 * Data: 16/06/2006 
 * Requisito: RFA0026 */
package com.fortes.rh.business.geral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.DependenteDao;
import com.fortes.rh.model.geral.Dependente;

@Component
public class DependenteManagerImpl extends GenericManagerImpl<Dependente, DependenteDao> implements DependenteManager
{
	@Autowired
	DependenteManagerImpl(DependenteDao dao) {
		setDao(dao);
	}
}
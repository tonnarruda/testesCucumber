package com.fortes.rh.business.desenvolvimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.PrioridadeTreinamentoDao;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;

/**
 * @author $author
 *
 */
@Component
public class PrioridadeTreinamentoManagerImpl extends GenericManagerImpl<PrioridadeTreinamento, PrioridadeTreinamentoDao> implements PrioridadeTreinamentoManager
{
    /** add more methods here **/
	@Autowired
	PrioridadeTreinamentoManagerImpl(PrioridadeTreinamentoDao dao) {
		setDao(dao);
	}
}
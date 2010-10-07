package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;

public interface ComissaoPeriodoDao extends GenericDao<ComissaoPeriodo>
{
	Collection<ComissaoPeriodo> findByComissao(Long comissaoId);
	ComissaoPeriodo findByIdProjection(Long id);
	ComissaoPeriodo findProximo(ComissaoPeriodo comissaoPeriodo);
}
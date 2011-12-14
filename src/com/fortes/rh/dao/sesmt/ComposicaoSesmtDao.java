package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;

public interface ComposicaoSesmtDao extends GenericDao<ComposicaoSesmt> 
{

	Collection<ComposicaoSesmt> findAllSelect(Long empresaId);

	ComposicaoSesmt findByIdProjection(Long composicaoSesmtId);

}

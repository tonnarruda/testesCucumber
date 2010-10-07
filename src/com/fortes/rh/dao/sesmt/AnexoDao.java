package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Anexo;

public interface AnexoDao extends GenericDao<Anexo>
{
	Collection<Anexo> findByOrigem(Long origemId, char origem);

}
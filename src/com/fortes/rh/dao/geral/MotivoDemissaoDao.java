package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.MotivoDemissao;

public interface MotivoDemissaoDao extends GenericDao<MotivoDemissao> 
{
	Collection<MotivoDemissao> findAllSelect(Long empresaId);
	Collection<MotivoDemissao> findMotivoDemissao(Integer page, Integer pagingSize,	Long empresaId, String motivo, Boolean ativo);
}
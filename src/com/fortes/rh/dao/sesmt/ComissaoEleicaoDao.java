package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ComissaoEleicao;

public interface ComissaoEleicaoDao extends GenericDao<ComissaoEleicao> 
{
	Collection<ComissaoEleicao> findByEleicao(Long eleicaoId);

	ComissaoEleicao findByIdProjection(Long id);

	void updateFuncao(Long id, String funcao);

	void removeByEleicao(Long eleicaoId);
}
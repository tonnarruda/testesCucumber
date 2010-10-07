package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Comissao;

public interface ComissaoDao extends GenericDao<Comissao>
{
	Collection<Comissao> findByEleicao(Long eleicaoId);
	Comissao findByIdProjection(Long comissaoId);
	Collection<Comissao> findAllSelect(Long empresaId);
	boolean updateTextosComunicados(Comissao comissao);
}
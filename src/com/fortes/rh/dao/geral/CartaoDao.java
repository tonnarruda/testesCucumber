package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Cartao;

public interface CartaoDao extends GenericDao<Cartao>
{
	Cartao findByEmpresaIdAndTipo(Long empresaId, String tipoCartao);
	Collection<Cartao> findByEmpresaId(Long empresaId);
}
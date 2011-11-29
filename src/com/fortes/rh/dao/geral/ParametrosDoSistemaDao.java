package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.Relacionamento;

public interface ParametrosDoSistemaDao extends GenericDao<ParametrosDoSistema>
{
	ParametrosDoSistema findByIdProjection(Long id);
	void updateModulos(String papeis);
	void disablePapeisIds();
	Collection<String> findTabelasRelacionadas(String relacionamento);
	Collection<Relacionamento> findRelacionamentoByTabela(Long[] ids, String tabela, String relacionamento);
}
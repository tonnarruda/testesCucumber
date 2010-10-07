package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Extintor;

public interface ExtintorDao extends GenericDao<Extintor>
{

	Integer getCount(Long empresaId, String tipoBusca, Integer numeroBusca, Boolean ativo);
	Collection<Extintor> findAllSelect(int page, int pagingSize, Long empresaId, String tipoBusca, Integer numeroBusca, Boolean valorAtivo);
	Collection<Extintor> findByEstabelecimento(Long estabelecimentoId, Boolean ativo);
	Collection<String> findFabricantesDistinctByEmpresa(Long empresaId);
	Collection<String> findLocalizacoesDistinctByEmpresa(Long empresaId);
}
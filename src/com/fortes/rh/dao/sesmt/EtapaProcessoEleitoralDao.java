package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;

public interface EtapaProcessoEleitoralDao extends GenericDao<EtapaProcessoEleitoral>
{
	Collection<EtapaProcessoEleitoral> findAllSelect(Long empresaId, Long eleicaoId, String orderBy);
	void removeByEleicao(Long eleicaoId);
}
package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;

public interface SolicitacaoEpiItemDao extends GenericDao<SolicitacaoEpiItem>
{
	Collection<SolicitacaoEpiItem> findBySolicitacaoEpi(Long solicitacaoEpiId);
	void removeAllBySolicitacaoEpi(Long solicitacaoEpiId);
	Collection<SolicitacaoEpiItem> findAllEntregasBySolicitacaoEpi(Long solicitacaoEpiId);
	SolicitacaoEpiItem findBySolicitacaoEpiAndEpi(Long id, Long long1);
	SolicitacaoEpiItem findByIdProjection(Long id);
	Integer countByTipoEPIAndTamanhoEPI(Long tipoEPIId, Long tamanhoEPIId);
}
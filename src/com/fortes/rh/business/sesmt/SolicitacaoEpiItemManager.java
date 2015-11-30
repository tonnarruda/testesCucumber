package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;

public interface SolicitacaoEpiItemManager extends GenericManager<SolicitacaoEpiItem>
{
	Collection<SolicitacaoEpiItem> findBySolicitacaoEpi(Long solicitacaoEpiId);
	void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, String[] selectMotivoSolicitacaoEpi, Date dataEntrega, boolean entregue, String[] selectTamanhoEpi);
	void removeAllBySolicitacaoEpi(Long solicitacaoEpiId);
	Collection<SolicitacaoEpiItem> findAllEntregasBySolicitacaoEpi(Long solicitacaoEpiId);
	SolicitacaoEpiItem findByIdProjection(Long id);
	Integer countByTipoEPIAndTamanhoEPI(Long tipoEPIId, Long tamanhoEPIId);
	Collection<SolicitacaoEpiItem> findAllDevolucoesBySolicitacaoEpi(Long solicitacaoEpiId);
	SolicitacaoEpiItem populaEPIsEntreguesDevolvidos(SolicitacaoEpiItem solicitacaoEpiItem);
}
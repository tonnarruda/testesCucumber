package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;

public interface EtapaProcessoEleitoralManager extends GenericManager<EtapaProcessoEleitoral>
{
	Collection<EtapaProcessoEleitoral> findAllSelect(Long empresaId, Long eleicaoId);
	void clonarEtapas(Long empresaId, Eleicao eleicao);
	void removeByEleicao(Long eleicaoId);
	Eleicao findImprimirCalendario(Long eleicaoId);
	void gerarEtapasModelo(Empresa empresa);
	void updatePrazos(Collection<EtapaProcessoEleitoral> etapaProcessoEleitorals, Date posse);
}
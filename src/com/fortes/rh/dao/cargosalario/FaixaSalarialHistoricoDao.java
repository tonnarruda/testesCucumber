package com.fortes.rh.dao.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistoricoVO;

public interface FaixaSalarialHistoricoDao extends GenericDao<FaixaSalarialHistorico>
{
	Collection<FaixaSalarialHistorico> findAllSelect(Long faixaSalarialId);
	FaixaSalarialHistorico findByIdProjection(Long faixaSalarialHistoricoId);
	boolean verifyData(Long faixaSalarialHistoricoId, Date data, Long faixaSalarialId);
	FaixaSalarialHistorico findByFaixaSalarialId(Long faixaSalariaId);
	Collection<FaixaSalarialHistorico> findByPeriodo(Long faixaSalarialId, Date dataProxima);
	Collection<FaixaSalarialHistorico> findByGrupoCargoAreaData(Collection<Long> grupoOcupacionals, Collection<Long> cargoIds, Collection<Long> areaIds, Date data, boolean ordemDataDescendente, Long empresaId);
	boolean setStatus(Long faixaSalarialHistoricoId, boolean aprovado);
	void removeByFaixas(Long[] faixaSalarialIds);
	Collection<FaixaSalarialHistorico> findPendenciasByFaixaSalarialHistorico(Long empresaId);
	Collection<FaixaSalarialHistorico> findHistoricosByFaixaSalarialId(Long faixaSalarialId);
	Long findIdByDataFaixa(FaixaSalarialHistorico faixaSalarialHistorico);
	Collection<FaixaSalarialHistoricoVO> findAllComHistoricoIndice(Long faixaSalarialId);
	void deleteByFaixaSalarial(Long[] faixaIds) throws Exception;
	Collection<FaixaSalarialHistorico> findByTabelaReajusteId(Long tabelaReajusteColaboradorId);
}
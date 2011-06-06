package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistoricoVO;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.ws.TSituacaoCargo;

public interface FaixaSalarialHistoricoManager extends GenericManager<FaixaSalarialHistorico>
{
	FaixaSalarialHistorico save(FaixaSalarialHistorico faixaSalarialHistorico, FaixaSalarial faixaSalarial, Empresa empresa, boolean salvaNoAC) throws Exception;
	void update(FaixaSalarialHistorico faixaSalarialHistorico, FaixaSalarial faixaSalarial, Empresa empresa) throws Exception;
	Collection<FaixaSalarialHistorico> findAllSelect(Long faixaSalarialId);
	FaixaSalarialHistorico findByIdProjection(Long faixaSalarialHistoricoId);
	boolean verifyData(Long faixaSalarialHistoricoId, Date data, Long faixaSalarialId);
	Double findUltimoHistoricoFaixaSalarial(Long faixaSalarialId);
	FaixaSalarialHistorico findByHistoricoFaixaSalarial(Long faixaSalarialId);
	Collection<FaixaSalarialHistorico> findByPeriodo(Long faixaSalarialId, Date data, Date dataProxima);
	Collection<FaixaSalarialHistorico> findByGrupoCargoAreaData(String[] grupoOcupacionalsCheck, String[] cargosCheck, String[] areasCheck, Date data) throws Exception;
	boolean verifyHistoricoIndiceNaData(Date data, Long indiceId);
	boolean setStatus(Long faixaSalarialHistoricoId, boolean aprovado);
	void remove(Long faixaSalarialHistoricoId, Empresa empresa) throws Exception;
	void removeByFaixas(Long[] faixaSalarialIds);
	Collection<PendenciaAC> findPendenciasByFaixaSalarialHistorico(Long empresaId);
	void sincronizar(Map<Long, Long> faixaSalarialIds);
	FaixaSalarialHistorico bind(TSituacaoCargo tSituacaoCargo, FaixaSalarial faixaSalarial);
	Long findIdByDataFaixa(FaixaSalarialHistorico faixaSalarialHistorico);
	Collection<FaixaSalarialHistoricoVO> findAllComHistoricoIndice(Long faixaSalarialId);
}
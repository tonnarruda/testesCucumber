package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.FaixaJaCadastradaException;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistoricoVO;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.ws.TSituacaoCargo;
import com.fortes.rh.security.spring.aop.callback.FaixaSalarialHistoricoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.security.auditoria.Modulo;

@Modulo("Histórico Faixa Salarial")
public interface FaixaSalarialHistoricoManager extends GenericManager<FaixaSalarialHistorico>
{
	@Audita(operacao="Inserção", auditor=FaixaSalarialHistoricoAuditorCallbackImpl.class)
	FaixaSalarialHistorico save(FaixaSalarialHistorico faixaSalarialHistorico, FaixaSalarial faixaSalarial, Empresa empresa, boolean salvaNoAC) throws Exception;
	void criarFaixaSalarialHistoricoNoAc(FaixaSalarialHistorico faixaSalarialHistorico, Empresa empresa) throws Exception;
	@Audita(operacao="Atualização", auditor=FaixaSalarialHistoricoAuditorCallbackImpl.class)
	void update(FaixaSalarialHistorico faixaSalarialHistorico, FaixaSalarial faixaSalarial, Empresa empresa) throws Exception;
	Collection<FaixaSalarialHistorico> findAllSelect(Long faixaSalarialId);
	FaixaSalarialHistorico findByIdProjection(Long faixaSalarialHistoricoId);
	boolean verifyData(Long faixaSalarialHistoricoId, Date data, Long faixaSalarialId);
	Double findUltimoHistoricoFaixaSalarial(Long faixaSalarialId);
	FaixaSalarialHistorico findByHistoricoFaixaSalarial(Long faixaSalarialId);
	Collection<FaixaSalarialHistorico> findByPeriodo(Long faixaSalarialId, Date data, Date dataProxima);
	Collection<FaixaSalarialHistorico> findByGrupoCargoAreaData(String[] grupoOcupacionalsCheck, String[] cargosCheck, String[] areasCheck, Date data, boolean ordemDataDescendente, Long empresaId, Boolean cargoAtivo) throws Exception;
	boolean verifyHistoricoIndiceNaData(Date data, Long indiceId);
	boolean setStatus(Long faixaSalarialHistoricoId, boolean aprovado);
	@Audita(operacao="Remoção", auditor=FaixaSalarialHistoricoAuditorCallbackImpl.class)
	void remove(Long faixaSalarialHistoricoId, Empresa empresa, boolean removerDoAC) throws Exception;
	void removeByFaixas(Long[] faixaSalarialIds);
	Collection<PendenciaAC> findPendenciasByFaixaSalarialHistorico(Long empresaId);
	FaixaSalarialHistorico sincronizar(Long faixaSalarialOrigemId, Long faixaSalarialDestinoId, Empresa empresaDestino) throws FaixaJaCadastradaException;
	FaixaSalarialHistorico bind(TSituacaoCargo tSituacaoCargo, FaixaSalarial faixaSalarial);
	Long findIdByDataFaixa(FaixaSalarialHistorico faixaSalarialHistorico);
	Collection<FaixaSalarialHistoricoVO> findAllComHistoricoIndice(Long faixaSalarialId);
	void deleteByFaixaSalarial(Long[] faixaIds) throws Exception;
	Collection<FaixaSalarialHistorico> findByTabelaReajusteId(Long tabelaReajusteColaboradorId);
	Collection<FaixaSalarialHistorico> findByTabelaReajusteIdData(Long tabelaReajusteColaboradorId, Date data);
	boolean existeHistoricoPorIndice(Long empresaId);
	boolean existeDependenciaComHistoricoIndice(Date dataHistoricoExcluir, Long indiceId);
}
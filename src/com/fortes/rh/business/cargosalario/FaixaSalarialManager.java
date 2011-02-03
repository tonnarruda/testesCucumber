package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;

public interface FaixaSalarialManager extends GenericManager<FaixaSalarial>
{
	FaixaSalarial findCodigoACById(Long id);
	void saveFaixaSalarial(FaixaSalarial faixaSalarial, FaixaSalarialHistorico faixaSalarialHistorico, Empresa empresa, String[] certificacaosCheck) throws Exception;
	void updateFaixaSalarial(FaixaSalarial faixaSalarial, Empresa empresa, String[] certificacaosCheck) throws Exception;
	void deleteFaixaSalarial(Long id, Empresa empresa) throws Exception;
	Collection<FaixaSalarial> findAllSelectByCargo(Long empresaId);
	FaixaSalarial findByFaixaSalarialId(Long faixaSalarialId);
	Collection<FaixaSalarial> findFaixaSalarialByCargo(Long cargoId);
	public Collection<FaixaSalarial> findFaixas(Empresa empresa, Boolean ativo);
	public FaixaSalarial findHistoricoAtual(Long faixaSalarialId);
	boolean verifyExistsNomeByCargo(Long cargoId, String faixaNome);
	void removeFaixaAndHistoricos(Long[] faixaSalarialIds);
	FaixaSalarial findFaixaSalarialByCodigoAc(String faixaCodigoAC, String empresaCodigoAC) throws Exception;
	FaixaSalarial findHistorico(Long faixaSalarialId, Date dataHistorico);
	void transfereFaixasCargo(FaixaSalarial faixa, Cargo cargo, Empresa empresaSistema) throws Exception;
	Map<Object, Object> findByCargo(String cargoId);
	Collection<Long> findByCargos(Collection<Long> cargoIds);
	Collection<FaixaSalarial> findByCargo(Long cargoId);
	void sincronizar(Map<Long, Long> cargoIds);
	FaixaSalarial montaFaixa(TCargo tCargo);
	void updateAC(TCargo tCargo);
	TCargo[] getFaixasAC();
}
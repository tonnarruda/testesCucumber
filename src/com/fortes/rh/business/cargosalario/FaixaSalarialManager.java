package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.security.spring.aop.callback.FaixaSalarialAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.security.auditoria.Modulo;

@Modulo("Faixa Salarial")
public interface FaixaSalarialManager extends GenericManager<FaixaSalarial>
{
	FaixaSalarial findCodigoACById(Long id);
	@Audita(operacao="Inserção", auditor=FaixaSalarialAuditorCallbackImpl.class)
	void saveFaixaSalarial(FaixaSalarial faixaSalarial, FaixaSalarialHistorico faixaSalarialHistorico, Empresa empresa, String[] certificacaosCheck) throws Exception;
	void updateFaixaSalarial(FaixaSalarial faixaSalarial, Empresa empresa, String[] certificacaosCheck) throws Exception;
	void deleteFaixaSalarial(Long id, Empresa empresa) throws Exception;
	Collection<FaixaSalarial> findAllSelectByCargo(Long empresaId);
	FaixaSalarial findByFaixaSalarialId(Long faixaSalarialId);
	Collection<FaixaSalarial> findFaixaSalarialByCargo(Long cargoId);
	public Collection<FaixaSalarial> findFaixas(Empresa empresa, Boolean ativo, Long faixaInativaId);
	public FaixaSalarial findHistoricoAtual(Long faixaSalarialId);
	boolean verifyExistsNomeByCargo(Long cargoId, String faixaNome);
	void removeFaixaAndHistoricos(Long[] faixaSalarialIds) throws Exception;
	FaixaSalarial findFaixaSalarialByCodigoAc(String faixaCodigoAC, String empresaCodigoAC, String grupoAC) throws Exception;
	FaixaSalarial findHistorico(Long faixaSalarialId, Date dataHistorico);
	void transfereFaixasCargo(FaixaSalarial faixa, Cargo cargo, Empresa empresaSistema) throws Exception;
	Map<Object, Object> findByCargo(String cargoId);
	Collection<FaixaSalarial> findByCargo(Long cargoId);
	public Collection<FaixaSalarial> findByCargoComCompetencia(Long cargoId) throws Exception;
	void sincronizar(Long cargoOrigemId, Cargo cargoDestino, Empresa empresaDestino) throws Exception;
	FaixaSalarial montaFaixa(TCargo tCargo);
	void updateAC(TCargo tCargo);
	TCargo[] getFaixasAC();
	Collection<FaixaSalarial> findSemCodigoAC(Long empresaId);
	void deleteFaixaSalarial(Long[] faixaIds) throws Exception;
	String findCodigoACDuplicado(Long empresaId);
	Collection<FaixaSalarial> findByCargos(Long[] cargosIds);
	Collection<FaixaSalarial> findComHistoricoAtual(Long[] faixasSalariaisIds);
	Collection<FaixaSalarial> findComHistoricoAtualByEmpresa(Long empresaId, boolean semCodigoAC);
	public Collection<FaixaSalarial> geraDadosRelatorioResumidoColaboradoresPorCargoXLS(List<HistoricoColaborador> historicoColaboradores);

}
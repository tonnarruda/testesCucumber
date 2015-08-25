package com.fortes.rh.dao.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;

/**
 * @author Igo Coelho
 */
public interface FaixaSalarialDao extends GenericDao<FaixaSalarial>
{
	FaixaSalarial findCodigoACById(Long id);
	Collection<FaixaSalarial> findAllSelectByCargo(Long empresaId);
	FaixaSalarial findByFaixaSalarialId(Long faixaSalarialId);
	Collection<FaixaSalarial> findFaixaSalarialByCargo(Long cargoId);
	FaixaSalarial findHistoricoAtual(Long faixaSalarialId, Date dataHistorico);
	Collection<FaixaSalarial> findComHistoricoAtual(Long[] faixasSalariaisIds);
	Collection<FaixaSalarial> findFaixas(Empresa empresa, Boolean ativo, Long faixaInativaId);
	void updateCodigoAC(String codigoAC, Long faixaSalarialId);
	boolean verifyExistsNomeByCargo(Long cargoId, String faixaNome);
	FaixaSalarial findFaixaSalarialByCodigoAc(String faixaCodigoAC, String empresaCodigoAC, String grupoAC);
	public void updateNomeECargo(Long faixaSalarialId, Long cargoId, String faixaNome);
	Collection<FaixaSalarial> findByCargo(Long cargoId);
	void updateAC(TCargo tCargo);
	TCargo[] getFaixasAC();
	Collection<FaixaSalarial> findSemCodigoAC(Long empresaId);
	String findCodigoACDuplicado(Long empresaId);
	public Collection<FaixaSalarial> findByCargoComCompetencia(Long cargoId);
	Collection<FaixaSalarial> findByCargos(Long[] cargosIds);
	Collection<FaixaSalarial> findComHistoricoAtualByEmpresa(Long empresaId, boolean semCodigoAC);
}
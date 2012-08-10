/* Autor: Bruno Bachiega
 * Data: 8/06/2006
 * Requisito: RFA0026
 */
package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.security.spring.aop.callback.CargoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.web.tags.CheckBox;

public interface CargoManager extends GenericManager<Cargo>
{
	@Audita(operacao="Remoção", auditor=CargoAuditorCallbackImpl.class)
	public void remove(Long cargoId, Empresa empresa)throws Exception;

	public Integer getCount(Long empresaId, Long areaId, String cargoNome, Boolean ativo);
	public Collection<Cargo> findCargos(int page, int pagingSize, Long empresaId, Long areaId, String cargoNome, Boolean ativo);
	public Collection<Cargo> findByGrupoOcupacionalIdsProjection(Long[] idsLong, Long empresaId);
	public Collection<Cargo> findByAreasOrganizacionalIdsProjection(Long[] idsLong, Long empresaId);
	public Collection<Cargo> getCargosByIds(Long[] cargoDoubleList, Long empresaId) throws Exception;
	public Collection<Cargo> findAllSelect(Long empresaId, String ordenarPor);
	public Cargo findByIdProjection(Long id);
	public Collection<Cargo> populaCargos(String[] cargosCheck);
	public Collection<Cargo> populaCargos(Long[] ids);
	public Cargo findByIdAllProjection(Long cargoId);
	public Collection<CheckBox> populaCheckBox(Long empresaId);
	public Collection<CheckBox> populaCheckBox(String[] gruposCheck, String[] cargosCheck, Long empresaId) throws Exception;
	public Collection<Cargo> getCargosFromFaixaSalarialHistoricos(Collection<FaixaSalarialHistorico> faixaSalarialHistoricos);
	public Collection<Cargo> findAllSelectModuloExterno(Long empresaId, String ordenarPor);
	boolean verifyExistCargoNome(String cargoNome, Long empresaId);
	public Collection<Cargo> findByGrupoOcupacional(Long grupoOcupacionalId);
	public Map findByAreaDoHistoricoColaborador(String[] areaOrganizacionalIds);
	public Collection<CheckBox> populaCheckBoxAllCargos();
	public Collection<Cargo> findBySolicitacao(Long solicitacaoId);
	public Collection<Cargo> findAllSelectDistinctNome();
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds, Map<Long, Long> areaInteresseIds, Map<Long, Long> conhecimentoIds, Map<Long, Long> habilidadeIds, Map<Long, Long> atitudeIds);
	public Collection<Cargo> findByEmpresaAC(String empCodigo, String codigo, String grupoAC);
	public Cargo preparaCargoDoAC(TCargo tCargo);
	public void updateCBO(Long id, TCargo tCargo);
	public Collection<Cargo> findAllSelect(Long[] empresaIds);
	public Collection<Cargo> findByArea(Long areaOrganizacionalId, Long empresaId);
	public String nomeCargos(Long[] cargosIds);
	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception;

	public boolean existemCargosSemAreaRelacionada(Long empresaId);
	public Collection<Cargo> getCargosSemAreaRelacionada(Long empresaId);

	public Collection<Cargo> findByAreaGrupo(Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId);
}
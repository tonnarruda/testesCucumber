/*
 * Autor: Bruno Bachiega
 * Data: 8/06/2006
 * Requisito: RFA008
*/
package com.fortes.rh.dao.cargosalario;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.ws.TCargo;

public interface CargoDao extends GenericDao<Cargo>
{
//	public Collection<Cargo> findByGrupoOcupacionalIds(Long[] gruposOcupacionaisIds);
	public Integer getCount(Long empresaId, Long areaId, String cargoNome, Boolean ativo);
	public Collection<Cargo> findCargos(int page, int pagingSize, Long empresaId, Long areaId, String cargoNome, Boolean ativo);
	public Collection<Cargo> findByGrupoOcupacionalIdsProjection(Long[] idsLong, Long empresaId, Boolean cargoAtivo);
	public Collection<Cargo> findByAreaOrganizacionalIdsProjection(Long[] idsLong, Long empresaId);
	public Collection<Cargo> findCargosByIds(Long[] cargoIds, Long empresaId);
	public Collection<Cargo> findAllSelect(Long[] empresaIds, String ordenarPor, Boolean exibirModuloExterno, Boolean ativo);
	public Cargo findByIdProjection(Long cargoId);
	public Cargo findByIdAllProjection(Long cargoId);
	boolean verifyExistCargoNome(String cargoNome, Long empresaId);
	public Collection<Cargo> findByGrupoOcupacional(Long grupoOcupacionalId);
	public Collection<Cargo> findByAreaDoHistoricoColaborador(Long[] areaIds);
	public Collection<Cargo> findAllSelectDistinctNomeMercado(Long[] empresaIds);
	public Collection<Cargo> findBySolicitacao(Long solicitacaoId);
	public Collection<Cargo> findSincronizarCargos(Long empresaOrigemId);
	public Collection<Cargo> findByEmpresaAC(String empCodigo, String codigoFaixa, String grupoAC);
	public Collection<Cargo> findByEmpresaAC(String empCodigo, String grupoAC);
	public void updateCBO(Long id, TCargo tCargo);
	public Collection<Cargo> findAllSelect(Long[] empresaIds);
	public Collection<Cargo> findByArea(Long areaOrganizacionalId, Long empresaId);
	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception;
	public Collection<Cargo> getCargosSemAreaRelacionada(Long empresaId);
	public Collection<Cargo> findByAreaGrupo(Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId);
	public Collection<Cargo> getCargosSemGrupoRelacionado(Long empresaId);
	public Collection<Cargo> findByEmpresa(Long empresaId);
	public void insereAreaRelacionada(Long areaMaeId, Long areaId);
	public void removeViculoComHabilidade(Long habilidadeId);
}
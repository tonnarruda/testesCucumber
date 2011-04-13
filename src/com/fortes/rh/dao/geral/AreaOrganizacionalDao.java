/* Autor: Bruno Bachiega
 * Data: 7/06/2006
 * Requisito: RFA004 */
package com.fortes.rh.dao.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.AreaOrganizacional;

public interface AreaOrganizacionalDao extends GenericDao<AreaOrganizacional>
{
	public AreaOrganizacional findAreaOrganizacionalCodigoAc(Long id);
	public Integer getCount(String nome, Long empresaId);
	public Collection<AreaOrganizacional> findAllList(int page, int pagingSize, Long usuarioId, String nome, Long empresaId, Boolean ativo);
	public AreaOrganizacional findIdMaeById(long idArea);
	public Collection<AreaOrganizacional> findByCargo(Long cargoId);
	public boolean verificaMaternidade(Long areaOrganizacionalId);
	public Collection<AreaOrganizacional> findAreaIdsByAreaInteresse(Long areaInteresseId);
	public AreaOrganizacional findByIdProjection(Long areaId);
	public AreaOrganizacional findAreaOrganizacionalByCodigoAc(String areaCodigoAC, String empresaCodigoAC, String grupoAC);
	public Collection<AreaOrganizacional> findByConhecimento(Long conhecimentoId);
	public Collection<AreaOrganizacional> findQtdColaboradorPorArea(Long estabelecimentoId, Date dataIni);
	public Collection<AreaOrganizacional> findByEmpresasIds(Long[] empresaIds, Boolean ativo);
	public Collection<AreaOrganizacional> findSincronizarAreas(Long empresaOrigemId);
	public Collection<AreaOrganizacional> findAreas(Long[] areaIds);
	public Collection<AreaOrganizacional> findByEmpresa(Long empresaId);
}
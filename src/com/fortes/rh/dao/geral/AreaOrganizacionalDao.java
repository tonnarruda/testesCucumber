package com.fortes.rh.dao.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.AreaOrganizacional;

public interface AreaOrganizacionalDao extends GenericDao<AreaOrganizacional>
{
	public AreaOrganizacional findAreaOrganizacionalCodigoAc(Long id);
	public Collection<AreaOrganizacional> findAllList(int page, int pagingSize, Long usuarioId, String nome, Boolean ativo, Collection<Long> areaInativaIds, Long... empresasIds);
	public AreaOrganizacional findIdMaeById(long idArea);
	public Collection<AreaOrganizacional> findByCargo(Long cargoId);
	public boolean verificaMaternidade(Long areaOrganizacionalId, Boolean ativa);
	public Collection<AreaOrganizacional> findAreaIdsByAreaInteresse(Long areaInteresseId);
	public AreaOrganizacional findByIdProjection(Long areaId);
	public AreaOrganizacional findAreaOrganizacionalByCodigoAc(String areaCodigoAC, String empresaCodigoAC, String grupoAC);
	public Collection<AreaOrganizacional> findByConhecimento(Long conhecimentoId);
	public Collection<AreaOrganizacional> findByHabilidade(Long habilidadeId);
	public Collection<AreaOrganizacional> findByAtitude(Long atitudeId);
	public Collection<AreaOrganizacional> findQtdColaboradorPorArea(Long estabelecimentoId, Date dataIni);
	public Collection<AreaOrganizacional> findByEmpresasIds(Long[] empresaIds, Boolean ativo);
	public Collection<AreaOrganizacional> findSincronizarAreas(Long empresaOrigemId);
	public Collection<AreaOrganizacional> findAreas(Long[] areaIds);
	public Collection<AreaOrganizacional> findByEmpresa(Long empresaId);
	public Long[] findIdsAreasDoResponsavelCoResponsavel(Long usuarioId, Long empresaId);
	public Collection<AreaOrganizacional> findSemCodigoAC(Long empresaId);
	public Collection<Long> findIdsAreasFilhas(Collection<Long> areasIds);
	public void desvinculaResponsavel(Long... colaboradoresIds);
	public void desvinculaCoResponsavel(Long... colaboradoresIds);
	public void removeComDependencias(Long id);
	public Collection<AreaOrganizacional> findAreasDoResponsavelCoResponsavel(Long usuarioId, Long empresaId, Boolean ativo, Collection<Long> areaInativaIds);
	public Long[] findAreasMaesIdsByEmpresaId(Long empresaId);
	public boolean possuiAreaFilhasByCodigoAC(String codigoAC, Long empresaId);
	public Collection<AreaOrganizacional> findByLntId(Long lntId, Long... empresaIdAreaOrganizacional);
	public Collection<Long> getAncestraisIds (Long... areasIds);
	public Collection<Long> getDescendentesIds (Long... areasIds);
}
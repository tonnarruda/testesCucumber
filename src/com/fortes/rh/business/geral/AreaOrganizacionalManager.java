/* Autor: Bruno Bachiega
 * Data: 7/06/2006
 * Requisito: RFA0026
 */

package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.ws.TAreaOrganizacional;
import com.fortes.web.tags.CheckBox;

public interface AreaOrganizacionalManager extends GenericManager<AreaOrganizacional>
{
	public Collection getNaoFamilia(Collection areas, Long id);
	public void insertLotacaoAC(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception;
	public void editarLotacaoAC(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception;
	public void deleteLotacaoAC(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception;
	public boolean verificaMaternidade(Long areaOrganizacionalId);
	public Integer getCount(String nome, Long empresaId);
	public Collection<AreaOrganizacional> findAllList(Long empresaId, Boolean ativo);
	public Collection<AreaOrganizacional> findAllList(Long usuarioId, Long empresaId, Boolean ativo);
	public Collection<AreaOrganizacional> findAllList(int page, int pagingSize, String nome, Long empresaId, Boolean ativo);
	public boolean findAreasQueNaoPertencemAEmpresa(Collection<Long> areasOrganizacionais, Empresa empresa);
	public Collection<CheckBox> populaCheckOrderDescricao(long empresaId);
	public Collection<AreaOrganizacional> populaAreas(String[] areasCheck);
	public AreaOrganizacional findAreaOrganizacionalCodigoAc(Long idAreaOrganizacional);
	public AreaOrganizacional getAreaMae(Collection<AreaOrganizacional> todasAreas, AreaOrganizacional area);
	public Collection<AreaOrganizacional> montaFamilia(Collection<AreaOrganizacional> areaOrganizacionals) throws Exception;
	public Collection<AreaOrganizacional> findAllSelectOrderDescricao(Long empresaId, Boolean ativo) throws Exception;
	public Collection<AreaOrganizacional> getDistinctAreaMae(Collection<AreaOrganizacional> todasAreas, Collection<AreaOrganizacional> areaOrganizacionals);
	public Collection<AreaOrganizacional> findByCargo(Long cargoId);
	public Collection<AreaOrganizacional> montaAllSelect(Long empresaId);
	public AreaOrganizacional getAreaOrganizacional(Collection<AreaOrganizacional> areaOrganizacionals, Long areaOrganizacionalId);
	public Map<String, Object> getParametrosRelatorio(String nomeRelatorio, Empresa empresa, String filtro);
	public Collection<AreaOrganizacional> getAreasByAreaInteresse(Long areaInteresseId);
	public AreaOrganizacional findAreaOrganizacionalByCodigoAc(String areaCodigoAC, String empresaCodigoAC);
	public Collection<AreaOrganizacional> montaFamiliaOrdemDescricao(Long empresaId, Boolean ativo) throws Exception;
	public Collection<AreaOrganizacional> findByConhecimento(Long conhecimentoId);
	public AreaOrganizacional findByIdProjection(Long areaId);
	public Collection<AreaOrganizacional> findQtdColaboradorPorArea(Long estabelecimentoId, Date dataIni);
	public Collection<AreaOrganizacional> findByEmpresasIds(Long[] empresaIds, Boolean ativo);
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds);
	public Collection<ExamesPrevistosRelatorio> setFamiliaAreas(Collection<ExamesPrevistosRelatorio> examesPrevistosRelatorios, Long empresaId) throws Exception;
	public void bind(AreaOrganizacional areaOrganizacionalTmp, TAreaOrganizacional areaOrganizacional);
	public Collection<CheckBox> populaCheckOrderDescricao(Long[] empresaIds);
}
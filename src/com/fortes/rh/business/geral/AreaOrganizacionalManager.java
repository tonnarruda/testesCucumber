/* Autor: Bruno Bachiega
 * Data: 7/06/2006
 * Requisito: RFA0026
 */

package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.AreaColaboradorException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.ws.TAreaOrganizacional;
import com.fortes.rh.security.spring.aop.callback.AreaOrganizacionalAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.web.tags.CheckBox;

public interface AreaOrganizacionalManager extends GenericManager<AreaOrganizacional>
{
	@Audita(operacao="Inserção", auditor=AreaOrganizacionalAuditorCallbackImpl.class)
	public void insert(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception, AreaColaboradorException;
	@Audita(operacao="Atualização", auditor=AreaOrganizacionalAuditorCallbackImpl.class)
	public void update(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception;
	@Audita(operacao="Remoção", auditor=AreaOrganizacionalAuditorCallbackImpl.class)
	public void deleteLotacaoAC(AreaOrganizacional areaOrganizacional, Empresa empresa) throws IntegraACException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Collection getNaoFamilia(Collection areas, Long id);
	public boolean verificaMaternidade(Long areaOrganizacionalId, Boolean ativa);
	public Collection<AreaOrganizacional> findAllListAndInativas(Boolean ativo, Collection<Long> areaInativaIds, Long... empresasIds);
	public Collection<AreaOrganizacional> findAllList(Long usuarioId, Long empresaId, Boolean ativo, Long areaInativaId);
	public Collection<AreaOrganizacional> findAllList(int page, int pagingSize, String nome, Long empresaId, Boolean ativo);
	public Collection<CheckBox> populaCheckOrderDescricao(long empresaId);
	public Collection<CheckBox> populaCheckComParameters(long empresaId);
	public Collection<CheckBox> populaCheckByAreasOrderDescricao(Long[] areaIds);
	public Collection<AreaOrganizacional> populaAreas(String[] areasCheck);
	public AreaOrganizacional findAreaOrganizacionalCodigoAc(Long idAreaOrganizacional);
	public AreaOrganizacional getAreaMae(Collection<AreaOrganizacional> todasAreas, AreaOrganizacional area);
	public Collection<AreaOrganizacional> montaFamilia(Collection<AreaOrganizacional> areaOrganizacionals) throws Exception;
	public Collection<AreaOrganizacional> findAllSelectOrderDescricao(Long empresaId, Boolean ativo, Long faixaInativaId, boolean somenteFolhas) throws Exception;
	public Collection<AreaOrganizacional> getDistinctAreaMae(Collection<AreaOrganizacional> todasAreas, Collection<AreaOrganizacional> areaOrganizacionals);
	public Collection<AreaOrganizacional> findByCargo(Long cargoId);
	public Collection<AreaOrganizacional> montaAllSelect(Long empresaId);
	public AreaOrganizacional getAreaOrganizacional(Collection<AreaOrganizacional> areaOrganizacionals, Long areaOrganizacionalId);
	public Map<String, Object> getParametrosRelatorio(String nomeRelatorio, Empresa empresa, String filtro);
	public Collection<AreaOrganizacional> getAreasByAreaInteresse(Long areaInteresseId);
	public AreaOrganizacional findAreaOrganizacionalByCodigoAc(String areaCodigoAC, String empresaCodigoAC, String grupoAC);
	public Collection<AreaOrganizacional> findByConhecimento(Long conhecimentoId);
	public Collection<AreaOrganizacional> findByHabilidade(Long habilidadeId);
	public Collection<AreaOrganizacional> findByAtitude(Long atitudeId);
	public AreaOrganizacional findByIdProjection(Long areaId);
	public Collection<AreaOrganizacional> findQtdColaboradorPorArea(Long estabelecimentoId, Date dataIni);
	public Collection<AreaOrganizacional> findByEmpresasIds(Long[] empresaIds, Boolean ativo);
	public void sincronizar(Long empresaOrigemId, Empresa empresaDestino, Map<Long, Long> areaIds,  List<String> mensagens);
	public Collection<ExamesPrevistosRelatorio> setFamiliaAreas(Collection<ExamesPrevistosRelatorio> examesPrevistosRelatorios, Long empresaId) throws Exception;
	public void bind(AreaOrganizacional areaOrganizacionalTmp, TAreaOrganizacional areaOrganizacional) throws Exception;
	public Collection<CheckBox> populaCheckOrderDescricao(Long[] empresaIds);
	public Collection<AreaOrganizacional> findAreasPossiveis(Collection<AreaOrganizacional> areas, Long id);
	public Collection<AreaOrganizacional> getAncestrais(Collection<AreaOrganizacional> areas, Long id);
	public String nomeAreas(Long[] areaIds);
	public AreaOrganizacional getMatriarca(Collection<AreaOrganizacional> areas, AreaOrganizacional area, Long filhaDeId);
	public Collection<AreaOrganizacional> findByEmpresa(Long empresaId);
	public Long[] findIdsAreasDoResponsavelCoResponsavel(Long usuarioId, Long empresaId);
	public Long[] selecionaFamilia(Collection<AreaOrganizacional> areaOrganizacionais, Collection<Long> areasIdsConfiguradas);
	public Collection<AreaOrganizacional> findSemCodigoAC(Long empresaId);
	public void deleteAreaOrganizacional(Long[] areaIds) throws Exception;
	public Long[] findIdsAreasDoResponsavelCoResponsavel(Usuario usuarioLogado, Long empresaId);
	public Collection<AreaOrganizacional> findAreasByUsuarioResponsavel(Usuario usuario, Long empresaId) throws Exception;
	public Collection<AreaOrganizacional> getDescendentes(Collection<AreaOrganizacional> areas, Long id, Collection<AreaOrganizacional> descendentes);
	public Collection<AreaOrganizacional> getFilhos(Collection<AreaOrganizacional> areas, Long id);
	public String[] getEmailsResponsaveis(Long areaId, Long empresaId, int tipoResponsavel, String notEmail) throws Exception;
	public String[] getEmailsResponsaveis(Long areaId, Collection<AreaOrganizacional> todasAreas, int tipoResponsavel) throws Exception;
	public String[] getEmailsResponsaveis(Collection<AreaOrganizacional> areas, Long empresaId, int tipoResponsavel) throws Exception;
	public String getEmailResponsavel(Long areaId) throws Exception;
	public void desvinculaResponsaveis(Long... colaboradoresIds);
	public boolean verificaAlteracaoStatusAtivo(Long areaId, Long areaMaeId);
	public Collection<AreaOrganizacional> ordenarAreasHierarquicamente(Collection<AreaOrganizacional> areas, Collection<Long> areasIds, int nivelHierarquico);
	public String getMascaraLotacoesAC(Empresa empresa) throws Exception;
	@Audita(operacao="Remoção", auditor=AreaOrganizacionalAuditorCallbackImpl.class)
	public void removeComDependencias(Long id) throws Exception;
	public Collection<AreaOrganizacional> findAllSelectOrderDescricaoByUsuarioId(Long empresaId, Long usuarioId, Boolean ativo, Long areaInativaId) throws Exception;
	public Collection<AreaOrganizacional> findAllListAndInativasByUsuarioId(Long empresaId, Long usuarioId, Boolean ativo, Collection<Long> areaInativaIds);
	public Collection<Long> findIdsAreasFilhas(Collection<Long> areasIds);
	public Long[] findAreasMaesIdsByEmpresaId(Long empresaId);
	public boolean verificarColaboradoresAreaMae(AreaOrganizacional areaMae);
	public void transferirColabDaAreaMaeParaAreaFilha(AreaOrganizacional areaOrganizacional);
	public boolean possuiAreaFilhasByCodigoAC(String codigoAC, Long empresaId);
	public String[] filtraPermitidas(String[] areasIds, Long empresaId);
	public Collection<AreaOrganizacional> findByLntId(Long lntId, Long... empresaIdAreaOrganizacional);
	public Collection<AreaOrganizacional> findByLntIdComEmpresa(Long lntId, Long... empresaIdAreaOrganizacional);
	public Map<Long, AreaOrganizacional> findAllMapAreasIds(Long empresaId);
	public Map<Long, String> findMapResponsaveisIdsEmails(Long empresaId);
	public Map<Long, String> findMapCoResponsaveisIdsEmails(Long empresaId);
	public Collection<Long> getAncestraisIds (Long... areasIds);
	public Collection<Long> getDescendentesIds (Long... areasIds);
	public boolean isResposnsavelOrCoResponsavelPorPropriaArea(Long colaboradorId, int tipoResponsavel);
	public Integer defineTipoResponsavel(Long colaboradorId);
}
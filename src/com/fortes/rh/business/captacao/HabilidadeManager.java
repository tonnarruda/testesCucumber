package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.security.spring.aop.callback.HabilidadeAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.security.auditoria.Modulo;
import com.fortes.web.tags.CheckBox;

@Modulo("Habilidade")
public interface HabilidadeManager extends GenericManager<Habilidade>
{
	public Collection<CheckBox> populaCheckOrderNome(Long[] areasIds, long empresaId);
	public Collection<Habilidade> findByCargo(Long cargoId);
	public Collection<Habilidade> populaHabilidades(String[] habilidadesCheck);
	public Collection<Habilidade> findByAreasOrganizacionalIds(Long[] areaOrganizacionalIds, Long empresasId);
	public Collection<Habilidade> findAllSelect(Long empresaId);
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds, Map<Long, Long> conhecimentoIds);
	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception;
	public Habilidade findByIdProjection(Long habilidadeId);
	@Audita(operacao="Remoção", auditor=HabilidadeAuditorCallbackImpl.class)
	public void removeComDependencia(Long id);
}

package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.web.tags.CheckBox;

public interface HabilidadeManager extends GenericManager<Habilidade>
{
	public Collection<CheckBox> populaCheckOrderNome(long empresaId);
	public Collection<Habilidade> findByCargo(Long cargoId);
	public Collection<Habilidade> populaHabilidades(String[] habilidadesCheck);
	public Collection<Habilidade> findByAreasOrganizacionalIds(Long[] areaOrganizacionalIds, Long empresasId);
}

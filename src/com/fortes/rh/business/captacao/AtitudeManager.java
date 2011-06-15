package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.web.tags.CheckBox;

public interface AtitudeManager extends GenericManager<Atitude>
{
	public Collection<CheckBox> populaCheckOrderNome(long empresaId);
	public Collection<Atitude> findByCargo(Long cargoId);
	public Collection<Atitude> populaAtitudes(String[] atitudesCheck);
	public Collection<Atitude> findByAreasOrganizacionalIds(Long[] areaOrganizacionalIds, Long empresasId);
	public Collection<Atitude> findAllSelect(Long empresaId);
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds, Map<Long, Long> conhecimentoIds);
}

package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;

public interface NivelCompetenciaFaixaSalarialManager extends GenericManager<NivelCompetenciaFaixaSalarial>
{
	Collection<NivelCompetenciaFaixaSalarial> findByFaixa(Long faixaSalarialId);
	
	void saveCompetencias(Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaFaixaSalariais, Long faixaSalarialId, Long candidatoId);

	Collection<NivelCompetenciaFaixaSalarial> findByCandidato(Long candidato);

	Collection<NivelCompetenciaFaixaSalarial> getCompetenciasCandidato(Long candidatoId, Long empresaId);
}

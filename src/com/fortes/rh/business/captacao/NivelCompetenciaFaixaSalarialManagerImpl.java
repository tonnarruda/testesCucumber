package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;
import com.fortes.rh.util.CollectionUtil;

public class NivelCompetenciaFaixaSalarialManagerImpl extends GenericManagerImpl<NivelCompetenciaFaixaSalarial, NivelCompetenciaFaixaSalarialDao> implements NivelCompetenciaFaixaSalarialManager
{
	public Collection<NivelCompetenciaFaixaSalarial> findByFaixa(Long faixaSalarialId) 
	{
		return getDao().findByFaixa(faixaSalarialId);
	}

	public Collection<NivelCompetenciaFaixaSalarial> findByCandidato(Long candidatoId) {
		return getDao().findByCandidato(candidatoId);
	}
	
	public void saveCompetencias(Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaFaixaSalariais, Long faixaSalarialId, Long candidatoId) 
	{
		if(candidatoId == null)
			getDao().deleteConfiguracaoByFaixa(faixaSalarialId);
		else
			getDao().deleteConfiguracaoByCandidatoFaixa(candidatoId, faixaSalarialId);
		
		for (NivelCompetenciaFaixaSalarial nivelCompetenciaFaixaSalarial : niveisCompetenciaFaixaSalariais)
		{
			if (nivelCompetenciaFaixaSalarial.getCompetenciaId() != null)
			{
				nivelCompetenciaFaixaSalarial.setFaixaSalarialIdProjection(faixaSalarialId);
				if(candidatoId != null)
					nivelCompetenciaFaixaSalarial.setCandidatoIdProjection(candidatoId);
				
				getDao().save(nivelCompetenciaFaixaSalarial);
			}
		}
	}
}

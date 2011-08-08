package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;

public class NivelCompetenciaFaixaSalarialManagerImpl extends GenericManagerImpl<NivelCompetenciaFaixaSalarial, NivelCompetenciaFaixaSalarialDao> implements NivelCompetenciaFaixaSalarialManager
{
	public Collection<NivelCompetenciaFaixaSalarial> findByFaixa(Long faixaSalarialId) 
	{
		return getDao().findByFaixa(faixaSalarialId);
	}

	public void saveByFaixa(Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaFaixaSalariais, Long faixaSalarialId) 
	{
		getDao().deleteConfiguracaoByFaixa(faixaSalarialId);
		
		for (NivelCompetenciaFaixaSalarial nivelCompetenciaFaixaSalarial : niveisCompetenciaFaixaSalariais)
		{
			if (nivelCompetenciaFaixaSalarial.getCompetenciaId() != null)
			{
				nivelCompetenciaFaixaSalarial.setFaixaSalarialIdProjection(faixaSalarialId);
				getDao().save(nivelCompetenciaFaixaSalarial);
			}
		}
	}
}

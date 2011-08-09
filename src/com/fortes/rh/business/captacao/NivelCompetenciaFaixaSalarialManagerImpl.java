package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;

public class NivelCompetenciaFaixaSalarialManagerImpl extends GenericManagerImpl<NivelCompetenciaFaixaSalarial, NivelCompetenciaFaixaSalarialDao> implements NivelCompetenciaFaixaSalarialManager
{
	private NivelCompetenciaManager nivelCompetenciaManager;
	
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

	public Collection<NivelCompetenciaFaixaSalarial> getCompetenciasCandidato(Long candidatoId, Long empresaId) 
	{
		Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaFaixaSalariaisSalvos = getDao().findByCandidato(candidatoId);
		
		if(!niveisCompetenciaFaixaSalariaisSalvos.isEmpty())
		{
			Collection<NivelCompetenciaFaixaSalarial> niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(null, empresaId);
			for (NivelCompetenciaFaixaSalarial nivelCompetenciaCandidato : niveisCompetenciaFaixaSalariaisSalvos)
			{
				for (NivelCompetenciaFaixaSalarial nivelCompetenciaFaixaSalarial : niveisCompetenciaFaixaSalariais) 
				{
					if(nivelCompetenciaCandidato.getCompetenciaId().equals(nivelCompetenciaFaixaSalarial.getCompetenciaId()) && nivelCompetenciaCandidato.getTipoCompetencia().equals(nivelCompetenciaFaixaSalarial.getTipoCompetencia()))
					{
						nivelCompetenciaCandidato.setCompetenciaDescricao(nivelCompetenciaFaixaSalarial.getCompetenciaDescricao());
						break;
					}
				}				
			}
		}
		
		return niveisCompetenciaFaixaSalariaisSalvos;
	}

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager) {
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}
}

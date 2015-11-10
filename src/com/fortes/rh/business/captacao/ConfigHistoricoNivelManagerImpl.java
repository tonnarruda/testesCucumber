package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.NivelCompetencia;

public class ConfigHistoricoNivelManagerImpl extends GenericManagerImpl<ConfigHistoricoNivel, ConfigHistoricoNivelDao> implements ConfigHistoricoNivelManager
{
	private NivelCompetenciaManager nivelCompetenciaManager;
	
	public Collection<ConfigHistoricoNivel> findByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId) {
		return getDao().findByNivelCompetenciaHistoricoId(nivelCompetenciaHistoricoId);
	}

	public Collection<ConfigHistoricoNivel> findNiveisCompetenciaByEmpresa(Long empresaId) 
	{
		Collection<NivelCompetencia> niveisCompetencia = nivelCompetenciaManager.find(new String[]{"empresa.id"}, new Object[]{empresaId}, new String[]{"descricao"});
		Collection<ConfigHistoricoNivel> configHistoricoNivels = new ArrayList<ConfigHistoricoNivel>();
		
		for (NivelCompetencia nivelCompetencia : niveisCompetencia) {
			ConfigHistoricoNivel configHistoricoNivel = new ConfigHistoricoNivel();
			configHistoricoNivel.setNivelCompetencia(nivelCompetencia);
			configHistoricoNivels.add(configHistoricoNivel);
		}
		
		return configHistoricoNivels;
	}

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager) {
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}

	public void removeByNivelConfiguracaoHistorico(Long nivelConfiguracaoHIstoricoId) {
		getDao().removeByNivelConfiguracaoHistorico(nivelConfiguracaoHIstoricoId);
	}
}

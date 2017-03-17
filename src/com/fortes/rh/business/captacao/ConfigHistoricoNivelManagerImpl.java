package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.NivelCompetencia;

@Component
public class ConfigHistoricoNivelManagerImpl extends GenericManagerImpl<ConfigHistoricoNivel, ConfigHistoricoNivelDao> implements ConfigHistoricoNivelManager
{
	@Autowired private NivelCompetenciaManager nivelCompetenciaManager;
	@Autowired
	public ConfigHistoricoNivelManagerImpl(ConfigHistoricoNivelDao configHistoricoNivelDao) {
		setDao(configHistoricoNivelDao);
	}
	
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

	public void removeByNivelConfiguracaoHistorico(Long nivelConfiguracaoHIstoricoId) {
		getDao().removeByNivelConfiguracaoHistorico(nivelConfiguracaoHIstoricoId);
	}

	public void removeNotIds(Long[] configHistoricoNiveisIds,Long nivelConfiguracaoHIstoricoId) {
		getDao().removeNotIn(configHistoricoNiveisIds,nivelConfiguracaoHIstoricoId);
	}

	public Collection<ConfigHistoricoNivel> findByEmpresaAndDataNivelCompetenciaHistorico(Long empresaId, Date dataNivelCompetenciaHistorico) {
		return getDao().findByEmpresaAndDataDoNivelCompetenciaHistorico(empresaId, dataNivelCompetenciaHistorico);
	}
}

package com.fortes.rh.business.captacao;

import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.util.LongUtil;

public class NivelCompetenciaHistoricoManagerImpl extends GenericManagerImpl<NivelCompetenciaHistorico, NivelCompetenciaHistoricoDao> implements NivelCompetenciaHistoricoManager
{

	private ConfigHistoricoNivelManager configHistoricoNivelManager;
	
	public void removeNivelConfiguracaoHistorico(Long id) throws Exception {
		if(getDao().existeDependenciaComCompetenciasDaFaixaSalarial(id))
			throw new FortesException("Este histórico dos níveis de competância não pode ser excluído, pois existe dependência com a configuração de competências da faixa salarial.");

		configHistoricoNivelManager.removeByNivelConfiguracaoHistorico(id);
		getDao().remove(id);
	}

	public void setConfigHistoricoNivelManager( ConfigHistoricoNivelManager configHistoricoNivelManager) {
		this.configHistoricoNivelManager = configHistoricoNivelManager;
	}

	public Long findByData(Date date, Long empresaId) 
	{
		return getDao().findByData(date, empresaId);
	}
	
	public NivelCompetenciaHistorico save(NivelCompetenciaHistorico nivelCompetenciaHistorico){
		return getDao().save(nivelCompetenciaHistorico);
	}

	public void updateNivelConfiguracaoHistorico(NivelCompetenciaHistorico nivelCompetenciaHistorico) {
		configHistoricoNivelManager.removeNotIds(LongUtil.collectionToArrayLong(nivelCompetenciaHistorico.getConfigHistoricoNiveis()), nivelCompetenciaHistorico.getId());
		this.update(nivelCompetenciaHistorico);
	}
}

package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.util.LongUtil;

public class NivelCompetenciaHistoricoManagerImpl extends GenericManagerImpl<NivelCompetenciaHistorico, NivelCompetenciaHistoricoDao> implements NivelCompetenciaHistoricoManager
{

	private ConfigHistoricoNivelManager configHistoricoNivelManager;
	
	public void removeNivelConfiguracaoHistorico(Long id) throws FortesException {
		
		Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> configNivelCompetenciaFaixaSalariais = getDao().dependenciaComCompetenciasDaFaixaSalarial(id);
		
		if(configNivelCompetenciaFaixaSalariais.size() > 0){
			StringBuffer stb = new StringBuffer("Este histórico dos níveis de competência não pode ser excluído, pois existem dependências</br> com as configurações de competências das faixas salariais abaixo.</br>");
			for (ConfiguracaoNivelCompetenciaFaixaSalarial configCompFs : configNivelCompetenciaFaixaSalariais){
				stb.append(configCompFs.getFaixaSalarial().getNomeDeCargoEFaixa());
				stb.append("; ");
			}
			
			throw new FortesException(stb.toString().substring(0, stb.toString().length() -2));
		}

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

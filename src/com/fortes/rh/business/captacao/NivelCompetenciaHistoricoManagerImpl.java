package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.util.LongUtil;

@Component
public class NivelCompetenciaHistoricoManagerImpl extends GenericManagerImpl<NivelCompetenciaHistorico, NivelCompetenciaHistoricoDao> implements NivelCompetenciaHistoricoManager
{
	@Autowired private ConfigHistoricoNivelManager configHistoricoNivelManager;
	@Autowired
	public NivelCompetenciaHistoricoManagerImpl(NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao) {
		setDao(nivelCompetenciaHistoricoDao);
	}
	
	public void removeNivelConfiguracaoHistorico(Long id) throws FortesException {
		
		Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> configNivelCompetenciaFaixaSalariais = getDao().dependenciaComCompetenciasDaFaixaSalarial(id);
		
		if(configNivelCompetenciaFaixaSalariais.size() > 0){
			StringBuffer stb = new StringBuffer("Este histórico dos níveis de competência não pode ser excluído, pois existem dependências</br> com as configuraçõe de competências das faixas salariais abaixo.</br>");
			for (ConfiguracaoNivelCompetenciaFaixaSalarial configCompFs : configNivelCompetenciaFaixaSalariais){
				stb.append(configCompFs.getFaixaSalarial().getNomeDeCargoEFaixa());
				stb.append("; ");
			}
			
			throw new FortesException(stb.toString().substring(0, stb.toString().length() -2));
		}

		configHistoricoNivelManager.removeByNivelConfiguracaoHistorico(id);
		getDao().remove(id);
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

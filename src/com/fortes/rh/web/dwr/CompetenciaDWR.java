package com.fortes.rh.web.dwr;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.util.CollectionUtil;

public class CompetenciaDWR
{
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;

	public Map getByFaixa(Long faixaId)
	{
		return CollectionUtil.convertCollectionToMap(configuracaoNivelCompetenciaManager.findCompetenciaByFaixaSalarial(faixaId), "getId", "getCompetenciaDescricao", ConfiguracaoNivelCompetencia.class);
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}


}

package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;

public class ConfiguracaoNivelCompetenciaCriterioFactory {
	public static ConfiguracaoNivelCompetenciaCriterio getEntity()
	{
		ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio = new ConfiguracaoNivelCompetenciaCriterio();
		configuracaoNivelCompetenciaCriterio.setCriterioDescricao("Critério");
		return configuracaoNivelCompetenciaCriterio;
	}
	
	public static ConfiguracaoNivelCompetenciaCriterio getEntity(Long id)
	{
		ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio = getEntity();
		configuracaoNivelCompetenciaCriterio.setId(id);
		return configuracaoNivelCompetenciaCriterio;
	}
	
	public static ConfiguracaoNivelCompetenciaCriterio getEntity(Long criterioId, String criterioDescricao)
	{
		ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio = getEntity();
		configuracaoNivelCompetenciaCriterio.setCriterioDescricao(criterioDescricao);
		configuracaoNivelCompetenciaCriterio.setCriterioId(criterioId);
		return configuracaoNivelCompetenciaCriterio;
	}
}

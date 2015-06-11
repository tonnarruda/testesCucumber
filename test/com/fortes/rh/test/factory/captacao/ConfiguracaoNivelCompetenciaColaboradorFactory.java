package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaColaboradorFactory
{
	public static ConfiguracaoNivelCompetenciaColaborador getEntity()
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		configuracaoNivelCompetenciaColaborador.setId(null);
		return configuracaoNivelCompetenciaColaborador;
	}

	public static ConfiguracaoNivelCompetenciaColaborador getEntity(Long id)
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = getEntity();
		configuracaoNivelCompetenciaColaborador.setId(id);

		return configuracaoNivelCompetenciaColaborador;
	}
	
	public static ConfiguracaoNivelCompetenciaColaborador getEntity(Colaborador colaborador, FaixaSalarial faixaSalarial, String Data)
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = getEntity();
		configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaColaborador.setData(DateUtil.criarDataDiaMesAno(Data));

		return configuracaoNivelCompetenciaColaborador;
	}

	public static Collection<ConfiguracaoNivelCompetenciaColaborador> getCollection()
	{
		Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradors = new ArrayList<ConfiguracaoNivelCompetenciaColaborador>();
		configuracaoNivelCompetenciaColaboradors.add(getEntity());

		return configuracaoNivelCompetenciaColaboradors;
	}
	
	public static Collection<ConfiguracaoNivelCompetenciaColaborador> getCollection(Long id)
	{
		Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradors = new ArrayList<ConfiguracaoNivelCompetenciaColaborador>();
		configuracaoNivelCompetenciaColaboradors.add(getEntity(id));
		
		return configuracaoNivelCompetenciaColaboradors;
	}
}

package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaColaboradorFactory
{
	public static ConfiguracaoNivelCompetenciaColaborador getEntity()
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		configuracaoNivelCompetenciaColaborador.setId(null);
		return configuracaoNivelCompetenciaColaborador;
	}
	
	public static ConfiguracaoNivelCompetenciaColaborador getEntity(Colaborador colaborador, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial)
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = getEntity();
		configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
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
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = getEntity(colaborador, faixaSalarial);
		configuracaoNivelCompetenciaColaborador.setData(DateUtil.criarDataDiaMesAno(Data));

		return configuracaoNivelCompetenciaColaborador;
	}

	public static ConfiguracaoNivelCompetenciaColaborador getEntity(Colaborador colaborador, FaixaSalarial faixaSalarial)
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = getEntity();
		configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador.setFaixaSalarial(faixaSalarial);

		return configuracaoNivelCompetenciaColaborador;
	}
	
	public static ConfiguracaoNivelCompetenciaColaborador getEntity(Colaborador colaborador, FaixaSalarial faixaSalarial, Date data, Colaborador avaliador, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial)
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = getEntity(colaborador, faixaSalarial);
		configuracaoNivelCompetenciaColaborador.setData(data);
		configuracaoNivelCompetenciaColaborador.setAvaliador(avaliador);
		configuracaoNivelCompetenciaColaborador.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		return configuracaoNivelCompetenciaColaborador;
	}
	
	public static ConfiguracaoNivelCompetenciaColaborador getEntity(Long id, Colaborador colaborador, FaixaSalarial faixaSalarial, Date data, Colaborador avaliador, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial)
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = getEntity(colaborador, faixaSalarial, data, avaliador, configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoNivelCompetenciaColaborador.setId(id);
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
	public static ConfiguracaoNivelCompetenciaColaborador getEntity(Date data, Colaborador colaborador, Colaborador avaliador, ColaboradorQuestionario colaboradorQuestionario) {
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = getEntity();
		configuracaoNivelCompetenciaColaborador.setData(data);
		configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador.setAvaliador(avaliador);
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(colaboradorQuestionario);
		
		return configuracaoNivelCompetenciaColaborador;
	}
}

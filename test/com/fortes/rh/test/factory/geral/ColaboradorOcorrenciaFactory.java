package com.fortes.rh.test.factory.geral;

import java.util.Date;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Providencia;

public class ColaboradorOcorrenciaFactory
{
	public static ColaboradorOcorrencia getEntity()
	{
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();

		return colaboradorOcorrencia;
	}

	public static ColaboradorOcorrencia getEntity(Long id)
	{
		ColaboradorOcorrencia colaboradorOcorrencia = getEntity();
		colaboradorOcorrencia.setId(id);

		return colaboradorOcorrencia;
	}
	
	public static ColaboradorOcorrencia getEntity(Colaborador colaborador, Ocorrencia ocorrencia, Providencia providencia, Date dataInicio, Date dataFim)
	{
		ColaboradorOcorrencia colaboradorOcorrencia = getEntity(colaborador, ocorrencia, dataInicio, dataFim);
		colaboradorOcorrencia.setProvidencia(providencia);
		return colaboradorOcorrencia;
	}
	
	public static ColaboradorOcorrencia getEntity(Colaborador colaborador, Ocorrencia ocorrencia, Date dataInicio,Date dataFim) {
		ColaboradorOcorrencia colaboradorOcorrencia = getEntity();
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);
		colaboradorOcorrencia.setDataIni(dataInicio);
		colaboradorOcorrencia.setDataFim(dataFim);
		return colaboradorOcorrencia;
	}
}

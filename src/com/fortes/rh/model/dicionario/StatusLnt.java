package com.fortes.rh.model.dicionario;

import java.util.Date;
import java.util.LinkedHashMap;

import com.fortes.rh.util.DateUtil;

public class StatusLnt extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 1L;
	
	public static final Character TODOS = 'T';
	public static final Character NAO_INICIADA = 'I';
	public static final Character EM_PLANEJAMENTO = 'P';
	public static final Character EM_ANALISE = 'A';
	public static final Character FINALIZADA = 'F';

	public StatusLnt()
	{
		put(TODOS, "Todas");
		put(NAO_INICIADA, "Não iniciada");
		put(EM_PLANEJAMENTO, "Em planejamento");
		put(EM_ANALISE, "Em análise");
		put(FINALIZADA, "Finalizada");
	}
	
	public static Character getStatus(Date dataInicio, Date dataFim, Date dataFinalizada)
	{
		if(dataInicio == null || dataFim == null)
			return null;
		Date hoje = DateUtil.criarDataMesAno(new Date());
		
		if (dataInicio.compareTo(hoje) > 0)
			return NAO_INICIADA;
		else if (dataInicio.compareTo(hoje) <= 0 && dataFim.compareTo(hoje) >= 0)
			return EM_PLANEJAMENTO;
		else if (dataFim.compareTo(hoje) < 0 && dataFinalizada == null)
			return EM_ANALISE;
		else if (dataFinalizada != null)
			return FINALIZADA;
		
		return null;
	}
	
	public static Character getNaoIniciada()
	{
		return NAO_INICIADA;
	}
	
	public static Character getEmPlanejamento()
	{
		return EM_PLANEJAMENTO;
	}
	
	public static Character getEmAnalise()
	{
		return EM_ANALISE;
	}
	
	public static Character getFinalizada()
	{
		return FINALIZADA;
	}
}

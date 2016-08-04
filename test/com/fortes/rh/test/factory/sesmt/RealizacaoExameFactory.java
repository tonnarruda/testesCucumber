package com.fortes.rh.test.factory.sesmt;

import java.util.Date;

import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;

public class RealizacaoExameFactory
{
	public static RealizacaoExame getEntity()
	{
		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setData(new Date());
		realizacaoExame.setResultado(ResultadoExame.NAO_REALIZADO.toString());
		realizacaoExame.setObservacao("Observação");

		return realizacaoExame;
	}
	
	public static RealizacaoExame getEntity(Long id)
	{
		RealizacaoExame realizacaoExame = getEntity();
		realizacaoExame.setId(id);
		
		return realizacaoExame;
	}
	
	public static RealizacaoExame getEntity(Date data, String Resultado)
	{
		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setData(data);
		realizacaoExame.setResultado(Resultado);

		return realizacaoExame;
	}
}
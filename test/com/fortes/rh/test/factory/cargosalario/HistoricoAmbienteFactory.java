package com.fortes.rh.test.factory.cargosalario;

import java.util.Date;

import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;

public class HistoricoAmbienteFactory
{
	public static HistoricoAmbiente getEntity()
	{
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setId(null);

		return historicoAmbiente;
	}

	public static HistoricoAmbiente getEntity(Long id)
	{
		HistoricoAmbiente historicoAmbiente = getEntity();
		historicoAmbiente.setId(id);

		return historicoAmbiente;
	}
	
	public static HistoricoAmbiente getEntity(Ambiente ambiente, Date data)
	{
		HistoricoAmbiente historicoAmbiente = getEntity();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(data);
		
		return historicoAmbiente;
	}
	
	public static HistoricoAmbiente getEntity(String descricao, Ambiente ambiente, Date data, String tempoExposicao)
	{
		HistoricoAmbiente historicoAmbiente = getEntity();
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setData(data);
		historicoAmbiente.setDescricao(descricao);
		historicoAmbiente.setTempoExposicao(tempoExposicao);
		
		return historicoAmbiente;
	}

}

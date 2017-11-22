package com.fortes.rh.test.factory.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;

public class HistoricoFuncaoFactory
{
	public static HistoricoFuncao getEntity()
	{
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setId(null);
		historicoFuncao.setFuncaoNome("Função");

		return historicoFuncao;
	}

	public static HistoricoFuncao getEntity(Long id)
	{
		HistoricoFuncao historicoFuncao = getEntity();
		historicoFuncao.setId(id);

		return historicoFuncao;
	}
	
	public static HistoricoFuncao getEntity(Funcao funcao, Date data, Collection<Epi> epis)
	{
		HistoricoFuncao historicoFuncao = getEntity();
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setData(data);
		historicoFuncao.setEpis(epis);
		
		return historicoFuncao;
	}
	
	public static HistoricoFuncao getEntity(String descricao, Collection<Epi> epis)
	{
		HistoricoFuncao historicoFuncao = getEntity();
		historicoFuncao.setDescricao(descricao);
		historicoFuncao.setEpis(epis);
		
		return historicoFuncao;
	}
}

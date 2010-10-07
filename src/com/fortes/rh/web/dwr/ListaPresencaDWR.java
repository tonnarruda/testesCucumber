package com.fortes.rh.web.dwr;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
public class ListaPresencaDWR
{
	private ColaboradorPresencaManager colaboradorPresencaManager;
	
	public boolean updateFrequencia(Long diaTurmaId, Long colaboradorTurmaId, boolean presenca) throws Exception
	{
		try
		{
			colaboradorPresencaManager.updateFrequencia(diaTurmaId, colaboradorTurmaId, presenca);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Erro ao alterar Frequência.");
		}
		
		return presenca;
	}
	
	public void marcarTodos(Long diaTurmaId, Long turmaId) throws Exception
	{
		colaboradorPresencaManager.marcarTodos(diaTurmaId, turmaId);
	}
	
	public void desmarcarTodos(Long diaTurmaId) throws Exception
	{
		colaboradorPresencaManager.removeByDiaTurma(diaTurmaId);
	}
	
	public String calculaFrequencia(Long colaboradorTurmaId, Integer qtdDias) throws Exception
	{
		return colaboradorPresencaManager.calculaFrequencia(colaboradorTurmaId, qtdDias);
	}
	
	public void setColaboradorPresencaManager(ColaboradorPresencaManager colaboradorPresencaManager)
	{
		this.colaboradorPresencaManager = colaboradorPresencaManager;
	}
}
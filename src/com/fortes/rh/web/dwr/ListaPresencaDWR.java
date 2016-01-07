package com.fortes.rh.web.dwr;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.model.dicionario.FiltroControleVencimentoCertificacao;
public class ListaPresencaDWR
{
	private ColaboradorPresencaManager colaboradorPresencaManager;
	
	public boolean updateFrequencia(Long diaTurmaId, Long colaboradorTurmaId, boolean presenca, int controlarVencimentoCertificacaoPor) throws Exception
	{
		try
		{
			colaboradorPresencaManager.updateFrequencia(diaTurmaId, colaboradorTurmaId, presenca, verificaCertificacao(controlarVencimentoCertificacaoPor));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Erro ao alterar Frequência.");
		}
		
		return presenca;
	}
	
	public void marcarTodos(Long diaTurmaId, Long turmaId, int controlarVencimentoCertificacaoPor) throws Exception
	{
		colaboradorPresencaManager.marcarTodos(diaTurmaId, turmaId, verificaCertificacao(controlarVencimentoCertificacaoPor));
	}
	
	public void desmarcarTodos(Long diaTurmaId, Long turmaId, int controlarVencimentoCertificacaoPor) throws Exception
	{
		colaboradorPresencaManager.removeByDiaTurma(diaTurmaId, turmaId, verificaCertificacao(controlarVencimentoCertificacaoPor));
	}
	
	public String calculaFrequencia(Long colaboradorTurmaId, Integer qtdDias) throws Exception
	{
		return colaboradorPresencaManager.calculaFrequencia(colaboradorTurmaId, qtdDias);
	}

	private boolean verificaCertificacao(int controlarVencimentoCertificacaoPor) 
	{
		return controlarVencimentoCertificacaoPor == FiltroControleVencimentoCertificacao.CERTIFICACAO.getOpcao();
	}
	
	public void setColaboradorPresencaManager(ColaboradorPresencaManager colaboradorPresencaManager)
	{
		this.colaboradorPresencaManager = colaboradorPresencaManager;
	}
}
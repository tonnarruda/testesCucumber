package com.fortes.rh.web.dwr;

import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.FiltroControleVencimentoCertificacao;
public class ListaPresencaDWR
{
	private ColaboradorPresencaManager colaboradorPresencaManager;
	
	public Collection<ColaboradorTurma> updateFrequencia(Long diaTurmaId, Long colaboradorTurmaId, boolean presenca, int controlarVencimentoCertificacaoPor, boolean certificadoEmTurmaPorterior) throws Exception
	{
		try
		{
			if(!certificadoEmTurmaPorterior)
				return colaboradorPresencaManager.updateFrequencia(diaTurmaId, colaboradorTurmaId, presenca, verificaCertificacao(controlarVencimentoCertificacaoPor));
			else
				return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Erro ao alterar Frequência.");
		}
	}
	
	public Collection<ColaboradorTurma> marcarTodos(Long diaTurmaId, Long turmaId, int controlarVencimentoCertificacaoPor) throws Exception
	{
		return colaboradorPresencaManager.marcarTodos(diaTurmaId, turmaId, verificaCertificacao(controlarVencimentoCertificacaoPor));
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
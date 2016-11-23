package com.fortes.rh.web.dwr;

import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

public class ColaboradorTurmaDWR
{
	private ColaboradorTurmaManager colaboradorTurmaManager;

	public String checaColaboradorInscritoEmOutraTurma(Long turmaId, Long cursoId, Long[] colaboradorIds) throws Exception
	{
		Collection<ColaboradorTurma> colaboradoresTurmas = colaboradorTurmaManager.findByTurmaCurso(cursoId);
		return colaboradorTurmaManager.checaColaboradorInscritoEmOutraTurma(colaboradorIds, colaboradoresTurmas, turmaId);
	}
	
	public String verificaColaboradorCertificadoNaCertificacaoPreRequisito(Long cursoId, Long[] colaboradorIds) throws Exception
	{
		return colaboradorTurmaManager.verificaColaboradorCertificado(colaboradorIds, cursoId);
	}
	
	public boolean existeColaboradoresNaTurma(Long turmaId) throws Exception
	{
		return colaboradorTurmaManager.findColabodoresByTurmaId(turmaId).size() > 0;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager) {
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}
		
}
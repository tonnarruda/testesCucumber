package com.fortes.rh.web.dwr;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

@Component
public class ColaboradorTurmaDWR
{
	@Autowired
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
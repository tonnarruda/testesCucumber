package com.fortes.rh.web.dwr;

import java.util.Collection;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

@Component
@RemoteProxy(name="ColaboradorTurmaDWR")
public class ColaboradorTurmaDWR
{
	@Autowired private ColaboradorTurmaManager colaboradorTurmaManager;

	@RemoteMethod
	public String checaColaboradorInscritoEmOutraTurma(Long turmaId, Long cursoId, Long[] colaboradorIds) throws Exception
	{
		Collection<ColaboradorTurma> colaboradoresTurmas = colaboradorTurmaManager.findByTurmaCurso(cursoId);
		return colaboradorTurmaManager.checaColaboradorInscritoEmOutraTurma(colaboradorIds, colaboradoresTurmas, turmaId);
	}
	
	@RemoteMethod
	public String verificaColaboradorCertificadoNaCertificacaoPreRequisito(Long cursoId, Long[] colaboradorIds) throws Exception
	{
		return colaboradorTurmaManager.verificaColaboradorCertificado(colaboradorIds, cursoId);
	}
	
	@RemoteMethod
	public boolean existeColaboradoresNaTurma(Long turmaId) throws Exception
	{
		return colaboradorTurmaManager.findColabodoresByTurmaId(turmaId).size() > 0;
	}
}
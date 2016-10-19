package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;

public class ExameSolicitacaoExameFactory
{
	public static ExameSolicitacaoExame getEntity(){
		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		exameSolicitacaoExame.setId(null);
		return exameSolicitacaoExame;
	}

	public static ExameSolicitacaoExame getEntity(Long id)
	{
		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		exameSolicitacaoExame.setId(id);
		return exameSolicitacaoExame;
	}

	public static ExameSolicitacaoExame getEntity(Exame exame, SolicitacaoExame solicitacaoExame, RealizacaoExame realizacaoExame, Integer periodicidade) {
		
		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		exameSolicitacaoExame.setExame(exame);
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExame.setRealizacaoExame(realizacaoExame);
		
		if(periodicidade != null)
			exameSolicitacaoExame.setPeriodicidade(periodicidade);
		
		return exameSolicitacaoExame;
	}
	
	public static ExameSolicitacaoExame getEntity(Exame exame, SolicitacaoExame solicitacaoExame, ClinicaAutorizada clinicaAutorizada, RealizacaoExame realizacaoExame, Integer periodicidade)
	{
		ExameSolicitacaoExame exameSolicitacaoExame = getEntity(exame, solicitacaoExame, realizacaoExame, periodicidade);
		exameSolicitacaoExame.setClinicaAutorizada(clinicaAutorizada);
		return exameSolicitacaoExame;
	}
}
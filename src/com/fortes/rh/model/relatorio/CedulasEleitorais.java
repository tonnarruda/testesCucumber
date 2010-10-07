package com.fortes.rh.model.relatorio;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.CandidatoEleicao;

public class CedulasEleitorais
{
	private Collection<CandidatoEleicao> candidatoEleicaos = new ArrayList<CandidatoEleicao>();

	public Collection<CandidatoEleicao> getCandidatoEleicaos()
	{
		return candidatoEleicaos;
	}

	public void setCandidatoEleicaos(Collection<CandidatoEleicao> candidatoEleicaos)
	{
		this.candidatoEleicaos = candidatoEleicaos;
	}
}

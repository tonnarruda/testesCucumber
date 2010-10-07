package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.geral.ColaboradorIdioma;

public interface CandidatoIdiomaManager extends GenericManager<CandidatoIdioma>
{
	public void removeCandidato(Candidato candidato) throws Exception;
	public Collection<CandidatoIdioma> findInCandidatos(Long[] candidatoIds);
	public void montaIdiomasBDS(Collection<CandidatoIdioma> candidatoIdiomas, Candidato candidato);

	public Collection<CandidatoIdioma> montaCandidatoIdiomaByColaboradorIdioma(Collection<ColaboradorIdioma> colaboradorIdiomas, Candidato candidato);
	public Collection<CandidatoIdioma> findByCandidato(Long candidatoId);
	public Collection<CandidatoIdioma> montaListCandidatoIdioma(Long colaboradorId);
}
package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;

public interface CandidatoIdiomaDao extends GenericDao<CandidatoIdioma>
{
	void removeCandidato(Candidato candidato);

	public Collection<CandidatoIdioma> findInCandidatos(Long[] candidatoIds);

	Collection<CandidatoIdioma> findByCandidato(Long candidatoId);
}
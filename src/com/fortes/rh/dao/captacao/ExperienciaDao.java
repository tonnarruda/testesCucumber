package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.geral.Colaborador;

public interface ExperienciaDao extends GenericDao<Experiencia>
{
	public void removeCandidato(Candidato candidato);
	public void removeColaborador(Colaborador colaborador);
	public Collection<Experiencia> findInCandidatos(Long[] candidatoIds);
	public Collection<Experiencia> findByColaborador(Colaborador c);
	public Collection<Experiencia> findByCandidato(Long candidatoId);

}
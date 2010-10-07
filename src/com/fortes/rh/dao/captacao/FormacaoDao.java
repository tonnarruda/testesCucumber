package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.geral.Colaborador;

public interface FormacaoDao extends GenericDao<Formacao>
{
	public void removeCandidato(Candidato candidato);
	public void removeColaborador(Colaborador colaborador);
	public Collection<Formacao> findInCandidatos(Long[] candidatoIds);
	public Collection<Formacao> findByColaborador(Long colaboradorId);
	public Collection<Formacao> findByCandidato(Long candidatoId);
}
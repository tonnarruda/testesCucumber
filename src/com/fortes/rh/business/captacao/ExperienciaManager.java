package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.geral.Colaborador;

public interface ExperienciaManager extends GenericManager<Experiencia>
{
	public void removeCandidato(Candidato candidato);
	public void removeColaborador(Colaborador colaborador);
	public Collection<Experiencia> findInCandidatos(Long[] candidatoIds);
	public void montaExperienciasBDS(Collection<Experiencia> experiencias, Candidato candidato);
	public Collection<Experiencia> findByColaborador(Long id);
	public Collection<Experiencia> findByCandidato(Long candidatoId);
	public void desvinculaCargo(Long cargoId, String cargoNomeMercado);

}
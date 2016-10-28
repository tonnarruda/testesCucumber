package com.fortes.rh.business.captacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ExperienciaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.geral.Colaborador;

@Component
public class ExperienciaManagerImpl extends GenericManagerImpl<Experiencia, ExperienciaDao> implements ExperienciaManager
{
	@Autowired
	ExperienciaManagerImpl(ExperienciaDao experienciaDao) {
		setDao(experienciaDao);
	}
	
	public void removeCandidato(Candidato candidato)
	{
		getDao().removeCandidato(candidato);
	}

	public void removeColaborador(Colaborador colaborador)
	{
		getDao().removeColaborador(colaborador);
	}

	public Collection<Experiencia> findInCandidatos(Long[] candidatoIds)
	{
		return getDao().findInCandidatos(candidatoIds);
	}

	public void montaExperienciasBDS(Collection<Experiencia> experiencias, Candidato candidato)
	{
		for (Experiencia experiencia : experiencias)
		{
			experiencia.setId(null);
			experiencia.setCargo(null);
			experiencia.setColaborador(null);
			experiencia.setCandidato(candidato);

			save(experiencia);
		}
	}

	public Collection<Experiencia> findByColaborador(Long id)
	{
		Colaborador c = new Colaborador();
		c.setId(id);

		return getDao().findByColaborador(c);
	}

	public Collection<Experiencia> findByCandidato(Long candidatoId)
	{
		return getDao().findByCandidato(candidatoId);
	}

	public void desvinculaCargo(Long cargoId, String cargoNomeMercado)
	{
		getDao().desvinculaCargo(cargoId, cargoNomeMercado);
	}

}
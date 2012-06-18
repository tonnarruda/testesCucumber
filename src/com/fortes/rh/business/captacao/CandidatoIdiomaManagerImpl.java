package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.dao.captacao.CandidatoIdiomaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.geral.ColaboradorIdioma;

public class CandidatoIdiomaManagerImpl extends GenericManagerImpl<CandidatoIdioma, CandidatoIdiomaDao> implements CandidatoIdiomaManager
{
	private ColaboradorIdiomaManager colaboradorIdiomaManager;
	
	public void removeCandidato(Candidato candidato) throws Exception
	{
		getDao().removeCandidato(candidato);
	}

	public Collection<CandidatoIdioma> findInCandidatos(Long[] candidatoIds)
	{
		return getDao().findInCandidatos(candidatoIds);
	}

	public void montaIdiomasBDS(Collection<CandidatoIdioma> candidatoIdiomas, Candidato candidato)
	{
		for (CandidatoIdioma idioma : candidatoIdiomas)
		{
			idioma.setId(null);
			idioma.setCandidato(candidato);

			save(idioma);
		}
	}

	public Collection<CandidatoIdioma> montaCandidatoIdiomaByColaboradorIdioma(Collection<ColaboradorIdioma> colaboradorIdiomas, Candidato candidato)
	{
		Collection<CandidatoIdioma> candidatoIdiomas = new ArrayList<CandidatoIdioma>();

		for (ColaboradorIdioma idioma : colaboradorIdiomas)
		{
			CandidatoIdioma ci = new CandidatoIdioma();
			ci.setCandidato(candidato);
			ci.setIdioma(idioma.getIdioma());
			ci.setNivel(idioma.getNivel());

			save(ci);

			candidatoIdiomas.add(ci);
		}
		return candidatoIdiomas;
	}

	public Collection<CandidatoIdioma> findByCandidato(Long candidatoId)
	{
		return getDao().findByCandidato(candidatoId);
	}

	public Collection<CandidatoIdioma> montaListCandidatoIdioma(Long colaboradorId)
	{
		Collection<CandidatoIdioma> candidatoIdiomas = new ArrayList<CandidatoIdioma>();
		
		for (ColaboradorIdioma colab : colaboradorIdiomaManager.findByColaborador(colaboradorId))
		{
			CandidatoIdioma cand = new CandidatoIdioma();
			cand.setId(colab.getId());
			cand.setIdioma(colab.getIdioma());
			cand.setNivel(colab.getNivel());

			candidatoIdiomas.add(cand);
		}
		
		return candidatoIdiomas;
	}

	public void setColaboradorIdiomaManager(ColaboradorIdiomaManager colaboradorIdiomaManager)
	{
		this.colaboradorIdiomaManager = colaboradorIdiomaManager;
	}
}
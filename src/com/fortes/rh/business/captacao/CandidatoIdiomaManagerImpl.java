package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.dao.captacao.CandidatoIdiomaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.util.CollectionUtil;

@Component
public class CandidatoIdiomaManagerImpl extends GenericManagerImpl<CandidatoIdioma, CandidatoIdiomaDao> implements CandidatoIdiomaManager
{
	private ColaboradorIdiomaManager colaboradorIdiomaManager;
	
	@Autowired
	CandidatoIdiomaManagerImpl(CandidatoIdiomaDao candidatoIdiomaDao) {
		setDao(candidatoIdiomaDao);
	}
	
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
		if(candidato.getCandidatoIdiomas() != null)
			getDao().remove(new CollectionUtil<CandidatoIdioma>().convertCollectionToArrayIds(candidato.getCandidatoIdiomas()));

		Collection<CandidatoIdioma> candidatoIdiomasTemp = new ArrayList<CandidatoIdioma>();
		
		if (colaboradorIdiomas != null && !colaboradorIdiomas.isEmpty()) {
			for (ColaboradorIdioma idioma : colaboradorIdiomas){
				CandidatoIdioma ci = new CandidatoIdioma();
				ci.setCandidato(candidato);
				ci.setIdioma(idioma.getIdioma());
				ci.setNivel(idioma.getNivel());
				save(ci);

				candidatoIdiomasTemp.add(ci);
			}
		}
		return candidatoIdiomasTemp;
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
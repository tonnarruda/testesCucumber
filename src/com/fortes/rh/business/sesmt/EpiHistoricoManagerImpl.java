package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EpiHistoricoDao;
import com.fortes.rh.model.geral.relatorio.PppFatorRisco;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;

@Component
public class EpiHistoricoManagerImpl extends GenericManagerImpl<EpiHistorico, EpiHistoricoDao> implements EpiHistoricoManager
{
	@Autowired
	EpiHistoricoManagerImpl(EpiHistoricoDao epiHistoricoDao) {
		setDao(epiHistoricoDao);
	}
	
	public Collection<EpiHistorico> findByData(Date data,  Long empresaId)
	{
		Collection<EpiHistorico> epiHistoricosRetorno = new ArrayList<EpiHistorico>();
		Collection<EpiHistorico> epiHistoricos = getDao().findByData(data, empresaId);

		Long idEpi = 0L;
		for (EpiHistorico epiHistorico : epiHistoricos)
		{
			if(!idEpi.equals(epiHistorico.getEpi().getId()))
			{
				epiHistoricosRetorno.add(epiHistorico);
				idEpi = epiHistorico.getEpi().getId();
			}
		}

		return epiHistoricosRetorno;
	}

	public Collection<EpiHistorico> getHistoricosEpi(PppFatorRisco fatorRisco)
	{
		Collection<Epi> epis = fatorRisco.getRisco().getEpis();
		Collection<EpiHistorico> retorno = new HashSet<EpiHistorico>();

		for (Epi epi : epis)
		{
			retorno.addAll(getDao().getHistoricosEpi(epi.getId(),fatorRisco.getDataInicio(),fatorRisco.getDataFim()));
		}

		return retorno;
	}
	
	public Collection<EpiHistorico> findByEpi(Long epiId)
	{
		return getDao().findByEpi(epiId);
	}
	
	public void clonar(EpiHistorico epiHistorico, Long epiId)
	{
		epiHistorico.setId(null);
		epiHistorico.setProjectionEpiId(epiId);
		
		getDao().save(epiHistorico);
	}

	public EpiHistorico findUltimoByEpiId(Long epiId) 
	{
		return getDao().findUltimoByEpiId(epiId);
	}

	public void removeByEpi(Long epiId) 
	{
		getDao().removeByEpi(epiId);
	}
}
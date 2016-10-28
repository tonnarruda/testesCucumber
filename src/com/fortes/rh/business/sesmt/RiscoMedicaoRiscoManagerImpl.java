package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoMedicaoRiscoDao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.util.CollectionUtil;

@Component
public class RiscoMedicaoRiscoManagerImpl extends GenericManagerImpl<RiscoMedicaoRisco, RiscoMedicaoRiscoDao> implements RiscoMedicaoRiscoManager
{
	@Autowired
	RiscoMedicaoRiscoManagerImpl(RiscoMedicaoRiscoDao riscoMedicaoRiscoDao) {
		setDao(riscoMedicaoRiscoDao);
	}
	
	public boolean removeByMedicaoRisco(Long medicaoRiscoId) 
	{
		if (medicaoRiscoId != null)
		{
			return getDao().removeByMedicaoRisco(medicaoRiscoId);
		}
		return true;
	}

	public Collection<RiscoMedicaoRisco> findMedicoesDeRiscosDoAmbiente(Long ambienteId, Date data) 
	{
		return getDao().findMedicoesDeRiscosDoAmbiente(ambienteId, data);
	}
	
	public Collection<RiscoMedicaoRisco> findMedicoesDeRiscosDaFuncao(Long funcaoId, Date data) 
	{
		return getDao().findMedicoesDeRiscosDaFuncao(funcaoId, data);
	}

	@SuppressWarnings("unchecked")
	public List<RiscoMedicaoRisco> getByRiscoPeriodo(Long riscoId, Long ambienteId, Date dataAmbienteIni, Date dataAmbienteFim) 
	{
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = getDao().findByRiscoAteData(riscoId, ambienteId, dataAmbienteFim);
		
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscosResultado = new ArrayList<RiscoMedicaoRisco>(); 
		
		for (RiscoMedicaoRisco riscoMedicaoRisco : riscoMedicaoRiscos) 
		{
			riscoMedicaoRiscosResultado.add(riscoMedicaoRisco);
			
			if (riscoMedicaoRisco.getMedicaoRisco().getData().compareTo(dataAmbienteIni) <= 0)
				break;
		}
		
		CollectionUtil<RiscoMedicaoRisco> collectionUtil = new CollectionUtil<RiscoMedicaoRisco>();
		
		riscoMedicaoRiscosResultado = collectionUtil.sortCollectionDate(riscoMedicaoRiscosResultado, "medicaoRisco.data", "asc");
		
		return collectionUtil.convertCollectionToList(riscoMedicaoRiscosResultado);
	}

	public MedicaoRisco findUltimaAteData(Long ambienteId, Date historicoAmbienteData)
	{
		return getDao().findUltimaAteData(ambienteId, historicoAmbienteData);
	}
}

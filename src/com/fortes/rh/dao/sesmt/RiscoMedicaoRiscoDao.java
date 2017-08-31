package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;

public interface RiscoMedicaoRiscoDao extends GenericDao<RiscoMedicaoRisco> 
{
	boolean removeByMedicaoRisco(Long medicaoRiscoId);
	Collection<RiscoMedicaoRisco> findMedicoesDeRiscosDoAmbiente(Long ambienteId, Date data);
	Collection<RiscoMedicaoRisco> findMedicoesDeRiscosDaFuncao(Long funcaoId, Date data);
	Collection<RiscoMedicaoRisco> findByRiscoAteData(Long riscoId, Long ambienteOuFuncaoId, Date dataFim, boolean controlaRiscoPorAmbiente);
	MedicaoRisco findUltimaAteData(Long ambienteOuFuncaoId, Date historicoAmbienteOuFuncaoData, boolean controlaRiscoPorAmbiente);
}

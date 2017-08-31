package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;

public interface RiscoMedicaoRiscoManager extends GenericManager<RiscoMedicaoRisco>
{
	boolean removeByMedicaoRisco(Long id);
	Collection<RiscoMedicaoRisco> findMedicoesDeRiscosDoAmbiente(Long ambienteId, Date data);
	Collection<RiscoMedicaoRisco> findMedicoesDeRiscosDaFuncao(Long funcaoId, Date data);
	List<RiscoMedicaoRisco> getByRiscoPeriodo(Long riscoId, Long ambienteId, Date dataAmbienteOuFuncaoIni, Date dataAmbienteOuFuncaoFim, boolean controlaRiscoPorAmbiente);
	MedicaoRisco findUltimaAteData(Long ambienteOuFuncaoId, Date historicoAmbienteOuFuncaoData, boolean controlaRiscoPorAmbiente);
}

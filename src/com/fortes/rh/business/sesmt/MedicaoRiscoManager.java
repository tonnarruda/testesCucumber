package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;

public interface MedicaoRiscoManager extends GenericManager<MedicaoRisco>
{
	Collection<MedicaoRisco> findAllSelectByAmbiente(Long empresaId, Long ambienteId);
	Collection<MedicaoRisco> findAllSelectByFuncao(Long empresaId, Long funcaoId);
	void save(MedicaoRisco medicaoRisco, String[] riscoIds, String[] ltcatValues, String[] ppraValues, String[] tecnicaValues, String[] intensidadeValues) throws Exception;
	String getTecnicasUtilizadas(Long empresaId);
	Collection<RiscoMedicaoRisco> preparaRiscosDaMedicao(MedicaoRisco medicaoRisco, Collection<Risco> riscos);
	void removeCascade(Long id);
	public MedicaoRisco getMedicaoRiscoMedicaoPorFuncao(Long medicaoRiscoId);
	Collection<RiscoMedicaoRisco> findRiscoMedicaoRiscos(Long medicaoRiscoId);
}

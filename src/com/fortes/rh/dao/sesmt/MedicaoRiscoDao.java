package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.MedicaoRisco;

public interface MedicaoRiscoDao extends GenericDao<MedicaoRisco> 
{
	MedicaoRisco findByIdProjection(Long id); 
	Collection<MedicaoRisco> findAllSelect(Long empresaId, Long ambienteId);
	Collection<String> findTecnicasUtilizadasDistinct(Long empresaId);
}

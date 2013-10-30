package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;

public interface ClinicaAutorizadaManager extends GenericManager<ClinicaAutorizada>
{
	Collection<ClinicaAutorizada> findByDataEmpresa(Long empresaId, Date data, Boolean vigentes);

	Collection<ClinicaAutorizada> selecionaPorTipo(Collection<ClinicaAutorizada> clinicasMedicosAutorizadas, String medico);

	Collection<ClinicaAutorizada> findByExame(Long empresaId, Long exameId, Date date);
	
	String findTipoOutrosDistinctByEmpresa(Long empresaId);
}
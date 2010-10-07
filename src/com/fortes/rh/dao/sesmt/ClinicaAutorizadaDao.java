package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;

public interface ClinicaAutorizadaDao extends GenericDao<ClinicaAutorizada>
{
	Collection<ClinicaAutorizada> findClinicasAtivasByDataEmpresa(Long empresaId, Date data);

	Collection<ClinicaAutorizada> findByExame(Long empresaId, Long exameId, Date date);
}
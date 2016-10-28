package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ClinicaAutorizadaDao;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.util.StringUtil;

@Component
public class ClinicaAutorizadaManagerImpl extends GenericManagerImpl<ClinicaAutorizada, ClinicaAutorizadaDao> implements ClinicaAutorizadaManager
{
	@Autowired
	ClinicaAutorizadaManagerImpl(ClinicaAutorizadaDao fooDao) {
		setDao(fooDao);
	}
	
	public Collection<ClinicaAutorizada> findByDataEmpresa(Long empresaId, Date data, Boolean vigentes)
	{
		return getDao().findByDataEmpresa(empresaId, data, vigentes);
	}

	public Collection<ClinicaAutorizada> selecionaPorTipo(Collection<ClinicaAutorizada> clinicasMedicosAutorizadas, String tipo)
	{
		Collection<ClinicaAutorizada> clinicasAutorizadas = new ArrayList<ClinicaAutorizada>();
		for (ClinicaAutorizada cli : clinicasMedicosAutorizadas)
		{
			if(cli.getTipo().equals(tipo))
				clinicasAutorizadas.add(cli);
		}

		return clinicasAutorizadas;
	}

	public Collection<ClinicaAutorizada> findByExame(Long empresaId, Long exameId, Date date)
	{
		return getDao().findByExame(empresaId, exameId, date);
	}
	
	public String findTipoOutrosDistinctByEmpresa(Long empresaId) 
	{
		Collection<String> fabricantes = getDao().findTipoOutrosDistinctByEmpresa(empresaId);
		if(fabricantes == null || fabricantes.isEmpty())
			return "";
		else
			return StringUtil.converteCollectionToStringComAspas(fabricantes);
	}
}
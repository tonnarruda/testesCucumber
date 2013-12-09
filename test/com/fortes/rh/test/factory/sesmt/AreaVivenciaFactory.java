package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.AreaVivencia;

public class AreaVivenciaFactory
{
	public static AreaVivencia getEntity()
	{
		AreaVivencia areaVivencia = new AreaVivencia();
		areaVivencia.setNome("Área Vivência");
		areaVivencia.setId(null);
		return areaVivencia;
	}

	public static AreaVivencia getEntity(Long id)
	{
		AreaVivencia areaVivencia = getEntity();
		areaVivencia.setId(id);

		return areaVivencia;
	}

	public static Collection<AreaVivencia> getCollection()
	{
		Collection<AreaVivencia> areaVivencias = new ArrayList<AreaVivencia>();
		areaVivencias.add(getEntity());

		return areaVivencias;
	}
	
	public static Collection<AreaVivencia> getCollection(Long id)
	{
		Collection<AreaVivencia> areaVivencias = new ArrayList<AreaVivencia>();
		areaVivencias.add(getEntity(id));
		
		return areaVivencias;
	}
}

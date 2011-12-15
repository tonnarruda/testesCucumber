package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.TipoDocumento;

public class TipoDocumentoFactory
{
	public static TipoDocumento getEntity()
	{
		TipoDocumento tipoDocumento = new TipoDocumento();
		tipoDocumento.setId(null);
		return tipoDocumento;
	}

	public static TipoDocumento getEntity(Long id)
	{
		TipoDocumento tipoDocumento = getEntity();
		tipoDocumento.setId(id);

		return tipoDocumento;
	}

	public static Collection<TipoDocumento> getCollection()
	{
		Collection<TipoDocumento> tipoDocumentos = new ArrayList<TipoDocumento>();
		tipoDocumentos.add(getEntity());

		return tipoDocumentos;
	}
	
	public static Collection<TipoDocumento> getCollection(Long id)
	{
		Collection<TipoDocumento> tipoDocumentos = new ArrayList<TipoDocumento>();
		tipoDocumentos.add(getEntity(id));
		
		return tipoDocumentos;
	}
}

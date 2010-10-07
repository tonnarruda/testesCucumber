package com.fortes.rh.test.util.mockObjects;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.Candidato;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;


/**
 * Classe criada para auxiliar a Mockagem estática de métodos de Xtream
 */
public class MockXStream extends XStream
{

	public MockXStream(HierarchicalStreamDriver arg0)
	{
		super(arg0);
	}

	@Override
	public Object fromXML(InputStream arg0)
	{
		Collection<Candidato> retorno = new ArrayList<Candidato>();
		Candidato candidato = new Candidato();
		candidato.setId(1L);
		retorno.add(candidato);

		return retorno;
	}



}

package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.web.dwr.CidadeDWR;

public class CidadeDWRTest extends MockObjectTestCase
{
	private CidadeDWR cidadeDWR;
	private Mock cidadeManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		cidadeDWR = new CidadeDWR();

		cidadeManager = new Mock(CidadeManager.class);

		cidadeDWR.setCidadeManager((CidadeManager) cidadeManager.proxy());
	}

	public void testGetCidades()
	{
		Estado estado = EstadoFactory.getEntity();
		estado.setId(1L);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setId(1L);
		cidade.setUf(estado);

		Collection<Cidade> cidades = new ArrayList<Cidade>();
		cidades.add(cidade);

		cidadeManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(cidades));

		Map retorno = cidadeDWR.getCidades(estado.getId().toString());

		assertEquals(2, retorno.size());
	}

	public void testGetCidadesSemUf()
	{
		Cidade cidade = CidadeFactory.getEntity();
		cidade.setId(1L);

		Collection<Cidade> cidades = new ArrayList<Cidade>();
		cidades.add(cidade);

		Map retorno = cidadeDWR.getCidades(null);

		assertEquals(0, retorno.size());
	}
}

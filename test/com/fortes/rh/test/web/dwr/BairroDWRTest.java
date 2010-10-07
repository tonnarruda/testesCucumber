package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.web.dwr.BairroDWR;

public class BairroDWRTest extends MockObjectTestCase
{
	private BairroDWR bairroDWR;
	private Mock bairroManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		bairroDWR = new BairroDWR();

		bairroManager = new Mock(BairroManager.class);

		bairroDWR.setBairroManager((BairroManager) bairroManager.proxy());
	}

	public void testGetBairros()
	{
		Cidade cidade = CidadeFactory.getEntity();
		cidade.setId(1L);

		Bairro bairro = new Bairro();
		bairro.setId(1L);
		bairro.setNome("Benfica");
		bairro.setCidade(cidade);

		Collection<Bairro> bairros = new ArrayList<Bairro>();
		bairros.add(bairro);

		bairroManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(bairros));

		String[] retorno = bairroDWR.getBairros(cidade.getId().toString());

		assertEquals(1, retorno.length);
	}

	public void testGetBairrosSemCidade()
	{
		Bairro bairro = new Bairro();
		bairro.setId(1L);
		bairro.setNome("Benfica");
		bairro.setCidade(null);

		Collection<Bairro> bairros = new ArrayList<Bairro>();
		bairros.add(bairro);

		String[] retorno = bairroDWR.getBairros(null);

		assertEquals(0, retorno.length);
	}

	public void testGetBairrosMap()
	{
		Cidade cidade = CidadeFactory.getEntity();
		cidade.setId(1L);

		Bairro bairro = new Bairro();
		bairro.setId(1L);
		bairro.setNome("Benfica");
		bairro.setCidade(cidade);

		Collection<Bairro> bairros = new ArrayList<Bairro>();
		bairros.add(bairro);

		bairroManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(bairros));

		Map retorno = bairroDWR.getBairrosMap(cidade.getId().toString());

		assertEquals(1, retorno.size());
	}

	public void testGetBairrosMapSemCidade()
	{
		Bairro bairro = new Bairro();
		bairro.setId(1L);
		bairro.setNome("Benfica");

		Collection<Bairro> bairros = new ArrayList<Bairro>();
		bairros.add(bairro);

		Map retorno = bairroDWR.getBairrosMap(null);

		assertEquals(0, retorno.size());
	}

	public void testNovoBairro()
	{
		String nomeBairro = "Benfica";
		Long cidadeId = 1L;

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setId(cidadeId);

		Bairro bairro = new Bairro();
		bairro.setId(1L);
		bairro.setCidade(cidade);

		bairroManager.expects(once()).method("existeBairro").with(ANYTHING).will(returnValue(false));

		bairroManager.expects(once()).method("save").with(ANYTHING).will(returnValue(bairro));

		Long retorno = bairroDWR.novoBairro(nomeBairro, cidadeId.toString());

		assertTrue(bairro.getId() == retorno);
	}

	public void testNovoBairroSemCidade()
	{
		String nomeBairro = "Benfica";
		Long cidadeId = 1L;

		Bairro bairro = new Bairro();
		bairro.setId(1L);

		bairroManager.expects(once()).method("existeBairro").with(ANYTHING).will(returnValue(true));

		Long retorno = bairroDWR.novoBairro(nomeBairro, cidadeId.toString());

		assertNull(retorno);
	}

}

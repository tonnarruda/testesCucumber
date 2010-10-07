package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.web.dwr.EstabelecimentoDWR;

public class EstabelecimentoDWRTest extends MockObjectTestCase
{
	private EstabelecimentoDWR estabelecimentoDWR;
	private Mock estabelecimentoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		estabelecimentoDWR = new EstabelecimentoDWR();

		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		estabelecimentoDWR.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
	}

	public void testCalcularDV()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		String cnpj = "999999999999";

		estabelecimentoManager.expects(once()).method("calculaDV").with(eq(cnpj)).will(returnValue("XX"));

		estabelecimentoManager.expects(once()).method("verificaCnpjExiste").with(ANYTHING, ANYTHING,ANYTHING).will(returnValue(true));

		String retorno = estabelecimentoDWR.calcularDV(cnpj, estabelecimento.getId().toString(), empresa.getId().toString());

		assertNotNull(retorno);
	}
	
	public void testGetByEmpresas()
	{
		Long empresaId = 1L;
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresaId )).will(returnValue(new ArrayList<Estabelecimento>()));
		
		assertEquals(0, estabelecimentoDWR.getByEmpresas(empresaId, new Long[]{1L}).size());
		
		empresaId = 0L;
		Long[] ids = new Long[]{1L};
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(ids )).will(returnValue(new ArrayList<Estabelecimento>()));
		assertEquals(0, estabelecimentoDWR.getByEmpresas(empresaId, ids).size());
	}

	public void testGetByEmpresa()
	{
		Long empresaId = 1L;
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresaId )).will(returnValue(new ArrayList<Estabelecimento>()));
		
		assertEquals(0, estabelecimentoDWR.getByEmpresa(empresaId).size());
	}
	
	public void testGetByEmpresaTodas()
	{
		Long empresaId = -1L;
		Collection<Estabelecimento> estabelecimentos = EstabelecimentoFactory.getCollection();
		
		estabelecimentoManager.expects(once()).method("findAll").will(returnValue(estabelecimentos));
		
		assertEquals(1, estabelecimentoDWR.getByEmpresa(empresaId).size());
	}
}

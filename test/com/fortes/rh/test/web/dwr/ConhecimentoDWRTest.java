package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.dwr.ConhecimentoDWR;

@SuppressWarnings("rawtypes")
public class ConhecimentoDWRTest extends MockObjectTestCase
{
	private ConhecimentoDWR conhecimentoDWR;
	private Mock conhecimentoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		conhecimentoDWR = new ConhecimentoDWR();

		conhecimentoManager = new Mock(ConhecimentoManager.class);
		conhecimentoDWR.setConhecimentoManager((ConhecimentoManager) conhecimentoManager.proxy());
	}

	public void testGetConhecimentos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setEmpresa(empresa);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(areaOrganizacional);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setId(1L);
		conhecimento.setAreaOrganizacionals(areaOrganizacionals);

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);

		conhecimentoManager.expects(once()).method("findByAreasOrganizacionalIds").with(ANYTHING, ANYTHING).will(returnValue(conhecimentos));

		String [] areaOrganizacionalIds = {areaOrganizacional.getId().toString()};

		Map retorno = conhecimentoDWR.getConhecimentos(areaOrganizacionalIds, empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testGetConhecimentosSemArea()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setId(1L);

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);

		conhecimentoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(conhecimentos));

		String [] areaOrganizacionalIds = null;

		Map retorno = conhecimentoDWR.getConhecimentos(areaOrganizacionalIds, empresa.getId());

		assertEquals(1, retorno.size());
	}
	
	public void testGetByEmpresa()
	{
		Long empresaId = 1L;
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(1L);
		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);
		
		conhecimentoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(conhecimentos));
		
		Map retorno = conhecimentoDWR.getByEmpresa(empresaId, new Long[]{empresaId});		
		assertEquals(1, retorno.size());
	}

	public void testGetByEmpresaTodosConhecimentos()
	{
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(1L);
		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);
		
		conhecimentoManager.expects(once()).method("findAllSelectDistinctNome").will(returnValue(conhecimentos));
		
		Map retorno = conhecimentoDWR.getByEmpresa(-1L, new Long[]{1L});		
		assertEquals(1, retorno.size());
	}
}

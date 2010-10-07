package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;
import com.fortes.rh.web.dwr.CandidatoDWR;

@SuppressWarnings("unchecked")
public class CandidatoDWRTest extends MockObjectTestCase
{
	private CandidatoDWR candidatoDWR;
	private Mock conhecimentoManager;
	private Mock candidatoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		candidatoDWR = new CandidatoDWR();

		conhecimentoManager = new Mock(ConhecimentoManager.class);
		candidatoManager = new Mock(CandidatoManager.class);

		candidatoDWR.setCandidatoManager((CandidatoManager) candidatoManager.proxy());
		candidatoDWR.setConhecimentoManager((ConhecimentoManager) conhecimentoManager.proxy());
	}

	public void testGetConhecimentos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();

		AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse(1L);
		areaInteresse.setEmpresa(empresa);
		areaInteresse.setAreasOrganizacionais(areaOrganizacionals);

		String [] areaInteresseIds = {areaInteresse.getId().toString()};

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setId(1L);
		conhecimento.setAreaOrganizacionals(areaOrganizacionals);

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);

		conhecimentoManager.expects(once()).method("findByAreaInteresse").with(ANYTHING, ANYTHING).will(returnValue(conhecimentos));

		Map retorno = candidatoDWR.getConhecimentos(areaInteresseIds, empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testGetConhecimentosSemAreaInteresseIds()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Collection<AreaOrganizacional> areaOrganizacionals = AreaOrganizacionalFactory.getCollection();

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setId(1L);
		conhecimento.setAreaOrganizacionals(areaOrganizacionals);

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);

		String [] areaInteresseIds = null;

		conhecimentoManager.expects(once()).method("findToList").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(conhecimentos));

		Map retorno = candidatoDWR.getConhecimentos(areaInteresseIds, empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testGetCandidatosHomonimos()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setId(1L);

		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);

		candidatoManager.expects(once()).method("getCandidatosByNome").with(ANYTHING).will(returnValue(candidatos));

		Map retorno  = candidatoDWR.getCandidatosHomonimos(candidato.getNome());

		assertEquals(candidatos.size(), retorno.size());
	}
	
	public void testFind()
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setId(1L);
		
		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		candidatos.add(candidato);
		
		candidatoManager.expects(once()).method("findByNomeCpf").with(ANYTHING, ANYTHING).will(returnValue(candidatos));
		
		Map retorno  = candidatoDWR.find("nome", "", 1L);
		
		assertEquals(candidatos.size(), retorno.size());
	}
}

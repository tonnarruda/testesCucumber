package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.web.dwr.ColaboradorDWR;

@SuppressWarnings("unchecked")
public class ColaboradorDWRTest extends MockObjectTestCase
{
	private ColaboradorDWR colaboradorDWR;
	private Mock colaboradorManager;
//	private Mock historicoColaboradorManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		colaboradorDWR = new ColaboradorDWR();

		colaboradorManager = new Mock(ColaboradorManager.class);
//		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);

		colaboradorDWR.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
//		colaboradorDWR.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());
	}

	public void testGetColaboradores()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setEmpresa(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setAreaOrganizacional(areaOrganizacional);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		String [] areaOrganizacionalIds = {areaOrganizacional.getId().toString()};

		colaboradorManager.expects(once()).method("findByAreasOrganizacionalIds").with(ANYTHING).will(returnValue(colaboradors));

		Map retorno = colaboradorDWR.getColaboradores(areaOrganizacionalIds, empresa.getId());

		assertEquals(1, retorno.size());
	}
	
	public void testGetByAreaEstabelecimentoEmpresasPassandoApenasEmpresa()
	{	
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);
		colaboradorManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(colaboradors));
		
		Map retorno = colaboradorDWR.getByAreaEstabelecimentoEmpresas(null, null, empresa.getId(), new Long[]{});
		
		assertEquals(1, retorno.size());
	}
	
	public void testGetByAreaEstabelecimentoEmpresasPassandoArea()
	{	
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setEmpresa(empresa);
		
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);
		colaboradorManager.expects(once()).method("findByAreaOrganizacionalEstabelecimento").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradors));
		
		String [] areaIds = {areaOrganizacional.getId().toString()};
		Map retorno = colaboradorDWR.getByAreaEstabelecimentoEmpresas(areaIds, null, empresa.getId(), new Long[]{});
		
		assertEquals(1, retorno.size());
	}
	
	public void testGetByAreaEstabelecimentoEmpresasPassandoEstabelecimento()
	{	
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);
		colaboradorManager.expects(once()).method("findByAreaOrganizacionalEstabelecimento").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradors));
		
		String [] estabelecimentoIds = {estabelecimento.getId().toString()};
		Map retorno = colaboradorDWR.getByAreaEstabelecimentoEmpresas(null, estabelecimentoIds, empresa.getId(), new Long[]{});
		
		assertEquals(1, retorno.size());
	}

	public void testGetColaboradoresSemArea()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		String [] areaOrganizacionalIds = null;

		colaboradorManager.expects(once()).method("findToList").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradors));

		Map retorno = colaboradorDWR.getColaboradores(areaOrganizacionalIds, empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testGetColaboradoresAreaEstabelecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setEmpresa(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setAreaOrganizacional(areaOrganizacional);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		colaboradorManager.expects(once()).method("findByAreasOrganizacionalIds").with(ANYTHING).will(returnValue(colaboradors));

		String [] areaOrganizacionalIds = {areaOrganizacional.getId().toString()};
		String [] estabelecimentoIds = null;

		Map retorno = colaboradorDWR.getColaboradoresAreaEstabelecimento(areaOrganizacionalIds, estabelecimentoIds, empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testGetColaboradoresAreaEstabelecimentoComEstabelecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setEmpresa(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setEmpresa(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setAreaOrganizacional(areaOrganizacional);
		colaborador.setEstabelecimento(estabelecimento);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		colaboradorManager.expects(once()).method("findByAreasOrganizacionaisEstabelecimentos").with(new Constraint[] {ANYTHING, ANYTHING}).will(returnValue(colaboradors));

		String [] areaOrganizacionalIds = {areaOrganizacional.getId().toString()};
		String [] estabelecimentoIds = {estabelecimento.getId().toString()};

		Map retorno = colaboradorDWR.getColaboradoresAreaEstabelecimento(areaOrganizacionalIds, estabelecimentoIds, empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testGetColaboradoresAreaEstabelecimentoSemArea()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setEmpresa(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEstabelecimento(estabelecimento);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		colaboradorManager.expects(once()).method("findByEstabelecimento").with(ANYTHING).will(returnValue(colaboradors));

		String [] areaOrganizacionalIds = null;
		String [] estabelecimentoIds = {estabelecimento.getId().toString()};

		Map retorno = colaboradorDWR.getColaboradoresAreaEstabelecimento(areaOrganizacionalIds, estabelecimentoIds, empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testGetColaboradoresByArea()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setEmpresa(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador1);

		colaboradorManager.expects(once()).method("findByAreaOrganizacionalEstabelecimento").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradors));

		String [] areaOrganizacionalIds = {areaOrganizacional.getId().toString()};

		Map retorno = colaboradorDWR.getColaboradoresByArea(areaOrganizacionalIds, empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testGetColaboradoresByAreaSemArea()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.setNome("Teste1");

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setColaborador(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setNome("Teste2");

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaborador2.setColaborador(colaborador2);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador1);
		colaboradors.add(colaborador2);

		colaboradorManager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(colaboradors));

		String [] areaOrganizacionalIds = null;

		Map retorno = colaboradorDWR.getColaboradoresByArea(areaOrganizacionalIds, empresa.getId());

		assertEquals(2, retorno.size());

	}

	public void testFind()
	{
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradors));

		Map retorno = colaboradorDWR.find("nome", "cpf", "matricula", 1L, false);

		assertEquals(0, retorno.size());
	}

	public void testGetByFuncaoAmbiente()
	{
		Funcao funcao = FuncaoFactory.getEntity(1L);

		Ambiente ambiente = AmbienteFactory.getEntity(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		colaboradorManager.expects(once()).method("findByFuncaoAmbiente").with(ANYTHING, ANYTHING).will(returnValue(colaboradors));

		Map retorno = colaboradorDWR.getByFuncaoAmbiente(funcao.getId(), ambiente.getId());

		assertEquals(1, retorno.size());
	}

}
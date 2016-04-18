package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.VerificacaoParentesco;
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

public class ColaboradorDWRTest extends MockObjectTestCase
{
	private ColaboradorDWR colaboradorDWR;
	private Mock colaboradorManager;
	private Mock empresaManager;
	private Mock historicoColaboradorManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		colaboradorDWR = new ColaboradorDWR();

		colaboradorManager = new Mock(ColaboradorManager.class);
		empresaManager = new Mock(EmpresaManager.class);
		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);

		colaboradorDWR.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		colaboradorDWR.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		colaboradorDWR.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());
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
		colaboradorManager.expects(once()).method("findAllSelect").with(ANYTHING, eq(null), ANYTHING).will(returnValue(colaboradors));
		
		Map retorno = colaboradorDWR.getByAreaEstabelecimentoEmpresas(null, null, empresa.getId(), new Long[]{}, null, true);
		
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
		colaboradorManager.expects(once()).method("findByAreaOrganizacionalEstabelecimento").with(ANYTHING, ANYTHING, ANYTHING, eq(null)).will(returnValue(colaboradors));
		
		String [] areaIds = {areaOrganizacional.getId().toString()};
		Map retorno = colaboradorDWR.getByAreaEstabelecimentoEmpresas(areaIds, null, empresa.getId(), new Long[]{}, null, true);
		
		assertEquals(1, retorno.size());
	}
	
	public void testGetByAreaEstabelecimentoEmpresasPassandoEstabelecimento()
	{	
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);
		colaboradorManager.expects(once()).method("findByAreaOrganizacionalEstabelecimento").with(ANYTHING, ANYTHING, ANYTHING, eq(null)).will(returnValue(colaboradors));
		
		String [] estabelecimentoIds = {estabelecimento.getId().toString()};
		Map retorno = colaboradorDWR.getByAreaEstabelecimentoEmpresas(null, estabelecimentoIds, empresa.getId(), new Long[]{}, null, true);
		
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

		colaboradorManager.expects(once()).method("findByAreaOrganizacionalEstabelecimento").with(ANYTHING, ANYTHING, ANYTHING, eq(null)).will(returnValue(colaboradors));

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
		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").withAnyArguments().will(returnValue(colaboradors));

		Map retorno = colaboradorDWR.find("nome", "cpf", "matricula", 1L, false, new Long[]{1L});

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

	public void testFindParentesByNome()
	{
		Empresa empresaBuscaMesmaEmpresa = EmpresaFactory.getEmpresa(1L);
		empresaBuscaMesmaEmpresa.setVerificaParentesco(VerificacaoParentesco.BUSCA_MESMA_EMPRESA);
		
		Empresa empresaBuscaTodasAsEmpresas = EmpresaFactory.getEmpresa(2L);
		empresaBuscaTodasAsEmpresas.setVerificaParentesco(VerificacaoParentesco.BUSCA_TODAS_AS_EMPRESAS);
		
		Colaborador colab1 = ColaboradorFactory.getEntity(1L);
		colab1.setNome("joao");
		colab1.setEmpresa(empresaBuscaMesmaEmpresa);

		Colaborador colab2 = ColaboradorFactory.getEntity(2L);
		colab2.setNome("joao");
		colab2.setEmpresa(empresaBuscaTodasAsEmpresas);
		
		Collection<Colaborador> colaboradoresMesmaEmpresa = Arrays.asList(colab1);
		Collection<Colaborador> colaboradoresTodasEmpresas = Arrays.asList(colab1, colab2);
		
		empresaManager.expects(once()).method("findById").with(eq(empresaBuscaMesmaEmpresa.getId())).will(returnValue(empresaBuscaMesmaEmpresa));
		colaboradorManager.expects(once()).method("findParentesByNome").with(eq(colab1.getId()), eq(empresaBuscaMesmaEmpresa.getId()), eq(new String[] {"joao"})).will(returnValue(colaboradoresMesmaEmpresa));
		colaboradorManager.expects(once()).method("montaParentesByNome").with(eq(colaboradoresMesmaEmpresa)).will(returnValue(colaboradoresMesmaEmpresa));
		
		empresaManager.expects(once()).method("findById").with(eq(empresaBuscaTodasAsEmpresas.getId())).will(returnValue(empresaBuscaTodasAsEmpresas));
		colaboradorManager.expects(once()).method("findParentesByNome").with(eq(colab2.getId()), eq(null), eq(new String[] {"joao"})).will(returnValue(colaboradoresTodasEmpresas));
		colaboradorManager.expects(once()).method("montaParentesByNome").with(eq(colaboradoresTodasEmpresas)).will(returnValue(colaboradoresTodasEmpresas));
		
		assertEquals(1, colaboradorDWR.findParentesByNome(colab1.getId(), empresaBuscaMesmaEmpresa.getId(), "joao").size());
		assertEquals(2, colaboradorDWR.findParentesByNome(colab2.getId(), empresaBuscaTodasAsEmpresas.getId(), "joao").size());
	}
	
	public void testExisteHistoricoAguardandoConfirmacaoNoFortesPessoalSemHistorico()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaboradorPedro = ColaboradorFactory.getEntity(2L);
		colaboradorPedro.setNome("Pedro");
		colaboradorPedro.setEmpresa(empresa);
		
		historicoColaboradorManager.expects(once()).method("findByColaboradorProjection").with(eq(colaboradorPedro.getId()), eq(StatusRetornoAC.AGUARDANDO)).will(returnValue(new ArrayList<HistoricoColaborador>()));
		
		assertFalse(colaboradorDWR.existeHistoricoAguardandoConfirmacaoNoFortesPessoal(colaboradorPedro.getId()));
	}
	
	public void testExisteHistoricoAguardandoConfirmacaoNoFortesPessoalComHistorico()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaboradorJoao = ColaboradorFactory.getEntity(1L);
		colaboradorJoao.setNome("Joao");
		colaboradorJoao.setEmpresa(empresa);

		HistoricoColaborador historicoColaboradorJoao = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorJoao.setStatus(StatusRetornoAC.AGUARDANDO);
		
		Collection<HistoricoColaborador> historicoColaboradores = Arrays.asList(historicoColaboradorJoao);
		
		historicoColaboradorManager.expects(once()).method("findByColaboradorProjection").with(eq(colaboradorJoao.getId()), eq(StatusRetornoAC.AGUARDANDO)).will(returnValue(historicoColaboradores));
		
		assertTrue(colaboradorDWR.existeHistoricoAguardandoConfirmacaoNoFortesPessoal(colaboradorJoao.getId()));
	}
	
}
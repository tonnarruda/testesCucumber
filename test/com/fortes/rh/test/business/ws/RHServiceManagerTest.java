package com.fortes.rh.test.business.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.ws.RHServiceImpl;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.ws.TAreaOrganizacional;
import com.fortes.rh.model.ws.TCandidato;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.model.ws.TEstabelecimento;
import com.fortes.rh.model.ws.TIndice;
import com.fortes.rh.model.ws.TIndiceHistorico;
import com.fortes.rh.model.ws.TOcorrencia;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.model.ws.TSituacaoCargo;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class RHServiceManagerTest extends MockObjectTestCase
{
	private RHServiceImpl rHServiceManager = new RHServiceImpl();
	private Mock historicoColaboradorManager;
	private Mock colaboradorManager;
	private Mock transactionManager;
	private Mock empresaManager;
	private Mock usuarioEmpresaManager;
	private Mock usuarioMensagemManager;
	private Mock estabelecimentoManager;
	private Mock cidadeManager;
	private Mock indiceManager;
	private Mock indiceHistoricoManager;
	private Mock faixaSalarialHistoricoManager;
	private Mock faixaSalarialManager;
	private Mock mensagemManager;
	private Mock ocorrenciaManager;
	private Mock colaboradorOcorrenciaManager;
	private Mock cargoManager;
	private Mock candidatoManager;
	private Mock areaOrganizacionalManager;

	protected void setUp() throws Exception
	{
		super.setUp();

		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		rHServiceManager.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());
		colaboradorManager = new Mock(ColaboradorManager.class);
		rHServiceManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		transactionManager = new Mock(PlatformTransactionManager.class);
		rHServiceManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
		empresaManager = new Mock(EmpresaManager.class);
		rHServiceManager.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		usuarioEmpresaManager = new Mock(UsuarioEmpresaManager.class);
		rHServiceManager.setUsuarioEmpresaManager((UsuarioEmpresaManager) usuarioEmpresaManager.proxy());
		usuarioMensagemManager = new Mock(UsuarioMensagemManager.class);
		rHServiceManager.setUsuarioMensagemManager((UsuarioMensagemManager) usuarioMensagemManager.proxy());
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		rHServiceManager.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		cidadeManager = new Mock(CidadeManager.class);
		rHServiceManager.setCidadeManager((CidadeManager) cidadeManager.proxy());
		indiceManager = new Mock(IndiceManager.class);
		rHServiceManager.setIndiceManager((IndiceManager) indiceManager.proxy());
		indiceHistoricoManager = new Mock(IndiceHistoricoManager.class);
		rHServiceManager.setIndiceHistoricoManager((IndiceHistoricoManager) indiceHistoricoManager.proxy());
		faixaSalarialHistoricoManager = new Mock(FaixaSalarialHistoricoManager.class);
		rHServiceManager.setFaixaSalarialHistoricoManager((FaixaSalarialHistoricoManager) faixaSalarialHistoricoManager.proxy());
		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		rHServiceManager.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());
		mensagemManager = new Mock(MensagemManager.class);
		rHServiceManager.setMensagemManager((MensagemManager) mensagemManager.proxy());
		ocorrenciaManager = new Mock(OcorrenciaManager.class);
		rHServiceManager.setOcorrenciaManager((OcorrenciaManager) ocorrenciaManager.proxy());
		colaboradorOcorrenciaManager = new Mock(ColaboradorOcorrenciaManager.class);
		rHServiceManager.setColaboradorOcorrenciaManager((ColaboradorOcorrenciaManager)colaboradorOcorrenciaManager.proxy());
		cargoManager = new Mock(CargoManager.class);
		rHServiceManager.setCargoManager((CargoManager)cargoManager.proxy());
		candidatoManager = new Mock(CandidatoManager.class);
		rHServiceManager.setCandidatoManager((CandidatoManager)candidatoManager.proxy());
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		rHServiceManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
	}

	public void testEco() throws Exception
	{
		assertEquals("ecoooo", rHServiceManager.eco("ecoooo"));
	}

	public void testDesligarColaborador() throws Exception
	{
		String dataDesligamento = "01/01/2009";
		String empresaCodigoAC = "123456";
		String colaboradorCodigoAC = "123123";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNomeComercial("nomeComercial");

		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresaCodigoAC)).will(returnValue(empresa));
		colaboradorManager.expects(once()).method("findByCodigoAC").with(eq(colaboradorCodigoAC), eq(empresa)).will(returnValue(colaborador));
		usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleSetorPessoal").with(eq(empresaCodigoAC)).will(returnValue(new ArrayList<UsuarioEmpresa>()));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING);
		colaboradorManager.expects(once()).method("desligaColaboradorAC").with(eq(colaboradorCodigoAC), eq(empresa), ANYTHING).will(returnValue(true));

		assertEquals(true, rHServiceManager.desligarEmpregado(colaboradorCodigoAC, empresaCodigoAC, dataDesligamento));
	}

	public void testReligarColaborador() throws Exception
	{
		String empresaCodigoAC = "123456";
		String colaboradorCodigoAC = "123123";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresaCodigoAC)).will(returnValue(empresa));
		colaboradorManager.expects(once()).method("religaColaboradorAC").with(eq(colaboradorCodigoAC), eq(empresa)).will(returnValue(true));
		assertEquals(true, rHServiceManager.religarEmpregado(colaboradorCodigoAC, empresaCodigoAC));
	}

	public void testAtualizarEmpregadoAndSituacao() throws Exception
	{
		String empresaCodigoAC = "12345";
		TSituacao situacao = new TSituacao();
		TEmpregado empregado = new TEmpregado();
		situacao.setEmpresaCodigoAC(empresaCodigoAC);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		colaboradorManager.expects(once()).method("updateEmpregado").with(eq(empregado)).will(returnValue(colaborador));
		historicoColaboradorManager.expects(once()).method("updateSituacao").with(eq(situacao));
		transactionManager.expects(once()).method("commit").with(ANYTHING);

		assertEquals(true, rHServiceManager.atualizarEmpregadoAndSituacao(empregado, situacao));
	}

	public void testAtualizarEmpregadoAndSituacaoEmpregadoException() throws Exception
	{
		String empresaCodigoAC = "12345";
		TSituacao situacao = new TSituacao();
		TEmpregado colaborador = new TEmpregado();
		situacao.setEmpresaCodigoAC(empresaCodigoAC);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		colaboradorManager.expects(once()).method("updateEmpregado").with(eq(colaborador)).will(throwException(new Exception()));
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		assertEquals(false, rHServiceManager.atualizarEmpregadoAndSituacao(colaborador, situacao));
	}

	public void testAtualizarEmpregadoAndSituacaoSituacaoException() throws Exception
	{
		String empresaCodigoAC = "12345";
		TEmpregado empregado = new TEmpregado();
		TSituacao situacao = new TSituacao();
		situacao.setEmpresaCodigoAC(empresaCodigoAC);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		colaboradorManager.expects(once()).method("updateEmpregado").with(eq(empregado)).will(returnValue(colaborador));
		historicoColaboradorManager.expects(once()).method("updateSituacao").with(eq(situacao)).will(throwException(new Exception()));;
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		assertEquals(false, rHServiceManager.atualizarEmpregadoAndSituacao(empregado, situacao));
	}

	public void testAtualizarColaborador() throws Exception
	{
		TEmpregado colaborador = new TEmpregado();

		colaboradorManager.expects(once()).method("updateEmpregado").with(eq(colaborador));
		assertEquals(true, rHServiceManager.atualizarEmpregado(colaborador));
	}

	public void testAtualizarColaboradorException() throws Exception
	{
		TEmpregado colaborador = new TEmpregado();

		colaboradorManager.expects(once()).method("updateEmpregado").with(eq(colaborador)).will(throwException(new Exception()));
		assertEquals(false, rHServiceManager.atualizarEmpregado(colaborador));
	}

	public void testCriarSituacao() throws Exception
	{
		TSituacao situacao = new TSituacao();
		situacao.setEmpresaCodigoAC("12345");
		situacao.setEmpregadoCodigoAC("54321");

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);

		historicoColaboradorManager.expects(once()).method("prepareSituacao").with(eq(situacao)).will(returnValue(historicoColaborador));
		colaboradorManager.expects(once()).method("findByCodigoAC").with(ANYTHING, ANYTHING).will(returnValue(colaborador));
		historicoColaboradorManager.expects(once()).method("save").with(eq(historicoColaborador));

		assertEquals(true, rHServiceManager.criarSituacao(situacao));
	}

	public void testCriarSituacaoException() throws Exception
	{
		TSituacao situacao = new TSituacao();
		situacao.setEmpresaCodigoAC("12345");
		situacao.setEmpregadoCodigoAC("54321");

		historicoColaboradorManager.expects(once()).method("prepareSituacao").with(eq(situacao)).will(throwException(new Exception()));
		assertEquals(false, rHServiceManager.criarSituacao(situacao));
	}

	public void testAtualizarSituacao() throws Exception
	{
		TSituacao situacao = new TSituacao();
		String empresaCodigoAC = "12345";
		situacao.setEmpresaCodigoAC(empresaCodigoAC);

		historicoColaboradorManager.expects(once()).method("updateSituacao").with(eq(situacao));
		assertEquals(true, rHServiceManager.atualizarSituacao(situacao));
	}

	public void testAtualizarSituacaoException() throws Exception
	{
		TSituacao situacao = new TSituacao();
		String empresaCodigoAC = "12345";
		situacao.setEmpresaCodigoAC(empresaCodigoAC);

		historicoColaboradorManager.expects(once()).method("updateSituacao").with(eq(situacao)).will(throwException(new Exception()));
		assertEquals(false, rHServiceManager.atualizarSituacao(situacao));
	}

	public void testRemoverSituacao() throws Exception
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		TSituacao situacao = new TSituacao();
		situacao.setId(1);
		situacao.setData("01/01/2000");
		situacao.setEmpregadoCodigoAC("55");
		situacao.setEmpresaCodigoAC("77");

		historicoColaboradorManager.expects(once()).method("findByAC").with(ANYTHING, eq(situacao.getEmpregadoCodigoAC()), eq(situacao.getEmpresaCodigoAC())).will(returnValue(historicoColaborador));
		historicoColaboradorManager.expects(once()).method("removeHistoricoAndReajusteAC").with(eq(historicoColaborador));
		assertEquals(true, rHServiceManager.removerSituacao(situacao));
	}

	public void testRemoverSituacaoException() throws Exception
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		TSituacao situacao = new TSituacao();
		situacao.setId(1);
		situacao.setData("01/01/2000");
		situacao.setEmpregadoCodigoAC("fadf622");
		situacao.setEmpresaCodigoAC("8674dsfaf");

		historicoColaboradorManager.expects(once()).method("findByAC").with(ANYTHING, eq(situacao.getEmpregadoCodigoAC()), eq(situacao.getEmpresaCodigoAC())).will(returnValue(historicoColaborador));
		historicoColaboradorManager.expects(once()).method("removeHistoricoAndReajusteAC").with(eq(historicoColaborador)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		assertEquals(false, rHServiceManager.removerSituacao(situacao));
	}
	
	public void testCriarCargo() throws Exception
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Cargo cargo = CargoFactory.getEntity(1L);
		TCargo tCargo = new TCargo();
		
		faixaSalarialManager.expects(once()).method("montaFaixa").with(eq(tCargo)).will(returnValue(faixaSalarial));
		cargoManager.expects(once()).method("preparaCargoDoAC").with(eq(tCargo)).will(returnValue(cargo));
		faixaSalarialManager.expects(once()).method("save").with(ANYTHING);
		
		assertEquals(true, rHServiceManager.criarCargo(tCargo));
	}
	
	public void testAtualizarCargo() throws Exception
	{
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		TCargo tCargo = new TCargo();
		
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING).will(returnValue(faixaSalarial));
		faixaSalarialManager.expects(once()).method("updateAC").with(eq(tCargo));
		cargoManager.expects(once()).method("updateCBO").with(ANYTHING, ANYTHING);
		
		assertEquals(true, rHServiceManager.atualizarCargo(tCargo));
	}
	
	public void testRemoverCargo() throws Exception
	{
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		TCargo tCargo = new TCargo();
		
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING).will(returnValue(faixaSalarial));
		faixaSalarialManager.expects(once()).method("remove").with(ANYTHING);
		faixaSalarialManager.expects(once()).method("findByCargo").with(ANYTHING).will(returnValue(new ArrayList<FaixaSalarial>()));
		cargoManager.expects(once()).method("remove").with(ANYTHING);
		
		assertEquals(true, rHServiceManager.removerCargo(tCargo));
	}
	
	public void testCriarSituacaoCargo() throws Exception
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		TSituacaoCargo tSituacaoCargo = new TSituacaoCargo();
		
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING).will(returnValue(faixaSalarial));
		faixaSalarialHistoricoManager.expects(once()).method("bind").with(ANYTHING, ANYTHING);
		faixaSalarialHistoricoManager.expects(once()).method("findIdByDataFaixa").with(ANYTHING).will(returnValue(null));
		faixaSalarialHistoricoManager.expects(once()).method("save").with(ANYTHING);
		
		assertEquals(true, rHServiceManager.criarSituacaoCargo(tSituacaoCargo));
	}
	
	public void testAtualizarSituacaoCargo() throws Exception
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		TSituacaoCargo tSituacaoCargo = new TSituacaoCargo();
		
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING).will(returnValue(faixaSalarial));
		faixaSalarialHistoricoManager.expects(once()).method("bind").with(ANYTHING, ANYTHING).will(returnValue(new FaixaSalarialHistorico()));
		faixaSalarialHistoricoManager.expects(once()).method("findIdByDataFaixa").with(ANYTHING).will(returnValue(1L));
		faixaSalarialHistoricoManager.expects(once()).method("update").with(ANYTHING);
		
		assertEquals(true, rHServiceManager.atualizarSituacaoCargo(tSituacaoCargo));
	}
	
	public void testRemoverSituacaoCargo() throws Exception
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		TSituacaoCargo tSituacaoCargo = new TSituacaoCargo();
		
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING).will(returnValue(faixaSalarial));
		faixaSalarialHistoricoManager.expects(once()).method("findIdByDataFaixa").with(ANYTHING).will(returnValue(1L));
		faixaSalarialHistoricoManager.expects(once()).method("remove").with(ANYTHING);
		
		assertEquals(true, rHServiceManager.removerSituacaoCargo(tSituacaoCargo));
	}
	
	public void testCriarAreaOrganizacional() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("123123");
		
		TAreaOrganizacional area = new TAreaOrganizacional();
		area.setEmpresaCodigo(empresa.getCodigoAC());
		
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC())).will(returnValue(empresa));
		areaOrganizacionalManager.expects(once()).method("bind").with(ANYTHING, ANYTHING);
		areaOrganizacionalManager.expects(once()).method("save").with(ANYTHING);
		
		assertEquals(true, rHServiceManager.criarAreaOrganizacional(area));
	}
	
	public void testCriarAreaOrganizacionalException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("123123");
		
		TAreaOrganizacional area = new TAreaOrganizacional();
		area.setEmpresaCodigo(empresa.getCodigoAC());
		
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC())).will(returnValue(empresa));
		areaOrganizacionalManager.expects(once()).method("bind").with(ANYTHING, ANYTHING);
		areaOrganizacionalManager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		
		assertEquals(false, rHServiceManager.criarAreaOrganizacional(area));
	}
	
	public void testAtualizarAreaOrganizacional() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("123123");
		
		TAreaOrganizacional area = new TAreaOrganizacional();
		area.setEmpresaCodigo(empresa.getCodigoAC());
		
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC())).will(returnValue(empresa));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING).will(returnValue(AreaOrganizacionalFactory.getEntity(1L)));
		areaOrganizacionalManager.expects(once()).method("bind").with(ANYTHING, ANYTHING);
		areaOrganizacionalManager.expects(once()).method("update").with(ANYTHING);
		
		assertEquals(true, rHServiceManager.atualizarAreaOrganizacional(area));
	}
	
	public void testAtualizarAreaOrganizacionalException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("123123");
		
		TAreaOrganizacional area = new TAreaOrganizacional();
		area.setEmpresaCodigo(empresa.getCodigoAC());
		
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC())).will(returnValue(empresa));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING).will(returnValue(AreaOrganizacionalFactory.getEntity(1L)));
		areaOrganizacionalManager.expects(once()).method("bind").with(ANYTHING, ANYTHING);
		areaOrganizacionalManager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		
		assertEquals(false, rHServiceManager.atualizarAreaOrganizacional(area));
	}
	
	public void testRemoverAreaOrganizacional() throws Exception
	{
		TAreaOrganizacional area = new TAreaOrganizacional();
		
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING);
		areaOrganizacionalManager.expects(once()).method("remove").with(ANYTHING);
		
		assertEquals(true, rHServiceManager.removerAreaOrganizacional(area));
	}
	
	public void testRemoverAreaOrganizacionalException() throws Exception
	{
		TAreaOrganizacional area = new TAreaOrganizacional();
		
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING);
		areaOrganizacionalManager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		
		assertEquals(false, rHServiceManager.removerAreaOrganizacional(area));
	}

	public void testCriarEstabelecimento() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("123123");

		Cidade cidade = CidadeFactory.getEntity();

		TEstabelecimento tEstabelecimento = new TEstabelecimento();
		tEstabelecimento.setCodigoEmpresa(empresa.getCodigoAC());
		tEstabelecimento.setCodigoCidade("1");
		tEstabelecimento.setUf("uf");

		cidadeManager.expects(once()).method("findByCodigoAC").with(eq(tEstabelecimento.getCodigoCidade()), eq(tEstabelecimento.getUf())).will(returnValue(cidade));
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC())).will(returnValue(empresa));
		estabelecimentoManager.expects(once()).method("save").with(ANYTHING);

		assertEquals(true, rHServiceManager.criarEstabelecimento(tEstabelecimento));
	}

	public void testCriarEstabelecimentoException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("123123");

		Cidade cidade = CidadeFactory.getEntity();

		TEstabelecimento tEstabelecimento = new TEstabelecimento();
		tEstabelecimento.setCodigoEmpresa(empresa.getCodigoAC());
		tEstabelecimento.setCodigoCidade("1");
		tEstabelecimento.setUf("uf");

		cidadeManager.expects(once()).method("findByCodigoAC").with(eq(tEstabelecimento.getCodigoCidade()), eq(tEstabelecimento.getUf())).will(returnValue(cidade));
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC())).will(returnValue(empresa));
		estabelecimentoManager.expects(once()).method("save").with(ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));

		assertEquals(false, rHServiceManager.criarEstabelecimento(tEstabelecimento));
	}

	public void testAtualizarEstabelecimento() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("123123");

		Cidade cidade = CidadeFactory.getEntity();

		TEstabelecimento tEstabelecimento = new TEstabelecimento();
		tEstabelecimento.setCodigo("456456");
		tEstabelecimento.setCodigoEmpresa(empresa.getCodigoAC());
		tEstabelecimento.setCodigoCidade("1");
		tEstabelecimento.setUf("uf");

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);

		estabelecimentoManager.expects(once()).method("findByCodigo").with(eq(tEstabelecimento.getCodigo()), eq(tEstabelecimento.getCodigoEmpresa())).will(returnValue(estabelecimento));
		cidadeManager.expects(once()).method("findByCodigoAC").with(eq(tEstabelecimento.getCodigoCidade()), eq(tEstabelecimento.getUf())).will(returnValue(cidade));
		estabelecimentoManager.expects(once()).method("update").with(eq(estabelecimento));

		assertEquals(true, rHServiceManager.atualizarEstabelecimento(tEstabelecimento));
	}

	public void testAtualizarEstabelecimentoException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("123123");

		Cidade cidade = CidadeFactory.getEntity();

		TEstabelecimento tEstabelecimento = new TEstabelecimento();
		tEstabelecimento.setCodigo("456456");
		tEstabelecimento.setCodigoEmpresa(empresa.getCodigoAC());
		tEstabelecimento.setCodigoCidade("1");
		tEstabelecimento.setUf("uf");

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);

		estabelecimentoManager.expects(once()).method("findByCodigo").with(eq(tEstabelecimento.getCodigo()), eq(tEstabelecimento.getCodigoEmpresa())).will(returnValue(estabelecimento));
		cidadeManager.expects(once()).method("findByCodigoAC").with(eq(tEstabelecimento.getCodigoCidade()), eq(tEstabelecimento.getUf())).will(returnValue(cidade));
		estabelecimentoManager.expects(once()).method("update").with(eq(estabelecimento)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));

		assertEquals(false, rHServiceManager.atualizarEstabelecimento(tEstabelecimento));
	}

	public void testRemoverEstabelecimento() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("123123");

		String codigoEstabelecimento = "12";
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(empresa.getCodigoAC())).will(returnValue(empresa));
		estabelecimentoManager.expects(once()).method("remove").with(eq(codigoEstabelecimento), eq(empresa.getId())).will(returnValue(true));

		assertEquals(true, rHServiceManager.removerEstabelecimento(codigoEstabelecimento, empresa.getCodigoAC()));
	}

	public void testCriarIndice() throws Exception
	{
		TIndice tIndice = new TIndice();

		indiceManager.expects(once()).method("save");
		assertEquals(true, rHServiceManager.criarIndice(tIndice));
	}

	public void testCriarIndiceException() throws Exception
	{
		TIndice tIndice = new TIndice();

		indiceManager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		assertEquals(false, rHServiceManager.criarIndice(tIndice));
	}

	public void testAtualizarIndice() throws Exception
	{
		TIndice tIndice = new TIndice();
		tIndice.setCodigo("123");
		Indice indice = IndiceFactory.getEntity(1L);

		indiceManager.expects(once()).method("findByCodigo").with(eq(tIndice.getCodigo())).will(returnValue(indice));
		indiceManager.expects(once()).method("update");
		assertEquals(true, rHServiceManager.atualizarIndice(tIndice));
	}

	public void testAtualizarIndiceException() throws Exception
	{
		TIndice tIndice = new TIndice();
		tIndice.setCodigo("123");
		Indice indice = IndiceFactory.getEntity(1L);

		indiceManager.expects(once()).method("findByCodigo").with(eq(tIndice.getCodigo())).will(returnValue(indice));
		indiceManager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		assertEquals(false, rHServiceManager.atualizarIndice(tIndice));
	}

	public void testRemoverIndice() throws Exception
	{
		String codigoIndice = "123";
		indiceManager.expects(once()).method("remove").with(eq(codigoIndice)).will(returnValue(true));
		assertEquals(true, rHServiceManager.removerIndice(codigoIndice));
	}

	public void testCriarIndiceHistorico() throws Exception
	{
		Indice indice = IndiceFactory.getEntity(1L);

		TIndiceHistorico tIndiceHistorico = new TIndiceHistorico();
		tIndiceHistorico.setIndiceCodigo("123");

		indiceManager.expects(once()).method("findByCodigo").with(eq(tIndiceHistorico.getIndiceCodigo())).will(returnValue(indice));
		indiceHistoricoManager.expects(once()).method("verifyExists").with(ANYTHING, ANYTHING).will(returnValue(false));
		indiceHistoricoManager.expects(once()).method("save").with(ANYTHING);

		assertEquals(true, rHServiceManager.criarIndiceHistorico(tIndiceHistorico));
	}

	public void testCriarIndiceHistoricoIndiceNull() throws Exception
	{
		TIndiceHistorico tIndiceHistorico = new TIndiceHistorico();
		tIndiceHistorico.setIndiceCodigo("123");

		indiceManager.expects(once()).method("findByCodigo").with(eq(tIndiceHistorico.getIndiceCodigo())).will(returnValue(null));

		assertEquals(false, rHServiceManager.criarIndiceHistorico(tIndiceHistorico));
	}

	public void testCriarIndiceHistoricoVerifyTrue() throws Exception
	{
		Indice indice = IndiceFactory.getEntity(1L);

		TIndiceHistorico tIndiceHistorico = new TIndiceHistorico();
		tIndiceHistorico.setIndiceCodigo("123");

		indiceManager.expects(once()).method("findByCodigo").with(eq(tIndiceHistorico.getIndiceCodigo())).will(returnValue(indice));
		indiceHistoricoManager.expects(once()).method("verifyExists").with(ANYTHING, ANYTHING).will(returnValue(true));
		indiceHistoricoManager.expects(once()).method("updateValor").with(ANYTHING, ANYTHING, ANYTHING);

		assertEquals(true, rHServiceManager.criarIndiceHistorico(tIndiceHistorico));
	}

	public void testRemoverIndiceHistorico() throws Exception
	{
//		Date data = DateUtil.montaDataByString("01/01/2000");
		Indice indice = IndiceFactory.getEntity(1L);

		TIndiceHistorico tIndiceHistorico = new TIndiceHistorico();
		tIndiceHistorico.setIndiceCodigo("123");

		indiceManager.expects(once()).method("findByCodigo").with(eq(tIndiceHistorico.getIndiceCodigo())).will(returnValue(indice));
		indiceHistoricoManager.expects(once()).method("remove").with(ANYTHING, eq(indice.getId())).will(returnValue(true));

		assertEquals(true, rHServiceManager.removerIndiceHistorico("01/01/2000", tIndiceHistorico.getIndiceCodigo()));
	}

	public void testRemoverIndiceHistoricoSemIndice() throws Exception
	{
		TIndiceHistorico tIndiceHistorico = new TIndiceHistorico();
		tIndiceHistorico.setIndiceCodigo("123");

		indiceManager.expects(once()).method("findByCodigo").with(eq(tIndiceHistorico.getIndiceCodigo())).will(returnValue(null));

		assertEquals(true, rHServiceManager.removerIndiceHistorico("01/01/2000", tIndiceHistorico.getIndiceCodigo()));
	}

//	public void testCancelaHistoricoColaborador() throws Exception
//	{
//		historicoColaboradorManager.expects(once()).method("cancelaHistoricoColaboradorAC").with(ANYTHING, ANYTHING, ANYTHING);
//		TSituacao situacao = new TSituacao();
//		assertEquals(true, rHServiceManager.cancelarSituacao(situacao, ""));
//	}

	public void testSetStatusFaixaSalarialHistorico() throws Exception
	{
		String mensagem = "";
		String empresaCodigoAC = "";
		Long faixaSalarialHistoricoId = 1L;
		boolean aprovado = false;

		String mensagemFinal = "";

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);

		Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();

		faixaSalarialHistoricoManager.expects(once()).method("findByIdProjection").with(eq(faixaSalarialHistorico.getId())).will(returnValue(faixaSalarialHistorico));
		mensagemManager.expects(once()).method("formataMensagemCancelamentoFaixaSalarialHistorico").with(eq(mensagem), eq(faixaSalarialHistorico)).will(returnValue(mensagemFinal));
		usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleSetorPessoal").with(eq(empresaCodigoAC)).will(returnValue(usuarioEmpresas));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").with(eq(mensagemFinal), ANYTHING, eq(null), eq(usuarioEmpresas));
		faixaSalarialHistoricoManager.expects(once()).method("setStatus").with(eq(faixaSalarialHistoricoId), eq(aprovado)).will(returnValue(true));

		assertEquals(true, rHServiceManager.setStatusFaixaSalarialHistorico(faixaSalarialHistoricoId, aprovado, mensagem, empresaCodigoAC));
	}

	public void testCriarEmpresa() throws Exception
	{
		TEmpresa empresaAC = new TEmpresa();
		empresaManager.expects(once()).method("criarEmpresa").with(eq(empresaAC)).will(returnValue(true));

		assertEquals(true, rHServiceManager.criarEmpresa(empresaAC));
	}

	public void testCriarOcorrencia()
	{
		TOcorrencia ocorrenciaAC = new TOcorrencia();
		empresaManager.expects(once()).method("findByCodigoAC");
		ocorrenciaManager.expects(once()).method("saveFromAC");

		assertTrue(rHServiceManager.criarOcorrencia(ocorrenciaAC));
	}

	public void testCriarOcorrenciaException()
	{
		TOcorrencia ocorrenciaAC = new TOcorrencia();
		empresaManager.expects(once()).method("findByCodigoAC");
		ocorrenciaManager.expects(once()).method("saveFromAC").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		assertFalse(rHServiceManager.criarOcorrencia(ocorrenciaAC));
	}

	public void testRemoverOcorrencia()
	{
		String codigoAC = "123";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("005");
		TOcorrencia ocorrencia = new TOcorrencia();
		ocorrencia.setEmpresa("005");
		ocorrencia.setCodigo("123");

		empresaManager.expects(once()).method("findByCodigoAC").will(returnValue(empresa));
		ocorrenciaManager.expects(once()).method("removeByCodigoAC").with(eq(codigoAC), eq(empresa.getId())).will(returnValue(true));

		assertTrue(rHServiceManager.removerOcorrencia(ocorrencia));
	}

	public void testRemoverOcorrenciaException()
	{
		String codigoAC = "123";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		TOcorrencia ocorrencia = new TOcorrencia();
		ocorrencia.setEmpresa("005");
		ocorrencia.setCodigo("123");

		empresaManager.expects(once()).method("findByCodigoAC").will(returnValue(empresa));
		ocorrenciaManager.expects(once()).method("removeByCodigoAC").with(eq(codigoAC), eq(empresa.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));

		assertFalse(rHServiceManager.removerOcorrencia(ocorrencia));
	}

	public void testCriarOcorrenciaEmpregado()
	{
		String empCodigo = "005";
		String data = "01/01/2000";
		
		TOcorrenciaEmpregado tColaboradorOcorrencia0 = new TOcorrenciaEmpregado();
		tColaboradorOcorrencia0.setData(data);
		tColaboradorOcorrencia0.setEmpresa(empCodigo);
		tColaboradorOcorrencia0.setCodigoEmpregado("123");
		tColaboradorOcorrencia0.setCodigo("333");
		tColaboradorOcorrencia0.setObs("obs");

		TOcorrenciaEmpregado tColaboradorOcorrencia1 = new TOcorrenciaEmpregado();
		tColaboradorOcorrencia1.setData(data);
		tColaboradorOcorrencia1.setEmpresa(empCodigo);
		tColaboradorOcorrencia1.setCodigoEmpregado("123");
		tColaboradorOcorrencia1.setCodigo("311");
		tColaboradorOcorrencia1.setObs("obs1");

		TOcorrenciaEmpregado[] tcolaboradorOcorrencias = new TOcorrenciaEmpregado[2];
		tcolaboradorOcorrencias[0] = tColaboradorOcorrencia0;
		tcolaboradorOcorrencias[1] = tColaboradorOcorrencia1;

		colaboradorOcorrenciaManager.expects(once()).method("saveOcorrenciasFromAC");

		assertTrue(rHServiceManager.criarOcorrenciaEmpregado(tcolaboradorOcorrencias));
	}

	public void testCriarOcorrenciaEmpregadoException()
	{
		TOcorrenciaEmpregado[] ocorrenciaEmpregados = new TOcorrenciaEmpregado[0];
		colaboradorOcorrenciaManager.expects(once()).method("saveOcorrenciasFromAC").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		assertFalse(rHServiceManager.criarOcorrenciaEmpregado(ocorrenciaEmpregados));
	}

	public void testRemoverOcorrenciaEmpregado()
	{
		String empCodigo = "005";
		String data = "01/01/2000";
		
		TOcorrenciaEmpregado tColaboradorOcorrencia0 = new TOcorrenciaEmpregado();
		tColaboradorOcorrencia0.setData(data);
		tColaboradorOcorrencia0.setEmpresa(empCodigo);
		tColaboradorOcorrencia0.setCodigoEmpregado("123");
		tColaboradorOcorrencia0.setCodigo("333");
		tColaboradorOcorrencia0.setObs("obs");

		TOcorrenciaEmpregado tColaboradorOcorrencia1 = new TOcorrenciaEmpregado();
		tColaboradorOcorrencia1.setData(data);
		tColaboradorOcorrencia1.setEmpresa(empCodigo);
		tColaboradorOcorrencia1.setCodigoEmpregado("123");
		tColaboradorOcorrencia1.setCodigo("311");
		tColaboradorOcorrencia1.setObs("obs1");

		TOcorrenciaEmpregado[] ocorrenciaEmpregados = new TOcorrenciaEmpregado[2];
		ocorrenciaEmpregados[0] = tColaboradorOcorrencia0;
		ocorrenciaEmpregados[1] = tColaboradorOcorrencia1;

		colaboradorOcorrenciaManager.expects(once()).method("removeFromAC");

		assertTrue(rHServiceManager.removerOcorrenciaEmpregado(ocorrenciaEmpregados));
	}

	public void testRemoverOcorrenciaEmpregadoException()
	{
		TOcorrenciaEmpregado[] ocorrenciaEmpregados = new TOcorrenciaEmpregado[0];
		colaboradorOcorrenciaManager.expects(once()).method("removeFromAC").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
		assertFalse(rHServiceManager.removerOcorrenciaEmpregado(ocorrenciaEmpregados));
	}

	public void testGetEmpresas()
	{
		Collection<Empresa> colecao = Arrays.asList(new Empresa[]{EmpresaFactory.getEmpresa(1L)});
		empresaManager.expects(once()).method("findToList").will(returnValue(colecao));
		assertEquals(1, rHServiceManager.getEmpresas().length);
	}
	public void testGetCidades()
	{
		Collection<Cidade> colecao = Arrays.asList(new Cidade[]{CidadeFactory.getEntity(1L)});
		cidadeManager.expects(once()).method("findAllByUf").will(returnValue(colecao));
		assertEquals(1, rHServiceManager.getCidades("CE").length);
	}
	public void testGetCargos()
	{
		Collection<Cargo> colecao = Arrays.asList(new Cargo[]{CargoFactory.getEntity(1L)});
		cargoManager.expects(once()).method("findAllSelect").will(returnValue(colecao));
		assertEquals(1, rHServiceManager.getCargos(1L).length);
	}
	
	public void testCadastrarCandidatos()  throws Exception
	{
		TCandidato cand = new TCandidato();
		cand.setEmpresaId(1L);
		cand.setNome("Candidato");
		cand.setNascimento(DateUtil.montaDataByString("01/01/1984"));
		cand.setSexo("M");
		cand.setCpf("123.456.789-88");
		cand.setColocacao("Emprego");
		cand.setCidadeId(1L);

		Cidade cidade = CidadeFactory.getEntity(1L);
		cidade.setUf(new Estado());

		empresaManager.expects(once()).method("findById").will(returnValue(EmpresaFactory.getEmpresa(1L)));
//		candidatoManager.expects(once()).method("findByCPF").will(returnValue(null));
		cidadeManager.expects(once()).method("findById").will(returnValue(cidade));

		cargoManager.expects(once()).method("populaCargos");

		candidatoManager.expects(once()).method("save");

		assertTrue(rHServiceManager.cadastrarCandidato(cand));

		// Exception: candidato já cadastrado
//		empresaManager.expects(once()).method("findById").will(returnValue(EmpresaFactory.getEmpresa(1L)));
//		Candidato candTmp = CandidatoFactory.getCandidato();
//		candTmp.setId(1L);
//		candidatoManager.expects(once()).method("findByCPF").will(returnValue(candTmp));
//
//		Exception exception = null;
//		try
//		{
//			rHServiceManager.cadastrarCandidato(cand);
//		}
//		catch (Exception e)
//		{
//			exception = e;
//		}
//		assertNotNull(exception);
	}
	
	String TODOS_OS_NOMES_ENCONTRADOS = "JOAO BATISTA (CPF 630.673.232-12) - VEGA\n" +
			"JOAO BATISTA CUFOSFOS (CPF 630.673.232-13) - FORTES\n" +
			"JOAO BATISTA - VEGA\n";
	
	Collection<Candidato> candidatosHomonimos = new ArrayList<Candidato>();
	
	public void testGetNomesHomologos() {
		// dado que
		dadoQueExisteHomonimosPara("joao batista");
		
		// quando
		String nomes = rHServiceManager.getNomesHomologos("joao batista");
		
		// entao
		String[] cadaNome = nomes.split("\n");
		
		assertEquals("Todos os nomes encontrados", TODOS_OS_NOMES_ENCONTRADOS, nomes);
		assertEquals("1o nome da lista", "JOAO BATISTA (CPF 630.673.232-12) - VEGA", cadaNome[0]);
		assertEquals("2o nome da lista", "JOAO BATISTA CUFOSFOS (CPF 630.673.232-13) - FORTES", cadaNome[1]);
		assertEquals("3o nome da lista", "JOAO BATISTA - VEGA", cadaNome[2]);
	}

	private void dadoQueExisteHomonimosPara(String nome) {
		
		dadoQueExisteCandidatoHomonimoCom("JOAO BATISTA", "630.673.232-12", "VEGA");
		dadoQueExisteCandidatoHomonimoCom("JOAO BATISTA CUFOSFOS", "630.673.232-13", "FORTES");
		dadoQueExisteCandidatoHomonimoCom("JOAO BATISTA", "", "VEGA");
		
		candidatoManager.expects(once())
			.method("getCandidatosByNome")
			.with(eq(nome))
			.will(returnValue(candidatosHomonimos));
	}

	private void dadoQueExisteCandidatoHomonimoCom(String nome, String cpf, String empresa) {
		Candidato candidato = new Candidato();
		candidato.setNome(nome);
		candidato.setCpf(cpf);
		candidato.setEmpresaNome(empresa);
		
		candidatosHomonimos.add(candidato);
	}

	public void testCancelarSituacao()
	{
		TSituacao situacao = new TSituacao();
		String mensagem = "";
		historicoColaboradorManager.expects(once()).method("cancelarSituacao");
		assertTrue(rHServiceManager.cancelarSituacao(situacao, mensagem));
	}

	public void testCancelarSituacaoException()
	{
		TSituacao situacao = new TSituacao();
		String mensagem = "";
		historicoColaboradorManager.expects(once()).method("cancelarSituacao").will(throwException(new Exception()));
		assertFalse(rHServiceManager.cancelarSituacao(situacao, mensagem));
	}
}

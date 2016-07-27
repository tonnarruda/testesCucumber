package com.fortes.rh.test.business.cargosalario;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManagerImpl;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.cargosalario.ReajusteColaboradorDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.relatorio.Cabecalho;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class ReajusteColaboradorManagerTest extends MockObjectTestCase
{
	ReajusteColaboradorManagerImpl reajusteColaboradorManager = new ReajusteColaboradorManagerImpl();
	Mock reajusteColaboradorDao = null;
	Mock transactionManager = null;
	Mock areaOrganizacionalManager = null;
	Mock parametrosDoSistemaManager;
	Mock indiceManager;
	Mock faixaSalarialManager;
	Mock tabelaReajusteColaboradorManager;
	Mock historicoColaboradorManager;
	Mock gerenciadorComunicacaoManager;
	Mock candidatoSolicitacaoManager;
	
	protected void setUp() throws Exception
	{
		reajusteColaboradorDao = new Mock(ReajusteColaboradorDao.class);
		reajusteColaboradorManager.setDao((ReajusteColaboradorDao) reajusteColaboradorDao.proxy());

		transactionManager = new Mock(PlatformTransactionManager.class);
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);

		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		MockSpringUtil.mocks.put("parametrosDoSistemaManager", parametrosDoSistemaManager);

		reajusteColaboradorManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
		reajusteColaboradorManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

		indiceManager = new Mock(IndiceManager.class);
		reajusteColaboradorManager.setIndiceManager((IndiceManager) indiceManager.proxy());

		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		reajusteColaboradorManager.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());
		
		gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class);
		reajusteColaboradorManager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());
		
		tabelaReajusteColaboradorManager = mock(TabelaReajusteColaboradorManager.class);
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
	}
	
	@Override
	protected void tearDown() throws Exception 
	{
		Mockit.restoreAllOriginalDefinitions();
	}

	@SuppressWarnings("unchecked")
	public void testFindByGruposAreas()
	{
		HashMap parametros = new HashMap();
		parametros.put("filtrarPor", 1);
		parametros.put("areas", new ArrayList<String>());
		parametros.put("grupos", new ArrayList<String>());
		parametros.put("tabela", 1L);


		ReajusteColaborador reajusteColaborador = new ReajusteColaborador();
		reajusteColaborador.setId(1L);

		Collection<ReajusteColaborador> colRetorno = new ArrayList<ReajusteColaborador>();
		colRetorno.add(reajusteColaborador);

		reajusteColaboradorDao.expects(once()).method("findByIdEstabelecimentoAreaGrupo").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(colRetorno));

		Collection<ReajusteColaborador> retorno = reajusteColaboradorManager.findByGruposAreas(parametros);

		assertEquals(1, retorno.size());
	}
	
	public void testInsertSolicitacaoReajuste()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Colaborador 1");
		
		reajusteColaboradorDao.expects(once()).method("save");
		gerenciadorComunicacaoManager.expects(once()).method("enviaAvisoAoCadastrarSolicitacaoRealinhamentoColaborador").with(eq(empresa.getId()), eq(colaborador), ANYTHING);
		
		Exception exception = null;
		
		try {
			reajusteColaboradorManager.insertSolicitacaoReajuste(getReajusteColaborador(), empresa.getId(), colaborador);
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testValidaSolicitacaoReajusteSucesso()
	{
		ReajusteColaborador reajusteColaborador = getReajusteColaborador();
		reajusteColaborador.setAreaOrganizacionalProposta(AreaOrganizacionalFactory.getEntity(10L));
		
		Exception exception = new Exception();
		exception = null;
		
		areaOrganizacionalManager.expects(once()).method("verificaMaternidade").will(returnValue(false));
		MockSpringUtil.mocks.put("tabelaReajusteColaboradorManager", tabelaReajusteColaboradorManager);
		tabelaReajusteColaboradorManager.expects(once()).method("findByIdProjection").will(returnValue(new TabelaReajusteColaborador()));
		MockSpringUtil.mocks.put("historicoColaboradorManager", historicoColaboradorManager);
		historicoColaboradorManager.expects(once()).method("verifyExists").will(returnValue(false));
		reajusteColaboradorDao.expects(once()).method("verifyExists").will(returnValue(false));
		
		try
		{
			reajusteColaboradorManager.validaSolicitacaoReajuste(reajusteColaborador);
		}
		catch (Exception e) 
		{
			exception = e;
		}
		
		assertNull(exception);
	}

	public void testValidaSolicitacaoReajusteColaboradorJaCadastradoNaTabelaReajuste()
	{
		ReajusteColaborador reajusteColaborador = getReajusteColaborador();
		reajusteColaborador.setAreaOrganizacionalProposta(AreaOrganizacionalFactory.getEntity(10L));

		Exception exception = new Exception();
		exception = null;
		
		areaOrganizacionalManager.expects(once()).method("verificaMaternidade").will(returnValue(false));
		MockSpringUtil.mocks.put("tabelaReajusteColaboradorManager", tabelaReajusteColaboradorManager);
		tabelaReajusteColaboradorManager.expects(once()).method("findByIdProjection").will(returnValue(new TabelaReajusteColaborador()));
		MockSpringUtil.mocks.put("historicoColaboradorManager", historicoColaboradorManager);
		historicoColaboradorManager.expects(once()).method("verifyExists").will(returnValue(true));
//		reajusteColaboradorDao.expects(once()).method("verifyExists").will(returnValue(true));

		try
		{
			reajusteColaboradorManager.validaSolicitacaoReajuste(reajusteColaborador);
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage());
			exception = e;
		}
		
		assertNotNull(exception);
		assertEquals("nome colaborador já possui um histórico na mesma data do planejamento de realinhamento.",exception.getMessage());
	}
	
	public void testInsertSolicitacaoReajusteComSalarioTipoValorSemInformarSalarioProposto()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Colaborador 1");
		
		ReajusteColaborador reajusteColaborador = getReajusteColaborador();
		
		FaixaSalarial faixaSalarialProposta = FaixaSalarialFactory.getEntity(1L);
		reajusteColaborador.setFaixaSalarialProposta(faixaSalarialProposta);

		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.VALOR);
		reajusteColaborador.setSalarioProposto(null);

		Exception exception = null;

		try
		{
			reajusteColaboradorManager.insertSolicitacaoReajuste(reajusteColaborador, empresa.getId(), colaborador);
		}
		catch (Exception e) {
			exception = e;
		}

		assertNull("No tipo 'Valor' não se registra o Índice.", reajusteColaborador.getIndiceProposto());
		assertNotNull("Está sem valor no salário e por isso não podia ter sido gravado.", exception);
	}

	public void testInsertSolicitacaoReajusteComSalarioTipoIndiceSemIndiceInformado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Colaborador 1");
		
		FaixaSalarial faixaSalarialProposta = FaixaSalarialFactory.getEntity(1L);

		ReajusteColaborador reajusteColaborador = getReajusteColaborador();
		reajusteColaborador.setFaixaSalarialProposta(faixaSalarialProposta);

		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.INDICE);
		reajusteColaborador.setIndiceProposto(null);


		Exception exception = null;

		try
		{
			reajusteColaboradorManager.insertSolicitacaoReajuste(reajusteColaborador, empresa.getId(), colaborador);
		}
		catch (Exception e) {
			exception = e;
		}

		assertNotNull("O Índice não foi informado.", exception);
	}

	public void testInsertSolicitacaoReajusteSemTipoSalario()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Colaborador 1");
		
		FaixaSalarial faixaSalarialProposta = FaixaSalarialFactory.getEntity(1L);

		ReajusteColaborador reajusteColaborador = getReajusteColaborador();
		reajusteColaborador.setFaixaSalarialProposta(faixaSalarialProposta);

		reajusteColaborador.setTipoSalarioProposto(-1);

		Exception exception = null;

		try
		{
			reajusteColaboradorManager.insertSolicitacaoReajuste(reajusteColaborador, empresa.getId(), colaborador);
		}
		catch (Exception e) {
			exception = e;
		}

		assertNotNull("Sem Tipo de Salario não poderia ser cadastrado.",exception);
	}

	public void testAplicarDissidio()
	{
		//Tabela onde o dissidio será aplicado
		TabelaReajusteColaborador tabelaReajusteColaborador1 = new TabelaReajusteColaborador();
		tabelaReajusteColaborador1.setId(1L);

		//cria historico para colaborador1
		Colaborador colaborador1 = new Colaborador();
		colaborador1.setId(1L);
		AreaOrganizacional areaOrganizacional1 = new AreaOrganizacional();
		areaOrganizacional1.setId(1L);
		Funcao funcao1 = new Funcao();
		funcao1.setId(1L);
		Ambiente ambiente1 = new Ambiente();
		ambiente1.setId(1L);
		Estabelecimento estabelecimento1 = new Estabelecimento();
		estabelecimento1.setId(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador1 = new HistoricoColaborador();
		historicoColaborador1.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador1.setSalario(200.0);
		historicoColaborador1.setFaixaSalarial(faixaSalarial);
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional1);
		historicoColaborador1.setFuncao(funcao1);
		historicoColaborador1.setAmbiente(ambiente1);
		historicoColaborador1.setEstabelecimento(estabelecimento1);

		//cria historico para colaborador2
		Colaborador colaborador2 = new Colaborador();
		colaborador2.setId(2L);
		AreaOrganizacional areaOrganizacional2 = new AreaOrganizacional();
		areaOrganizacional2.setId(2L);
		Funcao funcao2 = new Funcao();
		funcao2.setId(2L);
		Ambiente ambiente2 = new Ambiente();
		ambiente2.setId(2L);
		Estabelecimento estabelecimento2 = new Estabelecimento();
		estabelecimento2.setId(2L);

		HistoricoColaborador historicoColaborador2 = new HistoricoColaborador();
		historicoColaborador2.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador2.setSalario(300.0);
		historicoColaborador2.setFaixaSalarial(faixaSalarial);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional2);
		historicoColaborador2.setFuncao(funcao2);
		historicoColaborador2.setAmbiente(ambiente2);
		historicoColaborador2.setEstabelecimento(estabelecimento2);

		//monta coleção onde será aplicado e salvo o dissidio na tabela criada acima
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador1);
		historicoColaboradors.add(historicoColaborador2);

		try
		{
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
			reajusteColaboradorDao.expects(once()).method("deleteByColaboradoresTabelaReajuste").with(ANYTHING, eq(tabelaReajusteColaborador1.getId()));
			reajusteColaboradorDao.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(new ReajusteColaborador()));
			transactionManager.expects(once()).method("commit").with(ANYTHING);

			//DissidioPor   1 = Porcentagem (%) 2 = Valor (R$)
			Collection<ReajusteColaborador> reajustes = reajusteColaboradorManager.aplicarDissidio(historicoColaboradors, tabelaReajusteColaborador1, '2', 200.0);

			assertEquals(2, reajustes.size());

			for (ReajusteColaborador reajusteColaborador : reajustes)
			{
				if(reajusteColaborador.getColaborador().equals(colaborador1))
				{
					assertEquals(ambiente1, reajusteColaborador.getAmbienteAtual());
					assertEquals(ambiente1, reajusteColaborador.getAmbienteProposto());
					assertEquals(areaOrganizacional1, reajusteColaborador.getAreaOrganizacionalAtual());
					assertEquals(areaOrganizacional1, reajusteColaborador.getAreaOrganizacionalProposta());
					assertEquals(colaborador1, reajusteColaborador.getColaborador());
					assertEquals(estabelecimento1, reajusteColaborador.getEstabelecimentoAtual());
					assertEquals(estabelecimento1, reajusteColaborador.getEstabelecimentoProposto());
					assertEquals(funcao1, reajusteColaborador.getFuncaoAtual());
					assertEquals(funcao1, reajusteColaborador.getFuncaoProposta());
					assertEquals(faixaSalarial, reajusteColaborador.getFaixaSalarialAtual());
					assertEquals(faixaSalarial, reajusteColaborador.getFaixaSalarialProposta());
					assertEquals(TipoAplicacaoIndice.VALOR, reajusteColaborador.getTipoSalarioAtual());
					assertEquals(200.0, reajusteColaborador.getSalarioAtual());
					assertEquals(400.0, reajusteColaborador.getSalarioProposto());
					assertEquals(tabelaReajusteColaborador1, reajusteColaborador.getTabelaReajusteColaborador());
				}
				else
				{
					assertEquals(ambiente2, reajusteColaborador.getAmbienteAtual());
					assertEquals(ambiente2, reajusteColaborador.getAmbienteProposto());
					assertEquals(areaOrganizacional2, reajusteColaborador.getAreaOrganizacionalAtual());
					assertEquals(areaOrganizacional2, reajusteColaborador.getAreaOrganizacionalProposta());
					assertEquals(colaborador2, reajusteColaborador.getColaborador());
					assertEquals(estabelecimento2, reajusteColaborador.getEstabelecimentoAtual());
					assertEquals(estabelecimento2, reajusteColaborador.getEstabelecimentoProposto());
					assertEquals(funcao2, reajusteColaborador.getFuncaoAtual());
					assertEquals(funcao2, reajusteColaborador.getFuncaoProposta());
					assertEquals(300.0, reajusteColaborador.getSalarioAtual());
					assertEquals(500.0, reajusteColaborador.getSalarioProposto());
					assertEquals(tabelaReajusteColaborador1, reajusteColaborador.getTabelaReajusteColaborador());
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void testAplicarDissidioException()
	{
		transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();
		Exception exception = null;

		try
		{
			//DissidioPor   1 = Porcentagem (%) 2 = Valor (R$)
			reajusteColaboradorManager.aplicarDissidio(null, tabelaReajusteColaborador, '2', 200.0);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	private ReajusteColaborador getReajusteColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setId(1L);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador.setId(1L);

		FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity(1L);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador.setColaborador(colaborador);
		reajusteColaborador.setFaixaSalarialAtual(faixaSalarialAtual);
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.CARGO);
		return reajusteColaborador;
	}

	public void testFindByIdGroupByArea()
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);

		reajusteColaboradorDao.expects(once()).method("findByIdEstabelecimentoAreaGrupo").with(new Constraint[]{eq(tabelaReajusteColaborador.getId()), ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(ReajusteColaboradorFactory.getCollection()));
		Collection<ReajusteColaborador> retorno = reajusteColaboradorManager.findByIdEstabelecimentoAreaGrupo(tabelaReajusteColaborador.getId(), null, null, null, 1);
		assertEquals(1, retorno.size());
	}

	public void testUpdateReajusteColaboradorComTipoSalarioCargo()
	{
		Funcao funcaoProposta = new Funcao();
		funcaoProposta.setId(-1L);

		Ambiente ambienteProposto = new Ambiente();
		ambienteProposto.setId(-1L);

		ReajusteColaborador reajusteColaborador = initTestUpdateReajusteColaborador();
		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.CARGO);
		reajusteColaborador.setFuncaoProposta(funcaoProposta);
		reajusteColaborador.setAmbienteProposto(ambienteProposto);

		reajusteColaboradorDao.expects(once()).method("update").with(ANYTHING);

		Exception exception = null;
		try
		{
			reajusteColaboradorManager.updateReajusteColaborador(reajusteColaborador);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testUpdateReajusteColaboradorComTipoSalarioIndice()
	{
		Indice indiceProposto = IndiceFactory.getEntity(1L);

		Funcao funcaoProposta = new Funcao();
		funcaoProposta.setId(-1L);

		Ambiente ambienteProposto = new Ambiente();
		ambienteProposto.setId(-1L);

		ReajusteColaborador reajusteColaborador = initTestUpdateReajusteColaborador();
		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.INDICE);
		reajusteColaborador.setIndiceProposto(indiceProposto);
		reajusteColaborador.setQuantidadeIndiceProposto(1.0);
		reajusteColaborador.setFuncaoProposta(funcaoProposta);
		reajusteColaborador.setAmbienteProposto(ambienteProposto);

		reajusteColaboradorDao.expects(once()).method("update").with(ANYTHING);

		Exception exception = null;
		try
		{
			reajusteColaboradorManager.updateReajusteColaborador(reajusteColaborador);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testUpdateReajusteColaboradorComTipoSalariondiceErrado()
	{
		ReajusteColaborador reajusteColaborador = initTestUpdateReajusteColaborador();
		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.INDICE);
		reajusteColaborador.setIndiceProposto(null);

		Exception exception = null;
		try
		{
			reajusteColaboradorManager.updateReajusteColaborador(reajusteColaborador);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testUpdateReajusteColaboradorComTipoSalarioValor()
	{
		Funcao funcaoProposta = new Funcao();
		funcaoProposta.setId(-1L);

		Ambiente ambienteProposto = new Ambiente();
		ambienteProposto.setId(-1L);

		ReajusteColaborador reajusteColaborador = initTestUpdateReajusteColaborador();
		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.VALOR);
		reajusteColaborador.setSalarioProposto(1000D);
		reajusteColaborador.setFuncaoProposta(funcaoProposta);
		reajusteColaborador.setAmbienteProposto(ambienteProposto);

		reajusteColaboradorDao.expects(once()).method("update").with(ANYTHING);

		Exception exception = null;
		try
		{
			reajusteColaboradorManager.updateReajusteColaborador(reajusteColaborador);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}
	public void testUpdateReajusteColaboradorComTipoSalarioValorErrado()
	{
		ReajusteColaborador reajusteColaborador = initTestUpdateReajusteColaborador();
		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.VALOR);
		reajusteColaborador.setSalarioProposto(null);

		Exception exception = null;
		try
		{
			reajusteColaboradorManager.updateReajusteColaborador(reajusteColaborador);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public ReajusteColaborador initTestUpdateReajusteColaborador()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setId(1L);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional.setId(1L);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador.setId(1L);
		reajusteColaborador.setAreaOrganizacionalAtual(areaOrganizacional);
		reajusteColaborador.setProjectionGrupoOcupacionalPropostoId(grupoOcupacional.getId());
		reajusteColaborador.setFaixaSalarialAtual(reajusteColaborador.getFaixaSalarialProposta());

		return reajusteColaborador;
	}

	public void testDeleteByColaboradoresTabelaReajuste()
	{
		reajusteColaboradorDao.expects(once()).method("deleteByColaboradoresTabelaReajuste").isVoid();

		reajusteColaboradorManager.deleteByColaboradoresTabelaReajuste(new Long[]{}, 1L);
	}

	public void testFindByIdProjection()
	{
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);

		reajusteColaboradorDao.expects(once()).method("getSituacaoReajusteColaborador").withAnyArguments().will(returnValue(reajusteColaborador));

		ReajusteColaborador retorno = reajusteColaboradorManager.getSituacaoReajusteColaborador(1L);

		assertNotNull(retorno);
	}

	public void testOrdenaPorEstabelecimentoAreaOrGrupoOcupacionalByEstabelecimentoAndAreaOrganizacional()
	{
		AreaOrganizacional areaOrganizacionalProposta1 = AreaOrganizacionalFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacionalProposta2 = AreaOrganizacionalFactory.getEntity(2L);
		AreaOrganizacional areaOrganizacionalAtual1 = AreaOrganizacionalFactory.getEntity(3L);
		AreaOrganizacional areaOrganizacionalAtual2 = AreaOrganizacionalFactory.getEntity(4L);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(areaOrganizacionalProposta1);
		areaOrganizacionals.add(areaOrganizacionalProposta2);
		areaOrganizacionals.add(areaOrganizacionalAtual1);
		areaOrganizacionals.add(areaOrganizacionalAtual2);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);

		ReajusteColaborador reajusteColaborador1 = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador1.setAreaOrganizacionalProposta(areaOrganizacionalProposta1);
		reajusteColaborador1.setAreaOrganizacionalAtual(areaOrganizacionalAtual1);
		reajusteColaborador1.setEstabelecimentoProposto(estabelecimento);

		ReajusteColaborador reajusteColaborador2 = ReajusteColaboradorFactory.getReajusteColaborador(2L);
		reajusteColaborador2.setAreaOrganizacionalProposta(areaOrganizacionalProposta2);
		reajusteColaborador2.setAreaOrganizacionalAtual(areaOrganizacionalAtual2);
		reajusteColaborador2.setEstabelecimentoProposto(estabelecimento);

		Collection<ReajusteColaborador> reajusteColaboradors = new ArrayList<ReajusteColaborador>();
		reajusteColaboradors.add(reajusteColaborador1);
		reajusteColaboradors.add(reajusteColaborador2);

		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").withAnyArguments().will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").withAnyArguments().will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(eq(areaOrganizacionals),eq(reajusteColaborador1.getAreaOrganizacionalProposta().getId())).will(returnValue(areaOrganizacionalProposta1));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(eq(areaOrganizacionals),eq(reajusteColaborador1.getAreaOrganizacionalAtual().getId())).will(returnValue(areaOrganizacionalAtual1));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(eq(areaOrganizacionals),eq(reajusteColaborador2.getAreaOrganizacionalProposta().getId())).will(returnValue(areaOrganizacionalProposta2));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(eq(areaOrganizacionals),eq(reajusteColaborador2.getAreaOrganizacionalAtual().getId())).will(returnValue(areaOrganizacionalAtual2));

		Exception erro = null;
		try
		{
			Collection<ReajusteColaborador> retorno = reajusteColaboradorManager.ordenaPorEstabelecimentoAreaOrGrupoOcupacional(1L, reajusteColaboradors, "1");

			assertEquals("Ordena por Área Organizacional", reajusteColaboradors.size(), retorno.size());
		}
		catch (Exception e)
		{
			erro = e;
		}

		assertNull(erro);
	}

	public void testOrdenaPorEstabelecimentoAreaOrGrupoOcupacionalByGrupoOcupacional()
	{
		AreaOrganizacional areaOrganizacionalProposta1 = AreaOrganizacionalFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacionalProposta2 = AreaOrganizacionalFactory.getEntity(2L);
		AreaOrganizacional areaOrganizacionalAtual1 = AreaOrganizacionalFactory.getEntity(3L);
		AreaOrganizacional areaOrganizacionalAtual2 = AreaOrganizacionalFactory.getEntity(4L);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(areaOrganizacionalProposta1);
		areaOrganizacionals.add(areaOrganizacionalProposta2);
		areaOrganizacionals.add(areaOrganizacionalAtual1);
		areaOrganizacionals.add(areaOrganizacionalAtual2);
		
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional(1L);

		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setGrupoOcupacional(grupoOcupacional);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);

		ReajusteColaborador reajusteColaborador1 = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador1.setAreaOrganizacionalProposta(areaOrganizacionalProposta1);
		reajusteColaborador1.setAreaOrganizacionalAtual(areaOrganizacionalAtual1);
		reajusteColaborador1.setFaixaSalarialProposta(faixaSalarial);

		ReajusteColaborador reajusteColaborador2 = ReajusteColaboradorFactory.getReajusteColaborador(2L);
		reajusteColaborador2.setAreaOrganizacionalProposta(areaOrganizacionalProposta2);
		reajusteColaborador2.setAreaOrganizacionalAtual(areaOrganizacionalAtual2);
		reajusteColaborador2.setFaixaSalarialProposta(faixaSalarial);

		Collection<ReajusteColaborador> reajusteColaboradors = new ArrayList<ReajusteColaborador>();
		reajusteColaboradors.add(reajusteColaborador1);
		reajusteColaboradors.add(reajusteColaborador2);

		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").withAnyArguments().will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").withAnyArguments().will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(eq(areaOrganizacionals),eq(reajusteColaborador1.getAreaOrganizacionalProposta().getId())).will(returnValue(areaOrganizacionalProposta1));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(eq(areaOrganizacionals),eq(reajusteColaborador1.getAreaOrganizacionalAtual().getId())).will(returnValue(areaOrganizacionalAtual1));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(eq(areaOrganizacionals),eq(reajusteColaborador2.getAreaOrganizacionalProposta().getId())).will(returnValue(areaOrganizacionalProposta2));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(eq(areaOrganizacionals),eq(reajusteColaborador2.getAreaOrganizacionalAtual().getId())).will(returnValue(areaOrganizacionalAtual2));

		Exception erro = null;
		try
		{
			Collection<ReajusteColaborador> retorno = reajusteColaboradorManager.ordenaPorEstabelecimentoAreaOrGrupoOcupacional(1L, reajusteColaboradors, "2");

			assertEquals("Ordena por Grupo Ocupacional", reajusteColaboradors.size(), retorno.size());
		}
		catch (Exception e)
		{
			erro = e;
		}

		assertNull(erro);
	}

	public void testCalculaSalarioPropostoPorIndice()
	{
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
		indiceHistorico.setValor(450.0);

		Indice indice = IndiceFactory.getEntity(1L);
		indice.setIndiceHistoricoAtual(indiceHistorico);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setIndiceProposto(indice);
		reajusteColaborador.setQuantidadeIndiceProposto(2.0);
		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.INDICE);

		indiceManager.expects(once()).method("findHistorico").with(ANYTHING, ANYTHING).will(returnValue(indice));

		assertEquals(900.0, reajusteColaboradorManager.calculaSalarioProposto(reajusteColaborador));
	}

	public void testCalculaSalarioPropostoPorCargo()
	{
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setValor(300.0);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setFaixaSalarialProposta(faixaSalarial);
		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.CARGO);

		faixaSalarialManager.expects(once()).method("findHistorico").with(ANYTHING, ANYTHING).will(returnValue(faixaSalarial));

		assertEquals(300.0, reajusteColaboradorManager.calculaSalarioProposto(reajusteColaborador));
	}

	public void testGetParametrosRelatorio()
	{
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setAppVersao("1.01.1");

		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("Empresa");
		empresa.setLogoUrl("logo");

		parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));

		Map<String, Object> parametros = reajusteColaboradorManager.getParametrosRelatorio("Relatório Teste", empresa, "");

		Cabecalho cabecalho = (Cabecalho) parametros.get("CABECALHO");
		assertEquals("xxx" + File.separatorChar, parametros.get("SUBREPORT_DIR"));
		assertEquals(empresa.getNome(), cabecalho.getNomeEmpresa());
		assertEquals("", cabecalho.getLogoUrl()); //Arquivo nao existe
	}

	public void testUpdateFromHistoricoColaborador()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);

		reajusteColaboradorDao.expects(once()).method("updateFromHistoricoColaborador").with(eq(historicoColaborador));

		Exception exception = null;
		try
		{
			reajusteColaboradorManager.updateFromHistoricoColaborador(historicoColaborador);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}
}

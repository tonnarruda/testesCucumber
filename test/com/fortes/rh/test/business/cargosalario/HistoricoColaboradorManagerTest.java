package com.fortes.rh.test.business.cargosalario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManagerImpl;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.SituacaoColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.dicionario.CodigoGFIP;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockHibernateTemplate;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;
import com.fortes.rh.web.ws.AcPessoalClientTabelaReajusteInterface;

public class HistoricoColaboradorManagerTest extends MockObjectTestCaseManager<HistoricoColaboradorManagerImpl> implements TesteAutomaticoManager
{
	Mock historicoColaboradorDao;
	Mock areaOrganizacionalManager;
	Mock indiceHistoricoManager;
	Mock faixaSalarialHistoricoManager;
	Mock faixaSalarialManager;
	Mock indiceManager;
	Mock transactionManager;
	Mock reajusteColaboradorManager;
	Mock estabelecimentoManager;
	Mock acPessoalClientTabelaReajuste;
	Mock acPessoalClientColaborador;
	Mock colaboradorManager;
	Mock ambienteManager;
	Mock funcaoManager;
	Mock empresaManager;
	Mock gerenciadorComunicacaoManager;
	Mock candidatoSolicitacaoManager;
	Mock solicitacaoManager;
	
	protected void setUp() throws Exception
	{
		manager = new HistoricoColaboradorManagerImpl();
		
        transactionManager = new Mock(PlatformTransactionManager.class);
        manager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        historicoColaboradorDao = new Mock(HistoricoColaboradorDao.class);
		manager.setDao((HistoricoColaboradorDao) historicoColaboradorDao.proxy());

		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		manager.setAreaOrganizacionalManager((AreaOrganizacionalManager)areaOrganizacionalManager.proxy());

		indiceHistoricoManager = new Mock(IndiceHistoricoManager.class);
		manager.setIndiceHistoricoManager((IndiceHistoricoManager)indiceHistoricoManager.proxy());

		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		manager.setFaixaSalarialManager((FaixaSalarialManager)faixaSalarialManager.proxy());

		indiceManager = new Mock(IndiceManager.class);
		manager.setIndiceManager((IndiceManager)indiceManager.proxy());

		faixaSalarialHistoricoManager = new Mock(FaixaSalarialHistoricoManager.class);
		manager.setFaixaSalarialHistoricoManager((FaixaSalarialHistoricoManager)faixaSalarialHistoricoManager.proxy());

		reajusteColaboradorManager = new Mock(ReajusteColaboradorManager.class);
		manager.setReajusteColaboradorManager((ReajusteColaboradorManager)reajusteColaboradorManager.proxy());
		
		candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
		manager.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager)candidatoSolicitacaoManager.proxy());

		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		manager.setEstabelecimentoManager((EstabelecimentoManager)estabelecimentoManager.proxy());

		empresaManager = new Mock(EmpresaManager.class);
		manager.setEmpresaManager((EmpresaManager) empresaManager.proxy());

		acPessoalClientTabelaReajuste = mock(AcPessoalClientTabelaReajusteInterface.class);
		manager.setAcPessoalClientTabelaReajuste((AcPessoalClientTabelaReajusteInterface) acPessoalClientTabelaReajuste.proxy());

		acPessoalClientColaborador = mock(AcPessoalClientColaborador.class);
		manager.setAcPessoalClientColaborador((AcPessoalClientColaborador) acPessoalClientColaborador.proxy());

		gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		manager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());

		colaboradorManager = new Mock(ColaboradorManager.class);
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
		
		funcaoManager = mock(FuncaoManager.class);
		MockSpringUtil.mocks.put("funcaoManager", funcaoManager);
		ambienteManager = mock(AmbienteManager.class);
		MockSpringUtil.mocks.put("ambienteManager", ambienteManager);
		
		solicitacaoManager =  new Mock(SolicitacaoManager.class);
		MockSpringUtil.mocks.put("solicitacaoManager", solicitacaoManager);

		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		Mockit.redefineMethods(HibernateTemplate.class, MockHibernateTemplate.class);
	}

	public void testGetByColaboradorId()
	{
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		Long colaboradorId = 1L;

		historicoColaboradorDao.expects(once()).method("findPromocaoByColaborador").with(ANYTHING).will(returnValue(historicoColaboradors));
		Collection<HistoricoColaborador> retorno = manager.getByColaboradorId(colaboradorId);
		assertNotNull(retorno);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
		super.tearDown();
	}

	public void testFindPromocaoByColaborador()
	{
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		Long colaboradorId = 1L;

		historicoColaboradorDao.expects(once()).method("findPromocaoByColaborador").with(ANYTHING).will(returnValue(historicoColaboradors));
		manager.findPromocaoByColaborador(colaboradorId);
	}

	public void testGetHistoricosAtuaisByEstabelecimentoAreaGrupo()
	{
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();

		Long[] estabelecimentoIds = new Long[]{1L};
		Long[] areaOrganizacionalIds = new Long[]{1L};
		Long[] grupoOcupacionalIds = new Long[]{1L};
		Long empresaId = 1L;
		char filtrarPor = '1';
		Date data = new Date();

		historicoColaboradorDao.expects(once()).method("getHistoricosAtuaisByEstabelecimentoAreaGrupo").with(new Constraint[]{eq(estabelecimentoIds), eq(filtrarPor), eq(areaOrganizacionalIds), eq(grupoOcupacionalIds), eq(empresaId), eq(data)}).will(returnValue(historicoColaboradors));
		manager.getHistoricosAtuaisByEstabelecimentoAreaGrupo(estabelecimentoIds, filtrarPor, areaOrganizacionalIds, grupoOcupacionalIds, empresaId, data);
	}
	
	public void testRelatorioColaboradorCargoComColaboradoresRegistradosNoAC() throws Exception {
		
		HistoricoColaborador hc = dadoUmColaboradorQueTambemEstaRegistradoNoAC();
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(hc);
		
		Date data = new Date();
		Empresa empresa = EmpresaFactory.getEmpresa();
		
		//opcao 0
		historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(data), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoColaboradors));
		assertEquals(1, manager.relatorioColaboradorCargo(data, null, null, 2, '0', null, true, null, null, true, empresa.getId()).size());
	}
	
	private HistoricoColaborador dadoUmColaboradorQueTambemEstaRegistradoNoAC() {
		HistoricoColaborador hc = HistoricoColaboradorFactory.getEntity(1L);
		hc.setColaboradorCodigoAC("123456");
		return hc;
	}

	public void testRelatorioColaboradorCargo() throws Exception
	{
		//Long empresaId, Date dataHistorico, String[] cargosCheck, String[] estabelecimentosCheck, Integer qtdMeses, char opcaoFiltro
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaboradors.add(historicoColaborador);
		
		Date data = new Date();
		Empresa empresa = EmpresaFactory.getEmpresa();

		//opcao 0
		historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(data), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoColaboradors));
		assertEquals(1, manager.relatorioColaboradorCargo(data, null, null, 2, '0', null, true, null, null, false, empresa.getId()).size());
		
		// opcao 1
		historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(data), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoColaboradors));
		assertEquals(1, manager.relatorioColaboradorCargo(data, null, null, 2, '1', null, true, null, null, false, empresa.getId()).size());

		// quantidade de meses null
		historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(data), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoColaboradors));
		assertEquals(1, manager.relatorioColaboradorCargo(data, null, null, null, '1', null, true, null, null, false, empresa.getId()).size());

		// qtd Meses Desatualizacao 10
		historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(data), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoColaboradors));
		assertEquals(1, manager.relatorioColaboradorCargo(data, null, null, null, '1', null, true, 10, null, false, empresa.getId()).size());
	}
	
	public void testRelatorioColaboradorCargo_ComHistoricoColaboradorNulo() throws Exception
	{
		Exception exp = null;
		Date hoje = new Date();
		
		try {
			//Long empresaId, Date dataHistorico, String[] cargosCheck, String[] estabelecimentosCheck, Integer qtdMeses, char opcaoFiltro
			Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
			HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
			historicoColaboradors.add(historicoColaborador);
			
			Date data = new Date();
			Empresa empresa = EmpresaFactory.getEmpresa();
			empresa.setAcIntegra(false);
			historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(hoje), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<HistoricoColaborador>()));
			
			
			Collection<HistoricoColaborador> resultado = manager.relatorioColaboradorCargo(hoje, null, null, 2, '1', null, true, null, null, false, empresa.getId());
		} catch (Exception e) {
			exp = e;
		}
		
		assertNotNull(exp);
	}

	public void testRelatorioColaboradorCargoBuscaRemuneracaoVariavelNoACPessoal() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		Colaborador francisco = ColaboradorFactory.getEntity();
		francisco.setCodigoAC("000001");
		francisco.setEmpresa(empresa);
		HistoricoColaborador historicoColaboradorFrancisco = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaboradorFrancisco.setColaborador(francisco);

		Colaborador fulano = ColaboradorFactory.getEntity();
		fulano.setCodigoAC("000002");
		fulano.setEmpresa(empresa);
		HistoricoColaborador historicoColaboradorFulano = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaboradorFulano.setColaborador(fulano);
		
		Colaborador bruno = ColaboradorFactory.getEntity();
		bruno.setCodigoAC("000003");
		bruno.setEmpresa(empresa);
		HistoricoColaborador historicoColaboradorBruno = HistoricoColaboradorFactory.getEntity(3L);
		historicoColaboradorBruno.setColaborador(bruno);
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaboradorFrancisco);
		historicoColaboradors.add(historicoColaboradorFulano);
		historicoColaboradors.add(historicoColaboradorBruno);

		TRemuneracaoVariavel tRemuneracaoVariavel1 = new TRemuneracaoVariavel();
		tRemuneracaoVariavel1.setCodigoEmpregado("000001");
		tRemuneracaoVariavel1.setValor(100.0);

		TRemuneracaoVariavel tremRemuneracaoVariavel2 = new TRemuneracaoVariavel();
		tremRemuneracaoVariavel2.setCodigoEmpregado("000002");
		tremRemuneracaoVariavel2.setValor(300.0);
		
		TRemuneracaoVariavel[] treRemuneracaoVariavels = new TRemuneracaoVariavel[]{tRemuneracaoVariavel1, tremRemuneracaoVariavel2}; 

		Date hoje = new Date();
		historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(hoje), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoColaboradors));
		acPessoalClientColaborador.expects(atLeastOnce()).method("getRemuneracoesVariaveis").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(treRemuneracaoVariavels));
		empresaManager.expects(atLeastOnce()).method("findById").with(ANYTHING).will(returnValue(empresa));
		
		
		Collection<HistoricoColaborador> resultado = manager.relatorioColaboradorCargo(hoje, null, null, 2, '1', null, true, null, null, true, empresa.getId());

		assertEquals(3, resultado.size());
		
		HistoricoColaborador colab1 = (HistoricoColaborador) resultado.toArray()[0];
		HistoricoColaborador colab2 = (HistoricoColaborador) resultado.toArray()[1];
		
		assertEquals(100.0, colab1.getSalarioVariavel());
		assertEquals(300.0, colab2.getSalarioVariavel());
	}
	
	public void testRelatorioColaboradorCargo_ComRemuneracaoVariavelNoAC() throws Exception
	{
		Exception exp = null;
		
		Date hoje = DateUtil.criarDataMesAno(6, 1, 2012);
		
		try{
			Empresa empresa = EmpresaFactory.getEmpresa();
			historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(hoje), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<HistoricoColaborador>()));
			manager.relatorioColaboradorCargo(hoje, null, null, 2, '1', null, true, null, null, true, empresa.getId());
		
		}catch (Exception e) {
			exp = e;
		}
		
		assertNotNull(exp);
	}
	
	
	
	
	public void testGetHistoricoAtual()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		Long colaboradorId = 1L;

		historicoColaboradorDao.expects(once()).method("getHistoricoAtual").with(ANYTHING, ANYTHING).will(returnValue(historicoColaborador));
		manager.getHistoricoAtual(colaboradorId);
	}
	
	public void testGetValorTotalFolha()
	{
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador1.setSalario(200.0);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador2.setSalario(400.0);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3.setTipoSalario(TipoAplicacaoIndice.CARGO);
		
		FaixaSalarialHistorico faixaSalarialHistorico3 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico3.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico3.setValor(300.0);
		FaixaSalarial faixaSalarial3 = FaixaSalarialFactory.getEntity();
		faixaSalarial3.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico3);
		historicoColaborador3.setFaixaSalarial(faixaSalarial3);
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador1);
		historicoColaboradors.add(historicoColaborador2);
		historicoColaboradors.add(historicoColaborador3);
		
		historicoColaboradorDao.expects(once()).method("findHistoricoAdmitidos").with(ANYTHING, ANYTHING).will(returnValue(historicoColaboradors));
		assertEquals(900.0, manager.getValorTotalFolha(null, new Date()));
	}
	
	public void testFindSemDissidioByDataPercentual()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Date dataIni = DateUtil.criarDataMesAno(1, 2, 2011);
		Date dataFim = DateUtil.criarDataMesAno(1, 3, 2011);
		Double percentualDissidio = 5.0;

		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.setEmpresa(empresa);
		
		AreaOrganizacional areaOrganizacionalC1 = AreaOrganizacionalFactory.getEntity(3L);
		
		Estabelecimento estabelecimentoC1 = EstabelecimentoFactory.getEntity(5L);
		
		Cargo cargoc1 = CargoFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistoricoC1 = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistoricoC1.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistoricoC1.setValor(400.0);

		FaixaSalarial faixaSalarialC1 = FaixaSalarialFactory.getEntity(1L);
		faixaSalarialC1.setCargo(cargoc1);
		faixaSalarialC1.setFaixaSalarialHistoricoAtual(faixaSalarialHistoricoC1);
		
		HistoricoColaborador historico1Colaborador1 = HistoricoColaboradorFactory.getEntity();
		historico1Colaborador1.setData(DateUtil.criarDataMesAno(1, 3, 2011));
		historico1Colaborador1.setColaborador(colaborador1);
		historico1Colaborador1.setFaixaSalarial(faixaSalarialC1);
		historico1Colaborador1.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historico1Colaborador1.setSalario(200.0);
		historico1Colaborador1.setAreaOrganizacional(areaOrganizacionalC1);
		historico1Colaborador1.setEstabelecimento(estabelecimentoC1);
		
		HistoricoColaborador historico2Colaborador1 = HistoricoColaboradorFactory.getEntity();
		historico2Colaborador1.setData(DateUtil.criarDataMesAno(1, 1, 2011));
		historico2Colaborador1.setColaborador(colaborador1);
		historico2Colaborador1.setFaixaSalarial(faixaSalarialC1);
		historico2Colaborador1.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historico2Colaborador1.setSalario(400.0);
		historico2Colaborador1.setAreaOrganizacional(areaOrganizacionalC1);
		historico2Colaborador1.setEstabelecimento(estabelecimentoC1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setEmpresa(empresa);
		
		AreaOrganizacional areaOrganizacionalC2 = AreaOrganizacionalFactory.getEntity(4L);
		
		Estabelecimento estabelecimentoC2 = EstabelecimentoFactory.getEntity(6L);
		
		Cargo cargoC2 = CargoFactory.getEntity(2L);
		
		FaixaSalarialHistorico faixaSalarialHistoricoC2 = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistoricoC2.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistoricoC2.setValor(400.0);
		
		FaixaSalarial faixaSalarialC2 = FaixaSalarialFactory.getEntity(2L);
		faixaSalarialC2.setCargo(cargoC2);
		faixaSalarialC2.setFaixaSalarialHistoricoAtual(faixaSalarialHistoricoC2);
		
		HistoricoColaborador historico1Colaborador2 = HistoricoColaboradorFactory.getEntity();
		historico1Colaborador2.setData(DateUtil.criarDataMesAno(1, 1, 2011));
		historico1Colaborador2.setColaborador(colaborador2);
		historico1Colaborador2.setFaixaSalarial(faixaSalarialC2);
		historico1Colaborador2.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historico1Colaborador2.setSalario(300.0);
		historico1Colaborador2.setAreaOrganizacional(areaOrganizacionalC2);
		historico1Colaborador2.setEstabelecimento(estabelecimentoC2);
		
		HistoricoColaborador historico2Colaborador2 = HistoricoColaboradorFactory.getEntity();
		historico2Colaborador2.setData(DateUtil.criarDataMesAno(1, 4, 2011));
		historico2Colaborador2.setColaborador(colaborador2);
		historico2Colaborador2.setFaixaSalarial(faixaSalarialC2);
		historico2Colaborador2.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historico2Colaborador2.setSalario(400.0);
		historico2Colaborador2.setAreaOrganizacional(areaOrganizacionalC2);
		historico2Colaborador2.setEstabelecimento(estabelecimentoC2);
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historico1Colaborador1);
		historicoColaboradors.add(historico2Colaborador1);
		historicoColaboradors.add(historico1Colaborador2);
		historicoColaboradors.add(historico2Colaborador2);
		
		historicoColaboradorDao.expects(atLeastOnce()).method("findSemDissidioByDataPercentual").with(eq(dataIni), eq(dataFim), eq(percentualDissidio), eq(empresa.getId())).will(returnValue(historicoColaboradors));
		
		String[] cargosIds = new String[] {"1","2"};
		String[] areasIds = new String[] {"3","4"};
		String[] estabelecimentosIds = new String[] {"5","6"};
		
		Collection<HistoricoColaborador> resultado = manager.findSemDissidioByDataPercentual(dataIni, dataFim, percentualDissidio, empresa.getId(), cargosIds, areasIds, estabelecimentosIds);
		assertEquals("Todos hist. colaboradores", 4, resultado.size());

		cargosIds = new String[] {"1"};
		areasIds = new String[] {"3","4"};
		estabelecimentosIds = new String[] {"5","6"};
		
		resultado = manager.findSemDissidioByDataPercentual(dataIni, dataFim, percentualDissidio, empresa.getId(), cargosIds, areasIds, estabelecimentosIds);
		assertEquals("Sem cargo ID=2", historico1Colaborador1.getFaixaSalarial().getCargo().getId() , ((HistoricoColaborador) resultado.toArray()[0]).getFaixaSalarial().getCargo().getId());

		cargosIds = new String[] {"1","2"};
		areasIds = new String[] {"4"};
		estabelecimentosIds = new String[] {"5","6"};
		
		resultado = manager.findSemDissidioByDataPercentual(dataIni, dataFim, percentualDissidio, empresa.getId(), cargosIds, areasIds, estabelecimentosIds);
		assertEquals("Sem área ID=3", historico1Colaborador2.getAreaOrganizacional().getId() , ((HistoricoColaborador) resultado.toArray()[0]).getAreaOrganizacional().getId());
		
		cargosIds = new String[] {"1","2"};
		areasIds = new String[] {"3","4"};
		estabelecimentosIds = new String[] {"5"};
		
		resultado = manager.findSemDissidioByDataPercentual(dataIni, dataFim, percentualDissidio, empresa.getId(), cargosIds, areasIds, estabelecimentosIds);
		assertEquals("Sem estabelecimento ID=6", historico1Colaborador1.getEstabelecimento().getId() , ((HistoricoColaborador) resultado.toArray()[0]).getEstabelecimento().getId());
		

	}

	public void testFindByCargosIdsPaginado()
	{
		Long[] cargosIds = new Long[2];
		cargosIds[0] = 1L;
		cargosIds[1] = 2L;

		Colaborador c1 = ColaboradorFactory.getEntity();
		c1.setId(1L);
		Colaborador c2 = ColaboradorFactory.getEntity();
		c2.setId(2L);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(c1);
		hc1.setCargoId(1L);
		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(c2);
		hc2.setCargoId(2L);

		Collection<HistoricoColaborador> historicos = new ArrayList<HistoricoColaborador>();
		historicos.add(hc1);
		historicos.add(hc2);

		historicoColaboradorDao.expects(once()).method("findByCargosIds").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(historicos));

		assertEquals(2, manager.findByCargosIds(0,0,cargosIds,null, 1L).size());
	}

	public void testFindByGrupoOcupacionalIdsPaginado()
	{
		Long[] grupoOcupacionalIds_ = new Long[2];
		grupoOcupacionalIds_[0] = 1L;
		grupoOcupacionalIds_[1] = 2L;

		Colaborador c1 = ColaboradorFactory.getEntity();
		c1.setId(1L);
		Colaborador c2 = ColaboradorFactory.getEntity();
		c2.setId(2L);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(c1);
		hc1.setGrupoOcupacionalId(1L);
		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(c2);
		hc2.setGrupoOcupacionalId(2L);

		Collection<HistoricoColaborador> historicos = new ArrayList<HistoricoColaborador>();
		historicos.add(hc1);
		historicos.add(hc2);

		historicoColaboradorDao.expects(once()).method("findByGrupoOcupacionalIds").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(historicos));

		assertEquals(2, manager.findByGrupoOcupacionalIds(0,0,grupoOcupacionalIds_,1L).size());
	}
	
//	public void testGetPromocoes()
//	{
//		Collection<SituacaoColaborador> situacoes = new ArrayList<SituacaoColaborador>();
//		
//		Estabelecimento parajana = EstabelecimentoFactory.getEntity(1L);
//		
//		AreaOrganizacional garagem = AreaOrganizacionalFactory.getEntity(1L);
//		AreaOrganizacional lavajato = AreaOrganizacionalFactory.getEntity(2L);
//		
//		Cargo cobrador = CargoFactory.getEntity(1L);
//		FaixaSalarial faixaUmCobrador = FaixaSalarialFactory.getEntity(1L);
//		FaixaSalarial faixaDoisCobrador = FaixaSalarialFactory.getEntity(2L);
//
//		Cargo motorista = CargoFactory.getEntity(2L);
//		FaixaSalarial faixaAMotorista = FaixaSalarialFactory.getEntity(3L);
//		
//		Colaborador joao = ColaboradorFactory.getEntity(11L);
//		Colaborador maria = ColaboradorFactory.getEntity(22L);
//		
//		SituacaoColaborador joaoCobradorFaixaUm = new SituacaoColaborador(500.0, DateUtil.criarDataMesAno(01, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.CONTRATADO, 1L);
//		SituacaoColaborador joaoCobradorFaixaUmAumentoSalario = new SituacaoColaborador(600.0, DateUtil.criarDataMesAno(01, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.PROMOCAO, 1L);
//		SituacaoColaborador joaoCobradorFaixaDois = new SituacaoColaborador(500.0, DateUtil.criarDataMesAno(01, 04, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaDoisCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.PROMOCAO, 2L);
//		SituacaoColaborador joaoCobradorFaixaDoisAumentoSalario = new SituacaoColaborador(650.0, DateUtil.criarDataMesAno(01, 04, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaDoisCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.PROMOCAO, 2L);
//		SituacaoColaborador joaoCobradorFaixaDoisMudouArea = new SituacaoColaborador(650.0, DateUtil.criarDataMesAno(01, 05, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaDoisCobrador, parajana, lavajato, joao, MotivoHistoricoColaborador.PROMOCAO, 3L);
//		SituacaoColaborador joaoMotoristaFaixaA = new SituacaoColaborador(650.0, DateUtil.criarDataMesAno(06, 06, 2001), TipoAplicacaoIndice.CARGO, motorista, faixaAMotorista, parajana, lavajato, joao, MotivoHistoricoColaborador.PROMOCAO, 4L);
//	
//		SituacaoColaborador mariaCobradorFaixaUm = new SituacaoColaborador(500.0, DateUtil.criarDataMesAno(01, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, maria, MotivoHistoricoColaborador.CONTRATADO, 5L);
//		SituacaoColaborador mariaCobradorFaixaUmAumentoSalario = new SituacaoColaborador(555.0, DateUtil.criarDataMesAno(01, 02, 2000), TipoAplicacaoIndice.CARGO, motorista, faixaAMotorista, parajana, garagem, maria, MotivoHistoricoColaborador.PROMOCAO, 5L);
//		SituacaoColaborador mariaCobradorFaixaUmAumentoSalarioNovamente = new SituacaoColaborador(600.0, DateUtil.criarDataMesAno(05, 05, 2000), TipoAplicacaoIndice.VALOR, motorista, faixaAMotorista, parajana, garagem, maria, MotivoHistoricoColaborador.PROMOCAO, 6L);
//		
//		situacoes.add(joaoCobradorFaixaUm);
//		situacoes.add(joaoCobradorFaixaUmAumentoSalario);
//		situacoes.add(joaoCobradorFaixaDois);
//		situacoes.add(joaoCobradorFaixaDoisAumentoSalario);
//		situacoes.add(joaoCobradorFaixaDoisMudouArea);
//		situacoes.add(joaoMotoristaFaixaA);
//
//		situacoes.add(mariaCobradorFaixaUm);
//		situacoes.add(mariaCobradorFaixaUmAumentoSalario);
//		situacoes.add(mariaCobradorFaixaUmAumentoSalarioNovamente);
//		
//		Empresa empresa = EmpresaFactory.getEmpresa(1L);
//		Long[] empresasPermitidas = new Long[]{empresa.getId()};
//		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
//		
//		historicoColaboradorDao.expects(once()).method("getPromocoes").will(returnValue(situacoes));
//		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(AreaOrganizacional.TODAS), ANYTHING, eq(empresasPermitidas)).will(returnValue(areaOrganizacionals));
//		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areaOrganizacionals)).will(returnValue(areaOrganizacionals));
//		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, eq(garagem.getId())).will(returnValue(garagem));
//		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, eq(lavajato.getId())).will(returnValue(lavajato));
//		
//		Date dataIni = DateUtil.criarDataMesAno(1, 1, 1999);
//		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2300);
//		
//		List<RelatorioPromocoes> promocoes = historicoColaboradorManager.getPromocoes(null, null, dataIni, dataFim, empresa.getId());
//		
//		assertEquals(2, promocoes.size());
//		
//		RelatorioPromocoes promocaoParajanaGaragem = (RelatorioPromocoes) promocoes.get(0);
//		assertEquals(4, promocaoParajanaGaragem.getQtdHorizontal());
//		assertEquals(1, promocaoParajanaGaragem.getQtdVertical());
//		
//		RelatorioPromocoes promocaoParajanaLavajato = (RelatorioPromocoes) promocoes.get(1);
//		assertEquals(0, promocaoParajanaLavajato.getQtdHorizontal());
//		assertEquals(1, promocaoParajanaLavajato.getQtdVertical());
//	}
	
	public void testCountPromocoesMesAno()
	{
		Collection<SituacaoColaborador> situacoes = new ArrayList<SituacaoColaborador>();
		
		Estabelecimento parajana = EstabelecimentoFactory.getEntity(1L);
		
		AreaOrganizacional garagem = AreaOrganizacionalFactory.getEntity(1L);
		AreaOrganizacional lavajato = AreaOrganizacionalFactory.getEntity(2L);
		
		Cargo cobrador = CargoFactory.getEntity(1L);
		FaixaSalarial faixaUmCobrador = FaixaSalarialFactory.getEntity(1L);
		FaixaSalarial faixaDoisCobrador = FaixaSalarialFactory.getEntity(2L);
		
		Cargo motorista = CargoFactory.getEntity(2L);
		FaixaSalarial faixaAMotorista = FaixaSalarialFactory.getEntity(3L);
		
		Colaborador joao = ColaboradorFactory.getEntity(11L);
		Colaborador maria = ColaboradorFactory.getEntity(22L);
		
		SituacaoColaborador joaoCobradorFaixaUm = new SituacaoColaborador(500.0, DateUtil.criarDataMesAno(02, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.CONTRATADO, 1L);
		SituacaoColaborador joaoCobradorFaixaUmAumentoSalario = new SituacaoColaborador(600.0, DateUtil.criarDataMesAno(03, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.PROMOCAO, 1L);
		SituacaoColaborador joaoCobradorFaixaDois = new SituacaoColaborador(500.0, DateUtil.criarDataMesAno(01, 04, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaDoisCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.PROMOCAO, 2L);
		SituacaoColaborador joaoCobradorFaixaDoisAumentoSalario = new SituacaoColaborador(650.0, DateUtil.criarDataMesAno(05, 04, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaDoisCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.PROMOCAO, 2L);
		SituacaoColaborador joaoCobradorFaixaDoisMudouArea = new SituacaoColaborador(650.0, DateUtil.criarDataMesAno(01, 05, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaDoisCobrador, parajana, lavajato, joao, MotivoHistoricoColaborador.PROMOCAO, 3L);
		SituacaoColaborador joaoMotoristaFaixaA = new SituacaoColaborador(650.0, DateUtil.criarDataMesAno(06, 06, 2001), TipoAplicacaoIndice.CARGO, motorista, faixaAMotorista, parajana, lavajato, joao, MotivoHistoricoColaborador.PROMOCAO, 4L);
		
		SituacaoColaborador mariaCobradorFaixaUm = new SituacaoColaborador(500.0, DateUtil.criarDataMesAno(01, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, maria, MotivoHistoricoColaborador.CONTRATADO, 5L);
		SituacaoColaborador mariaCobradorFaixaUmAumentoSalario = new SituacaoColaborador(555.0, DateUtil.criarDataMesAno(9, 02, 2000), TipoAplicacaoIndice.CARGO, motorista, faixaAMotorista, parajana, garagem, maria, MotivoHistoricoColaborador.PROMOCAO, 5L);
		SituacaoColaborador mariaCobradorFaixaUmAumentoSalarioNovamente = new SituacaoColaborador(600.0, DateUtil.criarDataMesAno(05, 05, 2000), TipoAplicacaoIndice.VALOR, motorista, faixaAMotorista, parajana, garagem, maria, MotivoHistoricoColaborador.PROMOCAO, 6L);
		
		situacoes.add(joaoCobradorFaixaUm);
		situacoes.add(joaoCobradorFaixaUmAumentoSalario);
		situacoes.add(joaoCobradorFaixaDois);
		situacoes.add(joaoCobradorFaixaDoisAumentoSalario);
		situacoes.add(joaoCobradorFaixaDoisMudouArea);
		situacoes.add(joaoMotoristaFaixaA);
		
		situacoes.add(mariaCobradorFaixaUm);
		situacoes.add(mariaCobradorFaixaUmAumentoSalario);
		situacoes.add(mariaCobradorFaixaUmAumentoSalarioNovamente);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		historicoColaboradorDao.expects(once()).method("getPromocoes").will(returnValue(situacoes));
		
		Map<Character, Collection<Object[]>> promocoes = manager.montaPromocoesHorizontalEVertical(DateUtil.criarDataMesAno(01, 01, 2000), DateUtil.criarDataMesAno(01, 05, 2000), empresa.getId(), null);
		
		int qtdHorizontal = 0;
		for (Object[] promocaoHorizontal : promocoes.get('H'))
			qtdHorizontal += (Integer)promocaoHorizontal[1];
		
		int qtdVertical = 0;
		for (Object[] promocaoVertical : promocoes.get('V'))
			qtdVertical += (Integer)promocaoVertical[1];
		
		assertEquals(4, qtdHorizontal);
		assertEquals(1, qtdVertical);
	}

	public void testGetColaboradoresSemReajuste()
	{
		Collection<SituacaoColaborador> situacoes = new ArrayList<SituacaoColaborador>();
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Long[] empresasPermitidas = new Long[]{empresa.getId()};
		
		Estabelecimento parajana = EstabelecimentoFactory.getEntity(1L);
		
		AreaOrganizacional garagem = AreaOrganizacionalFactory.getEntity(1L);
		
		Cargo cobrador = CargoFactory.getEntity(1L);
		FaixaSalarial faixaUmCobrador = FaixaSalarialFactory.getEntity(1L);

		Colaborador joao = ColaboradorFactory.getEntity(11L);
		Colaborador pedro = ColaboradorFactory.getEntity(33L);
		Colaborador jose = ColaboradorFactory.getEntity(44L);
		Colaborador marilu = ColaboradorFactory.getEntity(55L);
		Colaborador isabel = ColaboradorFactory.getEntity(66L);
		Colaborador cicero = ColaboradorFactory.getEntity(77L);
		Colaborador carlos = ColaboradorFactory.getEntity(88L);
		Colaborador maria = ColaboradorFactory.getEntity(99L);
		Colaborador tiao = ColaboradorFactory.getEntity(100L);
		Colaborador toin = ColaboradorFactory.getEntity(110L);
		
		SituacaoColaborador joaoUmHistPassado = new SituacaoColaborador(660.0, DateUtil.criarDataMesAno(02, 02, 1999), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.CONTRATADO, 1L);
		
		SituacaoColaborador pedroUmHistNoPeriodo = new SituacaoColaborador(660.0, DateUtil.criarDataMesAno(12, 12, 2001), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, pedro, MotivoHistoricoColaborador.CONTRATADO, 1L);	
		
		SituacaoColaborador joseHistPassado = new SituacaoColaborador(660.0, DateUtil.criarDataMesAno(02, 02, 1999), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, jose, MotivoHistoricoColaborador.CONTRATADO, 1L);	
		SituacaoColaborador joseHistAindaPassado = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, jose, MotivoHistoricoColaborador.PROMOCAO, 1L);	
		SituacaoColaborador joseHistPeriodo = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 03, 2001), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, jose, MotivoHistoricoColaborador.PROMOCAO, 1L);	
		SituacaoColaborador joseHistPeriodoTambem = new SituacaoColaborador(750.0, DateUtil.criarDataMesAno(02, 11, 2001), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, jose, MotivoHistoricoColaborador.PROMOCAO, 1L);	

		SituacaoColaborador mariluHistPassado = new SituacaoColaborador(660.0, DateUtil.criarDataMesAno(02, 02, 1999), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, marilu, MotivoHistoricoColaborador.CONTRATADO, 1L);	
		SituacaoColaborador mariluHistAindaPassado = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, marilu, MotivoHistoricoColaborador.PROMOCAO, 1L);	
		SituacaoColaborador mariluHistPeriodoSemReajuste = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 02, 2001), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, marilu, MotivoHistoricoColaborador.PROMOCAO, 1L);	
		
		SituacaoColaborador isabelHistPassadoSemReajuste = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 02, 1999), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, isabel, MotivoHistoricoColaborador.CONTRATADO, 1L);	
		SituacaoColaborador isabelHistAindaPassadoSemReajuste = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, isabel, MotivoHistoricoColaborador.PROMOCAO, 1L);	
		SituacaoColaborador isabelHistPeriodoSemReajuste = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 02, 2001), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, isabel, MotivoHistoricoColaborador.PROMOCAO, 1L);	

		SituacaoColaborador ciceroHistPassadoSemReajuste = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 02, 1999), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, cicero, MotivoHistoricoColaborador.CONTRATADO, 1L);	
		SituacaoColaborador ciceroHistAindaPassadoSemReajuste = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, cicero, MotivoHistoricoColaborador.PROMOCAO, 1L);	

		SituacaoColaborador mariaHistPassadoSemReajuste = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 02, 1999), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, maria, MotivoHistoricoColaborador.CONTRATADO, 1L);	
		SituacaoColaborador mariaHistAindaPassadoSemReajuste = new SituacaoColaborador(888.0, DateUtil.criarDataMesAno(02, 02, 2001), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, maria, MotivoHistoricoColaborador.PROMOCAO, 1L);	

		SituacaoColaborador carlosHistPassado = new SituacaoColaborador(600.0, DateUtil.criarDataMesAno(02, 02, 1999), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, carlos, MotivoHistoricoColaborador.CONTRATADO, 1L);	
		SituacaoColaborador carlosHistAindaPassadoReajuste = new SituacaoColaborador(700.0, DateUtil.criarDataMesAno(02, 12, 1999), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, carlos, MotivoHistoricoColaborador.PROMOCAO, 1L);	
		
		SituacaoColaborador tiaoHistPassado = new SituacaoColaborador(600.0, DateUtil.criarDataMesAno(02, 02, 1999), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, tiao, MotivoHistoricoColaborador.CONTRATADO, 1L);	
		SituacaoColaborador tiaoHistAindaPassadoReajuste = new SituacaoColaborador(600.0, DateUtil.criarDataMesAno(02, 02, 2002), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, tiao, MotivoHistoricoColaborador.PROMOCAO, 1L);	
		
		SituacaoColaborador toinHistPassado = new SituacaoColaborador(600.0, DateUtil.criarDataMesAno(02, 02, 1999), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, toin, MotivoHistoricoColaborador.CONTRATADO, 1L);	
		SituacaoColaborador toinHistReajusteAnterior = new SituacaoColaborador(777.0, DateUtil.criarDataMesAno(02, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, toin, MotivoHistoricoColaborador.PROMOCAO, 1L);	
		SituacaoColaborador toinHistPeriodo = new SituacaoColaborador(800.0, DateUtil.criarDataMesAno(03, 03, 2001), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, toin, MotivoHistoricoColaborador.PROMOCAO, 1L);	
		SituacaoColaborador toinHistPeriodoTambem = new SituacaoColaborador(800.0, DateUtil.criarDataMesAno(02, 12, 2001), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, toin, MotivoHistoricoColaborador.PROMOCAO, 1L);		
		
		situacoes.add(joaoUmHistPassado);
		situacoes.add(pedroUmHistNoPeriodo);

		situacoes.add(joseHistPassado);
		situacoes.add(joseHistAindaPassado);
		situacoes.add(joseHistPeriodo);
		situacoes.add(joseHistPeriodoTambem);
		
		situacoes.add(mariluHistPassado);
		situacoes.add(mariluHistAindaPassado);
		situacoes.add(mariluHistPeriodoSemReajuste);

		situacoes.add(isabelHistPassadoSemReajuste);
		situacoes.add(isabelHistAindaPassadoSemReajuste);
		situacoes.add(isabelHistPeriodoSemReajuste);

		situacoes.add(ciceroHistPassadoSemReajuste);
		situacoes.add(ciceroHistAindaPassadoSemReajuste);
		
		situacoes.add(carlosHistPassado);
		situacoes.add(carlosHistAindaPassadoReajuste);
		
		situacoes.add(mariaHistPassadoSemReajuste);
		situacoes.add(mariaHistAindaPassadoSemReajuste);
		
		situacoes.add(tiaoHistPassado);
		situacoes.add(tiaoHistAindaPassadoReajuste);

		situacoes.add(toinHistPassado);
		situacoes.add(toinHistReajusteAnterior);
		situacoes.add(toinHistPeriodo);
		situacoes.add(toinHistPeriodoTambem);
		
		historicoColaboradorDao.expects(once()).method("getUltimasPromocoes").will(returnValue(situacoes));
		
		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(AreaOrganizacional.TODAS), ANYTHING, eq(empresasPermitidas)).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areaOrganizacionals)).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, eq(garagem.getId())).will(returnValue(garagem));
		
		List<SituacaoColaborador> retorno = manager.getColaboradoresSemReajuste(null, null, DateUtil.criarDataMesAno(02, 02, 2002), empresa.getId(), 12);
		assertEquals(6, retorno.size());
		assertEquals(joao, retorno.get(0).getColaborador());
		assertEquals(garagem, retorno.get(0).getAreaOrganizacional());
		assertEquals(tiao, retorno.get(5).getColaborador());
		assertEquals(garagem, retorno.get(5).getAreaOrganizacional());
	}

	public void testFindDistinctFuncao() throws Exception
	{
		Funcao f1 = new Funcao();
		f1.setId(1L);
		f1.setNome("F1");

		Funcao f2 = new Funcao();
		f2.setId(2L);
		f2.setNome("F2");

		HistoricoColaborador hc = new HistoricoColaborador();
		hc.setId(1L);
		hc.setFuncao(f1);
		hc.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		hc.setDataProximoHistorico(DateUtil.criarDataMesAno(1, 5, 2007));

		HistoricoColaborador hc2 = new HistoricoColaborador();
		hc2.setId(2L);
		hc2.setFuncao(f1);
		hc2.setData(DateUtil.criarDataMesAno(1, 5, 2007));
		hc2.setDataProximoHistorico(DateUtil.criarDataMesAno(1, 7, 2007));

		HistoricoColaborador hc3 = new HistoricoColaborador();
		hc3.setId(3L);
		hc3.setFuncao(f2);
		hc3.setData(DateUtil.criarDataMesAno(1, 7, 2007));

		Collection<HistoricoColaborador> historicos = new ArrayList<HistoricoColaborador>();
		historicos.add(hc);
		historicos.add(hc2);
		historicos.add(hc3);

		Collection<HistoricoColaborador> colhfRetorno = manager.findDistinctFuncao(historicos);

		assertEquals(2, colhfRetorno.size());
		assertFalse(colhfRetorno.contains(hc2));
		assertEquals(hc.getDataProximoHistorico(), hc3.getData());
	}

	public void testFindByColaboradorData()
	{
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(HistoricoColaboradorFactory.getEntity());

		historicoColaboradorDao.expects(once()).method("findByColaboradorData").with(ANYTHING,ANYTHING).will(returnValue(historicoColaboradors));

		assertEquals(1, manager.findByColaboradorData(1L,new Date()).size());
	}

	public void testGetHistoricoAnterior()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setId(1L);

		historicoColaboradorDao.expects(once()).method("getHistoricoAnterior").with(ANYTHING).will(returnValue(historicoColaborador));

		assertEquals(historicoColaborador.getId(), manager.getHistoricoAnterior(historicoColaborador).getId());
	}

	public void testInserirPeriodos()
	{
		HistoricoColaborador h1 = HistoricoColaboradorFactory.getEntity();
		h1.setData(DateUtil.criarDataMesAno(01, 01, 2001));

		HistoricoColaborador h2 = HistoricoColaboradorFactory.getEntity();
		h2.setData(DateUtil.criarDataMesAno(01, 04, 2001));

		Collection<HistoricoColaborador> historicos = new ArrayList<HistoricoColaborador>();
		historicos.add(h1);
		historicos.add(h2);

		assertEquals(2, manager.inserirPeriodos(historicos).size());
	}

	public void testFindDistinctAmbienteFuncao()
	{
		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
		Ambiente ambiente2 = new Ambiente();
		ambiente2.setId(2L);

		Funcao funcao = new Funcao();
		funcao.setId(1L);
		Funcao funcao2 = new Funcao();
		funcao2.setId(2L);

		HistoricoColaborador h1 = HistoricoColaboradorFactory.getEntity();
		h1.setData(DateUtil.criarDataMesAno(01, 01, 2001));
		h1.setAmbiente(ambiente);
		h1.setFuncao(funcao);

		HistoricoColaborador h2 = HistoricoColaboradorFactory.getEntity();
		h2.setData(DateUtil.criarDataMesAno(01, 04, 2001));
		h2.setAmbiente(ambiente);
		h2.setFuncao(funcao);

		HistoricoColaborador h3 = HistoricoColaboradorFactory.getEntity();
		h3.setData(DateUtil.criarDataMesAno(01, 12, 2001));
		h3.setAmbiente(ambiente2);
		h3.setFuncao(funcao2);

		Collection<HistoricoColaborador> historicos = new ArrayList<HistoricoColaborador>();
		historicos.add(h1);
		historicos.add(h2);
		historicos.add(h3);

		assertEquals(2, manager.findDistinctAmbienteFuncao(historicos).size());
	}

	public void testExisteHistoricoData()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaboradorId(1L);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 02, 2001));
		
		Collection<HistoricoColaborador> historicos = HistoricoColaboradorFactory.getCollection();
		historicos.add(historicoColaborador);

		historicoColaboradorDao.expects(once()).method("findByData").with(ANYTHING, ANYTHING).will(returnValue(historicos));

		assertTrue(manager.existeHistoricoData(historicoColaborador));

		historicoColaboradorDao.expects(once()).method("findByData").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<HistoricoColaborador>()));

		assertFalse(manager.existeHistoricoData(historicoColaborador));	}

	public void testMontaTipoSalarioValor()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		assertEquals("Por Valor", manager.montaTipoSalario(0.0, historicoColaborador.getTipoSalario(), ""));
	}

	public void testMontaTipoSalarioIndice()
	{
		Indice indice = IndiceFactory.getEntity();
		indice.setNome("Salario Mínimo");

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaborador.setIndice(indice);
		assertEquals("Por Índice (2.0 x Salario Mínimo)", manager.montaTipoSalario(2.0, historicoColaborador.getTipoSalario(), historicoColaborador.getIndice().getNome()));
	}

	public void testMontaTipoSalarioCargo()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.CARGO);
		assertEquals("Por Cargo", manager.montaTipoSalario(2.0, historicoColaborador.getTipoSalario(), ""));
	}

	public void testFindByColaboradorProjection()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		historicoColaboradorDao.expects(once()).method("findByColaboradorProjection").with(eq(colaborador.getId()), ANYTHING).will(returnValue(HistoricoColaboradorFactory.getCollection()));

		assertNotNull(manager.findByColaboradorProjection(colaborador.getId(), null));
	}

	public void testFindByColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Long[] empresasPermitidas = new Long[]{empresa.getId()};
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionals.add(areaOrganizacional);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);

		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador);

		historicoColaboradorDao.expects(once()).method("findPromocaoByColaborador").with(eq(colaborador.getId())).will(returnValue(historicoColaboradors));
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(AreaOrganizacional.TODAS), ANYTHING, eq(empresasPermitidas)).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areaOrganizacionals)).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(areaOrganizacional));

		Collection<HistoricoColaborador> retorno = null;

		Exception exception = null;
		try
		{
			retorno = manager.findByColaborador(colaborador.getId(), empresa.getId());
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull("Nulo", exception);
		assertNotNull("Não Nulo", retorno);
	}

	public void testFindHistoricosByTabelaReajuste()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("2fd1gf3");

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("22222");

		Date dataSituacao = new Date();


		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(dataSituacao);
		Collection<HistoricoColaborador> historicos = new ArrayList<HistoricoColaborador>();
		historicos.add(historicoColaborador);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);

		historicoColaboradorDao.expects(once()).method("findHistoricosByTabelaReajuste").with(eq(tabelaReajusteColaborador.getId())).will(returnValue(historicos));

		Collection<TSituacao> situacoes = manager.findHistoricosByTabelaReajuste(tabelaReajusteColaborador.getId(), empresa);

		TSituacao situacao = (TSituacao)situacoes.toArray()[0];

		assertEquals(1, situacoes.size());
		Long situacaoId = situacao.getId().longValue();
		assertEquals(historicoColaborador.getId(), situacaoId);
		assertEquals(DateUtil.formataDiaMesAno(dataSituacao), DateUtil.formataDiaMesAno(situacao.getDataFormatada()));
		assertEquals(colaborador.getCodigoAC(), situacao.getEmpregadoCodigoAC());
		assertEquals(empresa.getCodigoAC(), situacao.getEmpresaCodigoAC());
	}

	public void testAjustaTipoSalario()
	{
		FaixaSalarialHistorico faixaSalarialHistoricoAtual = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistoricoAtual.setTipo(TipoAplicacaoIndice.VALOR);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistoricoAtual);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setId(1L);
		historico.setIndice(null);
		historico.setQuantidadeIndice(0.0);
		historico.setSalario(null);

		historico.setFaixaSalarial(faixaSalarial);

		IndiceHistorico indiceHistoricoAtual = IndiceHistoricoFactory.getEntity(1L);
		indiceHistoricoAtual.setValor(22.0);

		Indice indice = IndiceFactory.getEntity(1L);
		indice.setIndiceHistoricoAtual(indiceHistoricoAtual);
		Double quantidadeIndice = 2.0;
		Double salarioColaborador = 5333.00;

		//Para Cargo
		HistoricoColaborador historicoColaboradorRetorno = manager.ajustaTipoSalario(historico, TipoAplicacaoIndice.CARGO, indice, quantidadeIndice, salarioColaborador);

		assertEquals(TipoAplicacaoIndice.CARGO, historicoColaboradorRetorno.getTipoSalario());
		assertEquals(null, historicoColaboradorRetorno.getIndice());
		assertEquals(null, historicoColaboradorRetorno.getSalarioCalculado());
		assertEquals(0.0, historicoColaboradorRetorno.getQuantidadeIndice());

		//Para Valor
		historicoColaboradorRetorno = manager.ajustaTipoSalario(historico, TipoAplicacaoIndice.VALOR, indice, quantidadeIndice, salarioColaborador);

		assertEquals(TipoAplicacaoIndice.VALOR, historicoColaboradorRetorno.getTipoSalario());
		assertEquals(null, historicoColaboradorRetorno.getIndice());
		assertEquals(salarioColaborador, historicoColaboradorRetorno.getSalarioCalculado());
		assertEquals(0.0, historicoColaboradorRetorno.getQuantidadeIndice());

		//Para Indice
		historicoColaboradorRetorno = manager.ajustaTipoSalario(historico, TipoAplicacaoIndice.INDICE, indice, quantidadeIndice, salarioColaborador);

		assertEquals(TipoAplicacaoIndice.INDICE, historicoColaboradorRetorno.getTipoSalario());
		assertEquals(indice, historicoColaboradorRetorno.getIndice());
		assertEquals(44.0, historicoColaboradorRetorno.getSalarioCalculado());
		assertEquals(quantidadeIndice, historicoColaboradorRetorno.getQuantidadeIndice());
	}

	public void testProgressaoColaboradorValor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		Collection<AreaOrganizacional> areas = AreaOrganizacionalFactory.getCollection();

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2006));
		historicoColaborador1.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador1.setSalario(500.0);

		Collection<HistoricoColaborador> historicoColaboradores = new ArrayList<HistoricoColaborador>();
		historicoColaboradores.add(historicoColaborador1);

		historicoColaboradorDao.expects(once()).method("findPromocaoByColaborador").with(ANYTHING).will(returnValue(historicoColaboradores));
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(AreaOrganizacional.TODAS), ANYTHING, eq(new Long[]{empresa.getId()})).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(areaOrganizacional));

		Collection<HistoricoColaborador> retorno = manager.progressaoColaborador(colaborador.getId(), empresa.getId());
		assertEquals(1, retorno.size());
	}

	public void testProgressaoColaboradorIndice() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		Collection<AreaOrganizacional> areas = AreaOrganizacionalFactory.getCollection();

		Date data = new Date();
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
		indiceHistorico.setData(data);
		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(indiceHistorico);

		Indice indice = IndiceFactory.getEntity(1L);
		indice.setIndiceHistoricoAtual(indiceHistorico);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setIndice(indice);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2006));
		historicoColaborador1.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador1.setIndice(indice);
		historicoColaborador1.setFaixaSalarial(faixaSalarial);
		historicoColaborador1.setSalario(500.0);

		Collection<HistoricoColaborador> historicoColaboradores = new ArrayList<HistoricoColaborador>();
		historicoColaboradores.add(historicoColaborador1);

		historicoColaboradorDao.expects(once()).method("findPromocaoByColaborador").with(ANYTHING).will(returnValue(historicoColaboradores));
		indiceHistoricoManager.expects(once()).method("findByPeriodo").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(indiceHistoricos));
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(AreaOrganizacional.TODAS), ANYTHING, eq(new long[]{empresa.getId()})).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(areaOrganizacional));

		Collection<HistoricoColaborador> retorno = manager.progressaoColaborador(colaborador.getId(), empresa.getId());
		assertEquals(2, retorno.size());
	}

	public void testProgressaoColaboradorCargo() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		Collection<AreaOrganizacional> areas = AreaOrganizacionalFactory.getCollection();

		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
		Indice indice = IndiceFactory.getEntity(1L);
		indice.setIndiceHistoricoAtual(indiceHistorico);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L, indice, StatusRetornoAC.CONFIRMADO, DateUtil.criarDataMesAno(01, 01, 2006));
		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity(2L, null, StatusRetornoAC.CONFIRMADO, DateUtil.criarDataMesAno(01, 01, 2006));

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L, colaborador, DateUtil.criarDataMesAno(01, 01, 2006), TipoAplicacaoIndice.CARGO, areaOrganizacional, indice, faixaSalarial, 500.0);
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(2L, colaborador, DateUtil.criarDataMesAno(01, 01, 2006), TipoAplicacaoIndice.CARGO, areaOrganizacional, indice, faixaSalarial, 600.0);

		Collection<HistoricoColaborador> historicoColaboradores = new ArrayList<HistoricoColaborador>();
		historicoColaboradores.add(historicoColaborador1);
		historicoColaboradores.add(historicoColaborador2);

		Collection<FaixaSalarialHistorico> faixaHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaHistoricos.add(faixaSalarialHistorico);
		faixaHistoricos.add(faixaSalarialHistorico2);

		historicoColaboradorDao.expects(once()).method("findPromocaoByColaborador").with(ANYTHING).will(returnValue(historicoColaboradores));
		faixaSalarialHistoricoManager.expects(atLeastOnce()).method("findByPeriodo").with(ANYTHING, ANYTHING).will(returnValue(faixaHistoricos));
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(AreaOrganizacional.TODAS), ANYTHING, eq(new Long[]{empresa.getId()})).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(areaOrganizacional));

		Collection<HistoricoColaborador> retorno = manager.progressaoColaborador(colaborador.getId(), empresa.getId());
		assertEquals(6, retorno.size());
	}

	public void  testFindByIdProjectionSemReajusteColaborador()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setReajusteColaborador(null);
		
		historicoColaboradorDao.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getId())).will(returnValue(historicoColaborador));
		
		HistoricoColaborador retorno = manager.findByIdProjection(historicoColaborador.getId());
		
		assertNull(retorno.getReajusteColaborador());
	}
	
	public void  testFindByIdProjectionComReajusteColaboradorIdNulo()
	{
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador.setId(null);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);
		
		historicoColaboradorDao.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getId())).will(returnValue(historicoColaborador));
		
		HistoricoColaborador retorno = manager.findByIdProjection(historicoColaborador.getId());
		
		assertNull(retorno.getReajusteColaborador());
	}
	
	public void  testFindByIdProjectionComReajusteColaborador()
	{
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);
		
		historicoColaboradorDao.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getId())).will(returnValue(historicoColaborador));
		
		HistoricoColaborador retorno = manager.findByIdProjection(historicoColaborador.getId());
		
		assertEquals(reajusteColaborador.getId(), retorno.getReajusteColaborador().getId());
	}
	
	public void testFindPendenciasByHistoricoColaborador()
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.setNomeComercial("Miles Davis");
		colaborador1.setDataAdmissao(DateUtil.criarDataMesAno(20,2,2010));

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setCargoNome("Trompetista");
		historicoColaborador1.setCargoNomeMercado("Trompetista");
		historicoColaborador1.setStatus(StatusRetornoAC.AGUARDANDO);

		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setNomeComercial("Bob Marley");
		colaborador2.setCodigoAC("02");

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setStatus(StatusRetornoAC.AGUARDANDO);

		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador1);
		historicoColaboradors.add(historicoColaborador2);

		historicoColaboradorDao.expects(once()).method("findPendenciasByHistoricoColaborador").withAnyArguments().will(returnValue(historicoColaboradors));

		Collection<PendenciaAC> pendenciaACs = manager.findPendenciasByHistoricoColaborador(1L);

		assertEquals(2, pendenciaACs.size());
		PendenciaAC pendenciaAC1 = (PendenciaAC) pendenciaACs.toArray()[0];
		PendenciaAC pendenciaAC2 = (PendenciaAC) pendenciaACs.toArray()[1];
		assertEquals("Contratação", pendenciaAC1.getPendencia());
		assertEquals("Novo Histórico de Colaborador", pendenciaAC2.getPendencia());
	}

	public void testUpdateHistoricoAndReajuste()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("001");

		Indice indice = IndiceFactory.getEntity(1L);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);

		HistoricoColaborador historicoAnterior = HistoricoColaboradorFactory.getEntity();

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setStatus(StatusRetornoAC.CANCELADO);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaborador.setIndice(indice);
		historicoColaborador.setQuantidadeIndice(2.0);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);

		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador);

		historicoColaboradorDao.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getId())).will(returnValue(historicoColaborador));
		acPessoalClientColaborador.expects(once()).method("verificaHistoricoNaFolhaAC").with(eq(historicoColaborador.getId()), eq(colaborador.getCodigoAC()), eq(empresa)).will(returnValue(false));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoCodigoAc").with(eq(historicoColaborador.getEstabelecimento().getId())).will(returnValue(estabelecimento));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalCodigoAc").with(eq(historicoColaborador.getAreaOrganizacional().getId())).will(returnValue(areaOrganizacional));
		faixaSalarialManager.expects(once()).method("findCodigoACById").with(eq(historicoColaborador.getFaixaSalarial().getId())).will(returnValue(faixaSalarial));
		indiceManager.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getIndice().getId())).will(returnValue(indice));
		reajusteColaboradorManager.expects(once()).method("updateFromHistoricoColaborador").with(eq(historicoColaborador));
		historicoColaboradorDao.expects(once()).method("update").with(eq(historicoColaborador));
		historicoColaboradorDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		acPessoalClientTabelaReajuste.expects(once()).method("saveHistoricoColaborador").with(eq(historicoColaboradors), eq(empresa), ANYTHING, eq(false));

		Exception exception = null;
		try
		{
			manager.updateHistorico(historicoColaborador, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}
	
	public void testInsertHistorico()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Indice indice = IndiceFactory.getEntity(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("001");
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setStatus(StatusRetornoAC.CANCELADO);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaborador.setIndice(indice);
		historicoColaborador.setQuantidadeIndice(2.0);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador);
		
		historicoColaboradorDao.expects(once()).method("findColaboradorCodigoAC").with(ANYTHING).will(returnValue(""));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoCodigoAc").with(eq(historicoColaborador.getEstabelecimento().getId())).will(returnValue(estabelecimento));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalCodigoAc").with(eq(historicoColaborador.getAreaOrganizacional().getId())).will(returnValue(areaOrganizacional));
		faixaSalarialManager.expects(once()).method("findCodigoACById").with(eq(historicoColaborador.getFaixaSalarial().getId())).will(returnValue(faixaSalarial));
		indiceManager.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getIndice().getId())).will(returnValue(indice));
		historicoColaboradorDao.expects(once()).method("save").with(eq(historicoColaborador));
		historicoColaboradorDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		acPessoalClientTabelaReajuste.expects(once()).method("saveHistoricoColaborador").with(eq(historicoColaboradors), eq(empresa), ANYTHING, eq(false));
		
		Exception exception = null;
		try
		{
			manager.insertHistorico(historicoColaborador, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNull(exception);
	}

	public void testInsertHistoricoException()
	{
		Empresa empresa = null;

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setSalario(1000.00);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);


		Exception exception = null;
		try
		{
			manager.insertHistorico(historicoColaborador, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	public void testUpdateHistorico()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("001");

		Indice indice = IndiceFactory.getEntity(1L);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();

		HistoricoColaborador historicoAnterior = HistoricoColaboradorFactory.getEntity();

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setStatus(StatusRetornoAC.CANCELADO);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaborador.setIndice(indice);
		historicoColaborador.setQuantidadeIndice(2.0);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);

		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador);

		historicoColaboradorDao.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getId())).will(returnValue(historicoColaborador));
		acPessoalClientColaborador.expects(once()).method("verificaHistoricoNaFolhaAC").with(eq(historicoColaborador.getId()), eq(colaborador.getCodigoAC()), eq(empresa)).will(returnValue(false));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoCodigoAc").with(eq(historicoColaborador.getEstabelecimento().getId())).will(returnValue(estabelecimento));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalCodigoAc").with(eq(historicoColaborador.getAreaOrganizacional().getId())).will(returnValue(areaOrganizacional));
		faixaSalarialManager.expects(once()).method("findCodigoACById").with(eq(historicoColaborador.getFaixaSalarial().getId())).will(returnValue(faixaSalarial));
		indiceManager.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getIndice().getId())).will(returnValue(indice));
		historicoColaboradorDao.expects(once()).method("update").with(eq(historicoColaborador));
		historicoColaboradorDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		acPessoalClientTabelaReajuste.expects(once()).method("saveHistoricoColaborador").with(eq(historicoColaboradors), eq(empresa), ANYTHING, eq(false));

		Exception exception = null;
		try
		{
			manager.updateHistorico(historicoColaborador, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testUpdateHistoricoException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(false);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setSalario(1000.00);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);

		historicoColaboradorDao.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getId())).will(null);

		Exception exception = null;
		try
		{
			manager.updateHistorico(historicoColaborador, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testFindByIdHQL()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);

		historicoColaboradorDao.expects(once()).method("findByIdHQL").with(eq(historicoColaborador.getId())).will(returnValue(historicoColaborador));

		assertEquals(historicoColaborador, manager.findByIdHQL(historicoColaborador.getId()));
	}

	public void testAlterouDadosIntegradoAC()
	{
		HistoricoColaborador historicoColaboradorTela = HistoricoColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaboradorBanco = HistoricoColaboradorFactory.getEntity(2L);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);

		historicoColaboradorTela.setEstabelecimento(estabelecimento);
		historicoColaboradorTela.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorTela.setAmbiente(ambiente);
		historicoColaboradorTela.setFaixaSalarial(faixaSalarial);
		historicoColaboradorTela.setFuncao(funcao);
		historicoColaboradorTela.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaboradorTela.setSalario(2323.0D);
		historicoColaboradorTela.setGfip(CodigoGFIP._00);

		historicoColaboradorBanco.setEstabelecimento(estabelecimento);
		historicoColaboradorBanco.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorBanco.setAmbiente(ambiente);
		historicoColaboradorBanco.setFaixaSalarial(faixaSalarial);
		historicoColaboradorBanco.setFuncao(funcao);
		historicoColaboradorBanco.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaboradorBanco.setSalario(2323.0D);
		historicoColaboradorBanco.setGfip(CodigoGFIP._00);

		assertEquals(false, manager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
	}

	public void testAlterouDadosIntegradoACArea()
	{
		HistoricoColaborador historicoColaboradorTela = HistoricoColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaboradorBanco = HistoricoColaboradorFactory.getEntity(2L);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacionalBanco = AreaOrganizacionalFactory.getEntity(2L);

		historicoColaboradorTela.setEstabelecimento(estabelecimento);
		historicoColaboradorTela.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorTela.setGfip(CodigoGFIP._00);

		historicoColaboradorBanco.setEstabelecimento(estabelecimento);
		historicoColaboradorBanco.setAreaOrganizacional(areaOrganizacionalBanco);
		historicoColaboradorBanco.setGfip(CodigoGFIP._00);

		assertEquals(true, manager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
	}

	public void testAlterouDadosIntegradoACQuantidadeIndice()
	{
		HistoricoColaborador historicoColaboradorTela = HistoricoColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaboradorBanco = HistoricoColaboradorFactory.getEntity(2L);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Indice indice = IndiceFactory.getEntity(1L);
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);

		historicoColaboradorTela.setGfip(CodigoGFIP._00);
		historicoColaboradorTela.setEstabelecimento(estabelecimento);
		historicoColaboradorTela.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorTela.setAmbiente(ambiente);
		historicoColaboradorTela.setFaixaSalarial(faixaSalarial);
		historicoColaboradorTela.setFuncao(funcao);
		historicoColaboradorTela.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaboradorTela.setIndice(indice);
		historicoColaboradorTela.setQuantidadeIndice(3D);

		historicoColaboradorBanco.setGfip(CodigoGFIP._00);
		historicoColaboradorBanco.setEstabelecimento(estabelecimento);
		historicoColaboradorBanco.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorBanco.setAmbiente(ambiente);
		historicoColaboradorBanco.setFaixaSalarial(faixaSalarial);
		historicoColaboradorBanco.setFuncao(funcao);
		historicoColaboradorBanco.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaboradorBanco.setIndice(indice);
		historicoColaboradorBanco.setQuantidadeIndice(2D);

		assertEquals(true, manager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
	}

	public void testAlterouDadosIntegradoACQuantidadeIndiceOk()
	{
		HistoricoColaborador historicoColaboradorTela = HistoricoColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaboradorBanco = HistoricoColaboradorFactory.getEntity(2L);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Indice indice = IndiceFactory.getEntity(1L);
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);

		historicoColaboradorTela.setGfip(CodigoGFIP._00);
		historicoColaboradorTela.setEstabelecimento(estabelecimento);
		historicoColaboradorTela.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorTela.setAmbiente(ambiente);
		historicoColaboradorTela.setFaixaSalarial(faixaSalarial);
		historicoColaboradorTela.setFuncao(funcao);
		historicoColaboradorTela.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaboradorTela.setIndice(indice);
		historicoColaboradorTela.setQuantidadeIndice(3D);
		historicoColaboradorTela.setSalario(null);

		historicoColaboradorBanco.setGfip(CodigoGFIP._00);
		historicoColaboradorBanco.setEstabelecimento(estabelecimento);
		historicoColaboradorBanco.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorBanco.setAmbiente(ambiente);
		historicoColaboradorBanco.setFaixaSalarial(faixaSalarial);
		historicoColaboradorBanco.setFuncao(funcao);
		historicoColaboradorBanco.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaboradorBanco.setIndice(indice);
		historicoColaboradorBanco.setQuantidadeIndice(3D);
		historicoColaboradorBanco.setSalario(null);

		assertEquals(false, manager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
	}

	public void testAlterouDadosIntegradoACValor()
	{
		HistoricoColaborador historicoColaboradorTela = HistoricoColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaboradorBanco = HistoricoColaboradorFactory.getEntity(2L);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);

		historicoColaboradorTela.setGfip(CodigoGFIP._00);
		historicoColaboradorTela.setEstabelecimento(estabelecimento);
		historicoColaboradorTela.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorTela.setAmbiente(ambiente);
		historicoColaboradorTela.setFaixaSalarial(faixaSalarial);
		historicoColaboradorTela.setFuncao(funcao);
		historicoColaboradorTela.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaboradorTela.setSalario(232.0D);

		historicoColaboradorBanco.setGfip(CodigoGFIP._00);
		historicoColaboradorBanco.setEstabelecimento(estabelecimento);
		historicoColaboradorBanco.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorBanco.setAmbiente(ambiente);
		historicoColaboradorBanco.setFaixaSalarial(faixaSalarial);
		historicoColaboradorBanco.setFuncao(funcao);
		historicoColaboradorBanco.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaboradorBanco.setSalario(2323.0D);

		assertEquals(true, manager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
	}

	public void testAlterouDadosIntegradoACCancelado()
	{
		HistoricoColaborador historicoColaboradorTela = HistoricoColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaboradorBanco = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaboradorBanco.setStatus(StatusRetornoAC.CANCELADO);

		assertEquals(true, manager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
	}

	public void testSetStatus()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);

		historicoColaboradorDao.expects(once()).method("setStatus").with(eq(historicoColaborador.getId()), eq(true)).will(returnValue(true));

		assertTrue(manager.setStatus(historicoColaborador.getId(), true));
	}

	public void testFindByAC()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		TSituacao situacao = new TSituacao();
		situacao.setData("01/01/2000");
		situacao.setEmpregadoCodigoAC("fadf622");
		situacao.setEmpresaCodigoAC("8674dsfaf");

		Date dataFormatada = situacao.getDataFormatada();
		historicoColaboradorDao.expects(once()).method("findByAC").with(eq(dataFormatada), eq(situacao.getEmpregadoCodigoAC()),	eq(situacao.getEmpresaCodigoAC()), ANYTHING)
			.will(returnValue(historicoColaborador));

		assertEquals(historicoColaborador, manager.findByAC(dataFormatada, situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), ""));
	}

	public void testAjustaAmbienteFuncao()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		Ambiente ambiente = new Ambiente();
		Funcao funcao = new Funcao();

		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);

		manager.ajustaAmbienteFuncao(historicoColaborador);

		assertNull(historicoColaborador.getAmbiente());
		assertNull(historicoColaborador.getFuncao());
	}

	public void testAjustaAmbienteFuncaoIdMenosUm()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		Ambiente ambiente = AmbienteFactory.getEntity(-1L);
		Funcao funcao = FuncaoFactory.getEntity(-1L);

		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);

		manager.ajustaAmbienteFuncao(historicoColaborador);

		assertNull(historicoColaborador.getAmbiente());
		assertNull(historicoColaborador.getFuncao());
	}
	
	public void testGetPrimeiroHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaboradorDao.expects(once()).method("getPrimeiroHistorico").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
		manager.getPrimeiroHistorico(colaborador.getId());
		
	}

	public void testExisteHistoricoAprovado()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);

		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador);

		historicoColaboradorDao.expects(once()).method("findHistoricoAprovado").with(eq(historicoColaborador.getId()), eq(colaborador.getId())).will(returnValue(historicoColaboradors));

		boolean retorno = manager.existeHistoricoAprovado(historicoColaborador.getId(), colaborador.getId());

		assertTrue(retorno);
	}

	public void testFindColaboradorCodigoAC()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("0025");
		colaborador.setHistoricoColaborador(historicoColaborador);

		historicoColaboradorDao.expects(once()).method("findColaboradorCodigoAC").with(eq(historicoColaborador.getId())).will(returnValue(colaborador.getCodigoAC()));

		String retorno = manager.findColaboradorCodigoAC(historicoColaborador.getId());

		assertEquals(colaborador.getCodigoAC(), retorno);
	}

	public void testGetHistoricoAtualOuFuturo()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);

		historicoColaboradorDao.expects(once()).method("getHistoricoAtual").with(eq(colaborador.getId()), eq(TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO)).will(returnValue(historicoColaborador));

		HistoricoColaborador retorno = manager.getHistoricoAtualOuFuturo(colaborador.getId());

		assertEquals(historicoColaborador.getId(), retorno.getId());
	}
	
	public void testGetHistoricoContratacaoAguardando()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.CONTRATADO);
		historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);

		historicoColaboradorDao.expects(once()).method("getHistoricoContratacaoAguardando").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));

		HistoricoColaborador retorno = manager.getHistoricoContratacaoAguardando(colaborador.getId());

		assertEquals(historicoColaborador.getId(), retorno.getId());
	}

	public void testVerificaDataPrimeiroHistorico()
	{
		//Historico antes da data de admissao
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(02, 02, 2000));

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 1999));
		historicoColaborador.setColaborador(colaborador);

		historicoColaboradorDao.expects(once()).method("getPrimeiroHistorico").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));

		assertEquals(true, manager.verificaDataPrimeiroHistorico(colaborador));

		//Mesma data
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(02, 02, 2000));
		historicoColaborador.setData(DateUtil.criarDataMesAno(02, 02, 2000));

		historicoColaboradorDao.expects(once()).method("getPrimeiroHistorico").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
		assertEquals(false, manager.verificaDataPrimeiroHistorico(colaborador));

		//Historico depois da data de admissao
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1999));
		historicoColaborador.setData(DateUtil.criarDataMesAno(02, 02, 2000));

		historicoColaboradorDao.expects(once()).method("getPrimeiroHistorico").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
		assertEquals(false, manager.verificaDataPrimeiroHistorico(colaborador));
	}

	public void testVerificaPrimeiroHistoricoAdmissao()
	{
		//Historico antes da data de admissao, editarHistorico = true
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(02, 02, 2000));

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 1999));
		historicoColaborador.setColaborador(colaborador);

		assertEquals(true, manager.verificaPrimeiroHistoricoAdmissao(true, historicoColaborador, colaborador));

		//Historico depois da data de admissao, editarHistorico = true
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1999));
		historicoColaborador.setData(DateUtil.criarDataMesAno(02, 02, 2000));

		assertEquals(false, manager.verificaPrimeiroHistoricoAdmissao(true, historicoColaborador, colaborador));

		//Historico depois da data de admissao, editarHistorico = false
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1999));
		historicoColaborador.setData(DateUtil.criarDataMesAno(02, 02, 2000));

		historicoColaboradorDao.expects(once()).method("getPrimeiroHistorico").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
		assertEquals(false, manager.verificaPrimeiroHistoricoAdmissao(false, historicoColaborador, colaborador));
	}

	public void testUpdateSituacao() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		TSituacao situacao = montaTSituacao();
		
		historicoColaboradorDao.expects(once()).method("findByAC").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(historicoColaborador));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Estabelecimento()));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new FaixaSalarial()));
		historicoColaboradorDao.expects(once()).method("update").with(ANYTHING);

		HistoricoColaborador retorno = manager.updateSituacao(situacao);

		assertEquals(historicoColaborador.getId(), retorno.getId());
	}
	
	public void testUpdateSituacaoComFaixaNulo() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		TSituacao situacao = montaTSituacao();
		
		historicoColaboradorDao.expects(once()).method("findByAC").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(historicoColaborador));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Estabelecimento()));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));

		String msg = "";
		try {
			manager.updateSituacao(situacao);
		} catch (Exception e) {
			msg = e.getMessage();
		}

		assertEquals(msg, "Não foi possível realizar a operação. Faixa salarial não existe no RH.");
	}
	
	public void testUpdateSituacaoComHistoricoInexistenet() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		TSituacao situacao = montaTSituacao();
		
		historicoColaboradorDao.expects(once()).method("findByAC").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));
		
		String msg = "";
		try {
			manager.updateSituacao(situacao);
		} catch (Exception e) {
			msg = e.getMessage();
		}
		
		assertEquals(msg, "Situação não encontrada no Fortes RH.");
	}
	
	public void testUpdateSituacaoComAreaNula() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		TSituacao situacao = montaTSituacao();
		
		historicoColaboradorDao.expects(once()).method("findByAC").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(historicoColaborador));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Estabelecimento()));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));

		String msg = "";
		try {
			manager.updateSituacao(situacao);
		} catch (Exception e) {
			msg = e.getMessage();
		}

		assertEquals(msg, "Não foi possível realizar a operação. Área organizacional não existe no RH.");
	}
	
	public void testUpdateSituacaoComEstabelecimentoNula() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		TSituacao situacao = montaTSituacao();
		
		historicoColaboradorDao.expects(once()).method("findByAC").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(historicoColaborador));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));

		String msg = "";
		try {
			manager.updateSituacao(situacao);
		} catch (Exception e) {
			msg = e.getMessage();
		}

		assertEquals(msg, "Não foi possível realizar a operação. Estabelecimento não existe no RH.");
	}

	private TSituacao montaTSituacao() {
		String empresaCodigoAC = "001";
		TSituacao situacao = new TSituacao();
		situacao.setTipoSalario("C");
		situacao.setId(1);
		situacao.setEmpresaCodigoAC(empresaCodigoAC);
		return situacao;
	}

	public void testPrepareSituacao() throws Exception
	{
		TSituacao situacao = new TSituacao();
		situacao.setTipoSalario("C");
		situacao.setData("01/01/2000");
		situacao.setEmpresaCodigoAC("001");
		situacao.setEmpregadoCodigoAC("002");
		situacao.setId(1);


		HistoricoColaborador historicoColaboradorAnterior = HistoricoColaboradorFactory.getEntity(1L);

		historicoColaboradorDao.expects(once()).method("findAtualByAC").with(ANYTHING, eq(situacao.getEmpregadoCodigoAC()), eq(situacao.getEmpresaCodigoAC()), ANYTHING).will(returnValue(historicoColaboradorAnterior));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Estabelecimento()));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new FaixaSalarial()));

		HistoricoColaborador historicoColaborador = manager.prepareSituacao(situacao);
		assertEquals(historicoColaborador.getMotivo(), MotivoHistoricoColaborador.IMPORTADO);
		assertEquals(historicoColaborador.getStatus(), StatusRetornoAC.CONFIRMADO);
	}

	public void testBindSituacao()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setCodigoAC("001");

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setCodigoAC("002");

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCodigoAC("003");

		Indice indice = IndiceFactory.getEntity(1L);
		indice.setCodigoAC("004");

		Date data = new Date();

		// Testando situação quando tipo=por cargo
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.CARGO);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setData(data);
		historicoColaborador.setIndice(null);
		historicoColaborador.setSalario(null);
		historicoColaborador.setQuantidadeIndice(null);

		TSituacao situacao = manager.bindSituacao(historicoColaborador, "");

		assertEquals(areaOrganizacional.getCodigoAC(), situacao.getLotacaoCodigoAC());
		assertEquals(estabelecimento.getCodigoAC(), situacao.getEstabelecimentoCodigoAC());
		assertEquals(faixaSalarial.getCodigoAC(), situacao.getCargoCodigoAC());
		assertEquals(null, situacao.getIndiceCodigoAC());
		assertEquals(TipoAplicacaoIndice.getCodigoAC(TipoAplicacaoIndice.CARGO), situacao.getTipoSalario());
		assertEquals(DateUtil.formataDiaMesAno(data), situacao.getData());
		assertEquals(0.0, situacao.getValor());
		assertEquals(0.0, situacao.getIndiceQtd());

//		 Testando situação quando tipo=por índice
		historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setData(data);
		historicoColaborador.setIndice(indice);
		historicoColaborador.setSalario(null);
		historicoColaborador.setQuantidadeIndice(2.0);

		situacao = manager.bindSituacao(historicoColaborador, "");

		assertEquals(areaOrganizacional.getCodigoAC(), situacao.getLotacaoCodigoAC());
		assertEquals(estabelecimento.getCodigoAC(), situacao.getEstabelecimentoCodigoAC());
		assertEquals(faixaSalarial.getCodigoAC(), situacao.getCargoCodigoAC());
		assertEquals(indice.getCodigoAC(), situacao.getIndiceCodigoAC());
		assertEquals(TipoAplicacaoIndice.getCodigoAC(TipoAplicacaoIndice.INDICE), situacao.getTipoSalario());
		assertEquals(DateUtil.formataDiaMesAno(data), situacao.getData());
		assertEquals(0.0, situacao.getValor());
		assertEquals(2.0, situacao.getIndiceQtd());

		//	Testando situação quando tipo=por valor
		historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setData(data);
		historicoColaborador.setIndice(null);
		historicoColaborador.setSalario(123.45);
		historicoColaborador.setQuantidadeIndice(null);

		situacao = manager.bindSituacao(historicoColaborador, "empresaCodigoAC");

		assertEquals(areaOrganizacional.getCodigoAC(), situacao.getLotacaoCodigoAC());
		assertEquals(estabelecimento.getCodigoAC(), situacao.getEstabelecimentoCodigoAC());
		assertEquals(faixaSalarial.getCodigoAC(), situacao.getCargoCodigoAC());
		assertEquals(null, situacao.getIndiceCodigoAC());
		assertEquals(TipoAplicacaoIndice.getCodigoAC(TipoAplicacaoIndice.VALOR), situacao.getTipoSalario());
		assertEquals(DateUtil.formataDiaMesAno(data), situacao.getData());
		assertEquals(123.45, situacao.getValor());
		assertEquals(0.0, situacao.getIndiceQtd());
		assertEquals("empresaCodigoAC", situacao.getEmpresaCodigoAC());
	}
	
	public void testVerifyDataHistoricoAdmissao()
	{
		Date dataAdmissao = DateUtil.criarDataMesAno(18, 06, 2007);
		Date data = new Date();
		
		HistoricoColaborador historicoColaborador = new HistoricoColaborador(dataAdmissao, data);
		
		historicoColaboradorDao.expects(once()).method("findHistoricoAdmissao").with(eq(1L)).will(returnValue(historicoColaborador));
		
		assertTrue(manager.verifyDataHistoricoAdmissao(1L));
	}
	
	public void testFindAllByColaborador()
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setColaborador(colaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaborador2.setColaborador(colaborador1);

		Collection<HistoricoColaborador> historicoColaboradors = Arrays.asList(historicoColaborador1, historicoColaborador2);

		
		historicoColaboradorDao.expects(once()).method("findAllByColaborador").with(eq(1L)).will(returnValue(historicoColaboradors));
		
		assertEquals(2, manager.findAllByColaborador(1L).size());
	}
	
	public void testUpdateAmbientesEFuncoes() throws Exception
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setColaborador(colaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaborador2.setColaborador(colaborador1);
		
		Collection<HistoricoColaborador> historicoColaboradors = Arrays.asList(historicoColaborador1, historicoColaborador2);
		
		historicoColaboradorDao.expects(once()).method("updateAmbienteEFuncao").will(returnValue(true));
		historicoColaboradorDao.expects(once()).method("updateAmbienteEFuncao").will(returnValue(true));
		
		manager.updateAmbientesEFuncoes(historicoColaboradors);
	}
	public void testUpdateAmbientesEFuncoesException()
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setColaborador(colaborador1);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaborador2.setColaborador(colaborador1);
		
		Collection<HistoricoColaborador> historicoColaboradors = Arrays.asList(historicoColaborador1, historicoColaborador2);
		
		historicoColaboradorDao.expects(once()).method("updateAmbienteEFuncao").with(eq(historicoColaborador1)).will(returnValue(true));
		historicoColaboradorDao.expects(once()).method("updateAmbienteEFuncao").with(eq(historicoColaborador2)).will(returnValue(false));
		
		Exception exception = null;
		try {
			manager.updateAmbientesEFuncoes(historicoColaboradors);
		} catch (Exception e) {
			exception = e;
		}
		
		assertNotNull(exception);
	}
	
	public void testMontaRelatorioSituacoes() throws ColecaoVaziaException, Exception
	{
		historicoColaboradorDao.expects(once()).method("findByPeriodo").will(returnValue(new ArrayList<HistoricoColaborador>()));
		
		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionals.add(areaOrganizacional);

		Date dataIni = new Date();
		Date dataFim = new Date();
		Long[] estabelecimentosIds = new Long[]{1L};
		Long[] areasIds = new Long[]{1L};
		manager.montaRelatorioSituacoes(1L, dataIni, dataFim, estabelecimentosIds, areasIds, "RH", 'A', false);
	}
	
	public void testExisteDependenciaComHistoricoIndiceComNenhumHistoricoIndice() 
	{
		Date dataHistoricoExcluir = new Date();
		Long indiceId = 1L;
		
		indiceHistoricoManager.expects(once()).method("findToList").will(returnValue(new ArrayList<IndiceHistorico>()));
		
		boolean existeDependencia = manager.existeDependenciaComHistoricoIndice(dataHistoricoExcluir, indiceId);
		
		assertFalse(existeDependencia);
	}
	
	public void testExisteDependenciaComHistoricoIndiceComUmHistoricoIndice() 
	{
		Date dataHistoricoExcluir = new Date();
		Long indiceId = 1L;
		
		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(new IndiceHistorico());
		
		indiceHistoricoManager.expects(once()).method("findToList").will(returnValue(indiceHistoricos));
		historicoColaboradorDao.expects(once()).method("existeDependenciaComHistoricoIndice").with(eq(dataHistoricoExcluir), NULL, eq(indiceId)) .will(returnValue(true));
		
		boolean existeDependencia = manager.existeDependenciaComHistoricoIndice(dataHistoricoExcluir, indiceId);
		
		assertTrue(existeDependencia);
	}

	public void testExisteDependenciaComHistoricoIndiceComVariosHistoricoIndice() 
	{
		Date dataHistoricoExcluir = new Date();
		Long indiceId = 1L;
		
		IndiceHistorico indiceHistorico2 = new IndiceHistorico();
		indiceHistorico2.setData(new Date());
		
		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(new IndiceHistorico());
		indiceHistoricos.add(indiceHistorico2);
		
		indiceHistoricoManager.expects(once()).method("findToList").will(returnValue(indiceHistoricos));
		historicoColaboradorDao.expects(once()).method("existeDependenciaComHistoricoIndice").with(eq(dataHistoricoExcluir), eq(indiceHistorico2.getData()), eq(indiceId)) .will(returnValue(true));
		
		boolean existeDependencia = manager.existeDependenciaComHistoricoIndice(dataHistoricoExcluir, indiceId);
		
		assertTrue(existeDependencia);
	}
	
	private void montaPromocoesHorizontalEVerticalPorArea(boolean somenteAreasFilhas, AreaOrganizacional areaMae1, AreaOrganizacional area1, AreaOrganizacional area2, Empresa empresa) 
	{
		Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
		areasOrganizacionais.add(areaMae1);
		areasOrganizacionais.add(area1);
		areasOrganizacionais.add(area2);
		
		Estabelecimento parajana = EstabelecimentoFactory.getEntity(1L);
		
		List<RelatorioPromocoes> promocoes = new ArrayList<RelatorioPromocoes>();
		
		RelatorioPromocoes relatorioPromocoesAreaMae1 = new RelatorioPromocoes();
		relatorioPromocoesAreaMae1.setArea(areaMae1);
		relatorioPromocoesAreaMae1.setEstabelecimento(parajana);
		relatorioPromocoesAreaMae1.setQtdHorizontal(3);
		relatorioPromocoesAreaMae1.setQtdVertical(0);
		
		RelatorioPromocoes relatorioPromocoesArea2 = new RelatorioPromocoes();
		relatorioPromocoesArea2.setArea(area2);
		relatorioPromocoesArea2.setEstabelecimento(parajana);
		relatorioPromocoesArea2.setQtdHorizontal(1);
		relatorioPromocoesArea2.setQtdVertical(1);
		
		promocoes.add(relatorioPromocoesAreaMae1);
		promocoes.add(relatorioPromocoesArea2);
		
		areaOrganizacionalManager.expects(once()).method("findAllList").withAnyArguments().will(returnValue(areasOrganizacionais));
		historicoColaboradorDao.expects(atLeastOnce()).method("getRelatorioPromocoes").will(returnValue(promocoes));
	}
	
	public void testMontaPromocoesHorizontalEVerticalPorArea()
	{
		boolean somenteAreasFilhas = false;

		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		AreaOrganizacional areaMae1 = AreaOrganizacionalFactory.getEntity(1L);
		areaMae1.setNome("AreaMae1");
	
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity(2L);
		area1.setAreaMae(areaMae1);
		area1.setNome("Area1");
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity(3L);
		area2.setNome("Area2");
		
		Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
		areasOrganizacionais.add(areaMae1);
		areasOrganizacionais.add(area1);
		areasOrganizacionais.add(area2);
		
		Collection<AreaOrganizacional> areasOrganizacionaisDecendentesAreaMae1 = new ArrayList<AreaOrganizacional>();
		areasOrganizacionaisDecendentesAreaMae1.add(area1);
		
		montaPromocoesHorizontalEVerticalPorArea(somenteAreasFilhas, areaMae1, area1, area2, empresa);

		areaOrganizacionalManager.expects(once()).method("findByIdProjection").with(eq(areaMae1.getId())).will(returnValue(areaMae1));
		areaOrganizacionalManager.expects(once()).method("getDescendentes").with(eq(areasOrganizacionais), eq(areaMae1.getId()), ANYTHING).will(returnValue(areasOrganizacionaisDecendentesAreaMae1));
		areaOrganizacionalManager.expects(once()).method("findByIdProjection").with(eq(area2.getId())).will(returnValue(area2));
		areaOrganizacionalManager.expects(once()).method("getDescendentes").with(eq(areasOrganizacionais), eq(area2.getId()), ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));

		Map<Character, Collection<DataGrafico>> promocoes = manager.montaPromocoesHorizontalEVerticalPorArea(DateUtil.criarDataMesAno(01, 01, 2000), DateUtil.criarDataMesAno(01, 05, 2000), empresa.getId(), somenteAreasFilhas, new Long[]{areaMae1.getId(), area2.getId()});
		
		Collection<DataGrafico> promocaoHorizontal = promocoes.get('H');
		Collection<DataGrafico> promocaovertical = promocoes.get('V');
		
		assertEquals("AreaMae1", ((DataGrafico) promocaoHorizontal.toArray()[0]).getLabel());
		assertEquals(4.0, ((DataGrafico) promocaoHorizontal.toArray()[0]).getData());
		
		assertEquals("Area2", ((DataGrafico) promocaoHorizontal.toArray()[1]).getLabel());
		assertEquals(4.0, ((DataGrafico) promocaoHorizontal.toArray()[1]).getData());
		
		assertEquals("AreaMae1", ((DataGrafico) promocaovertical.toArray()[0]).getLabel());
		assertEquals(1.0, ((DataGrafico) promocaovertical.toArray()[0]).getData());
		
		assertEquals("Area2", ((DataGrafico) promocaovertical.toArray()[1]).getLabel());
		assertEquals(1.0, ((DataGrafico) promocaovertical.toArray()[1]).getData());
	}
	
	public void testMontaPromocoesHorizontalEVerticalPorAreasomenteAreasFilhas()
	{
		boolean somenteAreasFilhas = true;

		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		AreaOrganizacional areaMae1 = AreaOrganizacionalFactory.getEntity(1L);
		areaMae1.setNome("AreaMae1");
	
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity(2L);
		area1.setAreaMae(areaMae1);
		area1.setNome("Area1");
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity(3L);
		area2.setNome("Area2");
		
		Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
		areasOrganizacionais.add(areaMae1);
		areasOrganizacionais.add(area1);
		areasOrganizacionais.add(area2);
		
		Collection<AreaOrganizacional> areasOrganizacionaisDecendentesAreaMae1 = new ArrayList<AreaOrganizacional>();
		areasOrganizacionaisDecendentesAreaMae1.add(area1);
		
		Estabelecimento parajana = EstabelecimentoFactory.getEntity(1L);
		
		List<RelatorioPromocoes> relatorioPromocoes = new ArrayList<RelatorioPromocoes>();
		
		RelatorioPromocoes relatorioPromocoesArea1 = new RelatorioPromocoes();
		relatorioPromocoesArea1.setArea(area1);
		relatorioPromocoesArea1.setEstabelecimento(parajana);
		relatorioPromocoesArea1.setQtdHorizontal(4);
		relatorioPromocoesArea1.setQtdVertical(1);
		
		relatorioPromocoes.add(relatorioPromocoesArea1);
		
		areaOrganizacionalManager.expects(once()).method("findAllList").withAnyArguments().will(returnValue(areasOrganizacionais));
		historicoColaboradorDao.expects(atLeastOnce()).method("getRelatorioPromocoes").will(returnValue(relatorioPromocoes));
		
		areaOrganizacionalManager.expects(once()).method("getFilhos").with(eq(areasOrganizacionais),eq(areaMae1.getId())).will(returnValue(areasOrganizacionaisDecendentesAreaMae1));
		areaOrganizacionalManager.expects(once()).method("findByIdProjection").with(eq(areaMae1.getId())).will(returnValue(areaMae1));
		areaOrganizacionalManager.expects(once()).method("getDescendentes").with(eq(areasOrganizacionais), eq(area1.getId()), ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
		
		Map<Character, Collection<DataGrafico>> promocoes = manager.montaPromocoesHorizontalEVerticalPorArea(DateUtil.criarDataMesAno(01, 01, 2000), DateUtil.criarDataMesAno(01, 05, 2000), empresa.getId(), somenteAreasFilhas, new Long[]{areaMae1.getId()});
		
		Collection<DataGrafico> promocaoHorizontal = promocoes.get('H');
		Collection<DataGrafico> promocaovertical = promocoes.get('V');
		
		assertEquals("AreaMae1 > Area1", ((DataGrafico) promocaoHorizontal.toArray()[0]).getLabel());
		assertEquals(4.0, ((DataGrafico) promocaoHorizontal.toArray()[0]).getData());
		
		assertEquals("AreaMae1 > Area1", ((DataGrafico) promocaovertical.toArray()[0]).getLabel());
		assertEquals(1.0, ((DataGrafico) promocaovertical.toArray()[0]).getData());
	}

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(historicoColaboradorDao);
	}
}
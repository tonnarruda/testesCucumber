package com.fortes.rh.test.business.cargosalario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
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
import com.fortes.rh.business.portalcolaborador.TransacaoPCManager;
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
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.dicionario.URLTransacaoPC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.portalcolaborador.ColaboradorPC;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;
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

@SuppressWarnings("deprecation")
public class HistoricoColaboradorManagerTest extends MockObjectTestCase
{
	HistoricoColaboradorManagerImpl historicoColaboradorManager = new HistoricoColaboradorManagerImpl();
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
	Mock transacaoPCManager;

	protected void setUp() throws Exception
	{
        transactionManager = new Mock(PlatformTransactionManager.class);
        historicoColaboradorManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        historicoColaboradorDao = new Mock(HistoricoColaboradorDao.class);
		historicoColaboradorManager.setDao((HistoricoColaboradorDao) historicoColaboradorDao.proxy());

		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		historicoColaboradorManager.setAreaOrganizacionalManager((AreaOrganizacionalManager)areaOrganizacionalManager.proxy());

		indiceHistoricoManager = new Mock(IndiceHistoricoManager.class);
		historicoColaboradorManager.setIndiceHistoricoManager((IndiceHistoricoManager)indiceHistoricoManager.proxy());

		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		historicoColaboradorManager.setFaixaSalarialManager((FaixaSalarialManager)faixaSalarialManager.proxy());

		indiceManager = new Mock(IndiceManager.class);
		historicoColaboradorManager.setIndiceManager((IndiceManager)indiceManager.proxy());

		faixaSalarialHistoricoManager = new Mock(FaixaSalarialHistoricoManager.class);
		historicoColaboradorManager.setFaixaSalarialHistoricoManager((FaixaSalarialHistoricoManager)faixaSalarialHistoricoManager.proxy());

		reajusteColaboradorManager = new Mock(ReajusteColaboradorManager.class);
		historicoColaboradorManager.setReajusteColaboradorManager((ReajusteColaboradorManager)reajusteColaboradorManager.proxy());
		
		candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
		historicoColaboradorManager.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager)candidatoSolicitacaoManager.proxy());

		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		historicoColaboradorManager.setEstabelecimentoManager((EstabelecimentoManager)estabelecimentoManager.proxy());

		empresaManager = new Mock(EmpresaManager.class);
		historicoColaboradorManager.setEmpresaManager((EmpresaManager) empresaManager.proxy());

		acPessoalClientTabelaReajuste = mock(AcPessoalClientTabelaReajusteInterface.class);
		historicoColaboradorManager.setAcPessoalClientTabelaReajuste((AcPessoalClientTabelaReajusteInterface) acPessoalClientTabelaReajuste.proxy());

		acPessoalClientColaborador = mock(AcPessoalClientColaborador.class);
		historicoColaboradorManager.setAcPessoalClientColaborador((AcPessoalClientColaborador) acPessoalClientColaborador.proxy());

		gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		historicoColaboradorManager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());
		
		transacaoPCManager = mock(TransacaoPCManager.class);
		historicoColaboradorManager.setTransacaoPCManager((TransacaoPCManager) transacaoPCManager.proxy());

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
		Collection<HistoricoColaborador> retorno = historicoColaboradorManager.getByColaboradorId(colaboradorId);
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
		historicoColaboradorManager.findPromocaoByColaborador(colaboradorId);
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
		historicoColaboradorManager.getHistoricosAtuaisByEstabelecimentoAreaGrupo(estabelecimentoIds, filtrarPor, areaOrganizacionalIds, grupoOcupacionalIds, empresaId, data);
	}
	
	public void testRelatorioColaboradorCargoComColaboradoresRegistradosNoAC() throws Exception {
		
		HistoricoColaborador hc = dadoUmColaboradorQueTambemEstaRegistradoNoAC();
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(hc);
		
		Date data = new Date();
		Empresa empresa = EmpresaFactory.getEmpresa();
		
		//opcao 0
		historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(data), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoColaboradors));
		assertEquals(1, historicoColaboradorManager.relatorioColaboradorCargo(data, null, null, 2, '0', null, true, null, null, true, empresa.getId()).size());
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
		assertEquals(1, historicoColaboradorManager.relatorioColaboradorCargo(data, null, null, 2, '0', null, true, null, null, false, empresa.getId()).size());
		
		// opcao 1
		historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(data), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoColaboradors));
		assertEquals(1, historicoColaboradorManager.relatorioColaboradorCargo(data, null, null, 2, '1', null, true, null, null, false, empresa.getId()).size());

		// quantidade de meses null
		historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(data), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoColaboradors));
		assertEquals(1, historicoColaboradorManager.relatorioColaboradorCargo(data, null, null, null, '1', null, true, null, null, false, empresa.getId()).size());

		// qtd Meses Desatualizacao 10
		historicoColaboradorDao.expects(once()).method("findByCargoEstabelecimento").with(new Constraint[]{eq(data), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicoColaboradors));
		assertEquals(1, historicoColaboradorManager.relatorioColaboradorCargo(data, null, null, null, '1', null, true, 10, null, false, empresa.getId()).size());
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
			
			
			Collection<HistoricoColaborador> resultado = historicoColaboradorManager.relatorioColaboradorCargo(hoje, null, null, 2, '1', null, true, null, null, false, empresa.getId());
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
		
		
		Collection<HistoricoColaborador> resultado = historicoColaboradorManager.relatorioColaboradorCargo(hoje, null, null, 2, '1', null, true, null, null, true, empresa.getId());

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
			historicoColaboradorManager.relatorioColaboradorCargo(hoje, null, null, 2, '1', null, true, null, null, true, empresa.getId());
		
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
		historicoColaboradorManager.getHistoricoAtual(colaboradorId);
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
		assertEquals(900.0, historicoColaboradorManager.getValorTotalFolha(null, new Date()));
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
		
		Collection<HistoricoColaborador> resultado = historicoColaboradorManager.findSemDissidioByDataPercentual(dataIni, dataFim, percentualDissidio, empresa.getId(), cargosIds, areasIds, estabelecimentosIds);
		assertEquals("Todos hist. colaboradores", 4, resultado.size());

		cargosIds = new String[] {"1"};
		areasIds = new String[] {"3","4"};
		estabelecimentosIds = new String[] {"5","6"};
		
		resultado = historicoColaboradorManager.findSemDissidioByDataPercentual(dataIni, dataFim, percentualDissidio, empresa.getId(), cargosIds, areasIds, estabelecimentosIds);
		assertEquals("Sem cargo ID=2", historico1Colaborador1.getFaixaSalarial().getCargo().getId() , ((HistoricoColaborador) resultado.toArray()[0]).getFaixaSalarial().getCargo().getId());

		cargosIds = new String[] {"1","2"};
		areasIds = new String[] {"4"};
		estabelecimentosIds = new String[] {"5","6"};
		
		resultado = historicoColaboradorManager.findSemDissidioByDataPercentual(dataIni, dataFim, percentualDissidio, empresa.getId(), cargosIds, areasIds, estabelecimentosIds);
		assertEquals("Sem área ID=3", historico1Colaborador2.getAreaOrganizacional().getId() , ((HistoricoColaborador) resultado.toArray()[0]).getAreaOrganizacional().getId());
		
		cargosIds = new String[] {"1","2"};
		areasIds = new String[] {"3","4"};
		estabelecimentosIds = new String[] {"5"};
		
		resultado = historicoColaboradorManager.findSemDissidioByDataPercentual(dataIni, dataFim, percentualDissidio, empresa.getId(), cargosIds, areasIds, estabelecimentosIds);
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

		assertEquals(2, historicoColaboradorManager.findByCargosIds(0,0,cargosIds,null, 1L).size());
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

		assertEquals(2, historicoColaboradorManager.findByGrupoOcupacionalIds(0,0,grupoOcupacionalIds_,1L).size());
	}
	
	public void testGetPromocoes()
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
		
		SituacaoColaborador joaoCobradorFaixaUm = new SituacaoColaborador(500.0, DateUtil.criarDataMesAno(01, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.CONTRATADO, 1L);
		SituacaoColaborador joaoCobradorFaixaUmAumentoSalario = new SituacaoColaborador(600.0, DateUtil.criarDataMesAno(01, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.PROMOCAO, 1L);
		SituacaoColaborador joaoCobradorFaixaDois = new SituacaoColaborador(500.0, DateUtil.criarDataMesAno(01, 04, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaDoisCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.PROMOCAO, 2L);
		SituacaoColaborador joaoCobradorFaixaDoisAumentoSalario = new SituacaoColaborador(650.0, DateUtil.criarDataMesAno(01, 04, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaDoisCobrador, parajana, garagem, joao, MotivoHistoricoColaborador.PROMOCAO, 2L);
		SituacaoColaborador joaoCobradorFaixaDoisMudouArea = new SituacaoColaborador(650.0, DateUtil.criarDataMesAno(01, 05, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaDoisCobrador, parajana, lavajato, joao, MotivoHistoricoColaborador.PROMOCAO, 3L);
		SituacaoColaborador joaoMotoristaFaixaA = new SituacaoColaborador(650.0, DateUtil.criarDataMesAno(06, 06, 2001), TipoAplicacaoIndice.CARGO, motorista, faixaAMotorista, parajana, lavajato, joao, MotivoHistoricoColaborador.PROMOCAO, 4L);
	
		SituacaoColaborador mariaCobradorFaixaUm = new SituacaoColaborador(500.0, DateUtil.criarDataMesAno(01, 02, 2000), TipoAplicacaoIndice.CARGO, cobrador, faixaUmCobrador, parajana, garagem, maria, MotivoHistoricoColaborador.CONTRATADO, 5L);
		SituacaoColaborador mariaCobradorFaixaUmAumentoSalario = new SituacaoColaborador(555.0, DateUtil.criarDataMesAno(01, 02, 2000), TipoAplicacaoIndice.CARGO, motorista, faixaAMotorista, parajana, garagem, maria, MotivoHistoricoColaborador.PROMOCAO, 5L);
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
		Long[] empresasPermitidas = new Long[]{empresa.getId()};
		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		
		historicoColaboradorDao.expects(once()).method("getPromocoes").will(returnValue(situacoes));
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(AreaOrganizacional.TODAS), ANYTHING, eq(empresasPermitidas)).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(eq(areaOrganizacionals)).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, eq(garagem.getId())).will(returnValue(garagem));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, eq(lavajato.getId())).will(returnValue(lavajato));
		
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 1999);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2300);
		
		List<RelatorioPromocoes> promocoes = historicoColaboradorManager.getPromocoes(null, null, dataIni, dataFim, empresa.getId());
		
		assertEquals(2, promocoes.size());
		
		RelatorioPromocoes promocaoParajanaGaragem = (RelatorioPromocoes) promocoes.get(0);
		assertEquals(4, promocaoParajanaGaragem.getQtdHorizontal());
		assertEquals(1, promocaoParajanaGaragem.getQtdVertical());
		
		RelatorioPromocoes promocaoParajanaLavajato = (RelatorioPromocoes) promocoes.get(1);
		assertEquals(0, promocaoParajanaLavajato.getQtdHorizontal());
		assertEquals(1, promocaoParajanaLavajato.getQtdVertical());
	}
	
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
		
		Map<Character, Collection<Object[]>> promocoes = historicoColaboradorManager.montaPromocoesHorizontalEVertical(DateUtil.criarDataMesAno(01, 01, 2000), DateUtil.criarDataMesAno(01, 05, 2000), empresa.getId(), null);
		
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
		
		List<SituacaoColaborador> retorno = historicoColaboradorManager.getColaboradoresSemReajuste(null, null, DateUtil.criarDataMesAno(02, 02, 2002), empresa.getId(), 12);
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

		Collection<HistoricoColaborador> colhfRetorno = historicoColaboradorManager.findDistinctFuncao(historicos);

		assertEquals(2, colhfRetorno.size());
		assertFalse(colhfRetorno.contains(hc2));
		assertEquals(hc.getDataProximoHistorico(), hc3.getData());
	}

	public void testFindByColaboradorData()
	{
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(HistoricoColaboradorFactory.getEntity());

		historicoColaboradorDao.expects(once()).method("findByColaboradorData").with(ANYTHING,ANYTHING).will(returnValue(historicoColaboradors));

		assertEquals(1, historicoColaboradorManager.findByColaboradorData(1L,new Date()).size());
	}

	public void testGetHistoricoAnterior()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setId(1L);

		historicoColaboradorDao.expects(once()).method("getHistoricoAnterior").with(ANYTHING).will(returnValue(historicoColaborador));

		assertEquals(historicoColaborador.getId(), historicoColaboradorManager.getHistoricoAnterior(historicoColaborador).getId());
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

		assertEquals(2, historicoColaboradorManager.inserirPeriodos(historicos).size());
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

		assertEquals(2, historicoColaboradorManager.findDistinctAmbienteFuncao(historicos).size());
	}

	public void testExisteHistoricoData()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaboradorId(1L);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 02, 2001));
		
		Collection<HistoricoColaborador> historicos = HistoricoColaboradorFactory.getCollection();
		historicos.add(historicoColaborador);

		historicoColaboradorDao.expects(once()).method("findByData").with(ANYTHING, ANYTHING).will(returnValue(historicos));

		assertTrue(historicoColaboradorManager.existeHistoricoData(historicoColaborador));

		historicoColaboradorDao.expects(once()).method("findByData").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<HistoricoColaborador>()));

		assertFalse(historicoColaboradorManager.existeHistoricoData(historicoColaborador));	}

	public void testMontaTipoSalarioValor()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		assertEquals("Por Valor", historicoColaboradorManager.montaTipoSalario(0.0, historicoColaborador.getTipoSalario(), ""));
	}

	public void testMontaTipoSalarioIndice()
	{
		Indice indice = IndiceFactory.getEntity();
		indice.setNome("Salario Mínimo");

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaborador.setIndice(indice);
		assertEquals("Por Índice (2.0 x Salario Mínimo)", historicoColaboradorManager.montaTipoSalario(2.0, historicoColaborador.getTipoSalario(), historicoColaborador.getIndice().getNome()));
	}

	public void testMontaTipoSalarioCargo()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.CARGO);
		assertEquals("Por Cargo", historicoColaboradorManager.montaTipoSalario(2.0, historicoColaborador.getTipoSalario(), ""));
	}

	public void testFindByColaboradorProjection()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		historicoColaboradorDao.expects(once()).method("findByColaboradorProjection").with(eq(colaborador.getId())).will(returnValue(HistoricoColaboradorFactory.getCollection()));

		assertNotNull(historicoColaboradorManager.findByColaboradorProjection(colaborador.getId()));
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
			retorno = historicoColaboradorManager.findByColaborador(colaborador.getId(), empresa.getId());
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

		Collection<TSituacao> situacoes = historicoColaboradorManager.findHistoricosByTabelaReajuste(tabelaReajusteColaborador.getId(), empresa);

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
		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorManager.ajustaTipoSalario(historico, TipoAplicacaoIndice.CARGO, indice, quantidadeIndice, salarioColaborador);

		assertEquals(TipoAplicacaoIndice.CARGO, historicoColaboradorRetorno.getTipoSalario());
		assertEquals(null, historicoColaboradorRetorno.getIndice());
		assertEquals(null, historicoColaboradorRetorno.getSalarioCalculado());
		assertEquals(0.0, historicoColaboradorRetorno.getQuantidadeIndice());

		//Para Valor
		historicoColaboradorRetorno = historicoColaboradorManager.ajustaTipoSalario(historico, TipoAplicacaoIndice.VALOR, indice, quantidadeIndice, salarioColaborador);

		assertEquals(TipoAplicacaoIndice.VALOR, historicoColaboradorRetorno.getTipoSalario());
		assertEquals(null, historicoColaboradorRetorno.getIndice());
		assertEquals(salarioColaborador, historicoColaboradorRetorno.getSalarioCalculado());
		assertEquals(0.0, historicoColaboradorRetorno.getQuantidadeIndice());

		//Para Indice
		historicoColaboradorRetorno = historicoColaboradorManager.ajustaTipoSalario(historico, TipoAplicacaoIndice.INDICE, indice, quantidadeIndice, salarioColaborador);

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

		Collection<HistoricoColaborador> retorno = historicoColaboradorManager.progressaoColaborador(colaborador.getId(), empresa.getId());
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
		indiceHistoricoManager.expects(once()).method("findByPeriodo").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(indiceHistoricos));
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(AreaOrganizacional.TODAS), ANYTHING, eq(new long[]{empresa.getId()})).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(areaOrganizacional));

		Collection<HistoricoColaborador> retorno = historicoColaboradorManager.progressaoColaborador(colaborador.getId(), empresa.getId());
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

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setObsReajuste("obsReajuste");
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setData(DateUtil.criarDataMesAno(01, 01, 2006));

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico2.setData(DateUtil.criarDataMesAno(01, 01, 2006));

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2006));
		historicoColaborador1.setTipoSalario(TipoAplicacaoIndice.CARGO);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador1.setIndice(indice);
		historicoColaborador1.setFaixaSalarial(faixaSalarial);
		historicoColaborador1.setSalario(500.0);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2006));
		historicoColaborador2.setTipoSalario(TipoAplicacaoIndice.CARGO);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setIndice(indice);
		historicoColaborador2.setFaixaSalarial(faixaSalarial);
		historicoColaborador2.setSalario(500.0);

		Collection<HistoricoColaborador> historicoColaboradores = new ArrayList<HistoricoColaborador>();
		historicoColaboradores.add(historicoColaborador1);
		historicoColaboradores.add(historicoColaborador2);

		Collection<FaixaSalarialHistorico> faixaHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaHistoricos.add(faixaSalarialHistorico);
		faixaHistoricos.add(faixaSalarialHistorico2);

		historicoColaboradorDao.expects(once()).method("findPromocaoByColaborador").with(ANYTHING).will(returnValue(historicoColaboradores));
		faixaSalarialHistoricoManager.expects(atLeastOnce()).method("findByPeriodo").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(faixaHistoricos));
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").with(eq(AreaOrganizacional.TODAS), ANYTHING, eq(new Long[]{empresa.getId()})).will(returnValue(areas));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(areaOrganizacional));

		Collection<HistoricoColaborador> retorno = historicoColaboradorManager.progressaoColaborador(colaborador.getId(), empresa.getId());
		assertEquals(6, retorno.size());
	}

	public void  testRemoveHistoricoAndReajuste()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("43232342");

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);

		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador);

		historicoColaboradorDao.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getId())).will(returnValue(historicoColaborador));
		historicoColaboradorDao.expects(once()).method("findHistoricoAprovado").with(eq(historicoColaborador.getId()), eq(colaborador.getId())).will(returnValue(historicoColaboradors));
		acPessoalClientColaborador.expects(once()).method("verificaHistoricoNaFolhaAC").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(false));
		historicoColaboradorDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(2));
		historicoColaboradorDao.expects(once()).method("findReajusteByHistoricoColaborador").with(eq(historicoColaborador.getId())).will(returnValue(1L));
		historicoColaboradorDao.expects(once()).method("remove").with(eq(historicoColaborador.getId()));
		historicoColaboradorDao.expects(once()).method("setaContratadoNoPrimeiroHistorico").with(eq(colaborador.getId()));
		historicoColaboradorDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		reajusteColaboradorManager.expects(once()).method("remove").with(ANYTHING);
		acPessoalClientTabelaReajuste.expects(once()).method("deleteHistoricoColaboradorAC").with(ANYTHING, ANYTHING);

		Exception exception = null;
		try
		{
			historicoColaboradorManager.removeHistoricoAndReajuste(historicoColaborador.getId(), colaborador.getId(), empresa);
		}
		catch (Exception e)
		{
			exception = e;
			e.printStackTrace();
		}

		assertNull(exception);
	}

	public void  testRemoveHistoricoAndReajusteException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("43232342");

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);

		historicoColaboradorDao.expects(once()).method("findByIdProjection").with(eq(historicoColaborador.getId())).will(returnValue(null));

		Exception exception = null;
		try
		{
			historicoColaboradorManager.removeHistoricoAndReajuste(historicoColaborador.getId(), colaborador.getId(), empresa);
		}
		catch (Exception e)
		{
			exception = e;
			e.printStackTrace();
		}

		assertNotNull(exception);
	}

	public void  testCancelarSituacaoRH_SEP() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setSalario(200.0);

		TSituacao situacao = new TSituacao();
		situacao.setId(1);

		String mensagem = "Teste";

		historicoColaboradorDao.expects(once()).method("findByIdProjectionHistorico").with(ANYTHING).will(returnValue(historicoColaborador));
		historicoColaboradorDao.expects(once()).method("setStatus").with(eq(historicoColaborador.getId()), eq(false)).will(returnValue(true));
		gerenciadorComunicacaoManager.expects(once()).method("enviaMensagemCancelamentoSituacao").with(eq(situacao), eq(mensagem), eq(historicoColaborador)).isVoid();
		solicitacaoManager.expects(once()).method("atualizaStatusSolicitacaoByColaborador").with(eq(colaborador), eq(StatusCandidatoSolicitacao.APROMOVER), eq(false)).isVoid();
		
		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorManager.cancelarSituacao(situacao, mensagem);

		assertEquals(historicoColaborador, historicoColaboradorRetorno);
	}

	public void  testCancelarSituacaoSEP() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setSalario(200.0);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setValor(1000.00);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);

		String empresaCodigoAC = "001";
		TSituacao situacao = new TSituacao();
		situacao.setTipoSalario("C");
		situacao.setEmpresaCodigoAC(empresaCodigoAC);
		String mensagem = "Teste";

		historicoColaboradorDao.expects(once()).method("findByAC").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(historicoColaborador));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(estabelecimento));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(areaOrganizacional));
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(faixaSalarial));
		historicoColaboradorDao.expects(once()).method("update").with(ANYTHING);
		gerenciadorComunicacaoManager.expects(once()).method("enviaMensagemCancelamentoSituacao").with(eq(situacao), eq(mensagem), eq(historicoColaborador)).isVoid();
		solicitacaoManager.expects(once()).method("atualizaStatusSolicitacaoByColaborador").with(eq(colaborador), eq(StatusCandidatoSolicitacao.APROMOVER), eq(false)).isVoid();
		
		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorManager.cancelarSituacao(situacao, mensagem);

		assertEquals(historicoColaborador, historicoColaboradorRetorno);
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

		Collection<PendenciaAC> pendenciaACs = historicoColaboradorManager.findPendenciasByHistoricoColaborador(1L);

		assertEquals(2, pendenciaACs.size());
		PendenciaAC pendenciaAC1 = (PendenciaAC) pendenciaACs.toArray()[0];
		PendenciaAC pendenciaAC2 = (PendenciaAC) pendenciaACs.toArray()[1];
		assertEquals("Contratação", pendenciaAC1.getPendencia());
		assertEquals("Nova Situação de Colaborador", pendenciaAC2.getPendencia());
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
			historicoColaboradorManager.updateHistorico(historicoColaborador, empresa);
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
			historicoColaboradorManager.insertHistorico(historicoColaborador, empresa);
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
			historicoColaboradorManager.insertHistorico(historicoColaborador, empresa);
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
			historicoColaboradorManager.updateHistorico(historicoColaborador, empresa);
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
			historicoColaboradorManager.updateHistorico(historicoColaborador, empresa);
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

		assertEquals(historicoColaborador, historicoColaboradorManager.findByIdHQL(historicoColaborador.getId()));
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

		assertEquals(false, historicoColaboradorManager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
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

		assertEquals(true, historicoColaboradorManager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
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

		assertEquals(true, historicoColaboradorManager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
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

		assertEquals(false, historicoColaboradorManager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
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

		assertEquals(true, historicoColaboradorManager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
	}

	public void testAlterouDadosIntegradoACCancelado()
	{
		HistoricoColaborador historicoColaboradorTela = HistoricoColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaboradorBanco = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaboradorBanco.setStatus(StatusRetornoAC.CANCELADO);

		assertEquals(true, historicoColaboradorManager.alterouDadosIntegradoAC(historicoColaboradorTela, historicoColaboradorBanco));
	}

	public void testSetStatus()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);

		historicoColaboradorDao.expects(once()).method("setStatus").with(eq(historicoColaborador.getId()), eq(true)).will(returnValue(true));

		assertTrue(historicoColaboradorManager.setStatus(historicoColaborador.getId(), true));
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

		assertEquals(historicoColaborador, historicoColaboradorManager.findByAC(dataFormatada, situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), ""));
	}

	public void testAjustaAmbienteFuncao()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		Ambiente ambiente = new Ambiente();
		Funcao funcao = new Funcao();

		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);

		historicoColaboradorManager.ajustaAmbienteFuncao(historicoColaborador);

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

		historicoColaboradorManager.ajustaAmbienteFuncao(historicoColaborador);

		assertNull(historicoColaborador.getAmbiente());
		assertNull(historicoColaborador.getFuncao());
	}
	
	public void testGetPrimeiroHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaboradorDao.expects(once()).method("getPrimeiroHistorico").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
		historicoColaboradorManager.getPrimeiroHistorico(colaborador.getId());
		
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

		boolean retorno = historicoColaboradorManager.existeHistoricoAprovado(historicoColaborador.getId(), colaborador.getId());

		assertTrue(retorno);
	}

	public void testFindColaboradorCodigoAC()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("0025");
		colaborador.setHistoricoColaborador(historicoColaborador);

		historicoColaboradorDao.expects(once()).method("findColaboradorCodigoAC").with(eq(historicoColaborador.getId())).will(returnValue(colaborador.getCodigoAC()));

		String retorno = historicoColaboradorManager.findColaboradorCodigoAC(historicoColaborador.getId());

		assertEquals(colaborador.getCodigoAC(), retorno);
	}

	public void testGetHistoricoAtualOuFuturo()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);

		historicoColaboradorDao.expects(once()).method("getHistoricoAtual").with(eq(colaborador.getId()), eq(TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO)).will(returnValue(historicoColaborador));

		HistoricoColaborador retorno = historicoColaboradorManager.getHistoricoAtualOuFuturo(colaborador.getId());

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

		HistoricoColaborador retorno = historicoColaboradorManager.getHistoricoContratacaoAguardando(colaborador.getId());

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

		assertEquals(true, historicoColaboradorManager.verificaDataPrimeiroHistorico(colaborador));

		//Mesma data
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(02, 02, 2000));
		historicoColaborador.setData(DateUtil.criarDataMesAno(02, 02, 2000));

		historicoColaboradorDao.expects(once()).method("getPrimeiroHistorico").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
		assertEquals(false, historicoColaboradorManager.verificaDataPrimeiroHistorico(colaborador));

		//Historico depois da data de admissao
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1999));
		historicoColaborador.setData(DateUtil.criarDataMesAno(02, 02, 2000));

		historicoColaboradorDao.expects(once()).method("getPrimeiroHistorico").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
		assertEquals(false, historicoColaboradorManager.verificaDataPrimeiroHistorico(colaborador));
	}

	public void testVerificaPrimeiroHistoricoAdmissao()
	{
		//Historico antes da data de admissao, editarHistorico = true
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(02, 02, 2000));

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 1999));
		historicoColaborador.setColaborador(colaborador);

		assertEquals(true, historicoColaboradorManager.verificaPrimeiroHistoricoAdmissao(true, historicoColaborador, colaborador));

		//Historico depois da data de admissao, editarHistorico = true
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1999));
		historicoColaborador.setData(DateUtil.criarDataMesAno(02, 02, 2000));

		assertEquals(false, historicoColaboradorManager.verificaPrimeiroHistoricoAdmissao(true, historicoColaborador, colaborador));

		//Historico depois da data de admissao, editarHistorico = false
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1999));
		historicoColaborador.setData(DateUtil.criarDataMesAno(02, 02, 2000));

		historicoColaboradorDao.expects(once()).method("getPrimeiroHistorico").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
		assertEquals(false, historicoColaboradorManager.verificaPrimeiroHistoricoAdmissao(false, historicoColaborador, colaborador));
	}

	public void testUpdateSituacao() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);

		String empresaCodigoAC = "001";
		TSituacao situacao = new TSituacao();
		situacao.setTipoSalario("C");
		situacao.setId(1);
		situacao.setEmpresaCodigoAC(empresaCodigoAC);

		historicoColaboradorDao.expects(once()).method("findByAC").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(historicoColaborador));
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));
		historicoColaboradorDao.expects(once()).method("update").with(ANYTHING);

		HistoricoColaborador retorno = historicoColaboradorManager.updateSituacao(situacao);

		assertEquals(historicoColaborador.getId(), retorno.getId());
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
		estabelecimentoManager.expects(once()).method("findEstabelecimentoByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));
		faixaSalarialManager.expects(once()).method("findFaixaSalarialByCodigoAc").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));

		HistoricoColaborador historicoColaborador = historicoColaboradorManager.prepareSituacao(situacao);
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

		TSituacao situacao = historicoColaboradorManager.bindSituacao(historicoColaborador, "");

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

		situacao = historicoColaboradorManager.bindSituacao(historicoColaborador, "");

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

		situacao = historicoColaboradorManager.bindSituacao(historicoColaborador, "empresaCodigoAC");

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
		
		assertTrue(historicoColaboradorManager.verifyDataHistoricoAdmissao(1L));
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
		
		assertEquals(2, historicoColaboradorManager.findAllByColaborador(1L).size());
	}
	
	public void testGetHistoricosComAmbienteEFuncao()
	{
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
		historicoColaborador1.setCargoId(1L);
		
		Collection<HistoricoColaborador> historicoColaboradors = Arrays.asList(historicoColaborador1);

		
		historicoColaboradorDao.expects(once()).method("findAllByColaborador").with(eq(1L)).will(returnValue(historicoColaboradors));
		ambienteManager.expects(once()).method("findByEstabelecimento").with(eq(1L)).will(returnValue(new ArrayList<Ambiente>()));
		funcaoManager.expects(once()).method("findByCargo").with(eq(1L)).will(returnValue(new ArrayList<Funcao>()));
		
		assertEquals(1, historicoColaboradorManager.getHistoricosComAmbienteEFuncao(1L).size());
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
		
		historicoColaboradorManager.updateAmbientesEFuncoes(historicoColaboradors);
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
			historicoColaboradorManager.updateAmbientesEFuncoes(historicoColaboradors);
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
		historicoColaboradorManager.montaRelatorioSituacoes(1L, dataIni, dataFim, estabelecimentosIds, areasIds, "RH", 'A', false);
	}
	
	public void testExisteDependenciaComHistoricoIndiceComNenhumHistoricoIndice() 
	{
		Date dataHistoricoExcluir = new Date();
		Long indiceId = 1L;
		
		indiceHistoricoManager.expects(once()).method("findToList").will(returnValue(new ArrayList<IndiceHistorico>()));
		
		boolean existeDependencia = historicoColaboradorManager.existeDependenciaComHistoricoIndice(dataHistoricoExcluir, indiceId);
		
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
		
		boolean existeDependencia = historicoColaboradorManager.existeDependenciaComHistoricoIndice(dataHistoricoExcluir, indiceId);
		
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
		
		boolean existeDependencia = historicoColaboradorManager.existeDependenciaComHistoricoIndice(dataHistoricoExcluir, indiceId);
		
		assertTrue(existeDependencia);
	}
	
	public void testEnfileirarHistoricosPC() {
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setCodigoAC("001");

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setCodigoAC("002");

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCodigoAC("003");

		Indice indice = IndiceFactory.getEntity(1L);
		indice.setCodigoAC("004");
		
		Pessoal pessoal1 = new Pessoal();
		pessoal1.setCpf("cpf1");

		Pessoal pessoal2 = new Pessoal();
		pessoal2.setCpf("cpf2");
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.setNome("colaborador1");
		colaborador1.setPessoal(pessoal1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setNome("colaborador2");
		colaborador2.setPessoal(pessoal2);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador1.setEstabelecimento(estabelecimento);
		historicoColaborador1.setFaixaSalarial(faixaSalarial);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(1, 2, 2014));
		historicoColaborador1.setIndice(null);
		historicoColaborador1.setSalario(123.45);
		historicoColaborador1.setQuantidadeIndice(null);
		historicoColaborador1.setColaborador(colaborador1);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaborador2.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setFaixaSalarial(faixaSalarial);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(2, 4, 2014));
		historicoColaborador2.setIndice(null);
		historicoColaborador2.setSalario(700.0);
		historicoColaborador2.setQuantidadeIndice(null);
		historicoColaborador2.setColaborador(colaborador1);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity(3L);
		historicoColaborador3.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador3.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador3.setEstabelecimento(estabelecimento);
		historicoColaborador3.setFaixaSalarial(faixaSalarial);
		historicoColaborador3.setData(DateUtil.criarDataMesAno(5, 6, 2014));
		historicoColaborador3.setIndice(null);
		historicoColaborador3.setSalario(800.0);
		historicoColaborador3.setQuantidadeIndice(null);
		historicoColaborador3.setColaborador(colaborador2);
		
		List<HistoricoColaborador> historicos = Arrays.asList(historicoColaborador1, historicoColaborador2, historicoColaborador3);
		
		historicoColaboradorDao.expects(once()).method("findPendenciasPortal").will(returnValue(historicos));
		transacaoPCManager.expects(atLeastOnce()).method("enfileirar").withAnyArguments().isVoid();
		
		Set<ColaboradorPC> colaboradoresPC = historicoColaboradorManager.enfileirarHistoricosPC();
		assertEquals(2, colaboradoresPC.size());
		assertEquals(2, ((ColaboradorPC) colaboradoresPC.toArray()[0]).getHistoricosPc().size());
		assertEquals(1, ((ColaboradorPC) colaboradoresPC.toArray()[1]).getHistoricosPc().size());
	}
}
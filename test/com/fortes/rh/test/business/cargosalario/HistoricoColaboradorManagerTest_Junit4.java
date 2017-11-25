package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManagerImpl;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;
import com.fortes.rh.web.ws.AcPessoalClientTabelaReajuste;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringUtil.class})
public class HistoricoColaboradorManagerTest_Junit4
{
	private HistoricoColaboradorManagerImpl historicoColaboradorManager = new HistoricoColaboradorManagerImpl();
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private AcPessoalClientTabelaReajuste acPessoalClientTabelaReajuste;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private AcPessoalClientColaborador acPessoalClientColaborador; 
	private ReajusteColaboradorManager reajusteColaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private EstabelecimentoManager estabelecimentoManager;
	private FaixaSalarialManager faixaSalarialManager;
	private SolicitacaoManager solicitacaoManager;
	private ColaboradorManager colaboradorManager;
	private HibernateTemplate hibernateTemplate;
	private AmbienteManager ambienteManager;
	private EmpresaManager empresaManager;
	private FuncaoManager funcaoManager;
	
	@Before
	public void setUp() throws Exception {
		
        historicoColaboradorDao = mock(HistoricoColaboradorDao.class);
        historicoColaboradorManager.setDao(historicoColaboradorDao);

		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		historicoColaboradorManager.setAreaOrganizacionalManager(areaOrganizacionalManager);

		faixaSalarialManager = mock(FaixaSalarialManager.class);
		historicoColaboradorManager.setFaixaSalarialManager(faixaSalarialManager);

		candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
		historicoColaboradorManager.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);

		gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		historicoColaboradorManager.setGerenciadorComunicacaoManager(gerenciadorComunicacaoManager);

		acPessoalClientColaborador = mock(AcPessoalClientColaborador.class);
		historicoColaboradorManager.setAcPessoalClientColaborador(acPessoalClientColaborador);

		reajusteColaboradorManager = mock(ReajusteColaboradorManager.class);
		historicoColaboradorManager.setReajusteColaboradorManager(reajusteColaboradorManager);
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		historicoColaboradorManager.setEstabelecimentoManager(estabelecimentoManager);

		solicitacaoManager = mock(SolicitacaoManager.class);
		historicoColaboradorManager.setSolicitacaoManager(solicitacaoManager);
		
		empresaManager = mock(EmpresaManager.class);
		historicoColaboradorManager.setEmpresaManager(empresaManager);

		acPessoalClientTabelaReajuste = mock(AcPessoalClientTabelaReajuste.class);
		historicoColaboradorManager.setAcPessoalClientTabelaReajuste(acPessoalClientTabelaReajuste);
		
		PowerMockito.mockStatic(SpringUtil.class);
		
		colaboradorManager = mock(ColaboradorManager.class);
		ambienteManager = mock(AmbienteManager.class);
		funcaoManager = mock(FuncaoManager.class);
		
		hibernateTemplate = mock(HibernateTemplate.class);
		SessionFactory sessionFactory = PowerMockito.mock(SessionFactory.class);
		hibernateTemplate.setSessionFactory(sessionFactory);
		
		BDDMockito.given(SpringUtil.getBean("colaboradorManager")).willReturn(colaboradorManager);
		BDDMockito.given(SpringUtil.getBean("ambienteManager")).willReturn(ambienteManager);
		BDDMockito.given(SpringUtil.getBean("funcaoManager")).willReturn(funcaoManager);
	}

	@Test
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

		when(historicoColaboradorDao.findByIdProjectionHistorico(historicoColaborador.getId())).thenReturn(historicoColaborador);
		when(historicoColaboradorDao.setStatus(historicoColaborador.getId(), false)).thenReturn(true);
		
		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorManager.cancelarSituacao(situacao, mensagem);

		assertEquals(historicoColaborador, historicoColaboradorRetorno);
	}

	@Test
	public void  testCancelarSituacaoSEP() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setSolPessoalReabrirSolicitacao(true);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setSalario(200.0);
		historicoColaborador.setCandidatoSolicitacao(candidatoSolicitacao);

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

		when(historicoColaboradorDao.findByAC(any(Date.class), anyString(), anyString(), anyString())).thenReturn(historicoColaborador);
		when(estabelecimentoManager.findEstabelecimentoByCodigoAc(anyString(), anyString(), anyString())).thenReturn(estabelecimento);
		when(areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(anyString(), anyString(), anyString())).thenReturn(areaOrganizacional);
		when(faixaSalarialManager.findFaixaSalarialByCodigoAc(anyString(), anyString(), anyString())).thenReturn(faixaSalarial);
		when(empresaManager.findByIdProjection(historicoColaborador.getColaborador().getEmpresa().getId())).thenReturn(empresa);
		when(candidatoSolicitacaoManager.findByCandidatoSolicitacao(historicoColaborador.getCandidatoSolicitacao())).thenReturn(candidatoSolicitacao);

		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorManager.cancelarSituacao(situacao, mensagem);
		assertEquals(historicoColaborador, historicoColaboradorRetorno);
	}
	
    @Test
    public void testAtualizarHistoricoContratacaoHistoricoNaoEncontradoNoRH() {
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);

        HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
        historicoColaborador.setColaborador(colaborador);
        TSituacao situacao = montaTSituacao();

        when(historicoColaboradorDao.findHistoricoMotivoContratacao(situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(null);

        String msg = "";
        try {
            historicoColaboradorManager.atualizarHistoricoContratacao(situacao);
        } catch (Exception e) {
            msg = e.getMessage();
        }
        assertEquals(msg, "Situação não encontrada no Fortes RH.");
    }

    @Test
    public void testAtualizarHistoricoContratacaoEstabelecimentoNaoExisteNoRH() {
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);

        HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
        historicoColaborador.setColaborador(colaborador);
        TSituacao situacao = montaTSituacao();

        when(historicoColaboradorDao.findHistoricoMotivoContratacao(situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(
                historicoColaborador);
        when(estabelecimentoManager.findEstabelecimentoByCodigoAc(situacao.getEstabelecimentoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(null);

        String msg = "";
        try {
            historicoColaboradorManager.atualizarHistoricoContratacao(situacao);
        } catch (Exception e) {
            msg = e.getMessage();
        }

        assertEquals(msg, "Não foi possível realizar a operação. Estabelecimento não existe no RH.");
    }

    @Test
    public void testAtualizarHistoricoContratacaoAreaOrganizacionalNaoExisteNoRH() {
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);

        HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
        historicoColaborador.setColaborador(colaborador);
        TSituacao situacao = montaTSituacao();

        when(historicoColaboradorDao.findHistoricoMotivoContratacao(situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(
                historicoColaborador);
        when(estabelecimentoManager.findEstabelecimentoByCodigoAc(situacao.getEstabelecimentoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(
                EstabelecimentoFactory.getEntity(1L));
        when(areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(situacao.getLotacaoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(null);

        String msg = "";
        try {
            historicoColaboradorManager.atualizarHistoricoContratacao(situacao);
        } catch (Exception e) {
            msg = e.getMessage();
        }

        assertEquals(msg, "Não foi possível realizar a operação. Área organizacional não existe no RH.");
    }

    @Test
    public void testAtualizarHistoricoContratacaoFaixaSalarialNaoExisteNoRH() throws Exception {
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);

        HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
        historicoColaborador.setColaborador(colaborador);
        TSituacao situacao = montaTSituacao();

        when(historicoColaboradorDao.findHistoricoMotivoContratacao(situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(
                historicoColaborador);
        when(estabelecimentoManager.findEstabelecimentoByCodigoAc(situacao.getEstabelecimentoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(
                EstabelecimentoFactory.getEntity(1L));
        when(areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(situacao.getLotacaoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(
                AreaOrganizacionalFactory.getEntity(2L));
        when(faixaSalarialManager.findFaixaSalarialByCodigoAc(situacao.getCargoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(null);

        String msg = "";
        try {
            historicoColaboradorManager.atualizarHistoricoContratacao(situacao);
        } catch (Exception e) {
            msg = e.getMessage();
        }
        assertEquals(msg, "Não foi possível realizar a operação. Faixa salarial não existe no RH.");
    }

    @Test
    public void testAtualizarHistoricoContratacaoSucesso() throws Exception {
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);

        HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
        historicoColaborador.setColaborador(colaborador);
        TSituacao situacao = montaTSituacao();

        when(historicoColaboradorDao.findHistoricoMotivoContratacao(situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(
                historicoColaborador);
        when(estabelecimentoManager.findEstabelecimentoByCodigoAc(situacao.getEstabelecimentoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(
                EstabelecimentoFactory.getEntity(1L));
        when(areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(situacao.getLotacaoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(
                AreaOrganizacionalFactory.getEntity(2L));
        when(faixaSalarialManager.findFaixaSalarialByCodigoAc(situacao.getCargoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC())).thenReturn(
                FaixaSalarialFactory.getEntity(1L));

        Exception exception = null;
        try {
            historicoColaboradorManager.atualizarHistoricoContratacao(situacao);
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);
    }

    private TSituacao montaTSituacao() {
        String empresaCodigoAC = "001";
        TSituacao situacao = new TSituacao();
        situacao.setTipoSalario("C");
        situacao.setId(1);
        situacao.setEmpresaCodigoAC(empresaCodigoAC);
        situacao.setEstabelecimentoCodigoAC("0001");
        situacao.setLotacaoCodigoAC("0001");
        situacao.setCargoCodigoAC("0003");
        return situacao;
    }
    
	@Test
	public void testExisteHistoricoConfirmadoByTabelaReajusteColaborador() {
		Long tabelaReajusteColaboradorId = 2L;
		when(historicoColaboradorDao.existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaboradorId)).thenReturn(false);
		
		boolean retorno = historicoColaboradorManager.existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaboradorId);
		verify(historicoColaboradorDao, times(1)).existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaboradorId);
		assertFalse(retorno);
    }
	
	@Test
	public void testIsUltimoHistoricoOrPosteriorAoUltimo() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L, "Empresa", "000001", "001");
		String empregadoCodigoAC = "000001";
		Date data = new Date(); 
		
		when(historicoColaboradorDao.isUltimoHistoricoOrPosteriorAoUltimo(data, empregadoCodigoAC, empresa.getCodigoAC(), empresa.getGrupoAC())).thenReturn(true);
		assertTrue(historicoColaboradorManager.isUltimoHistoricoOrPosteriorAoUltimo(data, empregadoCodigoAC, empresa.getCodigoAC(), empresa.getGrupoAC()));
	}
	
	@Test
	public void testRemoveHistoricoAndReajuste()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		DateUtil.criarDataMesAno(02, 02, 2000);
		colaborador.setCodigoAC("43232342");

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(03, 02, 2000));
		historicoColaborador.setReajusteColaborador(reajusteColaborador);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(03, 02, 2005));

		Collection<HistoricoColaborador> historicoColaboradors = Arrays.asList(historicoColaborador, historicoColaborador2);

		when(historicoColaboradorDao.findByIdProjection(eq(historicoColaborador.getId()))).thenReturn(historicoColaborador);
		when(historicoColaboradorDao.findHistoricoAprovado(eq(historicoColaborador.getId()), eq(colaborador.getId()))).thenReturn(historicoColaboradors);
		when(acPessoalClientColaborador.verificaHistoricoNaFolhaAC(historicoColaborador.getId(), colaborador.getCodigoAC(), empresa)).thenReturn(false);
		when(historicoColaboradorDao.findByColaboradorProjection(eq(colaborador.getId()), eq(StatusRetornoAC.CONFIRMADO))).thenReturn(historicoColaboradors);
		when(historicoColaboradorDao.findReajusteByHistoricoColaborador(eq(historicoColaborador.getId()))).thenReturn(1L);
		when(historicoColaboradorDao.getHibernateTemplateByGenericDao()).thenReturn(hibernateTemplate);
		when(colaboradorManager.findColaboradorByIdProjection(eq(colaborador.getId()))).thenReturn(colaborador);
		
		Exception exception = null;
		try
		{
			historicoColaboradorManager.removeHistoricoAndReajuste(historicoColaborador.getId(), colaborador.getId(), empresa, true);
		}
		catch (Exception e)
		{
			exception = e;
			e.printStackTrace();
		}

		assertNull(exception);
	}

	@Test
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

		when(historicoColaboradorDao.findByIdProjection(eq(historicoColaborador.getId()))).thenReturn(null);

		Exception exception = null;
		try
		{
			historicoColaboradorManager.removeHistoricoAndReajuste(historicoColaborador.getId(), colaborador.getId(), empresa, true);
		}
		catch (Exception e)
		{
			exception = e;
			e.printStackTrace();
		}

		assertNotNull(exception);
	}
	
	@Test
	public void testFiltraHistoricoColaboradorParaPPP() throws Exception{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L, "F1", cargo);
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);
		
		HistoricoColaborador historicoColaboradorComAmbienteNulo = HistoricoColaboradorFactory.getEntity(colaborador, faixaSalarial, new Date(), StatusRetornoAC.CONFIRMADO);
		historicoColaboradorComAmbienteNulo.setEstabelecimento(estabelecimento);
		
		HistoricoColaborador historicoColaboradorComFuncaoNula = HistoricoColaboradorFactory.getEntity(colaborador, faixaSalarial, new Date(), StatusRetornoAC.CONFIRMADO);
		historicoColaboradorComFuncaoNula.setAmbiente(ambiente);
		historicoColaboradorComFuncaoNula.setEstabelecimento(estabelecimento);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, faixaSalarial, new Date(), StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);
		
		Collection<HistoricoColaborador> historicos = Arrays.asList(historicoColaborador, historicoColaboradorComAmbienteNulo, historicoColaboradorComFuncaoNula);
		
		Collection<HistoricoColaborador> retorno = historicoColaboradorManager.filtraHistoricoColaboradorParaPPP(historicos);
		
		assertEquals(1, retorno.size());
	}
	
	@Test
	public void testGetHistoricosComAmbienteEFuncao()
	{
		Long empresaId = 1L;
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setNome("Estabelecimento");
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L, EmpresaFactory.getEmpresa(1L));
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setData(new Date());
		historicoColaborador1.setEstabelecimento(estabelecimento);
		historicoColaborador1.setCargoId(1L);
		
		Collection<HistoricoColaborador> historicoColaboradors = Arrays.asList(historicoColaborador1);
		
		when(historicoColaboradorDao.findAllByColaborador(eq(1L))).thenReturn(historicoColaboradors);
		when(ambienteManager.montaMapAmbientes(eq(empresaId), eq(historicoColaborador1.getEstabelecimento().getId()),
				eq(historicoColaborador1.getEstabelecimento().getNome()), eq(historicoColaborador1.getData()))).thenReturn(new LinkedHashMap<String, Collection<Ambiente>>());
		
		assertEquals(1, historicoColaboradorManager.getHistoricosComAmbienteEFuncao(1L, empresaId).size());
	}
}
package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockHibernateTemplate;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.SpringUtil;

public class HistoricoColaboradorManagerTest_Junit4
{
	private HistoricoColaboradorManagerImpl historicoColaboradorManagerImpl = new HistoricoColaboradorManagerImpl();
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private EstabelecimentoManager estabelecimentoManager;
	private FaixaSalarialManager faixaSalarialManager;
	private SolicitacaoManager solicitacaoManager;
	private EmpresaManager empresaManager;
	
	@Before
	public void setUp() throws Exception {
		
        historicoColaboradorDao = mock(HistoricoColaboradorDao.class);
        historicoColaboradorManagerImpl.setDao(historicoColaboradorDao);

		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		historicoColaboradorManagerImpl.setAreaOrganizacionalManager(areaOrganizacionalManager);

		faixaSalarialManager = mock(FaixaSalarialManager.class);
		historicoColaboradorManagerImpl.setFaixaSalarialManager(faixaSalarialManager);

		candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
		historicoColaboradorManagerImpl.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);

		gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		historicoColaboradorManagerImpl.setGerenciadorComunicacaoManager(gerenciadorComunicacaoManager);

		estabelecimentoManager = mock(EstabelecimentoManager.class);
		historicoColaboradorManagerImpl.setEstabelecimentoManager(estabelecimentoManager);

		solicitacaoManager = mock(SolicitacaoManager.class);
		historicoColaboradorManagerImpl.setSolicitacaoManager(solicitacaoManager);
		
		empresaManager = mock(EmpresaManager.class);
		historicoColaboradorManagerImpl.setEmpresaManager(empresaManager);
		
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
		Mockit.redefineMethods(HibernateTemplate.class, MockHibernateTemplate.class);
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
		
		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorManagerImpl.cancelarSituacao(situacao, mensagem);

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

		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorManagerImpl.cancelarSituacao(situacao, mensagem);
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
            historicoColaboradorManagerImpl.atualizarHistoricoContratacao(situacao);
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
            historicoColaboradorManagerImpl.atualizarHistoricoContratacao(situacao);
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
            historicoColaboradorManagerImpl.atualizarHistoricoContratacao(situacao);
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
            historicoColaboradorManagerImpl.atualizarHistoricoContratacao(situacao);
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
            historicoColaboradorManagerImpl.atualizarHistoricoContratacao(situacao);
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
}
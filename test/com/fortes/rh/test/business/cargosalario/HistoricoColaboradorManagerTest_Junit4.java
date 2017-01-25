package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.assertEquals;
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
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
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
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setSalario(200.0);
		historicoColaborador.setCandidatoSolicitacao(CandidatoSolicitacaoFactory.getEntity(1L));

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

		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorManagerImpl.cancelarSituacao(situacao, mensagem);
		assertEquals(historicoColaborador, historicoColaboradorRetorno);
	}
}
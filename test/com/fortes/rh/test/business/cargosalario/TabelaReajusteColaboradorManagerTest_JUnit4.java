package com.fortes.rh.test.business.cargosalario;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManagerImpl;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.web.ws.AcPessoalClientTabelaReajusteInterface;

public class TabelaReajusteColaboradorManagerTest_JUnit4
{
	TabelaReajusteColaboradorManagerImpl tabelaReajusteColaboradorManager = new TabelaReajusteColaboradorManagerImpl();
	HistoricoColaboradorManager historicoColaboradorManager;
	TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	AcPessoalClientTabelaReajusteInterface acPessoalClientTabelaReajuste;
	HibernateTemplate hibernateTemplate;

	@Before
	public void setUp() throws Exception
	{
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
		tabelaReajusteColaboradorDao = mock(TabelaReajusteColaboradorDao.class);
		faixaSalarialHistoricoManager = mock(FaixaSalarialHistoricoManager.class);
		acPessoalClientTabelaReajuste = mock(AcPessoalClientTabelaReajusteInterface.class);

		tabelaReajusteColaboradorManager.setDao(tabelaReajusteColaboradorDao);
		tabelaReajusteColaboradorManager.setHistoricoColaboradorManager(historicoColaboradorManager);
		tabelaReajusteColaboradorManager.setFaixaSalarialHistoricoManager(faixaSalarialHistoricoManager);
		tabelaReajusteColaboradorManager.setAcPessoalClientTabelaReajuste(acPessoalClientTabelaReajuste);
		
		hibernateTemplate = mock(HibernateTemplate.class);
	}
	
	@Test
	public void testMarcaUltima()
	{
		TabelaReajusteColaborador tabelaReajusteColaboradorTipoColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaboradorTipoColaborador.setAprovada(true);
		
		TabelaReajusteColaborador tabelaReajusteColaboradorTipoFaixa = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaboradorTipoFaixa.setAprovada(true);
		tabelaReajusteColaboradorTipoFaixa.setTipoReajuste(TipoReajuste.FAIXA_SALARIAL);
		
		TabelaReajusteColaborador tabelaReajusteColaboradorTipoIndice = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaboradorTipoIndice.setAprovada(true);
		tabelaReajusteColaboradorTipoIndice.setTipoReajuste(TipoReajuste.INDICE);

		Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = Arrays.asList(tabelaReajusteColaboradorTipoColaborador, tabelaReajusteColaboradorTipoFaixa, tabelaReajusteColaboradorTipoIndice);

		Exception exception = null;
		try {
			tabelaReajusteColaboradorManager.marcaUltima(tabelaReajusteColaboradors, true);
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	@Test
	public void testCancelarReajusteTipoColaborador() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		tabelaReajusteColaborador.setEmpresa(empresa);
		tabelaReajusteColaborador.setAprovada(false);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setHistoricoColaborador(historicoColaborador);
		colaborador.setNaoIntegraAc(false);

		Collection<TSituacao> situacaoIntegrados = new ArrayList<TSituacao>();
		TSituacao situacao = new TSituacao();
		situacao.setId(1);
		situacao.setEmpregadoCodigoAC("1");
		situacaoIntegrados.add(situacao);

		when(historicoColaboradorManager.findHistoricosByTabelaReajuste(eq(tabelaReajusteColaborador.getId()), eq(empresa))).thenReturn(situacaoIntegrados);
		when(tabelaReajusteColaboradorDao.getHibernateTemplateByGenericDao()).thenReturn(hibernateTemplate);
		
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.cancelar(TipoReajuste.COLABORADOR, tabelaReajusteColaborador.getId(), empresa, false);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}
	
	@Test
	public void testCancelarComColaboradorNaoIntegadoAC() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		Collection<TSituacao> situacaoIntegrados = new ArrayList<TSituacao>();
		TSituacao situacao = new TSituacao();
		situacao.setId(1);
		situacao.setEmpregadoCodigoAC("1");
		situacaoIntegrados.add(situacao);
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		tabelaReajusteColaborador.setEmpresa(empresa);
		tabelaReajusteColaborador.setAprovada(false);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setHistoricoColaborador(historicoColaborador);

		when(historicoColaboradorManager.existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaborador.getId())).thenReturn(false);
		when(historicoColaboradorManager.findHistoricosByTabelaReajuste(eq(tabelaReajusteColaborador.getId()), eq(empresa))).thenReturn(situacaoIntegrados);
		when(tabelaReajusteColaboradorDao.getHibernateTemplateByGenericDao()).thenReturn(hibernateTemplate);
		doThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))).when(acPessoalClientTabelaReajuste).deleteHistoricoColaboradorAC(eq(empresa), any(TSituacao.class));
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.cancelar(TipoReajuste.COLABORADOR, tabelaReajusteColaborador.getId(), empresa, true);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	@Test
	public void testCancelarReajusteDoColaboradorComEmpresaIntegradaEAderiuAoESocialEPossuiHistoricoConfirmado() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);

		when(historicoColaboradorManager.existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaborador.getId())).thenReturn(true);

		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.cancelar(TipoReajuste.COLABORADOR, tabelaReajusteColaborador.getId(), empresa, true);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	@Test
	public void testCancelarReajusteTipoFaixa() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		tabelaReajusteColaborador.setEmpresa(empresa);
		tabelaReajusteColaborador.setAprovada(false);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = Arrays.asList(faixaSalarialHistorico);
		
		when(faixaSalarialHistoricoManager.existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaborador.getId())).thenReturn(false);
		when(faixaSalarialHistoricoManager.findByTabelaReajusteId(tabelaReajusteColaborador.getId())).thenReturn(faixaSalarialHistoricos);
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.cancelar(TipoReajuste.FAIXA_SALARIAL, tabelaReajusteColaborador.getId(), empresa, true);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}
	
	@Test
	public void testCancelarReajusteTipoFaixaComEmpresaIntegradaEAderiuAoESocialEPossuiHistoricoConfirmado() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		tabelaReajusteColaborador.setEmpresa(empresa);
		tabelaReajusteColaborador.setAprovada(false);

		when(faixaSalarialHistoricoManager.existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaborador.getId())).thenReturn(true);
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.cancelar(TipoReajuste.FAIXA_SALARIAL, tabelaReajusteColaborador.getId(), empresa, true);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
}
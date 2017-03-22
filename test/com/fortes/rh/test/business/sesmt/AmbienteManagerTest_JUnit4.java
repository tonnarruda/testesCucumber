package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManagerImpl;
import com.fortes.rh.business.sesmt.ComposicaoSesmtManager;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoMedicaoRiscoManager;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatCabecalho;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatRelatorio;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoAmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoFuncaoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.util.DateUtil;

public class AmbienteManagerTest_JUnit4
{
	private AmbienteManagerImpl ambienteManager = new AmbienteManagerImpl();
	private AmbienteDao ambienteDao = null;
	private HistoricoAmbienteManager historicoAmbienteManager = null;
	private EstabelecimentoManager estabelecimentoManager;
	private FuncaoManager funcaoManager;
	private RiscoMedicaoRiscoManager riscoMedicaoRiscoManager;
	private EpcManager epcManager;
	private EpiManager epiManager;
	private ComposicaoSesmtManager composicaoSesmtManager;
	private EmpresaManager empresaManager;

	@Before
	public void setUp() throws Exception
    {
        ambienteDao = mock(AmbienteDao.class);
        ambienteManager.setDao(ambienteDao);

        historicoAmbienteManager = mock(HistoricoAmbienteManager.class);
        ambienteManager.setHistoricoAmbienteManager(historicoAmbienteManager);
        
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        ambienteManager.setEstabelecimentoManager(estabelecimentoManager);
        
        funcaoManager = mock(FuncaoManager.class);
        ambienteManager.setFuncaoManager(funcaoManager);
        
        riscoMedicaoRiscoManager = mock(RiscoMedicaoRiscoManager.class);
        ambienteManager.setRiscoMedicaoRiscoManager(riscoMedicaoRiscoManager);
        
        epcManager = mock(EpcManager.class);
        ambienteManager.setEpcManager(epcManager);
        
        epiManager = mock(EpiManager.class);
        ambienteManager.setEpiManager(epiManager);

        composicaoSesmtManager = mock(ComposicaoSesmtManager.class);
        ambienteManager.setComposicaoSesmtManager(composicaoSesmtManager);
        
        empresaManager = mock(EmpresaManager.class);
        ambienteManager.setEmpresaManager(empresaManager);
    }
	
	@Test
	public void testSincronizar(){
		
		Empresa empresaOrigem = EmpresaFactory.getEmpresa(1L);
		Empresa empresaDestino = EmpresaFactory.getEmpresa(2L);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento);
		
		HistoricoAmbiente ha1 = HistoricoAmbienteFactory.getEntity("ha1", null, DateUtil.criarDataMesAno(1, 1, 2000), "tempoExposicao hc1");
		HistoricoAmbiente ha2 = HistoricoAmbienteFactory.getEntity("ha2", null, DateUtil.criarDataMesAno(1, 2, 2000), "tempoExposicao hc2");
		
		Ambiente a1 = new Ambiente();
		a1.setHistoricoAtual(ha1);
		a1.setNome("A1");

		Ambiente a2 = new Ambiente();
		a2.setHistoricoAtual(ha2);
		a2.setNome("F2");

		Collection<Ambiente> ambientes = Arrays.asList(a1, a2);
		
		when(ambienteDao.findAllByEmpresa(empresaOrigem.getId())).thenReturn(ambientes);
		when(estabelecimentoManager.findByEmpresa(empresaDestino.getId())).thenReturn(estabelecimentos);
		
		Exception ex = null;
		try {
			ambienteManager.sincronizar(empresaOrigem.getId(), empresaDestino.getId());
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	}
	
	@Test
	public void testPopulaRelatorioPorFuncaoComComposicaoSesmt() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(2L);

		Epi epi1 = EpiFactory.getEntity(1L, "Epi 1", empresa);
		Epi epi2 = EpiFactory.getEntity(2L, "Epi 2", empresa);

		Collection<Epi> listEpis = Arrays.asList(epi1, epi2);
		
		HistoricoFuncao historicoFuncao = HistoricoFuncaoFactory.getEntity("Piso de concreto, paredes de aço, teto de madeira.", listEpis);
		
		Funcao funcao = FuncaoFactory.getEntity(null, "Função");
		funcao.setHistoricoAtual(historicoFuncao);
		
		Collection<Funcao> funcoes = new ArrayList<Funcao>();
		funcoes.add(funcao);
		
		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity("Piso de concreto, paredes de aço, teto de madeira.", null, new Date(), null);
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity("Paredes revestidas com isolamento acústico.", null, new Date(), null);
		
		Ambiente ambiente1 = AmbienteFactory.getEntity(50L, "Ambiente1", historicoAmbiente, null);
		Ambiente ambiente2 = AmbienteFactory.getEntity(51L, "Ambiente2", historicoAmbiente2, null);
		
		Collection<Ambiente> ambientes = Arrays.asList(ambiente1, ambiente2);

		when(estabelecimentoManager.findById(empresa.getId())).thenReturn(estabelecimento);
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(false);
		when(ambienteDao.findByIds(anyCollectionOf(Long.class), any(Date.class), anyLong())).thenReturn(ambientes);
		when(composicaoSesmtManager.findByData(anyLong(), any(Date.class))).thenReturn(null);
		when(funcaoManager.findFuncoesDoAmbiente(anyLong(), any(Date.class))).thenReturn(funcoes);
		when(epcManager.findEpcsDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Epc>());
		when(epiManager.findEpisDoAmbiente(anyLong(), any(Date.class))).thenReturn(listEpis);
		when(riscoMedicaoRiscoManager.findMedicoesDeRiscosDaFuncao(anyLong(), any(Date.class))).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		when(ambienteDao.getQtdColaboradorByAmbiente(anyLong(), any(Date.class), eq(Sexo.MASCULINO), (Long) eq(null))).thenReturn(10);
		when(ambienteDao.getQtdColaboradorByAmbiente(anyLong(), any(Date.class), eq(Sexo.FEMININO), (Long) eq(null))).thenReturn(50);

		Date hoje = new Date();
		String[] ambienteCheck = new String[]{ambiente1.getId().toString(), ambiente2.getId().toString()};
		boolean gerarPpra = true;
		boolean gerarLtcat = true;
		boolean exibirComposicaoSesmt = true;
		
		Collection<PpraLtcatRelatorio> relatorios = ambienteManager.montaRelatorioPpraLtcat(empresa, estabelecimento.getId(), hoje, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);
		
		assertEquals(2, relatorios.size());
		assertEquals(60, ((PpraLtcatRelatorio) relatorios.toArray()[0]).getCabecalho().getQtdTotal().intValue());
	}

	@Test
	public void testPopulaRelatorioPorAmbienteSemComposicaoSesmt() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(2L);
		
		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity("Piso de concreto, paredes de aço, teto de madeira.", null, new Date(), null);
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity("Paredes revestidas com isolamento acústico.", null, new Date(), null);
		
		Collection<HistoricoAmbiente> historicoAmbientes = Arrays.asList(historicoAmbiente);
		
		Ambiente ambiente1 = AmbienteFactory.getEntity(50L, "Ambiente1", historicoAmbiente, historicoAmbientes);
		Ambiente ambiente2 = AmbienteFactory.getEntity(51L, "Ambiente2", historicoAmbiente2, null);
		
		Collection<Ambiente> ambientes = Arrays.asList(ambiente1, ambiente2);

		when(estabelecimentoManager.findById(empresa.getId())).thenReturn(estabelecimento);
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(true);
		when(ambienteDao.findByIds(anyCollectionOf(Long.class), any(Date.class), anyLong())).thenReturn(ambientes);
		when(funcaoManager.findFuncoesDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Funcao>());
		when(epcManager.findEpcsDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Epc>());
		when(epiManager.findEpisDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Epi>());
		when(riscoMedicaoRiscoManager.findMedicoesDeRiscosDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		when(ambienteDao.getQtdColaboradorByAmbiente(anyLong(), any(Date.class), eq(Sexo.MASCULINO), (Long) eq(null))).thenReturn(10);
		when(ambienteDao.getQtdColaboradorByAmbiente(anyLong(), any(Date.class), eq(Sexo.FEMININO), (Long) eq(null))).thenReturn(50);

		Date hoje = new Date();
		String[] ambienteCheck = new String[]{ambiente1.getId().toString(), ambiente2.getId().toString()};
		boolean gerarPpra = true;
		boolean gerarLtcat = true;
		boolean exibirComposicaoSesmt = false;
		
		Collection<PpraLtcatRelatorio> relatorios = ambienteManager.montaRelatorioPpraLtcat(empresa, estabelecimento.getId(), hoje, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);
		
		assertEquals(2, relatorios.size());
		assertEquals(60, ((PpraLtcatRelatorio) relatorios.toArray()[0]).getCabecalho().getQtdTotal().intValue());
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testPopulaRelatorioPorAmbienteSemAmbienteException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(2L);
		
		PpraLtcatCabecalho cabecalho = new PpraLtcatCabecalho();
		cabecalho.setEmpresa(empresa);
		cabecalho.setEstabelecimento(estabelecimento);
		
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		
		when(estabelecimentoManager.findById(empresa.getId())).thenReturn(estabelecimento);
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(true);
		when(ambienteDao.findByIds(anyCollectionOf(Long.class), any(Date.class), anyLong())).thenReturn(ambientes);
		
	    thrown.expect(ColecaoVaziaException.class);
	    thrown.expectMessage("Não existem dados para o filtro informado.");

		Ambiente ambiente1 = AmbienteFactory.getEntity(50L, "Ambiente1", null, null);
		Ambiente ambiente2 = AmbienteFactory.getEntity(51L, "Ambiente2", null, null);

		Date hoje = new Date();
		String[] ambienteCheck = new String[]{ambiente1.getId().toString(), ambiente2.getId().toString()};
		boolean gerarPpra = true;
		boolean gerarLtcat = true;
		boolean exibirComposicaoSesmt = false;
		
		ambienteManager.montaRelatorioPpraLtcat(empresa, estabelecimento.getId(), hoje, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);
		fail("ColecaoVaziaException não foi lançada.");
	}

	@Test
	public void testPopulaRelatorioPorFuncaoSemFuncaoVinculadaAoAmbienteException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(2L);
		
		Ambiente ambiente1 = AmbienteFactory.getEntity(50L, "Ambiente1", null, null);
		Ambiente ambiente2 = AmbienteFactory.getEntity(51L, "Ambiente2", null, null);
		
		Collection<Ambiente> ambientes = Arrays.asList(ambiente1, ambiente2);

		when(estabelecimentoManager.findById(empresa.getId())).thenReturn(estabelecimento);
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(false);
		when(ambienteDao.findByIds(anyCollectionOf(Long.class), any(Date.class), anyLong())).thenReturn(ambientes);
		when(composicaoSesmtManager.findByData(anyLong(), any(Date.class))).thenReturn(null);
		when(funcaoManager.findFuncoesDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Funcao>());

		thrown.expect(ColecaoVaziaException.class);
		thrown.expectMessage("Nos históricos dos colaboradores não existem funções vinculadas aos ambientes selecionados no filtro.");
		
		Date hoje = new Date();
		String[] ambienteCheck = new String[]{ambiente1.getId().toString(), ambiente2.getId().toString()};
		boolean gerarPpra = true;
		boolean gerarLtcat = true;
		boolean exibirComposicaoSesmt = true;
		
		ambienteManager.montaRelatorioPpraLtcat(empresa, estabelecimento.getId(), hoje, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);
		fail("ColecaoVaziaException não foi lançada.");
	}
}
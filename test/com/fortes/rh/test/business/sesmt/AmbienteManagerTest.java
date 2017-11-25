package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
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
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

public class AmbienteManagerTest
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
		Date hoje = new Date();
		boolean gerarPpra = true;
		boolean gerarLtcat = true;
		boolean exibirComposicaoSesmt = true;
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(2L);
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao();

		HistoricoAmbiente historicoAtualAmbiente1 = HistoricoAmbienteFactory.getEntity("Piso de concreto, paredes de aço, teto de madeira.", null, new Date(), null);
		HistoricoAmbiente historicoAtualAmbiente2 = HistoricoAmbienteFactory.getEntity("Paredes revestidas com isolamento acústico.", null, new Date(), null);

		Ambiente ambiente1 = AmbienteFactory.getEntity(50L, "Ambiente1", historicoAtualAmbiente1, null);
		Ambiente ambiente2 = AmbienteFactory.getEntity(51L, "Ambiente2", historicoAtualAmbiente2, null);
		Collection<Ambiente> ambientes = Arrays.asList(ambiente1, ambiente2);
		String[] ambienteCheck = new String[]{ambiente1.getId().toString(), ambiente2.getId().toString()};

		Collection<Epi> listEpis = Arrays.asList(EpiFactory.getEntity(1L, "Epi 1", empresa), EpiFactory.getEntity(2L, "Epi 2", empresa));
		
		HistoricoFuncao historicoFuncao = HistoricoFuncaoFactory.getEntity("Piso de concreto, paredes de aço, teto de madeira.", listEpis);
		
		Funcao funcao = FuncaoFactory.getEntity(null, "Função");
		funcao.setHistoricoAtual(historicoFuncao);
		
		Collection<Funcao> funcoes = Arrays.asList(funcao);
		
		when(estabelecimentoManager.findById(empresa.getId())).thenReturn(estabelecimento);
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(false);
		when(ambienteDao.findByIds(eq(empresa.getId()), eq(LongUtil.arrayStringToCollectionLong(ambienteCheck)), eq(hoje), eq(estabelecimento.getId()),eq(localAmbiente))).thenReturn(ambientes);
		when(composicaoSesmtManager.findByData(anyLong(), any(Date.class))).thenReturn(null);
		when(funcaoManager.findFuncoesDoAmbiente(anyLong(), any(Date.class))).thenReturn(funcoes);
		when(epcManager.findEpcsDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Epc>());
		when(epiManager.findEpisDoAmbiente(anyLong(), any(Date.class))).thenReturn(listEpis);
		when(riscoMedicaoRiscoManager.findMedicoesDeRiscosDaFuncao(anyLong(), any(Date.class))).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		when(ambienteDao.getQtdColaboradorByAmbiente(anyLong(), any(Date.class), eq(Sexo.MASCULINO), (Long) eq(null))).thenReturn(10);
		when(ambienteDao.getQtdColaboradorByAmbiente(anyLong(), any(Date.class), eq(Sexo.FEMININO), (Long) eq(null))).thenReturn(50);
		
		Collection<PpraLtcatRelatorio> relatorios = ambienteManager.montaRelatorioPpraLtcat(empresa, estabelecimento, localAmbiente, hoje, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);

		assertEquals(2, relatorios.size());
		assertEquals(60, ((PpraLtcatRelatorio) relatorios.toArray()[0]).getCabecalho().getQtdTotal().intValue());
	}

	@Test
	public void testPopulaRelatorioPorAmbienteSemComposicaoSesmt() throws Exception
	{
		Date hoje = new Date();
		boolean gerarPpra = true;
		boolean gerarLtcat = true;
		boolean exibirComposicaoSesmt = false;
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao();

		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Estabelecimento estabelecimento = new Estabelecimento();
		
		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity("Piso de concreto, paredes de aço, teto de madeira.", null, new Date(), null);
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity("Paredes revestidas com isolamento acústico.", null, new Date(), null);
		Collection<HistoricoAmbiente> historicoAmbientes = Arrays.asList(historicoAmbiente);
		
		Ambiente ambiente1 = AmbienteFactory.getEntity(50L, "Ambiente1", historicoAmbiente, historicoAmbientes);
		Ambiente ambiente2 = AmbienteFactory.getEntity(51L, "Ambiente2", historicoAmbiente2, null);
		
		Collection<Ambiente> ambientes = Arrays.asList(ambiente1, ambiente2);
		String[] ambienteCheck = new String[]{ambiente1.getId().toString(), ambiente2.getId().toString()};

		when(estabelecimentoManager.findById(empresa.getId())).thenReturn(estabelecimento);
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(true);
		when(ambienteDao.findByIds(eq(empresa.getId()), eq(LongUtil.arrayStringToCollectionLong(ambienteCheck)), eq(hoje), eq(estabelecimento.getId()),eq(localAmbiente))).thenReturn(ambientes);
		when(funcaoManager.findFuncoesDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Funcao>());
		when(epcManager.findEpcsDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Epc>());
		when(epiManager.findEpisDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Epi>());
		when(riscoMedicaoRiscoManager.findMedicoesDeRiscosDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		when(ambienteDao.getQtdColaboradorByAmbiente(anyLong(), any(Date.class), eq(Sexo.MASCULINO), (Long) eq(null))).thenReturn(10);
		when(ambienteDao.getQtdColaboradorByAmbiente(anyLong(), any(Date.class), eq(Sexo.FEMININO), (Long) eq(null))).thenReturn(50);

		Collection<PpraLtcatRelatorio> relatorios = ambienteManager.montaRelatorioPpraLtcat(empresa, estabelecimento, localAmbiente, hoje, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);
		
		assertEquals(2, relatorios.size());
		assertEquals(60, ((PpraLtcatRelatorio) relatorios.toArray()[0]).getCabecalho().getQtdTotal().intValue());
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testPopulaRelatorioPorAmbienteSemAmbienteException() throws Exception
	{
		Date hoje = new Date();
		boolean gerarPpra = true;
		boolean gerarLtcat = true;
		boolean exibirComposicaoSesmt = false;
		String[] ambienteCheck = new String[]{};
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao();
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(2L);
		
		PpraLtcatCabecalho cabecalho = new PpraLtcatCabecalho();
		cabecalho.setEmpresa(empresa);
		cabecalho.setEstabelecimento(estabelecimento);
		
		
		when(estabelecimentoManager.findById(empresa.getId())).thenReturn(estabelecimento);
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(true);
		when(ambienteDao.findByIds(eq(empresa.getId()), eq(LongUtil.arrayStringToCollectionLong(ambienteCheck)), eq(hoje), eq(estabelecimento.getId()),eq(localAmbiente))).thenReturn(new ArrayList<Ambiente>());
		
	    thrown.expect(ColecaoVaziaException.class);
	    thrown.expectMessage("Não existem dados para o filtro informado.");

	    ambienteManager.montaRelatorioPpraLtcat(empresa, estabelecimento, LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao(), hoje, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);
		fail("ColecaoVaziaException não foi lançada.");
	}

	@Test
	public void testPopulaRelatorioPorFuncaoSemFuncaoVinculadaAoAmbienteException() throws Exception
	{
		Date hoje = new Date();
		boolean gerarPpra = true;
		boolean gerarLtcat = true;
		boolean exibirComposicaoSesmt = true;
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao();

		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Estabelecimento estabelecimento = new Estabelecimento();
		
		Ambiente ambiente1 = AmbienteFactory.getEntity(50L, "Ambiente1", null, null);
		Ambiente ambiente2 = AmbienteFactory.getEntity(51L, "Ambiente2", null, null);
		
		Collection<Ambiente> ambientes = Arrays.asList(ambiente1, ambiente2);
		String[] ambienteCheck = new String[]{ambiente1.getId().toString(), ambiente2.getId().toString()};

		when(estabelecimentoManager.findById(empresa.getId())).thenReturn(estabelecimento);
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(false);
		when(ambienteDao.findByIds(eq(empresa.getId()), eq(LongUtil.arrayStringToCollectionLong(ambienteCheck)), eq(hoje), eq(estabelecimento.getId()),eq(localAmbiente))).thenReturn(ambientes);
		
		when(composicaoSesmtManager.findByData(anyLong(), any(Date.class))).thenReturn(null);
		when(funcaoManager.findFuncoesDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Funcao>());

		thrown.expect(ColecaoVaziaException.class);
		thrown.expectMessage("Nos históricos dos colaboradores não existem funções vinculadas aos ambientes selecionados no filtro.");
		
		ambienteManager.montaRelatorioPpraLtcat(empresa, estabelecimento, localAmbiente, hoje, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);
		fail("ColecaoVaziaException não foi lançada.");
	}
	
	@Test
	public void testSaveAmbienteHistoricoCommit()throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		Ambiente ambiente = AmbienteFactory.getEntity("Ambiente 1", empresa);
		ambiente.setId(1L);

		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(ambiente.getNome(), null, "descricao", ambiente, new Date(), "");
		
		String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		
		Collection<RiscoAmbiente> riscosAmbientes = RiscoAmbienteFactory.getCollection();

		when(ambienteDao.save(ambiente)).thenReturn(ambiente);

		ambienteManager.saveAmbienteHistorico(empresa, historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
		
		assertEquals(Long.valueOf(1L), ambiente.getId());
	}
	
	@Test
	public void testGetCount()
	{
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();

		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		
		when(ambienteDao.getCount(empresa.getId(), historicoAmbiente)).thenReturn(ambientes.size());

		int retorno = ambienteManager.getCount(empresa.getId(), historicoAmbiente);

		assertEquals(ambientes.size(), retorno);
	}

	@Test
	public void testFindAmbientes()throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);

		Ambiente a1 = new Ambiente();
		a1.setId(1L);
		a1.setEmpresa(empresa);

		Ambiente a2 = new Ambiente();
		a2.setId(2L);
		a2.setEmpresa(empresa);

		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(a1);
		ambientes.add(a2);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		

		when(ambienteDao.findAmbientes(eq(0), eq(0), eq(empresa.getId()), eq(historicoAmbiente))).thenReturn(ambientes);
		Collection<Ambiente> ambienteRetorno = ambienteManager.findAmbientes(0, 0, empresa.getId(), historicoAmbiente);

		assertEquals("findAmbientes sem paginação",ambientes, ambienteRetorno);

		when(ambienteDao.findAmbientes(eq(1), eq(15), eq(empresa.getId()), eq(historicoAmbiente))).thenReturn(ambientes);
		ambienteRetorno = ambienteManager.findAmbientes(1, 15, empresa.getId(), historicoAmbiente);

		assertEquals("findAmbientes com paginação",ambientes, ambienteRetorno);
	}
	
	@Test
	public void testFindAmbientesColecaoVaziaException() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);

		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		when(ambienteDao.findAmbientes(eq(0), eq(0), eq(empresa.getId()), eq(historicoAmbiente))).thenReturn(new ArrayList<Ambiente>());

		thrown.expect(ColecaoVaziaException.class);
	    thrown.expectMessage("Não existem dados para o filtro informado.");
	    ambienteManager.findAmbientes(0, 0, empresa.getId(), historicoAmbiente);
	}
	
	@Test
	public void testFindAmbientesPorEmpresa()
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);

		HistoricoAmbiente historicoAmbiente = null;
		when(ambienteDao.findAmbientes(eq(0), eq(0), eq(empresa.getId()), eq(historicoAmbiente))).thenReturn(new ArrayList<Ambiente>());
		
		ambienteManager.findAmbientes(empresa.getId());
		verify(ambienteDao, times(1)).findAmbientes(eq(0), eq(0), eq(empresa.getId()), eq(historicoAmbiente));
	}
	
	@Test
	public void testRemoveCascade() throws Exception
	{
		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
		
		ambienteManager.removeCascade(ambiente.getId());

		verify(historicoAmbienteManager, times(1)).removeByAmbiente(ambiente.getId());
		verify(ambienteDao, times(1)).remove(ambiente.getId());
	}
	
	@Test
	public void testFindByEmpresa() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		
		Collection<Ambiente> ambientesRetorno = new ArrayList<Ambiente>();

		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);

		ambientesRetorno.add(ambiente);

		when(ambienteDao.find(eq(new String[]{"empresa.id"}), eq(new Object[]{empresa.getId()}), eq(new String[]{"nome"}))).thenReturn(ambientesRetorno);

		assertEquals(ambientesRetorno, ambienteManager.findByEmpresa(empresa.getId()));
	}
	
	@Test
	public void testFindByIdProjection()
	{
		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
		when(ambienteDao.findByIdProjection(eq(ambiente.getId()))).thenReturn(ambiente);
		
		ambienteManager.findByIdProjection(ambiente.getId());
		verify(ambienteDao, times(1)).findByIdProjection(eq(ambiente.getId()));
	}
	
	@Test
	public void testPopulaCheckBox()
	{
		Date data = new Date();
		Long empresaId = 2L;
		Long estabelecimentoId = 2L;
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao();
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(AmbienteFactory.getEntity(1L));
		when(ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimentoId, localAmbiente, data)).thenReturn(ambientes);
		
		assertEquals(1, ambienteManager.populaCheckBox(empresaId, estabelecimentoId, localAmbiente, data).size());
	}
	
	@Test
	public void testPopulaCheckBoxEstabelecimentoNulo()
	{
		Date data = new Date();
		Long empresaId = 2L;
		Long estabelecimentoId = null;
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao();
		Collection<Ambiente> ambientes = Arrays.asList(AmbienteFactory.getEntity(1L));

		when(ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimentoId, localAmbiente, data)).thenReturn(ambientes);
		assertEquals(1, ambienteManager.populaCheckBox(empresaId, estabelecimentoId, localAmbiente, data).size());
		verify(ambienteDao, times(1)).findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimentoId, localAmbiente, data);
	}
	
	@Test
	public void testPopulaCheckBoxEstabelecimentoNuloELocalAmbienteEstabelecimentoDoProprioEmpregador()
	{
		Date data = new Date();
		Long empresaId = 2L;
		Long estabelecimentoId = null;
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao();
		assertEquals(0, ambienteManager.populaCheckBox(empresaId, estabelecimentoId, localAmbiente, data).size());
		verify(ambienteDao, never()).findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimentoId, localAmbiente, data);
	}

	@Test
	public void testPopulaCheckBoxComException()
	{
		Date data = new Date();
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(AmbienteFactory.getEntity(1L));
		Long empresaId = 2L;
		Long estabelecimentoId = null;
		Integer localAmbiente = LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao();

		when(ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimentoId, localAmbiente, data)).thenReturn(null);
		assertEquals( new ArrayList<CheckBox>(), ambienteManager.populaCheckBox(empresaId, estabelecimentoId, localAmbiente, data));
	}
	
	@Test
	public void testGetQtdColaboradorByAmbiente(){
		Long ambienteId = 1L; 
		Date data = new Date();
		String sexo = Sexo.FEMININO;
		
		when(ambienteDao.getQtdColaboradorByAmbiente(ambienteId, data, sexo, null)).thenReturn(3);
		assertEquals(3, ambienteManager.getQtdColaboradorByAmbiente(ambienteId, data, sexo));
		verify(ambienteDao, times(1)).getQtdColaboradorByAmbiente(ambienteId, data, sexo, null);
	}
	
	@Test
	public void testFindAmbientesPorEstabelecimento()
	{
		Long[] estabelecimentoIds= new Long[]{1L};
		Date data = new Date();

		when(ambienteDao.findAmbientesPorEstabelecimento(estabelecimentoIds, data)).thenReturn(new ArrayList<Ambiente>());
		ambienteManager.findAmbientesPorEstabelecimento(estabelecimentoIds, data);
		
		verify(ambienteDao, times(1)).findAmbientesPorEstabelecimento(eq(estabelecimentoIds), eq(data));
	}
	
	@Test
	public void testFindAmbientesPorEstabelecimentoComEstabelecimentoNulo()
	{
		Long[] estabelecimentoIds= null;
		Date data = new Date();

		ambienteManager.findAmbientesPorEstabelecimento(estabelecimentoIds, data);
		verify(ambienteDao, never()).findAmbientesPorEstabelecimento(eq(estabelecimentoIds), eq(data));
	}
	
	@Test
	public void testFindAmbientesPorEstabelecimentoComArrayDeEstabelecimentoVazio()
	{
		Long[] estabelecimentoIds= new Long[]{};
		Date data = new Date();
		ambienteManager.findAmbientesPorEstabelecimento(estabelecimentoIds, data);
		verify(ambienteDao, never()).findAmbientesPorEstabelecimento(eq(estabelecimentoIds), eq(data));
	}
	
	@Test
	public void testPopulaCheckBoxByEstabelecimentos() 
	{
		Long[] estabelecimentoIds = new Long[]{1L};
		when(ambienteDao.findAmbientesPorEstabelecimento(eq(estabelecimentoIds), any(Date.class))).thenReturn(new ArrayList<Ambiente>());
		ambienteManager.populaCheckBoxByEstabelecimentos(estabelecimentoIds);
		verify(ambienteDao, times(1)).findAmbientesPorEstabelecimento(eq(estabelecimentoIds), any(Date.class));
	}
	
	@Test
	public void testPopulaCheckBoxByEstabelecimentosException() 
	{
		Long[] estabelecimentoIds = new Long[]{1L};
		when(ambienteDao.findAmbientesPorEstabelecimento(eq(estabelecimentoIds), any(Date.class))).thenReturn(null);
		Collection<CheckBox> checkBox = ambienteManager.populaCheckBoxByEstabelecimentos(estabelecimentoIds);
		verify(ambienteDao, times(1)).findAmbientesPorEstabelecimento(eq(estabelecimentoIds), any(Date.class));
		assertEquals( new ArrayList<CheckBox>(), checkBox); 
	}

	@Test
	public void atualizaDadosParaUltimoHistorico() {
		Long ambienteId = 2L;
		doNothing().when(ambienteDao).atualizaDadosParaUltimoHistorico(ambienteId);
		ambienteManager.atualizaDadosParaUltimoHistorico(ambienteId);
		verify(ambienteDao, times(1)).atualizaDadosParaUltimoHistorico(ambienteId);
	}
	
	@Test
	public void deleteAmbienteSemHistorico() throws Exception {
		doNothing().when(ambienteDao).deleteAmbienteSemHistorico();
		ambienteManager.deleteAmbienteSemHistorico();
		verify(ambienteDao, times(1)).deleteAmbienteSemHistorico();
	}
	
	@Test
	public void testMontaMapAmbientes(){
		Long empresaId = 2L;
		Long estabelecimentoId = 5L;
		String estabelecimentoNome = "Estabelecimento";
		Date data = new Date();
		Long estabelecimentoDeTerceiro = null;
		Collection<Ambiente> ambientesInternos = Arrays.asList(AmbienteFactory.getEntity());
		Collection<Ambiente> ambientesExternos = Arrays.asList(AmbienteFactory.getEntity());
				
		Map<String, Collection<Ambiente>> ambientes = new LinkedHashMap<String, Collection<Ambiente>>();
		ambientes.put("Ambientes do estabelecimento: " + estabelecimentoNome, ambientesInternos);
		ambientes.put("Ambientes de estabelecimentos de terceiros", ambientesExternos);
		
		when(ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(eq(empresaId), eq(estabelecimentoId),eq(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao()), eq(data))).thenReturn(ambientesInternos);
		
		when(ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(eq(empresaId), eq(estabelecimentoDeTerceiro), eq(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao()), eq(data))).thenReturn(ambientesExternos);
		
		assertEquals(ambientes, ambienteManager.montaMapAmbientes(empresaId, estabelecimentoId, estabelecimentoNome, data));
		verify(ambienteDao, times(1)).findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(eq(empresaId), eq(estabelecimentoId), 
				eq(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao()), eq(data));
		
		verify(ambienteDao, times(1)).findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(eq(empresaId), eq(estabelecimentoDeTerceiro),
				eq(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao()), eq(data));
	}
	
	@Test
	public void testMontaMapAmbientesSemAmbienteInterno(){
		Long empresaId = 2L;
		Long estabelecimentoId = 5L;
		String estabelecimentoNome = "Estabelecimento";
		Date data = new Date();
		Long estabelecimentoDeTerceiro = null;
		Collection<Ambiente> ambientesExternos = Arrays.asList(AmbienteFactory.getEntity());
				
		Map<String, Collection<Ambiente>> ambientes = new LinkedHashMap<String, Collection<Ambiente>>();
		ambientes.put("Ambientes de estabelecimentos de terceiros", ambientesExternos);
		
		when(ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(eq(empresaId), eq(estabelecimentoId),
				eq(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao()), eq(data))).thenReturn(new ArrayList<Ambiente>());
		
		when(ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(eq(empresaId), eq(estabelecimentoDeTerceiro),
				eq(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao()), eq(data))).thenReturn(ambientesExternos);
		
		assertEquals(ambientes, ambienteManager.montaMapAmbientes(empresaId, estabelecimentoId, estabelecimentoNome, data));
	}
	
	@Test
	public void testMontaMapAmbientesSemAmbienteExterno(){
		Long empresaId = 2L;
		Long estabelecimentoId = 5L;
		String estabelecimentoNome = "Estabelecimento";
		Date data = new Date();
		Long estabelecimentoDeTerceiro = null;
		Collection<Ambiente> ambientesInternos = Arrays.asList(AmbienteFactory.getEntity());
				
		Map<String, Collection<Ambiente>> ambientes = new LinkedHashMap<String, Collection<Ambiente>>();
		ambientes.put("Ambientes do estabelecimento: " + estabelecimentoNome, ambientesInternos);
		
		when(ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(eq(empresaId), eq(estabelecimentoId),
				eq(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao()), eq(data))).thenReturn(ambientesInternos);
		
		when(ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(eq(empresaId), eq(estabelecimentoDeTerceiro),
				eq(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao()), eq(data))).thenReturn(new ArrayList<Ambiente>());
		
		assertEquals(ambientes, ambienteManager.montaMapAmbientes(empresaId, estabelecimentoId, estabelecimentoNome, data));
	}
}
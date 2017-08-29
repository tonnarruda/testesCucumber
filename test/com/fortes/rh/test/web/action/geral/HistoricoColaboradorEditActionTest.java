package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.action.cargosalario.HistoricoColaboradorEditAction;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class HistoricoColaboradorEditActionTest
{
	private HistoricoColaboradorEditAction action;
	
	private FuncaoManager funcaoManager;
	private IndiceManager indiceManager;
	private AmbienteManager ambienteManager;
	private ColaboradorManager colaboradorManager;
	private SolicitacaoManager solicitacaoManager;
	private FaixaSalarialManager faixaSalarialManager;
	private EstabelecimentoManager estabelecimentoManager;
	private PlatformTransactionManager transactionManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;
	private Empresa empresaDoSistema;
	private HistoricoColaborador historicoColaborador;

	@Before
	public void setUp() throws Exception
	{
		action = new HistoricoColaboradorEditAction();
		funcaoManager = mock(FuncaoManager.class);
		indiceManager = mock(IndiceManager.class);
		ambienteManager = mock(AmbienteManager.class);
		colaboradorManager = mock(ColaboradorManager.class);
		solicitacaoManager = mock(SolicitacaoManager.class);
		faixaSalarialManager = mock(FaixaSalarialManager.class);
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		transactionManager = mock(PlatformTransactionManager.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
		quantidadeLimiteColaboradoresPorCargoManager = mock(QuantidadeLimiteColaboradoresPorCargoManager.class);
		
		action.setIndiceManager(indiceManager);
		action.setFuncaoManager(funcaoManager);
		action.setAmbienteManager(ambienteManager);
		action.setColaboradorManager(colaboradorManager);
		action.setSolicitacaoManager(solicitacaoManager);
		action.setTransactionManager(transactionManager);
		action.setFaixaSalarialManager(faixaSalarialManager);
		action.setEstabelecimentoManager(estabelecimentoManager);
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		action.setHistoricoColaboradorManager(historicoColaboradorManager);
		action.setQuantidadeLimiteColaboradoresPorCargoManager(quantidadeLimiteColaboradoresPorCargoManager);
		
		empresaDoSistema = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresaDoSistema);
		
		historicoColaborador = HistoricoColaboradorFactory.getEntity();
		
		PowerMockito.mockStatic(SpringUtil.class);
    	BDDMockito.given(SpringUtil.getBean("parametrosDoSistemaManager")).willReturn(parametrosDoSistemaManager);
	}
	
	@Test
	public void testList() throws Exception {
		assertEquals("success", action.list()); 
	}

	@Test
	public void testPrepare() throws Exception {
		// dado que
		dadoQueExistemEstabelecimentosParaEmpresaDoSistema();
		dadoQueExistemIndicesCadastrados();
		dadoQueExistemFaixasSalariaisAtivasParaEmpresaDoSistema();
		dadoQueExistemAreasOrganizacionaisParaEmpresaDoSistema();
		
		dadoQueHistoricoDoColaboradorPossuiUmCargoArea();
		action.setHistoricoColaborador(historicoColaborador);
		
		dadoQueExistemFuncoesCadastradas();
		dadoQueHistoricoDoColaboradorPossuiUmEstabelecimento();
		dadoQueExistemAmbientesCadastrados();
		// quando
		action.prepare();
		// entao
		verify(estabelecimentoManager).findAllSelect(eq(empresaDoSistema.getId()));
		verify(indiceManager).findAll(empresaDoSistema);
		verify(faixaSalarialManager).findFaixas(eq(empresaDoSistema), eq(Cargo.ATIVO), anyLong());
		verify(areaOrganizacionalManager).findAllSelectOrderDescricao(eq(empresaDoSistema.getId()), eq(AreaOrganizacional.ATIVA), anyLong(), anyBoolean());
		verify(funcaoManager).findByCargo(eq(historicoColaborador.getFaixaSalarial().getCargo().getId()));
		verify(ambienteManager).findByEstabelecimento(historicoColaborador.getEstabelecimento().getId());
	}
	
	@Test
	public void testPrepareInsert() throws Exception {
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		when(funcaoManager.findByCargo(anyLong())).thenReturn(new ArrayList<Funcao>());
		when(ambienteManager.findByEstabelecimento(any(Long[].class))).thenReturn(new ArrayList<Ambiente>());
		when(historicoColaboradorManager.findByColaboradorProjection(anyLong(), eq(StatusRetornoAC.AGUARDANDO))).thenReturn(new ArrayList<HistoricoColaborador>());
		when(faixaSalarialManager.findById(anyLong())).thenReturn(faixaSalarial);
		
		// comportamente do prepareInsert()
		dadoQueExisteHistoricoAtualParaColaborador();
		// comportamento do prepare()
		simulaComportamentoDoPrepareSemHistorico();
		
		String outcome = action.prepareInsert();
		
		assertEquals("success", outcome);
		assertNull("id do colaborador", historicoColaborador.getId());
		assertNotNull("data do colaborador", historicoColaborador.getData());
		assertQueMetodoPrepareFoiChamado();
	}
	
	@Test
	public void testPrepareInsertComHistoricoAguardandoConfirmacaoNoFortesPessoal() throws Exception {
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);
		
		Collection<HistoricoColaborador> historicosColaborador = Arrays.asList(historicoColaborador);
		action.setColaborador(colaborador);
		
		when(historicoColaboradorManager.findByColaboradorProjection(eq(colaborador.getId()), eq(StatusRetornoAC.AGUARDANDO))).thenReturn(historicosColaborador);

		assertEquals("input", action.prepareInsert());
	}
	
	@Test
	public void testPrepareInsertQuandoHouverSolicitacao() throws Exception {
		// comportamente do prepareInsert()
		dadoQueExisteHistoricoAtualParaColaborador();
		dadoQueExisteUmaSolicitacao();
		// comportamento do prepare()
		
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		when(faixaSalarialManager.findById(anyLong())).thenReturn(faixaSalarial);
		
		when(historicoColaboradorManager.findByColaboradorProjection(anyLong(), eq(StatusRetornoAC.AGUARDANDO))).thenReturn(new ArrayList<HistoricoColaborador>());
		simulaComportamentoDoPrepareQuandoHouverSolicitacao();
		
		String outcome = action.prepareInsert();
		
		assertEquals("success", outcome);
		assertNull("id do colaborador", historicoColaborador.getId());
		assertNotNull("data do colaborador", action.getHistoricoColaborador().getData());
		assertQueMetodoPrepareFoiChamadoQuandoHouverSolicitacao();
	}
	
	@Test
	public void testInsertQuandoNaoExisteHistoricoNaData() throws Exception {
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		dadoQueNaoExisteHistoricoNaData();
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(ColaboradorFactory.getEntity(1L));
		action.setHistoricoColaborador(historicoColaborador);
		
		when(transactionManager.getTransaction(any(TransactionDefinition.class))).thenReturn(null);
		when(colaboradorManager.findColaboradorById(anyLong())).thenReturn(historicoColaborador.getColaborador());
		when(historicoColaboradorManager.verificaPrimeiroHistoricoAdmissao(eq(true), eq(historicoColaborador), eq(historicoColaborador.getColaborador()))).thenReturn(false);
		
		dadoQueNaoOcorreErroAoAjustarFuncaoDoColaborador();
		
		String outcome = action.insert();
		
		assertEquals("success", outcome);
	}
	
	@Test
	public void testPrepareUpdateHistoricoAguardandoConfirmacao() throws Exception {
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Cargo cargo = CargoFactory.getEntity(1L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);	

		HistoricoColaborador historicoColaboradorAguagandoConfirmacao = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaboradorAguagandoConfirmacao.setEstabelecimento(estabelecimento);
		historicoColaboradorAguagandoConfirmacao.setFaixaSalarial(faixaSalarial);
		historicoColaboradorAguagandoConfirmacao.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAguagandoConfirmacao.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorAguagandoConfirmacao.setColaborador(colaborador);
		action.setHistoricoColaborador(historicoColaboradorAguagandoConfirmacao);
		
		Collection<HistoricoColaborador> historicosAguardandoConfirmacao = Arrays.asList(historicoColaboradorAguagandoConfirmacao);
		action.setColaborador(colaborador);
		
		when(historicoColaboradorManager.findByColaboradorProjection(eq(colaborador.getId()), eq(StatusRetornoAC.AGUARDANDO))).thenReturn(historicosAguardandoConfirmacao);
		when(historicoColaboradorManager.findByIdHQL(eq(historicoColaboradorAguagandoConfirmacao.getId()))).thenReturn(historicoColaboradorAguagandoConfirmacao);
		
		when(estabelecimentoManager.findAllSelect(eq(empresa.getId()))).thenReturn(new ArrayList<Estabelecimento>());
		when(indiceManager.findAll(eq(empresa))).thenReturn(new ArrayList<Indice>());
		
		when(funcaoManager.findByCargo(eq(historicoColaboradorAguagandoConfirmacao.getFaixaSalarial().getCargo().getId()))).thenReturn(new ArrayList<Funcao>());
		when(ambienteManager.findByEstabelecimento(eq(new Long[]{historicoColaboradorAguagandoConfirmacao.getEstabelecimento().getId()}))).thenReturn(new ArrayList<Ambiente>());
		
		when(faixaSalarialManager.findFaixas(eq(empresa), eq(true), anyLong())).thenReturn(new ArrayList<FaixaSalarial>());

		when(areaOrganizacionalManager.findAllSelectOrderDescricao(eq(empresa.getId()), eq(true), anyLong(), anyBoolean())).thenReturn(new ArrayList<AreaOrganizacional>());

		assertEquals("success", action.prepareUpdate());
	}

	@Test
	public void testPrepareUpdateHistoricoComOutroHistoricoAguardandoConfirmacao() throws Exception {
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaboradorConfirmado = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaboradorConfirmado.setEstabelecimento(estabelecimento);
		historicoColaboradorConfirmado.setFaixaSalarial(faixaSalarial);
		historicoColaboradorConfirmado.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorConfirmado.setColaborador(colaborador);
		action.setHistoricoColaborador(historicoColaboradorConfirmado);
		
		HistoricoColaborador historicoColaboradorAguardandoConfirmacao = HistoricoColaboradorFactory.getEntity(2L);
		historicoColaboradorAguardandoConfirmacao.setEstabelecimento(estabelecimento);
		historicoColaboradorAguardandoConfirmacao.setFaixaSalarial(faixaSalarial);
		historicoColaboradorAguardandoConfirmacao.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorAguardandoConfirmacao.setColaborador(colaborador);
		
		Collection<HistoricoColaborador> historicosAguardandoConfirmacao = Arrays.asList(historicoColaboradorAguardandoConfirmacao);
		action.setColaborador(colaborador);
		
		when(historicoColaboradorManager.findByColaboradorProjection(eq(colaborador.getId()), eq(StatusRetornoAC.AGUARDANDO))).thenReturn(historicosAguardandoConfirmacao);
		when(historicoColaboradorManager.findByIdHQL(eq(historicoColaboradorConfirmado.getId()))).thenReturn(historicoColaboradorConfirmado);
		
		assertEquals("input", action.prepareUpdate());
	}
	
	private void dadoQueNaoOcorreErroAoAjustarFuncaoDoColaborador() {
		when(historicoColaboradorManager.ajustaAmbienteFuncao(eq(historicoColaborador))).thenReturn(historicoColaborador);
	}

	private void dadoQueNaoExisteHistoricoNaData() {
		action.setHistoricoColaborador(historicoColaborador);
		when(historicoColaboradorManager.existeHistoricoData(eq(historicoColaborador))).thenReturn(false);
	}

	@Test
	public void testInsertQuandoJaExisteHistoricoNaData() throws Exception {
		// comportamento do insert()
		dadoQueJaExisteHistoricoNaData();
		// comportamento do prepareInsert()
		when(historicoColaboradorManager.findByColaboradorProjection(anyLong(), eq(StatusRetornoAC.AGUARDANDO))).thenReturn(new ArrayList<HistoricoColaborador>());
		simulaComportamentoDoPrepareInsert();
		
		when(transactionManager.getTransaction(any(TransactionDefinition.class))).thenReturn(null);
		String outcome = action.insert();
		
		assertEquals("input", outcome);
		assertQueMetodoPrepareInsertFoiChamado();
	}
	
	@Test
	public void testInsertQuandoOcorrerErroInternoQualquer() throws Exception {
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historicoColaborador.setData(new Date());
		dadoQueNaoExisteHistoricoNaData();
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(ColaboradorFactory.getEntity(1L));
		action.setHistoricoColaborador(historicoColaborador);
		
		when(transactionManager.getTransaction(any(TransactionDefinition.class))).thenReturn(null);
		when(colaboradorManager.findColaboradorById(anyLong())).thenReturn(historicoColaborador.getColaborador());
		when(historicoColaboradorManager.findByColaboradorProjection(anyLong(), eq(StatusRetornoAC.AGUARDANDO))).thenReturn(new ArrayList<HistoricoColaborador>());
		when(historicoColaboradorManager.verificaPrimeiroHistoricoAdmissao(eq(true), eq(historicoColaborador), eq(historicoColaborador.getColaborador()))).thenReturn(false);
		
		dadoQueNaoOcorreErroAoAjustarFuncaoDoColaborador();
		dadoQueOcorreErroGenericoAoInserirHistoricoDeColaborador();
		simulaComportamentoDoPrepareInsert();
		
		String outcome = action.insert();
		
		assertEquals("input", outcome);
		assertQueMetodoPrepareInsertFoiChamado();
	}
	
	private void dadoQueOcorreErroGenericoAoInserirHistoricoDeColaborador() throws Exception {
		doThrow(new RuntimeException("Erro interno.")).when(historicoColaboradorManager).insertHistorico(historicoColaborador, empresaDoSistema);
	}

	@Test
	public void testInsertQuandoOcorrerErroDeIntegraACException() throws Exception {
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historicoColaborador.setData(new Date());
		dadoQueNaoExisteHistoricoNaData();
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(ColaboradorFactory.getEntity(1L));
		action.setHistoricoColaborador(historicoColaborador);
		
		when(transactionManager.getTransaction(any(TransactionDefinition.class))).thenReturn(null);
		when(colaboradorManager.findColaboradorById(anyLong())).thenReturn(historicoColaborador.getColaborador());
		when(historicoColaboradorManager.findByColaboradorProjection(anyLong(),eq(StatusRetornoAC.AGUARDANDO))).thenReturn(new ArrayList<HistoricoColaborador>());
		when(historicoColaboradorManager.verificaPrimeiroHistoricoAdmissao(eq(true), eq(historicoColaborador), eq(historicoColaborador.getColaborador()))).thenReturn(false);
		
		
		dadoQueNaoOcorreErroAoAjustarFuncaoDoColaborador();
		dadoQueOcorreErroDeIntegracaoACAoInserirHistoricoDeColaborador();
		simulaComportamentoDoPrepareInsert();
		
		String outcome = action.insert();
		
		assertEquals("input", outcome);
		assertQueMetodoPrepareInsertFoiChamado();
	}


	@Test
	public void testPrepareUpdateComFolhaNaoProcessadaEEmpresaIntegradaEAderidaAoESocial() throws Exception
	{
		empresaDoSistema.setAcIntegra(true);
		action.setEmpresaSistema(empresaDoSistema);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historicoColaborador.setData(new Date());
		action.setHistoricoColaborador(historicoColaborador);
		
		when(historicoColaboradorManager.findByColaboradorProjection(colaborador.getId(), StatusRetornoAC.AGUARDANDO)).thenReturn(new ArrayList<HistoricoColaborador>());
		when(historicoColaboradorManager.findByIdHQL(historicoColaborador.getId())).thenReturn(historicoColaborador);
		when(historicoColaboradorManager.verificaHistoricoNaFolhaAC(historicoColaborador.getId(), historicoColaborador.getColaborador().getCodigoAC(), action.getEmpresaSistema())).thenReturn(false);
		when(parametrosDoSistemaManager.isAderiuAoESocial(action.getEmpresaSistema())).thenReturn(true);
		when(historicoColaboradorManager.existeHistoricoContratualComPendenciaNoESocial(action.getEmpresaSistema(), historicoColaborador.getColaborador().getCodigoAC())).thenReturn(false);
		when(historicoColaboradorManager.situacaoContratualEhInicioVinculo(action.getEmpresaSistema(), historicoColaborador.getColaborador().getCodigoAC(), historicoColaborador.getData())).thenReturn(false);
		when(areaOrganizacionalManager.getMascaraLotacoesAC(action.getEmpresaSistema())).thenReturn("001");
		action.prepareUpdate();
		
		assertFalse(action.isDisabledCamposIntegrados());
	}
	
	@Test
	public void testPrepareUpdateComFolhaNaoProcessadaEEmpresaIntegradaEAderidaAoESocialHistóricoEInicioDoVinculo() throws Exception
	{
		empresaDoSistema.setAcIntegra(true);
		action.setEmpresaSistema(empresaDoSistema);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historicoColaborador.setData(new Date());
		action.setHistoricoColaborador(historicoColaborador);
		
		when(historicoColaboradorManager.findByColaboradorProjection(colaborador.getId(), StatusRetornoAC.AGUARDANDO)).thenReturn(new ArrayList<HistoricoColaborador>());
		when(historicoColaboradorManager.findByIdHQL(historicoColaborador.getId())).thenReturn(historicoColaborador);
		when(historicoColaboradorManager.verificaHistoricoNaFolhaAC(historicoColaborador.getId(), historicoColaborador.getColaborador().getCodigoAC(), action.getEmpresaSistema())).thenReturn(false);
		when(parametrosDoSistemaManager.isAderiuAoESocial(action.getEmpresaSistema())).thenReturn(true);
		when(historicoColaboradorManager.existeHistoricoContratualComPendenciaNoESocial(action.getEmpresaSistema(), historicoColaborador.getColaborador().getCodigoAC())).thenReturn(false);
		when(historicoColaboradorManager.situacaoContratualEhInicioVinculo(action.getEmpresaSistema(), historicoColaborador.getColaborador().getCodigoAC(), historicoColaborador.getData())).thenReturn(true);
		when(areaOrganizacionalManager.getMascaraLotacoesAC(action.getEmpresaSistema())).thenReturn("001");
		action.prepareUpdate();
		
		assertTrue(action.isDisabledCamposIntegrados());
	}
	
	@Test
	public void testPrepareUpdateComFolhaNaoProcessadaEEmpresaIntegradaEAderidaAoESocialExistePendenciasNasSituacoesComOESocal() throws Exception
	{
		empresaDoSistema.setAcIntegra(true);
		action.setEmpresaSistema(empresaDoSistema);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historicoColaborador.setData(new Date());
		action.setHistoricoColaborador(historicoColaborador);
		
		when(historicoColaboradorManager.findByColaboradorProjection(colaborador.getId(), StatusRetornoAC.AGUARDANDO)).thenReturn(new ArrayList<HistoricoColaborador>());
		when(historicoColaboradorManager.findByIdHQL(historicoColaborador.getId())).thenReturn(historicoColaborador);
		when(historicoColaboradorManager.verificaHistoricoNaFolhaAC(historicoColaborador.getId(), historicoColaborador.getColaborador().getCodigoAC(), action.getEmpresaSistema())).thenReturn(false);
		when(parametrosDoSistemaManager.isAderiuAoESocial(action.getEmpresaSistema())).thenReturn(true);
		when(historicoColaboradorManager.existeHistoricoContratualComPendenciaNoESocial(action.getEmpresaSistema(), historicoColaborador.getColaborador().getCodigoAC())).thenReturn(true);
		when(areaOrganizacionalManager.getMascaraLotacoesAC(action.getEmpresaSistema())).thenReturn("001");
		action.prepareUpdate();
		
		assertTrue(action.isDisabledCamposIntegrados());
	}
	
	@Test
	public void testUpdateExceptionESocial() throws Exception
	{
		empresaDoSistema.setAcIntegra(true);
		action.setEmpresaSistema(empresaDoSistema);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaborador.setData(new Date());
		action.setHistoricoColaborador(historicoColaborador);
		
		when(parametrosDoSistemaManager.isAderiuAoESocial(action.getEmpresaSistema())).thenReturn(true);
		
		assertEquals("eSocial",action.update()); 
	}
	
	@Test
	public void testUpdateUpdateSomenteAmbienteEFuncao() throws Exception
	{
		empresaDoSistema.setAcIntegra(true);
		action.setEmpresaSistema(empresaDoSistema);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setData(new Date());
		action.setHistoricoColaborador(historicoColaborador);
		action.setDisabledCamposIntegrados(true);
		
		assertEquals("success",action.update()); 
		verify(historicoColaboradorManager).updateAmbienteEFuncao(historicoColaborador);
	}
	
	@Test
	public void testUpdate() throws Exception
	{
		empresaDoSistema.setAcIntegra(true);
		action.setEmpresaSistema(empresaDoSistema);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		
		historicoColaborador.setFaixaSalarial(FaixaSalarialFactory.getEntity(1L));
		historicoColaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setData(new Date());
		action.setHistoricoColaborador(historicoColaborador);
		
		when(parametrosDoSistemaManager.isAderiuAoESocial(action.getEmpresaSistema())).thenReturn(false);
		
		assertEquals("success",action.update());
		verify(historicoColaboradorManager, times(0)).updateAmbienteEFuncao(historicoColaborador);
		verify(historicoColaboradorManager, times(1)).updateHistorico(any(HistoricoColaborador.class), any(Empresa.class));
	}
	
	private void dadoQueOcorreErroDeIntegracaoACAoInserirHistoricoDeColaborador() throws Exception {
		doThrow(new IntegraACException("Erro de Integração.")).when(historicoColaboradorManager).insertHistorico(historicoColaborador, empresaDoSistema);
	}

	private void assertQueMetodoPrepareInsertFoiChamado() throws Exception {
		assertNull("id do colaborador", historicoColaborador.getId());
		assertNotNull("data do colaborador", historicoColaborador.getData());
		assertQueMetodoPrepareFoiChamado();		
	}

	private void simulaComportamentoDoPrepareInsert() {
		// comportamente do prepareInsert()
		dadoQueExisteHistoricoAtualParaColaborador();
		// comportamento do prepare()
		simulaComportamentoDoPrepareComHistorico();
	}

	private void dadoQueJaExisteHistoricoNaData() throws Exception {
		historicoColaborador.setData(new Date());
		historicoColaborador.setColaborador(ColaboradorFactory.getEntity(1L));
		action.setHistoricoColaborador(historicoColaborador);
		when(historicoColaboradorManager.existeHistoricoData(eq(historicoColaborador))).thenReturn(true);
		when(colaboradorManager.findColaboradorById(anyLong())).thenReturn(historicoColaborador.getColaborador());
	}
	
	private void assertQueMetodoPrepareFoiChamado() throws Exception {
		verify(estabelecimentoManager).findAllSelect(eq(empresaDoSistema.getId()));
		verify(indiceManager).findAll(empresaDoSistema);
		verify(faixaSalarialManager).findFaixas(eq(empresaDoSistema), eq(Cargo.ATIVO), anyLong());
		verify(areaOrganizacionalManager).findAllSelectOrderDescricao(eq(empresaDoSistema.getId()), eq(AreaOrganizacional.ATIVA), anyLong(), anyBoolean());
		verify(funcaoManager).findByCargo(eq(historicoColaborador.getFaixaSalarial().getCargo().getId()));
		verify(ambienteManager).findByEstabelecimento(historicoColaborador.getEstabelecimento().getId());
	}
	
	private void assertQueMetodoPrepareFoiChamadoQuandoHouverSolicitacao() throws Exception {
		verify(estabelecimentoManager).findAllSelect(eq(empresaDoSistema.getId()));
		verify(indiceManager).findAll(empresaDoSistema);
		verify(faixaSalarialManager).findFaixas(eq(empresaDoSistema), eq(Cargo.ATIVO), anyLong());
		verify(areaOrganizacionalManager).findAllSelectOrderDescricao(eq(empresaDoSistema.getId()), eq(AreaOrganizacional.ATIVA), anyLong(), anyBoolean());
		verify(funcaoManager).findByCargo(eq(historicoColaborador.getFaixaSalarial().getCargo().getId()));
	}
	
	private void simulaComportamentoDoPrepare() {
		dadoQueExistemEstabelecimentosParaEmpresaDoSistema();
		dadoQueExistemIndicesCadastrados();
		dadoQueExistemFaixasSalariaisAtivasParaEmpresaDoSistema();
		dadoQueExistemAreasOrganizacionaisParaEmpresaDoSistema();
	}
	
	private void simulaComportamentoDoPrepareSemHistorico() {
		simulaComportamentoDoPrepare();
		
		when(historicoColaboradorManager.getHistoricoAtualOuFuturo(eq(1L))).thenReturn(historicoColaborador);
	}
	
	private void simulaComportamentoDoPrepareComHistorico() {
		simulaComportamentoDoPrepare();
		
		dadoQueHistoricoDoColaboradorPossuiUmCargoArea();
		dadoQueExistemFuncoesCadastradas();
		dadoQueHistoricoDoColaboradorPossuiUmEstabelecimento();
		dadoQueExistemAmbientesCadastrados();		
	}
	
	private void simulaComportamentoDoPrepareQuandoHouverSolicitacao() {
		simulaComportamentoDoPrepare();
		
		dadoQueHistoricoDoColaboradorPossuiUmCargoArea();	
	}

	private void dadoQueExisteHistoricoAtualParaColaborador() {
		action.setColaborador(ColaboradorFactory.getEntity(1L));
		when(historicoColaboradorManager.getPrimeiroHistorico(eq(1L))).thenReturn(historicoColaborador);
		when(historicoColaboradorManager.getHistoricoAtual(eq(1L))).thenReturn(historicoColaborador);
	}
	
	private void dadoQueExisteUmaSolicitacao() {
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		
		action.setSolicitacao(solicitacao);
		action.setHistoricoColaborador(null);
		
		when(solicitacaoManager.findById(eq(1L))).thenReturn(solicitacao);
		when(faixaSalarialManager.findById(eq(1L))).thenReturn(faixaSalarial);
	}

	private void dadoQueExistemAmbientesCadastrados() {
		when(ambienteManager.findByEstabelecimento(eq(new Long[]{historicoColaborador.getEstabelecimento().getId()}))).thenReturn(Collections.EMPTY_LIST);
	}

	private void dadoQueHistoricoDoColaboradorPossuiUmEstabelecimento() {
		historicoColaborador.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
	}
	
	private void dadoQueExistemFuncoesCadastradas() {
		when(funcaoManager.findByCargo(eq(historicoColaborador.getFaixaSalarial().getCargo().getId()))).thenReturn(Collections.EMPTY_LIST);
	}

	private void dadoQueHistoricoDoColaboradorPossuiUmCargoArea() {
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity(1L);
		faixa.setCargo(CargoFactory.getEntity(1L));
		historicoColaborador.setFaixaSalarial(faixa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
	}

	private void dadoQueExistemAreasOrganizacionaisParaEmpresaDoSistema(){
		try {
			when(areaOrganizacionalManager.findAllSelectOrderDescricao(eq(empresaDoSistema.getId()), eq(AreaOrganizacional.ATIVA), anyLong(), anyBoolean())).thenReturn(Collections.EMPTY_LIST);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void dadoQueExistemFaixasSalariaisAtivasParaEmpresaDoSistema() {
		when(faixaSalarialManager.findFaixas(eq(empresaDoSistema), eq(Cargo.ATIVO), anyLong())).thenReturn(Collections.EMPTY_LIST);
	}
	
	private void dadoQueExistemIndicesCadastrados() {
		when(indiceManager.findAll(empresaDoSistema)).thenReturn(Collections.EMPTY_LIST);
	}

	private void dadoQueExistemEstabelecimentosParaEmpresaDoSistema() {
		when(estabelecimentoManager.findAllSelect(eq(empresaDoSistema.getId()))).thenReturn(Collections.EMPTY_LIST);
	}
}
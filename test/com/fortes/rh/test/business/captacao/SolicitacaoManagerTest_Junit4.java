package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.captacao.SolicitacaoManagerImpl;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringUtil.class,SecurityUtil.class})
public class SolicitacaoManagerTest_Junit4
{
	private SolicitacaoManagerImpl solicitacaoManager = new SolicitacaoManagerImpl();
	private SolicitacaoDao solicitacaoDao;
	private UsuarioManager usuarioManager;

	@Before
	public void setUp() throws Exception
	{
		solicitacaoDao = mock(SolicitacaoDao.class);
		solicitacaoManager.setDao(solicitacaoDao);
		usuarioManager = mock(UsuarioManager.class);
		
		PowerMockito.mockStatic(SpringUtil.class);
		PowerMockito.mockStatic(SecurityUtil.class);
	}
	
	@Test
	public void testCalculaIndicadorVagasPreenchidasNoPrazo(){
		Long empresaId = 2L;
		Long[] estabelecimentosIds = new Long[]{1L, 2L};
		Long[] areasIds = new Long[]{1L, 5L};
		Long[] solicitacoesIds = new Long[]{};
		Date dataDe = new Date();
		Date dataAte = DateUtil.incrementaDias(dataDe, 10);
		
		Solicitacao sol1 = SolicitacaoFactory.getSolicitacao();
		sol1.setQtdVagasPreenchidas(2);
		sol1.setQuantidade(1);
		
		Solicitacao sol2 = SolicitacaoFactory.getSolicitacao();
		sol2.setQtdVagasPreenchidas(1);
		sol2.setQuantidade(2);
		
		Collection<Solicitacao> solicitacoes = Arrays.asList(sol1,sol2);
		
		when(solicitacaoDao.calculaIndicadorVagasPreenchidasNoPrazo(empresaId, estabelecimentosIds, areasIds, solicitacoesIds, dataDe, dataAte)).thenReturn(solicitacoes);

		Double percentual = solicitacaoManager.calculaIndicadorVagasPreenchidasNoPrazo(empresaId, estabelecimentosIds, areasIds, solicitacoesIds, dataDe, dataAte);
		
		assertEquals(new Double(75.0), percentual);
	}
	
	@Test
	public void testCalculaIndicadorVagasPreenchidasNoPrazoSemSolicitacao(){
		Collection<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		when(solicitacaoDao.calculaIndicadorVagasPreenchidasNoPrazo(null, null, null, null, null, null)).thenReturn(solicitacoes);

		Double percentual = solicitacaoManager.calculaIndicadorVagasPreenchidasNoPrazo(null, null, null, null, null, null);
		
		assertEquals(new Double(0.0), percentual);
	}
	
	@Test
	public void testGetCountComUsuarioValido() {
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		Cargo cargo = CargoFactory.getEntity(-1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(-1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(-1L);
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();

		Solicitacao solicitacao1 = criaSolicitacao(1L, DateUtil.criarDataMesAno(1, 1, 2016), DateUtil.criarDataMesAno(1, 2, 2016));
		Solicitacao solicitacao2 = criaSolicitacao(2L, DateUtil.criarDataMesAno(1, 1, 2016), DateUtil.criarDataMesAno(1, 3, 2016));
		Collection<Solicitacao> solicitacaos = Arrays.asList(solicitacao1, solicitacao2);

		Long[] areasIds = null;
		String codigoBusca = null;
		Date dataFim = null;
		Date dataEncerramentoIni = DateUtil.criarDataMesAno(1, 2, 2016);
		Date dataEncerramentoFim = DateUtil.criarDataMesAno(1, 3, 2016);

		char visualizar = 'E';
		char status = 'T';

		when(solicitacaoDao.getCount(eq(visualizar), eq(empresa.getId()), eq(usuario.getId()), eq(estabelecimento.getId()), eq(areaOrganizacional.getId()), eq(cargo.getId()), 
				eq(motivoSolicitacao.getId()), anyString(), eq(status), eq(areasIds), eq(codigoBusca), any(Date.class), eq(dataFim), eq(false), eq(dataEncerramentoIni), eq(dataEncerramentoFim))).thenReturn(solicitacaos.size());

		int resutado = solicitacaoManager.getCount(visualizar, empresa.getId(), usuario.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), "desc", status, null, null, new Date(), null, false, dataEncerramentoIni, dataEncerramentoFim);

		assertEquals(solicitacaos.size(), resutado);
	}
	
	@Test
	public void testFindAllByVisualizacaoComUsuarioValido()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		Cargo cargo = CargoFactory.getEntity(-1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(-1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		MotivoSolicitacao motivo = MotivoSolicitacaoFactory.getEntity();

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(3L);

		Collection<Solicitacao> solicitacaos = Arrays.asList(solicitacao);
		Long[] areasIds = null;
		Date dataEncerramentoIni = null;
		Date dataEncerramentoFim = null;
		
		char visualizar = 'E';
		char status = 'T';
		
		when(solicitacaoDao.findAllByVisualizacao(
				eq(1), eq(15), eq(visualizar), eq(empresa.getId()), eq(usuario.getId()), eq(estabelecimento.getId()), eq(areaOrganizacional.getId()), eq(cargo.getId()), eq(motivo.getId()), 
				anyString(), eq(status), eq(areasIds) , eq("3"), any(Date.class), any(Date.class), eq(false), eq(dataEncerramentoIni), eq(dataEncerramentoFim))).thenReturn(solicitacaos);
				
		Collection<Solicitacao> resultado = solicitacaoManager.findAllByVisualizacao(1, 15, visualizar, empresa.getId(), usuario.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivo.getId(), null, status, null, "3", null, new Date(), false, dataEncerramentoIni, dataEncerramentoFim );

		assertEquals(solicitacaos.size(), resultado.size());
	}

	@Test
	public void testGetCountComUsuarioNulo()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		MockSpringUtilJUnit4.mocks.put("usuarioManager", usuarioManager);

		Cargo cargo = CargoFactory.getEntity(-1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(-1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		MotivoSolicitacao motivo = MotivoSolicitacaoFactory.getEntity();

		Solicitacao solicitacao1 = criaSolicitacao(1L, DateUtil.criarDataMesAno(01, 01, 2015), null);
		Solicitacao solicitacao2 = criaSolicitacao(2L, DateUtil.criarDataMesAno(31, 01, 2015), null);
		Collection<Solicitacao> solicitacaos = Arrays.asList(solicitacao1, solicitacao2);

		char visualizar = 'E';
		char status = 'T';

		when(solicitacaoDao.getCount(eq(visualizar), eq(empresa.getId()),  eq(usuario.getId()), eq(estabelecimento.getId()), eq(areaOrganizacional.getId()), eq(cargo.getId()), eq(motivo.getId()),
				anyString(), eq(status), any(Long[].class), anyString(), eq(DateUtil.criarDataMesAno(01, 01, 2015)), eq(DateUtil.criarDataMesAno(31, 01, 2015)), eq(true), any(Date.class), any(Date.class)))
				.thenReturn(solicitacaos.size());

		int resultado = solicitacaoManager.getCount(visualizar, empresa.getId(), usuario.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivo.getId(), "", status, null, null,
				DateUtil.criarDataMesAno(01, 01, 2015), DateUtil.criarDataMesAno(31, 01, 2015), true, null, null);
		
		assertEquals(solicitacaos.size(), resultado);
	}

	@Test
	public void testFindAllByVisualizacaoComUsuarioNulo()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		MockSpringUtilJUnit4.mocks.put("usuarioManager", usuarioManager);

		Cargo cargo = CargoFactory.getEntity(-1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(-1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		MotivoSolicitacao motivo = MotivoSolicitacaoFactory.getEntity();

		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao(1L);
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao(2L);
		Solicitacao solicitacao3 = SolicitacaoFactory.getSolicitacao(3L);

		Collection<Solicitacao> solicitacaos = Arrays.asList(solicitacao1, solicitacao2, solicitacao3);

		char visualizar = 'E';
		char status = 'T';

		when(solicitacaoDao.findAllByVisualizacao(eq(1), eq(15), eq(visualizar), eq(empresa.getId()), eq(usuario.getId()), eq(estabelecimento.getId()), eq(areaOrganizacional.getId()), eq(cargo.getId()),
				eq(motivo.getId()), eq(""), eq(status), any(Long[].class), eq(""), any(Date.class), any(Date.class), eq(true), any(Date.class), any(Date.class))).thenReturn(solicitacaos);

		Collection<Solicitacao> resultado = solicitacaoManager.findAllByVisualizacao(1, 15, visualizar, empresa.getId(), usuario.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivo.getId(), "", status, null, "", null, null, true, null, null);
		assertEquals(solicitacaos.size(), resultado.size());
	}
	
	private Solicitacao criaSolicitacao(Long id, Date dataSolicitacao, Date dataEncerramento){
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setId(id);
		solicitacao.setData(dataSolicitacao);
		solicitacao.setDataEncerramento(dataEncerramento);
		return solicitacao;
	}
}
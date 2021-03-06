package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManagerImpl;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.AcompanhamentoExperienciaColaborador;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.CategoriaESocial;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.geral.relatorio.TurnOverCollection;
import com.fortes.rh.model.json.ColaboradorJson;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TNaturalidadeAndNacionalidade;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class ColaboradorManagerTest_Junit4
{
	private ColaboradorManagerImpl colaboradorManager = new ColaboradorManagerImpl();
	private ColaboradorManager mockColaboradorManager;
    private ColaboradorDao colaboradorDao;
    private CandidatoManager candidatoManager;
    private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
    private MensagemManager mensagemManager;
    private UsuarioManager usuarioManager;
    private CidadeManager cidadeManager;
    private AcPessoalClientColaborador acPessoalClientColaborador;
    private HistoricoColaboradorManager historicoColaboradorManager;
    private PlatformTransactionManager transactionManager;
    private AreaOrganizacionalManager areaOrganizacionalManager;
    private EstadoManager estadoManager;
    private EmpresaManager empresaManager;
    private ParametrosDoSistemaManager parametrosDoSistemaManager;
    private UsuarioEmpresaManager usuarioEmpresaManager;
    private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
    private CargoManager cargoManager;

    @SuppressWarnings("deprecation")
	@Before
    public void setUp() throws Exception
    {
        colaboradorDao = mock(ColaboradorDao.class);
        mockColaboradorManager = mock(ColaboradorManager.class);
        candidatoManager = mock(CandidatoManager.class);
        candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
        mensagemManager = mock(MensagemManager.class);
        cidadeManager = mock(CidadeManager.class);
        acPessoalClientColaborador = mock(AcPessoalClientColaborador.class);
        historicoColaboradorManager= mock(HistoricoColaboradorManager.class);
        transactionManager = mock(PlatformTransactionManager.class);
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        estadoManager = mock(EstadoManager.class);
        empresaManager = mock(EmpresaManager.class);
        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
        cargoManager=mock(CargoManager.class);
        
        colaboradorManager.setDao(colaboradorDao);
        colaboradorManager.setCandidatoManager(candidatoManager);
        colaboradorManager.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);
        colaboradorManager.setMensagemManager(mensagemManager);
        colaboradorManager.setCidadeManager(cidadeManager);
        colaboradorManager.setAcPessoalClientColaborador(acPessoalClientColaborador);
        colaboradorManager.setHistoricoColaboradorManager(historicoColaboradorManager);
        colaboradorManager.setTransactionManager(transactionManager);
        colaboradorManager.setAreaOrganizacionalManager(areaOrganizacionalManager);
        colaboradorManager.setEstadoManager(estadoManager);
        colaboradorManager.setEmpresaManager(empresaManager);
        colaboradorManager.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        colaboradorManager.setGerenciadorComunicacaoManager(gerenciadorComunicacaoManager);
        colaboradorManager.setCargoManager(cargoManager);
        
        usuarioManager = mock(UsuarioManager.class);
        usuarioEmpresaManager = mock(UsuarioEmpresaManager.class);
        
    	PowerMockito.mockStatic(SpringUtil.class);
    	BDDMockito.given(SpringUtil.getBeanOld("usuarioManager")).willReturn(usuarioManager);
    	BDDMockito.given(SpringUtil.getBeanOld("usuarioEmpresaManager")).willReturn(usuarioEmpresaManager);
    }
    
    @Test
	public void testReligaColaborador() 
	{
    	Long colaboradorId = 1L;
    	Exception exception = null;

    	try {
			colaboradorManager.religaColaborador(colaboradorId);
		} catch (Exception e) {
			exception = e;
		}
    	assertNull(exception);
	}
    
    @Test
	public void testReligaColaboradorAC() 
	{
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	String codigoAC = "000001";
    	String empresaCodigoAC =	"0002";
    	String grupoAC = "0001"; 
    	
    	when(colaboradorDao.findByCodigoAC(codigoAC, empresaCodigoAC, grupoAC)).thenReturn(colaborador);
		colaboradorManager.religaColaboradorAC(codigoAC, empresaCodigoAC, grupoAC);
    	assertEquals(colaborador.getId(), colaboradorManager.religaColaboradorAC(codigoAC, empresaCodigoAC, grupoAC));
	}
    
    @Test
    public void testReenviaAguardandoContratacao() throws Exception{
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Cidade cidade = CidadeFactory.getEntity(1L);
    	cidade.setUf(EstadoFactory.getEntity(1L));
    	
    	Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
    	colaborador1.setHistoricoColaborador(HistoricoColaboradorFactory.getEntity(1L));
    	colaborador1.getPessoal().setEscolaridade(Escolaridade.ESPECIALIZACAO_INCOMPLETA);
    	colaborador1.getEndereco().setCidade(cidade);
    	
    	Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
    	colaborador2.setHistoricoColaborador(HistoricoColaboradorFactory.getEntity(2L));
    	colaborador2.getEndereco().setCidade(cidade);
    	
    	Collection<Colaborador> colaboradores = Arrays.asList(colaborador1, colaborador2);
    	
    	when(colaboradorDao.findByEmpresaAndStatusAC(empresa.getId(), null, null, StatusRetornoAC.AGUARDANDO, true, false, SituacaoColaborador.ATIVO, true, "c.nome")).thenReturn(colaboradores);
    	when(cidadeManager.findByIdProjection(cidade.getId())).thenReturn(cidade);
    	when(historicoColaboradorManager.bindSituacao(colaborador1.getHistoricoColaborador(), empresa.getCodigoAC())).thenReturn(new TSituacao());
    	when(historicoColaboradorManager.bindSituacao(colaborador2.getHistoricoColaborador(), empresa.getCodigoAC())).thenReturn(new TSituacao());
    	
    	Exception ex = null;
    	try {
    		colaboradorManager.reenviaAguardandoContratacao(empresa);
		} catch (Exception e) {
			ex = e;
		}
    	
    	assertNull(ex);
    }
    
    @Test
    public void testGetColaboradoresJson() {
    	String baseCnpj = null;
    	Long colaboradorId = 1L;
    	
		Collection<ColaboradorJson> colaboradoresJson = Arrays.asList(new ColaboradorJson());
		when(colaboradorDao.getColaboradoresJson(baseCnpj, colaboradorId, null)).thenReturn(colaboradoresJson);
		assertEquals(colaboradoresJson, colaboradorManager.getColaboradoresJson(baseCnpj, colaboradorId, null));
    }
    
    @Test(expected=ColecaoVaziaException.class)
    public void testFindAniversariantesPorTempoDeEmpresaException() throws Exception {
    	int mes = 2;
    	Long[] empresaIds = new Long[]{1L};
    	Long[] estabelecimentoIds = new Long[]{2L, 3L};
    	Long[] areaIds = new Long[]{5L};
    	
		when(colaboradorDao.findAniversariantesPorTempoDeEmpresa(mes, true, empresaIds, estabelecimentoIds, areaIds)).thenReturn(new ArrayList<Colaborador>());
		colaboradorManager.findAniversariantesPorTempoDeEmpresa(mes, true, empresaIds, estabelecimentoIds, areaIds);
	}
    
    @Test
    public void testFindAniversariantesPorTempoDeEmpresa() throws Exception {
    	int mes = 2;
    	Long[] empresaIds = new Long[]{1L};
    	Long[] estabelecimentoIds = new Long[]{2L, 3L};
    	Long[] areaIds = new Long[]{5L};
    	
		Collection<Colaborador> colaboradores = Arrays.asList(ColaboradorFactory.getEntity());
		when(colaboradorDao.findAniversariantesPorTempoDeEmpresa(mes, false, empresaIds, estabelecimentoIds, areaIds)).thenReturn(colaboradores);
		assertEquals(1, colaboradorManager.findAniversariantesPorTempoDeEmpresa(mes, false, empresaIds, estabelecimentoIds, areaIds).size());
	}
    
    @Test
    public void testSolicitacaoDesligamentoAc() throws Exception {
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);
        Exception exception = null;
        try {
        	colaboradorManager.desligaColaborador(true, new Date(), "observacao", 1L, 'I', true, true, colaborador.getId());
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
    }
    @Test
    public void testDesligaColaborador() throws Exception
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	Exception exception = null;
        try {
        	colaboradorManager.desligaColaborador(true, new Date(), "observacao", 1L, 'I', false, false, colaborador.getId());
		} catch (Exception e) {
			exception = e;
		}
    	assertNull(exception);
    }
    
    @Test
    public void testFindAdmitidosNoPeriodo() throws Exception
    {
    	Date dataReferencia = DateUtil.criarDataMesAno(25, 01, 2011);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Integer tempoDeEmpresa = 150;
    	    	
    	String[] areasCheck = new String[1];
    	String[] estabelecimentoCheck = new String[1];

    	Colaborador colaborador1 = new Colaborador(1L, "joao", "", dataReferencia, "", "", null, "pedro", 40, "areaNome", "areaMaeNome", "estabelecimentoNome");
    	Colaborador colaborador2 = new Colaborador(2L, "maria", "", dataReferencia, "", "", null, "pedro", 12,  "areaNome", "areaMaeNome", "estabelecimentoNome");
    	
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(colaborador1);
    	colaboradores.add(colaborador2);
    	
    	Colaborador colaborador3 = new Colaborador(1L, dataReferencia, 1.0, 12, 1L, "", "");
    	Colaborador colaborador5 = new Colaborador(1L, dataReferencia, 1.0, 33, 2L, "", "");
    	Colaborador colaborador4 = new Colaborador(2L, dataReferencia, 1.0, 12, 1L, "", "");
    	
    	Collection<Colaborador> colaboradoresComAvaliacoes = new ArrayList<Colaborador>();
    	colaboradoresComAvaliacoes.add(colaborador3);
    	colaboradoresComAvaliacoes.add(colaborador5);
    	colaboradoresComAvaliacoes.add(colaborador4);
    	
    	Collection<PeriodoExperiencia> periodoExperiencias = new ArrayList<PeriodoExperiencia>();

    	PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(1L);
    	periodoExperiencia.setDias(10);

    	PeriodoExperiencia periodoExperiencia2 = PeriodoExperienciaFactory.getEntity(2L);
    	periodoExperiencia2.setDias(30);
    	
    	periodoExperiencias.add(periodoExperiencia);
    	periodoExperiencias.add(periodoExperiencia2);
    	
    	when(colaboradorDao.findAdmitidosNoPeriodo(null, dataReferencia, empresa, areasCheck, estabelecimentoCheck, tempoDeEmpresa)).thenReturn(colaboradores);
    	when(colaboradorDao.findComAvaliacoesExperiencias(null, dataReferencia, empresa, areasCheck, estabelecimentoCheck)).thenReturn(colaboradoresComAvaliacoes);
    	
    	Collection<Colaborador> colabs = colaboradorManager.getAvaliacoesExperienciaPendentes(null, dataReferencia, empresa, areasCheck, estabelecimentoCheck, tempoDeEmpresa, null, periodoExperiencias);
    	assertEquals(2, colabs.size());
    	assertEquals("10 dias, respondida (12 dias)\n30 dias, respondida (33 dias)", ((Colaborador)colabs.toArray()[0]).getStatusAvaliacao());
    	assertEquals("10 dias, respondida (12 dias)", ((Colaborador)colabs.toArray()[1]).getStatusAvaliacao());
    	assertEquals("03/02/2011\n23/02/2011", ((Colaborador)colabs.toArray()[0]).getDatasDeAvaliacao());
    	assertEquals("03/02/2011", ((Colaborador)colabs.toArray()[1]).getDatasDeAvaliacao());
    }
    
    @Test
    public void testGetAvaliacoesExperienciaPendentesPeriodo() throws Exception
    {
    	Date dataFim = DateUtil.criarDataMesAno(25, 01, 2011);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	String[] areasCheck = new String[1];
    	String[] estabelecimentoCheck = new String[1];
    	
    	Colaborador colaborador1 = new Colaborador(1L, "joao","", dataFim, "", "", null, "pedro", 40, "areaNome", "areaMaeNome", "estabelecimentoNome");
    	colaborador1.setMatricula("1232456");
    	Colaborador colaborador2 = new Colaborador(2L, "maria","", dataFim, "", "", null, "pedro", 12, "areaNome", "areaMaeNome", "estabelecimentoNome");
    	colaborador2.setMatricula("9999999");
    	
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(colaborador1);
    	colaboradores.add(colaborador2);
    	
    	Colaborador colaboradorResposta3 = new Colaborador(2L, dataFim, 1.0, 30, 1L, "", "");
    	Colaborador colaboradorResposta4 = new Colaborador(2L, dataFim, 1.0, 55, 3L, "", "");
    	Colaborador colaboradorResposta5 = new Colaborador(5L, dataFim, 1.0, 33, 2L, "", "");
    	
    	Collection<Colaborador> colaboradoresComAvaliacoes = new ArrayList<Colaborador>();
    	colaboradoresComAvaliacoes.add(colaboradorResposta3);
    	colaboradoresComAvaliacoes.add(colaboradorResposta4);
    	colaboradoresComAvaliacoes.add(colaboradorResposta5);
    	    	
    	PeriodoExperiencia periodoExperiencia30Dias = PeriodoExperienciaFactory.getEntity(1L);
    	periodoExperiencia30Dias.setDias(30);
    	
    	PeriodoExperiencia periodoExperiencia60Dias = PeriodoExperienciaFactory.getEntity(2L);
    	periodoExperiencia60Dias.setDias(60);
    	
    	PeriodoExperiencia periodoExperiencia90Dias = PeriodoExperienciaFactory.getEntity(3L);
    	periodoExperiencia90Dias.setDias(90);
    	
    	Collection<PeriodoExperiencia> periodoExperiencias = new ArrayList<PeriodoExperiencia>();
    	periodoExperiencias.add(periodoExperiencia30Dias);
    	periodoExperiencias.add(periodoExperiencia60Dias);
    	periodoExperiencias.add(periodoExperiencia90Dias);
    	
    	when(colaboradorDao.findAdmitidosNoPeriodo(null, null, empresa, areasCheck, estabelecimentoCheck, null)).thenReturn(colaboradores);
    	when(colaboradorDao.findComAvaliacoesExperiencias(null, null, empresa, areasCheck, estabelecimentoCheck)).thenReturn(colaboradoresComAvaliacoes);
    	
    	Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
    	
		when(areaOrganizacionalManager.findByEmpresasIds(new Long[]{empresa.getId()}, AreaOrganizacional.TODAS)).thenReturn(areas);
		when(areaOrganizacionalManager.montaFamilia(areas)).thenReturn(areas);
		when(areaOrganizacionalManager.getAreaOrganizacional(areas, null)).thenReturn(new AreaOrganizacional());
    	
    	List<AcompanhamentoExperienciaColaborador> acompanhamentos = colaboradorManager.getAvaliacoesExperienciaPendentesPeriodo(DateUtil.criarDataMesAno(20, 01, 2010), DateUtil.criarDataMesAno(20, 01, 2012), empresa, areasCheck, estabelecimentoCheck, periodoExperiencias, false);
    	assertEquals(2, acompanhamentos.size());
    	
    	assertEquals("1232456", acompanhamentos.get(0).getMatricula());
    	assertEquals(3, acompanhamentos.get(0).getPeriodoExperiencias().size());
    	assertEquals("Previsto: 23/02/2011", acompanhamentos.get(0).getPeriodoExperiencias().get(0));
    	assertEquals("Previsto: 25/03/2011", acompanhamentos.get(0).getPeriodoExperiencias().get(1));
    	assertEquals("Previsto: 24/04/2011", acompanhamentos.get(0).getPeriodoExperiencias().get(2));

    	assertEquals("9999999", acompanhamentos.get(1).getMatricula());
    	assertEquals(3, acompanhamentos.get(1).getPeriodoExperiencias().size());
    	assertNotNull(acompanhamentos.get(1).getPeriodoExperiencias().get(0));
    	assertEquals("Previsto: 25/03/2011", acompanhamentos.get(1).getPeriodoExperiencias().get(1));
    	assertNotNull(acompanhamentos.get(1).getPeriodoExperiencias().get(2));
    }
    
    @Test
    public void testGetAvaliacoesExperienciaPendentesPeriodoExibirTituloAvaliacao() throws Exception
    {
    	Date dataFim = DateUtil.criarDataMesAno(25, 01, 2011);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	String[] areasCheck = new String[1];
    	String[] estabelecimentoCheck = new String[1];
    	
    	Colaborador colaborador1 = new Colaborador(1L, "joao","", dataFim, "", "", null, "pedro", 40, "areaNome", "areaMaeNome", "estabelecimentoNome");
    	colaborador1.setMatricula("1232456");
    	Colaborador colaborador2 = new Colaborador(2L, "maria","", dataFim, "", "", null, "pedro", 12, "areaNome", "areaMaeNome", "estabelecimentoNome");
    	colaborador2.setMatricula("9999999");
    	
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(colaborador1);
    	colaboradores.add(colaborador2);
    	
    	Colaborador colaboradorResposta3 = new Colaborador(2L, dataFim, 1.0, 30, 1L, "", "");
    	Colaborador colaboradorResposta4 = new Colaborador(2L, dataFim, 1.0, 55, 3L, "", "");
    	Colaborador colaboradorResposta5 = new Colaborador(5L, dataFim, 1.0, 33, 2L, "", "");
    	
    	Collection<Colaborador> colaboradoresComAvaliacoes = new ArrayList<Colaborador>();
    	colaboradoresComAvaliacoes.add(colaboradorResposta3);
    	colaboradoresComAvaliacoes.add(colaboradorResposta4);
    	colaboradoresComAvaliacoes.add(colaboradorResposta5);
    	    	
    	PeriodoExperiencia periodoExperiencia30Dias = PeriodoExperienciaFactory.getEntity(1L);
    	periodoExperiencia30Dias.setDias(30);
    	
    	PeriodoExperiencia periodoExperiencia60Dias = PeriodoExperienciaFactory.getEntity(2L);
    	periodoExperiencia60Dias.setDias(60);
    	
    	PeriodoExperiencia periodoExperiencia90Dias = PeriodoExperienciaFactory.getEntity(3L);
    	periodoExperiencia90Dias.setDias(90);
    	
    	Collection<PeriodoExperiencia> periodoExperiencias = new ArrayList<PeriodoExperiencia>();
    	periodoExperiencias.add(periodoExperiencia30Dias);
    	periodoExperiencias.add(periodoExperiencia60Dias);
    	periodoExperiencias.add(periodoExperiencia90Dias);
    	
    	when(colaboradorDao.findAdmitidosNoPeriodo(null, null, empresa, areasCheck, estabelecimentoCheck, null)).thenReturn(colaboradores);
    	when(colaboradorDao.findComAvaliacoesExperiencias(null, null, empresa, areasCheck, estabelecimentoCheck)).thenReturn(colaboradoresComAvaliacoes);
    	
    	Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
    	
		when(areaOrganizacionalManager.findByEmpresasIds(new Long[]{empresa.getId()}, AreaOrganizacional.TODAS)).thenReturn(areas);
		when(areaOrganizacionalManager.montaFamilia(areas)).thenReturn(areas);
		when(areaOrganizacionalManager.getAreaOrganizacional(areas, null)).thenReturn(new AreaOrganizacional());
    	
    	List<AcompanhamentoExperienciaColaborador> acompanhamentos = colaboradorManager.getAvaliacoesExperienciaPendentesPeriodo(DateUtil.criarDataMesAno(20, 01, 2010), DateUtil.criarDataMesAno(20, 01, 2012), empresa, areasCheck, estabelecimentoCheck, periodoExperiencias, true);
    	assertEquals(6, acompanhamentos.size());
    	
    	assertEquals("1232456", acompanhamentos.get(0).getMatricula());
    	assertEquals("Previsto: 23/02/2011", acompanhamentos.get(0).getPeriodoExperiencia());
    	assertEquals("Previsto: 25/03/2011", acompanhamentos.get(1).getPeriodoExperiencia());
    	assertEquals("Previsto: 24/04/2011", acompanhamentos.get(2).getPeriodoExperiencia());

    	assertEquals("9999999", acompanhamentos.get(3).getMatricula());
    	assertNotNull(acompanhamentos.get(1).getPeriodoExperiencia());
    	assertEquals("Previsto: 25/03/2011", acompanhamentos.get(1).getPeriodoExperiencia());
    	assertNotNull(acompanhamentos.get(1).getPeriodoExperiencia());
    }
    
    @Test
    public void testFindByEmpresaEstabelecimentoAndAreaOrganizacional(){
    	Long[] empresasIds = new Long[]{1L};
    	Long[] estabelecimentosIds = null;
    	Long[] areasIds = null;
    	
    	when(colaboradorDao.findByEmpresaEstabelecimentoAndAreaOrganizacional(empresasIds, estabelecimentosIds, areasIds, SituacaoColaborador.ATIVO)).thenReturn(new ArrayList<Colaborador>());
    	assertEquals(0, colaboradorManager.findByEmpresaEstabelecimentoAndAreaOrganizacional(empresasIds, estabelecimentosIds, areasIds, SituacaoColaborador.ATIVO).size());
	}
    
    @Test
    public void testFindColaboradoresQueNuncaRealizaramTreinamentoException() throws Exception {
    	Long[] empresaId = new Long[]{2L};
    	Long[] cursosIds = new Long[]{1L};
    	Long[] areaIds = new Long[]{2L, 3L};
    	Long[] estabelecimentosIds = null;
    	 
    	when(colaboradorDao.findColaboradoresQueNuncaRealizaramTreinamento(empresaId, areaIds, estabelecimentosIds)).thenReturn(new ArrayList<Colaborador>());
    	Exception exception = null;
    	try {
    		colaboradorManager.findColaboradoresQueNuncaRealizaramTreinamento(empresaId, cursosIds, areaIds, estabelecimentosIds);
		} catch (Exception e) {
			exception = e;
		}
    	assertNotNull(exception);
    	assertTrue(exception instanceof ColecaoVaziaException);
    	
	}

    @Test
    public void testFindColaboradoresQueNuncaRealizaramTreinamento() throws Exception {
    	Long[] empresaId = new Long[]{2L};
    	Long[] cursosIds = new Long[]{1L};
    	Long[] areaIds = new Long[]{2L, 3L};
    	Long[] estabelecimentosIds = null;
    	
    	when(colaboradorDao.findColaboradoresQueNuncaRealizaramTreinamento(empresaId, areaIds, estabelecimentosIds)).thenReturn(Arrays.asList(ColaboradorFactory.getEntity()));
    	assertEquals(1, colaboradorManager.findColaboradoresQueNuncaRealizaramTreinamento(empresaId, cursosIds, areaIds, estabelecimentosIds).size());
    }
    
	private TEmpregado iniciaTEmpregado() {
		TEmpregado empregado = new TEmpregado();
    	empregado.setId(1);
    	empregado.setCidadeCodigoAC("1234");
    	empregado.setUfSigla("UF");
    	empregado.setSexo("M");
    	empregado.setDeficiencia("1");
    	empregado.setCtpsDV("");
    	empregado.setIdentidadeUF("UF");
    	empregado.setCtpsUFSigla("UF");
    	empregado.setCtpsDV("1");
    	empregado.setEscolaridade("Superior Completo");
		return empregado;
	}
    
    @Test
    public void testSaveEmpregadosESituacoes() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setCriarUsuarioAutomaticamente(true);
    	
    	TEmpregado tEmpregado = iniciaTEmpregado();
    	tEmpregado.setCodigoACDestino("codigoACDestino");
    	tEmpregado.setCodigoAC("tEmp1");
    	
    	Pessoal pessoal = new Pessoal();
    	pessoal.setEscolaridade("Especialização");
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setPessoal(pessoal);
    	Estado estado = EstadoFactory.getEntity(1L);
    	Cidade cidade = CidadeFactory.getEntity();
    	cidade.setId(1L);
    	cidade.setUf(estado);
    	
    	TSituacao tSituacao1 = new TSituacao();
    	tSituacao1.setEmpregadoCodigoAC("tEmp1");
    	tSituacao1.setEmpregadoCodigoACDestino("codigoACDestino");
    	TSituacao[] tSituacoes = new TSituacao[]{tSituacao1};
    	
    	Usuario usuario = new Usuario(colaborador.getNome(), colaborador.getPessoal().getCpf(), empresa.getSenhaPadrao(), true, colaborador);
    	
    	when(cidadeManager.findByCodigoAC("1234", "UF")).thenReturn(cidade);
		when(estadoManager.findBySigla("UF")).thenReturn(estado);
		when(empresaManager.findEntidadeComAtributosSimplesById(empresa.getId())).thenReturn(empresa);
		when(usuarioManager.existeLogin(usuario)).thenReturn(false);
		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(parametrosDoSistema);
    	
    	Exception e = null;
    	try {
    		colaboradorManager.saveEmpregadosESituacoes(new TEmpregado[]{tEmpregado}, tSituacoes, empresa);
    	} catch (Exception e2) {
    		e = e2;
    	}
    	assertNull(e);
    }
    
    @Test
    public void testSaveEmpregadosESituacoesException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	
    	TEmpregado tEmpregado = iniciaTEmpregado();
    	tEmpregado.setCodigoAC("tEmp1");
    	
    	Pessoal pessoal = new Pessoal();
    	pessoal.setEscolaridade("Especialização");
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setPessoal(pessoal);
    	Estado estado = EstadoFactory.getEntity(1L);
    	Cidade cidade = CidadeFactory.getEntity();
    	cidade.setId(1L);
    	cidade.setUf(estado);
    	
    	TSituacao tSituacao1 = new TSituacao();
    	tSituacao1.setEmpregadoCodigoAC("tEmp1");
		TSituacao[] tSituacoes = new TSituacao[]{tSituacao1};
    	
		when(cidadeManager.findByCodigoAC("1234", "UF")).thenReturn(cidade);
		when(estadoManager.findBySigla("UF")).thenReturn(estado);
		when(empresaManager.findEntidadeComAtributosSimplesById(empresa.getId())).thenReturn(empresa);
    	when(empresaManager.findEntidadeComAtributosSimplesById(empresa.getId())).thenReturn(empresa);
    	
    	Exception e = null;
    	try {
    		colaboradorManager.saveEmpregadosESituacoes(new TEmpregado[]{tEmpregado}, tSituacoes, empresa);
		} catch (Exception e2) {
			e = e2;
		}
    	
    	assertEquals("O empregado null está sem situação.", e.getMessage());
    }
    
    @Test
    public void testGetNaturalidadesAndNacioanlidades() throws IntegraACException, Exception{
		
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(true);
    	empresa.setCodigoAC("0001");
    	empresa.setGrupoAC("001");
    	
    	TNaturalidadeAndNacionalidade tNaturalidadeAndNacionalidade1 = new TNaturalidadeAndNacionalidade();
    	tNaturalidadeAndNacionalidade1.setCodigo("001");
    	tNaturalidadeAndNacionalidade1.setNaturalidade("Buenos Aires");
    	tNaturalidadeAndNacionalidade1.setNacionalidade("Agentina");
    	
    	TNaturalidadeAndNacionalidade tNaturalidadeAndNacionalidade2 = new TNaturalidadeAndNacionalidade();
    	tNaturalidadeAndNacionalidade2.setCodigo("003");
    	tNaturalidadeAndNacionalidade2.setNaturalidade("Fortaleza - CE");
    	tNaturalidadeAndNacionalidade2.setNacionalidade("Brasileira");
    	
    	TNaturalidadeAndNacionalidade[] tNaturalidades = new TNaturalidadeAndNacionalidade[]{tNaturalidadeAndNacionalidade1, tNaturalidadeAndNacionalidade2};
    	
    	Colaborador colab1 = ColaboradorFactory.getEntity("001", 1L);
    	colab1.setEmpresa(empresa);
    	Colaborador colab2 = ColaboradorFactory.getEntity("003", 3L);
    	colab2.setEmpresa(empresa);
    	Collection<Colaborador> colaboradores = Arrays.asList(colab1, colab2);
    	
    	String[] colabCodigoFP = new String[]{"001","003"};
    	
    	when(acPessoalClientColaborador.getNaturalidadesAndNacionalidades(empresa, colabCodigoFP)).thenReturn(tNaturalidades);
    	when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
    	
    	Collection<Colaborador> retorno = colaboradorManager.getNaturalidadesAndNacionalidades(colaboradores, empresa.getId());
    	
    	assertEquals(tNaturalidadeAndNacionalidade1.getNacionalidade(), ((Colaborador) retorno.toArray()[0]).getNacionalidade());
    	assertEquals(tNaturalidadeAndNacionalidade1.getNaturalidade(), ((Colaborador) retorno.toArray()[0]).getNaturalidade());
    	
    	assertEquals(tNaturalidadeAndNacionalidade2.getNacionalidade(), ((Colaborador) retorno.toArray()[1]).getNacionalidade());
    	assertEquals(tNaturalidadeAndNacionalidade2.getNaturalidade(), ((Colaborador) retorno.toArray()[1]).getNaturalidade());
	}
    
    @Test
    public void testGetNaturalidadeAndNacionalidade() throws IntegraACException, Exception{
    	TNaturalidadeAndNacionalidade tNaturalidadeAndNacionalidade1 = new TNaturalidadeAndNacionalidade();
    	tNaturalidadeAndNacionalidade1.setCodigo("001");
    	tNaturalidadeAndNacionalidade1.setNaturalidade("Buenos Aires");
    	tNaturalidadeAndNacionalidade1.setNacionalidade("Agentina");
    	
    	TNaturalidadeAndNacionalidade[] tNaturalidades = new TNaturalidadeAndNacionalidade[]{tNaturalidadeAndNacionalidade1};
    	Colaborador colab1 = ColaboradorFactory.getEntity("001", 1L);
    	
    	when(acPessoalClientColaborador.getNaturalidadesAndNacionalidades(null, new String[]{colab1.getCodigoAC()})).thenReturn(tNaturalidades);
    	
    	TNaturalidadeAndNacionalidade retorno = colaboradorManager.getNaturalidadeAndNacionalidade(null, colab1.getCodigoAC());
    	
    	assertEquals(tNaturalidadeAndNacionalidade1.getNacionalidade(), retorno.getNacionalidade());
    	assertEquals(tNaturalidadeAndNacionalidade1.getNaturalidade(), retorno.getNaturalidade());
	}
        
    @Test
    public void testExcedeuContratacoes() throws Exception {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
    	doThrow(FortesException.class).when(mockColaboradorManager).validaQtdCadastros(empresa.getId());

		boolean excedeuContratacoes = colaboradorManager.excedeuContratacao(empresa.getId());
		
		assertTrue(excedeuContratacoes);
	}
    
    @Test
    public void testNaoExcedeuContratacoes() throws Exception {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	doNothing().when(mockColaboradorManager).validaQtdCadastros(empresa.getId());
    	
    	boolean excedeuContratacoes = colaboradorManager.excedeuContratacao(empresa.getId());
    	
    	assertTrue(excedeuContratacoes);
    }
    
    @Test
    public void testFindDemitidosTurnoverTempoServicoSemAgrupamento() throws Exception
    {
    	Colaborador colaboradorA = ColaboradorFactory.getEntity(1L);
    	colaboradorA.setNome("Colaborador a");
    	colaboradorA.setTempoServico(2);
    	
    	Colaborador colaboradorB = ColaboradorFactory.getEntity(1L);
    	colaboradorB.setNome("Colaborador b");
    	colaboradorB.setTempoServico(7);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Integer[] mesesIniciais = new Integer[]{1};
    	Integer[] mesesFinais = new Integer[]{12};

    	Character semAgrupamento = 'S';
    	int filtrarPor = 1;
    	
    	Date dataAtual = new Date();
    	Date dataInicial = DateUtil.getInicioMesData(dataAtual);
    	Date dataFinal = DateUtil.getUltimoDiaMes(dataAtual);
    	
    	Collection<Colaborador> colaboradores = Arrays.asList(colaboradorB,colaboradorA);
    	
    	when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
    	when(colaboradorDao.findDemitidosTurnover(empresa, dataInicial, dataFinal,mesesIniciais,mesesFinais,null,null,null,null,semAgrupamento)).thenReturn(colaboradores);
    	
		colaboradorManager.findDemitidosTurnoverTempoServico(mesesIniciais, mesesFinais, empresa.getId(), dataInicial, dataFinal, null, null, null, null, filtrarPor, semAgrupamento);
		
		assertEquals(colaboradorA.getNome(), ((Colaborador)colaboradores.toArray()[0]).getNome());
		assertNotNull(((Colaborador)colaboradores.toArray()[0]).getTempoServicoString());
    }
    @Test
    public void testFindDemitidosTurnoverTempoServicoAgrupadoPorArea() throws Exception
    {
    	Colaborador colaboradorA = ColaboradorFactory.getEntity(1L);
    	colaboradorA.setNome("Colaborador a");
    	colaboradorA.setTempoServico(2);
    	
    	AreaOrganizacional areaOrganizacionalA = AreaOrganizacionalFactory.getEntity(1l);
    	areaOrganizacionalA.setNome("area a");
		
    	colaboradorA.setAreaOrganizacional(areaOrganizacionalA);
    	
		AreaOrganizacional areaOrganizacionalB = AreaOrganizacionalFactory.getEntity(2l);
		areaOrganizacionalB.setNome("area b");

		Colaborador colaboradorB = ColaboradorFactory.getEntity(1L);
    	colaboradorB.setNome("Colaborador b");
    	colaboradorB.setTempoServico(7);
    	colaboradorB.setAreaOrganizacional(areaOrganizacionalB);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Integer[] mesesIniciais = new Integer[]{1};
    	Integer[] mesesFinais = new Integer[]{12};
    	
    	char agruparPorArea = 'A';
    	int filtrarPorArea = 1;
    	
    	Date dataAtual = new Date();
    	Date dataInicial = DateUtil.getInicioMesData(dataAtual);
    	Date dataFinal = DateUtil.getUltimoDiaMes(dataAtual);
    	
    	Collection<Colaborador> colaboradores = Arrays.asList(colaboradorB,colaboradorA);
    	
    	when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
    	when(colaboradorDao.findDemitidosTurnover(empresa, dataInicial, dataFinal,mesesIniciais,mesesFinais,null,null,null,null,agruparPorArea)).thenReturn(colaboradores);
    	
    	colaboradorManager.findDemitidosTurnoverTempoServico(mesesIniciais, mesesFinais, empresa.getId(), dataInicial, dataFinal, null, null, null, null, filtrarPorArea, agruparPorArea);
    	
    	assertEquals(colaboradorA.getAreaOrganizacional().getNome(), ((Colaborador)colaboradores.toArray()[0]).getAreaOrganizacional().getNome());
		assertNotNull(((Colaborador)colaboradores.toArray()[0]).getTempoServicoString());
    }
    @Test
    public void testFindDemitidosTurnoverTempoServicoAgrupadoPorCargo() throws Exception
    {
    	Colaborador colaboradorA = ColaboradorFactory.getEntity(1L);
    	colaboradorA.setNome("Colaborador a");
    	colaboradorA.setTempoServico(2);
    	
    	Cargo cargoA = CargoFactory.getEntity(1l);
    	cargoA.setNome("cargo a");
    	
    	colaboradorA.setCargoNomeProjection(cargoA.getNome());

    	Colaborador colaboradorB = ColaboradorFactory.getEntity(1L);
    	colaboradorB.setNome("Colaborador b");
    	colaboradorB.setTempoServico(20);
    	
    	Cargo cargoB = CargoFactory.getEntity(1l);
    	cargoB.setNome("cargo b");
    	
    	colaboradorB.setCargoNomeProjection(cargoB.getNome());
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Integer[] mesesIniciais = new Integer[]{1};
    	Integer[] mesesFinais = new Integer[]{12};
    	
    	char agruparPorCargo = 'C';
    	int filtrarPorCargo = 2;
    	
    	Date dataAtual = new Date();
    	Date dataInicial = DateUtil.getInicioMesData(dataAtual);
    	Date dataFinal = DateUtil.getUltimoDiaMes(dataAtual);
    	
    	Collection<Colaborador> colaboradores = Arrays.asList(colaboradorB,colaboradorA);
    	
    	when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
    	when(colaboradorDao.findDemitidosTurnover(empresa, dataInicial, dataFinal,mesesIniciais,mesesFinais,null,null,null,null,agruparPorCargo)).thenReturn(colaboradores);
    	
    	colaboradorManager.findDemitidosTurnoverTempoServico(mesesIniciais, mesesFinais, empresa.getId(), dataInicial, dataFinal, null, null, null, null, filtrarPorCargo, agruparPorCargo);

    	assertEquals(colaboradorA.getNome(), ((Colaborador)colaboradores.toArray()[0]).getNome());
    	assertNull(((Colaborador)colaboradores.toArray()[1]).getTempoServicoString());
		assertNotNull(((Colaborador)colaboradores.toArray()[0]).getTempoServicoString());
    }
    @Test
    public void testMontaTurnOverCargo() throws Exception
    {
    	Date dataAtual = new Date();
    	Date dataInicial = DateUtil.getInicioMesData(dataAtual);
    	Date dataFinal = DateUtil.getUltimoDiaMesComHoraZerada(dataAtual);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Character agruparPorCargo = 'C';
    	int filtrarPorCargo = 2;
    	
    	Cargo cargoA = CargoFactory.getEntity("cargoA");
    	cargoA.setId(1l);

    	Cargo cargoB = CargoFactory.getEntity("cargoB");
    	cargoB.setId(2l);
    	
    	Collection<TurnOver> admitidos = new ArrayList<TurnOver>();
    	admitidos.add(montaAdmitido(dataInicial, 21, cargoA.getId()));
    	admitidos.add(montaAdmitido(dataInicial, 5, cargoB.getId()));
    	
    	Collection<TurnOver> demitidos = new ArrayList<TurnOver>();
    	demitidos.add(montaDemitido(dataFinal, 25, cargoA.getId()));
    	demitidos.add(montaDemitido(dataFinal, 10, cargoB.getId()));
    	
    	List<Long> listaCargos = Arrays.asList(cargoA.getId(),cargoB.getId());
    	
    	when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
		when(colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(dataInicial,dataFinal,empresa,null,null,listaCargos,null,true,'C')).thenReturn(admitidos);
    	when(colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(dataInicial,dataFinal,empresa,null,null,listaCargos,null,false,'C')).thenReturn(demitidos);
    	when(cargoManager.findCollectionByIdProjection(new Long[]{cargoA.getId(),cargoB.getId()})).thenReturn(Arrays.asList(cargoA, cargoB));

    	HashMap<Long, Double> hashMapCargosColaboradoresAtivosInicioMes = new HashMap<Long,Double>();
    	hashMapCargosColaboradoresAtivosInicioMes.put(cargoA.getId(), 30.0);
    	hashMapCargosColaboradoresAtivosInicioMes.put(cargoB.getId(), 10.0);
    	
    	HashMap<Long, Double> hashMapCargosColaboradoresAtivosFinalMes = new HashMap<Long,Double>();
    	hashMapCargosColaboradoresAtivosFinalMes.put(cargoA.getId(), 5.0);
    	hashMapCargosColaboradoresAtivosFinalMes.put(cargoB.getId(), 20.0);
    	
    	when(colaboradorDao.countAtivosPeriodoAreaOuCargo(DateUtil.getUltimoDiaMesAnterior(dataInicial), Arrays.asList(empresa.getId()), null, null, listaCargos, null, null, false, null, false, agruparPorCargo)).thenReturn(hashMapCargosColaboradoresAtivosInicioMes);
    	when(colaboradorDao.countAtivosPeriodoAreaOuCargo(DateUtil.getUltimoDiaMes(dataInicial), Arrays.asList(empresa.getId()), null, null, listaCargos, null, null, false, null, false, agruparPorCargo)).thenReturn(hashMapCargosColaboradoresAtivosFinalMes);
    			
    	Collection<TurnOverCollection> turnOverCollectionsCargo = colaboradorManager.montaTurnOverAreaOuCargo(dataInicial, dataFinal, empresa.getId(), null, null, listaCargos, null, filtrarPorCargo, agruparPorCargo);
    	
    	assertEquals(2, turnOverCollectionsCargo.size());
    	
    	Collection<TurnOver> turnOversRetornoCargoA = ((TurnOverCollection) turnOverCollectionsCargo.toArray()[0]).getTurnOvers();
    	assertEquals(new Double(76.67), ((TurnOver) turnOversRetornoCargoA.toArray()[0]).getTurnOver());
    	
    	Collection<TurnOver> turnOversRetornoCargoB = ((TurnOverCollection) turnOverCollectionsCargo.toArray()[1]).getTurnOvers();
    	assertEquals(new Double(75.0), ((TurnOver) turnOversRetornoCargoB.toArray()[0]).getTurnOver());
    }
    @Test
    public void testMontaTurnOverArea() throws Exception
    {
    	Date dataAtual = new Date();
    	Date dataInicial = DateUtil.getInicioMesData(dataAtual);
    	Date dataFinal = DateUtil.getUltimoDiaMesComHoraZerada(dataAtual);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Character agruparPorArea = 'A';
    	int filtrarPorArea = 1;
    	
    	AreaOrganizacional areaA = AreaOrganizacionalFactory.getEntity(1l);
    	areaA.setNome("areaA");
    	
    	AreaOrganizacional areaB = AreaOrganizacionalFactory.getEntity(2l);
    	areaB.setNome("areaB");
    	
    	Collection<TurnOver> admitidos = new ArrayList<TurnOver>();
    	admitidos.add(montaAdmitido(dataInicial, 30, areaA.getId()));
    	admitidos.add(montaAdmitido(dataInicial, 18, areaB.getId()));
    	
    	Collection<TurnOver> demitidos = new ArrayList<TurnOver>();
    	demitidos.add(montaDemitido(dataFinal, 29, areaA.getId()));
    	demitidos.add(montaDemitido(dataFinal, 23, areaB.getId()));

    	List<Long> listaAreas = Arrays.asList(areaA.getId(),areaB.getId());
    	
    	when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
    	when(colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(dataInicial,dataFinal,empresa,null,listaAreas,null,null,true,'A')).thenReturn(admitidos);
    	when(colaboradorDao.countAdmitidosDemitidosPeriodoTurnover(dataInicial,dataFinal,empresa,null,listaAreas,null,null,false,'A')).thenReturn(demitidos);
    	when(areaOrganizacionalManager.findCollectionFilhasByAreasIds(new Long[]{areaA.getId(),areaB.getId()})).thenReturn(Arrays.asList(areaA,areaB));
    
    	HashMap<Long, Double> hashMapAreasColaboradoresAtivosInicioMes = new HashMap<Long,Double>();
    	hashMapAreasColaboradoresAtivosInicioMes.put(areaA.getId(), 50.0);
    	hashMapAreasColaboradoresAtivosInicioMes.put(areaB.getId(), 30.0);
    	
    	HashMap<Long, Double> hashMapAreasColaboradoresAtivosFinalMes = new HashMap<Long,Double>();
    	hashMapAreasColaboradoresAtivosFinalMes.put(areaA.getId(), 15.0);
    	hashMapAreasColaboradoresAtivosFinalMes.put(areaB.getId(), 25.0);
    	
    	when(colaboradorDao.countAtivosPeriodoAreaOuCargo(DateUtil.getUltimoDiaMesAnterior(dataInicial), Arrays.asList(empresa.getId()), null,listaAreas, null, null, null, false, null, false, agruparPorArea)).thenReturn(hashMapAreasColaboradoresAtivosInicioMes);
    	when(colaboradorDao.countAtivosPeriodoAreaOuCargo(DateUtil.getUltimoDiaMes(dataInicial), Arrays.asList(empresa.getId()), null, listaAreas, null, null, null, false, null, false, agruparPorArea)).thenReturn(hashMapAreasColaboradoresAtivosFinalMes);
    	
    	Collection<TurnOverCollection> turnOverCollectionsArea = colaboradorManager.montaTurnOverAreaOuCargo(dataInicial, dataFinal, empresa.getId(), null,listaAreas, null, null, filtrarPorArea, agruparPorArea);
    	
    	assertEquals(2, turnOverCollectionsArea.size());
    	
    	Collection<TurnOver> turnOversRetornoAreaA = ((TurnOverCollection) turnOverCollectionsArea.toArray()[0]).getTurnOvers();
    	assertEquals(new Double(59.0), ((TurnOver) turnOversRetornoAreaA.toArray()[0]).getTurnOver());
    	
    	Collection<TurnOver> turnOversRetornoAreaB = ((TurnOverCollection) turnOverCollectionsArea.toArray()[1]).getTurnOvers();
    	assertEquals(new Double(68.33), ((TurnOver) turnOversRetornoAreaB.toArray()[0]).getTurnOver());
    }

    private TurnOver montaAdmitido(Date data, double qtdAdmitidos,Long idAreaOuCargo) 
	{
		TurnOver admitido = new TurnOver();
    	admitido.setMesAnoQtdAdmitidos(data, qtdAdmitidos);
    	admitido.setIdAreaOuCargo(idAreaOuCargo);
    	
		return admitido;
	}
	private TurnOver montaDemitido(Date data, double qtdDemitidos, Long idAreaOuCargo) 
	{
		TurnOver demitido = new TurnOver();
		demitido.setMesAnoQtdDemitidos(data, qtdDemitidos);
		demitido.setIdAreaOuCargo(idAreaOuCargo);
		
		return demitido;
	}

    public void testConfirmarContratacaoColaboradorNaoEncontradoNoRH() throws Exception {
        TEmpregado empregado = new TEmpregado();
        TSituacao situacao = new TSituacao();

        when(colaboradorManager.updateEmpregado(empregado)).thenReturn(null);

        String msg = "";
        try {
            colaboradorManager.confirmarContratacao(empregado, situacao);
        } catch (Exception e) {
            msg = e.getMessage();
        }

        assertEquals("Empregado não encontrado.", msg);
    }

    @Test
    public void testConfirmarContratacao() throws Exception {
        TEmpregado empregado = new TEmpregado();
        empregado.setCodigoAC("000001");
        empregado.setEmpresaCodigoAC("0002");
        empregado.setGrupoAC("001");
        
        TSituacao situacao = new TSituacao();
        situacao.setData(DateUtil.formataDiaMesAno(new Date()));
        situacao.setCategoriaESocial(CategoriaESocial.CATEGORIA_101.getCodigo());

        Empresa empresa = EmpresaFactory.getEmpresa(2L);
        Colaborador colaborador = ColaboradorFactory.getEntity(1L, empresa);
        HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L, colaborador);
        

        when(colaboradorManager.updateEmpregado(empregado)).thenReturn(colaborador);
        when(historicoColaboradorManager.atualizarHistoricoContratacao(situacao)).thenReturn(historicoColaborador);
        when(empresaManager.findEntidadeComAtributosSimplesById(empresa.getId())).thenReturn(empresa);
        when(historicoColaboradorManager.isUltimoHistoricoOrPosteriorAoUltimo(any(Date.class), eq(empregado.getCodigoAC()), eq(empregado.getEmpresaCodigoAC()), eq(empregado.getGrupoAC()))).thenReturn(true);

        Exception exception = null;
        try {
            colaboradorManager.confirmarContratacao(empregado, situacao);
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
    }
    
    @Test
    public void testUpdateVinculoComVinculoaAtualizadoNoUltimoHistorico(){
    	String vinculoAtual = Vinculo.TEMPORARIO;
    	TSituacao situacao = new TSituacao();
    	situacao.setData(DateUtil.formataDiaMesAno(new Date()));
    	situacao.setCategoriaESocial(CategoriaESocial.CATEGORIA_101.getCodigo());
    	
    	String colaboradorCodigoAC = "000001";
    	String empresaCodigoAc = "0002";
    	String grupoAC = "001";
    	
		when(historicoColaboradorManager.isUltimoHistoricoOrPosteriorAoUltimo(any(Date.class), eq(colaboradorCodigoAC), eq(empresaCodigoAc), eq(grupoAC))).thenReturn(true);
		assertEquals(Vinculo.EMPREGO, colaboradorManager.updateVinculo(vinculoAtual, situacao, colaboradorCodigoAC, empresaCodigoAc, grupoAC));	
	}
    
    @Test
    public void testUpdateVinculoNaoEUltimoHistorico(){
    	String vinculoAtual = Vinculo.TEMPORARIO;
    	TSituacao situacao = new TSituacao();
    	situacao.setData(DateUtil.formataDiaMesAno(new Date()));
    	situacao.setCategoriaESocial(CategoriaESocial.CATEGORIA_101.getCodigo());
    	
    	String colaboradorCodigoAC = "000001";
    	String empresaCodigoAc = "0002";
    	String grupoAC = "001";
    	
		when(historicoColaboradorManager.isUltimoHistoricoOrPosteriorAoUltimo(any(Date.class), eq(colaboradorCodigoAC), eq(empresaCodigoAc), eq(grupoAC))).thenReturn(false);
		assertEquals(Vinculo.TEMPORARIO, colaboradorManager.updateVinculo(vinculoAtual, situacao, colaboradorCodigoAC, empresaCodigoAc, grupoAC));	
	}
    
    @Test
    public void testFindUltimoVinculo() throws Exception{
    	String colaboradorCodigoAC = "000001";
    	String empresaCodigoAC = "0002";
    	String grupoAC = "001";
    	String categoriaESocial = CategoriaESocial.CATEGORIA_101.getCodigo();
    	
		Empresa empresa = EmpresaFactory.getEmpresa(1L, "Empresa", empresaCodigoAC, grupoAC);
		when(empresaManager.findByCodigoAC(empresaCodigoAC, grupoAC)).thenReturn(empresa);
		when(acPessoalClientColaborador.getUltimaCategoriaESocial(empresa, colaboradorCodigoAC)).thenReturn(categoriaESocial);
		
		Exception exception = null;
        try {
            colaboradorManager.findUltimoVinculo(colaboradorCodigoAC, empresaCodigoAC, grupoAC);
        } catch (Exception e) {
            exception = e;
        }

        verify(empresaManager, times(1)).findByCodigoAC(empresaCodigoAC, grupoAC);
        verify(acPessoalClientColaborador, times(1)).getUltimaCategoriaESocial(empresa, colaboradorCodigoAC);
        assertNull(exception);
	}
	
    @Test
	public void testUpdateVinculo(){
    	String categoriaESocial = CategoriaESocial.CATEGORIA_101.getCodigo();
    	String colaboradorCodigoAC = "000001";
    	String empresaCodigoAC = "0002";
    	String grupoAC = "001";
    	
    	Exception exception = null;
        try {
        	colaboradorManager.updateVinculo(categoriaESocial, colaboradorCodigoAC, empresaCodigoAC, grupoAC);
        } catch (Exception e) {
            exception = e;
        }
        verify(colaboradorDao, times(1)).updateVinculo(eq(Vinculo.EMPREGO), eq(colaboradorCodigoAC), eq(empresaCodigoAC), eq(grupoAC));
        assertNull(exception);
	}
    
    @Test
    public void testSetDadosIntegrados() {
    	Colaborador colaborador = ColaboradorFactory.getEntity();
		when(colaboradorDao.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		assertNotNull(colaboradorManager.setDadosIntegrados(colaborador));
    }
}
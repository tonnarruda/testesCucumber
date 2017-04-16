package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManagerImpl;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.AcompanhamentoExperienciaColaborador;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.json.ColaboradorJson;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;

public class ColaboradorManagerTest_Junit4
{
	private ColaboradorManagerImpl colaboradorManager = new ColaboradorManagerImpl();
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
    

    @Before
    public void setUp() throws Exception
    {
        colaboradorDao = mock(ColaboradorDao.class);
        candidatoManager = mock(CandidatoManager.class);
        candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
        mensagemManager = mock(MensagemManager.class);
        cidadeManager = mock(CidadeManager.class);
        acPessoalClientColaborador = mock(AcPessoalClientColaborador.class);
        historicoColaboradorManager= mock(HistoricoColaboradorManager.class);
        transactionManager = mock(PlatformTransactionManager.class);
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        
        colaboradorManager.setDao(colaboradorDao);
        colaboradorManager.setCandidatoManager(candidatoManager);
        colaboradorManager.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);
        colaboradorManager.setMensagemManager(mensagemManager);
        colaboradorManager.setCidadeManager(cidadeManager);
        colaboradorManager.setAcPessoalClientColaborador(acPessoalClientColaborador);
        colaboradorManager.setHistoricoColaboradorManager(historicoColaboradorManager);
        colaboradorManager.setTransactionManager(transactionManager);
        colaboradorManager.setAreaOrganizacionalManager(areaOrganizacionalManager);
        
        usuarioManager = mock(UsuarioManager.class);
        MockSpringUtilJUnit4.mocks.put("usuarioManager", usuarioManager);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
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
}
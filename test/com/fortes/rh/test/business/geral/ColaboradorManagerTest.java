package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import remprot.RPClient;

import com.fortes.portalcolaborador.business.MovimentacaoOperacaoPCManager;
import com.fortes.portalcolaborador.business.operacao.ExcluirColaborador;
import com.fortes.portalcolaborador.model.ColaboradorPC;
import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.business.geral.ColaboradorManagerImpl;
import com.fortes.rh.business.geral.ColaboradorPeriodoExperienciaAvaliacaoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.AcompanhamentoExperienciaColaborador;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.EscolaridadeACPessoal;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.geral.relatorio.TurnOverCollection;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.ExperienciaFactory;
import com.fortes.rh.test.factory.captacao.FormacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.CandidatoIdiomaFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.ColaboradorIdiomaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockAutenticador;
import com.fortes.rh.test.util.mockObjects.MockImportacaoCSVUtil;
import com.fortes.rh.test.util.mockObjects.MockRPClient;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.test.util.mockObjects.MockTransactionStatus;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.importacao.ImportacaoCSVUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;

@SuppressWarnings("unchecked")
public class ColaboradorManagerTest extends MockObjectTestCase
{
    private ColaboradorManagerImpl colaboradorManager = new ColaboradorManagerImpl();
    private Mock colaboradorDao = null;
    private Mock transactionManager;
    private Mock candidatoManager;
    private Mock candidatoSolicitacaoManager;
    private Mock historicoColaboradorManager;
    private Mock areaOrganizacinoalManager;
    private Mock indiceManager;
    private Mock faixaSalarialManager;
    private Mock usuarioManager;
    private Mock cidadeManager;
    private Mock estadoManager;
    private Mock formacaoManager;
    private Mock empresaManager;
    private Mock colaboradorIdiomaManager;
    private Mock experienciaManager;
    private Mock acPessoalClientColaborador;
    private Mock parametrosDoSistemaManager;
    private Mock avaliacaoDesempenhoManager;
    private Mock configuracaoNivelCompetenciaManager;
    private Mock configuracaoNivelCompetenciaColaboradorManager;
    private Mock colaboradorPeriodoExperienciaAvaliacaoManager;
    private Mock solicitacaoManager;
    private Mock mensagemManager;
    private Mock mail;
    private Mock solicitacaoExameManager;
    private Mock movimentacaoOperacaoPCManager;
	private Colaborador colaborador;
	private List<Formacao> formacoes;
	private List<CandidatoIdioma> idiomas;
	private List<Experiencia> experiencias;


    protected void setUp() throws Exception
    {
        super.setUp();
        colaboradorDao = new Mock(ColaboradorDao.class);

        colaboradorManager.setDao((ColaboradorDao) colaboradorDao.proxy());

        candidatoManager = new Mock(CandidatoManager.class);
        colaboradorManager.setCandidatoManager((CandidatoManager) candidatoManager.proxy());

        candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
        colaboradorManager.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) candidatoSolicitacaoManager.proxy());
        
        historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
        colaboradorManager.setHistoricoColaboradorManager((HistoricoColaboradorManager)historicoColaboradorManager.proxy());

        areaOrganizacinoalManager = new Mock(AreaOrganizacionalManager.class);
        colaboradorManager.setAreaOrganizacionalManager((AreaOrganizacionalManager)areaOrganizacinoalManager.proxy());

        indiceManager = new Mock(IndiceManager.class);
        colaboradorManager.setIndiceManager((IndiceManager)indiceManager.proxy());

        faixaSalarialManager = new Mock(FaixaSalarialManager.class);
        colaboradorManager.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        colaboradorManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        cidadeManager = new Mock(CidadeManager.class);
        colaboradorManager.setCidadeManager((CidadeManager) cidadeManager.proxy());

        estadoManager = new Mock(EstadoManager.class);
        colaboradorManager.setEstadoManager((EstadoManager) estadoManager.proxy());

        experienciaManager = new Mock(ExperienciaManager.class);
        colaboradorManager.setExperienciaManager((ExperienciaManager) experienciaManager.proxy());

        formacaoManager = new Mock(FormacaoManager.class);
        colaboradorManager.setFormacaoManager((FormacaoManager) formacaoManager.proxy());

        colaboradorIdiomaManager = new Mock(ColaboradorIdiomaManager.class);
        colaboradorManager.setColaboradorIdiomaManager((ColaboradorIdiomaManager) colaboradorIdiomaManager.proxy());

        acPessoalClientColaborador = mock(AcPessoalClientColaborador.class);
		colaboradorManager.setAcPessoalClientColaborador((AcPessoalClientColaborador) acPessoalClientColaborador.proxy());

		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		colaboradorManager.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());

		empresaManager = mock(EmpresaManager.class);
		colaboradorManager.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		
		configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
		colaboradorManager.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());

		configuracaoNivelCompetenciaColaboradorManager = new Mock(ConfiguracaoNivelCompetenciaColaboradorManager.class);
		colaboradorManager.setConfiguracaoNivelCompetenciaColaboradorManager((ConfiguracaoNivelCompetenciaColaboradorManager) configuracaoNivelCompetenciaColaboradorManager.proxy());
		
		colaboradorPeriodoExperienciaAvaliacaoManager = new Mock(ColaboradorPeriodoExperienciaAvaliacaoManager.class);
		colaboradorManager.setColaboradorPeriodoExperienciaAvaliacaoManager((ColaboradorPeriodoExperienciaAvaliacaoManager) colaboradorPeriodoExperienciaAvaliacaoManager.proxy());

		solicitacaoManager = new Mock(SolicitacaoManager.class);
		colaboradorManager.setSolicitacaoManager((SolicitacaoManager) solicitacaoManager.proxy());
		
		mensagemManager = new Mock(MensagemManager.class);
		colaboradorManager.setMensagemManager((MensagemManager) mensagemManager.proxy());
		
		solicitacaoExameManager = new Mock(SolicitacaoExameManager.class);
		colaboradorManager.setSolicitacaoExameManager((SolicitacaoExameManager) solicitacaoExameManager.proxy());

		movimentacaoOperacaoPCManager = mock(MovimentacaoOperacaoPCManager.class);
		colaboradorManager.setMovimentacaoOperacaoPCManager((MovimentacaoOperacaoPCManager) movimentacaoOperacaoPCManager.proxy());
		
        mail = mock(Mail.class);
        colaboradorManager.setMail((Mail) mail.proxy());

        usuarioManager = new Mock(UsuarioManager.class);
        MockSpringUtil.mocks.put("usuarioManager", usuarioManager);
        MockSpringUtil.mocks.put("avaliacaoDesempenhoManager", avaliacaoDesempenhoManager);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(ImportacaoCSVUtil.class, MockImportacaoCSVUtil.class);
        Mockit.redefineMethods(Autenticador.class, MockAutenticador.class);
        Mockit.redefineMethods(RPClient.class, MockRPClient.class);
        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
    }

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
    	
    	Colaborador colaborador3 = new Colaborador(1L, dataReferencia, 1.0, 12, 1L);
    	Colaborador colaborador5 = new Colaborador(1L, dataReferencia, 1.0, 33, 2L);
    	Colaborador colaborador4 = new Colaborador(2L, dataReferencia, 1.0, 12, 1L);
    	
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
    	
    	colaboradorDao.expects(once()).method("findAdmitidosNoPeriodo").withAnyArguments().will(returnValue(colaboradores));
    	colaboradorDao.expects(once()).method("findComAvaliacoesExperiencias").withAnyArguments().will(returnValue(colaboradoresComAvaliacoes));
    	
    	Collection<Colaborador> colabs = colaboradorManager.getAvaliacoesExperienciaPendentes(null, dataReferencia, empresa, areasCheck, estabelecimentoCheck, tempoDeEmpresa, null, periodoExperiencias);
    	assertEquals(2, colabs.size());
    	assertEquals("10 dias, respondida (12 dias)\n30 dias, respondida (33 dias)", ((Colaborador)colabs.toArray()[0]).getStatusAvaliacao());
    	assertEquals("10 dias, respondida (12 dias)", ((Colaborador)colabs.toArray()[1]).getStatusAvaliacao());
    	assertEquals("03/02/2011\n23/02/2011", ((Colaborador)colabs.toArray()[0]).getDatasDeAvaliacao());
    	assertEquals("03/02/2011", ((Colaborador)colabs.toArray()[1]).getDatasDeAvaliacao());
    }
    
    public void testEnviaEmailAniversariantesSemEmpresas()
    {
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	
		executaTesteEnviaEmailAniversariante(empresas);
    }
    
    public void testEnviaEmailAniversariantesSemAniversariantesNoMes()
    {
    	Empresa empresa1 = EmpresaFactory.getEmpresa(1L); 
    	Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
    	
    	Collection<Empresa> empresas = Arrays.asList(empresa1, empresa2);
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();

    	int dia = DateUtil.getDia(new Date());
    	int mes = DateUtil.getMes(new Date());
    	
		colaboradorDao.expects(atLeastOnce()).method("findAniversariantesByEmpresa").with(ANYTHING, eq(dia), eq(mes)).will(returnValue(colaboradores));

    	executaTesteEnviaEmailAniversariante(empresas);
    }
    
    public void testEnviaEmailAniversariantesComAniversariantesNoMesSemEmail()
    {
    	Empresa empresa1 = EmpresaFactory.getEmpresa(1L); 
    	Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
    	
    	Collection<Empresa> empresas = Arrays.asList(empresa1, empresa2);

    	Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
    	colaborador1.getContato().setEmail(null);
    	Colaborador colaborador2 = ColaboradorFactory.getEntity(1L);
    	colaborador2.getContato().setEmail("");
    	
    	Collection<Colaborador> colaboradores = Arrays.asList(colaborador2);
    	
    	int dia = DateUtil.getDia(new Date());
    	int mes = DateUtil.getMes(new Date());
    	
		colaboradorDao.expects(atLeastOnce()).method("findAniversariantesByEmpresa").with(ANYTHING, eq(dia), eq(mes)).will(returnValue(colaboradores));
		executaTesteEnviaEmailAniversariante(empresas);
    }
    
    public void testEnviaEmailAniversariantesComAniversariantesNoMesComEmail()
    {
    	Empresa empresa1 = EmpresaFactory.getEmpresa(1L); 
    	Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
    	
    	Collection<Empresa> empresas = Arrays.asList(empresa1, empresa2);
    	
    	Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
    	colaborador1.getContato().setEmail("email@email.com");
    	Colaborador colaborador2 = ColaboradorFactory.getEntity(1L);
    	colaborador2.getContato().setEmail("");
    	
    	Collection<Colaborador> colaboradores = Arrays.asList(colaborador1,colaborador2);
    	
    	int dia = DateUtil.getDia(new Date());
    	int mes = DateUtil.getMes(new Date());
    	
    	colaboradorDao.expects(atLeastOnce()).method("findAniversariantesByEmpresa").with(ANYTHING, eq(dia), eq(mes)).will(returnValue(colaboradores));
    	mail.expects(atLeastOnce()).method("send").withAnyArguments().isVoid();
    	
    	executaTesteEnviaEmailAniversariante(empresas);
    }

	private void executaTesteEnviaEmailAniversariante(Collection<Empresa> empresas)
	{
    	Exception erro = null;
    	try {
    		colaboradorManager.enviaEmailAniversariantes(empresas);
		} catch (Exception e) {
			erro = e;
			e.printStackTrace();
		}

    	assertNull("Fluxo de execução ok", erro);
	}
    
    public void testGetAvaliacoesExperienciaPendentesPeriodo() throws Exception
    {
    	Date dataIni = DateUtil.criarDataMesAno(20, 01, 2011);
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
    	
    	Colaborador colaboradorResposta3 = new Colaborador(2L, dataFim, 1.0, 30, 1L);
    	Colaborador colaboradorResposta4 = new Colaborador(2L, dataFim, 1.0, 55, 3L);
    	Colaborador colaboradorResposta5 = new Colaborador(5L, dataFim, 1.0, 33, 2L);
    	
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
    	
    	colaboradorDao.expects(once()).method("findAdmitidosNoPeriodo").withAnyArguments().will(returnValue(colaboradores));
    	colaboradorDao.expects(once()).method("findComAvaliacoesExperiencias").withAnyArguments().will(returnValue(colaboradoresComAvaliacoes));
    	
    	Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
    	
		areaOrganizacinoalManager.expects(once()).method("findByEmpresasIds").with(ANYTHING, ANYTHING).will(returnValue(areas));
        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
        areaOrganizacinoalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));
    	
    	List<AcompanhamentoExperienciaColaborador> acompanhamentos = colaboradorManager.getAvaliacoesExperienciaPendentesPeriodo(DateUtil.incrementaMes(dataIni, -2), DateUtil.incrementaAno(dataFim, 2), empresa, areasCheck, estabelecimentoCheck, periodoExperiencias);
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
    
    public void testFindByAreaEstabelecimento()
    {
        AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
        areaOrganizacional.setId(1L);

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(2L);

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByAreaEstabelecimento").with(eq(areaOrganizacional.getId()), eq(estabelecimento.getId())).will(returnValue(colaboradores));

        assertEquals(1, colaboradorManager.findByAreaEstabelecimento(areaOrganizacional.getId(), estabelecimento.getId()).size());
    }
    
    public void testOrdenaByMediaPerformance()
    {
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	Colaborador joao1 = new Colaborador(1L, "joão da silva", "joão", "pedro", null, 20.20, null, false, 20L, "prova", "vega", "area");
    	Colaborador joao2 = new Colaborador(1L, "joão da silva", "joão", "pedro", null, 55.00, null, false, 21L, "prova II", "vega", "area");
    	
    	colaboradores.add(joao1);
    	colaboradores.add(joao2);
    	
    	Colaborador chico1 = new Colaborador(2L, "chico paulo", "chico", "pedro", null, 15.00, null, false, 21L, "prova II", "vega", "area");
    	Colaborador chico2 = new Colaborador(2L, "chico paulo", "chico", "pedro", null, 75.00, null, false, 23L, "prova III", "vega", "area");
    	colaboradores.add(chico1);
    	colaboradores.add(chico2);
    	
    	Collection<Colaborador> ordenadosPorMediaPerformance = colaboradorManager.ordenaByMediaPerformance(colaboradores);
    	assertEquals(4, ordenadosPorMediaPerformance.size());
    	
    	Colaborador testChico1 = (Colaborador) ordenadosPorMediaPerformance.toArray()[0];
    	Colaborador testChico2 = (Colaborador) ordenadosPorMediaPerformance.toArray()[1];
    	Colaborador testJoao1 = (Colaborador) ordenadosPorMediaPerformance.toArray()[2];
    	Colaborador testJoao2 = (Colaborador) ordenadosPorMediaPerformance.toArray()[3];
    	
    	assertEquals(chico1, testChico1);
    	assertEquals(45.0, testChico1.getMediaPerformance());
    	assertEquals(chico2, testChico2);
    	assertEquals(45.0, testChico2.getMediaPerformance());
    	
    	assertEquals(joao1, testJoao1);
    	assertEquals(37.6, testJoao1.getMediaPerformance());
    	assertEquals(joao2, testJoao2);
    	assertEquals(37.6, testJoao1.getMediaPerformance());
    }

    public void testFindByAreasOrganizacionaisEstabelecimentos()
    {
        AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
        areaOrganizacional.setId(1L);
        Collection<Long> areasIds = new ArrayList<Long>();
        areasIds.add(areaOrganizacional.getId());

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(2L);
        Collection<Long> estabelecimentosIds = new ArrayList<Long>();
        estabelecimentosIds.add(estabelecimento.getId());

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByAreasOrganizacionaisEstabelecimentos").with(eq(areasIds), eq(estabelecimentosIds), ANYTHING, ANYTHING).will(returnValue(colaboradores));

        assertEquals(1, colaboradorManager.findByAreasOrganizacionaisEstabelecimentos(areasIds, estabelecimentosIds).size());
    }
    
    public void testCountAdmitidosDemitidosTurnover()
    {
    	AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
    	areaOrganizacional.setId(1L);

    	Empresa empresa1 = EmpresaFactory.getEmpresa(1L);
    	Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
    	
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	
    	empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa1.getId())).will(returnValue(empresa1));
    	empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa2.getId())).will(returnValue(empresa2));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosTurnover").withAnyArguments().will(returnValue(new Integer(20)));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosTurnover").withAnyArguments().will(returnValue(new Integer(2)));
    	
    	assertEquals(new Integer(22), colaboradorManager.countAdmitidosDemitidosTurnover(null, null, Arrays.asList(empresa1.getId(), empresa2.getId()), new Long[]{estabelecimento.getId()}, new Long[]{areaOrganizacional.getId()}, null, true));
    	assertEquals(new Integer(0), colaboradorManager.countAdmitidosDemitidosTurnover(null, null, null, new Long[]{estabelecimento.getId()}, new Long[]{areaOrganizacional.getId()}, null, true));
    }

    public void testGetColaboradoresByEstabelecimentoAreaGrupo()
    {
        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
        Collection<Long> areasIds = new ArrayList<Long>();
        areasIds.add(areaOrganizacional.getId());

        Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(2L);
        Collection<Long> estabelecimentosIds = new ArrayList<Long>();
        estabelecimentosIds.add(estabelecimento.getId());

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByAreasOrganizacionaisEstabelecimentos").with(eq(areasIds), eq(estabelecimentosIds), ANYTHING, ANYTHING).will(returnValue(colaboradores));
        assertEquals(colaboradores, colaboradorManager.getColaboradoresByEstabelecimentoAreaGrupo('1', estabelecimentosIds, areasIds, null, null, null));

        colaboradorDao.expects(once()).method("findByCargoIdsEstabelecimentoIds").with(ANYTHING, eq(estabelecimentosIds), ANYTHING, ANYTHING).will(returnValue(colaboradores));
        assertEquals(colaboradores, colaboradorManager.getColaboradoresByEstabelecimentoAreaGrupo('2', estabelecimentosIds, null, null, null, null));
    }

    public void testFindByEstabelecimento()
    {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(2L);

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByEstabelecimento").with(eq(new Long[]{estabelecimento.getId()})).will(returnValue(colaboradores));

        assertEquals(1, colaboradorManager.findByEstabelecimento(new Long[]{estabelecimento.getId()}).size());
    }
    
    public void testPertenceEmpresa()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(2L);
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setEmpresa(empresa);
    	
    	colaboradorDao.expects(once()).method("findByIdProjectionEmpresa").with(eq(colaborador.getId())).will(returnValue(colaborador));
    	
    	assertEquals(true, colaboradorManager.pertenceEmpresa(colaborador.getId(), empresa.getId()));
    }
    
    public void testNaoPertenceEmpresa()
    {
    	Empresa outraEmpresa = EmpresaFactory.getEmpresa(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(2L);
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setEmpresa(empresa);
    	
    	colaboradorDao.expects(once()).method("findByIdProjectionEmpresa").with(eq(colaborador.getId())).will(returnValue(colaborador));
    	
    	assertEquals(false, colaboradorManager.pertenceEmpresa(colaborador.getId(), outraEmpresa.getId()));
    }
    
    public void testTriar() 
    {
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	solicitacao.setFaixaSalarial(faixaSalarial);
    	
    	solicitacaoManager.expects(once()).method("findByIdProjection").with(eq(solicitacao.getId())).will(returnValue(solicitacao));
    	configuracaoNivelCompetenciaManager.expects(once()).method("findCompetenciasIdsConfiguradasByFaixaSolicitacao").with(ANYTHING).will(returnValue(new Long[] { 1L }));
    	configuracaoNivelCompetenciaManager.expects(once()).method("somaConfiguracoesByFaixa").with(ANYTHING).will(returnValue(1));
    	colaboradorDao.expects(once()).method("triar").with(new Constraint[] { eq(empresa.getId()), ANYTHING, eq(Sexo.INDIFERENTE), ANYTHING, ANYTHING, eq(new String[0]), eq(new String[0]), eq(new Long[] { 1L }), eq(false) }).will(returnValue(new ArrayList<Colaborador>()));
    	
    	Exception exception = null;
    	
    	try {
			colaboradorManager.triar(solicitacao.getId(), empresa.getId(), null, Sexo.INDIFERENTE, null, null, new String[0], new String[0], false, null);
		} catch (Exception e) {
			exception = e;
			e.printStackTrace();
		}
		
		assertNull(exception);
    }
    
    public void testInsertColaboradoresSolicitacao() 
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	Candidato candidato = CandidatoFactory.getCandidato(1L);
    	
    	Long[] colaboradoresIds = new Long[] { 1L };
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	
    	colaboradorDao.expects(atLeastOnce()).method("findByIdComHistorico").with(ANYTHING, eq(StatusRetornoAC.CONFIRMADO)).will(returnValue(colaborador));
    	colaboradorIdiomaManager.expects(atLeastOnce()).method("find").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<ColaboradorIdioma>()));
    	experienciaManager.expects(atLeastOnce()).method("findByColaborador").with(ANYTHING).will(returnValue(new ArrayList<Experiencia>()));
    	formacaoManager.expects(atLeastOnce()).method("findByColaborador").with(ANYTHING).will(returnValue(new ArrayList<Formacao>()));
    	colaboradorDao.expects(atLeastOnce()).method("update").with(ANYTHING).isVoid();
    	
    	candidatoManager.expects(atLeastOnce()).method("saveOrUpdateCandidatoByColaborador").with(ANYTHING).will(returnValue(candidato));
    	candidatoSolicitacaoManager.expects(atLeastOnce()).method("insertCandidatos").with(ANYTHING, eq(solicitacao), eq(StatusCandidatoSolicitacao.APROMOVER)).isVoid();
    	
    	Exception exception = null;
    	try {
			colaboradorManager.insertColaboradoresSolicitacao(colaboradoresIds, solicitacao, StatusCandidatoSolicitacao.APROMOVER);
		} catch (Exception e) {
			exception = e;
			e.printStackTrace();
		}
		
		assertNull(exception);
	}
    
    public void testCalculaIndiceProcessoSeletivo()
    {
    	colaboradorDao.expects(once()).method("qtdDemitidosEm90Dias").will(returnValue(21));
    	colaboradorDao.expects(once()).method("qtdAdmitidosPeriodoEm90Dias").will(returnValue(99));
    	
    	assertEquals(78.79, colaboradorManager.calculaIndiceProcessoSeletivo(null, null, null, null));

    	colaboradorDao.expects(once()).method("qtdDemitidosEm90Dias").will(returnValue(21));
    	colaboradorDao.expects(once()).method("qtdAdmitidosPeriodoEm90Dias").will(returnValue(45));
    	//arredonda pra cima 46,666666	
    	assertEquals(53.33, colaboradorManager.calculaIndiceProcessoSeletivo(null, null, null, null));

    	colaboradorDao.expects(once()).method("qtdDemitidosEm90Dias").will(returnValue(0));
    	colaboradorDao.expects(once()).method("qtdAdmitidosPeriodoEm90Dias").will(returnValue(100));
    	
    	assertEquals(100.0, colaboradorManager.calculaIndiceProcessoSeletivo(null, null, null, null));
    	
    	colaboradorDao.expects(once()).method("qtdDemitidosEm90Dias").will(returnValue(10));
    	colaboradorDao.expects(once()).method("qtdAdmitidosPeriodoEm90Dias").will(returnValue(0));
    	
    	assertEquals(0.0, colaboradorManager.calculaIndiceProcessoSeletivo(null, null, null, null));
    	
    	colaboradorDao.expects(once()).method("qtdDemitidosEm90Dias").will(returnValue(10));
    	colaboradorDao.expects(once()).method("qtdAdmitidosPeriodoEm90Dias").will(returnValue(3));
    	
    	assertEquals(-233.33, colaboradorManager.calculaIndiceProcessoSeletivo(null, null, null, null));
    }

    public void testFindColaboradorByIdProjection()
    {
        Colaborador colaborador = new Colaborador();
        colaborador.setId(1L);

        colaboradorDao.expects(once()).method("findColaboradorByIdProjection").with(eq(colaborador.getId())).will(returnValue(colaborador));

        assertEquals(colaborador, colaboradorManager.findColaboradorByIdProjection(colaborador.getId()));
    }

    public void testFindByCargoIdsEstabelecimentoIds()
    {
        Cargo cargo = new Cargo();
        cargo.setId(2L);
        Collection<Long> cargosIds = new ArrayList<Long>();
        cargosIds.add(cargo.getId());

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(2L);
        Collection<Long> estabelecimentosIds = new ArrayList<Long>();
        estabelecimentosIds.add(estabelecimento.getId());

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByCargoIdsEstabelecimentoIds").with(eq(cargosIds), eq(estabelecimentosIds), ANYTHING, ANYTHING).will(returnValue(colaboradores));

        assertEquals(1, colaboradorManager.findByCargoIdsEstabelecimentoIds(cargosIds, estabelecimentosIds).size());
    }

    public void testFindByGrupoOcupacionalIdsEstabelecimentoIds()
    {
        GrupoOcupacional grupoOcupacional = new GrupoOcupacional();
        grupoOcupacional.setId(2L);
        Collection<Long> grupoOcupacionalIds = new ArrayList<Long>();
        grupoOcupacionalIds.add(grupoOcupacional.getId());

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(2L);
        Collection<Long> estabelecimentosIds = new ArrayList<Long>();
        estabelecimentosIds.add(estabelecimento.getId());

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByGrupoOcupacionalIdsEstabelecimentoIds").with(eq(grupoOcupacionalIds), eq(estabelecimentosIds)).will(returnValue(colaboradores));

        assertEquals(1, colaboradorManager.findByGrupoOcupacionalIdsEstabelecimentoIds(grupoOcupacionalIds, estabelecimentosIds).size());
    }

    public void testGetColaboradoresIntegraAc()
    {
        Colaborador c1 = ColaboradorFactory.getEntity();
        c1.setNaoIntegraAc(false);

        Colaborador c2 = ColaboradorFactory.getEntity();
        c2.setNaoIntegraAc(false);

        Colaborador c3 = ColaboradorFactory.getEntity();
        c3.setNaoIntegraAc(true);

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(c1);
        colaboradores.add(c2);
        colaboradores.add(c3);

        Collection<Colaborador> retorno = colaboradorManager.getColaboradoresIntegraAc(colaboradores);

        assertEquals(2, retorno.size());
    }

	public void testGetCountParametros()
    {
        Map parametros = new HashMap();

        colaboradorDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(2));

        Integer count = colaboradorManager.getCount(parametros);

        assertTrue(count.equals(2));
    }

    public void testFindAreaOrganizacionalByAreas()
    {
        HashMap<Object, Object> parametros = new HashMap<Object, Object>();
        parametros.put("areas", new ArrayList<String>());

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador.setId(1L);

        Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
        colaboradors.add(colaborador);

        colaboradorDao.expects(once()).method("findAreaOrganizacionalByAreas").withAnyArguments().will(returnValue(colaboradors));

        Collection<Colaborador> retorno = colaboradorManager.findAreaOrganizacionalByAreas(false, null, null, null, null, null, null, null, null, null, null, null, SituacaoColaborador.ATIVO, null, null);

        assertEquals(1, retorno.size());
    }

    private Map getParametros()
    {
        Map parametros = new HashMap();
        parametros.put("cargos", new String[]{"1","2"});
        parametros.put("estabelecimentos", new String[]{"1","2"});

        return parametros;
    }

    public void testMontaTurnOver() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(333L);
    	Date dataIni = DateUtil.criarDataMesAno(01, 01, 2010);
    	Date dataFim = DateUtil.criarDataMesAno(28, 12, 2010);
    	
    	Collection<TurnOver> admitidos = new ArrayList<TurnOver>();
    	admitidos.add(montaAdmitido(1, 2010, 21));
    	admitidos.add(montaAdmitido(2, 2010, 45));
    	admitidos.add(montaAdmitido(3, 2010, 31));
    	admitidos.add(montaAdmitido(4, 2010, 17));
    	admitidos.add(montaAdmitido(5, 2010, 13));
    	admitidos.add(montaAdmitido(6, 2010, 27));
    	admitidos.add(montaAdmitido(7, 2010, 51));
    	admitidos.add(montaAdmitido(8, 2010, 25));
    	admitidos.add(montaAdmitido(9, 2010, 23));
    	admitidos.add(montaAdmitido(10, 2010, 26));
    	admitidos.add(montaAdmitido(11, 2010, 23));
    	admitidos.add(montaAdmitido(12, 2010, 41));
    	
    	Collection<TurnOver> demitidos = new ArrayList<TurnOver>();
    	demitidos.add(montaDemitido(1, 2010, 25));
    	demitidos.add(montaDemitido(2, 2010, 15));
    	demitidos.add(montaDemitido(3, 2010, 26));
    	demitidos.add(montaDemitido(4, 2010, 23));
    	demitidos.add(montaDemitido(5, 2010, 28));
    	demitidos.add(montaDemitido(6, 2010, 6));
    	demitidos.add(montaDemitido(7, 2010, 14));
    	demitidos.add(montaDemitido(8, 2010, 40));
    	demitidos.add(montaDemitido(9, 2010, 20));
    	demitidos.add(montaDemitido(10, 2010, 20));
    	demitidos.add(montaDemitido(11, 2010, 19));
    	demitidos.add(montaDemitido(12, 2010, 10));

    	empresaManager.expects(atLeastOnce()).method("findByIdProjection").with(eq(empresa.getId())).will(returnValue(empresa));
    	
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[0])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[1])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[2])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[3])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[4])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[5])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[6])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[7])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[8])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[9])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[10])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(Arrays.asList((TurnOver) admitidos.toArray()[11])));
    	
    	
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[0])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[1])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[2])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[3])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[4])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[5])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[6])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[7])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[8])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[9])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[10])));
    	colaboradorDao.expects(once()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(Arrays.asList((TurnOver) demitidos.toArray()[11])));

    	colaboradorDao.expects(atLeastOnce()).method("countAtivosPeriodo").withAnyArguments().will(returnValue(973));
    	
    	TurnOverCollection collection = colaboradorManager.montaTurnOver(dataIni, dataFim, empresa.getId(), null, null, null, null, 1);
    	Collection<TurnOver> turnOvers = collection.getTurnOvers();
    	
    	TurnOver[] turnOverArray = (TurnOver[]) turnOvers.toArray(new TurnOver[12]);
    	
    	TurnOver mes1 = turnOverArray[0];
    	assertEquals("01/01/2010" , DateUtil.formataDiaMesAno(mes1.getMesAno()));
    	assertEquals(2.62 , mes1.getTurnOver());
    	
    	TurnOver mes2 = turnOverArray[1];
    	assertEquals("01/02/2010" , DateUtil.formataDiaMesAno(mes2.getMesAno()));
    	assertEquals(2.16 , mes2.getTurnOver());
    	
    	TurnOver mes3 = turnOverArray[2];
    	assertEquals("01/03/2010" , DateUtil.formataDiaMesAno(mes3.getMesAno()));
    	assertEquals(2.36 , mes3.getTurnOver());
    	
    	TurnOver mes4 = turnOverArray[3];
    	assertEquals("01/04/2010" , DateUtil.formataDiaMesAno(mes4.getMesAno()));
    	assertEquals(2.21 , mes4.getTurnOver());
    	
    	TurnOver mes5 = turnOverArray[4];
    	assertEquals("01/05/2010" , DateUtil.formataDiaMesAno(mes5.getMesAno()));
    	assertEquals(3.34 , mes5.getTurnOver());
    	
    	TurnOver mes6 = turnOverArray[5];
    	assertEquals("01/06/2010" , DateUtil.formataDiaMesAno(mes6.getMesAno()));
    	assertEquals(3.34 , mes6.getTurnOver());
    	
    	TurnOver mes12 = turnOverArray[11];
    	assertEquals("01/12/2010" , DateUtil.formataDiaMesAno(mes12.getMesAno()));
    	assertEquals(2.36 , mes12.getTurnOver());
    }
    
    public void testMontaTurnOverComMaisDeUmaEmpresa() throws Exception
    {
    	Empresa empresa1 = EmpresaFactory.getEmpresa(333L);
    	Date dataIni = DateUtil.criarDataMesAno(01, 01, 2010);
    	Date dataFim = DateUtil.criarDataMesAno(28, 12, 2010);
    	
    	Collection<TurnOver> admitidos = new ArrayList<TurnOver>();
    	admitidos.add(montaAdmitido(1, 2010, 21));
    	admitidos.add(montaAdmitido(2, 2010, 45));
    	
    	Collection<TurnOver> demitidos = new ArrayList<TurnOver>();
    	demitidos.add(montaDemitido(1, 2010, 25));
    	demitidos.add(montaDemitido(2, 2010, 15));
    	
    	empresaManager.expects(atLeastOnce()).method("findByIdProjection").with(eq(empresa1.getId())).will(returnValue(empresa1));
    	colaboradorDao.expects(atLeastOnce()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(true)}).will(returnValue(admitidos));
    	colaboradorDao.expects(atLeastOnce()).method("countAdmitidosDemitidosPeriodoTurnover").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(false)}).will(returnValue(demitidos));
    	colaboradorDao.expects(atLeastOnce()).method("countAtivosPeriodo").withAnyArguments().will(returnValue(200));
    	
    	TurnOverCollection collection = colaboradorManager.montaTurnOver(dataIni, dataFim, empresa1.getId(), null, null, null, null, 1);
    	Collection<TurnOver> turnOvers = collection.getTurnOvers();
    	
    	assertEquals(12, turnOvers.size());
    	TurnOver[] turnOverArray = (TurnOver[]) turnOvers.toArray(new TurnOver[12]);
    	
    	TurnOver mes1 = turnOverArray[0];
    	assertEquals("01/01/2010" , DateUtil.formataDiaMesAno(mes1.getMesAno()));
    	assertEquals(11.5 , mes1.getTurnOver());
    }

	private TurnOver montaAdmitido(int mes, int ano, double qtdAdmitidos) 
	{
		TurnOver admitido = new TurnOver();
		Date dataMesAno = DateUtil.criarDataMesAno(01, mes, ano);
    	admitido.setMesAnoQtdAdmitidos(dataMesAno, qtdAdmitidos);
    	
		return admitido;
	}
	
	private TurnOver montaDemitido(int mes, int ano, double qtdDemitidos) 
	{
		TurnOver demitido = new TurnOver();
		Date dataMesAno = DateUtil.criarDataMesAno(01, mes, ano);
		demitido.setMesAnoQtdDemitidos(dataMesAno, qtdDemitidos);
		
		return demitido;
	}

    public void testFindColaboradoresMotivoDemissao() throws Exception
    {
        colaboradorDao.expects(once()).method("findColaboradoresMotivoDemissao").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(ColaboradorFactory.getCollection()));
        assertNotNull(colaboradorManager.findColaboradoresMotivoDemissao(new Long[]{}, new Long[]{}, new Long[]{}, new Date(), new Date(), null, null));
    }

    public void testFindColaboradoresMotivoDemissaoException() throws Exception
    {
        Exception exc = null;
        try
        {
            colaboradorDao.expects(once()).method("findColaboradoresMotivoDemissao").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(null));
            colaboradorManager.findColaboradoresMotivoDemissao(new Long[]{}, new Long[]{}, new Long[]{}, new Date(), new Date(), null, null);
        }
        catch (Exception e)
        {
            exc = e;
        }

        assertNotNull(exc);
    }

    public void testFindColaboradoresMotivoDemissaoQuantidade() throws Exception
    {
        List<Object[]> lista = new ArrayList<Object[]>();
        lista.add(new Object[]{"motivo",true,1});

        colaboradorDao.expects(once()).method("findColaboradoresMotivoDemissaoQuantidade").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(lista));
        assertNotNull(colaboradorManager.findColaboradoresMotivoDemissaoQuantidade(new Long[]{}, new Long[]{}, new Long[]{}, new Date(), new Date(), null));
    }

    public void testSolicitacaoDesligamentoAc() throws Exception
    {
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);

        candidatoManager.expects(once()).method("updateDisponivelAndContratadoByColaborador").with(eq(true),eq(false),eq(new Long[]{colaborador.getId()})).isVoid();
    	candidatoSolicitacaoManager.expects(once()).method("setStatusByColaborador").with(ANYTHING, eq(new Long[]{colaborador.getId()})).isVoid();
        transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
        usuarioManager.expects(once()).method("desativaAcessoSistema").with(eq(new Long[]{colaborador.getId()}));
        historicoColaboradorManager.expects(once()).method("deleteHistoricosAguardandoConfirmacaoByColaborador").with(eq(new Long[]{colaborador.getId()}));
        transactionManager.expects(atLeastOnce()).method("commit").with(ANYTHING);

        colaboradorManager.desligaColaborador(true, new Date(), "observacao", 1L, 'I', true, true, colaborador.getId());
    }
    
    public void testDesligaColaborador() throws Exception
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	
    	candidatoManager.expects(once()).method("updateDisponivelAndContratadoByColaborador").with(eq(true),eq(false),eq(new Long[]{colaborador.getId()})).isVoid();
    	candidatoSolicitacaoManager.expects(once()).method("setStatusByColaborador").with(ANYTHING, eq(new Long[]{colaborador.getId()})).isVoid();
    	transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
    	usuarioManager.expects(once()).method("removeAcessoSistema").with(eq(new Long []{colaborador.getId()}));
    	areaOrganizacinoalManager.expects(once()).method("desvinculaResponsaveis").with(eq(new Long[]{colaborador.getId()}));
    	mensagemManager.expects(once()).method("removerMensagensViculadasByColaborador").withAnyArguments().isVoid();
    	colaboradorDao.expects(once()).method("desligaColaborador").withAnyArguments().isVoid();
    	transactionManager.expects(atLeastOnce()).method("commit").with(ANYTHING);
    	
    	colaboradorManager.desligaColaborador(true, new Date(), "observacao", 1L, 'I', false, false, colaborador.getId());
    }
    public void testFindByAreasOrganizacionalIds() throws Exception
    {
    	Long[] ids = new Long[]{1L};
    	colaboradorDao.expects(once()).method("findByAreaOrganizacionalIds").with(eq(ids)).will(returnValue(new ArrayList<Colaborador>()));
    	
    	assertNotNull(colaboradorManager.findByAreasOrganizacionalIds(ids));
    }
    
    public void testSaveDetalhes() {
    	// dado que
    	dadoUmColaboradorQualquer();
    	dadoDuasFormacoes();
    	dadoDoisIdiomas();
    	dadoDuasExperiencias();
    	// quando
    	colaboradorManager.saveDetalhes(colaborador, formacoes, idiomas, experiencias);
    	// entao espera-se que
    	assertQueFormacoesForamSalvas();
    	assertQueIdiomasForamSalvos();
    	assertQueExperienciasForamSalvas();
    }
    

    private void assertQueExperienciasForamSalvas() {
    	assertQueDadosDaExperienciaForamAlterados(experiencias.get(0));
    	assertQueDadosDaExperienciaForamAlterados(experiencias.get(1));
    	experienciaManager.verify();
	}

	private void assertQueDadosDaExperienciaForamAlterados(Experiencia experiencia) {
		assertNull("id", experiencia.getId());
		assertEquals("colaborador", colaborador, experiencia.getColaborador());
		assertNull("candidato", experiencia.getCandidato());
		assertNull("cargo", experiencia.getCargo());
	}

	private void assertQueIdiomasForamSalvos() {
    	colaboradorIdiomaManager.verify();
	}

	private void assertQueFormacoesForamSalvas() {
		assertQueDadosDaFormacaoForamAlterados(formacoes.get(0));
    	assertQueDadosDaFormacaoForamAlterados(formacoes.get(1));
    	formacaoManager.verify();
	}
	
	private void assertQueDadosDaFormacaoForamAlterados(Formacao formacao) {
		assertNull("id", formacao.getId());
		assertEquals("colaborador", colaborador, formacao.getColaborador());
		assertNull("candidato", formacao.getCandidato());
	}

	private void dadoDuasExperiencias() {
    	experiencias = new ArrayList<Experiencia>();
    	experiencias.add(ExperienciaFactory.getEntity(1L));
    	experiencias.add(ExperienciaFactory.getEntity(2L));
    	// esperado
    	experienciaManager.expects(once()).method("save").with(eq(experiencias.get(0)));
    	experienciaManager.expects(once()).method("save").with(eq(experiencias.get(1)));
	}

	private void dadoDoisIdiomas() {
    	idiomas = new ArrayList<CandidatoIdioma>();
    	idiomas.add(CandidatoIdiomaFactory.getCandidatoIdioma(1L));
    	idiomas.add(CandidatoIdiomaFactory.getCandidatoIdioma(2L));
    	// esperado
    	colaboradorIdiomaManager.expects(once()).method("save").with(ANYTHING); // codigo instancia novos idiomas
    	colaboradorIdiomaManager.expects(once()).method("save").with(ANYTHING); // codigo instancia novos idiomas
	}

	private void dadoDuasFormacoes() {
    	formacoes = new ArrayList<Formacao>();
    	formacoes.add(FormacaoFactory.getEntity(1L));
    	formacoes.add(FormacaoFactory.getEntity(2L));
    	// esperado
    	formacaoManager.expects(once()).method("save").with(eq(formacoes.get(0)));
    	formacaoManager.expects(once()).method("save").with(eq(formacoes.get(1)));
	}

	private void dadoUmColaboradorQualquer() {
		colaborador = ColaboradorFactory.getEntity(14L);
	}

	public void testRemoveColaborador() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(true);
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	
    	String [] tables = new String[]{"historicocolaborador"};
    	
    	colaboradorDao.expects(once()).method("findDependentTables").with(eq(colaborador.getId())).will(returnValue(tables));
    	candidatoManager.expects(once()).method("updateDisponivelAndContratadoByColaborador").with(eq(true),eq(false),eq(new Long[]{colaborador.getId()})).isVoid();
    	candidatoSolicitacaoManager.expects(once()).method("setStatusByColaborador").with(ANYTHING, eq(new Long[]{colaborador.getId()})).isVoid();
    	formacaoManager.expects(once()).method("removeColaborador").with(eq(colaborador)).isVoid();
    	colaboradorIdiomaManager.expects(once()).method("removeColaborador").with(eq(colaborador)).isVoid();
    	experienciaManager.expects(once()).method("removeColaborador").with(eq(colaborador)).isVoid();
    	historicoColaboradorManager.expects(once()).method("removeColaborador").with(eq(colaborador.getId())).isVoid();
    	configuracaoNivelCompetenciaManager.expects(once()).method("removeColaborador").with(eq(colaborador)).isVoid();
    	configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("removeColaborador").with(eq(colaborador)).isVoid();
    	colaboradorPeriodoExperienciaAvaliacaoManager.expects(once()).method("removeConfiguracaoAvaliacaoPeriodoExperiencia").withAnyArguments().isVoid();
    	mensagemManager.expects(once()).method("removeMensagensColaborador").with(eq(colaborador.getId()),eq(null)).isVoid();
    	colaboradorDao.expects(once()).method("remove").with(eq(colaborador.getId())).isVoid();
    	colaboradorDao.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
    	transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
    	acPessoalClientColaborador.expects(once()).method("remove").with(ANYTHING, ANYTHING).will(returnValue(true));
    	mensagemManager.expects(once()).method("removerMensagensViculadasByColaborador").withAnyArguments().isVoid();
    	transactionManager.expects(atLeastOnce()).method("commit").with(ANYTHING);
    	solicitacaoExameManager.expects(once()).method("removeByColaborador").with(eq(colaborador.getId()));
    	movimentacaoOperacaoPCManager.expects(once()).method("enfileirar").with(ANYTHING, ANYTHING, ANYTHING);

    	Exception exception = null;
    	try
		{
    		colaboradorManager.remove(colaborador, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
    }

    public void testRemoveColaboradorException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(true);
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	String [] tables = new String[]{"historicocolaborador"};
    	colaboradorDao.expects(once()).method("findDependentTables").with(eq(colaborador.getId())).will(returnValue(tables));
    	
    	formacaoManager.expects(once()).method("removeColaborador").with(eq(colaborador)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(colaborador.getId(),""))));;
    	
    	Exception exception = null;
    	try
    	{
    		colaboradorManager.remove(colaborador, empresa);
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }

    public void testFindColaboradoresMotivoDemissaoQuantidadeException() throws Exception
    {
        Exception exc = null;
        try
        {
            colaboradorDao.expects(once()).method("findColaboradoresMotivoDemissaoQuantidade").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(null));
            colaboradorManager.findColaboradoresMotivoDemissaoQuantidade(new Long[]{}, new Long[]{}, new Long[]{}, new Date(), new Date(), null);
        }
        catch (Exception e)
        {
            exc = e;
        }

        assertNotNull(exc);
    }
    
    public void testAvisoQtdCadastros() throws Exception
    {
//		TODO remprot
//		colaboradorDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(95));
//		String msg = colaboradorManager.avisoQtdCadastros();
//		assertEquals("Atualmente existem " + 95 + " colaboradores cadastrados no sistema.<br>Sua licença permite cadastrar " + 100 + " colaboradores.", msg);
//
//		colaboradorDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(96));
//		msg = colaboradorManager.avisoQtdCadastros();
//		assertEquals("Atualmente existem " + 96 + " colaboradores cadastrados no sistema.<br>Sua licença permite cadastrar " + 100 + " colaboradores.", msg);
//		
//		colaboradorDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(10));
//		msg = colaboradorManager.avisoQtdCadastros();
//		assertNull(msg);
    }


    public void testFindProjecaoSalarial() throws Exception
    {
        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
        Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
        colaborador1.setAreaOrganizacional(areaOrganizacional);

        Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
        colaboradors.add(colaborador1);

        Empresa empresa = EmpresaFactory.getEmpresa(1L);

        TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();

        Date data = new Date();

        Collection<Long> estabelecimentoIds = new ArrayList<Long>(1);
        estabelecimentoIds.add(1L);
        Collection<Long> cargoIds = new ArrayList<Long>(1);;
        cargoIds.add(1L);
        Collection<Long> grupoIds = new ArrayList<Long>(1);;
        grupoIds.add(1L);
        Collection<Long> areaIds = new ArrayList<Long>(1);;
        areaIds.add(1L);

        String filtro = "";

        colaboradorDao.expects(once()).method("findProjecaoSalarialByHistoricoColaborador").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradors));
        areaOrganizacinoalManager.expects(once()).method("findAllListAndInativas").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
        areaOrganizacinoalManager.expects(once()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));

        Collection<Colaborador> retorno = colaboradorManager.findProjecaoSalarial(tabelaReajusteColaborador.getId(), data, estabelecimentoIds, areaIds, grupoIds, cargoIds, filtro, empresa.getId());

        assertEquals("Sem TabelaReajusteColaborador", colaboradors.size(), retorno.size());

        tabelaReajusteColaborador.setId(1L);

        colaboradorDao.expects(once()).method("findProjecaoSalarialByHistoricoColaborador").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradors));
        colaboradorDao.expects(once()).method("findProjecaoSalarialByTabelaReajusteColaborador").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradors));
        areaOrganizacinoalManager.expects(once()).method("findAllListAndInativas").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
        areaOrganizacinoalManager.expects(once()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));

        retorno = colaboradorManager.findProjecaoSalarial(tabelaReajusteColaborador.getId(), data, estabelecimentoIds, areaIds, grupoIds, cargoIds, filtro, empresa.getId());

        assertEquals("Com TabelaReajusteColaborador", colaboradors.size(), retorno.size());
    }

    public void testOrdenaPorEstabelecimentoAreaOrGrupoOcupacionalFiltro1() throws Exception
    {
        Empresa empresa = EmpresaFactory.getEmpresa(1L);

        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
        areaOrganizacional.setEmpresa(empresa);

        Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
        areas.add(areaOrganizacional);

        Colaborador colaborador = ColaboradorFactory.getEntity(1L);
        colaborador.setAreaOrganizacional(areaOrganizacional);

        Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
        colaboradors.add(colaborador);

        areaOrganizacinoalManager.expects(once()).method("findAllListAndInativas").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(areas));

        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));

        areaOrganizacinoalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING);

        Collection<Colaborador> retorno = colaboradorManager.ordenaPorEstabelecimentoArea(colaboradors, empresa.getId());

        assertEquals(colaboradors.size(), retorno.size());
    }

    public void testOrdenaPorEstabelecimentoAreaOrGrupoOcupacionalFiltro2() throws Exception
    {
        Empresa empresa = EmpresaFactory.getEmpresa(1L);

        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
        areaOrganizacional.setEmpresa(empresa);

        Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
        areas.add(areaOrganizacional);

        Colaborador colaborador = ColaboradorFactory.getEntity(1L);
        colaborador.setAreaOrganizacional(areaOrganizacional);

        Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
        colaboradors.add(colaborador);

        areaOrganizacinoalManager.expects(once()).method("findAllListAndInativas").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(areas));

        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));

        areaOrganizacinoalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING);

        Collection<Colaborador> retorno = colaboradorManager.ordenaPorEstabelecimentoArea(colaboradors, empresa.getId());

        assertEquals(colaboradors.size(), retorno.size());
    }

    public void testRespondeuEntrevista()
    {
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);

        colaboradorDao.expects(once()).method("setRespondeuEntrevista").with(eq(colaborador.getId()));

        colaboradorManager.respondeuEntrevista(colaborador.getId());
    }
    
    public void testFindAllSelect()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	colaboradorDao.expects(once()).method("findAllSelect").with(eq(empresa.getId()), eq("nomeComercial")).will(returnValue(new ArrayList<Colaborador>()));

    	assertNotNull(colaboradorManager.findAllSelect(empresa.getId(), "nomeComercial"));
    }

    public void testFindByNomeCpfMatricula()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	colaboradorDao.expects(once()).method("findByNomeCpfMatricula").with(ANYTHING, eq(empresa.getId()), ANYTHING, eq(null)).will(returnValue(new ArrayList<Colaborador>()));

    	assertNotNull(colaboradorManager.findByNomeCpfMatricula(null, empresa.getId(), false, null));
    }

    public void testMigrarBairro()
    {
    	colaboradorDao.expects(once()).method("migrarBairro").with(eq("bairro"), eq("bairroDestino")).isVoid();

    	colaboradorManager.migrarBairro("bairro", "bairroDestino");
    }

    public void testFindListComHistoricoFuturo()
	{
    	Map parametros = new HashMap();
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		colaboradorDao.expects(once()).method("findList").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradors));

		Collection<Colaborador> retorno = colaboradorManager.findListComHistoricoFuturo(1, 10, parametros);

		assertEquals(colaboradors.size(), retorno.size());
	}

    public void testGetCountComHistoricoFuturo()
	{
    	Map parametros = new HashMap();

    	colaboradorDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(2));

    	Integer count = colaboradorManager.getCountComHistoricoFuturo(parametros);

    	assertTrue(count.equals(2));
	}

    public void testGetNome()
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setNome("Joao");

    	colaboradorDao.expects(once()).method("findColaboradorByIdProjection").with(eq(colaborador.getId())).will(returnValue(colaborador));

    	assertEquals("Joao", colaboradorManager.getNome(colaborador.getId()));
    }

    public void testGetNomeException()
    {
    	colaboradorDao.expects(once()).method("findColaboradorByIdProjection").with(ANYTHING).will(returnValue(null));

    	assertEquals("", colaboradorManager.getNome(null));
    }

    public void testUpdateEmpregado() throws Exception
    {
    	TEmpregado empregado = iniciaTEmpregado();

    	Pessoal pessoal = new Pessoal();
    	pessoal.setEscolaridade("Especialização");
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	Estado estado = EstadoFactory.getEntity(1L);
    	Cidade cidade = CidadeFactory.getEntity();
    	cidade.setId(1L);
    	cidade.setUf(estado);
    	colaborador.setPessoal(pessoal);

    	cidadeManager.expects(once()).method("findByCodigoAC").with(ANYTHING, ANYTHING).will(returnValue(cidade));
    	estadoManager.expects(once()).method("findBySigla").with(ANYTHING).will(returnValue(estado));
    	estadoManager.expects(once()).method("findBySigla").with(ANYTHING).will(returnValue(estado));
    	colaboradorDao.expects(once()).method("findByIdComHistorico").with(ANYTHING,eq(null)).will(returnValue(colaborador));
    	colaboradorDao.expects(once()).method("update").with(eq(colaborador));
    	movimentacaoOperacaoPCManager.expects(once()).method("enfileirar").with(ANYTHING, ANYTHING, ANYTHING);

    	assertEquals(colaborador, colaboradorManager.updateEmpregado(empregado, true));
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
    	
    	cidadeManager.expects(once()).method("findByCodigoAC").with(ANYTHING, ANYTHING).will(returnValue(cidade));
    	estadoManager.expects(once()).method("findBySigla").with(ANYTHING).will(returnValue(estado));
    	estadoManager.expects(once()).method("findBySigla").with(ANYTHING).will(returnValue(estado));
    	colaboradorDao.expects(once()).method("save").with(ANYTHING).isVoid();
//    	historicoColaboradorManager.expects(once()).method("bindSituacao").with(eq(tSituacoes[0]), ANYTHING);
//    	historicoColaboradorManager.expects(once()).method("save").with( ANYTHING);
    	
    	Exception e = null;
    	try {
    		colaboradorManager.saveEmpregadosESituacoes(new TEmpregado[]{tEmpregado}, tSituacoes, empresa);
		} catch (Exception e2) {
			e = e2;
		}
    	
    	assertEquals("O empregado null está sem situação.", e.getMessage());
    }
    
    public void testSaveEmpregadosESituacoes() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	
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
    	
    	cidadeManager.expects(once()).method("findByCodigoAC").with(ANYTHING, ANYTHING).will(returnValue(cidade));
    	estadoManager.expects(once()).method("findBySigla").with(ANYTHING).will(returnValue(estado));
    	estadoManager.expects(once()).method("findBySigla").with(ANYTHING).will(returnValue(estado));
    	colaboradorDao.expects(once()).method("save").with(ANYTHING).isVoid();
    	historicoColaboradorManager.expects(once()).method("bindSituacao").with(eq(tSituacoes[0]), ANYTHING);
    	historicoColaboradorManager.expects(once()).method("save").with( ANYTHING);
    	
    	Exception e = null;
    	try {
    		colaboradorManager.saveEmpregadosESituacoes(new TEmpregado[]{tEmpregado}, tSituacoes, empresa);
    	} catch (Exception e2) {
    		e = e2;
    	}
    	
    	assertNull(e);
    }
    
    public void testGetCountAtivosByEstabelecimento()
    {
    	colaboradorDao.expects(once()).method("getCountAtivosByEstabelecimento").with(ANYTHING).will(returnValue(1));
    	assertEquals((Integer)1,colaboradorManager.getCountAtivosEstabelecimento(1L));
    }
    
    public void testFindByAreaOrganizacionalEstabelecimento()
    {
    	Collection<AreaOrganizacional> areas = Arrays.asList(new AreaOrganizacional[]{AreaOrganizacionalFactory.getEntity(1L)});
    	colaboradorDao.expects(once()).method("findByAreaOrganizacionalEstabelecimento").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(areas));
    	assertNotNull(colaboradorManager.findByAreaOrganizacionalEstabelecimento(new ArrayList<Long>(), null, SituacaoColaborador.ATIVO));
    }
    
    public void testFindEmailsDeColaboradoresByPerfis()
    {
    	Collection<Perfil> perfis = new ArrayList<Perfil>();
    	Perfil perfil = new Perfil();
    	perfil.setId(10L);
		perfis.add(perfil);
		Long empresaId=1L;
		
		colaboradorDao.expects(once()).method("findEmailsDeColaboradoresByPerfis").with(ANYTHING,ANYTHING).will(returnValue(new ArrayList<String>()));
		
		assertEquals(0, colaboradorManager.findEmailsDeColaboradoresByPerfis(perfis, empresaId).size());
    }
    public void testFindEmailsDeColaboradoresByPerfisVazio()
    {
    	Collection<Perfil> perfis = null;
    	Long empresaId=1L;
    	assertEquals(0, colaboradorManager.findEmailsDeColaboradoresByPerfis(perfis, empresaId).size());
    }
    
    public void testFindAdmitidosHaDias() throws Exception
    {
    	Integer dias=45;
		Empresa empresa= EmpresaFactory.getEmpresa(1L);
		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		
		colaboradorDao.expects(once()).method("findAdmitidosHaDias").with(eq(dias),eq(empresa), ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
		areaOrganizacinoalManager.expects(once()).method("findByEmpresasIds").with(ANYTHING, ANYTHING).will(returnValue(areas));
        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
		assertEquals(0, colaboradorManager.findAdmitidosHaDias(dias, empresa, null).size());
    }
    
    public void testFindAdmitidos() throws Exception
    {
    	Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
    	Collection<Colaborador> admitidos = Arrays.asList(ColaboradorFactory.getEntity(1L));
    	
    	colaboradorDao.expects(once()).method("findAdmitidos").will(returnValue(admitidos));
    	areaOrganizacinoalManager.expects(once()).method("findByEmpresasIds").with(ANYTHING, ANYTHING).will(returnValue(areas));
        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
    	Long[] areasIds=new Long[]{1L};
		Long[] estabelecimentosIds=new Long[]{1L,2L};
		
		assertEquals(1, colaboradorManager.findAdmitidos(new Long[]{1L}, Vinculo.EMPREGO, new Date(), new Date(), areasIds, estabelecimentosIds, false).size());
    }
    
    public void testFindAdmitidosException() 
    {
    	colaboradorDao.expects(once()).method("findAdmitidos").will(returnValue(new ArrayList<Colaborador>()));
    	
    	Long[] areasIds=new Long[]{1L};
    	Long[] estabelecimentosIds=new Long[]{1L,2L};
    	
    	Exception exception =null;
    	
    	try {
			colaboradorManager.findAdmitidos(new Long[]{1L}, Vinculo.EMPREGO, new Date(), new Date(), areasIds, estabelecimentosIds, false);
		} catch (Exception e) {
			exception = e;
		}
		
		assertEquals("Não existem dados para o filtro informado.", exception.getMessage());
    }
    
    public void testMontaGraficoEvolucaoFolha() 
    {
    	HistoricoColaborador histJoao = HistoricoColaboradorFactory.getEntity();
    	histJoao.setTipoSalario(TipoAplicacaoIndice.VALOR);
    	histJoao.setSalario(200.00);
    	Colaborador joao = ColaboradorFactory.getEntity();
    	joao.setHistoricoColaborador(histJoao);

    	HistoricoColaborador histMaria = HistoricoColaboradorFactory.getEntity();
    	histMaria.setTipoSalario(TipoAplicacaoIndice.VALOR);
    	histMaria.setSalario(250.00);
    	Colaborador maria = ColaboradorFactory.getEntity();
    	maria.setHistoricoColaborador(histMaria);
    	
    	IndiceHistorico indiceHistPedro = IndiceHistoricoFactory.getEntity();
    	indiceHistPedro.setValor(100.00);
    	Indice indicePedro = IndiceFactory.getEntity();
    	indicePedro.setIndiceHistoricoAtual(indiceHistPedro);
    	HistoricoColaborador histPedro = HistoricoColaboradorFactory.getEntity();
    	histPedro.setTipoSalario(TipoAplicacaoIndice.INDICE);
    	histPedro.setIndice(indicePedro);
    	histPedro.setQuantidadeIndice(2.0);
    	Colaborador pedro = ColaboradorFactory.getEntity();
    	pedro.setHistoricoColaborador(histPedro);
    	
    	Collection<Colaborador> colaboradorsPrimeiraIteracao = new ArrayList<Colaborador>();
    	colaboradorsPrimeiraIteracao.add(pedro);
    	colaboradorsPrimeiraIteracao.add(joao);
    	colaboradorsPrimeiraIteracao.add(maria);
    	
    	FaixaSalarialHistorico faixaSalarialHistoricoRaimundo = FaixaSalarialHistoricoFactory.getEntity();
    	faixaSalarialHistoricoRaimundo.setValor(355.0);
    	faixaSalarialHistoricoRaimundo.setTipo(TipoAplicacaoIndice.VALOR);
    	FaixaSalarial faixaSalarialRaimundo = FaixaSalarialFactory.getEntity();
    	faixaSalarialRaimundo.setFaixaSalarialHistoricoAtual(faixaSalarialHistoricoRaimundo);
    	HistoricoColaborador histRaimundo = HistoricoColaboradorFactory.getEntity();
    	histRaimundo.setTipoSalario(TipoAplicacaoIndice.CARGO);
    	histRaimundo.setFaixaSalarial(faixaSalarialRaimundo);
    	Colaborador raimundo = ColaboradorFactory.getEntity();
    	raimundo.setHistoricoColaborador(histRaimundo);
    	
    	HistoricoColaborador histRodrigo = HistoricoColaboradorFactory.getEntity();
    	histRodrigo.setTipoSalario(TipoAplicacaoIndice.VALOR);
    	histRodrigo.setSalario(250.05);
    	Colaborador rodrigo = ColaboradorFactory.getEntity();
    	rodrigo.setHistoricoColaborador(histRodrigo);

    	Collection<Colaborador> colaboradorsSegundaIteracao = new ArrayList<Colaborador>();
    	colaboradorsSegundaIteracao.add(raimundo);
    	colaboradorsSegundaIteracao.add(rodrigo);
    	
    	colaboradorDao.expects(once()).method("findProjecaoSalarialByHistoricoColaborador").will(returnValue(colaboradorsSegundaIteracao));
    	colaboradorDao.expects(once()).method("findProjecaoSalarialByHistoricoColaborador").will(returnValue(colaboradorsPrimeiraIteracao));
    	
    	Date dataIni = DateUtil.criarDataMesAno(01, 02, 2000);
    	Date dataFim = DateUtil.criarDataMesAno(01, 03, 2000);
    	
    	Collection<Object[]> dados;
		dados = colaboradorManager.montaGraficoEvolucaoFolha(dataIni, dataFim, 1L, new Long[]{});
	
    	assertEquals(2, dados.size());
    	
    	Object[] result = (Object[]) dados.toArray()[0];
    	dataIni = DateUtil.getUltimoDiaMes(dataIni);
    	assertEquals((Long) dataIni.getTime(), (Long) result[0]);
    	assertEquals(650.0, (Double) result[1]);

    	Object[] result2 = (Object[]) dados.toArray()[1];
    	dataFim = DateUtil.getUltimoDiaMes(dataFim);
    	assertEquals((Long) dataFim.getTime(), (Long) result2[0]);
    	assertEquals(605.05, (Double) result2[1]);
    }
    
    public void testFindParticipantesByAvaliacaoDesempenho()
    {
    	colaboradorDao.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(1L), eq(true), eq(null)).will(returnValue(new ArrayList<Colaborador>()));
    	assertNotNull(colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(1L, true, null));
    }
    
    public void testFindByEstabelecimentoDataAdmissao()
    {
    	Estabelecimento matriz = EstabelecimentoFactory.getEntity(1L);
		Date hoje = DateUtil.criarDataMesAno(29, 8, 2011);
		
		Colaborador joao = ColaboradorFactory.getEntity(1L, "joao", "joao", "001");
		joao.setDataAdmissao(hoje);
		
		Colaborador pedro = ColaboradorFactory.getEntity(2L, "pedro", "pedro", "002");
		pedro.setDataAdmissao(hoje);
		
		Collection<Colaborador> colaboradores = Arrays.asList(joao, pedro);
		
		colaboradorDao.expects(once()).method("findByEstabelecimentoDataAdmissao").with(eq(matriz.getId()), eq(hoje), ANYTHING).will(returnValue(colaboradores));
		
		assertEquals(2, colaboradorManager.findByEstabelecimentoDataAdmissao(matriz.getId(), hoje, null).size());
    }
    
    public void testFindFormacaoEscolar() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	Cargo cargo = CargoFactory.getEntity(1L);
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	
    	Collection<Formacao> formacoes = Arrays.asList(FormacaoFactory.getEntity(1L), FormacaoFactory.getEntity(2L));
    	Collection<ColaboradorIdioma> colaboradorIdiomas = Arrays.asList(ColaboradorIdiomaFactory.getEntity(3L));
    	Collection<Colaborador> colaboradores = Arrays.asList(colaborador);
    	
    	Collection<Long> estabelecimentoIds = Arrays.asList(estabelecimento.getId());
    	Collection<Long> areaIds = Arrays.asList(areaOrganizacional.getId());
    	Collection<Long> cargoIds = Arrays.asList(cargo.getId());
    	
    	colaboradorDao.expects(once()).method("findAreaOrganizacionalByAreas").with(new Constraint[] {eq(false), eq(estabelecimentoIds), eq(areaIds), eq(cargoIds), eq(null), eq(null), eq(null), eq(null), eq(null), eq(null), eq(null), eq(null), eq(SituacaoColaborador.ATIVO), eq(null), eq(new Long[]{empresa.getId()})}).will(returnValue(colaboradores));
    	formacaoManager.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(formacoes));
    	colaboradorIdiomaManager.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(colaboradorIdiomas));;
    	
    	Collection<Colaborador> colaboradoresRetornados = colaboradorManager.findFormacaoEscolar(empresa.getId(), estabelecimentoIds, areaIds, cargoIds);
    	
		assertEquals(1, colaboradoresRetornados.size());
		assertEquals(2, ((Colaborador)colaboradoresRetornados.toArray()[0]).getFormacao().size());
		assertEquals(1, ((Colaborador)colaboradoresRetornados.toArray()[0]).getColaboradorIdiomas().size());
    }

    public void testPopulaEsxcolaridadePadrao()
    {
    	// Este trecho testa todas as condições previstas
    	testaEscolaridade(null, null, Escolaridade.ANALFABETO);
    	testaEscolaridade(null, EscolaridadeACPessoal.ANALFABETO, Escolaridade.ANALFABETO);
    	testaEscolaridade(Escolaridade.GINASIO_EM_ANDAMENTO, null, Escolaridade.GINASIO_EM_ANDAMENTO);
    	testaEscolaridade(Escolaridade.TECNICO_EM_ANDAMENTO, EscolaridadeACPessoal.COLEGIAL_COMPLETO, Escolaridade.TECNICO_EM_ANDAMENTO);
    	testaEscolaridade(Escolaridade.TECNICO_COMPLETO, EscolaridadeACPessoal.COLEGIAL_COMPLETO, Escolaridade.TECNICO_COMPLETO);
    	testaEscolaridade(Escolaridade.ESPECIALIZACAO, EscolaridadeACPessoal.SUPERIOR_COMPLETO, Escolaridade.ESPECIALIZACAO);
    	testaEscolaridade(Escolaridade.SUPERIOR_EM_ANDAMENTO, EscolaridadeACPessoal.SUPERIOR_EM_ANDAMENTO, Escolaridade.SUPERIOR_EM_ANDAMENTO);
    	testaEscolaridade(Escolaridade.SUPERIOR_COMPLETO, EscolaridadeACPessoal.SUPERIOR_COMPLETO, Escolaridade.SUPERIOR_COMPLETO);
    	testaEscolaridade(Escolaridade.MESTRADO, EscolaridadeACPessoal.MESTRADO, Escolaridade.MESTRADO);
    	testaEscolaridade(Escolaridade.DOUTORADO, EscolaridadeACPessoal.DOUTORADO, Escolaridade.DOUTORADO);
    	
    	// Este trecho testa situações variadas
    	testaEscolaridade(Escolaridade.GINASIO_COMPLETO, EscolaridadeACPessoal.COLEGIAL_EM_ANDAMENTO, Escolaridade.COLEGIAL_EM_ANDAMENTO);
    	testaEscolaridade(Escolaridade.PRIMARIO_EM_ANDAMENTO, EscolaridadeACPessoal.GINASIO_EM_ANDAMENTO, Escolaridade.GINASIO_EM_ANDAMENTO);
    	testaEscolaridade(Escolaridade.SUPERIOR_EM_ANDAMENTO, EscolaridadeACPessoal.SUPERIOR_COMPLETO, Escolaridade.SUPERIOR_COMPLETO);
    	testaEscolaridade(Escolaridade.ESPECIALIZACAO, EscolaridadeACPessoal.MESTRADO, Escolaridade.MESTRADO);
    }
    
    private void testaEscolaridade(String escolaridadeDocolaborador, String escolaridadeDoEmpregado, String escolaridadeEsperada) 
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.getPessoal().setEscolaridade(escolaridadeDocolaborador);
    	
    	TEmpregado tEmpregado = iniciaTEmpregado();
    	tEmpregado.setEscolaridade(escolaridadeDoEmpregado);
    	
    	colaboradorManager.populaEscolaridade(colaborador, tEmpregado);
    	
    	assertEquals(escolaridadeEsperada, colaborador.getPessoal().getEscolaridade());
    }
    
  //TODO remprot
//    public void testValidaQtdCadastros()
//    {
//    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
//    	parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));
//    	colaboradorDao.expects(once()).method("getCount").will(returnValue(10));
//    	
//    	Exception exception = null;
//    	
//    	try
//		{
//			colaboradorManager.validaQtdCadastros();
//		} catch (Exception e)
//		{
//			exception = e;
//		}
//		
//		assertNotNull(exception);
//    }
}





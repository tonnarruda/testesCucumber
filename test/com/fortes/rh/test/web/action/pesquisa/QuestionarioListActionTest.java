package com.fortes.rh.test.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.EntrevistaManager;
import com.fortes.rh.business.pesquisa.FichaMedicaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.EntrevistaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.pesquisa.QuestionarioListAction;
import com.fortes.web.tags.CheckBox;

public class QuestionarioListActionTest extends MockObjectTestCase
{
	private QuestionarioListAction action;
	private Mock questionarioManager;
	private Mock perguntaManager;
	private Mock aspectoManager;
    private Mock areaOrganizacionalManager;
    private Mock entrevistaManager;
    private Mock fichaMedicaManager;
    private Mock empresaManager;
    private Mock estabelecimentoManager;
    private Mock parametrosDoSistemaManager;

    protected void setUp() throws Exception
    {
        super.setUp();

        action = new QuestionarioListAction();

        questionarioManager = new Mock(QuestionarioManager.class);
        action.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());

        perguntaManager = new Mock(PerguntaManager.class);
        action.setPerguntaManager((PerguntaManager) perguntaManager.proxy());

        aspectoManager = new Mock(AspectoManager.class);
        action.setAspectoManager((AspectoManager) aspectoManager.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        entrevistaManager = new Mock(EntrevistaManager.class);
        action.setEntrevistaManager((EntrevistaManager) entrevistaManager.proxy());
        
        fichaMedicaManager = mock(FichaMedicaManager.class);
        action.setFichaMedicaManager((FichaMedicaManager) fichaMedicaManager.proxy());
        
        empresaManager = mock(EmpresaManager.class);
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());

        estabelecimentoManager = mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        
        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
    }

    protected void tearDown() throws Exception
    {
    	action = null;
        questionarioManager = null;
        MockSecurityUtil.verifyRole = false;

        Mockit.restoreAllOriginalDefinitions();
        super.tearDown();
    }

    public void testPrepareResultado() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
    	parametrosDoSistema.setCompartilharCursos(false);
    	
    	Empresa empresa = action.getEmpresaSistema();
		Collection<Empresa> empresas = new ArrayList<Empresa>();
		empresas.add(empresa);
		
		Long[] empresaIds = new Long[]{empresa.getId()};
    	
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setTipo(TipoQuestionario.ENTREVISTA);
    	action.setQuestionario(questionario);

    	String[] arrayIds = new String[]{"1"};

    	action.setAreasCheck(arrayIds);
    	action.setAspectosCheck(arrayIds);
    	action.setPerguntasCheck(arrayIds);

    	action.setExibirRespostas(true);
    	action.setExibirComentarios(true);
    	action.setAgruparPorAspectos(true);

    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	Collection<Entrevista> entrevistas = new ArrayList<Entrevista>();
    	entrevistas.add(entrevista);
    	
    	empresaManager.expects(once()).method("findDistinctEmpresasByQuestionario").with(eq(questionario.getId())).will(returnValue(empresas));

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));;
		estabelecimentoManager.expects(once()).method("findAllSelect").with(eq(empresaIds)).will(returnValue(new ArrayList<Estabelecimento>()));
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	
    	entrevistaManager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(entrevistas));

    	aspectoManager.expects(once()).method("populaCheckOrderNome").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	perguntaManager.expects(once()).method("populaCheckOrderTexto").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));

    	assertEquals("success", action.prepareResultado());

    	assertEquals(arrayIds, action.getAreasCheck());
    	assertEquals(arrayIds, action.getAspectosCheck());
    	assertEquals(arrayIds, action.getPerguntasCheck());

    	assertEquals(entrevistas, action.getEntrevistas());

    	assertNotNull(action.getAreasOrganizacionalsCheckList());
    	assertNotNull(action.getAspectosCheckList());
    	assertNotNull(action.getPerguntasCheckList());

    	assertTrue(action.isExibirRespostas());
    	assertTrue(action.isExibirComentarios());
    	assertTrue(action.isAgruparPorAspectos());
    }
    
    public void testPrepareResultadoFichaMedica() throws Exception
    {
    	fichaMedicaManager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<FichaMedica>()));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	
    	assertEquals("success", action.prepareResultadoFichaMedica());
    }
    
    public void testPrepareAplicarQuestionario() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);

    	Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
		perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(questionario.getId())).will(returnValue(perguntas ));

    	assertEquals("success", action.prepareAplicar());
    }

    public void testPrepareAplicarQuestionarioByAspecto() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);

    	Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);
    	Collection<Aspecto> aspectos = AspectoFactory.getCollection(1L);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(questionario.getId())).will(returnValue(perguntas ));
    	perguntaManager.expects(once()).method("getPerguntasSemAspecto").with(eq(perguntas)).will(returnValue(null));
    	aspectoManager.expects(once()).method("agruparPerguntasByAspecto").with(eq(aspectos),eq(perguntas),eq(1)).will(returnValue(aspectos));

    	assertEquals("success", action.prepareAplicarByAspecto());
    }

    public void testAplicarQuestionarioByOrdem() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);

    	questionarioManager.expects(once()).method("aplicarPorAspecto").with(eq(questionario.getId()),eq(false));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));

    	assertEquals("success", action.aplicarByOrdem());
    	assertNull(action.getActionErr());

    }

	public void testAplicarQuestionarioByOrdemEntrevista() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setTipo(TipoQuestionario.getENTREVISTA());
    	action.setQuestionario(questionario);

    	questionarioManager.expects(once()).method("aplicarPorAspecto").with(eq(questionario.getId()),eq(false));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));

    	assertEquals("Teste de esta em uso por uma entrevista", "success_entrevista", action.aplicarByOrdem());
    	assertNull(action.getActionErr());

    }

    public void testAplicarQuestionarioByOrdemException() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);

    	questionarioManager.expects(once()).method("aplicarPorAspecto").with(eq(questionario.getId()),eq(false)).will(throwException(new Exception("erro")));

    	assertEquals("success", action.aplicarByOrdem());
    	assertNotNull(action.getActionErr());
    }

    public void testAplicarQuestionarioByAspecto() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);

    	Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);
    	Collection<Aspecto> aspectos = AspectoFactory.getCollection(1L);

    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(questionario.getId())).will(returnValue(perguntas ));
    	perguntaManager.expects(once()).method("getPerguntasSemAspecto").with(eq(perguntas)).will(returnValue(null));
    	aspectoManager.expects(once()).method("agruparPerguntasByAspecto").with(eq(aspectos),eq(perguntas),eq(1)).will(returnValue(aspectos));
    	aspectoManager.expects(once()).method("desagruparPerguntasByAspecto").with(eq(aspectos)).will(returnValue(perguntas));
    	perguntaManager.expects(once()).method("salvarPerguntasByOrdem").with(eq(perguntas));
    	questionarioManager.expects(once()).method("aplicarPorAspecto").with(eq(questionario.getId()),eq(true));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));

    	assertEquals("success", action.aplicarByAspecto());
    	assertNull(action.getActionErr());
    }

	public void testAplicarQuestionarioByAspectoEntrevista() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setTipo(TipoQuestionario.getENTREVISTA());
    	action.setQuestionario(questionario);

    	Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);
    	Collection<Aspecto> aspectos = AspectoFactory.getCollection(1L);

    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(questionario.getId())).will(returnValue(perguntas ));
    	perguntaManager.expects(once()).method("getPerguntasSemAspecto").with(eq(perguntas)).will(returnValue(null));
    	aspectoManager.expects(once()).method("agruparPerguntasByAspecto").with(eq(aspectos),eq(perguntas),eq(1)).will(returnValue(aspectos));
    	aspectoManager.expects(once()).method("desagruparPerguntasByAspecto").with(eq(aspectos)).will(returnValue(perguntas));
    	perguntaManager.expects(once()).method("salvarPerguntasByOrdem").with(eq(perguntas));
    	questionarioManager.expects(once()).method("aplicarPorAspecto").with(eq(questionario.getId()),eq(true));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));

    	assertEquals("success_entrevista", action.aplicarByAspecto());
    	assertNull(action.getActionErr());
    }

    public void testAplicarQuestionarioByAspectoSemAspecto() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);

    	Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);
    	Collection<Pergunta> perguntasSemAspecto = PerguntaFactory.getCollection(2L);
    	Collection<Aspecto> aspectos = AspectoFactory.getCollection(1L);

    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(questionario.getId())).will(returnValue(perguntas ));
    	perguntaManager.expects(once()).method("getPerguntasSemAspecto").with(eq(perguntas)).will(returnValue(perguntasSemAspecto));
    	aspectoManager.expects(once()).method("agruparPerguntasByAspecto").with(eq(aspectos),eq(perguntas),eq(perguntasSemAspecto.size()+1)).will(returnValue(aspectos));
    	aspectoManager.expects(once()).method("desagruparPerguntasByAspecto").with(eq(aspectos)).will(returnValue(perguntas));
    	perguntaManager.expects(once()).method("salvarPerguntasByOrdem").with(eq(perguntas));
    	perguntaManager.expects(once()).method("salvarPerguntasByOrdem").with(eq(perguntasSemAspecto));
    	questionarioManager.expects(once()).method("aplicarPorAspecto").with(eq(questionario.getId()),eq(true));

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));

    	assertEquals("success", action.aplicarByAspecto());
    	assertNull(action.getActionErr());
    }

    public void testAplicarQuestionarioByAspectoException() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);

    	Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);
    	Collection<Aspecto> aspectos = AspectoFactory.getCollection(1L);

    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(questionario.getId())).will(returnValue(perguntas ));
    	perguntaManager.expects(once()).method("getPerguntasSemAspecto").with(eq(perguntas)).will(returnValue(null));
    	aspectoManager.expects(once()).method("agruparPerguntasByAspecto").with(eq(aspectos),eq(perguntas),eq(1)).will(returnValue(aspectos));
    	aspectoManager.expects(once()).method("desagruparPerguntasByAspecto").with(eq(aspectos)).will(returnValue(perguntas));
    	perguntaManager.expects(once()).method("salvarPerguntasByOrdem").with(eq(perguntas)).will(throwException(new Exception("erro")));

    	assertEquals("success", action.aplicarByAspecto());
    	assertNotNull(action.getActionErr());

    }

    public void testImprimeResultado() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);
    	action.setAgruparPorAspectos(true);

    	Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	perguntaManager.expects(once()).method("findByQuestionarioAspectoPergunta").with(eq(questionario.getId()), ANYTHING, ANYTHING, eq(true)).will(returnValue(perguntas));
    	questionarioManager.expects(once()).method("montaResultado").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING, ANYTHING}).will(returnValue(new ArrayList<ResultadoQuestionario>()));
    	estabelecimentoManager.expects(once()).method("nomeEstabelecimentos").with(ANYTHING,ANYTHING);
    	areaOrganizacionalManager.expects(once()).method("nomeAreas").with(ANYTHING);

    	assertEquals("success", action.imprimeResultado());
    	assertNotNull(action.getPerguntas());
    	assertNotNull(action.getQuestionario());
    	assertNotNull(action.getTipoPergunta());
    	assertNotNull(action.getResultadoObjetivas());
    }
    
    public void testImprimeResultadoException() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
    	parametrosDoSistema.setCompartilharCursos(false);
    	
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	
    	parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	perguntaManager.expects(once()).method("findByQuestionarioAspectoPergunta").with(eq(questionario.getId()), ANYTHING, ANYTHING, eq(false)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(questionario.getId(),""))));
    	
    	empresaManager.expects(once()).method("findDistinctEmpresasByQuestionario").with(eq(questionario.getId())).will(returnValue(new ArrayList<Empresa>()));

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));;
		estabelecimentoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Estabelecimento>()));
    	
    	aspectoManager.expects(once()).method("populaCheckOrderNome").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	perguntaManager.expects(once()).method("populaCheckOrderTexto").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	
    	assertEquals("input", action.imprimeResultado());
    }
    
    public void testImprimeResultadoSemPergunta() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
    	parametrosDoSistema.setCompartilharCursos(false);
    	
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);
    	action.setAgruparPorAspectos(true);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();

    	parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(action.getQuestionario()));
    	
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));;
    	
    	perguntaManager.expects(once()).method("findByQuestionarioAspectoPergunta").with(eq(questionario.getId()), ANYTHING, ANYTHING, eq(true)).will(returnValue(perguntas));
		estabelecimentoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Estabelecimento>()));
		
    	empresaManager.expects(once()).method("findDistinctEmpresasByQuestionario").with(eq(questionario.getId())).will(returnValue(new ArrayList<Empresa>()));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	aspectoManager.expects(once()).method("populaCheckOrderNome").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	perguntaManager.expects(once()).method("populaCheckOrderTexto").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));

    	assertEquals("input", action.imprimeResultado());
    }

    public void testImprimir() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setAplicarPorAspecto(false);

    	Map<String, Object> parametros = new HashMap<String, Object>();
    	parametros.put("test", new Object());

    	action.setQuestionario(questionario);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	questionarioManager.expects(once()).method("getQuestionarioRelatorio").with(eq(questionario)).will(returnValue(new ArrayList<Questionario>()));

    	assertEquals("success", action.imprimir());
    	assertNotNull(action.getParametros());
    	assertNotNull(action.getDataSource());
    }
    
    public void testImprimirFichaMedica()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(10L);
    	action.setQuestionario(questionario);
    	action.setColaboradorQuestionario(colaboradorQuestionario);
    	
    	questionarioManager.expects(once()).method("montaImpressaoFichaMedica").with(eq(1L),eq(10L),ANYTHING);
    	
    	assertEquals("success", action.imprimirFichaMedica());
    }
    
    public void testImprimirFichaMedicaException()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(10L);
    	action.setQuestionario(questionario);
    	action.setColaboradorQuestionario(colaboradorQuestionario);
    	
    	questionarioManager.expects(once()).method("montaImpressaoFichaMedica").with(eq(1L),eq(10L),ANYTHING).will(throwException(new RuntimeException()));
    	
    	assertEquals("input", action.imprimirFichaMedica());
    }

    public void testImprimirByAspecto() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setAplicarPorAspecto(true);

    	Map<String, Object> parametros = new HashMap<String, Object>();
    	parametros.put("test", new Object());

    	action.setQuestionario(questionario);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	questionarioManager.expects(once()).method("getQuestionarioRelatorio").with(eq(questionario)).will(returnValue(new ArrayList<Questionario>()));

    	assertEquals("success", action.imprimir());
    	assertNotNull(action.getParametros());
    	assertNotNull(action.getDataSource());
    }

    public void testLiberar() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);

    	questionarioManager.expects(once()).method("liberarQuestionario").with(eq(questionario.getId()), ANYTHING);

    	assertEquals("success",action.liberar());
    }

    public void testLiberarComException() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);

    	questionarioManager.expects(once()).method("liberarQuestionario").with(eq(questionario.getId()), ANYTHING).will(throwException(new Exception("Erro")));

    	assertEquals("success",action.liberar());
    	assertNotNull(action.getActionErr());
    }

	public void testGetsSets() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	action.setQuestionario(questionario);
    	assertEquals(questionario, action.getQuestionario());

    	action.getPerguntas();
    	action.getAspectos();
    	action.getPerguntasSemAspecto();
    	action.isPreview();
    	action.setPreview(true);
    	action.getTipoPergunta();
    	action.getTipoQuestionario();
    	action.getUrlVoltar();
    	action.setUrlVoltar(null);
    	action.getAreasIds();
    	action.setAreasIds(null);
    	action.getPeriodoFim();
    	action.setPeriodoFim(new Date());
    	action.getPeriodoIni();
    	action.setPeriodoIni(new Date());
    	action.getRespostas();
    	action.setFiltroQuestionario("1");
    	action.getFiltroQuestionario();
    	action.setTurmaId(123L);
    	action.getTurmaId();
    	action.setCursoId(3L);
    	action.getCursoId();
    	action.getResultadoQuestionarios();
    	action.getPerguntaFichaMedicas();
    	action.setActionMsg("msg");
    	action.getActionMsg();
    	action.getFichaMedicas();
    	action.getColaboradorRespostas();
    	action.isExibirNomesColaboradores();
    	action.setEstabelecimentosCheck(null);
    	action.setEstabelecimentosIds(null);
    	action.setExibirNomesColaboradores(false);
    	action.getEstabelecimentosCheckList();
    	action.getEstabelecimentosIds();
    	action.getEmpresaIds();
    	action.getEmpresaId();
    	action.getEmpresas();
    	action.isImprimirFormaEconomica();
    	action.setImprimirFormaEconomica(false);
    }
}

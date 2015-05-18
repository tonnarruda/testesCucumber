package com.fortes.rh.test.business.pesquisa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.EntrevistaManager;
import com.fortes.rh.business.pesquisa.FichaMedicaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManagerImpl;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.model.relatorio.PerguntaFichaMedica;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.FichaMedicaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.ServletActionContext;

public class QuestionarioManagerTest extends MockObjectTestCase
{
	private QuestionarioManagerImpl questionarioManager = new QuestionarioManagerImpl();
	private Mock questionarioDao;
	private Mock pesquisaManager;
	private Mock entrevistaManager;
	private Mock fichaMedicaManager;
	private Mock colaboradorQuestionarioManager;
	private Mock perguntaManager;
	private Mock respostaManager;
	private Mock aspectoManager;
	private Mock colaboradorRespostaManager;
	private Mock colaboradorManager;
	private Mock gerenciadorComunicacaoManager;
	private Mock avaliacaoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        questionarioDao = new Mock(QuestionarioDao.class);
        questionarioManager.setDao((QuestionarioDao) questionarioDao.proxy());

        pesquisaManager = new Mock(PesquisaManager.class);
        entrevistaManager = new Mock(EntrevistaManager.class);
        fichaMedicaManager = new Mock(FichaMedicaManager.class);
        avaliacaoManager = new Mock(AvaliacaoManager.class);
        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        perguntaManager = new Mock(PerguntaManager.class);
        respostaManager = new Mock(RespostaManager.class);
        aspectoManager = new Mock(AspectoManager.class);
        colaboradorManager = mock(ColaboradorManager.class);
        gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);

        questionarioManager.setPerguntaManager((PerguntaManager)perguntaManager.proxy());
        questionarioManager.setRespostaManager((RespostaManager)respostaManager.proxy());
        questionarioManager.setAspectoManager((AspectoManager)aspectoManager.proxy());
        questionarioManager.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());
        questionarioManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        questionarioManager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);

		colaboradorRespostaManager = new Mock(ColaboradorRespostaManager.class);
		MockSpringUtil.mocks.put("colaboradorRespostaManager", colaboradorRespostaManager);
		MockSpringUtil.mocks.put("fichaMedicaManager", fichaMedicaManager);
		MockSpringUtil.mocks.put("avaliacaoManager", avaliacaoManager);

		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);

    }

    protected void tearDown() throws Exception
	{
		super.tearDown();

		Mockit.restoreAllOriginalDefinitions();
	}

    public void testUpdatePesquisa()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	questionarioDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(questionario));
    	questionarioDao.expects(once()).method("update").with(eq(questionario));

    	Exception exception = null;
    	try
		{
			questionarioManager.updateQuestionario(questionario);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
    }

    public void testFindByIdProjection()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	questionarioDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(questionario));

    	Questionario retorno =  questionarioManager.findByIdProjection(questionario.getId());
    	assertEquals(questionario.getId(), retorno.getId());
    }

    public void testClonarQuestionario()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	Questionario questionarioClonado = null;

    	questionarioDao.expects(once()).method("save").with(ANYTHING).will(returnValue(questionarioClonado));

    	questionarioManager.clonarQuestionario(questionario, null);
    }

    public void testMontaUrlVoltar()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	MockSpringUtil.mocks.put("pesquisaManager", pesquisaManager);
    	MockSpringUtil.mocks.put("entrevistaManager", entrevistaManager);
    	MockSpringUtil.mocks.put("fichaMedicaManager", fichaMedicaManager);

    	//Pesquisa
    	pesquisaManager.expects(once()).method("getIdByQuestionario").with(eq(questionario.getId())).will(returnValue(1L));
    	assertEquals("../pesquisa/prepareUpdate.action?pesquisa.id=1", questionarioManager.montaUrlVoltar(questionario.getId()));

    	//Entrevista
    	pesquisaManager.expects(once()).method("getIdByQuestionario").with(eq(questionario.getId())).will(returnValue(null));
    	entrevistaManager.expects(once()).method("getIdByQuestionario").with(eq(questionario.getId())).will(returnValue(1L));
    	assertEquals("../entrevista/prepareUpdate.action?entrevista.id=1", questionarioManager.montaUrlVoltar(questionario.getId()));

    	//Ficha Medica
    	pesquisaManager.expects(once()).method("getIdByQuestionario").with(eq(questionario.getId())).will(returnValue(null));
    	entrevistaManager.expects(once()).method("getIdByQuestionario").with(eq(questionario.getId())).will(returnValue(null));
    	fichaMedicaManager.expects(once()).method("getIdByQuestionario").with(eq(questionario.getId())).will(returnValue(1L));
    	assertEquals("../../sesmt/fichaMedica/prepareUpdate.action?fichaMedica.id=1", questionarioManager.montaUrlVoltar(questionario.getId()));
    }

    public void testCalculaPercentualRespostas()
    {
    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setTipo(TipoPergunta.OBJETIVA);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setTipo(TipoPergunta.OBJETIVA);

    	Resposta respostaA = RespostaFactory.getEntity(1L);
    	Resposta respostaB = RespostaFactory.getEntity(2L);
    	Resposta respostaC = RespostaFactory.getEntity(3L);
    	Resposta respostaD = RespostaFactory.getEntity(4L);

    	ColaboradorResposta colaboradorResposta1 = new ColaboradorResposta();
    	colaboradorResposta1.setResposta(respostaA);
    	colaboradorResposta1.setPergunta(pergunta1);

    	ColaboradorResposta colaboradorResposta2 = new ColaboradorResposta();
    	colaboradorResposta2.setResposta(respostaA);
    	colaboradorResposta2.setPergunta(pergunta1);

    	ColaboradorResposta colaboradorResposta3 = new ColaboradorResposta();
    	colaboradorResposta3.setResposta(respostaB);
    	colaboradorResposta3.setPergunta(pergunta1);

    	ColaboradorResposta colaboradorResposta4 = new ColaboradorResposta();
    	colaboradorResposta4.setResposta(respostaC);
    	colaboradorResposta4.setPergunta(pergunta2);

    	ColaboradorResposta colaboradorResposta5 = new ColaboradorResposta();
    	colaboradorResposta5.setResposta(respostaD);
    	colaboradorResposta5.setPergunta(pergunta2);

    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorResposta1);
    	colaboradorRespostas.add(colaboradorResposta2);
    	colaboradorRespostas.add(colaboradorResposta3);
    	colaboradorRespostas.add(colaboradorResposta4);
    	colaboradorRespostas.add(colaboradorResposta5);

//    	Collection<QuestionarioResultadoPerguntaObjetiva> resultados = questionarioManager.calculaPercentualRespostas(colaboradorRespostas);
//
//    	assertFalse(resultados.isEmpty());
//    	for (QuestionarioResultadoPerguntaObjetiva resultado : resultados)
//		{
//			if(resultado.getRespostaId().equals(respostaA.getId()))
//			{
//				assertEquals("66,67", resultado.getQtdPercentualRespostas());
//				Integer a = new Integer(2);
//				assertEquals(a, resultado.getQtdRespostas());
//			}
//			if(resultado.getRespostaId().equals(respostaB.getId()))
//			{
//				assertEquals("33,33", resultado.getQtdPercentualRespostas());
//				Integer b = new Integer(1);
//				assertEquals(b, resultado.getQtdRespostas());
//			}
//		}
    }

    public void testChecarPesquisaLiberadaByQuestionario()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setLiberado(true);

    	Boolean retorno = true;

    	questionarioDao.expects(once()).method("checarQuestionarioLiberado").with(eq(questionario)).will(returnValue(retorno));

    	assertEquals("Teste que verifica o retorno do metodo. Este retorno e um boolean.", true, questionarioManager.checarPesquisaLiberadaByQuestionario(questionario));
    }

    public void testLiberarQuestionario() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setEmpresa(empresa);
    	questionario.setDataInicio(new Date());
    	questionario.setDataFim(new Date());

    	Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
    	colaborador1.setEmailColaborador("teste1@fortesinformatica.com.br");

    	ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionario1.setColaborador(colaborador1);

    	Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
    	colaborador2.setEmailColaborador("teste2@fortesinformatica.com.br");

    	ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity(2L);
    	colaboradorQuestionario2.setColaborador(colaborador2);

    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
    	colaboradorQuestionarios.add(colaboradorQuestionario1);
    	colaboradorQuestionarios.add(colaboradorQuestionario2);

    	questionarioDao.expects(once()).method("liberarQuestionario").with(eq(questionario.getId()));
    	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(colaboradorQuestionarios));
    	MockSpringUtil.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
    	questionarioDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(questionario));
    	gerenciadorComunicacaoManager.expects(once()).method("enviaEmailQuestionarioLiberado").with(eq(empresa), eq(questionario), eq(colaboradorQuestionarios)).isVoid();

    	questionarioManager.liberarQuestionario(questionario.getId(), empresa);
    }

    public void testAplicarPorAspecto() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	Boolean aplicarPorAspecto = true;

    	questionarioDao.expects(once()).method("aplicarPorAspecto").with(ANYTHING, ANYTHING);

    	questionarioManager.aplicarPorAspecto(questionario.getId(), aplicarPorAspecto);
    }

    public void testEnviaEmailNaoRespondida()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setDataInicio(new Date());
    	questionario.setDataFim(new Date());

    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionario.setColaborador(colaborador);
    	colaboradorQuestionario.setQuestionario(questionario);

    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
    	colaboradorQuestionarios.add(colaboradorQuestionario);

    	colaboradorQuestionarioManager.expects(once()).method("findByQuestionarioEmpresaRespondida").with(eq(questionario.getId()), eq(false), eq(null), eq(empresa.getId())).will(returnValue(colaboradorQuestionarios));
    	MockSpringUtil.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
    	questionarioDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(questionario));
    	gerenciadorComunicacaoManager.expects(once()).method("enviaEmailQuestionario").with(eq(empresa), eq(questionario), eq(colaboradorQuestionarios)).isVoid();
    	
    	Exception exception = null;

    	try
		{
    		questionarioManager.enviaEmailNaoRespondida(empresa, questionario.getId());
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
    	
	}
    
    public void testAplicarPorAspectoComException() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Boolean aplicarPorAspecto = true;

    	questionarioManager.setDao(null);

    	Exception exception = null;

    	try
		{
    		questionarioManager.aplicarPorAspecto(questionario.getId(), aplicarPorAspecto);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }

    public void testGetQuestionarioRelatorio()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setQuestionario(questionario);

    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setQuestionario(questionario);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(ANYTHING).will(returnValue(perguntas));

    	questionarioManager.getQuestionarioRelatorio(questionario);
    }

    public void testFindResponderQuestionario()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setAplicarPorAspecto(true);

    	questionarioDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(questionario));

    	Aspecto aspecto = AspectoFactory.getEntity(1L);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setQuestionario(questionario);
    	pergunta1.setAspecto(aspecto);

    	Resposta resposta1 = RespostaFactory.getEntity(1L);
    	resposta1.setPergunta(pergunta1);

    	Resposta resposta2 = RespostaFactory.getEntity(2L);
    	resposta2.setPergunta(pergunta1);

    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setQuestionario(questionario);

    	Resposta resposta3 = RespostaFactory.getEntity(3L);
    	resposta3.setPergunta(pergunta2);

    	Resposta resposta4 = RespostaFactory.getEntity(4L);
    	resposta4.setPergunta(pergunta2);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	perguntaManager.expects(once()).method("findByQuestionarioAspectoPergunta").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(perguntas));

    	Collection<Resposta> respostas = new ArrayList<Resposta>();
    	respostas.add(resposta1);
    	respostas.add(resposta2);
    	respostas.add(resposta3);
    	respostas.add(resposta4);

    	respostaManager.expects(once()).method("findInPerguntaIds").with(ANYTHING).will(returnValue(respostas));

    	assertEquals(questionario, questionarioManager.findResponderQuestionario(questionario));
    }

    public void testFindQuestionarioPorUsuario()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setEmpresa(empresa);

    	Usuario usuario = UsuarioFactory.getEntity();
    	usuario.setId(1L);

    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setUsuario(usuario);

    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionario.setColaborador(colaborador);
    	colaboradorQuestionario.setQuestionario(questionario);

    	Collection<Questionario> questionarios = new ArrayList<Questionario>();
    	questionarios.add(questionario);

    	questionarioDao.expects(once()).method("findQuestionarioPorUsuario").with(ANYTHING).will(returnValue(questionarios));

    	Collection<Questionario> retorno = questionarioManager.findQuestionarioPorUsuario(usuario.getId());

    	assertEquals(questionarios.size(), retorno.size());
    }

    public void testRemoverPerguntasDoQuestionario()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	Aspecto aspecto = AspectoFactory.getEntity(1L);
    	aspecto.setQuestionario(questionario);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setQuestionario(questionario);
    	pergunta1.setAspecto(aspecto);

    	Resposta resposta1 = RespostaFactory.getEntity(1L);
    	resposta1.setPergunta(pergunta1);

    	Resposta resposta2 = RespostaFactory.getEntity(2L);
    	resposta2.setPergunta(pergunta1);

    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setQuestionario(questionario);

    	Resposta resposta3 = RespostaFactory.getEntity(3L);
    	resposta3.setPergunta(pergunta2);

    	Resposta resposta4 = RespostaFactory.getEntity(4L);
    	resposta4.setPergunta(pergunta2);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	perguntaManager.expects(once()).method("removerPerguntasDoQuestionario").with(ANYTHING);

    	aspectoManager.expects(once()).method("removerAspectosDoQuestionario").with(ANYTHING);

    	questionarioManager.removerPerguntasDoQuestionario(questionario.getId());
    }

    public void testMontaResultado() throws Exception
    {
    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setTipo(TipoPergunta.SUBJETIVA);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setTipo(TipoPergunta.SUBJETIVA);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	Resposta resposta1 = RespostaFactory.getEntity(1L);
    	resposta1.setPergunta(pergunta1);

    	Collection<Resposta> respostas = new ArrayList<Resposta>();
    	respostas.add(resposta1);

    	ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorResposta1.setPergunta(pergunta1);

    	ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity(2L);
    	colaboradorResposta2.setPergunta(pergunta2);

    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorResposta1);
    	colaboradorRespostas.add(colaboradorResposta2);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	respostaManager.expects(once()).method("findInPerguntaIds").with(ANYTHING).will(returnValue(respostas));
    	colaboradorRespostaManager.expects(once()).method("findInPerguntaIds").will(returnValue(colaboradorRespostas));
    	colaboradorRespostaManager.expects(once()).method("existeRespostaSemCargo").with(eq( new Long[] { pergunta1.getId(), pergunta2.getId() } )).will(returnValue(false));
    	colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostas").will(returnValue(new ArrayList<QuestionarioResultadoPerguntaObjetiva>()));
    	colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostasMultipla").will(returnValue(new ArrayList<QuestionarioResultadoPerguntaObjetiva>()));

    	Collection<ResultadoQuestionario> resultado = questionarioManager.montaResultado(perguntas, new Long[] { pergunta1.getId(), pergunta2.getId() }, null, null, null, null, null, true, null, questionario, false);
    	assertEquals(2, resultado.size());
    	assertEquals(1, ((ResultadoQuestionario)resultado.toArray()[0]).getColabRespostas().size());
    	assertEquals(1, ((ResultadoQuestionario)resultado.toArray()[0]).getRespostas().size());
    }
    
    public void testMontaResultadoQuandoQuestionarioAnonimo() throws Exception
    {
    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setTipo(TipoPergunta.SUBJETIVA);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);

    	Resposta resposta1 = RespostaFactory.getEntity(1L);
    	resposta1.setPergunta(pergunta1);

    	Collection<Resposta> respostas = new ArrayList<Resposta>();
    	respostas.add(resposta1);

    	ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorResposta1.setPergunta(pergunta1);

    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorResposta1);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setAnonimo(true);
    	questionario.setTipo(TipoQuestionario.PESQUISA);
    	
    	ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionario1.setQuestionario(questionario);

    	ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity(2L);
    	colaboradorQuestionario2.setQuestionario(questionario);
    	
    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
    	colaboradorQuestionarios.add(colaboradorQuestionario1);
    	colaboradorQuestionarios.add(colaboradorQuestionario2);
    	
    	respostaManager.expects(once()).method("findInPerguntaIds").with(ANYTHING).will(returnValue(respostas));
    	colaboradorRespostaManager.expects(once()).method("findInPerguntaIds").will(returnValue(colaboradorRespostas));
    	colaboradorRespostaManager.expects(once()).method("existeRespostaSemCargo").with(eq( new Long[] { pergunta1.getId() } )).will(returnValue(false));
    	colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostas").will(returnValue(new ArrayList<QuestionarioResultadoPerguntaObjetiva>()));
    	colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostasMultipla").will(returnValue(new ArrayList<QuestionarioResultadoPerguntaObjetiva>()));
    	colaboradorRespostaManager.expects(once()).method("apenasUmColaboradorRespondeuPesquisaAnonima").will(returnValue(false));
    	colaboradorQuestionarioManager.expects(once()).method("countByQuestionarioRespondido").with(eq(questionario.getId())).will(returnValue(colaboradorQuestionarios.size()));
    	
    	Exception exception = null;
    	try
		{
    		questionarioManager.montaResultado(perguntas, new Long[] { pergunta1.getId() }, null, null, null, null, null, true, null, questionario, true);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
    }
    
    public void testMontaResultadoQuandoQuestionarioAnonimoEApenasUmaReposta() throws Exception
    {
    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setTipo(TipoPergunta.SUBJETIVA);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);

    	Resposta resposta1 = RespostaFactory.getEntity(1L);
    	resposta1.setPergunta(pergunta1);

    	Collection<Resposta> respostas = new ArrayList<Resposta>();
    	respostas.add(resposta1);

    	ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorResposta1.setPergunta(pergunta1);

    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorResposta1);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setAnonimo(true);
    	questionario.setTipo(TipoQuestionario.PESQUISA);

    	ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionario1.setQuestionario(questionario);
    	
    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
    	colaboradorQuestionarios.add(colaboradorQuestionario1);
    	
    	respostaManager.expects(once()).method("findInPerguntaIds").with(ANYTHING).will(returnValue(respostas));
    	colaboradorRespostaManager.expects(once()).method("findInPerguntaIds").will(returnValue(colaboradorRespostas));
    	colaboradorRespostaManager.expects(once()).method("existeRespostaSemCargo").with(eq( new Long[] { pergunta1.getId() } )).will(returnValue(false));
    	colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostas").will(returnValue(new ArrayList<QuestionarioResultadoPerguntaObjetiva>()));
    	colaboradorRespostaManager.expects(once()).method("apenasUmColaboradorRespondeuPesquisaAnonima").will(returnValue(true));
    	colaboradorQuestionarioManager.expects(once()).method("countByQuestionarioRespondido").with(eq(questionario.getId())).will(returnValue(colaboradorQuestionarios.size()));

    	Exception exception = null;
    	try {
    		questionarioManager.montaResultado(perguntas, new Long[] { pergunta1.getId() }, null, null, null, null, null, true, null, questionario, true);
		}
		catch (Exception e) {
			exception = e;
		}

		assertEquals("Não é possível gerar o relatório porque a pesquisa é anônima e possui respostas de um único colaborador.", exception.getMessage());
    }
    
    public void testMontaResultadosAvaliacaoDesempenho()
    {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
		avaliacaoDesempenho.setAvaliacao(AvaliacaoFactory.getEntity(1L));
		
		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setTipo(TipoPergunta.NOTA);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setTipo(TipoPergunta.SUBJETIVA);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);
    	Long[] perguntasIds = new CollectionUtil<Pergunta>().convertCollectionToArrayIds(perguntas);
		Collection<Resposta> respostas = new ArrayList<Resposta>();
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		colaboradorQuestionario.setColaborador(ColaboradorFactory.getEntity(1L));
    	ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorResposta1.setPergunta(pergunta1);
    	colaboradorResposta1.setColaboradorQuestionario(colaboradorQuestionario);
		Collection<ColaboradorResposta> colaboradorRespostas = Arrays.asList(colaboradorResposta1);
		
		Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>(); 
		
		Collection<ResultadoAvaliacaoDesempenho> resultadoQuestionarios = new ArrayList<ResultadoAvaliacaoDesempenho>();
		ResultadoAvaliacaoDesempenho resultadoAvaliacaoDesempenho = new ResultadoAvaliacaoDesempenho(2L, "José", 1.0);
		resultadoQuestionarios.add(resultadoAvaliacaoDesempenho);
		
		Long avaliadoId = 1L;
		boolean desconsiderarAutoAvaliacao = false;
		
		colaboradorQuestionarioManager.expects(once()).method("getMediaPeformance").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Double(2)));
		colaboradorManager.expects(once()).method("getNome").with(eq(1L)).will(returnValue("José"));
		avaliacaoManager.expects(once()).method("montaObsAvaliadores").will(returnValue("obsAvaliadores"));
		perguntaManager.expects(atLeastOnce()).method("setAvaliadoNaPerguntaDeAvaliacaoDesempenho").with(ANYTHING, eq("José"));
		
		Collection<ResultadoAvaliacaoDesempenho> resultados = questionarioManager.montaResultadosAvaliacaoDesempenho(perguntas, null, respostas, avaliadoId, colaboradorRespostas, percentuaisDeRespostas, avaliacaoDesempenho, 1, desconsiderarAutoAvaliacao);
		assertEquals(2, resultados.size());
    }
    
    public void testMontaResultadosAvaliacaoDesempenhoComAspecto()
    {
    	Aspecto aspecto1 = AspectoFactory.getEntity(1L);
    	aspecto1.setNome("tecnico");
    	Aspecto aspecto2 = AspectoFactory.getEntity(2L);
    	aspecto2.setNome("humano");
    	
    	AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
    	avaliacaoDesempenho.setAvaliacao(AvaliacaoFactory.getEntity(1L));
    	
    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setTipo(TipoPergunta.NOTA);
    	pergunta1.setAspecto(aspecto1);
    	pergunta1.setPeso(3);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setTipo(TipoPergunta.SUBJETIVA);
    	pergunta2.setAspecto(aspecto2);
    	pergunta2.setPeso(2);
    	Pergunta pergunta3 = PerguntaFactory.getEntity(3L);
    	pergunta3.setTipo(TipoPergunta.OBJETIVA);
    	pergunta3.setAspecto(aspecto2);
    	pergunta3.setPeso(4);
    	Pergunta pergunta4 = PerguntaFactory.getEntity(4L);
    	pergunta4.setTipo(TipoPergunta.NOTA);
    	pergunta4.setAspecto(aspecto2);
    	pergunta4.setPeso(13);
    	
    	Collection<Pergunta> perguntas = Arrays.asList(pergunta1, pergunta2, pergunta3, pergunta4);
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionario.setColaborador(colaborador);
    	
    	Resposta resposta = RespostaFactory.getEntity(1L);
    	resposta.setPergunta(pergunta3);
    	resposta.setPeso(20);
    	Collection<Resposta> respostas = Arrays.asList(resposta);

    	ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorResposta1.setPergunta(pergunta1);
    	colaboradorResposta1.setValor(7);
    	colaboradorResposta1.setResposta(resposta);
    	colaboradorResposta1.setColaboradorQuestionario(colaboradorQuestionario);
    	
    	ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity(2L);
    	colaboradorResposta2.setPergunta(pergunta3);
    	colaboradorResposta2.setResposta(resposta);
    	colaboradorResposta2.setColaboradorQuestionario(colaboradorQuestionario);
    	
    	ColaboradorResposta colaboradorResposta3 = ColaboradorRespostaFactory.getEntity(3L);
    	colaboradorResposta3.setValor(6);
    	colaboradorResposta3.setPergunta(pergunta4);
    	colaboradorResposta3.setResposta(resposta);
    	colaboradorResposta3.setColaboradorQuestionario(colaboradorQuestionario);
    	
    	Collection<ColaboradorResposta> colaboradorRespostas = Arrays.asList(colaboradorResposta1, colaboradorResposta2, colaboradorResposta3);
    	Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>(); 
    	
    	Long avaliadoId = colaborador.getId();
    	boolean desconsiderarAutoAvaliacao = false;
    	
    	colaboradorQuestionarioManager.expects(once()).method("getMediaPeformance").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Double(2)));
    	colaboradorManager.expects(once()).method("getNome").with(eq(1L)).will(returnValue("José"));
    	avaliacaoManager.expects(once()).method("montaObsAvaliadores").will(returnValue("obsAvaliadores"));
    	perguntaManager.expects(atLeastOnce()).method("setAvaliadoNaPerguntaDeAvaliacaoDesempenho").with(ANYTHING, eq("José"));
    	
    	Collection<ResultadoAvaliacaoDesempenho> resultados = questionarioManager.montaResultadosAvaliacaoDesempenho(perguntas, null, respostas, avaliadoId, colaboradorRespostas, percentuaisDeRespostas, avaliacaoDesempenho, 1, desconsiderarAutoAvaliacao);
    	assertEquals(4, resultados.size());
    }

    public void testEnviaLembreteDeQuestionarioNaoLiberadaComException()
    {
    	gerenciadorComunicacaoManager.expects(atLeastOnce()).method("enviaLembreteDeQuestionarioNaoLiberado");
    	questionarioManager.enviaLembreteDeQuestionarioNaoLiberado();
    }
    
    public void testMontaImpressaoFichaMedica()
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	fichaMedica.setProjectionQuestionarioTitulo("Teste");
    	fichaMedica.setRodape("Rodapé");
    	
    	Long id = 10L;
    	Long colaboradorQuestionarioId = 3L;
    	
    	HashMap<String, Object> parametros = new HashMap<String, Object>();
    	
    	Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);
    	
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(id)).will(returnValue(perguntas));
    	colaboradorQuestionarioManager.expects(once()).method("findByIdColaboradorCandidato").with(eq(colaboradorQuestionarioId)).will(returnValue(ColaboradorQuestionarioFactory.getEntity(1231L)));
    	colaboradorRespostaManager.expects(once()).method("findByColaboradorQuestionario").will(returnValue(new ArrayList<ColaboradorResposta>()));
    	fichaMedicaManager.expects(once()).method("findByQuestionario").will(returnValue(fichaMedica));
    	perguntaManager.expects(atLeastOnce()).method("montaImpressaoPergunta");
    	
    	Collection<PerguntaFichaMedica> perguntaFichaMedicas =  
    		questionarioManager.montaImpressaoFichaMedica(id, colaboradorQuestionarioId, parametros);
    	
    	assertEquals(1, perguntaFichaMedicas.size());
    }
}

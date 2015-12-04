package com.fortes.rh.test.business.pesquisa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionUsageException;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManagerImpl;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.dao.pesquisa.ColaboradorRespostaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorRespostaManagerTest extends MockObjectTestCase
{
	private ColaboradorRespostaManagerImpl colaboradorRespostaManager = new ColaboradorRespostaManagerImpl();
	private Mock colaboradorRespostaDao;
	private Mock colaboradorQuestionarioManager;
	private Mock transactionManager;
	private Mock historicoColaboradorManager;
	private Mock questionarioManager;
	private Mock colaboradorManager;
	private Mock avaliacaoManager;
	private Mock nivelCompetenciaManager;
	private Mock configuracaoNivelCompetenciaFaixaSalarialManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        colaboradorRespostaDao = new Mock(ColaboradorRespostaDao.class);
        colaboradorRespostaManager.setDao((ColaboradorRespostaDao) colaboradorRespostaDao.proxy());

        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        colaboradorRespostaManager.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        colaboradorRespostaManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
        colaboradorRespostaManager.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());

        questionarioManager = new Mock(QuestionarioManager.class);
        colaboradorRespostaManager.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());

        colaboradorManager = new Mock(ColaboradorManager.class);
        colaboradorRespostaManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        
        avaliacaoManager = mock(AvaliacaoManager.class);
        colaboradorRespostaManager.setAvaliacaoManager((AvaliacaoManager) avaliacaoManager.proxy());
        
        nivelCompetenciaManager = mock(NivelCompetenciaManager.class);
        colaboradorRespostaManager.setNivelCompetenciaManager((NivelCompetenciaManager) nivelCompetenciaManager.proxy());
        
        configuracaoNivelCompetenciaFaixaSalarialManager = new Mock(ConfiguracaoNivelCompetenciaFaixaSalarialManager.class);
        colaboradorRespostaManager.setConfiguracaoNivelCompetenciaFaixaSalarialManager((ConfiguracaoNivelCompetenciaFaixaSalarialManager) configuracaoNivelCompetenciaFaixaSalarialManager.proxy());
    }

    public void testCountRespostas()
    {
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	List<Integer[]> lista = new ArrayList<Integer[]>();

    	Long[] areasIds = new Long[]{};

    	colaboradorRespostaDao.expects(once()).method("countRespostas").with(new Constraint[]{ANYTHING, ANYTHING, eq(areasIds), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(lista));

    	assertEquals(lista, colaboradorRespostaManager.countRespostas(pergunta.getId(), null, areasIds, null, null, null));
    }

    public void testFindInPerguntaIds()
    {
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	Collection<ColaboradorResposta> colaboradorRespostas = ColaboradorRespostaFactory.getCollection();

    	Long[] perguntasIds = new Long[]{pergunta.getId()};
    	Long[] areasIds = new Long[]{};

    	colaboradorRespostaDao.expects(once()).method("findInPerguntaIds").with(new Constraint[]{eq(perguntasIds), ANYTHING, eq(areasIds), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradorRespostas));

    	assertEquals(colaboradorRespostas, colaboradorRespostaManager.findInPerguntaIds(perguntasIds, null, areasIds, null, null, null, false, null, null, null));
    }

    public void testFindByQuestionarioColaborador()
    {
    	Collection<ColaboradorResposta> colaboradorRespostas = ColaboradorRespostaFactory.getCollection();
    	Long questionarioId = 1L;
    	Long colaboradorId = 2L;

    	colaboradorRespostaDao.expects(once()).method("findByQuestionarioColaborador").with(eq(questionarioId), eq(colaboradorId), ANYTHING, ANYTHING).will(returnValue(colaboradorRespostas));

    	assertEquals(colaboradorRespostas, colaboradorRespostaManager.findByQuestionarioColaborador(questionarioId, colaboradorId, null, null));
    }

    public void testSalvaQuestionarioRespondidaPesquisa() throws Exception
    {
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	Cargo cargo = CargoFactory.getEntity(1L);
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial.setCargo(cargo);

    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setAreaOrganizacional(areaOrganizacional);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setTipo(TipoQuestionario.PESQUISA);

    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);

    	HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
    	historicoColaborador.setAreaOrganizacional(areaOrganizacional);
    	historicoColaborador.setFaixaSalarial(faixaSalarial);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));

    	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId()), eq(colaborador.getId()), ANYTHING).will(returnValue(colaboradorQuestionario));

    	colaboradorQuestionario.setRespondida(true);

    	colaboradorQuestionarioManager.expects(once()).method("update").with(eq(colaboradorQuestionario));

    	String respostas = "PG01¨RO01¨PG02¨RO02¨PG03¨RN10¨RCTESETE¨PG04¨RSTETETETETE";

    	historicoColaboradorManager.expects(once()).method("getHistoricoAtualOuFuturo").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));

    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus status = null;

    	transactionManager.expects(once()).method("getTransaction").with(eq(def)).will(returnValue(status));

    	colaboradorRespostaDao.expects(atLeastOnce()).method("save").with(ANYTHING);

    	transactionManager.expects(once()).method("commit").with(ANYTHING).isVoid();

    	colaboradorRespostaManager.salvaQuestionarioRespondido(respostas, questionario, colaborador.getId(), null, ' ', new Date(), null, false);

    }
    
    public void testRemoveFicha() throws Exception
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	
    	colaboradorRespostaDao.expects(once()).method("removeByColaboradorQuestionario").with(eq(colaboradorQuestionario.getId())).isVoid();
    	colaboradorQuestionarioManager.expects(once()).method("remove").with(eq(colaboradorQuestionario.getId())).isVoid();
    	
    	Exception exception = null;
    	
    	try
		{
    		colaboradorRespostaManager.removeFicha(colaboradorQuestionario.getId());			
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNull(exception);
    }
    
    public void testRemoveFichaException() throws Exception
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	
    	colaboradorRespostaDao.expects(once()).method("removeByColaboradorQuestionario").with(eq(colaboradorQuestionario.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(colaboradorQuestionario.getId(),""))));;
    	
    	Exception exception = null;
    	
    	try
    	{
    		colaboradorRespostaManager.removeFicha(colaboradorQuestionario.getId());			
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}
    	
    	assertNotNull(exception);
    }

    public void testSalvaQuestionarioRespondidaEntrevista() throws Exception
    {
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	Cargo cargo = CargoFactory.getEntity(1L);
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial.setCargo(cargo);

    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setAreaOrganizacional(areaOrganizacional);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setTipo(TipoQuestionario.ENTREVISTA);

    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);

    	HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
    	historicoColaborador.setAreaOrganizacional(areaOrganizacional);
    	historicoColaborador.setFaixaSalarial(faixaSalarial);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));

    	colaboradorQuestionario.setRespondida(true);

    	colaboradorQuestionarioManager.expects(once()).method("save").with(ANYTHING).will(returnValue(colaboradorQuestionario));

    	String respostas = "PG01¨RO01¨PG02¨RO02¨PG03¨RN10¨RCTESETE¨PG04¨RSTETETETETE";

    	colaboradorManager.expects(once()).method("respondeuEntrevista").with(ANYTHING);

    	historicoColaboradorManager.expects(once()).method("getHistoricoAtualOuFuturo").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
    	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId()), eq(colaborador.getId()), ANYTHING).will(returnValue(colaboradorQuestionario));

    	colaboradorRespostaDao.expects(once()).method("removeByColaboradorQuestionario").with(eq(colaboradorQuestionario.getId()));
    	colaboradorQuestionarioManager.expects(once()).method("remove").with(eq(colaboradorQuestionario.getId()));

    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    	TransactionStatus status = null;

    	transactionManager.expects(once()).method("getTransaction").with(eq(def)).will(returnValue(status));

    	colaboradorRespostaDao.expects(atLeastOnce()).method("save").with(ANYTHING);

    	transactionManager.expects(once()).method("commit").with(ANYTHING);

    	colaboradorRespostaManager.salvaQuestionarioRespondido(respostas, questionario, colaborador.getId(), null, ' ', new Date(), null, false);

    }

    public void testSalvaQuestionarioRespondidaComExcecao() throws Exception
    {
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	Cargo cargo = CargoFactory.getEntity(1L);
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial.setCargo(cargo);

    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setAreaOrganizacional(areaOrganizacional);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setTipo(TipoQuestionario.PESQUISA);

    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);

    	HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
    	historicoColaborador.setAreaOrganizacional(areaOrganizacional);
    	historicoColaborador.setFaixaSalarial(faixaSalarial);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId()), eq(colaborador.getId()), ANYTHING).will(returnValue(colaboradorQuestionario));

    	colaboradorQuestionario.setRespondida(true);

    	String respostas = "PG01¨RO01¨PG02¨RO02¨PG03¨RN10¨RCTESETE¨PG04¨RSTETETETETE";

    	historicoColaboradorManager.expects(once()).method("getHistoricoAtualOuFuturo").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));

    	colaboradorQuestionario.setRespondida(true);

    	colaboradorQuestionarioManager.expects(once()).method("update").with(eq(colaboradorQuestionario));

    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    	transactionManager.expects(once()).method("getTransaction").with(eq(def));

    	colaboradorRespostaDao.expects(atLeastOnce()).method("save").with(ANYTHING);

    	transactionManager.expects(once()).method("commit").with(ANYTHING).will(throwException(new TransactionUsageException("Erro")));

    	transactionManager.expects(once()).method("rollback").with(ANYTHING);

    	Exception exception = null;

    	try
    	{
    		colaboradorRespostaManager.salvaQuestionarioRespondido(respostas, questionario, colaborador.getId(), null, ' ', new Date(), null, false);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }


    public void testFindRespostasColaborador()
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);

    	Collection<ColaboradorResposta> colaboradorRespostas = ColaboradorRespostaFactory.getCollection();

    	colaboradorRespostaDao.expects(once()).method("findRespostasColaborador").with(ANYTHING, ANYTHING).will(returnValue(colaboradorRespostas));

    	Boolean aplicarPorAspecto = false;

    	assertEquals(colaboradorRespostas, colaboradorRespostaManager.findRespostasColaborador(colaboradorQuestionario.getId(), aplicarPorAspecto));
    }

    public void testFindRespostasColaboradorAplicadoPorAspecto()
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);

    	Aspecto aspecto = AspectoFactory.getEntity(1L);

		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		pergunta.setAspecto(aspecto);

		Resposta resposta = RespostaFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);

    	Collection<ColaboradorResposta> colaboradorRespostas = ColaboradorRespostaFactory.getCollection();

    	for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
		{
			colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
			colaboradorResposta.setAreaOrganizacional(areaOrganizacional);
			colaboradorResposta.setPergunta(pergunta);
			colaboradorResposta.setResposta(resposta);
		}

    	colaboradorRespostaDao.expects(once()).method("findRespostasColaborador").with(ANYTHING, ANYTHING).will(returnValue(colaboradorRespostas));

    	Boolean aplicarPorAspecto = true;

    	assertEquals(colaboradorRespostas, colaboradorRespostaManager.findRespostasColaborador(colaboradorQuestionario.getId(), aplicarPorAspecto));

    }

    public void testFindRespostasColaboradorAplicadoPorAspectoPerguntaSemAspecto()
    {
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);

		Pergunta pergunta = PerguntaFactory.getEntity(1L);

		Resposta resposta = RespostaFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);

    	Collection<ColaboradorResposta> colaboradorRespostas = ColaboradorRespostaFactory.getCollection();

    	for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
		{
			colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);
			colaboradorResposta.setAreaOrganizacional(areaOrganizacional);
			colaboradorResposta.setPergunta(pergunta);
			colaboradorResposta.setResposta(resposta);
		}

    	colaboradorRespostaDao.expects(once()).method("findRespostasColaborador").with(ANYTHING, ANYTHING).will(returnValue(colaboradorRespostas));

    	Boolean aplicarPorAspecto = true;

    	assertEquals(colaboradorRespostas, colaboradorRespostaManager.findRespostasColaborador(colaboradorQuestionario.getId(), aplicarPorAspecto));

    }
    
    public void testCalculaPercentualRespostasDeAvaliacoesDeDesempenho()
    {
    	Long avaliadoId = 1L;
    	Long avaliacaoDesempenhoId = 1L;
    	
    	List<Object[]> countRespostas = new ArrayList<Object[]>();
    	//resposta.ordem, count(resposta.id), pergunta.id, resposta.id, count(respostas da pergunta)
    	countRespostas.add(new Object[]{1,1,384L,103L,3});
    	countRespostas.add(new Object[]{2,3,382L,102L,3});
    	countRespostas.add(new Object[]{2,2,384L,104L,3});
    	
    	colaboradorRespostaDao.expects(once()).method("countRespostas").with(eq(avaliadoId), eq(avaliacaoDesempenhoId), eq(true)).will(returnValue(countRespostas));
    	Collection<QuestionarioResultadoPerguntaObjetiva> resultadosObjetivas = colaboradorRespostaManager.calculaPercentualRespostas(avaliadoId, avaliacaoDesempenhoId, true);
    	
    	assertEquals(3, resultadosObjetivas.size());
    	for (QuestionarioResultadoPerguntaObjetiva resultado : resultadosObjetivas)
    	{
    		if(resultado.getRespostaId().equals(103L))
    			assertEquals("33,33", resultado.getQtdPercentualRespostas());
    		if(resultado.getRespostaId().equals(102L))
    			assertEquals("100,00", resultado.getQtdPercentualRespostas());
    		if(resultado.getRespostaId().equals(104L))
    			assertEquals("66,67", resultado.getQtdPercentualRespostas());
    	}
    }

    public void testCalculaPercentualRespostasMultiplaDeAvaliacoesDeDesempenho()
    {
    	Long avaliadoId = 1L;
    	Long avaliacaoDesempenhoId = 1L;
    	boolean desconsiderarAutoAvaliacao = true;
    	
    	List<Object[]> countRespostas = new ArrayList<Object[]>();
    	//resposta.ordem, count(resposta.id), pergunta.id, resposta.id
    	countRespostas.add(new Object[]{1,1,384L,103L});
    	countRespostas.add(new Object[]{1,2,382L,102L});
    	countRespostas.add(new Object[]{2,1,384L,104L});
    	countRespostas.add(new Object[]{1,3,385L,105L});
    	
    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
    	colaboradorQuestionarios.add(new ColaboradorQuestionario());
    	colaboradorQuestionarios.add(new ColaboradorQuestionario());
    	colaboradorQuestionarios.add(new ColaboradorQuestionario());
    	
    	colaboradorRespostaDao.expects(once()).method("countRespostasMultiplas").with(eq(avaliadoId), eq(avaliacaoDesempenhoId), eq(true)).will(returnValue(countRespostas));
    	colaboradorQuestionarioManager.expects(once()).method("findByColaboradorAndAvaliacaoDesempenho").with(eq(avaliadoId), eq(avaliacaoDesempenhoId), eq(true), eq(desconsiderarAutoAvaliacao)).will(returnValue(colaboradorQuestionarios));
    	Collection<QuestionarioResultadoPerguntaObjetiva> resultadosMultiplas = colaboradorRespostaManager.calculaPercentualRespostasMultipla(avaliadoId, avaliacaoDesempenhoId, true);
    	
    	assertEquals(4, resultadosMultiplas.size());
    	for (QuestionarioResultadoPerguntaObjetiva resultado : resultadosMultiplas)
    	{
    		if(resultado.getRespostaId().equals(103L))
    			assertEquals("33,33", resultado.getQtdPercentualRespostas());
    		if(resultado.getRespostaId().equals(102L))
    			assertEquals("66,67", resultado.getQtdPercentualRespostas());
    		if(resultado.getRespostaId().equals(104L))
    			assertEquals("33,33", resultado.getQtdPercentualRespostas());
    		if(resultado.getRespostaId().equals(105L))
    			assertEquals("100,00", resultado.getQtdPercentualRespostas());
    	}
    }
    
    public void testCalculaPercentualRespostasDeAvaliacoes()
    {
    	Long[] perguntasIds = new Long[]{1L};
    	Long[] areasIds = new Long[]{2L};

    	List<Object[]> countRespostas = new ArrayList<Object[]>();
    	//resposta.ordem, count(resposta.id), pergunta.id, resposta.id, count(respostas da pergunta)
    	countRespostas.add(new Object[]{1,1,384L,103L,3});
    	countRespostas.add(new Object[]{2,3,382L,102L,3});
    	countRespostas.add(new Object[]{2,2,384L,104L,3});

    	colaboradorRespostaDao.expects(once()).method("countRespostas").with(new Constraint[]{eq(perguntasIds), ANYTHING, eq(areasIds), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(countRespostas));
    	Collection<QuestionarioResultadoPerguntaObjetiva> resultadosObjetivas = colaboradorRespostaManager.calculaPercentualRespostas(perguntasIds, null, areasIds, null, null, null, false, null, null, null);

    	assertEquals(3, resultadosObjetivas.size());
    	for (QuestionarioResultadoPerguntaObjetiva resultado : resultadosObjetivas)
		{
			if(resultado.getRespostaId().equals(103L))
				assertEquals("33,33", resultado.getQtdPercentualRespostas());
			if(resultado.getRespostaId().equals(102L))
				assertEquals("100,00", resultado.getQtdPercentualRespostas());
			if(resultado.getRespostaId().equals(104L))
				assertEquals("66,67", resultado.getQtdPercentualRespostas());
		}
    }
    
    public void testCalculaPercentualRespostasMultiplaDeAvaliacoes()
    {
    	Long[] perguntasIds = new Long[]{1L};
    	Long[] areasIds = new Long[]{2L};
    	
    	List<Object[]> countRespostas = new ArrayList<Object[]>();
    	//resposta.ordem, count(resposta.id), pergunta.id, resposta.id
    	countRespostas.add(new Object[]{1,1,384L,103L});
    	countRespostas.add(new Object[]{1,2,382L,102L});
    	countRespostas.add(new Object[]{2,1,384L,104L});
    	
    	colaboradorRespostaDao.expects(once()).method("countRespostasMultiplas").with(new Constraint[]{eq(perguntasIds), ANYTHING, eq(areasIds), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(countRespostas));
    	Collection<QuestionarioResultadoPerguntaObjetiva> resultadosMultiplas = colaboradorRespostaManager.calculaPercentualRespostasMultipla(perguntasIds, null, areasIds, null, null, null, false, null, null);
    	
    	assertEquals(3, resultadosMultiplas.size());
    	for (QuestionarioResultadoPerguntaObjetiva resultado : resultadosMultiplas)
    	{
    		if(resultado.getRespostaId().equals(103L))
    			assertEquals("50,00", resultado.getQtdPercentualRespostas());
    		if(resultado.getRespostaId().equals(102L))
    			assertEquals("100,00", resultado.getQtdPercentualRespostas());
    		if(resultado.getRespostaId().equals(104L))
    			assertEquals("50,00", resultado.getQtdPercentualRespostas());
    	}
    }
    
    public void testSave()
    {
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setAreaOrganizacional(areaOrganizacional);
    	colaborador.setEstabelecimento(estabelecimento);
    	
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(22L); 
    	colaboradorQuestionario.setColaborador(colaborador);

    	HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
    	historicoColaborador.setAreaOrganizacional(areaOrganizacional);
    	historicoColaborador.setEstabelecimento(estabelecimento);
    	
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	pergunta.setTipo(TipoPergunta.OBJETIVA);
    	
    	Resposta resposta = RespostaFactory.getEntity(1L);
    	
    	ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity(10L);
    	colaboradorResposta.setPergunta(pergunta);
    	colaboradorResposta.setResposta(resposta);
    	
    	Collection<ColaboradorResposta> colaboradorRespostas = Arrays.asList(colaboradorResposta);
    	
    	historicoColaboradorManager.expects(once()).method("getHistoricoAtual").with(eq(colaborador.getId())).will(returnValue(historicoColaborador));
    	colaboradorRespostaDao.expects(once()).method("save").will(returnValue(ColaboradorRespostaFactory.getEntity(1L)));
    	colaboradorQuestionarioManager.expects(once()).method("save").isVoid();
    	
    	Usuario usuarioLogado = UsuarioFactory.getEntity(1L);
    	
		colaboradorRespostaManager.save(colaboradorRespostas, colaboradorQuestionario, usuarioLogado.getId(), null);
    }
    
    public void testSavePerformance()
    {
       	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
       	colaborador.setNome("Babão do Chefe");
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
    	
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(22L);
    	colaboradorQuestionario.setColaborador(colaborador);
    	colaboradorQuestionario.setAvaliacao(avaliacao);
    	
    	ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity(1L);
    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setTipo(TipoPergunta.OBJETIVA);
    	pergunta1.setPeso(9);
    	pergunta1.setTexto("Relacionamento com colegas");
    	Resposta resposta1 = RespostaFactory.getEntity(1L);
    	resposta1.setTexto("ruim");
    	resposta1.setPeso(1);
    	
    	colaboradorResposta1.setPergunta(pergunta1);
    	colaboradorResposta1.setResposta(resposta1);
    	
    	ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity(2L);
    	colaboradorResposta2.setValor(5);
    	
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setTipo(TipoPergunta.NOTA);
    	pergunta2.setPeso(9);
    	pergunta2.setNotaMinima(1);
    	pergunta2.setNotaMaxima(5);
    	pergunta2.setTexto("Relacionamento com chefia");
    	
    	colaboradorResposta2.setPergunta(pergunta2);
    	
    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorResposta1);
    	colaboradorRespostas.add(colaboradorResposta2);
    	
    	colaboradorQuestionarioManager.expects(once()).method("save").isVoid();
    	colaboradorQuestionarioManager.expects(once()).method("update").isVoid();
    	historicoColaboradorManager.expects(atLeastOnce()).method("getHistoricoAtual").with(eq(colaborador.getId())).will(returnValue(new HistoricoColaborador()));
    	colaboradorRespostaDao.expects(atLeastOnce()).method("save");
    	
    	Integer pontuacaoMaxima = 98;
    	
    	avaliacaoManager.expects(once()).method("getPontuacaoMaximaDaPerformance").will(returnValue(pontuacaoMaxima));
    	colaboradorRespostaDao.expects(once()).method("findByColaboradorQuestionario").will(returnValue(colaboradorRespostas));
    	
    	
    	Usuario usuarioLogado = UsuarioFactory.getEntity(1L);

    	colaboradorRespostaManager.save(colaboradorRespostas, colaboradorQuestionario, usuarioLogado.getId(), null);
    	
    	assertEquals("55,1%", colaboradorQuestionario.getPerformanceFormatada());
    }
    
    public void testSavePerformanceDaAvaliacaoExperiencia()
    {
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
    	
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(22L);
    	colaboradorQuestionario.setAvaliacao(avaliacao);
    	
    	Pergunta perguntaObjetiva = PerguntaFactory.getEntity(1L);
    	perguntaObjetiva.setTipo(TipoPergunta.OBJETIVA);
    	perguntaObjetiva.setPeso(9);
    	
    	Resposta respostaObjetivaA = RespostaFactory.getEntity(1L);
    	respostaObjetivaA.setPeso(1);
    	
    	Pergunta perguntaNota = PerguntaFactory.getEntity(2L);
    	perguntaNota.setTipo(TipoPergunta.NOTA);
    	perguntaNota.setPeso(2);
    	
    	Pergunta perguntaMultipla = PerguntaFactory.getEntity(3L);
    	perguntaMultipla.setTipo(TipoPergunta.MULTIPLA_ESCOLHA);
    	perguntaMultipla.setPeso(5);

    	Resposta respostaMuliplaA = RespostaFactory.getEntity(2L);
    	respostaMuliplaA.setPeso(2);
    	
    	Resposta respostaMuliplaB = RespostaFactory.getEntity(3L);
    	respostaMuliplaB.setPeso(4);
    	
    	ColaboradorResposta colaboradorRespostaObjetiva = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorRespostaObjetiva.setPergunta(perguntaObjetiva);
    	colaboradorRespostaObjetiva.setResposta(respostaObjetivaA);
    	
    	ColaboradorResposta colaboradorRespostaNota = ColaboradorRespostaFactory.getEntity(2L);
    	colaboradorRespostaNota.setValor(5);    	
    	colaboradorRespostaNota.setPergunta(perguntaNota);
    	
    	ColaboradorResposta colaboradorRespostaMultiplaA = ColaboradorRespostaFactory.getEntity(3L);
    	colaboradorRespostaMultiplaA.setPergunta(perguntaMultipla);
    	colaboradorRespostaMultiplaA.setResposta(respostaMuliplaA);

    	ColaboradorResposta colaboradorRespostaMultiplaB = ColaboradorRespostaFactory.getEntity(4L);
    	colaboradorRespostaMultiplaB.setPergunta(perguntaMultipla);
    	colaboradorRespostaMultiplaB.setResposta(respostaMuliplaB);
    	
    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorRespostaObjetiva);
    	colaboradorRespostas.add(colaboradorRespostaNota);
    	colaboradorRespostas.add(colaboradorRespostaMultiplaA);
    	colaboradorRespostas.add(colaboradorRespostaMultiplaB);
    	
    	Integer pontuacaoMaxima = 105;
    	
    	avaliacaoManager.expects(once()).method("getPontuacaoMaximaDaPerformance").will(returnValue(pontuacaoMaxima));
    	colaboradorRespostaDao.expects(once()).method("findByColaboradorQuestionario").will(returnValue(colaboradorRespostas));
    	colaboradorRespostaDao.expects(once()).method("removeByColaboradorQuestionario").isVoid();
    	colaboradorQuestionarioManager.expects(once()).method("update").with(eq(colaboradorQuestionario));
    	
    	colaboradorRespostaManager.update(new ArrayList<ColaboradorResposta>(), colaboradorQuestionario, 1L, null, null);
    	
    	assertEquals("46,67%", colaboradorQuestionario.getPerformanceFormatada());
    }
  
    public void testSavePerformanceDaAvaliacaoExperienciaComNivelCompetencia()
    {
    	ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
    	configuracaoNivelCompetenciaFaixaSalarial.setData(DateUtil.criarDataMesAno(1, 1, 2015));
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setFaixaSalarial(faixaSalarial);
    	
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
    	
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(22L);
    	colaboradorQuestionario.setAvaliacao(avaliacao);
    	colaboradorQuestionario.setColaborador(colaborador);
    	
    	Pergunta perguntaObjetiva = PerguntaFactory.getEntity(1L);
    	perguntaObjetiva.setTipo(TipoPergunta.OBJETIVA);
    	perguntaObjetiva.setPeso(9);
    	
    	Resposta respostaObjetivaA = RespostaFactory.getEntity(1L);
    	respostaObjetivaA.setPeso(1);
    	
    	Pergunta perguntaNota = PerguntaFactory.getEntity(2L);
    	perguntaNota.setTipo(TipoPergunta.NOTA);
    	perguntaNota.setPeso(2);
    	
    	Pergunta perguntaMultipla = PerguntaFactory.getEntity(3L);
    	perguntaMultipla.setTipo(TipoPergunta.MULTIPLA_ESCOLHA);
    	perguntaMultipla.setPeso(5);
    	
    	Resposta respostaMuliplaA = RespostaFactory.getEntity(2L);
    	respostaMuliplaA.setPeso(2);
    	
    	Resposta respostaMuliplaB = RespostaFactory.getEntity(3L);
    	respostaMuliplaB.setPeso(4);
    	
    	ColaboradorResposta colaboradorRespostaObjetiva = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorRespostaObjetiva.setPergunta(perguntaObjetiva);
    	colaboradorRespostaObjetiva.setResposta(respostaObjetivaA);
    	
    	ColaboradorResposta colaboradorRespostaNota = ColaboradorRespostaFactory.getEntity(2L);
    	colaboradorRespostaNota.setValor(5);    	
    	colaboradorRespostaNota.setPergunta(perguntaNota);
    	
    	ColaboradorResposta colaboradorRespostaMultiplaA = ColaboradorRespostaFactory.getEntity(3L);
    	colaboradorRespostaMultiplaA.setPergunta(perguntaMultipla);
    	colaboradorRespostaMultiplaA.setResposta(respostaMuliplaA);
    	
    	ColaboradorResposta colaboradorRespostaMultiplaB = ColaboradorRespostaFactory.getEntity(4L);
    	colaboradorRespostaMultiplaB.setPergunta(perguntaMultipla);
    	colaboradorRespostaMultiplaB.setResposta(respostaMuliplaB);
    	
    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorRespostaObjetiva);
    	colaboradorRespostas.add(colaboradorRespostaNota);
    	colaboradorRespostas.add(colaboradorRespostaMultiplaA);
    	colaboradorRespostas.add(colaboradorRespostaMultiplaB);
    	
    	Integer pontuacaoMaxima = 105;
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	NivelCompetencia nivelCompetencia1 = NivelCompetenciaFactory.getEntity(1L);
		NivelCompetencia nivelCompetencia2 = NivelCompetenciaFactory.getEntity(2L);
		
		ConfiguracaoNivelCompetencia configNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configNivelCompetencia1.setNivelCompetencia(nivelCompetencia1);

		ConfiguracaoNivelCompetencia configNivelCompetencia2 = new ConfiguracaoNivelCompetencia();
		configNivelCompetencia2.setNivelCompetencia(nivelCompetencia2);

		Collection<ConfiguracaoNivelCompetencia> confgniveisCompetencia = Arrays.asList(configNivelCompetencia1, configNivelCompetencia2);
		
		configuracaoNivelCompetenciaFaixaSalarialManager.expects(once()).method("findByFaixaSalarialIdAndData").will(returnValue(configuracaoNivelCompetenciaFaixaSalarial));
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		
		colaboradorManager.expects(once()).method("findColaboradorByDataHistorico").will(returnValue(colaborador));
		nivelCompetenciaManager.expects(once()).method("getPontuacaoObtidaByConfiguracoesNiveisCompetencia").with(eq(confgniveisCompetencia)).will(returnValue(20));
		nivelCompetenciaManager.expects(once()).method("getOrdemMaxima").with(eq(empresa.getId()), eq(configuracaoNivelCompetenciaFaixaSalarial.getData())).will(returnValue(50));
    	
		historicoColaboradorManager.expects(once()).method("getHistoricoAtual").will(returnValue(historicoColaborador));
    	avaliacaoManager.expects(once()).method("getPontuacaoMaximaDaPerformance").will(returnValue(pontuacaoMaxima));
    	colaboradorRespostaDao.expects(once()).method("findByColaboradorQuestionario").will(returnValue(colaboradorRespostas));
    	colaboradorRespostaDao.expects(once()).method("removeByColaboradorQuestionario").isVoid();
    	colaboradorQuestionarioManager.expects(once()).method("update").with(eq(colaboradorQuestionario));
    	
    	colaboradorRespostaManager.update(new ArrayList<ColaboradorResposta>(), colaboradorQuestionario, 1L, empresa.getId(), confgniveisCompetencia);
    	
    	assertEquals("23,9%", colaboradorQuestionario.getPerformanceFormatada());
    	assertEquals("9,76%", colaboradorQuestionario.getPerformanceNivelCompetenciaFormatada());
    }

    public void testSavePerformanceDaAvaliacaoExperienciaNegativa()
    {
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
    	
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(22L);
    	colaboradorQuestionario.setAvaliacao(avaliacao);
    	
    	Pergunta perguntaObjetiva = PerguntaFactory.getEntity(1L);
    	perguntaObjetiva.setTipo(TipoPergunta.OBJETIVA);
    	perguntaObjetiva.setPeso(9);
    	
    	Resposta respostaObjetivaA = RespostaFactory.getEntity(1L);
    	respostaObjetivaA.setPeso(-1);
    	
    	Pergunta perguntaNota = PerguntaFactory.getEntity(2L);
    	perguntaNota.setTipo(TipoPergunta.NOTA);
    	perguntaNota.setPeso(2);
    	
    	ColaboradorResposta colaboradorRespostaObjetiva = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorRespostaObjetiva.setPergunta(perguntaObjetiva);
    	colaboradorRespostaObjetiva.setResposta(respostaObjetivaA);
    	
    	ColaboradorResposta colaboradorRespostaNota = ColaboradorRespostaFactory.getEntity(2L);
    	colaboradorRespostaNota.setValor(5);    	
    	colaboradorRespostaNota.setPergunta(perguntaNota);
    	
    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorRespostaObjetiva);
    	colaboradorRespostas.add(colaboradorRespostaNota);
    	
    	Integer pontuacaoMaxima = 5;
    	
    	avaliacaoManager.expects(once()).method("getPontuacaoMaximaDaPerformance").will(returnValue(pontuacaoMaxima));
    	colaboradorRespostaDao.expects(once()).method("findByColaboradorQuestionario").will(returnValue(colaboradorRespostas));
    	colaboradorRespostaDao.expects(once()).method("removeByColaboradorQuestionario").isVoid();
    	colaboradorQuestionarioManager.expects(once()).method("update").with(eq(colaboradorQuestionario));
    	
    	colaboradorRespostaManager.update(new ArrayList<ColaboradorResposta>(), colaboradorQuestionario, 1L, null, null);
    	
    	assertEquals("20%", colaboradorQuestionario.getPerformanceFormatada());
    }
    
    public void testSavePerformanceDaAvaliacaoExperienciaPesoNulo()
    {
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
    	
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(22L);
    	colaboradorQuestionario.setAvaliacao(avaliacao);
    	
    	
    	Pergunta perguntaObjetiva = PerguntaFactory.getEntity(1L);
    	perguntaObjetiva.setTipo(TipoPergunta.OBJETIVA);
    	perguntaObjetiva.setPeso(2);
    	
    	Resposta respostaObjetivaA = RespostaFactory.getEntity(1L);
    	respostaObjetivaA.setPeso(1);

      	ColaboradorResposta colaboradorRespostaObjetiva = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorRespostaObjetiva.setPergunta(perguntaObjetiva);
    	colaboradorRespostaObjetiva.setResposta(respostaObjetivaA);

    	
    	Pergunta perguntaObjetiva2 = PerguntaFactory.getEntity(1L);
    	perguntaObjetiva2.setTipo(TipoPergunta.OBJETIVA);
    	perguntaObjetiva2.setPeso(2);
    	
    	Resposta respostaObjetivaA2 = RespostaFactory.getEntity(1L);

      	ColaboradorResposta colaboradorRespostaObjetiva2 = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorRespostaObjetiva2.setPergunta(perguntaObjetiva2);
    	colaboradorRespostaObjetiva2.setResposta(respostaObjetivaA2);
    	
    	
    	Pergunta perguntaNota = PerguntaFactory.getEntity(2L);
    	perguntaNota.setTipo(TipoPergunta.NOTA);
    	perguntaNota.setPeso(2);
    	
    	ColaboradorResposta colaboradorRespostaNota = ColaboradorRespostaFactory.getEntity(2L);
    	colaboradorRespostaNota.setValor(5);    	
    	colaboradorRespostaNota.setPergunta(perguntaNota);
    	
    	
    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorRespostaObjetiva);
    	colaboradorRespostas.add(colaboradorRespostaNota);
    	
    	Integer pontuacaoMaxima = 22;
    	
    	avaliacaoManager.expects(once()).method("getPontuacaoMaximaDaPerformance").will(returnValue(pontuacaoMaxima));
    	colaboradorRespostaDao.expects(once()).method("findByColaboradorQuestionario").will(returnValue(colaboradorRespostas));
    	colaboradorRespostaDao.expects(once()).method("removeByColaboradorQuestionario").isVoid();
    	colaboradorQuestionarioManager.expects(once()).method("update").with(eq(colaboradorQuestionario));
    	
    	colaboradorRespostaManager.update(new ArrayList<ColaboradorResposta>(), colaboradorQuestionario, 1L, null, null);
    	
    	assertEquals("54,55%", colaboradorQuestionario.getPerformanceFormatada());
    }
    
    public void testFindByAvaliadoAndAvaliacaoDesempenho()
	{
    	boolean desconsiderarAutoAvaliacao = false;
    	
    	colaboradorRespostaDao.expects(once()).method("findByAvaliadoAndAvaliacaoDesempenho").will(returnValue(new ArrayList<ColaboradorResposta>()));
    	assertNotNull(colaboradorRespostaManager.findByAvaliadoAndAvaliacaoDesempenho(1L, 10L, desconsiderarAutoAvaliacao));
	}
}
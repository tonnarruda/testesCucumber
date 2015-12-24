package com.fortes.rh.test.business.avaliacao;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManagerImpl;
import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.ParticipanteAvaliacaoDesempenhoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.util.DateUtil;

public class AvaliacaoDesempenhoManagerTest extends MockObjectTestCase
{
	private AvaliacaoDesempenhoManagerImpl avaliacaoDesempenhoManager = new AvaliacaoDesempenhoManagerImpl();
	private Mock avaliacaoDesempenhoDao;
	private Mock colaboradorQuestionarioManager;
	private Mock perguntaManager;
	private Mock respostaManager;
	private Mock colaboradorRespostaManager;
	private Mock questionarioManager;
	private Mock gerenciadorComunicacaoManager;
	private Mock configuracaoCompetenciaAvaliacaoDesempenhoManager;
	private Mock configuracaoNivelCompetenciaManager;
	private Mock colaboradorManager;
	private Mock nivelCompetenciaManager;
	private Mock participanteAvaliacaoDesempenhoManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        avaliacaoDesempenhoDao = new Mock(AvaliacaoDesempenhoDao.class);
        avaliacaoDesempenhoManager.setDao((AvaliacaoDesempenhoDao) avaliacaoDesempenhoDao.proxy());
        colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
        avaliacaoDesempenhoManager.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());
        perguntaManager = mock(PerguntaManager.class);
        avaliacaoDesempenhoManager.setPerguntaManager((PerguntaManager) perguntaManager.proxy());
        respostaManager = mock(RespostaManager.class);
        avaliacaoDesempenhoManager.setRespostaManager((RespostaManager) respostaManager.proxy());
        colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
        avaliacaoDesempenhoManager.setColaboradorRespostaManager((ColaboradorRespostaManager) colaboradorRespostaManager.proxy());
        questionarioManager = mock(QuestionarioManager.class);
        avaliacaoDesempenhoManager.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());
        gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
        avaliacaoDesempenhoManager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());
        configuracaoCompetenciaAvaliacaoDesempenhoManager = mock(ConfiguracaoCompetenciaAvaliacaoDesempenhoManager.class);
        avaliacaoDesempenhoManager.setConfiguracaoCompetenciaAvaliacaoDesempenhoManager((ConfiguracaoCompetenciaAvaliacaoDesempenhoManager) configuracaoCompetenciaAvaliacaoDesempenhoManager.proxy());
        configuracaoNivelCompetenciaManager = mock(ConfiguracaoNivelCompetenciaManager.class);
        avaliacaoDesempenhoManager.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
        colaboradorManager = mock(ColaboradorManager.class);
        avaliacaoDesempenhoManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        nivelCompetenciaManager = mock(NivelCompetenciaManager.class);
        avaliacaoDesempenhoManager.setNivelCompetenciaManager((NivelCompetenciaManager) nivelCompetenciaManager.proxy());
        participanteAvaliacaoDesempenhoManager = mock(ParticipanteAvaliacaoDesempenhoManager.class);
        avaliacaoDesempenhoManager.setParticipanteAvaliacaoDesempenhoManager((ParticipanteAvaliacaoDesempenhoManager) participanteAvaliacaoDesempenhoManager.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = AvaliacaoDesempenhoFactory.getCollection(1L);

		avaliacaoDesempenhoDao.expects(once()).method("findAllSelect").with(eq(empresaId),ANYTHING,ANYTHING).will(returnValue(avaliacaoDesempenhos));
		assertEquals(avaliacaoDesempenhos, avaliacaoDesempenhoManager.findAllSelect(empresaId, null, null));
	}
	
	public void testClonar() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		avaliacao.setEmpresa(empresa);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		avaliacaoDesempenho.setLiberada(true);
		
		Collection<ColaboradorQuestionario> participantes = new ArrayList<ColaboradorQuestionario>();
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity(1L);
		participantes.add(colaboradorQuestionario1);
		
		avaliacaoDesempenhoDao.expects(once()).method("findByIdProjection").with(eq(3L)).will(returnValue(avaliacaoDesempenho));
		avaliacaoDesempenhoDao.expects(once()).method("save").with(ANYTHING);
		
		avaliacaoDesempenhoManager.clonar(3L, false, empresa.getId());
	}
	
	public void testGerarAutoAvaliacoes()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho.setTitulo("teste");
		avaliacaoDesempenho.setInicio(DateUtil.criarDataMesAno(11, 07, 2011));
		avaliacaoDesempenho.setFim(DateUtil.criarDataMesAno(15, 07, 2011));
		avaliacaoDesempenho.setAvaliacao(AvaliacaoFactory.getEntity(1L));
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		
		Collection<Colaborador> participantes = new ArrayList<Colaborador>();
		participantes.add(colaborador1);
		participantes.add(colaborador2);
		
		avaliacaoDesempenhoDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		colaboradorQuestionarioManager.expects(atLeastOnce()).method("save").with(ANYTHING);

		avaliacaoDesempenhoManager.gerarAutoAvaliacoes(avaliacaoDesempenho, participantes);
	}	
	
	public void testLiberar() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
		
		avaliacaoDesempenhoDao.expects(once()).method("liberarOrBloquear").with(eq(3L), eq(true));
		
		avaliacaoDesempenhoManager.liberarOrBloquear(avaliacaoDesempenho, true);
	}
	
	public void testBloquear() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
		
		colaboradorQuestionarioManager.expects(once()).method("findRespondidasByAvaliacaoDesempenho").with(eq(3L)).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		configuracaoCompetenciaAvaliacaoDesempenhoManager.expects(once()).method("existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado").with(eq(3L)).will(returnValue(false));
		avaliacaoDesempenhoDao.expects(once()).method("liberarOrBloquear").with(eq(3L), eq(false));
		
		avaliacaoDesempenhoManager.liberarOrBloquear(avaliacaoDesempenho, false);
	}
	
	public void testFindByIdProjection()
	{
		avaliacaoDesempenhoDao.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(AvaliacaoDesempenhoFactory.getEntity(1L)));
		assertNotNull(avaliacaoDesempenhoManager.findByIdProjection(1L));
	}
	
	public void testFindByAvaliador()
	{
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = AvaliacaoDesempenhoFactory.getCollection(1L);
		avaliacaoDesempenhoDao.expects(once()).method("findByAvaliador").with(eq(1L),eq(true),ANYTHING).will(returnValue(avaliacaoDesempenhos));
		assertNotNull(avaliacaoDesempenhoManager.findByAvaliador(1L, true, null));
	}

	public void testEnviarLembrete()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		gerenciadorComunicacaoManager.expects(once()).method("enviarLembreteAvaliacaoDesempenho").with(ANYTHING,ANYTHING);
		
		avaliacaoDesempenhoManager.enviarLembrete(1L, empresa);
	}
	
	public void testMontaResultado() throws Exception
	{
		Collection<Long> avaliadosIds = Arrays.asList(1L,2L);
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
		avaliacaoDesempenho.setAvaliacao(AvaliacaoFactory.getEntity(1L));
		boolean agruparPorAspectos = true;
		boolean desconsiderarAutoAvaliacao = false;
		
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(PerguntaFactory.getEntity(1L));
		perguntas.add(PerguntaFactory.getEntity(2L));
		Collection<Resposta> respostas = new ArrayList<Resposta>();
		
		Collection<ColaboradorResposta> colaboradorRespostas = Arrays.asList(ColaboradorRespostaFactory.getEntity(1L));
		
		Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>(); 
		Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostasMultiplas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>();
		
		Collection<ResultadoAvaliacaoDesempenho> resultadoQuestionarios = new ArrayList<ResultadoAvaliacaoDesempenho>();
		ResultadoAvaliacaoDesempenho resultadoAvaliacaoDesempenho = new ResultadoAvaliacaoDesempenho(2L, "José", 1.0);
		resultadoQuestionarios.add(resultadoAvaliacaoDesempenho);
		
		perguntaManager.expects(atLeastOnce()).method("findByQuestionarioAspectoPergunta").will(returnValue(perguntas));
		respostaManager.expects(atLeastOnce()).method("findInPerguntaIds").will(returnValue(respostas));
		
		colaboradorRespostaManager.expects(once()).method("findByAvaliadoAndAvaliacaoDesempenho").with(eq(1L),eq(3L), eq(false)).will(returnValue(new ArrayList<ColaboradorResposta>()));
		colaboradorRespostaManager.expects(once()).method("findByAvaliadoAndAvaliacaoDesempenho").with(eq(2L),eq(3L), eq(false)).will(returnValue(colaboradorRespostas));
		
		colaboradorQuestionarioManager.expects(atLeastOnce()).method("getQtdAvaliadores").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(3));
		perguntaManager.expects(atLeastOnce()).method("getPontuacoesMaximas").with(ANYTHING).will(returnValue(new HashMap<Long, Integer>()));
		
		colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostas").with(eq(2L),eq(3L),eq(false)).will(returnValue(percentuaisDeRespostas));
		colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostasMultipla").with(eq(2L),eq(3L),eq(false)).will(returnValue(percentuaisDeRespostasMultiplas));
		
		questionarioManager.expects(once()).method("montaResultadosAvaliacaoDesempenho").withAnyArguments().will(returnValue(resultadoQuestionarios));
		
		Collection<ResultadoAvaliacaoDesempenho> resultados = avaliacaoDesempenhoManager.montaResultado(avaliadosIds, avaliacaoDesempenho, agruparPorAspectos, desconsiderarAutoAvaliacao);
		assertEquals(1, resultados.size());
	}
	
	public void testMontaResultadoColecaoVaziaException() 
	{
		boolean desconsiderarAutoAvaliacao = false;
		
		Collection<Long> avaliadosIds = Arrays.asList(1L);
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
		avaliacaoDesempenho.setAvaliacao(AvaliacaoFactory.getEntity(1L));
		
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		Collection<Resposta> respostas = new ArrayList<Resposta>();
		Collection<ColaboradorResposta> colaboradorRespostas = Arrays.asList(ColaboradorRespostaFactory.getEntity(1L));
		Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>(); 
		Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostasMultiplas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>();
		
		perguntaManager.expects(atLeastOnce()).method("findByQuestionarioAspectoPergunta").will(returnValue(perguntas));
		respostaManager.expects(atLeastOnce()).method("findInPerguntaIds").will(returnValue(respostas));
		
		colaboradorRespostaManager.expects(once()).method("findByAvaliadoAndAvaliacaoDesempenho").with(eq(1L),eq(3L), eq(false)).will(returnValue(colaboradorRespostas));
		
		colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostas").with(eq(1L),eq(3L),eq(false)).will(returnValue(percentuaisDeRespostas));
		colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostasMultipla").with(eq(1L),eq(3L),eq(false)).will(returnValue(percentuaisDeRespostasMultiplas));
		
		colaboradorQuestionarioManager.expects(atLeastOnce()).method("getQtdAvaliadores").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(1));
		perguntaManager.expects(once()).method("getPontuacoesMaximas").with(ANYTHING).will(returnValue(new HashMap<Long, Integer>()));
		
		questionarioManager.expects(once()).method("montaResultadosAvaliacaoDesempenho").withAnyArguments().will(returnValue(new ArrayList<ResultadoAvaliacaoDesempenho>()));
		
		Collection<ResultadoAvaliacaoDesempenho> resultados = null;
		Exception exception = null;
		try
		{
			resultados = avaliacaoDesempenhoManager.montaResultado(avaliadosIds, avaliacaoDesempenho, false, desconsiderarAutoAvaliacao);
		} 
		catch (ColecaoVaziaException e) {
			exception = e;
		}
		
		assertTrue(exception instanceof ColecaoVaziaException);
		assertNull(resultados);
	}
	
	public void testGetResultadoAvaliacaoDesempenho()
	{
		Long competenciaId = 2L;
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(1L);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial cncf = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
		cncf.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		
		NivelCompetencia nivelCompetenciaColaborador1 = NivelCompetenciaFactory.getEntity(1L);
		nivelCompetenciaColaborador1.setOrdem(2);
		nivelCompetenciaColaborador1.setDescricao("Java");
		
		NivelCompetencia nivelCompetenciaColaborador2 = NivelCompetenciaFactory.getEntity(2L);
		nivelCompetenciaColaborador2.setOrdem(5);
		nivelCompetenciaColaborador2.setDescricao("Java");
				
		ConfiguracaoNivelCompetencia cncColab1 = ConfiguracaoNivelCompetenciaFactory.getEntity(1L);
		cncColab1.setCompetenciaId(competenciaId);
		cncColab1.setConfiguracaoNivelCompetenciaFaixaSalarial(cncf);
		cncColab1.setNivelCompetenciaColaborador(nivelCompetenciaColaborador1);
		cncColab1.setAvaliadorPeso(2);
		cncColab1.setAvaliadorId(1L);
		cncColab1.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		ConfiguracaoNivelCompetencia cncColab2 = ConfiguracaoNivelCompetenciaFactory.getEntity(2L);
		cncColab2.setCompetenciaId(competenciaId);
		cncColab2.setConfiguracaoNivelCompetenciaFaixaSalarial(cncf);
		cncColab2.setNivelCompetenciaColaborador(nivelCompetenciaColaborador2);
		cncColab2.setAvaliadorPeso(1);
		cncColab2.setAvaliadorId(2L);
		cncColab2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		ConfiguracaoNivelCompetencia cncFaixa = ConfiguracaoNivelCompetenciaFactory.getEntity(3L);
		cncFaixa.setCompetenciaId(competenciaId);
		cncFaixa.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		cncFaixa.setNivelCompetencia(nivelCompetenciaColaborador2);
		
		Collection<ConfiguracaoNivelCompetencia> configNiveisCompetenciasDoColaborador = new ArrayList<ConfiguracaoNivelCompetencia>();
		configNiveisCompetenciasDoColaborador.add(cncColab1);
		configNiveisCompetenciasDoColaborador.add(cncColab2);
		
		Collection<ConfiguracaoNivelCompetencia> configNiveisCompetenciasDaFaixa = new ArrayList<ConfiguracaoNivelCompetencia>();
		configNiveisCompetenciasDaFaixa.add(cncFaixa);
		
		configuracaoNivelCompetenciaManager.expects(once()).method("findCompetenciasAndPesos").withAnyArguments().will(returnValue(configNiveisCompetenciasDoColaborador));
		configuracaoNivelCompetenciaManager.expects(once()).method("findByConfiguracaoNivelCompetenciaFaixaSalarial").withAnyArguments().will(returnValue(configNiveisCompetenciasDaFaixa));
		colaboradorManager.expects(once()).method("findByIdHistoricoAtual").withAnyArguments().will(returnValue(ColaboradorFactory.getEntity(1L)));
		nivelCompetenciaManager.expects(once()).method("getOrdemMaximaByNivelCompetenciaHistoricoId").withAnyArguments().will(returnValue(5.0));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findByAvalDesempenhoIdAbadColaboradorId").withAnyArguments().will(returnValue(5.0));
		
		ResultadoAvaliacaoDesempenho resultado = avaliacaoDesempenhoManager.getResultadoAvaliacaoDesempenho(1L,2L);
		assertEquals(1, resultado.getCompetencias().size());
		assertEquals(100.0, resultado.getPerformanceAutoAvaliacao());
		assertEquals(50.0, resultado.getProdutividade());
		
		Competencia competencia = ((Competencia) resultado.getCompetencias().toArray()[0]);
		
		assertEquals(60.0, competencia.getPerformance());
	}
}
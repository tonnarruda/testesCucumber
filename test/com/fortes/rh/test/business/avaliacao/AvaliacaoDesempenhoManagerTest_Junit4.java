package com.fortes.rh.test.business.avaliacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManagerImpl;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.ParticipanteAvaliacaoDesempenhoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaCriterioManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
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
import com.fortes.rh.exception.AvaliacaoRespondidaException;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoParticipanteAvaliacao;
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

public class AvaliacaoDesempenhoManagerTest_Junit4
{
	private AvaliacaoDesempenhoManagerImpl avaliacaoDesempenhoManager = new AvaliacaoDesempenhoManagerImpl();
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private AvaliacaoManager avaliacaoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private PerguntaManager perguntaManager;
	private RespostaManager respostaManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private QuestionarioManager questionarioManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private ColaboradorManager colaboradorManager;
	private NivelCompetenciaManager nivelCompetenciaManager;
	private ParticipanteAvaliacaoDesempenhoManager participanteAvaliacaoDesempenhoManager;
	private ConfiguracaoNivelCompetenciaCriterioManager configuracaoNivelCompetenciaCriterioManager;
	private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;
	
	@Before
	public void setUp() throws Exception
    {
        avaliacaoDesempenhoDao = mock(AvaliacaoDesempenhoDao.class);
        avaliacaoDesempenhoManager.setDao(avaliacaoDesempenhoDao);
        
        avaliacaoManager = mock(AvaliacaoManager.class);
        avaliacaoDesempenhoManager.setAvaliacaoManager(avaliacaoManager);
        
        colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
        avaliacaoDesempenhoManager.setColaboradorQuestionarioManager(colaboradorQuestionarioManager);

        perguntaManager = mock(PerguntaManager.class);
        avaliacaoDesempenhoManager.setPerguntaManager(perguntaManager);
        
        respostaManager = mock(RespostaManager.class);
        avaliacaoDesempenhoManager.setRespostaManager(respostaManager);
        
        colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
        avaliacaoDesempenhoManager.setColaboradorRespostaManager(colaboradorRespostaManager);
        
        questionarioManager = mock(QuestionarioManager.class);
        avaliacaoDesempenhoManager.setQuestionarioManager((QuestionarioManager) questionarioManager);
        
        gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
        avaliacaoDesempenhoManager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager);
        
        configuracaoNivelCompetenciaManager = mock(ConfiguracaoNivelCompetenciaManager.class);
        avaliacaoDesempenhoManager.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager);
        
        colaboradorManager = mock(ColaboradorManager.class);
        avaliacaoDesempenhoManager.setColaboradorManager((ColaboradorManager) colaboradorManager);
        
        nivelCompetenciaManager = mock(NivelCompetenciaManager.class);
        avaliacaoDesempenhoManager.setNivelCompetenciaManager((NivelCompetenciaManager) nivelCompetenciaManager);
        
        participanteAvaliacaoDesempenhoManager = mock(ParticipanteAvaliacaoDesempenhoManager.class);
        avaliacaoDesempenhoManager.setParticipanteAvaliacaoDesempenhoManager((ParticipanteAvaliacaoDesempenhoManager) participanteAvaliacaoDesempenhoManager);
        
        configuracaoNivelCompetenciaCriterioManager = mock(ConfiguracaoNivelCompetenciaCriterioManager.class);
        avaliacaoDesempenhoManager.setConfiguracaoNivelCompetenciaCriterioManager((ConfiguracaoNivelCompetenciaCriterioManager) configuracaoNivelCompetenciaCriterioManager);
        
        configuracaoNivelCompetenciaFaixaSalarialManager = mock(ConfiguracaoNivelCompetenciaFaixaSalarialManager.class);
        avaliacaoDesempenhoManager.setConfiguracaoNivelCompetenciaFaixaSalarialManager((ConfiguracaoNivelCompetenciaFaixaSalarialManager) configuracaoNivelCompetenciaFaixaSalarialManager);
    }

	@Test
	public void testFindAllSelect()
	{ 
		Long empresaId = 1L;
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = AvaliacaoDesempenhoFactory.getCollection(1L);
		when(avaliacaoDesempenhoDao.findAllSelect(eq(empresaId), eq(true), eq(TipoModeloAvaliacao.DESEMPENHO))).thenReturn(avaliacaoDesempenhos);
		assertEquals(avaliacaoDesempenhos, avaliacaoDesempenhoManager.findAllSelect(empresaId, true, TipoModeloAvaliacao.DESEMPENHO));
	}

	@Test
	public void testClonarParaMesmaEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L, empresa);
		AvaliacaoDesempenho avaliacaoDesempenhoOrigem = AvaliacaoDesempenhoFactory.getEntity(3L, "Avaliação", true, avaliacao, empresa);
		AvaliacaoDesempenho avaliacaoDesempenhoDestino = AvaliacaoDesempenhoFactory.getEntity(4L, "Avaliação(Clone)", true, avaliacao, empresa);
		
		when(avaliacaoDesempenhoDao.findByIdProjection(avaliacaoDesempenhoOrigem.getId())).thenReturn(avaliacaoDesempenhoOrigem);
		when(avaliacaoDesempenhoDao.save(avaliacaoDesempenhoOrigem)).thenReturn(avaliacaoDesempenhoDestino);
		
		Exception exception = null;
		try {
			avaliacaoDesempenhoManager.clonar(avaliacaoDesempenhoOrigem.getId(), false, empresa.getId());
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	@Test
	public void testClonarParaEmpresaDiferente()
	{
		Empresa empresaOrigem = EmpresaFactory.getEmpresa(1L);
		Empresa empresaDestino = EmpresaFactory.getEmpresa(2L);
		
		Avaliacao avaliacaoOrigem = AvaliacaoFactory.getEntity(1L, empresaOrigem);
		Avaliacao avaliacaoDestino = AvaliacaoFactory.getEntity(2L, empresaDestino);
		
		AvaliacaoDesempenho avaliacaoDesempenhoOrigem = AvaliacaoDesempenhoFactory.getEntity(3L, "Avaliação", true, avaliacaoOrigem, empresaOrigem);
		AvaliacaoDesempenho avaliacaoDesempenhoDestino = AvaliacaoDesempenhoFactory.getEntity(4L, "Avaliação(Clone)", true, avaliacaoDestino, empresaDestino);
		
		Collection<ColaboradorQuestionario> participantes = new ArrayList<ColaboradorQuestionario>();
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity(1L);
		participantes.add(colaboradorQuestionario1);
		
		when(avaliacaoDesempenhoDao.findByIdProjection(avaliacaoDesempenhoOrigem.getId())).thenReturn(avaliacaoDesempenhoOrigem);
		when(avaliacaoManager.findById(avaliacaoOrigem.getId())).thenReturn(avaliacaoOrigem);
		when(avaliacaoManager.save(avaliacaoDestino)).thenReturn(avaliacaoDestino);
		when(avaliacaoDesempenhoDao.save(avaliacaoDesempenhoOrigem)).thenReturn(avaliacaoDesempenhoDestino);
		
		Exception exception = null;
		try {
			avaliacaoDesempenhoManager.clonar(avaliacaoDesempenhoOrigem.getId(), true, empresaDestino.getId());
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	@Test
	public void testFindByIdProjection()
	{
		when(avaliacaoDesempenhoDao.findByIdProjection(eq(1L))).thenReturn(AvaliacaoDesempenhoFactory.getEntity(1L));
		assertNotNull(avaliacaoDesempenhoManager.findByIdProjection(1L));
	}
	@Test
	public void testLiberar() 
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
		Exception exception = null;
		try {
			avaliacaoDesempenhoManager.liberarOrBloquear(avaliacaoDesempenho, false);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	@Test(expected=Exception.class)
	public void testLiberarException() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
		doThrow(Exception.class).when(avaliacaoDesempenhoDao).liberarOrBloquear(3L, true);
		avaliacaoDesempenhoManager.liberarOrBloquear(avaliacaoDesempenho, true);
	}
	
	@Test
	public void testRemover()
	{
		Long avaliacaoDesempenhoId = 1L;
		when(colaboradorQuestionarioManager.findRespondidasByAvaliacaoDesempenho(eq(avaliacaoDesempenhoId))).thenReturn(new ArrayList<ColaboradorQuestionario>());
		Exception exception = null;
		try {
			avaliacaoDesempenhoManager.remover(avaliacaoDesempenhoId);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testRemoverException() throws Exception 
	{
		Long avaliacaoDesempenhoId = 1L;
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = Arrays.asList(ColaboradorQuestionarioFactory.getEntity()); 
				
		when(colaboradorQuestionarioManager.findRespondidasByAvaliacaoDesempenho(eq(avaliacaoDesempenhoId))).thenReturn(colaboradorQuestionarios);
		thrown.expect(AvaliacaoRespondidaException.class);
	    thrown.expectMessage("Não foi possível excluir, pois já existem respostas para essa avaliação");
		
		avaliacaoDesempenhoManager.remover(avaliacaoDesempenhoId);
	}
	
	@Test
	public void testFindByAvaliador()
	{
		Long[] empresasIds = {1L, 2L};
		
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = AvaliacaoDesempenhoFactory.getCollection(1L);
		when(avaliacaoDesempenhoDao.findByAvaliador(eq(1L),eq(true),eq(empresasIds))).thenReturn(avaliacaoDesempenhos);
		assertNotNull(avaliacaoDesempenhoManager.findByAvaliador(1L, true, empresasIds));
	}

	@Test
	public void testMontaResultado() throws Exception
	{
		Collection<Long> avaliadosIds = Arrays.asList(2L);
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
		avaliacaoDesempenho.setAvaliacao(AvaliacaoFactory.getEntity(1L));
		boolean agruparPorAspectos = true;
		boolean desconsiderarAutoAvaliacao = false;
		Map<Long, Integer> pontuacoesMaximasPerguntas = new HashMap<Long, Integer>();
		Long avaliadorId = 5L;
		
		Long[] perguntasIds = {1L, 2L};
		Collection<Pergunta> perguntas = Arrays.asList(PerguntaFactory.getEntity(1L), PerguntaFactory.getEntity(2L));
		Collection<Resposta> respostas = new ArrayList<Resposta>();
		
		Collection<ColaboradorResposta> colaboradorRespostas = Arrays.asList(ColaboradorRespostaFactory.getEntity(1L));
		
		Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>(); 
		Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostasMultiplas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>();
		
		Collection<ResultadoAvaliacaoDesempenho> resultadoQuestionarios = new ArrayList<ResultadoAvaliacaoDesempenho>();
		ResultadoAvaliacaoDesempenho resultadoAvaliacaoDesempenho = new ResultadoAvaliacaoDesempenho(2L, "José", 1.0);
		resultadoQuestionarios.add(resultadoAvaliacaoDesempenho);
		
		when(colaboradorQuestionarioManager.getQtdAvaliadores(avaliacaoDesempenho.getId(), avaliadorId, desconsiderarAutoAvaliacao)).thenReturn(3);
		when(perguntaManager.findByQuestionarioAspectoPergunta(1L, null, null, agruparPorAspectos)).thenReturn(perguntas);
		when(respostaManager.findInPerguntaIds(perguntasIds)).thenReturn(respostas);
		when(colaboradorRespostaManager.findByAvaliadoAndAvaliacaoDesempenho(eq(2L),eq(3L), eq(false))).thenReturn(colaboradorRespostas);
		when(perguntaManager.getPontuacoesMaximas(perguntasIds)).thenReturn(pontuacoesMaximasPerguntas);
		
		when(colaboradorRespostaManager.calculaPercentualRespostas(eq(2L),eq(3L),eq(false))).thenReturn(percentuaisDeRespostas);
		when(colaboradorRespostaManager.calculaPercentualRespostasMultipla(eq(2L),eq(3L),eq(false))).thenReturn(percentuaisDeRespostasMultiplas);
		
		when(questionarioManager.montaResultadosAvaliacaoDesempenho(eq(perguntas), eq(pontuacoesMaximasPerguntas), eq(respostas), eq(2L), eq(colaboradorRespostas), eq(percentuaisDeRespostasMultiplas), 
				eq(avaliacaoDesempenho), anyInt(), eq(desconsiderarAutoAvaliacao), anyBoolean())).thenReturn(resultadoQuestionarios);
		Collection<ResultadoAvaliacaoDesempenho> resultados = avaliacaoDesempenhoManager.montaResultado(avaliadosIds, avaliacaoDesempenho, agruparPorAspectos, desconsiderarAutoAvaliacao);
		assertEquals(1, resultados.size());
	}
	
	@Test
	public void testMontaResultadoException() throws Exception
	{
		Collection<Long> avaliadosIds = Arrays.asList(2L);
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(3L);
		avaliacaoDesempenho.setAvaliacao(AvaliacaoFactory.getEntity(1L));
		boolean agruparPorAspectos = true;
		boolean desconsiderarAutoAvaliacao = false;
		Map<Long, Integer> pontuacoesMaximasPerguntas = new HashMap<Long, Integer>();
		Collection<Pergunta> perguntas = Arrays.asList(PerguntaFactory.getEntity(1L), PerguntaFactory.getEntity(2L));
		Collection<Resposta> respostas = new ArrayList<Resposta>();
		
		Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostasMultiplas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>();
		
		Collection<ResultadoAvaliacaoDesempenho> resultadoQuestionarios = new ArrayList<ResultadoAvaliacaoDesempenho>();
		ResultadoAvaliacaoDesempenho resultadoAvaliacaoDesempenho = new ResultadoAvaliacaoDesempenho(2L, "José", 1.0);
		resultadoQuestionarios.add(resultadoAvaliacaoDesempenho);
		
		when(questionarioManager.montaResultadosAvaliacaoDesempenho(eq(perguntas), eq(pontuacoesMaximasPerguntas), eq(respostas), eq(2L), anyCollectionOf(ColaboradorResposta.class), eq(percentuaisDeRespostasMultiplas), 
				eq(avaliacaoDesempenho), anyInt(), eq(desconsiderarAutoAvaliacao), anyBoolean())).thenReturn(new ArrayList<ResultadoAvaliacaoDesempenho>());
		
		thrown.expect(ColecaoVaziaException.class);
	    thrown.expectMessage("Nenhuma avaliação foi respondida para os colaboradores informados.");
	    avaliacaoDesempenhoManager.montaResultado(avaliadosIds, avaliacaoDesempenho, agruparPorAspectos, desconsiderarAutoAvaliacao);
	}
	
	@Test
	public void testEnviarLembrete()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Long avaliacaoDesempenhoId = 1L;
		Exception exception = null;
		try {
			avaliacaoDesempenhoManager.enviarLembrete(avaliacaoDesempenhoId, empresa);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	@Test
	public void testEnviarLembreteAoLiberar()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Long avaliacaoDesempenhoId = 1L;
		Exception exception = null;
		try {
			avaliacaoDesempenhoManager.enviarLembreteAoLiberar(avaliacaoDesempenhoId, empresa);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	@Test
	public void testFindIdsAvaliacaoDesempenho() 
	{
		Long avaliacaoId = 1L;
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = Arrays.asList(AvaliacaoDesempenhoFactory.getEntity(), AvaliacaoDesempenhoFactory.getEntity());
		when(avaliacaoDesempenhoDao.findIdsAvaliacaoDesempenho(avaliacaoId)).thenReturn(avaliacaoDesempenhos);
		assertEquals(avaliacaoDesempenhos, avaliacaoDesempenhoManager.findIdsAvaliacaoDesempenho(avaliacaoId));
	}
	
	@Test
	public void findCountTituloModeloAvaliacao() 
	{
		when(avaliacaoDesempenhoDao.findCountTituloModeloAvaliacao(anyInt(), eq(15), any(Date.class), any(Date.class), anyLong(), anyString(), anyLong(), anyBoolean())).thenReturn(2);
		assertEquals(new Integer(2), avaliacaoDesempenhoManager.findCountTituloModeloAvaliacao(1, 15, null, null, null, null, null, null));
	}

	@Test
	public void testFindTituloModeloAvaliacao() 
	{
		Collection<AvaliacaoDesempenho> avaliacoesDesempenho = Arrays.asList(AvaliacaoDesempenhoFactory.getEntity());  
		when(avaliacaoDesempenhoDao.findTituloModeloAvaliacao(anyInt(), eq(15), any(Date.class), any(Date.class), anyLong(), anyString(), anyLong(), anyBoolean())).thenReturn(avaliacoesDesempenho);
		assertEquals(avaliacoesDesempenho, avaliacaoDesempenhoManager.findTituloModeloAvaliacao(0, 15, null, null, null, null, null, null));
	}

	@Test
	public void testPopulaCheckBox()
	{
		Long empresaId = 2L;
		boolean ativa = true;
		
		Collection<AvaliacaoDesempenho> avaliacoesDesempenho = Arrays.asList(AvaliacaoDesempenhoFactory.getEntity()); 
		when(avaliacaoDesempenhoDao.findAllSelect(empresaId, ativa, null)).thenReturn(avaliacoesDesempenho);

		Exception exception = null;
		try {
			avaliacaoDesempenhoManager.populaCheckBox(empresaId, ativa);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}

	@Test
	public void testLiberarEmLoteException() throws Exception 
	{
		String [] avaliacoesCheck = {"2"};
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity("Avaliação de Desempenho");
		when(participanteAvaliacaoDesempenhoManager.findColaboradoresParticipantes(2L, TipoParticipanteAvaliacao.AVALIADO)).thenReturn(new ArrayList<Colaborador>());
		when(avaliacaoDesempenhoDao.findByIdProjection(2L)).thenReturn(avaliacaoDesempenho);
		thrown.expect(FortesException.class);
	    thrown.expectMessage("- " + avaliacaoDesempenho.getTitulo() + "<br /> ");
	    
	    avaliacaoDesempenhoManager.liberarEmLote(avaliacoesCheck);
	}
	
	@Test
	public void testLiberarEmLote() throws Exception 
	{
		String [] avaliacoesCheck = {"2"};
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity("Avaliação de Desempenho");

		when(participanteAvaliacaoDesempenhoManager.findColaboradoresParticipantes(2L, TipoParticipanteAvaliacao.AVALIADO)).thenReturn(Arrays.asList(ColaboradorFactory.getEntity()));
		when(participanteAvaliacaoDesempenhoManager.findColaboradoresParticipantes(2L, TipoParticipanteAvaliacao.AVALIADOR)).thenReturn(Arrays.asList(ColaboradorFactory.getEntity()));
		when(avaliacaoDesempenhoDao.findByIdProjection(2L)).thenReturn(avaliacaoDesempenho);
		
		Exception exception = null;
		try {
			avaliacaoDesempenhoManager.liberarEmLote(avaliacoesCheck);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	@Test
	public void testGetResultadoAvaliacaoDesempenho(){
		Long competenciaId = 2L;
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(1L);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial cncf = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
		cncf.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		
		NivelCompetencia nivelCompetenciaColaborador1 = NivelCompetenciaFactory.getEntity("Java", 2);
		nivelCompetenciaColaborador1.setId(1L);
		
		NivelCompetencia nivelCompetenciaColaborador2 = NivelCompetenciaFactory.getEntity("Java", 5);
		nivelCompetenciaColaborador2.setId(2L);
				
		ConfiguracaoNivelCompetencia cncColab1 = ConfiguracaoNivelCompetenciaFactory.getEntity(1L, nivelCompetenciaColaborador1, cncf, competenciaId, 1L, TipoCompetencia.CONHECIMENTO, 2);
		ConfiguracaoNivelCompetencia cncColab2 = ConfiguracaoNivelCompetenciaFactory.getEntity(2L, nivelCompetenciaColaborador2, cncf, competenciaId, 2L, TipoCompetencia.CONHECIMENTO, 1);
		
		ConfiguracaoNivelCompetencia cncFaixa = ConfiguracaoNivelCompetenciaFactory.getEntity(null, nivelCompetenciaColaborador2, competenciaId, TipoCompetencia.CONHECIMENTO);
		cncFaixa.setId(competenciaId);
		
		Collection<ConfiguracaoNivelCompetencia> configNiveisCompetenciasDoColaborador = Arrays.asList(cncColab1, cncColab2);
		Collection<ConfiguracaoNivelCompetencia> configNiveisCompetenciasDaFaixa = Arrays.asList(cncFaixa);
		
		ConfiguracaoNivelCompetenciaCriterio cncCriterio1 = new ConfiguracaoNivelCompetenciaCriterio();
		cncCriterio1.setNivelCompetencia(nivelCompetenciaColaborador1);
		
		ConfiguracaoNivelCompetenciaCriterio cncCriterio2 = new ConfiguracaoNivelCompetenciaCriterio();
		cncCriterio2.setNivelCompetencia(nivelCompetenciaColaborador2);
		
		Collection<ConfiguracaoNivelCompetenciaCriterio> cncCriterios = Arrays.asList(cncCriterio1, cncCriterio2);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho.setPermiteAutoAvaliacao(true);

		Long avaliadoId = 2L;
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		mockTestGetResultadoAvaliacaoDesempenho(nivelCompetenciaHistorico, cncf, cncColab1, cncColab2, configNiveisCompetenciasDoColaborador, configNiveisCompetenciasDaFaixa, cncCriterios, avaliacaoDesempenho, avaliadoId, empresa);
		
		ResultadoAvaliacaoDesempenho resultado = avaliacaoDesempenhoManager.getResultadoAvaliacaoDesempenho(avaliacaoDesempenho,avaliadoId, empresa.getId());
		assertEquals(1, resultado.getCompetencias().size());
		assertEquals(new Double(70.0), resultado.getPerformanceAutoAvaliacao());
		assertEquals(new Double(50.0), resultado.getProdutividade());
		assertEquals(new Double(70.0), ((Competencia) resultado.getCompetencias().toArray()[0]).getPerformance());
	}

	private void mockTestGetResultadoAvaliacaoDesempenho(NivelCompetenciaHistorico nivelCompetenciaHistorico, ConfiguracaoNivelCompetenciaFaixaSalarial cncf, ConfiguracaoNivelCompetencia cncColab1, ConfiguracaoNivelCompetencia cncColab2, Collection<ConfiguracaoNivelCompetencia> configNiveisCompetenciasDoColaborador, Collection<ConfiguracaoNivelCompetencia> configNiveisCompetenciasDaFaixa, Collection<ConfiguracaoNivelCompetenciaCriterio> cncCriterios, AvaliacaoDesempenho avaliacaoDesempenho, Long avaliadoId, Empresa empresa) {
		when(configuracaoNivelCompetenciaManager.findCompetenciasAndPesos(avaliacaoDesempenho.getId(), avaliadoId)).thenReturn(configNiveisCompetenciasDoColaborador);
		when(configuracaoNivelCompetenciaManager.findByConfiguracaoNivelCompetenciaFaixaSalarial(cncf.getId())).thenReturn(configNiveisCompetenciasDaFaixa);
		when(colaboradorManager.findByIdHistoricoAtual(avaliadoId, true)).thenReturn(ColaboradorFactory.getEntity(1L));
		when(nivelCompetenciaManager.getOrdemMaximaByNivelCompetenciaHistoricoId(nivelCompetenciaHistorico.getId())).thenReturn(5.0);
		when(participanteAvaliacaoDesempenhoManager.findByAvalDesempenhoIdAbadColaboradorId(avaliacaoDesempenho.getId(), avaliadoId, TipoParticipanteAvaliacao.AVALIADO)).thenReturn(5.0);
		when(configuracaoNivelCompetenciaCriterioManager.findByConfiguracaoNivelCompetencia(cncColab1.getId(), cncf.getId())).thenReturn(cncCriterios);
		when(configuracaoNivelCompetenciaCriterioManager.findByConfiguracaoNivelCompetencia(cncColab2.getId(), cncf.getId())).thenReturn(cncCriterios);
		when(nivelCompetenciaManager.findAllSelect(empresa.getId(), nivelCompetenciaHistorico.getId(), null)).thenReturn(NivelCompetenciaFactory.getCollection());
	}
}
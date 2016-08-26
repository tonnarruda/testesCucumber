package com.fortes.rh.test.business.avaliacao;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.AvaliacaoManagerImpl;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;

public class AvaliacaoManagerTest_JUnit4
{
	private AvaliacaoManagerImpl avaliacaoManager = new AvaliacaoManagerImpl();
	private AvaliacaoDao avaliacaoDao;
	private PerguntaManager perguntaManager;
	
	private RespostaManager respostaManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private QuestionarioManager questionarioManager;
	
	@Before
	public void setUp() throws Exception
    {
        avaliacaoDao = mock(AvaliacaoDao.class);
        perguntaManager = mock(PerguntaManager.class);
        respostaManager = mock(RespostaManager.class);
        questionarioManager = mock(QuestionarioManager.class);
        colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
        
        avaliacaoManager.setDao(avaliacaoDao);
        avaliacaoManager.setPerguntaManager(perguntaManager);
        avaliacaoManager.setRespostaManager(respostaManager);
        avaliacaoManager.setColaboradorRespostaManager(colaboradorRespostaManager);
        avaliacaoManager.setQuestionarioManager(questionarioManager);
    }

	@Test
	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		Collection<Avaliacao> avaliacaos = AvaliacaoFactory.getCollection(1L);
		when(avaliacaoDao.findAllSelect(null, null, empresaId, null, TipoModeloAvaliacao.DESEMPENHO, null)).thenReturn(avaliacaos);
		assertEquals(avaliacaos, avaliacaoManager.findAllSelect(null, null, empresaId, null, TipoModeloAvaliacao.DESEMPENHO, null));
	}
	
	@Test
	public void testGetCount()
	{
		Long empresaId = 1L;
		Boolean ativo = true;
		char modeloAvaliacao = TipoModeloAvaliacao.DESEMPENHO;
		Integer qtdAvaliacoes = 2;

		when(avaliacaoDao.getCount(empresaId, ativo, modeloAvaliacao, null)).thenReturn(qtdAvaliacoes);
		
		assertEquals(qtdAvaliacoes, avaliacaoManager.getCount(empresaId, ativo, modeloAvaliacao, null));
	}
	
	@Test
	public void testGetQuestionarioRelatorio() {
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		Collection<Pergunta> perguntas = Arrays.asList(PerguntaFactory.getEntity(1L)); 
		
		when(perguntaManager.getPerguntasRespostaByQuestionarioAgrupadosPorAspecto(eq(avaliacao.getId()), anyBoolean())).thenReturn(perguntas);
		QuestionarioRelatorio questionarioRelatorio = avaliacaoManager.getQuestionarioRelatorio(avaliacao, true);

		assertNotNull(questionarioRelatorio);
	}
	
	
	@Test
	public void testFindPeriodoExperienciaIsNull()
	{
		Long empresaId = 1L;
		Collection<Avaliacao> avaliacaos = AvaliacaoFactory.getCollection(1L);

		when(avaliacaoDao.findPeriodoExperienciaIsNull(eq(TipoModeloAvaliacao.DESEMPENHO), eq(empresaId))).thenReturn(avaliacaos);
		assertEquals(avaliacaos, avaliacaoManager.findPeriodoExperienciaIsNull(TipoModeloAvaliacao.DESEMPENHO, empresaId));
	}
}
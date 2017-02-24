package com.fortes.rh.test.business.avaliacao;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.ParticipanteAvaliacaoDesempenhoManagerImpl;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.avaliacao.ParticipanteAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoParticipanteAvaliacao;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.SpringUtil;

public class ParticipanteAvaliacaoDesempenhoManagerTest
{
	private ParticipanteAvaliacaoDesempenhoManagerImpl participanteAvaliacaoDesempenhoManager = new ParticipanteAvaliacaoDesempenhoManagerImpl();
	private ParticipanteAvaliacaoDesempenhoDao particapanteAvaliacaoDesempenhoDao;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;

	@Before
	public void setUp() throws Exception
    {
        particapanteAvaliacaoDesempenhoDao = mock(ParticipanteAvaliacaoDesempenhoDao.class);
        participanteAvaliacaoDesempenhoManager.setDao(particapanteAvaliacaoDesempenhoDao);
        
        colaboradorQuestionarioManager =  mock(ColaboradorQuestionarioManager.class);

        MockSpringUtilJUnit4.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
    }

	@Test
	public void testCloneAvaliados() {
		ParticipanteAvaliacaoDesempenho participante = criarParticipanteAvaliacaoDesempenho(new AvaliacaoDesempenho(), 1L, TipoParticipanteAvaliacao.AVALIADO);
		
		Collection<ParticipanteAvaliacaoDesempenho> participantes = new ArrayList<ParticipanteAvaliacaoDesempenho>();
		participantes.add(participante);
		
		Exception exception = null;
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		try {
			participanteAvaliacaoDesempenhoManager.clone(avaliacaoDesempenho, participantes);
		} catch (Exception e) {
			exception = e;
		}
		verify(particapanteAvaliacaoDesempenhoDao).save(any(ParticipanteAvaliacaoDesempenho.class));
		assertNull(exception);
	}
	
	@Test
	public void testSave(){
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho.setLiberada(false);
		
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenhoAvaliado = criarParticipanteAvaliacaoDesempenho(avaliacaoDesempenho, 1L, TipoParticipanteAvaliacao.AVALIADO);
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenhoAvaliador = criarParticipanteAvaliacaoDesempenho(avaliacaoDesempenho, 1L, TipoParticipanteAvaliacao.AVALIADOR);
		
		Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliados = Arrays.asList(participanteAvaliacaoDesempenhoAvaliado);
		Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliadores = Arrays.asList(participanteAvaliacaoDesempenhoAvaliador);
		
		Exception exception=null;
		try {
			participanteAvaliacaoDesempenhoManager.save(avaliacaoDesempenho, participantesAvaliados, participantesAvaliadores, new ArrayList<ColaboradorQuestionario>(), 
					new Long[]{1L, 1L, 2L, 2L}, new Long[]{3L, 3L}, new Long[]{4L, 4L});
		} catch (Exception e) {
			exception = e;
		}
		verify(particapanteAvaliacaoDesempenhoDao, times(1)).remove(new Long[]{3L});
		verify(particapanteAvaliacaoDesempenhoDao, times(1)).remove(new Long[]{4L});
		verify(colaboradorQuestionarioManager, times(1)).remove(new Long[]{1L,2L});
		assertNull(exception);
	}

	private ParticipanteAvaliacaoDesempenho criarParticipanteAvaliacaoDesempenho( AvaliacaoDesempenho avaliacaoDesempenho, Long colaboradorId, char tipoParticipanteAvaliacao ) {
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenhoAvaliador = new ParticipanteAvaliacaoDesempenho();
		participanteAvaliacaoDesempenhoAvaliador.setAvaliacaoDesempenho(avaliacaoDesempenho);
		participanteAvaliacaoDesempenhoAvaliador.setColaboradorId(colaboradorId);
		participanteAvaliacaoDesempenhoAvaliador.setTipo(tipoParticipanteAvaliacao);
		return participanteAvaliacaoDesempenhoAvaliador;
	}
}

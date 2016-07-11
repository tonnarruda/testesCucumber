package com.fortes.rh.test.business.avaliacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.ParticipanteAvaliacaoDesempenhoManagerImpl;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.avaliacao.ParticipanteAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoParticipanteAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;

public class ParticipanteAvaliacaoDesempenhoManagerTest extends MockObjectTestCase
{
	private ParticipanteAvaliacaoDesempenhoManagerImpl participanteAvaliacaoDesempenhoManager = new ParticipanteAvaliacaoDesempenhoManagerImpl();
	private Mock particapanteAvaliacaoDesempenhoDao;
	private Mock colaboradorQuestionarioManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        particapanteAvaliacaoDesempenhoDao = new Mock(ParticipanteAvaliacaoDesempenhoDao.class);
        participanteAvaliacaoDesempenhoManager.setDao((ParticipanteAvaliacaoDesempenhoDao) particapanteAvaliacaoDesempenhoDao.proxy());
        colaboradorQuestionarioManager =  new Mock(ColaboradorQuestionarioManager.class);
		MockSpringUtil.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

	public void testCloneAvaliados() {
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		ParticipanteAvaliacaoDesempenho participante = new ParticipanteAvaliacaoDesempenho();
		participante.setColaborador(colaborador);
		participante.setAvaliacaoDesempenho(new AvaliacaoDesempenho());
		participante.setTipo(TipoParticipanteAvaliacao.AVALIADO);
		
		Collection<ParticipanteAvaliacaoDesempenho> participantes = new ArrayList<ParticipanteAvaliacaoDesempenho>();
		participantes.add(participante);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		
		particapanteAvaliacaoDesempenhoDao.expects(once()).method("save").with(new Constraint[]{ANYTHING}).isVoid();
		participanteAvaliacaoDesempenhoManager.clone(avaliacaoDesempenho, participantes);
	}
	
	public void testSave(){
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho.setLiberada(false);
		
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenhoAvaliado = new ParticipanteAvaliacaoDesempenho();
		participanteAvaliacaoDesempenhoAvaliado.setAvaliacaoDesempenho(avaliacaoDesempenho);
		participanteAvaliacaoDesempenhoAvaliado.setColaboradorId(1L);
		participanteAvaliacaoDesempenhoAvaliado.setTipo(TipoParticipanteAvaliacao.AVALIADO);
		
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenhoAvaliador = new ParticipanteAvaliacaoDesempenho();
		participanteAvaliacaoDesempenhoAvaliador.setAvaliacaoDesempenho(avaliacaoDesempenho);
		participanteAvaliacaoDesempenhoAvaliador.setColaboradorId(1L);
		participanteAvaliacaoDesempenhoAvaliador.setTipo(TipoParticipanteAvaliacao.AVALIADOR);
		
		Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliados = Arrays.asList(participanteAvaliacaoDesempenhoAvaliado);
		Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliadores = Arrays.asList(participanteAvaliacaoDesempenhoAvaliador);
		
		particapanteAvaliacaoDesempenhoDao.expects(once()).method("remove").with(eq(new Long[]{})).isVoid();
		particapanteAvaliacaoDesempenhoDao.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();
		
		particapanteAvaliacaoDesempenhoDao.expects(once()).method("remove").with(eq(new Long[]{})).isVoid();
		colaboradorQuestionarioManager.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();
		colaboradorQuestionarioManager.expects(once()).method("remove").with(eq(new Long[]{})).isVoid();
		particapanteAvaliacaoDesempenhoDao.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();
		Exception exception=null;
		try {
			participanteAvaliacaoDesempenhoManager.save(avaliacaoDesempenho, participantesAvaliados, participantesAvaliadores, new ArrayList<ColaboradorQuestionario>(), new Long[]{}, new Long[]{}, new Long[]{});
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
}

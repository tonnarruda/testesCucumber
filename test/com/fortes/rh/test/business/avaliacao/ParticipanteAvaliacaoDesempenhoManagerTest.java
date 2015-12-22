package com.fortes.rh.test.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.ParticipanteAvaliacaoDesempenhoManagerImpl;
import com.fortes.rh.dao.avaliacao.ParticipanteAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoParticipanteAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

public class ParticipanteAvaliacaoDesempenhoManagerTest extends MockObjectTestCase
{
	private ParticipanteAvaliacaoDesempenhoManagerImpl participanteAvaliacaoDesempenhoManager = new ParticipanteAvaliacaoDesempenhoManagerImpl();
	private Mock particapanteAvaliacaoDesempenhoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        particapanteAvaliacaoDesempenhoDao = new Mock(ParticipanteAvaliacaoDesempenhoDao.class);
        participanteAvaliacaoDesempenhoManager.setDao((ParticipanteAvaliacaoDesempenhoDao) particapanteAvaliacaoDesempenhoDao.proxy());
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
		
		particapanteAvaliacaoDesempenhoDao.expects(once()).method("clone").with(new Constraint[]{ANYTHING}).isVoid();
		participanteAvaliacaoDesempenhoManager.clone(avaliacaoDesempenho, participantes);
	}
}

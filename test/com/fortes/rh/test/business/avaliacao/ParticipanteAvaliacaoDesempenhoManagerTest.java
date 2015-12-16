package com.fortes.rh.test.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.ParticipanteAvaliacaoDesempenhoManagerImpl;
import com.fortes.rh.dao.avaliacao.ParticipanteAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoParticipanteAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.util.LongUtil;

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

	public void testSaveAvaliados() {
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		
		particapanteAvaliacaoDesempenhoDao.expects(once()).method("findParticipantes").with(new Constraint[]{ eq(avaliacaoDesempenho.getId()), eq(TipoParticipanteAvaliacao.AVALIADO)}).will(returnValue(colaboradors));
		participanteAvaliacaoDesempenhoManager.save(avaliacaoDesempenho, LongUtil.collectionToArrayLong(colaboradors), null, TipoParticipanteAvaliacao.AVALIADO);
		
		particapanteAvaliacaoDesempenhoDao.expects(once()).method("findParticipantes").with(new Constraint[]{ eq(avaliacaoDesempenho.getId()), eq(TipoParticipanteAvaliacao.AVALIADO)}).will(returnValue(new ArrayList<Colaborador>()));
		particapanteAvaliacaoDesempenhoDao.expects(once()).method("save").with(new Constraint[]{ANYTHING}).isVoid();
		participanteAvaliacaoDesempenhoManager.save(avaliacaoDesempenho, LongUtil.collectionToArrayLong(colaboradors), null, TipoParticipanteAvaliacao.AVALIADO);
	}
}

package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.TurmaAvaliacaoTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.pesquisa.AvaliacaoTurmaDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.TurmaAvaliacaoTurma;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.AvaliacaoTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

public class TurmaAvaliacaoTurmaDaoHibernateTest extends GenericDaoHibernateTest<TurmaAvaliacaoTurma>
{
	private TurmaAvaliacaoTurmaDao turmaAvaliacaoTurmaDao;
	private TurmaDao turmaDao;
	private AvaliacaoTurmaDao avaliacaoTurmaDao;

	public GenericDao<TurmaAvaliacaoTurma> getGenericDao()
	{
		return turmaAvaliacaoTurmaDao;
	}

	@Override
	public TurmaAvaliacaoTurma getEntity() {
		return new TurmaAvaliacaoTurma();
	}
	
	public void testVerificaAvaliacaoliberada()
	{
		AvaliacaoTurma avaliacaoTurma = AvaliacaoTurmaFactory.getEntity();
		avaliacaoTurmaDao.save(avaliacaoTurma);
		
		Turma turma1 = TurmaFactory.getEntity();
		turmaDao.save(turma1);
		
		Turma turma2 = TurmaFactory.getEntity();
		turmaDao.save(turma2);
		
		TurmaAvaliacaoTurma turmaAvaliacaoTurma1 = new TurmaAvaliacaoTurma();
		turmaAvaliacaoTurma1.setTurma(turma1);
		turmaAvaliacaoTurma1.setLiberada(true);
		turmaAvaliacaoTurma1.setAvaliacaoTurma(avaliacaoTurma );
		turmaAvaliacaoTurmaDao.save(turmaAvaliacaoTurma1);
		
		TurmaAvaliacaoTurma turmaAvaliacaoTurma2 = new TurmaAvaliacaoTurma();
		turmaAvaliacaoTurma2.setTurma(turma2);
		turmaAvaliacaoTurma2.setLiberada(false);
		turmaAvaliacaoTurma2.setAvaliacaoTurma(avaliacaoTurma );
		turmaAvaliacaoTurmaDao.save(turmaAvaliacaoTurma2);
		
		assertFalse(turmaAvaliacaoTurmaDao.verificaAvaliacaoliberada(turma2.getId()));
	}
	
	public void testRemoveByTurma()
	{
		AvaliacaoTurma avaliacaoTurma = AvaliacaoTurmaFactory.getEntity();
		avaliacaoTurmaDao.save(avaliacaoTurma);
		
		Turma turma1 = TurmaFactory.getEntity();
		turmaDao.save(turma1);
		
		Turma turma2 = TurmaFactory.getEntity();
		turmaDao.save(turma2);
		
		TurmaAvaliacaoTurma turmaAvaliacaoTurma1 = new TurmaAvaliacaoTurma();
		turmaAvaliacaoTurma1.setTurma(turma1);
		turmaAvaliacaoTurma1.setLiberada(true);
		turmaAvaliacaoTurma1.setAvaliacaoTurma(avaliacaoTurma );
		turmaAvaliacaoTurmaDao.save(turmaAvaliacaoTurma1);
		
		TurmaAvaliacaoTurma turmaAvaliacaoTurma2 = new TurmaAvaliacaoTurma();
		turmaAvaliacaoTurma2.setTurma(turma2);
		turmaAvaliacaoTurma2.setLiberada(false);
		turmaAvaliacaoTurma2.setAvaliacaoTurma(avaliacaoTurma );
		turmaAvaliacaoTurmaDao.save(turmaAvaliacaoTurma2);
		
		Collection<TurmaAvaliacaoTurma> turmaAvaliacaoTurmas1 = turmaAvaliacaoTurmaDao.find(new String[]{"turma.id"},new Object[]{turma1.getId()});
		assertEquals("Turma 1", 0, turmaAvaliacaoTurmas1.size());
		
		Collection<TurmaAvaliacaoTurma> turmaAvaliacaoTurmas2 = turmaAvaliacaoTurmaDao.find(new String[]{"turma.id"},new Object[]{turma2.getId()});
		assertEquals("Turma 2", 1, turmaAvaliacaoTurmas2.size());
	}

	public void setTurmaAvaliacaoTurmaDao(TurmaAvaliacaoTurmaDao turmaAvaliacaoTurmaDao) {
		this.turmaAvaliacaoTurmaDao = turmaAvaliacaoTurmaDao;
	}
	
	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}

	public void setAvaliacaoTurmaDao(AvaliacaoTurmaDao avaliacaoTurmaDao) {
		this.avaliacaoTurmaDao = avaliacaoTurmaDao;
	}

}
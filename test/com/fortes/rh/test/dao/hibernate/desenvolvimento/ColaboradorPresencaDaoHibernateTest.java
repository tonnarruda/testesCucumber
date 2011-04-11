package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorPresencaFactory;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

public class ColaboradorPresencaDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorPresenca>
{
	private ColaboradorPresencaDao colaboradorPresencaDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private TurmaDao turmaDao;
	private ColaboradorDao colaboradorDao;
	private DiaTurmaDao diaTurmaDao;
	private AvaliacaoCursoDao avaliacaoCursoDao;
	private AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao;

	public ColaboradorPresenca getEntity()
	{
		ColaboradorPresenca colaboradorPresenca = new ColaboradorPresenca();
		colaboradorPresenca.setId(null);
		colaboradorPresenca.setPresenca(true);

		return colaboradorPresenca;
	}

	public void testExistPresencaByTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);

		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		colaboradorTurma.setTurma(turma);
		colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);

		Collection<ColaboradorPresenca> retornos = colaboradorPresencaDao.existPresencaByTurma(turma.getId());

		assertEquals(1, retornos.size());
	}
	
	public void testFindPresencaByTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = prepareColaboradorTurma(turma);
		
		DiaTurma diaTurma = new DiaTurma();
		diaTurma = diaTurmaDao.save(diaTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		Collection<ColaboradorPresenca> retornos = colaboradorPresencaDao.findPresencaByTurma(turma.getId());
		
		assertEquals(1, retornos.size());
	}
	
	public void testRemoveByColaboradorDiaTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = prepareColaboradorTurma(turma);
		
		DiaTurma diaTurma = new DiaTurma();
		diaTurma = diaTurmaDao.save(diaTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		colaboradorPresencaDao.remove(diaTurma.getId(), colaboradorTurma.getId());
		
		assertEquals(new Integer(0), colaboradorPresencaDao.getCount(new String[]{"id"}, new Object[]{colaboradorPresenca.getId()}));
		
	}
	
	public void testRemoveByDiaTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = prepareColaboradorTurma(turma);
		
		DiaTurma diaTurma = new DiaTurma();
		diaTurma = diaTurmaDao.save(diaTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		colaboradorPresencaDao.remove(diaTurma.getId(), null);
		
		assertEquals(new Integer(0), colaboradorPresencaDao.getCount(new String[]{"id"}, new Object[]{colaboradorPresenca.getId()}));
	}
	
	public void testRemoveByColaboradorTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = prepareColaboradorTurma(turma);
		
		DiaTurma diaTurma = new DiaTurma();
		diaTurma = diaTurmaDao.save(diaTurma);
		
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setColaboradorTurma(colaboradorTurma);
		colaboradorPresenca.setDiaTurma(diaTurma);
		colaboradorPresencaDao.save(colaboradorPresenca);
		
		Long[] colaboradorTurmaIds = new Long[]{colaboradorTurma.getId()};
		
		assertEquals(new Integer(1), colaboradorPresencaDao.getCount(new String[]{"id"}, new Object[]{colaboradorPresenca.getId()}));
		colaboradorPresencaDao.removeByColaboradorTurma(colaboradorTurmaIds);		
		assertEquals(new Integer(0), colaboradorPresencaDao.getCount(new String[]{"id"}, new Object[]{colaboradorPresenca.getId()}));
	}

	private ColaboradorTurma prepareColaboradorTurma(Turma turma)
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma);
		colaboradorTurma = colaboradorTurmaDao.save(colaboradorTurma);
		return colaboradorTurma;
	}

	public void testqtdDiaPresentesTurma() 
	{
		Turma turma = TurmaFactory.getEntity();
		turma.setRealizada(true);
		turmaDao.save(turma);
		
		DiaTurma diaTurma1 = DiaTurmaFactory.getEntity();
		diaTurma1.setTurma(turma);
		diaTurmaDao.save(diaTurma1);
		
		ColaboradorPresenca colaboradorPresenca1 = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca1.setDiaTurma(diaTurma1);
		colaboradorPresencaDao.save(colaboradorPresenca1);
		
		ColaboradorPresenca colaboradorPresenca2 = ColaboradorPresencaFactory.getEntity();
		colaboradorPresenca2.setDiaTurma(diaTurma1);
		colaboradorPresencaDao.save(colaboradorPresenca2);
		
		assertEquals(new Integer(2), colaboradorPresencaDao.qtdDiaPresentesTurma(turma.getId()));
	}
	
	public GenericDao<ColaboradorPresenca> getGenericDao()
	{
		return colaboradorPresencaDao;
	}

	public void setColaboradorTurmaDao(ColaboradorTurmaDao ColaboradorTurmaDao)
	{
		this.colaboradorTurmaDao = ColaboradorTurmaDao;
	}

	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}

	public void setColaboradorPresencaDao(ColaboradorPresencaDao colaboradorPresencaDao)
	{
		this.colaboradorPresencaDao = colaboradorPresencaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setDiaTurmaDao(DiaTurmaDao diaTurmaDao)
	{
		this.diaTurmaDao = diaTurmaDao;
	}

	public void setAvaliacaoCursoDao(AvaliacaoCursoDao avaliacaoCursoDao) {
		this.avaliacaoCursoDao = avaliacaoCursoDao;
	}

	public void setAproveitamentoAvaliacaoCursoDao(
			AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao) {
		this.aproveitamentoAvaliacaoCursoDao = aproveitamentoAvaliacaoCursoDao;
	}


}
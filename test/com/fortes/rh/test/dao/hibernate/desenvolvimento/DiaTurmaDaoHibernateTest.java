package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.util.DateUtil;

public class DiaTurmaDaoHibernateTest extends GenericDaoHibernateTest<DiaTurma>
{
	private DiaTurmaDao diaTurmaDao;
	private TurmaDao turmaDao;

	public DiaTurma getEntity()
	{
		DiaTurma diaTurma = new DiaTurma();

		diaTurma.setId(null);
		diaTurma.setDia(new Date());
		diaTurma.setTurma(null);

		return diaTurma;
	}
	public GenericDao<DiaTurma> getGenericDao()
	{
		return diaTurmaDao;
	}

	public void setDiaTurmaDao(DiaTurmaDao diaTurmaDao)
	{
		this.diaTurmaDao = diaTurmaDao;
	}
	
	public void testFindByTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		DiaTurma diaTurma1 = getEntity();
		diaTurma1.setTurma(turma);
		diaTurma1 = diaTurmaDao.save(diaTurma1);

		DiaTurma diaTurma2 = getEntity();
		diaTurma2.setTurma(turma);
		diaTurma2 = diaTurmaDao.save(diaTurma2);
		
		assertEquals(2, diaTurmaDao.findByTurma(turma.getId()).size());
	}
	
	public void testDeleteDiasTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		DiaTurma diaTurma1 = getEntity();
		diaTurma1.setTurma(turma);
		diaTurma1 = diaTurmaDao.save(diaTurma1);
		
		DiaTurma diaTurma2 = getEntity();
		diaTurma2.setTurma(turma);
		diaTurma2 = diaTurmaDao.save(diaTurma2);
		
		diaTurmaDao.deleteDiasTurma(turma.getId());
		assertEquals(0, diaTurmaDao.findByTurma(turma.getId()).size());
	}
	

	public void testdiasDasTurmas()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		DiaTurma diaTurma1 = getEntity();
		diaTurma1.setTurma(turma);
		diaTurmaDao.save(diaTurma1);
		
		DiaTurma diaTurma2 = getEntity();
		diaTurma2.setTurma(turma);
		diaTurmaDao.save(diaTurma2);
		
		assertEquals(new Integer(2), diaTurmaDao.qtdDiasDasTurmas(turma.getId()));
	}
	public void testFindByTurmaAndPeriodo()
	{
		Turma turma = TurmaFactory.getEntity();
		turma = turmaDao.save(turma);
		
		DiaTurma diaTurma1 = getEntity();
		diaTurma1.setTurma(turma);
		diaTurma1.setDia(DateUtil.criarDataMesAno(29, 11, 2014));
		diaTurmaDao.save(diaTurma1);
		
		DiaTurma diaTurma2 = getEntity();
		diaTurma2.setTurma(turma);
		diaTurma2.setDia(DateUtil.criarDataMesAno(5, 12, 2014));
		diaTurmaDao.save(diaTurma2);
		
		Collection<DiaTurma> diasTurmaretorno = diaTurmaDao.findByTurmaAndPeriodo(turma.getId(), DateUtil.criarDataMesAno(1, 12, 2014), DateUtil.criarDataMesAno(10, 12, 2014));
		
		assertEquals(1, diasTurmaretorno.size());
	}

	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}
	
}
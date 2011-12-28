package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.TipoDespesaDao;
import com.fortes.rh.dao.geral.TurmaTipoDespesaDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.TipoDespesaFactory;
import com.fortes.rh.test.factory.geral.TurmaTipoDespesaFactory;

public class TurmaTipoDespesaDaoHibernateTest extends GenericDaoHibernateTest<TurmaTipoDespesa>
{
	private TurmaTipoDespesaDao turmaTipoDespesaDao;
	private TurmaDao turmaDao;
	private TipoDespesaDao tipoDespesaDao;
	
	@Override
	public TurmaTipoDespesa getEntity()
	{
		return TurmaTipoDespesaFactory.getEntity();
	}

	@Override
	public GenericDao<TurmaTipoDespesa> getGenericDao()
	{
		return turmaTipoDespesaDao;
	}

	public void setTurmaTipoDespesaDao(TurmaTipoDespesaDao turmaTipoDespesaDao)
	{
		this.turmaTipoDespesaDao = turmaTipoDespesaDao;
	}
	
	public void testFindTipoDespesaTurma() 
	{
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);
		
		TipoDespesa tipoDespesa = TipoDespesaFactory.getEntity();
		tipoDespesaDao.save(tipoDespesa);
		
		TurmaTipoDespesa turmaTipoDespesa = TurmaTipoDespesaFactory.getEntity();
		turmaTipoDespesa.setTurma(turma);
		turmaTipoDespesa.setTipoDespesa(tipoDespesa);
		turmaTipoDespesaDao.save(turmaTipoDespesa);
		
		assertEquals(1, turmaTipoDespesaDao.findTipoDespesaTurma(turma.getId()).size());
	}

	public void testRemoveByTurma() 
	{
		Exception ex = null;
		try {
			turmaTipoDespesaDao.removeByTurma(1L);
		} catch (Exception e) {
			ex = e;
		}
		assertNull(ex);
	}
	
	public void testRemove() 
	{
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);
		
		TipoDespesa tipoDespesa = TipoDespesaFactory.getEntity();
		tipoDespesaDao.save(tipoDespesa);
		
		TurmaTipoDespesa turmaTipoDespesa = TurmaTipoDespesaFactory.getEntity();
		turmaTipoDespesa.setTurma(turma);
		turmaTipoDespesa.setTipoDespesa(tipoDespesa);
		turmaTipoDespesaDao.save(turmaTipoDespesa);
		
		turmaTipoDespesaDao.save(turmaTipoDespesa);
		TurmaTipoDespesa turmaTipoDespesaResult = (TurmaTipoDespesa) turmaTipoDespesaDao.findById(turmaTipoDespesa.getId());
		
		assertEquals(turma.getId(),turmaTipoDespesaResult.getTurma().getId());
		
		Exception ex = null;
		try {
			turmaTipoDespesaDao.remove(turmaTipoDespesaResult.getId());
		} catch (Exception e) {
			ex = e;
		}
		assertNull(ex);
	}
	
	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}

	public void setTipoDespesaDao(TipoDespesaDao tipoDespesaDao) {
		this.tipoDespesaDao = tipoDespesaDao;
	}
}

package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Arrays;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.dao.sesmt.RiscoFasePcmatDao;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.FasePcmatFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFasePcmatFactory;

public class RiscoFasePcmatDaoHibernateTest extends GenericDaoHibernateTest<RiscoFasePcmat>
{
	private RiscoFasePcmatDao riscoFasePcmatDao;
	private FasePcmatDao fasePcmatDao;
	private RiscoDao riscoDao;

	@Override
	public RiscoFasePcmat getEntity()
	{
		return RiscoFasePcmatFactory.getEntity();
	}

	@Override
	public GenericDao<RiscoFasePcmat> getGenericDao()
	{
		return riscoFasePcmatDao;
	}
	
	public void testFindByFasePcmat()
	{
		FasePcmat fasePcmat1 = FasePcmatFactory.getEntity();
		fasePcmatDao.save(fasePcmat1);

		FasePcmat fasePcmat2 = FasePcmatFactory.getEntity();
		fasePcmatDao.save(fasePcmat2);
		
		Risco risco1 = RiscoFactory.getEntity();
		riscoDao.save(risco1);
		
		Risco risco2 = RiscoFactory.getEntity();
		riscoDao.save(risco2);
		
		RiscoFasePcmat riscoFasePcmat1 = RiscoFasePcmatFactory.getEntity();
		riscoFasePcmat1.setRisco(risco1);
		riscoFasePcmat1.setFasePcmat(fasePcmat1);
		riscoFasePcmatDao.save(riscoFasePcmat1);
		
		RiscoFasePcmat riscoFasePcmat2 = RiscoFasePcmatFactory.getEntity();
		riscoFasePcmat2.setRisco(risco2);
		riscoFasePcmat2.setFasePcmat(fasePcmat1);
		riscoFasePcmatDao.save(riscoFasePcmat2);
		
		RiscoFasePcmat riscoFasePcmat3 = RiscoFasePcmatFactory.getEntity();
		riscoFasePcmat3.setRisco(risco2);
		riscoFasePcmat3.setFasePcmat(fasePcmat2);
		riscoFasePcmatDao.save(riscoFasePcmat3);
		
		assertEquals("FasePcmat 1", 2, riscoFasePcmatDao.findByFasePcmat(fasePcmat1.getId()).size());
		assertEquals("FasePcmat 2", 1, riscoFasePcmatDao.findByFasePcmat(fasePcmat2.getId()).size());
	}
	
	public void testRemoveByFasePcmatRisco()
	{
		FasePcmat fasePcmat1 = FasePcmatFactory.getEntity();
		fasePcmatDao.save(fasePcmat1);

		FasePcmat fasePcmat2 = FasePcmatFactory.getEntity();
		fasePcmatDao.save(fasePcmat2);
		
		Risco risco1 = RiscoFactory.getEntity();
		riscoDao.save(risco1);
		
		Risco risco2 = RiscoFactory.getEntity();
		riscoDao.save(risco2);
		
		RiscoFasePcmat riscoFasePcmat1 = RiscoFasePcmatFactory.getEntity();
		riscoFasePcmat1.setRisco(risco1);
		riscoFasePcmat1.setFasePcmat(fasePcmat1);
		riscoFasePcmatDao.save(riscoFasePcmat1);
		
		RiscoFasePcmat riscoFasePcmat2 = RiscoFasePcmatFactory.getEntity();
		riscoFasePcmat2.setRisco(risco2);
		riscoFasePcmat2.setFasePcmat(fasePcmat1);
		riscoFasePcmatDao.save(riscoFasePcmat2);
		
		RiscoFasePcmat riscoFasePcmat3 = RiscoFasePcmatFactory.getEntity();
		riscoFasePcmat3.setRisco(risco2);
		riscoFasePcmat3.setFasePcmat(fasePcmat2);
		riscoFasePcmatDao.save(riscoFasePcmat3);
		
		riscoFasePcmatDao.removeByFasePcmatRisco(fasePcmat1.getId(), Arrays.asList(risco2.getId()));
		
		assertEquals("FasePcmat 1", 1, riscoFasePcmatDao.findByFasePcmat(fasePcmat1.getId()).size());
		assertEquals("FasePcmat 2", 1, riscoFasePcmatDao.findByFasePcmat(fasePcmat2.getId()).size());
		
		riscoFasePcmatDao.removeByFasePcmatRisco(fasePcmat2.getId(), Arrays.asList(risco2.getId()));
		
		assertEquals("FasePcmat 1", 1, riscoFasePcmatDao.findByFasePcmat(fasePcmat1.getId()).size());
		assertEquals("FasePcmat 2", 0, riscoFasePcmatDao.findByFasePcmat(fasePcmat2.getId()).size());
	}

	public void setRiscoFasePcmatDao(RiscoFasePcmatDao riscoFasePcmatDao)
	{
		this.riscoFasePcmatDao = riscoFasePcmatDao;
	}

	public void setFasePcmatDao(FasePcmatDao fasePcmatDao) {
		this.fasePcmatDao = fasePcmatDao;
	}

	public void setRiscoDao(RiscoDao riscoDao) {
		this.riscoDao = riscoDao;
	}
}

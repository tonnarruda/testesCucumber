package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.EpiPcmatDao;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.EpiPcmatFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;

public class EpiPcmatDaoHibernateTest extends GenericDaoHibernateTest<EpiPcmat>
{
	private EpiPcmatDao epiPcmatDao;
	private EpiDao epiDao;
	private PcmatDao pcmatDao;

	@Override
	public EpiPcmat getEntity()
	{
		return EpiPcmatFactory.getEntity();
	}

	@Override
	public GenericDao<EpiPcmat> getGenericDao()
	{
		return epiPcmatDao;
	}

	public void testFindByPcmat()
	{
		Epi capacete = EpiFactory.getEntity();
		epiDao.save(capacete);

		Epi bota = EpiFactory.getEntity();
		epiDao.save(bota);
		
		Pcmat pcmat1 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat1);
		
		Pcmat pcmat2 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat2);
		
		EpiPcmat epiPcmat1 = new EpiPcmat();
		epiPcmat1.setEpi(capacete);
		epiPcmat1.setPcmat(pcmat1);
		epiPcmatDao.save(epiPcmat1);
		
		EpiPcmat epiPcmat2 = new EpiPcmat();
		epiPcmat2.setEpi(bota);
		epiPcmat2.setPcmat(pcmat1);
		epiPcmatDao.save(epiPcmat2);
		
		EpiPcmat epiPcmat3 = new EpiPcmat();
		epiPcmat3.setEpi(capacete);
		epiPcmat3.setPcmat(pcmat2);
		epiPcmatDao.save(epiPcmat3);
		
		assertEquals("Pcmat 1", 2, epiPcmatDao.findByPcmat(pcmat1.getId()).size());
		assertEquals("Pcmat 2", 1, epiPcmatDao.findByPcmat(pcmat2.getId()).size());
	}
	
	public void setEpiPcmatDao(EpiPcmatDao epiPcmatDao)
	{
		this.epiPcmatDao = epiPcmatDao;
	}

	public void setEpiDao(EpiDao epiDao) {
		this.epiDao = epiDao;
	}

	public void setPcmatDao(PcmatDao pcmatDao) {
		this.pcmatDao = pcmatDao;
	}
}

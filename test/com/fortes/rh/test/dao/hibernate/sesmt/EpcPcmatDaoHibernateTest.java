package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.EpcDao;
import com.fortes.rh.dao.sesmt.EpcPcmatDao;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.EpcPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.EpcFactory;
import com.fortes.rh.test.factory.sesmt.EpcPcmatFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;

public class EpcPcmatDaoHibernateTest extends GenericDaoHibernateTest<EpcPcmat>
{
	private EpcPcmatDao epcPcmatDao;
	private EpcDao epcDao;
	private PcmatDao pcmatDao;

	@Override
	public EpcPcmat getEntity()
	{
		return EpcPcmatFactory.getEntity();
	}

	public void testFindByPcmat()
	{
		Epc corrimao = EpcFactory.getEntity();
		epcDao.save(corrimao);

		Epc ponte = EpcFactory.getEntity();
		epcDao.save(ponte);
		
		Pcmat pcmat1 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat1);
		
		Pcmat pcmat2 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat2);
		
		EpcPcmat epcPcmat1 = new EpcPcmat();
		epcPcmat1.setEpc(corrimao);
		epcPcmat1.setPcmat(pcmat1);
		epcPcmatDao.save(epcPcmat1);
		
		EpcPcmat epcPcmat2 = new EpcPcmat();
		epcPcmat2.setEpc(ponte);
		epcPcmat2.setPcmat(pcmat1);
		epcPcmatDao.save(epcPcmat2);
		
		EpcPcmat epcPcmat3 = new EpcPcmat();
		epcPcmat3.setEpc(corrimao);
		epcPcmat3.setPcmat(pcmat2);
		epcPcmatDao.save(epcPcmat3);
		
		assertEquals("Pcmat 1", 2, epcPcmatDao.findByPcmat(pcmat1.getId()).size());
		assertEquals("Pcmat 2", 1, epcPcmatDao.findByPcmat(pcmat2.getId()).size());
	}
	
	@Override
	public GenericDao<EpcPcmat> getGenericDao()
	{
		return epcPcmatDao;
	}

	public void setEpcPcmatDao(EpcPcmatDao epcPcmatDao)
	{
		this.epcPcmatDao = epcPcmatDao;
	}

	public void setEpcDao(EpcDao epcDao) {
		this.epcDao = epcDao;
	}

	public void setPcmatDao(PcmatDao pcmatDao) {
		this.pcmatDao = pcmatDao;
	}
}

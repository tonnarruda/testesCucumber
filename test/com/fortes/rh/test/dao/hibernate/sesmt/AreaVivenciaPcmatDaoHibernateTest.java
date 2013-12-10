package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.AreaVivenciaDao;
import com.fortes.rh.dao.sesmt.AreaVivenciaPcmatDao;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.sesmt.AreaVivencia;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.AreaVivenciaFactory;
import com.fortes.rh.test.factory.sesmt.AreaVivenciaPcmatFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;

public class AreaVivenciaPcmatDaoHibernateTest extends GenericDaoHibernateTest<AreaVivenciaPcmat>
{
	private AreaVivenciaPcmatDao areaVivenciaPcmatDao;
	private AreaVivenciaDao areaVivenciaDao;
	private PcmatDao pcmatDao;

	public AreaVivenciaPcmat getEntity()
	{
		return AreaVivenciaPcmatFactory.getEntity();
	}

	public GenericDao<AreaVivenciaPcmat> getGenericDao()
	{
		return areaVivenciaPcmatDao;
	}
	
	public void testFindByPcmat()
	{
		Pcmat pcmat1 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat1);

		Pcmat pcmat2 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat2);
		
		AreaVivencia av1 = AreaVivenciaFactory.getEntity();
		areaVivenciaDao.save(av1);
		
		AreaVivencia av2 = AreaVivenciaFactory.getEntity();
		areaVivenciaDao.save(av2);
		
		AreaVivenciaPcmat avp1 = AreaVivenciaPcmatFactory.getEntity();
		avp1.setAreaVivencia(av1);
		avp1.setPcmat(pcmat1);
		areaVivenciaPcmatDao.save(avp1);
		
		AreaVivenciaPcmat avp2 = AreaVivenciaPcmatFactory.getEntity();
		avp2.setAreaVivencia(av2);
		avp2.setPcmat(pcmat1);
		areaVivenciaPcmatDao.save(avp2);
		
		AreaVivenciaPcmat avp3 = AreaVivenciaPcmatFactory.getEntity();
		avp3.setPcmat(pcmat2);
		areaVivenciaPcmatDao.save(avp3);
		
		assertEquals("Pcmat 1", 2, areaVivenciaPcmatDao.findByPcmat(pcmat1.getId()).size());
		assertEquals("Pcmat 2", 1, areaVivenciaPcmatDao.findByPcmat(pcmat2.getId()).size());
	}

	public void setAreaVivenciaPcmatDao(AreaVivenciaPcmatDao areaVivenciaPcmatDao)
	{
		this.areaVivenciaPcmatDao = areaVivenciaPcmatDao;
	}

	public void setPcmatDao(PcmatDao pcmatDao) 
	{
		this.pcmatDao = pcmatDao;
	}

	public void setAreaVivenciaDao(AreaVivenciaDao areaVivenciaDao) 
	{
		this.areaVivenciaDao = areaVivenciaDao;
	}
}

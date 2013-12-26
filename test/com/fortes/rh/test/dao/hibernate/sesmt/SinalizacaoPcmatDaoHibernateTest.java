package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.dao.sesmt.SinalizacaoPcmatDao;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.model.sesmt.SinalizacaoPcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;
import com.fortes.rh.test.factory.sesmt.SinalizacaoPcmatFactory;

public class SinalizacaoPcmatDaoHibernateTest extends GenericDaoHibernateTest<SinalizacaoPcmat>
{
	private SinalizacaoPcmatDao sinalizacaoPcmatDao;
	private PcmatDao pcmatDao;

	public SinalizacaoPcmat getEntity()
	{
		return SinalizacaoPcmatFactory.getEntity();
	}

	public void testFindByPcmat()
	{
		Pcmat pcmat1 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat1);
		
		Pcmat pcmat2 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat2);
		
		SinalizacaoPcmat sinalizacaoPcmat1 = new SinalizacaoPcmat();
		sinalizacaoPcmat1.setDescricao("Uso obrigatório de máscara");
		sinalizacaoPcmat1.setPcmat(pcmat1);
		sinalizacaoPcmatDao.save(sinalizacaoPcmat1);
		
		SinalizacaoPcmat sinalizacaoPcmat2 = new SinalizacaoPcmat();
		sinalizacaoPcmat2.setDescricao("Use protetor auricular");
		sinalizacaoPcmat2.setPcmat(pcmat1);
		sinalizacaoPcmatDao.save(sinalizacaoPcmat2);
		
		SinalizacaoPcmat sinalizacaoPcmat3 = new SinalizacaoPcmat();
		sinalizacaoPcmat3.setDescricao("Obrigatório uso de luvas");
		sinalizacaoPcmat3.setPcmat(pcmat2);
		sinalizacaoPcmatDao.save(sinalizacaoPcmat3);
		
		assertEquals("Pcmat 1", 2, sinalizacaoPcmatDao.findByPcmat(pcmat1.getId()).size());
		assertEquals("Pcmat 2", 1, sinalizacaoPcmatDao.findByPcmat(pcmat2.getId()).size());
	}
	
	public GenericDao<SinalizacaoPcmat> getGenericDao()
	{
		return sinalizacaoPcmatDao;
	}

	public void setSinalizacaoPcmatDao(SinalizacaoPcmatDao sinalizacaoPcmatDao)
	{
		this.sinalizacaoPcmatDao = sinalizacaoPcmatDao;
	}

	public void setPcmatDao(PcmatDao pcmatDao) {
		this.pcmatDao = pcmatDao;
	}
}

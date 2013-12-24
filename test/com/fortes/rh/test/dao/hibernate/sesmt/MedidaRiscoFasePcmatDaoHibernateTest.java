package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.dao.sesmt.MedidaRiscoFasePcmatDao;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.dao.sesmt.RiscoFasePcmatDao;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.FasePcmatFactory;
import com.fortes.rh.test.factory.sesmt.MedidaRiscoFasePcmatFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFasePcmatFactory;

public class MedidaRiscoFasePcmatDaoHibernateTest extends GenericDaoHibernateTest<MedidaRiscoFasePcmat>
{
	private MedidaRiscoFasePcmatDao medidaRiscoFasePcmatDao;
	private RiscoFasePcmatDao riscoFasePcmatDao;
	private FasePcmatDao fasePcmatDao;
	private PcmatDao pcmatDao;
	
	private Pcmat pcmat1;
	private Pcmat pcmat2;
	
	public void testFindByRiscoFasePcmat()
	{
		RiscoFasePcmat[] riscosFasePcmat = dadoQueExistamRiscosComMedidas();
		
		assertEquals(1, medidaRiscoFasePcmatDao.findByRiscoFasePcmat(riscosFasePcmat[0].getId()).size());
		assertEquals(2, medidaRiscoFasePcmatDao.findByRiscoFasePcmat(riscosFasePcmat[1].getId()).size());
	}
	
	public void testDeleteByRiscoFasePcmat()
	{
		RiscoFasePcmat[] riscosFasePcmat = dadoQueExistamRiscosComMedidas();
		
		assertEquals(1, medidaRiscoFasePcmatDao.findByRiscoFasePcmat(riscosFasePcmat[0].getId()).size());
		assertEquals(2, medidaRiscoFasePcmatDao.findByRiscoFasePcmat(riscosFasePcmat[1].getId()).size());
		
		medidaRiscoFasePcmatDao.deleteByRiscoFasePcmat(riscosFasePcmat[0].getId());
		
		assertEquals(0, medidaRiscoFasePcmatDao.findByRiscoFasePcmat(riscosFasePcmat[0].getId()).size());
		assertEquals(2, medidaRiscoFasePcmatDao.findByRiscoFasePcmat(riscosFasePcmat[1].getId()).size());
	}
	
	public void testFindByPcmat()
	{
		dadoQueExistamRiscosComMedidas();
		
		assertEquals(1, medidaRiscoFasePcmatDao.findByPcmat(pcmat1.getId()).size());
		assertEquals(2, medidaRiscoFasePcmatDao.findByPcmat(pcmat2.getId()).size());
	}

	private RiscoFasePcmat[] dadoQueExistamRiscosComMedidas() 
	{
		pcmat1 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat1);

		pcmat2 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat2);
		
		FasePcmat fasePcmat1 = FasePcmatFactory.getEntity();
		fasePcmat1.setPcmat(pcmat1);
		fasePcmatDao.save(fasePcmat1);
		
		FasePcmat fasePcmat2 = FasePcmatFactory.getEntity();
		fasePcmat2.setPcmat(pcmat2);
		fasePcmatDao.save(fasePcmat2);
		
		RiscoFasePcmat riscoFasePcmat1 = RiscoFasePcmatFactory.getEntity();
		riscoFasePcmat1.setFasePcmat(fasePcmat1);
		riscoFasePcmatDao.save(riscoFasePcmat1);

		RiscoFasePcmat riscoFasePcmat2 = RiscoFasePcmatFactory.getEntity();
		riscoFasePcmat2.setFasePcmat(fasePcmat2);
		riscoFasePcmatDao.save(riscoFasePcmat2);
		
		MedidaRiscoFasePcmat medidaRiscoFasePcmat1 = MedidaRiscoFasePcmatFactory.getEntity();
		medidaRiscoFasePcmat1.setRiscoFasePcmat(riscoFasePcmat1);
		medidaRiscoFasePcmatDao.save(medidaRiscoFasePcmat1);
		
		MedidaRiscoFasePcmat medidaRiscoFasePcmat2 = MedidaRiscoFasePcmatFactory.getEntity();
		medidaRiscoFasePcmat2.setRiscoFasePcmat(riscoFasePcmat2);
		medidaRiscoFasePcmatDao.save(medidaRiscoFasePcmat2);
		
		MedidaRiscoFasePcmat medidaRiscoFasePcmat3 = MedidaRiscoFasePcmatFactory.getEntity();
		medidaRiscoFasePcmat3.setRiscoFasePcmat(riscoFasePcmat2);
		medidaRiscoFasePcmatDao.save(medidaRiscoFasePcmat3);
		
		return new RiscoFasePcmat[] { riscoFasePcmat1, riscoFasePcmat2 };
	}

	@Override
	public MedidaRiscoFasePcmat getEntity()
	{
		return MedidaRiscoFasePcmatFactory.getEntity();
	}

	@Override
	public GenericDao<MedidaRiscoFasePcmat> getGenericDao()
	{
		return medidaRiscoFasePcmatDao;
	}

	public void setMedidaRiscoFasePcmatDao(MedidaRiscoFasePcmatDao medidaRiscoFasePcmatDao)
	{
		this.medidaRiscoFasePcmatDao = medidaRiscoFasePcmatDao;
	}

	public void setRiscoFasePcmatDao(RiscoFasePcmatDao riscoFasePcmatDao) {
		this.riscoFasePcmatDao = riscoFasePcmatDao;
	}

	public void setFasePcmatDao(FasePcmatDao fasePcmatDao) {
		this.fasePcmatDao = fasePcmatDao;
	}

	public void setPcmatDao(PcmatDao pcmatDao) {
		this.pcmatDao = pcmatDao;
	}
}

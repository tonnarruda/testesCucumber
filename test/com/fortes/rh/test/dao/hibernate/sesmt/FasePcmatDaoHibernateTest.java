package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.FaseDao;
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.sesmt.Fase;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.FaseFactory;
import com.fortes.rh.test.factory.sesmt.FasePcmatFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;

public class FasePcmatDaoHibernateTest extends GenericDaoHibernateTest<FasePcmat>
{
	private FasePcmatDao fasePcmatDao;
	private FaseDao faseDao;
	private PcmatDao pcmatDao;

	@Override
	public FasePcmat getEntity()
	{
		return FasePcmatFactory.getEntity();
	}

	@Override
	public GenericDao<FasePcmat> getGenericDao()
	{
		return fasePcmatDao;
	}

	public void testFindByPcmat()
	{
		Fase aterramento = FaseFactory.getEntity();
		faseDao.save(aterramento);

		Fase pintura = FaseFactory.getEntity();
		faseDao.save(pintura);
		
		Pcmat pcmat1 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat1);
		
		Pcmat pcmat2 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat2);
		
		FasePcmat fasePcmat1 = new FasePcmat();
		fasePcmat1.setFase(aterramento);
		fasePcmat1.setPcmat(pcmat1);
		fasePcmatDao.save(fasePcmat1);
		
		FasePcmat fasePcmat2 = new FasePcmat();
		fasePcmat2.setFase(pintura);
		fasePcmat2.setPcmat(pcmat1);
		fasePcmatDao.save(fasePcmat2);
		
		FasePcmat fasePcmat3 = new FasePcmat();
		fasePcmat3.setFase(aterramento);
		fasePcmat3.setPcmat(pcmat2);
		fasePcmatDao.save(fasePcmat3);
		
		assertEquals("Pcmat 1", 2, fasePcmatDao.findByPcmat(pcmat1.getId()).size());
		assertEquals("Pcmat 2", 1, fasePcmatDao.findByPcmat(pcmat2.getId()).size());
	}
	
	public void testFindByIdProjection()
	{
		Fase aterramento = FaseFactory.getEntity();
		aterramento.setDescricao("Aterramento");
		faseDao.save(aterramento);
		
		Pcmat pcmat = PcmatFactory.getEntity();
		pcmatDao.save(pcmat);

		FasePcmat fasePcmat = FasePcmatFactory.getEntity();
		fasePcmat.setFase(aterramento);
		fasePcmat.setPcmat(pcmat);
		fasePcmatDao.save(fasePcmat);
		
		assertEquals("Aterramento", fasePcmatDao.findByIdProjection(fasePcmat.getId()).getFase().getDescricao());
	}
	
	public void testUpdateOrdem()
	{
		Fase aterramento = FaseFactory.getEntity();
		aterramento.setDescricao("Aterramento");
		faseDao.save(aterramento);

		Fase pintura = FaseFactory.getEntity();
		pintura.setDescricao("Pintura");
		faseDao.save(pintura);
		
		Pcmat pcmat = PcmatFactory.getEntity();
		pcmatDao.save(pcmat);

		FasePcmat fasePcmat1 = FasePcmatFactory.getEntity();
		fasePcmat1.setFase(aterramento);
		fasePcmat1.setPcmat(pcmat);
		fasePcmat1.setOrdem(1);
		fasePcmatDao.save(fasePcmat1);

		FasePcmat fasePcmat2 = FasePcmatFactory.getEntity();
		fasePcmat2.setFase(aterramento);
		fasePcmat2.setPcmat(pcmat);
		fasePcmat2.setOrdem(2);
		fasePcmatDao.save(fasePcmat2);

		Collection<FasePcmat> fasesPcmat = fasePcmatDao.findByPcmat(pcmat.getId());
		FasePcmat[] retorno = fasesPcmat.toArray(new FasePcmat[2]);
		
		assertEquals(fasePcmat1.getId(), retorno[0].getId());
		assertEquals(fasePcmat2.getId(), retorno[1].getId());
		
		fasePcmatDao.updateOrdem(fasePcmat1.getId(), 2);
		fasePcmatDao.updateOrdem(fasePcmat2.getId(), 1);
		
		fasesPcmat = fasePcmatDao.findByPcmat(pcmat.getId());
		retorno = fasesPcmat.toArray(new FasePcmat[2]);
		
		assertEquals(fasePcmat2.getId(), retorno[0].getId());
		assertEquals(fasePcmat1.getId(), retorno[1].getId());
	}
	
	public void setFasePcmatDao(FasePcmatDao fasePcmatDao)
	{
		this.fasePcmatDao = fasePcmatDao;
	}

	public void setFaseDao(FaseDao faseDao) {
		this.faseDao = faseDao;
	}

	public void setPcmatDao(PcmatDao pcmatDao) {
		this.pcmatDao = pcmatDao;
	}
}

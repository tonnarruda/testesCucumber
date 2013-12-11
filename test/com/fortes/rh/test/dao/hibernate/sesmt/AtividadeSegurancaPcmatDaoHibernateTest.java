package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.AtividadeSegurancaPcmatDao;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.AtividadeSegurancaPcmatFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;

public class AtividadeSegurancaPcmatDaoHibernateTest extends GenericDaoHibernateTest<AtividadeSegurancaPcmat>
{
	private AtividadeSegurancaPcmatDao atividadeSegurancaPcmatDao;
	private PcmatDao pcmatDao;

	public AtividadeSegurancaPcmat getEntity()
	{
		return AtividadeSegurancaPcmatFactory.getEntity();
	}

	public GenericDao<AtividadeSegurancaPcmat> getGenericDao()
	{
		return atividadeSegurancaPcmatDao;
	}
	
	public void testFindByPcmat()
	{
		Pcmat pcmat1 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat1);

		Pcmat pcmat2 = PcmatFactory.getEntity();
		pcmatDao.save(pcmat2);
		
		AtividadeSegurancaPcmat atividade1 = AtividadeSegurancaPcmatFactory.getEntity();
		atividade1.setPcmat(pcmat1);
		atividadeSegurancaPcmatDao.save(atividade1);
		
		AtividadeSegurancaPcmat atividade2 = AtividadeSegurancaPcmatFactory.getEntity();
		atividade2.setPcmat(pcmat1);
		atividadeSegurancaPcmatDao.save(atividade2);
		
		AtividadeSegurancaPcmat atividade3 = AtividadeSegurancaPcmatFactory.getEntity();
		atividade3.setPcmat(pcmat2);
		atividadeSegurancaPcmatDao.save(atividade3);
		
		assertEquals("Pcmat 1", 2, atividadeSegurancaPcmatDao.findByPcmat(pcmat1.getId()).size());
		assertEquals("Pcmat 2", 1, atividadeSegurancaPcmatDao.findByPcmat(pcmat2.getId()).size());
	}

	public void setAtividadeSegurancaPcmatDao(AtividadeSegurancaPcmatDao atividadeSegurancaPcmatDao)
	{
		this.atividadeSegurancaPcmatDao = atividadeSegurancaPcmatDao;
	}

	public void setPcmatDao(PcmatDao pcmatDao) {
		this.pcmatDao = pcmatDao;
	}
}

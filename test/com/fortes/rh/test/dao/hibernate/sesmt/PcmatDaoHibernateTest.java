package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.ObraDao;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.ObraFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;
import com.fortes.rh.util.DateUtil;

public class PcmatDaoHibernateTest extends GenericDaoHibernateTest<Pcmat>
{
	private PcmatDao pcmatDao;
	private ObraDao obraDao;

	@Override
	public Pcmat getEntity()
	{
		return PcmatFactory.getEntity();
	}

	@Override
	public GenericDao<Pcmat> getGenericDao()
	{
		return pcmatDao;
	}

	public void testFindByObra()
	{
		Obra obra1 = ObraFactory.getEntity();
		obra1.setNome("Obra A");
		obraDao.save(obra1);

		Obra obra2 = ObraFactory.getEntity();
		obra2.setNome("Obra B");
		obraDao.save(obra2);

		Pcmat pcmat1 = PcmatFactory.getEntity();
		pcmat1.setObra(obra1);
		pcmat1.setAPartirDe(DateUtil.criarDataMesAno(01, 12, 2013));
		pcmatDao.save(pcmat1);

		Pcmat pcmat2 = PcmatFactory.getEntity();
		pcmat2.setObra(obra1);
		pcmat2.setAPartirDe(DateUtil.criarDataMesAno(05, 12, 2013));
		pcmatDao.save(pcmat2);
		
		Pcmat pcmat3 = PcmatFactory.getEntity();
		pcmat3.setObra(obra2);
		pcmat1.setAPartirDe(DateUtil.criarDataMesAno(10, 12, 2013));
		pcmatDao.save(pcmat3);

		assertEquals("Obra 1", 2, pcmatDao.findByObra(obra1.getId()).size());
		assertEquals("Obra 2", 1, pcmatDao.findByObra(obra2.getId()).size());
	}
	
	public void testFindUltimoHistorico()
	{
		Obra obra1 = ObraFactory.getEntity();
		obra1.setNome("Obra A");
		obraDao.save(obra1);
		
		Obra obra2 = ObraFactory.getEntity();
		obra2.setNome("Obra B");
		obraDao.save(obra2);
		
		Pcmat pcmat1 = PcmatFactory.getEntity();
		pcmat1.setObra(obra1);
		pcmat1.setAPartirDe(DateUtil.criarDataMesAno(01, 12, 2013));
		pcmatDao.save(pcmat1);
		
		Pcmat pcmat2 = PcmatFactory.getEntity();
		pcmat2.setObra(obra1);
		pcmat2.setAPartirDe(DateUtil.criarDataMesAno(10, 12, 2013));
		pcmatDao.save(pcmat2);
		
		Pcmat pcmat3 = PcmatFactory.getEntity();
		pcmat3.setObra(obra2);
		pcmat3.setAPartirDe(DateUtil.criarDataMesAno(20, 12, 2013));
		pcmatDao.save(pcmat3);
		
		Pcmat resultado = pcmatDao.findUltimoHistorico(null, obra1.getId());
		Pcmat resultado2 = pcmatDao.findUltimoHistorico(pcmat2.getId(), obra1.getId());
		assertEquals(pcmat2.getAPartirDe(), resultado.getAPartirDe());

		assertEquals(pcmat1.getAPartirDe(), resultado2.getAPartirDe());
	}

	
	public void setPcmatDao(PcmatDao pcmatDao)
	{
		this.pcmatDao = pcmatDao;
	}

	public void setObraDao(ObraDao obraDao) {
		this.obraDao = obraDao;
	}
}

package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.AnexoDao;
import com.fortes.rh.model.sesmt.Anexo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class AnexoDaoHibernateTest extends GenericDaoHibernateTest<Anexo>
{

	private AnexoDao anexoDao;

	@Override
	public Anexo getEntity()
	{
		Anexo anexo = new Anexo();
		return anexo;
	}

//	public void testFindByOrigem()
//	{
//		Anexo anexo1 = new Anexo();
//		anexo1.setOrigemId(1L);
//		anexo1.setOrigem(OrigemAnexo.LTCAT);
//
//		Anexo anexo2 = new Anexo();
//		anexo1.setOrigemId(2L);
//		anexo1.setOrigem(OrigemAnexo.PPRA);
//
//		anexo1 = anexoDao.save(anexo1);
//		anexo2 = anexoDao.save(anexo2);
//
//		Collection<Anexo> anexos = anexoDao.findByOrigem(1L, OrigemAnexo.LTCAT);
//		assertEquals(1, anexos.size());
//	}
	@Override
	public GenericDao<Anexo> getGenericDao()
	{
		return anexoDao;
	}

	public void setAnexoDao(AnexoDao anexoDao)
	{
		this.anexoDao = anexoDao;
	}
}
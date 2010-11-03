package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.AnexoDao;
import com.fortes.rh.model.dicionario.OrigemAnexo;
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

	public void testFindByOrigem()
	{
		Anexo anexo1 = new Anexo();
		anexo1.setUrl("www.xxx.com");
		anexo1.setNome("anexo1");
		anexo1.setObservacao("obs");
		anexo1.setOrigemId(1L);
		anexo1.setOrigem(OrigemAnexo.LTCAT);
		anexoDao.save(anexo1);

		Anexo anexo2 = new Anexo();
		anexo2.setUrl("www.yyyy.com");
		anexo2.setNome("anexo2");
		anexo2.setObservacao("obs2");
		anexo2.setOrigemId(2L);
		anexo2.setOrigem(OrigemAnexo.PPRA);
		anexo2 = anexoDao.save(anexo2);

		Collection<Anexo> anexos = anexoDao.findByOrigem(anexo1.getOrigemId(), anexo1.getOrigem());
		assertEquals(1, anexos.size());
	}
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
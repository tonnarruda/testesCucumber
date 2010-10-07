package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoDao;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoReuniaoFactory;

public class ComissaoReuniaoDaoHibernateTest extends GenericDaoHibernateTest<ComissaoReuniao>
{
	ComissaoReuniaoDao comissaoReuniaoDao;
	ComissaoDao comissaoDao;

	public void setComissaoReuniaoDao(ComissaoReuniaoDao comissaoReuniaoDao)
	{
		this.comissaoReuniaoDao = comissaoReuniaoDao;
	}

	@Override
	public ComissaoReuniao getEntity()
	{
		return new ComissaoReuniao();
	}

	@Override
	public GenericDao<ComissaoReuniao> getGenericDao()
	{
		return comissaoReuniaoDao;
	}

	public void testFindByIdProjection()
	{
		ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity();
		comissaoReuniaoDao.save(comissaoReuniao);
		assertEquals(comissaoReuniao, comissaoReuniaoDao.findByIdProjection(comissaoReuniao.getId()));
	}

	public void testFindByComissao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);

		ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity();
		comissaoReuniao.setComissao(comissao);
		comissaoReuniaoDao.save(comissaoReuniao);

		Collection<ComissaoReuniao> resultado = comissaoReuniaoDao.findByComissao(comissao.getId());
		assertEquals(1, resultado.size());
	}

	public void setComissaoDao(ComissaoDao comissaoDao)
	{
		this.comissaoDao = comissaoDao;
	}
}
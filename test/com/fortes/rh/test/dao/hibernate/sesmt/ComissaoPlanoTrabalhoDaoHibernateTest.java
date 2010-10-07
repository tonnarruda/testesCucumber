package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.ComissaoPlanoTrabalhoDao;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPlanoTrabalhoFactory;

public class ComissaoPlanoTrabalhoDaoHibernateTest extends GenericDaoHibernateTest<ComissaoPlanoTrabalho>
{
	ComissaoPlanoTrabalhoDao comissaoPlanoTrabalhoDao;
	ComissaoDao comissaoDao;

	public void setComissaoPlanoTrabalhoDao(ComissaoPlanoTrabalhoDao comissaoPlanoTrabalhoDao)
	{
		this.comissaoPlanoTrabalhoDao = comissaoPlanoTrabalhoDao;
	}

	@Override
	public ComissaoPlanoTrabalho getEntity()
	{
		return new ComissaoPlanoTrabalho();
	}

	@Override
	public GenericDao<ComissaoPlanoTrabalho> getGenericDao()
	{
		return comissaoPlanoTrabalhoDao;
	}

	public void testFindByIdProjection()
	{
		ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity();
		comissaoPlanoTrabalhoDao.save(comissaoPlanoTrabalho);
		assertEquals(comissaoPlanoTrabalho, comissaoPlanoTrabalhoDao.findByIdProjection(comissaoPlanoTrabalho.getId()));
	}

	public void testFindByComissao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);

		ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity();
		comissaoPlanoTrabalho.setComissao(comissao);
		comissaoPlanoTrabalhoDao.save(comissaoPlanoTrabalho);

		Collection<ComissaoPlanoTrabalho> resultado = comissaoPlanoTrabalhoDao.findByComissao(comissao.getId());
		assertEquals(1, resultado.size());
	}

	public void setComissaoDao(ComissaoDao comissaoDao)
	{
		this.comissaoDao = comissaoDao;
	}
}
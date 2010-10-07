package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoDao;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoPresencaDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;

public class ComissaoReuniaoPresencaDaoHibernateTest extends GenericDaoHibernateTest<ComissaoReuniaoPresenca>
{
	ComissaoReuniaoPresencaDao comissaoReuniaoPresencaDao;
	ComissaoReuniaoDao comissaoReuniaoDao;
	ColaboradorDao colaboradorDao;
	ComissaoDao comissaoDao;

	public void setComissaoReuniaoDao(ComissaoReuniaoDao comissaoReuniaoDao)
	{
		this.comissaoReuniaoDao = comissaoReuniaoDao;
	}

	public void setComissaoReuniaoPresencaDao(ComissaoReuniaoPresencaDao comissaoReuniaoPresencaDao)
	{
		this.comissaoReuniaoPresencaDao = comissaoReuniaoPresencaDao;
	}

	@Override
	public ComissaoReuniaoPresenca getEntity()
	{
		return new ComissaoReuniaoPresenca();
	}

	@Override
	public GenericDao<ComissaoReuniaoPresenca> getGenericDao()
	{
		return comissaoReuniaoPresencaDao;
	}

	public void testRemoveByReuniao()
	{
		ComissaoReuniao comissaoReuniao = new ComissaoReuniao();
		comissaoReuniaoDao.save(comissaoReuniao);

		ComissaoReuniaoPresenca comissaoReuniaoPresenca = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca.setComissaoReuniao(comissaoReuniao);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca);

		comissaoReuniaoPresencaDao.removeByReuniao(comissaoReuniao.getId());
	}

	public void testFindByReuniao()
	{
		ComissaoReuniao comissaoReuniao = new ComissaoReuniao();
		comissaoReuniaoDao.save(comissaoReuniao);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		ComissaoReuniaoPresenca comissaoReuniaoPresenca = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca.setComissaoReuniao(comissaoReuniao);
		comissaoReuniaoPresenca.setColaborador(colaborador);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca);

		Collection<ComissaoReuniaoPresenca> resultado = comissaoReuniaoPresencaDao.findByReuniao(comissaoReuniao.getId());
		assertEquals(1, resultado.size());

		ComissaoReuniaoPresenca elemento = ((ComissaoReuniaoPresenca)resultado.toArray()[0]);
		assertEquals(elemento.getColaborador(), colaborador);
		assertEquals(elemento.getId(), comissaoReuniaoPresenca.getId());
	}

	public void testFindByComissao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);

		ComissaoReuniao comissaoReuniao = new ComissaoReuniao();
		comissaoReuniao.setComissao(comissao);
		comissaoReuniaoDao.save(comissaoReuniao);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		ComissaoReuniaoPresenca comissaoReuniaoPresenca = new ComissaoReuniaoPresenca();
		comissaoReuniaoPresenca.setComissaoReuniao(comissaoReuniao);
		comissaoReuniaoPresenca.setColaborador(colaborador);
		comissaoReuniaoPresencaDao.save(comissaoReuniaoPresenca);

		Collection<ComissaoReuniaoPresenca> resultado = comissaoReuniaoPresencaDao.findByComissao(comissao.getId());
		assertEquals(1, resultado.size());
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setComissaoDao(ComissaoDao comissaoDao)
	{
		this.comissaoDao = comissaoDao;
	}
}
package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.ComissaoPlanoTrabalhoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPlanoTrabalhoFactory;

public class ComissaoPlanoTrabalhoDaoHibernateTest extends GenericDaoHibernateTest<ComissaoPlanoTrabalho>
{
	ComissaoPlanoTrabalhoDao comissaoPlanoTrabalhoDao;
	ComissaoDao comissaoDao;
	ColaboradorDao colaboradorDao;

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

		Colaborador responsavel = ColaboradorFactory.getEntity();
		responsavel.setNome("Responsável");
		colaboradorDao.save(responsavel);

		Colaborador corresponsavel = ColaboradorFactory.getEntity();
		corresponsavel.setNome("Corresponsável");
		colaboradorDao.save(corresponsavel);
		
		ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity();
		comissaoPlanoTrabalho.setComissao(comissao);
		comissaoPlanoTrabalhoDao.save(comissaoPlanoTrabalho);
		
		ComissaoPlanoTrabalho comissaoPlanoTrabalhoEmAndamento = ComissaoPlanoTrabalhoFactory.getEntity();
		comissaoPlanoTrabalhoEmAndamento.setComissao(comissao);
		comissaoPlanoTrabalhoEmAndamento.setSituacao("ANDAMENTO");
		comissaoPlanoTrabalhoDao.save(comissaoPlanoTrabalhoEmAndamento);
		
		ComissaoPlanoTrabalho comissaoPlanoTrabalhoComResponsavelECorresponsavel = ComissaoPlanoTrabalhoFactory.getEntity();
		comissaoPlanoTrabalhoComResponsavelECorresponsavel.setComissao(comissao);
		comissaoPlanoTrabalhoComResponsavelECorresponsavel.setResponsavel(responsavel);
		comissaoPlanoTrabalhoComResponsavelECorresponsavel.setCorresponsavel(corresponsavel);
		comissaoPlanoTrabalhoDao.save(comissaoPlanoTrabalhoComResponsavelECorresponsavel);

		Collection<ComissaoPlanoTrabalho> comissaoPlanoTrabalhoSemFiltro = comissaoPlanoTrabalhoDao.findByComissao(comissao.getId(), null, null, null);
		assertEquals(3, comissaoPlanoTrabalhoSemFiltro.size());
		
		Collection<ComissaoPlanoTrabalho> comissaoPlanoTrabalhoComFiltroSituacao = comissaoPlanoTrabalhoDao.findByComissao(comissao.getId(), "ANDAMENTO", null, null);
		assertEquals(1, comissaoPlanoTrabalhoComFiltroSituacao.size());
		
		Collection<ComissaoPlanoTrabalho> comissaoPlanoTrabalhoComFiltroSituacaoResponsavel = comissaoPlanoTrabalhoDao.findByComissao(comissao.getId(), "TODAS", responsavel.getId(), null);
		assertEquals(1, comissaoPlanoTrabalhoComFiltroSituacaoResponsavel.size());
		
		Collection<ComissaoPlanoTrabalho> comissaoPlanoTrabalhoComFiltroSituacaoCorresponsavel = comissaoPlanoTrabalhoDao.findByComissao(comissao.getId(), "TODAS", null, corresponsavel.getId());
		assertEquals(1, comissaoPlanoTrabalhoComFiltroSituacaoCorresponsavel.size());
	}

	public void setComissaoDao(ComissaoDao comissaoDao)
	{
		this.comissaoDao = comissaoDao;
	}
	
	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}
}
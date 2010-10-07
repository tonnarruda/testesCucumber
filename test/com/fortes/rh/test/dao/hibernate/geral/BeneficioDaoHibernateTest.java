package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.BeneficioDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.HistoricoColaboradorBeneficioDao;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

public class BeneficioDaoHibernateTest extends GenericDaoHibernateTest<Beneficio>
{
	private BeneficioDao beneficioDao;
	private ColaboradorDao colaboradorDao;
	private HistoricoColaboradorBeneficioDao historicoColaboradorBeneficioDao;

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setHistoricoColaboradorBeneficioDao(HistoricoColaboradorBeneficioDao historicoColaboradorBeneficioDao)
	{
		this.historicoColaboradorBeneficioDao = historicoColaboradorBeneficioDao;
	}

	public Beneficio getEntity()
	{
		Beneficio beneficio = new Beneficio();

		beneficio.setId(null);
		beneficio.setNome("beneficio");
		beneficio.setEmpresa(null);

		return beneficio;
	}

	public GenericDao<Beneficio> getGenericDao()
	{
		return beneficioDao;
	}

	public void setBeneficioDao(BeneficioDao beneficioDao)
	{
		this.beneficioDao = beneficioDao;
	}

	public void testGetBeneficiosByHistoricoColaborador()
	{
		Beneficio beneficio = getEntity();
		beneficio = beneficioDao.save(beneficio);

		Collection<Beneficio> beneficios = new ArrayList<Beneficio>();
		beneficios.add(beneficio);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaboradorBeneficio historicoColaboradorBeneficio = new HistoricoColaboradorBeneficio();
		historicoColaboradorBeneficio.setBeneficios(beneficios);
		historicoColaboradorBeneficio.setColaborador(colaborador);
		historicoColaboradorBeneficio = historicoColaboradorBeneficioDao.save(historicoColaboradorBeneficio);

		List<Long> beneficioIds = beneficioDao.getBeneficiosByHistoricoColaborador(historicoColaboradorBeneficio.getId());

		assertEquals(1, beneficioIds.size());
	}

	public void testFindBeneficioById()
	{
		Beneficio beneficio = getEntity();
		beneficio = beneficioDao.save(beneficio);

		Beneficio beneficioRetorno = beneficioDao.findBeneficioById(beneficio.getId());

		assertEquals(beneficio.getId(), beneficioRetorno.getId());
	}
}
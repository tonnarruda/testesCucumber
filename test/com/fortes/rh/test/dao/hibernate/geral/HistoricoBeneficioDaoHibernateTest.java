package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.BeneficioDao;
import com.fortes.rh.dao.geral.HistoricoBeneficioDao;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.BeneficioFactory;
import com.fortes.rh.test.factory.geral.HistoricoBeneficioFactory;
import com.fortes.rh.util.DateUtil;

public class HistoricoBeneficioDaoHibernateTest extends GenericDaoHibernateTest<HistoricoBeneficio>
{
	private HistoricoBeneficioDao historicoBeneficioDao;
	private BeneficioDao beneficioDao;

	public HistoricoBeneficio getEntity()
	{
		HistoricoBeneficio historicoBeneficio = new HistoricoBeneficio();

		historicoBeneficio.setId(null);
		historicoBeneficio.setParaColaborador(50.0);
		historicoBeneficio.setParaDependenteDireto(50.0);
		historicoBeneficio.setParaDependenteIndireto(50.0);
		historicoBeneficio.setValor(100.0);
		historicoBeneficio.setData(new Date());

		return historicoBeneficio;
	}

	public GenericDao<HistoricoBeneficio> getGenericDao()
	{
		return historicoBeneficioDao;
	}

	public void setHistoricoBeneficioDao(HistoricoBeneficioDao historicoBeneficioDao)
	{
		this.historicoBeneficioDao = historicoBeneficioDao;
	}

	public void testFindByHistoricoId()
	{
		Beneficio beneficio = BeneficioFactory.getEntity();
		beneficio = beneficioDao.save(beneficio);

		HistoricoBeneficio historicoBeneficio = HistoricoBeneficioFactory.getEntity();
		historicoBeneficio.setBeneficio(beneficio);
		historicoBeneficio = historicoBeneficioDao.save(historicoBeneficio);

		HistoricoBeneficio retorno = historicoBeneficioDao.findByHistoricoId(historicoBeneficio.getId());

		assertEquals(historicoBeneficio.getId(), retorno.getId());
	}

	public void testGetHistoricosPeriodo()
	{
		Beneficio beneficio = BeneficioFactory.getEntity();
		beneficio = beneficioDao.save(beneficio);

		HistoricoBeneficio historicoBeneficio = HistoricoBeneficioFactory.getEntity();
		historicoBeneficio.setBeneficio(beneficio);
		historicoBeneficio.setData(DateUtil.criarDataMesAno(01, 01, 1986));
		historicoBeneficio = historicoBeneficioDao.save(historicoBeneficio);

		HistoricoBeneficio historicoBeneficio2 = HistoricoBeneficioFactory.getEntity();
		historicoBeneficio2.setBeneficio(beneficio);
		historicoBeneficio2.setData(DateUtil.criarDataMesAno(01, 04, 1986));
		historicoBeneficio2 = historicoBeneficioDao.save(historicoBeneficio2);

		HistoricoBeneficio historicoBeneficio3 = HistoricoBeneficioFactory.getEntity();
		historicoBeneficio3.setBeneficio(beneficio);
		historicoBeneficio3.setData(DateUtil.criarDataMesAno(01, 07, 1986));
		historicoBeneficio3 = historicoBeneficioDao.save(historicoBeneficio3);

		Collection<HistoricoBeneficio> retornos = historicoBeneficioDao.getHistoricos();

		assertTrue(retornos.size()>=3);
	}

	public void setBeneficioDao(BeneficioDao beneficioDao)
	{
		this.beneficioDao = beneficioDao;
	}

}
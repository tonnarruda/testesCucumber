package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.AreaFormacaoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.AreaFormacaoFactory;

public class AreaFormacaoDaoHibernateTest extends GenericDaoHibernateTest<AreaFormacao>
{
	private AreaFormacaoDao areaFormacaoDao;
	private CargoDao cargoDao;

	public AreaFormacao getEntity()
	{
		AreaFormacao areaFormacao = new AreaFormacao();

		areaFormacao.setId(null);
		areaFormacao.setNome("nome da area de formação");

		return areaFormacao;
	}

	public void testFindByCargo()
	{
		Cargo cargo = CargoFactory.getEntity();

		AreaFormacao area1 = getEntity();
		areaFormacaoDao.save(area1);
		AreaFormacao area2 = getEntity();
		areaFormacaoDao.save(area2);

		Collection<AreaFormacao> areaFormacaos = new ArrayList<AreaFormacao>();
		areaFormacaos.add(area1);
		areaFormacaos.add(area2);

		cargo.setAreaFormacaos(areaFormacaos);
		cargoDao.save(cargo);

		Collection<AreaFormacao> areas = areaFormacaoDao.findByCargo(cargo.getId());

		assertEquals(2, areas.size());
	}
	
	public void testfFindByFiltro()
	{
		AreaFormacao area1 = getEntity();
		area1.setNome("xcdaaaaa");
		areaFormacaoDao.save(area1);

		AreaFormacao area2 = getEntity();
		area2.setNome("xcdbbbbbb");
		areaFormacaoDao.save(area2);

		AreaFormacao areaFormacao = AreaFormacaoFactory.getEntity();
		areaFormacao.setNome("xcd");
		
		Collection<AreaFormacao> areas = areaFormacaoDao.findByFiltro(1, 15, areaFormacao);
		assertEquals("Contador", new Integer(2), areaFormacaoDao.getCount(areaFormacao));
		assertEquals(2, areas.size());
	}

	public GenericDao<AreaFormacao> getGenericDao()
	{
		return areaFormacaoDao;
	}

	public void setAreaFormacaoDao(AreaFormacaoDao areaFormacaoDao)
	{
		this.areaFormacaoDao = areaFormacaoDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}
}
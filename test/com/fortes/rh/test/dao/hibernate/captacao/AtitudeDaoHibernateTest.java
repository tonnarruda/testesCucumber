package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;

public class AtitudeDaoHibernateTest extends GenericDaoHibernateTest<Atitude>
{
	private AtitudeDao atitudeDao;
	private EmpresaDao empresaDao;
	private CargoDao cargoDao;
	private Atitude atitude;
	private Empresa empresa;
	private AreaOrganizacional area;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	
	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresa1 = empresaDao.save(empresa1);
		empresa2 = empresaDao.save(empresa2);

		Atitude ai1 = AtitudeFactory.getEntity();
		ai1.setNome("Proativo");
		ai1.setEmpresa(empresa1);
		ai1 = atitudeDao.save(ai1);

		Atitude ai2 = AtitudeFactory.getEntity();
		ai2.setNome("Firme");
		ai2.setEmpresa(empresa1);
		ai2 = atitudeDao.save(ai2);

		Atitude ai3 = AtitudeFactory.getEntity();
		ai3.setNome("Comunicativo");
		ai3.setEmpresa(empresa2);
		ai3 = atitudeDao.save(ai3);

		Collection<Atitude> retorno = atitudeDao.findAllSelect(empresa1.getId());

		assertEquals("teste1", 2, retorno.size());

		retorno = atitudeDao.findAllSelect(empresa2.getId());
		assertEquals("teste2", 1, retorno.size());
		assertEquals("teste3", ai3.getId(), retorno.toArray(new Atitude[]{})[0].getId());
	}
	
	public void testFindSincronizarConhecimentos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Atitude atitude = AtitudeFactory.getEntity();
		atitude.setNome("Conhecimento");
		atitude.setEmpresa(empresa);
		atitude.setObservacao("Observ");
		atitudeDao.save(atitude);

		assertEquals(1, atitudeDao.findSincronizarAtitudes(empresa.getId()).size());
	}
	
	public void testFindByCargo()
	{
		Cargo cargo = CargoFactory.getEntity();

		Atitude atitude1 = AtitudeFactory.getEntity();
		atitude1 = atitudeDao.save(atitude1);
		
		Atitude atitude2 = getEntity();
		atitude2 = atitudeDao.save(atitude2);

		Collection<Atitude> atitudes = new ArrayList<Atitude>();
		atitudes.add(atitude1);
		atitudes.add(atitude2);

		cargo.setAtitudes(atitudes);
		cargo = cargoDao.save(cargo);

		Collection<Atitude> atitudesRetorno = atitudeDao.findByCargo(cargo.getId());

		assertEquals(2, atitudesRetorno.size());
	}
	
	public void testFindByAreasOrganizacionalIds()
	{
		empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
				
		area = AreaOrganizacionalFactory.getEntity();
		area = areaOrganizacionalDao.save(area);
		
		Long[] areasIds = new Long[]{area.getId()};
		
		Collection<AreaOrganizacional> areas =  new ArrayList<AreaOrganizacional>();
		areas.add(area);
		
		Atitude atitude = AtitudeFactory.getEntity();
		atitude.setEmpresa(empresa);
		atitude.setAreaOrganizacionals(areas);
		atitude = atitudeDao.save(atitude);
				
		Collection<Atitude> atitudes = atitudeDao.findByAreasOrganizacionalIds(areasIds, empresa.getId());

		assertEquals(1, atitudes.size());
		
	}
	
	@Override
	public Atitude getEntity()
	{
		return AtitudeFactory.getEntity();
	}

	@Override
	public GenericDao<Atitude> getGenericDao()
	{
		return atitudeDao;
	}

	public void setAtitudeDao(AtitudeDao atitudeDao)
	{
		this.atitudeDao = atitudeDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	public AreaOrganizacional getArea() {
		return area;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public Atitude getAtitude() {
		return atitude;
	}

}

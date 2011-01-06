package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;

public class HabilidadeDaoHibernateTest extends GenericDaoHibernateTest<Habilidade>
{
	private HabilidadeDao habilidadeDao;
	private EmpresaDao empresaDao;
	private CargoDao cargoDao;
	private AreaOrganizacional areaOrganizacional;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	
	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresa1 = empresaDao.save(empresa1);
		empresa2 = empresaDao.save(empresa2);

		Habilidade ai1 = HabilidadeFactory.getEntity();
		ai1.setNome("Proativo");
		ai1.setEmpresa(empresa1);
		ai1 = habilidadeDao.save(ai1);

		Habilidade ai2 = HabilidadeFactory.getEntity();
		ai2.setNome("Firme");
		ai2.setEmpresa(empresa1);
		ai2 = habilidadeDao.save(ai2);

		Habilidade ai3 = HabilidadeFactory.getEntity();
		ai3.setNome("Comunicativo");
		ai3.setEmpresa(empresa2);
		ai3 = habilidadeDao.save(ai3);

		Collection<Habilidade> retorno = habilidadeDao.findAllSelect(empresa1.getId());

		assertEquals("teste1", 2, retorno.size());

		retorno = habilidadeDao.findAllSelect(empresa2.getId());
		assertEquals("teste2", 1, retorno.size());
		assertEquals("teste3", ai3.getId(), retorno.toArray(new Habilidade[]{})[0].getId());
	}
	
	public void testFindByCargo()
	{
		Cargo cargo = CargoFactory.getEntity();

		Habilidade habilidade1 = HabilidadeFactory.getEntity();
		habilidade1 = habilidadeDao.save(habilidade1);
		
		Habilidade habilidade2 = getEntity();
		habilidade2 = habilidadeDao.save(habilidade2);

		Collection<Habilidade> habilidades = new ArrayList<Habilidade>();
		habilidades.add(habilidade1);
		habilidades.add(habilidade2);

		cargo.setHabilidades(habilidades);
		cargo = cargoDao.save(cargo);

		Collection<Habilidade> atitudesRetorno = habilidadeDao.findByCargo(cargo.getId());

		assertEquals(2, atitudesRetorno.size());
	}
	
	public void testFindByAreasOrganizacionalIds()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		area = areaOrganizacionalDao.save(area);

		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(area);

		Long[] areasIds = new Long[]{area.getId()};

		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidade.setNome("Habilidade");
		habilidade.setEmpresa(empresa);
		habilidade.setAreaOrganizacionals(areas);
		habilidade = habilidadeDao.save(habilidade);

		Collection<Habilidade> habilidades = habilidadeDao.findByAreasOrganizacionalIds(areasIds, empresa.getId());

		assertEquals(1, habilidades.size());
	}

	public void testFindByAreasOrganizacionalIdsVazia() {
		Long[] areasIds = new Long[]{};
		Collection<Habilidade> habilidades = habilidadeDao.findByAreasOrganizacionalIds(areasIds, 1L);
		assertTrue(habilidades.isEmpty());
	}

	
	@Override
	public Habilidade getEntity()
	{
		return HabilidadeFactory.getEntity();
	}

	@Override
	public GenericDao<Habilidade> getGenericDao()
	{
		return habilidadeDao;
	}

	public void setHabilidadeDao(HabilidadeDao habilidadeDao)
	{
		this.habilidadeDao = habilidadeDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}
}

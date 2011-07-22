package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.exception.ConstraintViolationException;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;

public class QuantidadeLimiteColaboradoresPorCargoDaoHibernateTest extends GenericDaoHibernateTest<QuantidadeLimiteColaboradoresPorCargo>
{
	private QuantidadeLimiteColaboradoresPorCargoDao quantidadeLimiteColaboradoresPorCargoDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private CargoDao cargoDao;
	private EmpresaDao empresaDao;
	
	@Override
	public QuantidadeLimiteColaboradoresPorCargo getEntity()
	{
		return new QuantidadeLimiteColaboradoresPorCargo();
	}

	@Override
	public GenericDao<QuantidadeLimiteColaboradoresPorCargo> getGenericDao()
	{
		return quantidadeLimiteColaboradoresPorCargoDao;
	}
	
	public void testFindByArea()
	{
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("pedreiro");
		cargoDao.save(cargo1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setNome("padeiro");
		cargoDao.save(cargo2);
		
		QuantidadeLimiteColaboradoresPorCargo qtdLimite1 = new QuantidadeLimiteColaboradoresPorCargo();
		qtdLimite1.setAreaOrganizacional(area);
		qtdLimite1.setCargo(cargo1);
		qtdLimite1.setLimite(120);
		quantidadeLimiteColaboradoresPorCargoDao.save(qtdLimite1);
		
		QuantidadeLimiteColaboradoresPorCargo qtdLimite2 = new QuantidadeLimiteColaboradoresPorCargo();
		qtdLimite2.setAreaOrganizacional(area);
		qtdLimite2.setCargo(cargo2);
		qtdLimite2.setLimite(20);
		quantidadeLimiteColaboradoresPorCargoDao.save(qtdLimite2);
		
		Collection<QuantidadeLimiteColaboradoresPorCargo> qtds = quantidadeLimiteColaboradoresPorCargoDao.findByArea(area.getId());
		assertEquals(2, qtds.size());
		assertEquals(qtdLimite2, qtds.toArray()[0]);
	}
	
	public void testFindByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		area.setEmpresa(empresa);
		areaOrganizacionalDao.save(area);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("pedreiro");
		cargoDao.save(cargo1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setNome("padeiro");
		cargoDao.save(cargo2);
		
		QuantidadeLimiteColaboradoresPorCargo qtdLimite1 = new QuantidadeLimiteColaboradoresPorCargo();
		qtdLimite1.setAreaOrganizacional(area);
		qtdLimite1.setCargo(cargo1);
		qtdLimite1.setLimite(120);
		quantidadeLimiteColaboradoresPorCargoDao.save(qtdLimite1);
		
		QuantidadeLimiteColaboradoresPorCargo qtdLimite2 = new QuantidadeLimiteColaboradoresPorCargo();
		qtdLimite2.setAreaOrganizacional(area);
		qtdLimite2.setCargo(cargo2);
		qtdLimite2.setLimite(20);
		quantidadeLimiteColaboradoresPorCargoDao.save(qtdLimite2);
		
		Collection<QuantidadeLimiteColaboradoresPorCargo> qtds = quantidadeLimiteColaboradoresPorCargoDao.findByEmpresa(empresa.getId());
		assertEquals(2, qtds.size());
	}

	public void testSaveCargoDuplicado()
	{
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("pedreiro");
		cargoDao.save(cargo);
		
		QuantidadeLimiteColaboradoresPorCargo qtdLimite1 = new QuantidadeLimiteColaboradoresPorCargo();
		qtdLimite1.setAreaOrganizacional(area);
		qtdLimite1.setCargo(cargo);
		qtdLimite1.setLimite(120);
		quantidadeLimiteColaboradoresPorCargoDao.save(qtdLimite1);

		QuantidadeLimiteColaboradoresPorCargo qtdLimite2 = new QuantidadeLimiteColaboradoresPorCargo();
		qtdLimite2.setAreaOrganizacional(area);
		qtdLimite2.setCargo(cargo);
		qtdLimite2.setLimite(20);

		Exception exception = null;
		try {
			quantidadeLimiteColaboradoresPorCargoDao.save(qtdLimite2);			
			Collection<QuantidadeLimiteColaboradoresPorCargo> qtds = quantidadeLimiteColaboradoresPorCargoDao.findByArea(area.getId());
			assertEquals(1, qtds.size());
		} catch (ConstraintViolationException e) {
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testDeleteByArea()
	{
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area1);
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area2);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("pedreiro");
		cargoDao.save(cargo1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setNome("padeiro");
		cargoDao.save(cargo2);
		
		QuantidadeLimiteColaboradoresPorCargo qtdLimite1 = new QuantidadeLimiteColaboradoresPorCargo();
		qtdLimite1.setAreaOrganizacional(area2);
		qtdLimite1.setCargo(cargo1);
		qtdLimite1.setLimite(120);
		quantidadeLimiteColaboradoresPorCargoDao.save(qtdLimite1);
		
		QuantidadeLimiteColaboradoresPorCargo qtdLimite2 = new QuantidadeLimiteColaboradoresPorCargo();
		qtdLimite2.setAreaOrganizacional(area1);
		qtdLimite2.setCargo(cargo2);
		qtdLimite2.setLimite(20);
		quantidadeLimiteColaboradoresPorCargoDao.save(qtdLimite2);
		
		quantidadeLimiteColaboradoresPorCargoDao.deleteByArea(area1.getId());
		Collection<QuantidadeLimiteColaboradoresPorCargo> qtdsArea1 = quantidadeLimiteColaboradoresPorCargoDao.findByArea(area1.getId());
		Collection<QuantidadeLimiteColaboradoresPorCargo> qtdsArea2 = quantidadeLimiteColaboradoresPorCargoDao.findByArea(area2.getId());
		
		assertEquals(0, qtdsArea1.size());
		assertEquals(1, qtdsArea2.size());
	}
	
	
	public void testFindLimite()
	{
		AreaOrganizacional areaFilha = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaFilha);
		
		AreaOrganizacional areaMae = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaMae);
		
		Collection<Long> areasIds = new ArrayList<Long>();
		areasIds.add(areaFilha.getId());
		areasIds.add(areaMae.getId());
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("pedreiro");
		cargoDao.save(cargo);
		
		QuantidadeLimiteColaboradoresPorCargo qtdLimite = new QuantidadeLimiteColaboradoresPorCargo();
		qtdLimite.setAreaOrganizacional(areaMae);
		qtdLimite.setCargo(cargo);
		qtdLimite.setLimite(15);
		quantidadeLimiteColaboradoresPorCargoDao.save(qtdLimite);
		
		QuantidadeLimiteColaboradoresPorCargo limite = quantidadeLimiteColaboradoresPorCargoDao.findLimite(cargo.getId(), areasIds);
		assertEquals(15, limite.getLimite());
		assertEquals(areaMae.getId(), limite.getAreaOrganizacional().getId());
		assertEquals(null, quantidadeLimiteColaboradoresPorCargoDao.findLimite(4545454L, areasIds));
	}
	
	public void setQuantidadeLimiteColaboradoresPorCargoDao(QuantidadeLimiteColaboradoresPorCargoDao quantidadeLimiteColaboradoresPorCargoDao)
	{
		this.quantidadeLimiteColaboradoresPorCargoDao = quantidadeLimiteColaboradoresPorCargoDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}

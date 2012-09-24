package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaColaboradorDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoNivelCompetenciaColaborador>
{
	private ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao;
	private CargoDao cargoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private ColaboradorDao colaboradorDao;

	@Override
	public ConfiguracaoNivelCompetenciaColaborador getEntity()
	{
		return ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
	}

	@Override
	public GenericDao<ConfiguracaoNivelCompetenciaColaborador> getGenericDao()
	{
		return configuracaoNivelCompetenciaColaboradorDao;
	}

	public void testFindByIdProjection()
	{
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaColaborador.setData(DateUtil.criarDataMesAno(8, 8, 2011));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador);
		
		assertEquals(configuracaoNivelCompetenciaColaborador, configuracaoNivelCompetenciaColaboradorDao.findByIdProjection(configuracaoNivelCompetenciaColaborador.getId()));
	}

	public void testFindByColaborador()
	{
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial1);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador1 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador1.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador1.setFaixaSalarial(faixaSalarial1);
		configuracaoNivelCompetenciaColaborador1.setData(DateUtil.criarDataMesAno(7, 7, 2011));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador1);

		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador2 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador2.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador2.setFaixaSalarial(faixaSalarial2);
		configuracaoNivelCompetenciaColaborador2.setData(DateUtil.criarDataMesAno(8, 8, 2011));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador2);
		
		Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradores = configuracaoNivelCompetenciaColaboradorDao.findByColaborador(colaborador.getId());
		
		assertEquals(2, configuracaoNivelCompetenciaColaboradores.size());
		assertEquals(configuracaoNivelCompetenciaColaborador2, (ConfiguracaoNivelCompetenciaColaborador)configuracaoNivelCompetenciaColaboradores.toArray()[0]);
	}
	
	public void testChecarHistoricoMesmaData()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Date data = DateUtil.criarDataMesAno(8, 8, 2011);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador1 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador1.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador1.setData(data);
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador1);

		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador2 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador2.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador2.setData(data);
		
		assertNotNull(configuracaoNivelCompetenciaColaboradorDao.checarHistoricoMesmaData(configuracaoNivelCompetenciaColaborador2));

		configuracaoNivelCompetenciaColaborador2.setData(DateUtil.criarDataMesAno(10, 8, 2011));

		assertNull(configuracaoNivelCompetenciaColaboradorDao.checarHistoricoMesmaData(configuracaoNivelCompetenciaColaborador2));
	}
	
	public void testDeleteByFaixaSalarial() {
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);

		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador);
		
		Exception exception = null;
		try {
			configuracaoNivelCompetenciaColaboradorDao.deleteByFaixaSalarial(new Long[] {faixaSalarial.getId()});
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testFindByData()
	{
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial1);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador1 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador1.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador1.setFaixaSalarial(faixaSalarial1);
		configuracaoNivelCompetenciaColaborador1.setData(DateUtil.criarDataMesAno(7, 7, 2011));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador1);

		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador2 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador2.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador2.setFaixaSalarial(faixaSalarial2);
		configuracaoNivelCompetenciaColaborador2.setData(DateUtil.criarDataMesAno(8, 8, 2011));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador2);
		
		ConfiguracaoNivelCompetenciaColaborador config1 = configuracaoNivelCompetenciaColaboradorDao.findByData(DateUtil.criarDataMesAno(7, 7, 2011), colaborador.getId());
		ConfiguracaoNivelCompetenciaColaborador config2 = configuracaoNivelCompetenciaColaboradorDao.findByData(DateUtil.criarDataMesAno(8, 8, 2011), colaborador.getId());
		ConfiguracaoNivelCompetenciaColaborador config3 = configuracaoNivelCompetenciaColaboradorDao.findByData(DateUtil.criarDataMesAno(8, 8, 2011), colaborador2.getId());
		ConfiguracaoNivelCompetenciaColaborador config4 = configuracaoNivelCompetenciaColaboradorDao.findByData(DateUtil.criarDataMesAno(9, 9, 2011), colaborador.getId());
		
		assertEquals(configuracaoNivelCompetenciaColaborador1.getId(), config1.getId());
		assertEquals(configuracaoNivelCompetenciaColaborador2.getId(), config2.getId());
		assertNull(config3);
		assertNull(config4);
	}

	public void setConfiguracaoNivelCompetenciaColaboradorDao(ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao)
	{
		this.configuracaoNivelCompetenciaColaboradorDao = configuracaoNivelCompetenciaColaboradorDao;
	}
	
	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}
}

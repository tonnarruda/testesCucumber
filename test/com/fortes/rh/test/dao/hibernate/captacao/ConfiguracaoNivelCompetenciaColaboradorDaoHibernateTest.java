package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaColaboradorDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoNivelCompetenciaColaborador>
{
	private ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao;
	private CargoDao cargoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private ColaboradorDao colaboradorDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private HistoricoColaboradorDao historicoColaboradorDao;

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
	
	public void testDeleteByFaixaSalarial() {
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);

		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador);

		Exception exception = null;
		try {
			int result = configuracaoNivelCompetenciaColaboradorDao.findToList(new String[]{"data"}, new String[]{"data"}, new String[]{"id"}, new Long[]{configuracaoNivelCompetenciaColaborador.getId()}).size();
			assertEquals(1, result);
			
			configuracaoNivelCompetenciaColaboradorDao.deleteByFaixaSalarial(new Long[] {faixaSalarial.getId()});

			result = configuracaoNivelCompetenciaColaboradorDao.findToList(new String[]{"data"}, new String[]{"data"}, new String[]{"id"}, new Long[]{configuracaoNivelCompetenciaColaborador.getId()}).size();
			assertEquals(0, result);
			
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
		
		Colaborador avaliador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador1);
		
		Colaborador avaliador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setAvaliador(avaliador1);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliador(avaliador2);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador1 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador1.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador1.setColaboradorQuestionario(colaboradorQuestionario1);
		configuracaoNivelCompetenciaColaborador1.setFaixaSalarial(faixaSalarial1);
		configuracaoNivelCompetenciaColaborador1.setData(DateUtil.criarDataMesAno(7, 7, 2011));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador1);

		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador2 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador2.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador2.setColaboradorQuestionario(colaboradorQuestionario2);
		configuracaoNivelCompetenciaColaborador2.setFaixaSalarial(faixaSalarial2);
		configuracaoNivelCompetenciaColaborador2.setData(DateUtil.criarDataMesAno(8, 8, 2011));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador2);
		
		ConfiguracaoNivelCompetenciaColaborador config1 = configuracaoNivelCompetenciaColaboradorDao.findByData(DateUtil.criarDataMesAno(7, 7, 2011), colaborador.getId(), avaliador1.getId(), colaboradorQuestionario1.getId());
		ConfiguracaoNivelCompetenciaColaborador config2 = configuracaoNivelCompetenciaColaboradorDao.findByData(DateUtil.criarDataMesAno(8, 8, 2011), colaborador.getId(), avaliador2.getId(), colaboradorQuestionario2.getId());
		ConfiguracaoNivelCompetenciaColaborador config3 = configuracaoNivelCompetenciaColaboradorDao.findByData(DateUtil.criarDataMesAno(8, 8, 2011), colaborador2.getId(),avaliador1.getId(), colaboradorQuestionario1.getId());
		ConfiguracaoNivelCompetenciaColaborador config4 = configuracaoNivelCompetenciaColaboradorDao.findByData(DateUtil.criarDataMesAno(8, 8, 2011), colaborador.getId(), avaliador1.getId(), colaboradorQuestionario1.getId());
		ConfiguracaoNivelCompetenciaColaborador config5 = configuracaoNivelCompetenciaColaboradorDao.findByData(DateUtil.criarDataMesAno(9, 9, 2011), colaborador.getId(), avaliador1.getId(), colaboradorQuestionario1.getId());
		
		assertEquals(configuracaoNivelCompetenciaColaborador1.getId(), config1.getId());
		assertEquals(configuracaoNivelCompetenciaColaborador2.getId(), config2.getId());
		assertNull(config3);
		assertNull(config4);
		assertNull(config5);
	}

	public void testExisteConfigCompetenciaParaAFaixaDestehistorico(){
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaborador);
		
		assertFalse(configuracaoNivelCompetenciaColaboradorDao.existeConfigCompetenciaParaAFaixaDestehistorico(historicoColaborador.getId()));
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaColaborador.setData(DateUtil.criarDataMesAno(8, 8, 2011));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador);
		
		assertTrue(configuracaoNivelCompetenciaColaboradorDao.existeConfigCompetenciaParaAFaixaDestehistorico(historicoColaborador.getId()));
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

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao)
	{
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setHistoricoColaboradorDao(
			HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}
}

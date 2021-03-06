package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
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
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;

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
	
	public void testFindByDataAndFaixaSalarial(){
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2005));
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaborador);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador1 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador1.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador1.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoNivelCompetenciaColaborador1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaColaborador1.setData(DateUtil.criarDataMesAno(8, 8, 2011));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador1);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador2 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador2.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoNivelCompetenciaColaborador2.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador2.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaColaborador2.setData(DateUtil.criarDataMesAno(8, 8, 2015));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador2);
		
		assertEquals(1, (configuracaoNivelCompetenciaColaboradorDao.findByDataAndFaixaSalarial(DateUtil.criarDataMesAno(1, 1, 2005), DateUtil.criarDataMesAno(1, 11, 2011), faixaSalarial.getId())).size());
		assertEquals(2, (configuracaoNivelCompetenciaColaboradorDao.findByDataAndFaixaSalarial(DateUtil.criarDataMesAno(1, 1, 2005), DateUtil.criarDataMesAno(1, 11, 2015), faixaSalarial.getId())).size());
	}
	
	public void testFindCargosAvaliadores()
	{
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity("I", cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador avaliador1 = ColaboradorFactory.getEntity();
		avaliador1.setNome("Avaliador 1");
		colaboradorDao.save(avaliador1);

		Colaborador avaliador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador2);
		
		Colaborador colaboradorAvaliado = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaboradorAvaliado);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(null, avaliador1, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2016));
		historicoColaboradorDao.save(historicoColaborador1);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(null, avaliador2, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2016));
		historicoColaboradorDao.save(historicoColaborador2);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity(colaboradorAvaliado, avaliador1, null, avaliacaoDesempenho, true);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity(colaboradorAvaliado, avaliador2, null, avaliacaoDesempenho, true);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ConfiguracaoNivelCompetenciaColaborador config1 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(DateUtil.criarDataMesAno(7, 7, 2011), colaboradorAvaliado, avaliador1, colaboradorQuestionario1);
		configuracaoNivelCompetenciaColaboradorDao.save(config1);
		
		ConfiguracaoNivelCompetenciaColaborador config2 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(DateUtil.criarDataMesAno(8, 8, 2011), colaboradorAvaliado, avaliador2, colaboradorQuestionario2);
		configuracaoNivelCompetenciaColaboradorDao.save(config2);
		
		Collection<Colaborador> colaboradores = configuracaoNivelCompetenciaColaboradorDao.findAvaliadores(avaliacaoDesempenho.getId(), colaboradorAvaliado.getId());
		
		assertEquals(2, colaboradores.size());
		assertEquals(avaliador1.getNome(), ((Colaborador)colaboradores.toArray()[0]).getNome());
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

	public void setConfiguracaoNivelCompetenciaFaixaSalarialDao(ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao) {
		this.configuracaoNivelCompetenciaFaixaSalarialDao = configuracaoNivelCompetenciaFaixaSalarialDao;
	}

	public void setAvaliacaoDesempenhoDao(AvaliacaoDesempenhoDao avaliacaoDesempenhoDao) {
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}
}

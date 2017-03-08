package com.fortes.rh.test.dao.hibernate.captacao;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaColaboradorDaoHibernateTest_JUnit4 extends GenericDaoHibernateTest_JUnit4<ConfiguracaoNivelCompetenciaColaborador>
{
	@Autowired
	private ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private CargoDao cargoDao;
	@Autowired
	private FaixaSalarialDao faixaSalarialDao;

	@Test
	public void testColabsComDependenciaComCompetenciasDaFaixaSalarial()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity("colabNome");
		colaboradorDao.save(colaborador);
				
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaborador.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaColaborador.setData(DateUtil.criarDataMesAno(8, 8, 2011));
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador);
		
		Collection<ConfiguracaoNivelCompetenciaColaborador> retorno = configuracaoNivelCompetenciaColaboradorDao.colabsComDependenciaComCompetenciasDaFaixaSalarial(faixaSalarial.getId(), DateUtil.criarDataMesAno(9, 9, 2010), null);
		
		assertEquals(colaborador.getNome(), ((ConfiguracaoNivelCompetenciaColaborador) retorno.toArray()[0]).getColaborador().getNome());
	}

	@Override
	public GenericDao<ConfiguracaoNivelCompetenciaColaborador> getGenericDao() {
		return configuracaoNivelCompetenciaColaboradorDao;
	}

	@Override
	public ConfiguracaoNivelCompetenciaColaborador getEntity() {
		return ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
	}

}

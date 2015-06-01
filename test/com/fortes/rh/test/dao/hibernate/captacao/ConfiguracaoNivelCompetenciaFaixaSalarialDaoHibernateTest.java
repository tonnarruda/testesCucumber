package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class ConfiguracaoNivelCompetenciaFaixaSalarialDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoNivelCompetenciaFaixaSalarial>
{
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	private FaixaSalarialDao faixaSalarialDao;

	public ConfiguracaoNivelCompetenciaFaixaSalarial getEntity()
	{
		return ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
	}

	public GenericDao<ConfiguracaoNivelCompetenciaFaixaSalarial> getGenericDao()
	{
		return configuracaoNivelCompetenciaFaixaSalarialDao;
	}

	public void testDeleteByFaixaSalarial() {
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);

		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarial.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);
		
		int result = configuracaoNivelCompetenciaFaixaSalarialDao.findToList(new String[]{"data"}, new String[]{"data"}, new String[]{"id"}, new Long[]{configuracaoNivelCompetenciaFaixaSalarial.getId()}).size();
		assertEquals(1, result);
		
		configuracaoNivelCompetenciaFaixaSalarialDao.deleteByFaixaSalarial(new Long[] {faixaSalarial.getId()});

		result = configuracaoNivelCompetenciaFaixaSalarialDao.findToList(new String[]{"data"}, new String[]{"data"}, new String[]{"id"}, new Long[]{configuracaoNivelCompetenciaFaixaSalarial.getId()}).size();
		assertEquals(0, result);

	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialDao(ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao)
	{
		this.configuracaoNivelCompetenciaFaixaSalarialDao = configuracaoNivelCompetenciaFaixaSalarialDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}
}

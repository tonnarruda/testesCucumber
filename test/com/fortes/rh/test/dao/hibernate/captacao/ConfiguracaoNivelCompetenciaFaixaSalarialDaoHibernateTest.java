package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaFaixaSalarialDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoNivelCompetenciaFaixaSalarial>
{
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	private FaixaSalarialDao faixaSalarialDao;
	private NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao;
	private EmpresaDao empresaDao;

	public ConfiguracaoNivelCompetenciaFaixaSalarial getEntity()
	{
		return ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
	}

	public GenericDao<ConfiguracaoNivelCompetenciaFaixaSalarial> getGenericDao()
	{
		return configuracaoNivelCompetenciaFaixaSalarialDao;
	}

	public void testDeleteByFaixaSalarial() 
	{
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
	
	private NivelCompetenciaHistorico iniciaNivelCompetenciaHistorico(Empresa empresa, Date data) 
	{
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity();
		nivelCompetenciaHistorico.setData(data);
		nivelCompetenciaHistorico.setEmpresa(empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico);
		return nivelCompetenciaHistorico;
	}
	
	public void testExistByNivelCompetenciaHistoricoId() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = iniciaNivelCompetenciaHistorico(empresa, DateUtil.criarDataMesAno(1, 1, 2005));
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarial.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);
		
		assertTrue(configuracaoNivelCompetenciaFaixaSalarialDao.existByNivelCompetenciaHistoricoId(nivelCompetenciaHistorico.getId()));
	}
	
	public void testFindByFaixaSalarialIdAndData() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = iniciaNivelCompetenciaHistorico(empresa, DateUtil.criarDataMesAno(1, 1, 2005));
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial1);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial1 = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarial1.setFaixaSalarial(faixaSalarial1);
		configuracaoNivelCompetenciaFaixaSalarial1.setData(DateUtil.criarDataMesAno(1, 1, 2005));
		configuracaoNivelCompetenciaFaixaSalarial1.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial1);

		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial2 = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarial2.setFaixaSalarial(faixaSalarial1);
		configuracaoNivelCompetenciaFaixaSalarial2.setData(DateUtil.criarDataMesAno(10,1,2015));
		configuracaoNivelCompetenciaFaixaSalarial2.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial2);

		assertEquals(configuracaoNivelCompetenciaFaixaSalarial1.getData(), configuracaoNivelCompetenciaFaixaSalarialDao.findByFaixaSalarialIdAndData(faixaSalarial1.getId(),DateUtil.criarDataMesAno(1, 1, 2005)).getData());
	}
	
	public void setConfiguracaoNivelCompetenciaFaixaSalarialDao(ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao)
	{
		this.configuracaoNivelCompetenciaFaixaSalarialDao = configuracaoNivelCompetenciaFaixaSalarialDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setNivelCompetenciaHistoricoDao(
			NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao) {
		this.nivelCompetenciaHistoricoDao = nivelCompetenciaHistoricoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}

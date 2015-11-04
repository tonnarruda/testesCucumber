package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ConfigHistoricoNivelFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.util.DateUtil;

public class ConfigHistoricoNivelDaoHibernateTest extends GenericDaoHibernateTest<ConfigHistoricoNivel>
{
	private EmpresaDao empresaDao;
	private ConfigHistoricoNivelDao configHistoricoNivelDao;
	private NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao;
	private NivelCompetenciaDao nivelCompetenciaDao;

	@Override
	public ConfigHistoricoNivel getEntity()
	{
		return ConfigHistoricoNivelFactory.getEntity();
	}

	@Override
	public GenericDao<ConfigHistoricoNivel> getGenericDao()
	{
		return configHistoricoNivelDao;
	}
	
	private NivelCompetencia nivelCompetencia(Empresa empresa, String descricao, Integer ordem, Double percentual, NivelCompetenciaHistorico nivelCompetenciaHistorico)
	{
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		nivelCompetencia.setEmpresa(empresa);
		nivelCompetencia.setDescricao(descricao);
		nivelCompetenciaDao.save(nivelCompetencia);
		
		ConfigHistoricoNivel configHistoricoNivel = new ConfigHistoricoNivel();
		configHistoricoNivel.setOrdem(ordem);
		configHistoricoNivel.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configHistoricoNivel.setNivelCompetencia(nivelCompetencia);
		configHistoricoNivel.setPercentual(percentual);
		configHistoricoNivelDao.save(configHistoricoNivel);
		
		return nivelCompetencia;
	}
	
	private NivelCompetenciaHistorico iniciaNivelCompetenciaHistorico(Empresa empresa, Date data) 
	{
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity();
		nivelCompetenciaHistorico.setData(data);
		nivelCompetenciaHistorico.setEmpresa(empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico);
		return nivelCompetenciaHistorico;
	}
	
	public void testFindByNivelCompetenciaHistoricoId()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico1 = iniciaNivelCompetenciaHistorico(empresa, DateUtil.criarDataMesAno(1, 1, 2005));
		nivelCompetencia(empresa, "Ruim", 1, 10.0, nivelCompetenciaHistorico1);
		nivelCompetencia(empresa, "Bom", 2, 50.0, nivelCompetenciaHistorico1);
		nivelCompetencia(empresa, "Ótimo", 3, 90.0, nivelCompetenciaHistorico1);
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico2 = iniciaNivelCompetenciaHistorico(empresa, DateUtil.criarDataMesAno(1, 1, 2015));
		nivelCompetencia(empresa, "Ruim", 1, 10.0, nivelCompetenciaHistorico2);
		nivelCompetencia(empresa, "Bom", 2, 50.0, nivelCompetenciaHistorico2);
		
		Collection<ConfigHistoricoNivel> configHistoricoNiveis = configHistoricoNivelDao.findByNivelCompetenciaHistoricoId(nivelCompetenciaHistorico1.getId());
		assertEquals(3, configHistoricoNiveis.size());
		
		configHistoricoNiveis = configHistoricoNivelDao.findByNivelCompetenciaHistoricoId(nivelCompetenciaHistorico2.getId());
		assertEquals(2, configHistoricoNiveis.size());
	}

	public void setConfigHistoricoNivelDao(ConfigHistoricoNivelDao configHistoricoNivelDao){
		this.configHistoricoNivelDao = configHistoricoNivelDao;
	}
	
	public void setNivelCompetenciaHistoricoDao(NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao) {
		this.nivelCompetenciaHistoricoDao = nivelCompetenciaHistoricoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setNivelCompetenciaDao(NivelCompetenciaDao nivelCompetenciaDao) {
		this.nivelCompetenciaDao = nivelCompetenciaDao;
	}
}

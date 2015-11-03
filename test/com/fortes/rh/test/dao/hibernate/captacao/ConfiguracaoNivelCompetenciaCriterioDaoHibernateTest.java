package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaCriterioDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaCriterioFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaCriterioDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoNivelCompetenciaCriterio>
{
	private ConfiguracaoNivelCompetenciaCriterioDao configuracaoNivelCompetenciaCriterioDao;
	private ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao;
	private ConhecimentoDao conhecimentoDao;
	private EmpresaDao empresaDao;
	private NivelCompetenciaDao nivelCompetenciaDao;
	private ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao;
	private NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao;
	private ConfigHistoricoNivelDao configHistoricoNivelDao;
	
	public GenericDao<ConfiguracaoNivelCompetenciaCriterio> getGenericDao() {
		return configuracaoNivelCompetenciaCriterioDao;
	}
	
	public ConfiguracaoNivelCompetenciaCriterio getEntity() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity();
		nivelCompetenciaHistorico.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		nivelCompetenciaHistorico.setEmpresa(empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		nivelCompetencia.setEmpresa(empresa);
		nivelCompetenciaDao.save(nivelCompetencia);
		
		ConfigHistoricoNivel configHistoricoNivel1 = new ConfigHistoricoNivel();
		configHistoricoNivel1.setOrdem(1);
		configHistoricoNivel1.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configHistoricoNivel1.setNivelCompetencia(nivelCompetencia);
		configHistoricoNivelDao.save(configHistoricoNivel1);
		
		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia1 = new CriterioAvaliacaoCompetencia();
		criterioAvaliacaoCompetencia1.setDescricao("Criterio");

		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia1);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		criterioAvaliacaoCompetencia1.setConhecimento(conhecimento);
		conhecimento.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetencias);
		conhecimentoDao.save(conhecimento);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = ConfiguracaoNivelCompetenciaFactory.getEntity();
		configuracaoNivelCompetencia.setCompetenciaId(conhecimento.getId());
		configuracaoNivelCompetencia.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia);
		
		ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio = ConfiguracaoNivelCompetenciaCriterioFactory.getEntity(criterioAvaliacaoCompetencia1.getId(), criterioAvaliacaoCompetencia1.getDescricao());
		configuracaoNivelCompetenciaCriterio.setConfiguracaoNivelCompetencia(configuracaoNivelCompetencia);
		configuracaoNivelCompetenciaCriterio.setNivelCompetencia(nivelCompetencia);
		
		return configuracaoNivelCompetenciaCriterio;
	}

	public void testFindByConfiguracaoNivelCompetencia() {
		ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio = getEntity();
		configuracaoNivelCompetenciaCriterioDao.save(configuracaoNivelCompetenciaCriterio);

		assertEquals(1, configuracaoNivelCompetenciaCriterioDao.findByConfiguracaoNivelCompetencia(configuracaoNivelCompetenciaCriterio.getConfiguracaoNivelCompetencia().getId()).size());
	}
	
	public void testRemoveByConfiguracaoNivelCompetenciaColaborador()
	{
		ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio = getEntity();
		configuracaoNivelCompetenciaCriterioDao.save(configuracaoNivelCompetenciaCriterio);
		
		assertEquals(1, configuracaoNivelCompetenciaCriterioDao.findByConfiguracaoNivelCompetencia(configuracaoNivelCompetenciaCriterio.getConfiguracaoNivelCompetencia().getId()).size());
		
		configuracaoNivelCompetenciaCriterioDao.removeByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaCriterio.getConfiguracaoNivelCompetencia().getConfiguracaoNivelCompetenciaColaborador().getId());
		
		assertEquals(0, configuracaoNivelCompetenciaCriterioDao.findByConfiguracaoNivelCompetencia(configuracaoNivelCompetenciaCriterio.getConfiguracaoNivelCompetencia().getId()).size());
	}

	public void setConfiguracaoNivelCompetenciaCriterioDao(
			ConfiguracaoNivelCompetenciaCriterioDao configuracaoNivelCompetenciaCriterioDao) {
		this.configuracaoNivelCompetenciaCriterioDao = configuracaoNivelCompetenciaCriterioDao;
	}

	public void setConfiguracaoNivelCompetenciaDao(
			ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao) {
		this.configuracaoNivelCompetenciaDao = configuracaoNivelCompetenciaDao;
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao) {
		this.conhecimentoDao = conhecimentoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setNivelCompetenciaDao(NivelCompetenciaDao nivelCompetenciaDao) {
		this.nivelCompetenciaDao = nivelCompetenciaDao;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorDao(
			ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao) {
		this.configuracaoNivelCompetenciaColaboradorDao = configuracaoNivelCompetenciaColaboradorDao;
	}

	public void setNivelCompetenciaHistoricoDao(NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao) {
		this.nivelCompetenciaHistoricoDao = nivelCompetenciaHistoricoDao;
	}

	public void setConfigHistoricoNivelDao(ConfigHistoricoNivelDao configHistoricoNivelDao) {
		this.configHistoricoNivelDao = configHistoricoNivelDao;
	}
}
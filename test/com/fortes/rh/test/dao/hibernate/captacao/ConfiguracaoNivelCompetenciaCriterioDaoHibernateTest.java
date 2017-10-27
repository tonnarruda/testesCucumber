package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaCriterioDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ConfigHistoricoNivelFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaCriterioFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaCriterioDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoNivelCompetenciaCriterio>
{
	private EmpresaDao empresaDao;
	private ConhecimentoDao conhecimentoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private NivelCompetenciaDao nivelCompetenciaDao;
	private ConfigHistoricoNivelDao configHistoricoNivelDao;
	private NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao;
	private ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao;
	private ConfiguracaoNivelCompetenciaCriterioDao configuracaoNivelCompetenciaCriterioDao;
	private ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao;
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	
	public GenericDao<ConfiguracaoNivelCompetenciaCriterio> getGenericDao() {
		return configuracaoNivelCompetenciaCriterioDao;
	}
	
	public ConfiguracaoNivelCompetenciaCriterio getEntity() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2015), empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(null, null, empresa);
		nivelCompetenciaDao.save(nivelCompetencia);
		
		ConfigHistoricoNivel configHistoricoNivel1 = ConfigHistoricoNivelFactory.getEntity(null, 1, nivelCompetenciaHistorico, nivelCompetencia, null);
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
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = ConfiguracaoNivelCompetenciaFactory.getEntity(nivelCompetencia, conhecimento.getId(), null, configuracaoNivelCompetenciaColaborador, null, null);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia);
		
		ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio = ConfiguracaoNivelCompetenciaCriterioFactory.getEntity(criterioAvaliacaoCompetencia1.getId(), criterioAvaliacaoCompetencia1.getDescricao());
		configuracaoNivelCompetenciaCriterio.setConfiguracaoNivelCompetencia(configuracaoNivelCompetencia);
		configuracaoNivelCompetenciaCriterio.setNivelCompetencia(nivelCompetencia);
		
		return configuracaoNivelCompetenciaCriterio;
	}

	public void testFindByConfiguracaoNivelCompetencia() {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresaDao.save(empresa);
		
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntityAndNivelCompetenciaAndNivelCOmpetenciaHistorico(1L);
		
		NivelCompetencia nivelCompetencia = configHistoricoNivel.getNivelCompetencia();
		nivelCompetenciaDao.save(nivelCompetencia);

		NivelCompetenciaHistorico nivelCompetenciaHistorico = configHistoricoNivel.getNivelCompetenciaHistorico();
		nivelCompetenciaHistorico.setEmpresa(empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico);

		configHistoricoNivelDao.save(configHistoricoNivel);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarialDao.save(faixaSalarial);

		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L, faixaSalarial, new Date() );
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);
		
		ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio = getEntity();
		configuracaoNivelCompetenciaCriterio.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetenciaCriterioDao.save(configuracaoNivelCompetenciaCriterio);
		
		assertEquals(1, configuracaoNivelCompetenciaCriterioDao.findByConfiguracaoNivelCompetencia(configuracaoNivelCompetenciaCriterio.getConfiguracaoNivelCompetencia().getId(), configuracaoNivelCompetenciaFaixaSalarial.getId()).size());
	}
	
	public void testRemoveByConfiguracaoNivelCompetenciaColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresaDao.save(empresa);
		
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntityAndNivelCompetenciaAndNivelCOmpetenciaHistorico(1L);
		
		NivelCompetencia nivelCompetencia = configHistoricoNivel.getNivelCompetencia();
		nivelCompetenciaDao.save(nivelCompetencia);

		NivelCompetenciaHistorico nivelCompetenciaHistorico = configHistoricoNivel.getNivelCompetenciaHistorico();
		nivelCompetenciaHistorico.setEmpresa(empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico);

		configHistoricoNivelDao.save(configHistoricoNivel);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarialDao.save(faixaSalarial);

		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L, faixaSalarial, new Date() );
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);
		
		ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio = getEntity();
		configuracaoNivelCompetenciaCriterio.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetenciaCriterioDao.save(configuracaoNivelCompetenciaCriterio);
		
		assertEquals(1, configuracaoNivelCompetenciaCriterioDao.findByConfiguracaoNivelCompetencia(configuracaoNivelCompetenciaCriterio.getConfiguracaoNivelCompetencia().getId(), configuracaoNivelCompetenciaFaixaSalarial.getId()).size());
		
		configuracaoNivelCompetenciaCriterioDao.removeByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaCriterio.getConfiguracaoNivelCompetencia().getConfiguracaoNivelCompetenciaColaborador().getId());
		
		assertEquals(0, configuracaoNivelCompetenciaCriterioDao.findByConfiguracaoNivelCompetencia(configuracaoNivelCompetenciaCriterio.getConfiguracaoNivelCompetencia().getId(), configuracaoNivelCompetenciaFaixaSalarial.getId()).size());
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

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialDao(
			ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao) {
		this.configuracaoNivelCompetenciaFaixaSalarialDao = configuracaoNivelCompetenciaFaixaSalarialDao;
	}
}
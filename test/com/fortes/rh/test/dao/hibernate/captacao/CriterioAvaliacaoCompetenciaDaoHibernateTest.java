package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.CriterioAvaliacaoCompetenciaDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ConfigHistoricoNivelFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;

public class CriterioAvaliacaoCompetenciaDaoHibernateTest extends GenericDaoHibernateTest<CriterioAvaliacaoCompetencia>
{
	private EmpresaDao empresaDao;
	private HabilidadeDao habilidadeDao;
	private ConhecimentoDao conhecimentoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private NivelCompetenciaDao nivelCompetenciaDao;
	private ConfigHistoricoNivelDao configHistoricoNivelDao;
	private NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao; 
	private CriterioAvaliacaoCompetenciaDao criterioAvaliacaoCompetenciaDao;
	private ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao;
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	
	public CriterioAvaliacaoCompetencia getEntity()
	{
		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia = new CriterioAvaliacaoCompetencia();
		criterioAvaliacaoCompetencia.setId(null);
		criterioAvaliacaoCompetencia.setDescricao("Criterio");
		return criterioAvaliacaoCompetencia;
	}
	
	public GenericDao<CriterioAvaliacaoCompetencia> getGenericDao()
	{
		return criterioAvaliacaoCompetenciaDao;
	}

	public void setCriterioAvaliacaoCompetenciaDao(CriterioAvaliacaoCompetenciaDao CriterioAvaliacaoCompetenciaDao)
	{
		this.criterioAvaliacaoCompetenciaDao = CriterioAvaliacaoCompetenciaDao;
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao) {
		this.conhecimentoDao = conhecimentoDao;
	}
	
	public void testFindByCompetencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia1 = new CriterioAvaliacaoCompetencia(null, "Criterio 1");

		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia1);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		criterioAvaliacaoCompetencia1.setConhecimento(conhecimento);
		conhecimento.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetencias);
		conhecimentoDao.save(conhecimento);

		assertEquals(1, criterioAvaliacaoCompetenciaDao.findByCompetencia(conhecimento.getId(), TipoCompetencia.CONHECIMENTO).size());
	}
	
	public void testRemoveByCompetencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia1 = new CriterioAvaliacaoCompetencia(null, "Criterio 1");
		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia2 = new CriterioAvaliacaoCompetencia(null, "Criterio 1");

		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia1);
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia2);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		criterioAvaliacaoCompetencia1.setConhecimento(conhecimento);
		criterioAvaliacaoCompetencia2.setConhecimento(conhecimento);
		conhecimento.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetencias);
		conhecimentoDao.save(conhecimento);
		
		criterioAvaliacaoCompetenciaDao.removeByCompetencia(conhecimento.getId(), TipoCompetencia.CONHECIMENTO, new Long[]{criterioAvaliacaoCompetencia2.getId()});
		
		assertEquals(1, criterioAvaliacaoCompetenciaDao.findByCompetencia(conhecimento.getId(), TipoCompetencia.CONHECIMENTO).size());
	}
	
	public void testExisteCriterioAvaliacaoCompetencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidade.setEmpresa(empresa);
		habilidadeDao.save(habilidade);

		assertFalse(criterioAvaliacaoCompetenciaDao.existeCriterioAvaliacaoCompetencia(empresa.getId()));
		
		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia1 = new CriterioAvaliacaoCompetencia(null, "Criterio 1");
		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia2 = new CriterioAvaliacaoCompetencia(null, "Criterio 2");

		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia1);
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia2);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setEmpresa(empresa);
		
		criterioAvaliacaoCompetencia1.setConhecimento(conhecimento);
		criterioAvaliacaoCompetencia2.setConhecimento(conhecimento);
		conhecimento.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetencias);
		conhecimentoDao.save(conhecimento);
		
		assertTrue(criterioAvaliacaoCompetenciaDao.existeCriterioAvaliacaoCompetencia(empresa.getId()));
	}
	
	public void testFindByCompetenciaAndCNCFId(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2015), empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(null, null, empresa);
		nivelCompetenciaDao.save(nivelCompetencia);
		
		ConfigHistoricoNivel configHistoricoNivel1 = ConfigHistoricoNivelFactory.getEntity(null, 1, nivelCompetenciaHistorico, nivelCompetencia, 100.0);
		configHistoricoNivelDao.save(configHistoricoNivel1);
		
		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia1 = new CriterioAvaliacaoCompetencia();
		criterioAvaliacaoCompetencia1.setDescricao("Criterio");
		criterioAvaliacaoCompetenciaDao.save(criterioAvaliacaoCompetencia1);

		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia1);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		criterioAvaliacaoCompetencia1.setConhecimento(conhecimento);
		conhecimento.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetencias);
		conhecimentoDao.save(conhecimento);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, new Date());
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = ConfiguracaoNivelCompetenciaFactory.getEntity(nivelCompetencia, conhecimento.getId(), null, configuracaoNivelCompetenciaFaixaSalarial, null, null);
		configuracaoNivelCompetencia.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia);
		
		configuracaoNivelCompetenciaFaixaSalarialDao.getHibernateTemplateByGenericDao().flush();
		
		assertEquals(1, criterioAvaliacaoCompetenciaDao.findByCompetenciaAndCNCFId(conhecimento.getId(), configuracaoNivelCompetenciaFaixaSalarial.getId(), TipoCompetencia.CONHECIMENTO).size());
	}

	public void setHabilidadeDao(HabilidadeDao habilidadeDao) {
		this.habilidadeDao = habilidadeDao;
	}

	public void setConfigHistoricoNivelDao(
			ConfigHistoricoNivelDao configHistoricoNivelDao) {
		this.configHistoricoNivelDao = configHistoricoNivelDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setConfiguracaoNivelCompetenciaDao(
			ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao) {
		this.configuracaoNivelCompetenciaDao = configuracaoNivelCompetenciaDao;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialDao(
			ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao) {
		this.configuracaoNivelCompetenciaFaixaSalarialDao = configuracaoNivelCompetenciaFaixaSalarialDao;
	}

	public void setNivelCompetenciaHistoricoDao(
			NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao) {
		this.nivelCompetenciaHistoricoDao = nivelCompetenciaHistoricoDao;
	}

	public void setNivelCompetenciaDao(NivelCompetenciaDao nivelCompetenciaDao) {
		this.nivelCompetenciaDao = nivelCompetenciaDao;
	}
	
}
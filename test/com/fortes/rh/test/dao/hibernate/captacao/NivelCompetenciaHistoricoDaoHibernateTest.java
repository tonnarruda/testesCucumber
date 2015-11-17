package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.util.DateUtil;

public class NivelCompetenciaHistoricoDaoHibernateTest extends GenericDaoHibernateTest<NivelCompetenciaHistorico>
{
	private EmpresaDao empresaDao;
	private NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao;
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;

	public NivelCompetenciaHistorico getEntity()
	{
		return NivelCompetenciaHistoricoFactory.getEntity();
	}
	
	public void testExisteDependenciaComCompetenciasDaFaixaSalarial()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2015), empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico);

		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);
		
		assertTrue(nivelCompetenciaHistoricoDao.existeDependenciaComCompetenciasDaFaixaSalarial(nivelCompetenciaHistorico.getId()));
	}
	
	public void testFindByData()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico1 = NivelCompetenciaHistoricoFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2000), empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico1);
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico2 = NivelCompetenciaHistoricoFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2005), empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico2);
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico3 = NivelCompetenciaHistoricoFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2015), empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico3);
		
		Long nivelCompetenciaHistoricoId = nivelCompetenciaHistoricoDao.findByData(new Date(), empresa.getId());
		
		assertEquals(nivelCompetenciaHistorico3.getId(), nivelCompetenciaHistoricoId);
	} 

	public GenericDao<NivelCompetenciaHistorico> getGenericDao()
	{
		return nivelCompetenciaHistoricoDao;
	}

	public void setNivelCompetenciaHistoricoDao(NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao)
	{
		this.nivelCompetenciaHistoricoDao = nivelCompetenciaHistoricoDao;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialDao(
			ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao) {
		this.configuracaoNivelCompetenciaFaixaSalarialDao = configuracaoNivelCompetenciaFaixaSalarialDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}

package com.fortes.rh.test.dao.hibernate.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;

public class NivelCompetenciaHistoricoDaoHibernateTest extends DaoHibernateAnnotationTest
{
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao;
	@Autowired
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	@Autowired
	private CargoDao cargoDao;
	@Autowired
	private FaixaSalarialDao faixaSalarialDao;
	
	@Test
	public void testDependenciaComCompetenciasDaFaixaSalarial()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2015), empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico);

		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarial.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);
	
		Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> configuracaoNivelCompetenciaFaixaSalariais = nivelCompetenciaHistoricoDao.dependenciaComCompetenciasDaFaixaSalarial(nivelCompetenciaHistorico.getId());
		
		assertTrue(configuracaoNivelCompetenciaFaixaSalariais.size() > 0);
	}
	
	@Test
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
}

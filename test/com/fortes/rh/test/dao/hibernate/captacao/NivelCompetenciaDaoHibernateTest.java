package com.fortes.rh.test.dao.hibernate.captacao;


import java.util.Arrays;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class NivelCompetenciaDaoHibernateTest extends GenericDaoHibernateTest<NivelCompetencia>
{
	private NivelCompetenciaDao nivelCompetenciaDao;
	private NivelCompetenciaFaixaSalarialDao nivelCompetenciaFaixaSalarialDao;
	private EmpresaDao empresaDao;
	private CargoDao cargoDao;
	private ConhecimentoDao conhecimentoDao;
	private HabilidadeDao habilidadeDao;
	private AtitudeDao atitudeDao;
	private FaixaSalarialDao faixaSalarialDao;

	@Override
	public NivelCompetencia getEntity()
	{
		return NivelCompetenciaFactory.getEntity();
	}

	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity();
		nivelRuim.setDescricao("Ruim");
		nivelRuim.setOrdem(1);
		nivelRuim.setEmpresa(empresa1);
		nivelCompetenciaDao.save(nivelRuim);

		NivelCompetencia nivelRegular = NivelCompetenciaFactory.getEntity();
		nivelRegular.setDescricao("Regular");
		nivelRegular.setOrdem(2);
		nivelRegular.setEmpresa(empresa1);
		nivelCompetenciaDao.save(nivelRegular);

		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity();
		nivelBom.setDescricao("Bom");
		nivelBom.setOrdem(3);
		nivelBom.setEmpresa(empresa1);
		nivelCompetenciaDao.save(nivelBom);
		
		NivelCompetencia nivelBomEmpresa2 = NivelCompetenciaFactory.getEntity();
		nivelBomEmpresa2.setDescricao("Bom");
		nivelBomEmpresa2.setOrdem(3);
		nivelBomEmpresa2.setEmpresa(empresa2);
		nivelCompetenciaDao.save(nivelBomEmpresa2);
		
		assertEquals(3, nivelCompetenciaDao.findAllSelect(empresa1.getId()).size());
		assertTrue(nivelCompetenciaDao.findAllSelect(null).size() >= 4);
	}
	
	public void testFindByFaixa()
	{
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);
		
		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidadeDao.save(habilidade);
		
		Atitude atitude = AtitudeFactory.getEntity();
		atitudeDao.save(atitude);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setConhecimentos(Arrays.asList(conhecimento));
		cargo.setHabilidades(Arrays.asList(habilidade));
		cargo.setAtitudes(Arrays.asList(atitude));
		cargoDao.save(cargo);
		
		assertEquals(cargo, cargoDao.findByIdAllProjection(cargo.getId()));//SQL MEGA POWER GAMBIIII
		assertEquals(3, nivelCompetenciaDao.findByFaixa(cargo.getId()).size());
	}
	
	public void testDeleteConfiguracaoByFaixa()
	{
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);
		
		Atitude atitude = AtitudeFactory.getEntity();
		atitudeDao.save(atitude);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial1);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial2);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		nivelCompetenciaDao.save(nivelCompetencia);
		
		NivelCompetenciaFaixaSalarial nivelCompetenciaFaixaSalarial1 = new NivelCompetenciaFaixaSalarial();
		nivelCompetenciaFaixaSalarial1.setFaixaSalarial(faixaSalarial1);
		nivelCompetenciaFaixaSalarial1.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaFaixaSalarial1.setCompetenciaId(atitude.getId());
		nivelCompetenciaFaixaSalarial1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		nivelCompetenciaFaixaSalarialDao.save(nivelCompetenciaFaixaSalarial1);
		
		NivelCompetenciaFaixaSalarial nivelCompetenciaFaixaSalarial2 = new NivelCompetenciaFaixaSalarial();
		nivelCompetenciaFaixaSalarial2.setFaixaSalarial(faixaSalarial1);
		nivelCompetenciaFaixaSalarial2.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaFaixaSalarial2.setCompetenciaId(conhecimento.getId());
		nivelCompetenciaFaixaSalarial2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		nivelCompetenciaFaixaSalarialDao.save(nivelCompetenciaFaixaSalarial2);
		
		NivelCompetenciaFaixaSalarial nivelCompetenciaFaixaSalarialDiferente = new NivelCompetenciaFaixaSalarial();
		nivelCompetenciaFaixaSalarialDiferente.setFaixaSalarial(faixaSalarial2);
		nivelCompetenciaFaixaSalarialDiferente.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaFaixaSalarialDiferente.setCompetenciaId(conhecimento.getId());
		nivelCompetenciaFaixaSalarialDiferente.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		nivelCompetenciaFaixaSalarialDao.save(nivelCompetenciaFaixaSalarialDiferente);
		
		assertEquals(2, nivelCompetenciaFaixaSalarialDao.findByFaixa(faixaSalarial1.getId()).size());
		
		nivelCompetenciaFaixaSalarialDao.deleteConfiguracaoByFaixa(faixaSalarial1.getId());

		assertEquals(0, nivelCompetenciaFaixaSalarialDao.findByFaixa(faixaSalarial1.getId()).size());
	}

	@Override
	public GenericDao<NivelCompetencia> getGenericDao()
	{
		return nivelCompetenciaDao;
	}

	public void setNivelCompetenciaDao(NivelCompetenciaDao nivelCompetenciaDao)
	{
		this.nivelCompetenciaDao = nivelCompetenciaDao;
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao) {
		this.conhecimentoDao = conhecimentoDao;
	}

	public void setHabilidadeDao(HabilidadeDao habilidadeDao) {
		this.habilidadeDao = habilidadeDao;
	}

	public void setAtitudeDao(AtitudeDao atitudeDao) {
		this.atitudeDao = atitudeDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setNivelCompetenciaFaixaSalarialDao(NivelCompetenciaFaixaSalarialDao nivelCompetenciaFaixaSalarialDao) {
		this.nivelCompetenciaFaixaSalarialDao = nivelCompetenciaFaixaSalarialDao;
	}
}

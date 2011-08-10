package com.fortes.rh.test.dao.hibernate.captacao;


import java.util.Arrays;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class NivelCompetenciaDaoHibernateTest extends GenericDaoHibernateTest<NivelCompetencia>
{
	private NivelCompetenciaDao nivelCompetenciaDao;
	private ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao;
	private EmpresaDao empresaDao;
	private CargoDao cargoDao;
	private ConhecimentoDao conhecimentoDao;
	private HabilidadeDao habilidadeDao;
	private AtitudeDao atitudeDao;
	private FaixaSalarialDao faixaSalarialDao;
	private CandidatoDao candidatoDao;
	private ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao;

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
		assertEquals(3, nivelCompetenciaDao.findByCargoOrEmpresa(cargo.getId(), null).size());
	}
	
	public void testFindByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setEmpresa(empresa);
		conhecimentoDao.save(conhecimento);
		
		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidade.setEmpresa(empresa);
		habilidadeDao.save(habilidade);
		
		Atitude atitude = AtitudeFactory.getEntity();
		atitude.setEmpresa(empresa);
		atitudeDao.save(atitude);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setConhecimentos(Arrays.asList(conhecimento));
		cargo.setHabilidades(Arrays.asList(habilidade));
		cargo.setAtitudes(Arrays.asList(atitude));
		cargoDao.save(cargo);
		
		assertEquals(cargo, cargoDao.findByIdAllProjection(cargo.getId()));//SQL MEGA POWER GAMBIIII
		assertEquals(3, nivelCompetenciaDao.findByCargoOrEmpresa(null, empresa.getId()).size());
	}
	
	public void testNivelCompetenciaFaixaFindByFaixa()
	{
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);
		
		Atitude atitude = AtitudeFactory.getEntity();
		atitudeDao.save(atitude);
		
		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato1);

		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato2);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		nivelCompetenciaDao.save(nivelCompetencia);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaCandidato1 = new ConfiguracaoNivelCompetencia();
		nivelCompetenciaCandidato1.setCandidato(candidato1);
		nivelCompetenciaCandidato1.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaCandidato1.setCompetenciaId(atitude.getId());
		nivelCompetenciaCandidato1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(nivelCompetenciaCandidato1);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaCandidato2 = new ConfiguracaoNivelCompetencia();
		nivelCompetenciaCandidato2.setCandidato(candidato1);
		nivelCompetenciaCandidato2.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaCandidato2.setCompetenciaId(conhecimento.getId());
		nivelCompetenciaCandidato2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(nivelCompetenciaCandidato2);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaCandidatoDiferente = new ConfiguracaoNivelCompetencia();
		nivelCompetenciaCandidatoDiferente.setCandidato(candidato2);
		nivelCompetenciaCandidatoDiferente.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaCandidatoDiferente.setCompetenciaId(conhecimento.getId());
		nivelCompetenciaCandidatoDiferente.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(nivelCompetenciaCandidatoDiferente);
		
		assertEquals(2, configuracaoNivelCompetenciaDao.findByCandidato(candidato1.getId()).size());
	}
	
	public void testFindByColaborador()
	{
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimentoDao.save(conhecimento);
		
		Atitude atitude = AtitudeFactory.getEntity();
		atitudeDao.save(atitude);
		
		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato1);
		
		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato2);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		nivelCompetenciaDao.save(nivelCompetencia);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaCandidato1 = new ConfiguracaoNivelCompetencia();
		nivelCompetenciaCandidato1.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		nivelCompetenciaCandidato1.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaCandidato1.setCompetenciaId(atitude.getId());
		nivelCompetenciaCandidato1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(nivelCompetenciaCandidato1);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaCandidato2 = new ConfiguracaoNivelCompetencia();
		nivelCompetenciaCandidato2.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		nivelCompetenciaCandidato2.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaCandidato2.setCompetenciaId(conhecimento.getId());
		nivelCompetenciaCandidato2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(nivelCompetenciaCandidato2);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaCandidatoDiferente = new ConfiguracaoNivelCompetencia();
		nivelCompetenciaCandidatoDiferente.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaCandidatoDiferente.setCompetenciaId(conhecimento.getId());
		nivelCompetenciaCandidatoDiferente.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(nivelCompetenciaCandidatoDiferente);
		
		assertEquals(2, configuracaoNivelCompetenciaDao.findByColaborador(configuracaoNivelCompetenciaColaborador.getId()).size());
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
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial1);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia1);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia2.setFaixaSalarial(faixaSalarial1);
		configuracaoNivelCompetencia2.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia2.setCompetenciaId(conhecimento.getId());
		configuracaoNivelCompetencia2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia2);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaDiferente = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetenciaDiferente.setFaixaSalarial(faixaSalarial2);
		configuracaoNivelCompetenciaDiferente.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetenciaDiferente.setCompetenciaId(conhecimento.getId());
		configuracaoNivelCompetenciaDiferente.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetenciaDiferente);
		
		assertEquals(2, configuracaoNivelCompetenciaDao.findByFaixa(faixaSalarial1.getId()).size());
		
		configuracaoNivelCompetenciaDao.deleteConfiguracaoByFaixa(faixaSalarial1.getId());

		assertEquals(0, configuracaoNivelCompetenciaDao.findByFaixa(faixaSalarial1.getId()).size());
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

	public void setConfiguracaoNivelCompetenciaDao(ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao) {
		this.configuracaoNivelCompetenciaDao = configuracaoNivelCompetenciaDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao) {
		this.candidatoDao = candidatoDao;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorDao(ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao) {
		this.configuracaoNivelCompetenciaColaboradorDao = configuracaoNivelCompetenciaColaboradorDao;
	}
}

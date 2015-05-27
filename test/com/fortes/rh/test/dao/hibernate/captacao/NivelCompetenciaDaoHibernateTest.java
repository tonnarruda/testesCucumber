package com.fortes.rh.test.dao.hibernate.captacao;


import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.util.DateUtil;

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
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	private ColaboradorManager colaboradorManager;
	private ColaboradorDao colaboradorDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private EstabelecimentoDao estabelecimentoDao;
	private CursoDao cursoDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;

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
	
	
	public void testRemoveByConfiguracaoNivelColaborador()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		NivelCompetencia nivel = NivelCompetenciaFactory.getEntity();
		nivel.setEmpresa(empresa1);
		nivelCompetenciaDao.save(nivel);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador);
		
		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidadeDao.save(habilidade);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia.setNivelCompetencia(nivel);
		configuracaoNivelCompetencia.setCompetenciaId(habilidade.getId());
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia);
		
		Collection<ConfiguracaoNivelCompetencia> configs = configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		assertEquals(1, configs.size());
		
		configuracaoNivelCompetenciaDao.deleteConfiguracaoNivelCompetenciaByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		
		configs = configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		assertEquals(0, configs.size());
	}
	
	public void testRemoveByFaixas()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		NivelCompetencia nivel = NivelCompetenciaFactory.getEntity();
		nivel.setEmpresa(empresa1);
		nivelCompetenciaDao.save(nivel);
		
		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidadeDao.save(habilidade);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia.setNivelCompetencia(nivel);
		configuracaoNivelCompetencia.setCompetenciaId(habilidade.getId());
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia);

		Collection<ConfiguracaoNivelCompetencia> configs = configuracaoNivelCompetenciaDao.findByFaixa(faixaSalarial.getId(), null);
		assertEquals(1, configs.size());
		
		configuracaoNivelCompetenciaDao.removeByFaixas(new Long[]{faixaSalarial.getId()});
		
		configs = configuracaoNivelCompetenciaDao.findByFaixa(faixaSalarial.getId(), null);
		assertEquals(0, configs.size());
	}
	
	public void testRemoveColaborador()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		NivelCompetencia nivel = NivelCompetenciaFactory.getEntity();
		nivel.setEmpresa(empresa1);
		nivelCompetenciaDao.save(nivel);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidadeDao.save(habilidade);

		ConfiguracaoNivelCompetenciaColaborador configNivelCompetenciaColaborador1 = new ConfiguracaoNivelCompetenciaColaborador();
		configNivelCompetenciaColaborador1.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaboradorDao.save(configNivelCompetenciaColaborador1);
		
		ConfiguracaoNivelCompetencia configNivelComp1 = new ConfiguracaoNivelCompetencia();
		configNivelComp1.setNivelCompetencia(nivel);
		configNivelComp1.setCompetenciaId(habilidade.getId());
		configNivelComp1.setConfiguracaoNivelCompetenciaColaborador(configNivelCompetenciaColaborador1);
		configuracaoNivelCompetenciaDao.save(configNivelComp1);

		ConfiguracaoNivelCompetencia configNivelComp2 = new ConfiguracaoNivelCompetencia();
		configNivelComp2.setNivelCompetencia(nivel);
		configNivelComp2.setCompetenciaId(habilidade.getId());
		configNivelComp2.setConfiguracaoNivelCompetenciaColaborador(configNivelCompetenciaColaborador1);
		configuracaoNivelCompetenciaDao.save(configNivelComp2);
		
		ConfiguracaoNivelCompetenciaColaborador configNivelCompetenciaColaborador2 = new ConfiguracaoNivelCompetenciaColaborador();
		configNivelCompetenciaColaborador2.setColaborador(colaborador);
		configuracaoNivelCompetenciaColaboradorDao.save(configNivelCompetenciaColaborador2);
		
		ConfiguracaoNivelCompetencia configNivelComp3 = new ConfiguracaoNivelCompetencia();
		configNivelComp3.setNivelCompetencia(nivel);
		configNivelComp3.setCompetenciaId(habilidade.getId());
		configNivelComp3.setConfiguracaoNivelCompetenciaColaborador(configNivelCompetenciaColaborador2);
		configuracaoNivelCompetenciaDao.save(configNivelComp3);
		
		configuracaoNivelCompetenciaDao.removeColaborador(colaborador);
		
		Collection<ConfiguracaoNivelCompetencia> configs = configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaColaborador(configNivelCompetenciaColaborador2.getId());
		assertEquals(0, configs.size());

		configs = configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaColaborador(configNivelCompetenciaColaborador1.getId());
		assertEquals(0, configs.size());
	}
	
	public void testRemoveByCandidato() {
		
		NivelCompetencia nivel = NivelCompetenciaFactory.getEntity();
		nivelCompetenciaDao.save(nivel);
		
		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidadeDao.save(habilidade);

		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato1);
		
		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato2);
		
		ConfiguracaoNivelCompetencia configNivelComp1 = new ConfiguracaoNivelCompetencia();
		configNivelComp1.setNivelCompetencia(nivel);
		configNivelComp1.setCandidato(candidato1);
		configNivelComp1.setCompetenciaId(habilidade.getId());
		configuracaoNivelCompetenciaDao.save(configNivelComp1);

		ConfiguracaoNivelCompetencia configNivelComp2 = new ConfiguracaoNivelCompetencia();
		configNivelComp2.setNivelCompetencia(nivel);
		configNivelComp2.setCandidato(candidato2);
		configNivelComp2.setCompetenciaId(habilidade.getId());
		configuracaoNivelCompetenciaDao.save(configNivelComp2);
		
		assertEquals(1,configuracaoNivelCompetenciaDao.findByCandidato(candidato1.getId()).size());
		assertEquals(1, configuracaoNivelCompetenciaDao.findByCandidato(candidato2.getId()).size());
		
		configuracaoNivelCompetenciaDao.removeByCandidato(candidato1.getId());
		
		assertEquals(0,configuracaoNivelCompetenciaDao.findByCandidato(candidato1.getId()).size());
		assertEquals(1, configuracaoNivelCompetenciaDao.findByCandidato(candidato2.getId()).size());
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
	
	public void testFindByConfiguracaoNivelCompetenciaColaborador()
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
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = criaConfiguracaoNivelCompetencia(atitude.getId(), nivelCompetencia, configuracaoNivelCompetenciaColaborador, TipoCompetencia.ATITUDE);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = criaConfiguracaoNivelCompetencia(conhecimento.getId(), nivelCompetencia, configuracaoNivelCompetenciaColaborador, TipoCompetencia.CONHECIMENTO);
		criaConfiguracaoNivelCompetencia(conhecimento.getId(), nivelCompetencia, null, TipoCompetencia.CONHECIMENTO);
		
		Collection<ConfiguracaoNivelCompetencia> configuracoesNivelCompetencia = configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		
		assertEquals(2, configuracoesNivelCompetencia.size());
		assertEquals(configuracaoNivelCompetencia1.getId(), ((ConfiguracaoNivelCompetencia) (configuracoesNivelCompetencia.toArray()[0])).getId());
		assertEquals(configuracaoNivelCompetencia2.getId(), ((ConfiguracaoNivelCompetencia) (configuracoesNivelCompetencia.toArray()[1])).getId());
		
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
		
		assertEquals(2, configuracaoNivelCompetenciaDao.findByFaixa(faixaSalarial1.getId(), null).size());
		
		configuracaoNivelCompetenciaDao.deleteConfiguracaoByFaixa(faixaSalarial1.getId());

		assertEquals(0, configuracaoNivelCompetenciaDao.findByFaixa(faixaSalarial1.getId(), null).size());
	}
	
	public void testFindByFaixaSalarial()
	{
		Conhecimento conhecimento = criaConhecimento();
		
		Atitude atitude = criaAtitude();
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		NivelCompetencia nivelCompetencia1 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia1.setDescricao("bom");
		nivelCompetenciaDao.save(nivelCompetencia1);

		NivelCompetencia nivelCompetencia2 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia2.setDescricao("dificil");
		nivelCompetenciaDao.save(nivelCompetencia2);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixaSalarial1 = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		cncFaixaSalarial1.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		cncFaixaSalarial1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(cncFaixaSalarial1);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixaSalarial2 = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		cncFaixaSalarial2.setData(DateUtil.criarDataMesAno(1, 2, 2015));
		cncFaixaSalarial2.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(cncFaixaSalarial2);

		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia1);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetencia1.setConfiguracaoNivelCompetenciaFaixaSalarial(cncFaixaSalarial2);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia1);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia2.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia2.setNivelCompetencia(nivelCompetencia2);
		configuracaoNivelCompetencia2.setCompetenciaId(conhecimento.getId());
		configuracaoNivelCompetencia2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetencia2.setConfiguracaoNivelCompetenciaFaixaSalarial(cncFaixaSalarial1);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia2);
		
		configuracaoNivelCompetenciaDao.findByFaixa(faixaSalarial.getId(), null);//gambi do SQL, precisa disso só para a proxima consulta
		
		Collection<ConfiguracaoNivelCompetencia> competenciasDaFaixa = configuracaoNivelCompetenciaDao.findCompetenciaByFaixaSalarial(faixaSalarial.getId(), null);
		assertEquals(1, competenciasDaFaixa.size());
		
		ConfiguracaoNivelCompetencia competenciaAtitude = (ConfiguracaoNivelCompetencia)competenciasDaFaixa.toArray()[0]; 
		assertEquals(configuracaoNivelCompetencia1.getId(), competenciaAtitude.getId());
		assertEquals("atividade (bom)", competenciaAtitude.getCompetenciaDescricaoNivel());
	}
	
	public void testFindCompetenciaColaborador()
	{
		Conhecimento conhecimento = criaConhecimento();
		
		Atitude atitude = criaAtitude();
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		NivelCompetencia nivelCompetencia1 = criaNivelCompetencia("Bom", 4);
		NivelCompetencia nivelCompetencia2 = criaNivelCompetencia("Péssimo", 1);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "pedro", "pedro", "0123");
		colaboradorManager.save(colaborador);
		
		ConfiguracaoNivelCompetenciaColaborador configColaborador = criaConfiguracaoNivelCompetenciaColaborador(faixaSalarial, colaborador, DateUtil.criarDataMesAno(17, 8, 2011), null, null);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = criaConfiguracaoNivelCompetencia(atitude.getId(), nivelCompetencia1, configColaborador, TipoCompetencia.ATITUDE);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = criaConfiguracaoNivelCompetencia(conhecimento.getId(), nivelCompetencia2, configColaborador, TipoCompetencia.CONHECIMENTO);
		
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2010);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2015);
		
		configuracaoNivelCompetenciaDao.findByFaixa(faixaSalarial.getId(), null); // Arranjo para teste de consulta SQL
		Collection<ConfiguracaoNivelCompetencia> configs = configuracaoNivelCompetenciaDao.findCompetenciaColaborador(dataIni, dataFim, new Long[] { configuracaoNivelCompetencia1.getId(), configuracaoNivelCompetencia2.getId() }, faixaSalarial.getId(), true);
		assertEquals(2, configs.size());
		assertEquals("atividade", ((ConfiguracaoNivelCompetencia)configs.toArray()[0]).getCompetenciaDescricao());
		assertEquals("esporte", ((ConfiguracaoNivelCompetencia)configs.toArray()[1]).getCompetenciaDescricao());
	}
	
	public void testFindCompetenciaCandidato()
	{
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setNome("java");
		conhecimentoDao.save(conhecimento);
		
		Atitude atitude = AtitudeFactory.getEntity();
		atitude.setNome("proativo");
		atitudeDao.save(atitude);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarialDao.save(faixaSalarial);
		
		NivelCompetencia nivel1 = NivelCompetenciaFactory.getEntity();
		nivel1.setDescricao("pessimo");
		nivel1.setOrdem(1);
		nivelCompetenciaDao.save(nivel1);

		NivelCompetencia nivel2 = NivelCompetenciaFactory.getEntity();
		nivel2.setDescricao("bom");
		nivel2.setOrdem(2);
		nivelCompetenciaDao.save(nivel2);

		Candidato joao = CandidatoFactory.getCandidato();
		joao.setNome("joao");
		candidatoDao.save(joao);

		Candidato jose = CandidatoFactory.getCandidato();
		jose.setNome("jose");
		candidatoDao.save(jose);
		
		Collection<Long> candidatosIds = Arrays.asList(joao.getId(), jose.getId());
		
		ConfiguracaoNivelCompetencia configFaixa1 = new ConfiguracaoNivelCompetencia();
		configFaixa1.setFaixaSalarial(faixaSalarial);
		configFaixa1.setNivelCompetencia(nivel1);
		configFaixa1.setCompetenciaId(atitude.getId());
		configFaixa1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(configFaixa1);

		ConfiguracaoNivelCompetencia configFaixa2 = new ConfiguracaoNivelCompetencia();
		configFaixa2.setFaixaSalarial(faixaSalarial);
		configFaixa2.setNivelCompetencia(nivel2);
		configFaixa2.setCompetenciaId(conhecimento.getId());
		configFaixa2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configFaixa2);
		
		ConfiguracaoNivelCompetencia configJoao1 = new ConfiguracaoNivelCompetencia();
		configJoao1.setFaixaSalarial(faixaSalarial);
		configJoao1.setCandidato(joao);
		configJoao1.setNivelCompetencia(nivel1);
		configJoao1.setCompetenciaId(conhecimento.getId());
		configJoao1.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configJoao1);

		ConfiguracaoNivelCompetencia configJoao2 = new ConfiguracaoNivelCompetencia();
		configJoao2.setFaixaSalarial(faixaSalarial);
		configJoao2.setCandidato(joao);
		configJoao2.setNivelCompetencia(nivel2);
		configJoao2.setCompetenciaId(atitude.getId());
		configJoao2.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(configJoao2);

		ConfiguracaoNivelCompetencia configJose1 = new ConfiguracaoNivelCompetencia();
		configJose1.setFaixaSalarial(faixaSalarial);
		configJose1.setCandidato(jose);
		configJose1.setNivelCompetencia(nivel2);
		configJose1.setCompetenciaId(atitude.getId());
		configJose1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(configJose1);
		
		configuracaoNivelCompetenciaDao.findByFaixa(faixaSalarial.getId(), null); // Arranjo para teste de consulta SQL
		Collection<ConfiguracaoNivelCompetencia> configs = configuracaoNivelCompetenciaDao.findCompetenciaCandidato(faixaSalarial.getId(), candidatosIds);
		
		assertEquals(5, configs.size());
		//a ordem é importante para o manager
		assertNull("faixa 2", ((ConfiguracaoNivelCompetencia)configs.toArray()[1]).getCandidato());
		assertNull("faixa 1", ((ConfiguracaoNivelCompetencia)configs.toArray()[0]).getCandidato());
		assertEquals("joao", ((ConfiguracaoNivelCompetencia)configs.toArray()[2]).getCandidato().getNome());
		assertEquals("joao", ((ConfiguracaoNivelCompetencia)configs.toArray()[3]).getCandidato().getNome());
		assertEquals("jose", ((ConfiguracaoNivelCompetencia)configs.toArray()[4]).getCandidato().getNome());
	}
	
	public void testFindCompetenciasIdsConfiguradasByFaixaSolicitacao()
	{
		Conhecimento conhecimento = criaConhecimento();
		
		Atitude atitude = criaAtitude();
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		NivelCompetencia nivelCompetencia1 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia1.setDescricao("bom");
		nivelCompetenciaDao.save(nivelCompetencia1);

		NivelCompetencia nivelCompetencia2 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia2.setDescricao("dificil");
		nivelCompetenciaDao.save(nivelCompetencia2);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia1);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia1);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia2.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia2.setNivelCompetencia(nivelCompetencia2);
		configuracaoNivelCompetencia2.setCompetenciaId(conhecimento.getId());
		configuracaoNivelCompetencia2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia2);
		
		Long[] competenciasIds = configuracaoNivelCompetenciaDao.findCompetenciasIdsConfiguradasByFaixaSolicitacao(faixaSalarial.getId());
		assertEquals(2, competenciasIds.length);
	}
	
	public void testSomaConfiguracoesByFaixa()
	{
		Conhecimento conhecimento = criaConhecimento();
		
		Atitude atitude = criaAtitude();
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial2);
		
		NivelCompetencia nivelCompetencia1 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia1.setDescricao("bom");
		nivelCompetencia1.setOrdem(7);
		nivelCompetenciaDao.save(nivelCompetencia1);
		
		NivelCompetencia nivelCompetencia2 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia2.setDescricao("dificil");
		nivelCompetencia2.setOrdem(5);
		nivelCompetenciaDao.save(nivelCompetencia2);
		
		NivelCompetencia nivelCompetencia3 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia3.setDescricao("impossivel");
		nivelCompetencia3.setOrdem(1);
		nivelCompetenciaDao.save(nivelCompetencia3);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia1);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia1);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia2.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia2.setNivelCompetencia(nivelCompetencia2);
		configuracaoNivelCompetencia2.setCompetenciaId(conhecimento.getId());
		configuracaoNivelCompetencia2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia2);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia3 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia3.setFaixaSalarial(faixaSalarial2);
		configuracaoNivelCompetencia3.setNivelCompetencia(nivelCompetencia3);
		configuracaoNivelCompetencia3.setCompetenciaId(conhecimento.getId());
		configuracaoNivelCompetencia3.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia3);
		
		Integer total = configuracaoNivelCompetenciaDao.somaConfiguracoesByFaixa(faixaSalarial.getId());
		assertEquals(new Integer(12), total);

		total = configuracaoNivelCompetenciaDao.somaConfiguracoesByFaixa(faixaSalarial2.getId());
		assertEquals(new Integer(1), total);
	}

	public void testFindByColaborador()
	{
		Conhecimento conhecimento = criaConhecimento();
		
		Atitude atitude = criaAtitude();
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		NivelCompetencia nivelCompetencia1 = criaNivelCompetencia("Bom", 4);
		NivelCompetencia nivelCompetencia2 = criaNivelCompetencia("Péssimo", 1);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "Pedro", "pedro", "0123");
		colaboradorManager.save(colaborador);
		
		Colaborador avaliador = ColaboradorFactory.getEntity(2L, "Maria", "pedro", "0123");
		colaboradorManager.save(avaliador);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ConfiguracaoNivelCompetenciaColaborador configColaborador = criaConfiguracaoNivelCompetenciaColaborador(faixaSalarial, colaborador, DateUtil.criarDataMesAno(17, 8, 2011), null, null);
		ConfiguracaoNivelCompetenciaColaborador configColaborador2 = criaConfiguracaoNivelCompetenciaColaborador(faixaSalarial, colaborador, DateUtil.criarDataMesAno(16, 8, 2011), null, null);
		
		criaConfiguracaoNivelCompetencia(atitude.getId(), nivelCompetencia1, configColaborador, TipoCompetencia.ATITUDE);
		criaConfiguracaoNivelCompetencia(conhecimento.getId(), nivelCompetencia2, configColaborador, TipoCompetencia.CONHECIMENTO);
		criaConfiguracaoNivelCompetencia(atitude.getId(), nivelCompetencia1, configColaborador2, TipoCompetencia.ATITUDE);

		Collection<ConfiguracaoNivelCompetencia> configs = configuracaoNivelCompetenciaDao.findByColaborador(colaborador.getId(), avaliador.getId(), colaboradorQuestionario.getId());
		assertEquals(2, configs.size());
		assertEquals(atitude.getId(), ((ConfiguracaoNivelCompetencia)configs.toArray()[0]).getCompetenciaId());
		assertEquals(conhecimento.getId(), ((ConfiguracaoNivelCompetencia)configs.toArray()[1]).getCompetenciaId());
	}
	
	public void testFindByColaboradorComAvaliador()
	{
		Conhecimento conhecimento = criaConhecimento();
		
		Atitude atitude = criaAtitude();
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		NivelCompetencia nivelCompetencia1 = criaNivelCompetencia("Bom", 4);
		NivelCompetencia nivelCompetencia2 = criaNivelCompetencia("Péssimo", 1);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "Pedro", "pedro", "0123");
		colaboradorManager.save(colaborador);
		
		Colaborador avaliador = ColaboradorFactory.getEntity(2L, "Maria", "pedro", "0123");
		colaboradorManager.save(avaliador);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ConfiguracaoNivelCompetenciaColaborador configColaborador = criaConfiguracaoNivelCompetenciaColaborador(faixaSalarial, colaborador, DateUtil.criarDataMesAno(17, 8, 2011), avaliador, colaboradorQuestionario );
		ConfiguracaoNivelCompetenciaColaborador configColaborador2 = criaConfiguracaoNivelCompetenciaColaborador(faixaSalarial, colaborador, DateUtil.criarDataMesAno(16, 8, 2011), avaliador, colaboradorQuestionario );
		
		criaConfiguracaoNivelCompetencia(atitude.getId(), nivelCompetencia1, configColaborador, TipoCompetencia.ATITUDE);
		criaConfiguracaoNivelCompetencia(conhecimento.getId(), nivelCompetencia2, configColaborador, TipoCompetencia.CONHECIMENTO);
		criaConfiguracaoNivelCompetencia(atitude.getId(), nivelCompetencia1, configColaborador2, TipoCompetencia.ATITUDE);

		Collection<ConfiguracaoNivelCompetencia> configs = configuracaoNivelCompetenciaDao.findByColaborador(colaborador.getId(), avaliador.getId(), colaboradorQuestionario.getId());
		assertEquals(2, configs.size());
		assertEquals(atitude.getId(), ((ConfiguracaoNivelCompetencia)configs.toArray()[0]).getCompetenciaId());
		assertEquals(conhecimento.getId(), ((ConfiguracaoNivelCompetencia)configs.toArray()[1]).getCompetenciaId());
	}

	private NivelCompetencia criaNivelCompetencia(String descricao, Integer ordem)
	{
		NivelCompetencia nivelCompetencia1 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia1.setDescricao("bom");
		nivelCompetencia1.setOrdem(4);
		nivelCompetenciaDao.save(nivelCompetencia1);
		return nivelCompetencia1;
	}

	private Atitude criaAtitude()
	{
		Atitude atitude = AtitudeFactory.getEntity();
		atitude.setNome("atividade");
		atitudeDao.save(atitude);
		return atitude;
	}

	private Conhecimento criaConhecimento()
	{
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setNome("esporte");
		conhecimentoDao.save(conhecimento);
		return conhecimento;
	}

	private ConfiguracaoNivelCompetenciaColaborador criaConfiguracaoNivelCompetenciaColaborador(FaixaSalarial faixaSalarial, Colaborador colaborador, Date data, Colaborador avaliador, ColaboradorQuestionario colabQuestionario)
	{
		ConfiguracaoNivelCompetenciaColaborador configColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		configColaborador.setColaborador(colaborador);
		configColaborador.setFaixaSalarial(faixaSalarial);
		configColaborador.setData(data);
		configColaborador.setColaboradorQuestionario(colabQuestionario);
		configuracaoNivelCompetenciaColaboradorDao.save(configColaborador);
		return configColaborador;
	}

	private ConfiguracaoNivelCompetencia criaConfiguracaoNivelCompetencia(Long competenciaId, NivelCompetencia nivelCompetencia, ConfiguracaoNivelCompetenciaColaborador configColaborador, Character tipoCopetencia)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(configColaborador);
		configuracaoNivelCompetencia.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia.setCompetenciaId(competenciaId);
		configuracaoNivelCompetencia.setTipoCompetencia(tipoCopetencia);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia);
		
		return configuracaoNivelCompetencia;
	}
	
	public void testFindColaboradoresCompetenciasAbaixoDoNivel()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Curso cursoConhecimento = CursoFactory.getEntity();
		cursoConhecimento.setNome("cursoConhecimento");
		cursoDao.save(cursoConhecimento);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setNome("conhecimento");
		conhecimento.setCursos(Arrays.asList(cursoConhecimento));
		conhecimentoDao.save(conhecimento);
		
		Curso cursoAtitude = CursoFactory.getEntity();
		cursoAtitude.setNome("cursoAtitude");
		cursoDao.save(cursoAtitude);
		
		Atitude atitude = AtitudeFactory.getEntity();
		atitude.setNome("atitude");
		atitude.setCursos(Arrays.asList(cursoAtitude));
		atitudeDao.save(atitude);

		NivelCompetencia nivelPessimo = NivelCompetenciaFactory.getEntity();
		nivelPessimo.setDescricao("pessimo");
		nivelPessimo.setOrdem(1);
		nivelCompetenciaDao.save(nivelPessimo);

		NivelCompetencia nivelRegular = NivelCompetenciaFactory.getEntity();
		nivelRegular.setDescricao("regular");
		nivelRegular.setOrdem(2);
		nivelCompetenciaDao.save(nivelRegular);
		
		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity();
		nivelBom.setDescricao("bom");
		nivelBom.setOrdem(3);
		nivelCompetenciaDao.save(nivelBom);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetencia configFaixa1 = new ConfiguracaoNivelCompetencia();
		configFaixa1.setConfiguracaoNivelCompetenciaColaborador(null);
		configFaixa1.setCandidato(null);
		configFaixa1.setFaixaSalarial(faixaSalarial);
		configFaixa1.setNivelCompetencia(nivelBom);
		configFaixa1.setCompetenciaId(atitude.getId());
		configFaixa1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(configFaixa1);
		
		ConfiguracaoNivelCompetencia configFaixa2 = new ConfiguracaoNivelCompetencia();
		configFaixa2.setConfiguracaoNivelCompetenciaColaborador(null);
		configFaixa2.setCandidato(null);
		configFaixa2.setFaixaSalarial(faixaSalarial);
		configFaixa2.setNivelCompetencia(nivelRegular);
		configFaixa2.setCompetenciaId(conhecimento.getId());
		configFaixa2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configFaixa2);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("matriz");
		estabelecimentoDao.save(estabelecimento);
		
		Colaborador ataliba = ColaboradorFactory.getEntity(1L, "ataliba", "ataliba", "0456");
		ataliba.setEmpresa(empresa);
		ataliba.setDesligado(false);
		colaboradorManager.save(ataliba);

		Colaborador tiburcio = ColaboradorFactory.getEntity(1L, "tiburcio", "tiburcio", "0123");
		tiburcio.setEmpresa(empresa);
		tiburcio.setDesligado(false);
		colaboradorManager.save(tiburcio);
		
		HistoricoColaborador historicoAtaliba = HistoricoColaboradorFactory.getEntity();
		historicoAtaliba.setColaborador(ataliba);
		historicoAtaliba.setEstabelecimento(estabelecimento);
		historicoAtaliba.setData(DateUtil.criarDataMesAno(17, 8, 2011));
		historicoAtaliba.setFaixaSalarial(faixaSalarial);
		historicoAtaliba.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoAtaliba);
		
		HistoricoColaborador historicoTiburcio = HistoricoColaboradorFactory.getEntity();
		historicoTiburcio.setColaborador(tiburcio);
		historicoTiburcio.setEstabelecimento(estabelecimento);
		historicoTiburcio.setData(DateUtil.criarDataMesAno(17, 8, 2011));
		historicoTiburcio.setFaixaSalarial(faixaSalarial);
		historicoTiburcio.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoTiburcio);
		
		ConfiguracaoNivelCompetenciaColaborador configAtaliba = criaConfiguracaoNivelCompetenciaColaborador(faixaSalarial, ataliba, DateUtil.criarDataMesAno(17, 8, 2011), null, null);
		
		ConfiguracaoNivelCompetencia configAtaliba1 = new ConfiguracaoNivelCompetencia();
		configAtaliba1.setConfiguracaoNivelCompetenciaColaborador(configAtaliba);
		configAtaliba1.setFaixaSalarial(faixaSalarial);
		configAtaliba1.setNivelCompetencia(nivelBom);
		configAtaliba1.setCompetenciaId(atitude.getId());
		configAtaliba1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(configAtaliba1);
		
		ConfiguracaoNivelCompetencia configAtaliba2 = new ConfiguracaoNivelCompetencia();
		configAtaliba2.setConfiguracaoNivelCompetenciaColaborador(configAtaliba);
		configAtaliba2.setNivelCompetencia(nivelPessimo);
		configAtaliba2.setCompetenciaId(conhecimento.getId());
		configAtaliba2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configAtaliba2);
		
		criaConfiguracaoNivelCompetenciaColaborador(faixaSalarial, tiburcio, DateUtil.criarDataMesAno(17, 8, 2011), null, null);
		
		Collection<ConfiguracaoNivelCompetencia> configs = configuracaoNivelCompetenciaDao.findColaboradoresCompetenciasAbaixoDoNivel(empresa.getId(), null, null, null, 'C');
		ConfiguracaoNivelCompetencia[] configsArray = configs.toArray(new ConfiguracaoNivelCompetencia[]{});
		
		assertEquals(3, configs.size());
		assertEquals("Ordenado por nome para agrupar por colaborador", ataliba.getNome(), configsArray[0].getConfiguracaoNivelCompetenciaColaborador().getColaborador().getNome());
		assertEquals("Ordenado por nome para agrupar por colaborador", tiburcio.getNome(), configsArray[1].getConfiguracaoNivelCompetenciaColaborador().getColaborador().getNome());
		assertEquals("Ordenado por nome para agrupar por colaborador", tiburcio.getNome(), configsArray[2].getConfiguracaoNivelCompetenciaColaborador().getColaborador().getNome());
		
		configs = configuracaoNivelCompetenciaDao.findColaboradoresCompetenciasAbaixoDoNivel(empresa.getId(), null, null, null, 'T');
		configsArray = configs.toArray(new ConfiguracaoNivelCompetencia[]{});
		
		assertEquals(3, configs.size());
		assertEquals("Ordenado por treinamento", cursoAtitude.getNome(), configsArray[0].getCursoNome());
		assertEquals("Ordenado por treinamento", cursoConhecimento.getNome(), configsArray[1].getCursoNome());
		assertEquals("Ordenado por treinamento", cursoConhecimento.getNome(), configsArray[2].getCursoNome());
	}
	
	public void testGetOrdemMaxima()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		NivelCompetencia nivelCompetencia1 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia1.setOrdem(1);
		nivelCompetencia1.setEmpresa(empresa);
		nivelCompetenciaDao.save(nivelCompetencia1);
		
		NivelCompetencia nivelCompetencia2 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia2.setOrdem(4);
		nivelCompetencia2.setEmpresa(empresa);
		nivelCompetenciaDao.save(nivelCompetencia2);

		NivelCompetencia nivelCompetencia3 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia3.setOrdem(4);
		nivelCompetencia3.setEmpresa(empresa);
		nivelCompetenciaDao.save(nivelCompetencia3);

		NivelCompetencia nivelCompetencia4 = NivelCompetenciaFactory.getEntity();
		nivelCompetencia4.setOrdem(2);
		nivelCompetencia4.setEmpresa(empresa);
		nivelCompetenciaDao.save(nivelCompetencia4);
		
		assertEquals((int) 4, nivelCompetenciaDao.getOrdemMaxima(empresa.getId()));
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

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setHistoricoColaboradorDao(
			HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setCursoDao(CursoDao cursoDao) {
		this.cursoDao = cursoDao;
	}

	public void setColaboradorQuestionarioDao(
			ColaboradorQuestionarioDao colaboradorQuestionarioDao) {
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialDao(
			ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao) {
		this.configuracaoNivelCompetenciaFaixaSalarialDao = configuracaoNivelCompetenciaFaixaSalarialDao;
	}
}

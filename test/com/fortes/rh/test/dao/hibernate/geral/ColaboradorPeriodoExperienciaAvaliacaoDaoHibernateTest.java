package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorPeriodoExperienciaAvaliacaoDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorPeriodoExperienciaAvaliacao>
{
	private ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao;
	private ColaboradorDao colaboradorDao;
	private PeriodoExperienciaDao periodoExperienciaDao;
	private AvaliacaoDao avaliacaoDao;
	private EmpresaDao empresaDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private CargoDao cargoDao;
	private FaixaSalarialDao faixaSalarialDao;
	
	public ColaboradorPeriodoExperienciaAvaliacao getEntity()
	{
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao = new ColaboradorPeriodoExperienciaAvaliacao();
		colaboradorPeriodoExperienciaAvaliacao.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		
		return colaboradorPeriodoExperienciaAvaliacao;
	}

	public void testRemoveByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorPeriodoExperienciaAvaliacao configuracao = new ColaboradorPeriodoExperienciaAvaliacao();
		configuracao.setColaborador(colaborador);
		configuracao.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(configuracao);
		
		colaboradorPeriodoExperienciaAvaliacaoDao.removeByColaborador(colaborador.getId());
		Collection<ColaboradorPeriodoExperienciaAvaliacao> configuracoes = colaboradorPeriodoExperienciaAvaliacaoDao.find(new String[]{"colaborador.id"}, new Object[]{colaborador.getId()});
		
		assertEquals(0, configuracoes.size());
	}

	public void testFindByColaborador()
	{
		Colaborador colab1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colab1);

		Colaborador colab2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colab2);
		
		ColaboradorPeriodoExperienciaAvaliacao config1 = new ColaboradorPeriodoExperienciaAvaliacao();
		config1.setColaborador(colab1);
		config1.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config1);

		ColaboradorPeriodoExperienciaAvaliacao config2 = new ColaboradorPeriodoExperienciaAvaliacao();
		config2.setColaborador(colab1);
		config2.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config2);
		
		ColaboradorPeriodoExperienciaAvaliacao config3 = new ColaboradorPeriodoExperienciaAvaliacao();
		config3.setColaborador(colab2);
		config3.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config3);
		
		Collection<ColaboradorPeriodoExperienciaAvaliacao> configs = colaboradorPeriodoExperienciaAvaliacaoDao.findByColaborador(colab1.getId());
		
		assertEquals(2, configs.size());
	}
	
	public void testGetColaboradoresComAvaliacaoVencidaHoje()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setTitulo("experiencia");
		avaliacaoDao.save(avaliacao);
		
		PeriodoExperiencia periodo1 = PeriodoExperienciaFactory.getEntity();
		periodo1.setDias(1);
		periodoExperienciaDao.save(periodo1);

		PeriodoExperiencia periodo60 = PeriodoExperienciaFactory.getEntity();
		periodo60.setDias(60);
		periodoExperienciaDao.save(periodo60);
		
		Date hoje = new Date();
		Date ontem = DateUtil.retornaDataDiaAnterior(hoje);
		Date amanha = DateUtil.incrementaDias(hoje, 1);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setNome("João");
		joao.setEmpresa(empresa);
		joao.setDataAdmissao(ontem);
		joao.setDataDesligamento(hoje);
		colaboradorDao.save(joao);
		
		HistoricoColaborador historicoColaboradorJoao = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorJoao.setColaborador(joao);
		historicoColaboradorJoao.setFaixaSalarial(faixaSalarial);
		historicoColaboradorJoao.setData(ontem);
		historicoColaboradorDao.save(historicoColaboradorJoao);
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setNome("Maria");
		maria.setEmpresa(empresa);
		maria.setDataAdmissao(ontem);
		maria.setDataDesligamento(amanha);
		colaboradorDao.save(maria);
		
		HistoricoColaborador historicoColaboradorMaria = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorMaria.setColaborador(maria);
		historicoColaboradorMaria.setFaixaSalarial(faixaSalarial);
		historicoColaboradorMaria.setData(ontem);
		historicoColaboradorDao.save(historicoColaboradorMaria);
		
		Colaborador pedro = ColaboradorFactory.getEntity();
		pedro.setNome("Pedro");
		pedro.setEmpresa(empresa);
		pedro.setDataAdmissao(ontem);
		pedro.setDataDesligamento(ontem);
		pedro.setEmailColaborador("");
		colaboradorDao.save(pedro);
		
		HistoricoColaborador historicoColaboradorPedro = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorPedro.setColaborador(pedro);
		historicoColaboradorPedro.setFaixaSalarial(faixaSalarial);
		historicoColaboradorPedro.setData(ontem);
		historicoColaboradorDao.save(historicoColaboradorPedro);
		
		ColaboradorQuestionario colaboradorQuestionarioMaria = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioMaria.setRespondida(true);
		colaboradorQuestionarioMaria.setColaborador(maria);
		colaboradorQuestionarioMaria.setAvaliacao(avaliacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioMaria);
			
		ColaboradorPeriodoExperienciaAvaliacao config1Joao = new ColaboradorPeriodoExperienciaAvaliacao();
		config1Joao.setColaborador(joao);
		config1Joao.setPeriodoExperiencia(periodo1);
		config1Joao.setAvaliacao(avaliacao);
		config1Joao.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config1Joao);
		
		ColaboradorPeriodoExperienciaAvaliacao config2Joao = new ColaboradorPeriodoExperienciaAvaliacao();
		config2Joao.setColaborador(joao);
		config2Joao.setPeriodoExperiencia(periodo60);
		config2Joao.setAvaliacao(avaliacao);
		config2Joao.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config2Joao);

		ColaboradorPeriodoExperienciaAvaliacao configMaria = new ColaboradorPeriodoExperienciaAvaliacao();
		configMaria.setColaborador(maria);
		configMaria.setPeriodoExperiencia(periodo1);
		configMaria.setAvaliacao(avaliacao);
		configMaria.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(configMaria);
		
		ColaboradorPeriodoExperienciaAvaliacao configPedro = new ColaboradorPeriodoExperienciaAvaliacao();
		configPedro.setColaborador(pedro);
		configPedro.setPeriodoExperiencia(periodo1);
		configPedro.setAvaliacao(avaliacao);
		configPedro.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(configPedro);
		
		Collection<ColaboradorPeriodoExperienciaAvaliacao> configs = colaboradorPeriodoExperienciaAvaliacaoDao.getColaboradoresComAvaliacaoVencidaHoje();
		
		assertTrue(configs.size() >= 2);
	}

	public void testRemoveByAvaliacao()
	{
		Colaborador joao = ColaboradorFactory.getEntity();
		colaboradorDao.save(joao);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao = new ColaboradorPeriodoExperienciaAvaliacao();
		colaboradorPeriodoExperienciaAvaliacao.setAvaliacao(avaliacao);
		colaboradorPeriodoExperienciaAvaliacao.setColaborador(joao);
		colaboradorPeriodoExperienciaAvaliacao.setTipo(ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colaboradorPeriodoExperienciaAvaliacao);
		
		colaboradorPeriodoExperienciaAvaliacaoDao.removeByAvaliacao(avaliacao.getId());
		Collection<ColaboradorPeriodoExperienciaAvaliacao> retorno = colaboradorPeriodoExperienciaAvaliacaoDao.find(new String[]{"avaliacao.id"}, new Object[]{avaliacao.getId()});
		
		assertEquals(0, retorno.size());
	}
	
	public GenericDao<ColaboradorPeriodoExperienciaAvaliacao> getGenericDao()
	{
		return colaboradorPeriodoExperienciaAvaliacaoDao;
	}

	public void setColaboradorPeriodoExperienciaAvaliacaoDao(ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao)
	{
		this.colaboradorPeriodoExperienciaAvaliacaoDao = colaboradorPeriodoExperienciaAvaliacaoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setPeriodoExperienciaDao(PeriodoExperienciaDao periodoExperienciaDao) {
		this.periodoExperienciaDao = periodoExperienciaDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao) {
		this.avaliacaoDao = avaliacaoDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao) {
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
	
	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}
	
}
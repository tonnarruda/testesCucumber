package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.ColaboradorPeriodoExperienciaAvaliacaoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorPeriodoExperienciaAvaliacaoDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorPeriodoExperienciaAvaliacao>
{
	private ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao;
	private ColaboradorDao colaboradorDao;
	private PeriodoExperienciaDao periodoExperienciaDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
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
		
		ColaboradorPeriodoExperienciaAvaliacao configuracao = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colaborador, null, null, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
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
		
		ColaboradorPeriodoExperienciaAvaliacao config1 = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colab1, null, null, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config1);

		ColaboradorPeriodoExperienciaAvaliacao config2 = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colab1, null, null, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config2);
		
		ColaboradorPeriodoExperienciaAvaliacao config3 = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colab2, null, null, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config3);
		
		Collection<ColaboradorPeriodoExperienciaAvaliacao> configs = colaboradorPeriodoExperienciaAvaliacaoDao.findByColaborador(colab1.getId());
		
		assertEquals(2, configs.size());
	}
	
	public void testFindColaboradoresComAvaliacaoNaoRespondida()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avPeriodoExperiencia = AvaliacaoFactory.getEntity();
		avPeriodoExperiencia.setTipoModeloAvaliacao(TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA);
		avaliacaoDao.save(avPeriodoExperiencia);
		
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
		
		Colaborador joao = ColaboradorFactory.getEntity(null, "João", empresa, ontem, hoje);
		colaboradorDao.save(joao);
		
		HistoricoColaborador historicoColaboradorJoao = HistoricoColaboradorFactory.getEntity(joao, ontem, faixaSalarial, null, null, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboradorJoao);
		
		Colaborador maria = ColaboradorFactory.getEntity(null, "Maria", empresa, ontem, amanha);
		colaboradorDao.save(maria);
		
		HistoricoColaborador historicoColaboradorMaria = HistoricoColaboradorFactory.getEntity(maria, ontem, faixaSalarial, null, null, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboradorMaria);
		
		Colaborador pedro = ColaboradorFactory.getEntity(null, "Pedro", empresa, ontem, ontem);
		colaboradorDao.save(pedro);
		
		HistoricoColaborador historicoColaboradorPedro = HistoricoColaboradorFactory.getEntity(pedro, ontem, faixaSalarial, null, null, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboradorPedro);
		
		ColaboradorQuestionario colaboradorQuestionarioMaria = ColaboradorQuestionarioFactory.getEntity(maria, avPeriodoExperiencia, null, true);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioMaria);
			
		ColaboradorPeriodoExperienciaAvaliacao config1Joao = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(joao, periodo1, avPeriodoExperiencia, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config1Joao);
		
		ColaboradorPeriodoExperienciaAvaliacao config2Joao = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(joao, periodo60, avPeriodoExperiencia, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config2Joao);
		
		ColaboradorPeriodoExperienciaAvaliacao configMaria = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(maria, periodo1, avPeriodoExperiencia, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(configMaria);
		
		ColaboradorPeriodoExperienciaAvaliacao configPedro = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(pedro, periodo1, avPeriodoExperiencia, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(configPedro);
		
		Collection<ColaboradorPeriodoExperienciaAvaliacao> configs = colaboradorPeriodoExperienciaAvaliacaoDao.findColaboradoresComAvaliacaoNaoRespondida();
		
		int resultado = filtraColaboradorPeriodoExperienciaAvaliacao(avPeriodoExperiencia, configs);
		
		assertEquals(2, resultado);
	}

	public void testFindColaboradoresComAvaliacaoNaoRespondidaComAvaliacaoDesempenhoComMesmaAvaliacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avPeriodoExperiencia = AvaliacaoFactory.getEntity();
		avPeriodoExperiencia.setTipoModeloAvaliacao(TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA);
		avaliacaoDao.save(avPeriodoExperiencia);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setAvaliacao(avPeriodoExperiencia);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
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
		
		Colaborador joao = ColaboradorFactory.getEntity(null, "João", empresa, ontem, hoje);
		colaboradorDao.save(joao);
		
		HistoricoColaborador historicoColaboradorJoao = HistoricoColaboradorFactory.getEntity(joao, ontem, faixaSalarial, null, null, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboradorJoao);
		
		Colaborador maria = ColaboradorFactory.getEntity(null, "Maria", empresa, ontem, amanha);
		colaboradorDao.save(maria);
		
		HistoricoColaborador historicoColaboradorMaria = HistoricoColaboradorFactory.getEntity(maria, ontem, faixaSalarial, null, null, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboradorMaria);
		
		Colaborador pedro = ColaboradorFactory.getEntity(null, "Pedro", empresa, ontem, ontem);
		colaboradorDao.save(pedro);
		
		HistoricoColaborador historicoColaboradorPedro = HistoricoColaboradorFactory.getEntity(pedro, ontem, faixaSalarial, null, null, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboradorPedro);
		
		ColaboradorQuestionario colaboradorQuestionarioJoao = ColaboradorQuestionarioFactory.getEntity(joao, avPeriodoExperiencia, avaliacaoDesempenho, false);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioJoao);
		
		ColaboradorQuestionario colaboradorQuestionarioMaria = ColaboradorQuestionarioFactory.getEntity(maria, avPeriodoExperiencia, null, true);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioMaria);
		
		ColaboradorPeriodoExperienciaAvaliacao config1Joao = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(joao, periodo1, avPeriodoExperiencia, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config1Joao);
		
		ColaboradorPeriodoExperienciaAvaliacao config2Joao = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(joao, periodo60, avPeriodoExperiencia, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(config2Joao);
		
		ColaboradorPeriodoExperienciaAvaliacao configMaria = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(maria, periodo1, avPeriodoExperiencia, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(configMaria);
		
		ColaboradorPeriodoExperienciaAvaliacao configPedro = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(pedro, periodo1, avPeriodoExperiencia, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
		colaboradorPeriodoExperienciaAvaliacaoDao.save(configPedro);
		
		Collection<ColaboradorPeriodoExperienciaAvaliacao> configs = colaboradorPeriodoExperienciaAvaliacaoDao.findColaboradoresComAvaliacaoNaoRespondida();
		
		int resultado = filtraColaboradorPeriodoExperienciaAvaliacao(avPeriodoExperiencia, configs);
		
		assertEquals(2, resultado);
	}

	private int filtraColaboradorPeriodoExperienciaAvaliacao(Avaliacao avaliacao, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorPeriodoExperienciaAvaliacaos)
	{
		int resultado = 0;
		for (ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao : colaboradorPeriodoExperienciaAvaliacaos) {
			if(colaboradorPeriodoExperienciaAvaliacao.getAvaliacao().equals(avaliacao))
				resultado++;
		}
		return resultado;
	}

	public void testRemoveByAvaliacao()
	{
		Colaborador joao = ColaboradorFactory.getEntity();
		colaboradorDao.save(joao);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(joao, null, avaliacao, ColaboradorPeriodoExperienciaAvaliacao.TIPO_COLABORADOR);
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

	public void setAvaliacaoDesempenhoDao(AvaliacaoDesempenhoDao avaliacaoDesempenhoDao)
	{
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}
	
}
package com.fortes.rh.test.dao.hibernate.avaliacao;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.ColaboradorPeriodoExperienciaAvaliacaoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;

public class AvaliacaoDaoHibernateTest_Junit4 extends GenericDaoHibernateTest_JUnit4<Avaliacao>
{
	@Autowired private ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao;
	@Autowired private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	@Autowired private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired private PeriodoExperienciaDao periodoExperienciaDao;
	@Autowired private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private AvaliacaoDao avaliacaoDao;
	@Autowired private EmpresaDao empresaDao;

	@Override
	public Avaliacao getEntity()
	{
		return AvaliacaoFactory.getEntity();
	}

	@Override
	public GenericDao<Avaliacao> getGenericDao()
	{
		return avaliacaoDao;
	}
	
	@Test
	public void testFindModelosAtivosAndModelosConfiguradosParaOColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		PeriodoExperiencia periodoExperiencia15Dias = PeriodoExperienciaFactory.getEntity(empresa, 15, true);
		periodoExperienciaDao.save(periodoExperiencia15Dias);
		
		PeriodoExperiencia periodoExperiencia30Dias = PeriodoExperienciaFactory.getEntity(empresa, 30, true);
		periodoExperienciaDao.save(periodoExperiencia30Dias);
		
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity(empresa, "Avaliação 1", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, periodoExperiencia15Dias, false);
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity(empresa, "Avaliação 2", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, periodoExperiencia30Dias, true);
		avaliacaoDao.save(avaliacao2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao15Dias = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colaborador, periodoExperiencia15Dias, avaliacao1, 'C');
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colaboradorPeriodoExperienciaAvaliacao15Dias);
		
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao30Dias = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colaborador, periodoExperiencia30Dias, avaliacao2, 'C');
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colaboradorPeriodoExperienciaAvaliacao30Dias);
		
		Collection<Avaliacao> avaliacoes = avaliacaoDao.findModelosPeriodoExperienciaAtivosAndModelosConfiguradosParaOColaborador(empresa.getId(), colaborador.getId());
		assertEquals(2, avaliacoes.size());
	}
	
	@Test
	public void testFindModelosAcompanhamentoPeriodoExperiencia() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		PeriodoExperiencia periodoExperiencia15Dias = PeriodoExperienciaFactory.getEntity(empresa, 15, true);
		periodoExperienciaDao.save(periodoExperiencia15Dias);
		
		PeriodoExperiencia periodoExperiencia30Dias = PeriodoExperienciaFactory.getEntity(empresa, 30, true);
		periodoExperienciaDao.save(periodoExperiencia30Dias);
		
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity(empresa, "Avaliação 1", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, periodoExperiencia15Dias, false);
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity(empresa, "Avaliação 2", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, periodoExperiencia30Dias, true);
		avaliacaoDao.save(avaliacao2);
		
		Collection<Avaliacao> avaliacoes = avaliacaoDao.findModelosAcompanhamentoPeriodoExperiencia(true, empresa.getId(), null, null, null);
		assertEquals(1, avaliacoes.size());
	}
	
	@Test
	public void testFindModelosAcompanhamentoPeriodoExperienciaComRestricaoParaGestorDaPropriaArea() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		PeriodoExperiencia periodoExperiencia15Dias = PeriodoExperienciaFactory.getEntity(empresa, 15, true);
		periodoExperienciaDao.save(periodoExperiencia15Dias);
		
		PeriodoExperiencia periodoExperiencia30Dias = PeriodoExperienciaFactory.getEntity(empresa, 30, true);
		periodoExperienciaDao.save(periodoExperiencia30Dias);
		
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity(empresa, "Avaliação 1", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, periodoExperiencia15Dias, false);
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity(empresa, "Avaliação 2", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, periodoExperiencia30Dias, true);
		avaliacaoDao.save(avaliacao2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao15Dias = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colaborador, periodoExperiencia15Dias, avaliacao1, 'G');
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colaboradorPeriodoExperienciaAvaliacao15Dias);
		
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao30Dias = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colaborador, periodoExperiencia30Dias, avaliacao2, 'C');
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colaboradorPeriodoExperienciaAvaliacao30Dias);
		
		Collection<Avaliacao> avaliacoes = avaliacaoDao.findModelosAcompanhamentoPeriodoExperiencia(true, empresa.getId(), null, colaborador.getId(), AreaOrganizacional.RESPONSAVEL);
		assertEquals(1, avaliacoes.size());
	}
	
	@Test
	public void testFindModelosAcompanhamentoPeriodoExperienciaComRestricaoParaGestorDaPropriaAreaAvaliacaoRespondida() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		PeriodoExperiencia periodoExperiencia15Dias = PeriodoExperienciaFactory.getEntity(empresa, 15, true);
		periodoExperienciaDao.save(periodoExperiencia15Dias);
		
		PeriodoExperiencia periodoExperiencia30Dias = PeriodoExperienciaFactory.getEntity(empresa, 30, true);
		periodoExperienciaDao.save(periodoExperiencia30Dias);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(empresa, "Avaliação 2", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, periodoExperiencia15Dias, true);
		avaliacaoDao.save(avaliacao);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity(empresa, "Avaliação 2", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, periodoExperiencia30Dias, true);
		avaliacaoDao.save(avaliacao2);
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacionalFilha = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalFilha.setAreaMae(areaOrganizacionalMae);
		areaOrganizacionalDao.save(areaOrganizacionalFilha);
		
		Colaborador colaboradorResponsavelAreaFilha = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaboradorResponsavelAreaFilha);
		
		Colaborador colaboradorResponsavelAreaMae = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaboradorResponsavelAreaMae);
		
		areaOrganizacionalFilha.setResponsavel(colaboradorResponsavelAreaFilha);
		areaOrganizacionalDao.update(areaOrganizacionalFilha);
		
		areaOrganizacionalMae.setResponsavel(colaboradorResponsavelAreaMae);
		areaOrganizacionalDao.update(areaOrganizacionalMae);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaboradorResponsavelAreaFilha, new Date(), StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setAreaOrganizacional(areaOrganizacionalFilha);
		historicoColaboradorDao.save(historicoColaborador);
		
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao15Dias = ColaboradorPeriodoExperienciaAvaliacaoFactory.getEntity(colaboradorResponsavelAreaFilha, periodoExperiencia15Dias, avaliacao, 'C');
		colaboradorPeriodoExperienciaAvaliacaoDao.save(colaboradorPeriodoExperienciaAvaliacao15Dias);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(colaboradorResponsavelAreaFilha, colaboradorResponsavelAreaMae, avaliacao2, null, true);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		Collection<Avaliacao> avaliacoes = avaliacaoDao.findModelosAcompanhamentoPeriodoExperiencia(true, empresa.getId(), null, colaboradorResponsavelAreaFilha.getId(), AreaOrganizacional.RESPONSAVEL);
		assertEquals(1, avaliacoes.size());
	}
}
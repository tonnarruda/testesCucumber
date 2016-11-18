package com.fortes.rh.test.dao.hibernate.avaliacao;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorPeriodoExperienciaAvaliacaoFactory;

public class AvaliacaoDaoHibernateTest_Junit4 extends GenericDaoHibernateTest_JUnit4<Avaliacao>
{
	@Autowired
	private ColaboradorPeriodoExperienciaAvaliacaoDao colaboradorPeriodoExperienciaAvaliacaoDao;
	@Autowired
	private PeriodoExperienciaDao periodoExperienciaDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private AvaliacaoDao avaliacaoDao;
	@Autowired
	private EmpresaDao empresaDao;
	

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
	public void FindModelosAtivosAndModelosConfiguradosParaOColaborador() {
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

}

package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.sesmt.CandidatoEleicaoDao;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;

public class CandidatoEleicaoDaoHibernateTest extends GenericDaoHibernateTest<CandidatoEleicao>
{
	CandidatoEleicaoDao candidatoEleicaoDao;
	EleicaoDao eleicaoDao;
	ColaboradorDao colaboradorDao;
	CargoDao cargoDao;
	FaixaSalarialDao faixaSalarialDao;
	HistoricoColaboradorDao historicoColaboradorDao;

	@Override
	public CandidatoEleicao getEntity()
	{
		return CandidatoEleicaoFactory.getEntity();
	}

	@Override
	public GenericDao<CandidatoEleicao> getGenericDao()
	{
		return candidatoEleicaoDao;
	}

	public void testFindByEleicao()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Joao");
		colaboradorDao.save(colaborador);
		
		Cargo cargo = new Cargo();
		cargo.setNome("Desenvolvedor");
		cargo.setNomeMercado("");
		cargoDao.save(cargo);
			
		FaixaSalarial faixaSalarial = new FaixaSalarial();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setData(new Date());
		historicoColaboradorDao.save(historicoColaborador);
		
		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao.setEleicao(eleicao);
		candidatoEleicao.setCandidato(colaborador);
		candidatoEleicaoDao.save(candidatoEleicao);
		
		//Criação Colaborador 2
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Maria da Penha");
		colaboradorDao.save(colaborador2);

		Cargo cargo2 = new Cargo();
		cargo2.setNome("Analista");
		cargo2.setNomeMercado("");
		cargoDao.save(cargo2);
			
		FaixaSalarial faixaSalarial2 = new FaixaSalarial();
		faixaSalarial2.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial2);
		
		HistoricoColaborador historicoColaborador2 = new HistoricoColaborador();
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setFaixaSalarial(faixaSalarial2);
		historicoColaborador2.setData(new Date());
		historicoColaboradorDao.save(historicoColaborador2);
		
		CandidatoEleicao candidatoEleicao2 = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao2.setEleicao(eleicao);
		candidatoEleicao2.setCandidato(colaborador2);
		candidatoEleicao2.setEleito(true);
		candidatoEleicaoDao.save(candidatoEleicao2);

		Collection<CandidatoEleicao> candidatos = candidatoEleicaoDao.findByEleicao(eleicao.getId(), false, true);
		assertEquals(2, candidatos.size());
		Collection<CandidatoEleicao> candidatosEleitos = candidatoEleicaoDao.findByEleicao(eleicao.getId(), true, true);
		assertEquals(1, candidatosEleitos.size());
	}

	public void testRemoveByEleicao()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);

		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao.setEleicao(eleicao);
		candidatoEleicaoDao.save(candidatoEleicao);

		candidatoEleicaoDao.removeByEleicao(eleicao.getId());

		Collection<CandidatoEleicao> candidatos = candidatoEleicaoDao.findByEleicao(eleicao.getId(), false, true);
		assertEquals(0, candidatos.size());
	}

	public void testFindByIdProjection()
	{
		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity();
		candidatoEleicaoDao.save(candidatoEleicao);

		assertEquals(candidatoEleicao.getId(), candidatoEleicaoDao.findByIdProjection(candidatoEleicao.getId()).getId());
	}
	
	public void testFindCandidatoEleicao()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao.setCandidato(colaborador);
		candidatoEleicaoDao.save(candidatoEleicao);
		
		assertEquals(candidatoEleicao.getId(), candidatoEleicaoDao.findCandidatoEleicao(candidatoEleicao.getId()).getId());
	}
	
	public void testFindByColaboradorIdAndEleicaoId()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao.setEleicao(eleicao);
		candidatoEleicao.setCandidato(colaborador);
		candidatoEleicaoDao.save(candidatoEleicao);
		
		assertEquals(candidatoEleicao.getId(), candidatoEleicaoDao.findByColaboradorIdAndEleicaoId(colaborador.getId(), eleicao.getId()).getId());
	}

	public void testSetEleito()
	{
		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao.setEleito(false);
		candidatoEleicaoDao.save(candidatoEleicao);

		candidatoEleicaoDao.setEleito(true, new Long[]{candidatoEleicao.getId()});

		CandidatoEleicao candidatoEleicaoTmp = candidatoEleicaoDao.findByIdProjection(candidatoEleicao.getId());
		assertTrue(candidatoEleicaoTmp.isEleito());

		candidatoEleicaoDao.setEleito(false, new Long[]{candidatoEleicao.getId()});

		candidatoEleicaoTmp = candidatoEleicaoDao.findByIdProjection(candidatoEleicao.getId());
		assertFalse(candidatoEleicaoTmp.isEleito());
	}

	public void testSetQtdVotos()
	{
		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao.setQtdVoto(1);
		candidatoEleicaoDao.save(candidatoEleicao);

		candidatoEleicaoDao.setQtdVotos(10, candidatoEleicao.getId());

		CandidatoEleicao candidatoEleicaoTmp = candidatoEleicaoDao.findByIdProjection(candidatoEleicao.getId());
		assertEquals(new Integer(10), candidatoEleicaoTmp.getQtdVoto());
	}
	
	public void testFindByColaborador()
    {
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);
		
		Eleicao eleicao2 = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao2);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("João do Caminhão");
		colaboradorDao.save(colaborador);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Maria da Farinha");
		colaboradorDao.save(colaborador2);

		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao.setEleicao(eleicao);
		candidatoEleicao.setCandidato(colaborador);
		candidatoEleicaoDao.save(candidatoEleicao);
		
		CandidatoEleicao candidatoEleicaoFora = CandidatoEleicaoFactory.getEntity();
		candidatoEleicaoFora.setEleicao(eleicao);
		candidatoEleicaoFora.setCandidato(colaborador2);
		candidatoEleicaoDao.save(candidatoEleicaoFora);
		
		CandidatoEleicao candidatoEleicao2 = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao2.setEleicao(eleicao2);
		candidatoEleicao2.setCandidato(colaborador);
		candidatoEleicaoDao.save(candidatoEleicao2);
		
		Collection<CandidatoEleicao> candidatoEleicaos = candidatoEleicaoDao.findByColaborador(colaborador.getId());
		assertEquals(2,candidatoEleicaos.size());
    }

	public void setEleicaoDao(EleicaoDao eleicaoDao)
	{
		this.eleicaoDao = eleicaoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}
	public void setCandidatoEleicaoDao(CandidatoEleicaoDao candidatoEleicaoDao)
	{
		this.candidatoEleicaoDao = candidatoEleicaoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setHistoricoColaboradorDao(
			HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}
}
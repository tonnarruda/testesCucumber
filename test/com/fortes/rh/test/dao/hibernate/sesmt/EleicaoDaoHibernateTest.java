package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.CandidatoEleicaoDao;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;

public class EleicaoDaoHibernateTest extends GenericDaoHibernateTest<Eleicao>
{
	EleicaoDao eleicaoDao;
	CandidatoEleicaoDao candidatoEleicaoDao;
	EmpresaDao empresaDao;
	ColaboradorDao colaboradorDao;
	HistoricoColaboradorDao historicoColaboradorDao;

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEleicaoDao(EleicaoDao eleicaoDao)
	{
		this.eleicaoDao = eleicaoDao;
	}

	@Override
	public Eleicao getEntity()
	{
		return EleicaoFactory.getEntity();
	}

	@Override
	public GenericDao<Eleicao> getGenericDao()
	{
		return eleicaoDao;
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Eleicao eleicao = getEntity();
		eleicao.setEmpresa(empresa);
		eleicaoDao.save(eleicao);

		Collection<Eleicao> eleicaos = eleicaoDao.findAllSelect(empresa.getId());
		assertEquals(1, eleicaos.size());
	}

	public void testFindByIdProjection()
	{
		Eleicao eleicao = getEntity();
		eleicaoDao.save(eleicao);

		assertEquals(eleicao.getId(), eleicaoDao.findByIdProjection(eleicao.getId()).getId());
	}

	public void testUpdateVotos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Eleicao eleicao = getEntity();
		eleicao.setEmpresa(empresa);
		eleicao.setQtdVotoBranco(2);
		eleicao.setQtdVotoNulo(14);
		eleicaoDao.save(eleicao);

		eleicao.setQtdVotoBranco(10);
		eleicao.setQtdVotoNulo(11);
		eleicaoDao.updateVotos(eleicao);

		Eleicao eleicaoTmp = eleicaoDao.findByIdProjection(eleicao.getId());
		assertEquals(eleicao.getQtdVotoBranco(), eleicaoTmp.getQtdVotoBranco());
		assertEquals(eleicao.getQtdVotoNulo(), eleicaoTmp.getQtdVotoNulo());
	}

	public void testFindImprimirResultado()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicao.setQtdVotoBranco(10);
		eleicao.setQtdVotoNulo(0);
		eleicaoDao.save(eleicao);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(new Date());
		historicoColaboradorDao.save(historicoColaborador);

		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao.setCandidato(colaborador);
		candidatoEleicao.setQtdVoto(10);
		candidatoEleicao.setEleicao(eleicao);
		candidatoEleicaoDao.save(candidatoEleicao);

		CandidatoEleicao candidatoEleicao2 = CandidatoEleicaoFactory.getEntity();
		candidatoEleicao2.setCandidato(colaborador);
		candidatoEleicao2.setQtdVoto(10);
		candidatoEleicao2.setEleicao(eleicao);
		candidatoEleicaoDao.save(candidatoEleicao2);

		Collection<CandidatoEleicao> candidatoEleicaos = new ArrayList<CandidatoEleicao>();
		candidatoEleicaos.add(candidatoEleicao);
		candidatoEleicaos.add(candidatoEleicao2);

		Collection<CandidatoEleicao> colecao = eleicaoDao.findImprimirResultado(eleicao.getId());

		assertEquals(2,colecao.size());

		Eleicao eleicaoResultado = ((CandidatoEleicao)colecao.toArray()[0]).getEleicao();
		assertEquals(eleicaoResultado.getQtdVotoBranco(),eleicao.getQtdVotoBranco());
		assertEquals(eleicaoResultado.getQtdVotoNulo(),eleicao.getQtdVotoNulo());
	}

	public void setCandidatoEleicaoDao(CandidatoEleicaoDao candidatoEleicaoDao)
	{
		this.candidatoEleicaoDao = candidatoEleicaoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}
}
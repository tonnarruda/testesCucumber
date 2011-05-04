package com.fortes.rh.test.dao.hibernate.avaliacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;

public class AvaliacaoDesempenhoDaoHibernateTest extends GenericDaoHibernateTest<AvaliacaoDesempenho>
{
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private AvaliacaoDao avaliacaoDao;
	private EmpresaDao empresaDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private ColaboradorDao colaboradorDao;

	@Override
	public AvaliacaoDesempenho getEntity()
	{
		return AvaliacaoDesempenhoFactory.getEntity();
	}

	@Override
	public GenericDao<AvaliacaoDesempenho> getGenericDao()
	{
		return avaliacaoDesempenhoDao;
	}

	public void setAvaliacaoDesempenhoDao(AvaliacaoDesempenhoDao avaliacaoDesempenhoDao)
	{
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}
	
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setEmpresa(empresa);
		avaliacaoDao.save(avaliacao);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		assertEquals(1,avaliacaoDesempenhoDao.findAllSelect(empresa.getId(), null, null).size());
		
	}
	
	public void testFindByAvaliador()
	{
		AvaliacaoDesempenho avaliacaoDesempenho1 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho1.setLiberada(true);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho1);
		
		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho2.setLiberada(false);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setAvaliacaoDesempenho(avaliacaoDesempenho1);
		colaboradorQuestionario1.setAvaliador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho1);
		colaboradorQuestionario2.setAvaliador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ColaboradorQuestionario colaboradorQuestionario3 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario3.setAvaliacaoDesempenho(avaliacaoDesempenho2);
		colaboradorQuestionario3.setAvaliador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario3);
		
		assertEquals(1, avaliacaoDesempenhoDao.findByAvaliador(colaborador.getId(), true).size());
	}
	
	public void testLiberarOrBloquear()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setLiberada(false);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		avaliacaoDesempenhoDao.liberarOrBloquear(avaliacaoDesempenho.getId(), true);
		AvaliacaoDesempenho retorno = avaliacaoDesempenhoDao.findByIdProjection(avaliacaoDesempenho.getId());
		assertTrue(retorno.isLiberada());
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao) {
		this.avaliacaoDao = avaliacaoDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao) {
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}
}

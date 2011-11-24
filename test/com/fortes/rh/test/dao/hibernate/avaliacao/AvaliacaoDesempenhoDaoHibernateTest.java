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
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("empresa");
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setEmpresa(empresa);
		avaliacaoDao.save(avaliacao);
		
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
		colaboradorQuestionario1.setAvaliacao(avaliacao);
		colaboradorQuestionario1.setAvaliador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho1);
		colaboradorQuestionario2.setAvaliacao(avaliacao);
		colaboradorQuestionario2.setAvaliador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ColaboradorQuestionario colaboradorQuestionario3 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario3.setAvaliacaoDesempenho(avaliacaoDesempenho2);
		colaboradorQuestionario3.setAvaliacao(avaliacao);
		colaboradorQuestionario3.setAvaliador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario3);
		
		assertEquals(1, avaliacaoDesempenhoDao.findByAvaliador(colaborador.getId(), true, empresa.getId()).size());
	}
	
	public void testLiberarOrBloquear()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setEmpresa(empresa);
		avaliacaoDao.save(avaliacao);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		avaliacaoDesempenho.setLiberada(false);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		avaliacaoDesempenhoDao.liberarOrBloquear(avaliacaoDesempenho.getId(), true);
		AvaliacaoDesempenho retorno = avaliacaoDesempenhoDao.findByIdProjection(avaliacaoDesempenho.getId());
		assertTrue(retorno.isLiberada());
	}
	
	public void testFindTituloModeloAvaliacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setEmpresa(empresa);
		avaliacaoDao.save(avaliacao);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setTitulo("avaliação desempenho de 90 dias");
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho2.setTitulo("avaliação desempenho de 30 dias");
		avaliacaoDesempenho2.setAvaliacao(avaliacao);
		avaliacaoDesempenho2.setLiberada(true);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);

		AvaliacaoDesempenho avaliacaoDesempenho3 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho3.setTitulo("avaliação desempenho de 30 dias");
		avaliacaoDesempenho3.setAvaliacao(avaliacao);
		avaliacaoDesempenho3.setLiberada(false);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho3);
		
		assertEquals(3, avaliacaoDesempenhoDao.findTituloModeloAvaliacao(empresa.getId(), "ação", avaliacao.getId(), null).size());
		assertEquals(2, avaliacaoDesempenhoDao.findTituloModeloAvaliacao(empresa.getId(), "ação", avaliacao.getId(), false).size());
		assertEquals(1, avaliacaoDesempenhoDao.findTituloModeloAvaliacao(empresa.getId(), "ação", avaliacao.getId(), true).size());
	}
	
	public void testFindIdsAvaliacaoDesempenha()
	{
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		AvaliacaoDesempenho avaliacaoDesempenho1 = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho1.setAvaliacao(avaliacao1);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho1);

		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity(2L);
		avaliacaoDesempenho2.setAvaliacao(avaliacao1);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);

		AvaliacaoDesempenho avaliacaoDesempenhoFora = AvaliacaoDesempenhoFactory.getEntity(3L);
		avaliacaoDesempenhoFora.setAvaliacao(avaliacao2);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenhoFora);
		
		assertEquals(2, avaliacaoDesempenhoDao.findIdsAvaliacaoDesempenho(avaliacao1.getId()).size());
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

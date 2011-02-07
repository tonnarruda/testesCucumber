package com.fortes.rh.test.dao.hibernate.pesquisa;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.AvaliacaoTurmaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;

public class AvaliacaoTurmaDaoHibernateTest extends GenericDaoHibernateTest<AvaliacaoTurma>
{
	private AvaliacaoTurma avaliacaoTurma;
	private AvaliacaoTurmaDao avaliacaoTurmaDao;
	private EmpresaDao empresaDao;
	private Questionario questionario;
	
	
	@Override
	public AvaliacaoTurma getEntity()
	{
		return AvaliacaoTurmaFactory.getEntity();
	}

	@Override
	public GenericDao<AvaliacaoTurma> getGenericDao()
	{
		return avaliacaoTurmaDao;
	}
		
	
	public void testFindByIdProjection() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresaDao.save(empresa);
		
		questionario = QuestionarioFactory.getEntity();
		questionario.setId(1L);
		questionario.setEmpresa(empresa);
		
		avaliacaoTurma = new AvaliacaoTurma();
		avaliacaoTurma.setId(1L);
		avaliacaoTurma.setAtiva(true);
		avaliacaoTurma.setQuestionario(questionario);
		
		avaliacaoTurma = avaliacaoTurmaDao.findByIdProjection(avaliacaoTurma.getId());

    	assertEquals(avaliacaoTurma, avaliacaoTurma);

	}
	
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
	public void setAvaliacaoTurmaDao(AvaliacaoTurmaDao avaliacaoTurmaDao) {
		this.avaliacaoTurmaDao = avaliacaoTurmaDao;
	}

}

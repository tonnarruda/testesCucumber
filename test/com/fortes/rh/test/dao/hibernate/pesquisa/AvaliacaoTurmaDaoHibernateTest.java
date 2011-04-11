package com.fortes.rh.test.dao.hibernate.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.AvaliacaoTurmaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;

public class AvaliacaoTurmaDaoHibernateTest extends GenericDaoHibernateTest<AvaliacaoTurma>
{
	private AvaliacaoTurma avaliacaoTurma;
	private AvaliacaoTurmaDao avaliacaoTurmaDao;
	private QuestionarioDao questionarioDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
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
		Empresa empresa = criaEmpresa();
		
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

	public void testFindToList() throws Exception
	{
		Empresa empresa = criaEmpresa();
		
		ColaboradorQuestionario colabQuestionario1 = ColaboradorQuestionarioFactory.getEntity(1L);
		colaboradorQuestionarioDao.save(colabQuestionario1);
		ColaboradorQuestionario colabQuestionario2 = ColaboradorQuestionarioFactory.getEntity(2L);
		colaboradorQuestionarioDao.save(colabQuestionario2);
		ColaboradorQuestionario colabQuestionario3 = ColaboradorQuestionarioFactory.getEntity(3L);
		colaboradorQuestionarioDao.save(colabQuestionario3);
		
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		colaboradorQuestionarios.add(colabQuestionario1);
		colaboradorQuestionarios.add(colabQuestionario2);
		colaboradorQuestionarios.add(colabQuestionario3);
		
		Questionario questionarioA = criaQuestionario(empresa, 3333L, "Titulo A");
		questionarioA.setColaboradorQuestionarios(colaboradorQuestionarios);
		questionarioDao.save(questionarioA);

		Questionario questionarioB = criaQuestionario(empresa, 3333L, "Titulo B");
		questionarioB.setColaboradorQuestionarios(colaboradorQuestionarios);
		questionarioDao.save(questionarioB);
		
		criarAvaliacao(questionarioA, 22L);
		criarAvaliacao(questionarioB, 23L);

		Collection<AvaliacaoTurma> resposta = avaliacaoTurmaDao.findToList(empresa.getId(), 0, 10);
		assertEquals(2, resposta.size());

		AvaliacaoTurma avaliacaoTurmaResp = (AvaliacaoTurma) resposta.toArray()[1];
		assertEquals("Titulo B", avaliacaoTurmaResp.getQuestionario().getTitulo());
	}
	
	public void testgetIdByQuestionario() throws Exception
	{
		Empresa empresa = criaEmpresa();
		Questionario questionario = criaQuestionario(empresa, 3333L, "Titulo");
		questionarioDao.save(questionario);
		criarAvaliacao(questionario, 22L);

    	assertNotNull(avaliacaoTurmaDao.getIdByQuestionario(questionario.getId()));
	}

	public void testVerificaEmpresaDoQuestionario() throws Exception
	{
		Empresa empresa = criaEmpresa();
		Questionario questionario = criaQuestionario(empresa, 3333L, "Titulo");
		questionarioDao.save(questionario);
		AvaliacaoTurma avaliacaoTurma = criarAvaliacao(questionario, 2222L);
		
		assertEquals(true, avaliacaoTurmaDao.verificaEmpresaDoQuestionario(avaliacaoTurma.getId(), empresa.getId()));
	}

	public void testFindAllSelect() throws Exception
	{
		Empresa empresa = criaEmpresa();
		Questionario questionario1 = criaQuestionario(empresa, 3331L, "Titulo 1");
		questionarioDao.save(questionario1);
		Questionario questionario2 = criaQuestionario(empresa, 3333L, "Titulo 2");
		questionarioDao.save(questionario2);
		criarAvaliacao(questionario1, 9991L);
		criarAvaliacao(questionario2, null);
		
		assertEquals(new Integer(2), avaliacaoTurmaDao.getCount(empresa.getId()));
	}



	public void testGetCount() throws Exception
	{
		Empresa empresa = criaEmpresa();

		Questionario questionario1 = criaQuestionario(empresa, 3331L, "Titulo 1");
		questionarioDao.save(questionario1);
		criarAvaliacao(questionario1, 9991L);

		Questionario questionario2 = criaQuestionario(empresa, 3333L, "Titulo 2");
		questionarioDao.save(questionario2);
		criarAvaliacao(questionario2, 9999L);
		
		assertEquals(2, avaliacaoTurmaDao.findAllSelect(empresa.getId(), true).size());
	}

	private Empresa criaEmpresa() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresaDao.save(empresa);
		return empresa;
	}
	
	private Questionario criaQuestionario(Empresa empresa, Long id, String titulo) 
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setId(id);
		questionario.setTitulo(titulo);
		questionario.setLiberado(true);
		questionario.setTipo(0);
		questionario.setAplicarPorAspecto(false);
		questionario.setQuantidadeDeResposta(10);
		questionario.setEmpresa(empresa);
		return questionario;
	}
	
	private AvaliacaoTurma criarAvaliacao(Questionario questionario, Long id) 
	{
		AvaliacaoTurma avaliacaoTurma = new AvaliacaoTurma();
		avaliacaoTurma.setId(id);
		avaliacaoTurma.setAtiva(true);
		avaliacaoTurma.setQuestionario(questionario);
		avaliacaoTurmaDao.save(avaliacaoTurma);
		return avaliacaoTurma;
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
	public void setAvaliacaoTurmaDao(AvaliacaoTurmaDao avaliacaoTurmaDao) {
		this.avaliacaoTurmaDao = avaliacaoTurmaDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao) {
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setQuestionarioDao(QuestionarioDao questionarioDao) {
		this.questionarioDao = questionarioDao;
	}
}

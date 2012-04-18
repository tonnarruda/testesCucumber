package com.fortes.rh.test.dao.hibernate.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.TurmaAvaliacaoTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.AvaliacaoTurmaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.TurmaAvaliacaoTurma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;

public class AvaliacaoTurmaDaoHibernateTest extends GenericDaoHibernateTest<AvaliacaoTurma>
{
	private AvaliacaoTurmaDao avaliacaoTurmaDao;
	private TurmaDao turmaDao;
	private QuestionarioDao questionarioDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private EmpresaDao empresaDao;
	
	private AvaliacaoTurma avaliacaoTurma;
	private TurmaAvaliacaoTurmaDao turmaAvaliacaoTurmaDao;
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
	
	public void testFindByTurma() throws Exception
	{
		Empresa empresa = criaEmpresa();
		
		Questionario questionario1 = criaQuestionario(empresa, 3331L, "Titulo 1");
		questionarioDao.save(questionario1);
		
		Questionario questionario2 = criaQuestionario(empresa, 3333L, "Titulo 2");
		questionarioDao.save(questionario2);
		
		AvaliacaoTurma avaliacaoTurma1 = new AvaliacaoTurma();
		avaliacaoTurma1.setAtiva(true);
		avaliacaoTurma1.setQuestionario(questionario1);
		avaliacaoTurmaDao.save(avaliacaoTurma1);

		AvaliacaoTurma avaliacaoTurma2 = new AvaliacaoTurma();
		avaliacaoTurma2.setAtiva(true);
		avaliacaoTurma2.setQuestionario(questionario2);
		avaliacaoTurmaDao.save(avaliacaoTurma2);

		AvaliacaoTurma avaliacaoTurma3 = new AvaliacaoTurma();
		avaliacaoTurma3.setAtiva(true);
		avaliacaoTurma3.setQuestionario(questionario2);
		avaliacaoTurmaDao.save(avaliacaoTurma3);
		
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);

		TurmaAvaliacaoTurma turmaAvaliacaoTurma1 = new TurmaAvaliacaoTurma();
		turmaAvaliacaoTurma1.setTurma(turma);
		turmaAvaliacaoTurma1.setAvaliacaoTurma(avaliacaoTurma1);
		turmaAvaliacaoTurmaDao.save(turmaAvaliacaoTurma1);
		
		TurmaAvaliacaoTurma turmaAvaliacaoTurma3 = new TurmaAvaliacaoTurma();
		turmaAvaliacaoTurma3.setTurma(turma);
		turmaAvaliacaoTurma3.setAvaliacaoTurma(avaliacaoTurma3);
		turmaAvaliacaoTurmaDao.save(turmaAvaliacaoTurma3);
		
		Collection<AvaliacaoTurma> aTurmas = avaliacaoTurmaDao.findByTurma(turma.getId());
		
		assertEquals(2, aTurmas.size());
		assertEquals("Titulo 1", ((AvaliacaoTurma) aTurmas.toArray()[0]).getQuestionarioTitulo());
		assertEquals("Titulo 2", ((AvaliacaoTurma) aTurmas.toArray()[1]).getQuestionarioTitulo());
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

	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}

	public void setTurmaAvaliacaoTurmaDao(TurmaAvaliacaoTurmaDao turmaAvaliacaoTurmaDao) {
		this.turmaAvaliacaoTurmaDao = turmaAvaliacaoTurmaDao;
	}
}

package com.fortes.rh.test.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.PesquisaManagerImpl;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.dao.pesquisa.PesquisaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.ServletActionContext;

public class PesquisaManagerTest extends MockObjectTestCase
{
	private PesquisaManagerImpl pesquisaManager = new PesquisaManagerImpl();
	private Mock pesquisaDao;
	private Mock perguntaManager;
	private Mock questionarioManager;
	private Mock colaboradorQuestionarioManager;
	private Mock transactionManager;
	private Mock colaboradorRespostaManager;


    protected void setUp() throws Exception
    {
        pesquisaDao = new Mock(PesquisaDao.class);
        pesquisaManager.setDao((PesquisaDao) pesquisaDao.proxy());

        perguntaManager = new Mock(PerguntaManager.class);
        pesquisaManager.setPerguntaManager((PerguntaManager) perguntaManager.proxy());

        questionarioManager = new Mock(QuestionarioManager.class);
        pesquisaManager.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());

        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        pesquisaManager.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        pesquisaManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		
		colaboradorRespostaManager = new Mock(ColaboradorRespostaManager.class);
		MockSpringUtil.mocks.put("colaboradorRespostaManager", colaboradorRespostaManager);
    }

    protected void tearDown() throws Exception
    {
    	MockSecurityUtil.verifyRole = false;
    }
    
    public void testDelete() throws Exception
    {
    	Exception exception = null;
    	try
		{
    		Questionario questionario = QuestionarioFactory.getEntity(1L);
        	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
        	pesquisa.setQuestionario(questionario);

        	Empresa empresa = EmpresaFactory.getEmpresa(1L);

        	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();

        	Resposta resposta1 = RespostaFactory.getEntity(1L);
        	Resposta resposta2 = RespostaFactory.getEntity(2L);

        	Collection<Resposta> respostas = new ArrayList<Resposta>();
        	respostas.add(resposta1);
        	respostas.add(resposta2);

        	Pergunta pergunta = PerguntaFactory.getEntity(1L);
        	pergunta.setRespostas(respostas);
        	pergunta.setQuestionario(questionario);

        	Aspecto aspecto = AspectoFactory.getEntity(1L);
        	aspecto.setQuestionario(questionario);

        	pesquisaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(pesquisa));
        	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(colaboradorQuestionarios));
        	pesquisaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(pesquisa.getId()), eq(empresa.getId())).will(returnValue(true));
        	questionarioManager.expects(once()).method("removerPerguntasDoQuestionario").with(ANYTHING);
        	pesquisaDao.expects(once()).method("removerPesquisaDoQuestionario").with(ANYTHING);
        	questionarioManager.expects(once()).method("remove").with(ANYTHING);
        	colaboradorRespostaManager.expects(once()).method("removeByQuestionarioId").with(ANYTHING).isVoid();

    		pesquisaManager.delete(pesquisa.getId(), empresa.getId());
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
    }

    public void testDeleteEmpresaInvalida() throws Exception
    {
    	Exception exception = null;
    	try
    	{
    		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    		Empresa empresa = EmpresaFactory.getEmpresa(1L);

    		pesquisaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(pesquisa.getId()), eq(empresa.getId())).will(returnValue(false));
    		pesquisaManager.delete(pesquisa.getId(), empresa.getId());
    		colaboradorRespostaManager.expects(once()).method("removeByQuestionarioId").with(ANYTHING).isVoid();
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }


    public void testDeleteExceptionNaoDeleta() throws Exception
    {
    	Exception exception = null;
    	try
    	{
    		Questionario questionario = QuestionarioFactory.getEntity(1L);
        	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
        	pesquisa.setQuestionario(questionario);

        	Empresa empresa = EmpresaFactory.getEmpresa(1L);

        	ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity(1L);
        	ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity(1L);

        	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
        	colaboradorQuestionarios.add(colaboradorQuestionario1);
        	colaboradorQuestionarios.add(colaboradorQuestionario2);

        	pesquisaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(pesquisa));
        	pesquisaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(pesquisa.getId()), eq(empresa.getId())).will(returnValue(true));
        	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(colaboradorQuestionarios));

    		pesquisaManager.delete(pesquisa.getId(), empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }

    public void testFindToListByEmpresa()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	Collection<Pesquisa> pesquisas = PesquisaFactory.getCollection();

    	pesquisaDao.expects(once()).method("findToList").with(new Constraint[] { eq(empresa.getId()), eq(1), eq(10), ANYTHING, ANYTHING }).will(returnValue(pesquisas));

    	assertEquals(pesquisas, pesquisaManager.findToListByEmpresa(empresa.getId(), 1, 10, "", 'T'));
    }

    public void testFindByQuestionario()
    {
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	pesquisaDao.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(pesquisa));

    	assertEquals(pesquisa, pesquisaManager.findByQuestionario(questionario.getId()));
    }

    public void testFindByIdProjection()
    {
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

    	pesquisaDao.expects(once()).method("findByIdProjection").with(eq(pesquisa.getId())).will(returnValue(pesquisa));

    	assertEquals(pesquisa, pesquisaManager.findByIdProjection(pesquisa.getId()));
    }

    public void testFindByIdProjectionException()
    {
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

    	pesquisaDao.expects(once()).method("findByIdProjection").with(eq(pesquisa.getId())).will(throwException(new Exception("erro")));

    	assertNull(pesquisaManager.findByIdProjection(pesquisa.getId()));
    }

    public void testUpdate()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	pesquisaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(pesquisa.getId()), eq(empresa.getId())).will(returnValue(true));
    	pesquisaDao.expects(once()).method("update").with(eq(pesquisa));
    	questionarioManager.expects(once()).method("updateQuestionario").with(eq(questionario));
    	transactionManager.expects(once()).method("commit").with(ANYTHING);

    	Exception exception = null;
    	try
		{
			pesquisaManager.update(pesquisa, questionario, empresa.getId());
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
    }

    public void testUpdateError()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	pesquisaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(pesquisa.getId()), eq(empresa.getId())).will(returnValue(true));
    	pesquisaDao.expects(once()).method("update").with(eq(pesquisa)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(pesquisa.getId(),""))));
    	transactionManager.expects(once()).method("rollback").with(ANYTHING);

    	Exception exception = null;
    	try
    	{
    		pesquisaManager.update(pesquisa, questionario, empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }

    public void testUpdateEmpresaInvalida()
    {
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	pesquisaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(pesquisa.getId()), eq(empresa.getId())).will(returnValue(false));
    	transactionManager.expects(once()).method("rollback").with(ANYTHING);

    	Exception exception = null;
    	try
    	{

    		pesquisaManager.update(pesquisa, questionario, empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }

    public void testVerificarEmpresa()
    {
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	pesquisaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(pesquisa.getId()), eq(empresa.getId())).will(returnValue(false));

    	Exception exception = null;
    	try
    	{
    		pesquisaManager.verificarEmpresaValida(pesquisa.getId(), empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);

    }

    public void testVerificarEmpresaException()
    {
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	pesquisaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(pesquisa.getId()), eq(empresa.getId())).will(returnValue(true));

    	Exception exception = null;
    	try
    	{
    		pesquisaManager.verificarEmpresaValida(pesquisa.getId(), empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNull(exception);
    }

    public void testSave() throws Exception
    {
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	questionarioManager.expects(once()).method("save").with(eq(questionario)).will(returnValue(questionario));
    	pesquisaDao.expects(once()).method("save").with(eq(pesquisa)).will(returnValue(pesquisa));
    	transactionManager.expects(once()).method("commit").with(ANYTHING);

    	Pesquisa pesquisaRetorno = pesquisaManager.save(pesquisa, questionario, empresa);

    	assertEquals(pesquisa, pesquisaRetorno);
    	assertEquals(questionario, pesquisaRetorno.getQuestionario());
    }

    public void testSaveException()
    {
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	questionarioManager.expects(once()).method("save").with(eq(questionario)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(pesquisa.getId(),""))));
    	transactionManager.expects(once()).method("rollback").with(ANYTHING);

    	Exception exception = null;
		try
		{
			pesquisaManager.save(pesquisa, questionario, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }


    public void testClonarPesquisa() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();

    	Questionario questionario = QuestionarioFactory.getEntity();
    	questionario.setEmpresa(empresa);

    	Pesquisa pesquisa = PesquisaFactory.getEntity();
    	pesquisa.setQuestionario(questionario);

    	Aspecto aspecto = AspectoFactory.getEntity();

    	Resposta resposta1 = RespostaFactory.getEntity(1L);
    	Resposta resposta2 = RespostaFactory.getEntity(2L);
    	Resposta resposta3 = RespostaFactory.getEntity(3L);

    	Collection<Resposta> respostas1 = new ArrayList<Resposta>();
    	respostas1.add(resposta1);
    	respostas1.add(resposta2);

    	Collection<Resposta> respostas2 = new ArrayList<Resposta>();
    	respostas2.add(resposta3);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setQuestionario(questionario);
    	pergunta1.setAspecto(aspecto);
    	pergunta1.setRespostas(respostas1);

    	Pergunta pergunta2 = PerguntaFactory.getEntity(1L);
    	pergunta2.setQuestionario(questionario);
    	pergunta2.setAspecto(aspecto);
    	pergunta2.setRespostas(respostas2);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	Pesquisa pesquisaClonada = PesquisaFactory.getEntity();

    	Questionario questionarioClonado = QuestionarioFactory.getEntity();
    	questionarioClonado.setEmpresa(empresa);

    	TransactionStatus status = null;

    	pesquisaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(pesquisa));
    	questionarioManager.expects(once()).method("clonarQuestionario").withAnyArguments().will(returnValue(questionarioClonado));
    	pesquisaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(pesquisaClonada));
    	perguntaManager.expects(atLeastOnce()).method("clonarPerguntas").withAnyArguments();

    	Pesquisa retorno = pesquisaManager.clonarPesquisa(pesquisa.getId(), null);
    	assertEquals(questionarioClonado.getId(), retorno.getId());

    	pesquisaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(pesquisa));
    	
    	questionarioManager.expects(once()).method("clonarQuestionario").with(ANYTHING, ANYTHING).will(returnValue(questionarioClonado));
    	pesquisaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(pesquisaClonada));
    
    	questionarioManager.expects(once()).method("clonarQuestionario").with(ANYTHING, ANYTHING).will(returnValue(questionarioClonado));
    	pesquisaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(pesquisaClonada));
    	
    	perguntaManager.expects(atLeastOnce()).method("clonarPerguntas").withAnyArguments();
    	
    	retorno = pesquisaManager.clonarPesquisa(pesquisa.getId(), new Long[]{1L, 2L});
    	assertEquals(questionarioClonado.getId(), retorno.getId());
    }

    public void testClonarPesquisaComException() throws Exception
    {
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

    	Questionario questionarioClonado = QuestionarioFactory.getEntity();

    	Pesquisa pesquisaClonada = PesquisaFactory.getEntity();

    	pesquisaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(pesquisa));
    	questionarioManager.expects(once()).method("clonarQuestionario").with(ANYTHING, ANYTHING).will(returnValue(questionarioClonado));
    	pesquisaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(pesquisaClonada));

    	Exception exception = null;

    	try
		{
    		pesquisaManager.clonarPesquisa(pesquisa.getId(), null);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }

    public void testFindParaSerRespondida()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	pesquisaDao.expects(once()).method("findById").with(ANYTHING).will(returnValue(pesquisa));

    	Collection<Pergunta> pergutas = PerguntaFactory.getCollection(3L);

    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(pesquisa.getId())).will(returnValue(pergutas));

    	assertEquals(pesquisa, pesquisaManager.findParaSerRespondida(pesquisa.getId()));

    }

    public void testGetIdByQuestionario()
    {
    	pesquisaDao.expects(once()).method("getIdByQuestionario").with(ANYTHING).will(returnValue(1L));

    	pesquisaManager.getIdByQuestionario(1L);

    }

    public void testGetCount()
    {
    	pesquisaDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(1));

    	pesquisaManager.getCount(1L, null);
    }

}

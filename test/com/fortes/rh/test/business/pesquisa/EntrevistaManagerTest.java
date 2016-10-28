package com.fortes.rh.test.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.EntrevistaManagerImpl;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.dao.pesquisa.EntrevistaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.EntrevistaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.opensymphony.webwork.ServletActionContext;

public class EntrevistaManagerTest extends MockObjectTestCase
{
	private EntrevistaManagerImpl entrevistaManager = new EntrevistaManagerImpl();
	private Mock entrevistaDao;
	private Mock perguntaManager;
	private Mock questionarioManager;
	private Mock colaboradorQuestionarioManager;
	private Mock transactionManager;


    protected void setUp() throws Exception
    {
        entrevistaDao = new Mock(EntrevistaDao.class);
        entrevistaManager.setDao((EntrevistaDao) entrevistaDao.proxy());

        perguntaManager = new Mock(PerguntaManager.class);
        entrevistaManager.setPerguntaManager((PerguntaManager) perguntaManager.proxy());

        questionarioManager = new Mock(QuestionarioManager.class);
        entrevistaManager.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());

        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        entrevistaManager.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        entrevistaManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
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
        	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
        	entrevista.setQuestionario(questionario);

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

        	entrevistaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(entrevista));
        	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(colaboradorQuestionarios));
        	entrevistaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(entrevista.getId()), eq(empresa.getId())).will(returnValue(true));
        	questionarioManager.expects(once()).method("removerPerguntasDoQuestionario").with(ANYTHING);
        	entrevistaDao.expects(once()).method("remove").with(ANYTHING);
        	questionarioManager.expects(once()).method("remove").with(ANYTHING);

    		entrevistaManager.delete(entrevista.getId(), empresa.getId());
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
    }
    
    public void testDeleteException() throws Exception
    {
    	Exception exception = null;
    	try
    	{
    		Questionario questionario = QuestionarioFactory.getEntity(1L);
    		Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    		entrevista.setQuestionario(questionario);
    		
    		Empresa empresa = EmpresaFactory.getEmpresa(1L);
    		
    		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
    		colaboradorQuestionarios.add(new ColaboradorQuestionario());
    		
    		entrevistaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(entrevista.getId()), eq(empresa.getId())).will(returnValue(true));
    		entrevistaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(entrevista));
    		colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(colaboradorQuestionarios));
    		
    		entrevistaManager.delete(entrevista.getId(), empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}
    	
    	assertNotNull(exception);
    }

    public void testDeleteEmpresaInvalida() throws Exception
    {
    	Exception exception = null;
    	try
    	{
    		Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    		Empresa empresa = EmpresaFactory.getEmpresa(1L);

    		entrevistaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(entrevista.getId()), eq(empresa.getId())).will(returnValue(false));
    		entrevistaManager.delete(entrevista.getId(), empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }

    public void testDeleteErro() throws Exception
    {
    	Exception exception = null;
    	try
    	{
    		Questionario questionario = QuestionarioFactory.getEntity(1L);
        	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
        	entrevista.setQuestionario(questionario);

        	Empresa empresa = EmpresaFactory.getEmpresa(1L);

        	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
        	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
        	colaboradorQuestionarios.add(colaboradorQuestionario);

        	Aspecto aspecto = AspectoFactory.getEntity(1L);
        	aspecto.setQuestionario(questionario);

        	entrevistaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(entrevista.getId()), eq(empresa.getId())).will(returnValue(true));
        	entrevistaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(entrevista));
        	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(colaboradorQuestionarios));

    		entrevistaManager.delete(entrevista.getId(), empresa.getId());
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

    	Collection<Entrevista> entrevistas = EntrevistaFactory.getCollection();

    	entrevistaDao.expects(once()).method("findToList").with(eq(empresa.getId()), eq(1), eq(10)).will(returnValue(entrevistas));

    	assertEquals(entrevistas, entrevistaManager.findToListByEmpresa(empresa.getId(), 1, 10));
    }

    public void testFindByIdProjection()
    {
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);

    	entrevistaDao.expects(once()).method("findByIdProjection").with(eq(entrevista.getId())).will(returnValue(entrevista));

    	assertEquals(entrevista, entrevistaManager.findByIdProjection(entrevista.getId()));
    }
    
    public void testGetIdByQuestionario()
    {
    	Long questionarioId = 1L;
    	
    	entrevistaDao.expects(once()).method("getIdByQuestionario").with(eq(questionarioId)).will(returnValue(questionarioId));
    	
    	assertEquals(questionarioId, entrevistaManager.getIdByQuestionario(questionarioId));
    }
    
    public void testGetCount()
    {
    	Long empresaId = 1L;
    	Integer retorno = 1;
    	
    	entrevistaDao.expects(once()).method("getCount").with(eq(empresaId)).will(returnValue(retorno));
    	
    	assertEquals(retorno, entrevistaManager.getCount(empresaId));
    }
    
    public void testFindAllSelect()
    {
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Collection<Entrevista> entrevistas = EntrevistaFactory.getCollection();
    	
    	boolean ativa = true;
    	entrevistaDao.expects(once()).method("findAllSelect").with(eq(entrevista.getId()), eq(ativa)).will(returnValue(entrevistas));
    	
    	assertEquals(entrevistas, entrevistaManager.findAllSelect(empresa.getId(), ativa));
    }

    public void testFindByIdProjectionException()
    {
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);

    	entrevistaDao.expects(once()).method("findByIdProjection").with(eq(entrevista.getId())).will(throwException(new Exception("erro")));

    	assertNull(entrevistaManager.findByIdProjection(entrevista.getId()));
    }

    public void testUpdate()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	entrevista.setQuestionario(questionario);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	entrevistaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(entrevista.getId()), eq(empresa.getId())).will(returnValue(true));
    	entrevistaDao.expects(once()).method("update").with(eq(entrevista));
    	questionarioManager.expects(once()).method("updateQuestionario").with(eq(questionario));
    	transactionManager.expects(once()).method("commit").with(ANYTHING);

    	Exception exception = null;
    	try
		{
			entrevistaManager.update(entrevista, questionario, empresa.getId());
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
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	entrevista.setQuestionario(questionario);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	entrevistaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(entrevista.getId()), eq(empresa.getId())).will(returnValue(true));
    	entrevistaDao.expects(once()).method("update").with(eq(entrevista)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(entrevista.getId(),""))));
    	transactionManager.expects(once()).method("rollback").with(ANYTHING);

    	Exception exception = null;
    	try
    	{
    		entrevistaManager.update(entrevista, questionario, empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }

    public void testUpdateEmpresaInvalida()
    {
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	entrevistaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(entrevista.getId()), eq(empresa.getId())).will(returnValue(false));
    	transactionManager.expects(once()).method("rollback").with(ANYTHING);

    	Exception exception = null;
    	try
    	{

    		entrevistaManager.update(entrevista, questionario, empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }

    public void testVerificarEmpresa()
    {
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	entrevistaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(entrevista.getId()), eq(empresa.getId())).will(returnValue(false));

    	Exception exception = null;
    	try
    	{
    		entrevistaManager.verificarEmpresaValida(entrevista.getId(), empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);

    }

    public void testVerificarEmpresaException()
    {
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	entrevistaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(entrevista.getId()), eq(empresa.getId())).will(returnValue(true));

    	Exception exception = null;
    	try
    	{
    		entrevistaManager.verificarEmpresaValida(entrevista.getId(), empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNull(exception);
    }

    public void testSave() throws Exception
    {
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	questionarioManager.expects(once()).method("save").with(eq(questionario)).will(returnValue(questionario));
    	entrevistaDao.expects(once()).method("save").with(eq(entrevista)).will(returnValue(entrevista));
    	transactionManager.expects(once()).method("commit").with(ANYTHING);

    	Entrevista entrevistaRetorno = entrevistaManager.save(entrevista, questionario, empresa);

    	assertEquals(entrevista, entrevistaRetorno);
    	assertEquals(questionario, entrevistaRetorno.getQuestionario());
    }

    public void testSaveException()
    {
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	questionarioManager.expects(once()).method("save").with(eq(questionario)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(entrevista.getId(),""))));
    	transactionManager.expects(once()).method("rollback").with(ANYTHING);

    	Exception exception = null;
		try
		{
			entrevistaManager.save(entrevista, questionario, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }

    public void testClonarEntrevista() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();

    	Questionario questionario = QuestionarioFactory.getEntity();
    	questionario.setEmpresa(empresa);

    	Entrevista entrevista = EntrevistaFactory.getEntity();
    	entrevista.setQuestionario(questionario);

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

    	Entrevista entrevistaClonada = EntrevistaFactory.getEntity();

    	Questionario questionarioClonado = QuestionarioFactory.getEntity();

    	TransactionStatus status = null;

    	entrevistaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(entrevista));
    	entrevistaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(entrevistaClonada));
    	perguntaManager.expects(once()).method("clonarPerguntas").with(ANYTHING, ANYTHING, ANYTHING).isVoid();
    	questionarioManager.expects(once()).method("findById").withAnyArguments().will(returnValue(questionarioClonado));
    	questionarioManager.expects(once()).method("clonarQuestionario").with(ANYTHING, ANYTHING).will(returnValue(questionarioClonado));
    	
    	Exception ret = null;
    	try {
    		entrevistaManager.clonarEntrevista(entrevista.getId(), empresa.getId());
			
		} catch (Exception e) {
			ret = e;
		}
		assertNull(ret);
    }

    public void testClonarEntrevistaComException() throws Exception
    {
    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	entrevistaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(entrevista));

    	Exception exception = null;

    	try
		{
    		entrevistaManager.clonarEntrevista(entrevista.getId(), new Long[] { null });
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

    	Entrevista entrevista = EntrevistaFactory.getEntity(1L);
    	entrevista.setQuestionario(questionario);

    	entrevistaDao.expects(once()).method("findById").with(ANYTHING).will(returnValue(entrevista));

    	Collection<Pergunta> pergutas = PerguntaFactory.getCollection(3L);

    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(entrevista.getId())).will(returnValue(pergutas));

    	assertEquals(entrevista, entrevistaManager.findParaSerRespondida(entrevista.getId()));

    }

}

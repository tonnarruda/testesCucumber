package com.fortes.rh.test.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionUsageException;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.FichaMedicaManagerImpl;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.dao.pesquisa.FichaMedicaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.FichaMedicaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.opensymphony.webwork.ServletActionContext;

public class FichaMedicaManagerTest extends MockObjectTestCase
{
	private FichaMedicaManagerImpl fichaMedicaManager = new FichaMedicaManagerImpl();
	private Mock fichaMedicaDao;
	private Mock perguntaManager;
	private Mock questionarioManager;
	private Mock colaboradorQuestionarioManager;
	private Mock transactionManager;


    protected void setUp() throws Exception
    {
        fichaMedicaDao = new Mock(FichaMedicaDao.class);
        fichaMedicaManager.setDao((FichaMedicaDao) fichaMedicaDao.proxy());

        perguntaManager = new Mock(PerguntaManager.class);
        fichaMedicaManager.setPerguntaManager((PerguntaManager) perguntaManager.proxy());

        questionarioManager = new Mock(QuestionarioManager.class);
        fichaMedicaManager.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());

        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        fichaMedicaManager.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        fichaMedicaManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

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
        	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
        	fichaMedica.setQuestionario(questionario);

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

        	fichaMedicaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(fichaMedica));
        	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(colaboradorQuestionarios));
        	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
        	fichaMedicaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(fichaMedica.getId()), eq(empresa.getId())).will(returnValue(true));
        	questionarioManager.expects(once()).method("removerPerguntasDoQuestionario").with(ANYTHING);
        	fichaMedicaDao.expects(once()).method("remove").with(ANYTHING);
        	questionarioManager.expects(once()).method("remove").with(ANYTHING);

    		transactionManager.expects(once()).method("commit").with(ANYTHING);

    		fichaMedicaManager.delete(fichaMedica.getId(), empresa.getId());
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
    		FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    		fichaMedica.setQuestionario(questionario);
    		
    		Empresa empresa = EmpresaFactory.getEmpresa(1L);
    		
    		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
    		colaboradorQuestionarios.add(new ColaboradorQuestionario());
    		
    		fichaMedicaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(fichaMedica.getId()), eq(empresa.getId())).will(returnValue(true));
    		fichaMedicaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(fichaMedica));
    		colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(colaboradorQuestionarios));
    		
    		fichaMedicaManager.delete(fichaMedica.getId(), empresa.getId());
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
    		FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    		Empresa empresa = EmpresaFactory.getEmpresa(1L);

    		fichaMedicaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(fichaMedica.getId()), eq(empresa.getId())).will(returnValue(false));
    		fichaMedicaManager.delete(fichaMedica.getId(), empresa.getId());
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
        	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
        	fichaMedica.setQuestionario(questionario);

        	Empresa empresa = EmpresaFactory.getEmpresa(1L);

        	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();

        	Aspecto aspecto = AspectoFactory.getEntity(1L);
        	aspecto.setQuestionario(questionario);

        	fichaMedicaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(fichaMedica));
        	colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(colaboradorQuestionarios));
        	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
        	fichaMedicaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(fichaMedica.getId()), eq(empresa.getId())).will(returnValue(true));
        	questionarioManager.expects(once()).method("removerPerguntasDoQuestionario").with(ANYTHING);
        	fichaMedicaDao.expects(once()).method("remove").with(ANYTHING);
        	questionarioManager.expects(once()).method("remove").with(ANYTHING);

    		transactionManager.expects(once()).method("commit").with(ANYTHING).will(throwException(new TransactionUsageException("Erro")));

    		transactionManager.expects(once()).method("rollback").with(ANYTHING);

    		fichaMedicaManager.delete(fichaMedica.getId(), empresa.getId());
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

    	Collection<FichaMedica> fichaMedicas = FichaMedicaFactory.getCollection();

    	fichaMedicaDao.expects(once()).method("findToList").with(eq(empresa.getId()), eq(1), eq(10)).will(returnValue(fichaMedicas));

    	assertEquals(fichaMedicas, fichaMedicaManager.findToListByEmpresa(empresa.getId(), 1, 10));
    }

    public void testFindByIdProjection()
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);

    	fichaMedicaDao.expects(once()).method("findByIdProjection").with(eq(fichaMedica.getId())).will(returnValue(fichaMedica));

    	assertEquals(fichaMedica, fichaMedicaManager.findByIdProjection(fichaMedica.getId()));
    }
    
    public void testGetIdByQuestionario()
    {
    	Long questionarioId = 1L;
    	
    	fichaMedicaDao.expects(once()).method("getIdByQuestionario").with(eq(questionarioId)).will(returnValue(questionarioId));
    	
    	assertEquals(questionarioId, fichaMedicaManager.getIdByQuestionario(questionarioId));
    }
    
    public void testGetCount()
    {
    	Long empresaId = 1L;
    	Integer retorno = 1;
    	
    	fichaMedicaDao.expects(once()).method("getCount").with(eq(empresaId)).will(returnValue(retorno));
    	
    	assertEquals(retorno, fichaMedicaManager.getCount(empresaId));
    }
    
    public void testFindAllSelect()
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Collection<FichaMedica> fichaMedicas = FichaMedicaFactory.getCollection();
    	
    	boolean ativa = true;
    	fichaMedicaDao.expects(once()).method("findAllSelect").with(eq(fichaMedica.getId()), eq(ativa)).will(returnValue(fichaMedicas));
    	
    	assertEquals(fichaMedicas, fichaMedicaManager.findAllSelect(empresa.getId(), ativa));
    }

    public void testFindByIdProjectionException()
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);

    	fichaMedicaDao.expects(once()).method("findByIdProjection").with(eq(fichaMedica.getId())).will(throwException(new Exception("erro")));

    	assertNull(fichaMedicaManager.findByIdProjection(fichaMedica.getId()));
    }

    public void testUpdate()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	fichaMedica.setQuestionario(questionario);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	fichaMedicaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(fichaMedica.getId()), eq(empresa.getId())).will(returnValue(true));
    	fichaMedicaDao.expects(once()).method("update").with(eq(fichaMedica));
    	questionarioManager.expects(once()).method("updateQuestionario").with(eq(questionario));
    	transactionManager.expects(once()).method("commit").with(ANYTHING);

    	Exception exception = null;
    	try
		{
			fichaMedicaManager.update(fichaMedica, questionario, empresa.getId());
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
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	fichaMedica.setQuestionario(questionario);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	fichaMedicaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(fichaMedica.getId()), eq(empresa.getId())).will(returnValue(true));
    	fichaMedicaDao.expects(once()).method("update").with(eq(fichaMedica)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(fichaMedica.getId(),""))));
    	transactionManager.expects(once()).method("rollback").with(ANYTHING);

    	Exception exception = null;
    	try
    	{
    		fichaMedicaManager.update(fichaMedica, questionario, empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }

    public void testUpdateEmpresaInvalida()
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	fichaMedicaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(fichaMedica.getId()), eq(empresa.getId())).will(returnValue(false));
    	transactionManager.expects(once()).method("rollback").with(ANYTHING);

    	Exception exception = null;
    	try
    	{

    		fichaMedicaManager.update(fichaMedica, questionario, empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }

    public void testVerificarEmpresa()
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	fichaMedicaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(fichaMedica.getId()), eq(empresa.getId())).will(returnValue(false));

    	Exception exception = null;
    	try
    	{
    		fichaMedicaManager.verificarEmpresaValida(fichaMedica.getId(), empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);

    }

    public void testVerificarEmpresaException()
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	fichaMedicaDao.expects(once()).method("verificaEmpresaDoQuestionario").with(eq(fichaMedica.getId()), eq(empresa.getId())).will(returnValue(true));

    	Exception exception = null;
    	try
    	{
    		fichaMedicaManager.verificarEmpresaValida(fichaMedica.getId(), empresa.getId());
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNull(exception);
    }

    public void testSave() throws Exception
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	questionarioManager.expects(once()).method("save").with(eq(questionario)).will(returnValue(questionario));
    	fichaMedicaDao.expects(once()).method("save").with(eq(fichaMedica)).will(returnValue(fichaMedica));
    	transactionManager.expects(once()).method("commit").with(ANYTHING);

    	FichaMedica fichaMedicaRetorno = fichaMedicaManager.save(fichaMedica, questionario, empresa);

    	assertEquals(fichaMedica, fichaMedicaRetorno);
    	assertEquals(questionario, fichaMedicaRetorno.getQuestionario());
    }

    public void testSaveException()
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
    	questionarioManager.expects(once()).method("save").with(eq(questionario)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(fichaMedica.getId(),""))));
    	transactionManager.expects(once()).method("rollback").with(ANYTHING);

    	Exception exception = null;
		try
		{
			fichaMedicaManager.save(fichaMedica, questionario, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }


    public void testClonarFichaMedica() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();

    	Questionario questionario = QuestionarioFactory.getEntity();
    	questionario.setEmpresa(empresa);

    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity();
    	fichaMedica.setQuestionario(questionario);

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

    	FichaMedica fichaMedicaClonada = FichaMedicaFactory.getEntity();

    	Questionario questionarioClonado = QuestionarioFactory.getEntity();

    	TransactionStatus status = null;

    	fichaMedicaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(fichaMedica));
    	questionarioManager.expects(once()).method("clonarQuestionario").with(ANYTHING, ANYTHING).will(returnValue(questionarioClonado));
    	fichaMedicaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(fichaMedicaClonada));
    	perguntaManager.expects(atLeastOnce()).method("clonarPerguntas").with(ANYTHING, ANYTHING, ANYTHING);

    	fichaMedicaManager.clonarFichaMedica(fichaMedica.getId(), empresa.getId());
    }

    public void testClonarFichaMedicaComException() throws Exception
    {
    	FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);

    	Questionario questionarioClonado = QuestionarioFactory.getEntity();

    	FichaMedica fichaMedicaClonada = FichaMedicaFactory.getEntity();

    	fichaMedicaDao.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(fichaMedica));
    	questionarioManager.expects(once()).method("clonarQuestionario").with(ANYTHING, ANYTHING).will(returnValue(questionarioClonado));
    	fichaMedicaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(fichaMedicaClonada));

    	Exception exception = null;

    	try
		{
    		fichaMedicaManager.clonarFichaMedica(fichaMedica.getId(), 1L);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }

}

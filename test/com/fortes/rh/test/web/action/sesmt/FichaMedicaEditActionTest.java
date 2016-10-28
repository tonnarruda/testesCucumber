package com.fortes.rh.test.web.action.sesmt;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.pesquisa.FichaMedicaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.FichaMedicaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.web.action.pesquisa.FichaMedicaEditAction;

public class FichaMedicaEditActionTest extends MockObjectTestCase 
{
	private FichaMedicaEditAction action;
	private Mock manager;
	
	@Override
	protected void setUp() throws Exception 
	{
		action = new FichaMedicaEditAction();
		manager = mock(FichaMedicaManager.class);
		action.setFichaMedicaManager((FichaMedicaManager) manager.proxy());
		
		Empresa empresa = EmpresaFactory.getEmpresa(5L);
		action.setEmpresaSistema(empresa);
	}
	
	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
	}
	
	public void testPrepareInsert() throws Exception
	{
		assertEquals("success", action.prepareInsert());
	}
	
	public void testPrepareUpdate() throws Exception
	{
		FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
		action.setFichaMedica(fichaMedica);
		manager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(fichaMedica));
		
		assertEquals("success", action.prepareUpdate());
	}

	public void testInsert() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(33L);
		FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
		fichaMedica.setQuestionario(questionario);
		action.setFichaMedica(fichaMedica);
		manager.expects(once()).method("save").with(eq(fichaMedica),eq(questionario),eq(action.getEmpresaSistema())).will(returnValue(fichaMedica));

		assertEquals("success", action.insert());
		assertEquals(questionario, action.getQuestionario());
	}
	public void testInsertException() 
	{
		Questionario questionario = QuestionarioFactory.getEntity(33L);
		FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
		fichaMedica.setQuestionario(questionario);
		action.setFichaMedica(fichaMedica);
		
		manager.expects(once()).method("save").with(eq(fichaMedica),eq(questionario),eq(action.getEmpresaSistema())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		
		assertEquals("input",action.insert());
	}

	public void testUpdate() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(33L);
		FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
		fichaMedica.setQuestionario(questionario);
		action.setFichaMedica(fichaMedica);
		
		manager.expects(once()).method("update").with(eq(fichaMedica),eq(questionario),eq(action.getEmpresaSistema().getId())).isVoid();

		assertEquals("success", action.update());
	}
	public void testUpdateException() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(33L);
		FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
		fichaMedica.setQuestionario(questionario);
		action.setFichaMedica(fichaMedica);
		
		manager.expects(once()).method("update").with(eq(fichaMedica),eq(questionario),eq(action.getEmpresaSistema().getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		
		assertEquals("input", action.update());
	}
	
	public void testGravar() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(33L);
		FichaMedica fichaMedica = FichaMedicaFactory.getEntity(1L);
		fichaMedica.setQuestionario(questionario);
		action.setFichaMedica(fichaMedica);
		
		manager.expects(once()).method("update").with(eq(fichaMedica),eq(questionario),eq(action.getEmpresaSistema().getId())).isVoid();

		assertEquals("success", action.gravar());
	}

	public void testGetSet() throws Exception
	{
		action.setFichaMedica(null);
		assertNotNull(action.getFichaMedica());
		action.setQuantidadeDeResposta(10);
		assertEquals(10,action.getQuantidadeDeResposta());
		action.setQuestionario(new Questionario());

	}

}

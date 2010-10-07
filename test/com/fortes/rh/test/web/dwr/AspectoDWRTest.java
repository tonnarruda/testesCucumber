package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.web.dwr.AspectoDWR;

public class AspectoDWRTest extends MockObjectTestCase
{
	private AspectoDWR aspectoDWR;
	private Mock aspectoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		aspectoDWR = new AspectoDWR();

		aspectoManager = new Mock(AspectoManager.class);

		aspectoDWR.setAspectoManager((AspectoManager) aspectoManager.proxy());
	}

	public void testGetAspectos()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Aspecto aspecto = AspectoFactory.getEntity(1L);
		aspecto.setQuestionario(questionario);

		Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
		aspectos.add(aspecto);

		aspectoManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(aspectos));

		assertEquals(1, (aspectoDWR.getAspectos(questionario.getId())).length);
	}
	
	public void testGetAspectosId()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		
		Aspecto aspecto = AspectoFactory.getEntity(1L);
		aspecto.setQuestionario(questionario);
		
		Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
		aspectos.add(aspecto);
		
		aspectoManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(aspectos));
		
		assertEquals(1, (aspectoDWR.getAspectosId(questionario.getId())).size());
	}

	public void testGetAspectosSemQuestionario()
	{
		Aspecto aspecto = AspectoFactory.getEntity(1L);

		Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
		aspectos.add(aspecto);

		assertEquals(0, (aspectoDWR.getAspectos(null)).length);
	}

}

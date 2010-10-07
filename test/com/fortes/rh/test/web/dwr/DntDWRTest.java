package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.web.dwr.DntDWR;

public class DntDWRTest extends MockObjectTestCase
{
	private DntDWR dntDWR;
	private Mock turmaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		dntDWR = new DntDWR();

		turmaManager = new Mock(TurmaManager.class);
		dntDWR.setTurmaManager((TurmaManager) turmaManager.proxy());
	}

	public void testGetTurmas()
	{
		Curso curso = CursoFactory.getEntity();
		curso.setId(1L);

		Turma turma = TurmaFactory.getEntity();
		turma.setId(1L);
		turma.setCurso(curso);

		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma);

		turmaManager.expects(once()).method("findToList").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(turmas));

		Map retorno = dntDWR.getTurmas(curso.getId().toString());

		assertEquals(1, retorno.size());
	}
}

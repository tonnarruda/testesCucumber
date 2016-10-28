package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.dwr.DiaTurmaDWR;

public class DiaTurmaDWRTest extends MockObjectTestCase
{
	private DiaTurmaDWR diaTurmaDWR;
	private Mock diaTurmaManager;
	private Mock turmaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		diaTurmaDWR = new DiaTurmaDWR();

		diaTurmaManager = new Mock(DiaTurmaManager.class);
		turmaManager = new Mock(TurmaManager.class);

		diaTurmaDWR.setDiaTurmaManager((DiaTurmaManager) diaTurmaManager.proxy());
		diaTurmaDWR.setTurmaManager((TurmaManager) turmaManager.proxy());
	}

	public void testGetDias() throws Exception
	{
		DiaTurma diaTurma = new DiaTurma();
		diaTurma.setId(1L);
		diaTurma.setDia(DateUtil.criarDataMesAno(10, 12, 2000));

		Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
		diaTurmas.add(diaTurma);

		diaTurmaManager.expects(once()).method("montaListaDias").with(ANYTHING, ANYTHING, eq(false)).will(returnValue(diaTurmas));

		String dataIniStr = "01/12/2000";
		String dataFimStr = "20/12/2000";

		Collection<DiaTurma> retorno = diaTurmaDWR.getDias(dataIniStr, dataFimStr, false);

		assertEquals(1, retorno.size());
	}

	public void testGetDiasDataIniMaiorDataFim() throws Exception
	{
		String dataIniStr = "22/12/2000";
		String dataFimStr = "20/12/2000";

		Collection<DiaTurma> retorno = diaTurmaDWR.getDias(dataIniStr, dataFimStr, false);

		assertNull(retorno);
	}

	public void testGetDiasPorTurma()
	{
		Turma turma = TurmaFactory.getEntity();
		turma.setId(1L);
		turma.setDataPrevIni(DateUtil.criarDataMesAno(10, 12, 2000));
		turma.setDataPrevFim(DateUtil.criarDataMesAno(12, 12, 2000));

		DiaTurma diaTurma = new DiaTurma();
		diaTurma.setId(1L);
		diaTurma.setTurma(turma);
		diaTurma.setDia(DateUtil.criarDataMesAno(10, 12, 2000));

		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma);

		turmaManager.expects(once()).method("findToList").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(turmas));

		Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
		diaTurmas.add(diaTurma);

		diaTurmaManager.expects(once()).method("find").with(ANYTHING, ANYTHING).will(returnValue(diaTurmas));

		String retorno = diaTurmaDWR.getDiasPorTurma(turma.getId(), "teste");

		assertNotNull(retorno);
	}

	public void testGetDiasPorTurmaDataFimMariorDataIni()
	{
		Turma turma = TurmaFactory.getEntity();
		turma.setId(1L);
		turma.setDataPrevIni(DateUtil.criarDataMesAno(15, 12, 2000));
		turma.setDataPrevFim(DateUtil.criarDataMesAno(12, 12, 2000));

		DiaTurma diaTurma = new DiaTurma();
		diaTurma.setId(1L);
		diaTurma.setTurma(turma);
		diaTurma.setDia(DateUtil.criarDataMesAno(10, 12, 2000));

		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma);

		turmaManager.expects(once()).method("findToList").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(turmas));
		
		assertNull(diaTurmaDWR.getDiasPorTurma(turma.getId(), "teste"));
	}

	public void testGetDiasPorTurmaComExcecao()
	{
		Turma turma = TurmaFactory.getEntity();
		turma.setId(1L);
		turma.setDataPrevIni(DateUtil.criarDataMesAno(05, 12, 2000));
		turma.setDataPrevFim(DateUtil.criarDataMesAno(12, 12, 2000));

		DiaTurma diaTurma = new DiaTurma();
		diaTurma.setId(1L);

		Collection<Turma> turmas = new ArrayList<Turma>();
		turmas.add(turma);

		turmaManager.expects(once()).method("findToList").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(turmas));
		diaTurmaManager.expects(once()).method("find").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));

		assertNull(diaTurmaDWR.getDiasPorTurma(turma.getId(), "teste"));
	}

}

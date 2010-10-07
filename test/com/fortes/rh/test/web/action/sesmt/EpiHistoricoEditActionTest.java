package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EpiHistoricoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.web.action.sesmt.EpiHistoricoEditAction;

public class EpiHistoricoEditActionTest extends MockObjectTestCase
{
	private EpiHistoricoEditAction action;
	private Mock epiHistoricoManager;
	private Mock epiManager;

	protected void setUp()
	{
		action = new EpiHistoricoEditAction();
		epiHistoricoManager = new Mock(EpiHistoricoManager.class);
		epiManager = new Mock(EpiManager.class);

		action.setEpiHistoricoManager((EpiHistoricoManager) epiHistoricoManager.proxy());
		action.setEpiManager((EpiManager)epiManager.proxy());
	}

	public void testDelete() throws Exception
	{
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setId(1L);
		action.setEpiHistorico(epiHistorico);

		epiHistoricoManager.expects(once()).method("remove").with(ANYTHING);

		assertEquals("success", action.delete());
	}

	public void testPrepareInsert() throws Exception
	{
		action.getEpi().setId(1L);
		epiManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(new Epi()));
		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setId(1L);
		action.setEpiHistorico(epiHistorico);
		action.getEpiHistorico().setEpi(new Epi());

		epiHistoricoManager.expects(once()).method("findById").with(eq(epiHistorico.getId())).will(returnValue(epiHistorico));
		assertEquals("success", action.prepareUpdate());
	}

	public void testInsert() throws Exception
	{
		Epi epi = new Epi();
		epi.setId(1L);

		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setId(1L);
		epiHistorico.setData(new Date());
		epiHistorico.setEpi(epi);
		action.setEpiHistorico(epiHistorico);

		Collection<EpiHistorico> historicos = new ArrayList<EpiHistorico>();

		epiHistoricoManager.expects(atLeastOnce()).method("find").with(ANYTHING, ANYTHING).will(returnValue(historicos));
		epiHistoricoManager.expects(atLeastOnce()).method("save").with(eq(epiHistorico));
		assertEquals("success", action.insert());

		historicos.add(epiHistorico);

		assertEquals("success", action.insert());

		EpiHistorico epiHistorico2 = new EpiHistorico();
		epiHistorico2.setId(2L);

		historicos.add(epiHistorico2);

		assertEquals("error", action.insert());

	}

	public void testUpdate() throws Exception
	{
		Epi epi = new Epi();
		epi.setId(1L);

		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setId(1L);
		epiHistorico.setData(new Date());
		epiHistorico.setEpi(epi);
		action.setEpiHistorico(epiHistorico);

		Collection<EpiHistorico> historicos = new ArrayList<EpiHistorico>();

		epiHistoricoManager.expects(atLeastOnce()).method("find").with(ANYTHING, ANYTHING).will(returnValue(historicos));
		epiHistoricoManager.expects(atLeastOnce()).method("update").with(eq(epiHistorico));

		assertEquals("success", action.update());

		historicos.add(epiHistorico);

		assertEquals("success", action.update());

		EpiHistorico epiHistorico2 = new EpiHistorico();
		epiHistorico2.setId(2L);

		historicos.add(epiHistorico2);


		assertEquals("error", action.update());
	}

	public void testGetSet()
	{
		action.getModel();
		action.getEpiHistorico();
		action.getEpis();
		action.setEpis(null);
		action.isEpiEhFardamento();
	}
}

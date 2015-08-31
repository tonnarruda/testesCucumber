package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

import com.fortes.rh.business.geral.BeneficioManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.HistoricoColaboradorBeneficioManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.geral.BeneficioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.geral.HistoricoColaboradorBeneficioEditAction;

public class HistoricoColaboradorBeneficioEditActionTest extends MockObjectTestCase
{
	private HistoricoColaboradorBeneficioEditAction action;
	private Mock manager;
	private Mock colaboradorManager;
	private Mock beneficioManager;

	protected void setUp()
	{
		action = new HistoricoColaboradorBeneficioEditAction();
		manager = new Mock(HistoricoColaboradorBeneficioManager.class);
		colaboradorManager = new Mock(ColaboradorManager.class);
		beneficioManager = new Mock(BeneficioManager.class);
		action.setHistoricoColaboradorBeneficioManager((HistoricoColaboradorBeneficioManager) manager.proxy());
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		action.setBeneficioManager((BeneficioManager) beneficioManager.proxy());
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

	public void testPrepareInsert() throws Exception
	{
		Colaborador c1 = new Colaborador();
    	c1.setId(1L);
    	action.setColaborador(c1);

    	Beneficio b1 = new Beneficio();
    	b1.setId(1L);

    	Beneficio b2 = new Beneficio();
    	b2.setId(1L);

    	Collection<Beneficio> beneficios1 = new ArrayList<Beneficio>();
    	beneficios1.add(b1);
    	beneficios1.add(b2);

    	HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
    	historico.setId(1L);
    	historico.setColaborador(c1);
    	historico.setBeneficios(beneficios1);

    	Collection<Beneficio> beneficios2 = new ArrayList<Beneficio>();
    	beneficios2.add(b1);

    	HistoricoColaboradorBeneficio ultimoHistorico = new HistoricoColaboradorBeneficio();
    	ultimoHistorico.setId(2L);
    	ultimoHistorico.setColaborador(c1);
    	ultimoHistorico.setBeneficios(beneficios2);

    	manager.expects(once()).method("getUltimoHistorico").with(ANYTHING).will(returnValue(ultimoHistorico));
    	beneficioManager.expects(once()).method("getBeneficiosByHistoricoColaborador").with(ANYTHING).will(returnValue(beneficios2));
    	colaboradorManager.expects(once()).method("findColaboradorById").with(eq(c1.getId())).will(returnValue(c1));
    	beneficioManager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(beneficios1));
		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Colaborador c1 = new Colaborador();
    	c1.setId(1L);
    	action.setColaborador(c1);

    	Beneficio b1 = new Beneficio();
    	b1.setId(1L);

    	Beneficio b2 = new Beneficio();
    	b2.setId(1L);

    	HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
    	historico.setId(1L);
    	historico.setColaborador(c1);
    	historico.setData(DateUtil.criarDataMesAno(01, 01, 2008));

    	Collection<Beneficio> beneficios1 = new ArrayList<Beneficio>();
    	beneficios1.add(b1);
    	beneficios1.add(b2);
    	historico.setBeneficios(beneficios1);
    	action.setHistoricoColaboradorBeneficio(historico);

    	manager.expects(once()).method("getUltimoHistorico").with(eq(c1.getId())).will(returnValue(null));
    	beneficioManager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(beneficios1));
		manager.expects(once()).method("findById").with(eq(historico.getId())).will(returnValue(historico));
		assertEquals("success", action.prepareUpdate());
	}

	public void testPrepareUpdateDataInvalida() throws Exception
	{
		Colaborador c1 = new Colaborador();
		c1.setId(1L);
		action.setColaborador(c1);

		Beneficio b1 = new Beneficio();
		b1.setId(1L);

		Beneficio b2 = new Beneficio();
		b2.setId(1L);

		HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
		historico.setId(1L);
		historico.setColaborador(c1);
		historico.setData(DateUtil.criarDataMesAno(01, 01, 2008));

		HistoricoColaboradorBeneficio ultimoHistorico = new HistoricoColaboradorBeneficio();
		ultimoHistorico.setId(2L);
		ultimoHistorico.setColaborador(c1);
		ultimoHistorico.setData(DateUtil.criarDataMesAno(01, 01, 2009));

		Collection<Beneficio> beneficios1 = new ArrayList<Beneficio>();
		beneficios1.add(b1);
		beneficios1.add(b2);
		historico.setBeneficios(beneficios1);
		action.setHistoricoColaboradorBeneficio(historico);

		manager.expects(once()).method("getUltimoHistorico").with(eq(c1.getId())).will(returnValue(ultimoHistorico));
		beneficioManager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(beneficios1));
		manager.expects(once()).method("findById").with(eq(historico.getId())).will(returnValue(historico));
		assertEquals("error", action.prepareUpdate());
		assertNotNull(action.getMsgAlert());
	}

	public void testInsert() throws Exception
	{
    	Colaborador c1 = new Colaborador();
    	c1.setId(1L);
    	action.setColaborador(c1);

    	Beneficio b1 = new Beneficio();
    	b1.setId(1L);
    	b1.setNome("b1");

    	Beneficio b2 = new Beneficio();
    	b2.setId(2L);
    	b2.setNome("b2");

    	HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
    	historico.setColaborador(c1);
    	historico.setDataMesAno("01/2001");

    	Collection<Beneficio> beneficios = new ArrayList<Beneficio>();
    	beneficios.add(b1);
    	beneficios.add(b2);

    	historico.setBeneficios(beneficios);

    	action.setHistoricoColaboradorBeneficio(historico);

    	String[] beneficiosCheck = new String[2];
    	beneficiosCheck[0] = "1";
    	beneficiosCheck[1] = "2";
    	action.setBeneficiosCheck(beneficiosCheck);

    	manager.expects(once()).method("getUltimoHistorico").with(ANYTHING).will(returnValue(null));
    	manager.expects(once()).method("getHistoricoByColaboradorData").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(null));
    	manager.expects(once()).method("saveHistorico").with(eq(historico));
		assertEquals("success", action.insert());

	}

	@SuppressWarnings("unchecked")
	public void testInsertDataExiste() throws Exception
	{
		Colaborador c1 = ColaboradorFactory.getEntity(1L);
		action.setColaborador(c1);

		Beneficio b1 = BeneficioFactory.getEntity(1L, "b1");

		Beneficio b2 = BeneficioFactory.getEntity(2L, "b2");

		HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
		historico.setColaborador(c1);
		historico.setDataMesAno("01/2001");

		Collection<Beneficio> beneficios = Arrays.asList(new Beneficio[] {b1, b2});

		historico.setBeneficios(beneficios);

		action.setHistoricoColaboradorBeneficio(historico);

		String[] beneficiosCheck = new String[2];
		beneficiosCheck[0] = "1";
		beneficiosCheck[1] = "2";
		action.setBeneficiosCheck(beneficiosCheck);

		HistoricoColaboradorBeneficio historico2 = new HistoricoColaboradorBeneficio();
		historico2.setId(2L);
		historico2.setColaborador(c1);
		historico2.setDataMesAno("01/2001");

		manager.expects(once()).method("getHistoricoByColaboradorData").with(ANYTHING,ANYTHING).will(returnValue(historico2));
		manager.expects(once()).method("getUltimoHistorico").with(ANYTHING).will(returnValue(historico2));
    	beneficioManager.expects(once()).method("getBeneficiosByHistoricoColaborador").with(ANYTHING).will(returnValue(beneficios));
    	colaboradorManager.expects(once()).method("findColaboradorById").with(eq(c1.getId())).will(returnValue(c1));
    	beneficioManager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(beneficios));
		assertEquals("error", action.insert());
	}

	public void testInsertDataMenorUltimo() throws Exception
	{
		Colaborador c1 = new Colaborador();
		c1.setId(1L);
		action.setColaborador(c1);

		Beneficio b1 = new Beneficio();
		b1.setId(1L);
		b1.setNome("b1");

		Collection<Beneficio> beneficios = new ArrayList<Beneficio>();
		beneficios.add(b1);

		HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
		historico.setColaborador(c1);
		historico.setDataMesAno("01/2001");
		historico.setBeneficios(beneficios);

		action.setHistoricoColaboradorBeneficio(historico);

		String[] beneficiosCheck = new String[2];
		beneficiosCheck[0] = "1";
		beneficiosCheck[1] = "2";
		action.setBeneficiosCheck(beneficiosCheck);

		HistoricoColaboradorBeneficio ultimoHistorico = new HistoricoColaboradorBeneficio();
		ultimoHistorico.setId(1L);
		ultimoHistorico.setColaborador(c1);
		ultimoHistorico.setData(DateUtil.criarDataMesAno(01, 01, 2002));
		ultimoHistorico.setBeneficios(beneficios);

		manager.expects(once()).method("getHistoricoByColaboradorData").with(ANYTHING,ANYTHING).will(returnValue(ultimoHistorico));
		manager.expects(once()).method("getUltimoHistorico").with(ANYTHING).will(returnValue(ultimoHistorico));
    	beneficioManager.expects(once()).method("getBeneficiosByHistoricoColaborador").with(ANYTHING).will(returnValue(beneficios));
    	colaboradorManager.expects(once()).method("findColaboradorById").with(eq(c1.getId())).will(returnValue(c1));
    	beneficioManager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(beneficios));
		assertEquals("error", action.insert());

	}

	public void testUpdate() throws Exception
	{
		Colaborador c1 = new Colaborador();
		c1.setId(1L);
		action.setColaborador(c1);

		Beneficio b1 = new Beneficio();
		b1.setId(1L);
		b1.setNome("b1");

		Beneficio b2 = new Beneficio();
		b2.setId(2L);
		b2.setNome("b2");

		HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
		historico.setId(1L);
		historico.setColaborador(c1);
		Collection<Beneficio> beneficios = new ArrayList<Beneficio>();
		beneficios.add(b1);
		beneficios.add(b2);
		historico.setBeneficios(beneficios);
		historico.setDataMesAno("01/2001");
		action.setHistoricoColaboradorBeneficio(historico);

		String[] beneficiosCheck = new String[2];
		beneficiosCheck[0] = "1";
		beneficiosCheck[1] = "2";
		action.setBeneficiosCheck(beneficiosCheck);

		manager.expects(once()).method("getUltimoHistorico").with(ANYTHING).will(returnValue(null));
		manager.expects(once()).method("getHistoricoByColaboradorData").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(null));
		manager.expects(once()).method("update").with(eq(historico));
		assertEquals("success", action.update());

	}

	public void testUpdateDataCadastrada() throws Exception
	{
		Colaborador c1 = new Colaborador();
		c1.setId(1L);
		action.setColaborador(c1);

		Beneficio b1 = new Beneficio();
		b1.setId(1L);
		b1.setNome("b1");

		Beneficio b2 = new Beneficio();
		b2.setId(2L);
		b2.setNome("b2");

		HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
		historico.setId(1L);
		historico.setColaborador(c1);

		Collection<Beneficio> beneficios = new ArrayList<Beneficio>();
		beneficios.add(b1);
		beneficios.add(b2);
		historico.setBeneficios(beneficios);
		historico.setDataMesAno("01/2001");
		action.setHistoricoColaboradorBeneficio(historico);

		HistoricoColaboradorBeneficio historico2 = new HistoricoColaboradorBeneficio();
		historico2.setId(2L);
		historico2.setColaborador(c1);
		historico2.setDataMesAno("01/2001");
		historico2.setData(DateUtil.criarDataMesAno(01, 01, 2001));

		String[] beneficiosCheck = new String[2];
		beneficiosCheck[0] = "1";
		beneficiosCheck[1] = "2";
		action.setBeneficiosCheck(beneficiosCheck);

		manager.expects(once()).method("getUltimoHistorico").with(ANYTHING).will(returnValue(null));
		manager.expects(once()).method("getHistoricoByColaboradorData").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(historico2));
		manager.expects(once()).method("findById").with(eq(historico.getId())).will(returnValue(historico));
		beneficioManager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(beneficios));
		assertEquals("error", action.update());
	}

	public void testUpdateDataMenor() throws Exception
	{
		Colaborador c1 = new Colaborador();
		c1.setId(1L);
		action.setColaborador(c1);

		Beneficio b1 = new Beneficio();
		b1.setId(1L);
		b1.setNome("b1");

		Beneficio b2 = new Beneficio();
		b2.setId(2L);
		b2.setNome("b2");

		HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
		historico.setId(1L);
		historico.setColaborador(c1);

		Collection<Beneficio> beneficios = new ArrayList<Beneficio>();
		beneficios.add(b1);
		beneficios.add(b2);
		historico.setBeneficios(beneficios);
		historico.setDataMesAno("01/2001");
		action.setHistoricoColaboradorBeneficio(historico);

		HistoricoColaboradorBeneficio historico2 = new HistoricoColaboradorBeneficio();
		historico2.setId(2L);
		historico2.setColaborador(c1);
		historico2.setDataMesAno("01/2001");
		historico2.setData(DateUtil.criarDataMesAno(01, 01, 2002));

		String[] beneficiosCheck = new String[2];
		beneficiosCheck[0] = "1";
		beneficiosCheck[1] = "2";
		action.setBeneficiosCheck(beneficiosCheck);

		manager.expects(once()).method("getUltimoHistorico").with(ANYTHING).will(returnValue(historico2));
		manager.expects(once()).method("getHistoricoByColaboradorData").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(historico2));
		manager.expects(once()).method("findById").with(eq(historico.getId())).will(returnValue(historico));
		beneficioManager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(beneficios));
		assertEquals("error", action.update());
	}

	public void testGetSet()
	{
		action.getHistoricoColaboradorBeneficio();
		action.getColaborador();
		action.getBeneficiosCheck();
		action.getBeneficiosCheckList();
		action.setBeneficiosCheckList(null);
		action.setMsgAlert("msg");
	}
}

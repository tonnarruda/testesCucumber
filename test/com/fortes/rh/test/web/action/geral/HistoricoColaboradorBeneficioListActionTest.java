package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.HistoricoColaboradorBeneficioManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.geral.HistoricoColaboradorBeneficioListAction;

public class HistoricoColaboradorBeneficioListActionTest extends MockObjectTestCase
{
	private HistoricoColaboradorBeneficioListAction action;
	private Mock manager;
	private Mock colaboradorManager;

	protected void setUp()
	{
		action = new HistoricoColaboradorBeneficioListAction();
		manager = new Mock(HistoricoColaboradorBeneficioManager.class);
		colaboradorManager = new Mock(ColaboradorManager.class);

		action.setHistoricoColaboradorBeneficioManager((HistoricoColaboradorBeneficioManager) manager.proxy());
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

    public void testList() throws Exception
    {
    	Colaborador c1 = new Colaborador();
    	c1.setId(1L);
    	action.setColaborador(c1);

    	Colaborador c2 = new Colaborador();
    	c2.setId(2L);

    	Beneficio b1 = new Beneficio();
    	b1.setId(1L);

    	Beneficio b2 = new Beneficio();
    	b2.setId(1L);

    	HistoricoColaboradorBeneficio historico1 = new HistoricoColaboradorBeneficio();
    	historico1.setId(1L);
    	historico1.setColaborador(c1);
    	Collection<Beneficio> beneficios1 = new ArrayList<Beneficio>();
    	beneficios1.add(b1);
    	beneficios1.add(b2);
    	historico1.setBeneficios(beneficios1);

    	HistoricoColaboradorBeneficio historico2 = new HistoricoColaboradorBeneficio();
    	historico2.setId(2L);
    	historico2.setColaborador(c1);
    	Collection<Beneficio> beneficios2 = new ArrayList<Beneficio>();
    	beneficios2.add(b1);
    	historico1.setBeneficios(beneficios2);

    	HistoricoColaboradorBeneficio historico3 = new HistoricoColaboradorBeneficio();
    	historico3.setId(3L);
    	historico3.setColaborador(c2);
    	Collection<Beneficio> beneficios3 = new ArrayList<Beneficio>();
    	beneficios3.add(b1);
    	historico1.setBeneficios(beneficios3);

    	Collection<HistoricoColaboradorBeneficio> historicos = new ArrayList<HistoricoColaboradorBeneficio>();
    	historicos.add(historico1);
    	historicos.add(historico2);
    	historicos.add(historico3);
    	action.setMsgAlert("msg");

    	colaboradorManager.expects(once()).method("findColaboradorById").with(eq(c1.getId())).will(returnValue(c1));
    	manager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(historicos));

    	assertEquals(action.list(), "success");
    	assertEquals(action.getHistoricoColaboradorBeneficios(), historicos);
    }

	public void testDelete() throws Exception
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);
		action.setColaborador(colaborador);

		HistoricoColaboradorBeneficio historicoBeneficioColaborador = new HistoricoColaboradorBeneficio();
		historicoBeneficioColaborador.setId(1L);
		historicoBeneficioColaborador.setColaborador(colaborador);
		action.setHistoricoColaboradorBeneficio(historicoBeneficioColaborador);

		manager.expects(once()).method("getUltimoHistorico").with(ANYTHING).will(returnValue(null));
		manager.expects(once()).method("deleteHistorico").with(ANYTHING);
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(colaborador));
		manager.expects(once()).method("find").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(null));

		assertEquals("success", action.delete());
	}

	public void testDeleteNaoUltimo() throws Exception
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);
		action.setColaborador(colaborador);

		HistoricoColaboradorBeneficio historicoBeneficioColaborador = new HistoricoColaboradorBeneficio();
		historicoBeneficioColaborador.setId(1L);
		historicoBeneficioColaborador.setColaborador(colaborador);
		historicoBeneficioColaborador.setDataMesAno("02/2008");
		action.setHistoricoColaboradorBeneficio(historicoBeneficioColaborador);

		HistoricoColaboradorBeneficio historicoBeneficioColaborador2 = new HistoricoColaboradorBeneficio();
		historicoBeneficioColaborador2.setId(2L);
		historicoBeneficioColaborador2.setData(DateUtil.criarDataMesAno(01, 03, 2008));
		historicoBeneficioColaborador2.setColaborador(colaborador);

		manager.expects(once()).method("getUltimoHistorico").with(eq(colaborador.getId())).will(returnValue(historicoBeneficioColaborador2));
		colaboradorManager.expects(once()).method("findColaboradorById").with(ANYTHING).will(returnValue(colaborador));
		manager.expects(once()).method("find").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(null));

		assertEquals("success", action.delete());

		assertNotNull(action.getMsgAlert());
	}

	public void testGetSet()
	{
		action.getHistoricoColaboradorBeneficio();
		action.getColaborador();
		action.setMsgAlert("msg");
	}
}

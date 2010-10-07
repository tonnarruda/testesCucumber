package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.TipoComissaoReuniao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoMembroFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoReuniaoFactory;
import com.fortes.rh.web.action.sesmt.ComissaoReuniaoEditAction;

public class ComissaoReuniaoEditActionTest extends MockObjectTestCase
{
	private ComissaoReuniaoEditAction action;
	private Mock manager;
	private Mock comissaoMembroManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(ComissaoReuniaoManager.class);
        action = new ComissaoReuniaoEditAction();
        action.setComissaoReuniaoManager((ComissaoReuniaoManager) manager.proxy());

        comissaoMembroManager = new Mock(ComissaoMembroManager.class);
        action.setComissaoMembroManager((ComissaoMembroManager)comissaoMembroManager.proxy());
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals("success", action.execute());
    }

    public void testList() throws Exception
    {
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	manager.expects(once()).method("findByComissao").with(ANYTHING).will(returnValue(new ArrayList<ComissaoReuniao>()));
    	comissaoMembroManager.expects(once()).method("findColaboradorByComissao").with(ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
    	assertEquals("success", action.list());
    	assertNotNull(action.getComissaoReuniaos());
    }

    public void testDelete() throws Exception
    {
    	action.setComissaoReuniao(ComissaoReuniaoFactory.getEntity(1L));

    	manager.expects(once()).method("remove").with(ANYTHING).isVoid();
    	assertEquals("success", action.delete());
    }

    public void testImprimirCalendario() throws Exception
    {
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	Collection<ComissaoReuniao> colecao = new ArrayList<ComissaoReuniao>();
    	colecao.add(ComissaoReuniaoFactory.getEntity(1L));

    	manager.expects(once()).method("findImprimirCalendario").with(ANYTHING).will(returnValue(colecao));

    	String ret = "";
   		ret = action.imprimirCalendario();

   		assertEquals("success",ret);
    }

    public void testImprimirCalendarioException() throws Exception
    {
    	action.setComissao(ComissaoFactory.getEntity(1L));
    	manager.expects(once()).method("findImprimirCalendario").with(ANYTHING).will(throwException(new ColecaoVaziaException("")));
    	manager.expects(once()).method("findByComissao").with(ANYTHING).will(returnValue(new ArrayList<ComissaoReuniao>()));
    	comissaoMembroManager.expects(once()).method("findColaboradorByComissao").with(ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
    	String ret = "";
   		ret = action.imprimirCalendario();

    	assertEquals("input",ret);
    }

    public void testImprimirAta() throws Exception
    {
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	Collection<ComissaoMembro> colecao = new ArrayList<ComissaoMembro>();
    	ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity(1L);
    	colecao.add(comissaoMembro);

    	ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity(1L);
    	comissaoReuniao.setDados(1L, "Descrição", TipoComissaoReuniao.ORDINARIA, new Date(), "14:00", "Sala X", "");
    	action.setComissaoReuniao(comissaoReuniao);

    	manager.expects(once()).method("findImprimirAta").with(ANYTHING, ANYTHING).will(returnValue(colecao));

    	String ret = "";
    	ret = action.imprimirAta();

    	assertEquals("success", ret);
    }

    public void testPrepare() throws Exception
    {
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	action.setComissaoReuniao(comissaoReuniao);
    	manager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(comissaoReuniao));

		String ret = "";
   		ret = action.prepare();

    	assertEquals("success", ret);
    }

    public void testInsert() throws Exception
    {
    	String[] colaboradorChecks = {"1"};
    	String[] colaboradorIds = {"1"};
    	String[] justificativas = {""};

    	action.setColaboradorChecks(colaboradorChecks);
    	action.setColaboradorIds(colaboradorIds);
    	action.setJustificativas(justificativas);

    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity(1L);
    	action.setComissaoReuniao(comissaoReuniao);
    	manager.expects(once()).method("saveOrUpdate").with(eq(comissaoReuniao),eq(colaboradorChecks),eq(colaboradorIds),eq(justificativas)).will(returnValue(comissaoReuniao));

    	String ret = "";
    	ret = action.insert();

    	assertEquals("success", ret);
    }

    public void testUpdate() throws Exception
    {
    	String[] colaboradorChecks = {"1"};
    	String[] colaboradorIds = {"1"};
    	String[] justificativas = {""};

    	action.setColaboradorChecks(colaboradorChecks);
    	action.setColaboradorIds(colaboradorIds);
    	action.setJustificativas(justificativas);

    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity(1L);
    	action.setComissaoReuniao(comissaoReuniao);
    	manager.expects(once()).method("saveOrUpdate").with(eq(comissaoReuniao),eq(colaboradorChecks),eq(colaboradorIds),eq(justificativas)).will(returnValue(comissaoReuniao));

    	String ret = "";
    	ret = action.update();

    	assertEquals("success", ret);
    }

    public void testGetSet() throws Exception
    {
    	assertNotNull(action.getComissaoReuniao());

    	ComissaoReuniao eleicaol = new ComissaoReuniao();
    	action.setComissaoReuniao(eleicaol);
    	assertTrue(action.getComissaoReuniao() instanceof ComissaoReuniao);

    	action.getComissaos();
    	action.getComissao();
    	action.getParametros();
    	action.getPresencas();
    	action.getColaboradors();
    	action.getColaboradorChecks();
    	action.getColaboradorIds();
    	action.getJustificativas();
    	action.getComissaoReuniaoPresencaMatrizes();
    	action.getComissaoMembros();
    }
}
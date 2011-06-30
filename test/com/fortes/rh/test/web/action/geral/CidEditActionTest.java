package com.fortes.rh.test.web.action.geral;

import java.util.Collections;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.CidManager;
import com.fortes.rh.model.geral.Cid;
import com.fortes.rh.web.action.geral.CidEditAction;
import com.opensymphony.xwork.Action;

public class CidEditActionTest extends MockObjectTestCase
{
	
	CidEditAction action;
	
	private Mock cidManagerMock;
	
	public void setUp() throws Exception {
		action = new CidEditAction();
		action.setCidManager(mockaManager());
	}
	
	private CidManager mockaManager() {
		cidManagerMock = new Mock(CidManager.class);
		return  (CidManager) cidManagerMock.proxy();
	}
	
	public void testExecute() throws Exception {
		String outcome = action.execute();
		assertQueUsuarioFoiRedirecionadoComSucesso(outcome);
	}

	public void testFind() throws Exception {
		
		dadoQueUsuarioEntrouComCodigoEDescricao();
		dadoQueExistemRegistrosDeCBOsCadastradosNoBanco();
		
		String outcome = action.find();
		
		assertQueUsuarioFoiRedirecionadoComSucesso(outcome);
		assertQueJsonFoiGerado();
	}

	private void assertQueUsuarioFoiRedirecionadoComSucesso(String outcome) {
		assertEquals("outcome", Action.SUCCESS, outcome);
	}

	private void dadoQueExistemRegistrosDeCBOsCadastradosNoBanco() {
		cidManagerMock.expects(once()).method("buscaCids")
			.with(eq("COD"), eq("DESCRICAO"))
			.will(returnValue(Collections.singleton(new Cid("COD", "DESCRICAO"))));
	}

	private void assertQueJsonFoiGerado() {
		String json = action.getJson();
		assertNotNull("json", json);
		assertEquals("json", "[{\"codigo\":\"COD\",\"descricao\":\"DESCRICAO\"}]", json);
	}

	private void dadoQueUsuarioEntrouComCodigoEDescricao() {
		action.setCodigo("COD");
		action.setDescricao("DESCRICAO");
	}

}
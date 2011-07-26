package com.fortes.rh.test.web.action.geral;

import java.util.Collections;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.model.geral.CodigoCBO;
import com.fortes.rh.web.action.geral.CodigoCBOEditAction;
import com.opensymphony.xwork.Action;

public class CodigoCBOEditActionTest extends MockObjectTestCase
{
	
	CodigoCBOEditAction action;
	
	private Mock codigoCBOManagerMock;
	
	public void setUp() throws Exception {
		action = new CodigoCBOEditAction();
		action.setCodigoCBOManager(mockaManager());
	}
	
	private CodigoCBOManager mockaManager() {
		codigoCBOManagerMock = new Mock(CodigoCBOManager.class);
		return  (CodigoCBOManager) codigoCBOManagerMock.proxy();
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
		codigoCBOManagerMock.expects(once()).method("buscaCodigosCBO")
			.with(eq("DESCRICAO"))
			.will(returnValue(Collections.singleton(new CodigoCBO("COD", "DESCRICAO"))));
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
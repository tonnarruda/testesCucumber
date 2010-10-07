package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraSolicitacaoReajusteTestAlert extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraSolicitacaoReajusteTestAlert()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraSolicitacaoReajuste() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/fortesrh/index.action");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow37");
		selenium.click("menuItemText58");
		selenium.waitForPageToLoad("30000");
		assertEquals("Empresas", selenium.getTitle());
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Empresa", selenium.getTitle());
		selenium.click("integra");
		selenium.click("//button[@onclick=\"if(document.getElementById('integra').checked) return validaFormulario('form', new Array('nome', 'razao', 'cnpj','acUsuario','acUrlSoap','acUrlWdsl', 'remetente', 'respSetorPessoal', 'respRH'), null) ; else return validaFormulario('form', new Array('nome', 'razao', 'cnpj', 'remetente', 'respSetorPessoal', 'respRH'), null)\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Empresas", selenium.getTitle());
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText49");
		selenium.waitForPageToLoad("30000");
		assertEquals("Solicitação de Realinhamento de Cargos & Salários", selenium.getTitle());
		selenium.select("tabelaReajuste", "label=Tabela 2009");
		selenium.select("areaOrganizacional", "label=Desenvolvimento > Desenvolvimento Java");
		selenium.waitForCondition("selenium.isTextPresent('ZX teste ac')", "5000");
		selenium.select("colaborador", "label=ZX teste ac");
		selenium.waitForCondition("selenium.getValue('colaborador') == -1","5000");
		assertEquals("Já existe uma solicitação de realinhamento para este colaborador.", selenium.getAlert());
		selenium.select("colaborador", "label=Igo Coelho");
		selenium.waitForCondition("selenium.getValue('colaborador') == -1","5000");
		assertEquals("Antes de fazer uma solicitação de realinhamento, o cadastro deste colaborador precisa ser concluído no AC Pessoal.", selenium.getAlert());
		selenium.select("tabelaReajuste", "label=Selecione...");
		selenium.select("colaborador", "label=ZX teste ac");
		selenium.waitForCondition("selenium.getValue('tabelaReajuste') == -1","5000");
		assertEquals("Selecione um Planejamento de Realinhamento.", selenium.getAlert());
		selenium.click("DHTMLSuite_menuBar_arrow37");
		selenium.click("DHTMLSuite_menuItem58");
		selenium.waitForPageToLoad("30000");
		assertEquals("Empresas", selenium.getTitle());
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Empresa", selenium.getTitle());
		selenium.click("integra");
		selenium.click("//button[@onclick=\"if(document.getElementById('integra').checked) return validaFormulario('form', new Array('nome', 'razao', 'cnpj','acUsuario','acUrlSoap','acUrlWdsl', 'remetente', 'respSetorPessoal', 'respRH'), null) ; else return validaFormulario('form', new Array('nome', 'razao', 'cnpj', 'remetente', 'respSetorPessoal', 'respRH'), null)\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Empresas", selenium.getTitle());
	}
}
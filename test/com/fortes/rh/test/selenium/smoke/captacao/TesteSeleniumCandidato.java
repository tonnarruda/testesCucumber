package com.fortes.rh.test.selenium.smoke.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCandidato extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCandidato()
	{
		selenium = getSeleniumInstance();
	}

	public void testCandidato() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl()+"/index.action");
		selenium.click("link=Candidatos");
		selenium.waitForPageToLoad("30000");
		assertEquals("Candidatos", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Candidatos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nome"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Candidato", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nome"));
		selenium.click("avancar");
		selenium.waitForCondition("selenium.isTextPresent('Formação Escolar')", "30000");
		selenium.waitForCondition("selenium.isTextPresent('Idiomas')", "30000");
		selenium.click("avancar");
		selenium.waitForCondition("selenium.isTextPresent('Cargo')", "30000");
		selenium.waitForCondition("selenium.isTextPresent('Conhecimentos')", "30000");
		selenium.click("avancar");
		selenium.waitForCondition("selenium.isTextPresent('Experiência Profissional')", "30000");
		selenium.waitForCondition("selenium.isTextPresent('Observações')", "30000");
		selenium.click("avancar");
		assertTrue(selenium.isTextPresent("Identidade"));
		selenium.click("gravar");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"if (confirm('Tem certeza que deseja cancelar?')) window.location='list.action?nomeBusca=&cpfBusca=&page=1&visualizar=�'\"]");
		assertTrue(selenium.getConfirmation().matches("^Tem certeza que deseja cancelar[\\s\\S]$"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Candidatos", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsertCurriculoPlus.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Escanear Currículo", selenium.getTitle());
		selenium.click("//button[@onclick='return validaForm();']");
		assertEquals("Deve ser selecionado pelo menos um item do campo 'Cargo/Função Pretendida'", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Candidatos", selenium.getTitle());
	}
}
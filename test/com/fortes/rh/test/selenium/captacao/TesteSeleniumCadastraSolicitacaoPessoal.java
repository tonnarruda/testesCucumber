package com.fortes.rh.test.selenium.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraSolicitacaoPessoal extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraSolicitacaoPessoal()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraSolicitacaoPessoal() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText21");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Solicitaçőes de Pessoal"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Inserir Solicitação de Pessoal"));
		selenium.select("estabelecimento", "label=Estabelecimento Padrão");
		selenium.select("area", "label=Desenvolvimento > Desenvolvimento Java");
		selenium.select("faixa", "label=Desenvolvedor I");
		selenium.select("form_solicitacao_escolaridade", "label=Superior incompleto");
		selenium.select("motivoSolicitacaoId", "label=Aumento de quadro");
		selenium.click("estado");
		selenium.select("estado", "label=Ceará");
		selenium.runScript("populaBairros()");
		selenium.click("cidade");
		selenium.select("cidade", "label=Fortaleza");
		selenium.click("//span[@onclick=\"marcarDesmarcarListCheckBox(document.forms[0], 'bairrosCheck',true); \"]");
		selenium.click("//span[@onclick=\"marcarDesmarcarListCheckBox(document.forms[0], 'beneficiosCheck',true); \"]");
		selenium.click("form_solicitacao_liberada");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('estabelecimento','area','faixa','quantidade','remuneracao','motivoSolicitacaoId','cidade'), null);\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Solicitaçőes de Pessoal"));
	}

	public void testIncluirCandidatoNaSolicitacao() throws Exception
	{
		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText21");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Solicitaçőes de Pessoal"));
		selenium.click("//img[@title='Candidatos da Seleção']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Candidatos da Seleção"));
		selenium.open(getAppUrl() + "/captacao/candidatoSolicitacao/list.action?solicitacao.id=1");
		selenium.click("//button[@onclick=\"window.location='../candidato/prepareBusca.action?solicitacao.id=1'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.select("formBusca_escolaridade", "label=Superior completo");
		selenium.click("//span[@onclick=\"marcarDesmarcarListCheckBox(document.forms[0], 'cargosCheck',false); \"]");
		selenium.click("//button[@onclick=\"document.getElementById('pagina').value = 1;return validaFormulario('form', null, new Array('dataCadIni','dataCadFim'));\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Inserir Candidatos na Solicitação"));
		selenium.click("candidatosId");
		selenium.click("//button[@onclick='prepareEnviarForm();']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Candidatos da Seleção"));
	}

	public void testContratarCandidato() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/captacao/candidatoSolicitacao/list.action?solicitacao.id=1");
		selenium.click("//div[@id='logoDiv']/a/img");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText21");
		selenium.waitForPageToLoad("60000");
		selenium.open(getAppUrl() + "/captacao/candidatoSolicitacao/list.action?solicitacao.id=1");
		selenium.waitForPageToLoad("60000");
		selenium.click("//img[@title='Contratar o Candidato']");
		assertTrue(selenium.getConfirmation().matches("^Deseja realmente contratar o candidato Antonio Selenium[\\s\\S]$"));
		selenium.waitForPageToLoad("60000");
		selenium.type("nascimento", "11/10/1975");
		selenium.click("avancar");
		selenium.type("nomeComercial", "Antonio Selenium");
		selenium.type("dt_admissao", "01/10/2008");
		selenium.select("estabelecimento", "label=Estabelecimento Padrão");
		selenium.select("areaOrganizacional", "label=Desenvolvimento > Desenvolvimento Java");
		selenium.select("faixa", "label=Desenvolvedor I");
		selenium.click("//select[@id='faixa']/option[7]");
		selenium.click("avancar");
		selenium.click("avancar");
		selenium.click("avancar");
		selenium.click("gravar");
		selenium.waitForPageToLoad("60000");
		assertTrue(selenium.isTextPresent("Colaboradores"));
	}


}
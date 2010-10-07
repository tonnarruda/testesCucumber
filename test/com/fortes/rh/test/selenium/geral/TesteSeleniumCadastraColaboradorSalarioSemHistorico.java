package com.fortes.rh.test.selenium.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraColaboradorSalarioSemHistorico extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraColaboradorSalarioSemHistorico()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraColaboradorComSalarioSemHistorico() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/cargosalario/indice/list.action");
		selenium.click("menuItemText8");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Colaborador", selenium.getTitle());
		selenium.type("nomeColab", "Teste salario sem historico");
		selenium.type("nascimento", "28/06/1975");
		selenium.type("cpf", "21958222526");
		selenium.type("ende", "teste");
		selenium.type("num", "58");
		selenium.select("uf", "label=CE");
		selenium.waitForCondition("selenium.isTextPresent('Fortaleza')", "30000");
		selenium.select("cidade", "label=Fortaleza");
		selenium.type("ddd", "85");
		selenium.type("fone", "89989898");
		selenium.click("link=Dados Funcionais");
		selenium.type("dt_admissao", "09/02/2009");
		selenium.select("estabelecimento", "label=Estabelecimento Padrão");
		selenium.select("areaOrganizacional", "label=Desenvolvimento > Desenvolvimento Java");
		selenium.click("//select[@id='areaOrganizacional']/option[4]");
		selenium.select("faixa", "label=Desenvolvedor I");
		selenium.click("//select[@id='faixa']/option[7]");
		selenium.select("tipoSalario", "label=Índice");
		selenium.click("//select[@id='tipoSalario']/option[3]");
		selenium.waitForCondition("selenium.isTextPresent('Salario selenium')", "30000");
		selenium.select("indice", "label=Salario selenium");
		selenium.click("//select[@id='indice']/option[3]");
		Thread.sleep(5000);
		assertEquals("Atenção: Não existe Histórico nessa data para o tipo de salário selecionado.", selenium.getAlert());

	}
}
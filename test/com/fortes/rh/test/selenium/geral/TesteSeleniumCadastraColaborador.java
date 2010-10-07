package com.fortes.rh.test.selenium.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraColaborador extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraColaborador()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraColaborador() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/geral/colaborador/prepareInsert.action");
		assertEquals("Inserir Colaborador", selenium.getTitle());
		selenium.click("DHTMLSuite_menuBar_arrow373");
		selenium.click("menuItemText8");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Colaborador", selenium.getTitle());
		selenium.click("link=Documentos");
		selenium.click("gravar");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("link=Dados Pessoais");
		selenium.type("nomeColab", "Maria Selenium");
		selenium.type("nascimento", "01/01/2000");
		selenium.select("insert_colaborador_pessoal_sexo", "label=Feminino");
		selenium.type("cpf", "17866251487");
		selenium.type("ende", "Logradouro");
		selenium.type("num", "11");
		selenium.select("uf", "label=CE");
		selenium.waitForCondition("selenium.isTextPresent('Camocim')", "10000");
		selenium.select("cidade", "label=Camocim");
		selenium.type("bairroNome", "Alagado");
		selenium.type("ddd", "88");
		selenium.type("fone", "8552236");
		selenium.type("insert_colaborador_contato_foneCelular", "8855858");
		selenium.select("insert_colaborador_pessoal_escolaridade", "label=Superior incompleto");
		selenium.select("insert_colaborador_pessoal_estadoCivil", "label=União Estável");
		selenium.select("insert_colaborador_pessoal_deficiencia", "label=Visual");
		selenium.click("avancar");
		selenium.type("nomeComercial", "Maria Selenium");
		selenium.type("dt_admissao", "01/01/2005");
		selenium.select("estabelecimento", "label=Estabelecimento Padrão");
		selenium.select("areaOrganizacional", "label=Desenvolvimento > Desenvolvimento Java");
		selenium.select("faixa", "label=Desenvolvedor I");
		//teste do js no campo Salario Proposto
		selenium.select("tipoSalario", "label=Cargo");
		selenium.select("tipoSalario", "label=Índice");
		assertTrue(selenium.isTextPresent("Índice"));
		assertTrue(selenium.isTextPresent("exact:Qtd."));
		assertTrue(selenium.isTextPresent("Salario Minimo"));
		selenium.select("tipoSalario", "label=Valor");
		assertTrue(selenium.isTextPresent("Salário"));
		selenium.select("tipoSalario", "label=Cargo");
		selenium.click("link=Formação Escolar");
		selenium.type("desCursos", "curso");
		selenium.click("link=Experiências");
		selenium.type("obs", "informaçőes testes");
		selenium.click("link=Documentos");
		selenium.type("rg", "34242343");
		selenium.type("insert_colaborador_habilitacao_numeroHab", "43434343");
		selenium.type("titEleitNumero", "89797899");
		selenium.type("certMilNumero", "089089008");
		selenium.type("ctpsNumero", "4234324");
		selenium.type("pis", "4324244");
		selenium.click("gravar");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Maria Selenium"));
	}

	public void testEdicaoColaborador() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("DHTMLSuite_menuBar_arrow373");
		selenium.click("menuItemText8");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("labelLink");
		selenium.type("nomeBusca", "Selenium");
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Colaborador", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='list.action?nomeBusca=Selenium&cpfBusca='\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Colaborador", selenium.getTitle());
		selenium.type("nomeComercial", "Maria Selenium II");
		selenium.click("link=Dados Funcionais");
		assertTrue(selenium.isTextPresent("Colocação"));
		selenium.click("link=Documentos");
		assertTrue(selenium.isTextPresent("Identidade"));
		selenium.click("link=Formação Escolar");
		selenium.click("gravar");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Maria Selenium II"));
	}

	public void testTela() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.click("DHTMLSuite_menuBar_arrow373");
		selenium.click("menuItemText8");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("labelLink");
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Igo Coelho"));
		selenium.highlight("//table[@id='colaborador']/tbody/tr[2]/td[1]/a[1]/img");
		selenium.click("//table[@id='colaborador']/tbody/tr[2]/td[1]/a[1]/img");
		selenium.waitForPageToLoad("30000");
		assertEquals("Desligar Colaborador", selenium.getTitle());
		selenium.click("//input[@value=' ' and @type='button' and @onclick=\"window.location='list.action?nomeBusca=&cpfBusca='\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("//img[@title='Entrevista de desligamento']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Entrevista de Desligamento", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='../../geral/colaborador/list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("//table[@id='colaborador']/tbody/tr[2]/td[1]/a[2]/img");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Colaborador", selenium.getTitle());
		selenium.click("link=Dados Funcionais");
		assertTrue(selenium.isTextPresent("Colocação"));
		selenium.click("link=Formação Escolar");
		assertTrue(selenium.isTextPresent("Formação Escolar"));
		selenium.click("avancar");
		assertTrue(selenium.isTextPresent("Experiência Profissional"));
		selenium.click("avancar");
		assertTrue(selenium.isTextPresent("Identidade"));
		assertTrue(selenium.isTextPresent("Certificado Militar"));
		assertTrue(selenium.isTextPresent("PIS - Programa de Integração Social"));
		selenium.click("voltar");
		assertTrue(selenium.isTextPresent("Informaçőes Adicionais"));
		selenium.click("voltar");
		assertTrue(selenium.isTextPresent("Idioma"));
		selenium.click("link=Dados Funcionais");
		assertTrue(selenium.isTextPresent("Estabelecimento"));
		selenium.click("voltar");
		assertTrue(selenium.isTextPresent("Escolaridade"));
		selenium.click("//button[@onclick=\"window.location='list.action?nomeBusca=&cpfBusca='\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("//table[@id='colaborador']/tbody/tr[4]/td[1]/a[3]/img");
		selenium.waitForPageToLoad("30000");
		assertEquals("Progressão do Colaborador", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='../../geral/colaborador/list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("//table[@id='colaborador']/tbody/tr[4]/td[1]/a[4]/img");
		selenium.waitForPageToLoad("30000");
		assertEquals("Performance Funcional", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Identificação do Colaborador"));
		assertTrue(selenium.isTextPresent("Histórico de Cursos de Formação Profissional"));
		assertTrue(selenium.isTextPresent("Participação de Eventos Educativos na Empresa"));
		assertTrue(selenium.isTextPresent("Ocorrências"));
		assertTrue(selenium.isTextPresent("Documentos do Colaborador"));
		selenium.click("//button[@onclick=\"window.location='../../geral/colaborador/list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
	}
}
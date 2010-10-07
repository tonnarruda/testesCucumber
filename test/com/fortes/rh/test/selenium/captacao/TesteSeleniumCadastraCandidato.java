package com.fortes.rh.test.selenium.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraCandidato extends TestCase {
	private Selenium selenium;

	public TesteSeleniumCadastraCandidato()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraCandidato() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText2");
		selenium.waitForPageToLoad("30000");
		assertEquals("Candidatos", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Candidato", selenium.getTitle());
		selenium.type("nomeCand", "Selenium teste");
		selenium.type("cpf", "00000000000");
		selenium.select("insert_candidato_pessoal_escolaridade", "label=Superior incompleto");
		selenium.type("ende", "rua 4");
		selenium.type("num", "11");
		selenium.type("insert_candidato_endereco_complemento", "complemento");
		selenium.select("uf", "label=CE");
		selenium.waitForCondition("selenium.isTextPresent('Fortaleza')", "30000");
		selenium.select("cidade", "label=Fortaleza");
		selenium.type("bairroNome", "palmacia");
		selenium.type("email", "selenium@seleniumteste.com");
		selenium.type("ddd", "85");
		selenium.type("fone", "88226655");
		assertFalse(selenium.isEditable("quantidadeId"));
		selenium.select("pagaPensaoId", "label=Sim");
		assertTrue(selenium.isEditable("quantidadeId"));
		selenium.type("insert_candidato_contato_foneCelular", "88558855");
		selenium.type("candidato.pessoal.parentesAmigos", "Vizinho");
		selenium.select("insert_candidato_pessoal_estadoCivil", "label=Separado Judicialmente");
		selenium.type("qtdFilhos", "2");
		selenium.type("insert_candidato_pessoal_conjuge", "Maria");
		selenium.type("insert_candidato_pessoal_profissaoConjuge", "Programadora");
		selenium.type("insert_candidato_pessoal_pai", "Jose");
		selenium.type("insert_candidato_pessoal_profissaoPai", "motorista");
		selenium.type("insert_candidato_pessoal_mae", "Maria");
		selenium.type("insert_candidato_pessoal_profissaoMae", "Promotora da avon");
		selenium.select("pagaPensaoId", "label=Sim");
		selenium.select("pagaPensaoId", "label=Não");
		selenium.select("insert_candidato_socioEconomica_possuiVeiculo", "label=Sim");
		selenium.select("insert_candidato_pessoal_deficiencia", "label=Auditiva");
		selenium.click("avancar");
		assertEquals("Inserir Candidato", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Formação Escolar"));
		assertTrue(selenium.isTextPresent("Idioma"));
		selenium.type("desCursos", "Java");
		selenium.click("avancar");
		assertEquals("Inserir Candidato", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Cargo"));
		assertTrue(selenium.isTextPresent("Áreas de Interesse"));
		assertTrue(selenium.isTextPresent("Conhecimentos"));
		selenium.click("checkGroupcargosCheck5");
		selenium.waitForCondition("selenium.isTextPresent('MySQL')", "1000");
		selenium.click("checkGroupareasCheck1");
		selenium.click("//div[@id='listCheckBoxareasCheck']/label[1]");
		selenium.click("avancar");
		selenium.select("insert_candidato_colocacao", "label=Estágio");
		assertEquals("Inserir Candidato", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Experiência Profissional"));
		selenium.type("obs", "não tem");
		selenium.type("obsrh", "ele é bom");
		selenium.click("avancar");
		assertEquals("Inserir Candidato", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Identidade"));
		assertTrue(selenium.isTextPresent("Carteira de Habilitação"));
		assertTrue(selenium.isTextPresent("Título Eleitoral"));
		assertTrue(selenium.isTextPresent("Certificado Militar"));
		assertTrue(selenium.isTextPresent("CTPS - Carteira de Trabalho e Previdência Social"));
		selenium.type("rg", "95000000000000");
		selenium.type("insert_candidato_pessoal_rgOrgaoEmissor", "sspce");
		selenium.select("rgUf", "label=CE");
		selenium.type("insert_candidato_habilitacao_numeroHab", "54545454");
		selenium.type("insert_candidato_habilitacao_registro", "a454");
		selenium.type("insert_candidato_habilitacao_categoria", "AB");
		selenium.type("titEleitNumero", "8888888");
		selenium.type("titEleitZona", "585");
		selenium.type("titEleitSecao", "555");
		selenium.type("certMilNumero", "884596");
		selenium.type("certMilTipo", "E");
		selenium.type("certMilSerie", "as444");
		selenium.type("ctpsNumero", "54545545");
		selenium.type("ctpsSerie", "54545");
		selenium.type("ctpsDv", "4");
		selenium.select("ctpsUf", "label=CE");
		selenium.click("voltar");
		assertEquals("Inserir Candidato", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Experiência Profissional"));
		selenium.click("avancar");
		assertEquals("Inserir Candidato", selenium.getTitle());
		selenium.click("gravar");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Operação Efetuada com Sucesso!"));
		selenium.waitForCondition("selenium.isTextPresent('Selenium')", "1000");
		assertTrue(selenium.isTextPresent("Selenium"));
		assertTrue(selenium.isTextPresent("Consultor"));
		assertTrue(selenium.isTextPresent("incompleto"));
		assertTrue(selenium.isTextPresent("Vizinho"));
		assertTrue(selenium.isTextPresent("Masculino"));
		assertTrue(selenium.isTextPresent("Estágio"));
		assertTrue(selenium.isTextPresent("Candidato sem foto"));
		assertTrue(selenium.isTextPresent("DADOS PESSOAIS"));
		assertTrue(selenium.isTextPresent("Separado Judicialmente"));
		assertTrue(selenium.isTextPresent("Maria"));
		assertTrue(selenium.isTextPresent("Jose"));
		assertTrue(selenium.isTextPresent("Maria"));
		assertTrue(selenium.isTextPresent("Promotora da avon"));
		assertTrue(selenium.isTextPresent("INFORMAÇŐES SÓCIO-ECONÔMICAS"));
		assertTrue(selenium.isTextPresent("INFORMAÇŐES ADICIONAIS"));
		assertTrue(selenium.isTextPresent("Java"));
		assertTrue(selenium.isTextPresent("Possui Veículo: Sim"));
		selenium.click("//button[@onclick=\"window.location='list.action?nomeBusca=&cpfBusca=&page=1'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Candidatos", selenium.getTitle());
	}

	public void testEdicaoCandidato() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText2");
		selenium.waitForPageToLoad("30000");
		assertEquals("Candidatos", selenium.getTitle());
		selenium.click("labelLink");
		selenium.type("cpfBusca", "000.000.000-00");
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Candidatos", selenium.getTitle());
		selenium.click("//table[@id='candidato']/tbody/tr[2]/td[1]/a[3]/img");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Candidato", selenium.getTitle());
		assertEquals("Selenium teste", selenium.getValue("nomeCand"));
		selenium.type("nomeCand", "Selenium teste II");
		assertEquals("000.000.000-00", selenium.getValue("cpf"));
		assertEquals("complemento", selenium.getValue("update_candidato_endereco_complemento"));
		assertEquals("palmacia", selenium.getValue("bairroNome"));
		assertEquals("selenium@seleniumteste.com", selenium.getValue("email"));
		assertEquals("motorista", selenium.getValue("update_candidato_pessoal_profissaoPai"));
		selenium.click("avancar");
		assertEquals("Java", selenium.getValue("desCursos"));
		selenium.click("avancar");
		assertEquals("Editar Candidato", selenium.getTitle());
		selenium.click("link=Experiências");
		assertEquals("não tem", selenium.getValue("obs"));
		assertEquals("ele é bom", selenium.getValue("obsrh"));
		assertTrue(selenium.isTextPresent("Experiência Profissional"));
		selenium.click("avancar");
		assertEquals("95000000000000", selenium.getValue("rg"));
		assertEquals("54545454", selenium.getValue("update_candidato_habilitacao_numeroHab"));
		assertEquals("8888888", selenium.getValue("titEleitNumero"));
		assertEquals("884596", selenium.getValue("certMilNumero"));
		assertEquals("54545545", selenium.getValue("ctpsNumero"));
		selenium.click("gravar");
		selenium.waitForPageToLoad("30000");
		assertEquals("Candidatos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Selenium teste II"));
	}

	public void testExclusaoCandidato() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText2");
		selenium.waitForPageToLoad("30000");
		assertEquals("Candidatos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Selenium teste II"));
		selenium.click("//table[@id='candidato']/tbody/tr[3]/td[1]/a[4]/img");
		assertTrue(selenium.getConfirmation().matches("^Confirma exclusão[\\s\\S]$"));
		selenium.waitForPageToLoad("3000");
		assertEquals("Candidatos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Candidato excluído com sucesso!"));
		assertFalse(selenium.isTextPresent("Selenium teste II"));
	}
}

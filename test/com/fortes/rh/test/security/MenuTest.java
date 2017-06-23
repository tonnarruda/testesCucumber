package com.fortes.rh.test.security;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import junit.framework.TestCase;

import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.dicionario.FiltroControleVencimentoCertificacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.Menu;
import com.fortes.rh.test.factory.acesso.PapelFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;

public class MenuTest extends TestCase {

	@Test
	public void testGetMenuFormatado() {
		Collection<Papel> papels = new ArrayList<Papel>();
		Papel papel = PapelFactory.getSesmt();
		papels.add(papel);

		ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity();
		parametros.setCodClienteSuporte("000006");
		parametros.setCodEmpresaSuporte("0002");

		assertEquals(criaMenuPadrao(), Menu.getMenuFormatado(papels, "localhost", parametros, new ArrayList<Empresa>(), EmpresaFactory.getEmpresa(), null));
	}

	@Test
	public void testGetMenuFormatadoComFilho() {
		Collection<Papel> papels = new ArrayList<Papel>();
		Papel papel1 = PapelFactory.getSesmt();
		Papel papel2 = PapelFactory.getSesmt();
		Papel papel3 = PapelFactory.getSesmt();

		papel2.setId(2L);
		papel2.setNome("SESMT 1");
		papel2.setOrdem(2);
		papel2.setPapelMae(papel1);

		papel3.setId(3L);
		papel3.setNome("SESMT 2");
		papel3.setOrdem(3);
		papel3.setPapelMae(papel2);

		papels.add(papel1);
		papels.add(papel2);
		papels.add(papel3);

		ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity();
		parametros.setCodClienteSuporte("000006");
		parametros.setCodEmpresaSuporte("0002");

		assertEquals(criaMenuPadrao(), Menu.getMenuFormatado(papels, "localhost", parametros, new ArrayList<Empresa>(), EmpresaFactory.getEmpresa(), null));
	}

	@Test
	public void testGetMenuLinkInterno() {
		Collection<Papel> papels = new ArrayList<Papel>();
		Papel utilitario = PapelFactory.getUtilitario();
		ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity();
		Empresa empresa = EmpresaFactory.getEmpresa();
		String url = "/sesmt/ppp/list.action";

		Papel sesmt = PapelFactory.getSesmt();
		sesmt.setUrl(url);
		sesmt.setPapelMae(utilitario);

		papels.add(utilitario);
		papels.add(sesmt);

		parametros.setCodClienteSuporte("000006");
		parametros.setCodEmpresaSuporte("0002");

		empresa.setControlarVencimentoCertificacaoPor(FiltroControleVencimentoCertificacao.CURSO.getOpcao());
		String menuFormatado = Menu.getMenuFormatado(papels, "localhost", parametros, new ArrayList<Empresa>(), empresa, null);

		assertTrue(menuFormatado.contains(url));
	}

	@Test
	public void testGetMenuLinkExterno() {
		Collection<Papel> papels = new ArrayList<Papel>();
		Papel papel = PapelFactory.getUtilitario();
		ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity();
		Empresa empresa = EmpresaFactory.getEmpresa();
		String url = "https://portaldocliente.fortestecnologia.com.br/portal_autentica_portal.php?location=/portal_solicitacao.php";

		papels.add(papel);
		papels.add(PapelFactory.getFormularioSolicitacao());

		parametros.setCodClienteSuporte("000006");
		parametros.setCodEmpresaSuporte("0002");

		empresa.setControlarVencimentoCertificacaoPor(FiltroControleVencimentoCertificacao.CURSO.getOpcao());
		String menuFormatado = Menu.getMenuFormatado(papels, "localhost", parametros, new ArrayList<Empresa>(), empresa, null);

		assertTrue(menuFormatado.contains(url));
	}

	private String criaMenuPadrao() {
		StringBuilder menu = new StringBuilder();
		menu.append("<ul id='menuDropDown'>\n");
		menu.append("<li><a href='localhosturl.com.br'  >SESMT</a>\n");
		menu.append("<ul>\n");
		menu.append("</ul>\n");
		menu.append("</li>\n");
		menu.append("<li><a href='localhost/logout.action' accesskey='a'>S<u>a</u>ir</a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em;'><a href='localhost/geral/documentoVersao/list.action' class='versao'> Versão: 1</a></li>\n");
		menu.append("</ul>\n");
		menu.append("\n");
		return menu.toString();
	}
}

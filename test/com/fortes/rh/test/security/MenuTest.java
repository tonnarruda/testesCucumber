package com.fortes.rh.test.security;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.security.Menu;


public class MenuTest extends TestCase
{
	public void testGetMenuFormatado()
	{
		Collection<Papel> papels = new ArrayList<Papel>();
		Papel papel1 = new Papel();
		papel1.setId(1L);
		papel1.setCodigo("ROLE_SESMT");
		papel1.setMenu(true);
		papel1.setNome("SESMT");
		papel1.setOrdem(1);
		papel1.setUrl("url.com.br");
		papels.add(papel1);
		
		StringBuilder menu = new StringBuilder();
		menu.append("<ul id='menuDropDown'>\n");
		menu.append("<li><a href='localhosturl.com.br'  >SESMT</a>\n");
		menu.append("<ul>\n");
		menu.append("</ul>\n");
		menu.append("</li>\n");
		menu.append("<li><a href='localhost/logout.action' accesskey='a'>S<u>a</u>ir</a></li>\n");
		menu.append("</ul>\n");
		menu.append("\n");
						
		assertEquals(menu.toString(), Menu.getMenuFormatado(papels, "localhost"));
	}

	public void testGetMenuFormatadoComFilho()
	{
		Collection<Papel> papels = new ArrayList<Papel>();
		Papel papel1 = new Papel();
		papel1.setId(1L);
		papel1.setCodigo("ROLE_SESMT");
		papel1.setMenu(true);
		papel1.setNome("SESMT");
		papel1.setOrdem(1);
		papel1.setUrl("url.com.br");

		Papel papel2 = new Papel();
		papel2.setId(2L);
		papel2.setCodigo("ROLE_SESMT");
		papel2.setMenu(true);
		papel2.setNome("SESMT 1");
		papel2.setOrdem(1);
		papel2.setPapelMae(papel1);
		papel2.setUrl("url.com.br");

		Papel papel3 = new Papel();
		papel3.setId(3L);
		papel3.setCodigo("ROLE_SESMT");
		papel3.setMenu(true);
		papel3.setNome("SESMT 2");
		papel3.setOrdem(1);
		papel3.setPapelMae(papel2);
		papel3.setUrl("url.com.br");

		papels.add(papel1);
		papels.add(papel2);
		papels.add(papel3);
		
		StringBuilder menu = new StringBuilder();
		menu.append("<ul id='menuDropDown'>\n");
		menu.append("<li><a href='localhosturl.com.br'  >SESMT</a>\n");
		menu.append("<ul>\n");
		menu.append("<li><a href='localhosturl.com.br'>SESMT 1</a><ul>\n");
		menu.append("<li><a href='localhosturl.com.br'>SESMT 2</a></li>\n");
		menu.append("</ul>\n");
		menu.append("</li>\n");
		menu.append("</ul>\n");
		menu.append("</li>\n");
		menu.append("<li><a href='localhost/logout.action' accesskey='a'>S<u>a</u>ir</a></li>\n");
		menu.append("</ul>\n");
		menu.append("\n");
		
		assertEquals(menu.toString(), Menu.getMenuFormatado(papels, "localhost"));
	}
}

package com.fortes.rh.test.security;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.Menu;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;


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
		menu.append("<li style='float: right; line-height: 0.8em;'><a href='localhost/geral/documentoVersao/list.action' class='versao'> Versão: 1</a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='contatos.action' title='Contatos'><img src='localhost/imgs/telefone.gif' style='vertical-align: middle;'></a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='http://twitter.com/#!/fortesinfo' target='_blank' title='Twitter'><img src='localhost/imgs/twitter.png' style='vertical-align: middle;'></a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='http://blog.fortesinformatica.com.br/categoria/ente-rh/?utm_source=sistema&utm_medium=icone-barra-lateral&utm_content=ente-rh&utm_campaign=clique-blog' target='_blank' title='Blog'><img src='localhost/imgs/blog.png' style='vertical-align: middle;'></a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='videoteca.action' title='Videoteca'><img src='localhost/imgs/video.png' style='vertical-align: middle;'></a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='http://www.logmein123.com'  target='_blank' title='LogMeIn'><img src='localhost/imgs/logmeinrescue.png' style='vertical-align: middle;'></a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='http://chatonline.grupofortes.com.br/cliente/MATRIZ/000006/0002' target='_blank' title='Fortes Chat'><img src='localhost/imgs/chat_fortes.png' style='vertical-align: middle;'></a></li>\n");
		menu.append("</ul>\n");
		menu.append("\n");
		ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity();
		parametros.setCodClienteSuporte("000006");
		parametros.setCodEmpresaSuporte("0002");
		
		assertEquals(menu.toString(), Menu.getMenuFormatado(papels, "localhost", parametros, new ArrayList<Empresa>(), EmpresaFactory.getEmpresa()));
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
		menu.append("</ul>\n");
		menu.append("</li>\n");
		menu.append("<li><a href='localhost/logout.action' accesskey='a'>S<u>a</u>ir</a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em;'><a href='localhost/geral/documentoVersao/list.action' class='versao'> Versão: 1</a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='contatos.action' title='Contatos'><img src='localhost/imgs/telefone.gif' style='vertical-align: middle;'></a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='http://twitter.com/#!/fortesinfo' target='_blank' title='Twitter'><img src='localhost/imgs/twitter.png' style='vertical-align: middle;'></a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='http://blog.fortesinformatica.com.br/categoria/ente-rh/?utm_source=sistema&utm_medium=icone-barra-lateral&utm_content=ente-rh&utm_campaign=clique-blog' target='_blank' title='Blog'><img src='localhost/imgs/blog.png' style='vertical-align: middle;'></a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='videoteca.action' title='Videoteca'><img src='localhost/imgs/video.png' style='vertical-align: middle;'></a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='http://www.logmein123.com'  target='_blank' title='LogMeIn'><img src='localhost/imgs/logmeinrescue.png' style='vertical-align: middle;'></a></li>\n");
		menu.append("<li style='float: right; line-height: 0.8em'><a href='http://chatonline.grupofortes.com.br/cliente/MATRIZ/000006/0002' target='_blank' title='Fortes Chat'><img src='localhost/imgs/chat_fortes.png' style='vertical-align: middle;'></a></li>\n");
		menu.append("</ul>\n");
		menu.append("\n");
		
		ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity();
		parametros.setCodClienteSuporte("000006");
		parametros.setCodEmpresaSuporte("0002");
		
		assertEquals(menu.toString(), Menu.getMenuFormatado(papels, "localhost", parametros, new ArrayList<Empresa>(), EmpresaFactory.getEmpresa()));
	}
}

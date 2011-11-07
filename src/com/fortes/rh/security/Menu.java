package com.fortes.rh.security;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis.utils.StringUtils;

import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;

public abstract class Menu 
{
	private static Collection<Papel> roles;
	
	public static String getMenuFormatado(Collection<Papel> rolesPapel, String contexto, ParametrosDoSistema parametros, Collection<Empresa> empresasDoUsuario)
	{
		roles = new ArrayList<Papel>();
        for (Papel papel : rolesPapel)
        {
        	if (papel.isMenu())
        		roles.add(papel);
        }

		StringBuilder menu = new StringBuilder("<ul id='menuDropDown'>\n");

		for(Papel papel : roles)
		{
			if (papel.getPapelMae() == null)
			{
				String accesskey = "";
				String url = papel.getUrl().equals("#") ? "#" : contexto + papel.getUrl();
				String nome = papel.getNome();
				
				if(papel.getAccesskey() != null)
				{					
					accesskey = "accesskey='" + papel.getAccesskey() + "'";
					nome = papel.getNome().replaceFirst(papel.getAccesskey(), "<u>" +papel.getAccesskey()+ "</u>");
				}
				
				menu.append("<li><a href='" + url + "' " + accesskey + " >" + nome +  "</a>\n");
				menu.append("<ul>\n" + getFilhos(papel.getId(), contexto, empresasDoUsuario) + "</ul>\n</li>\n");
			}
		}

		menu.append("<li><a href='" + contexto + "/logout.action' accesskey='a'>S<u>a</u>ir</a></li>\n");

		menu.append("<li style='float: right; line-height: 0.8em;'><a href='" + contexto + "/geral/documentoVersao/list.action' class='versao'> Versão: "  +  parametros.getAppVersao() +"</a></li>\n");
		
		menu.append("<li style='float: right; line-height: 0.8em'>" +
				"<a href='http://twitter.com/#!/entetecnologia' target='_blank' title='Twitter Fortes RH'>" +
				"<img src='"+ contexto + "/imgs/twitter.png' style='vertical-align: middle;'></a></li>\n");
		
		menu.append("<li style='float: right; line-height: 0.8em'>" +
				"<a href='http://fortesrh.blogspot.com' target='_blank' title='Blog Fortes RH'>" +
				"<img src='"+ contexto + "/imgs/blog.png' style='vertical-align: middle;'></a></li>\n");
		
		
		if ( parametros != null && !StringUtils.isEmpty(parametros.getCodEmpresaSuporte()) && !StringUtils.isEmpty(parametros.getCodClienteSuporte()))
			menu.append("<li style='float: right; line-height: 0.8em'>" 
				+ "<a href='http://chatonline.grupofortes.com.br/cliente/MATRIZ/" + parametros.getCodClienteSuporte() + "/" + parametros.getCodEmpresaSuporte() + "' target='_blank' title='Suporte'>" 
				+ "<img src='" + contexto + "/imgs/ChatFortes.gif' style='vertical-align: middle;'></a></li>\n");
		
		menu.append("</ul>\n\n");

		return menu.toString();
	}

	private static String getFilhos(Long id, String contexto, Collection<Empresa> empresasDoUsuario)
	{
		StringBuilder menuFilho = new StringBuilder();
		String maisFilhos = "";
		boolean carregar = true;
		
		for(Papel papel : roles)
		{
			if (papel.getPapelMae() != null && papel.getPapelMae().getId() == id)
			{
				String url = papel.getUrl().equals("#") ? "#" : contexto + papel.getUrl();
				menuFilho.append("<li><a href='" + url + "'>" + papel.getNome() + "</a>");

				maisFilhos = getFilhos(papel.getId(), contexto, empresasDoUsuario);

				if (!maisFilhos.equals(""))
				{
					menuFilho.append("<ul>\n" + maisFilhos+ "</ul>\n");
				}

				menuFilho.append("</li>\n");
				
				
				if (empresasDoUsuario != null && papel.getCodigo().equals("ROLE_UTI") && carregar)
				{
					menuFilho.append("<li><a href='#'>Alterar Empresa</a>");
					menuFilho.append("<ul>\n");
					for (Empresa emp : empresasDoUsuario) 
						menuFilho.append("<li><a href='" + contexto + "/index.action?empresaId=" + emp.getId() + "'>" + emp.getNome() + "</a>");
					menuFilho.append("</li>\n");
					menuFilho.append("</ul>\n");
					carregar=false;
				}
				
				
			}						
		}

		return menuFilho.toString();
	}

}

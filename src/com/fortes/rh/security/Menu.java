package com.fortes.rh.security;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.acesso.Papel;

public abstract class Menu
{
	private static Collection<Papel> roles;

	public static String getMenuFormatado(Collection<Papel> rolesPapel, String contexto)
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
				menu.append("<ul>\n" + getFilhos(papel.getId(), contexto) + "</ul>\n</li>\n");
			}
		}

		menu.append("<li><a href='" + contexto + "/logout.action' accesskey='a'>S<u>a</u>ir</a></li>\n");

		menu.append("</ul>\n\n");

		return menu.toString();
	}

	private static String getFilhos(Long id, String contexto)
	{
		StringBuilder menuFilho = new StringBuilder();
		String maisFilhos = "";

		for(Papel papel : roles)
		{
			if (papel.getPapelMae() != null && papel.getPapelMae().getId() == id)
			{
				String url = papel.getUrl().equals("#") ? "#" : contexto + papel.getUrl();
				menuFilho.append("<li><a href='" + url + "'>" + papel.getNome() + "</a>");

				maisFilhos = getFilhos(papel.getId(), contexto);

				if (!maisFilhos.equals(""))
				{
					menuFilho.append("<ul>\n" + maisFilhos+ "</ul>\n");
				}

				menuFilho.append("</li>\n");
			}
		}

		return menuFilho.toString();
	}
}
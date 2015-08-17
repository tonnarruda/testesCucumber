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
	private static Collection<String> papeisParaEmpresasIntegradas = new ArrayList<String>();
	
	static{
		papeisParaEmpresasIntegradas.add("ROLE_REL_RECIBO_PAGAMENTO");
	}
	
	public static String getMenuFormatado(Collection<Papel> rolesPapel, String contexto, ParametrosDoSistema parametros, Collection<Empresa> empresasDoUsuario, Empresa empresaLogada)
	{
		roles = new ArrayList<Papel>();
        for (Papel papel : rolesPapel)
        {
        	if ( papel.isMenu() ) 
        		roles.add(papel);
        }

		StringBuilder menu = new StringBuilder("<ul id='menuDropDown'>\n");

		if(!empresaLogada.isProcessoExportacaoAC()){
			for(Papel papel : roles)
			{
				if (papel.getPapelMae() == null)
				{
					String accesskey = "";
					String url = papel.getUrl().equals("#") ? "#" : contexto + papel.getUrl();
					String nome = papel.getNome();
					boolean exibeMenuTru = nome.equals("Utilitários") && empresaLogada.isCodigoTruCurso();

					if(papel.getAccesskey() != null)
					{					
						accesskey = "accesskey='" + papel.getAccesskey() + "'";
						nome = papel.getNome().replaceFirst(papel.getAccesskey(), "<u>" +papel.getAccesskey()+ "</u>");
					}

					menu.append("<li><a href='" + url + "' " + accesskey + " >" + nome +  "</a>\n");
					menu.append("<ul>\n");

					if (empresasDoUsuario != null && papel.getCodigo().equals("ROLE_UTI"))
					{
						menu.append("<li><a href='#'>Alterar Empresa</a>");
						menu.append("<ul>\n");
						for (Empresa emp : empresasDoUsuario) 
							menu.append("<li><a href='" + contexto + "/index.action?empresaId=" + emp.getId() + "'>" + emp.getNome() + "</a>");
						menu.append("</li>\n");
						menu.append("</ul>\n");
					}

					menu.append(getFilhos(papel.getId(), contexto, empresaLogada));

					if (exibeMenuTru)
						menu.append("<li><a href='" + contexto + "/exportacao/prepareExportacaoTreinamentos.action'>Exportar Curso/Turma como ocorrência para o TRU</a>");

					menu.append("</ul>\n</li>\n");
				}
			}
		}

		menu.append("<li><a href='" + contexto + "/logout.action' accesskey='a'>S<u>a</u>ir</a></li>\n");

		menu.append("<li style='float: right; line-height: 0.8em;'><a href='" + contexto + "/geral/documentoVersao/list.action' class='versao'> Versão: "  +  parametros.getAppVersao() +"</a></li>\n");
		
		menu.append("<li style='float: right; line-height: 0.8em'>" +
				"<a href='contatos.action' title='Contatos'>" +
				"<img src='"+ contexto + "/imgs/telefone.gif' style='vertical-align: middle;'></a></li>\n");

		menu.append("<li style='float: right; line-height: 0.8em'>" +
				"<a href='http://twitter.com/#!/fortesinfo' target='_blank' title='Twitter'>" +
				"<img src='"+ contexto + "/imgs/twitter.png' style='vertical-align: middle;'></a></li>\n");
		
		menu.append("<li style='float: right; line-height: 0.8em'>" +
				"<a href='http://blog.fortesinformatica.com.br/categoria/ente-rh/?utm_source=sistema&utm_medium=icone-barra-lateral&utm_content=ente-rh&utm_campaign=clique-blog' target='_blank' title='Blog'>" +
				"<img src='"+ contexto + "/imgs/blog.png' style='vertical-align: middle;'></a></li>\n");
		
		menu.append("<li style='float: right; line-height: 0.8em'>" +
				"<a href='videoteca.action' title='Videoteca'>" +
				"<img src='"+ contexto + "/imgs/video.png' style='vertical-align: middle;'></a></li>\n");
		
		menu.append("<li style='float: right; line-height: 0.8em'>" +
				"<a href='http://www.logmein123.com'  target='_blank' title='LogMeIn'>" +
				"<img src='"+ contexto + "/imgs/logmeinrescue.png' style='vertical-align: middle;'></a></li>\n");
		
		if (parametros.isSuporteVeica())
			menu.append("<li style='float: right; line-height: 0.8em'>" 
					+ "<a href='http://184.106.249.85' target='_blank' title='Fortes Chat'>" 
					+ "<img src='" + contexto + "/imgs/ChatFortes.gif' style='vertical-align: middle;'></a></li>\n");
		else if (!StringUtils.isEmpty(parametros.getCodEmpresaSuporte()) && !StringUtils.isEmpty(parametros.getCodClienteSuporte()))
			menu.append("<li style='float: right; line-height: 0.8em'>" 
				+ "<a href='http://chatonline.grupofortes.com.br/cliente/MATRIZ/" + parametros.getCodClienteSuporte() + "/" + parametros.getCodEmpresaSuporte() + "' target='_blank' title='Fortes Chat'>" 
				+ "<img src='" + contexto + "/imgs/ChatFortes.gif' style='vertical-align: middle;'></a></li>\n");
		
		menu.append("</ul>\n\n");

		return menu.toString();
	}

	private static String getFilhos(Long id, String contexto, Empresa empresaLogada)
	{
		StringBuilder menuFilho = new StringBuilder();
		String maisFilhos = "";
		
		for(Papel papel : roles)
		{
			if (papel.getPapelMae() != null && papel.getPapelMae().getId() == id)
			{
				String url = papel.getUrl().equals("#") ? "#" : contexto + papel.getUrl();
				
				menuFilho.append("<li>");
				
				if (verificaPapeisParaEmpresasIntegradas(papel, empresaLogada) && verificaEmpresaComSolicitacaoDesligamento(papel, empresaLogada)) 
				{
					menuFilho.append("<a href='" + url + "'>" + papel.getNome() + "</a>");
				}

				maisFilhos = getFilhos(papel.getId(), contexto, empresaLogada);

				if (!maisFilhos.equals(""))
				{
					menuFilho.append("<ul>\n" + maisFilhos+ "</ul>\n");
				}

				menuFilho.append("</li>\n");
			}						
		}

		return menuFilho.toString();
	}
	
	private static boolean verificaPapeisParaEmpresasIntegradas(Papel papel, Empresa empresa) 
	{
		return !papeisParaEmpresasIntegradas.contains(papel.getCodigo()) || (papeisParaEmpresasIntegradas.contains(papel.getCodigo()) && empresa.isAcIntegra());
	}
	
	private static boolean verificaEmpresaComSolicitacaoDesligamento(Papel papel, Empresa empresa) 
	{
		return !papel.getCodigo().equalsIgnoreCase("ROLE_MOV_APROV_REPROV_SOL_DESLIGAMENTO") || empresa.isSolicitarConfirmacaoDesligamento();
	}
}

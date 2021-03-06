package com.fortes.rh.security;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;

public abstract class Menu 
{
	private static Collection<Papel> roles;
	private static String CURSOSVENCIDOSAVENCER = "ROLE_REL_CURSOS_VENCIDOS_A_VENCER";
	private static String CERTIFICADOSVENCIDOSAVENCER = "ROLE_REL_CERTIFICADOS_VENCIDOS_A_VENCER";
	private static String COLABORADORAVALIACAOPRATICA = "ROLE_COLABORADOR_AVALIACAO_PRATICA";
	private static String COLABORADORES_CERTIFICACOES = "ROLE_REL_COLABORADORES_CERTIFICACOES";
	private static String AVALIACAOPRATICA = "ROLE_AVALIACAO_PRATICA";
	
	private static Collection<String> papeisParaEmpresasIntegradas = new ArrayList<String>();
	static{
		papeisParaEmpresasIntegradas.add("ROLE_REL_RECIBO_PAGAMENTO");
		papeisParaEmpresasIntegradas.add("ROLE_REL_RECIBO_DECIMO_TERCEIRO");
		papeisParaEmpresasIntegradas.add("ROLE_REL_DECLARACAO_RENDIMENTOS");
		papeisParaEmpresasIntegradas.add("ROLE_REL_RECIBO_FERIAS");
		papeisParaEmpresasIntegradas.add("ROLE_REL_ADIANTAMENTO_DE_FOLHA");
		papeisParaEmpresasIntegradas.add("ROLE_REL_PAGAMENTO_COMPLEMENTAR");
		papeisParaEmpresasIntegradas.add("ROLE_REL_FERIAS");
	}
	
	private static Collection<String> papeisParaURLExternas = new ArrayList<String>();
	static{
		papeisParaURLExternas.add("ROLE_FORMULARIO_SOLICITACAO_EXTERNO");
	}
	
	public static String getMenuFormatado(Collection<Papel> rolesPapel, String contexto, ParametrosDoSistema parametros, Collection<Empresa> empresasDoUsuario, Empresa empresaLogada, Long idDoUsuario)
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
					
					if(papel.getUrl() == null)
						papel.setUrl("#");
					
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

					menu.append(getFilhos(papel.getId(), contexto, empresaLogada, idDoUsuario, parametros));

					if (exibeMenuTru)
						menu.append("<li><a href='" + contexto + "/exportacao/prepareExportacaoTreinamentos.action'>Exportar Curso/Turma como ocorrência para o TRU</a>");
					
					menu.append("</ul>\n</li>\n");
				}
			}
		}

		menu.append("<li><a href='" + contexto + "/logout.action' accesskey='a'>S<u>a</u>ir</a></li>\n");

		menu.append("<li style='float: right; line-height: 0.8em;'><a href='" + contexto + "/geral/documentoVersao/list.action' class='versao'> Versão: "  +  parametros.getAppVersao() +"</a></li>\n");
		
		menu.append("</ul>\n\n");

		return menu.toString();
	}

	private static String getFilhos(Long id, String contexto, Empresa empresaLogada, Long idDoUsuario, ParametrosDoSistema parametros)
	{
		StringBuilder menuFilho = new StringBuilder();
		String maisFilhos = "";

		for(Papel papel : roles)
		{
			if (papel.getPapelMae() != null && papel.getPapelMae().getId() == id )
			{
				if(((empresaLogada.isControlarVencimentoPorCurso() && !papel.getId().equals(648L)) 
						|| (empresaLogada.isControlarVencimentoPorCertificacao() && !papel.getId().equals(635L)))){
					
					if(papel.getUrl() == null)
						papel.setUrl("#");

					String url = papel.getUrl().equals("#") ? "#" : contexto + papel.getUrl();
					
					menuFilho.append("<li>");
					decideUrlMenu(empresaLogada, idDoUsuario, parametros, menuFilho, papel, url);
					maisFilhos = getFilhos(papel.getId(), contexto, empresaLogada, idDoUsuario, parametros);
	
					if (!maisFilhos.equals(""))
					{
						menuFilho.append("<ul>\n" + maisFilhos+ "</ul>\n");
					}
					menuFilho.append("</li>\n");
				}
			}						
		}

		return menuFilho.toString();
	}

	private static void decideUrlMenu(Empresa empresaLogada, Long idDoUsuario, ParametrosDoSistema parametros, StringBuilder menuFilho,	Papel papel, String url) {
		if (verificaPapeisParaEmpresasIntegradas(papel, empresaLogada) && verificaEmpresaComSolicitacaoDesligamento(papel, empresaLogada) 
				&& verificaPapeisParaCursosOuCertificacaoVencidasAVencer(papel, empresaLogada) && verificaPapeisUsuarioSOS(papel, idDoUsuario)
				&& verificaParametrosSistemaAutorizacaoSolicitacaoPessoal(papel, parametros)
				&& verificaPapeisParaColaboradoresSemCursoDasCertificacoes(papel, empresaLogada)) 
		{
			
			menuFilho.append(retornaUrl(papel,url));
		}
	}
	
	private static String retornaUrl(Papel papel, String url) {
		if(papeisParaURLExternas.contains(papel.getCodigo())){
			url = "<a href='" + papel.getUrl() + "'target='_blank'>" + papel.getNome() + "</a>";
		} else {
			url = "<a href='" + url + "'>" + papel.getNome() + "</a>";
		}
		return url;
	}

	private static boolean verificaPapeisParaCursosOuCertificacaoVencidasAVencer(Papel papel, Empresa empresa) 
	{
		if(papel.getCodigo().equalsIgnoreCase(CERTIFICADOSVENCIDOSAVENCER) || papel.getCodigo().equalsIgnoreCase(CURSOSVENCIDOSAVENCER))
		{
			return (empresa.isControlarVencimentoPorCertificacao() && papel.getCodigo().equalsIgnoreCase(CERTIFICADOSVENCIDOSAVENCER)) 
					|| (!empresa.isControlarVencimentoPorCertificacao() && papel.getCodigo().equalsIgnoreCase(CURSOSVENCIDOSAVENCER));
		}else{
			return (!papel.getCodigo().equalsIgnoreCase(COLABORADORAVALIACAOPRATICA) || empresa.isControlarVencimentoPorCertificacao()) 
					&& (!papel.getCodigo().equalsIgnoreCase(AVALIACAOPRATICA) || empresa.isControlarVencimentoPorCertificacao())
					&& !(papel.getCodigo().equalsIgnoreCase(COLABORADORES_CERTIFICACOES) && empresa.isControlarVencimentoPorCertificacao());
		}
	}
	
	private static boolean verificaPapeisParaEmpresasIntegradas(Papel papel, Empresa empresa) 
	{
		return !papeisParaEmpresasIntegradas.contains(papel.getCodigo()) || (papeisParaEmpresasIntegradas.contains(papel.getCodigo()) && empresa.isAcIntegra());
	}
	
	private static boolean verificaEmpresaComSolicitacaoDesligamento(Papel papel, Empresa empresa) 
	{
		return !papel.getCodigo().equalsIgnoreCase("ROLE_MOV_APROV_REPROV_SOL_DESLIGAMENTO") || empresa.isSolicitarConfirmacaoDesligamento();
	}
	
	private static boolean verificaPapeisUsuarioSOS(Papel papel, Long idDoUsuario) 
	{
		return !papel.getCodigo().equalsIgnoreCase("USUARIO_SOS") || idDoUsuario.equals(1L);
	}
	
	private static boolean verificaParametrosSistemaAutorizacaoSolicitacaoPessoal(Papel papel, ParametrosDoSistema parametros) 
	{
		return !papel.getCodigo().equalsIgnoreCase("ROLE_MOV_AUTOR_COLAB_SOL_PESSOAL") || parametros.isAutorizacaoGestorNaSolicitacaoPessoal();
	}
	
	private static boolean verificaPapeisParaColaboradoresSemCursoDasCertificacoes(Papel papel, Empresa empresa){
		return !papel.getCodigo().equalsIgnoreCase("ROLE_REL_COLABORADORES_SEM_CURSO_CERTIFICACOES") || empresa.isControlarVencimentoPorCertificacao();
	}
}

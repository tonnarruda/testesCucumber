/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32
 */

package com.fortes.rh.business.acesso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.StringUtil;

public class PapelManagerImpl extends GenericManagerImpl<Papel, PapelDao> implements PapelManager
{
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	private String[] marcados;
	private Collection<Papel> listaFull = new ArrayList<Papel>();

	public String getPerfilOrganizado(String[] marcados, boolean acessoModulos)
	{
		this.marcados = marcados;

		ParametrosDoSistema parametros = parametrosDoSistemaManager.findByIdProjection(1L);
		String[] permissoes = parametrosDoSistemaManager.getModulosDecodificados(parametros);

		Collection<Long> modulosConfigurados = Autenticador.getModulosNaoConfigurados(parametros.getServidorRemprot());

		this.listaFull = getDao().findNotIn(modulosConfigurados);
		Collection<Papel> papeisPermitidos = new ArrayList<Papel>();

		if (acessoModulos)
		{
			this.marcados = permissoes;
			 papeisPermitidos = listaFull;
		}
		else
		{
			for (Papel papel : listaFull)
			{
				boolean permitido = false;
				for (String permissao : permissoes)
				{
					if (papel.getId().equals(Long.valueOf(permissao)))
					{
						permitido = true;
						break;
					}
				}
				if (permitido)
					papeisPermitidos.add(papel);
			}
		}

		return montarOpcoes(papeisPermitidos);
	}
	
	private String montarOpcoes(Collection<Papel> papeis)
	{
		StringBuilder perfilOrganizado = new StringBuilder();
		String marcar = "";

		for (Papel papel : papeis)
		{
			if(papel.getPapelMae() == null)
			{
				if (this.marcados != null)
				{
					marcar = "";
					for (int i = 0; i < marcados.length; i++)
					{
						if (papel.getId().toString().equals(marcados[i]))
							marcar = "checked";
					}
				}

				papel.setIdExibir("idCheck" + papel.getId());
				perfilOrganizado.append("<li><input type='checkbox' " + marcar + " onchange='checkSystem(this)' onclick='checkSystem(this)' onfocus='blur()' name='permissoes' value='" + papel.getId() + "' id='" + papel.getIdExibir() + "' /><label for='" + papel.getIdExibir() + "'>" + papel.getNome() + "</label>");
				perfilOrganizado.append("\n<ul class='padding'>\n" + getFilhos(papel.getId(), papeis, papel.getIdExibir()) + "</ul>\n</li>\n");
			}
		}

		return perfilOrganizado.toString();
	}
	
	public String montarArvore(Collection<Papel> papeis)
	{
		StringBuilder perfilOrganizado = new StringBuilder();
		
		for (Papel papel : papeis)
		{
			if(papel.getPapelMae() == null || papel.getPapelMae().getId() == null)
			{
				perfilOrganizado.append( "\n" + papel.getNome() + "\n");
				perfilOrganizado.append( getFolha(papel.getId(), papeis, 3));
			}
		}
		
		return perfilOrganizado.substring(1);
	}
	
	private String getFolha(Long id, Collection<Papel> papeis, int posicao)
	{
		StringBuffer filhos = new StringBuffer();
		
		for (Papel papel : papeis)
		{
			StringBuffer maisFilhos;
			if(papel.getPapelMae() != null && papel.getPapelMae().getId() != null && papel.getPapelMae().getId().equals(id))
			{
				filhos.append(String.format("%"+ posicao +"s", "") +  papel.getNome() + "\n");
				maisFilhos = new StringBuffer(getFolha(papel.getId(), papeis, posicao + 3));

				if(!maisFilhos.toString().equals("")) 
					filhos.append( maisFilhos.toString() );
			}
		}

		return filhos.toString();
	}

	

	private String getFilhos(Long id, Collection<Papel> papeis, String idExibir)
	{
		StringBuilder filhos = new StringBuilder();
		String maisFilhos = "";
		String marcar = "";

		Long filhoNumero = Long.valueOf(1);

		for (Papel papel : papeis)
		{
			if(papel.getPapelMae() != null && papel.getPapelMae().getId() == id)
			{
				if (marcados != null)
				{
					marcar = "";
					for (int i = 0; i < marcados.length; i++)
					{
						if (papel.getId().toString().equals(marcados[i]))
							marcar = "checked";
					}
				}

				papel.setIdExibir(idExibir + "_" + filhoNumero);
				filhos.append("<li><input type='checkbox' " + marcar + " onchange='checkSystem(this)' onclick='checkSystem(this)' onfocus='blur()' name='permissoes' value='" + papel.getId() + "' id='" + papel.getIdExibir() + "' /><label for='" + papel.getIdExibir() + "'>" + papel.getNome() + "</label>");
				filhoNumero++;

				maisFilhos = getFilhos(papel.getId(), papeis, papel.getIdExibir());
				if(maisFilhos != "")
				{
					filhos.append("\n<ul class='padding'>\n" + maisFilhos + "</ul>\n");
				}
				filhos.append("</li>\n");
			}

		}

		return filhos.toString();
	}

	public Collection<Long> getPapeisPermitidos()
	{
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findByIdProjection(1L);
		String[] modulosPermitidos = parametrosDoSistemaManager.getModulosDecodificados(parametros);
		
		Collection<Long> modulosIds = LongUtil.arrayStringToCollectionLong(modulosPermitidos);
		
		return modulosIds;
	}
	
	public void atualizarPapeis(Long atualizaPapeisIdsAPartirDe) 
	{
		if (atualizaPapeisIdsAPartirDe != null)
		{
			Collection<Long> papeisPermitidosIds = getPapeisPermitidos();
			
			Collection<Papel> novosPapeis = getDao().findPapeisAPartirDe(atualizaPapeisIdsAPartirDe);
			
			Collection<Long> todosOsPapeisPermitidos = new TreeSet<Long>();
			todosOsPapeisPermitidos.addAll(papeisPermitidosIds);
			
			for (Papel papel : novosPapeis)
			{
				if (todosOsPapeisPermitidos.contains(papel.getPapelMae().getId()))
					todosOsPapeisPermitidos.add(papel.getId());
				
			}
			
			String modulos = StringUtil.converteCollectionLongToString(todosOsPapeisPermitidos); 
			
			parametrosDoSistemaManager.updateModulos(modulos);
			parametrosDoSistemaManager.disablePapeisIds();
		}
	}
	
	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Collection<Papel> findByPerfil(Long perfilId) 
	{
		return getDao().findByPerfil(perfilId);
	}


}
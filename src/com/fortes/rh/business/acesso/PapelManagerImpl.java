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

		String[] permissoes = parametrosDoSistemaManager.getModulosDecodificados();

		this.listaFull = findAll(new String[]{"ordem"});
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
		String[] modulosPermitidos = parametrosDoSistemaManager.getModulosDecodificados();
		
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
				if (papeisPermitidosIds.contains(papel.getPapelMae().getId()))
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


}
package com.fortes.rh.business.acesso;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.exception.NotRegistredException;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.StringUtil;
import com.opensymphony.xwork.ActionContext;

@Component
public class PapelManagerImpl extends GenericManagerImpl<Papel, PapelDao> implements PapelManager
{
	@Autowired private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private static Long ROLE_CX_MENSAGEM = 495L;
	private ParametrosDoSistema parametrosDoSistema;
	
	@Autowired
	PapelManagerImpl(PapelDao dao) {
		setDao(dao);
	}

	public String getPerfilOrganizado(String[] marcados, Collection<Papel> papeisComHelp, Long idDoUsuario) throws NotConectAutenticationException, NotRegistredException
	{
		Collection<Long> modulosNaoConfigurados = Autenticador.getModulosNaoConfigurados();
		Collection<Papel> papeisSemModulosNaoConfigurados = getDao().findNotIn(modulosNaoConfigurados);
		parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		return montarOpcoes(papeisSemModulosNaoConfigurados, marcados, papeisComHelp, idDoUsuario);
	}
	
	private String montarOpcoes(Collection<Papel> papeisSemModulosNaoConfigurados, String[] marcados, Collection<Papel> papeisComHelp, Long idDoUsuario)
	{
		StringBuilder perfilOrganizado = new StringBuilder();
		String marcar = "";

		for (Papel papel : papeisSemModulosNaoConfigurados)
		{
			if(papel.getPapelMae() == null)
			{
				if (marcados != null)
				{
					marcar = "";
					for (int i = 0; i < marcados.length; i++)
					{
						if (papel.getId().toString().equals(marcados[i])){
							marcar = "checked";
							break;
						}
					}
				}

				papel.setIdExibir("idCheck" + papel.getId());
				perfilOrganizado.append("<li><input type='checkbox' " + marcar + " onchange='checkSystem(this)' onclick='checkSystem(this)' onfocus='blur()' name='permissoes' value='" + papel.getId() + "' id='" + papel.getIdExibir() + "' /><label for='" + papel.getIdExibir() + "'>" + papel.getNome() + "</label>");
				perfilOrganizado.append("\n<ul class='padding'>\n" + getFilhos(papel.getId(), papeisSemModulosNaoConfigurados, papel.getIdExibir(), marcados, papeisComHelp, idDoUsuario) + "</ul>\n</li>\n");
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

	private String getFilhos(Long id, Collection<Papel> papeis, String idExibir, String[] marcados, Collection<Papel> papeisComHelp, Long idDoUsuario)
	{
		StringBuilder filhos = new StringBuilder();
		String maisFilhos = "";
		String marcar = "";

		Long filhoNumero = Long.valueOf(1);

		for (Papel papel : papeis)
		{
			if(idDoUsuario != null && !idDoUsuario.equals(1L) && papel.getId().equals(673L))
				continue;
			
			if(!parametrosDoSistema.isAutorizacaoGestorNaSolicitacaoPessoal() && papel.getId().equals(684L))
				continue;
			
			if(papel.getPapelMae() != null && papel.getPapelMae().getId() == id)
			{
				if (marcados != null)
				{
					marcar = "";
					for (int i = 0; i < marcados.length; i++)
					{
						if (papel.getId().toString().equals(marcados[i]))
						{
							marcar = "checked";
							break;
						}
					}
				}

				papel.setIdExibir(idExibir + "_" + filhoNumero);
				filhos.append("<li><input type='checkbox' " + marcar + " onchange='checkSystem(this)' onclick='checkSystem(this)' onfocus='blur()' name='permissoes' value='" + papel.getId() + "' id='" + papel.getIdExibir() + "' /><label for='" + papel.getIdExibir() + "'>" + papel.getNome() + "</label>");
				
				if(papel.getHelp() != null && !StringUtil.isBlank(papel.getHelp()))
				{
					papeisComHelp.add(papel);
					filhos.append("<img id=help_" + papel.getIdExibir() + " src=\"" + parametrosDoSistemaManager.getContexto() + "/imgs/help.gif\" width=\"16\" height=\"16\" style=\"margin-left:2px;margin-bottom:-4px\"/>");
				}

				filhoNumero++;

				maisFilhos = getFilhos(papel.getId(), papeis, papel.getIdExibir(), marcados, papeisComHelp, idDoUsuario);
				if(maisFilhos != "")
				{
					filhos.append("\n<ul class='padding'>\n" + maisFilhos + "</ul>\n");
				}
				filhos.append("</li>\n");
			}

		}

		return filhos.toString();
	}

	public Collection<Long> getPapeisPermitidos() throws Exception
	{
		Collection<Long> modulosConfigurados = new ArrayList<Long>();
		Collection<Long> modulosNaoConfigurados = Autenticador.getModulosNaoConfigurados();
		
		for (Long modulo : Autenticador.modulos) {
			if (!modulosNaoConfigurados.contains(modulo))
				modulosConfigurados.add(modulo);
		}
		
		modulosConfigurados.add(ROLE_CX_MENSAGEM);//Para inserir roles filhas

		Collection<Long> papeisPermitidos = new ArrayList<Long>(modulosConfigurados);
		papeisPermitidos.addAll(addFilhos(modulosConfigurados));
		
		return papeisPermitidos;
	}
	
	private Collection<Long> addFilhos(Collection<Long> modulos) throws Exception
	{
		Collection<Long> retornoPapeisPermitidos = new ArrayList<Long>();
		Collection<Papel> todosPapeis = findAll(); 

		for (Long modulo: modulos) 
			retornoPapeisPermitidos.addAll(maisPapeis(modulo, todosPapeis));
		
		return retornoPapeisPermitidos;
	}

	private Collection<Long> maisPapeis(Long papelId, Collection<Papel> todosPapeis )
	{
		Collection<Long> retornoPapeisPermitidos = new ArrayList<Long>();
		
		for (Papel papel : todosPapeis) 
		{
			if(papel.getPapelMae() != null && papelId.equals(papel.getPapelMae().getId()))
			{
				retornoPapeisPermitidos.add(papel.getId());
				retornoPapeisPermitidos.addAll(maisPapeis(papel.getId(), todosPapeis));
			}
		}
		
		return retornoPapeisPermitidos;
	}

	public Collection<Papel> findByPerfil(Long perfilId) 
	{
		return getDao().findByPerfil(perfilId);
	}

	public boolean possuiModuloSESMT() throws NotConectAutenticationException, NotRegistredException
	{
		return SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()).equals(1L) 
				|| !parametrosDoSistemaManager.findById(1L).verificaRemprot() 
				|| !Autenticador.getModulosNaoConfigurados().contains(75L);
	}
}
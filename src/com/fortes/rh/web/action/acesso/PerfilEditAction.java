package com.fortes.rh.web.action.acesso;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.acesso.PapelManager;
import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

public class PerfilEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;
	
	private PerfilManager perfilManager;
	private PapelManager papelManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	private Perfil perfil = new Perfil();;
	private String[] permissoes;
	private String exibirPerfil;

	private Collection<Long> modulosNaoConfigurados;

	
	private void prepare() throws Exception
	{
		if(perfil != null && perfil.getId() != null)
			perfil = (Perfil) perfilManager.findById(perfil.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		exibirPerfil = papelManager.getPerfilOrganizado(null, false);
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		permissoes = perfilManager.montaPermissoes(perfil);

		exibirPerfil = papelManager.getPerfilOrganizado(permissoes, false);

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		perfil.setPapeis(getPapeisInformados());
		perfilManager.save(perfil);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		perfil.setPapeis(getPapeisInformados());
		perfilManager.update(perfil);

		return Action.SUCCESS;
	}

	public String prepareModulos() throws Exception
	{
		if(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId().equals(1L))
		{
			//TODO remprot msgAC
			//modulosNaoConfigurados = Autenticador.getModulosNaoConfigurados(parametrosDoSistemaManager.findByIdProjection(1L).getServidorRemprot());
			permissoes = perfilManager.montaPermissoes(perfil);
			exibirPerfil = papelManager.getPerfilOrganizado(permissoes, true);
		}
		else
			addActionError("Você não tem permissão para acessar essa tela.");

		return Action.SUCCESS;
	}

	public String updateModulos() throws Exception
	{
		if(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId().equals(1L))
		{
			//TODO remprot msgAC
			//modulosNaoConfigurados = Autenticador.getModulosNaoConfigurados(parametrosDoSistemaManager.findByIdProjection(1L).getServidorRemprot());
			String papeis = StringUtil.converteArrayToString(permissoes);
			parametrosDoSistemaManager.updateModulos(papeis);

			addActionMessage("Dados gravados com sucesso.");

			permissoes = perfilManager.montaPermissoes(perfil);
			exibirPerfil = papelManager.getPerfilOrganizado(permissoes, true);
		}
		else
			addActionError("Você não tem permissão para acessar essa tela.");

		return Action.SUCCESS;
	}

	private Collection<Papel> getPapeisInformados()
	{
		Collection<Papel> result = new ArrayList<Papel>();

		if (permissoes != null)
		{
			for (int i = 0; i < permissoes.length; i++)
			{
				Papel papel = new Papel();
				papel.setId(Long.valueOf(permissoes[i]));
				result.add(papel);
			}
		}

		return result;
	}

	public Object getModel()
	{
		return getPerfil();
	}

	public Perfil getPerfil()
	{
		return perfil;
	}

	public void setPerfil(Perfil perfil)
	{
		this.perfil=perfil;
	}

	public void setPerfilManager(PerfilManager perfilManager)
	{
		this.perfilManager=perfilManager;
	}

	public void setPapelManager(PapelManager papelManager)
	{
		this.papelManager = papelManager;
	}

	public String getExibirPerfil()
	{
		return exibirPerfil;
	}

	public void setExibirPerfil(String exibirPerfil)
	{
		this.exibirPerfil = exibirPerfil;
	}

	public String[] getPermissoes()
	{
		return permissoes;
	}

	public void setPermissoes(String[] permissoes)
	{
		this.permissoes = permissoes;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Collection<Long> getModulosNaoConfigurados() {
		return modulosNaoConfigurados;
	}
}
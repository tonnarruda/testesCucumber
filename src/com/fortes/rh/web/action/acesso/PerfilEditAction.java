package com.fortes.rh.web.action.acesso;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.acesso.PapelManager;
import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.exception.NotRegistredException;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

public class PerfilEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;
	
	private PerfilManager perfilManager;
	private PapelManager papelManager;

	private Collection<Papel> papeisComHelp = new ArrayList<Papel>();
	private Perfil perfil = new Perfil();;
	private String[] permissoes;
	private String exibirPerfil;

	private Collection<Long> modulosNaoConfigurados;

	
	private void prepare() throws Exception
	{
		if(perfil != null && perfil.getId() != null)
			perfil = (Perfil) perfilManager.findById(perfil.getId());
	}

	public String prepareInsert()
	{
		try {
			prepare();
			exibirPerfil = papelManager.getPerfilOrganizado(null, papeisComHelp, getUsuarioLogado().getId());
		} catch (NotConectAutenticationException e) {
			e.printStackTrace();
			addActionMessage(e.getMessage());
		} catch (NotRegistredException e) {
			e.printStackTrace();
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		try {
			prepare();
			permissoes = perfilManager.montaPermissoes(perfil);
			exibirPerfil = papelManager.getPerfilOrganizado(permissoes, papeisComHelp, getUsuarioLogado().getId());
		} catch (NotConectAutenticationException e) {
			e.printStackTrace();
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
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

	public Collection<Long> getModulosNaoConfigurados() {
		return modulosNaoConfigurados;
	}

	public Collection<Papel> getPapeisComHelp() {
		return papeisComHelp;
	}
}
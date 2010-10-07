package com.fortes.rh.model.acesso;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.model.geral.Empresa;

public class UsuarioEmpresaManagerImpl extends GenericManagerImpl<UsuarioEmpresa, UsuarioEmpresaDao> implements UsuarioEmpresaManager
{
	public void removeAllUsuario(Usuario usuario)
	{
		getDao().removeAllUsuario(usuario);
	}

	public UsuarioEmpresa findByUsuarioEmpresa(Long usuarioId, Long empresaId)
	{
		Collection<UsuarioEmpresa> usuarioEmpresas = find(new String[] { "usuario.id", "empresa.id" }, new Object[] { usuarioId, empresaId });

		if (usuarioEmpresas.isEmpty())
			return null;
		else
			return (UsuarioEmpresa) usuarioEmpresas.toArray()[0];
	}

	public Collection<UsuarioEmpresa> findAllBySelectUsuarioEmpresa(Long empresaId)
	{
		return getDao().findAllBySelectUsuarioEmpresa(empresaId);
	}

	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRoleSetorPessoal(String empresaCodigoAC)
	{
		return getDao().findUsuariosByEmpresaRoleSetorPessoal(empresaCodigoAC, null);
	}
	
	/** 
	 * Usuários que recebem mensagens de lembrete para Avaliação do Período de Experiência 
	 * Obs. Utiliza o mesmo ROLE que recebe mensagens do AC Pessoal.  */
	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRoleAvaliacaoExperiencia(Long empresaId)
	{
		return getDao().findUsuariosByEmpresaRoleSetorPessoal(null, empresaId);
	}

	public void save(Usuario usuario, String[] empresaIds, String[] selectPerfils)
	{
		if (empresaIds != null && selectPerfils != null)
		{
			for (int i = 0; i < empresaIds.length; i++)
			{
				UsuarioEmpresa usuarioEmpresaAux = new UsuarioEmpresa();
				usuarioEmpresaAux.setUsuario(usuario);

				Empresa empresaAux = new Empresa();
				empresaAux.setId(Long.valueOf(empresaIds[i]));
				usuarioEmpresaAux.setEmpresa(empresaAux);

				Perfil perfilAux = new Perfil();
				perfilAux.setId(Long.valueOf(selectPerfils[i]));
				usuarioEmpresaAux.setPerfil(perfilAux);

				save(usuarioEmpresaAux);
			}
		}
	}

	public Collection<UsuarioEmpresa> findByUsuario(Long usuarioId)
	{
		return getDao().findByUsuario(usuarioId);
	}
}
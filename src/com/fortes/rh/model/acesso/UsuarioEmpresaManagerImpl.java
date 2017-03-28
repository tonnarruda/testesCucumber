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

	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRoleSetorPessoal(String empresaCodigoAC, String grupoAC, Long notUsuarioId)
	{
		return getDao().findUsuariosByEmpresaRole(empresaCodigoAC, grupoAC, null, "ROLE_VISUALIZAR_PENDENCIA_AC", notUsuarioId);
	}

	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRole(Long empresaId, String role)
	{
		return getDao().findUsuariosByEmpresaRole(empresaId, role);
	}
	
	public boolean containsRole(Long usuarioId, Long empresaId, String role) {
		return getDao().containsRole(usuarioId, empresaId, role);
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

	public Collection<UsuarioEmpresa> findUsuarioResponsavelAreaOrganizacional(Collection<Long> areasIds) 
	{
		return getDao().findUsuarioResponsavelAreaOrganizacional(areasIds);
	}

	public Collection<UsuarioEmpresa> findUsuarioResponsavelOuCoResponsavelPorAreaOrganizacional(Collection<Long> areasIds, Long colaboradorId, int tipoResponsavel){
		return getDao().findUsuarioResponsavelOuCoResponsavelPorAreaOrganizacional(areasIds, colaboradorId, tipoResponsavel);
	}
	
	public Collection<UsuarioEmpresa> findUsuarioCoResponsavelAreaOrganizacional(Collection<Long> areasIds) 
	{
		return getDao().findUsuarioCoResponsavelAreaOrganizacional(areasIds);
	}
	
	public Collection<UsuarioEmpresa> findPerfisEmpresas() {
		return getDao().findPerfisEmpresas();
	}

	public Collection<UsuarioEmpresa> findUsuariosAtivo(Collection<Long> usuarioIds, Long empresaId) 
	{
		return getDao().findUsuariosAtivo(usuarioIds, empresaId);
	}

	public Collection<UsuarioEmpresa> findByColaboradorId(Long colaboradorId) {
		return getDao().findByColaboradorId(colaboradorId);
	}
}
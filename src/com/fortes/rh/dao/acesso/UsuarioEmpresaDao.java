package com.fortes.rh.dao.acesso;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;

public interface UsuarioEmpresaDao extends GenericDao<UsuarioEmpresa>
{
	public Collection<UsuarioEmpresa> findAllBySelectUsuarioEmpresa(Long empresaId);

	public void removeAllUsuario(Usuario usuario);

	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRoleSetorPessoal(String empresaCodigoAC, String grupoAC, Long empresaId);

	public Collection<UsuarioEmpresa> findByUsuario(Long usuarioId);
	
	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRole(Long empresaId, String role);

	public Collection<UsuarioEmpresa> findUsuarioResponsavelAreaOrganizacional(Collection<Long> areasIds);
}
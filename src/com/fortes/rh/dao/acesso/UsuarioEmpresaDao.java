package com.fortes.rh.dao.acesso;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;

public interface UsuarioEmpresaDao extends GenericDao<UsuarioEmpresa>
{
	public Collection<UsuarioEmpresa> findAllBySelectUsuarioEmpresa(Long empresaId);

	public void removeAllUsuario(Usuario usuario);

	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRole(String empresaCodigoAC, String grupoAC, Long empresaId, String role, Long usuarioIdDesconsiderado);

	public Collection<UsuarioEmpresa> findByUsuario(Long usuarioId);
	
	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRole(Long empresaId, String role);
	
	public boolean containsRole(Long usuarioId, Long empresaId, String role);

	public Collection<UsuarioEmpresa> findUsuarioResponsavelAreaOrganizacional(Collection<Long> areasIds);
	
	public Collection<UsuarioEmpresa> findUsuarioCoResponsavelAreaOrganizacional(Collection<Long> areasIds);

	public Collection<UsuarioEmpresa> findPerfisEmpresas();

	public Collection<UsuarioEmpresa> findUsuariosAtivo(Collection<Long> usuarioIds, Long empresaId);

	public Collection<UsuarioEmpresa> findByColaboradorId(Long colaboradorId);
}
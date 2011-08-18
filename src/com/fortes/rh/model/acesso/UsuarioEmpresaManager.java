package com.fortes.rh.model.acesso;

import java.util.Collection;

import com.fortes.business.GenericManager;

public interface UsuarioEmpresaManager extends GenericManager<UsuarioEmpresa>
{
	UsuarioEmpresa findByUsuarioEmpresa(Long usuarioId, Long empresaId);
	Collection<UsuarioEmpresa> findAllBySelectUsuarioEmpresa(Long empresaId);
	void removeAllUsuario(Usuario usuario);
	Collection<UsuarioEmpresa> findUsuariosByEmpresaRoleSetorPessoal(String empresaCodigoAC, String grupoAC);
	Collection<UsuarioEmpresa> findUsuariosByEmpresaRoleAvaliacaoExperiencia(Long empresaId, String role);
	void save(Usuario usuario, String[] empresaIds, String[] selectPerfils);
	Collection<UsuarioEmpresa> findByUsuario(Long usuarioId);
	Collection<UsuarioEmpresa> findUsuariosByEmpresaRole(Long empresaId, String role);
	Collection<UsuarioEmpresa> findUsuarioResponsavelAreaOrganizacional(Collection<Long> areasIds);
	Collection<UsuarioEmpresa> findPerfisEmpresas();
}
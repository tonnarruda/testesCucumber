package com.fortes.rh.model.acesso;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.security.spring.aop.callback.UsuarioEmpresaAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface UsuarioEmpresaManager extends GenericManager<UsuarioEmpresa>
{
	UsuarioEmpresa findByUsuarioEmpresa(Long usuarioId, Long empresaId);
	Collection<UsuarioEmpresa> findAllBySelectUsuarioEmpresa(Long empresaId);
	void removeAllUsuario(Usuario usuario);
	Collection<UsuarioEmpresa> findUsuariosByEmpresaRoleSetorPessoal(String empresaCodigoAC, String grupoAC, Long notUsuarioId);
	@Audita(operacao="Inserção/Atualização", auditor=UsuarioEmpresaAuditorCallbackImpl.class)
	void save(Usuario usuario, String[] empresaIds, String[] selectPerfils);
	Collection<UsuarioEmpresa> findByUsuario(Long usuarioId);
	Collection<UsuarioEmpresa> findUsuariosByEmpresaRole(Long empresaId, String role);
	Collection<UsuarioEmpresa> findUsuarioResponsavelAreaOrganizacional(Collection<Long> areasIds);
	Collection<UsuarioEmpresa> findUsuarioCoResponsavelAreaOrganizacional(Collection<Long> areasIds);
	Collection<UsuarioEmpresa> findPerfisEmpresas();
	Collection<UsuarioEmpresa> findUsuariosAtivo(Collection<Long> usuarioIds, Long empresaId);
	Collection<UsuarioEmpresa> findByColaboradorId(Long colaboradorId);
}
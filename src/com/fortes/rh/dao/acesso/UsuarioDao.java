/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32 */
package com.fortes.rh.dao.acesso;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;

public interface UsuarioDao extends GenericDao<Usuario>
{
	Usuario findByLogin(String login);

	Collection findUsuarios(int page, int pagingSize, String nomeBusca, Empresa empresa);

	Integer getCountUsuario(String nomeBusca, Empresa empresa);

	Collection<Usuario> findAllSelect();

	Usuario findByLogin(Usuario usuario);

	void desativaAcessoSistema(boolean removeAcesso, Long... colaboradoresIds);
	
	void reativaAcessoSistema(Long colaboradorId);

	void setUltimoLogin(Long id);

	void desativaSuperAdmin();
	
	Usuario findByIdProjection(Long usuarioId);
	
	Collection<Usuario> findAllSelect(Long empresaId);
	
	String[] findEmailsByUsuario(Long[] usuariosIds);

	void updateConfiguracoesMensagens(Long usuarioId, String caixasMensagens);

	Collection<Empresa> findEmpresas(String usuarioNome);

	String[] findEmailsByPerfil(String role, Long EmpresaId);
	
	public String[] findEmailsByPerfilAndGestor(String role, Long empresaId, Collection<Long> areaOrganizacionalIds, boolean isVerTodosColaboradores, String notEmail);
	
	void removeAcessoSistema(Long... colaboradoresIds);
}
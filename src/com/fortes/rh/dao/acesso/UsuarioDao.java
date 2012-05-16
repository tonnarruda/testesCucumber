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
	public Usuario findByLogin(String login);

	public Collection findUsuarios(int page, int pagingSize, String nomeBusca, Empresa empresa);

	public Integer getCountUsuario(String nomeBusca, Empresa empresa);

	public Collection<Usuario> findAllSelect();

	public Usuario findByLogin(Usuario usuario);

	public void desativaAcessoSistema(Long colaboradorId);
	
	public void reativaAcessoSistema(Long colaboradorId);

	public void setUltimoLogin(Long id);

	public void desativaSuperAdmin();
	
	public Usuario findByIdProjection(Long usuarioId);
	
	public Collection<Usuario> findAllSelect(Long empresaId);
	
	public String[] findEmailsByUsuario(Long[] usuariosIds);

}
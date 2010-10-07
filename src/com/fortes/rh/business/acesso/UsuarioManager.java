/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32
 */
package com.fortes.rh.business.acesso;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.LoginExisteException;
import com.fortes.rh.exception.SenhaNaoConfereException;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.web.tags.CheckBox;

public interface UsuarioManager extends GenericManager<Usuario>
{
	public Usuario findByLogin(String login);

	public Colaborador prepareCriarUsuario(Colaborador colaborador);

	public void updateSenha(Usuario usuario);

	public Collection findUsuarios(int page, int pagingSize,String nomeBusca, Empresa empresa);

	public Integer getCountUsuario(String nomeBusca, Empresa empresa);

	public Collection<Usuario> findAllSelect();

	public Collection<Usuario> findAllBySelectUsuarioEmpresa(Long empresaId);

	public void removeUsuario(Usuario usuario) throws Exception;

	public boolean existeLogin(Usuario usuario);

	Collection<CheckBox> populaCheckOrderNome(Long empresaId);

	public void desativaAcessoSistema(Long colaboradorId);
	
	public void reativaAcessoSistema(Long colaboradorId);

	public Usuario save(Usuario usuario, Long colaboradorId, String[] empresasId, String[] selectPerfils) throws LoginExisteException, SenhaNaoConfereException, Exception;

	public void update(Usuario usuario, Long colaboradorId, String[] empresasId, String[] selectPerfils) throws LoginExisteException, SenhaNaoConfereException, Exception;

	public void createAuto(Empresa empresa, Perfil perfil, String senhaPadrao) throws Exception;

	public void setUltimoLogin(Long id);

}
/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32
 */

package com.fortes.rh.business.acesso;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.exception.NotRegistredException;
import com.fortes.rh.model.acesso.Papel;

public interface PapelManager extends GenericManager<Papel>
{
	String getPerfilOrganizado(String[] marcados, Collection<Papel> papeisComHelp, Long idDoUsuario) throws NotConectAutenticationException, NotRegistredException;
	boolean possuiModuloSESMT() throws NotConectAutenticationException, NotRegistredException;
	Collection<Long> getPapeisPermitidos() throws Exception;
	Collection<Papel> findByPerfil(Long perfilId);
	String montarArvore(Collection<Papel> papeis);
}
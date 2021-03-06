package com.fortes.rh.dao.geral;


import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.UsuarioMensagem;

public interface UsuarioMensagemDao extends GenericDao<UsuarioMensagem>
{
	Collection<UsuarioMensagem> listaUsuarioMensagem(Long usuarioId, Long empresaId, Character[] arrayTipos);
	UsuarioMensagem findByIdProjection(Long usuarioMensagemId, Long empresaId);
	Boolean possuiMensagemNaoLida(Long usuarioId, Long empresaId);
	Long findAnteriorOuProximo(Long usuarioMensagemId, Long usuarioId, Long empresaId, char opcao, Character tipo);
	Integer countMensagens(Long empresaId, Long usuarioId, Character tipo);
}
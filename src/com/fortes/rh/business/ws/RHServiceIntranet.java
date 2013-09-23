package com.fortes.rh.business.ws;

import java.util.Collection;
import java.util.Map;

import com.fortes.rh.model.ws.UsuarioIntranet;


public interface RHServiceIntranet
{
	Boolean usuarioIsDesligado(Long colaboradorId);
	Map<Long, String> getListaSetor(Long[] empresaIds);
	Collection<UsuarioIntranet> atualizaUsuarios(Long empresaId);
}
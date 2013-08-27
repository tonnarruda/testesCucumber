package com.fortes.rh.business.ws;

import java.util.Map;

import com.fortes.rh.model.ws.UsuarioIntranet;


public interface RHServiceIntranet
{
	UsuarioIntranet getUsuario(Long colaboradorId);
	Map<Long, String> getListaSetor(Long[] empresaIds);
	Long[] getUsuarioPorSetor(Long areaId);
}
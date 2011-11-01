package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.UsuarioMensagem;

public interface UsuarioMensagemManager extends GenericManager<UsuarioMensagem>
{
	Collection<UsuarioMensagem> listaUsuarioMensagem(Long usuarioId, Long empresaId);
	UsuarioMensagem findByIdProjection(Long usuarioMensagemId, Long empresaId);
	Boolean possuiMensagemNaoLida(Long usuarioId, Long empresaId);
	void salvaMensagem(Empresa empresa, Mensagem mensage, String[] usuariosCheck) throws Exception;
	void saveMensagemAndUsuarioMensagem(String mensagem, String remetrnte, String link, Collection<UsuarioEmpresa> usuarioEmpresas, Colaborador colaborador, char tipoMensagem);
	void saveMensagemAndUsuarioMensagemRespAreaOrganizacional(String mensagem, String remetrnte, String link, Collection<UsuarioEmpresa> usuarioEmpresas, Collection<Long> areasIds);
	Long getAnteriorOuProximo(Long usuarioMensagemId, Long usuarioId, Long empresaId, char opcao);
	void delete(UsuarioMensagem usuarioMensagem, Long[] usuarioMensagemIds);
}
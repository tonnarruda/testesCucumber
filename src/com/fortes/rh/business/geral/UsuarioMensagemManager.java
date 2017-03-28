package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.geral.CaixaMensagem;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.UsuarioMensagem;

public interface UsuarioMensagemManager extends GenericManager<UsuarioMensagem>
{
	Map<Character, CaixaMensagem> listaMensagens(Long usuarioId, Long empresaId, Long colaboradorId);
	UsuarioMensagem findByIdProjection(Long usuarioMensagemId, Long empresaId);
	Boolean possuiMensagemNaoLida(Long usuarioId, Long empresaId);
	void salvaMensagem(Empresa empresa, Mensagem mensage, String[] usuariosCheck) throws Exception;
	void saveMensagemAndUsuarioMensagem(String mensagem, String remetrnte, String link, Collection<UsuarioEmpresa> usuarioEmpresas, Colaborador colaborador, char tipoMensagem, Avaliacao avaliacao, Long usuarioIdDesconsiderado);
	void saveMensagemAndUsuarioMensagemRespAreaOrganizacional(String mensagem, String remetente, String link, Collection<Long> areasIds, char tipoMensagem, Avaliacao avaliacao, Long usuarioIdDesconsiderado);
	void saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(String mensagem, String remetente, String link, Collection<Long> areasIds, char tipoMensagem, Avaliacao avaliacao, Long usuarioIdDesconsiderado);
	Long getAnteriorOuProximo(Long usuarioMensagemId, Long usuarioId, Long empresaId, char opcao, Character tipo);
	void delete(UsuarioMensagem usuarioMensagem, Long[] usuarioMensagemIds);
	Integer countMensagens(Long empresaId, Long usuarioId, Character tipo);
	void saveMensagemResponderAcompPeriodoExperiencia(String msg, String remetente, String link, Collection<Long> areasIds, char tipoMensagem, Avaliacao avaliacao, Colaborador colaborador, int tipoResponsavel);
}
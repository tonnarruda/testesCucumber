package com.fortes.rh.business.captacao;

import java.util.Collection;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.web.tags.CheckBox;

public interface AnuncioManager extends GenericManager<Anuncio>
{
	Collection<CheckBox> getEmpresasCheck(Long empresaId) throws Exception;

	String[] montaEmails(String emailAvulso, String[] empresasCheck);

	void removeBySolicitacao(Long solicitacaoId);

	Anuncio findByIdProjection(Long anuncioId);

	Anuncio findBySolicitacao(Long solicitacaoId);

	Collection<Anuncio> findAnunciosModuloExterno(Long empresaId, Long candidatoId);

	Collection<Anuncio> findAnunciosSolicitacaoAberta(Long empresaId);

	void enviarAnuncioEmail(Long anuncioId, Long empresaId, String nomeFrom, String emailFrom, String nomeTo, String emailTo) throws AddressException, MessagingException;
}
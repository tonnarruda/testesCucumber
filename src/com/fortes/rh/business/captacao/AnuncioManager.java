package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.web.tags.CheckBox;

public interface AnuncioManager extends GenericManager<Anuncio>
{
	Collection<CheckBox> getEmpresasCheck(Long empresaId) throws Exception;

	String[] montaEmails(String emailAvulso, String[] empresasCheck);

	Collection<Anuncio> findAnunciosSolicitacaoAberta(Long empresaIdExterno);

	void removeBySolicitacao(Long solicitacaoId);

	Anuncio findByIdProjection(Long anuncioId);

	Anuncio findBySolicitacao(Long solicitacaoId);

}
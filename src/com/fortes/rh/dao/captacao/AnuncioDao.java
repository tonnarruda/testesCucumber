package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Anuncio;

public interface AnuncioDao extends GenericDao<Anuncio>
{

	Collection<Anuncio> findAnunciosSolicitacaoAberta(Long empresaIdExterno);

	void removeBySolicitacao(Long solicitacaoId);

	Anuncio findByIdProjection(Long anuncioId);

	Anuncio findBySolicitacao(Long solicitacaoId);
}
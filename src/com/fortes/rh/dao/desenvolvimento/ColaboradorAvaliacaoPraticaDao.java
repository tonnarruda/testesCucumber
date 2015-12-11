package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

public interface ColaboradorAvaliacaoPraticaDao extends GenericDao<ColaboradorAvaliacaoPratica> 
{
	Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId);
	void removeAllByColaboradorId(Long colaboradorId);
}
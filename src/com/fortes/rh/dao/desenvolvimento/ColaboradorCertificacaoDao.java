package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.geral.Colaborador;

public interface ColaboradorCertificacaoDao extends GenericDao<ColaboradorCertificacao> 
{
	Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	Collection<Colaborador> colabNaCertificacaoNaoCertificadosByCertificacaoId(Long certificacaoId);
}

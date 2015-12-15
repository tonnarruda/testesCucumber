package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;

public interface ColaboradorCertificacaoDao extends GenericDao<ColaboradorCertificacao> 
{
	Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	Collection<ColaboradorCertificacao> colabNaCertificacaoNaoCertificados(Long certificacaoId, Long[] areasIds, Long[] estabelecimentosIds);
	Collection<ColaboradorCertificacao> colaboradoresCertificados(Long certificacaoId, Long[] areasIds, Long[] estabelecimentosIds);
}

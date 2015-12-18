package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;

public interface ColaboradorCertificacaoDao extends GenericDao<ColaboradorCertificacao> 
{
	Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	Collection<ColaboradorCertificacao> colaboradoresCertificados(Date dataIni, Date dataFim, char filtroCetificacao, Long[] areasIds, Long[] estabelecimentosIds, Long[] certificacoesIds);
	Collection<ColaboradorCertificacao> colaboradoresCertificadosByColaboradorTurmaId(Long colaboradorTurmaId);
	Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId);
}

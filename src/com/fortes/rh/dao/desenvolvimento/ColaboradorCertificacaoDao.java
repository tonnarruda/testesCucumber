package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

public interface ColaboradorCertificacaoDao extends GenericDao<ColaboradorCertificacao> 
{
	Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	Collection<ColaboradorCertificacao> colaboradoresCertificadosByColaboradorTurmaId(Long colaboradorTurmaId);
	Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId);
	ColaboradorCertificacao colaboradorCertificadoByColaboradorIdAndCertificacaId(Long colaboradorId, Long certificacaoId);
	ColaboradorCertificacao findUltimaCertificacaoByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	Collection<ColaboradorCertificacao> getColaboradorCertificadoFilhas(Long[] colaboradorCertificacaoIds, Long colaboradorId);
	void removeDependencias(Long colaboradorCertificacaoId);
	Collection<ColaboradorTurma> colaboradoresTurmaCertificados(Long colaboradorId, Long certificacaoId);
	ColaboradorCertificacao findByColaboradorTurma(Long colaboradorTurmaId);
	Collection<ColaboradorCertificacao> colaboradoresQueParticipaDoCertificado(Date dataFim, Long certificadoId, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds);
	ColaboradorCertificacao findColaboradorCertificadoInfomandoSeEUltimaCertificacao(Long colaboradorCertificacaoId, Long colaboradorId, Long certificacaoId);
}

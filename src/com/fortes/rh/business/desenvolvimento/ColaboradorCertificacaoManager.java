package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;

public interface ColaboradorCertificacaoManager extends GenericManager<ColaboradorCertificacao>
{
	Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	ColaboradorCertificacao findUltimaCertificacaoByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] colaboradoresIds) throws CloneNotSupportedException;
	Collection<ColaboradorCertificacao> certificaColaborador(Long colaboradorTurmaId, Long colaboradorId, Long certificacaoId, CertificacaoManager certificacaoManager);
	Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId);
	void descertificarColaborador(Long colaboradorCertificacaoId);
	void descertificarColaboradorByColaboradorTurma(Long colaboradorTurmaId, boolean removerColaboradorAvaliacaoPratica);
	void saveColaboradorCertificacao(ColaboradorCertificacao colaboradorCertificacao);
	ColaboradorCertificacao findByColaboradorTurma(Long colaboradorTurmaId);
	ColaboradorCertificacao findColaboradorCertificadoInfomandoSeEUltimaCertificacao(Long colaboradorCertificacaoId, Long colaboradorId, Long certificacaoId);
	Date getMaiorDataDasTurmasDaCertificacao(Long colaboradorCertificacaoId);
	boolean existeColaboradorCertificadoEmUmaTurmaPosterior(Long turmaId, Long colaboradorCertificacaoId);
	Collection<ColaboradorCertificacao> colaboradoresParticipamCertificacao(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds);
	Collection<ColaboradorCertificacao> colaboradoresQueParticipaDoCertificado(Long certificacaoId);
}

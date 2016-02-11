package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;

public interface ColaboradorCertificacaoManager extends GenericManager<ColaboradorCertificacao>
{
	Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	ColaboradorCertificacao findUltimaCertificacaoByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Integer mesesCertificacoesAVencer, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] colaboradoresIds);
	void verificaCertificacaoByColaboradorTurmaId(Long colaboradorTurmaId);
	Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId);
	Collection<ColaboradorCertificacao> colaboradoresCertificadosByColaboradorIdAndCertificacaId(Long colaboradorId, Long certificacaoId);
	void descertificarColaborador(Long colaboradorCertificacaoId, boolean removerColaboradorAvaliacaoPratica);
	void descertificarColaboradorByColaboradorTurma(Long colaboradorTurmaId, boolean removerColaboradorAvaliacaoPratica);
	void saveColaboradorCertificacao(ColaboradorCertificacao colaboradorCertificacao);
	public ColaboradorCertificacao findByColaboradorTurma(Long colaboradorTurmaId);
	public ColaboradorCertificacao findColaboradorCertificadoInfomandoSeEUltimaCertificacao(Long colaboradorCertificacaoId, Long colaboradorId, Long certificacaoId);
	Date getMaiorDataDasTurmasDaCertificacao(Long colaboradorCertificacaoId);
	boolean existeColaboradorCertificadoEmUmaTurmaPosterior(Long turmaId, Long colaboradorCertificacaoId);
}

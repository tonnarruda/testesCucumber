package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;

public interface ColaboradorCertificacaoManager extends GenericManager<ColaboradorCertificacao>
{
	Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	ColaboradorCertificacao findUltimaCertificacaoByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, char filtroCetificacao, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds);
	void verificaCertificacaoByColaboradorTurmaId(Long colaboradorTurmaId);
	Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId);
	Collection<ColaboradorCertificacao> colaboradoresCertificadosByColaboradorIdAndCertificacaId(Long colaboradorId, Long certificacaoId);
}

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
	ColaboradorCertificacao verificaCertificacao(Long colaboradorId, Long certificacaoId);
	ColaboradorCertificacao findUltimaCertificacaoByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	void removeDependenciaDaAvPratica(Long colaboradorCertificacaoId);
	Collection<ColaboradorTurma> colaboradoresTurmaCertificados(Long colaboradorId, Long certificacaoId);
	ColaboradorCertificacao findByColaboradorTurma(Long colaboradorTurmaId);
	Collection<ColaboradorCertificacao> colaboradoresQueParticipamDaCertificacao(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, Long[] certificadosId, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds, boolean colaboradorCertificado);
	ColaboradorCertificacao findColaboradorCertificadoInfomandoSeEUltimaCertificacao(Long colaboradorCertificacaoId, Long colaboradorId, Long certificacaoId);
	Date getMaiorDataDasTurmasDaCertificacao(Long colaboradorCertificacaoId);
	Collection<ColaboradorCertificacao> findColaboradorCertificadoEmUmaTurmaPosterior(Long turmaId, Long colaboradorId);
	Collection<ColaboradorCertificacao> findColaboradorCertificacaoPreRequisito(Long colaboradorCertificacaoId);
	Collection<ColaboradorCertificacao> findColaboradoresCertificados(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, Long[] certificacoesIds, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds);
	Collection<ColaboradorCertificacao> findColaboradoresQueParticipamDaCertificacao(Long[] certificacoesId, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds);
	Collection<ColaboradorCertificacao> findColaboradoresCertificadosENaoCertificados(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, Long certificacaoId, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds, Long[] cursosIds);
}

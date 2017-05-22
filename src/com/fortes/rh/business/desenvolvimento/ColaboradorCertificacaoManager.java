package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.web.tags.CheckBox;

public interface ColaboradorCertificacaoManager extends GenericManager<ColaboradorCertificacao>
{
	Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	ColaboradorCertificacao findUltimaCertificacaoByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, Boolean certificado, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] filtroColaboradoresIds, String situacaoColaborador);
	Collection<ColaboradorCertificacao> certificaColaborador(Long colaboradorTurmaId, Long colaboradorId, Long certificacaoId, CertificacaoManager certificacaoManager);
	Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId);
	void descertificarColaborador(Long colaboradorCertificacaoId);
	void descertificarColaboradorByColaboradorTurma(Long colaboradorTurmaId);
	void saveColaboradorCertificacao(ColaboradorCertificacao colaboradorCertificacao);
	ColaboradorCertificacao findColaboradorCertificadoInfomandoSeEUltimaCertificacao(Long colaboradorCertificacaoId, Long colaboradorId, Long certificacaoId);
	Date getMaiorDataDasTurmasDaCertificacao(Long colaboradorCertificacaoId);
	boolean existeColaboradorCertificadoEmUmaTurmaPosterior(Long turmaId, Long colaboradorCertificacaoId);
	Collection<ColaboradorCertificacao> colaboradoresParticipamCertificacao(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds, Long[] filtroColaboradoresIds, String situacaoColaborador);
	Collection<ColaboradorCertificacao> possuemAvaliacoesPraticasRealizadas(Long certificacaoId, ColaboradorTurmaManager colaboradorTurmaManager);
	Collection<Colaborador> colaboradoresQueParticipamDaCertificacao(Long certificadosId);
	void reprocessaCertificacao(Long certificacaoId, CertificacaoManager certificacaoManager);
	boolean existiColaboradorCertificadoByTurma(Long turmaId);
	boolean isCertificadoByColaboradorTurmaId(Long colaboradorTurmaId);
	void setCertificaçõesNomesInColaboradorTurmas(Collection<ColaboradorTurma> colaboradorTurmas);
	Collection<CheckBox> checkBoxColaboradoresSemCertificacaoDWR(Long empresaId, Long[] areasIds, Long[] estabelecimentosIds, Long[] certificacoesIds, String situacaoColaborador);
	Collection<ColaboradorCertificacao> colaboradoresSemCertificacao(Long empresaId, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds, Long[] certificacoesIds, String situacaoColaborador);
}

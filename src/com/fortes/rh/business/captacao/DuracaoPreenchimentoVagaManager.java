package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;

public interface DuracaoPreenchimentoVagaManager
{
//	DuracaoPreenchimentoVaga save(DuracaoPreenchimentoVaga duracaoPreenchimentoVaga, Date dataAdmissao);
//	public List<DuracaoPreenchimentoVaga> findSomaDiasPreenchimentoSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos);
//	public List<IndicadorDuracaoPreenchimentoVaga> findMotivosPreenchimentoSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId);
	public Collection<IndicadorDuracaoPreenchimentoVaga> gerarIndicadorMotivoPreenchimentoVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao, char dataStatusAprovacaoSolicitacao, boolean indicadorResumido) throws Exception;
	public Collection<IndicadorDuracaoPreenchimentoVaga> gerarIndicadorDuracaoPreenchimentoVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, Long[] solicitacaoIds, boolean considerarContratacaoFutura) throws Exception;
}
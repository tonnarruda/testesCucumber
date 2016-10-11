package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.ExameAnualRelatorio;

public interface RealizacaoExameManager extends GenericManager<RealizacaoExame>
{
	Collection<ExameAnualRelatorio> getRelatorioExame(Long estabelecimentoId, Date dataIni, Date dataFim);
	Collection<RealizacaoExame> findRealizadosByColaborador(Long empresaId, Long colaboradorId);
	void save(SolicitacaoExame solicitacaoExame, String[] selectResultados, String[] observacoes, Date[] datasRealizacaoExames);
	void save(ExameSolicitacaoExame exameSolicitacaoExame, Date solicitacaoExameData, String resultadoExame, String observacao);
	Collection<Long> findIdsBySolicitacaoExame(long solicitacaoExameId);
	void remove(Long[] realizacaoExameIds);
	void marcarResultadoComoNormal(Collection<Long> realizacaoExameIds);
	Integer findQtdRealizados(Long empresaId, Date dataIni, Date dataFim);
	Collection<Exame> findQtdPorExame(Long empresaId, Date dataDe, Date dataAte);
}
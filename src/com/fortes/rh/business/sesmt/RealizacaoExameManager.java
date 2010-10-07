package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.ExameAnualRelatorio;

public interface RealizacaoExameManager extends GenericManager<RealizacaoExame>
{
	Collection<ExameAnualRelatorio> getRelatorioAnual(Long estabelecimentoId, Date data);
	Collection<RealizacaoExame> findRealizadosByColaborador(Long empresaId, Long colaboradorId);
	void save(SolicitacaoExame solicitacaoExame, String[] selectResultados, String[] observacoes);
	Collection<Long> findIdsBySolicitacaoExame(long solicitacaoExameId);
	void remove(Long[] realizacaoExameIds);
	void marcarResultadoComoNormal(Collection<Long> realizacaoExameIds);
	void save(ExameSolicitacaoExame exameSolicitacaoExame, Date solicitacaoExameData, String resultadoExame, String string);
}
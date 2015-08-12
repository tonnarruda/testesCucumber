package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;

public interface ColaboradorAfastamentoDao extends GenericDao<ColaboradorAfastamento>
{
	Integer getCount(Long empresaId, Long afastamentoId, String matriculaBusca, String nomeBusca, Long[] estabelecimentoIds, Date inicio, Date fim);
	Collection<ColaboradorAfastamento> findAllSelect(int page, int pagingSize, boolean isListagemColaboradorAfastamento, Long empresaId, Long afastamentoId, String matriculaBusca, String nomeBusca, Long[] estabelecimentoIds, Long[] areaIds, Date inicio, Date fim, String[] ordenarPor, char afastadoPeloINSS);
	Collection<ColaboradorAfastamento> findByColaborador(Long colaboradorId);
	Collection<Afastamento> findQtdAfastamentosPorMotivo(Long empresaId, Date dataIni, Date dataFim);
	Integer findQtdAfastamentosInss(Long empresaId, Date dataIni, Date dataFim, boolean inss);
	boolean exists(ColaboradorAfastamento colaboradorAfastamento);
	Collection<ColaboradorAfastamento> findRelatorioResumoAfastamentos(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds, Long[] motivosIds, ColaboradorAfastamento colaboradorAfastamento);
	Collection<Absenteismo> countAfastamentosByPeriodo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<Long> afastamentosIds);
	ColaboradorAfastamento findByColaboradorAfastamentoId(Long colaboradorAfastamentoId);
}
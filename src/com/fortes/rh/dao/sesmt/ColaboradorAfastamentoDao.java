package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;

public interface ColaboradorAfastamentoDao extends GenericDao<ColaboradorAfastamento>
{
	Integer getCount(Long empresaId, Long afastamentoId, String nomeBusca, Long[] estabelecimentoIds, Date inicio, Date fim);
	Collection<ColaboradorAfastamento> findAllSelect(int page, int pagingSize, Long empresaId, Long afastamentoId, String nomeBusca, Long[] estabelecimentoIds, Long[] areaIds, Date inicio, Date fim, String ascOuDesc, boolean ordenaColaboradorPorNome, char afastadoPeloINSS);
	Collection<ColaboradorAfastamento> findByColaborador(Long colaboradorId);
}
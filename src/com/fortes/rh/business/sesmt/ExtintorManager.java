package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.relatorio.ManutencaoAndInspecaoRelatorio;

public interface ExtintorManager extends GenericManager<Extintor>
{
	Integer getCount(Long empresaId, String tipoBusca, Integer numeroBusca, char ativo);
	Collection<Extintor> findAllSelect(int page, int pagingSize, Long id, String tipoBusca, Integer numeroBusca, char ativo);
	Collection<Extintor> findByEstabelecimento(Long estabelecimentoId, Boolean ativo);
	String getFabricantes(Long empresaId);
	String getLocalizacoes(Long empresaId);
	Collection<ManutencaoAndInspecaoRelatorio> relatorioManutencaoAndInspecao(Long estabelecimentoId, Date dataVencimento, boolean inspecaoVencida, boolean cargaVencida, boolean testeHidrostaticoVencido)  throws Exception;
	String montaLabelFiltro(Long estabelecimentoId, Date dataVencimento);
}
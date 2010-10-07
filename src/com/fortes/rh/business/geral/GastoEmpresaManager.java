package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.relatorio.GastoRelatorio;
import com.fortes.rh.model.geral.relatorio.GastoRelatorioItem;
import com.fortes.rh.model.geral.relatorio.TotalGastoRelatorio;

public interface GastoEmpresaManager extends GenericManager<GastoEmpresa>
{
	Collection<GastoRelatorio> filtroRelatorio(LinkedHashMap parametros) throws Exception;

	void clonarGastosPorColaborador(String dataClone, GastoEmpresa gastoEmpresa);

	void importarGastosAC(Empresa empresa, String[] gastos, Date data) throws Exception;

	Integer getCount(Long empresaId);

	Collection<GastoEmpresa> findAllList(int page, int pagingSize, Long empresaId);

	public Collection<GastoRelatorioItem> multiplicarGastoRelatorioItem(GastoRelatorioItem gastoRelatorioItem, Date dataIni, Date dataFim);

	public Map<Date,Double> totalizarInvestimentos(Collection<GastoRelatorio> gastoRelatorios);

	public Collection<TotalGastoRelatorio> getTotalInvestimentos(Collection<GastoRelatorio> gastoRelatorios);
}
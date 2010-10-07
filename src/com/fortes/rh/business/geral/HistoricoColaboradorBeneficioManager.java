package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;

public interface HistoricoColaboradorBeneficioManager extends GenericManager<HistoricoColaboradorBeneficio>
{
	public List filtroRelatorioByColaborador(LinkedHashMap filtro);
	public List filtroRelatorioByAreas(LinkedHashMap filtro);
	public Collection<HistoricoColaboradorBeneficio> setHistoricosEntrePeriodos(List historicoColaboradorBeneficios, LinkedHashMap parametros);
	public HistoricoColaboradorBeneficio getHistoricoByColaboradorData(Long id, Date data);
	public HistoricoColaboradorBeneficio getUltimoHistorico(Long colaboradorId);
	public void updateDataAteUltimoHistorico(Long historicoId, Date dataAte);
	public HistoricoColaboradorBeneficio saveHistorico(HistoricoColaboradorBeneficio historicoColaboradorBeneficio) throws Exception;
	public void deleteHistorico(HistoricoColaboradorBeneficio historicoColaboradorBeneficio) throws Exception;
}
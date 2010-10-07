package com.fortes.rh.dao.geral;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;

public interface HistoricoColaboradorBeneficioDao extends GenericDao<HistoricoColaboradorBeneficio>
{
	List filtroRelatorioByAreasEstabelecimentos(LinkedHashMap filtro);
	List filtroRelatorioByColaborador(LinkedHashMap filtro);
	HistoricoColaboradorBeneficio getHistoricoByColaboradorData(Long id, Date data);
	Date getDataUltimoHistorico(Long colaboradorId);
	HistoricoColaboradorBeneficio getUltimoHistorico(Long colaboradorId);
	void updateDataAteUltimoHistorico(Long historicoId, Date dataAte);
}
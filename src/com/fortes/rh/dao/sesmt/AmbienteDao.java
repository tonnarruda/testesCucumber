package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;

public interface AmbienteDao extends GenericDao<Ambiente>
{
	Integer getCount(Long empresaId, HistoricoAmbiente historicoAmbiente);
	Collection<Ambiente> findAmbientes(int page, int pagingSize, Long empresaId, HistoricoAmbiente historicoAmbiente);
	Ambiente findByIdProjection(Long ambienteId);
	int getQtdColaboradorByAmbiente(Long ambienteId, Date data, String sexo, Long funcaoId);
	Collection<Ambiente> findAllByEmpresa(Long empresaId);
	void atualizaDadosParaUltimoHistorico(Long ambienteId);
	void deleteAmbienteSemHistorico() throws Exception;
	Collection<Ambiente> findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(Long empresaId, Long estabelecimentoId, Integer localAmbiente, Date data);
	Collection<Ambiente> findAmbientesPorEstabelecimento(Long[] estabelecimentoIds, Date data);
	public Collection<Ambiente> findByIds(Long empresaId, Collection<Long> ambienteIds, Date data, Long estabelecimentoId, Integer localAmbiente);
}
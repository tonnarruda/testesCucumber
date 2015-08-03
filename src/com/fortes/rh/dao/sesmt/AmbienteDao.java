package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Ambiente;

public interface AmbienteDao extends GenericDao<Ambiente>
{
	Integer getCount(Long empresaId, Ambiente ambiente);
	Collection<Ambiente> findAmbientes(int page, int pagingSize, Long empresaId, Ambiente ambiente);
	Collection<Ambiente> findByEstabelecimento(Long... estabelecimentoIds);
	Ambiente findByIdProjection(Long ambienteId);
	Collection<Ambiente> findByIds(Collection<Long> ambienteIds, Date data, Long estabelecimentoId);
	int getQtdColaboradorByAmbiente(Long ambienteId, Date data, String sexo, Long funcaoId);
	void deleteByEstabelecimento(Long[] estabelecimentoIds) throws Exception;
}
package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;

public interface ExtintorManutencaoDao extends GenericDao<ExtintorManutencao>
{
	public Collection<ExtintorManutencao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean semRetorno, String localizacao);
	public Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean semRetorno);
	public Collection<ExtintorManutencao> findManutencaoVencida(Long estabelecimentoId, Date dataVencimento, String motivo);
	public ExtintorManutencao findByIdProjection(Long extintorManutencaoId);
}
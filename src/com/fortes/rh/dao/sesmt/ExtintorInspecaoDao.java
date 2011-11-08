package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ExtintorInspecao;

public interface ExtintorInspecaoDao extends GenericDao<ExtintorInspecao>
{
	Collection<ExtintorInspecao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade, String localizacao);
	Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade);
	Collection<String> findEmpresasResponsaveisDistinct(Long empresaId);
	Collection<ExtintorInspecao> findInspecoesVencidas(Long estabelecimentoId, Date dataVencimento);
	ExtintorInspecao findByIdProjection(Long extintorInspecaoId);
}
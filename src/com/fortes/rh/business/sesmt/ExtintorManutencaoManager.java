package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.ExtintorManutencao;

public interface ExtintorManutencaoManager extends GenericManager<ExtintorManutencao>
{
	Collection<ExtintorManutencao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean somenteSemRetorno, String localizacao);
	Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean somenteSemRetorno);
	ExtintorManutencao saveOrUpdate(ExtintorManutencao extintorManutencao, String[] servicoChecks)throws Exception;
	Collection<ExtintorManutencao> findManutencaoVencida(Long estabelecimentoId, Date dataVencimento, String motivo);
	void save(ExtintorManutencao extintorManutencao, String[] servicoChecks) throws Exception;
}
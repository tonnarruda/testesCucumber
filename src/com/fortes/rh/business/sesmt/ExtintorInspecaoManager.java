package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.ExtintorInspecao;

public interface ExtintorInspecaoManager extends GenericManager<ExtintorInspecao>
{
	Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade);
	Collection<ExtintorInspecao> findAllSelect(int page, int pagingSize, Long id, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade, String localizacao);
	ExtintorInspecao saveOrUpdate(ExtintorInspecao extintorInspecao, String[] itemChecks) throws Exception;
	String getEmpresasResponsaveis(Long empresaId);
	Collection<ExtintorInspecao> findInspecoesVencidas(Long estabelecimentoId, Date dataVencimento);
}
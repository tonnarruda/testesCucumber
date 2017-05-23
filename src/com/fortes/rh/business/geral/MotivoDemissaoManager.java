package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.MotivoDemissao;

public interface MotivoDemissaoManager extends GenericManager<MotivoDemissao>
{
	Map<String, Object> getParametrosRelatorio(Empresa empresa, Date dataIni, Date dataFim, Long[] estabelecimentosIds, Long[] areasOrganizacionaisIds, Map<String, Object> parametros);

	Collection<MotivoDemissao> findAllSelect(Long empresaId);

	void sincronizar(Long empresaOrigemId, Long empresaDestinoId);

	Collection<MotivoDemissao> findMotivoDemissao(Integer page, Integer pagingSize,	Long empresaId, String motivo, Boolean ativo);
}
package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;

public class MotivoDemissaoManagerImpl extends GenericManagerImpl<MotivoDemissao, MotivoDemissaoDao> implements MotivoDemissaoManager
{
	public Map<String, Object> getParametrosRelatorio(Empresa empresa, Date dataIni, Date dataFim, Map<String, Object> parametros)
	{
		String filtro = DateUtil.formataDiaMesAno(dataIni) + " - " + DateUtil.formataDiaMesAno(dataFim);

		Map<String, Object> parametrosRelatorio = RelatorioUtil.getParametrosRelatorio("Relatório de Desligamento", empresa, filtro);

		parametros.putAll(parametrosRelatorio);

		return parametros;
	}

	public Collection<MotivoDemissao> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId) 
	{
		Collection<MotivoDemissao> motivosDemissaoDeOrigem = getDao().findAllSelect(empresaOrigemId);
		
		for (MotivoDemissao motivoDemissao : motivosDemissaoDeOrigem)
		{
			clonar(motivoDemissao, empresaDestinoId);
			update(motivoDemissao);
		}
	}
	
	private void clonar(MotivoDemissao motivoDemissao, Long empresaDestinoId) 
	{
		motivoDemissao.setId(null);
		motivoDemissao.setEmpresaId(empresaDestinoId);
		
		getDao().save(motivoDemissao);
	}
}
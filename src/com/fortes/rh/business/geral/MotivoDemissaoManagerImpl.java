package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;

@Component
public class MotivoDemissaoManagerImpl extends GenericManagerImpl<MotivoDemissao, MotivoDemissaoDao> implements MotivoDemissaoManager
{
	private EstabelecimentoManager estabelecimentoManager;
	
	@Autowired
	MotivoDemissaoManagerImpl(MotivoDemissaoDao dao) {
		setDao(dao);
	}
	
	public Map<String, Object> getParametrosRelatorio(Empresa empresa, Date dataIni, Date dataFim, Long[] estabelecimentosIds, Long[] areasOrganizacionaisIds, Map<String, Object> parametros)
	{
		String filtro = "Período: " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim);
		filtro += "\nEstabelecimentos: " + estabelecimentoManager.nomeEstabelecimentos(estabelecimentosIds, null);

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

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

}
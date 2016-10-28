package com.fortes.rh.business.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.GastoDao;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GrupoGasto;
import com.fortes.rh.util.LongUtil;

@Component
public class GastoManagerImpl extends GenericManagerImpl<Gasto, GastoDao> implements GastoManager
{
	@Autowired
	GastoManagerImpl(GastoDao dao) {
		setDao(dao);
	}
	
	public Collection<Gasto> getGastosSemGrupo(Long empresaId)
	{
		return getDao().getGastosSemGrupo(empresaId);
	}

	public void agrupar(GrupoGasto grupoGastoAgrupar, String[] gastosCheck) throws Exception
	{
		if(gastosCheck != null && gastosCheck.length > 0)
		{
			getDao().updateGrupoGastoByGastos(grupoGastoAgrupar.getId(), LongUtil.arrayStringToArrayLong(gastosCheck));
		}
	}
	
	public Collection<Gasto> findSemCodigoAC(Long empresaId) {
		return getDao().findSemCodigoAC(empresaId);
	}

	public Collection<Gasto> findGastosDoGrupo(Long id)
	{
		return getDao().findGastosDoGrupo(id);
	}

	public Collection<Gasto> findByEmpresa(Long empresaId)
	{
		return getDao().findByEmpresa(empresaId);
	}

	public Gasto findByIdProjection(Long gastoId)
	{
		return getDao().findByIdProjection(gastoId);
	}
}
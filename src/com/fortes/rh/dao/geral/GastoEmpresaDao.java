package com.fortes.rh.dao.geral;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.GastoEmpresa;

public interface GastoEmpresaDao extends GenericDao<GastoEmpresa>
{
	List filtroRelatorioByAreas(LinkedHashMap parametros);
	List filtroRelatorioByColaborador(LinkedHashMap parametros);
	Integer getCount(Long empresaId);
	Collection<GastoEmpresa> findAllList(int page, int pagingSize, Long empresaId);
}
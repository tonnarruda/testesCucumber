package com.fortes.rh.dao.geral;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.AreaFormacao;

public interface AreaFormacaoDao extends GenericDao<AreaFormacao>
{
	Collection<AreaFormacao> findByCargo(Long id);
	Collection<AreaFormacao> findByFiltro(int page, int pagingSize, AreaFormacao areaformacao);
	public Integer getCount(AreaFormacao areaFormacao);
}
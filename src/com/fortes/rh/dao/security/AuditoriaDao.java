package com.fortes.rh.dao.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.security.Auditoria;

public interface AuditoriaDao extends GenericDao<Auditoria>
{
	public Map findEntidade(Long empresaId);
	public Auditoria projectionFindById(Long id, Long empresaId);
	public Integer getCount(Map parametros, Long empresaId);
	public Collection<Auditoria> list(int page, int pagingSize, Map parametros, Long empresaId);
	public List<String> findOperacoesPeloModulo(String modulo);
}
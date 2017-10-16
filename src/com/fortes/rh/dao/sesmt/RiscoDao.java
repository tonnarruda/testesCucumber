package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Risco;

public interface RiscoDao extends GenericDao<Risco>
{
	List findEpisByRisco(Long riscoId);
	Collection<Risco> findAllSelect(Long empresaId);
	Collection<Risco> listRiscos(int page, int pagingSize, Risco risco);
	Integer getCount(Risco risco);
}
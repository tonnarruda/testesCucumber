package com.fortes.rh.dao.hibernate.sesmt;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RiscoFuncaoDao;
import com.fortes.rh.model.sesmt.RiscoFuncao;

public class RiscoFuncaoDaoHibernate extends GenericDaoHibernate<RiscoFuncao> implements RiscoFuncaoDao
{
	public boolean removeByHistoricoFuncao(Long historicoFuncaoId) 
	{
		String hql = "delete from RiscoFuncao r where r.historicoFuncao.id=:id";
		Query query = getSession().createQuery(hql);
		query.setLong("id", historicoFuncaoId);
		
		return query.executeUpdate() == 1;
	}
}

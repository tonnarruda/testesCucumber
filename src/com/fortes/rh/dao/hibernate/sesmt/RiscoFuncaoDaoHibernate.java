package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RiscoFuncaoDao;
import com.fortes.rh.model.sesmt.Risco;
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

	@SuppressWarnings("unchecked")
	public Collection<Risco> findRiscosByFuncaoData(Long funcaoId, Date data) 
	{
		StringBuilder hql = new StringBuilder("select new Risco(r.id, r.descricao, r.grupoRisco) ");
		hql.append("from RiscoFuncao rf join rf.risco r join rf.historicoFuncao hf ");
		hql.append("where hf.funcao.id = :funcaoId ");
		hql.append("and hf.data = (select max(hf2.data) " +
								"from HistoricoFuncao hf2 " +
								"where hf2.funcao.id = :funcaoId and hf2.data <= :data) ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("funcaoId", funcaoId);
		query.setDate("data", data);
		
		return query.list();
	}

	public void removeByFuncao(Long funcaoId) 
	{
		String hql = "delete from RiscoFuncao rf where rf.historicoFuncao.id in (select id from HistoricoFuncao where funcao_id = :funcaoId) ";
		Query query = getSession().createQuery(hql);
		query.setLong("funcaoId", funcaoId);
		
		query.executeUpdate();
	}
}

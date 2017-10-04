package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.MedicaoRiscoDao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;

@SuppressWarnings("unchecked")
public class MedicaoRiscoDaoHibernate extends GenericDaoHibernate<MedicaoRisco> implements MedicaoRiscoDao
{
	public MedicaoRisco findByIdProjection(Long id) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "medicao");
		criteria.createCriteria("medicao.ambiente", "ambiente", Criteria.LEFT_JOIN);
		criteria.createCriteria("medicao.funcao", "funcao", Criteria.LEFT_JOIN);
		criteria.createCriteria("medicao.riscoMedicaoRiscos", "riscoMedicaoRiscos");
		
		criteria.add(Expression.eq("medicao.id", id));
		
		return (MedicaoRisco) criteria.uniqueResult();
	}
	
	public Collection<MedicaoRisco> findAllSelectByAmbiente(Long empresaId, Long ambienteId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "medicao");
		criteria.createCriteria("medicao.ambiente", "ambiente");
		criteria.createCriteria("ambiente.estabelecimento", "estab");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("medicao.id"), "id");
		p.add(Projections.property("medicao.data"), "data");
		p.add(Projections.property("ambiente.id"), "projectionAmbienteId");
		p.add(Projections.property("ambiente.nome"), "projectionAmbienteNome");
		p.add(Projections.property("estab.nome"), "projectionEstabelecimentoNome");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("ambiente.empresa.id", empresaId));
		
		if (ambienteId != null)
			criteria.add(Expression.eq("ambiente.id", ambienteId));
		
		criteria.addOrder(Order.desc("medicao.data"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public Collection<MedicaoRisco> findAllSelectByFuncao(Long empresaId, Long funcaoId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "medicao");
		criteria.createCriteria("medicao.funcao", "funcao");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("medicao.id"), "id");
		p.add(Projections.property("medicao.data"), "data");
		p.add(Projections.property("funcao.id"), "projectionFuncaoId");
		p.add(Projections.property("funcao.nome"), "projectionFuncaoNome");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("funcao.empresa.id", empresaId));
		
		if (funcaoId != null)
			criteria.add(Expression.eq("funcao.id", funcaoId));
		
		criteria.addOrder(Order.desc("medicao.data"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public Collection<String> findTecnicasUtilizadasDistinct(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(RiscoMedicaoRisco.class, "riscoMedicaoRisco");
		criteria.createCriteria("riscoMedicaoRisco.medicaoRisco", "medicaoRisco");
		criteria.createCriteria("medicaoRisco.ambiente", "ambiente");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("riscoMedicaoRisco.tecnicaUtilizada"), "tecnicaUtilizada");

		criteria.setProjection(p);
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("riscoMedicaoRisco.tecnicaUtilizada"));

		criteria.add(Expression.eq("ambiente.empresa.id", empresaId));
		
		return criteria.list();
	}

	public MedicaoRisco getMedicaoRiscoMedicaoPorFuncao(Long medicaoRiscoId) 
	{
		Criteria criteria = getSession().createCriteria(MedicaoRisco.class, "mr");
		criteria.createCriteria("mr.funcao", "f");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("mr.id"), "id");
		p.add(Projections.property("mr.data"), "data");
		p.add(Projections.property("f.id"), "projectionFuncaoId");
		p.add(Projections.property("f.nome"), "projectionFuncaoNome");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("mr.id", medicaoRiscoId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (MedicaoRisco) criteria.uniqueResult();
	}

	public Collection<RiscoMedicaoRisco> findRiscoMedicaoRiscos(Long medicaoRiscoId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new RiscoMedicaoRisco(r.id, r.descricao, rmr.intensidadeMedida, rmr.tecnicaUtilizada, rmr.descricaoPpra, rmr.descricaoLtcat ) "); 
		hql.append("from RiscoMedicaoRisco as rmr ");
		hql.append("left join rmr.risco as r "); 
		hql.append("left join rmr.medicaoRisco as mr "); 
		hql.append("left join mr.funcao as f "); 
		hql.append("where mr.id = :medicaoRiscoId "); 

		Query query = getSession().createQuery(hql.toString());
		query.setLong("medicaoRiscoId", medicaoRiscoId);

		return query.list();
	}
}

package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
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
		criteria.createCriteria("medicao.ambiente", "ambiente");
		criteria.createCriteria("medicao.riscoMedicaoRiscos", "riscoMedicaoRiscos");
		
		criteria.add(Expression.eq("medicao.id", id));
		
		return (MedicaoRisco) criteria.uniqueResult();
	}
	
	public Collection<MedicaoRisco> findAllSelect(Long empresaId, Long ambienteId) 
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
}

package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.TurmaTipoDespesaDao;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.util.LongUtil;

@Component
@SuppressWarnings("unchecked")
public class TurmaTipoDespesaDaoHibernate extends GenericDaoHibernate<TurmaTipoDespesa> implements TurmaTipoDespesaDao
{

	public Collection<TurmaTipoDespesa> findTipoDespesaTurma(Long turmaId) {
		
		Criteria criteria = getSession().createCriteria(TurmaTipoDespesa.class, "t");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("t.tipoDespesa.id"), "projectionTipoDespesaId");
		p.add(Projections.property("t.despesa"), "despesa");

		criteria.setProjection(p);
		criteria.add(Expression.eq("t.turma.id", turmaId));
	
		criteria.setResultTransformer(new AliasToBeanResultTransformer(TurmaTipoDespesa.class));

		return criteria.list();
	}

	public void removeByTurma(Long turmaId) 
	{
		String hql = "delete TurmaTipoDespesa t where t.turma.id = :turmaId";

		Query query = getSession().createQuery(hql);

		query.setLong("turmaId", turmaId);
		query.executeUpdate();
	}

	public Collection<TipoDespesa> somaDespesasPorTipo(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds) 
	{
		Criteria criteria = getSession().createCriteria(TurmaTipoDespesa.class,"ttd");
		criteria.createCriteria("ttd.turma", "t");
		criteria.createCriteria("ttd.tipoDespesa", "td");

		ProjectionList p = Projections.projectionList().create();
        
		p.add(Projections.alias(Projections.sum("ttd.despesa"), "totalDespesas"));
		p.add(Projections.alias(Projections.groupProperty("td.id"), "id"));
		p.add(Projections.alias(Projections.groupProperty("td.descricao"), "descricao"));
        
        criteria.setProjection(p);
        
        criteria.add(Expression.ge("t.dataPrevIni", dataIni));
        criteria.add(Expression.le("t.dataPrevFim", dataFim));
        
        if (LongUtil.arrayIsNotEmpty(empresaIds))
        	criteria.add(Expression.in("t.empresa.id", empresaIds));
        
        if (LongUtil.arrayIsNotEmpty(cursoIds))
        	criteria.add(Expression.in("t.curso.id", cursoIds));
        
        criteria.add(Expression.eq("t.realizada", true));
        
        criteria.addOrder(Order.desc("totalDespesas"));
        
        criteria.setResultTransformer(new AliasToBeanResultTransformer(TipoDespesa.class));

        return  criteria.list();
	}
}

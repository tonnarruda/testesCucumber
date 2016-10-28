/*
 * autor: Moesio Medeiros
 * data: 05/06/2006
 * Requisito: RFA007
 */
package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.BeneficioDao;
import com.fortes.rh.model.geral.Beneficio;

@Component
public class BeneficioDaoHibernate extends GenericDaoHibernate<Beneficio> implements BeneficioDao
{
	@SuppressWarnings("unchecked")
	public Beneficio findBeneficioById(Long id)
	{
		Criteria criteria = getSession().createCriteria(Beneficio.class, "b");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("b.id"), "id");
		p.add(Projections.property("b.nome"), "nome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("b.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Beneficio.class));

		Beneficio beneficio = new Beneficio();
		Collection<Beneficio> beneficios = criteria.list();

		if(beneficios != null && !beneficios.isEmpty())
			beneficio = (Beneficio) beneficios.toArray()[0];

		return beneficio;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getBeneficiosByHistoricoColaborador(Long historicoId)
	{
		String queryHQL = "select b.id from HistoricoColaboradorBeneficio hcb" +
						  " left join hcb.beneficios b" +
						  " where hcb.id=:historicoId order by b.nome";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("historicoId", historicoId);

		return query.list();
	}
}
package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.HistoricoBeneficioDao;
import com.fortes.rh.model.geral.HistoricoBeneficio;

@Component
public class HistoricoBeneficioDaoHibernate extends GenericDaoHibernate<HistoricoBeneficio> implements HistoricoBeneficioDao
{

	public HistoricoBeneficio findByHistoricoId(Long id)
	{
		Criteria criteria = getSession().createCriteria(HistoricoBeneficio.class,"hb");
		criteria.createCriteria("hb.beneficio", "b", Criteria.LEFT_JOIN);
		criteria.createCriteria("b.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("hb.id"), "id");
		p.add(Projections.property("hb.paraColaborador"), "paraColaborador");
		p.add(Projections.property("hb.paraDependenteDireto"), "paraDependenteDireto");
		p.add(Projections.property("hb.paraDependenteIndireto"), "paraDependenteIndireto");
		p.add(Projections.property("hb.valor"), "valor");
		p.add(Projections.property("hb.data"), "data");
		p.add(Projections.property("b.id"), "projectionBeneficioId");
		p.add(Projections.property("b.nome"), "projectionBeneficioNome");
		p.add(Projections.property("e.id"), "projectionEmpresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("hb.id", id));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoBeneficio.class));

		return (HistoricoBeneficio) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<HistoricoBeneficio> getHistoricos()
	{
		Criteria criteria = getSession().createCriteria(HistoricoBeneficio.class,"hb");
		criteria.createCriteria("hb.beneficio", "b", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("hb.id"), "id");
		p.add(Projections.property("hb.paraColaborador"), "paraColaborador");
		p.add(Projections.property("hb.paraDependenteDireto"), "paraDependenteDireto");
		p.add(Projections.property("hb.paraDependenteIndireto"), "paraDependenteIndireto");
		p.add(Projections.property("hb.valor"), "valor");
		p.add(Projections.property("hb.data"), "data");
		p.add(Projections.property("b.nome"), "projectionBeneficioNome");

		criteria.setProjection(p);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoBeneficio.class));

		return criteria.list();
	}
}
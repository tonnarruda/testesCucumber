package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ParticipanteCursoLntDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.dicionario.StatusRetornoAC;

public class ParticipanteCursoLntDaoHibernate extends GenericDaoHibernate<ParticipanteCursoLnt> implements ParticipanteCursoLntDao
{
	@SuppressWarnings("unchecked")
	public Collection<ParticipanteCursoLnt> findByLnt(Long lntId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "plnt");
		criteria.createCriteria("plnt.cursoLnt", "clnt");
		criteria.createCriteria("clnt.lnt", "lnt");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("plnt.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("lnt.id", lntId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<ParticipanteCursoLnt> findByLntIdAndAreasParticipantesIdsAndEmpresasIds(Long lntId, Long[] areasParticipantesIds, Long[] empresasIds, String[] order) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.cursoLnt", "cu");
		criteria.createCriteria("p.colaborador", "c");
		criteria.createCriteria("p.areaOrganizacional", "a");
		criteria.createCriteria("c.empresa", "e");
		criteria.createCriteria("a.empresa", "ae");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.estabelecimento", "est");
		criteria.createCriteria("a.responsavel", "resp", Criteria.LEFT_JOIN);
		criteria.createCriteria("a.coResponsavel", "coResp", Criteria.LEFT_JOIN);
		
		criteria.setProjection(projectionsParticipantes());
		criteria.add(Subqueries.propertyEq("hc.data", detachedUltimoHistoricoColaborador()));
		criteria.add(Expression.eq("cu.lnt.id", lntId));
		
		if(areasParticipantesIds != null && areasParticipantesIds.length > 0)
			criteria.add(Expression.in("a.id", areasParticipantesIds));
		
		if(empresasIds != null && empresasIds.length > 0)
			criteria.add(Expression.in("e.id", empresasIds));

		if(order != null)
			for(int i = 0; i < order.length; i++) 
				criteria.addOrder(Order.asc(order[i]));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ParticipanteCursoLnt> findByCursoLntId(Long cursoLntId) {
		DetachedCriteria subCriteria = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
														.setProjection(Projections.max("hc2.data"))
														.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"))
														.add(Restrictions.leProperty("hc2.data", "l.dataInicio"))
														.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
		
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.cursoLnt", "cu");
		criteria.createCriteria("p.colaborador", "c");
		criteria.createCriteria("p.areaOrganizacional", "a");
		criteria.createCriteria("c.empresa", "e");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.estabelecimento", "est");
		criteria.createCriteria("cu.lnt", "l");
		
		criteria.setProjection(projectionsParticipantes());
		
		criteria.add(Expression.eq("cu.id", cursoLntId));
		criteria.add(Subqueries.propertyEq("hc.data", subCriteria));
		
		criteria.addOrder(Order.asc("a.nome"));
		criteria.addOrder(Order.asc("cu.nomeNovoCurso"));
		criteria.addOrder(Order.asc("c.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	private DetachedCriteria detachedUltimoHistoricoColaborador() {
		return DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"))
				.add(Restrictions.le("hc2.data", new Date()))
				.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
	}

	private ProjectionList projectionsParticipantes() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("cu.id"), "cursoLntId");
		p.add(Projections.property("cu.nomeNovoCurso"), "cursoLntNome");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.matricula"), "colaboradorMatricula");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("a.id"), "areaId");
		p.add(Projections.property("a.nome"), "areaNomeFolha");
		p.add(Projections.sqlProjection("monta_familia_area(a3_.id) as areaNome", new String[] {"areaNome"}, new Type[] {Hibernate.TEXT}), "areaNome");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nome"), "empresaNome");
		p.add(Projections.property("est.nome"), "estabelecimentoNome");
		
		return p;
	}
	
}

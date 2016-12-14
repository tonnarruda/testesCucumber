package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.CursoLntDao;
import com.fortes.rh.model.desenvolvimento.CursoLnt;

public class CursoLntDaoHibernate extends GenericDaoHibernate<CursoLnt> implements CursoLntDao
{
	@SuppressWarnings("unchecked")
	public Collection<CursoLnt> findByLntId(Long lntId) {
		
		Criteria criteria = getSession().createCriteria(getEntityClass(),"cl");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cl.id"), "id");
		p.add(Projections.property("cl.nomeNovoCurso"), "nomeNovoCurso");
		p.add(Projections.property("cl.cargaHoraria"), "cargaHoraria");
		p.add(Projections.property("cl.conteudoProgramatico"), "conteudoProgramatico");
		p.add(Projections.property("cl.custo"), "custo");
		p.add(Projections.property("cl.justificativa"), "justificativa");
		p.add(Projections.property("cl.curso.id"), "cursoId");
		p.add(Projections.property("cl.lnt.id"), "lntId");
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("(select case when ");
		stringBuffer.append("(select count(*) from participantecursolnt where cursolnt_id = this_.id) = ");
		stringBuffer.append("(select count(distinct(pcl.colaborador_id)) from participantecursolnt pcl inner join colaboradorTurma ct on ct.cursolnt_id = pcl.cursolnt_id and pcl.colaborador_id = ct.colaborador_Id where pcl.cursolnt_id = this_.id) ");
		stringBuffer.append("then false else true end) as existePerticipanteASerRelacionado ");
		
		p.add(Projections.sqlProjection(stringBuffer.toString(), new String[] {"existePerticipanteASerRelacionado"}, new Type[] {Hibernate.BOOLEAN}), "existePerticipanteASerRelacionado");
		criteria.setProjection(Projections.distinct(p));
		
		criteria.add(Expression.eq("cl.lnt.id", lntId));
		criteria.addOrder(Order.asc("cl.nomeNovoCurso"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(CursoLnt.class));
		
		return criteria.list();
	}
	
	public Boolean existePerticipanteASerRelacionado(Long cursoLntId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select case when ");
		sql.append("(select count(*) from participantecursolnt where cursolnt_id = :cursoLntId) = ");
		sql.append("(select count(distinct(pcl.colaborador_id)) from participantecursolnt pcl inner join colaboradorTurma ct on ct.cursolnt_id = pcl.cursolnt_id and pcl.colaborador_id = ct.colaborador_Id where pcl.cursolnt_id = :cursoLntId) ");
		sql.append("then false else true end ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		query.setLong("cursoLntId", cursoLntId);		
		
		return (Boolean) query.uniqueResult();
	}

	public void updateNomeNovoCurso(Long cursoId, String nomeNovoCurso) {
		String queryHQL = "update CursoLnt set nomeNovoCurso = :nomeNovoCurso where curso.id = :cursoId ";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("cursoId", cursoId);
		query.setString("nomeNovoCurso", nomeNovoCurso);
		query.executeUpdate();
	}

	public void removeCursoId(Long cursoId) {
		String queryHQL = "update CursoLnt set curso.id = null where curso.id = :cursoId ";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("cursoId", cursoId);
		query.executeUpdate();
	}
}

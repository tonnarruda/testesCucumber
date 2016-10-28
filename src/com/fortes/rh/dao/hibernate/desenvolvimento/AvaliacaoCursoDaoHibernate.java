package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

@Component
public class AvaliacaoCursoDaoHibernate extends GenericDaoHibernate<AvaliacaoCurso> implements AvaliacaoCursoDao
{
	@SuppressWarnings("unchecked")
	public Collection<AvaliacaoCurso> findByCursos(Long[] cursosIds)
	{
		Criteria criteria = getSession().createCriteria(AvaliacaoCurso.class, "a");
		criteria.createCriteria("a.cursos", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.tipo"), "tipo");
		p.add(Projections.property("a.minimoAprovacao"), "minimoAprovacao");
		criteria.setProjection(Projections.distinct(p));
		
		if (cursosIds != null && cursosIds.length > 0)
			criteria.add(Expression.in("c.id", cursosIds));
		
		criteria.addOrder(Order.asc("a.titulo"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public Integer countAvaliacoes(Long id, String wherePor)
	{
		Criteria criteria = null;
		if(wherePor.equalsIgnoreCase("T"))
		{
			criteria = getSession().createCriteria(Turma.class,"t");
			criteria.createCriteria("t.curso", "c");			
		}
		else
			criteria = getSession().createCriteria(Curso.class,"c");
		
		criteria.createCriteria("c.avaliacaoCursos", "ac");
		
		criteria.setProjection(Projections.rowCount());
		if(wherePor.equalsIgnoreCase("T"))
			criteria.add(Expression.eq("t.id", id));
		else
			criteria.add(Expression.eq("c.id", id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Integer) criteria.uniqueResult();
	}

	public Integer countAvaliacoes(Long[] cursoIds)
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");
		
		criteria.createCriteria("c.avaliacaoCursos", "ac");
		
		criteria.setProjection(Projections.rowCount());
		criteria.add(Expression.in("c.id", cursoIds));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Integer) criteria.uniqueResult();
	}
	
	public Collection<AvaliacaoCurso> buscaFiltro(String titulo)
	{
		Criteria criteria = getSession().createCriteria(AvaliacaoCurso.class, "a");
		
		if (!StringUtils.isEmpty(titulo))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.titulo) ilike  normalizar(?)", "%" + titulo + "%", StandardBasicTypes.STRING));
		
		criteria.addOrder(Order.asc("a.titulo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}
	
	public boolean existeAvaliacaoCursoRespondida(Long avaliacaoCursoId, char tipoAvaliacaoCurso) {
		
		Criteria criteria = null;
		
		if(tipoAvaliacaoCurso == TipoAvaliacaoCurso.AVALIACAO)
			criteria = getSession().createCriteria(ColaboradorQuestionario.class,"entidade");
		else
			criteria = getSession().createCriteria(AproveitamentoAvaliacaoCurso.class,"entidade");
			
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.count("id"));
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("entidade.avaliacaoCurso.id", avaliacaoCursoId));
		
		return ((Integer) criteria.uniqueResult()) > 0;
	}
}
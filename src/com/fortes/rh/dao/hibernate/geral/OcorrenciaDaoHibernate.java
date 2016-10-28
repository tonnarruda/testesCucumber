package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.OcorrenciaDao;
import com.fortes.rh.model.geral.Ocorrencia;

@Component
@SuppressWarnings("unchecked")
public class OcorrenciaDaoHibernate extends GenericDaoHibernate<Ocorrencia> implements OcorrenciaDao
{
	public boolean removeByCodigoAC(String codigo, Long empresaId)
	{
		String hql = "delete from Ocorrencia o where o.codigoAC = :codigo and o.empresa.id = :id";
		Query query = getSession().createQuery(hql);
		query.setString("codigo", codigo);
		query.setLong("id", empresaId);//tem que ser por ID, ta correto(CUIDADO: caso mude tem que verificar o grupoAC)
		return query.executeUpdate() == 1;
	}

	public Ocorrencia findByCodigoAC(String codigo, String codigoEmpresa, String grupoAC)
	{
		String hql = "select o from Ocorrencia o join o.empresa emp where o.codigoAC = :codigo and emp.codigoAC = :empCodigo and emp.grupoAC = :grupoAC ";
		Query query = getSession().createQuery(hql);
		query.setString("codigo", codigo);
		query.setString("empCodigo", codigoEmpresa);
		query.setString("grupoAC", grupoAC);
		
		return (Ocorrencia) query.uniqueResult();
	}
	
	public Collection<Ocorrencia> findSincronizarOcorrenciaInteresse(Long empresaOrigemId) 
	{
		Criteria criteria = getSession().createCriteria(Ocorrencia.class, "o");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("o.id"), "id");
		p.add(Projections.property("o.descricao"), "descricao");
		p.add(Projections.property("o.pontuacao"), "pontuacao");
		p.add(Projections.property("o.codigoAC"), "codigoAC");
		p.add(Projections.property("o.integraAC"), "integraAC");

		criteria.setProjection(p);
		criteria.add(Expression.eq("o.empresa.id", empresaOrigemId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ocorrencia.class));

		return criteria.list();
	}
	
	public Collection<Ocorrencia> findAllSelect(Long[] empresaIds)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "o");
		criteria.createCriteria("o.empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("o.id"), "id");
		p.add(Projections.property("o.descricao"), "descricao");
		p.add(Projections.property("e.id"), "projectionEmpresaId");
		p.add(Projections.property("e.nome"), "projectionEmpresaNome");
		criteria.setProjection(p);
		
		if(empresaIds != null && empresaIds.length > 0)
			criteria.add(Expression.in("e.id", empresaIds));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.addOrder(Order.asc("o.descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public Collection<Ocorrencia> findOcorrenciasComAbseteismo(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "o");
		criteria.createCriteria("o.empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("o.id"), "id");
		p.add(Projections.property("o.descricao"), "descricao");
		criteria.setProjection(p);
		
		if(empresaId != null)
			criteria.add(Expression.eq("e.id", empresaId));

		criteria.add(Expression.eq("o.absenteismo", true));
		
		criteria.addOrder(Order.asc("o.descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public Collection<Ocorrencia> find(int page, int pagingSize, Ocorrencia ocorrencia, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "o");

		montaConsulta(criteria, ocorrencia, empresaId);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("o.id"), "id");
		p.add(Projections.property("o.descricao"), "descricao");
		p.add(Projections.property("o.pontuacao"), "pontuacao");
		p.add(Projections.property("o.absenteismo"), "absenteismo");
		p.add(Projections.property("o.codigoAC"), "codigoAC");
		p.add(Projections.property("o.integraAC"), "integraAC");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("o.descricao"));

		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	private void montaConsulta(Criteria criteria, Ocorrencia ocorrencia, Long empresaId) 
	{
		criteria.add(Expression.eq("o.empresa.id", empresaId));

		if(ocorrencia != null && StringUtils.isNotEmpty(ocorrencia.getDescricao()))
			criteria.add(Expression.ilike("o.descricao", "%"+ ocorrencia.getDescricao() +"%"));
	}

	public Integer getCount(Ocorrencia ocorrencia, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "o");
		criteria.setProjection(Projections.rowCount());
		
		montaConsulta(criteria, ocorrencia, empresaId);
		
		return (Integer) criteria.uniqueResult();
	}

	public Collection<Ocorrencia> findSemCodigoAC(Long empresaId, Boolean integraAC) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "o");
		criteria.createCriteria("o.empresa", "e");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("o.id"), "id");
		p.add(Projections.property("o.descricao"), "descricao");
		p.add(Projections.property("e.nome"), "projectionEmpresaNome");

		criteria.setProjection(p);
		
		criteria.add(Expression.isNull("o.codigoAC"));
		
		if(integraAC != null)
			criteria.add(Expression.eq("o.integraAC", integraAC));
		
		criteria.add(Expression.eq("e.id", empresaId));

		criteria.addOrder(Order.asc("e.nome"));
		criteria.addOrder(Order.asc("o.descricao"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();	
	}
	
	public Collection<Ocorrencia> findComCodigoAC(Long empresaId) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "o");
		criteria.createCriteria("o.empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("o.codigoAC"), "codigoAC");
		p.add(Projections.property("o.descricao"), "descricao");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.isNotNull("o.codigoAC"));
		criteria.add(Expression.eq("o.integraAC", true));
		
		criteria.add(Expression.eq("e.id", empresaId));
		
		criteria.addOrder(Order.asc("o.descricao"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();	
	}

}
package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("unchecked")
public class CertificacaoDaoHibernate extends GenericDaoHibernate<Certificacao> implements CertificacaoDao
{
	public Collection<Certificacao> findAllSelect(Long empresaId)
	{
		Criteria criteria = createCriteria(empresaId);
		return criteria.list();
	}
	
	public Collection<Certificacao> findAllSelectNotCertificacaoId(Long empresaId, Long certificacaoId)
	{
		Criteria criteria = createCriteria(empresaId);
		
		if(certificacaoId != null)
			criteria.add(Expression.ne("c.id", certificacaoId));
		
		return criteria.list();
	}

	private Criteria createCriteria(Long empresaId) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria;
	}
	
	public Collection<Certificacao> findAllSelect(Integer page, Integer pagingSize, Long empresaId, String nomeBusca)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresaId));
	
		if(pagingSize != 0)
        {
        	criteria.setFirstResult(((page - 1) * pagingSize));
        	criteria.setMaxResults(pagingSize);
        }
		
		if(StringUtils.isNotBlank(nomeBusca))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + nomeBusca + "%", Hibernate.STRING));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Certificacao> findByFaixa(Long faixaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.faixaSalarials", "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("f.id", faixaId));
		criteria.addOrder(Order.asc("c.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<MatrizTreinamento> findMatrizTreinamento(Collection<Long> faixaIds)
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento(cert.id, cert.nome, faixa.nome, cargo.nome, curso.nome, curso.id) ");
		hql.append("from Certificacao cert ");
		hql.append("left join cert.cursos curso ");
		hql.append("left join cert.faixaSalarials faixa ");
		hql.append("left join faixa.cargo cargo ");
		hql.append("where faixa.id in (:faixasIds) ");

		hql.append("group by cert.id, cert.nome, faixa.nome, cargo.nome, curso.nome, curso.id ");
		hql.append("order by cargo.nome, faixa.nome, cert.nome, curso.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("faixasIds", faixaIds, Hibernate.LONG);

		return query.list();
	}

	public Certificacao findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.id", id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (Certificacao) criteria.uniqueResult();
	}

	public Integer getCount(Long empresaId, String nomeBusca) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.setProjection(Projections.rowCount());
		
		criteria.add(Expression.eq("c.empresa.id", empresaId));
	
		if(StringUtils.isNotBlank(nomeBusca))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + nomeBusca + "%", Hibernate.STRING));
		
		return (Integer) criteria.uniqueResult();
	}

	public void deleteByFaixaSalarial(Long[] faixaIds) throws Exception {
		if(faixaIds != null && faixaIds.length > 0)
		{
			String[] sql = new String[] {"delete from faixasalarial_certificacao where  faixasalarials_id in ("+StringUtils.join(faixaIds, ",")+");"};
			JDBCConnection.executeQuery(sql);
		}
	}

	public Collection<Colaborador> findColaboradoresNaCertificacoa(Long certificacaoId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("WITH cursoCertificados AS ( ");
		sql.append("		select distinct cc.cursos_id as cusosIds from certificacao  c ");
		sql.append("		inner join certificacao_avaliacaopratica cap on cap.certificacao_id = c.id ");
		sql.append("		inner join certificacao_curso cc on cc.certificacaos_id = c.id ");
		sql.append("		where c.id = :certificacaoId ");
		sql.append("		) ");
		sql.append("select col.id, col.nome from colaboradorturma  ct ");
		sql.append("inner join colaborador col on col.id = ct.colaborador_id ");
		sql.append("inner join turma t on t.id = ct.turma_id ");
		sql.append("inner join cursoCertificados on ct.curso_id = cursoCertificados.cusosIds ");
		sql.append("group by col.id, col.nome ");
		sql.append("having count(ct.curso_id) >= (select count(*) from cursoCertificados) ");
		sql.append("order by col.id, col.nome ");
		
		Query q = getSession().createSQLQuery(sql.toString());
		q.setLong("certificacaoId", certificacaoId);
		Collection<Object[]> resultado = q.list();
		
		Collection<Colaborador> lista = new ArrayList<Colaborador>();
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			lista.add(new Colaborador((String) res[1],((BigInteger)res[0]).longValue()));
		}

		return lista;	
	}
}
package com.fortes.rh.dao.hibernate.sesmt;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ComissaoMembroDao;
import com.fortes.rh.model.dicionario.TipoMembroComissao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;

@Component
@SuppressWarnings("unchecked")
public class ComissaoMembroDaoHibernate extends GenericDaoHibernate<ComissaoMembro> implements ComissaoMembroDao
{
	public Collection<ComissaoMembro> findByComissaoPeriodo(Long[] comissaoPeriodoIds)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria = criteria.createCriteria("c.colaborador", "colab", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("colab.nome"), "projectionColaboradorNome");
		p.add(Projections.property("colab.id"), "projectionColaboradorId");
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.funcao"), "funcao");
		p.add(Projections.property("c.tipo"), "tipo");
		p.add(Projections.property("c.comissaoPeriodo.id"), "projectionComissaoPeriodoId");
		p.add(Projections.property("c.colaborador.id"), "projectionColaboradorId");
		criteria.setProjection(p);

		criteria.add(Expression.in("c.comissaoPeriodo.id", comissaoPeriodoIds));

		criteria.addOrder(Order.asc("c.comissaoPeriodo.id"));
		criteria.addOrder(Order.asc("colab.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public void updateFuncaoETipo(Long id, String funcao, String tipo)
	{
		String hql = "update ComissaoMembro set funcao = :funcao, tipo = :tipo where id = :id";

		Query query = getSession().createQuery(hql);
		query.setLong("id", id);
		query.setString("funcao", funcao);
		query.setString("tipo", tipo);

		query.executeUpdate();
	}

	public void removeByComissaoPeriodo(Long[] comissaoPeriodoIds)
	{
		if (comissaoPeriodoIds != null && comissaoPeriodoIds.length >0)
		{
			String hql = "delete from ComissaoMembro c where c.comissaoPeriodo.id in (:comissaoPeriodoId)";
			Query query = getSession().createQuery(hql);
			query.setParameterList("comissaoPeriodoId",comissaoPeriodoIds,StandardBasicTypes.LONG);
			query.executeUpdate();
		}
	}

	public Collection<Colaborador> findColaboradorByComissao(Long comissaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cm");
		criteria.createCriteria("cm.colaborador", "co");
		criteria.createCriteria("cm.comissaoPeriodo", "cp");
		criteria.createCriteria("cp.comissao", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("co.id")), "id");
		p.add(Projections.property("co.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", comissaoId));
		criteria.addOrder(Order.asc("co.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Collection<ComissaoMembro> findDistinctByComissaoPeriodo(Long comissaoPeriodoId, Date dataLimiteParaDesligados)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cm");
		criteria.createCriteria("cm.colaborador", "co");
		criteria.createCriteria("cm.comissaoPeriodo", "cp");
		criteria.createCriteria("cp.comissao", "c");

		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.distinct(Projections.property("co.id")), "id");
		
		p.add(Projections.property("co.id"), "projectionColaboradorId");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("cm.tipo"), "tipo");
		p.add(Projections.property("cm.funcao"), "funcao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cp.id", comissaoPeriodoId));
		
		if(dataLimiteParaDesligados != null)
			criteria.add(Restrictions.or(Expression.isNull("co.dataDesligamento"), Expression.gt("co.dataDesligamento", dataLimiteParaDesligados)));
		
		criteria.addOrder(Order.asc("co.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ComissaoMembro.class));
		return criteria.list();
	}

	public Collection<ComissaoMembro> findByComissao(Long comissaoId, String tipoMembroComissao) {
		
		StringBuilder hql = new StringBuilder("select new ComissaoMembro(colaborador.nome, colaborador.nomeComercial, cm.tipo, cm.funcao) ");
		hql.append("from ComissaoMembro cm ");
		hql.append("join cm.colaborador colaborador ");
		hql.append("join cm.comissaoPeriodo cp ");
		hql.append("join cp.comissao comissao ");
		hql.append("where comissao.id = :comissaoId ");
		hql.append("and cp.aPartirDe = (select max(cp2.aPartirDe) from ComissaoPeriodo cp2 ");
		hql.append("				where cp2.comissao.id = comissao.id ");
		hql.append("				and cp2.aPartirDe <= :hoje ) ");
		
		if (StringUtils.isNotBlank(tipoMembroComissao))
			hql.append("and cm.tipo like :tipoMembroComissao ");
		
		hql.append("order by cm.tipo, colaborador.nome");
		
		Query query = getSession().createQuery(hql.toString());

		if (StringUtils.isNotBlank(tipoMembroComissao))
			query.setString("tipoMembroComissao", tipoMembroComissao);
		
		query.setLong("comissaoId", comissaoId);
		query.setDate("hoje", new Date());
		
		return query.list();
	}
	
	
	public Collection<Comissao> findComissaoByColaborador(Long colaboradorId) {
		
		StringBuilder hql = new StringBuilder("select new Comissao(comissao.dataIni, comissao.dataFim) ");
		hql.append("from ComissaoMembro cm ");
		hql.append("join cm.comissaoPeriodo cp ");
		hql.append("join cp.comissao comissao ");
		hql.append("where cm.colaborador.id = :colaboradorId ");
		hql.append("and cp.aPartirDe = (select max(cp2.aPartirDe) from ComissaoPeriodo cp2 ");
		hql.append("				where cp2.comissao.id = comissao.id ");
		hql.append("				and cp2.aPartirDe <= :hoje ) ");
		hql.append("and cm.tipo = :tipo ");
		
		Query query = getSession().createQuery(hql.toString());

		query.setLong("colaboradorId", colaboradorId);
		query.setDate("hoje", new Date());
		query.setString("tipo", TipoMembroComissao.ELEITO);
		
		return query.list();
	}
	

	public Collection<ComissaoMembro> findByColaborador(Long colaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria = criteria.createCriteria("c.colaborador", "colab");
		criteria = criteria.createCriteria("c.comissaoPeriodo", "cp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("colab.nome"), "projectionColaboradorNome");
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.funcao"), "funcao");
		p.add(Projections.property("c.tipo"), "tipo");
		p.add(Projections.property("cp.aPartirDe"), "projectionComissaoPeriodoAPartirDe");
		p.add(Projections.property("c.colaborador.id"), "projectionColaboradorId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("colab.id", colaboradorId));

		criteria.addOrder(Order.asc("cp.aPartirDe"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Colaborador> findColaboradoresNaComissao(Long comissaoId) 
	{
		StringBuilder hql = new StringBuilder("select distinct new Colaborador(co.nome, co.id) ");
		hql.append("from ComissaoMembro cm ");
		hql.append("left join cm.comissaoPeriodo cp ");
		hql.append("left join cm.colaborador co ");
		hql.append("where cp.comissao.id = :comissaoId ");
		hql.append("and cp.aPartirDe = (select max(cp2.aPartirDe) from ComissaoPeriodo cp2 ");
		hql.append("				where cp2.comissao.id = :comissaoId )");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("comissaoId", comissaoId);
		
		return query.list();
	}

	public Map<Long, Date> colaboradoresComEstabilidade(Long[] colaboradoresIds) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("	cme.colaborador_id, ");
		sql.append("	c.dataFim  + interval '1 year' as data ");
		sql.append("	 ");
		sql.append("from ");
		sql.append("	ComissaoMembro cme  ");
		sql.append("inner join ");
		sql.append("	ComissaoPeriodo cpe  ");
		sql.append("		on cme.comissaoPeriodo_id=cpe.id  ");
		sql.append("inner join ");
		sql.append("	Comissao c  ");
		sql.append("		on cpe.comissao_id=c.id  ");
		sql.append("where ");
		sql.append("	cme.colaborador_id in (:colaboradoresIds) ");
		sql.append("	and cpe.aPartirDe=( ");
		sql.append("		select ");
		sql.append("			max(cpe2.aPartirDe)  ");
		sql.append("		from ");
		sql.append("			ComissaoPeriodo cpe2 ");
		sql.append("		where ");
		sql.append("			cpe2.comissao_id=c.id  ");
		sql.append("			and cpe2.aPartirDe<=current_date ");
		sql.append("	) ");
		sql.append("and c.dataFim=( ");
		sql.append("		select ");
		sql.append("			max(c3.dataFim)  ");
		sql.append("		from ");
		sql.append("			ComissaoMembro cme3 ");
		sql.append("		inner join ");
		sql.append("			ComissaoPeriodo cpe3  ");
		sql.append("				on cme3.comissaoPeriodo_id=cpe3.id  ");
		sql.append("		inner join ");
		sql.append("			Comissao c3 ");
		sql.append("				on cpe3.comissao_id=c3.id                 ");
		sql.append("		where ");
		sql.append("			cme3.colaborador_id = cme.colaborador_id ");
		sql.append("	 	)  ");
		sql.append("	  ");
		sql.append("	and cme.tipo=:tipo ");
		sql.append("	and (c.dataFim + interval '1 year') >= current_date ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		query.setParameterList("colaboradoresIds", colaboradoresIds);
		query.setString("tipo", TipoMembroComissao.ELEITO);
		
		Collection<Object[]> resultado = query.list();
		Map<Long, Date> map = new HashMap<Long, Date>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			map.put(((BigInteger)res[0]).longValue(), ((Date)res[1]));
		}
		
		return map;
	}
}
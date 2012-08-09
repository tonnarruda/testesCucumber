package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoPresencaDao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;

/**
 * @author Tiago Lopes
 *
 */
@SuppressWarnings("unchecked")
public class ComissaoReuniaoPresencaDaoHibernate extends GenericDaoHibernate<ComissaoReuniaoPresenca> implements ComissaoReuniaoPresencaDao
{
	public Collection<ComissaoReuniaoPresenca> findByReuniao(Long comissaoReuniaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"presenca");
		criteria.createCriteria("presenca.comissaoReuniao", "cr");
		criteria.createCriteria("presenca.colaborador", "co");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("presenca.id"), "id");
		p.add(Projections.property("presenca.presente"), "presente");
		p.add(Projections.property("presenca.justificativaFalta"), "justificativaFalta");
		p.add(Projections.property("co.id"), "projectionColaboradorId");
		p.add(Projections.property("cr.id"), "projectionComissaoReuniaoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cr.id", comissaoReuniaoId));

		criteria.addOrder(Order.asc("co.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public void removeByReuniao(Long comissaoReuniaoId)
	{
		String hql = "delete from ComissaoReuniaoPresenca crp WHERE crp.comissaoReuniao.id = :comissaoReuniaoId";
		Query query = getSession().createQuery(hql);
		query.setLong("comissaoReuniaoId", comissaoReuniaoId);
		query.executeUpdate();
	}

	public Collection<ComissaoReuniaoPresenca> findByComissao(Long comissaoId, boolean ordenarPorDataNome)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"presenca");
		criteria.createCriteria("presenca.comissaoReuniao", "cr");
		criteria.createCriteria("presenca.colaborador", "co");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("presenca.id"), "id");
		p.add(Projections.property("presenca.presente"), "presente");
		p.add(Projections.property("presenca.justificativaFalta"), "justificativaFalta");
		p.add(Projections.property("co.id"), "projectionColaboradorId");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("cr.id"), "projectionComissaoReuniaoId");
		p.add(Projections.property("cr.data"), "projectionComissaoReuniaoData");
		p.add(Projections.property("cr.horario"), "projectionComissaoReuniaoHora");
		p.add(Projections.property("cr.descricao"), "projectionComissaoReuniaoDescricao");
		p.add(Projections.property("cr.tipo"), "projectionComissaoReuniaoTipo");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cr.comissao.id", comissaoId));

		if (ordenarPorDataNome)
			criteria.addOrder(Order.asc("cr.data"));

		criteria.addOrder(Order.asc("co.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public boolean existeReuniaoPresenca(Long comissaoId,	Collection<Long> colaboradorIds) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"presenca");
		criteria.createCriteria("presenca.comissaoReuniao", "cr");
		criteria.createCriteria("presenca.colaborador", "co");

		criteria.add(Expression.eq("cr.comissao.id", comissaoId));
		criteria.add(Expression.in("presenca.colaborador.id", colaboradorIds));

		return criteria.list().size() > 0;
	}

	public List<ComissaoReuniaoPresenca> findPresencaColaboradoresByReuniao(Long comissaoReuniaoId) 
	{
		StringBuffer hql = new StringBuffer();
		hql.append("select new ComissaoReuniaoPresenca(co.id, co.nome, crp.presente, crp.justificativaFalta) "); 
		hql.append("from ComissaoPeriodo cp ");
		hql.append("inner join cp.comissaoMembros cm "); 
		hql.append("inner join cm.colaborador co ");
		hql.append("inner join cp.comissao c ");
		hql.append("inner join c.comissaoReunioes cr ");
		hql.append("left join cr.comissaoReuniaoPresencas crp with crp.colaborador.id = co.id ");
		hql.append("left join c.comissaoPeriodos cp2 with cp2.aPartirDe = (select min(aPartirDe) from ComissaoPeriodo where comissao.id = c.id and aPartirDe > cp.aPartirDe) ");
		hql.append("where cr.id = :comissaoReuniaoId ");
		hql.append("and cr.data >= cp.aPartirDe and cr.data < coalesce(cp2.aPartirDe, c.dataFim + 1) ");
		hql.append("order by co.nome");
		
		Query query = getSession().createQuery(hql.toString());

		query.setLong("comissaoReuniaoId", comissaoReuniaoId);
		
		return query.list();
	}
	
	public Collection<ComissaoReuniaoPresenca> findPresencasByComissao(Long comissaoId) 
	{
		StringBuffer hql = new StringBuffer();
		hql.append("select distinct new ComissaoReuniaoPresenca(co.id, co.nome, cr.id, cr.tipo, cr.data, crp.presente, crp.justificativaFalta) ");
		hql.append("from Comissao c ");
		hql.append("inner join c.comissaoPeriodos cp ");
		hql.append("inner join cp.comissaoMembros cm "); 
		hql.append("inner join cm.colaborador co ");
		hql.append("left join c.comissaoReunioes cr ");
		hql.append("left join cr.comissaoReuniaoPresencas crp with crp.colaborador.id = co.id ");
		hql.append("where c.id = :comissaoId ");
		hql.append("order by co.nome, cr.data ");
		
		Query query = getSession().createQuery(hql.toString());

		query.setLong("comissaoId", comissaoId);
		
		return query.list();
	}
}
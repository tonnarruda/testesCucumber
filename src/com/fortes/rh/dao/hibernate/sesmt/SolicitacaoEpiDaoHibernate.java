package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;

/**
 * @author Tiago Lopes
 *
 */
@SuppressWarnings("unchecked")
public class SolicitacaoEpiDaoHibernate extends GenericDaoHibernate<SolicitacaoEpi> implements SolicitacaoEpiDao
{
	public Collection<SolicitacaoEpi> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, Boolean entregue)
	{
		Query query = montaConsultaFind(false, empresaId, dataIni, dataFim, colaborador.getNome(), colaborador.getMatricula(), entregue);

		if(pagingSize != 0)
        {
        	query.setFirstResult(((page - 1) * pagingSize));
        	query.setMaxResults(pagingSize);
        }
		return query.list();
	}

	public Integer getCount(Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, Boolean entregue)
	{
		Query query = montaConsultaFind(true, empresaId, dataIni, dataFim, colaborador.getNome(), colaborador.getMatricula(), entregue);
		return (Integer)query.uniqueResult();
	}

	private Query montaConsultaFind(boolean count, Long empresaId, Date dataIni, Date dataFim, String nomeBusca, String matriculaBusca, Boolean entregue)
	{
		StringBuilder hql = null;
		if (count)
			hql = new StringBuilder("select count(se.id) ");
		else
			hql = new StringBuilder("select new SolicitacaoEpi(se.id, se.data, se.entregue, co.nome, ca.nome) ");

		hql.append("from SolicitacaoEpi se ");
		hql.append("left join se.colaborador co ");
		hql.append("left join se.cargo ca ");
		hql.append("where se.empresa.id = :empresaId ");

		if (StringUtils.isNotBlank(matriculaBusca))
			hql.append("and lower(co.matricula) like :matricula ");

		if (StringUtils.isNotBlank(nomeBusca))
			hql.append("and lower(co.nome) like :nome ");

		if (dataIni != null && dataFim != null)
			hql.append("and se.data between :dataIni and :dataFim ");

		if (entregue != null)
			hql.append("and se.entregue = :entregue ");

		if(!count)
			hql.append("order by se.data DESC, co.nome ASC ");

		Query query = getSession().createQuery(hql.toString());


		if (dataIni != null && dataFim != null)
		{
			query.setDate("dataIni", dataIni);
			query.setDate("dataFim", dataFim);
		}

		if (entregue != null)
			query.setBoolean("entregue", entregue);

		if (StringUtils.isNotBlank(matriculaBusca))
			query.setString("matricula", matriculaBusca.toLowerCase());

		if (StringUtils.isNotBlank(nomeBusca))
			query.setString("nome", "%" + nomeBusca.toLowerCase() + "%");

		query.setLong("empresaId", empresaId);

		return query;
	}

	public SolicitacaoEpi findByIdProjection(Long solicitacaoEpiId)
	{
		Criteria criteria = getSession().createCriteria(SolicitacaoEpi.class, "se");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("se.id"), "id");
		p.add(Projections.property("se.data"), "data");
		p.add(Projections.property("se.entregue"), "entregue");

		criteria.setProjection(p);

		criteria.add(Expression.eq("se.id", solicitacaoEpiId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(SolicitacaoEpi.class));

		return (SolicitacaoEpi) criteria.uniqueResult();
	}

	public Collection<SolicitacaoEpi> findVencimentoEpi(Long empresaId, Date data, boolean exibirVencimentoCA)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new SolicitacaoEpi(e.id,co.id,e.nome,co.nome,ca.nome,se.data,eh.validadeUso,item.qtdEntregue, eh.vencimentoCA) ");
		hql.append("from Epi e ");
		hql.append("join e.solicitacaoEpiItems item ");
		hql.append("join item.solicitacaoEpi se ");
		hql.append("join se.colaborador co ");
		hql.append("join se.cargo ca ");
		hql.append("left join e.epiHistoricos eh ");
		hql.append("where eh.data = (select max(eh2.data)");
		hql.append("                    from EpiHistorico eh2");
		hql.append("                    where eh2.epi.id = e.id");
		hql.append("                    and eh2.data <= :data)");
		hql.append("and ((:data - se.data) >= eh.validadeUso ");

		if(exibirVencimentoCA)
			hql.append(" or :data >= eh.vencimentoCA ");
		
		hql.append(" ) and se.entregue = true ");
		hql.append("and co.desligado = false and e.empresa.id = :empresaId ");
		hql.append("order by co.id,e.id, se.data ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setLong("empresaId", empresaId);

		return query.list();
	}

	public Collection<SolicitacaoEpi> findEntregaEpi(Long empresaId, Long[] epiIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new SolicitacaoEpi(e.id,co.id,e.nome,co.nome,ca.nome,eh.data,eh.validadeUso,item.qtdEntregue) ");
		hql.append("from Epi e ");
		hql.append("join e.solicitacaoEpiItems item ");
		hql.append("join item.solicitacaoEpi se ");
		hql.append("join se.colaborador co ");
		hql.append("join se.cargo ca ");
		hql.append("left join e.epiHistoricos eh ");
		hql.append("where se.entregue = true ");
		
		if(epiIds != null && epiIds.length != 0)
			hql.append("and e.id in (:epiCheck) ");
		
		hql.append("and co.desligado = false and e.empresa.id = :empresaId ");
		hql.append("order by co.id,e.id,eh.data ");
		
		Query query = getSession().createQuery(hql.toString());
		
		query.setLong("empresaId", empresaId);
		
		if(epiIds != null && epiIds.length != 0)
			query.setParameterList("epiCheck", epiIds, Hibernate.LONG);
		
		return query.list();
	}
}
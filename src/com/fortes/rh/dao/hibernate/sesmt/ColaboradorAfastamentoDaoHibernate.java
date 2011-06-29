package com.fortes.rh.dao.hibernate.sesmt;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ColaboradorAfastamentoDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;

/**
 * @author Tiago Lopes
 *
 */
@SuppressWarnings("unchecked")
public class ColaboradorAfastamentoDaoHibernate extends GenericDaoHibernate<ColaboradorAfastamento> implements ColaboradorAfastamentoDao
{
	public Integer getCount(Long empresaId, Long afastamentoId, String nomeBusca, Long[] estabelecimentoIds, Date inicio, Date fim)
	{
		Query query = montaConsultaFind(true, "", empresaId, inicio, fim, nomeBusca, estabelecimentoIds, null, afastamentoId, false, false, 'T');
		return (Integer)query.uniqueResult();
	}

	public Collection<ColaboradorAfastamento> findAllSelect(int page, int pagingSize, Long empresaId, Long afastamentoId, String nomeBusca, Long[] estabelecimentoIds, Long[] areaIds, Date inicio, Date fim, String ascOuDesc, boolean ordenaColaboradorPorNome, boolean ordenaPorCid, char afastadoPeloINSS)
	{
		Query query = montaConsultaFind(false, ascOuDesc, empresaId, inicio, fim, nomeBusca, estabelecimentoIds, areaIds, afastamentoId, ordenaColaboradorPorNome, ordenaPorCid, afastadoPeloINSS);

		if(pagingSize != 0)
        {
        	query.setFirstResult(((page - 1) * pagingSize));
        	query.setMaxResults(pagingSize);
        }

		return query.list();
	}

	private Query montaConsultaFind(boolean isCount, String ascOuDesc, Long empresaId, Date inicio, Date fim, String nomeBusca, Long[] estabelecimentoIds, Long[] areaIds, Long afastamentoId, boolean ordenaColaboradorPorNome, boolean ordenaPorCid, char afastadoPeloINSS)
	{
		StringBuilder hql = null;

		Date ultimaData = (fim != null) ? fim : new Date();

		if (isCount)
			hql = new StringBuilder("select count(ca.id) ");
		else
			hql = new StringBuilder("select new ColaboradorAfastamento(ca.id, ca.inicio, ca.fim, a.descricao, co.nome, es.nome, ao.id, upper(trim(coalesce(ca.cid,''))) ) ");

		hql.append("from ColaboradorAfastamento ca ");
		hql.append("join ca.afastamento a ");
		hql.append("join ca.colaborador co ");
		hql.append("join co.historicoColaboradors hc ");
		hql.append("join hc.estabelecimento as es ");
		hql.append("join hc.areaOrganizacional as ao ");
		hql.append("where co.empresa.id = :empresaId ");

		if (afastamentoId != null)
			hql.append("and a.id = :afastamentoId ");

		if (isNotBlank(nomeBusca))
			hql.append("and lower(co.nome) like :nome ");

		if (estabelecimentoIds.length > 0)
			hql.append("and es.id in (:estabelecimentoIds) ");
		
		if (areaIds != null && areaIds.length > 0)
			hql.append("and ao.id in (:areaIds) ");
		
		if (afastadoPeloINSS == 'A')
			hql.append("and a.inss = true ");

		if (afastadoPeloINSS == 'N')
			hql.append("and a.inss = false ");

		hql.append("and hc.data = ( ");
		hql.append("select max(hc2.data) " );
		hql.append("	from HistoricoColaborador as hc2 ");
		hql.append("	where hc2.colaborador.id = co.id ");
		hql.append("	and hc2.data <= :ultimaData  and hc2.status = :status ");
		hql.append(") ");

		if (inicio != null && fim != null)
		{
			hql.append(
				"AND ( "
					+ "( ca.fim != null AND (ca.inicio between :inicio and :fim OR ca.fim between :inicio and :fim) ) " +
				" OR (ca.fim = null AND (ca.inicio <= :fim) )" +
				" ) ");
		}

		if (!isCount){
			if (ordenaPorCid)
				hql.append("ORDER BY upper(trim(coalesce(ca.cid,''))) ASC, co.nome " + ascOuDesc + ", ca.inicio");
			else if (ordenaColaboradorPorNome)
				hql.append("ORDER BY co.nome ASC, ca.inicio " + ascOuDesc);
			else
				hql.append("ORDER BY ca.inicio " + ascOuDesc +", co.nome ASC");
		}else{
			if (ordenaColaboradorPorNome)
				hql.append("ORDER BY co.nome ");
		}
		
		
		
		Query query = getSession().createQuery(hql.toString());

		if (afastamentoId != null)
			query.setLong("afastamentoId", afastamentoId);

		if (inicio != null && fim != null)
		{
			query.setDate("inicio", inicio);
			query.setDate("fim", fim);
		}

		query.setDate("ultimaData", ultimaData);
		query.setLong("empresaId", empresaId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if (isNotBlank(nomeBusca))
			query.setString("nome", "%" + nomeBusca.toLowerCase() + "%");

		if (estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		if (areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);
		
		return query;
	}
	
	public Collection<ColaboradorAfastamento> findByColaborador(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ca");
		criteria.createCriteria("ca.colaborador", "co", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("ca.afastamento", "a", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ca.id"), "id");
		p.add(Projections.property("ca.inicio"), "inicio");
		p.add(Projections.property("ca.fim"), "fim");
		p.add(Projections.property("ca.medicoNome"), "medicoNome");
		p.add(Projections.property("ca.medicoCrm"), "medicoCrm");
		p.add(Projections.property("ca.observacao"), "observacao");
		p.add(Projections.property("a.descricao"), "afastamentoDescricao");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("co.id", colaboradorId));
		criteria.addOrder(Order.asc("ca.inicio"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorAfastamento.class));
		
		return criteria.list();
	}
}
package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.HistoricoColaboradorBeneficioDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;

@Component
public class HistoricoColaboradorBeneficioDaoHibernate extends GenericDaoHibernate<HistoricoColaboradorBeneficio> implements HistoricoColaboradorBeneficioDao
{

	@SuppressWarnings("unchecked")
	public List filtroRelatorioByAreasEstabelecimentos(LinkedHashMap parametros)
	{
		String hql = " select distinct(a.id), b.nome, hcb.data, hcb.dataAte, c.id, c.dataDesligamento, 0, a.nome " +
					 " from HistoricoColaboradorBeneficio hcb " +
					 " 	left join hcb.colaborador c " +
					 " 	left join c.historicoColaboradors hc " +
					 " 	left join hc.areaOrganizacional a " +
					 " 	left join hc.estabelecimento e " +
					 " 	left join hcb.beneficios b " +
					 " where a.id in (:areasId) " +
					 " 	and e.id in (:estabelecimentosId) " +
					 " 	and hcb.data < :dataFim " +
					 " 	and (hcb.data >= ( " +
					 "					select max(hcb2.data)" +
					 "					from HistoricoColaboradorBeneficio hcb2" +
					 "					where hcb2.colaborador.id = c.id " +
					 "					 and hcb2.data <= :dataIni" +
					 "				   )" +
					 " 	or hcb.data > :dataIni) " +
					 " order by c.id,b.nome,hcb.data " ;

		Query query = getSession().createQuery(hql);
		query.setDate("dataIni", (Date)parametros.get("dataIni"));
		query.setDate("dataFim", (Date)parametros.get("dataFim"));

		query.setParameterList("areasId",(Collection<Long>)parametros.get("areas"), StandardBasicTypes.LONG);
		query.setParameterList("estabelecimentosId",(Collection<Long>)parametros.get("estabelecimentos"), StandardBasicTypes.LONG);

		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List filtroRelatorioByColaborador(LinkedHashMap parametros)
	{
		Query query;
		Colaborador colaborador = null;

		String hql = " select distinct(a.id), b.nome, hcb.data, hcb.dataAte, c.id, c.dataDesligamento, 0, a.nome  " +
					 " from HistoricoColaboradorBeneficio hcb " +
					 " 	left join hcb.colaborador c " +
					 " 	left join c.historicoColaboradors hc " +
					 " 	left join hc.areaOrganizacional a " +
					 " 	left join hc.estabelecimento e " +
					 " 	left join hcb.beneficios b " +
					 " where c.id = :colaboradorId " +
					 " 	and hcb.data < :dataFim " +
					 " 	and (hcb.data >= ( " +
					 "					select max(hcb2.data)" +
					 "					from HistoricoColaboradorBeneficio hcb2" +
					 "					where hcb2.colaborador.id = c.id " +
					 "					 and hcb2.data <= :dataIni" +
					 "				   )" +
					 " 	or hcb.data > :dataIni) " +
					 " order by c.id,b.nome,hcb.data " ;

		query = getSession().createQuery(hql);
		query.setDate("dataIni", (Date)parametros.get("dataIni"));
		query.setDate("dataFim", (Date)parametros.get("dataFim"));
		colaborador = (Colaborador)parametros.get("colaborador");
		query.setLong("colaboradorId", colaborador.getId());

		return query.list();
	}

	public Date getDataUltimoHistorico(Long colaboradorId)
	{
		String hql = " select max(hcb.data) " +
					 " from HistoricoColaboradorBeneficio hcb " +
					 " left join hcb.colaborador c " +
					 " where c.id = :colaboradorId " ;

		Query query;
		query = getSession().createQuery(hql);
		query.setLong("colaboradorId", colaboradorId);

		Date data = null;

		if(query.list().size()>0)
			data = (Date) query.list().toArray()[0];

		return data;

	}

	public HistoricoColaboradorBeneficio getUltimoHistorico(Long colaboradorId)
	{
		return getHistoricoByColaboradorData(colaboradorId, null);
	}

	public HistoricoColaboradorBeneficio getHistoricoByColaboradorData(Long colaboradorId, Date data)
	{
		Criteria criteria = getSession().createCriteria(HistoricoColaboradorBeneficio.class, "hcb");
		criteria.createCriteria("hcb.colaborador", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hcb.id"), "id");
		p.add(Projections.property("hcb.data"), "data");
		p.add(Projections.property("c.id"), "colaboradorId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));

		if(data != null)
			criteria.add(Expression.eq("hcb.data", data));

		criteria.addOrder(Order.desc("data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoColaboradorBeneficio.class));
		criteria.setMaxResults(1);

		return (HistoricoColaboradorBeneficio) criteria.uniqueResult();
	}

	public void updateDataAteUltimoHistorico(Long historicoId, Date dataAte)
	{
		String hql = " update HistoricoColaboradorBeneficio hcb " +
				     "	set hcb.dataAte = :dataAte " +
					 " where hcb.id = :historicoId " ;

		Session session = getSession();

		Query query = session.createQuery(hql);
		query.setLong("historicoId", historicoId);
		query.setDate("dataAte", dataAte);
		query.executeUpdate();
	}
}
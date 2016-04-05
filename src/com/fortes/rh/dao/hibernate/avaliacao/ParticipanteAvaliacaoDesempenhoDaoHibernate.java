package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.ParticipanteAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoParticipanteAvaliacao;
import com.fortes.rh.model.geral.Colaborador;

public class ParticipanteAvaliacaoDesempenhoDaoHibernate extends GenericDaoHibernate<ParticipanteAvaliacaoDesempenho> implements ParticipanteAvaliacaoDesempenhoDao
{
	@SuppressWarnings("unchecked")
	public Collection<Colaborador> findColaboradoresParticipantes(Long avaliacaoDesempenhoId, Character tipo) {
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.id, co.nome, co.nomeComercial, e.id, ");
		hql.append("ao.id, ao.nome, fs.id, fs.nome, ca.id, ca.nome, p.produtividade ) ");
		hql.append("from ParticipanteAvaliacaoDesempenho as p ");
		hql.append("inner join p.colaborador as co ");
		hql.append("inner join co.empresa as e ");
		hql.append("inner join co.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where ");
		hql.append("  hc.data = (");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		hql.append("   and hc2.data <= current_date  ");
		hql.append("  ) ");
		hql.append("and p.avaliacaoDesempenho.id = :avaliacaoDesempenhoId ");

		if(tipo != null)
			hql.append("and tipo = :tipo ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		if(tipo != null)
			query.setCharacter("tipo", tipo);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ParticipanteAvaliacaoDesempenho> findParticipantes(Long avaliacaoDesempenhoId, Character tipo) {
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
		.setProjection(Projections.max("hc2.data"))
		.add(Restrictions.eqProperty("hc2.colaborador.id", "co.id"))
		.add(Restrictions.le("hc2.data", new Date()));
		
		Criteria criteria = getSession().createCriteria(ParticipanteAvaliacaoDesempenho.class, "p"); 
		criteria.createCriteria("p.colaborador", "co");
		criteria.createCriteria("co.historicoColaboradors", "hc");
		criteria.createCriteria("hc.faixaSalarial", "fs");
		criteria.createCriteria("fs.cargo", "c");
		criteria.createCriteria("hc.areaOrganizacional", "ao");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.tipo"), "tipo");
		p.add(Projections.property("p.produtividade"), "produtividade");
		p.add(Projections.property("p.avaliacaoDesempenho.id"), "projectionAvaliacaoDesempenhoId");
		p.add(Projections.property("co.id"), "projectionColaboradorId");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("co.nomeComercial"), "projectionColaboradorNomeComercial");
		p.add(Projections.property("fs.id"), "projectionColaboradorFaixaId");
		p.add(Projections.property("fs.nome"), "projectionColaboradorFaixaNome");
		p.add(Projections.property("c.nome"), "projectionColaboradorFaixaCargoNome");
		p.add(Projections.property("ao.nome"), "projectionColaboradorAreaOrganizacionalNome");
		criteria.setProjection(p);
		
		criteria.add(Restrictions.eq("p.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		criteria.add(Restrictions.eq("p.tipo", tipo));
		criteria.add(Subqueries.propertyEq("hc.data", subQueryHc));
		
		criteria.addOrder(Order.asc("co.nome"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<FaixaSalarial> findFaixasSalariaisDosAvaliadosByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Long[] notFaixasSalariaisId) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select distinct new FaixaSalarial(fs.id, fs.nome, ca.nome) ");
		hql.append("from ParticipanteAvaliacaoDesempenho as p ");
		hql.append("inner join p.colaborador as co ");
		hql.append("inner join co.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("where ");
		hql.append("  hc.data = ( ");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		hql.append("   and hc2.data <= current_date ");
		hql.append("  ) ");
		hql.append("and p.avaliacaoDesempenho.id = :avaliacaoDesempenhoId ");
		hql.append("and tipo = :tipo ");

		if(notFaixasSalariaisId != null && notFaixasSalariaisId.length > 0)
			hql.append("and fs.id not in (:notFaixasSalariaisId) ");
			
		Query query = getSession().createQuery(hql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setCharacter("tipo", TipoParticipanteAvaliacao.AVALIADO);
		
		if(notFaixasSalariaisId != null && notFaixasSalariaisId.length > 0)
			query.setParameterList("notFaixasSalariaisId", notFaixasSalariaisId);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<FaixaSalarial> findFaixasSalariaisDosAvaliadosByAvaliador(Long avaliacaoDesempenhoId, Long avaliadorId) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select distinct new FaixaSalarial(fs.id, fs.nome, ca.nome) ");
		hql.append("from ColaboradorQuestionario as cq ");
		hql.append("inner join cq.colaborador as co ");
		hql.append("inner join co.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("where ");
		hql.append("  hc.data = ( ");
		hql.append("   select max(hc2.data) ");
		hql.append("   from HistoricoColaborador as hc2 ");
		hql.append("   where hc2.colaborador.id = co.id ");
		hql.append("   and hc2.data <= current_date  ");
		hql.append("  ) ");
		hql.append("and cq.avaliacaoDesempenho.id = :avaliacaoDesempenhoId ");
		hql.append("and cq.avaliador.id = :avaliadorId ");
		hql.append("order by ca.nome, fs.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setLong("avaliadorId", avaliadorId);
		
		return query.list();
	}
	
	public void removeNotIn(Long[] participantes, Long avaliacaoDesempenhoId, Character tipo) throws Exception {
		String hql = "delete from ParticipanteAvaliacaoDesempenho where ";
		if(participantes != null && participantes.length > 0)
			hql+="id not in (:participantes) and ";
		hql+="avaliacaoDesempenho.id = :avaliacaoDesempenhoId ";
		if(tipo != null)
			hql+="and tipo = :tipo";
		
		Query query = getSession().createQuery(hql);
		if(participantes != null && participantes.length > 0)
			query.setParameterList("participantes", participantes);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		if(tipo != null)
			query.setCharacter("tipo", tipo);
		
		query.executeUpdate();
	}

	public Double findByAvalDesempenhoIdAbadColaboradorId(Long avaliacaoDesempenhoId, Long avaliadoId, Character tipoParticipanteAvaliacao) 
	{
		Criteria criteria = getSession().createCriteria(ParticipanteAvaliacaoDesempenho.class, "p"); 
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.produtividade"), "produtividade");
		criteria.setProjection(p);
		
		criteria.add(Restrictions.eq("p.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		criteria.add(Restrictions.eq("p.colaborador.id", avaliadoId));
		criteria.add(Restrictions.eq("p.tipo", tipoParticipanteAvaliacao));
		
		if(criteria.uniqueResult() != null && !"".equals(criteria.uniqueResult()))
			return (Double) criteria.uniqueResult();
		else
			return null;
	}
}
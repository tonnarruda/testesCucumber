package com.fortes.rh.dao.hibernate.sesmt;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.util.ModelUtil;

@SuppressWarnings("unchecked")
public class AmbienteDaoHibernate extends GenericDaoHibernate<Ambiente> implements AmbienteDao
{
	public Integer getCount(Long empresaId, HistoricoAmbiente historicoAmbiente)
	{
		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");
		criteria.setProjection(Projections.rowCount());

		montaQuery(empresaId, historicoAmbiente, criteria);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (Integer) criteria.list().get(0);
	}

	public Collection<Ambiente> findAmbientes(int page, int pagingSize, Long empresaId, HistoricoAmbiente historicoAmbiente)
	{
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		p.add(Projections.property("ha.localAmbiente"), "projectionLocalAmbiente");
		p.add(Projections.property("estab.nome"), "estabelecimentoNomeOrEstabalecimentoDeTerceiro");

		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");

		montaQuery(empresaId, historicoAmbiente, criteria);
		
		criteria.setProjection(p);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ambiente.class));
		criteria.addOrder(Order.asc("a.nome"));

		//	Se page e pagingSize = 0, chamada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		return criteria.list();
	}

	private void montaQuery(Long empresaId, HistoricoAmbiente historicoAmbiente, Criteria criteria) {
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoAmbiente.class, "ha2")
				.setProjection(Projections.max("ha2.data"))
				.add(Restrictions.eqProperty("ha2.ambiente.id", "ha.ambiente.id"));
		
		criteria.createCriteria("a.historicoAmbientes", "ha", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("ha.estabelecimento", "estab", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Subqueries.propertyEq("ha.data", subQueryHc));
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		
		if (historicoAmbiente != null){
			if(isNotBlank(historicoAmbiente.getNomeAmbiente())){
				criteria.add(Expression.sqlRestriction("normalizar({alias}.nome) ilike normalizar(?)", "%" + historicoAmbiente.getNomeAmbiente() + "%", Hibernate.STRING));
			}
			if(historicoAmbiente.getLocalAmbiente() == null && ModelUtil.hasNotNull("getEstabelecimento().getId()", historicoAmbiente)){
				criteria.add(Expression.eq("ha.estabelecimento.id", historicoAmbiente.getEstabelecimento().getId()));
			}
			else if(historicoAmbiente.getLocalAmbiente() != null  && historicoAmbiente.getLocalAmbiente().equals(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao())){
				criteria.add(Expression.eq("ha.localAmbiente", LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao()));
			}
			else if(historicoAmbiente.getLocalAmbiente() != null  && historicoAmbiente.getLocalAmbiente().equals(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao())){ 
				criteria.add(Expression.eq("ha.localAmbiente", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao()));
				if(ModelUtil.hasNotNull("getEstabelecimento().getId()", historicoAmbiente))
					criteria.add(Expression.eq("ha.estabelecimento.id", historicoAmbiente.getEstabelecimento().getId()));
			}
		}
	}

	public Ambiente findByIdProjection(Long ambienteId)
	{
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		p.add(Projections.property("a.empresa.id"), "projectionEmpresaId");

		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");
		criteria.add(Expression.eq("a.id", ambienteId));
		criteria.setProjection(p);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ambiente.class));
		
		return (Ambiente) criteria.uniqueResult();
	}
	
	public int getQtdColaboradorByAmbiente(Long ambienteId, Date data, String sexo, Long funcaoId)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select count(*) as qtd ");
		hql.append("from HistoricoColaborador hc ");
		hql.append("	join hc.colaborador c ");
		hql.append("where   hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.data <=:data and hc2.status = :status and hc2.colaborador.id = hc.colaborador.id) ");
		hql.append("	and c.pessoal.sexo = :sexo ");
		hql.append("	and hc.ambiente.id = :ambienteId ");
		hql.append("	and (c.dataDesligamento is null or c.dataDesligamento > :data) ");

		if(funcaoId != null)
			hql.append("	and hc.funcao.id = :funcaoId ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setString("sexo", sexo);
		query.setLong("ambienteId", ambienteId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if(funcaoId != null)
			query.setLong("funcaoId", funcaoId);

		return (Integer)query.uniqueResult();
	}

	public void deleteAmbienteSemHistorico() throws Exception {
		String hql = "select a.id from Ambiente as a where (select count(ha.id) from HistoricoAmbiente as ha where ha.ambiente.id = a.id) = 0";
		Query query = getSession().createQuery(hql);
		ArrayList<Long> ambienteIds = (ArrayList<Long>) query.list();
		
		if(ambienteIds.size() > 0){
			hql = "delete Ambiente where id in(:ambienteIds)";
			query = getSession().createQuery(hql);
			query.setParameterList("ambienteIds", ambienteIds);
			query.executeUpdate();
		}
	}

	public Collection<Ambiente> findAllByEmpresa(Long empresaId) {
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoAmbiente.class, "ha2")
				.setProjection(Projections.max("ha2.data"))
				.add(Restrictions.eqProperty("ha2.ambiente.id", "ha.ambiente.id"))
				.add(Restrictions.isNull("ha2.dataInativo"));
		
		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");
		criteria.createCriteria("a.historicoAmbientes", "ha", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		p.add(Projections.property("ha.descricao"), "historicoAmbienteAtualDescricao");
		p.add(Projections.property("ha.data"), "historicoAmbienteAtualData");
		p.add(Projections.property("ha.tempoExposicao"), "historicoAmbienteAtualTempoExposicao");
		p.add(Projections.property("ha.nomeAmbiente"), "historicoAmbienteNomeAmbiente");
		criteria.setProjection(p);

		criteria.add(Subqueries.propertyEq("ha.data", subQueryHc));
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ambiente.class));
		criteria.addOrder(Order.asc("a.nome"));

		return criteria.list();
	}

	public void atualizaDadosParaUltimoHistorico(Long ambienteId) {
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoAmbiente.class, "ha2")
				.setProjection(Projections.max("ha2.data"))
				.add(Restrictions.eqProperty("ha2.ambiente.id", "ha.ambiente.id"));

		Criteria criteria = getSession().createCriteria(HistoricoAmbiente.class, "ha");
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ha.nomeAmbiente"), "nomeAmbiente");
		criteria.setProjection(p);

		criteria.add(Subqueries.propertyEq("ha.data", subQueryHc));
		criteria.add(Expression.eq("ha.ambiente.id", ambienteId));
		criteria.setMaxResults(1);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoAmbiente.class));
		HistoricoAmbiente historicoAmbiente = (HistoricoAmbiente) criteria.uniqueResult();
			
		String hql = "update Ambiente set nome = :nome where id = :id";
		Query query = getSession().createQuery(hql);
		
		query.setString("nome", historicoAmbiente.getNomeAmbiente());
		query.setLong("id", ambienteId);
		query.executeUpdate();
	}

	public Collection<Ambiente> findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(Long empresaId, Long estabelecimentoId, Integer localAmbiente, Date data) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");

		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoAmbiente.class, "ha2")
				.setProjection(Projections.max("ha2.data"))
				.add(Restrictions.eqProperty("ha2.ambiente.id", "ha.ambiente.id"))
				.add(Restrictions.le("ha2.data", data));

		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");
		criteria.createCriteria("a.historicoAmbientes", "ha", CriteriaSpecification.LEFT_JOIN);

		criteria.add(Subqueries.propertyEq("ha.data", subQueryHc));
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		
		criteria.add(Restrictions.eq("ha.localAmbiente", localAmbiente));
		
		if(localAmbiente.equals(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao()) & estabelecimentoId != null)
			criteria.add(Expression.eq("ha.estabelecimento.id", estabelecimentoId));
		
		criteria.setProjection(p);
		criteria.addOrder(Order.asc("a.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ambiente.class));

		return criteria.list();
	}
	
	public Collection<Ambiente> findByIds(Long empresaId, Collection<Long> ambienteIds, Date data, Long estabelecimentoId, Integer localAmbiente) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("ha.nomeAmbiente"), "nome");
		p.add(Projections.property("ha.descricao"), "historicoAmbienteAtualDescricao");
		p.add(Projections.property("ha.tempoExposicao"), "historicoAmbienteAtualTempoExposicao");
		p.add(Projections.property("ha.cnpjEstabelecimentoDeTerceiros"), "historicoAmbienteAtualCnpjEstabTerceiros");
		p.add(Projections.property("ha.localAmbiente"), "projectionLocalAmbiente");

		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoAmbiente.class, "ha2")
				.setProjection(Projections.max("ha2.data"))
				.add(Restrictions.eqProperty("ha2.ambiente.id", "ha.ambiente.id"))
				.add(Restrictions.le("ha2.data", data));

		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");
		criteria.createCriteria("a.historicoAmbientes", "ha", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		if(ambienteIds != null && !ambienteIds.isEmpty())
			criteria.add(Expression.in("a.id", ambienteIds));
		
		criteria.add(Subqueries.propertyEq("ha.data", subQueryHc));
		criteria.add(Restrictions.eq("ha.localAmbiente", localAmbiente));
		
		if(localAmbiente.equals(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao()) & estabelecimentoId != null)
			criteria.add(Expression.eq("ha.estabelecimento.id", estabelecimentoId));
		
		criteria.setProjection(p);
		criteria.addOrder(Order.asc("ha.nomeAmbiente"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ambiente.class));

		return criteria.list();
	}
	
	
	public Collection<Ambiente> findAmbientesPorEstabelecimento(Long[] estabelecimentoIds, Date data) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		p.add(Projections.property("estab.nome"), "projectionEstabelecimentoNome");
		p.add(Projections.property("estab.id"), "projectionEstabelecimentoId");

		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoAmbiente.class, "ha2")
				.setProjection(Projections.max("ha2.data"))
				.add(Restrictions.eqProperty("ha2.ambiente.id", "ha.ambiente.id"))
				.add(Restrictions.le("ha2.data", data));

		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");
		criteria.createCriteria("a.historicoAmbientes", "ha", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("ha.estabelecimento", "estab", CriteriaSpecification.LEFT_JOIN);

		criteria.add(Subqueries.propertyEq("ha.data", subQueryHc));
		criteria.add(Expression.in("ha.estabelecimento.id", estabelecimentoIds));
		
		criteria.setProjection(p);
		criteria.addOrder(Order.asc("a.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ambiente.class));

		return criteria.list();
	}
	
}
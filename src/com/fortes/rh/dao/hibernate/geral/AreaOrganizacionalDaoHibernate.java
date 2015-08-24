/* Autor: Bruno Bachiega
 * Data: 7/06/2006
 * Requisito: RFA004*/
package com.fortes.rh.dao.hibernate.geral;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("unchecked")
public class AreaOrganizacionalDaoHibernate extends GenericDaoHibernate<AreaOrganizacional> implements AreaOrganizacionalDao
{
	public AreaOrganizacional findAreaOrganizacionalCodigoAc(Long id)
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class, "ao");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ao.id"), "id");
		p.add(Projections.property("ao.codigoAC"), "codigoAC");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ao.id", id));
		
		criteria.addOrder(Order.asc("ao.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));
		criteria.setMaxResults(1);

		return (AreaOrganizacional) criteria.uniqueResult();
	}

	public AreaOrganizacional findByIdProjection(Long areaId)
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class, "ao");
		criteria.createCriteria("ao.areaMae", "areaMae", Criteria.LEFT_JOIN);
		criteria.createCriteria("ao.responsavel", "r", Criteria.LEFT_JOIN);
		criteria.createCriteria("ao.coResponsavel", "cor", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ao.id"), "id");
		p.add(Projections.property("ao.nome"), "nome");
		p.add(Projections.property("ao.ativo"), "ativo");
		p.add(Projections.property("ao.codigoAC"), "codigoAC");
		p.add(Projections.property("ao.emailsNotificacoes"), "emailsNotificacoes");
		p.add(Projections.property("areaMae.id"), "areaMaeId");
		p.add(Projections.property("areaMae.nome"), "areaMaeNome");
		p.add(Projections.property("r.id"), "idResponsavel");
		p.add(Projections.property("r.nomeComercial"), "nomeResponsavel");
		p.add(Projections.property("r.contato.email"), "emailResponsavel");
		p.add(Projections.property("cor.id"), "idCoResponsavel");
		p.add(Projections.property("cor.nomeComercial"), "nomeCoResponsavel");
		p.add(Projections.property("cor.contato.email"), "emailCoResponsavel");
		p.add(Projections.property("ao.empresa.id"), "empresaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ao.id", areaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));

		return (AreaOrganizacional) criteria.uniqueResult();
	}

	public Integer getCount(String nome, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class, "ao");
		criteria.setProjection(Projections.rowCount());

		montaConsulta(criteria, null, nome, AreaOrganizacional.TODAS, null, empresaId);

		return (Integer) criteria.list().get(0);
	}

	public Collection<AreaOrganizacional> findAllList(int page, int pagingSize, Long colaboradorId, String nome, Boolean ativo, Collection<Long> areaInativaIds, Long... empresasIds)
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class,"ao");
		// existem criterias criados no método montaConsulta
		criteria.createCriteria("am.empresa", "eMae", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ao.id"), "id");
		p.add(Projections.property("ao.ativo"), "ativo");
		p.add(Projections.property("ao.nome"), "nome");
		p.add(Projections.property("ao.codigoAC"), "codigoAC");
		p.add(Projections.property("ao.emailsNotificacoes"), "emailsNotificacoes");
		p.add(Projections.property("am.id"), "idAreaMae");
		p.add(Projections.property("am.nome"), "nomeAreaMae");
		p.add(Projections.property("r.id"), "idResponsavel");
		p.add(Projections.property("r.nomeComercial"), "nomeResponsavel");
		p.add(Projections.property("r.contato.email"), "emailResponsavel");
		p.add(Projections.property("cor.id"), "idCoResponsavel");
		p.add(Projections.property("cor.nomeComercial"), "nomeCoResponsavel");
		p.add(Projections.property("cor.contato.email"), "emailCoResponsavel");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nome"), "empresaNome");
		p.add(Projections.property("eMae.id"), "empresaAreaMaeId");
		p.add(Projections.property("eMae.nome"), "empresaAreaMaeNome");

		criteria.setProjection(p);

		montaConsulta(criteria, colaboradorId, nome, ativo, areaInativaIds, empresasIds);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));
		criteria.addOrder(Order.asc("ao.nome"));

		// Se page e pagingSize = 0, chamada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		return criteria.list();
	}

	private void montaConsulta(Criteria criteria, Long colaboradorId, String nome, Boolean ativo, Collection<Long> areaInativaIds, Long... empresasIds)
	{
		criteria.createCriteria("areaMae", "am", Criteria.LEFT_JOIN);
		criteria.createCriteria("responsavel", "r", Criteria.LEFT_JOIN);
		criteria.createCriteria("coResponsavel", "cor", Criteria.LEFT_JOIN);
		criteria.createCriteria("ao.empresa", "e");

		if(colaboradorId != null)
			criteria.add(Expression.eq("r.id", colaboradorId));

		if(nome != null && !nome.equals(""))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + nome.trim() + "%", Hibernate.STRING));

		if(ativo != null)
		{
			if (areaInativaIds == null || areaInativaIds.isEmpty())
				criteria.add(Expression.eq("ao.ativo", ativo));
			else
				criteria.add(Expression.or(Expression.eq("ao.ativo", ativo), Expression.in("ao.id", areaInativaIds)));
		}

		if(LongUtil.arrayIsNotEmpty(empresasIds))
			criteria.add(Expression.in("ao.empresa.id", empresasIds));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}

	public AreaOrganizacional findIdMaeById(long idArea)
	{

		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class,"ao");
		criteria.createCriteria("ao.areaMae", "areaMae", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("areaMae.id"), "areaMaeId");

		criteria.setProjection(p);
		criteria.add(Expression.eq("ao.id", idArea));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));

		return (AreaOrganizacional) criteria.list().toArray()[0];
	}

	public Collection<AreaOrganizacional> findByCargo(Long cargoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AreaOrganizacional(a.id, a.nome, a.ativo) ");
		hql.append("from Cargo as c ");
		hql.append("join c.areasOrganizacionais as a ");
		hql.append("	where c.id = :cargoId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("cargoId", cargoId);

		return query.list();
	}

	public boolean verificaMaternidade(Long areaOrganizacionalId, Boolean ativa)
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class,"ao");
		criteria.createCriteria("ao.areaMae", "areaMae", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("ao.id"), "id");
		p.add(Projections.property("areaMae.id"), "areaMaeId");

		criteria.setProjection(p);
		criteria.add(Expression.eq("areaMae.id", areaOrganizacionalId));
		
		if(ativa != null)
			criteria.add(Expression.eq("ao.ativo", ativa));

		criteria.addOrder(Order.asc("ao.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));

		Collection<AreaOrganizacional> areas = criteria.list();

		return areas.size() > 0;
	}

	public Collection<AreaOrganizacional> findAreaIdsByAreaInteresse(Long areaInteresseId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AreaOrganizacional(ao.id, ao.nome, ao.ativo) ");
		hql.append("from AreaInteresse as a ");
		hql.append("join a.areasOrganizacionais as ao ");
		hql.append("	where a.id = :areaInteresseId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("areaInteresseId", areaInteresseId);

		return query.list();
	}

	public AreaOrganizacional findAreaOrganizacionalByCodigoAc(String areaCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class, "ao");
		criteria.createCriteria("ao.empresa", "emp", Criteria.LEFT_JOIN);
		criteria.createCriteria("ao.areaMae", "areaMae", Criteria.LEFT_JOIN);
		criteria.createCriteria("ao.responsavel", "r", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ao.id"), "id");
		p.add(Projections.property("ao.nome"), "nome");
		p.add(Projections.property("ao.ativo"), "ativo");
		p.add(Projections.property("ao.codigoAC"), "codigoAC");
		p.add(Projections.property("ao.emailsNotificacoes"), "emailsNotificacoes");
		p.add(Projections.property("areaMae.id"), "areaMaeId");
		p.add(Projections.property("areaMae.nome"), "areaMaeNome");
		p.add(Projections.property("r.id"), "idResponsavel");
		p.add(Projections.property("r.nomeComercial"), "nomeResponsavel");
		p.add(Projections.property("r.contato.email"), "emailResponsavel");
		p.add(Projections.property("ao.empresa.id"), "empresaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ao.codigoAC", areaCodigoAC));
		criteria.add(Expression.eq("emp.codigoAC", empresaCodigoAC));
		criteria.add(Expression.eq("emp.grupoAC", grupoAC));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));
		criteria.setMaxResults(1);

		return (AreaOrganizacional) criteria.uniqueResult();
	}

	public Collection<AreaOrganizacional> findByConhecimento(Long conhecimentoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AreaOrganizacional(ao.id, ao.nome, ao.ativo) ");
		hql.append("from Conhecimento as c ");
		hql.append("join c.areaOrganizacionals as ao ");
		hql.append("	where c.id = :conhecimentoId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("conhecimentoId", conhecimentoId);

		return query.list();
	}
	
	public Collection<AreaOrganizacional> findByHabilidade(Long habilidadeId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AreaOrganizacional(ao.id, ao.nome, ao.ativo) ");
		hql.append("from Habilidade as h ");
		hql.append("join h.areaOrganizacionals as ao ");
		hql.append("	where h.id = :habilidadeId ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("habilidadeId", habilidadeId);
		
		return query.list();
	}
	
	public Collection<AreaOrganizacional> findByAtitude(Long atitudeId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AreaOrganizacional(ao.id, ao.nome, ao.ativo) ");
		hql.append("from Atitude as a ");
		hql.append("join a.areaOrganizacionals as ao ");
		hql.append("	where a.id = :atitudeId ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("atitudeId", atitudeId);
		
		return query.list();
	}

	public Collection<AreaOrganizacional> findQtdColaboradorPorArea(Long estabelecimentoId, Date data)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AreaOrganizacional(ao.id, ao.nome, count(ao.id)) ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("join hc.areaOrganizacional ao ");
		hql.append("join hc.colaborador co ");
		
		hql.append("where hc.estabelecimento.id = :estabelecimentoId ");
		hql.append("and (co.dataDesligamento is null ");
		hql.append("or co.dataDesligamento >= :data) ");
		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("                from HistoricoColaborador as hc2 ");
		hql.append("                where hc2.colaborador.id = co.id ");
		hql.append("                and hc2.data <= :data and hc2.status = :status ) ");
		hql.append("group by ao.id, ao.nome ");
		hql.append("order by ao.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setDate("data", data);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public Collection<AreaOrganizacional> findByEmpresasIds(Long[] empresaIds, Boolean ativo) {
		
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class,"ao");
		criteria.createCriteria("ao.empresa", "e");
		criteria.createCriteria("ao.areaMae", "am", Criteria.LEFT_JOIN);
		criteria.createCriteria("am.empresa", "eMae", Criteria.LEFT_JOIN);
		
		criteria.createCriteria("ao.responsavel", "res", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ao.id"), "id");
		p.add(Projections.property("ao.nome"), "nome");
		p.add(Projections.property("ao.ativo"), "ativo");
		p.add(Projections.property("am.id"), "idAreaMae");
		p.add(Projections.property("am.nome"), "nomeAreaMae");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nome"), "empresaNome");
		p.add(Projections.property("eMae.id"), "empresaAreaMaeId");
		p.add(Projections.property("eMae.nome"), "empresaAreaMaeNome");
		p.add(Projections.property("res.nome"), "responsavelNome");

		criteria.setProjection(p);

		if(ativo != null)
			criteria.add(Expression.eq("ao.ativo",ativo));

		if(empresaIds != null && empresaIds.length > 0)
			criteria.add(Expression.in("ao.empresa.id", empresaIds));

		criteria.addOrder(Order.asc("ao.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));
		
		return criteria.list();
	}
	
	public Collection<AreaOrganizacional> findByEmpresa(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class,"ao");
		criteria.createCriteria("ao.empresa", "e");
		criteria.createCriteria("ao.areaMae", "am", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ao.id"), "id");
		p.add(Projections.property("ao.nome"), "nome");
		p.add(Projections.property("ao.ativo"), "ativo");
		p.add(Projections.property("am.id"), "idAreaMae");
		p.add(Projections.property("am.nome"), "nomeAreaMae");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nome"), "empresaNome");

		criteria.setProjection(p);

		if(empresaId != null && !empresaId.equals(-1L))
			criteria.add(Expression.eq("ao.empresa.id", empresaId));
		else
			criteria.addOrder(Order.asc("e.nome"));

		criteria.addOrder(Order.asc("ao.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));
		
		return criteria.list();
	}

	public Collection<AreaOrganizacional> findSincronizarAreas(Long empresaOrigemId) {
		
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class,"ao");
		criteria.createCriteria("ao.areaMae", "am", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ao.id"), "id");
		p.add(Projections.property("ao.nome"), "nome");
		p.add(Projections.property("ao.ativo"), "ativo");
//		p.add(Projections.property("ao.codigoAC"), "codigoAC");
		p.add(Projections.property("am.id"), "idAreaMae");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ao.empresa.id", empresaOrigemId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.addOrder(Order.asc("ao.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));
		
		
		return criteria.list();
	}

	public Collection<AreaOrganizacional> findAreas(Long[] areaIds) 
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class,"a");
		criteria.createCriteria("a.areaMae", "am", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		p.add(Projections.property("am.id"), "idAreaMae");

		criteria.setProjection(p);
		
		if(areaIds != null && areaIds.length > 0)
			criteria.add(Expression.in("a.id", areaIds));

		criteria.addOrder(Order.asc("a.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));
		return criteria.list();
	}

	public Long[] findIdsAreasDoResponsavelCoResponsavel(Long usuarioId, Long empresaId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select a.id from AreaOrganizacional a "); 
		sql.append("inner join colaborador c on (c.id = a.responsavel_id or c.id = a.coResponsavel_id) ");
		sql.append("inner join usuario u on u.id = c.usuario_id ");
		sql.append("where u.id = :usuarioId ");
		sql.append("and a.empresa_id = :empresaId ");
		sql.append("order by a.id ");
	
		Query query = getSession().createSQLQuery(sql.toString());
		
		query.setLong("usuarioId", usuarioId);
		query.setLong("empresaId", empresaId);
		
		return LongUtil.collectionStringToArrayLong(query.list());
	}
	
	public Collection<AreaOrganizacional> findAreasDoResponsavelCoResponsavel(Long usuarioId, Long empresaId, Boolean ativo, Collection<Long> areaInativaIds) 
	{
		StringBuilder sql = new StringBuilder();
		
		sql.append("select distinct * from monta_familia_areas_filhas_by_usuario_and_empresa(:usuarioId, :empresaId)");
		
		if(ativo != null)
			if (areaInativaIds == null || areaInativaIds.isEmpty())
				sql.append("where areaativo = :ativo ");
			else
				sql.append("where (areaativo = :ativo or areaid in (:areaInativaIds))");
		
		sql.append("order by areanome");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		query.setLong("usuarioId", usuarioId);
		query.setLong("empresaId", empresaId);
		
		if(ativo != null)
			query.setBoolean("ativo", ativo);
		
		if(areaInativaIds != null)
			query.setParameterList("areaInativaIds", areaInativaIds, Hibernate.LONG);
		
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		
		for (Iterator<Object[]> it = query.list().iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			AreaOrganizacional area = new AreaOrganizacional();
			BigInteger id = (BigInteger)res[0];
			area.setId(id.longValue());
			area.setNome((String)res[1]);
			areas.add(area);
		}

		return areas;
	}

	public Collection<AreaOrganizacional> findSemCodigoAC(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"a");
		criteria.createCriteria("a.areaMae", "am", Criteria.LEFT_JOIN);
		criteria.createCriteria("a.empresa", "e");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		p.add(Projections.property("am.id"), "idAreaMae");
		p.add(Projections.property("am.nome"), "nomeAreaMae");
		p.add(Projections.property("e.nome"), "empresaNome");

		criteria.setProjection(p);
		
		criteria.add(Expression.isNull("a.codigoAC"));
		
		if(empresaId != null)
			criteria.add(Expression.eq("e.id", empresaId));

		criteria.addOrder(Order.asc("e.nome"));
		criteria.addOrder(Order.asc("a.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public Collection<Long> findIdsAreasFilhas(Collection<Long> areasIds) 
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class, "a");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		criteria.setProjection(p);
		
		criteria.add(Expression.in("a.areaMae.id", areasIds));
		criteria.addOrder(Order.asc("a.id"));
		
		return criteria.list();
	}

	public void desvinculaCoResponsavel(Long... colaboradoresIds)
	{
		String hql = "update AreaOrganizacional set coResponsavel.id = null where coResponsavel.id in (:ids)";
		
		desvinculaResponsaveis(hql, colaboradoresIds);
	}
	
	public void desvinculaResponsavel(Long... colaboradoresIds)
	{
		String hql = "update AreaOrganizacional set responsavel.id = null where responsavel.id in (:ids)";

		desvinculaResponsaveis(hql, colaboradoresIds);
	}

	private void desvinculaResponsaveis(String hql, Long... colaboradoresIds)
	{
		Query query = getSession().createQuery(hql);

		query.setParameterList("ids", colaboradoresIds);

		query.executeUpdate();
	}
	
	public void removeComDependencias(Long id) 
	{
		String[] sqls = new String[]{
			"DELETE FROM areainteresse_areaorganizacional WHERE areasorganizacionais_id = " + id,
			"DELETE FROM atitude_areaorganizacional WHERE areaorganizacionals_id = " + id,
			"DELETE FROM cargo_areaorganizacional WHERE areasorganizacionais_id = " + id,
			"DELETE FROM configuracaolimitecolaborador WHERE areaorganizacional_id = " + id,
			"DELETE FROM conhecimento_areaorganizacional WHERE areaorganizacionals_id = " + id,
			"DELETE FROM habilidade_areaorganizacional WHERE areaorganizacionals_id = " + id,
			"DELETE FROM quantidadelimitecolaboradoresporcargo WHERE areaorganizacional_id = " + id,
			"DELETE FROM solicitacaobds_empresabds WHERE solicitacaobds_id IN (SELECT id FROM solicitacaobds WHERE areaorganizacional_id = " + id + ")",
			"DELETE FROM solicitacaobds WHERE areaorganizacional_id = " + id,
			"DELETE FROM areaorganizacional WHERE id = " + id
		};
		
		JDBCConnection.executeQuery(sqls);
	}

	public Long[] findAreasMaesIdsByEmpresaId(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class, "a");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("a.empresa.id", empresaId));
		criteria.add(Expression.isNull("a.areaMae.id"));
		criteria.add(Expression.eq("a.ativo",true));
		criteria.addOrder(Order.asc("a.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return LongUtil.collectionStringToArrayLong(criteria.list());
	}
}
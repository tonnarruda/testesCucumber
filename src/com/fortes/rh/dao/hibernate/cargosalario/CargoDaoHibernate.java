/*
 * Autor: Bruno Bachiega
 * Data: 8/06/2006
 * Requisito: RFA008
*/
package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.ws.TCargo;

@Component
@SuppressWarnings("unchecked")
public class CargoDaoHibernate extends GenericDaoHibernate<Cargo> implements CargoDao
{
	public Integer getCount(Long empresaId, Long areaId, String cargoNome, Boolean ativo)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class,"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		criteria.setProjection(p);

		montaConsulta(criteria, empresaId, areaId, cargoNome, ativo);

		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		//Unica forma que o distinct funciona não da para usar count Francisco 17/12/2008
		Collection<Cargo> cargos = criteria.list();
		if(cargos == null || cargos.isEmpty())
			return 0;
		else
			return cargos.size();
	}

	public Collection<Cargo> findCargos(int page, int pagingSize, Long empresaId, Long areaId, String cargoNome, Boolean ativo)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");

		montaConsulta(criteria, empresaId, areaId, cargoNome, ativo);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");
		p.add(Projections.property("go.nome"), "grupoNome");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("c.nome"));


		// Se page e pagingSize = 0, chanada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return criteria.list();
	}

	private void montaConsulta(Criteria criteria, Long empresaId, Long areaId, String cargoNome, Boolean ativo)
	{
		criteria = criteria.createCriteria("c.grupoOcupacional", "go", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("c.areasOrganizacionais", "a", Criteria.LEFT_JOIN);

		criteria.add(Expression.eq("c.empresa.id", empresaId));

		if(areaId != null && areaId != -1)
			criteria.add(Expression.eq("a.id", areaId));

		if(cargoNome != null && !cargoNome.equals(""))
			criteria.add(Expression.ilike("c.nomeMercado","%"+ cargoNome +"%"));

		if(ativo != null)
			criteria.add(Expression.eq("c.ativo", ativo));

	}

	public Collection<Cargo> findByGrupoOcupacionalIdsProjection(Long[] idsLong, Long empresaId, Boolean cargoAtivo)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");
		p.add(Projections.property("c.ativo"), "ativo");

		criteria.setProjection(p);

		if(idsLong != null && idsLong.length > 0)
			criteria.add(Expression.in("grupoOcupacional.id", idsLong));
		
		if(empresaId != null)
			criteria.add(Expression.eq("empresa.id", empresaId));

		if(cargoAtivo != null)
			criteria.add(Expression.eq("c.ativo", cargoAtivo));

		criteria.addOrder(Order.asc("c.nomeMercado"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return criteria.list();
	}

	public Collection<Cargo> findByAreaOrganizacionalIdsProjection(Long[] idsLong, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");
		criteria.createCriteria("areasOrganizacionais","a", Criteria.LEFT_JOIN);
		criteria.createCriteria("empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");
		p.add(Projections.property("c.ativo"), "ativo");
		p.add(Projections.property("e.id"), "empresaIdProjection");
		p.add(Projections.property("e.nome"), "empresaNomeProjection");

		criteria.setProjection(p);
		criteria.setProjection(Projections.distinct(p));

		if(idsLong != null && idsLong.length > 0)
			criteria.add(Expression.in("a.id", idsLong));
		if(empresaId != null && empresaId > 0)
			criteria.add(Expression.eq("e.id", empresaId));

		criteria.addOrder(Order.asc("c.nomeMercado"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return criteria.list();
	}

	public Collection<Cargo> findCargosByIds(Long[] cargoIds, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");
		criteria.createCriteria("grupoOcupacional", "go", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");
		p.add(Projections.property("c.missao"), "missao");
		p.add(Projections.property("c.competencias"), "competencias");
		p.add(Projections.property("c.responsabilidades"), "responsabilidades");
		p.add(Projections.property("c.escolaridade"), "escolaridade");
		p.add(Projections.property("c.experiencia"), "experiencia");
		p.add(Projections.property("c.recrutamento"), "recrutamento");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.cboCodigo"), "cboCodigo");
		p.add(Projections.property("c.atitude"), "atitude");
		p.add(Projections.property("c.complementoConhecimento"), "complementoConhecimento");
		p.add(Projections.property("go.nome"), "grupoNome");
		
		criteria.setProjection(p);

		if(empresaId != null)
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.add(Expression.in("c.id", cargoIds));
		criteria.addOrder(Order.asc("c.nomeMercado"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return criteria.list();
	}

	public Collection<Cargo> findAllSelect(Long[] empresaIds, String ordenarPor, Boolean exibirModuloExterno, Boolean ativo)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");
		criteria.createCriteria("empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.ativo"), "ativo");
		p.add(Projections.property("e.id"), "empresaIdProjection");
		p.add(Projections.property("e.nome"), "empresaNomeProjection");

		criteria.setProjection(p);
		
		if(empresaIds != null && empresaIds.length > 0)
			criteria.add(Expression.in("c.empresa.id", empresaIds));
		
		if (exibirModuloExterno != null)
			criteria.add(Expression.eq("c.exibirModuloExterno", exibirModuloExterno));
		
		if(ativo != null)
			criteria.add(Expression.eq("c.ativo", ativo));
		
		criteria.addOrder(Order.asc("c."+ordenarPor));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return criteria.list();

	}

	public Cargo findByIdProjection(Long cargoId)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.id", cargoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));
		criteria.setMaxResults(1);

		return (Cargo) criteria.uniqueResult();
	}

	public Cargo findByIdAllProjection(Long cargoId)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");
		criteria.createCriteria("grupoOcupacional", "g", Criteria.LEFT_JOIN);
		criteria.createCriteria("empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");
		p.add(Projections.property("c.missao"), "missao");
		p.add(Projections.property("c.competencias"), "competencias");
		p.add(Projections.property("c.conhecimentos"), "conhecimentos");
		p.add(Projections.property("c.complementoConhecimento"), "complementoConhecimento");
		p.add(Projections.property("c.responsabilidades"), "responsabilidades");
		p.add(Projections.property("c.escolaridade"), "escolaridade");
		p.add(Projections.property("c.experiencia"), "experiencia");
		p.add(Projections.property("c.recrutamento"), "recrutamento");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.cboCodigo"), "cboCodigo");
		p.add(Projections.property("c.ativo"), "ativo");
		p.add(Projections.property("c.exibirModuloExterno"), "exibirModuloExterno");
		p.add(Projections.property("c.atitude"), "atitude");
		p.add(Projections.property("g.id"), "grupoOcupacionalIdProjection");
		p.add(Projections.property("g.nome"), "grupoNome");
		p.add(Projections.property("e.id"), "empresaIdProjection");
		p.add(Projections.property("e.nome"), "empresaNomeProjection");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.id", cargoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return (Cargo) criteria.uniqueResult();
	}

	public boolean verifyExistCargoNome(String cargoNome, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");

		criteria.add(Expression.ilike("c.nome", cargoNome));
		criteria.add(Expression.eq("c.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list().size() > 0;
	}

	public Collection<Cargo> findByGrupoOcupacional(Long grupoOcupacionalId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Cargo(c.id, c.nome, c.ativo) ");
		hql.append("from GrupoOcupacional as g ");
		hql.append("join g.cargos as c ");
		hql.append("	where g.id = :grupoOcupacionalId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("grupoOcupacionalId", grupoOcupacionalId);

		return query.list();
	}

	public Collection<Cargo> findByAreaDoHistoricoColaborador(Long[] areaIds)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Cargo(c.id, c.nomeMercado, c.ativo, e.id, e.nome) ");

		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.colaborador as co ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as c ");
		hql.append("left join hc.areaOrganizacional as a ");
		hql.append("left join c.empresa as e ");

		hql.append("where a.id in ( :areaIds ) ");

		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("	from HistoricoColaborador as hc2 ");
		hql.append("	where hc2.colaborador.id = co.id ");
		hql.append("	and hc2.data <= :hoje and hc2.status = :status ) ");
		hql.append("order by c.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		query.setParameterList("areaIds", areaIds, StandardBasicTypes.LONG);

		return query.list();
	}

	public Collection<Cargo> findAllSelectDistinctNomeMercado(Long[] empresaIds)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("c.nomeMercado")), "nomeMercado");
		p.add(Projections.property("c.ativo"), "ativo");
		criteria.setProjection(p);
		
		if(empresaIds != null && empresaIds.length > 0)
			criteria.add(Expression.in("c.empresa.id", empresaIds));
		
		criteria.addOrder(Order.asc("c.nomeMercado"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return criteria.list();
	}

	public Collection<Cargo> findBySolicitacao(Long solicitacaoId)
	{
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("s.id", solicitacaoId));
		criteria.addOrder(Order.asc("c.nomeMercado"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));
		
		return criteria.list();
	}
	
	/**
	 * Usado na importação de cadastros.
	 * @see EmpresaManager.sincronizaEntidades() */
	public Collection<Cargo> findSincronizarCargos(Long empresaOrigemId) {
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");

		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");
		p.add(Projections.property("c.missao"), "missao");
		p.add(Projections.property("c.competencias"), "competencias");
		p.add(Projections.property("c.responsabilidades"), "responsabilidades");
		p.add(Projections.property("c.escolaridade"), "escolaridade");
		p.add(Projections.property("c.experiencia"), "experiencia");
		p.add(Projections.property("c.recrutamento"), "recrutamento");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.cboCodigo"), "cboCodigo");
		p.add(Projections.property("c.ativo"), "ativo");
		p.add(Projections.property("c.exibirModuloExterno"), "exibirModuloExterno");
		p.add(Projections.property("c.atitude"), "atitude");
		p.add(Projections.property("c.grupoOcupacional"), "grupoOcupacional");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("c.empresa.id", empresaOrigemId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return criteria.list();
	}

	public Collection<Cargo> findByEmpresaAC(String empCodigo, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");
		criteria.createCriteria("c.empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("e.codigoAC", empCodigo));
		criteria.add(Expression.eq("e.grupoAC", grupoAC));

		criteria.addOrder(Order.asc("c.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));
		
		return criteria.list();
	}

	public Collection<Cargo> findByEmpresaAC(String empCodigo, String codigoFaixa, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(FaixaSalarial.class, "fs");
		criteria.createCriteria("fs.cargo", "c");
		criteria.createCriteria("c.empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("fs.codigoAC", codigoFaixa));
		criteria.add(Expression.eq("e.codigoAC", empCodigo));
		criteria.add(Expression.eq("e.grupoAC", grupoAC));
		
		criteria.addOrder(Order.asc("c.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));
		
		return criteria.list();
	}

	public void updateCBO(Long id, TCargo tCargo)
	{
		String hql = "update Cargo c set c.cboCodigo = :cbo where c.id = :id  ";
		
		Query query = getSession().createQuery(hql);
		
		query.setString("cbo", tCargo.getCboCodigo());
		query.setLong("id", id);//tem que ser por ID, ta correto(CUIDADO: caso mude tem que verificar o grupoAC)
		
		query.executeUpdate();
	}

	public Collection<Cargo> findAllSelect(Long[] empresaIds)
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");
		criteria.createCriteria("empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.ativo"), "ativo");
		p.add(Projections.property("e.id"), "empresaIdProjection");
		p.add(Projections.property("e.nome"), "empresaNomeProjection");

		criteria.setProjection(p);
		
		if(empresaIds != null && empresaIds.length > 0)
			criteria.add(Expression.in("e.id", empresaIds));
		
		criteria.addOrder(Order.asc("e.nome"));//não mudar o dwr precisa dessa ordem
		criteria.addOrder(Order.asc("c.nomeMercado"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return criteria.list();
	}

	public Collection<Cargo> findByArea(Long areaOrganizacionalId, Long empresaId) {
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");
		criteria.createCriteria("areasOrganizacionais","ao", Criteria.INNER_JOIN);
		criteria.createCriteria("empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nomeMercado"), "nomeMercado");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.ativo"), "ativo");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ao.id", areaOrganizacionalId));
		criteria.add(Expression.eq("e.id", empresaId));
		
		criteria.addOrder(Order.asc("c.nomeMercado"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return criteria.list();
	}

	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception {
		if(areaIds != null && areaIds.length > 0)
		{
			String[] sql = new String[] {"delete from cargo_areaorganizacional where areasorganizacionais_id in ("+StringUtils.join(areaIds, ",")+");"};
			JDBCConnection.executeQuery(sql);
		}
	}

	public Collection<Cargo> getCargosSemAreaRelacionada(Long empresaId) 
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Cargo(c.id, c.nomeMercado, c.ativo, e.id, e.nome) ");
		hql.append("from Cargo as c ");
		hql.append("left join c.areasOrganizacionais as a ");
		hql.append("left join c.empresa as e ");
		
		if(empresaId != null)
			hql.append("where e.id = :empresaId ");
		
		hql.append("group by c.id, c.nomeMercado, c.ativo, e.id, e.nome ");
		hql.append("having count(a.id) = 0 ");
		hql.append("order by e.nome, c.nomeMercado ");

		Query query = getSession().createQuery(hql.toString());
		if(empresaId != null)
			query.setLong("empresaId", empresaId);

		return query.list();
	}
	
	public Collection<Cargo> getCargosSemGrupoRelacionado(Long empresaId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Cargo(c.id, c.nomeMercado, c.ativo, e.id, e.nome) ");
		hql.append("from Cargo as c ");
		hql.append("left join c.empresa as e ");
		hql.append("where e.id = :empresaId ");
		hql.append("and c.grupoOcupacional.id is null ");
		hql.append("order by c.nomeMercado ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);

		return query.list();
	}

	public Collection<Cargo> findByAreaGrupo(Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId) 
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select distinct new Cargo(c.id, c.nomeMercado, c.ativo, e.id, e.nome) ");
		hql.append("from Cargo as c ");
		hql.append("left join c.areasOrganizacionais as a ");
		hql.append("left join c.empresa as e ");
		hql.append("where e.id = :empresaId ");
		
		if (areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
			hql.append("and a.id in (:areaOrganizacionalIds) ");
		
		if (grupoOcupacionalIds != null && grupoOcupacionalIds.length > 0)
			hql.append("and c.grupoOcupacional.id in (:grupoOcupacionalIds) ");
		
		hql.append("order by e.nome, c.nomeMercado ");

		Query query = getSession().createQuery(hql.toString());
		
		query.setLong("empresaId", empresaId);
		
		if (areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
			query.setParameterList("areaOrganizacionalIds", areaOrganizacionalIds, StandardBasicTypes.LONG);
		
		if (grupoOcupacionalIds != null && grupoOcupacionalIds.length > 0)
			query.setParameterList("grupoOcupacionalIds", grupoOcupacionalIds, StandardBasicTypes.LONG);

		return query.list();
	}

	public Collection<Cargo> findByEmpresa(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Cargo.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.addOrder(Order.asc("c.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));

		return criteria.list();
	}

	public void insereAreaRelacionada(Long areaMaeId, Long areaId) 
	{
		if(areaMaeId != null && areaId != null)
		{
			String[] sql = new String[] {"insert into cargo_areaorganizacional (cargo_id, areasorganizacionais_id) select cargo_id, " + areaId + " from cargo_areaorganizacional where areasorganizacionais_id = " + areaMaeId + ";"};
			JDBCConnection.executeQuery(sql);
		}
	}
}

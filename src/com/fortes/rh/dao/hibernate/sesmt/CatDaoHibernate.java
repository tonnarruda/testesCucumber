package com.fortes.rh.dao.hibernate.sesmt;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.CatDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Cat;

@SuppressWarnings("unchecked")
public class CatDaoHibernate extends GenericDaoHibernate<Cat> implements CatDao
{
	public Collection<Cat> findByColaborador(Colaborador colaborador)
	{
		return  findCatsColaboradorByDate(colaborador, null);
	}

	public Collection<Cat> findCatsColaboradorByDate(Colaborador colaborador, Date data)
	{
		Criteria criteria = getSession().createCriteria(Cat.class,"c");
		criteria.createCriteria("c.colaborador","cc");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("cc.id"), "colaboradorId");
		p.add(Projections.property("cc.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.data"), "data");
		p.add(Projections.property("c.numeroCat"), "numeroCat");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.gerouAfastamento"), "gerouAfastamento");

		criteria.setProjection(p);
		criteria.add(Expression.eq("cc.id", colaborador.getId()));

		if(data != null)
			criteria.add(Expression.le("c.data", data));

		criteria.addOrder(Order.desc("c.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cat.class));

		return  criteria.list();
	}

	public Collection<Cat> findAllSelect(Long empresaId, Date inicio, Date fim, Long[] estabelecimentoIds, String nomeBusca, Long[] areaIds)
	{
		StringBuilder hql = new StringBuilder("select new Cat(c.id, c.data, c.numeroCat, c.observacao, c.gerouAfastamento, co.id, co.matricula, co.nome, es.nome, ao.id) ");

		hql.append("from Cat c ");
		hql.append("join c.colaborador co ");
		hql.append("join co.historicoColaboradors hc ");
		hql.append("join hc.estabelecimento as es ");
		hql.append("join hc.areaOrganizacional as ao ");

		hql.append("where co.empresa.id = :empresaId ");

		if (isNotBlank(nomeBusca))
			hql.append("and lower(co.nome) like :nome ");

		if (estabelecimentoIds.length > 0)
			hql.append("and es.id in (:estabelecimentoIds) ");
		
		if (areaIds.length > 0)
			hql.append("and ao.id in (:areaIds) ");

		if (inicio != null && fim != null)
			hql.append("and c.data between :inicio and :fim ");

		hql.append("and hc.data = ( ");
		hql.append("select max(hc2.data) " );
		hql.append("	from HistoricoColaborador as hc2 ");
		hql.append("	where hc2.colaborador.id = co.id ");
		hql.append("	and hc2.data <= :ultimaData and hc2.status = :status ");
		hql.append(") ");

		hql.append("ORDER BY c.data DESC, co.nome ASC");

		Query query = getSession().createQuery(hql.toString());

		if (inicio != null && fim != null)
		{
			query.setDate("inicio", inicio);
			query.setDate("fim", fim);
		}

		query.setLong("empresaId", empresaId);
		query.setDate("ultimaData", new Date());

		if (isNotBlank(nomeBusca))
			query.setString("nome", "%" + nomeBusca.toLowerCase() + "%");

		if (estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		
		if (areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return query.list();
	}

	public Collection<Object[]> getCatsRelatorio(Long estabelecimentoId, Date inicio, Date fim)
	{
		StringBuilder hql = new StringBuilder("select cat.data, cat.gerouAfastamento");
		hql.append(" from HistoricoColaborador hc");
		hql.append(" left join hc.colaborador as c");
		hql.append(" join c.cats cat");
		hql.append(" where hc.estabelecimento.id = :estabelecimentoId");
		hql.append(" and hc.data = (select max(hc2.data)");
		hql.append("				from HistoricoColaborador hc2");
		hql.append(" 				where hc2.data <= cat.data  and hc2.status = :status ");
		hql.append(" 				and c.id = hc2.colaborador.id)");
		hql.append(" and cat.data between :dataIni and :dataFim");
		hql.append(" order by cat.data");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setDate("dataIni", inicio);
		query.setDate("dataFim", fim);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public Cat findUltimoCat(Long empresaId) 
	{
		StringBuilder hql = new StringBuilder("from Cat cat where cat.colaborador.empresa.id = :empresaId order by cat.data desc");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setMaxResults(1);

		return (Cat) query.uniqueResult();
	}
	
	public Map<Integer,Integer> findQtdPorDiaSemana(Long empresaId, Date dataIni, Date dataFim) 
	{
		StringBuilder sql = new StringBuilder("select cast(extract(dow from cat.data) as integer) as diaSemana, cast(count(*) as integer) as qtd ");
		sql.append("from cat "); 
		sql.append("inner join colaborador col on cat.colaborador_id = col.id "); 
		sql.append("where col.empresa_id = :empresaId and cat.data between :dataIni and :dataFim ");
		sql.append("group by extract(dow from cat.data)");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		
		Collection<Object[]> resultado = query.list();
		
		Map<Integer,Integer> qtds = new HashMap<Integer, Integer>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			qtds.put((Integer)res[0], (Integer)res[1]);
		}

		return qtds;
	}

	public Map<String,Integer> findQtdPorHorario(Long empresaId, Date dataIni, Date dataFim) 
	{
		StringBuilder sql = new StringBuilder("select substring(cat.horario, 0, 3) as hora, cast(count(*) as integer) as qtd ");
		sql.append("from cat "); 
		sql.append("inner join colaborador col on cat.colaborador_id = col.id "); 
		sql.append("where col.empresa_id = :empresaId and cat.data between :dataIni and :dataFim ");
		sql.append("group by hora ");
		sql.append("order by hora ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		
		Collection<Object[]> resultado = query.list();
		
		Map<String,Integer> qtds = new HashMap<String, Integer>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			qtds.put((String)res[0], (Integer)res[1]);
		}
		
		return qtds;
	}

	public Cat findByIdProjectionSimples(Long catId) 
	{
		StringBuilder hql = new StringBuilder("select new Cat(cat, col.nome, amb.nome, func.nome, nat.descricao, emp.nome, emp.razaoSocial, emp.endereco, cid.nome, uf.sigla) ");
		hql.append(" from Cat cat");
		hql.append(" inner join cat.colaborador col");
		hql.append(" inner join col.empresa emp");
		hql.append(" left join emp.cidade cid");
		hql.append(" left join emp.uf uf");
		hql.append(" left join cat.naturezaLesao nat");
		hql.append(" left join cat.ambienteColaborador amb");
		hql.append(" left join cat.funcaoColaborador func");
		hql.append(" where cat.id = :catId");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("catId", catId);

		return  (Cat) query.uniqueResult();
	}
	
	public Cat findByIdProjectionDetalhada(Long catId) 
	{
		StringBuilder hql = new StringBuilder("select new Cat(cat, emp.razaoSocial, emp.cnpj, est.complementoCnpj, emp.cnae, emp.endereco, empCid.nome, empUf.sigla, emp.ddd, emp.telefone, " +
				"col.nome, col.pessoal.mae, col.pessoal.dataNascimento, col.pessoal.sexo, col.pessoal.estadoCivil, col.pessoal.ctps.ctpsNumero, col.pessoal.ctps.ctpsSerie, col.pessoal.ctps.ctpsDataExpedicao, ctpsUf, col.pessoal.rg, col.pessoal.rgDataExpedicao, col.pessoal.rgOrgaoEmissor, rgUf, col.pessoal.pis, hc.salario," +
				"col.endereco.logradouro, col.endereco.numero, col.endereco.bairro, col.endereco.cep, cid, uf, " +
				"col.contato.ddd, col.contato.foneFixo, ca.nomeMercado, fs.codigoCbo, " +
				"nat.descricao ) ");
		hql.append(" from Cat cat");
		hql.append(" left join cat.testemunha1");
		hql.append(" left join cat.testemunha2");
		hql.append(" inner join cat.colaborador col");
		hql.append(" inner join col.empresa emp");
		hql.append(" left join col.endereco.cidade cid");
		hql.append(" left join col.endereco.uf uf");
		hql.append(" left join col.pessoal.ctps.ctpsUf ctpsUf ");
		hql.append(" left join col.pessoal.rgUf rgUf ");
		hql.append(" left join col.historicoColaboradors hc ");
		hql.append(" left join hc.estabelecimento est ");
		hql.append(" left join hc.faixaSalarial fs ");
		hql.append(" left join fs.cargo ca ");
		hql.append(" left join emp.cidade empCid");
		hql.append(" left join emp.uf empUf");
		hql.append(" left join cat.naturezaLesao nat");
		hql.append(" left join cat.ambienteColaborador amb");
		hql.append(" left join cat.funcaoColaborador func");
		hql.append(" where cat.id = :catId");
		hql.append(" and hc.data = (select max(hc2.data)");
		hql.append("				from HistoricoColaborador hc2");
		hql.append(" 				where hc2.data <= cat.data and hc2.status = :status ");
		hql.append(" 				and col.id = hc2.colaborador.id)");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("catId", catId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return  (Cat) query.uniqueResult();
	}
}
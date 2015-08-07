package com.fortes.rh.dao.hibernate.sesmt;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.util.DateUtil;

/**
 * @author Tiago Lopes
 *
 */
@SuppressWarnings("unchecked")
public class ColaboradorAfastamentoDaoHibernate extends GenericDaoHibernate<ColaboradorAfastamento> implements ColaboradorAfastamentoDao
{
	public Integer getCount(Long empresaId, Long afastamentoId, String nomeBusca, Long[] estabelecimentoIds, Date inicio, Date fim)
	{
		Query query = montaConsultaFind(true, false, empresaId, inicio, fim, nomeBusca, estabelecimentoIds, null, afastamentoId, null, 'T');
		return (Integer)query.uniqueResult();
	}

	public Collection<ColaboradorAfastamento> findAllSelect(int page, int pagingSize, boolean isListagemColaboradorAfastamento, Long empresaId, Long afastamentoId, String nomeBusca, Long[] estabelecimentoIds, Long[] areaIds, Date inicio, Date fim, String[] ordenarPor, char afastadoPeloINSS)
	{
		Query query = montaConsultaFind(false, isListagemColaboradorAfastamento, empresaId, inicio, fim, nomeBusca, estabelecimentoIds, areaIds, afastamentoId, ordenarPor, afastadoPeloINSS);

		if(pagingSize != 0)
        {
        	query.setFirstResult(((page - 1) * pagingSize));
        	query.setMaxResults(pagingSize);
        }

		return query.list();
	}

	private Query montaConsultaFind(boolean isCount, boolean isListagemColaboradorAfastamento, Long empresaId, Date inicio, Date fim, String nomeBusca, Long[] estabelecimentoIds, Long[] areaIds, Long afastamentoId, String[] ordenarPor, char afastadoPeloINSS)
	{
		StringBuilder hql = null;

		Date ultimaData = (fim != null) ? fim : new Date();

		if (isCount)
			hql = new StringBuilder("select count(ca.id) ");
		else
			hql = new StringBuilder("select new ColaboradorAfastamento(ca.id, ca.inicio, ca.fim, a.descricao, co.nome, co.matricula, es.nome, ao.id, upper(trim(coalesce(ca.cid,''))), ca.medicoNome, co.dataAdmissao, cast('" + DateUtil.formataDiaMesAno(ultimaData) + "' as text) ) ");

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

		if (ordenarPor != null)
		{
			hql.append("ORDER BY ");
			for (String ordem : ordenarPor) 
			{
				if(ordem.equals("data"))
				{
					hql.append(" ca.inicio");
					if(isListagemColaboradorAfastamento)
						hql.append(" DESC");
				}
				if(ordem.equals("cid"))
					hql.append(" upper(trim(coalesce(ca.cid,'')))");
				if(ordem.equals("colaboradorNome"))
					hql.append(" co.nome");
				if(ordem.equals("areaNome"))
					hql.append(" ao.nome");
				if(!ordenarPor[ordenarPor.length -1].equals(ordem))
					hql.append(",");
			}
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
	
	public ColaboradorAfastamento findByColaboradorAfastamentoId(Long colaboradorAfastamentoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ca");
		criteria.createCriteria("ca.colaborador", "co", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("ca.afastamento", "a", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ca.id"), "id");
		p.add(Projections.property("ca.inicio"), "inicio");
		p.add(Projections.property("ca.fim"), "fim");
		p.add(Projections.property("ca.medicoNome"), "medicoNome");
		p.add(Projections.property("ca.cid"), "cid");
		p.add(Projections.property("ca.medicoCrm"), "medicoCrm");
		p.add(Projections.property("ca.observacao"), "observacao");
		p.add(Projections.property("a.descricao"), "afastamentoDescricao");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("co.nomeComercial"), "projectionColaboradorNomeComercial");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ca.id", colaboradorAfastamentoId));
		criteria.add(Expression.eq("co.desligado", false));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorAfastamento.class));
		
		return (ColaboradorAfastamento) criteria.uniqueResult();
	}
	
	public Collection<Afastamento> findQtdAfastamentosPorMotivo(Long empresaId, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ca");
		criteria.createCriteria("ca.afastamento", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("ca.colaborador", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.groupProperty("a.descricao"), "descricao"));
		p.add(Projections.alias(Projections.count("ca.id"), "qtd"));
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.between("ca.inicio", dataIni, dataFim));
		
		criteria.addOrder(Order.desc("qtd"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Afastamento.class));
		
		return criteria.list();
	}

	public Integer findQtdAfastamentosInss(Long empresaId, Date dataIni, Date dataFim, boolean inss) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ca");
		criteria.createCriteria("ca.afastamento", "a");
		criteria.createCriteria("ca.colaborador", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.count("ca.id"));
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.eq("a.inss", inss));
		criteria.add(Expression.between("ca.inicio", dataIni, dataFim));
		
		return (Integer) criteria.uniqueResult();
	}

	public boolean exists(ColaboradorAfastamento colaboradorAfastamento) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ca");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ca.id"), "id");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ca.colaborador.id", colaboradorAfastamento.getColaborador().getId()));
		criteria.add(Expression.eq("ca.afastamento.id", colaboradorAfastamento.getAfastamento().getId()));
		if(colaboradorAfastamento.getInicio() != null)
			criteria.add(Expression.eq("ca.inicio", colaboradorAfastamento.getInicio()));
		if(colaboradorAfastamento.getFim() != null)
			criteria.add(Expression.eq("ca.fim", colaboradorAfastamento.getFim()));
		if(colaboradorAfastamento.getObservacao() != null)
			criteria.add(Expression.eq("ca.observacao", colaboradorAfastamento.getObservacao()));
		if(colaboradorAfastamento.getMedicoNome() != null)
			criteria.add(Expression.eq("ca.medicoNome", colaboradorAfastamento.getMedicoNome()));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return !criteria.list().isEmpty();
	}

	public Collection<ColaboradorAfastamento> findRelatorioResumoAfastamentos(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds, Long[] motivosIds, ColaboradorAfastamento colaboradorAfastamento) 
	{
		StringBuilder hql = new StringBuilder("select new ColaboradorAfastamento(co.id, co.matricula, co.nome, co.dataAdmissao, ao.id, ca.inicio, ca.fim, cast(sum(coalesce((ca.fim - ca.inicio + 1), 1)) as integer), cast(count(ca.id) as integer) ) ");
		hql.append("from ColaboradorAfastamento ca ");
		hql.append("join ca.colaborador co ");
		hql.append("join co.historicoColaboradors hc ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("where co.empresa.id = :empresaId ");

		if (estabelecimentosIds != null && estabelecimentosIds.length > 0)
			hql.append("and es.id in (:estabelecimentosIds) ");
		
		if (areasIds != null && areasIds.length > 0)
			hql.append("and ao.id in (:areasIds) ");

		if (motivosIds != null && motivosIds.length > 0)
			hql.append("and ca.afastamento.id in (:motivosIds) ");

		hql.append("and hc.data = ( ");
		hql.append("select max(hc2.data) " );
		hql.append("	from HistoricoColaborador as hc2 ");
		hql.append("	where hc2.colaborador.id = co.id ");
		hql.append("	and hc2.data <= :dataFim  and hc2.status = :status ");
		hql.append(") ");
		hql.append(
			"AND ( "
				+ "(ca.inicio between :dataInicio and :dataFim) " +
			" ) ");

		hql.append("GROUP BY co.id, co.matricula, co.nome, co.dataAdmissao, ao.id, ca.inicio, ca.fim ");
		hql.append("ORDER BY co.nome, cast('01/'||to_char(ca.inicio,'MM/yyyy') as date) ");
		
		Query query = getSession().createQuery(hql.toString());

		query.setDate("dataInicio", colaboradorAfastamento.getInicio());
		query.setDate("dataFim", colaboradorAfastamento.getFim());
		query.setLong("empresaId", empresaId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		if (estabelecimentosIds != null && estabelecimentosIds.length > 0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);

		if (areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if (motivosIds != null && motivosIds.length > 0)
			query.setParameterList("motivosIds", motivosIds, Hibernate.LONG);
		
		return query.list();
	}
	
	public Collection<Absenteismo> countAfastamentosByPeriodo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<Long> afastamentosIds) 
	{
		String diasDoPeriodo = "select cast('" + DateUtil.formataAnoMesDia(dataIni) + "' as date) + serie as dia from generate_series(0, cast('" + DateUtil.formataAnoMesDia(dataFim) + "' as date) - cast('" + DateUtil.formataAnoMesDia(dataIni) + "' as date)) as serie ";

		StringBuilder sql = new StringBuilder();
		sql.append("select date_part('year',dia) as ano, date_part('month',dia) as mes, count(ca.id) as total from ");
		sql.append(" ( " + diasDoPeriodo + " ) as datasDoPeriodo  ");
		sql.append("      left join ");
		sql.append("      ( ");
		sql.append("			select ca.id, ca.colaborador_id, ca.inicio, ca.fim, ca.afastamento_id  ");
		sql.append("			from ColaboradorAfastamento ca ");
		sql.append("			left join Afastamento a on  a.id = ca.afastamento_id ");
		sql.append("            left join Colaborador c on c.id = ca.colaborador_id ");
		sql.append("            left join HistoricoColaborador hc on hc.colaborador_id = c.id ");
		sql.append("				and hc.data = ( ");
		sql.append("					select max(hc2.data) ");
		sql.append("					from HistoricoColaborador as hc2 ");
		sql.append("					where hc2.colaborador_id = c.id ");
		sql.append("						and hc2.data <= :data and hc2.status = :status ");
		sql.append("				) ");
		sql.append("            left join FaixaSalarial fs on hc.faixasalarial_id = fs.id ");
		sql.append("		    where a.absenteismo = true ");
		sql.append("		    and hc.status = :status ");
		
		if(empresaIds != null && ! empresaIds.isEmpty())
			sql.append("		and c.empresa_id in (:empresaIds) ");
		if(areasIds != null && !areasIds.isEmpty())
			sql.append("		and hc.areaorganizacional_id in (:areaIds) ");
		if(cargosIds != null && !cargosIds.isEmpty())
			sql.append("		and fs.cargo_id in (:cargosIds) ");
		if(estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			sql.append("		and hc.estabelecimento_id in (:estabelecimentoIds) ");
		if(afastamentosIds != null && !afastamentosIds.isEmpty())
			sql.append("		and ca.afastamento_id in (:afastamentosIds) ");
		
		sql.append("      ) as ca  ");
		sql.append("        on     ");
		sql.append("          (datasDoPeriodo.dia between ca.inicio and ca.fim) ");
		sql.append("       	  or   ");
		sql.append("       	  (ca.fim is null and datasDoPeriodo.dia = ca.inicio) ");
		sql.append("group by date_part('year',dia), date_part('month',dia) ");
		sql.append("order by date_part('year',dia), date_part('month',dia) ");
		
		Query query = getSession().createSQLQuery(sql.toString());

		query.setDate("data", dataFim);
		if(empresaIds != null && ! empresaIds.isEmpty())
			query.setParameterList("empresaIds", empresaIds, Hibernate.LONG);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if(areasIds != null && !areasIds.isEmpty())
			query.setParameterList("areaIds", areasIds, Hibernate.LONG);
		if(cargosIds != null && !cargosIds.isEmpty())
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);
		if(estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			query.setParameterList("estabelecimentoIds", estabelecimentosIds, Hibernate.LONG);
		if(afastamentosIds != null && !afastamentosIds.isEmpty())
			query.setParameterList("afastamentosIds", afastamentosIds, Hibernate.LONG);
		
		Collection<Object[]> lista = query.list();
		Collection<Absenteismo> absenteismos = new ArrayList<Absenteismo>();

		DecimalFormat df = new DecimalFormat("00");
		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			absenteismos.add(new Absenteismo(df.format((Double)array[0]), df.format((Double)array[1]), ((BigInteger)array[2]).intValue()));
		}

		return absenteismos;
	}
}
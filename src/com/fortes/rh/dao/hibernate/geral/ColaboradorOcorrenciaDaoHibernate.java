package com.fortes.rh.dao.hibernate.geral;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("unchecked")
public class ColaboradorOcorrenciaDaoHibernate extends GenericDaoHibernate<ColaboradorOcorrencia> implements ColaboradorOcorrenciaDao
{
	public Collection<ColaboradorOcorrencia> findByColaborador(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.colaborador","c");
		criteria.createCriteria("co.ocorrencia","o");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("c.nomeComercial"),"colaboradorNomeComercial");
		p.add(Projections.property("o.descricao"), "ocorrenciaDescricao");
		p.add(Projections.property("o.pontuacao"), "ocorrenciaPontuacao");
		p.add(Projections.property("co.dataIni"), "dataIni");
		p.add(Projections.property("co.dataFim"), "dataFim");
		p.add(Projections.property("co.observacao"), "observacao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("co.colaborador.id", colaboradorId));
		criteria.addOrder(Order.asc("co.dataIni"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));
		return criteria.list();
	}

	//TODO Milosa, falta pegar o historico atual
	public Collection<ColaboradorOcorrencia> filtrar(Long[] ocorrenciaCheckLong, Long[] colaboradorCheckLong, Long[] estabelecimentoCheckLong, Map parametros)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.ocorrencia","o");
		criteria.createCriteria("co.colaborador","c");
		criteria.createCriteria("c.historicoColaboradors","hc", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.estabelecimento","e", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.areaOrganizacional","a", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("c.nomeComercial"),"colaboradorNomeComercial");
		p.add(Projections.property("o.descricao"), "ocorrenciaDescricao");
		p.add(Projections.property("co.dataIni"), "dataIni");
		p.add(Projections.property("co.dataFim"), "dataFim");
		p.add(Projections.property("co.observacao"), "observacao");

		p.add(Projections.property("o.pontuacao"), "ocorrenciaPontuacao");
		p.add(Projections.property("c.id"),"colaboradorId");
		p.add(Projections.property("c.nome"),"colaboradorNome");
		p.add(Projections.property("e.nome"),"estabelecimentoNome");
		p.add(Projections.property("a.nome"),"areaNome");

		criteria.setProjection(p);

		criteria.add(Expression.in("co.colaborador.id", colaboradorCheckLong));
		criteria.add(Expression.in("co.ocorrencia.id", ocorrenciaCheckLong));
		criteria.add(Expression.in("hc.estabelecimento.id", estabelecimentoCheckLong));
		criteria.add(Expression.le("hc.data", new Date()));

		if(parametros.get("dataIni") != null)
			criteria.add(Expression.ge("co.dataIni",parametros.get("dataIni")));

		if(parametros.get("dataFim") != null)
			criteria.add(Expression.le("co.dataIni",parametros.get("dataFim")));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("c.id"));
		criteria.addOrder(Order.desc("hc.data"));
		criteria.addOrder(Order.asc("co.dataIni"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));

		return criteria.list();
	}

	public Collection<ColaboradorOcorrencia> findProjection(int page, int pagingSize, Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "c");
		criteria.createCriteria("c.ocorrencia","o");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.dataIni"), "dataIni");
		p.add(Projections.property("c.dataFim"), "dataFim");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("o.descricao"), "projectionDescricao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.colaborador.id", colaboradorId));
		criteria.addOrder(Order.asc("c.dataIni"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));

		// Se page e pagingSize = 0, chamada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		return criteria.list();
	}

	public ColaboradorOcorrencia findByIdProjection(Long colaboradorOcorrenciaId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.ocorrencia","o");
		criteria.createCriteria("co.colaborador","c");
		criteria.createCriteria("c.empresa","e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("co.dataIni"), "dataIni");
		p.add(Projections.property("co.observacao"), "observacao");
		p.add(Projections.property("e.id"), "projectionEmpresaId");
		p.add(Projections.property("e.codigoAC"), "projectionEmpresaCodigoAC");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.codigoAC"), "projectionColaboradorCodigoAC");
		p.add(Projections.property("o.id"), "ocorrenciaId");
		p.add(Projections.property("o.codigoAC"), "projectionOcorrenciaCodigoAC");
		p.add(Projections.property("o.integraAC"), "projectionOcorrenciaIntegraAC");
		criteria.setProjection(p);

		criteria.add(Expression.eq("co.id", colaboradorOcorrenciaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));
		
		return (ColaboradorOcorrencia) criteria.uniqueResult();
	}

	public ColaboradorOcorrencia findByDadosAC(Date dataIni, String ocorrenciaCodigoAC, String colaboradorCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.colaborador","colab");
		criteria.createCriteria("co.ocorrencia","oco");
		criteria.createCriteria("oco.empresa","e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("co.dataFim"), "dataFim");
		criteria.setProjection(p);

		criteria.add(Expression.eq("co.dataIni", dataIni));
		criteria.add(Expression.eq("oco.codigoAC", ocorrenciaCodigoAC));
		criteria.add(Expression.eq("colab.codigoAC", colaboradorCodigoAC));
		criteria.add(Expression.eq("e.codigoAC", empresaCodigoAC));
		criteria.add(Expression.eq("e.grupoAC", grupoAC));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));
		return (ColaboradorOcorrencia) criteria.uniqueResult();
	}

	public boolean verifyExistsMesmaData(Long colaboradorOcorrenciaId, Long colaboradorId, Long ocorrenciaId, Long empresaId, Date dataIni)
	{

		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.ocorrencia","oco");
		criteria.createCriteria("oco.empresa","e");

		if (colaboradorOcorrenciaId != null)
			criteria.add(Expression.ne("co.id", colaboradorOcorrenciaId));

		criteria.add(Expression.eq("co.dataIni", dataIni));
		criteria.add(Expression.eq("co.ocorrencia.id", ocorrenciaId));
		criteria.add(Expression.eq("co.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("e.id", empresaId));

		criteria.setProjection(Projections.rowCount());

		boolean exists = ((Integer)criteria.uniqueResult()) > 0;
		return exists;
	}

	public String montaDiasDoPeriodo(Date dataIni, Date dataFim) 
	{
		StringBuilder diasDoPeriodo = new StringBuilder();
		
		int qtdDias = DateUtil.diferencaEntreDatas(dataIni, dataFim);
		for (int i = 0; i < qtdDias; i++) 
			diasDoPeriodo.append("select cast('" + DateUtil.formataAnoMesDia(DateUtil.incrementaDias(dataIni, i)) + "' as date) as dia union ");

		diasDoPeriodo.append("select cast('" + DateUtil.formataAnoMesDia(DateUtil.incrementaDias(dataIni, qtdDias)) + "' as date) as dia ");
		
		return diasDoPeriodo.toString();
	}
	
	public Collection<Absenteismo> countFaltasByPeriodo(Date dataIni, Date dataFim, Long empresaId, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> ocorrenciasIds) 
	{
		String diasDoPeriodo = montaDiasDoPeriodo(dataIni, dataFim);

		StringBuilder sql = new StringBuilder();
		sql.append("select date_part('year',dia) as ano, date_part('month',dia) as mes, count(hc.id) as total from ");
		sql.append(" ( " + diasDoPeriodo + " ) as datasDoPeriodo  ");
		sql.append("left join ColaboradorOcorrencia co on ");
		sql.append("	((datasDoPeriodo.dia between co.dataini and co.datafim) or (co.datafim is null and datasDoPeriodo.dia = co.dataini)) ");
		if(ocorrenciasIds != null && !ocorrenciasIds.isEmpty())
			sql.append("	and co.ocorrencia_id in (:ocorrenciasIds) ");
		sql.append("left join Ocorrencia o on ");
		sql.append("	o.id = co.ocorrencia_id ");
		sql.append("left join Colaborador c on c.id = co.colaborador_id ");
		sql.append("	and c.empresa_id = :empresaId ");
		sql.append("left join HistoricoColaborador hc on hc.colaborador_id = c.id ");
		sql.append("	and hc.status = :status ");
		
		if(areasIds != null && !areasIds.isEmpty())
			sql.append("	and hc.areaorganizacional_id in (:areaIds) ");
		if(estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			sql.append("	and hc.estabelecimento_id in (:estabelecimentoIds) ");
		
		sql.append("	and hc.data = ( ");
		sql.append("		select max(hc2.data) ");
		sql.append("		from HistoricoColaborador as hc2 ");
		sql.append("		where hc2.colaborador_id = c.id ");
		sql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		sql.append("	) ");
		sql.append("where o.absenteismo = true or co.id is null ");
		sql.append("group by date_part('year',dia), date_part('month',dia) ");
		sql.append("order by date_part('year',dia), date_part('month',dia) ");
		
		Query query = getSession().createSQLQuery(sql.toString());

		query.setDate("hoje", new Date());
		query.setLong("empresaId", empresaId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if(areasIds != null && !areasIds.isEmpty())
			query.setParameterList("areaIds", areasIds, Hibernate.LONG);
		if(estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			query.setParameterList("estabelecimentoIds", estabelecimentosIds, Hibernate.LONG);
		if(ocorrenciasIds != null && !ocorrenciasIds.isEmpty())
			query.setParameterList("ocorrenciasIds", ocorrenciasIds, Hibernate.LONG);
		
		Collection lista = query.list();
		Collection<Absenteismo> absenteismos = new ArrayList<Absenteismo>();

		DecimalFormat df = new DecimalFormat("00");
		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			absenteismos.add(new Absenteismo(df.format((Double)array[0]), df.format((Double)array[1]), ((BigInteger)array[2]).intValue()));
		}

		return absenteismos;
	}

	public void deleteByOcorrencia(Long[] ocorrenciaIds) throws Exception 
	{
		if(ocorrenciaIds != null && ocorrenciaIds.length > 0){
			String hql = "delete ColaboradorOcorrencia co where co.ocorrencia.id in (:ocorrenciaIds)";
			Query query = getSession().createQuery(hql);
	
			query.setParameterList("ocorrenciaIds", ocorrenciaIds, Hibernate.LONG);
			query.executeUpdate();		
		}
	}

	public Collection<ColaboradorOcorrencia> findColaboradorOcorrencia(Collection<Long> ocorrenciaIds, Collection<Long> colaboradorIds, Date dataIni, Date dataFim, Long empresaId, Collection<Long> areaIds, Collection<Long> estabelecimentoIds, boolean detalhamento) 
	{
		StringBuilder hql = new StringBuilder();

		if (detalhamento)
			hql.append("select distinct new ColaboradorOcorrencia(co.id, co.dataIni, co.dataFim, co.observacao, c.id, c.nome, c.nomeComercial, o.pontuacao, o.descricao, es.nome, ao.nome, p.descricao) ");
		else
			hql.append("select distinct new ColaboradorOcorrencia(c.id, c.nome, SUM(o.pontuacao)) ");
		
		hql.append("from ColaboradorOcorrencia as co ");
		hql.append("inner join co.ocorrencia as o ");
		hql.append("left join co.providencia as p ");
		hql.append("inner join co.colaborador as c ");
		hql.append("left join c.historicoColaboradors as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.estabelecimento as es ");
		
		hql.append("where hc.data = (select max(hc2.data) ");
		hql.append("				from HistoricoColaborador as hc2 "); 
		hql.append("				where hc2.colaborador.id = hc.colaborador.id ");
		hql.append("				and hc2.data <= :hoje ");
		hql.append("				and hc2.status = :statusHistColab) ");
		
		if(colaboradorIds.isEmpty())
		{
			if(!areaIds.isEmpty())
				hql.append("and hc.areaOrganizacional.id in (:areaIds) ");

			if(!estabelecimentoIds.isEmpty())
				hql.append("and hc.estabelecimento.id in (:estabelecimentoIds) ");
		}
		else
			hql.append("and hc.colaborador.id in (:colaboradorIds) ");
		
		if(!ocorrenciaIds.isEmpty())
			hql.append("and co.ocorrencia.id in (:ocorrenciaIds) ");
		
		hql.append("and co.dataIni >= :dataIni ");
		hql.append("and co.dataIni <= :dataFim ");
		hql.append("and o.empresa.id = :empresaId ");
		
		if (detalhamento)
		{
			hql.append("order by c.nome asc, c.id asc, co.dataIni asc ");
		} else
		{
			hql.append("group by c.nome, c.id ");
			hql.append("order by c.nome asc ");
		}

		Query query = getSession().createQuery(hql.toString());

		if(colaboradorIds.isEmpty())
		{
			if(!areaIds.isEmpty())
				query.setParameterList("areaIds", areaIds, Hibernate.LONG);

			if(!estabelecimentoIds.isEmpty())
				query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		}
		else
			query.setParameterList("colaboradorIds", colaboradorIds, Hibernate.LONG);
		
			
		if(!ocorrenciaIds.isEmpty())
			query.setParameterList("ocorrenciaIds", ocorrenciaIds, Hibernate.LONG);
		
		query.setInteger("statusHistColab", StatusRetornoAC.CONFIRMADO);
		query.setDate("hoje", new Date());
		query.setLong("empresaId", empresaId);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);

		return query.list();
	}
}
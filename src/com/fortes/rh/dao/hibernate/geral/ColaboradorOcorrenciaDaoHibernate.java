package com.fortes.rh.dao.hibernate.geral;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

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
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.util.DateUtil;

@Component
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
		criteria.add(Expression.eq("o.performance", true));
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
		criteria.addOrder(Order.desc("c.dataIni"));

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
		p.add(Projections.property("co.dataFim"), "dataFim");
		p.add(Projections.property("co.providencia.id"), "providenciaId");
		p.add(Projections.property("co.observacao"), "observacao");
		p.add(Projections.property("e.id"), "projectionEmpresaId");
		p.add(Projections.property("e.codigoAC"), "projectionEmpresaCodigoAC");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("c.codigoAC"), "projectionColaboradorCodigoAC");
		p.add(Projections.property("o.id"), "ocorrenciaId");
		p.add(Projections.property("o.descricao"), "ocorrenciaDescricao");
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

	public boolean verifyExistsMesmaData(Long colaboradorOcorrenciaId, Long colaboradorId, Long ocorrenciaId, Long empresaId, Date dataIni, Date dataFim)
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
	
	public Collection<ColaboradorOcorrencia> verifyOcorrenciasMesmaData(Long colaboradorOcorrenciaId, Long colaboradorId, Long ocorrenciaId, Long empresaId, Date dataIni, Date dataFim) {
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.ocorrencia","oco");
		criteria.createCriteria("oco.empresa","e");
		
		if (colaboradorOcorrenciaId != null)
			criteria.add(Expression.ne("co.id", colaboradorOcorrenciaId));
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("co.dataIni"), "dataIni");
		p.add(Projections.property("co.dataFim"), "dataFim");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("co.ocorrencia.id", ocorrenciaId));
		criteria.add(Expression.eq("co.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("e.id", empresaId));
		
		if (dataFim != null)
			criteria.add(Expression.le("co.dataIni", dataFim));
		else
			criteria.add(Expression.le("co.dataIni", dataIni));
			
		criteria.add(Expression.ge("co.dataFim", dataIni));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));
		
		return criteria.list();
	}

	public Collection<Absenteismo> countFaltasByPeriodo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<Long> ocorrenciasIds) 
	{
		String diasDoPeriodo = "select cast('" + DateUtil.formataAnoMesDia(dataIni) + "' as date) + serie as dia from generate_series(0, cast('" + DateUtil.formataAnoMesDia(dataFim) + "' as date) - cast('" + DateUtil.formataAnoMesDia(dataIni) + "' as date)) as serie ";

		StringBuilder sql = new StringBuilder();
		sql.append("select date_part('year',dia) as ano, date_part('month',dia) as mes, count(co.id) as total ");
		sql.append("from  ( " + diasDoPeriodo + " ) as datasDoPeriodo  ");
		sql.append("      left join ");
		sql.append("      ( ");
		sql.append("			select co.id, co.colaborador_id, co.dataini, co.datafim, co.ocorrencia_id ");
		sql.append("			from ColaboradorOcorrencia co ");
		sql.append("			left join Ocorrencia o on o.id = co.ocorrencia_id ");
		sql.append("            left join Colaborador c on c.id = co.colaborador_id ");
		
		sql.append("            left join HistoricoColaborador hc on hc.colaborador_id = c.id ");
		sql.append("				 and hc.data = ( ");
		sql.append("				 	select max(hc2.data) ");
		sql.append("				 	from HistoricoColaborador as hc2 ");
		sql.append("				 	where hc2.colaborador_id = c.id ");
		sql.append("						and hc2.data <= :data and hc2.status = :status ");
		sql.append("				 ) ");
		sql.append("            left join FaixaSalarial fs on hc.faixasalarial_id = fs.id ");
		sql.append("			where o.absenteismo = true ");
		sql.append("		    and hc.status = :status ");
		
		if(empresaIds != null && ! empresaIds.isEmpty())
			sql.append("	    and c.empresa_id in (:empresaIds) ");
		if(areasIds != null && !areasIds.isEmpty())
			sql.append("	    and hc.areaorganizacional_id in (:areaIds) ");
		if(cargosIds != null && !cargosIds.isEmpty())
			sql.append("	    and fs.cargo_id in (:cargosIds) ");
		if(estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			sql.append("	    and hc.estabelecimento_id in (:estabelecimentoIds) ");
		if(ocorrenciasIds != null && !ocorrenciasIds.isEmpty())
			sql.append("	    and co.ocorrencia_id in (:ocorrenciasIds) ");
		
		sql.append("      ) as co  ");
		sql.append("        on     ");
		sql.append("          (datasDoPeriodo.dia between co.dataini and co.datafim)    ");
		sql.append("       	  or   ");
		sql.append("       	  (co.datafim is null and datasDoPeriodo.dia = co.dataini)  ");
		
		
		sql.append("group by date_part('year',dia), date_part('month',dia) ");
		sql.append("order by date_part('year',dia), date_part('month',dia) ");
		
		Query query = getSession().createSQLQuery(sql.toString());

		query.setDate("data", dataFim);
		if(empresaIds != null && ! empresaIds.isEmpty())
			query.setParameterList("empresaIds", empresaIds, StandardBasicTypes.LONG);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if(areasIds != null && !areasIds.isEmpty())
			query.setParameterList("areaIds", areasIds, StandardBasicTypes.LONG);
		if(cargosIds != null && !cargosIds.isEmpty())
			query.setParameterList("cargosIds", cargosIds, StandardBasicTypes.LONG);
		if(estabelecimentosIds != null && !estabelecimentosIds.isEmpty())
			query.setParameterList("estabelecimentoIds", estabelecimentosIds, StandardBasicTypes.LONG);
		if(ocorrenciasIds != null && !ocorrenciasIds.isEmpty())
			query.setParameterList("ocorrenciasIds", ocorrenciasIds, StandardBasicTypes.LONG);
		
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

	public void deleteByOcorrencia(Long[] ocorrenciaIds) throws Exception 
	{
		if(ocorrenciaIds != null && ocorrenciaIds.length > 0){
			String hql = "delete ColaboradorOcorrencia co where co.ocorrencia.id in (:ocorrenciaIds)";
			Query query = getSession().createQuery(hql);
	
			query.setParameterList("ocorrenciaIds", ocorrenciaIds, StandardBasicTypes.LONG);
			query.executeUpdate();		
		}
	}

	public Collection<ColaboradorOcorrencia> findColaboradorOcorrencia(Collection<Long> ocorrenciaIds, Collection<Long> colaboradorIds, Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> areaIds, Collection<Long> estabelecimentoIds, boolean detalhamento, Character agruparPor, String situacao, Long notUsuarioId) 
	{
		StringBuilder hql = new StringBuilder();

		if (detalhamento)
			hql.append("select distinct new ColaboradorOcorrencia(co.id, co.dataIni, co.dataFim, co.observacao, c.id, c.matricula, c.nome, c.nomeComercial, o.pontuacao, o.descricao, es.nome, ao.nome, p.id, p.descricao) ");
		else
			hql.append("select distinct new ColaboradorOcorrencia(c.id, c.matricula, c.nome, SUM(o.pontuacao)) ");
		
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
		hql.append("				and hc2.data <= co.dataIni ");
		hql.append("				and hc2.status = :statusHistColab) ");
		
		if(colaboradorIds.isEmpty())
		{
			if(!areaIds.isEmpty())
				hql.append("and hc.areaOrganizacional.id in (:areaIds) ");

			if(!estabelecimentoIds.isEmpty())
				hql.append("and hc.estabelecimento.id in (:estabelecimentoIds) ");
			if(!situacao.equals(SituacaoColaborador.TODOS))
				hql.append("and c.desligado = :desligado ");
		}
		else
			hql.append("and hc.colaborador.id in (:colaboradorIds) ");
		
		if(!ocorrenciaIds.isEmpty())
			hql.append("and co.ocorrencia.id in (:ocorrenciaIds) ");
		
		hql.append("and co.dataIni <= :dataFim ");
		hql.append("and (co.dataFim >= :dataIni or co.dataIni >= :dataIni)");
		
		if(empresaIds != null && ! empresaIds.isEmpty())
			hql.append("and o.empresa.id in (:empresaIds) ");
		
		if(notUsuarioId != null)
			hql.append("and ( c.usuario.id <> :notUsuarioId or c.usuario.id is null ) ");
			
		if (detalhamento)
		{
			hql.append("order by ");
			
			if( agruparPor == 'P' )
				hql.append(" p.descricao asc, p.id asc, ");
			else if ( agruparPor == 'O' )
				hql.append(" o.descricao asc asc, ");
			
			hql.append(" c.nome asc, c.id asc, co.dataIni asc ");
		} else
		{
			hql.append("group by c.nome, c.id, c.matricula ");
			hql.append("order by c.nome asc ");
		}

		Query query = getSession().createQuery(hql.toString());

		if(colaboradorIds.isEmpty())
		{
			if(!areaIds.isEmpty())
				query.setParameterList("areaIds", areaIds, StandardBasicTypes.LONG);

			if(!estabelecimentoIds.isEmpty())
				query.setParameterList("estabelecimentoIds", estabelecimentoIds, StandardBasicTypes.LONG);
			
			if(!situacao.equals(SituacaoColaborador.TODOS)){
				query.setBoolean("desligado", situacao.equals(SituacaoColaborador.ATIVO) ? false : true);
			}
		}
		else
			query.setParameterList("colaboradorIds", colaboradorIds, StandardBasicTypes.LONG);
			
		if(!ocorrenciaIds.isEmpty())
			query.setParameterList("ocorrenciaIds", ocorrenciaIds, StandardBasicTypes.LONG);
		if(empresaIds != null && ! empresaIds.isEmpty())
			query.setParameterList("empresaIds", empresaIds, StandardBasicTypes.LONG);
		
		if(notUsuarioId != null)
			query.setLong("notUsuarioId", notUsuarioId);
		
		query.setInteger("statusHistColab", StatusRetornoAC.CONFIRMADO);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);

		return query.list();
	}

	public Collection<ColaboradorOcorrencia> findByFiltros(int page, int pagingSize, String colaboradorNome, String ocorrenciaDescricao, Boolean comProvidencia, Long[] colaboradoresIds, Long empresaId) {
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.colaborador","c");
		criteria.createCriteria("co.ocorrencia","o");
		criteria.createCriteria("co.providencia","p", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		
		if(pagingSize > 0)
		{
			p.add(Projections.property("c.nomeComercial"),"colaboradorNomeComercial");
			p.add(Projections.property("c.nome"),"colaboradorNome");
			p.add(Projections.property("o.descricao"), "ocorrenciaDescricao");
			p.add(Projections.property("o.pontuacao"), "ocorrenciaPontuacao");
			p.add(Projections.property("co.dataIni"), "dataIni");
			p.add(Projections.property("co.dataFim"), "dataFim");
			p.add(Projections.property("co.observacao"), "observacao");
			p.add(Projections.property("p.descricao"), "providenciaDescricao");

			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		criteria.setProjection(p);

		if(colaboradoresIds != null && colaboradoresIds.length > 0)
			criteria.add(Expression.in("c.id", colaboradoresIds));
		
		if(StringUtils.isNotBlank(colaboradorNome))
			criteria.add(Expression.like("c.nome", "%"+ colaboradorNome +"%").ignoreCase() );

		if(StringUtils.isNotBlank(ocorrenciaDescricao))
			criteria.add(Expression.like("o.descricao", "%"+ ocorrenciaDescricao +"%").ignoreCase() );

		if(comProvidencia != null)
		{
			if (comProvidencia)
				criteria.add(Expression.isNotNull("co.providencia.id"));
			else
				criteria.add(Expression.isNull("co.providencia.id"));
		}
		
		criteria.add(Expression.eq("o.empresa.id", empresaId));

		criteria.addOrder(Order.desc("co.dataIni"));
		criteria.addOrder(Order.asc("c.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));
		return criteria.list();
	}
	
	public Collection<ColaboradorOcorrencia> findByEmpresaId(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorOcorrencia.class, "co");
		criteria.createCriteria("co.colaborador","c");
		criteria.createCriteria("co.ocorrencia","o");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("co.dataIni"), "dataIni");
		p.add(Projections.property("co.dataFim"), "dataFim");
		p.add(Projections.property("co.observacao"), "observacao");
		p.add(Projections.property("o.id"), "ocorrenciaId");
		p.add(Projections.property("o.codigoAC"), "projectionOcorrenciaCodigoAC");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.codigoAC"), "projectionColaboradorCodigoAC");
		p.add(Projections.property("co.providencia.id"), "providenciaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.addOrder(Order.asc("co.dataIni"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorOcorrencia.class));

		return criteria.list();
	}
}
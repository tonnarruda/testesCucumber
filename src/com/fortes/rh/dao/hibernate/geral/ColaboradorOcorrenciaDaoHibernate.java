package com.fortes.rh.dao.hibernate.geral;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import sun.util.calendar.Gregorian;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;

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
			diasDoPeriodo.append("select cast('" + DateUtil.formataDiaMesAno(DateUtil.incrementaDias(dataIni, i)) + "' as date) as dia union ");

		diasDoPeriodo.append("select cast('" + DateUtil.formataDiaMesAno(DateUtil.incrementaDias(dataIni, qtdDias)) + "' as date) as dia ");
		
		return diasDoPeriodo.toString();
	}
	
	public Collection<Absenteismo> countFaltasByPeriodo(Date dataIni, Date dataFim, Long empresaId, Collection<Long> estabelecimentosIds, Collection<Long> areasIds) 
	{
		String diasDoPeriodo = montaDiasDoPeriodo(dataIni, dataFim);

		//zigs quando a ocorrencia terminar fora do periodo
		
		StringBuilder sql = new StringBuilder();
		sql.append("select date_part('year',dia) as ano, date_part('month',dia) as mes, count(co.dataini) as total from ");
		sql.append(" ( " + diasDoPeriodo + " ) as datasDoPeriodo  ");
		sql.append("left join ColaboradorOcorrencia co on ");
		sql.append("	datasDoPeriodo.dia between :dataIni and :dataFim ");
		sql.append("	and datasDoPeriodo.dia between co.dataini and co.datafim ");
		sql.append("	and co.absenteismo = true ");
		sql.append("left join Colaborador c on c.id = co.colaborador_id ");
		sql.append("	and c.empresa_id = :empresaId ");
		sql.append("left join HistoricoColaborador hc on hc.colaborador_id = c.id ");
		sql.append("	and hc.status = :status ");
		sql.append("	and hc.data = ( ");
		sql.append("		select max(hc2.data) ");
		sql.append("		from HistoricoColaborador as hc2 ");
		sql.append("		where hc2.colaborador_id = c.id ");
		sql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		sql.append("	)");
		sql.append("group by date_part('year',dia), date_part('month',dia) ");
		sql.append("order by date_part('month',dia), date_part('year',dia) ");
		
		Query query = getSession().createSQLQuery(sql.toString());

		query.setDate("hoje", new Date());
		query.setDate("dataIni", new Date());
		query.setDate("dataFim", new Date());
		query.setLong("empresaId", 4L);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		Collection<Absenteismo> absenteismos = new ArrayList<Absenteismo>();
		Collection lista = query.list();

		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			absenteismos.add(new Absenteismo(((Double)array[0]).toString(), ((Double)array[1]).toString(), ((BigInteger)array[2]).intValue()));
		}

		return absenteismos;
	}
}
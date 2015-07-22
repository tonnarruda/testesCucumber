package com.fortes.rh.dao.hibernate.sesmt;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;

@SuppressWarnings("unchecked")
public class ExameDaoHibernate extends GenericDaoHibernate<Exame> implements ExameDao
{
	public Exame findByIdProjection(Long exameId)
	{
		Criteria criteria = getSession().createCriteria(Exame.class, "e");
		criteria.createCriteria("e.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.periodico"), "periodico");
		p.add(Projections.property("e.periodicidade"), "periodicidade");
		p.add(Projections.property("emp.id"), "empresaIdProjection");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", exameId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Exame.class));

		return (Exame) criteria.uniqueResult();
	}

	public Collection<Exame> findByHistoricoFuncao(Long historicoFuncaoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Exame(e.id, e.nome) ");
		hql.append("from HistoricoFuncao as hf ");
		hql.append("join hf.exames as e ");
		hql.append("	where hf.id = :historicoFuncaoId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("historicoFuncaoId", historicoFuncaoId);

		return query.list();
	}

	public Collection<Long> findBySolicitacaoExame(Long solicitacaoExameId)
	{
		StringBuilder hql = new StringBuilder("select e.id ");
		hql.append("from ExameSolicitacaoExame exameSol ");
		hql.append("join exameSol.solicitacaoExame se ");
		hql.append("join exameSol.exame e ");
		hql.append("where se.id = :solicitacaoExameId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("solicitacaoExameId", solicitacaoExameId);

		return query.list();
	}

	public Collection<ExamesPrevistosRelatorio> findExamesPeriodicosPrevistos(Long empresaId, Date dataInicio, Date dataFim, Long[] exameIds, Long[] estabelecimentoIds, Long[] areaIds, Long[] colaboradorIds, boolean imprimirAfastados, boolean imprimirDesligados)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio(co.id,e.id,ao.id,ca.nome,co.matricula,co.nome,co.nomeComercial,e.nome,se.data,re.data,ese.periodicidade, se.motivo, es.id, es.nome) ");
		hql.append("from SolicitacaoExame se ");
		hql.append("join se.exameSolicitacaoExames ese ");
		hql.append("join ese.exame e ");
		hql.append("left join ese.realizacaoExame re ");
		hql.append("join se.colaborador co ");
		if (imprimirAfastados)
			hql.append("left join co.colaboradorAfastamento afa ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("where se.empresa.id = :empresaId ");
		hql.append("and se.data between :dataInicio and :dataFim ");
		hql.append("and ese.periodicidade > 0 ");

		if (areaIds != null && areaIds.length > 0)
			hql.append("and ao.id in (:areaIds) ");
		
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append("and es.id in (:estabelecimentoIds) ");
		
		if (colaboradorIds != null && colaboradorIds.length > 0)
			hql.append("and co.id in (:colaboradorIds) ");
		
		if (exameIds != null && exameIds.length > 0)
			hql.append("and e.id in (:exameIds) ");
		
		if (imprimirAfastados)
			hql.append("and (afa.fim = null or (afa.fim between :dataInicio and :dataFim)) ");
		
		if (!imprimirDesligados)
			hql.append("and co.desligado = :imprimirDesligados ");
		
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("	) ");
		hql.append("and re.resultado != :naoRealizado ");
		hql.append("order by co.id,e.id ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("hoje", new Date());
		query.setDate("dataInicio", dataInicio);
		query.setDate("dataFim", dataFim);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("empresaId", empresaId);
		
		if(!imprimirDesligados)
			query.setBoolean("imprimirDesligados", imprimirDesligados);

		query.setString("naoRealizado", ResultadoExame.NAO_REALIZADO.toString());
		
		if (areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		if (colaboradorIds != null && colaboradorIds.length > 0)
			query.setParameterList("colaboradorIds", colaboradorIds, Hibernate.LONG);
		if (exameIds != null && exameIds.length > 0)
			query.setParameterList("exameIds", exameIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<ExamesRealizadosRelatorio> findExamesRealizadosRelatorioResumido(Long empresaId, Date dataInicio, Date dataFim, ClinicaAutorizada clinicaAutorizada, Long[] examesIds)
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio(e.id, e.nome, clinica.id, clinica.nome, count(*)) ");
		hql.append("from ExameSolicitacaoExame ese ");
		hql.append("join ese.realizacaoExame re ");
		hql.append("left join ese.clinicaAutorizada clinica ");
		hql.append("join ese.exame e ");

		hql.append(" where e.empresa.id = :empresaId ");
		hql.append(" and re.data between :dataInicio and :dataFim ");
		
		if (clinicaAutorizada != null && clinicaAutorizada.getId() != null)
			hql.append(" and clinica.id = :clinicaAutorizadaId ");
		
		if (examesIds != null && examesIds.length > 0)
			hql.append(" and e.id in (:exameIds) ");
		
		hql.append(" group by e.id, e.nome, clinica.id, clinica.nome ");
		hql.append(" order by e.nome, clinica.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataInicio", dataInicio);
		query.setDate("dataFim", dataFim);
		query.setLong("empresaId", empresaId);
		
		if (clinicaAutorizada != null && clinicaAutorizada.getId() != null)
			query.setLong("clinicaAutorizadaId", clinicaAutorizada.getId());
		
		if (examesIds != null && examesIds.length > 0)
			query.setParameterList("exameIds", examesIds, Hibernate.LONG);
		
		return query.list();
	}
	
	public Collection<ExamesRealizadosRelatorio> findExamesRealizadosColaboradores(Long empresaId, String nomeBusca, Date inicio, Date fim, String solicitacaoMotivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds)  
	{
		Query query = montaConsultaDeExamesRealizados(empresaId, nomeBusca, inicio, fim, solicitacaoMotivo, exameResultado, clinicaAutorizadaId, examesIds, estabelecimentosIds, new Colaborador());
		
		return query.list();
	}

	public Collection<ExamesRealizadosRelatorio> findExamesRealizadosCandidatos(Long empresaId, String nomeBusca, Date inicio, Date fim, String solicitacaoMotivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds)  
	{
		Query query = montaConsultaDeExamesRealizados(empresaId, nomeBusca, inicio, fim, solicitacaoMotivo, exameResultado, clinicaAutorizadaId, examesIds, estabelecimentosIds, new Candidato());
		
		return query.list();
	}
	
	private Query montaConsultaDeExamesRealizados(Long empresaId, String nomeBusca, Date inicio, Date fim, String solicitacaoMotivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds, Examinado examinado)
	{
		StringBuilder hql = new StringBuilder("select distinct new com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio(e.id,examinado.nome,cast(:tipoPessoa as char),e.nome,se.data,clinica.id,clinica.nome,re.resultado,se.motivo,es.id,es.nome, re.observacao) ");
		hql.append("from ExameSolicitacaoExame ese ");
		hql.append("left join ese.realizacaoExame re ");
		hql.append("left join ese.clinicaAutorizada clinica ");
		hql.append("left join ese.exame e ");
		hql.append("left join ese.solicitacaoExame se ");
		
		examinado.setJoins(hql);
		
		hql.append("where se.empresa.id = :empresaId ");
		hql.append("and se.data between :inicio and :fim ");
		if (isNotBlank(nomeBusca))
			hql.append("and lower(examinado.nome) like :nome ");
		
		if (estabelecimentosIds != null && estabelecimentosIds.length > 0)
			hql.append("and es.id in (:estabelecimentoIds) ");
		
		if (examesIds != null && examesIds.length > 0)
			hql.append("and e.id in (:exameIds) ");
		
		if (clinicaAutorizadaId != null)
			hql.append("and clinica.id = :clinicaId ");
		
		if (StringUtils.isNotBlank(solicitacaoMotivo))
			hql.append("and se.motivo = :motivo ");
		
		if (StringUtils.isNotBlank(exameResultado))
		{
			if(exameResultado.equalsIgnoreCase(ResultadoExame.NAO_REALIZADO.toString()))
				hql.append("and (re = null or re.resultado = :resultado) ");
			else
				hql.append("and re.resultado = :resultado ");
		}
		
		examinado.setWhereMaxData(hql);
	
		hql.append("order by e.nome, clinica.id, se.data ");
		
		Query query = getSession().createQuery(hql.toString());
		
		examinado.setParametros(query);
		
		if (isNotBlank(nomeBusca))
			query.setString("nome", "%" + nomeBusca.toLowerCase() + "%");
		
		query.setDate("inicio", inicio);
		query.setDate("fim", fim);
		query.setLong("empresaId", empresaId);
		
		if (estabelecimentosIds != null && estabelecimentosIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentosIds, Hibernate.LONG);
		
		if (examesIds != null && examesIds.length > 0)
			query.setParameterList("exameIds", examesIds, Hibernate.LONG);
		
		if (clinicaAutorizadaId != null)
			query.setLong("clinicaId", clinicaAutorizadaId);
		
		if (StringUtils.isNotBlank(solicitacaoMotivo))
			query.setString("motivo", solicitacaoMotivo);
		
		if (StringUtils.isNotBlank(exameResultado))
			query.setString("resultado", exameResultado);
		
		return query;
	}
	
	private void montaQuery(Long empresaId, Exame exame, Criteria criteria) {
		if(empresaId != null)
			criteria.add(Expression.eq("e.empresa.id", empresaId));
		
		if (exame != null && isNotBlank(exame.getNome()))
			criteria.add(Expression.sqlRestriction("normalizar({alias}.nome) ilike normalizar(?)", "%" + exame.getNome() + "%", Hibernate.STRING));
	}

	public Integer getCount(Long empresaId, Exame exame) {

		Criteria criteria = getSession().createCriteria(Exame.class, "e");

		montaQuery(empresaId, exame, criteria);

		criteria.setProjection(Projections.rowCount());

		return (Integer) criteria.uniqueResult();
	}

	public Collection<Exame> find(Integer page, Integer pagingSize, Long empresaId, Exame exame) 
	{
		Criteria criteria = getSession().createCriteria(Exame.class, "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.periodico"), "periodico");
		p.add(Projections.property("e.periodicidade"), "periodicidade");

		criteria.setProjection(p);
		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);
		
		montaQuery(empresaId, exame, criteria);
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Exame.class));

		return criteria.list();
	}

	public Collection<Exame> findPriorizandoExameRelacionado(Long empresaId, Long colaboradorId) 
	{
		getSession().flush();
		
		StringBuilder sqlBody = new StringBuilder();
		StringBuilder sql = new StringBuilder();

		sqlBody.append("from exame e ");
		sqlBody.append("left join historicofuncao_exame hfe on e.id = hfe.exames_id ");
		sqlBody.append("left join historicofuncao hf on hfe.historicofuncao_id = hf.id ");
		sqlBody.append("left join historicocolaborador hc on (hc.funcao_id = hf.funcao_id and hc.colaborador_id = :colaboradorId) ");
		sqlBody.append("where (e.empresa_id = :empresaId or e.empresa_id is null) ");
		sqlBody.append("and hf.data=( select max(hf2.data) from historicofuncao hf2 where hf2.funcao_id=hf.funcao_id ) ");
		sqlBody.append("and hc.data = (select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id=hc.colaborador_id )  ");

		sql.append("select distinct e.id, e.nome, e.periodicidade, e.aso, true as relacionadoAoColaborador ");
		sql.append(sqlBody);
		
		sql.append("union ");
		
		sql.append("select distinct e.id, e.nome, e.periodicidade, e.aso, false as relacionadoAoColaborador ");
		sql.append("from exame e ");
		sql.append("where e.aso = true ");
		sql.append("and e.id not in  ");
		sql.append("( ");
		sql.append("	select distinct e.id ");
		sql.append(sqlBody);
		sql.append(")  ");
		
		sql.append("union ");
		
		sql.append("select e.id, e.nome, e.periodicidade, e.aso, false as relacionadoAoColaborador ");
		sql.append("from exame e  ");
		sql.append("where e.empresa_id = :empresaId  ");
		sql.append("and e.id not in  ");
		sql.append("( ");
		sql.append("	select distinct e.id ");
		sql.append(sqlBody);
		sql.append(")  ");
		sql.append("and e.id not in  ");
		sql.append("( ");
		sql.append("	select distinct e.id ");
		sql.append("from exame e ");
		sql.append("where e.aso = true ");
		sql.append(")  ");
		sql.append("order by 4 desc, 5 desc, 2 ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setLong("empresaId", empresaId);
		query.setLong("colaboradorId", colaboradorId);
		
		Collection<Exame> exames = new ArrayList<Exame>();
		Collection<Object[]> lista = query.list();

		int i;
		Exame exame;
		
		for (Object[] obj : lista) {
			i = 0;
			exame = new Exame(); 
			exame.setId(new BigInteger(obj[i].toString()).longValue());
			exame.setNome(obj[++i].toString());
			exame.setPeriodicidade(new Integer(obj[++i].toString()));
			exame.setAso(new Boolean(obj[++i].toString()));
			exame.setRelacionadoAoColaborador(new Boolean(obj[++i].toString()));
			exames.add(exame);
		}
		
		return exames;
	}

	public Collection<Exame> findByEmpresaComAsoPadrao(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Exame.class, "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.periodicidade"), "periodicidade");

		criteria.setProjection(p);
		criteria.add(Expression.or(Expression.eq("e.empresa.id", empresaId), Expression.isNull("e.empresa.id")));

		criteria.addOrder(Order.desc("e.aso"));
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Exame.class));

		return criteria.list();
	}
	
	public Collection<Exame> findAsoPadrao() 
	{
		Criteria criteria = getSession().createCriteria(Exame.class, "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.periodicidade"), "periodicidade");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("e.aso", true));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Exame.class));
		
		return criteria.list();
	}
	
	/**
	 * @author Arnaldo
	 * Classes internas para auxiliar a formação das consultas do método que monta a consulta dos exames realizados por colaboradores e candidatos
	 */
	private interface Examinado {
		public void setJoins(StringBuilder hql);
		public void setWhereMaxData(StringBuilder hql);
		public void setParametros(Query query);
	}
	
	private class Colaborador implements Examinado{
		
		public void setJoins(StringBuilder hql)
		{
			hql.append("inner join se.colaborador examinado ");
			hql.append("left join examinado.historicoColaboradors as hc ");
			hql.append("left join hc.estabelecimento as es ");
		}

		public void setWhereMaxData(StringBuilder hql)
		{
			hql.append("and hc.data = ( select max(hc2.data) ");
			hql.append("	        	from HistoricoColaborador as hc2 ");
			hql.append("	   			where hc2.colaborador.id = examinado.id ");
			hql.append("	   			and hc2.status = :status) ");
		}

		public void setParametros(Query query)
		{
			query.setInteger("status", StatusRetornoAC.CONFIRMADO);
			query.setCharacter("tipoPessoa", 'C');
		}
	}
	
	private class Candidato implements Examinado{
		
		public void setJoins(StringBuilder hql)
		{
			hql.append("inner join se.candidato examinado ");
			hql.append("left join examinado.candidatoSolicitacaos as cs ");
			hql.append("left join cs.solicitacao as s ");
			hql.append("left join s.estabelecimento as es ");
		}

		public void setWhereMaxData(StringBuilder hql)
		{
			hql.append("and (s.data = ( select max(s2.data) from CandidatoSolicitacao cs2 ");
			hql.append("				inner join cs2.solicitacao as s2 ");
			hql.append("				where cs2.candidato.id = examinado.id ");
			hql.append("				and s2.data <= se.data ");
			hql.append("				and s2.empresa.id = :empresaId) or s.data is null) ");
		}

		public void setParametros(Query query)
		{
			query.setCharacter("tipoPessoa", 'A');			
		}
	}
}
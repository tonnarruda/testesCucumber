package com.fortes.rh.dao.hibernate.sesmt;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessResourceFailureException;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.util.LongUtil;

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

	/**
	 * dataProximoExame: É a data da solicitação do exame, quando o exame não foi realizado.
	 * */
	public Collection<ExamesPrevistosRelatorio> findExamesPeriodicosPrevistosNaoRealizados(Long empresaId, Date dataInicio, Date dataFim, Long[] exameIds, Long[] estabelecimentoIds, Long[] areaIds, Long[] colaboradorIds, boolean imprimirAfastados, boolean imprimirDesligados)
	{
		Criteria criteria = createCriteriaFindExamesPeriodicosPrevistos(imprimirAfastados);
		ProjectionList p = projectionFindExamesPeriodicosPrevistos();
		p.add(Projections.property("se.data"), "dataProximoExame");

		criteria.setProjection(p);
		criteria.add(Expression.eq("se.empresa.id", empresaId));
		criteria.add(Expression.le("se.data", dataFim));
		criteria.add(Expression.ge("se.data", dataInicio));
		
		if (areaIds != null && areaIds.length > 0)
			criteria.add(Expression.in("ao.id", areaIds));
		
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			criteria.add(Expression.in("es.id", estabelecimentoIds));
		
		if (colaboradorIds != null && colaboradorIds.length > 0)
			criteria.add(Expression.in("co.id", colaboradorIds));
		
		if (exameIds != null && exameIds.length > 0)
			criteria.add(Expression.in("e.id", exameIds));
		
		if (imprimirAfastados)
			criteria.add(Expression.disjunction().add(Expression.isNull("afa.fim")).add(Expression.eq("afa.fim",dataFim) )); 
		
		if (!imprimirDesligados)
			criteria.add(Expression.eq("co.desligado", imprimirDesligados));
	    
	    criteria.add(Subqueries.propertyEq("hc.data", utimoHistoricoColaborador(dataFim) ));
	    criteria.add(Expression.disjunction().add(Expression.isNull("re.resultado")).add(Expression.eq("re.resultado",ResultadoExame.NAO_REALIZADO.toString())));
	    criteria.addOrder(Order.asc("co.nome"));
	    
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ExamesPrevistosRelatorio.class));
		
		return criteria.list();
	}
	
	public Collection<ExamesPrevistosRelatorio> findExamesPeriodicosPrevistos(Long empresaId, Date dataInicio, Date dataFim, Long[] exameIds, Long[] estabelecimentoIds, Long[] areaIds, Long[] colaboradorIds, boolean imprimirAfastados, boolean imprimirDesligados, boolean transfereExamesCandidatoColaborador){
		Criteria criteria = createCriteriaFindExamesPeriodicosPrevistos(imprimirAfastados);
		ProjectionList p = projectionFindExamesPeriodicosPrevistos();
		p.add(Projections.property("se.data"), "dataSolicitacaoExame");
		p.add(Projections.property("re.data"), "dataRealizacaoExame");
		p.add(Projections.sqlProjection("(re3_.data + (ese1_.periodicidade || ' month')::interval) as dataProximoExame", new String[]{"dataProximoExame"}, new Type[]{Hibernate.DATE}));
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("se.empresa.id", empresaId ));
		criteria.add(Expression.le("se.data", dataFim ));
		criteria.add(Expression.gt("ese.periodicidade", 0 ));
		
		if (areaIds != null && areaIds.length > 0)
			criteria.add(Expression.in("ao.id", areaIds ));
		
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			criteria.add(Expression.in("es.id", estabelecimentoIds ));
		
		if (colaboradorIds != null && colaboradorIds.length > 0)
			criteria.add(Expression.in("co.id", colaboradorIds ));
		
		if (exameIds != null && exameIds.length > 0)
			criteria.add(Expression.in("e.id", exameIds ));
		
		if (imprimirAfastados)
			criteria.add(Expression.disjunction().add(Expression.isNull("afa.fim")).add(Expression.eq("afa.fim",dataFim))); 
		
		if (!imprimirDesligados)
			criteria.add(Expression.eq("co.desligado", imprimirDesligados ));
		
	    criteria.add(Subqueries.propertyEq("hc.data", utimoHistoricoColaborador(dataFim)));
	    criteria.add(Expression.ne("re.resultado",ResultadoExame.NAO_REALIZADO.toString() ));
	    
	    criteria.add(Expression.sqlRestriction("(re3_.data + (ese1_.periodicidade || ' month')::interval) between ? and ? ", new Date[]{dataInicio,dataFim}, new Type[]{Hibernate.DATE, Hibernate.DATE}));

	    criteria.add(Expression.sqlRestriction("this_.data = (select se2.data from solicitacaoexame as se2 join examesolicitacaoexame ese2 on se2.id = ese2.solicitacaoexame_id " 
																	+ "join realizacaoexame as re2 on re2.id = ese2.realizacaoexame_id "
																	+ "where se2.colaborador_id = co4_.id and ese2.exame_id = e2_.id and re2.data = ("
																			+ "select max(re4.data) from examesolicitacaoexame ese4 left join solicitacaoexame se4 on se4.id = ese4.solicitacaoexame_id "
																			+ "left join realizacaoexame re4 on re4.id = ese4.realizacaoexame_id "
																			+ "where se4.colaborador_id = co4_.id and re4.resultado<> ? and ese4.exame_id = e2_.id ) limit 1 ) "
																	, new String[]{ResultadoExame.NAO_REALIZADO.toString()}, new Type[]{Hibernate.STRING}));
	    if(!transfereExamesCandidatoColaborador)
	    	criteria.add(Expression.eq("se.candidato.id",null));
	    
	    criteria.addOrder(Order.asc("co.nome")).setResultTransformer(new AliasToBeanResultTransformer(ExamesPrevistosRelatorio.class));
		
		return criteria.list();
	}

	private Criteria createCriteriaFindExamesPeriodicosPrevistos(boolean imprimirAfastados) throws DataAccessResourceFailureException, IllegalStateException, HibernateException {
		Criteria criteria = getSession().createCriteria(SolicitacaoExame.class, "se");
		criteria.createCriteria("se.exameSolicitacaoExames", "ese", Criteria.INNER_JOIN);
		criteria.createCriteria("ese.exame", "e", Criteria.INNER_JOIN);
		criteria.createCriteria("ese.realizacaoExame", "re", Criteria.LEFT_JOIN);
		criteria.createCriteria("se.colaborador", "co", Criteria.INNER_JOIN);
		criteria.createCriteria("co.historicoColaboradors", "hc", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.estabelecimento", "es", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", Criteria.LEFT_JOIN);

		if(imprimirAfastados)
			criteria.createCriteria("co.colaboradorAfastamento", "afa", Criteria.LEFT_JOIN);
		
		return criteria;
	}

	private ProjectionList projectionFindExamesPeriodicosPrevistos() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "colaboradorId");
		p.add(Projections.property("e.id"), "exameId");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.sqlProjection("monta_familia_area(ao7_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNome");
		p.add(Projections.property("ca.nome"), "cargoNome");
		p.add(Projections.property("co.matricula"), "colaboradorMatricula");
		p.add(Projections.property("co.nome"), "colaboradorNome");
		p.add(Projections.property("co.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("e.nome"), "exameNome");
		p.add(Projections.property("ese.periodicidade"), "examePeriodicidade");
		p.add(Projections.property("se.motivo"), "motivoSolicitacaoExame");
		p.add(Projections.property("es.id"), "estabelecimentolId");
		p.add(Projections.property("es.nome"), "estabelecimentolNome");
		return p;
	}
	
	private DetachedCriteria utimoHistoricoColaborador(Date data) {
		DetachedCriteria subSelect = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
	    		.setProjection(Projections.max("hc2.data"))
	    		.add(Restrictions.eqProperty("hc2.colaborador.id", "co.id"))
	    		.add(Restrictions.le("hc2.data", data))
	    		.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
		return subSelect;
	}

	public Collection<ExamesRealizadosRelatorio> findExamesRealizadosRelatorioResumido(Long empresaId, Date dataInicio, Date dataFim, ClinicaAutorizada clinicaAutorizada, Long[] examesIds, String resultadoExame)
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio(e.id, e.nome, clinica.id, clinica.nome, count(*)) ");
		hql.append("from ExameSolicitacaoExame ese ");
		hql.append("join ese.solicitacaoExame se ");
		hql.append("join ese.realizacaoExame re ");
		hql.append("left join ese.clinicaAutorizada clinica ");
		hql.append("join ese.exame e ");

		hql.append("where se.empresa.id = :empresaId ");
		hql.append(" and re.data between :dataInicio and :dataFim ");
		
		if (clinicaAutorizada != null && clinicaAutorizada.getId() != null)
			hql.append(" and clinica.id = :clinicaAutorizadaId ");
		
		if (examesIds != null && examesIds.length > 0)
			hql.append(" and e.id in (:exameIds) ");
		
		if (StringUtils.isNotBlank(resultadoExame))
		{
			if(resultadoExame.equalsIgnoreCase(ResultadoExame.NAO_REALIZADO.toString()))
				hql.append("and (re = null or re.resultado = :resultado) ");
			else
				hql.append("and re.resultado = :resultado ");
		}
		
		hql.append(" group by e.id, e.nome, clinica.id, clinica.nome ");
		hql.append(" order by e.nome, clinica.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataInicio", dataInicio);
		query.setDate("dataFim", dataFim);
		query.setLong("empresaId", empresaId);
		
		if (StringUtils.isNotBlank(resultadoExame))
			query.setString("resultado", resultadoExame);
		
		if (clinicaAutorizada != null && clinicaAutorizada.getId() != null)
			query.setLong("clinicaAutorizadaId", clinicaAutorizada.getId());
		
		if (examesIds != null && examesIds.length > 0)
			query.setParameterList("exameIds", examesIds, Hibernate.LONG);
		
		return query.list();
	}

	private Examinado getExaminado(Character tipoPessoa) {
		
		Map<Character, Examinado> listaTipoPessoa = new HashMap<Character, ExameDaoHibernate.Examinado>();
		listaTipoPessoa.put(TipoPessoa.COLABORADOR.getChave(), new TipoPessoaColaborador());
		listaTipoPessoa.put(TipoPessoa.CANDIDATO.getChave(), new TipoPessoaCandidato());
		listaTipoPessoa.put(TipoPessoa.TODOS.getChave(), new TipoPessoaTodos());
		
		return listaTipoPessoa.get(tipoPessoa);
	}
	
	public Collection<ExamesRealizadosRelatorio> findExamesRealizadosCandidatosAndColaboradores(Long empresaId, String nomeBusca, Date inicio, Date fim, String solicitacaoMotivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds, Character tipoPessoa, boolean transfereExamesCandidatoColaborador)  
	{
		Examinado examinado = getExaminado(tipoPessoa);
		
		StringBuilder sql = new StringBuilder("select ");
		examinado.setSelect(sql);
		sql.append("(case when se.colaborador_id is not null then 'C' else 'A' end) as tipo, ");
		sql.append("e.id as exameId,e.nome as exameNome,se.data,clinica.id as clinicaId,clinica.nome as clinicaNome,re.resultado,se.motivo, ");
		sql.append("re.observacao ");
		sql.append("from ExameSolicitacaoExame ese ");
		sql.append("left join realizacaoExame re on ese.realizacaoExame_id = re.id ");
		sql.append("left join clinicaAutorizada clinica on ese.clinicaAutorizada_id = clinica.id ");
		sql.append("left join exame e on ese.exame_id = e.id ");
		sql.append("left join solicitacaoExame se on ese.solicitacaoExame_id = se.id ");
		
		examinado.setJoins(sql);
		
		sql.append("where se.empresa_id = :empresaId ");
		sql.append("and re.data between :inicio and :fim ");

		if (LongUtil.arrayIsNotEmpty(examesIds))
			sql.append("and e.id in (:exameIds) ");
		
		if (clinicaAutorizadaId != null)
			sql.append("and clinica.id = :clinicaId ");
		
		if (StringUtils.isNotBlank(solicitacaoMotivo))
			sql.append("and se.motivo = :motivo ");
		
		if (StringUtils.isNotBlank(exameResultado))
		{
			if(exameResultado.equalsIgnoreCase(ResultadoExame.NAO_REALIZADO.toString()))
				sql.append("and (re = null or re.resultado = :resultado) ");
			else
				sql.append("and re.resultado = :resultado ");
		}
		
		examinado.setWhere(sql, nomeBusca, estabelecimentosIds, transfereExamesCandidatoColaborador);
		
		sql.append("order by e.nome, clinica.id, se.data ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		
		examinado.setParametros(query);
		
		if (isNotBlank(nomeBusca))
			query.setString("nome", "%" + nomeBusca.toLowerCase() + "%");
		
		query.setDate("inicio", inicio);
		query.setDate("fim", fim);
		query.setLong("empresaId", empresaId);
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			query.setParameterList("estabelecimentoIds", estabelecimentosIds, Hibernate.LONG);
		
		if (LongUtil.arrayIsNotEmpty(examesIds))
			query.setParameterList("exameIds", examesIds, Hibernate.LONG);
		
		if (clinicaAutorizadaId != null)
			query.setLong("clinicaId", clinicaAutorizadaId);
		
		if (StringUtils.isNotBlank(solicitacaoMotivo))
			query.setString("motivo", solicitacaoMotivo);
		
		if (StringUtils.isNotBlank(exameResultado))
			query.setString("resultado", exameResultado);
		
		Collection<Object[]> lista = query.list();

		int i = 0;
		Collection<ExamesRealizadosRelatorio> examesRealizadosRelatorios = new ArrayList<ExamesRealizadosRelatorio>();
		ExamesRealizadosRelatorio examesRealizadosRelatorio;
		
		for (Object[] obj : lista) {
			i = 0;
			examesRealizadosRelatorio = new ExamesRealizadosRelatorio(
					(String) obj[i],
					LongUtil.bigIntegerToLong(obj[++i], null),
					(String) obj[++i],
					(String) obj[++i],
					LongUtil.bigIntegerToLong(obj[++i], null),
					(String) obj[++i],
					(Date) obj[++i],
					LongUtil.bigIntegerToLong(obj[++i], null),
					(String) obj[++i],
					(String) obj[++i],
					(String) obj[++i],
					(String) obj[++i]
					);
			
			examesRealizadosRelatorios.add(examesRealizadosRelatorio);
		}

		return examesRealizadosRelatorios;
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
		// Utilizado para que os inserts do testes sejam executados. Necessário entender o porque disto.
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
		public void setSelect(StringBuilder hql);
		public void setWhere(StringBuilder sql, String nomeBusca, Long[] estabelecimentosIds, boolean transfereExamesCandidatoColaborador);
		public void setParametros(Query query);
	}
	
	private class TipoPessoaColaborador implements Examinado{
		
		public void setSelect(StringBuilder sql) {
			sql.append("colab.nome as nome,");
			sql.append("estabColab.id  as estId, ");
			sql.append("estabColab.nome as estNome, ");

		}
		
		public void setJoins(StringBuilder sql)
		{
			sql.append("left join colaborador colab on se.colaborador_id = colab.id ");
			sql.append("left join historicoColaborador as hc on colab.id = hc.colaborador_id ");
			sql.append("left join estabelecimento as estabColab on hc.estabelecimento_id = estabColab.id ");
			sql.append("left join candidato as cand on se.candidato_id = cand.id ");
		}

		public void setWhere(StringBuilder sql, String nomeBusca, Long[] estabelecimentosIds, boolean transfereExamesCandidatoColaborador)
		{
			if (isNotBlank(nomeBusca))
				sql.append("and lower(colab.nome) like :nome ");

			if (LongUtil.arrayIsNotEmpty(estabelecimentosIds))
				sql.append("and estabColab.id in (:estabelecimentoIds) ");
			
			if(!transfereExamesCandidatoColaborador)
				sql.append(" and se.candidato_id is null ");

			sql.append("and ( ");
			sql.append("	  ( se.motivo = :motivoSolicitacaoExame ");
			sql.append("		and (");
			sql.append("			(se.candidato_id is not null and cand.contratado = true) ");
			sql.append("			or hc.data = (select min(hc2.data) ");
			sql.append("	        	from HistoricoColaborador as hc2 ");
			sql.append("	   			where hc2.status = :status ");
			sql.append("	   			and hc2.colaborador_id = colab.id ) ");
			sql.append("	 		) ");
			sql.append("	  ) or");
			sql.append("	  ( se.motivo != :motivoSolicitacaoExame ");
			sql.append("		   and hc.data = ( select max(hc2.data) ");
			sql.append("	        	from HistoricoColaborador as hc2 ");
			sql.append("	   			where hc2.data <= re.data and hc2.status = :status ");
			sql.append("	   			and hc2.colaborador_id = colab.id )");
			sql.append("	  ) ");
			sql.append("	) ");
		}

		public void setParametros(Query query)
		{
			query.setInteger("status", StatusRetornoAC.CONFIRMADO);
			query.setString("motivoSolicitacaoExame", MotivoSolicitacaoExame.ADMISSIONAL);
		}

	}
	
	private class TipoPessoaCandidato implements Examinado{
		
		public void setSelect(StringBuilder sql) {
			sql.append("distinct cand.nome as nome,");
			sql.append("estabCand.id  as estId, ");
			sql.append("estabCand.nome as estNome, ");

		}
		
		public void setJoins(StringBuilder hql)
		{
			hql.append("inner join candidato cand on se.candidato_id = cand.id ");
			hql.append("left join candidatoSolicitacao as cs on cand.id = cs.candidato_id ");
			hql.append("left join solicitacao as s on cs.solicitacao_id = s.id ");
			hql.append("left join estabelecimento as estabCand on s.estabelecimento_id = estabCand.id ");
		}

		public void setWhere(StringBuilder sql, String nomeBusca, Long[] estabelecimentosIds, boolean transfereExamesCandidatoColaborador)
		{
			if (isNotBlank(nomeBusca))
				sql.append("and lower(cand.nome) like :nome ");

			if (LongUtil.arrayIsNotEmpty(estabelecimentosIds))
				sql.append("and estabCand.id in (:estabelecimentoIds) ");
			
			if(transfereExamesCandidatoColaborador)
				sql.append(" and se.colaborador_id is null ");

			sql.append("and (s.data = ( select max(s2.data) from CandidatoSolicitacao cs2 ");
			sql.append("				inner join solicitacao as s2 on cs2.solicitacao_id = s2.id ");
			sql.append("				where cs2.candidato_id = cand.id ");
			sql.append("				and s2.data <= se.data ");
			sql.append("				and s2.empresa_id = :empresaId) ");
			sql.append("	or s.data is null) "); 
		}

		public void setParametros(Query query)
		{
		}
	}
	
	private class TipoPessoaTodos implements Examinado{
		
		public void setSelect(StringBuilder sql) {
			sql.append("(case when se.colaborador_id is not null then colab.nome else cand.nome end) as nome,");
			sql.append("(case when se.colaborador_id is not null then estabColab.id else estabCand.id end) as estId, ");
			sql.append("(case when se.colaborador_id is not null then estabColab.nome else estabCand.nome end) as estNome, ");

		}
		
		public void setJoins(StringBuilder hql)
		{
			hql.append("left join colaborador colab on se.colaborador_id = colab.id ");
			hql.append("left join historicoColaborador as hc on colab.id = hc.colaborador_id ");
			hql.append("left join estabelecimento as estabColab on hc.estabelecimento_id = estabColab.id ");
			hql.append("left join candidato as cand on se.candidato_id = cand.id ");
			hql.append("left join candidatoSolicitacao as cs on cand.id = cs.candidato_id ");
			hql.append("left join solicitacao as s on cs.solicitacao_id = s.id ");
			hql.append("left join estabelecimento as estabCand on s.estabelecimento_id = estabCand.id ");
		}

		public void setWhere(StringBuilder sql, String nomeBusca, Long[] estabelecimentosIds, boolean transfereExamesCandidatoColaborador)
		{
			if (isNotBlank(nomeBusca)){
				sql.append("and ( ");
				sql.append("    case when colab.id is not null then ");
				sql.append("       lower(colab.nome) ");
				sql.append("    else ");
				sql.append("       lower(cand.nome)  ");
 	 			sql.append("    end ) like :nome ");
			}
			
			if (LongUtil.arrayIsNotEmpty(estabelecimentosIds)){
				sql.append("and ( ");
				sql.append("    case when colab.id is not null then ");
				sql.append("       estabColab.id ");
				sql.append("    else ");
				sql.append("       estabCand.id ");
 	 			sql.append("    end ) in (:estabelecimentoIds) ");
			}

			sql.append("and  ");
			sql.append("    case when colab.id is not null then ");
			sql.append("    	  ( se.motivo = :motivoSolicitacaoExame ");
			sql.append("    		and (");
			sql.append("    			(se.candidato_id is not null and cand.contratado = true) ");
			sql.append("    			or hc.data = (select min(hc2.data) ");
			sql.append("    	        	from HistoricoColaborador as hc2 ");
			sql.append("    	   			where hc2.status = :status ");
			sql.append("    	   			and hc2.colaborador_id = colab.id ) ");
			sql.append("    	 		) ");
			sql.append("    	  ) or");
			sql.append("    	  ( se.motivo != :motivoSolicitacaoExame ");
			sql.append("    		   and hc.data = ( select max(hc2.data) ");
			sql.append("    	        	from HistoricoColaborador as hc2 ");
			sql.append("    	   			where hc2.data <= re.data and hc2.status = :status ");
			sql.append("    	   			and hc2.colaborador_id = colab.id )");
			sql.append("    	  ) ");
			sql.append("    else ");
			sql.append("        (s.data = ( select max(s2.data) from CandidatoSolicitacao cs2 ");
			sql.append("    				inner join solicitacao as s2 on s2.id = cs2.solicitacao_id");
			sql.append("    				where cs2.candidato_id = cand.id ");
			sql.append("    				and s2.data <= se.data ");
			sql.append("    				and s2.empresa_id = :empresaId) ");
			sql.append("    	) or (s.data is null) "); 
			sql.append("    end ");
		}

		public void setParametros(Query query)
		{
			query.setInteger("status", StatusRetornoAC.CONFIRMADO);
			query.setString("motivoSolicitacaoExame", MotivoSolicitacaoExame.ADMISSIONAL);
		}

	}}
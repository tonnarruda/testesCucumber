package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
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
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.model.json.TurmaJson;
import com.fortes.rh.util.LongUtil;

@Component
@SuppressWarnings("unchecked")
public class TurmaDaoHibernate extends GenericDaoHibernate<Turma> implements TurmaDao
{
    public Turma findByIdProjection(Long turmaId)
    {
    	Criteria criteria = getSession().createCriteria(Turma.class, "t");
    	criteria.createCriteria("t.empresa", "e");
    	criteria.createCriteria("t.curso", "c");

    	ProjectionList p = Projections.projectionList().create();

    	p.add(Projections.property("t.id"), "id");
    	p.add(Projections.property("t.descricao"), "descricao");
    	p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
    	p.add(Projections.property("t.dataPrevFim"), "dataPrevFim");
    	p.add(Projections.property("t.instrutor"), "instrutor");
    	p.add(Projections.property("t.custo"), "custo");
    	p.add(Projections.property("t.horario"), "horario");
    	p.add(Projections.property("t.instituicao"), "instituicao");
    	p.add(Projections.property("t.qtdParticipantesPrevistos"), "qtdParticipantesPrevistos");
    	p.add(Projections.property("t.realizada"), "realizada");
    	p.add(Projections.property("t.porTurno"), "porTurno");
    	p.add(Projections.property("t.assinaturaDigitalUrl"), "assinaturaDigitalUrl");
    	p.add(Projections.property("t.empresa.id"), "empresaId");
    	p.add(Projections.property("c.id"), "cursoId");
    	p.add(Projections.property("c.nome"), "cursoNome");
    	p.add(Projections.property("c.cargaHoraria"), "projectionCursoCargaHoraria");
    	p.add(Projections.property("c.percentualMinimoFrequencia"), "projectionCursoPercentualMinimoFrequencia");
    	p.add(Projections.property("e.id"), "projectionEmpresaId");

    	criteria.setProjection(Projections.distinct(p));
    	criteria.add(Expression.eq("t.id", turmaId));

    	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    	criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

    	return (Turma) criteria.uniqueResult();
    }
    

    public Collection<Turma> getTurmaFinalizadas(Long cursoId)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
        criteria.createCriteria("t.curso", "c");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        criteria.setProjection(p);

        criteria.add(Expression.eq("c.id", cursoId));
        criteria.add(Expression.eq("t.realizada", true));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

        return criteria.list();
	}

	public List filtroRelatorioByAreas(LinkedHashMap parametros)
	{
		String hql = " select ao.nome, c.nome, t.dataPrevFim, t.custo," +
					 " (" +
					 "		select count(ct2.id) " +
					 "		from ColaboradorTurma ct2 " +
					 "		inner join ct2.turma t2 " +
					 "		where t2.id = t.id " +
					 "	), ao.id " +
					 " from Turma t " +
					 " inner join t.curso c " +
					 " inner join t.colaboradorTurmas ct " +
					 " inner join ct.colaborador co " +
					 " inner join co.historicoColaboradors hc " +
					 " inner join hc.areaOrganizacional ao " +
					 " where ao.id in (:areasId) " +
					 " and hc.data = (" +
					 "					select max(hc2.data) " +
					 "					from HistoricoColaborador hc2 " +
					 "					where hc2.data <= t.dataPrevFim " +
					 "					and hc2.status = :status " +
					 "					and hc2.colaborador.id = co.id " +
					 "				  ) " +
					 " and t.dataPrevFim between :dataIni and :dataFim " +
					 " order by ao.id,c.id,t.id " ;

		Query query = getSession().createQuery(hql);
		query.setDate("dataIni", (Date)parametros.get("dataIni"));
		query.setDate("dataFim", (Date)parametros.get("dataFim"));
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		query.setParameterList("areasId",(Collection<Long>)parametros.get("areas"), StandardBasicTypes.LONG);

		return query.list();
	}

	public List filtroRelatorioByColaborador(LinkedHashMap parametros)
	{
		StringBuilder hql = new StringBuilder(" select ao.nome, c.nome, t.dataPrevFim, t.custo, ");
					 hql.append(" (" );
					 hql.append("		select count(ct2.id) " );
					 hql.append("		from ColaboradorTurma ct2 " );
					 hql.append("		inner join ct2.turma t2 " );
					 hql.append("		where t2.id = t.id " );
					 hql.append("	), ao.id " );
					 hql.append(" from Turma t " );
					 hql.append(" inner join t.curso c " );
					 hql.append(" inner join t.colaboradorTurmas ct " );
					 hql.append(" inner join ct.colaborador co " );
					 hql.append(" inner join co.historicoColaboradors hc " );
					 hql.append(" inner join hc.areaOrganizacional ao " );
					 hql.append(" where co.id = (:colaboradorId) " );
					 hql.append(" and hc.data = (" );
					 hql.append("					select max(hc2.data) " );
					 hql.append("					from HistoricoColaborador hc2 " );
					 hql.append("					where hc2.data <= t.dataPrevFim and hc2.status = :status " );
					 hql.append("					and hc2.colaborador.id = co.id " );
					 hql.append("			) " );
					 hql.append(" and t.dataPrevFim between :dataIni and :dataFim " );
					 hql.append(" order by ao.id,c.id,t.id " );

			Query query = getSession().createQuery(hql.toString());
			query.setDate("dataIni", (Date)parametros.get("dataIni"));
			query.setDate("dataFim", (Date)parametros.get("dataFim"));
			query.setInteger("status", StatusRetornoAC.CONFIRMADO);

			Colaborador colaborador = null;
			colaborador = (Colaborador)parametros.get("colaborador");
			query.setLong("colaboradorId", colaborador.getId());

			return query.list();
	}
	
	public Collection<Turma> findByTurmasPeriodo(Long[] turmaIds, Date dataIni, Date dataFim, Boolean realizada)
	{
		StringBuilder hql = new StringBuilder("select new Turma(c.id, c.nome, t.id, t.descricao, t.dataPrevIni, t.dataPrevFim, t.custo, count(ct.id), td.descricao, ttd.despesa) from Turma t ");
									hql.append("left join t.colaboradorTurmas as ct ");
									hql.append("join t.curso as c ");
									hql.append("left join t.turmaTipoDespesas as ttd ");
									hql.append("left join ttd.tipoDespesa as td ");
									hql.append("where t.dataPrevIni between :dataIni and :dataFim ");
									hql.append("and t.id in (:turmaIds) ");
									
									if(realizada != null)
										hql.append("and t.realizada = :realizada ");
									
									hql.append("group by c.id, c.nome, t.id, t.descricao, t.dataPrevIni, t.dataPrevFim, t.custo, td.descricao, ttd.despesa ");
									hql.append("order by c.nome, t.descricao, t.id, td.descricao ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setParameterList("turmaIds", turmaIds);
		
		if(realizada != null)
			query.setBoolean("realizada", realizada);
		
		return query.list();
	}

	public Collection<Turma> findAllSelect(Long cursoId)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
        criteria.createCriteria("t.curso", "c");
        criteria.createCriteria("t.turmaAvaliacaoTurmas", "tat", Criteria.LEFT_JOIN);
        criteria.createCriteria("tat.avaliacaoTurma", "a", Criteria.LEFT_JOIN);
        criteria.createCriteria("a.questionario", "q", Criteria.LEFT_JOIN);

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
        p.add(Projections.property("t.dataPrevFim"), "dataPrevFim");
        p.add(Projections.property("t.empresa.id"), "projectionEmpresaId");

        p.add(Projections.property("t.instrutor"), "instrutor");
        p.add(Projections.property("t.realizada"), "realizada");

        criteria.setProjection(p);

        criteria.add(Expression.eq("c.id", cursoId));

        criteria.addOrder(Order.asc("t.descricao"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

        return criteria.list();
	}

	public Collection<Turma> findTurmas(Integer page, Integer pagingSize, Long cursoId, String descricao){
		getSession().flush();
		StringBuffer sql = new StringBuffer("select t.id, t.descricao, t.dataPrevIni, t.dataPrevFim, t.empresa_id, count(a.id) as qtdAvaliacoes, t.instrutor, t.realizada, sat.status ");
		sql.append("from turma t ");
		sql.append("inner join curso c on t.curso_id = c.id "); 
		sql.append("left join turma_avaliacaoturma tat on tat.turma_id = t.id "); 
		sql.append("left join avaliacaoturma a on tat.avaliacaoturma_id = a.id "); 
		sql.append("left join questionario q on a.questionario_id = q.id ");
		sql.append("left join situacaoavaliacaoturma sat on sat.turma_id = t.id "); 
		sql.append("where c.id = :cursoId ");
		if (StringUtils.isNotBlank(descricao))
			sql.append("and t.descricao ilike :descricao ");
		
		sql.append("group by t.id, t.descricao, t.dataPrevIni, t.dataPrevFim, t.empresa_id, t.instrutor, t.realizada, sat.status ");
		sql.append("order by t.dataPrevIni desc, t.descricao ");
		if (pagingSize != null && pagingSize != 0)
			sql.append("offset :offset limit :limit ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setLong("cursoId", cursoId);
		if (StringUtils.isNotBlank(descricao))
			query.setString("descricao", "%" + descricao + "%");
		if (pagingSize != null && pagingSize != 0){
			query.setInteger("offset", ((page - 1) * pagingSize));
			query.setInteger("limit", pagingSize);
		}
		
		return montaTurma(query.list());
	}

	private Collection<Turma> montaTurma(List<Object[]> result)	throws NumberFormatException {
		SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");
		Collection<Turma> turmas = new ArrayList<Turma>();
		Turma turma;
		int i;
		
		for (Object[] obj : result) {
			i = 0;
			turma = new Turma();
			turma.setId(((BigInteger)obj[i++]).longValue());
			turma.setDescricao(((String)obj[i++]));
			
			try {
				turma.setDataPrevIni(sDF.parse(obj[i++].toString()));
			} catch (Exception e) {e.printStackTrace();}
			
			try {
				turma.setDataPrevFim(sDF.parse(obj[i++].toString()));
			} catch (Exception e) {e.printStackTrace();}
			
			turma.setEmpresaId(((BigInteger)obj[i++]).longValue());
			turma.setQtdAvaliacoes(new Integer(obj[i++].toString()));
			turma.setInstrutor((String)obj[i++]);
			turma.setRealizada(new Boolean(obj[i++].toString()));
			turma.setStatus(((String)obj[i++]).charAt(0));			
			
			turmas.add(turma);
		}
		return turmas;
	}

	public Collection<Turma> findPlanosDeTreinamento(int page, int pagingSize, Long cursoId, Date dataIni, Date dataFim, Boolean realizada, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
		criteria.createCriteria("t.curso", "c");

		ProjectionList p = Projections.projectionList().create();

        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
        p.add(Projections.property("t.dataPrevFim"), "dataPrevFim");
        p.add(Projections.property("t.empresa.id"), "projectionEmpresaId");
        p.add(Projections.property("t.horario"), "horario");
        p.add(Projections.property("t.qtdParticipantesPrevistos"), "qtdParticipantesPrevistos");
        p.add(Projections.property("t.custo"), "custo");
        p.add(Projections.property("t.instrutor"), "instrutor");
        p.add(Projections.property("t.realizada"), "realizada");
        p.add(Projections.property("c.id"), "cursoId");
        p.add(Projections.property("c.nome"), "cursoNome");
        p.add(Projections.property("c.cargaHoraria"), "projectionCursoCargaHoraria");

        criteria.setProjection(p);

		if (cursoId != null)
			criteria.add(Expression.eq("c.id", cursoId));

		if (dataIni != null && dataFim != null)
		{
			criteria.add(Expression.or( 
							Expression.or(Expression.between("t.dataPrevIni", dataIni, dataFim), Expression.between("t.dataPrevFim", dataIni, dataFim)), 
							Expression.and(Expression.le("t.dataPrevIni", dataIni), Expression.ge("t.dataPrevFim", dataFim))
						));
		}

        if (realizada != null)
        	criteria.add(Expression.eq("t.realizada", realizada));

		if (empresaId != null)
			criteria.add(Expression.eq("t.empresa.id", empresaId));

        if(pagingSize != 0)
        {
        	criteria.setFirstResult(((page - 1) * pagingSize));
        	criteria.setMaxResults(pagingSize);
        }

        criteria.addOrder(Order.desc("t.dataPrevIni"));
        criteria.addOrder(Order.asc("c.nome"));
        criteria.addOrder(Order.asc("t.descricao"));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

        return criteria.list();
	}

	public Integer countPlanosDeTreinamento(Long cursoId, Date dataIni, Date dataFim, Boolean realizada)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
		criteria.createCriteria("t.curso", "c");

		criteria.setProjection(Projections.rowCount());

		if (cursoId != null)
			criteria.add(Expression.eq("c.id", cursoId));

		if (dataIni != null)
			criteria.add(Expression.ge("t.dataPrevIni", dataIni));

		if (dataFim != null)
			criteria.add(Expression.le("t.dataPrevFim", dataFim));

		if (realizada != null)
			criteria.add(Expression.eq("t.realizada", realizada));

		return (Integer) criteria.uniqueResult();
	}

	public void updateRealizada(Long turmaId, boolean realizada)throws Exception
	{
		String hql = "update Turma set realizada = :realizada where id = :turmaId";

		Query query = getSession().createQuery(hql);
		query.setLong("turmaId", turmaId);
		query.setBoolean("realizada", realizada);

		query.executeUpdate();
	}

	public void updateCusto(Long turmaId, double custo) {
		String hql = "update Turma set custo = :custo where id = :turmaId";

		Query query = getSession().createQuery(hql);
		query.setLong("turmaId", turmaId);
		query.setDouble("custo", custo);

		query.executeUpdate();
	}


	public Collection<Turma> findByFiltro(Date dataPrevIni, Date dataPrevFim, Boolean realizada, Long[] empresaIds, Long[] cursoIds)
	{
        Criteria criteria = getSession().createCriteria(Turma.class,"t");
        criteria.createCriteria("t.curso", "c");
        criteria.createCriteria("c.empresa", "e");
        criteria.createCriteria("c.empresasParticipantes", "ep", Criteria.LEFT_JOIN);

        ProjectionList p = Projections.projectionList().create();

        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
        p.add(Projections.property("c.nome"), "cursoNome");

        criteria.setProjection(Projections.distinct(p));
        
        if(empresaIds != null && empresaIds.length > 0)
        	criteria.add(Expression.or(Expression.in("c.empresa.id", empresaIds),Expression.in("ep.id", empresaIds)));

		if (dataPrevIni != null)
			criteria.add(Expression.ge("t.dataPrevIni", dataPrevIni));

		if (dataPrevFim != null)
			criteria.add(Expression.le("t.dataPrevFim", dataPrevFim));

		if (realizada != null)
			criteria.add(Expression.eq("t.realizada", realizada));
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			criteria.add(Expression.in("c.id", cursoIds));

		criteria.addOrder(Order.desc("t.dataPrevIni"));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("t.descricao"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

        return criteria.list();
	}

	public Collection<Turma> findByCursos(Long[] cursoIds)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
		criteria.createCriteria("t.curso", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("t.id"), "id");
		p.add(Projections.property("t.descricao"), "descricao");
		p.add(Projections.property("c.nome"), "cursoNome");
		
		criteria.setProjection(p);
		
		if (cursoIds != null)
			criteria.add(Expression.in("c.id", cursoIds));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("t.descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));
		
		return criteria.list();
	}

	public Collection<Turma> findByIdProjection(Long[] ids)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
        criteria.createCriteria("t.curso", "c");
        criteria.createCriteria("t.empresa", "e");

        ProjectionList p = Projections.projectionList().create();

        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
        p.add(Projections.property("t.dataPrevFim"), "dataPrevFim");
        p.add(Projections.property("t.instrutor"), "instrutor");
        p.add(Projections.property("t.horario"), "horario");
        p.add(Projections.property("t.instituicao"), "instituicao");
        p.add(Projections.property("c.id"), "cursoId");
        p.add(Projections.property("c.nome"), "cursoNome");

        criteria.setProjection(p);
       	criteria.add(Expression.in("t.id", ids));

        criteria.addOrder(Order.asc("t.dataPrevIni"));
        criteria.addOrder(Order.asc("c.nome"));
        criteria.addOrder(Order.asc("t.descricao"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

		return criteria.list();
	}
	
	 public Integer quantidadeParticipantesPrevistos(Date dataIni, Date dataFim, Long[] empresasIds, Long[] cursosIds)
	 {
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
		criteria.createCriteria("t.empresa", "e");

        criteria.setProjection(Projections.sum("t.qtdParticipantesPrevistos"));
        criteria.add(Expression.ge("t.dataPrevIni", dataIni));
        criteria.add(Expression.le("t.dataPrevFim", dataFim));
        criteria.add(Expression.in("e.id", empresasIds));
        
        if (LongUtil.arrayIsNotEmpty(cursosIds))
        	criteria.add(Expression.in("t.curso.id", cursosIds));

        Integer valor = (Integer) criteria.uniqueResult();
        if(valor == null)
        	return 0;
        else
        	return valor;
	}
	 
	public Integer quantidadeParticipantesPresentes(Date dataIni, Date dataFim, Long[] empresasIds, Long[] areasIds, Long[] cursosIds, Long[] estabelecimentosIds) 
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"))
				.add(Restrictions.le("hc2.data", new Date()));
		
		Criteria criteria = getSession().createCriteria(ColaboradorPresenca.class,"cp");
		criteria.createCriteria("cp.colaboradorTurma", "ct");
		criteria.createCriteria("ct.turma", "t");
		criteria.createCriteria("t.empresa", "e");
		criteria.createCriteria("ct.colaborador", "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");

        criteria.setProjection(Projections.countDistinct("cp.colaboradorTurma.id"));
        criteria.add(Expression.ge("t.dataPrevIni", dataIni));
        criteria.add(Expression.le("t.dataPrevFim", dataFim));
        criteria.add(Expression.in("e.id", empresasIds));
        
        criteria.add(Subqueries.propertyEq("hc.data", subQueryHc));
        
        if (LongUtil.arrayIsNotEmpty(areasIds))
        	criteria.add(Expression.in("hc.areaOrganizacional.id", areasIds));
        
        if (LongUtil.arrayIsNotEmpty(cursosIds))
        	criteria.add(Expression.in("t.curso.id", cursosIds));
        
        if (LongUtil.arrayIsNotEmpty(estabelecimentosIds)){
        	criteria.add(Expression.in("hc.estabelecimento.id", estabelecimentosIds));
		}

        Integer valor = (Integer) criteria.uniqueResult();
        
        if (valor == null)
        	return 0;
        else
        	return valor;
	}
		
	public Collection<Turma> findByEmpresaOrderByCurso(Long empresaId){
		
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
        criteria.createCriteria("t.curso", "c");

        ProjectionList p = Projections.projectionList().create();

        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
        p.add(Projections.property("t.dataPrevFim"), "dataPrevFim");
        p.add(Projections.property("t.instrutor"), "instrutor");
        p.add(Projections.property("t.horario"), "horario");
        p.add(Projections.property("t.custo"), "custo");
        p.add(Projections.property("t.instituicao"), "instituicao");
        p.add(Projections.property("t.realizada"), "realizada");
        p.add(Projections.property("t.qtdParticipantesPrevistos"), "qtdParticipantesPrevistos");
        
		p.add(Projections.property("c.id"), "cursoId");
		p.add(Projections.property("c.nome"), "cursoNome");
		p.add(Projections.property("c.conteudoProgramatico"), "projectionCursoConteudoProgramatico");
		p.add(Projections.property("c.criterioAvaliacao"), "projectionCursoCriterioAvaliacao");
		p.add(Projections.property("c.cargaHoraria"), "projectionCursoCargaHoraria");
		p.add(Projections.property("c.percentualMinimoFrequencia"), "projectionCursoPercentualMinimoFrequencia");
		
        criteria.setProjection(p);
        criteria.add(Expression.eq("t.empresa.id", empresaId));

        criteria.addOrder(Order.asc("c.id"));//não alterar a ordem
        criteria.addOrder(Order.asc("t.id"));//não alterar a ordem

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

		return criteria.list();
	}


	public Double somaCustosNaoDetalhados(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds) 
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(TurmaTipoDespesa.class, "ttd");
        
		ProjectionList pSub = Projections.projectionList().create();
		pSub.add(Projections.property("ttd.turma.id"), "id");
        
		subQuery.setProjection(pSub);
        subQuery.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		Criteria criteria = getSession().createCriteria(Turma.class,"t");

        criteria.setProjection(Projections.sum("t.custo"));
        criteria.add(Expression.ge("t.dataPrevIni", dataIni));
        criteria.add(Expression.le("t.dataPrevFim", dataFim));
        if(LongUtil.arrayIsNotEmpty(empresaIds))
        	criteria.add(Expression.in("t.empresa.id", empresaIds));
        criteria.add(Subqueries.propertyNotIn("t.id", subQuery));
        
        if (LongUtil.arrayIsNotEmpty(cursoIds))
        	criteria.add(Expression.in("t.curso.id", cursoIds));
        
        criteria.add(Expression.eq("t.realizada", true));

    	Double valor = (Double) criteria.uniqueResult();
        
    	return valor == null ? 0.0 : valor;
	}


	public Double somaCustos(Date dataIni, Date dataFim, Long[] empresaIds) {
		
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
		
		criteria.setProjection(Projections.sum("t.custo"));
		criteria.add(Expression.ge("t.dataPrevIni", dataIni));
		criteria.add(Expression.le("t.dataPrevFim", dataFim));
		criteria.add(Expression.in("t.empresa.id", empresaIds));

    	Double valor = (Double) criteria.uniqueResult();
        
    	return valor == null ? 0.0 : valor;
	}

	public Collection<TurmaJson> getTurmasJson(String baseCnpj, Long turmaId, char realizada) {
		StringBuilder hql = new StringBuilder();
		hql.append("select new com.fortes.rh.model.json.TurmaJson(t.id, c.nome, t.descricao, t.dataPrevIni, t.dataPrevFim, t.realizada, t.qtdParticipantesPrevistos, ");
		hql.append("e.id, e.nome, e.cnpj) ");
		
		hql.append("from Turma as t ");
		hql.append("inner join t.curso c ");
		hql.append("left join c.empresa e ");
		hql.append("left join c.empresasParticipantes ep ");
		hql.append("where 1=1");
		
		if(baseCnpj!= null && !baseCnpj.isEmpty())
			hql.append(" and (e.cnpj = :baseCnpj or ep.cnpj = :baseCnpj) ");
		
		if(turmaId != null)
			hql.append("and t.id = :turmaId ");
		
		if(realizada == 'S')
			hql.append("and t.realizada = true");
		else if(realizada == 'N')
			hql.append("and t.realizada = false");
		
		Query query = getSession().createQuery(hql.toString());
		
		if(baseCnpj!= null && !baseCnpj.isEmpty())
			query.setString("baseCnpj", baseCnpj);

		if(turmaId != null)
			query.setLong("turmaId", turmaId);
		
		return query.list();
	}

	public Collection<Turma> getTurmasByCursoNotTurmaId(Long cursoId, Long notTurmaId) {
		Criteria criteria = getSession().createCriteria(Turma.class,"t");

        ProjectionList p = Projections.projectionList().create();

        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
		
        criteria.setProjection(p);
        criteria.add(Expression.eq("t.curso.id", cursoId));
        criteria.add(Expression.ne("t.id", notTurmaId));

        criteria.addOrder(Order.asc("t.descricao"));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

		return criteria.list();
	}
}
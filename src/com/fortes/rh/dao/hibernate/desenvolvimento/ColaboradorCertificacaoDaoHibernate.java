package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;

public class ColaboradorCertificacaoDaoHibernate extends GenericDaoHibernate<ColaboradorCertificacao> implements ColaboradorCertificacaoDao
{
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.id"), "id");
		p.add(Projections.property("cc.data"), "data");
		
		criteria.setProjection(p);

		if(colaboradorId != null)
			criteria.add(Expression.eq("cc.colaborador.id",colaboradorId));
		
		criteria.add(Expression.eq("cc.certificacao.id" , certificacaoId));
		criteria.addOrder(Order.desc("cc.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));

		return criteria.list();
	}
	
	public ColaboradorCertificacao findUltimaCertificacaoByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) 
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(ColaboradorCertificacao.class, "cc2")
				.setProjection(Projections.max("cc2.data"))
				.add(Restrictions.eqProperty("cc2.colaborador.id", "cc.colaborador.id"))
				.add(Restrictions.eqProperty("cc2.certificacao.id", "cc.certificacao.id"));
		
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");
		criteria.createCriteria("cc.certificacao", "ct", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.id"), "id");
		p.add(Projections.property("cc.data"), "data");
		p.add(Projections.property("cc.colaborador.id"), "colaboradorId");
		p.add(Projections.property("ct.id"), "certificacaoId");
		p.add(Projections.property("ct.periodicidade"), "certificacaoPeriodicidade");
		p.add(Projections.property("ct.certificacaoPreRequisito.id"), "certificacaoPreRequisitoId");
		
		criteria.setProjection(p);

		criteria.add(Expression.eq("cc.colaborador.id",colaboradorId));
		criteria.add(Expression.eq("cc.certificacao.id" , certificacaoId));

		criteria.add(Subqueries.propertyEq("cc.data", subQuery));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));

		return (ColaboradorCertificacao) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> colaboradoresCertificadosByColaboradorTurmaId(Long colaboradorTurmaId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct cc.certificacaos_id, ct.colaborador_id, cert.certificacaoPreRequisito_id ");
		sql.append("from certificacao_curso cc ");
		sql.append("inner join certificacao cert on cert.id = cc.certificacaos_id ");
		sql.append("inner join colaboradorturma  ct on ct.curso_id = cc.cursos_id ");
		sql.append("where ct.id = :colaboradorTurmaId ");
		sql.append("and verifica_certificacao(cc.certificacaos_id, ct.colaborador_id) ");
		sql.append("order by cc.certificacaos_id, ct.colaborador_id, cert.certificacaoPreRequisito_id ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);
		
		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		Collection<ColaboradorCertificacao> Colaboradores = new ArrayList<ColaboradorCertificacao>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			ColaboradorCertificacao colabs = new ColaboradorCertificacao(((BigInteger)res[0]).longValue(), ((BigInteger)res[1]).longValue(), res[2] != null ? ((BigInteger)res[2]).longValue() : null);
			Colaboradores.add(colabs);
		}

		return Colaboradores;
	}
	
	@SuppressWarnings("unchecked")
	public ColaboradorCertificacao verificaCertificacao(Long colaboradorId, Long certificacaoId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select c.id, :colaboradorId, c.certificacaoPreRequisito_id ");
		sql.append("from certificacao c ");
		sql.append("where verifica_certificacao(:certificacaoId, :colaboradorId) ");
		sql.append("and c.id = :certificacaoId ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("colaboradorId", colaboradorId);
		query.setLong("certificacaoId", certificacaoId);
		
		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		
		ColaboradorCertificacao colaboradorCertificacao = null;
		Iterator<Object[]> it = resultado.iterator();
		
		if (it.hasNext()){
			Object[] res = it.next();
			colaboradorCertificacao = new ColaboradorCertificacao(((BigInteger)res[0]).longValue(), ((BigInteger)res[1]).longValue(), res[2] != null ? ((BigInteger)res[2]).longValue() : null);
		}
		
		return colaboradorCertificacao;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select cc.id as ccId, cc.data as ccData, cert.nome as certNome, cert.periodicidade as certPeri, ao.id as aoId, ao.areaMae_id as aoMaeId, monta_familia_area(ao.id) as areaOrganizacionalNome, ");
		sql.append("co.nome as ColNome, co.email as colEmail, fs.nome as fsNome, ca.nome as caNome ");
		sql.append("from ColaboradorCertificacao cc ");
		sql.append("left join certificacao cert on cert.id = cc.certificacao_id ");
		sql.append("left join colaborador co on co.id = cc.colaborador_id ");
		sql.append("left join historicoColaborador hc on hc.colaborador_id = co.id ");
		sql.append("left join faixaSalarial fs on fs.id = hc.faixaSalarial_id ");
		sql.append("left join cargo ca on ca.id = fs.cargo_id ");
		sql.append("left join areaOrganizacional ao on ao.id = hc.areaOrganizacional_id ");
		sql.append("where co.desligado = false ");
		sql.append("and hc.data = (select max(hc2.data) from historicocolaborador hc2  where hc2.colaborador_id = co.id and hc2.status = 1)  ");
		sql.append("and cert.empresa_id = :empresaId ");
		sql.append("and cert.periodicidade is not null ");
		sql.append("and (cc.data + cast((coalesce(cert.periodicidade,0) || ' month') as interval)) = :data ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("data", data);
		
		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		Collection<ColaboradorCertificacao> Colaboradores = new ArrayList<ColaboradorCertificacao>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			ColaboradorCertificacao colabs = new ColaboradorCertificacao();
			colabs.setId(((BigInteger)res[0]).longValue());
			colabs.setData((Date)res[1]);
			colabs.setCertificacaoNome((String)res[2]);
			colabs.setCertificacaoPeriodicidade(res[3] != null ? (Integer)res[3] : null);
			colabs.setAreaOrganizacionalId(((BigInteger)res[4]).longValue());
			colabs.setAreaOrganizacionalAreaMaeId(res[5] != null ? ((BigInteger)res[5]).longValue() : null);
			colabs.setAreaOrganizacionalNome((String)res[6]);
			colabs.setColaboradorNome((String)res[7]);
			colabs.setColaboradorEmail(res[8]!= null ? (String)res[8] : null);
			colabs.setFaixaSalarialNome((String)res[9]);
			colabs.setCargoNome((String)res[10]);
			
			Colaboradores.add(colabs);
		}

		return Colaboradores;
	}

	public void removeDependenciaDaAvPratica(Long colaboradorCertificacaoId) {
		Query query = getSession().createQuery("update ColaboradorAvaliacaoPratica cap set colaboradorCertificacao.id = null where cap.colaboradorCertificacao.id = :colaboradorCertificacaoId ");
		query.setLong("colaboradorCertificacaoId", colaboradorCertificacaoId);
		
		query.executeUpdate();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<ColaboradorTurma> colaboradoresTurmaCertificados(Long colaboradorId, Long certificacaoId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ct.id as colaboradorTurmaId, t.id as turmaId, t.dataPrevFim as turmaDataPrevFim FROM colaboradorTurma ct ");
		sql.append("INNER JOIN turma t ON t.id = ct.turma_id AND t.dataprevfim = ( (SELECT MAX(dataprevfim) FROM turma t2 WHERE t2.curso_id = t.curso_id AND t2.realizada ) ) ");
		sql.append("INNER JOIN curso c ON c.id = t.curso_id ");
		sql.append("WHERE ct.colaborador_id = :colaboradorId AND t.realizada AND c.id IN ((SELECT cursos_id FROM certificacao_curso WHERE certificacaos_id = :certificacaoId)) ");
		sql.append("AND ct.aprovado ");
		sql.append("order by t.dataprevfim desc");//Não remover importante
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("colaboradorId", colaboradorId);
		query.setLong("certificacaoId", certificacaoId);
		
		List resultado = query.list();
		Collection<ColaboradorTurma> colaboradoresTurma = new ArrayList<ColaboradorTurma>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			ColaboradorTurma colabs = new ColaboradorTurma();
			colabs.setId(((BigInteger)res[0]).longValue());
			colabs.setTurmaId(((BigInteger)res[1]).longValue());
			colabs.setTurmaDataPrevFim((Date)res[2]);
			colaboradoresTurma.add(colabs);
		}
		return colaboradoresTurma;
	}

	@SuppressWarnings("rawtypes")
	public ColaboradorCertificacao findByColaboradorTurma(Long colaboradorTurmaId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct cc.id as id, cc.colaborador_id as colaboradorId, cc.certificacao_id as certificacaoId FROM colaboradorcertificacao cc ");
		sql.append("JOIN colaboradorcertificacao_colaboradorturma cc_ct ON cc_ct.colaboradorcertificacao_id = cc.id WHERE cc_ct.colaboradoresturmas_id = :colaboradorTurmaId ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);
		
		List resultado = query.list();
		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			colaboradorCertificacao.setId(((BigInteger)res[0]).longValue());
			colaboradorCertificacao.setColaboradorId(((BigInteger)res[1]).longValue());
			colaboradorCertificacao.setCertificacaoId(((BigInteger)res[2]).longValue());
		}
		return colaboradorCertificacao;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> colaboradoresQueParticipamDaCertificacao(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, Long[] certificadosId, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds, boolean colaboradorCertificado){
		DetachedCriteria ultimoHistoricoColaborador = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2").setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "hc.colaborador.id")).add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
		
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("c.historicoColaboradors", "hc", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.estabelecimento", "e", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("fs.cargo", "cg", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.INNER_JOIN);
		criteria.createCriteria("c.colaboradorCertificacaos", "cc", (colaboradorCertificado ? Criteria.INNER_JOIN : Criteria.LEFT_JOIN));
		criteria.createCriteria("cc.certificacao", "cert", Criteria.LEFT_JOIN);
		criteria.setProjection(Projections.distinct(montaProjectionColaboradoresQueParticipamDaCertificacao()));
		
		if(areasIds != null && areasIds.length > 0)
			criteria.add(Expression.in("hc.areaOrganizacional.id",areasIds));
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			criteria.add(Expression.in("hc.estabelecimento.id" , estabelecimentosIds));
		if(colaboradoresIds != null && colaboradoresIds.length > 0)
			criteria.add(Expression.in("ct.colaborador.id" , colaboradoresIds));
	    criteria.add(Expression.sqlRestriction("this_.curso_id in (select cursos_id from certificacao_curso where certificacaos_id in ("+ StringUtil.converteArrayToString(StringUtil.LongToString(certificadosId)) + ")) ", new String[]{}, new Type[]{}));
	    criteria.add(Subqueries.propertyEq("hc.data", ultimoHistoricoColaborador));
	    criteria.add(Expression.disjunction().add(Expression.or(Expression.isNull("cc.data"), Subqueries.propertyEq("cc.data", dataChedUltimoColaboradorCertificacao(dataIni, dataFim, mesesCertificacoesAVencer, "cert8_")))));
	    criteria.add(Expression.disjunction().add(Expression.or(Expression.isNull("cc.certificacao.id"), Expression.in("cc.certificacao.id",certificadosId))));
	    criteria.addOrder(Order.asc("c.nome"));
	    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	    criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));
		return criteria.list();
	}

	private ProjectionList montaProjectionColaboradoresQueParticipamDaCertificacao() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.id"), "id");
		p.add(Projections.property("cc.data"), "data");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("c.matricula"), "colaboradorMatricula");
		p.add(Projections.property("e.id"), "estabelecimentoId");
		p.add(Projections.property("e.nome"), "estabelecimentoNome");
		p.add(Projections.property("fs.id"), "faixaSalarialId");
		p.add(Projections.property("fs.nome"), "faixaSalarialNome");
		p.add(Projections.property("cg.id"), "cargoId");
		p.add(Projections.property("cg.nome"), "cargoNome");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.sqlProjection("monta_familia_area(ao6_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNome");
		return p;
	}

	private DetachedCriteria dataChedUltimoColaboradorCertificacao(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, String alias) {
		DetachedCriteria ultimoColaboradorCertificacao = DetachedCriteria.forClass(ColaboradorCertificacao.class, "cc2").setProjection(Projections.max("cc2.data"))
				.add(Restrictions.eqProperty("cc2.colaborador.id", "cc.colaborador.id")).add(Restrictions.eqProperty("cc2.certificacao.id", "cc.certificacao.id"));
				if(dataIni != null) 
					ultimoColaboradorCertificacao.add(Restrictions.ge("cc2.data", dataIni));
				if(dataFim != null) 
					ultimoColaboradorCertificacao.add(Restrictions.le("cc2.data", dataFim));
				if(mesesCertificacoesAVencer != null && mesesCertificacoesAVencer != 0){
					String dataVencimento = DateUtil.formataDiaMesAno(DateUtil.incrementaMes(new Date(), mesesCertificacoesAVencer));
					ultimoColaboradorCertificacao.add(Expression.sqlRestriction("(this0__.data + ("+alias+".periodicidade || ' month')::interval) <= '" + dataVencimento + "' ", new String[]{}, new Type[]{}));
				}
		
		return ultimoColaboradorCertificacao;
	}

	public ColaboradorCertificacao findColaboradorCertificadoInfomandoSeEUltimaCertificacao(Long colaboradorCertificacaoId, Long colaboradorId, Long certificacaoId) {
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.id"), "id");
		p.add(Projections.property("cc.data"), "data");
		p.add(Projections.property("cc.colaborador.id"), "colaboradorId");
		p.add(Projections.property("cc.certificacao.id"), "certificacaoId");
		p.add(Projections.sqlProjection(" case when {alias}.data = (select max(data) from colaboradorcertificacao where colaborador_id = " + colaboradorId + "  and certificacao_id = "+ certificacaoId +" ) "
				+ " and not exists(select * from colaboradoravaliacaopratica where colaborador_id = " + colaboradorId + "  and certificacao_id = "+ certificacaoId +" and colaboradorcertificacao_id is null )"
				+ "then true else false end as ultimaCertificacao ", new String[] {"ultimaCertificacao"}, new Type[] {Hibernate.BOOLEAN}), "ultimaCertificacao");
	        
		criteria.setProjection(p);
		criteria.add(Expression.eq("cc.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("cc.certificacao.id", certificacaoId));
		criteria.add(Expression.eq("cc.id", colaboradorCertificacaoId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));

		return (ColaboradorCertificacao) criteria.uniqueResult();
	}

	public Date getMaiorDataDasTurmasDaCertificacao(Long colaboradorCertificacaoId) {
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");
		criteria.createCriteria("cc.colaboradoresTurmas", "ccct", Criteria.INNER_JOIN);
		criteria.createCriteria("ccct.turma", "t", Criteria.INNER_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.max("t.dataPrevFim"));
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("cc.id", colaboradorCertificacaoId));

		return  (Date) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> findColaboradorCertificadoEmUmaTurmaPosterior(Long turmaId, Long colaboradorId) {
		StringBuilder sql = new StringBuilder();
		sql.append("WITH colaboradorescertificadosNaTurma AS ( ");
		sql.append("		SELECT cc.id, cc.certificacao_id, cc.colaborador_id, cc.data FROM colaboradorcertificacao cc ");
		sql.append("																	 JOIN colaboradorcertificacao_colaboradorturma cc_ct ON cc_ct.colaboradorcertificacao_id = cc.id ");
		sql.append("																	 WHERE cc_ct.colaboradoresturmas_id in( ");
		sql.append("																											(select id from colaboradorturma where turma_id = :turmaId) ");
		sql.append("																										   )");
		sql.append("		) ");
		sql.append("SELECT  cc.id, cc.certificacao_id, cc.colaborador_id, cc.data FROM colaboradorcertificacao cc ");
		sql.append("															  JOIN colaboradorescertificadosNaTurma cct ON cc.certificacao_id = cct.certificacao_id ");
		sql.append("																										   AND cc.colaborador_id = cct.colaborador_id AND cc.data > cct.data ");
		if(colaboradorId != null)
			sql.append("WHERE cc.colaborador_id = :colaboradorId");
		
		Query q = getSession().createSQLQuery(sql.toString());
		q.setLong("turmaId", turmaId);
		
		if(colaboradorId != null)
			q.setLong("colaboradorId", colaboradorId);
		
		Collection<Object[]> resultado = q.list();
		Collection<ColaboradorCertificacao> colaboradoresCertificados = new ArrayList<ColaboradorCertificacao>();
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			colaboradoresCertificados.add(new ColaboradorCertificacao(((BigInteger)res[0]).longValue(), ((BigInteger)res[1]).longValue(), ((BigInteger)res[2]).longValue(), ((Date)res[3])));
		}
		return colaboradoresCertificados;	
	}

	public Collection<ColaboradorCertificacao> findColaboradorCertificacaoPreRequisito(Long colaboradorCertificacaoId) {
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.id"), "id");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("cc.colaboradorCertificacaoPreRequisito.id",colaboradorCertificacaoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));

		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> findColaboradoresCertificados(Date dataIni, Date dataFim, Integer mesesCertificacoesAVencer, Long[] certificacoesIds, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds){
		DetachedCriteria ultimoHistoricoColaborador = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2").setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "hc.colaborador.id")).add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
		
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");
		criteria.createCriteria("cc.colaborador", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("c.historicoColaboradors", "hc", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.estabelecimento", "e", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("fs.cargo", "cg", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.INNER_JOIN);
		criteria.createCriteria("cc.certificacao", "cert", Criteria.INNER_JOIN);
		criteria.setProjection(Projections.distinct(montaProjectionColaboradoresQueParticipamDaCertificacao()));
		
		if(areasIds != null && areasIds.length > 0)
			criteria.add(Expression.in("hc.areaOrganizacional.id",areasIds));
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			criteria.add(Expression.in("hc.estabelecimento.id" , estabelecimentosIds));
		if(colaboradoresIds != null && colaboradoresIds.length > 0)
			criteria.add(Expression.in("c.id" , colaboradoresIds));
		
		criteria.add(Expression.in("cc.certificacao.id" , certificacoesIds));
	    criteria.add(Subqueries.propertyEq("hc.data", ultimoHistoricoColaborador));

	    criteria.add(Expression.disjunction().add(Expression.or(Expression.isNull("cc.data"), Subqueries.propertyEq("cc.data", dataChedUltimoColaboradorCertificacao(dataIni, dataFim, mesesCertificacoesAVencer, "cert7_")))));
	    criteria.add(Expression.disjunction().add(Expression.or(Expression.isNull("cc.certificacao.id"), Expression.in("cc.certificacao.id",certificacoesIds))));
	    criteria.addOrder(Order.asc("c.nome"));
	    
	    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	    criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> findColaboradoresQueParticipamDaCertificacao(Long[] certificacoesId, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds){
		DetachedCriteria ultimoHistoricoColaborador = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2").setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "hc.colaborador.id")).add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
		
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("c.historicoColaboradors", "hc", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.estabelecimento", "e", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("fs.cargo", "cg", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.INNER_JOIN);
		criteria.createCriteria("c.colaboradorCertificacaos", "cc", Criteria.LEFT_JOIN);

		criteria.setProjection(Projections.distinct(montaProjectionColaboradoresQueParticipamDaCertificacao()));
		
		if(areasIds != null && areasIds.length > 0)
			criteria.add(Expression.in("hc.areaOrganizacional.id",areasIds));
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			criteria.add(Expression.in("hc.estabelecimento.id" , estabelecimentosIds));
		if(colaboradoresIds != null && colaboradoresIds.length > 0)
			criteria.add(Expression.in("c.id" , colaboradoresIds));
		
		criteria.add(Expression.sqlRestriction("{alias}.curso_id in (select cursos_id from certificacao_curso where certificacaos_id in("+ StringUtil.converteArrayToString(StringUtil.LongToString(certificacoesId)) + ")) ", new String[]{}, new Type[]{}));
	    criteria.add(Subqueries.propertyEq("hc.data", ultimoHistoricoColaborador));

	    criteria.addOrder(Order.asc("c.nome"));
	    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	    criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));
		return criteria.list();
	}
}
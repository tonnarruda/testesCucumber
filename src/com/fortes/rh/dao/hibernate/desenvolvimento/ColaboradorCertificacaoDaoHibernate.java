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
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;

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
		criteria.addOrder(Order.asc("cc.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));

		return criteria.list();
	}
	
	public ColaboradorCertificacao findUltimaCertificacaoByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) 
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(ColaboradorCertificacao.class, "cc2")
				.setProjection(Projections.max("cc2.data"))
				.add(Restrictions.eqProperty("cc2.colaborador.id", "cc.colaborador.id"));
		
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.id"), "id");
		p.add(Projections.property("cc.data"), "data");
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
	public ColaboradorCertificacao colaboradorCertificadoByColaboradorIdAndCertificacaId(Long colaboradorId, Long certificacaoId) 
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
	
	public Collection<ColaboradorCertificacao> getColaboradorCertificadoFilhas(Long[] colaboradorCertificacaoIds, Long colaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.id"), "id");
		p.add(Projections.property("cc.data"), "data");
		p.add(Projections.property("cc.certificacao"), "certificacao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cc.colaborador.id",colaboradorId));
		criteria.add(Expression.in("cc.certificacao.id" , colaboradorCertificacaoIds));
		criteria.add(Expression.not(Expression.in("cc.id" , colaboradorCertificacaoIds)));
		
		criteria.addOrder(Order.asc("cc.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));

		return criteria.list();
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

	public void removeDependencias(Long colaboradorCertificacaoId) {
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
		sql.append("AND verifica_aprovacao(c.id, t.id, ct.id, c.percentualminimofrequencia) order by t.dataprevfim desc");
		
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
	
	public Collection<ColaboradorCertificacao> colaboradoresQueParticipaDoCertificado(Date dataFim, Long certificadoId, Long[] areasIds, Long[] estabelecimentosIds, Long[] colaboradoresIds) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select cc.id as ccId, cc.data as ccData, ");
		sql.append("ct.id as ctId, ct.nome as certNome, ct.periodicidade as certPer, ");
		sql.append("cp.colaborador_id as ColId, cp.matricula, cp.nome as ColNome,  cp.nomeComercial, ");
		sql.append("est.id as estId, est.nome as estNome, ");
		sql.append("cg.id as cgId, cg.nome as cgNome, ");
		sql.append("ao.id as aoId, monta_familia_area(ao.id) as aoNome ");
		sql.append("from (WITH colaboradorNoCursoDaCertificacao as ( ");
		sql.append("select distinct ct.colaborador_id, cu.id from colaboradorturma ct ");
		sql.append("inner join turma t on t.id = ct.turma_id ");
		sql.append("inner join curso cu on cu.id = t.curso_id ");
		sql.append("where cu.id in (select cursos_id from certificacao_curso where certificacaos_id = :certificadoId) ");
		
		if(colaboradoresIds != null && colaboradoresIds.length > 0)
			sql.append("and ct.colaborador_id in (:colaboradoresIds) ");

		sql.append("order by ct.colaborador_id) ");
		sql.append("select ccc.colaborador_id, c.nome, c.matricula, c.nomecomercial, hc.faixasalarial_id, hc.estabelecimento_id, hc.areaorganizacional_id ");
		sql.append("from colaboradorNoCursoDaCertificacao ccc ");
		sql.append("inner join colaborador c on c.id = ccc.colaborador_id ");
		sql.append("inner join historicocolaborador hc on  hc.colaborador_id = ccc.colaborador_id ");
		sql.append("where hc.data = (select max(data) from historicocolaborador hc2 where hc2.colaborador_id = ccc.colaborador_id) ");
		sql.append("and hc.status = :status ");
		
		if(areasIds != null && areasIds.length > 0)
			sql.append("and hc.areaorganizacional_id in (:areasIds) ");
		
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			sql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		
		sql.append("group by ccc.colaborador_id, c.nome, c.matricula, c.nomecomercial, hc.faixasalarial_id, hc.estabelecimento_id, hc.areaorganizacional_id ");
		sql.append("having count(ccc.colaborador_id) = (select count(cursos_id) from certificacao_curso where certificacaos_id = :certificadoId) ");
		sql.append("order by c.nome) as cp ");
		sql.append("left join colaboradorcertificacao cc on cc.colaborador_id = cp.colaborador_id and cc.certificacao_id = :certificadoId ");
		sql.append("and cc.data = (select max(cc2.data) from colaboradorcertificacao cc2 where cc2.colaborador_id = cp.colaborador_id ");
		
		if(dataFim != null)
			sql.append("and cc2.data <= :dataFim ");
		
		sql.append(") left join certificacao ct on ct.id = :certificadoId ");
		sql.append("left join faixasalarial fx on fx.id = cp.faixasalarial_id ");
		sql.append("left join cargo cg on cg.id = fx.cargo_id ");
		sql.append("left join estabelecimento est on est.id = cp.estabelecimento_id ");
		sql.append("left join areaOrganizacional ao on ao.id = cp.areaorganizacional_id ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("certificadoId", certificadoId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if(dataFim != null)
			query.setDate("dataFim", dataFim);
		
		if(areasIds != null && areasIds.length >0)
			query.setParameterList("areasIds", areasIds);
		
		if(estabelecimentosIds != null && estabelecimentosIds.length >0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds);
		
		if(colaboradoresIds != null && colaboradoresIds.length >0)
			query.setParameterList("colaboradoresIds", colaboradoresIds);
		
		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		Collection<ColaboradorCertificacao> ColaboradoresCertificacao = new ArrayList<ColaboradorCertificacao>();

		for(Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			
			ColaboradorCertificacao colabs = new ColaboradorCertificacao();
			colabs.setId(res[0] != null ? ((BigInteger)res[0]).longValue() : null);
			colabs.setData(res[1] != null ? (Date)res[1] : null);
			colabs.setCertificacaoId(res[2] != null ? ((BigInteger)res[2]).longValue() : null);
			colabs.setCertificacaoNome(res[3] != null ? (String)res[3] : null);
			colabs.setCertificacaoPeriodicidade(res[4] != null ? (Integer)res[4] : null);
			colabs.setColaboradorId(((BigInteger)res[5]).longValue());
			colabs.setColaboradorMatricula(res[6]!= null ? (String)res[6] : null);
			colabs.setColaboradorNome(res[7]!= null ? (String)res[7] : null);
			colabs.setColaboradorNomeComercial(res[8]!= null ? (String)res[8] : null);
			colabs.setEstabelecimentoId(res[9] != null ? ((BigInteger)res[9]).longValue() : null);
			colabs.setEstabelecimentoNome(res[10]!= null ? (String)res[10] : null);
			colabs.setCargoId(res[11] != null ? ((BigInteger)res[11]).longValue() : null);
			colabs.setCargoNome(res[12]!= null ? (String)res[12] : null);
			colabs.setAreaOrganizacionalId(res[13] != null ? ((BigInteger)res[13]).longValue() : null);
			colabs.setAreaOrganizacionalNome(res[14]!= null ? (String)res[14] : null);
			
			ColaboradoresCertificacao.add(colabs);
		}

		return ColaboradoresCertificacao;
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
}
package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.TipoCertificacao;

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

	public Collection<ColaboradorCertificacao> colaboradoresCertificados(Date dataIni, Date dataFim, char filtroCetificacao, Long[] areasIds, Long[] estabelecimentosIds, Long[] certificacoesIds) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct c.id as colabId, c.nome as colabNome, c.nomecomercial as colabNomeComercial, c.matricula as colabMatricula, estab.id as estabelecimentoId, estab.nome as estabelecimentoNome, cg.id as cargoId, cg.nome as cargoNome, ");
		sql.append("cert.id as certId, cert.nome as certNome, cert.periodicidade as certPeriodicidade, ccert.data as certData, ccert.id as  colaboradorcertificacaoId ");
		sql.append("from colaboradorcertificacao ccert ");
		sql.append("inner join colaborador c on c.id = ccert.colaborador_id ");
		sql.append("inner join certificacao cert on cert.id = ccert.certificacao_id  ");
		sql.append("inner join historicocolaborador hc on hc.colaborador_id = c.id  ");
		sql.append("inner join faixasalarial fx on fx.id = hc.faixasalarial_id  ");
		sql.append("inner join cargo cg  on cg.id = fx.cargo_id  ");
		sql.append("inner join estabelecimento estab on estab.id = hc.estabelecimento_id  ");
		sql.append("where desligado = false  ");
		sql.append("and hc.data = (select max(hc2.data) from historicocolaborador hc2  where hc2.colaborador_id = c.id and hc2.status = 1  )  ");
		sql.append("and cert.id in (:certificacoesIds) ");
		sql.append("and ccert.data between :dataIni and :dataFim ");
		sql.append("and cert.periodicidade is not null ");
		
		if(filtroCetificacao == TipoCertificacao.VENCIDA)
			sql.append("and ccert.data + cast((coalesce(cert.periodicidade,0) || ' month') as interval) <= :dataFim ");
		else if(filtroCetificacao == TipoCertificacao.AVENCER)
			sql.append("and ccert.data + cast((coalesce(cert.periodicidade,0) || ' month') as interval) > :dataFim ");
		
		if(areasIds != null && areasIds.length >0)
			sql.append("and hc.areaorganizacional_id in (:areasOrganizacionaisIds) ");
		
		if(estabelecimentosIds != null && estabelecimentosIds.length >0)
			sql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		
		sql.append("order by c.nome, cert.nome ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameterList("certificacoesIds", certificacoesIds);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		
		if(areasIds != null && areasIds.length >0)
			query.setParameterList("areasOrganizacionaisIds", areasIds);
		
		if(estabelecimentosIds != null && estabelecimentosIds.length >0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds);
		
		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		Collection<ColaboradorCertificacao> Colaboradores = new ArrayList<ColaboradorCertificacao>();
		
		for (@SuppressWarnings("unchecked")
		Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			ColaboradorCertificacao colabs = new ColaboradorCertificacao(((BigInteger)res[0]).longValue(), (String)res[1], (String)res[2], (String)res[3], ((BigInteger)res[4]).longValue(), (String)res[5], ((BigInteger)res[6]).longValue(), (String)res[7], ((BigInteger)res[8]).longValue(), (String)res[9], (Integer)res[10], (Date)res[11], ((BigInteger)res[12]).longValue());
			Colaboradores.add(colabs);
		}

		return Colaboradores;
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
		sql.append("INNER JOIN turma t ON t.id = ct.turma_id AND t.dataprevfim = ( (SELECT MAX(dataprevfim) FROM turma t2 WHERE t2.curso_id = t.curso_id AND t2.realizada AND t2.id = ct.turma_id) ) ");
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
}
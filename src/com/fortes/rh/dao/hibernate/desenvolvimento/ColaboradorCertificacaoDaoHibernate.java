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
import com.fortes.rh.model.dicionario.StatusRetornoAC;
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

	public Collection<ColaboradorCertificacao> colaboradoresCertificados(Date dataIni, Date dataFim, char filtroCetificacao, Long[] areasIds, Long[] estabelecimentosIds, Long[] certificacoesIds) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct c.id as colabId, c.nome as colabNome, c.nomecomercial as colabNomeComercial, c.matricula as colabMatricula, cg.id as cargoId, cg.nome as cargoNome, ");
		sql.append("cert.id as certId, cert.nome as certNome, cert.periodicidade as certPeriodicidade, ccert.data as certData, ccert.id as  colaboradorcertificacaoId ");
		sql.append("from colaboradorcertificacao ccert ");
		sql.append("inner join colaborador c on c.id = ccert.colaborador_id ");
		sql.append("inner join certificacao cert on cert.id = ccert.certificacao_id  ");
		sql.append("inner join historicocolaborador hc on hc.colaborador_id = c.id  ");
		sql.append("inner join faixasalarial fx on fx.id = hc.faixasalarial_id  ");
		sql.append("inner join cargo cg  on cg.id = fx.cargo_id  ");
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
			ColaboradorCertificacao colabs = new ColaboradorCertificacao(((BigInteger)res[0]).longValue(), (String)res[1], (String)res[2], (String)res[3], ((BigInteger)res[4]).longValue(), (String)res[5], ((BigInteger)res[6]).longValue(), (String)res[7], (Integer)res[8], (Date)res[9], ((BigInteger)res[10]).longValue());
			Colaboradores.add(colabs);
		}

		return Colaboradores;
	}

	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> colaboradoresCertificadosByColaboradorTurmaId(Long colaboradorTurmaId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select cc.certificacaos_id, ct.colaborador_id ");
		sql.append("from certificacao_curso cc ");
		sql.append("inner join colaboradorturma  ct on ct.curso_id = cc.cursos_id ");
		sql.append("where ct.id = :colaboradorTurmaId ");
		sql.append("and verifica_certificacao(cc.certificacaos_id, ct.colaborador_id) ");
		sql.append("order by cc.certificacaos_id, ct.curso_id ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);
		
		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		Collection<ColaboradorCertificacao> Colaboradores = new ArrayList<ColaboradorCertificacao>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			ColaboradorCertificacao colabs = new ColaboradorCertificacao(((BigInteger)res[0]).longValue(), ((BigInteger)res[1]).longValue());
			Colaboradores.add(colabs);
		}

		return Colaboradores;
	}

	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");
		criteria.createCriteria("cc.certificacao", "cert", Criteria.LEFT_JOIN);
		criteria.createCriteria("cc.colaborador", "col", Criteria.LEFT_JOIN);
		criteria.createCriteria("col.historicoColaboradors", "hc", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("fs.cargo", "ca", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.INNER_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.id"), "id");
		p.add(Projections.property("cc.data"), "data");
		p.add(Projections.property("cert.nome"), "certificacaoNome");
		p.add(Projections.property("cert.periodicidade"), "certificacaoPeriodicidade");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.property("ao.areaMae.id"), "areaOrganizacionalAreaMaeId");
		p.add(Projections.sqlProjection("monta_familia_area(ao6_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNome");
		p.add(Projections.property("col.nome"), "colaboradorNome");
		p.add(Projections.property("col.contato.email"), "colaboradorEmail");
		p.add(Projections.property("fs.nome"), "faixaSalarialNome");
		p.add(Projections.property("ca.nome"), "cargoNome");
		
		criteria.setProjection(p);

		criteria.add(Expression.eq("cc.data", data));
		criteria.add(Expression.eq("cert.empresa.id" , empresaId));

	    DetachedCriteria subSelect = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
	    		.setProjection(Projections.max("hc2.data"))
	    		.add(Restrictions.eqProperty("hc2.colaborador.id", "col.id"))
	    		.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
	    
	    criteria.add(Subqueries.propertyEq("hc.data", subSelect));
		
		criteria.addOrder(Order.asc("cc.data"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));

		return criteria.list();
	}
}

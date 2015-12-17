package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
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

		criteria.add(Expression.eq("cc.colaborador.id",colaboradorId));
		criteria.add(Expression.eq("cc.certificacao.id" , certificacaoId));

		criteria.addOrder(Order.asc("cc.data"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));

		return criteria.list();
	}

	public Collection<ColaboradorCertificacao> colabNaCertificacaoNaoCertificados(Long[] areasIds, Long[] estabelecimentosIds, Long certificacaoId) 
	{
		StringBuilder sql = new StringBuilder();
		dadosParaGerarEntidade(sql);
		sql.append("from colaborador c ");
		sql.append("left join colaboradorturma ct on ct.colaborador_id = c.id ");
		sql.append("left join turma t on t.id = ct.turma_id ");
		sql.append("left join curso cu on cu.id = t.curso_id ");
		sql.append("left join certificacao_curso certcu on certcu.cursos_id = cu.id ");
		sql.append("inner join certificacao cert on cert.id = certcu.certificacaos_id ");
		sql.append("inner join historicocolaborador hc on hc.colaborador_id = c.id ");
		sql.append("inner join faixasalarial fx on fx.id = hc.faixasalarial_id ");
		sql.append("inner join cargo cg on cg.id = fx.cargo_id ");
		sql.append("where c.id in ( ");
		sql.append("	select colaborador_id from ");
		sql.append("	( ");
		sql.append("	select distinct colaborador_id, ct.curso_id from colaboradorturma ct ");
		sql.append("		where ct.curso_id in (select cursos_id from certificacao_curso  where certificacaos_id = :certificacaoId) ");
		sql.append("		and ct.colaborador_id not in (select colaborador_id from colaboradorcertificacao where certificacao_id = :certificacaoId) ");
		sql.append("		group by ct.colaborador_id, ct.curso_id ");
		sql.append("	) as colabNasTurmas ");
		sql.append(" ");
		sql.append("	group by colaborador_id ");
		sql.append("	having count(colaborador_id) = (select count(distinct cursos_id) from certificacao_curso  where certificacaos_id = :certificacaoId) ");
		sql.append("       )  ");
		sql.append("and desligado = false ");
		sql.append("and hc.data = (select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = c.id and hc2.status = 1  ) ");
		sql.append("and cert.id = :certificacaoId ");
		sql.append("and t.dataPrevFim = (select max(t2.dataPrevFim) from turma t2 where t2.curso_id = cu.id and t.realizada) ");
		sql.append("and t.realizada ");
		
		if(areasIds != null && areasIds.length >0)
			sql.append("and hc.areaorganizacional_id in (:areasOrganizacionaisIds) ");
		
		if(estabelecimentosIds != null && estabelecimentosIds.length >0)
			sql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		
		sql.append("order by c.nome, cert.nome, cu.nome ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		if(areasIds != null && areasIds.length >0)
			query.setParameterList("areasOrganizacionaisIds", areasIds);
		
		if(estabelecimentosIds != null && estabelecimentosIds.length >0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds);
		
		query.setLong("certificacaoId", certificacaoId);
		
		return montaEntidadeCertificacaoColaborador(query);
	}

	public Collection<ColaboradorCertificacao> colaboradoresCertificados(Date dataIni, Date dataFim, char filtroCetificacao, Long[] areasIds, Long[] estabelecimentosIds, Long[] certificacoesIds) 
	{
		StringBuilder sql = new StringBuilder();
		dadosParaGerarEntidade(sql);
		sql.append("from colaboradorcertificacao ccert ");
		sql.append("inner join colaborador c on c.id = ccert.colaborador_id ");
		sql.append("inner join certificacao_curso certcu on certcu.certificacaos_id = ccert.certificacao_id ");
		sql.append("inner join certificacao cert on cert.id = ccert.certificacao_id  ");
		sql.append("inner join curso cu on cu.id = certcu.cursos_id  ");
		sql.append("inner join colaboradorturma ct on ct.colaborador_id = c.id  ");
		sql.append("inner join turma t on t.id = ct.turma_id  ");
		sql.append("inner join historicocolaborador hc on hc.colaborador_id = c.id  ");
		sql.append("inner join faixasalarial fx on fx.id = hc.faixasalarial_id  ");
		sql.append("inner join cargo cg on cg.id = fx.cargo_id  ");
		sql.append("where desligado = false  ");
		sql.append("and hc.data = (select max(hc2.data) from historicocolaborador hc2  where hc2.colaborador_id = c.id and hc2.status = 1  )  ");
		sql.append("and cert.id in (:certificacoesIds) ");
		sql.append("and t.dataPrevFim = (select max(t2.dataPrevFim) from turma t2 where t2.curso_id = cu.id and t.realizada) ");
		sql.append("and t.realizada ");
		sql.append("and ccert.data between :dataIni and :dataFim ");
		
		if(filtroCetificacao == TipoCertificacao.VENCIDA)
			sql.append("and ccert.data + cast((coalesce(cert.periodicidade,0) || ' month') as interval) <= :dataFim ");
		else if(filtroCetificacao == TipoCertificacao.AVENCER)
			sql.append("and ccert.data + cast((coalesce(cert.periodicidade,0) || ' month') as interval) > :dataFim ");
		
		if(areasIds != null && areasIds.length >0)
			sql.append("and hc.areaorganizacional_id in (:areasOrganizacionaisIds) ");
		
		if(estabelecimentosIds != null && estabelecimentosIds.length >0)
			sql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		
		sql.append("order by c.nome, cert.nome, cu.nome ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameterList("certificacoesIds", certificacoesIds);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		
		if(areasIds != null && areasIds.length >0)
			query.setParameterList("areasOrganizacionaisIds", areasIds);
		
		if(estabelecimentosIds != null && estabelecimentosIds.length >0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds);
		
		return montaEntidadeCertificacaoColaborador(query);
	}

	private void dadosParaGerarEntidade(StringBuilder sql) 
	{
		//Ao alterar esse retorno alterar tambem o construtor abaixo em montaEntidadeCertificacaoColaborador()
		sql.append("select c.id as colabId, c.nome as colabNome, c.nomecomercial as colabNomeComercial, c.matricula as colabMatricula, cg.id as cargoId, cg.nome as cargoNome, cert.id as certId, ");
		sql.append("cert.nome as certNome, cu.id as cursoId, cu.nome as cursoNome, t.dataprevini as turmaIni, t.dataprevFim as turmaFim, t.realizada as turmaRealizada,  ");
		sql.append("verifica_aprovacao(cu.id, t.id, ct.id, cu.percentualminimofrequencia) as aprovacao, ");
		sql.append("ccert.data, cert.periodicidade  ");
	}
	
	private Collection<ColaboradorCertificacao> montaEntidadeCertificacaoColaborador(Query query) throws HibernateException 
	{
		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		Collection<ColaboradorCertificacao> Colaboradores = new ArrayList<ColaboradorCertificacao>();
		
		for (@SuppressWarnings("unchecked")
		Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			ColaboradorCertificacao colabs = new ColaboradorCertificacao(((BigInteger)res[0]).longValue(), (String)res[1], (String)res[2], (String)res[3], ((BigInteger)res[4]).longValue(), (String)res[5], ((BigInteger)res[6]).longValue(), (String)res[7], ((BigInteger)res[8]).longValue(), (String)res[9], (Date)res[10], (Date)res[11], (Boolean)res[12], (Boolean)res[13], (Date)res[14], (Integer)res[15]);
			Colaboradores.add(colabs);
		}

		return Colaboradores;
	}
}

package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;

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

	public Collection<ColaboradorCertificacao> colabNaCertificacaoNaoCertificados(Long certificacaoId, Long[] areasIds, Long[] estabelecimentosIds) 
	{
		StringBuilder sql = new StringBuilder();
		//Certificação,Matrícula,Nome,Nome Comercial,Cargo,Treinamentos,Status Treinamentos
		sql.append("select  c.id, c.nome, c.nomecomercial, c.matricula, cg.id, cg.nome, cert.id, cert.nome, cu.id, cu.nome, t.dataprevini, t.dataprevFim, t.realizada, ");
		sql.append("verifica_aprovacao(cu.id, t.id, ct.id, cu.percentualminimofrequencia),  ");
		sql.append("verifica_certificacao(cert.id, c.id) ");
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
		sql.append("and hc.data = (select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = c.id) ");
		sql.append("and cert.id = :certificacaoId ");
		sql.append("and hc.areaorganizacional_id in (:areasOrganizacionaisIds) ");
		sql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		sql.append("order by c.nome ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("certificacaoId", certificacaoId);
		query.setParameterList("areasOrganizacionaisIds", areasIds);
		query.setParameterList("estabelecimentosIds", estabelecimentosIds);
		
		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		Collection<ColaboradorCertificacao> Colaboradores = new ArrayList<ColaboradorCertificacao>();
		
		for (@SuppressWarnings("unchecked")
		Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			ColaboradorCertificacao colabs = new ColaboradorCertificacao(((BigInteger)res[0]).longValue(), (String)res[1], (String)res[2], (String)res[3], ((BigInteger)res[4]).longValue(), (String)res[5], ((BigInteger)res[6]).longValue(), (String)res[7], ((BigInteger)res[8]).longValue(), (String)res[9], (Date)res[10], (Date)res[11], (Boolean)res[12], (Boolean)res[13], (Boolean)res[14]);
			Colaboradores.add(colabs);
		}

		return Colaboradores;
	}
}

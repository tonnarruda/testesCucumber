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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

public class ColaboradorAvaliacaoPraticaDaoHibernate extends GenericDaoHibernate<ColaboradorAvaliacaoPratica> implements ColaboradorAvaliacaoPraticaDao
{
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select cap.id as capId, cap.data as capdata, cap.nota as capNota, cap.colaboradorCertificacao_id, ap.id as apId, ap.notaMinima as apMinimo, ap.titulo as apTitulo from ColaboradorAvaliacaoPratica cap ");
		sql.append("left join AvaliacaoPratica ap on ap.id = cap.avaliacaopratica_id ");
		sql.append("where cap.certificacao_id = :certificacaoId and cap.colaborador_id = :colaboradorId ");

		if(colaboradorCertificacaoId != null)
		{
			sql.append("and ");
			sql.append("(cap.data between ");
			sql.append("			coalesce( ");
			sql.append("						(select data from colaboradorcertificacao where data = (select max(data) from colaboradorcertificacao ");
			sql.append("								where data < (select data from colaboradorcertificacao where id = :colaboradorCertificacaoId) ");
			sql.append("								and certificacao_id = :certificacaoId and colaborador_id = :colaboradorId ");
			sql.append("						) and certificacao_id = :certificacaoId and colaborador_id = :colaboradorId) ");
			sql.append("					, '01/01/2000' ");
			sql.append("					) ");
			sql.append("	and (select data from colaboradorcertificacao where id = :colaboradorCertificacaoId) ");
			sql.append(") ");
		}else
		{
			sql.append("and cap.data > coalesce((select max(data) from colaboradorcertificacao where certificacao_id = :certificacaoId and colaborador_id = :colaboradorId),'01/01/2000') ");
		}	
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("colaboradorId",colaboradorId);
		query.setLong("certificacaoId",certificacaoId);
		
		if(colaboradorCertificacaoId != null)
			query.setLong("colaboradorCertificacaoId",colaboradorCertificacaoId);
		
		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		Collection<ColaboradorAvaliacaoPratica> ColaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();

			ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = new ColaboradorAvaliacaoPratica();
			colaboradorAvaliacaoPratica.setId(((BigInteger)res[0]).longValue());
			colaboradorAvaliacaoPratica.setData((Date)res[1]);
			colaboradorAvaliacaoPratica.setNota((Double)res[2]);
			colaboradorAvaliacaoPratica.setColaboradorCertificacaoId(res[3] != null ? ((BigInteger)res[3]).longValue() : null );
			colaboradorAvaliacaoPratica.setAvaliacaoPraticaId(((BigInteger)res[4]).longValue());
			colaboradorAvaliacaoPratica.setAvaliacaoPraticaNotaMinima((Double)res[5]);
			colaboradorAvaliacaoPratica.setAvaliacaoPraticaTitulo((String)res[6]);
			
			ColaboradorAvaliacaoPraticas.add(colaboradorAvaliacaoPratica);
		}

		return ColaboradorAvaliacaoPraticas;
	}

	public void removeAllByColaboradorId(Long colaboradorId) 
	{
		Query query = getSession().createQuery("delete ColaboradorAvaliacaoPratica cap where cap.colaborador.id = :colaboradorId");
		query.setLong("colaboradorId", colaboradorId);

		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		Criteria criteria = getSession().createCriteria(ColaboradorAvaliacaoPratica.class, "cap");
		criteria.add(Expression.eq("colaborador.id", colaboradorId))
		.add(Expression.eq("certificacao.id",certificacaoId))
		.add(Expression.isNull("colaboradorCertificacao.id"));

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cap.id"), "id");
		p.add(Projections.property("cap.data"), "data");
		p.add(Projections.property("cap.nota"), "nota");
		p.add(Projections.property("cap.avaliacaoPratica.id"), "avaliacaoPraticaId");
		p.add(Projections.property("cap.certificacao.id"), "certificacaoId");
		p.add(Projections.property("cap.colaborador.id"), "colaboradorId");

		criteria.setProjection(p);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorAvaliacaoPratica.class));
		return criteria.list();
	}
}

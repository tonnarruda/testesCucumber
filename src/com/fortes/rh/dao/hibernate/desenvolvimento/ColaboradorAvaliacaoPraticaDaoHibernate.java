package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

public class ColaboradorAvaliacaoPraticaDaoHibernate extends GenericDaoHibernate<ColaboradorAvaliacaoPratica> implements ColaboradorAvaliacaoPraticaDao
{
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select id, data, nota, avaliacaopratica_id from ColaboradorAvaliacaoPratica ");
		sql.append("where certificacao_id = :certificacaoId and colaborador_id = :colaboradorId ");

		if(colaboradorCertificacaoId != null)
		{
			sql.append("and ");
			sql.append("(data between ");
			sql.append("			coalesce( ");
			sql.append("						(select data from colaboradorcertificacao where data = (select max(data) from colaboradorcertificacao ");
			sql.append("								where data < (select data from colaboradorcertificacao where id = :colaboradorCertificacaoId) ");
			sql.append("								and certificacao_id = :certificacaoId and colaborador_id = :colaboradorId ");
			sql.append("						) and certificacao_id = :certificacaoId and colaborador_id = :colaboradorId) ");
			sql.append("					, '01/01/2000' ");
			sql.append("					) ");
			sql.append("	and (select data from colaboradorcertificacao where id = :colaboradorCertificacaoId) ");
			sql.append(") ");
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
			colaboradorAvaliacaoPratica.setAvaliacaoPraticaId(((BigInteger)res[3]).longValue());
			
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
}

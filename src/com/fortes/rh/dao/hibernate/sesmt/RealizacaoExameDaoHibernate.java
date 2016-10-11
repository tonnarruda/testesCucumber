package com.fortes.rh.dao.hibernate.sesmt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;

@SuppressWarnings("unchecked")
public class RealizacaoExameDaoHibernate extends GenericDaoHibernate<RealizacaoExame> implements RealizacaoExameDao
{

	public Collection<Object[]> getRelatorioExame(Long estabelecimentoId, Date dataIni, Date dataFim)
	{
		StringBuilder hql = new StringBuilder("select e.id, se.motivo, e.nome, re.resultado");
		hql.append(" from HistoricoColaborador hc");
		hql.append(" left join hc.colaborador as c");
		hql.append(" join c.solicitacaoExames se");
		hql.append(" join se.exameSolicitacaoExames ese");
		hql.append(" join ese.exame e");
		hql.append(" join ese.realizacaoExame re");
		hql.append(" where c.id = se.colaborador.id");
		hql.append(" and hc.estabelecimento.id = :estabelecimentoId");
		hql.append(" and hc.data = (select max(hc2.data)");
		hql.append("				from HistoricoColaborador hc2");
		hql.append(" 				where hc2.status = :status ");
		hql.append(" 				and c.id = hc2.colaborador.id)");
		hql.append(" and re.data >= :dataIni");
		hql.append(" and re.data <= :dataFim");
		hql.append(" and re.resultado != 'NAO_REALIZADO'");
		hql.append(" order by se.motivo");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}
	
	public Collection<RealizacaoExame> findRealizadosByColaborador(Long empresaId, Long colaboradorId)
	{
		StringBuilder hql = new StringBuilder("select new RealizacaoExame(re.data, exame.nome, re.resultado, re.observacao, se.motivo) ");
		hql.append("from RealizacaoExame re ");
		hql.append("join re.exameSolicitacaoExame ese ");
		hql.append("join ese.solicitacaoExame se ");
		hql.append("join se.colaborador co ");
		hql.append("join ese.exame exame ");
		hql.append("where co.id = :colaboradorId ");
		hql.append("and se.empresa.id = :empresaId ");
		hql.append("and re.resultado != :resultado ");
		hql.append("ORDER BY re.data ASC, exame.nome");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("colaboradorId", colaboradorId);
		query.setLong("empresaId", empresaId);
		query.setString("resultado", ResultadoExame.NAO_REALIZADO.toString());

		return query.list();
	}

	public Collection<Long> findIdsBySolicitacaoExame(long solicitacaoExameId)
	{
		Criteria criteria = getSession().createCriteria(ExameSolicitacaoExame.class, "ese");
		criteria.createCriteria("ese.realizacaoExame", "re");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("re.id"), "id");

		criteria.setProjection(p);
		criteria.add(Expression.eq("ese.solicitacaoExame.id", solicitacaoExameId));

        return criteria.list();
	}

	@Override
	public void remove(Long[] realizacaoExameIds) throws DataAccessException
	{
		String hql = "delete RealizacaoExame re where re.id in (:ids)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ids", realizacaoExameIds, Hibernate.LONG);
		query.executeUpdate();
	}

	public void marcarResultadoComoNormal(Collection<Long> realizacaoExameIds)
	{
		String hql = "update RealizacaoExame re set re.resultado = :result where re.id in (:ids) ";
		Query query = getSession().createQuery(hql);
		query.setString("result", ResultadoExame.NORMAL.toString());
		query.setParameterList("ids", realizacaoExameIds, Hibernate.LONG);
		query.executeUpdate();
	}
	
	public Integer findQtdRealizados(Long empresaId, Date dataIni, Date dataFim)
	{
		Criteria criteria = getSession().createCriteria(ExameSolicitacaoExame.class, "ese");
		criteria.createCriteria("ese.realizacaoExame", "re");
		criteria.createCriteria("ese.solicitacaoExame", "se");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.count("ese.id"));

		criteria.setProjection(p);
		criteria.add(Expression.between("re.data", dataIni, dataFim));
		criteria.add(Expression.eq("se.empresa.id", empresaId));
		criteria.add(Expression.ne("re.resultado", ResultadoExame.NAO_REALIZADO.toString()));

        return (Integer) criteria.uniqueResult();
	}
	
	public Collection<Exame> findQtdPorExame(Long empresaId, Date dataIni, Date dataFim)
	{
		StringBuilder subQtdNormal = new StringBuilder();
		subQtdNormal.append("(select count(*) from examesolicitacaoexame ese "); 
		subQtdNormal.append("inner join realizacaoexame re on ese.realizacaoexame_id = re.id "); 
		subQtdNormal.append("inner join solicitacaoexame se on ese.solicitacaoexame_id = se.id "); 
		subQtdNormal.append("where re.data between :dataIni and :dataFim "); 
		subQtdNormal.append("and se.empresa_id = :empresaId "); 
		subQtdNormal.append("and re.resultado = :resultadoNormal "); 
		subQtdNormal.append("and ese.exame_id = e.id ) "); 
		
		StringBuilder subQtdAnormal = new StringBuilder();
		subQtdAnormal.append("(select count(*) from examesolicitacaoexame ese "); 
		subQtdAnormal.append("inner join realizacaoexame re on ese.realizacaoexame_id = re.id "); 
		subQtdAnormal.append("inner join solicitacaoexame se on ese.solicitacaoexame_id = se.id "); 
		subQtdAnormal.append("where re.data between :dataIni and :dataFim "); 
		subQtdAnormal.append("and se.empresa_id = :empresaId "); 
		subQtdAnormal.append("and re.resultado = :resultadoAnormal "); 
		subQtdAnormal.append("and ese.exame_id = e.id ) "); 

		StringBuilder sql = new StringBuilder("select e.id, e.nome, ");

		sql.append(subQtdNormal);
		sql.append(" as qtdNormal,");

		sql.append(subQtdAnormal);
		sql.append(" as qtdAnormal ");

		sql.append("from exame e ");
		sql.append("where e.empresa_id = :empresaId ");
		sql.append("group by e.id, e.nome ");
		
		sql.append("having ");
		sql.append(subQtdNormal);
		sql.append(" > 0 or");
		sql.append(subQtdAnormal);
		sql.append(" > 0 ");
		
		sql.append("order by qtdNormal desc");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("empresaId", empresaId);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setString("resultadoNormal", ResultadoExame.NORMAL.toString());
		query.setString("resultadoAnormal", ResultadoExame.ANORMAL.toString());
		
		Collection<Object[]> resultado = query.list();
		
		Collection<Exame> exames = new ArrayList<Exame>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			Exame exame = new Exame();
			exame.setId(((BigInteger)res[0]).longValue());
			exame.setNome((String)res[1]);
			exame.setQtdNormal(((BigInteger)res[2]).intValue());
			exame.setQtdAnormal(((BigInteger)res[3]).intValue());
			exames.add(exame);
		}

        return exames;
	}
}
package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

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
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.RealizacaoExame;

@SuppressWarnings("unchecked")
public class RealizacaoExameDaoHibernate extends GenericDaoHibernate<RealizacaoExame> implements RealizacaoExameDao
{

	public Collection<Object[]> getRelatorioAnual(Long estabelecimentoId, Date dataIni, Date dataFim)
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
		hql.append(" 				where hc2.data <= re.data  and hc2.status = :status ");
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
}
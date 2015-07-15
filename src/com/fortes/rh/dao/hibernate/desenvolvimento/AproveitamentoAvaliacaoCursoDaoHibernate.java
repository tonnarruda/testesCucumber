package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("unchecked")
public class AproveitamentoAvaliacaoCursoDaoHibernate extends GenericDaoHibernate<AproveitamentoAvaliacaoCurso> implements AproveitamentoAvaliacaoCursoDao
{

	public Collection<AproveitamentoAvaliacaoCurso> findNotas(Long avaliacaoId, Long[] colaboradoresIds)
	{
		Criteria criteria = getSession().createCriteria(AproveitamentoAvaliacaoCurso.class, "a");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.valor"), "valor");
		p.add(Projections.property("a.colaboradorTurma.id"), "projectionColaboradorTurmaId");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("avaliacaoCurso.id", avaliacaoId));
		criteria.add(Expression.in("colaboradorTurma.id", colaboradoresIds));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public AproveitamentoAvaliacaoCurso findByColaboradorTurmaAvaliacaoId(Long colaboradorTurmaId, Long avaliacaoCursoId)
	{
		Criteria criteria = getSession().createCriteria(AproveitamentoAvaliacaoCurso.class, "a");

		criteria.add(Expression.eq("a.colaboradorTurma.id", colaboradorTurmaId));
		criteria.add(Expression.eq("a.avaliacaoCurso.id", avaliacaoCursoId));

		return (AproveitamentoAvaliacaoCurso) criteria.uniqueResult();
	}
	
	/*
	 * Retorna o id + Nota.
	 * A consulta de APROVADOS só deve ser usada quando for só para 1 avaliação
	 * (por causa da cláusula group by).  Tiago 
	 * 
	 * @see com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao#findColaboradorTurma(java.lang.Long, int, java.lang.String, java.lang.Boolean)
	 */
	public Collection<ColaboradorTurma> findColaboradorTurma(Long id, int qtdAvaliacao, String wherePor, Boolean aprovado)
	{
		StringBuilder hql = new StringBuilder("select new ColaboradorTurma(a.colaboradorTurma.id, a.valor) from AproveitamentoAvaliacaoCurso a ");
		hql.append("left join a.avaliacaoCurso ac ");
		hql.append("left join a.colaboradorTurma ct ");
		hql.append("left join ct.turma t ");
		hql.append("where ");
		
		String turmaOuCurso = wherePor.equalsIgnoreCase("T") ? "t.id" : "t.curso.id";
		
		hql.append(turmaOuCurso + " = :id ");

		if(aprovado)
		{
			hql.append(" and a.valor >= ac.minimoAprovacao ");
			hql.append(" group by a.colaboradorTurma.id,a.valor");
			hql.append(" having count(a.colaboradorTurma.id) = :qtdAvaliacao ");
		}
		else
		{
			hql.append(" and a.valor < ac.minimoAprovacao ");
			hql.append(" group by a.colaboradorTurma.id,a.valor ");
		}

		Query query = getSession().createQuery(hql.toString());
		query.setLong("id", id);

		if(aprovado)
			query.setInteger("qtdAvaliacao", qtdAvaliacao);

		return query.list();
	}

	public Collection<Long> find(Long id, int qtdAvaliacao, String wherePor, Boolean aprovado)
	{
		StringBuilder hql = new StringBuilder("select a.colaboradorTurma.id from AproveitamentoAvaliacaoCurso a ");
		hql.append("left join a.avaliacaoCurso ac ");
		hql.append("left join a.colaboradorTurma ct ");
		hql.append("left join ct.turma t ");
		hql.append("where ");

		if(wherePor.equalsIgnoreCase("T"))
			hql.append("t.id = :id ");
		else
			hql.append("t.curso.id = :id ");

		if(aprovado)
		{
			hql.append("and a.valor >= ac.minimoAprovacao ");
			hql.append("group by a.colaboradorTurma.id having count(a.colaboradorTurma.id) = :qtdAvaliacao ");
		}
		else
		{
			hql.append("and a.valor < ac.minimoAprovacao ");
			hql.append("group by a.colaboradorTurma.id ");
		}

		Query query = getSession().createQuery(hql.toString());
		query.setLong("id", id);

		if(aprovado)
			query.setInteger("qtdAvaliacao", qtdAvaliacao);

		return query.list();
	}

	public Collection<Long> findColaboradores(Long id, Integer qtdAvaliacao, String wherePor, boolean aprovado)
	{
		StringBuilder hql = new StringBuilder("select colab.id from AproveitamentoAvaliacaoCurso a ");
		hql.append("left join a.avaliacaoCurso ac ");
		hql.append("left join a.colaboradorTurma ct ");
		hql.append("left join ct.colaborador colab ");
		hql.append("left join ct.turma t ");
		hql.append("where ");

		if(wherePor.equalsIgnoreCase("T"))
			hql.append("t.id = :id ");
		else
			hql.append("t.curso.id = :id ");

		if(aprovado)
		{
			hql.append("and a.valor >= ac.minimoAprovacao ");
			hql.append("group by colab.id having count(colab.id) = :qtdAvaliacao ");
		}
		else
		{
			hql.append("and a.valor < ac.minimoAprovacao ");
			hql.append("group by colab.id ");
		}

		Query query = getSession().createQuery(hql.toString());
		query.setLong("id", id);

		if(aprovado)
			query.setInteger("qtdAvaliacao", qtdAvaliacao);

		return query.list();
	}

	public Collection<Long> find(Long[] cursoIds, Integer qtdAvaliacao, boolean aprovado)
	{
		StringBuilder hql = new StringBuilder("select colab.id from AproveitamentoAvaliacaoCurso a ");
		hql.append("left join a.avaliacaoCurso ac ");
		hql.append("left join a.colaboradorTurma ct ");
		hql.append("left join ct.colaborador colab ");
		hql.append("left join ct.turma t ");
		hql.append("where ");

		hql.append("t.curso.id in (:id) ");

		if(aprovado)
		{
			hql.append("and a.valor >= ac.minimoAprovacao ");
			hql.append("group by colab.id having count(colab.id) = :qtdAvaliacao ");
		}
		else
		{
			hql.append("and a.valor < ac.minimoAprovacao ");
			hql.append("group by colab.id ");
		}

		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("id", cursoIds, Hibernate.LONG);

		if(aprovado)
			query.setInteger("qtdAvaliacao", qtdAvaliacao);

		return query.list();
	}

	public Collection<Long> findAprovadosComAvaliacao(Collection<Long> cursoIds, Date dataIni, Date dataFim)
	{
		StringBuilder hql = new StringBuilder("select ct.id from AproveitamentoAvaliacaoCurso aproveitamento ");
		hql.append("join aproveitamento.avaliacaoCurso avc ");
		hql.append("join aproveitamento.colaboradorTurma ct ");
		hql.append("join ct.colaborador colab ");
		hql.append("join ct.turma t ");
		hql.append("join t.curso c ");
		hql.append("where t.dataPrevIni >= :dataIni and t.dataPrevFim <= :dataFim ");

		if (cursoIds != null && cursoIds.size() > 0)
			hql.append("and c.id in (:cursoIds) ");

		hql.append("and aproveitamento.valor >= avc.minimoAprovacao ");
		hql.append("group by ct.id,c.id ");
		hql.append("having count(ct.id) = (select count(avc2.id) from AvaliacaoCurso avc2 join avc2.cursos c2 where c2.id=c.id) ");
		hql.append("order by ct.id ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		if (cursoIds != null && cursoIds.size() > 0)
			query.setParameterList("cursoIds", cursoIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<Long> findReprovados(Date dataIni, Date dataFim, Long empresaId)
	{
		StringBuilder hql = new StringBuilder("select distinct ct.id from ColaboradorTurma ct ");
		hql.append("join ct.turma t ");
		hql.append("join t.curso c ");
		hql.append("join c.avaliacaoCursos avaliacao ");
		hql.append("left join ct.aproveitamentoAvaliacaoCursos aproveitamento with aproveitamento.avaliacaoCurso.id=avaliacao.id ");

		hql.append("where t.dataPrevIni >= :dataIni and t.dataPrevFim <= :dataFim ");
		hql.append("and c.empresa.id = :empresaId ");
		hql.append("and (aproveitamento.valor < avaliacao.minimoAprovacao ");
		hql.append("or aproveitamento = null) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setLong("empresaId", empresaId);

		return query.list();
	}

	public void remove(Long cursoId, String[] avaliacaoCursoIds)
	{
		String hql = "delete from AproveitamentoAvaliacaoCurso where colaboradorTurma.id in(select id from ColaboradorTurma where curso.id = :cursoId) ";

		if(avaliacaoCursoIds != null && avaliacaoCursoIds.length > 0)
			hql += "and avaliacaoCurso.id not in (:avaliacaoCursoIds)";

		Query query = getSession().createQuery(hql);
		query.setLong("cursoId", cursoId);

		if(avaliacaoCursoIds != null && avaliacaoCursoIds.length > 0)
			query.setParameterList("avaliacaoCursoIds", LongUtil.arrayStringToCollectionLong(avaliacaoCursoIds), Hibernate.LONG);

		query.executeUpdate();
	}

	public void removeByTurma(Long turmaId)
	{
		String hql = "delete from AproveitamentoAvaliacaoCurso where colaboradorTurma.id in(select id from ColaboradorTurma where turma.id = :turmaId) ";

		Query query = getSession().createQuery(hql);
		query.setLong("turmaId", turmaId);

		query.executeUpdate();
	}
	
	public void removeByColaboradorTurma(Long colaboradorTurmaId)
	{
		String hql = "delete from AproveitamentoAvaliacaoCurso where colaboradorTurma.id  = :colaboradorTurmaId ";

		Query query = getSession().createQuery(hql);
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);

		query.executeUpdate();
	}

	public Collection<AproveitamentoAvaliacaoCurso> findByColaboradorCurso(Long colaboradorId, Long cursoId) 
	{
		Criteria criteria = getSession().createCriteria(AproveitamentoAvaliacaoCurso.class, "a");
		criteria.createCriteria("a.colaboradorTurma", "ct");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.valor"), "valor");
		p.add(Projections.property("a.avaliacaoCurso.id"), "projectionAvaliacaoCursoId");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ct.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("ct.curso.id", cursoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
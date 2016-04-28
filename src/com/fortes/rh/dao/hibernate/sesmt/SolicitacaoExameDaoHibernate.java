package com.fortes.rh.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoExameRelatorio;
import com.fortes.rh.util.StringUtil;

/**
 * @author Tiago Lopes
 *
 */
@SuppressWarnings("unchecked")
public class SolicitacaoExameDaoHibernate extends GenericDaoHibernate<SolicitacaoExame> implements SolicitacaoExameDao
{
	public Collection<SolicitacaoExame> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, TipoPessoa vinculo, String nomeBusca, String matriculaBusca, String motivo, Long[] exameIds, ResultadoExame resultadoExame)
	{
		StringBuilder hql = new StringBuilder("select distinct new SolicitacaoExame(se.id, se.data, se.motivo, se.ordem, se.medicoCoordenador.nome, co.nome, co.nomeComercial, ca.nome, cg.nome, co.desligado) ");		
		hql.append("from SolicitacaoExame se ");
		hql.append("left join se.colaborador co ");
		hql.append("left join se.candidato ca ");
		hql.append("left join se.exameSolicitacaoExames ese ");
		hql.append("left join ese.realizacaoExame re ");
		hql.append("left join co.historicoColaboradors hc ");
		hql.append("left join hc.faixaSalarial fs ");
		hql.append("left join fs.cargo cg ");
		hql.append("where se.empresa.id = :empresaId ");
		
		hql.append("and (ca.id is not null or hc.id is null or (hc.data = (select max(hc2.data) ");
		hql.append("from HistoricoColaborador as hc2 ");
		hql.append("where hc2.colaborador.id = co.id )) ");
		
		hql.append(" ) ");
		
		if (exameIds != null && exameIds.length > 0)
			hql.append("and ese.exame.id in (:exameIds) ");

		if (StringUtils.isNotBlank(motivo))
			hql.append("and se.motivo like :motivo ");

		if (dataIni != null && dataFim != null)
			hql.append("and se.data between :dataIni and :dataFim ");

		switch (vinculo)
		{
			case COLABORADOR:
				hql.append("and co != null ");
				hql.append("and lower(co.matricula) like lower(:matricula) ");
				hql.append("and lower(co.nome) like lower(:nome) ");
				break;
			case CANDIDATO:
				hql.append("and ca != null ");
				hql.append("and lower(ca.nome) like lower(:nome) ");
				break;
			case TODOS:
				hql.append("and (lower(co.nome) like lower(:nome) ");
				hql.append("or lower(ca.nome) like lower(:nome)) ");
				break;
		}
		
		if (resultadoExame != null)
		{
			hql.append("and ");
			
			// no caso de "Não informado", o resultado pode tb não existir no BD
			if (resultadoExame.equals(ResultadoExame.NAO_REALIZADO))
				hql.append("(re = null or re.resultado = :resultado) ");
			else
				hql.append("ese.realizacaoExame.resultado = :resultado ");
		}
		
		//hql.append("and hc.data = (select max(hc2.data) from HistoricoColaborador as hc2 where hc2.colaborador.id = co.id )");

		hql.append("ORDER BY se.data DESC, se.ordem, co.nome ASC");
		
		Query query = getSession().createQuery(hql.toString());

		if (dataIni != null && dataFim != null)
		{
			query.setDate("dataIni", dataIni);
			query.setDate("dataFim", dataFim);
		}
		
		switch (vinculo)
		{
			case COLABORADOR:
				query.setString("matricula", "%" + StringUtil.lower(matriculaBusca) + "%");
		}
		
		query.setString("nome", "%" + StringUtil.lower(nomeBusca) + "%");
		
		if (resultadoExame != null)
			query.setString("resultado", resultadoExame.toString());
		
		if (exameIds != null && exameIds.length > 0)
			query.setParameterList("exameIds", exameIds, Hibernate.LONG);

		if (StringUtils.isNotBlank(motivo))
			query.setString("motivo", motivo);

		query.setLong("empresaId", empresaId);
		
		if(pagingSize != 0)
        {
        	query.setFirstResult(((page - 1) * pagingSize));
        	query.setMaxResults(pagingSize);
        }
		
		return query.list();
		
//		return solicitacaoExames;
	}
	
	public Integer getCount(Long empresaId, Date dataIni, Date dataFim, TipoPessoa vinculo, String nomeBusca, String matriculaBusca, String motivo, Long[] exameIds, ResultadoExame resultadoExame)
	{
//		List<Long> solicitacaoExameIds = findDistinctSolicitacaoIds(empresaId, dataIni, dataFim, vinculo, nomeBusca, matriculaBusca, motivo, exameIds, resultadoExame);
		
		Collection<SolicitacaoExame> solicitacaoExameIds = findAllSelect(0,0, empresaId, dataIni, dataFim, vinculo, nomeBusca, matriculaBusca, motivo, exameIds, resultadoExame); 
		
		if (solicitacaoExameIds == null)
			return 0;
		else
			return solicitacaoExameIds.size();
	}

	public Collection<SolicitacaoExameRelatorio> findImprimirSolicitacaoExames(Long solicitacaoExameId)
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.sesmt.relatorio.SolicitacaoExameRelatorio(medico.nome, medico.crm, medico.assinaturaDigital, clinica.nome, clinica.tipo, clinica.outro, clinica.telefone, clinica.horarioAtendimento, clinica.endereco,exame.nome,co.nome,ca.nome,co.pessoal.dataNascimento,ca.pessoal.dataNascimento,se.motivo, co.matricula, f.nome, a.nome, fsol.nome, se.observacao) ");
		hql.append("from ExameSolicitacaoExame exameSol ");
		hql.append("join exameSol.solicitacaoExame se ");
		hql.append("join se.medicoCoordenador medico ");
		hql.append("join exameSol.exame exame ");
		hql.append("left join se.colaborador co ");
		hql.append("left join co.historicoColaboradors hc ");
		hql.append("left join hc.funcao f ");
		hql.append("left join hc.ambiente a ");
		hql.append("left join se.candidato ca ");
		hql.append("left join ca.candidatoSolicitacaos cas  ");
		hql.append("left join cas.solicitacao s  ");
		hql.append("left join s.funcao fsol  ");
		hql.append("left join exameSol.clinicaAutorizada clinica ");
		hql.append("where se.id = :solicitacaoExameId ");
		
		hql.append("and (hc.data = (select max(hc2.data) ");
		hql.append("				from HistoricoColaborador as hc2 ");
		hql.append("				where hc2.colaborador.id = co.id ");
		hql.append("				and hc2.data <= :hoje and hc2.status = :status ) ");
		hql.append("	or hc.data is null) ");
		
		hql.append("and (s.data = (select max(s2.data) ");
		hql.append("			    from CandidatoSolicitacao cas2 ");
		hql.append("			    left join cas2.solicitacao s2 ");
		hql.append("			    where cas2.candidato.id = ca.id) ");
		hql.append("	or s.data is null) ");
		
		hql.append("order by clinica.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("solicitacaoExameId", solicitacaoExameId);
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public Collection<SolicitacaoExame> findByCandidatoOuColaborador(TipoPessoa vinculo, Long candidatoOuColaboradorId, String motivo)
	{
		Criteria criteria = getSession().createCriteria(SolicitacaoExame.class, "se");
		criteria.createCriteria("se.colaborador", "col", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("se.candidato", "ca", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("se.medicoCoordenador", "medico", CriteriaSpecification.LEFT_JOIN);

		ProjectionList projectionList = Projections.projectionList().create();
		projectionList.add(Projections.property("se.id"),"id");
		projectionList.add(Projections.property("se.data"),"data");
		projectionList.add(Projections.property("se.observacao"),"observacao");
		projectionList.add(Projections.property("medico.nome"),"medicoCoordenadorNome");
		criteria.setProjection(projectionList);

		switch (vinculo)
		{
			case CANDIDATO:
				criteria.add(Expression.eq("ca.id" , candidatoOuColaboradorId));
				break;
			case COLABORADOR:
				criteria.add(Expression.eq("col.id" , candidatoOuColaboradorId));
				break;
			default:
				return new ArrayList<SolicitacaoExame>();
		}

		if (StringUtils.isNotBlank(motivo))
			criteria.add(Expression.eq("se.motivo" , motivo));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(SolicitacaoExame.class));

		return criteria.list();
	}

	public void transferirCandidatoToColaborador(Long empresaId, Long candidatoId, Long colaboradorId)
	{
		String hql = "update SolicitacaoExame se set se.colaborador.id = :colaboradorId, se.candidato.id = null where (se.candidato.id = :candidatoId and se.empresa.id = :empresaId)";
		Query query = getSession().createQuery(hql);
		query.setLong("empresaId", empresaId);
		query.setLong("candidatoId", candidatoId);
		query.setLong("colaboradorId", colaboradorId);
		query.executeUpdate();
	}
	
	public void transferirColaboradorToCandidato(Long empresaId, Long candidatoId, Long colaboradorId)
	{
		String hql = "update SolicitacaoExame se set se.colaborador.id = null, se.candidato.id = :candidatoId where (se.colaborador.id = :colaboradorId and se.empresa.id = :empresaId)";
		Query query = getSession().createQuery(hql);
		query.setLong("empresaId", empresaId);
		query.setLong("candidatoId", candidatoId);
		query.setLong("colaboradorId", colaboradorId);
		query.executeUpdate();
	}
	
	

	/**
	 * Relatório de Atendimentos médicos
	 */
	public Collection<SolicitacaoExame> findAtendimentosMedicos(Date inicio, Date fim, String[] motivos, MedicoCoordenador medicoCoordenador, Long empresaId, boolean agruparPorMotivo, boolean ordenarPorNome, char situacao)
	{
		StringBuilder hql = new StringBuilder("select distinct new SolicitacaoExame(se.id, se.data, se.motivo, se.ordem, se.observacao, se.medicoCoordenador.nome, co.nome, co.nomeComercial, ca.nome, cargo.nome, cargoDoCandidato.nome, co.matricula) ");
		hql.append("from SolicitacaoExame se ");
		hql.append("left join se.candidato ca ");
		
		hql.append("left join ca.candidatoSolicitacaos cs ");
		hql.append("left join cs.solicitacao sol ");
		hql.append("left join sol.faixaSalarial fs2 ");
		hql.append("left join fs2.cargo cargoDoCandidato ");
		
		hql.append("left join se.colaborador co ");
		hql.append("left join co.historicoColaboradors hc ");
		hql.append("left join hc.faixaSalarial fs ");
		hql.append("left join fs.cargo cargo ");
		hql.append("left join se.exameSolicitacaoExames es ");
		hql.append("left join es.realizacaoExame re ");
		
		hql.append("where se.empresa.id = :empresaId ");
		
		hql.append("and (ca != null and (sol = null or sol.id = (select max(s2.id) from CandidatoSolicitacao cs2 ");
		hql.append(" 								join cs2.solicitacao s2 join cs2.candidato ca2 ");
		hql.append(" 								where ca2.id=ca.id and s2.data <= :fim)) ");
		hql.append("  or hc.data = (select max(hc2.data) from HistoricoColaborador hc2 ");
		hql.append("				where hc2.colaborador.id = co.id ");
		hql.append("				and hc2.data <= :hoje) ) ");
		
		if (motivos.length > 0)
			hql.append("and se.motivo in (:motivos) ");
		
		if (medicoCoordenador!=null && medicoCoordenador.getId()!=null)
			hql.append("and se.medicoCoordenador.id = :medicoId ");

		hql.append("and se.data between :inicio and :fim ");
		
		if (situacao == 'A') {
			hql.append("and re.id is not null ");
		} else if (situacao == 'S') {
			hql.append("and re.id is null ");
		}

		hql.append("order by ");
		
		if (agruparPorMotivo)
			hql.append("se.motivo ASC, ");

		if (ordenarPorNome)
			hql.append("co.nome, ca.nome, se.data, se.ordem");
		else
			hql.append("se.data, se.ordem, co.nome, ca.nome");
		
		Query query = getSession().createQuery(hql.toString());

		query.setDate("inicio", inicio);
		query.setDate("fim", fim);
		
		if (motivos.length > 0)
			query.setParameterList("motivos", motivos, Hibernate.STRING);

		if (medicoCoordenador!=null && medicoCoordenador.getId()!=null)
			query.setLong("medicoId", medicoCoordenador.getId());
		
		query.setLong("empresaId", empresaId);
		query.setDate("hoje", new Date());
		
		return query.list();
	}

	public SolicitacaoExame findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(SolicitacaoExame.class, "se");
		criteria.createCriteria("se.colaborador", "col", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("se.candidato", "ca", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("se.medicoCoordenador", "medico", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("se.empresa", "emp", CriteriaSpecification.LEFT_JOIN);

		ProjectionList projectionList = Projections.projectionList().create();
		projectionList.add(Projections.property("se.id"), "id");
		projectionList.add(Projections.property("se.data"), "data");
		projectionList.add(Projections.property("se.ordem"), "ordem");
		projectionList.add(Projections.property("se.observacao"), "observacao");
		projectionList.add(Projections.property("se.motivo"), "motivo");
		projectionList.add(Projections.property("medico.id"), "medicoCoordenadorId");
		projectionList.add(Projections.property("medico.nome"), "medicoCoordenadorNome");
		projectionList.add(Projections.property("ca.id"), "candidatoId");
		projectionList.add(Projections.property("col.id"), "colaboradorId");
		projectionList.add(Projections.property("ca.nome"), "candidatoNome");
		projectionList.add(Projections.property("col.id"), "colaboradorId");
		projectionList.add(Projections.property("col.nome"), "colaboradorNome");
		projectionList.add(Projections.property("col.nomeComercial"), "colaboradorNomeComercial");
		projectionList.add(Projections.property("emp.id"), "empresaId");
		criteria.setProjection(projectionList);

		criteria.add(Expression.eq("se.id" , id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(SolicitacaoExame.class));

		return (SolicitacaoExame) criteria.uniqueResult();
	}

	public void removeByCandidato(Long candidatoId) {
		String queryHQL = "delete from ExameSolicitacaoExame e where e.solicitacaoExame.id in (select se.id from SolicitacaoExame se where se.candidato.id = :candidatoId)";
		getSession().createQuery(queryHQL).setLong("candidatoId",candidatoId).executeUpdate();
	
		queryHQL = "delete from SolicitacaoExame se where se.candidato.id = :candidatoId";
		getSession().createQuery(queryHQL).setLong("candidatoId",candidatoId).executeUpdate();
	}

	public void removeByColaborador(Long colaboradorId) {
		String queryHQL = "delete from ExameSolicitacaoExame e where e.solicitacaoExame.id in (select se.id from SolicitacaoExame se where se.colaborador.id = :colaboradorId)";
		getSession().createQuery(queryHQL).setLong("colaboradorId",colaboradorId).executeUpdate();
	
		queryHQL = "delete from SolicitacaoExame se where se.colaborador.id = :colaboradorId";
		getSession().createQuery(queryHQL).setLong("colaboradorId",colaboradorId).executeUpdate();
	}

	public Integer findProximaOrdem(Date data) 
	{
		Criteria criteria = getSession().createCriteria(SolicitacaoExame.class, "se");
		criteria.setProjection(Projections.max("se.ordem"));
		criteria.add(Expression.eq("se.data", data));

		return criteria.uniqueResult() == null ? 1 : ((Integer) criteria.uniqueResult()) + 1;  
	}
	
	public void ajustaOrdem(Date data, Integer ordemInicial, Integer ordemFinal, Integer ajuste) 
	{
		String hql = "update SolicitacaoExame set ordem = ordem + :ajuste where data = :data";
		
		if (ordemInicial != null)
			hql += " and ordem >= :ordemInicial";
		
		if (ordemFinal != null)
			hql += " and ordem <= :ordemFinal";
		
		Query query = getSession().createQuery(hql);

		query.setInteger("ajuste", ajuste);
		query.setDate("data", data);
		
		if (ordemInicial != null)
			query.setInteger("ordemInicial", ordemInicial);
		
		if (ordemFinal != null)
			query.setInteger("ordemFinal", ordemFinal);

		query.executeUpdate();
	}
}
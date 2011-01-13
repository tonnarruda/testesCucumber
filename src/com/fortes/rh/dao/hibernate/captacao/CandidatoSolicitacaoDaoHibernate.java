package com.fortes.rh.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;

@SuppressWarnings("unchecked")
public class CandidatoSolicitacaoDaoHibernate extends GenericDaoHibernate<CandidatoSolicitacao> implements CandidatoSolicitacaoDao
{
    public CandidatoSolicitacao findByCandidatoSolicitacao(CandidatoSolicitacao cand)
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
        criteria.createCriteria("cs.candidato", "c", Criteria.LEFT_JOIN);
        criteria.createCriteria("cs.solicitacao", "s", Criteria.LEFT_JOIN);
        criteria.createCriteria("s.solicitante", "u", Criteria.LEFT_JOIN);
        criteria.createCriteria("s.areaOrganizacional", "a", Criteria.LEFT_JOIN);
        criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
        criteria.createCriteria("fs.cargo", "cr", Criteria.LEFT_JOIN);

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cs.id"), "id");
        p.add(Projections.property("cs.triagem"), "triagem");
        p.add(Projections.property("c.id"), "candidatoId");
        p.add(Projections.property("c.nome"), "candidatoNome");
        p.add(Projections.property("c.contratado"), "candidatoContratado");
        p.add(Projections.property("c.blackList"), "projectionCandidatoBlackList");
        p.add(Projections.property("s.id"), "solicitacaoId");
        p.add(Projections.property("s.quantidade"), "solicitacaoQtd");
        p.add(Projections.property("u.id"), "solicitanteId");
        p.add(Projections.property("u.nome"), "solicitanteNome");
        p.add(Projections.property("a.id"), "areaOrganizacionalId");
        p.add(Projections.property("a.nome"), "areaOrganizacionalNome");
        p.add(Projections.property("fs.id"), "faixaId");
        p.add(Projections.property("fs.nome"), "faixaNome");
        p.add(Projections.property("cr.id"), "cargoId");
        p.add(Projections.property("cr.nome"), "cargoNome");

        criteria.setProjection(p);

        criteria.add(Expression.eq("cs.id", cand.getId()));
        criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

        Collection<CandidatoSolicitacao> result = criteria.list();

        if (result.size() > 0)
            return (CandidatoSolicitacao) result.toArray()[0];
        else
            return null;
    }

    public Collection<CandidatoSolicitacao> findNaoAptos(Long solicitacaoId)
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
        criteria.createCriteria("cs.candidato", "c");
        criteria.createCriteria("cs.historicoCandidatos", "h");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cs.id"), "id");
        p.add(Projections.property("c.id"), "candidatoId");
        p.add(Projections.property("c.nome"), "candidatoNome");
        p.add(Projections.property("c.contato.email"), "projectionCandidatoEmail");


        criteria.setProjection(p);
        criteria.add(Expression.eq("cs.solicitacao.id", solicitacaoId));
        criteria.add(Expression.eq("h.apto", false));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

        return criteria.list();
    }
    
    public void updateSolicitacaoCandidatos(Solicitacao solicitacao, Collection<Long> ids)
    {
        String hql = "update CandidatoSolicitacao set solicitacao = :solicitacao where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setEntity("solicitacao", solicitacao);
        query.setParameterList("ids", ids, Hibernate.LONG);
        query.executeUpdate();
    }

    public Collection findCandidatosAptosMover(Long[] candidatosSolicitacaoId, Solicitacao solicitacao)
    {
        String hql = "select cs.id from CandidatoSolicitacao cs where cs.id in (:ids) and cs.candidato.id not in " +
            "(select cs2.candidato.id from CandidatoSolicitacao cs2 where cs2.solicitacao = :solicitacao)";

        Query query = getSession().createQuery(hql);
        query.setEntity("solicitacao", solicitacao);
        query.setParameterList("ids", candidatosSolicitacaoId, Hibernate.LONG);

        return query.list();
    }

    public CandidatoSolicitacao findCandidatoSolicitacaoById(Long candidatoSolicitacaoId)
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
        criteria.createCriteria("cs.candidato", "c");
        criteria.createCriteria("cs.solicitacao", "s");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cs.id"), "id");
        p.add(Projections.property("c.id"), "candidatoId");
        p.add(Projections.property("c.blackList"), "projectionCandidatoBlackList");
        p.add(Projections.property("s.id"), "solicitacaoId");

        criteria.setProjection(p);
        criteria.add(Expression.eq("cs.id", candidatoSolicitacaoId));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

        return (CandidatoSolicitacao) criteria.list().toArray()[0];
    }

    public Collection<CandidatoSolicitacao> getCandidatoSolicitacaoList(Integer page, Integer pagingSize, Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, boolean semHistorico, String observacaoRH, String nomeBusca)
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
        criteria.createCriteria("cs.candidato", "c", Criteria.LEFT_JOIN);
        criteria.createCriteria("cs.historicoCandidatos", "h", Criteria.LEFT_JOIN);
        criteria.createCriteria("h.etapaSeletiva", "e", Criteria.LEFT_JOIN);

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cs.id"), "id");
        p.add(Projections.property("c.id"), "candidatoId");
        p.add(Projections.property("c.nome"), "candidatoNome");
        p.add(Projections.property("c.idF2RH"), "candidatoIdF2RH");
        p.add(Projections.property("c.contratado"), "candidatoContratado");
        p.add(Projections.property("c.pessoal.indicadoPor"), "candidatoIndicadoPor");
        p.add(Projections.property("h.responsavel"), "responsavel");
        p.add(Projections.property("h.data"), "data");
        p.add(Projections.property("h.observacao"), "observacao");
        p.add(Projections.property("h.apto"), "projectionApto");
        p.add(Projections.property("e.id"), "projectionEtapaId");
        p.add(Projections.property("e.nome"), "projectionEtapaNome");
        criteria.setProjection(p);

        criteria.add(Expression.eq("cs.triagem", false));

        if(contratado)
        	criteria.add(Expression.eq("c.contratado", false));

        if(visualizar != null)
        {
        	if(semHistorico)
        	{
                Disjunction disjunction = Expression.disjunction();
                disjunction.add(Expression.eq("h.apto", visualizar));
                disjunction.add(Expression.isNull("h.apto"));
                criteria.add(disjunction);
        	}
        	else
        		criteria.add(Expression.eq("h.apto", visualizar));
        }

        if(etapaSeletivaId != null)
        	criteria.add(Expression.eq("e.id", etapaSeletivaId));

        if (StringUtils.isNotBlank(indicadoPor))
        	criteria.add(Restrictions.sqlRestriction("normalizar(c1_.indicadoPor) ilike  normalizar(?)", "%" + indicadoPor + "%", Hibernate.STRING));

        if (StringUtils.isNotBlank(observacaoRH))
			criteria.add(Restrictions.sqlRestriction("normalizar(c1_.observacaoRH) ilike  normalizar(?)", "%" + observacaoRH + "%", Hibernate.STRING));
        
        if (StringUtils.isNotBlank(nomeBusca))
        	criteria.add(Restrictions.sqlRestriction("normalizar(c1_.nome) ilike  normalizar(?)", "%" + nomeBusca + "%", Hibernate.STRING));

        criteria.add(Expression.eq("cs.solicitacao.id", solicitacaoId));

        if (page != null && pagingSize != null)
        {
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
        }

        Disjunction any = Expression.disjunction();
        any.add(Expression.isNull("h.id"));
        any.add(Expression.sql("h2_.id = (select h3.id from HistoricoCandidato h3 left join EtapaSeletiva e2 on e2.id = h3.etapaSeletiva_id where h3.candidatoSolicitacao_id = this_.id order by h3.data desc, e2.ordem desc limit 1) "));
        criteria.add(any);

        criteria.addOrder(Order.asc("c.nome"));
        criteria.addOrder(Order.asc("h.data"));
        criteria.addOrder(Order.asc("e.ordem"));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

        return criteria.list();
    }

    public Collection<CandidatoSolicitacao> findBySolicitacaoTriagem(Long solicitacaoId)
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
        criteria.createCriteria("cs.candidato", "c");
        criteria.createCriteria("c.endereco.cidade", "ci");
        criteria.createCriteria("c.endereco.uf", "u");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cs.id"), "id");
        p.add(Projections.property("c.id"), "candidatoId");
        p.add(Projections.property("c.nome"), "candidatoNome");
        p.add(Projections.property("c.dataAtualizacao"), "projectionCandidatoDataAtualizacao");
        p.add(Projections.property("c.origem"), "projectionCandidatoOrigem");
        p.add(Projections.property("c.pessoal.escolaridade"), "projectionCandidatoEscolaridade");
        p.add(Projections.property("cs.id"), "solicitacaoId");
        p.add(Projections.property("ci.nome"), "projectionCidadeNome");
        p.add(Projections.property("u.sigla"), "projectionUfSigla");

        criteria.setProjection(p);
        criteria.add(Expression.eq("cs.solicitacao.id", solicitacaoId));
        criteria.add(Expression.eq("cs.triagem", true));

        criteria.addOrder(Order.asc("c.nome"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

        return criteria.list();
    }
    
    public Collection<CandidatoSolicitacao> findByFiltroSolicitacaoTriagem(Boolean triagem)
    {

    	Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
        criteria.createCriteria("cs.solicitacao", "s", Criteria.LEFT_JOIN);

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.distinct(Projections.property("s.id")), "solicitacaoId");
        p.add(Projections.property("cs.triagem"), "triagem");
        p.add(Projections.property("s.descricao"), "ProjectionSolicitacaoDescricao");

        criteria.setProjection(p);
        criteria.add(Expression.eq("cs.triagem", triagem));
        criteria.add(Expression.eq("s.encerrada", false));

        criteria.addOrder(Order.asc("s.descricao"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

        return criteria.list();
    }

    public void updateTriagem(Long candidatoSolicitacaoid, boolean triagem)
    {
        String hql = "update CandidatoSolicitacao set triagem = :triagem where id = :candidatoSolicitacaoid";

        Query query = getSession().createQuery(hql);

        query.setBoolean("triagem", triagem);
        query.setLong("candidatoSolicitacaoid", candidatoSolicitacaoid);

        query.executeUpdate();

    }

    public CandidatoSolicitacao getCandidatoSolicitacaoByCandidato(Long id)
    {
        Criteria criteria = montaCriteriaFindCandidatoSolicitacao();
        criteria.add(Expression.eq("c.id", id));
        criteria.setMaxResults(1);

        return (CandidatoSolicitacao) criteria.uniqueResult();
    }

    public Collection<CandidatoSolicitacao> findCandidatoSolicitacaoById(Long[] candidatoSolicitacaoIds)
    {
        Criteria criteria = montaCriteriaFindCandidatoSolicitacao();
        criteria.add(Expression.in("cs.id", candidatoSolicitacaoIds));

        return criteria.list();
    }

    private Criteria montaCriteriaFindCandidatoSolicitacao()
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
        criteria.createCriteria("cs.candidato", "c");
        criteria.createCriteria("cs.solicitacao", "s");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cs.id"), "id");
        p.add(Projections.property("c.id"), "candidatoId");
        p.add(Projections.property("s.id"), "solicitacaoId");

        criteria.setProjection(p);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

        return criteria;
    }

    public Collection<CandidatoSolicitacao> getCandidatosBySolicitacao(Solicitacao solicitacao, ArrayList<Long> idCandidatosComHistoricos)
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
        criteria.createCriteria("cs.candidato", "c");
        criteria.createCriteria("cs.solicitacao", "s");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cs.id"), "id");
        p.add(Projections.property("c.id"), "candidatoId");
        p.add(Projections.property("c.nome"), "candidatoNome");
        p.add(Projections.property("c.pessoal.cpf"), "candidatoPessoalCpf");
        p.add(Projections.property("s.id"), "solicitacaoId");

        criteria.setProjection(p);
        criteria.add(Expression.eq("s.id", solicitacao.getId()));

        if(idCandidatosComHistoricos != null && !idCandidatosComHistoricos.isEmpty())
            criteria.add(Expression.not(Expression.in("cs.id", idCandidatosComHistoricos)));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

        return criteria.list();
    }

    public Collection<Long> getCandidatosBySolicitacao(Long solicitacaoId)
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
        criteria.createCriteria("cs.candidato", "c");
        criteria.createCriteria("cs.solicitacao", "s");

        ProjectionList p = Projections.projectionList().create();

        p.add(Projections.property("c.id"), "candidatoId");

        criteria.setProjection(p);
        criteria.add(Expression.eq("s.id", solicitacaoId));

        return criteria.list();
    }

    public Integer getCount(Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, String observacaoRH, String nomeBusca)
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
        criteria.createCriteria("cs.candidato", "c", Criteria.LEFT_JOIN);
        criteria.createCriteria("cs.historicoCandidatos", "h", Criteria.LEFT_JOIN);
        criteria.createCriteria("h.etapaSeletiva", "e", Criteria.LEFT_JOIN);

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cs.id"), "id");
        criteria.setProjection(p);
        criteria.setProjection(Projections.rowCount());

        criteria.add(Expression.eq("cs.triagem", false));

        if(contratado)
        	criteria.add(Expression.eq("c.contratado", false));

        if(visualizar != null)
        	criteria.add(Expression.eq("h.apto", visualizar));

        if(etapaSeletivaId != null)
        	criteria.add(Expression.eq("e.id", etapaSeletivaId));

        if (StringUtils.isNotBlank(indicadoPor))
        	criteria.add(Restrictions.sqlRestriction("normalizar(c1_.indicadoPor) ilike  normalizar(?)", "%" + indicadoPor + "%", Hibernate.STRING));

        if (StringUtils.isNotBlank(observacaoRH))
			criteria.add(Restrictions.sqlRestriction("normalizar(c1_.observacaoRH) ilike  normalizar(?)", "%" + observacaoRH + "%", Hibernate.STRING));

        if (StringUtils.isNotBlank(nomeBusca))
        	criteria.add(Restrictions.sqlRestriction("normalizar(c1_.nome) ilike  normalizar(?)", "%" + nomeBusca + "%", Hibernate.STRING));
        
        criteria.add(Expression.eq("cs.solicitacao.id", solicitacaoId));

        Disjunction any = Expression.disjunction();
        any.add(Expression.isNull("h.id"));
        any.add(Expression.sql("h2_.id = (select h3.id from HistoricoCandidato h3 left join EtapaSeletiva e2 on e2.id = h3.etapaSeletiva_id where h3.candidatoSolicitacao_id = this_.id order by h3.data desc, e2.ordem desc limit 1) "));
        criteria.add(any);

        return (Integer) criteria.uniqueResult();
    }

	public Collection<CandidatoSolicitacao> findHistoricoAptoByEtapaSolicitacao(Long empresaId, Long[] etapaIds) 
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(HistoricoCandidato.class, "hc2");
        ProjectionList pSub = Projections.projectionList().create();

        pSub.add(Projections.max("hc2.data"));
        subQuery.setProjection(pSub);
        subQuery.add(Restrictions.sqlRestriction("this0__.candidatoSolicitacao_id=cs1_.id"));
        
		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs");
		criteria.createCriteria("cs.solicitacao", "s");
		criteria.createCriteria("s.faixaSalarial", "fs");
		criteria.createCriteria("fs.cargo", "c");
		criteria.createCriteria("cs.candidato", "ca");
		criteria.createCriteria("hc.etapaSeletiva", "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cs.id"), "id");
		p.add(Projections.property("es.nome"), "projectionEtapaNome");
		p.add(Projections.property("es.id"), "projectionEtapaId");
		p.add(Projections.property("ca.nome"), "candidatoNome");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("c.nome"), "cargoNome");
		
		criteria.setProjection(p);

		criteria.add(Expression.eq("hc.apto", true));
		criteria.add(Expression.eq("s.encerrada", false));
		criteria.add(Expression.eq("s.empresa.id", empresaId));
		criteria.add(Expression.eq("ca.contratado", false));
		
		if(etapaIds.length > 0)
			criteria.add(Expression.in("es.id", etapaIds));
		
		criteria.add(Subqueries.propertyEq("hc.data", subQuery));

		criteria.addOrder(Order.asc("es.nome"));
		criteria.addOrder(Order.asc("ca.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

		return criteria.list();
	}
}
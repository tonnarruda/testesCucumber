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
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

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
        p.add(Projections.property("s.data"), "solicitacaoData");
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
        criteria.add(Expression.eq("h.apto", Apto.NAO));

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
        p.add(Projections.property("cs.status"), "status");
        p.add(Projections.property("cs.statusAutorizacaoGestor"), "statusAutorizacaoGestor");
        p.add(Projections.property("cs.obsAutorizacaoGestor"), "obsAutorizacaoGestor");
        p.add(Projections.property("cs.dataAutorizacaoGestor"), "dataAutorizacaoGestor");
        p.add(Projections.property("c.id"), "candidatoId");
        p.add(Projections.property("c.blackList"), "projectionCandidatoBlackList");
        p.add(Projections.property("s.id"), "solicitacaoId");

        criteria.setProjection(p);
        criteria.add(Expression.eq("cs.id", candidatoSolicitacaoId));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

        return (CandidatoSolicitacao) criteria.list().toArray()[0];
    }

    //TODO BACALHAU, ajustar parametros. Ex.: o contratado passa true e usa false
    public Collection<CandidatoSolicitacao> getCandidatoSolicitacaoList(Integer page, Integer pagingSize, Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, boolean semHistorico, String observacaoRH, String nomeBusca, Character status)
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cs.id"), "id");
        p.add(Projections.property("cs.status"), "status");
        p.add(Projections.property("cs.statusAutorizacaoGestor"), "statusAutorizacaoGestor");
        p.add(Projections.property("cs.dataAutorizacaoGestor"), "dataAutorizacaoGestor");
        p.add(Projections.property("cs.obsAutorizacaoGestor"), "obsAutorizacaoGestor");
        p.add(Projections.property("col.solicitacao.id"), "solicitacaoId");//usado para regra de excluir em candidatosolicitacaoList
        p.add(Projections.property("c.id"), "candidatoId");
        p.add(Projections.property("c.nome"), "candidatoNome");
        p.add(Projections.property("c.contato.foneFixo"), "candidatoFoneFixo");
        p.add(Projections.property("c.contato.foneCelular"), "candidatoFoneCelular");
        p.add(Projections.property("c.contato.ddd"), "candidatoDdd");
        p.add(Projections.property("c.contato.email"), "candidatoEmail");
        p.add(Projections.property("c.contratado"), "candidatoContratado");
        p.add(Projections.property("c.pessoal.indicadoPor"), "candidatoIndicadoPor");
        p.add(Projections.property("h.responsavel"), "responsavel");
        p.add(Projections.property("h.data"), "data");
        p.add(Projections.property("h.observacao"), "observacao");
        p.add(Projections.property("h.apto"), "projectionApto");
        p.add(Projections.property("e.id"), "projectionEtapaId");
        p.add(Projections.property("e.nome"), "projectionEtapaNome");
        p.add(Projections.property("emp.id"), "projectionCandidatoEmpresaId");
        p.add(Projections.property("emp.nome"), "projectionCandidatoEmpresaNome");
        criteria.setProjection(p);
        
        montaConsulta(criteria, solicitacaoId, etapaSeletivaId, indicadoPor, visualizar, contratado, semHistorico, observacaoRH, nomeBusca, status);

        if (page != null && pagingSize != null)
        {
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
        }

        criteria.addOrder(Order.asc("c.nome"));
        criteria.addOrder(Order.asc("h.data"));
        criteria.addOrder(Order.asc("e.ordem"));

        criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

        return criteria.list();
    }

	private void montaConsulta(Criteria criteria, Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, boolean semHistorico, String observacaoRH, String nomeBusca, Character status) 
	{
		// cuidado com a ordem das joins, tem um alias amarrado...h2_ IMUNDO
        criteria.createCriteria("cs.candidato", "c", Criteria.LEFT_JOIN);
        criteria.createCriteria("cs.historicoCandidatos", "h", Criteria.LEFT_JOIN);
        criteria.createCriteria("h.etapaSeletiva", "e", Criteria.LEFT_JOIN);
        criteria.createCriteria("c.empresa", "emp", Criteria.LEFT_JOIN);
        criteria.createCriteria("c.colaborador", "col", Criteria.LEFT_JOIN);
        
		criteria.add(Expression.eq("cs.triagem", false));

        if(contratado)
        	criteria.add(Expression.eq("c.contratado", false));

        if(visualizar != null)
        {
        	if(semHistorico)
        	{
                Disjunction disjunction = Expression.disjunction();
                
                if(visualizar)
                {
                	disjunction.add(Expression.ne("h.apto", Apto.NAO));
                	disjunction.add(Expression.isNull("h.apto"));
                }
                else
                	disjunction.add(Expression.eq("h.apto", Apto.NAO));
                
                criteria.add(disjunction);
        	}
        	else
        	{
        		if(visualizar)
        			criteria.add(Expression.ne("h.apto", Apto.NAO));
        		else
        			criteria.add(Expression.eq("h.apto", Apto.NAO));
        	}
        }
        
        if(status != null && status == 'I'){
        	criteria.add(Expression.eq("cs.status", status));
        	criteria.add(Expression.or(Expression.isNull("h.apto"), Expression.eq("h.apto", Apto.INDIFERENTE)));
        }
        if(status != null && status == 'A')
        	criteria.add(Expression.eq("h.apto", Apto.SIM));
        if(status != null && status == 'P')
        	criteria.add(Expression.eq("c.contratado", true));

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
        any.add(Expression.sql("h2_.id = (select hcand.id from HistoricoCandidato hcand left join EtapaSeletiva e2 on e2.id = hcand.etapaSeletiva_id "
        		+ "where hcand.candidatoSolicitacao_id = this_.id order by hcand.data desc, e2.ordem desc limit 1) "));
        criteria.add(any);
        
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}
    
    public Collection<CandidatoSolicitacao> getCandidatoSolicitacaoEtapasEmGrupo(Long solicitacaoId, Long etapaSeletivaId)
    {
    	Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");
    	criteria.createCriteria("cs.candidato", "c", Criteria.LEFT_JOIN);
    	criteria.createCriteria("cs.historicoCandidatos", "h", Criteria.LEFT_JOIN);
    	criteria.createCriteria("h.etapaSeletiva", "e", Criteria.LEFT_JOIN);
    	
    	ProjectionList p = Projections.projectionList().create();
    	p.add(Projections.property("cs.id"), "id");
    	p.add(Projections.property("c.id"), "candidatoId");
    	p.add(Projections.property("c.nome"), "candidatoNome");
    	criteria.setProjection(p);
    	
   		criteria.add(Expression.eq("cs.solicitacao.id", solicitacaoId));
   		
   		if(etapaSeletivaId != null)
        	criteria.add(Expression.eq("e.id", etapaSeletivaId));

   		criteria.add(Expression.eq("cs.triagem", false));
    	
		Disjunction disjunction = Expression.disjunction();
		disjunction.add(Expression.ne("h.apto", Apto.NAO));
		disjunction.add(Expression.isNull("h.apto"));
		criteria.add(disjunction);

		Disjunction disjunction2 = Expression.disjunction();
		disjunction2.add(Expression.eq("c.contratado", false));
		disjunction2.add(Expression.not(Expression.eq("cs.status", StatusCandidatoSolicitacao.CONTRATADO)));
		criteria.add(disjunction2);
    	
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
        criteria.createCriteria("c.endereco.cidade", "ci", Criteria.LEFT_JOIN);
        criteria.createCriteria("c.endereco.uf", "u", Criteria.LEFT_JOIN);

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

    public void updateTriagem(Long[] candidatoSolicitacaoIdsSelecionados, boolean triagem)
    {
        String hql = "update CandidatoSolicitacao set triagem = :triagem where id in (:candidatoSolicitacaoIdsSelecionados)";

        Query query = getSession().createQuery(hql);

        query.setBoolean("triagem", triagem);
        query.setParameterList("candidatoSolicitacaoIdsSelecionados", candidatoSolicitacaoIdsSelecionados, Hibernate.LONG);

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
       	criteria.add(Expression.eq("cs.triagem", false));

        return criteria.list();
    }

    public Integer getCount(Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, String observacaoRH, String nomeBusca, Character status, boolean semHistorico)
    {
        Criteria criteria = getSession().createCriteria(CandidatoSolicitacao.class, "cs");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cs.id"), "id");
        criteria.setProjection(p);
        criteria.setProjection(Projections.rowCount());

        montaConsulta(criteria, solicitacaoId, etapaSeletivaId, indicadoPor, visualizar, contratado, semHistorico, observacaoRH, nomeBusca, status);
        
        return (Integer) criteria.uniqueResult();
    }

	public Collection<CandidatoSolicitacao> getCandidatosBySolicitacao(Long[] etapaIds, Long empresaId, char statusSolicitacao, char situacaoCandidato, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs");
		criteria.createCriteria("cs.solicitacao", "s");
		criteria.createCriteria("s.faixaSalarial", "fs");
		criteria.createCriteria("fs.cargo", "c");
		criteria.createCriteria("s.areaOrganizacional", "ao");
		criteria.createCriteria("cs.candidato", "ca");
		criteria.createCriteria("hc.etapaSeletiva", "es");
		criteria.createCriteria("s.motivoSolicitacao", "ms");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cs.id"), "id");
		p.add(Projections.property("es.nome"), "projectionEtapaNome");
		p.add(Projections.property("es.id"), "projectionEtapaId");
		p.add(Projections.property("ca.nome"), "candidatoNome");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.apto"), "apto");
		p.add(Projections.property("c.nome"), "cargoNome");
		p.add(Projections.sqlProjection("monta_familia_area(ao5_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNome");
		p.add(Projections.property("s.observacaoLiberador"), "solicitacaoObservacaoLiberador");
		p.add(Projections.property("ms.descricao"), "descricaoMotivoSolicitacao");
		
		criteria.setProjection(p);

		if (statusSolicitacao == StatusSolicitacao.ABERTA)
			criteria.add(Expression.eq("s.encerrada", false));
		else if (statusSolicitacao == StatusSolicitacao.ENCERRADA)
			criteria.add(Expression.eq("s.encerrada", true));
		
		if (situacaoCandidato != Apto.INDIFERENTE)
			criteria.add(Expression.eq("hc.apto", situacaoCandidato));
		
		criteria.add(Expression.eq("s.empresa.id", empresaId));
		
		if (etapaIds.length > 0)
			criteria.add(Expression.in("es.id", etapaIds));
		
		if (dataIni != null && dataFim != null) 
			criteria.add(Expression.between("hc.data", dataIni, dataFim));
		
		criteria.addOrder(Order.asc("es.nome"));
		criteria.addOrder(Order.asc("ao.nome"));
		criteria.addOrder(Order.asc("ca.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));

		return criteria.list();
	}

	public void setStatusByColaborador(char status, Long... colaboradoresIds) 
	{
		String hql = "update CandidatoSolicitacao set status = :status where candidato.id in (select candidato.id from Colaborador where id in (:colaboradorId))";

		Query query = getSession().createQuery(hql);
		query.setCharacter("status", status);
		query.setParameterList("colaboradorId", colaboradoresIds, Hibernate.LONG);
		
		query.executeUpdate();
	}

	public void setStatusBySolicitacaoAndCandidato(char status, Long candidatoId, Long solicitacaoId) 
	{
		String hql = "update CandidatoSolicitacao set status = :status where candidato.id = :candidatoId and solicitacao.id = :solicitacaoId ";
		
		Query query = getSession().createQuery(hql);
		query.setCharacter("status", status);
		query.setLong("candidatoId", candidatoId);
		query.setLong("solicitacaoId", solicitacaoId);
		
		query.executeUpdate();
	}

	public void removeByCandidato(Long candidatoId) 
	{
		String hql = "delete from HistoricoCandidato hs where hs.candidatoSolicitacao.id in (select id from CandidatoSolicitacao c where c.candidato.id = :candidatoId)";
		Query query = getSession().createQuery(hql);
		query.setLong("candidatoId", candidatoId);
		query.executeUpdate();

		hql = "delete from CandidatoSolicitacao where candidato.id = :candidatoId";
		query = getSession().createQuery(hql);
		query.setLong("candidatoId", candidatoId);
		query.executeUpdate();
	}

	public Collection<ColaboradorQuestionario> findAvaliacoesCandidatoSolicitacao(Long solicitacaoId, Long candidatoId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorQuestionario(cq.id, a.id, a.titulo, cs.candidato.id) ");
		hql.append("from CandidatoSolicitacao cs ");
		hql.append("left join cs.solicitacao s ");
		hql.append("left join s.solicitacaoAvaliacaos sa ");
		hql.append("left join s.colaboradorQuestionarios cq with cq.candidato.id = cs.candidato.id and cq.avaliacao.id = sa.avaliacao.id ");
		hql.append("left join sa.avaliacao a ");
		hql.append("where cs.solicitacao.id = :solicitacaoId ");
		hql.append("and cs.candidato.id = :candidatoId");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("solicitacaoId", solicitacaoId);
		query.setLong("candidatoId", candidatoId);
		
		return query.list();
	}
	
	public void atualizaCandidatoSolicitacaoStatusContratado(Long colaboradorId)
	{
		String hql = "update CandidatoSolicitacao set status = :status where solicitacao.id = (select solicitacao.id from Colaborador where id = :colaboradorId) and candidato.id = (select candidato.id from Colaborador where id = :colaboradorId) ";

		Query query = getSession().createQuery(hql);
		query.setCharacter("status", StatusCandidatoSolicitacao.CONTRATADO);
		query.setLong("colaboradorId", colaboradorId);
		
		query.executeUpdate();
	}

	public Collection<CandidatoSolicitacao> findColaboradorParticipantesDaSolicitacaoByAreas(Long[] areasOrganizacionaisId, String solicitacaoDescricaoBusca, String colaboradorNomeBusca, char statusBusca, Integer page, Integer pagingSize){
		Criteria criteria = getSession().createCriteria(Solicitacao.class, "s");
		criteria.createCriteria("s.areaOrganizacional", "aos");
		criteria.createCriteria("s.estabelecimento", "est");
		criteria.createCriteria("s.faixaSalarial", "fs");
		criteria.createCriteria("fs.cargo", "cg");
		criteria.createCriteria("s.candidatoSolicitacaos", "cs");
		criteria.createCriteria("cs.candidato", "c");
		criteria.createCriteria("c.colaborador", "col");
		criteria.createCriteria("col.historicoColaboradors", "hc");
		
		ProjectionList p = projectionsFindColaboradorParticipantesDaSolicitacaoByAreas();
		criteria.setProjection(Projections.distinct(p));
		
		if(!StringUtils.isEmpty(solicitacaoDescricaoBusca))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.descricao) ilike  normalizar(?)", "%" + solicitacaoDescricaoBusca + "%", Hibernate.STRING));
		
		if(!StringUtils.isEmpty(colaboradorNomeBusca))
			criteria.add(Restrictions.sqlRestriction("normalizar(col7_.nome) ilike  normalizar(?)", "%" + colaboradorNomeBusca + "%", Hibernate.STRING));
		
		if(statusBusca != 'T')
			criteria.add(Expression.eq("cs.statusAutorizacaoGestor", statusBusca));
		
		criteria.add(Expression.isNotNull("cs.statusAutorizacaoGestor"));
		
		criteria.add(Expression.in("hc.areaOrganizacional.id", areasOrganizacionaisId));
		criteria.add(Expression.eq("s.encerrada", false));
		criteria.add(Subqueries.propertyEq("hc.data", montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO)));
		
		if(page != null){
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);			
		}
		
		criteria.addOrder(Order.asc("col.nome"));
		criteria.addOrder(Order.asc("s.descricao"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoSolicitacao.class));
		
		return criteria.list();
	}

	private ProjectionList projectionsFindColaboradorParticipantesDaSolicitacaoByAreas() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("s.id"), "solicitacaoId");
		p.add(Projections.property("s.descricao"), "solicitacaoDescricao");
		p.add(Projections.property("col.id"), "colaboradorId");
		p.add(Projections.property("col.nome"), "colaboradorNome");
		p.add(Projections.property("cs.id"), "id");
		p.add(Projections.property("cs.statusAutorizacaoGestor"), "statusAutorizacaoGestor");
		p.add(Projections.property("cs.dataAutorizacaoGestor"), "dataAutorizacaoGestor");
		p.add(Projections.property("cs.obsAutorizacaoGestor"), "obsAutorizacaoGestor");
		p.add(Projections.property("fs.nome"), "faixaNome");
		p.add(Projections.property("cg.nome"), "cargoNome");
		p.add(Projections.property("est.nome"), "solicitacaoEstabelecimentoNome");
		p.add(Projections.sqlProjection("monta_familia_area(aos1_.id) as solicitacaoNomeArea", new String[] {"solicitacaoNomeArea"}, new Type[] {Hibernate.TEXT}), "solicitacaoNomeArea");
		return p;
	}
	
	private DetachedCriteria montaSubQueryHistoricoColaborador(Date data, Integer status)
	{
		return DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "col.id"))
				.add(Restrictions.le("hc2.data", data))
				.add(Restrictions.eq("hc2.status", status));
	}

	public void updateStatusAutorizacaoGestor(CandidatoSolicitacao candidatoSolicitacao) {
		String hql = "update CandidatoSolicitacao set "
				+ "statusAutorizacaoGestor = :statusAutorizacaoGestor, "
				+ "dataAutorizacaoGestor = :dataAutorizacaoGestor, "
				+ "obsAutorizacaoGestor = :obsAutorizacaoGestor "
				+ "where id = :id ";
		
		Query query = getSession().createQuery(hql);
		query.setDate("dataAutorizacaoGestor", candidatoSolicitacao.getDataAutorizacaoGestor());
		query.setCharacter("statusAutorizacaoGestor", candidatoSolicitacao.getStatusAutorizacaoGestor());
		query.setString("obsAutorizacaoGestor", candidatoSolicitacao.getObsAutorizacaoGestor());
		query.setLong("id", candidatoSolicitacao.getId());
		
		query.executeUpdate();
	}
}
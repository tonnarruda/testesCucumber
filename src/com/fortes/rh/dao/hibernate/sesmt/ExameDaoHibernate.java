package com.fortes.rh.dao.hibernate.sesmt;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;

@SuppressWarnings("unchecked")
public class ExameDaoHibernate extends GenericDaoHibernate<Exame> implements ExameDao
{
	public Exame findByIdProjection(Long exameId)
	{
		Criteria criteria = getSession().createCriteria(Exame.class, "e");
		criteria.createCriteria("e.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.periodico"), "periodico");
		p.add(Projections.property("e.periodicidade"), "periodicidade");
		p.add(Projections.property("emp.id"), "empresaIdProjection");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", exameId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Exame.class));

		return (Exame) criteria.uniqueResult();
	}

	public Collection<Exame> findByHistoricoFuncao(Long historicoFuncaoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Exame(e.id, e.nome) ");
		hql.append("from HistoricoFuncao as hf ");
		hql.append("join hf.exames as e ");
		hql.append("	where hf.id = :historicoFuncaoId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("historicoFuncaoId", historicoFuncaoId);

		return query.list();
	}

	public Collection<Long> findBySolicitacaoExame(Long solicitacaoExameId)
	{
		StringBuilder hql = new StringBuilder("select e.id ");
		hql.append("from ExameSolicitacaoExame exameSol ");
		hql.append("join exameSol.solicitacaoExame se ");
		hql.append("join exameSol.exame e ");
		hql.append("where se.id = :solicitacaoExameId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("solicitacaoExameId", solicitacaoExameId);

		return query.list();
	}

	public Collection<ExamesPrevistosRelatorio> findExamesPeriodicosPrevistos(Long empresaId, Date data, Long[] exameIds, Long[] estabelecimentoIds, Long[] areaIds, Long[] colaboradorIds, boolean imprimirAfastados, boolean imprimirDesligados)
	{
		String whereColaboradores = "", whereEstabelecimentos = "",
			   whereAreasOrganizacionais = "", whereExames = "",
			   afastados = "", whereDesligados = "";

		if (areaIds != null && areaIds.length > 0)
			whereAreasOrganizacionais = "and ao.id in (:areaIds) ";
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			whereEstabelecimentos = "and es.id in (:estabelecimentoIds) ";
		if (colaboradorIds != null && colaboradorIds.length > 0)
			whereColaboradores = "and co.id in (:colaboradorIds) ";
		if (exameIds != null && exameIds.length > 0)
			whereExames = "and e.id in (:exameIds) ";
		if (imprimirAfastados)
			afastados = "and (afa.fim = null or afa.fim >= :data) ";
		if (!imprimirDesligados)
			whereDesligados = "and co.desligado = :imprimirDesligados  ";
		
		StringBuilder hql = new StringBuilder();
		hql.append("select new com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio(co.id,e.id,ao.id,ca.nome,co.nome,co.nomeComercial,e.nome,se.data,re.data,ese.periodicidade, se.motivo) ");
		hql.append("from SolicitacaoExame se ");
		hql.append("join se.exameSolicitacaoExames ese ");
		hql.append("join ese.exame e ");
		hql.append("join ese.realizacaoExame re ");
		hql.append("join se.colaborador co ");
		hql.append("left join co.colaboradorAfastamento afa ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("where se.empresa.id = :empresaId ");
		hql.append("and ese.periodicidade > 0 ");
		hql.append("and se.data <= :data ");
		hql.append(whereDesligados);
		hql.append(whereAreasOrganizacionais);
		hql.append(whereEstabelecimentos);
		hql.append(whereColaboradores);
		hql.append(whereExames);
		hql.append(afastados);
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("	) ");
		hql.append("and re.resultado != :naoRealizado ");
		hql.append("ORDER BY co.id,e.id ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("hoje", new Date());
		query.setDate("data", data);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("empresaId", empresaId);
		
		if(!imprimirDesligados)
			query.setBoolean("imprimirDesligados", imprimirDesligados);

		query.setString("naoRealizado", ResultadoExame.NAO_REALIZADO.toString());
		
		if (areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		if (colaboradorIds != null && colaboradorIds.length > 0)
			query.setParameterList("colaboradorIds", colaboradorIds, Hibernate.LONG);
		if (exameIds != null && exameIds.length > 0)
			query.setParameterList("exameIds", exameIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<ExamesRealizadosRelatorio> findExamesRealizadosRelatorioResumido(Long empresaId, Date dataInicio, Date dataFim, ClinicaAutorizada clinicaAutorizada, Long[] examesIds)
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio(e.id, e.nome, clinica.id, clinica.nome, count(*)) ");
		hql.append("from ExameSolicitacaoExame ese ");
		hql.append("join ese.realizacaoExame re ");
		hql.append("left join ese.clinicaAutorizada clinica ");
		hql.append("join ese.exame e ");

		hql.append(" where e.empresa.id = :empresaId ");
		hql.append(" and re.data between :dataInicio and :dataFim ");
		
		if (clinicaAutorizada != null && clinicaAutorizada.getId() != null)
			hql.append(" and clinica.id = :clinicaAutorizadaId ");
		
		if (examesIds != null && examesIds.length > 0)
			hql.append(" and e.id in (:exameIds) ");
		
		hql.append(" group by e.id, e.nome, clinica.id, clinica.nome ");
		hql.append(" order by e.nome, clinica.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataInicio", dataInicio);
		query.setDate("dataFim", dataFim);
		query.setLong("empresaId", empresaId);
		
		if (clinicaAutorizada != null && clinicaAutorizada.getId() != null)
			query.setLong("clinicaAutorizadaId", clinicaAutorizada.getId());
		
		if (examesIds != null && examesIds.length > 0)
			query.setParameterList("exameIds", examesIds, Hibernate.LONG);
		
		return query.list();
	}
	public Collection<ExamesRealizadosRelatorio> findExamesRealizados(Long empresaId, String nomeBusca, Date inicio, Date fim, String solicitacaoMotivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds, String vinculo)  
	{
		String whereEstabelecimentos = "", whereExames = "", whereResultado="", whereClinica="", whereMotivo="";
		
		if (vinculo.equals("COLABORADOR") && estabelecimentosIds != null && estabelecimentosIds.length > 0)
			whereEstabelecimentos = "and es.id in (:estabelecimentoIds) ";
		
		if (examesIds != null && examesIds.length > 0)
			whereExames = "and e.id in (:exameIds) ";
		
		if (clinicaAutorizadaId != null)
			whereClinica = "and clinica.id = :clinicaId ";
		
		if (StringUtils.isNotBlank(solicitacaoMotivo))
			whereMotivo = "and se.motivo = :motivo ";
		
		if (StringUtils.isNotBlank(exameResultado))
		{
			if(exameResultado.equalsIgnoreCase(ResultadoExame.NAO_REALIZADO.toString()))
				whereResultado = "and (re = null or re.resultado = :resultado) ";
			else
				whereResultado = "and re.resultado = :resultado ";
		}
		
		StringBuilder hql = new StringBuilder("select distinct new com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio(e.id,co.nome,ca.nome,e.nome,se.data,clinica.id,clinica.nome,re.resultado,se.motivo) ");
		hql.append("from ExameSolicitacaoExame ese ");
		hql.append("left join ese.realizacaoExame re ");
		hql.append("left join ese.clinicaAutorizada clinica ");
		hql.append("left join ese.exame e ");
		hql.append("left join ese.solicitacaoExame se ");
		hql.append("left join se.colaborador co ");
		hql.append("left join se.candidato ca ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("where se.empresa.id = :empresaId ");
		hql.append("and se.data between :inicio and :fim ");
		if (isNotBlank(nomeBusca))
			hql.append("and lower(co.nome) like :nome ");
		hql.append(whereEstabelecimentos);
		hql.append(whereExames);
		hql.append(whereClinica);
		hql.append(whereMotivo);
		hql.append(whereResultado);
		
		if (vinculo.equals("COLABORADOR"))
		{
			hql.append("	and hc.data = ( ");
			hql.append("		select max(hc2.data) " );
			hql.append("		from HistoricoColaborador as hc2 ");
			hql.append("		where hc2.colaborador.id = co.id ");
			hql.append("			and hc2.data <= :hoje  and hc2.status = :status ");
			hql.append("	) ");
		}

		hql.append("ORDER BY clinica.id ASC,e.nome ASC,se.data ASC ");
		
		Query query = getSession().createQuery(hql.toString());

		if (vinculo.equals("COLABORADOR"))
		{
			query.setDate("hoje", new Date());			
			query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		}
		
		if (isNotBlank(nomeBusca))
			query.setString("nome", "%" + nomeBusca.toLowerCase() + "%");
		
		query.setDate("inicio", inicio);
		query.setDate("fim", fim);
		query.setLong("empresaId", empresaId);
		
		if (vinculo.equals("COLABORADOR") && estabelecimentosIds != null && estabelecimentosIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentosIds, Hibernate.LONG);
		
		if (examesIds != null && examesIds.length > 0)
			query.setParameterList("exameIds", examesIds, Hibernate.LONG);
		
		if (clinicaAutorizadaId != null)
			query.setLong("clinicaId", clinicaAutorizadaId);
		
		if (StringUtils.isNotBlank(solicitacaoMotivo))
			query.setString("motivo", solicitacaoMotivo);
		
		if (StringUtils.isNotBlank(exameResultado))
		query.setString("resultado", exameResultado);
		
		return query.list();
	}
	
	private void montaQuery(Long empresaId, Exame exame, Criteria criteria) {
		if(empresaId != null)
			criteria.add(Expression.eq("e.empresa.id", empresaId));
		
		if (exame != null && isNotBlank(exame.getNome()))
			criteria.add(Expression.sqlRestriction("normalizar({alias}.nome) ilike normalizar(?)", "%" + exame.getNome() + "%", Hibernate.STRING));
	}

	public Integer getCount(Long empresaId, Exame exame) {

		Criteria criteria = getSession().createCriteria(Exame.class, "e");

		montaQuery(empresaId, exame, criteria);

		criteria.setProjection(Projections.rowCount());

		return (Integer) criteria.uniqueResult();
	}

	public Collection<Exame> find(Integer page, Integer pagingSize, Long empresaId, Exame exame) 
	{
		Criteria criteria = getSession().createCriteria(Exame.class, "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.periodico"), "periodico");
		p.add(Projections.property("e.periodicidade"), "periodicidade");

		criteria.setProjection(p);
		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);
		
		montaQuery(empresaId, exame, criteria);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Exame.class));

		return criteria.list();
	}
}
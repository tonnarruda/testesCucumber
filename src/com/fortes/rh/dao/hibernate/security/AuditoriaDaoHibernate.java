package com.fortes.rh.dao.hibernate.security;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.security.AuditoriaDao;
import com.fortes.rh.model.security.Auditoria;
import com.fortes.rh.util.StringUtil;

@Component
public class AuditoriaDaoHibernate extends GenericDaoHibernate<Auditoria> implements AuditoriaDao
{
	@SuppressWarnings("unchecked")
	public Map findEntidade(Long empresaId)
	{
		Map<String, String> comboEntidade = new LinkedHashMap<String, String>();

		String queryHQL = "select a.entidade from Auditoria a where a.empresa.id="+empresaId+" group by a.entidade order by a.entidade";

		Session newSession = getSession();
		Collection<String> result = newSession.createQuery(queryHQL).list();

		comboEntidade.put("", "Todas");//na criteria ele não fará where por entidade
		for (String string : result)
		{
			comboEntidade.put(string, StringUtil.camelCaseToSnakeCase(string));
		}

		return comboEntidade;
	}

	private Criteria getCriteriaAuditoria()
	{
		Criteria criteria = getSession().createCriteria(Auditoria.class, "a");
		criteria.createCriteria("a.usuario", "u", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.dados"), "dados");
		p.add(Projections.property("a.data"), "data");
		p.add(Projections.property("a.chave"), "chave");
		p.add(Projections.property("a.operacao"), "operacao");
		p.add(Projections.property("a.entidade"), "entidade");
		p.add(Projections.property("u.nome"), "usuarioNome");
		criteria.setProjection(p);

		return criteria;
	}

	public Auditoria projectionFindById(Long auditoriaId, Long empresaId)
	{
		Criteria criteria = getCriteriaAuditoria();

		criteria.add(Expression.eq("a.id",auditoriaId));
		criteria.add(Expression.eq("a.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Auditoria.class));

		return (Auditoria) criteria.uniqueResult();
	}

	public Integer getCount(Map parametros, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Auditoria.class, "a");
		criteria.createCriteria("a.usuario", "u");
		criteria.setProjection(Projections.rowCount());

		getFind(parametros, criteria, empresaId);

		return (Integer) criteria.list().get(0);
	}

	@SuppressWarnings({"unchecked","deprecation"})
	public Collection<Auditoria> list(int page, int pagingSize, Map parametros, Long empresaId)
	{
		Criteria criteria = getCriteriaAuditoria();

		getFind(parametros, criteria, empresaId);

		criteria.addOrder(Order.desc("a.data"));
		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Auditoria.class));

		return criteria.list();
	}

	@SuppressWarnings({"unchecked","deprecation"})
	private void getFind(Map parametros, Criteria criteria, Long empresaId)
	{
		//Periodo
		if(parametros.get("dataIni") != null)
		{
			Date dataAuxIni = (Date) parametros.get("dataIni");

			dataAuxIni.setHours(0);
			dataAuxIni.setMinutes(0);
			dataAuxIni.setSeconds(0);

			criteria.add(Expression.ge("a.data" ,dataAuxIni));
		}

		if(parametros.get("dataFim") != null)
		{
			Date dataAuxFim = (Date) parametros.get("dataFim");

			dataAuxFim.setHours(23);
			dataAuxFim.setMinutes(59);
			dataAuxFim.setSeconds(59);

			criteria.add(Expression.le("a.data",dataAuxFim));
		}

		//Usuario
		if(parametros.get("usuarioId") != null && (Long)parametros.get("usuarioId") != -1)
		{
			criteria.add(Expression.eq("u.id", parametros.get("usuarioId")));
		}

		//Operacao
		if(parametros.get("operacao") != null && !(parametros.get("operacao").equals("")))//la no ftl todas('T')
		{
			criteria.add(Expression.eq("a.operacao", parametros.get("operacao")));
		}
		
		//dados
		if(parametros.get("dados") != null && !(parametros.get("dados").equals("")))
		{
			criteria.add(Expression.ilike("a.dados", "%" + parametros.get("dados") + "%"));
		}

		//Entidade
		if(parametros.get("entidade") != null && !parametros.get("entidade").equals(""))
		{
			criteria.add(Expression.eq("a.entidade",parametros.get("entidade")));
		}

		//Empresa
		criteria.add(Expression.eq("a.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}

	@SuppressWarnings("unchecked")
	public List<String> findOperacoesPeloModulo(String modulo) {

		Criteria criteria = getSession()
			.createCriteria(Auditoria.class)
			.setProjection(Projections
				.alias(Projections.groupProperty("operacao"), "operacao"))
			.addOrder(Order.asc("operacao"));
			
		if (modulo != null 
				&& !"".equals(modulo))
			criteria.add(Restrictions.eq("entidade", modulo));
		
		List<String> operacoes = criteria.list();
		return operacoes;
	}
}
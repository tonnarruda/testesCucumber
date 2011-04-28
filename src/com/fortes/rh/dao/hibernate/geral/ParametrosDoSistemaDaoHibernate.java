package com.fortes.rh.dao.hibernate.geral;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ParametrosDoSistemaDao;
import com.fortes.rh.model.geral.ParametrosDoSistema;

public class ParametrosDoSistemaDaoHibernate extends GenericDaoHibernate<ParametrosDoSistema> implements ParametrosDoSistemaDao
{
	public ParametrosDoSistema findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(ParametrosDoSistema.class,"ps");
		criteria.createCriteria("ps.perfilPadrao", "p", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ps.id"), "id");
		p.add(Projections.property("ps.appVersao"), "appVersao");
		p.add(Projections.property("ps.appContext"), "appContext");
		p.add(Projections.property("ps.servidorRemprot"), "servidorRemprot");
		p.add(Projections.property("ps.atualizadorPath"), "atualizadorPath");
		p.add(Projections.property("ps.appUrl"), "appUrl");
		p.add(Projections.property("ps.atualizadoSucesso"), "atualizadoSucesso");
		p.add(Projections.property("ps.acVersaoWebServiceCompativel"), "acVersaoWebServiceCompativel");
		p.add(Projections.property("ps.upperCase"), "upperCase");
		p.add(Projections.property("ps.atualizaPapeisIdsAPartirDe"), "atualizaPapeisIdsAPartirDe");
		p.add(Projections.property("ps.campoExtraColaborador"), "campoExtraColaborador");
		p.add(Projections.property("ps.codEmpresaSuporte"), "codEmpresaSuporte");
		p.add(Projections.property("ps.codClienteSuporte"), "codClienteSuporte");
		p.add(Projections.property("ps.camposCandidatoVisivel"), "camposCandidatoVisivel");
		p.add(Projections.property("ps.camposCandidatoObrigatorio"), "camposCandidatoObrigatorio");
		p.add(Projections.property("ps.camposCandidatoTabs"), "camposCandidatoTabs");
		p.add(Projections.property("ps.enviarEmail"), "enviarEmail");
		p.add(Projections.property("ps.emailSmtp"), "emailSmtp");
		p.add(Projections.property("ps.emailPort"), "emailPort");
		p.add(Projections.property("ps.emailUser"), "emailUser");
		p.add(Projections.property("ps.emailPass"), "emailPass");
		p.add(Projections.property("p.id"), "projectionPerfilPadraoId");
		p.add(Projections.property("p.nome"), "projectionPerfilPadraoNome");
		criteria.setProjection(p);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ParametrosDoSistema.class));

		return (ParametrosDoSistema) criteria.add(Expression.eq("ps.id", id)).uniqueResult();
	}

	public String findModulos(Long id)
	{
		Criteria criteria = getSession().createCriteria(ParametrosDoSistema.class,"ps");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ps.modulos"), "modulos");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ps.id", id));

		return (String) criteria.uniqueResult();
	}

	public void updateModulos(String papeis)
	{
		String hql = "update ParametrosDoSistema p set p.modulos = :papeisCodificados";
		Query query = getSession().createQuery(hql);
		query.setString("papeisCodificados", papeis);
		query.executeUpdate();
	}

	public void disablePapeisIds() {
		
		String hql = "update ParametrosDoSistema p set p.atualizaPapeisIdsAPartirDe = null";
		Query query = getSession().createQuery(hql);
		
		query.executeUpdate();
	}

	public void updateCampoExtra(boolean campoExtraColaborador) {
		
		String hql = "update ParametrosDoSistema p set p.campoExtraColaborador = :campoExtraColaborador";
		Query query = getSession().createQuery(hql);
		query.setBoolean("campoExtraColaborador", campoExtraColaborador);
		query.executeUpdate();
	}
}
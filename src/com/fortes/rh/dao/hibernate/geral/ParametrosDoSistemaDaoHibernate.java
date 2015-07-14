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
		p.add(Projections.property("ps.telaInicialModuloExterno"), "telaInicialModuloExterno");
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
		p.add(Projections.property("ps.emailRemetente"), "emailRemetente");
		p.add(Projections.property("ps.caminhoBackup"), "caminhoBackup");
		p.add(Projections.property("ps.inibirGerarRelatorioPesquisaAnonima"), "inibirGerarRelatorioPesquisaAnonima");
		p.add(Projections.property("ps.quantidadeColaboradoresRelatorioPesquisaAnonima"), "quantidadeColaboradoresRelatorioPesquisaAnonima");
		p.add(Projections.property("p.id"), "projectionPerfilPadraoId");
		p.add(Projections.property("p.nome"), "projectionPerfilPadraoNome");
		p.add(Projections.property("ps.proximaVersao"), "proximaVersao");
		p.add(Projections.property("ps.suporteVeica"), "suporteVeica");
		criteria.setProjection(p);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ParametrosDoSistema.class));

		return (ParametrosDoSistema) criteria.add(Expression.eq("ps.id", id)).uniqueResult();
	}

	public void updateServidorRemprot(String servidorRemprot) 
	{
		String hql = "update ParametrosDoSistema p set p.servidorRemprot = :servidorRemprot";
		Query query = getSession().createQuery(hql);
		query.setString("servidorRemprot", servidorRemprot);
		query.executeUpdate();
	}

	public String getContexto() 
	{
		StringBuilder hql = new StringBuilder("select appContext ");
		hql.append("from ParametrosDoSistema ");
		hql.append("where id = 1 ");
		Query query = getSession().createQuery(hql.toString());
				
		return query.uniqueResult().toString();
	}
}
package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraVisivelObrigadotorioDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;

public class ConfiguracaoCampoExtraVisivelObrigadotorioDaoHibernate extends GenericDaoHibernate<ConfiguracaoCampoExtraVisivelObrigadotorio> implements ConfiguracaoCampoExtraVisivelObrigadotorioDao{

	private ProjectionList projectionConfiguracao(){
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ccevo.id"), "id");
		p.add(Projections.property("ccevo.empresa.id"), "empresaId");
		p.add(Projections.property("ccevo.camposExtrasVisiveis"),"camposExtrasVisiveis");
		p.add(Projections.property("ccevo.camposExtrasObrigatorios"),"camposExtrasObrigatorios");
		p.add(Projections.property("ccevo.tipoConfiguracaoCampoExtra"),"tipoConfiguracaoCampoExtra");
		return p;
	}
	
	public ConfiguracaoCampoExtraVisivelObrigadotorio findByEmpresaId(Long empresaId, String tipoConfiguracao) {
		Criteria criteria = getSession().createCriteria(ConfiguracaoCampoExtraVisivelObrigadotorio.class, "ccevo");
		criteria.add(Expression.eq("ccevo.empresa.id", empresaId));
		criteria.add(Expression.eq("ccevo.tipoConfiguracaoCampoExtra", tipoConfiguracao));
		criteria.setProjection(projectionConfiguracao());
		 criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoCampoExtraVisivelObrigadotorio.class));
		return (ConfiguracaoCampoExtraVisivelObrigadotorio) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoCampoExtraVisivelObrigadotorio> findCollectionByEmpresaId(Long empresaId, String[] tiposConfiguracao) {
		Criteria criteria = getSession().createCriteria(ConfiguracaoCampoExtraVisivelObrigadotorio.class, "ccevo");
		criteria.add(Expression.eq("ccevo.empresa.id", empresaId));
		
		if(tiposConfiguracao != null  && tiposConfiguracao.length > 0)
			criteria.add(Expression.in("ccevo.tipoConfiguracaoCampoExtra", tiposConfiguracao));

		criteria.setProjection(projectionConfiguracao());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoCampoExtraVisivelObrigadotorio.class));
		return criteria.list();
	}

	public void removeByEmpresaAndTipoConfig(Long empresaId,String[] tipoConfiguracao) {
		String queryHQL = "delete from ConfiguracaoCampoExtraVisivelObrigadotorio ccevo where ccevo.empresa.id = :empresaId and tipoConfiguracaoCampoExtra in(:tipoConfiguracao) ";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("empresaId", empresaId);
		query.setParameterList("tipoConfiguracao", tipoConfiguracao);

		query.executeUpdate();
	}
}

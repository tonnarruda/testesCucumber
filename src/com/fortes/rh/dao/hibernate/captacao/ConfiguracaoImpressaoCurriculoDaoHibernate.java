package com.fortes.rh.dao.hibernate.captacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConfiguracaoImpressaoCurriculoDao;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;

@Component
public class ConfiguracaoImpressaoCurriculoDaoHibernate extends GenericDaoHibernate<ConfiguracaoImpressaoCurriculo> implements ConfiguracaoImpressaoCurriculoDao
{

	public ConfiguracaoImpressaoCurriculo findByUsuario(Long usuarioId, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		
		ProjectionList p = geraProjectionDosAtributosSimples("c");		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.usuario.id", usuarioId));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (ConfiguracaoImpressaoCurriculo) criteria.uniqueResult();
	}
}

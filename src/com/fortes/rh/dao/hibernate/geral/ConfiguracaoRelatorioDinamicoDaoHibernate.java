package com.fortes.rh.dao.hibernate.geral;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ConfiguracaoRelatorioDinamicoDao;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;

@Component
public class ConfiguracaoRelatorioDinamicoDaoHibernate extends GenericDaoHibernate<ConfiguracaoRelatorioDinamico> implements ConfiguracaoRelatorioDinamicoDao
{

	public void removeByUsuario(Long usuarioId) {
		String hql = "delete ConfiguracaoRelatorioDinamico where usuario.id = :usuarioId";
		Query query = getSession().createQuery(hql);
		query.setLong("usuarioId", usuarioId);

		query.executeUpdate();
	}
	
	public ConfiguracaoRelatorioDinamico findByUsuario(Long usuarioId) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Expression.eq("usuario.id", usuarioId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (ConfiguracaoRelatorioDinamico) criteria.uniqueResult();
	}
}

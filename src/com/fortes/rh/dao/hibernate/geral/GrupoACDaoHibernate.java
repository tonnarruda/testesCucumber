package com.fortes.rh.dao.hibernate.geral;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TGrupo;

@Component
public class GrupoACDaoHibernate extends GenericDaoHibernate<GrupoAC> implements GrupoACDao
{

	public TGrupo[] findTGrupos() 
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.ws.TGrupo(g.codigo, g.descricao) ");
		hql.append("from GrupoAC g ");

		hql.append("order by g.codigo ");

		Query query = getSession().createQuery(hql.toString());

		return (TGrupo[]) query.list().toArray(new TGrupo[]{});
	}

	public GrupoAC findByCodigo(String codigo) 
	{
		Criteria criteria = getSession().createCriteria(GrupoAC.class, "g");
		criteria.add(Expression.eq("g.codigo", codigo));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (GrupoAC) criteria.uniqueResult();
	}
}

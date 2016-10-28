package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.CodigoCBODao;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CodigoCBO;

@Component
public class CodigoCBODaoHibernate extends GenericDaoHibernate<CodigoCBO> implements CodigoCBODao
{
	public Collection<AutoCompleteVO> buscaCodigosCBO(String descricao)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AutoCompleteVO(c.id, c.codigo || ' - ' || c.descricao ) ");
		hql.append("from CodigoCBO as c ");
		
		hql.append(" where ");
		hql.append(" normalizar(upper(c.codigo)) like normalizar(:descricao) ");
		hql.append(" or normalizar(upper(c.descricao)) like normalizar(:descricao) ");
		
		hql.append(" order by c.descricao");		

		Query query = getSession().createQuery(hql.toString());
		query.setString("descricao", "%" + descricao.toUpperCase() + "%");
		
		return query.list();
	}

	public String findDescricaoByCodigo(String cboCodigo) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select c.descricao  ");
		hql.append("from CodigoCBO as c ");
		hql.append(" where c.codigo = :codigo ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setString("codigo", cboCodigo);
		
		return (String) query.uniqueResult();
	}
}

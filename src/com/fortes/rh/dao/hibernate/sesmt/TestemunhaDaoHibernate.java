package com.fortes.rh.dao.hibernate.sesmt;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.TestemunhaDao;
import com.fortes.rh.model.sesmt.Testemunha;

@Component
public class TestemunhaDaoHibernate extends GenericDaoHibernate<Testemunha> implements TestemunhaDao
{
	public void removerDependenciaComCat(Long idTestemunha, String nomeAtributo) {
		String hql = "update Cat set " + nomeAtributo +".id = null where " + nomeAtributo + ".id = :id";
		Query query = getSession().createQuery(hql);
		query.setLong("id", idTestemunha);
		query.executeUpdate();
	}
}
package com.fortes.rh.dao.hibernate.sesmt;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.TipoTamanhoEPIDao;
import com.fortes.rh.model.sesmt.TipoTamanhoEPI;

public class TipoTamanhoEPIDaoHibernate extends GenericDaoHibernate<TipoTamanhoEPI> implements TipoTamanhoEPIDao
{
	public void removeByTipoEPI(Long tipoEPIId) {
		String hql = "delete from TipoTamanhoEPI where tipoEPI.id = :tipoEPIId ";

		Query query = getSession().createQuery(hql);
		query.setLong("tipoEPIId", tipoEPIId);

		query.executeUpdate();
	}
}
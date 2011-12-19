package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.TipoDocumentoDao;
import com.fortes.rh.model.geral.TipoDocumento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.TipoDocumentoFactory;

public class TipoDocumentoDaoHibernateTest extends GenericDaoHibernateTest<TipoDocumento>
{
	private TipoDocumentoDao tipoDocumentoDao;

	@Override
	public TipoDocumento getEntity()
	{
		return TipoDocumentoFactory.getEntity();
	}

	@Override
	public GenericDao<TipoDocumento> getGenericDao()
	{
		return tipoDocumentoDao;
	}

	public void setTipoDocumentoDao(TipoDocumentoDao tipoDocumentoDao)
	{
		this.tipoDocumentoDao = tipoDocumentoDao;
	}
}

package com.fortes.rh.dao.hibernate.geral;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.TipoDocumentoDao;
import com.fortes.rh.model.geral.TipoDocumento;

@Component
public class TipoDocumentoDaoHibernate extends GenericDaoHibernate<TipoDocumento> implements TipoDocumentoDao
{
}

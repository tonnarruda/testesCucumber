package com.fortes.rh.business.geral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.TipoDocumentoDao;
import com.fortes.rh.model.geral.TipoDocumento;

@Component
public class TipoDocumentoManagerImpl extends GenericManagerImpl<TipoDocumento, TipoDocumentoDao> implements TipoDocumentoManager
{
	@Autowired
	TipoDocumentoManagerImpl(TipoDocumentoDao dao) {
		setDao(dao);
	}
}

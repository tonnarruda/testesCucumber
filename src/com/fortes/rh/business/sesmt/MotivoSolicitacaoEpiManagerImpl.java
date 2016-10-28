package com.fortes.rh.business.sesmt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.MotivoSolicitacaoEpiDao;
import com.fortes.rh.model.sesmt.MotivoSolicitacaoEpi;

@Component
public class MotivoSolicitacaoEpiManagerImpl extends GenericManagerImpl<MotivoSolicitacaoEpi, MotivoSolicitacaoEpiDao> implements MotivoSolicitacaoEpiManager
{
	@Autowired
	MotivoSolicitacaoEpiManagerImpl(MotivoSolicitacaoEpiDao motivoSolicitacaoEpiDao) {
			setDao(motivoSolicitacaoEpiDao);
	}
}

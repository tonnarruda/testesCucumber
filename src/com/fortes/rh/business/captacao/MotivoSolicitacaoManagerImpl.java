package com.fortes.rh.business.captacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.MotivoSolicitacaoDao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;

@Component
public class MotivoSolicitacaoManagerImpl extends GenericManagerImpl<MotivoSolicitacao, MotivoSolicitacaoDao> implements MotivoSolicitacaoManager
{
	@Autowired
	MotivoSolicitacaoManagerImpl(MotivoSolicitacaoDao solicitacaoDao) {
		setDao(solicitacaoDao);
	}
}
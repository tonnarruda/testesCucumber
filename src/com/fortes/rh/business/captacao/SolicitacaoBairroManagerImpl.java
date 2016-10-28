package com.fortes.rh.business.captacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.SolicitacaoBairroDao;
import com.fortes.rh.model.captacao.SolicitacaoBairro;

@Component
public class SolicitacaoBairroManagerImpl extends GenericManagerImpl<SolicitacaoBairro, SolicitacaoBairroDao> implements SolicitacaoBairroManager
{
	@Autowired
	SolicitacaoBairroManagerImpl(SolicitacaoBairroDao solicitacaoDao) {
		setDao(solicitacaoDao);
	}
}
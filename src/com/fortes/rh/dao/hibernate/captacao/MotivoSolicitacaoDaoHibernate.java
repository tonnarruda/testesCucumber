package com.fortes.rh.dao.hibernate.captacao;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.MotivoSolicitacaoDao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;

@Component
public class MotivoSolicitacaoDaoHibernate extends GenericDaoHibernate<MotivoSolicitacao> implements MotivoSolicitacaoDao
{
}
package com.fortes.rh.dao.hibernate.captacao;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.SolicitacaoBairroDao;
import com.fortes.rh.model.captacao.SolicitacaoBairro;

@Component
public class SolicitacaoBairroDaoHibernate extends GenericDaoHibernate<SolicitacaoBairro> implements SolicitacaoBairroDao
{
}
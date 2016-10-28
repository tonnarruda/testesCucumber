package com.fortes.rh.dao.hibernate.captacao;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.CandidatoCurriculoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;

@Component
public class CandidatoCurriculoDaoHibernate extends GenericDaoHibernate<CandidatoCurriculo> implements CandidatoCurriculoDao
{
	public void removeCandidato(Candidato candidato) throws Exception
	{
		String queryHQL = "delete from CandidatoCurriculo cc where cc.candidato.id = :candidato";

		getSession().createQuery(queryHQL).setLong("candidato",candidato.getId()).executeUpdate();
	}
}
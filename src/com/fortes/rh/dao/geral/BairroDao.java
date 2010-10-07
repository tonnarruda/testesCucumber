package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Bairro;

public interface BairroDao extends GenericDao<Bairro>
{
	Collection<Bairro> list(int page, int pagingSize, Bairro bairro);
	public boolean existeBairro(Bairro bairro);
	Collection<Bairro> findAllSelect(Long cidadeId);
	Collection<Bairro> getBairrosBySolicitacao(Long solicitacaoId);
	Collection<Bairro> getBairrosByIds(Long[] bairroIds);
	Bairro findByIdProjection(Long id);
	Collection<String> findBairrosNomes();
}
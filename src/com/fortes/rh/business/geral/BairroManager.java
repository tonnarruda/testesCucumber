package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;

public interface BairroManager extends GenericManager<Bairro>
{
	Collection<Bairro> list(int page, int pagingSize, Bairro bairro);
	public boolean existeBairro(Bairro bairro);
	public Collection<Bairro> getBairrosBySolicitacao(Long solicitacaoId);
	Collection<Bairro> findAllSelect(Long cidadeId);
	public Collection<Bairro> getBairrosByIds(Long[] bairrosIds);
	void migrarRegistros(Bairro bairro, Bairro bairroDestino) throws Exception;
	public Bairro findByIdProjection(Long id);
	Collection<Bairro> findByCidade(Cidade cidade);
	String getArrayBairros();
	Integer getCount(Bairro bairro);
}
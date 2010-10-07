package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.ComissaoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;

public interface ComissaoEleicaoManager extends GenericManager<ComissaoEleicao>
{

	Collection<ComissaoEleicao> findByEleicao(Long eleicaoId);

	void save(String[] colaboradorsCheck, Eleicao eleicao) throws Exception;

	void updateFuncao(String[] comissaoEleicaoIds, String[] funcaoComissaos)throws Exception;

	void removeByEleicao(Long eleicaoId);

}
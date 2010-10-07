package com.fortes.rh.dao.pesquisa;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.pesquisa.Resposta;

public interface RespostaDao extends GenericDao<Resposta>
{
	Collection<Resposta> findByPergunta(Long id);
	Collection<Resposta> findInPerguntaIds(Long[] perguntaIds);
	void removerRespostasDasPerguntas(Collection<Long> perguntaIds);
}
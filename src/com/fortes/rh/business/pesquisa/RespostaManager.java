package com.fortes.rh.business.pesquisa;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;

public interface RespostaManager extends GenericManager<Resposta>
{
	Collection<Resposta> findByPergunta(Long id);
	Collection<Resposta> findInPerguntaIds(Long[] perguntaIds);
	Collection<Resposta> salvarRespostas(Long perguntaId, String[] respostaRetorno, Integer[] pesoRespostaObjetiva);
	Collection<Resposta> findRespostasSugeridas(Long questionarioId);
	void clonarResposta(Pergunta perguntaClonada, Collection<Resposta> respostas);
	void removerRespostasDasPerguntas(Collection<Long> perguntaIds);
}
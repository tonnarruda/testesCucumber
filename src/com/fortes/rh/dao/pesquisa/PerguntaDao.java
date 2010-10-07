package com.fortes.rh.dao.pesquisa;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.pesquisa.Pergunta;

public interface PerguntaDao extends GenericDao<Pergunta>
{
	Collection<Pergunta> findByQuestionario(Long questionarioId);
	Integer getUltimaOrdenacao(Long questionarioId) throws Exception;
	Pergunta findByIdProjection(Long perguntaId);
	Collection<Pergunta> findByQuestionarioAspecto(Long questionarioId, Long[] aspectosIds);
	Long findUltimaPerguntaObjetiva(Long questionarioOrAvaliacaoId);
	boolean reordenaPergunta(Long perguntaId, char sinal);
	Long findIdByOrdem(Long questionarioId, int ordem);
	boolean reposicionarPerguntas(Long questionarioId, Integer ordem, char sinal);
	void updateAndReorder(Pergunta pergunta);
	void updateOrdem(Long perguntaId, int novaOrdem) throws Exception;
	Collection<Pergunta> findByQuestionarioAspectoPergunta(Long questionarioId, Long[] aspectosIds, Long[] perguntasIds, boolean agruparPorAspectos);
	void removerPerguntasDoQuestionario(Long questionarioId);
	Collection<Long> findPerguntasDoQuestionario(Long questionarioId);

	int getTotalPerguntas(Long questionarioId);
	boolean existsOrdem(Long questionarioOrAvaliacaoId, Integer ordem);
}
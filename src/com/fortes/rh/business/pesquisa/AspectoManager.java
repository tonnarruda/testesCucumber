package com.fortes.rh.business.pesquisa;

import java.util.Collection;
import java.util.HashMap;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.web.tags.CheckBox;

public interface AspectoManager extends GenericManager<Aspecto>
{
	void update(Aspecto aspecto, Long empresaId) throws Exception;
	void delete(Long grupoId, Long empresaId) throws Exception;
	Aspecto findByIdProjection(Long aspectoId);
	Collection<Aspecto> findByQuestionario(Long questionarioId);
	Collection<Aspecto> agruparPerguntasByAspecto(Collection<Aspecto> aspectos, Collection<Pergunta> perguntas, int ordemInicial);
	Aspecto saveOrGetAspectoByNome(String aspectoNome, Long questionarioId);
	Collection<Pergunta> desagruparPerguntasByAspecto(Collection<Aspecto> aspectos);
	Collection<CheckBox> populaCheckOrderNome(Long questionarioId);
	void removerAspectosDoQuestionario(Long questionarioId);
	HashMap<Long, Aspecto> clonarAspectos(Long questionarioId, Questionario questionarioClonado, Avaliacao avaliacaoClonada);
	String getAspectosByAvaliacao(Long avaliacaoId);
	Aspecto saveByAvaliacao(Aspecto aspecto, Avaliacao avaliacao);
	String getAspectosFormatadosByAvaliacao(Long avaliacaoId);
}